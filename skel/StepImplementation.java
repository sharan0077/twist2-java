import com.thoughtworks.twist2.Step;

public class StepImplementation {
    @Step("Say hello to {arg0}")
    public void helloWorld(String name) {
        System.out.println("Hello, " + name);
    }
}