package core;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

    private Scanner scanner;

    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public Client(Scanner scanner) {
        this.scanner = scanner;
    }

    public void connect() {
        Socket client;
        var validInput = false;

        while (!validInput) {
            try {
                System.out.println("Please enter the port you want to connect to: ");
                var port = scanner.nextInt();

                client = new Socket("localhost", port);

                this.outputStream = new ObjectOutputStream(client.getOutputStream());
                this.inputStream = new ObjectInputStream(client.getInputStream());

                validInput = true;
            } catch (UnknownHostException e) {
                System.out.println("This host is invalid, please try again: ");
            } catch (IOException e) {
                System.out.println("There has been a problem connecting you to this port!");
                e.printStackTrace();
            }
        }

    }

    public void waitForInput() {
        try {
            String line;
            while(true) {
                line = scanner.nextLine();
                if(line == "quit") {
                    break;
                }

                outputStream.writeObject(line);
                outputStream.flush();
            }


        } catch (IOException e) {
            System.out.println("There has been a problem with the client!");
            e.printStackTrace();
        } finally {
            try {
                scanner.close();
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                System.out.println("There has been a problem closing the connections by the client");
            }
        }
    }

    public void waitForServer() {
        new Thread(() -> {
            String line;
            while(true) {
                try {
                    line = (String) this.inputStream.readObject();

                    if(line != null && line != "quit") {
                        System.out.println(line);
                    }
                } catch (ClassNotFoundException | IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


}
