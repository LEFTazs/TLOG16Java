package timelogger;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import timelogger.exceptions.*;

@lombok.Getter
public class WorkDay {
    private List<Task> tasks;
    private long requiredMinPerDay;
    private LocalDate actualDay;
    private long sumPerDay;
    
    public WorkDay(long requiredMinPerDay, LocalDate actualDay) {
        if (requiredMinPerDay < 0)
            throw new NegativeMinutesOfWorkException();
        
        this.requiredMinPerDay = requiredMinPerDay;
        this.actualDay = actualDay;
        this.tasks = new ArrayList<>();
        
        if (this.actualDay.isAfter(LocalDate.now()))
            throw new FutureWorkException();
    }
    
    public WorkDay(long requiredMinPerDay,
                    int actualYear, int actualMonth, int actualDay) {
        this(requiredMinPerDay, 
                LocalDate.of(actualYear, actualMonth, actualDay));
    }
    
    public WorkDay(long requiredMinPerDay) {
        this(requiredMinPerDay, LocalDate.now());
    }
    
    public WorkDay(int actualYear, int actualMonth, int actualDay) {
        this(450, actualYear, actualMonth, actualDay);
    }
    
    public WorkDay() {
        this(450, LocalDate.now());
    }
    

    private boolean isFutureDate(LocalDate date) {
        return date.isAfter(LocalDate.now());
    }
    
    public long getExtraMinPerDay() {
        updateSumPerDay();
        return sumPerDay - requiredMinPerDay;
    }
        
    public void addTask(Task t) {
        if (Util.isSeperatedTime(tasks, t)) {
            tasks.add(t);
        } else {
            throw new NotSeparatedTimesException();
        }
    }
    
    public LocalTime getLatestTaskEndTime() {
        Task latestTask = tasks.stream()
                .filter(task -> task.isEndTimeSet())
                .max(Comparator.comparing(
                        task -> localTimeToLong(task.getEndTime()))
                )
                .orElse(null);
        if (latestTask == null) {
            return null;
        } else {
            return latestTask.getEndTime();
        }
    }
    
    private long localTimeToLong(LocalTime localTime) {
        int hours = localTime.getHour();
        int mins = localTime.getMinute();
        int seconds = localTime.getSecond();
        
        return hours * 60 * 60 + mins * 60 + seconds;
    } 
    
    public boolean hasTask() {
        return !tasks.isEmpty();
    }
    
    
    public Task getTask(int index) {
        return tasks.get(index);
    }
    
    public Task setTask(int index, Task newTask) {
        Task modifiedTask = tasks.set(index, newTask);
        return modifiedTask;
    }
    
    public void deleteTask(int index) {
        tasks.remove(index);
    }
    
    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }
    
    public Task getUnfinishedTask(int index) {
        return getUnfinishedTasks().get(index);
    }
    
    public List<Task> getUnfinishedTasks() {
        List<Task> unfinishedTasks = tasks.stream()
                .filter(task -> !task.isEndTimeSet())
                .collect(Collectors.toList()
                );
        return unfinishedTasks;
    }
    
    public void setRequiredMinPerDay(long requiredMinPerDay) {
        if (requiredMinPerDay < 0)
            throw new NegativeMinutesOfWorkException();
        
        this.requiredMinPerDay = requiredMinPerDay;
    }
    
    public void setActualDay(int year, int month, int day) {        
        this.actualDay = LocalDate.of(year, month, day);
        
        if (this.actualDay.isAfter(LocalDate.now()))
            throw new FutureWorkException();
    }

    public long getSumPerDay() {
        updateSumPerDay();
        return sumPerDay;
    }
    
    private void updateSumPerDay() {
        sumPerDay = tasks.stream()
                .mapToLong(task -> task.getMinPerTask())
                .sum();
    }
    
    public void printTasks() {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, tasks.get(i));
        }
    }
    
    public void printUnfinishedTasks() {
        int j = 1;
        for (int i = 0; i < tasks.size(); i++) {
            if (!tasks.get(i).isEndTimeSet()) {
                System.out.printf("%d. %s\n", j, tasks.get(i));
                j++;
            }
        }
    }
}
