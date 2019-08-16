package timelogger;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import timelogger.exceptions.*;

public class WorkDayTest {
    
    @Test
    public void getExtraMinPerDayDefault() {
        Task task = new Task("1234", "", "07:30", "08:45");
        WorkDay workday = new WorkDay();
        workday.addTask(task);
        assertEquals(-375, workday.getExtraMinPerDay());
    }
    
    @Test
    public void getExtraMinPerDayEmpty() {
        WorkDay workday = new WorkDay();
        assertEquals(-workday.getRequiredMinPerDay(), 
                workday.getExtraMinPerDay());
    }
    
    @Test
    public void negativeMinutesOfWorkException() {
        WorkDay workday = new WorkDay();
        assertThrows(NegativeMinutesOfWorkException.class, () -> 
                workday.setRequiredMinPerDay(-50));
    }
    
    @Test
    public void setActualDayFutureWorkException() {
        WorkDay workday = new WorkDay();
        int currentYear = LocalDate.now().getYear();
        assertThrows(FutureWorkException.class, () -> 
                workday.setActualDay(currentYear + 1, 0, 0));
    }
    
    @Test
    public void constructorFutureWorkException() {
        int currentYear = LocalDate.now().getYear();
        assertThrows(FutureWorkException.class, () -> 
                new WorkDay(currentYear, 0, 0));
    }
    
    @Test
    public void getSumPerDay() {
        Task task1 = new Task("1234", "", "07:30", "08:45");
        Task task2 = new Task("1235", "", "08:45", "09:45");
        WorkDay workday = new WorkDay();
        workday.addTask(task1);
        workday.addTask(task2);
        assertEquals(135, workday.getSumPerDay());
    }
    
    @Test
    public void getSumPerDayDefault() {
        WorkDay workday = new WorkDay();
        assertEquals(0, workday.getSumPerDay());
    }
    
    @Test
    public void getLatestTaskEndTime() {
        Task task1 = new Task("1234", "", "07:30", "08:45");
        Task task2 = new Task("1235", "", "09:30", "11:45");
        WorkDay workday = new WorkDay();
        workday.addTask(task1);
        workday.addTask(task2);
        assertEquals(LocalTime.of(11,45), workday.getLatestTaskEndTime());
    }
    
    @Test
    public void getLatestTaskEndTimeEmpty() {
        WorkDay workday = new WorkDay();
        assertEquals(null, workday.getLatestTaskEndTime());
    }
    
    @Test
    public void notSeparatedTimesException() {
        Task task1 = new Task("1234", "", "07:30", "08:45");
        Task task2 = new Task("1235", "", "08:30", "09:45");
        WorkDay workday = new WorkDay();
        workday.addTask(task1);
        assertThrows(NotSeparatedTimesException.class, () ->
                workday.addTask(task2));
    }
    
    @Test
    public void fullConstructor() {
        long requiredMinPerDay = 400;
        LocalDate now = LocalDate.now();
        
        WorkDay workday = new WorkDay(requiredMinPerDay, 
                now.getYear(), now.getMonthValue(), now.getDayOfMonth());
        
        assertEquals(requiredMinPerDay, workday.getRequiredMinPerDay());
        assertEquals(now, workday.getActualDay());
    }
    
    @Test
    public void defaultRequiredMinutesConstructor() {
        LocalDate now = LocalDate.now();
        
        WorkDay workday = new WorkDay(
                now.getYear(), now.getMonthValue(), now.getDayOfMonth());
        
        assertEquals(450, workday.getRequiredMinPerDay());
        assertEquals(now, workday.getActualDay());
    }
    
    @Test
    public void defaultDateConstructor() {
        long requiredMinPerDay = 300;
        LocalDate now = LocalDate.now();
        
        WorkDay workday = new WorkDay(requiredMinPerDay);
        
        assertEquals(requiredMinPerDay, workday.getRequiredMinPerDay());
        assertEquals(now, workday.getActualDay());
    }
    
    @Test
    public void defaultConstructor() {
        LocalDate now = LocalDate.now();
        
        WorkDay workday = new WorkDay();
        
        assertEquals(450, workday.getRequiredMinPerDay());
        assertEquals(now, workday.getActualDay());
    }
    
    @Test
    public void setActualDay() {
        WorkDay workday = new WorkDay();
        workday.setActualDay(2016, 9, 1);
        assertEquals(LocalDate.of(2016, 9, 1), workday.getActualDay());
    }
    
    @Test
    public void setRequiredMinPerDay() {
        long requiredMinPerDay = 300;
        WorkDay workday = new WorkDay();
        workday.setRequiredMinPerDay(requiredMinPerDay);
        assertEquals(requiredMinPerDay, workday.getRequiredMinPerDay());
    }
    
    @Test
    public void emptyTimeFieldException() {
        Task task = new Task("1234");
        WorkDay workday = new WorkDay();
        workday.addTask(task);
        assertThrows(EmptyTimeFieldException.class, () -> 
                workday.getSumPerDay());
    }
    
    @Test
    public void notSeparatedTimesExceptionCausedByRounding() {
        Task task1 = new Task("1234", "", "08:45", "09:50");
        Task task2 = new Task("1235", "", "08:20", "08:45");
        WorkDay workday = new WorkDay();
        workday.addTask(task1);
        assertThrows(NotSeparatedTimesException.class, () ->
                workday.addTask(task2));
    }
}
