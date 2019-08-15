package tlog16java;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
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
        
    public void addTask(Task t) {
        if (Util.isSeperatedTime(tasks, t)) {
            tasks.add(t);
        }
    }
    
    public LocalTime getLatestTaskEndTime() {
        Task latestTask = tasks.stream()
                .filter(task -> task.isEndTimeSet())
                .max(Comparator.comparing(
                        task -> task.getEndTime().hashCode())
                )
                .orElseThrow();
        return latestTask.getEndTime();
    }
    
    public boolean hasTask() {
        return !tasks.isEmpty();
    }
    
    
    public Task getTask(int index) {
        return tasks.get(index);
    }

    public List<Task> getTasks() {
        return new ArrayList<Task>(tasks);
    }
    
    public long getRequiredMinPerDay() {
        return requiredMinPerDay;
    }
    
    public void setRequiredMinPerDay(long requiredMinPerDay) {
        this.requiredMinPerDay = requiredMinPerDay;
    }

    public LocalDate getActualDay() {
        return actualDay;
    }
    
    public void setActualDay(int year, int month, int day) {
        this.actualDay = LocalDate.of(year, month, day);
    }

    public long getSumPerDay() {
        return sumPerDay;
    }
    
    public void printTasks() {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, tasks.get(i));
        }
    }
}
