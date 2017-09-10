package ru.r5am;

/*
 * Created by Zoer on 09.09.2017.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    final public static String userHomePath = System.getProperty("user.home");  // папка пользователя
    final public static String iniFileName = ".tomato/tomato.ini";              // файл конфигурации

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("ru.r5am.fxml"));
        primaryStage.setTitle("Помидор");

        // Установка размеров главной формы
        primaryStage.setMinWidth(125);
        primaryStage.setMinHeight(125);
        primaryStage.setMaxWidth(800);
        primaryStage.setMaxHeight(600);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("tomato.css").toExternalForm());
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
