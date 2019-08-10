package tlog16java;

import java.time.LocalTime;
import static java.time.temporal.ChronoUnit.MINUTES;

public class Task {
    private String taskId;
    private String comment;
    private LocalTime startTime;
    private LocalTime endTime;
    
    public Task(String taskId, String comment, 
                int startHour, int startMin,
                int endHour, int endMin) {
        this.taskId = taskId;
        this.comment = comment;
        this.startTime = LocalTime.of(startHour, startMin);
        this.endTime = LocalTime.of(endHour, endMin);
    }
    
    public Task(String taskId, String comment,
                String startTime, String endTime) {
        this.taskId = taskId;
        this.comment = comment;
        this.startTime = LocalTime.parse(startTime);
        this.endTime = LocalTime.parse(endTime);
    }
    
    public long getMinPerTask() {
        long minPerTask = MINUTES.between(startTime, endTime);
        return minPerTask;
    }
    
    public boolean isValidTaskId() {
        String regex = "(LT-|)(\\d{4})";
        boolean isValid = taskId.matches(regex);
        return isValid;
    }
    
    public boolean isMultipleQuarterHour() {
        long timeInterval = this.getMinPerTask();
        boolean isMultiple = timeInterval % 15 == 0;
        return isMultiple;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getComment() {
        return comment;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }
}
