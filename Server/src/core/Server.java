package core;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.util.Scanner;

public class Server {

    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private Scanner scanner;

    public Server(Scanner scanner) {
        this.scanner = scanner;
    }

    public void initialize() {
        ServerSocket server;

        try {
            server = new ServerSocket(8080);

            System.out.println("Server is waiting for clients ...");

            var client = server.accept();
            System.out.println("Connection with player established!");

            this.outputStream = new ObjectOutputStream(client.getOutputStream());
            this.outputStream.flush();
            this.inputStream = new ObjectInputStream(client.getInputStream());

            new Thread(() -> {

                try {
                    String line;
                    while(true) {
                        line = (String) inputStream.readObject();

                        if(line != null && line != "quit") {
                            System.out.println(line);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();

                } finally {
                    try {
                        inputStream.close();
                        outputStream.close();

                    } catch (IOException e) {
                        System.out.println("There has been a problem closing the connections on the server");
                    }
                }

            }).start();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("There has been an error on the server");
        }

    }

    public void readAndSendToClient() {
        while(true) {
            try {
                var command = this.scanner.nextLine();
                outputStream.writeObject(command);
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
