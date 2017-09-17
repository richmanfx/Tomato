package ru.r5am.controllers;

import javafx.fxml.FXML;
import ru.r5am.Tomato;
import ru.r5am.classes.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.Thread.sleep;

public class MainController {

    @FXML
    private void initialize() throws InvocationTargetException, NoSuchMethodException,
            InstantiationException, IOException, IllegalAccessException, InterruptedException {

        // Считываем параметры из INI-файла
        readConfig();


    }

    /**
     * Считать параметры из INI-файла
     */
    private void readConfig() throws InvocationTargetException, NoSuchMethodException,
            InstantiationException, IOException, IllegalAccessException, InterruptedException {

        // Полный путь к конфиг-файлу
        File fullIniFilePath = new File(Tomato.userHomePath + File.separator + Tomato.iniFileName);

        // Если файл существует, то считать настройки. Если нет файла, то значения "по умолчанию"
        if (fullIniFilePath.exists() && fullIniFilePath.isFile())
            new ReadConfig();

        // Для отладки
        Tomato.rootLogger.debug(String.format("beginTime = %s", ReadConfig.beginTime));
        Tomato.rootLogger.debug(String.format("workDuration = %s", ReadConfig.workDuration));
        Tomato.rootLogger.debug(String.format("timeoutDuration = %s", ReadConfig.timeoutDuration));
        Tomato.rootLogger.debug(String.format("lunchDuration = %s", ReadConfig.lunchDuration));
        Tomato.rootLogger.debug(String.format("untilLunchCycles = %s", ReadConfig.untilLunchCycles));
        Tomato.rootLogger.debug(String.format("afterLunchCycles = %s", ReadConfig.afterLunchCycles));



        if (isWorkingDay())
            System.out.println("Сегодня рабочий день");
//
//        workingDayWaiting(isWorkingDay());

        getBeginTime(ReadConfig.beginTime);

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


    /**
     * Ожидание наступления рабочего дня недели
     */
    private void workingDayWaiting(boolean workDay) throws InterruptedException {

//        while (!workDay) {
        while (workDay) {
            workDay = isWorkingDay();
            Tomato.rootLogger.info(String.format("День недели рабочий? - %s", workDay));
            int period = 5; // минуты
            sleep(period * 60 * 1000);  // Через период снова проверить
        }
    }

    /**
     * Определить оставшееся время до времени запуска
     */
    private void getRemainingTimeBeforeBegin() {

    }

    /**
     * Преобразовать во время данные из конфига
     */
    private void getBeginTime(String beginTime) throws InterruptedException {

        String beginHour = beginTime.split(":")[0];
        String beginMinute = beginTime.split(":")[1];

        TimeZone mskTz = TimeZone.getTimeZone("Europe/Moscow");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Calendar calendar = Calendar.getInstance(mskTz);
        calendar.setLenient(false);     // Убрать "мягкий режим"

        dateFormat.setCalendar(calendar);

        calendar.setTimeZone(mskTz);

        Tomato.rootLogger.info("Текущее время: " + dateFormat.format(calendar.getTime()));
        Tomato.rootLogger.info("День недели: " + calendar.get(Calendar.DAY_OF_WEEK));
        Tomato.rootLogger.info("Временная зона: " + calendar.getTimeZone().getDisplayName());

        Calendar calendarBegin = Calendar.getInstance(mskTz);
        calendarBegin.setLenient(false);
        calendarBegin.set(Calendar.HOUR_OF_DAY, Integer.parseInt(beginHour));
        calendarBegin.set(Calendar.MINUTE, Integer.parseInt(beginMinute));
        calendarBegin.set(Calendar.SECOND, 0);
        calendarBegin.set(Calendar.MILLISECOND, 0);

        // Если сегодня время начала уже прошло, до добавляем 1 день - на завра
        if (calendar.getTime().after(calendarBegin.getTime()))
            calendarBegin.add(Calendar.DAY_OF_YEAR, 1);

        // Разница между начальным и текущим временем
        long diffMilliseconds = calendarBegin.getTimeInMillis() - calendar.getTimeInMillis();
        System.out.println("Миллисекунд до Начального времени: " + diffMilliseconds);

        // Спать до начального времени (может чуть раньше и потом ловить кажду секунду?)
//        Thread.sleep(diffMilliseconds);
        System.out.println("Время после просыпания: " + dateFormat.format(Calendar.getInstance(mskTz).getTime()));


    }
}
