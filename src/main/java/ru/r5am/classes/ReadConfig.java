package ru.r5am.classes;

import jfork.nproperty.Cfg;
import jfork.nproperty.ConfigParser;
import ru.r5am.Tomato;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@Cfg
public class ReadConfig {

    // Параметры в файле конфигурации с значениями по умолчанию
    public static String beginTime = "10:00";       // Время запуска Помидора
    public static int workDuration = 45;            // Длительность работы
    public static int timeoutDuration = 15;         // Длительность перерыва
    public static int lunchDuration = 60;           // Длительность обеда
    public static int untilLunchCycles = 4;         // Количество периодов работа/отдых до обеда
    public static int afterLunchCycles = 4;         // Количество периодов работа/отдых после обеда

    // Конструктор
    public ReadConfig() throws NoSuchMethodException, InstantiationException, IllegalAccessException,
                        IOException, InvocationTargetException {
        ConfigParser.parse(this, Tomato.userHomePath + File.separator + Tomato.iniFileName);
    }

}
