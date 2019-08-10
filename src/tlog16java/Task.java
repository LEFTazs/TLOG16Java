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
    
    public Task(String taskId) {
        this.taskId = taskId;
    }
    
    
    public long getMinPerTask() {
        long minPerTask = MINUTES.between(startTime, endTime);
        return minPerTask;
    }
    
    public boolean isMultipleQuarterHour() {
        long timeInterval = this.getMinPerTask();
        boolean isMultiple = timeInterval % 15 == 0;
        return isMultiple;
    }

    
    public boolean isValidTaskId() {
        return isValidRedmineTaskId() || isValidLTTaskId();
    }
    
    private boolean isValidRedmineTaskId() {
        String regex = "\\d{4}";
        boolean isValid = taskId.matches(regex);
        return isValid;
    }
    
    private boolean isValidLTTaskId() {
        String regex = "LT-\\d{4}";
        boolean isValid = taskId.matches(regex);
        return isValid;
    }
    
    
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(int hour, int min) {
        this.startTime = LocalTime.of(hour, min);
    }
    
    public void setStartTime(String startTime) {
        this.startTime = LocalTime.parse(startTime);
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(int hour, int min) {
        this.startTime = LocalTime.of(hour, min);
    }
    
    public void setEndTime(String endTime) {
        this.endTime = LocalTime.parse(endTime);
    }

}
