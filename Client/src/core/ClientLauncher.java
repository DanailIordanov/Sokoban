package core;

import IO.ConsoleReader;

import java.util.Scanner;

public class ClientLauncher {

    public static void main(String[] args) {

        var scanner = new Scanner(System.in);
        var reader = new ConsoleReader(scanner);
        var client = new Client(reader);
        client.connect();
        client.waitForServer();
        client.waitForInput();

    }

}
