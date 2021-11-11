package ru.itits.fxexample.application;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class JavaFxApplication extends Application {

    public EventHandler<KeyEvent> onKeyPressed = event -> {
        if(event.getCode() == KeyCode.ESCAPE) {
            System.exit(0);
        }
    };

    @Override
    public void start(Stage stage) throws Exception {
        String fxmlFile = "/fxml/Main.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);

        scene.setOnKeyPressed(onKeyPressed);

        stage.setScene(scene);
        stage.setTitle("World of Tanks бесплатно без смс и регистрации");
        stage.setResizable(true);
        stage.setFullScreen(false);

        stage.show();
    }

    public void launchApp(String[] args) {
        launch(args);
    }
}
