package tlog16java;

import java.time.LocalTime;
import java.time.DayOfWeek;
import static java.time.temporal.ChronoUnit.MINUTES;
import java.util.ArrayList;
import java.util.List;

public class Util {
    public static LocalTime roundToMultipleQuarterHour(
            LocalTime startTime, LocalTime endTime) {
        long minuteDuration = MINUTES.between(startTime, endTime);
        long remainder = minuteDuration % 15;
        long roundedMinuteDuration;
        if (remainder < 15 / 2) {
            roundedMinuteDuration = minuteDuration - remainder;
        } else {
            roundedMinuteDuration = minuteDuration - remainder + 15;
        }
        return startTime.plusMinutes(roundedMinuteDuration);
    }
    
    public static boolean isSeperatedTime(
            List<Task> tasks, Task t) {
        List<Task> tasks_ = new ArrayList<>(tasks);
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
    
    public static boolean isWeekday(WorkDay workDay) {
        DayOfWeek dayOfWeek = workDay.getActualDay().getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY ||
                dayOfWeek == DayOfWeek.SUNDAY;
    }

    public static boolean isMultipleQuarterHour(
            LocalTime startTime, LocalTime endTime) {
        long timeInterval = MINUTES.between(startTime, endTime);
        boolean isMultiple = timeInterval % 15 == 0;
        return isMultiple;
    }

}
