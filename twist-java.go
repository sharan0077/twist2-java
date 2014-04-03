package main

import (
	"errors"
	"flag"
	"fmt"
	"github.com/twist2/common"
	"os"
	"os/exec"
	"path"
	"path/filepath"
	"strings"
)

const (
	executable_path           = "twist2_java_executable"
	additional_libs_env_name  = "twist2_java_additional_libs"
	classpath_env_name        = "twist2_java_classpath"
	jvm_args_env_name         = "twist2_java_jvm_args"
	main_class_name           = "com.thoughtworks.twist2.TwistRuntime"
	step_implementation_class = "StepImplementation.java"
)

func appendClasspath(source *string, classpath string) {
	if len(*source) == 0 {
		*source = classpath
	} else {
		*source = fmt.Sprintf("%s%c%s", *source, os.PathListSeparator, classpath)
	}
}

var projectRoot = ""
var start = flag.Bool("start", false, "Start the java runner")
var initialize = flag.Bool("init", false, "Initialize the java runner")

func getInstallationPath() string {
	possibleInstallationPaths := []string{"/usr/local/lib/twist2/java", "/usr/lib/twist2/java"}
	for _, p := range possibleInstallationPaths {
		if common.DirExists(p) {
			return p
		}
	}

	panic(errors.New("Can't find installation files"))
}

func getInstallationSharePath() string {
	possibleInstallationPaths := []string{"/usr/local/share/twist2", "/usr/share/twist2"}
	for _, p := range possibleInstallationPaths {
		if common.DirExists(p) {
			return p
		}
	}

	panic(errors.New("Can't find installation files"))
}

func getProjectRoot() string {
	pwd, err := os.Getwd()
	if err != nil {
		panic(err)
	}

	return pwd
}

func getIntelliJClasspath() string {
	intellijOutDir := path.Join(getProjectRoot(), "out", "production")
	if !common.DirExists(intellijOutDir) {
		return ""
	}

	cp := ""
	walker := func(path string, info os.FileInfo, err error) error {
		if path == intellijOutDir {
			return nil
		}
		if info.IsDir() {
			appendClasspath(&cp, path)
			// we need only top-level directories. Don't walk nested
			return filepath.SkipDir
		}
		return nil
	}
	filepath.Walk(intellijOutDir, walker)
	return cp
}

func getEclipseClasspath() string {
	eclipseOutDir := path.Join(getProjectRoot(), "bin")
	if !common.DirExists(eclipseOutDir) {
		return ""
	}

	return eclipseOutDir
}

// User set classpath & additional libs will be comma separated
// it could be relative path, but JVM needs full path to be specified
// so this function splits the path, convert them to absolute path forms a classpath
func getClassPathForVariable(envVariableName string) string {
	value := os.Getenv(envVariableName)
	cp := ""
	if len(value) > 0 {
		paths := strings.Split(value, ",")
		for _, p := range paths {
			abs, err := filepath.Abs(p)
			if err == nil {
				appendClasspath(&cp, abs)
			} else {
				appendClasspath(&cp, p)
			}
		}
	}
	return cp
}

type initializerFunc func()

func showMessage(action, filename string) {
	fmt.Printf(" %s  %s\n", action, filename)
}

func createSrcDirectory() {
	showMessage("create", "src/")
	if !common.DirExists("src") {
		err := os.Mkdir("src", 0755)
		if err != nil {
			fmt.Printf("Failed to make directory. %s\n", err.Error())
		}
	} else {
		showMessage("skip", "src/")
	}
}

func createStepImplementationClass() {
	destFile := path.Join("src", step_implementation_class)
	showMessage("create", destFile)
	if common.FileExists(destFile) {
		showMessage("skip", destFile)
	} else {
		srcFile := path.Join(getInstallationSharePath(), "skel", "java", step_implementation_class)
		err := common.CopyFile(srcFile, destFile)
		if err != nil {
			showMessage("error", fmt.Sprintf("Failed to copy %s. %s", srcFile, err.Error()))
		}
	}
}

func createJavaJSON() {
	destFile := path.Join("env", "default", "java.json")
	showMessage("create", destFile)
	if common.FileExists(destFile) {
		showMessage("skip", destFile)
	} else {
		srcFile := path.Join(getInstallationSharePath(), "skel", "env", "java.json")
		err := common.CopyFile(srcFile, destFile)
		if err != nil {
			showMessage("error", fmt.Sprintf("Failed to copy %s. %s", srcFile, err.Error()))
		}
	}
}

func printUsage() {
	flag.PrintDefaults()
	os.Exit(2)
}

func main() {
	flag.Parse()
	if *start {
		cp := ""
		appendClasspath(&cp, path.Join(getInstallationPath(), "*"))
		appendClasspath(&cp, path.Join(getInstallationPath(), "libs", "*"))

		// If user has specified classpath, that will be taken. If not search for IntelliJ and Eclipse out directories before giving up
		userSpecifiedClasspath := getClassPathForVariable(classpath_env_name)
		if userSpecifiedClasspath != "" {
			appendClasspath(&cp, userSpecifiedClasspath)
		} else {
			cpfound := false
			icp := getIntelliJClasspath()
			if icp != "" {
				appendClasspath(&cp, icp)
				cpfound = true
			} else {
				ecp := getEclipseClasspath()
				if ecp != "" {
					appendClasspath(&cp, ecp)
					cpfound = true
				}
			}

			if !cpfound {
				fmt.Println("Failed to detect project build path")
				fmt.Printf("Set \"%s\" variable to the build directory\n", classpath_env_name)
				fmt.Printf("Set \"%s\" variable to specify dependent libraries\n", additional_libs_env_name)
				fmt.Println("These variables can be set as environment variables or specify it in env/default/java.json file")
				os.Exit(2)
			}

			additionalLibs := getClassPathForVariable(additional_libs_env_name)
			if additionalLibs != "" {
				appendClasspath(&cp, additionalLibs)
			}
		}

		javaPath := os.Getenv(executable_path)
		if javaPath == "" {
			javaPath = "java"
		}
		args := []string{"-classpath", cp}
		if os.Getenv(jvm_args_env_name) != "" {
			args = append(args, os.Getenv(jvm_args_env_name))
		}
		args = append(args, main_class_name)
		cmd := exec.Command(javaPath, args...)
		cmd.Stdout = os.Stdout
		cmd.Stdin = os.Stdin
		err := cmd.Run()
		if err != nil {
			fmt.Printf("Failed to start Java. %s\n", err.Error())
			os.Exit(1)
		}
	} else if *initialize {
		funcs := []initializerFunc{createSrcDirectory, createStepImplementationClass, createJavaJSON}
		for _, f := range funcs {
			f()
		}
	} else {
		printUsage()
	}
}
