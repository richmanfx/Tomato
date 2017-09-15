package main.java.ru.r5am;

import jfork.nproperty.Cfg;
import jfork.nproperty.ConfigParser;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@Cfg
class ReadConfig {

    // Параметры в файле конфигурации с значениями по умолчанию
    static String beginTime = "10:00";       // Время запуска Помидора
    static int workDuration = 45;            // Длительность работы
    static int timeoutDuration = 15;         // Длительность перерыва
    static int lunchDuration = 60;           // Длительность обеда
    static int untilLunchCycles = 4;         // Количество периодов работа/отдых до обеда
    static int afterLunchCycles = 4;         // Количество периодов работа/отдых после обеда

    // Конструктор
    ReadConfig() throws NoSuchMethodException, InstantiationException, IllegalAccessException,
                        IOException, InvocationTargetException {
        ConfigParser.parse(this, Main.userHomePath + File.separator + Main.iniFileName);
    }

}
