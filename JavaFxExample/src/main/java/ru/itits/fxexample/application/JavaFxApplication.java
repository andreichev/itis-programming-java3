package ru.itits.fxexample.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.itits.fxexample.engine.Level;
import ru.itits.fxexample.engine.server.Server;
import ru.itits.fxexample.engine.server.ServerClient;
import ru.itits.fxexample.events.Events;
import ru.itits.fxexample.engine.World;

import java.util.List;

public class JavaFxApplication extends Application {
    private static JavaFxApplication instance;

    private World world;
    private Events events;
    private Server server;

    @Override
    public void start(Stage stage) throws Exception {
        instance = this;

        String fxmlFile = "/fxml/Main.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent parent = fxmlLoader.load();

        Scene scene = new Scene(parent);
        events = new Events();
        world = new World(events);

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

    public Events getEvents() {
        return events;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public Server getServer() {
        return server;
    }

    public void loadLevel(Level level) {
        level.initialize(world);
        if (server == null) { return; }
        List<ServerClient> clientList = server.getClients();
        for(ServerClient client: clientList) {
            level.playerConnected(client.getId());
        }
    }

    public static JavaFxApplication getInstance() { return instance; }
}
