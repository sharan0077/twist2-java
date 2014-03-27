
ifndef prefix
prefix=/usr/local
endif

PROGRAM_NAME=twist2

default: clean
	ant jar

clean:
	ant clean

install:
	install -m 755 -d $(prefix)/lib/$(PROGRAM_NAME)/java
	install -m 755 -d $(prefix)/lib/$(PROGRAM_NAME)/java/libs
	install -m 644 libs/* $(prefix)/lib/$(PROGRAM_NAME)/java/libs
	install -m 644 build/jar/* $(prefix)/lib/$(PROGRAM_NAME)/java
	install -m 755 -d $(prefix)/bin
	install -m 755 twist-java.sh $(prefix)/bin
	install -m 755 twist-java-project-init.sh $(prefix)/bin
	install -m 755 -d $(prefix)/share/$(PROGRAM_NAME)/languages
	install -m 644 java.json $(prefix)/share/$(PROGRAM_NAME)/languages
	install -m 755 -d $(prefix)/share/$(PROGRAM_NAME)/skel/java
	install -m 755 -d $(prefix)/share/$(PROGRAM_NAME)/skel/env
	install -m 644 skel/StepImplementation.java $(prefix)/share/$(PROGRAM_NAME)/skel/java
	install -m 644 skel/classpath.json $(prefix)/share/$(PROGRAM_NAME)/skel/env
