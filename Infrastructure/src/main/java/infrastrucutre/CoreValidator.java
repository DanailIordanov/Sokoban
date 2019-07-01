package infrastrucutre;

public class CoreValidator {

    public static void checkInput(String input) {
        if (input.length() != 1 && !input.equals("quit")) {
            throw new IllegalArgumentException("Your input should be just a single letter!");
        } else if (!input.equals("w") && !input.equals("s") && !input.equals("d") && !input.equals("a") && !input.equals("quit")) {
            throw new IllegalArgumentException("The key you have pressed is not a valid command!");
        }
    }

}
