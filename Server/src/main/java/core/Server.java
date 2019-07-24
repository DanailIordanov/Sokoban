package core;

import IO.contracts.UserWriter;
import handlers.GameHandler;
import handlers.StatusHandler;
import infrastrucutre.Constants;
import javafx.scene.input.KeyEvent;

public class Server {

    private UserWriter writer;

    private GameHandler game;
    private StatusHandler status;

    public Server(UserWriter writer, GameHandler game, StatusHandler status) {
        this.writer = writer;
        this.game = game;
        this.status = status;
    }

    public void runSingle(KeyEvent keyEvent) {
        this.game.play(keyEvent, true);

        if (this.status.isWon()) {
            this.writer.showMessage(Constants.WON_STATUS);
        }
    }

}
