package core;

import IO.ConsoleReader;
import IO.StreamReader;
import IO.StreamWriter;
import IO.contracts.Reader;
import IO.contracts.Writer;
import data.Packet;
import handlers.DisplayHandler;
import handlers.GameHandler;
import handlers.StatusHandler;
import infrastrucutre.ConsoleCleaner;
import infrastrucutre.Constants;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;

public class Server {

    private ServerSocket server;

    private Reader inputStream;
    private Writer outputStream;

    private ConsoleReader reader;

    private GameHandler game;
    private StatusHandler status;
    private DisplayHandler display;

    public Server(ConsoleReader reader, GameHandler game, StatusHandler status, DisplayHandler display) {
        this.reader = reader;
        this.game = game;
        this.status = status;
        this.display = display;
    }

    public void initialize() {
        System.out.println(Constants.SERVER_WELCOME_MESSAGE);
        var port = reader.getNextInt();

        var localHost = this.detectAddress();

        try {
            this.server = new ServerSocket(port, 1, localHost);
        } catch (IOException e) {
            System.out.println(Constants.UNABLE_TO_INITIALIZE_SERVER);
            System.exit(0);
        }

        System.out.println(Constants.WAITING_MESSAGE);
        System.out.println(Constants.REACHED_ON + localHost.toString());

        this.establishConnection();

        this.getClientHandler().start();

        this.sendToClient(new Packet(this.display.show(), true));

        ConsoleCleaner.clear();
        System.out.println(this.display.show());

    }

    public void run() {
        while (true) {
            var command = this.reader.getValidCommand();
            if (command.equals(Constants.QUIT_COMMAND)) {
                this.sendToClient(new Packet(Constants.GAME_OVER, false));
                break;
            }

            ConsoleCleaner.clear();

            var result = game.play(command, true);

            this.sendToClient(new Packet(result, true));

            System.out.println(result);

            if (this.status.isWon()) {
                this.sendToClient(new Packet(Constants.WON_STATUS, false));
                System.out.println(Constants.WON_STATUS);

                break;
            }
        }

        this.reader.closeConnection();
        System.exit(0);
    }

    private InetAddress detectAddress() {
        InetAddress localHost = null;
        try {
            localHost = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            System.out.println(Constants.UNABLE_TO_DETECT_ADDRESS);
            var validHost = false;
            while (!validHost) {
                try {
                    localHost = InetAddress.getByName(reader.getNext());
                    validHost = true;
                } catch (UnknownHostException ex) {
                    System.out.println(Constants.INVALID_ADDRESS);
                }
            }
        }
        return localHost;
    }

    private void establishConnection() {
        try {
            var client = this.server.accept();
            System.out.println(Constants.CONNECTED_TO_SERVER);

            this.outputStream = new StreamWriter(new ObjectOutputStream(client.getOutputStream()));
            this.inputStream = new StreamReader(new ObjectInputStream(client.getInputStream()));
        } catch (IOException e) {
            System.out.println(Constants.UNABLE_TO_CONNECT);
        }
    }

    private Thread getClientHandler() {
        return new Thread(() -> {
            Packet packet;
            while (true) {
                packet = this.inputStream.read();

                if (packet == null || packet.getOutput().equals(Constants.QUIT_COMMAND)) {
                    break;
                }

                var result = this.respondToClient(packet);

                System.out.println(result);
            }

            this.sendToClient(new Packet(Constants.GAME_OVER, false));
            System.out.println(Constants.GAME_OVER);

            this.inputStream.closeConnection();
            this.outputStream.closeConnection();
            this.closeConnection();
            System.exit(0);
        });
    }

    private String respondToClient(Packet packet) {
        var result = this.game.play(packet.getOutput(), false);

        if (this.status.isWon()) {
            result += System.lineSeparator() + Constants.WON_STATUS;
        }

        this.sendToClient(new Packet(result, true));

        return result;
    }

    private void sendToClient(Packet data) {
        outputStream.write(data);
    }

    private void closeConnection() {
        try {
            this.server.close();
        } catch (IOException e) {
            System.out.println();
        }
    }
}
