package ru.itis.base;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JavaFxApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        String fxmlFile = "/fxml/Main.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent parent = fxmlLoader.load();
        primaryStage.setScene(new Scene(parent));
        primaryStage.setTitle("World of Tanks бесплатно без смс и регистрации");
        primaryStage.setResizable(true);
        primaryStage.setFullScreen(true);

        Scene scene = primaryStage.getScene();
        scene.setOnKeyPressed(KeyListener.instance.onKeyPressed);
        scene.setOnKeyReleased(KeyListener.instance.onKeyReleased);
        primaryStage.show();
        Game.instance.startTimer();
    }

    public void launchApp() {
        launch();
    }
}
