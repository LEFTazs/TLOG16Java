package tlog16java;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class WorkMonth {
    private List<WorkDay> days;
    private YearMonth date;
    private long sumPerMonth;
    private long requiredMinPerMonth;
    
    public WorkMonth(int year, int month) {
        this.date = YearMonth.of(year, month);
        this.days = new ArrayList<>();
    }
    
    public long getExtraMinPerMonth() {
        int extraMins = 0;
        for (WorkDay workDay : days) {
            extraMins += workDay.getExtraMinPerDay();
        }
        return extraMins;
    }
    
    public boolean isNewDate(WorkDay workDay) {
        for (WorkDay checkable : days) {
            if (workDay.getActualDay().
                    equals(checkable.getActualDay())) {
                return false;
            }
        }
        return true;
    }
    
    public boolean isSameMonth(WorkDay workDay) {
        int inputMonth = workDay.getActualDay().getMonthValue();
        int selfMonth = date.getMonthValue();
        
        return inputMonth == selfMonth;
    }
    
    public void addWorkDay(WorkDay workDay, boolean isWeekendEnabled) {
        if (this.isNewDate(workDay) && this.isSameMonth(workDay)) {
            if (isWeekendEnabled || !Util.isWeekday(workDay)) {
                days.add(workDay);
            }
        }
    }
    
    public void addWorkDay(WorkDay workDay) {
        boolean isWeekendEnabled = false;
        this.addWorkDay(workDay, isWeekendEnabled);
    }
    
    public WorkDay getDay(int index) {
        return days.get(index);
    }

    public YearMonth getDate() {
        return date;
    }

    public long getSumPerMonth() {
        return sumPerMonth;
    }

    public long getRequiredMinPerMonth() {
        return requiredMinPerMonth;
    }
}
