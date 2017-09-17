package ru.r5am;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ru.r5am.classes.*;

/**
 * Created by Zoer (R5AM) on 09.09.2017.
 */
public class Tomato extends Application {

    public final static String userHomePath = System.getProperty("user.home");  // папка пользователя
    public final static String iniFileName = ".tomato/tomato.ini";              // файл конфигурации
    private final static String programIcon = "/images/tomato.png";             // Иконка приложения

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/ru.r5am.fxml"));

        primaryStage.setTitle("Помидор");   // Заголовок окна

        // Посмотрим путь, откуда мы стартуем - требуется класс /ru/r5am/ApplicationStartUpPath
        ApplicationStartUpPath startUpPath = new ApplicationStartUpPath();
        try {
            System.out.println("startUpPath: " + startUpPath.getApplicationStartUp());
        } catch (Exception e) {
            e.printStackTrace();
        }


        // Иконка приложения
        String imageUrl = getClass().getResource(programIcon).toString();
        Image iconsImage = new Image(imageUrl);
        primaryStage.getIcons().add(iconsImage);

        // Установка размеров главной формы
        primaryStage.setMinWidth(640);
        primaryStage.setMinHeight(480);
        primaryStage.setMaxWidth(640);
        primaryStage.setMaxHeight(480);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("/css/tomato.css").toExternalForm());
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
