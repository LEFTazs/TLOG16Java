package timelogger;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import timelogger.exceptions.*;

public class TimeLoggerTest {

    @Test
    public void addMonth() {
        Task task = new Task("1234", "", "07:30", "10:30");
        WorkDay workday = new WorkDay(2016, 4, 14);
        WorkMonth workmonth = new WorkMonth(2016, 4);
        TimeLogger timelogger = new TimeLogger();
        
        workday.addTask(task);
        workmonth.addWorkDay(workday);
        timelogger.addMonth(workmonth);
        
        assertEquals(task.getMinPerTask(), 
                timelogger.getMonth(0).getSumPerMonth());
    }
    
    @Test
    public void notNewMonthException() {
        WorkMonth workmonth1 = new WorkMonth(2016, 4);
        WorkMonth workmonth2 = new WorkMonth(2016, 4);
        TimeLogger timelogger = new TimeLogger();
        
        timelogger.addMonth(workmonth1);
        assertThrows(NotNewMonthException.class, () -> 
                timelogger.addMonth(workmonth2));
    }
}
