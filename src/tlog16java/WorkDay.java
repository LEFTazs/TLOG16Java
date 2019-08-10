package tlog16java;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WorkDay {
    private List<Task> tasks;
    private long requiredMinPerDay;
    private LocalDate actualDay;
    private long sumPerDay;
    
    public WorkDay(long requiredMinPerDay,
                    int actualYear, int actualMonth, int actualDay) {
        this.requiredMinPerDay = requiredMinPerDay;
        this.actualDay = LocalDate.of(actualYear, actualMonth, actualDay);
        this.tasks = new ArrayList<>();
    }
    
    public WorkDay(long requiredMinPerDay) {
        this.requiredMinPerDay = requiredMinPerDay;
        this.actualDay = LocalDate.now();
        this.tasks = new ArrayList<>();
    }
    
    public WorkDay(int actualYear, int actualMonth, int actualDay) {
        this.requiredMinPerDay = 450;
        this.actualDay = LocalDate.of(actualYear, actualMonth, actualDay);
        this.tasks = new ArrayList<>();
    }
    
    public WorkDay() {
        this.requiredMinPerDay = 450;
        this.actualDay = LocalDate.now();
        this.tasks = new ArrayList<>();
    }
    
    public long getExtraMinPerDay() {
        return sumPerDay - requiredMinPerDay;
    }
    
    public boolean isSeperatedTime(Task t) {
        for (Task checkable : tasks) {
            boolean inputEndsAfterCheckableBegins = 
                    t.getEndTime().isAfter(checkable.getStartTime());
            boolean inputStartsBeforeCheckableStarts = 
                    t.getStartTime().isBefore(checkable.getEndTime());
            if (inputEndsAfterCheckableBegins && 
                    inputStartsBeforeCheckableStarts) {
                return true;
            }
        }
        return false;
    }
    
    public void addTask(Task t) {
        if (t.isMultipleQuarterHour() &&
                this.isSeperatedTime(t)) {
            tasks.add(t);
        }
    }
    
    public boolean isWeekday() {
        DayOfWeek dayOfWeek = actualDay.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY ||
                dayOfWeek == DayOfWeek.SUNDAY;
    }

    public long getRequiredMinPerDay() {
        return requiredMinPerDay;
    }

    public LocalDate getActualDay() {
        return actualDay;
    }

    public long getSumPerDay() {
        return sumPerDay;
    }
}
