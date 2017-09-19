package ru.r5am.classes;

import java.util.Date;

/**
 * Created by Александр Ящук (R5AM, Zoer), best_worker@list.ru on 19.09.2017.
 *
 * Класс события, из которых состоит цикл дня.
 */

public class Event {

    private Date beginTime;     // время начала события
    private Date endTime;       // время окончания события
    private long duration;      // продолжительность события в минутах
    private boolean workFlag;    // признак рабочего события, иначе - событие отдыха

    // Конструктор
    public Event(Date beginTime, long duration, boolean workFlag) {
        this.beginTime = beginTime;
        this.duration = duration;
        this.workFlag = workFlag;

        this.endTime = new Date(beginTime.getTime() + duration * 60 * 1000);
    }

    // Геттеры
    public Date getBeginTime() { return beginTime; }
    public Date getEndTime() { return endTime; }
    public long getDuration() { return duration; }
    public boolean isWorkFlag() { return workFlag; }
}
