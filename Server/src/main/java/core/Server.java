package core;

import IO.contracts.UserReader;
import IO.contracts.UserWriter;
import data.Packet;
import handlers.ConnectionHandler;
import handlers.DisplayHandler;
import handlers.GameHandler;
import handlers.StatusHandler;
import infrastrucutre.ConsoleCleaner;
import infrastrucutre.Constants;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {

    private ServerSocket server;

    private UserReader reader;
    private UserWriter writer;

    private DisplayHandler display;
    private GameHandler game;
    private StatusHandler status;
    private ConnectionHandler connection;

    public Server(DisplayHandler display, UserReader reader, UserWriter writer,
                  GameHandler game, StatusHandler status, ConnectionHandler connection) {
        this.reader = reader;
        this.writer = writer;
        this.display = display;
        this.game = game;
        this.status = status;
        this.connection = connection;
    }

    public void initialize() {
        System.out.println(Constants.SERVER_WELCOME_MESSAGE);
        var port = reader.getPort();

        var localHost = this.connection.detectAddress();

        try {
            this.server = new ServerSocket(port, 1, localHost);
        } catch (IOException e) {
            this.writer.showMessage(Constants.UNABLE_TO_INITIALIZE_SERVER);
            System.exit(0);
        }

        this.writer.showMessage(Constants.WAITING_MESSAGE);
        this.writer.showMessage(Constants.REACHED_ON + localHost.toString());

        this.connection.establish(this.server);

        this.getClientHandler().start();

        this.connection.send(new Packet(this.display.show(), true));
        ConsoleCleaner.clear();

        this.writer.showField(this.display.show());
    }

    public void run() {
        while (true) {
            var command = this.reader.getValidCommand();

            if (command.equals(Constants.QUIT_COMMAND)) {
                this.connection.send(new Packet(Constants.GAME_OVER, false));
                break;
            }

            ConsoleCleaner.clear();

            var result = this.game.play(command, true);

            this.connection.send(new Packet(result, true));

            this.writer.showField(result);

            if (this.status.isWon()) {
                this.connection.send(new Packet(Constants.WON_STATUS, false));
                this.writer.showMessage(Constants.WON_STATUS);

                break;
            }
        }

        this.reader.closeConnection();
        synchronized (ConnectionHandler.class) {
            if (!this.server.isClosed()) {
                this.connection.close(this.server);
            }
        }
        System.exit(0);
    }

    private Thread getClientHandler() {
        return new Thread(() -> {
            Packet clientRequest;
            while (true) {
                //TODO remove the dependency to InputStream
                clientRequest = this.connection.getInputStream().read();

                if (clientRequest == null || clientRequest.getOutput().equals(Constants.QUIT_COMMAND)) {
                    this.connection.send(new Packet(Constants.GAME_OVER, false));
                    this.writer.showMessage(Constants.GAME_OVER);

                    break;
                }

                var result = this.game.play(clientRequest.getOutput(), false);

                this.connection.send(new Packet(result, true));

                this.writer.showField(result);

                if (this.status.isWon()) {
                    this.connection.send(new Packet(Constants.WON_STATUS, false));
                    this.writer.showMessage(Constants.WON_STATUS);

                    break;
                }
            }

            synchronized (ConnectionHandler.class) {
                if (!this.server.isClosed()) {
                    this.connection.close(this.server);
                }
            }
            System.exit(0);
        });
    }

}
