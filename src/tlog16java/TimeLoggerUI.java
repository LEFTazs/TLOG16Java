package tlog16java;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Scanner;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

public class TimeLoggerUI {
    private TimeLogger timelogger;
    
    private Scanner scanner;
    
    public TimeLoggerUI() {
        timelogger = new TimeLogger();
        scanner = new Scanner(System.in);
    }
    
    public static void main(String[] args) {
        TimeLoggerUI ui = new TimeLoggerUI();
        
        ui.addNewMonth();
        ui.addNewMonth();
        
        ui.listMonths();
        ui.listDays();
    }
    
    private void listMonths() {
        List<WorkMonth> months = timelogger.getMonths();
        for (int i = 0; i < months.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, months.get(i).getDate());
        }
    }
    
    private int listDays() {
        listMonths();
        int numberOfMonths = timelogger.getMonths().size();
        int chosenMonth = readIndex("Write row number: ", numberOfMonths);
        // ^ new method?
        
        List<WorkDay> days = timelogger.getMonth(chosenMonth).getDays();
        for (int i = 0; i < days.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, days.get(i).getActualDay());
        }
        return chosenMonth;
    }
    
    private int listTasks() {
        int chosenMonth = listDays();
        int numberOfDaysInChosenMonth = 
                timelogger.getMonth(chosenMonth).getDays().size();
        int chosenDay = 
                readIndex("Write row number: ", numberOfDaysInChosenMonth);
        
        List<Task> tasks = 
                timelogger.getMonth(chosenMonth).getDay(chosenDay).getTasks();
        for (int i = 0; i < tasks.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, tasks.get(i));
        }
        return chosenDay;
    }
    
    private void addNewMonth() {
        int newYear = readYear("Please write the year: ", 2025);
        int newMonth = readMonth("Please write the month: ");
        WorkMonth newWorkMonth = new WorkMonth(newYear, newMonth);
        timelogger.addMonth(newWorkMonth);
    }
    
    private void addNewDay() {
        listMonths();
        int numberOfMonths = timelogger.getMonths().size();
        int chosenMonth = readIndex(
                "Write row number: ", 
                numberOfMonths);
        YearMonth chosenMonthDate = timelogger.getMonth(chosenMonth).getDate();
        int newDayActualDay = readDay(
                "Please write the day: ", 
                chosenMonthDate.lengthOfMonth());
        int newDayWorkingHours = readHours(
                "Please write the working hours: ");
        
        WorkDay newDay = new WorkDay(
                newDayWorkingHours, 
                chosenMonthDate.getYear(), 
                chosenMonthDate.getMonthValue(), 
                newDayActualDay);
        timelogger.getMonth(chosenMonth).addWorkDay(newDay);
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
                        hours -> 0 <= hours && hours <= 24);
        return newHours;
    }
    
    private int readIndex(String message, int maxValueExclusive) {
        int index = readIntWithCondition(
                message, 
                input -> 0 < input && input <= maxValueExclusive);
        int indexZeroBased = index - 1;
        return indexZeroBased;
    }
    
    private int readIntWithCondition(String message, IntPredicate condition) {
        int scan;
        do {
            System.out.print(message);
            scan = scanner.nextInt();
        } while (condition.test(scan) != true);
        return scan;
    }
}
