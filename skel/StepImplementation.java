import com.thoughtworks.twist2.Step;

public class StepImplementation {
    @Step("Say {} to the {}")
    public void helloWorld(String greeting, String name) {
        System.out.println(greeting + ", " + name);
    }
}

