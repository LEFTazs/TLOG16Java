package timelogger;

import java.time.LocalTime;
import java.time.DayOfWeek;
import static java.time.temporal.ChronoUnit.MINUTES;
import java.util.ArrayList;
import java.util.List;
import timelogger.exceptions.*;

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
        boolean isNotSeperatedTime;
        if (t.isEndTimeSet()) {
            isNotSeperatedTime = tasks_.stream()
                    .filter(task -> task.isEndTimeSet())
                    .anyMatch(checkable -> 
                            (t.getEndTime().isAfter(checkable.getStartTime()) && 
                            t.getStartTime().isBefore(checkable.getEndTime()))
                            || t.getStartTime().equals(checkable.getStartTime())
                    );
        } else {
            isNotSeperatedTime = tasks_.stream()
                    .filter(task -> task.isEndTimeSet())
                    .anyMatch(checkable -> 
                            t.getStartTime().isBefore(checkable.getEndTime())
                    );
        }
        return !isNotSeperatedTime;
    }
    
    public static boolean isWeekday(WorkDay workDay) {
        DayOfWeek dayOfWeek = workDay.getActualDay().getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY ||
                dayOfWeek == DayOfWeek.SUNDAY;
    }

    public static boolean isMultipleQuarterHour(
            LocalTime startTime, LocalTime endTime) {
        if (startTime == null || endTime == null)
            throw new EmptyTimeFieldException();
        if (startTime.isAfter(endTime))
            throw new NotExpectedTimeOrderException();
        
        long timeInterval = MINUTES.between(startTime, endTime);
        boolean isMultiple = timeInterval % 15 == 0;
        return isMultiple;
    }

}
