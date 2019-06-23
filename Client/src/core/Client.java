package core;

import IO.ConsoleReader;
import IO.StreamReader;
import IO.StreamWriter;
import data.Packet;
import infrastrucutre.ConsoleCleaner;
import infrastrucutre.Constants;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    private ConsoleReader reader;

    private Socket client;
    private StreamReader inputStream;
    private StreamWriter outputStream;

    public Client(ConsoleReader reader) {
        this.reader = reader;
    }

    public void connect() {
        var validInput = false;

        while (!validInput) {
            try {
                System.out.println(Constants.CLIENT_WELCOME_MESSAGE);
                var port = reader.getNextInt();

                this.client = new Socket("localhost", port);

                this.outputStream = new StreamWriter(new ObjectOutputStream(client.getOutputStream()));
                this.inputStream = new StreamReader(new ObjectInputStream(client.getInputStream()));

                validInput = true;
            } catch (UnknownHostException e) {
                System.out.println(Constants.INVALID_HOST);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void waitForServer() {
        new Thread(() -> {
            Packet packet;
            while (true) {
                packet = inputStream.read();

                if (packet != null && !packet.getOutput().equals(Constants.QUIT_COMMAND)) {
                    if(packet.isToClearConsole()) {
                        ConsoleCleaner.clear();
                    }
                    System.out.println(packet.getOutput());
                } else {
                    System.out.println(Constants.GAME_OVER);
                    break;
                }
            }
            System.exit(0);
        }).start();
    }

    public void waitForInput() {
        String line;
        while (true) {
            line = reader.getValidCommand();

            outputStream.write(new Packet(line, false));

            if (line.equals(Constants.QUIT_COMMAND)) break;
        }

        reader.closeConnection();
        inputStream.closeConnection();
        outputStream.closeConnection();
        System.exit(0);
    }

}
