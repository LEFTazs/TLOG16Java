package timelogger;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import timelogger.exceptions.*;

public class UtilTest {
    
    @Test
    public void roundToMultipleQuarterHour() {
        LocalTime startTime = LocalTime.of(7, 30);
        LocalTime endTime = LocalTime.of(7, 50);
        
        assertEquals(LocalTime.of(7, 45), 
                Util.roundToMultipleQuarterHour(startTime, endTime));
    }
    
    @Test
    public void isMultipleQuarterHourFalse() {
        LocalTime startTime = LocalTime.of(7, 30);
        LocalTime endTime = LocalTime.of(7, 50);
        
        assertEquals(false, 
                Util.isMultipleQuarterHour(startTime, endTime));
    }
    
    @Test
    public void isMultipleQuarterHourTrue() {
        LocalTime startTime = LocalTime.of(7, 30);
        LocalTime endTime = LocalTime.of(7, 45);
        
        assertEquals(true, 
                Util.isMultipleQuarterHour(startTime, endTime));
    }
    
    @Test
    public void emptyTimeFieldException() {
        LocalTime startTime = null;
        LocalTime endTime = LocalTime.of(7, 45);
        
        assertThrows(EmptyTimeFieldException.class, () -> 
                Util.isMultipleQuarterHour(startTime, endTime));
    }
    
    @Test
    public void notExpectedTimeOrderException() {
        LocalTime startTime = LocalTime.of(8, 30);
        LocalTime endTime = LocalTime.of(7, 45);
        
        assertThrows(NotExpectedTimeOrderException.class, () -> 
                Util.isMultipleQuarterHour(startTime, endTime));
    }
    
    @Test
    public void isSeperatedTime() {
        List<Task> tasksToCheck = new ArrayList<>();
        
        tasksToCheck.add(simpleTaskCreator(6, 30, 6, 45));
        tasksToCheck.add(simpleTaskCreator(5, 30, 6, 30));
        
        tasksToCheck.add(simpleTaskCreator(6, 30, 6, 45));
        tasksToCheck.add(simpleTaskCreator(6, 45, 7, 0));
        
        tasksToCheck.add(simpleTaskCreator(6, 30, 6, 30));
        tasksToCheck.add(simpleTaskCreator(5, 30, 6, 30));
        
        tasksToCheck.add(simpleTaskCreator(6, 30, 7, 30));
        tasksToCheck.add(simpleTaskCreator(7, 30, 7, 30));
        
        tasksToCheck.add(simpleTaskCreator(6, 30, 7, 0));
        tasksToCheck.add(simpleTaskCreator(6, 0, 6, 45));
        
        tasksToCheck.add(simpleTaskCreator(6, 30, 7, 0));
        tasksToCheck.add(simpleTaskCreator(6, 30, 6, 45));
        
        tasksToCheck.add(simpleTaskCreator(6, 30, 7, 0));
        tasksToCheck.add(simpleTaskCreator(6, 45, 7, 15));
        
        tasksToCheck.add(simpleTaskCreator(6, 30, 7, 0));
        tasksToCheck.add(simpleTaskCreator(6, 45, 7, 0));
        
        tasksToCheck.add(simpleTaskCreator(6, 30, 6, 30));
        tasksToCheck.add(simpleTaskCreator(6, 30, 7, 0));
        
        tasksToCheck.add(simpleTaskCreator(6, 30, 7, 30));
        tasksToCheck.add(simpleTaskCreator(6, 30, 6, 30));
        
        assertEquals("TTTTFFFFFF", isSeperatedTimeCoder(tasksToCheck));
    }
    
    private Task simpleTaskCreator(int startHour, int startMin,
            int endHour, int endMin) {
        return new Task("1234", "", startHour, startMin, endHour, endMin);
    }
    
    private String isSeperatedTimeCoder(List<Task> tasksToCheck) {
        String codedResults = "";
        for (int i = 0; i < tasksToCheck.size() - 1; i += 2) {
            Task taskIn = tasksToCheck.get(i);
            Task taskNew = tasksToCheck.get(i + 1);
            if (isSeperatedTimeWrapper(taskIn, taskNew))
                codedResults += "T";
            else
                codedResults += "F";
        }
        return codedResults;
    }
    
    private boolean isSeperatedTimeWrapper(Task taskIn, Task taskNew) {
        List<Task> tasks = new ArrayList<>();
        tasks.add(taskIn);
        boolean isSeperated = Util.isSeperatedTime(tasks, taskNew);
        return isSeperated;
    }
}
