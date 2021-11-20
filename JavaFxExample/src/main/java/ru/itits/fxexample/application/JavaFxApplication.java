package ru.itits.fxexample.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.itits.fxexample.events.Events;
import ru.itits.fxexample.engine.World;

public class JavaFxApplication extends Application {
    private static JavaFxApplication instance;

    private World world;
    private Events events;

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

    public static JavaFxApplication getInstance() { return instance; }
}
