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
import java.net.ServerSocket;

public class Server {

    private ServerSocket server;

    private Reader inputStream;
    private Writer outputStream;

    private ConsoleReader reader;

    private GameHandler gameHandler;
    private StatusHandler status;
    private DisplayHandler display;

    public Server(ConsoleReader reader, GameHandler gameHandler, StatusHandler status, DisplayHandler display) {
        this.reader = reader;
        this.gameHandler = gameHandler;
        this.status = status;
        this.display = display;
    }

    public void initialize() {
        try {
            System.out.println(Constants.SERVER_WELCOME_MESSAGE);
            var port = reader.getNextInt();
            this.server = new ServerSocket(port);

            System.out.println(Constants.WAITING_MESSAGE);

            var client = this.server.accept();
            System.out.println(Constants.CONNECTED_TO_SERVER);

            this.outputStream = new StreamWriter(new ObjectOutputStream(client.getOutputStream()));
            this.inputStream = new StreamReader(new ObjectInputStream(client.getInputStream()));

            new Thread(() -> {
                Packet packet;
                while (true) {
                    packet = this.inputStream.read();

                    if (packet == null || packet.getOutput().equals(Constants.QUIT_COMMAND)) {
                        break;
                    }

                    var result = this.gameHandler.play(packet.getOutput(), false);

                    if(this.status.isWon()) {
                        result += System.lineSeparator() + Constants.WON_STATUS;
                    }

                    this.outputStream.write(new Packet(result, true));

                    System.out.println(result);
                }

                this.outputStream.write(new Packet(Constants.GAME_OVER, false));
                System.out.println(Constants.GAME_OVER);

                this.inputStream.closeConnection();
                this.outputStream.closeConnection();
                System.exit(0);
            }).start();

            this.sendToClient(new Packet(this.display.show(), true));

            ConsoleCleaner.clear();
            System.out.println(this.display.show());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void run() {
        while (true) {
            var command = this.reader.getValidCommand();
            if (command.equals(Constants.QUIT_COMMAND)) {
                this.sendToClient(new Packet(Constants.GAME_OVER, false));
                break;
            }

            var result = gameHandler.play(command, true);

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

    void sendToClient(Packet data) {
        outputStream.write(data);
    }

}
