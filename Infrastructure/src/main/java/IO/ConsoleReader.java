package IO;

import infrastrucutre.CoreValidator;

import java.util.Scanner;

public class ConsoleReader {

    private Scanner scanner;

    public ConsoleReader(Scanner scanner) {
        this.scanner = scanner;
    }

    public String getNext() {
        return this.scanner.next();
    }

    public int getNextInt() {
        return this.scanner.nextInt();
    }

    public String getValidCommand() {
        String command = null;

        var commandIsValid = false;
        while (!commandIsValid) {
            try {
                command = this.getNext();
                CoreValidator.checkInput(command);
                commandIsValid = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        return command;
    }

    public void closeConnection() {
        this.scanner.close();
    }
}
