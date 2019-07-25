package core;

import IO.ConsoleReader;
import IO.ConsoleWriter;
import fields.DynamicField;
import fields.StaticField;
import handlers.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Scanner;

public class ServerLauncher extends Application {

    public static void main(String[] args) {

        launch(args);

    }

    @Override
    public void start(Stage stage) throws Exception {
        var root = new StackPane();

        var staticField = new StaticField();
        var dynamicField = new DynamicField();

        var position = new PositionHandler(staticField, dynamicField);
        var validation = new ValidationHandler(staticField, dynamicField);
        var display = new DisplayHandler(staticField, dynamicField);

        var reader = new ConsoleReader(new Scanner(System.in));
        var commandHandler = new CommandHandler(reader);
        var writer = new ConsoleWriter();

        var game = new GameHandler(dynamicField, position, validation, display, commandHandler);

        var status = new StatusHandler(staticField, dynamicField);

        var server = new Server(writer, game, status);

        var staticGrid = staticField.getDisplayField();
        staticGrid.setAlignment(Pos.CENTER);
        var dynamicGrid = dynamicField.getDisplayField();
        dynamicGrid.setAlignment(Pos.CENTER);
        root.getChildren().addAll(staticGrid, dynamicGrid);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 540, 420, Color.BLACK);

        scene.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> server.runSingle(keyEvent));

        stage.setTitle("Sokoban");
        stage.setScene(scene);
        stage.show();
    }

}
