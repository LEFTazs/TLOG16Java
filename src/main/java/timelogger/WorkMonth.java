package timelogger;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import timelogger.exceptions.*;

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
        long extraMins = days.stream()
                .mapToLong(workDay -> workDay.getExtraMinPerDay())
                .sum();
        return extraMins;
    }
    
    public boolean isNewDate(WorkDay workDay) {
        boolean isNotNewDate = days.stream()
                .anyMatch(checkable -> workDay.getActualDay()
                        .equals(checkable.getActualDay())
                );
        return !isNotNewDate;
    }
    
    public boolean isSameMonth(WorkDay workDay) {
        int inputMonth = workDay.getActualDay().getMonthValue();
        int selfMonth = date.getMonthValue();
        
        return inputMonth == selfMonth;
    }
    
    public void addWorkDay(WorkDay workDay, boolean isWeekendEnabled) {
        if (!this.isSameMonth(workDay))
            throw new NotTheSameMonthException();
        
        if (!this.isNewDate(workDay))
            throw new NotNewDateException();
        
        if (!isWeekendEnabled && Util.isWeekday(workDay))
            throw new WeekendNotEnabledException();
        
        days.add(workDay);
    }
    
    public void addWorkDay(WorkDay workDay) {
        boolean isWeekendEnabled = false;
        this.addWorkDay(workDay, isWeekendEnabled);
    }
    
    public WorkDay getDay(int index) {
        return days.get(index);
    }

    public List<WorkDay> getDays() {
        return new ArrayList<>(days);
    }

    public YearMonth getDate() {
        return date;
    }

    public long getSumPerMonth() {
        updateSumPerMonth();
        return sumPerMonth;
    }
    
    private void updateSumPerMonth() {
        sumPerMonth = days.stream()
                .mapToLong(day -> day.getSumPerDay())
                .sum();
    }

    public long getRequiredMinPerMonth() {
        updateRequiredMinPerMonth();
        return requiredMinPerMonth;
    }
    
    private void updateRequiredMinPerMonth() {
        requiredMinPerMonth = days.stream()
                .mapToLong(day -> day.getRequiredMinPerDay())
                .sum();
    }
    
    public void printDays() {
        for (int i = 0; i < days.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, days.get(i).getActualDay());
        }
    }
}
