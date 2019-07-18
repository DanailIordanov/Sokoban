package IO;

import IO.contracts.UserReader;
import infrastrucutre.CoreValidator;
import infrastrucutre.GameMode;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ConsoleReader implements UserReader {

    private Scanner scanner;

    public ConsoleReader(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public GameMode getGameMode() {
        return null;
    }

    @Override
    public InetAddress getIP() throws UnknownHostException {
        var line = this.scanner.next();
        return InetAddress.getByName(line);
    }

    @Override
    public int getPort() {
        return this.scanner.nextInt();
    }

    @Override
    public String getValidCommand() {
        String command = null;

        var commandIsValid = false;
        while (!commandIsValid) {
            try {
                command = this.scanner.next();
                CoreValidator.checkInput(command);
                commandIsValid = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        return command;
    }

    @Override
    public void closeConnection() {
        this.scanner.close();
    }
}
