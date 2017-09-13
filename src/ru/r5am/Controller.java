package ru.r5am;

import javafx.fxml.FXML;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Controller {

    @FXML
    private void initialize() throws InvocationTargetException, NoSuchMethodException,
            InstantiationException, IOException, IllegalAccessException {

        // Считываем параметры из INI-файла
        readConfig();


    }

    /**
     * Считать параметры из INI-файла
     */
    private void readConfig() throws InvocationTargetException, NoSuchMethodException,
            InstantiationException, IOException, IllegalAccessException {

        // Полный путь к конфиг-файлу
        File fullIniFilePath = new File(Main.userHomePath + File.separator + Main.iniFileName);

        // Если файл существует, то считать настройки. Если нет файла, то значения "по умолчанию"
        if (fullIniFilePath.exists() && fullIniFilePath.isFile()) {
            new ReadConfig();
        } else {
            ReadConfig.beginTime = "10:00";          // Время запуска Помидора
            ReadConfig.workDuration = 45;            // Длительность работы
            ReadConfig.timeoutDuration = 15;         // Длительность перерыва
            ReadConfig.lunchDuration = 60;           // Длительность обеда
            ReadConfig.untilLunchCycles = 4;         // Количество периодов работа/отдых до обеда
            ReadConfig.afterLunchCycles = 4;         // Количество периодов работа/отдых после обеда

        }
//        System.out.printf("%s", ReadConfig.beginTime);

        // Во время преобразовать
        SimpleDateFormat beginDataTime = new SimpleDateFormat("yyyy.MM.dd EEEEEEEEEEE HH:mm");
        System.out.println("Current Date: " + beginDataTime.format(new Date()));

        if (isWorkingDay())
            System.out.println("Сегодня рабочий день");

    }


    /**
     * Определить рабочий ли день в настоящий момент.
     * @return true - если день рабочий
     */
    private boolean isWorkingDay(){
        SimpleDateFormat dataTime = new SimpleDateFormat("E");
        String weekDay = dataTime.format(new Date());
        List<String> workDays = Arrays.asList("Пн", "Вт", "Ср", "Чт", "Пт");
        return workDays.contains(weekDay);
    }

}
