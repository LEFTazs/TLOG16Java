package tlog16java;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.Scanner;
import java.util.function.IntPredicate;

public class TimeLoggerUI {
    private TimeLogger timelogger;
    
    private Scanner scanner;
    
    private int chosenMonthIndex;
    private int chosenDayIndex;
    private int chosenTaskIndex;
    
    private final int MAX_LOGGABLE_YEAR;
    
    private String menuText;
    
    public TimeLoggerUI() {
        timelogger = new TimeLogger();
        
        scanner = new Scanner(System.in);
        
        chosenMonthIndex = -1;
        chosenDayIndex = -1;
        chosenTaskIndex = -1;
        
        MAX_LOGGABLE_YEAR = 2025;
        
        menuText = 
                "\n\n0. Exit\n"
                + "1. List months\n"
                + "2. List days\n"
                + "3. List tasks\n"
                + "4. Add new month\n"
                + "5. Add day to a chosen month\n"
                + "6. Start a task for a day\n"
                + "7. Finish a specific task\n"
                + "8. Delete a task\n"
                + "9. Modify task\n"
                + "10. Statistics\n";
    }
    
    public static void main(String[] args) {
        TimeLoggerUI ui = new TimeLoggerUI();
        
        ui.mainLoop();
    }
    
    public void mainLoop() {
        boolean exit = false;
        while (!exit) {
            System.out.println(this.menuText);
            int chosenMenuItem = readIntWithCondition("Choose a menu item: ", 
                    index -> index >=0 && index <= 10);
            switch (chosenMenuItem) {
                case 0:
                    exit = true;
                    break;
                case 1:
                    listMonths();
                    break;
                case 2:
                    listDays();
                    break;
                case 3:
                    listTasks();
                    break;
                case 4:
                    addNewMonth();
                    break;
                case 5:
                    addNewDay();
                    break;
                case 6:
                    startTask();
                    break;
                case 7:
                    finishTask();
                    break;
                case 8:
                    deleteTask();
                    break;
                case 9:
                    modifyTask();
                    break;
                case 10:
                    showStatistics();
                    break;
            }
        }
    }
    
    private void listMonths() {
        timelogger.printMonths();
    }
    
    private void listAndChooseMonth() {
        listMonths();
        int numberOfMonths = timelogger.getMonths().size();
        int chosenMonth = readIndex("Write row number: ", numberOfMonths);
        
        this.chosenMonthIndex = chosenMonth;
    }
    
    private void listDays() {
        listAndChooseMonth();
        
        timelogger.getMonth(this.chosenMonthIndex)
                .printDays();
    }
    
    private void listAndChooseDay() {
        listDays();
        int numberOfDaysInChosenMonth = 
                timelogger.getMonth(this.chosenMonthIndex).getDays().size();
        int chosenDay = 
                readIndex("Write row number: ", numberOfDaysInChosenMonth);
        
        this.chosenDayIndex = chosenDay;
    }
    
    private void listTasks() {
        listAndChooseDay();
        
        timelogger.getMonth(this.chosenMonthIndex)
                .getDay(this.chosenDayIndex)
                .printTasks();
    }
    
    private void listAndChooseTask() {
        listTasks();
        int numberOfTasksInChosenDay = 
                timelogger.getMonth(this.chosenMonthIndex)
                        .getDay(this.chosenDayIndex)
                        .getTasks()
                        .size();
        int chosenTask = 
                readIndex("Write row number: ", numberOfTasksInChosenDay);
        
        this.chosenTaskIndex = chosenTask;
    }
    
    private void listUnfinishedTasks() {
        listAndChooseDay();
        
        timelogger.getMonth(this.chosenMonthIndex)
                .getDay(this.chosenDayIndex)
                .printUnfinishedTasks();
    }
    
    private void listAndChooseUnfinishedTask() {
        listUnfinishedTasks();
        WorkDay chosenDay = timelogger.getMonth(this.chosenMonthIndex)
                        .getDay(this.chosenDayIndex);
        int numberOfUnfinishedTasksInChosenDay = 
                chosenDay.getUnfinishedTasks().size();
        int chosenTaskIndexInUnfinished = 
                readIndex("Write row number: ", numberOfUnfinishedTasksInChosenDay);
        
        int chosenTaskIndex = 0;
        int i = 0;
        while (i != chosenTaskIndexInUnfinished ||
                i >= numberOfUnfinishedTasksInChosenDay ||
                chosenTaskIndex >= chosenDay.getTasks().size()) {
            if (!chosenDay.getTask(chosenTaskIndex).isEndTimeSet())
                i++;
            chosenTaskIndex++;
        }
        
        this.chosenTaskIndex = chosenTaskIndex;
    }
    
    private void addNewMonth() {
        int newYear = 
                readYear("Please write the year: ", this.MAX_LOGGABLE_YEAR);
        int newMonth = 
                readMonth("Please write the month: ");
        WorkMonth newWorkMonth = new WorkMonth(newYear, newMonth);
        timelogger.addMonth(newWorkMonth);
    }
    
    private void addNewDay() {
        listAndChooseMonth();
        YearMonth chosenMonthDate = 
                timelogger.getMonth(this.chosenMonthIndex)
                        .getDate();
        int newDayActualDay = readDay(
                "Please write the day: ", 
                chosenMonthDate.lengthOfMonth());
        int newDayWorkingHours = readHours(
                "Please write the working hours: ");
        
        WorkDay newDay = new WorkDay(
                newDayWorkingHours * 60,
                chosenMonthDate.getYear(), 
                chosenMonthDate.getMonthValue(), 
                newDayActualDay);
        timelogger.getMonth(this.chosenMonthIndex)
                .addWorkDay(newDay, true);
    }
    
    private void startTask() {
        listAndChooseDay();
        String taskId = 
                readString("Please write task id: ");
        String comment = 
                readString("Please write a comment: ");
        String startTime = 
                readString("Please write start time (HH:MM): ");
        
        WorkDay chosenDay = timelogger
                .getMonth(this.chosenMonthIndex)
                .getDay(this.chosenDayIndex);
        Task newTask = new Task(taskId);
        newTask.setComment(comment);
        if (chosenDay.hasTask()) {
            LocalTime latestEndTime = chosenDay.getLatestTaskEndTime();
            System.out.printf("{%s}\n", latestEndTime);
            if (startTime.isBlank()) {
                newTask.setStartTime(latestEndTime);
            }
        }
        if (!startTime.isBlank())
            newTask.setStartTime(startTime);
        //TODO: else throw exception
        
        timelogger
                .getMonth(this.chosenMonthIndex)
                .getDay(this.chosenDayIndex)
                .addTask(newTask);
    }
    
    private void finishTask() {
        listAndChooseUnfinishedTask();
        LocalTime endTime = readTime("Write the chosen task's endtime: ");
        
        timelogger
                .getMonth(this.chosenMonthIndex)
                .getDay(this.chosenDayIndex)
                .getTask(this.chosenTaskIndex)
                .setEndTime(endTime);
    }
    
    private void deleteTask() {
        listAndChooseTask();
        
        boolean confirmed = readBoolean(
                "Are you sure you want to delete the given task? (y / n) ");
        if (confirmed) { 
            timelogger
                    .getMonth(this.chosenMonthIndex)
                    .getDay(this.chosenDayIndex)
                    .deleteTask(this.chosenTaskIndex);
        }
    }
    
    private void modifyTask() {
        listAndChooseTask();
        
        Task chosenTask = timelogger.getMonth(this.chosenMonthIndex)
                .getDay(this.chosenDayIndex)
                .getTask(this.chosenTaskIndex);
        
        System.out.printf("Task ID {%s}\n", chosenTask.getTaskId());
        String newTaskId = readString("Write a new Task ID: ");
        if (newTaskId.isBlank())
            newTaskId = chosenTask.getTaskId();
        System.out.printf("Comment {%s}\n", chosenTask.getComment());
        String newComment = readString("Write a new comment: ");
        if (newComment.isBlank())
            newComment = chosenTask.getComment();
        System.out.printf("Start time {%s}\n", chosenTask.getStartTime());
        String newStartTime = readString("Write a new start time: ");
        if (newStartTime.isBlank())
            newStartTime = chosenTask.getStartTime().toString();
        System.out.printf("End time {%s}\n", chosenTask.getEndTime());
        String newEndTime = readString("Write a new end time: ");
        if (newEndTime.isBlank())
            newEndTime = chosenTask.getEndTime().toString();
        
        Task newTask = new Task(newTaskId, newComment, 
                newStartTime, newEndTime);
        timelogger.getMonth(this.chosenMonthIndex)
                .getDay(this.chosenDayIndex)
                .setTask(this.chosenTaskIndex, newTask);
    }
    
    private void showStatistics() {
        listAndChooseMonth();

        WorkMonth chosenMonth = timelogger.getMonth(this.chosenMonthIndex);
        printMonthStatistics(chosenMonth);
        chosenMonth
                .getDays()
                .stream()
                .forEachOrdered(this::printDayStatistics);
    }
    
    private void printMonthStatistics(WorkMonth workmonth) {
        System.out.print("Sum per month: ");
        System.out.println(workmonth.getSumPerMonth());
        System.out.print("Required minimum per month: ");
        System.out.println(workmonth.getRequiredMinPerMonth());
    }
    
    private void printDayStatistics(WorkDay workday) {
        System.out.print("Sum per day: ");
        System.out.println(workday.getSumPerDay());
        System.out.print("Required minimum per day: ");
        System.out.println(workday.getRequiredMinPerDay());
    }
    
    
    private int readYear(String message, int maxYearExclusive) {
        int currentYear = LocalDate.now().getYear();
        int newYear = 
                readIntWithCondition(
                        message, 
                        year -> currentYear <= year && year < maxYearExclusive);
        return newYear;
    }
    
    private int readMonth(String message) {
        int newMonth =
                readIntWithCondition(
                        message,
                        month -> 1 <= month && month <= 12);
        return newMonth;
    }
    
    private int readDay(String message, int monthLength) {
        int newDay =
                readIntWithCondition(
                        message,
                        day -> 1 <= day && day <= monthLength);
        return newDay;
    }
    
    private int readHours(String message) {
        int newHours =
                readIntWithCondition(
                        message,
                        hours -> 0 <= hours && hours < 24);
        return newHours;
    }
    
    private LocalTime readTime(String message) {
        String scan = readString(message);
        LocalTime time = LocalTime.parse(scan); //TODO: check input
        return time;
    }
    
    private int readIndex(String message, int maxValueExclusive) {
        int index = readIntWithCondition(
                message, 
                input -> 0 < input && input <= maxValueExclusive);
        int indexZeroBased = index - 1;
        return indexZeroBased;
    }
    
    private int readIntWithCondition(String message, IntPredicate condition) {
        int scanConverted;
        do {
            System.out.print(message);
            String scan = scanner.nextLine();
            scanConverted = Integer.parseInt(scan);
        } while (condition.test(scanConverted) != true);
        return scanConverted;
    }
    
    private String readString(String message) {
        System.out.print(message);
        String scan = scanner.nextLine();
        return scan;
    }
    
    private boolean readBoolean(String message) {
        do {
            System.out.print(message);
            String scan = scanner.nextLine();
            String scanFormatted = scan.trim().toLowerCase();
            if (scanFormatted.equals("y"))
                return true;
            else if (scanFormatted.equals("n"))
                return false;
        } while (true);
    }
}
