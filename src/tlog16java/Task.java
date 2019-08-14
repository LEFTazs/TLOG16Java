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
        roundEndTime();
    }
    
    public Task(String taskId, String comment,
                String startTime, String endTime) {
        this.taskId = taskId;
        this.comment = comment;
        this.startTime = LocalTime.parse(startTime);
        this.endTime = LocalTime.parse(endTime);
        roundEndTime();
    }
    
    public Task(String taskId) {
        this.taskId = taskId;
    }
    
    
    public long getMinPerTask() {
        long minPerTask = MINUTES.between(startTime, endTime);
        return minPerTask;
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
    
    private void roundEndTime() {
        if (!Util.isMultipleQuarterHour(this.startTime, this.endTime)) {
            LocalTime roundedEndTime = Util.roundToMultipleQuarterHour(
                    this.startTime, this.endTime);
            this.endTime = roundedEndTime;
        }
    }
    
    public boolean isStartTimeSet() {
        return this.startTime != null;
    }
    
    public boolean isEndTimeSet() {
        return this.endTime != null;
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
    
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
        if (isEndTimeSet())
            roundEndTime();
    }

    public void setStartTime(int hour, int min) {
        this.startTime = LocalTime.of(hour, min);
        if (isEndTimeSet())
            roundEndTime();
    }
    
    public void setStartTime(String startTime) {
        this.startTime = LocalTime.parse(startTime);
        if (isEndTimeSet())
            roundEndTime();
    }

    public LocalTime getEndTime() {
        return endTime;
    }
    
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
        roundEndTime();
    }

    public void setEndTime(int hour, int min) {
        this.startTime = LocalTime.of(hour, min);
        roundEndTime();
    }
    
    public void setEndTime(String endTime) {
        this.endTime = LocalTime.parse(endTime);
        roundEndTime();
    }

    @Override
    public String toString() {
        String stringForm = "Task ID: " + taskId;
        if (comment != null) {
            stringForm += ", Comment: " + comment;
        }
        if (startTime != null) {
            stringForm += ", Start time: " + startTime;
        }
        if (endTime != null) {
            stringForm += ", End time: " + endTime;
        }
        return stringForm;
    }

}
