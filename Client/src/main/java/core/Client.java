package core;

import IO.StreamReader;
import IO.StreamWriter;
import IO.contracts.UserReader;
import data.Packet;
import infrastrucutre.ConsoleCleaner;
import infrastrucutre.Constants;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    private UserReader reader;

    private Socket client;
    private StreamReader inputStream;
    private StreamWriter outputStream;

    public Client(UserReader reader) {
        this.reader = reader;
    }

    public void connect() {
        var validInput = false;

        while (!validInput) {
            try {
                System.out.println(Constants.CLIENT_WELCOME_MESSAGE);
                var port = reader.getPort();
                System.out.println(Constants.CLIENT_IP_REQUEST);
                this.client = new Socket(reader.getIP(), port);

                this.outputStream = new StreamWriter(new ObjectOutputStream(client.getOutputStream()));
                this.inputStream = new StreamReader(new ObjectInputStream(client.getInputStream()));

                validInput = true;
            } catch (UnknownHostException e) {
                System.out.println(Constants.INVALID_ADDRESS);
            } catch (IOException e) {
                System.out.println(Constants.UNABLE_TO_CONNECT);
            }
        }

        this.getServerHandler().start();
    }

    public void run() {
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

    private Thread getServerHandler() {
        return new Thread(() -> {
            Packet packet;
            while (true) {
                packet = inputStream.read();

                if (packet == null || packet.getOutput().equals(Constants.QUIT_COMMAND)) {
                    System.out.println(Constants.GAME_OVER);
                    break;
                }

                if(packet.isToClearConsole()) {
                    ConsoleCleaner.clear();
                }
                System.out.println(packet.getOutput());
            }
            System.exit(0);
        });
    }

}
