package ru.r5am.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import oracle.help.library.helpset.HelpSetParseException;
import ru.r5am.Tomato;
import ru.r5am.classes.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.Thread.sleep;

public class MainController {

    @FXML
    private void initialize() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IOException,
            IllegalAccessException, InterruptedException {

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

        TimeZone mskTz = TimeZone.getTimeZone("Europe/Moscow");
        TimeZone.setDefault(mskTz);  // Московская зона всем по дефолту

    }


    /**
     * Определить рабочий ли день в настоящий момент.
     * @return true - если день рабочий
     */
    private boolean isWorkingDay(){
        SimpleDateFormat dataTime = new SimpleDateFormat("E");
        String weekDay = dataTime.format(new Date());
        List<String> workDays = Arrays.asList("Пн", "Вт", "Ср", "Чт", "Пт", "Mon", "Tue", "Wed", "Thu", "Fri");
        boolean isWorkDay = workDays.contains(weekDay);
        if (!isWorkDay)
            Tomato.rootLogger.info("Not worked day: " + weekDay);
        return isWorkDay;
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
    private Date getBeginTime(String beginTime) throws InterruptedException {

        String beginHour = beginTime.split(":")[0];
        String beginMinute = beginTime.split(":")[1];

        Calendar calendarBegin = Calendar.getInstance();
        calendarBegin.setLenient(false);
        calendarBegin.set(Calendar.HOUR_OF_DAY, Integer.parseInt(beginHour));
        calendarBegin.set(Calendar.MINUTE, Integer.parseInt(beginMinute));
        calendarBegin.set(Calendar.SECOND, 0);
        calendarBegin.set(Calendar.MILLISECOND, 0);

        return calendarBegin.getTime();

//        TimeZone mskTz = TimeZone.getTimeZone("Europe/Moscow");
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

//        Calendar calendar = Calendar.getInstance(timeZone);
//        calendar.setLenient(false);     // Убрать "мягкий режим"

//        dateFormat.setCalendar(calendar);

//        calendar.setTimeZone(mskTz);

//        Tomato.rootLogger.info(String.format("Current time - Текущее время: %s", dateFormat.format(calendar.getTime())));
//        Tomato.rootLogger.info("Day of weak: " + calendar.get(Calendar.DAY_OF_WEEK));
//        Tomato.rootLogger.info("Time zone: " + calendar.getTimeZone().getDisplayName());



//        // Если сегодня время начала уже прошло, до добавляем 1 день - на завра
//        if (calendar.getTime().after(calendarBegin.getTime()))
//            calendarBegin.add(Calendar.DAY_OF_YEAR, 1);
//
//        // Разница между начальным и текущим временем
//        long diffMilliseconds = calendarBegin.getTimeInMillis() - calendar.getTimeInMillis();
//        Tomato.rootLogger.info("Millisecond before 'Start': " + diffMilliseconds);
//
//        // Спать до начального времени (может чуть раньше и потом ловить кажду секунду?)
////        Thread.sleep(diffMilliseconds);
//        Tomato.rootLogger.info("Time after sleeping: " + dateFormat.format(Calendar.getInstance(mskTz).getTime()));
    }


    // Привязка переменных к компонентам в main.fxml
    @FXML private Button buttonStartStop;
    @FXML private Button buttonHelp;
    @FXML private Button buttonSettings;
    @FXML private Button buttonExit;

    /**
     *  Обработка нажатий мышкой на Buttons (клавиатура отдельно обрабатывается!)
     */
    public void buttonProcessing(ActionEvent actionEvent) throws InvocationTargetException, NoSuchMethodException, InstantiationException,
            IOException, IllegalAccessException, HelpSetParseException, InterruptedException {

        Object source = actionEvent.getSource();

        // Если источник события не кнопка, то ничего не делать и выйти
        if (!(source instanceof Button)) return;

        // Нисходящее приведение
        Button clickedButton = (Button) source;

        switch (clickedButton.getId()) {

            case "buttonStartStop":
                if (Objects.equals(clickedButton.getText(), "Start"))
                    startingCycle(actionEvent, clickedButton);
                else
                    stoppingCycle(actionEvent, clickedButton);
                break;

            case "buttonHelp":
                Tomato.rootLogger.info("Pushed 'Help'");
//              // Запускаем OHJ
//              try {
//                  MyHelp.showHelp();
//              } catch (Exception e) {
//                  e.printStackTrace();
//              }
                break;
//
            case "buttonSettings":
                Tomato.rootLogger.info("Pushed 'Settings'");
//                // Редактирование настроек
//                actionSettingsEdit(actionEvent);
////                 Обновление Main формы
////                initialize();
                break;

            case "buttonExit":
//                MyHelp.disposeHelp();
                // Вызываем метод закрытия текущего окна
                actionWindowClose(actionEvent);
                break;
        }
    }

    /**
     * Остановка цикла рабочего дня
     * @param actionEvent Событие с кнопкой
     * @param stopButton Кнопка
     */
    private void stoppingCycle(ActionEvent actionEvent, Button stopButton) {
        stopButton.setStyle("-fx-text-fill: yellow;");
        stopButton.setText("Start");
        Tomato.rootLogger.debug("Pushed 'Stop'");
    }

    /**
     * Запуск цикла рабочего дня
     * @param actionEvent Событие с кнопкой
     * @param startButton Кнопка
     */
    private void startingCycle(ActionEvent actionEvent, Button startButton) throws InterruptedException {

        // изменить вид кнопки 'Start'
        startButton.setStyle("-fx-text-fill: red;");
        startButton.setText("Stop");
        Tomato.rootLogger.debug("Pushed 'Start'");

        // Определить рабочее ли время
        boolean isWorkTime = isWorkingTime();

    }

    /**
     * Определить рабочее ли время в текущий момент
     * @return true - время рабочее
     */
    private boolean isWorkingTime() throws InterruptedException {
        boolean isWorkTime = true;

        // Если рабочий день, то проверяем рабочее ли время
        if (isWorkingDay()) {
            Tomato.rootLogger.info("Today's a work day");

            if (!isCurrentTimeInWorkingDayPeriod()) {
                Tomato.rootLogger.info("Now nonworking time");
                isWorkTime = false;
            } else {
                Tomato.rootLogger.info("Now working time");
            }
        } else {
            isWorkTime = false;
        }
        return isWorkTime;
    }


    /**
     * Определить попадает ли текущее время в рабочий период дня
     * @return true - текущее время попадает в рабочий период дня
     */
    private boolean isCurrentTimeInWorkingDayPeriod() throws InterruptedException {
        boolean time = false;

        Calendar calendar = Calendar.getInstance();
        calendar.setLenient(false);     // Убрать "мягкий режим"

        // Время начала рабочего периода дня
        long beginTimeOfDayInMs = getBeginTime(ReadConfig.beginTime).getTime();

        // Текущее время
        long currentTimeInMs = calendar.getTimeInMillis();
        Tomato.rootLogger.info("Current time: " + new Date(currentTimeInMs));

        // Время конца рабочего дня в миллисекундах
        long endTimeOfDayImMs = currentTimeInMs +
                ReadConfig.untilLunchCycles * (ReadConfig.workDuration + ReadConfig.timeoutDuration) * 3600 +
                ReadConfig.lunchDuration * 3600 + ReadConfig.afterLunchCycles * (ReadConfig.workDuration + ReadConfig.timeoutDuration) * 3600;

        // Если попадает
        if ((currentTimeInMs >= beginTimeOfDayInMs) && (currentTimeInMs < endTimeOfDayImMs)) {
            time = true;
        }

        return time;
    }

    /**
     * Закрыть текущее окно
     * @param actionEvent Событие кнопки
     */
    private void actionWindowClose(ActionEvent actionEvent) {
        Tomato.rootLogger.debug("Worked method 'actionWindowClose'");
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
