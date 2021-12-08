package ru.itits.fxexample.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.itits.fxexample.engine.Level;
import ru.itits.fxexample.engine.World;
import ru.itits.fxexample.engine.network.Server;
import ru.itits.fxexample.input.Input;

public class JavaFxApplication extends Application {
    private static JavaFxApplication instance;

    private World world;
    private Input input;
    private Server server;

    @Override
    public void start(Stage stage) throws Exception {
        instance = this;

        String fxmlFile = "/fxml/Main.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent parent = fxmlLoader.load();

        Scene scene = new Scene(parent);
        input = new Input();
        world = new World(input);

        stage.setScene(scene);
        stage.setTitle("World of Tanks бесплатно без смс и регистрации");
        stage.setResizable(true);
        stage.setFullScreen(false);

        stage.show();
    }

    public void start(String[] args) {
        launch(args);
    }

    public World getWorld() {
        return world;
    }

    public Input getInput() {
        return input;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public Server getServer() {
        return server;
    }

    public void loadLevel(Level level) {
        world.setLevel(level);
        level.initialize(world);
    }

    public static JavaFxApplication getInstance() { return instance; }
}
