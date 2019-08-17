package timelogger;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import timelogger.exceptions.*;

public class WorkMonthTest {
    
    @Test
    public void getSumPerMonth() {
        Task task1 = new Task("1234", "", "07:30", "08:45");
        WorkDay workday1 = new WorkDay(420, 2016, 9, 2);
        workday1.addTask(task1);
        Task task2 = new Task("1235", "", "08:45", "09:45");
        WorkDay workday2 = new WorkDay(420, 2016, 9, 1);
        workday2.addTask(task2);
        
        WorkMonth workmonth = new WorkMonth(2016, 9);
        workmonth.addWorkDay(workday1);
        workmonth.addWorkDay(workday2);
        
        assertEquals(135, workmonth.getSumPerMonth());
    }
    
    @Test
    public void getSumPerMonthDefault() {
        WorkMonth workmonth = new WorkMonth(2016, 9);
        
        assertEquals(0, workmonth.getSumPerMonth());
    }
    
    @Test
    public void getExtraMinPerMonth() {
        Task task1 = new Task("1234", "", "07:30", "08:45");
        WorkDay workday1 = new WorkDay(420, 2016, 9, 2);
        workday1.addTask(task1);
        Task task2 = new Task("1235", "", "08:45", "09:45");
        WorkDay workday2 = new WorkDay(420, 2016, 9, 1);
        workday2.addTask(task2);
        
        WorkMonth workmonth = new WorkMonth(2016, 9);
        workmonth.addWorkDay(workday1);
        workmonth.addWorkDay(workday2);
        
        assertEquals(-705, workmonth.getExtraMinPerMonth());
    }
    
    @Test
    public void getExtraMinPerMonthDefault() {
        WorkMonth workmonth = new WorkMonth(2016, 9);
        
        assertEquals(0, workmonth.getExtraMinPerMonth());
    }
    
    @Test
    public void getRequiredMinPerMonth() {
        WorkDay workday1 = new WorkDay(420, 2016, 9, 1);
        WorkDay workday2 = new WorkDay(420, 2016, 9, 2);
        
        WorkMonth workmonth = new WorkMonth(2016, 9);
        workmonth.addWorkDay(workday1);
        workmonth.addWorkDay(workday2);
        
        assertEquals(840, workmonth.getRequiredMinPerMonth());
    }
    
    @Test
    public void getRequiredMinPerMonthDefault() {
        WorkMonth workmonth = new WorkMonth(2016, 9);
        
        assertEquals(0, workmonth.getRequiredMinPerMonth());
    }
    
    @Test
    public void getSumPerMonthDefaultDay() {
        Task task = new Task("1234", "", "07:30", "08:45");
        WorkDay workday = new WorkDay(2016, 9, 9);
        workday.addTask(task);
        
        WorkMonth workmonth = new WorkMonth(2016, 9);
        workmonth.addWorkDay(workday);
        
        assertEquals(workday.getSumPerDay(), workmonth.getSumPerMonth());
    }
    
    @Test
    public void getSumPerMonthDefaultWeekendDay() {
        Task task = new Task("1234", "", "07:30", "08:45");
        WorkDay workday = new WorkDay(2016, 8, 28);
        workday.addTask(task);
        
        WorkMonth workmonth = new WorkMonth(2016, 8);
        workmonth.addWorkDay(workday, true);
        
        assertEquals(workday.getSumPerDay(), workmonth.getSumPerMonth());
    }
    
    @Test
    public void weekendNotEnabledException() {
        Task task = new Task("1234", "", "07:30", "08:45");
        WorkDay workday = new WorkDay(2016, 8, 28);
        workday.addTask(task);
        
        WorkMonth workmonth = new WorkMonth(2016, 8);
        assertThrows(WeekendNotEnabledException.class, () -> 
                workmonth.addWorkDay(workday, false));
    }
    
    @Test
    public void notNewDateException() {
        WorkDay workday1 = new WorkDay(2016, 9, 1);
        WorkDay workday2 = new WorkDay(2016, 9, 1);
        
        WorkMonth workmonth = new WorkMonth(2016, 9);
        workmonth.addWorkDay(workday1);
        assertThrows(NotNewDateException.class, () -> 
                workmonth.addWorkDay(workday2));
    }
    
    @Test
    public void notTheSameMonthException() {
        WorkDay workday1 = new WorkDay(2016, 9, 1);
        WorkDay workday2 = new WorkDay(2016, 8, 30);
        
        WorkMonth workmonth = new WorkMonth(2016, 9);
        workmonth.addWorkDay(workday1);
        assertThrows(NotTheSameMonthException.class, () -> 
                workmonth.addWorkDay(workday2));
    }
    
    @Test
    public void getSumPerMonthEmptyTimeFieldException() {
        Task task = new Task("1234");
        WorkDay workday = new WorkDay(2016, 9, 1);
        WorkMonth workmonth = new WorkMonth(2016, 9);
        
        workday.addTask(task);
        workmonth.addWorkDay(workday);
        
        assertThrows(EmptyTimeFieldException.class, () -> 
                workmonth.getSumPerMonth());
    }
    
    @Test
    public void getExtraMinPerMonthEmptyTimeFieldException() {
        Task task = new Task("1234");
        WorkDay workday = new WorkDay(2016, 9, 1);
        WorkMonth workmonth = new WorkMonth(2016, 9);
        
        workday.addTask(task);
        workmonth.addWorkDay(workday);
        
        assertThrows(EmptyTimeFieldException.class, () -> 
                workmonth.getExtraMinPerMonth());
    }
}
