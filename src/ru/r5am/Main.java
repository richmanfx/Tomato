package ru.r5am;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Created by Zoer (R5AM) on 09.09.2017.
 */
public class Main extends Application {

    final static String userHomePath = System.getProperty("user.home");  // папка пользователя
    final static String iniFileName = ".tomato/tomato.ini";              // файл конфигурации
    private final static String programIcon = "tomato.png";              // Иконка приложения

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("ru.r5am.fxml"));

        primaryStage.setTitle("Помидор");   // Заголовок окна

        // Посмотрим путь, откуда мы стартуем - требуется класс /ru/r5am/ApplicationStartUpPath
//        ApplicationStartUpPath startUpPath = new ApplicationStartUpPath();
//        try {
//            System.out.println("startUpPath: " + startUpPath.getApplicationStartUp());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        // Иконка приложения
        String imageUrl = getClass().getResource(programIcon).toString();
        Image iconsImage = new Image(imageUrl);
        primaryStage.getIcons().add(iconsImage);

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
