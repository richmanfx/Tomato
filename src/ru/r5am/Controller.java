package ru.r5am;

import javafx.fxml.FXML;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.Thread.sleep;

public class Controller {

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



//        if (isWorkingDay())
//            System.out.println("Сегодня рабочий день");
//
//        workingDayWaiting(isWorkingDay());

//        getBeginTime(ReadConfig.beginTime);

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
            System.out.printf("День недели рабочий? - %s\n", workDay);
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

        String beginHour = beginTime.substring(0,2);
        String beginMinute = beginTime.substring(3,5);

        TimeZone mskTz = TimeZone.getTimeZone("Europe/Moscow");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Calendar calendar = Calendar.getInstance(mskTz);
        calendar.setLenient(false);     // Убрать "мягкий режим"

        dateFormat.setCalendar(calendar);

        calendar.setTimeZone(mskTz);

        System.out.println("Текущее время: " + dateFormat.format(calendar.getTime()));
        System.out.println("День недели: " + calendar.get(Calendar.DAY_OF_WEEK));
        System.out.println("Временная зона: " + calendar.getTimeZone().getDisplayName());

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
