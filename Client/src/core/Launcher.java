package core;

import java.util.Scanner;

public class Launcher {

    public static void main(String[] args) {

        var scanner = new Scanner(System.in);
        var client = new Client(scanner);
        client.connect();
        client.waitForServer();
        client.waitForInput();

    }

}
