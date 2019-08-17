package timelogger;

import java.time.LocalTime;
import org.junit.Test;
//import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.*;
import timelogger.Task;
import timelogger.exceptions.*;

public class TaskTest {
    
    @Test
    public void wrongOrderException() {
        String taskId = "1111";
        String startTime = "08:45";
        String wrongEndTime = "07:30";
        assertThrows(NotExpectedTimeOrderException.class, () ->
                new Task(taskId, "", startTime, wrongEndTime));
    }
    
    @Test
    public void emptyTimeFieldException() {
        String taskId = "1111";
        String comment = "";
        assertThrows(EmptyTimeFieldException.class, () -> 
                new Task(taskId, comment, null, null));
    }
    
    @Test
    public void getMinPerTask() {
        String taskId = "1111";
        Task task = new Task(taskId, "", "07:30", "08:45");
        assertEquals(75, task.getMinPerTask());
    }
        
    @Test
    public void invalidRedmineIdException() {
        String invalidRedmineId = "41242";
        assertThrows(InvalidTaskIdException.class, () -> 
                new Task(invalidRedmineId));
    }
    
    @Test
    public void invalidLTTaskIdException() {
        String invalidRedmineId = "LT-42423";
        assertThrows(InvalidTaskIdException.class, () -> 
                new Task(invalidRedmineId));
    }

    @Test
    public void noTaskIdException() {
        assertThrows(NoTaskIdException.class, () -> 
                new Task(null));
    }
    
    @Test
    public void getCommentEmpty() {
        String taskId = "1111";
        Task task = new Task(taskId);
        assertEquals("", task.getComment());
    }
    
    @Test
    public void constructorRoundingEndTime() {
        String taskId = "1111";
        String startTime = "07:30";
        String endTime = "07:50";
        Task task = new Task(taskId, "", startTime, endTime);
        assertEquals(LocalTime.of(7, 45), task.getEndTime());
    }
    
    @Test
    public void setStartTimeWithStringRounding() {
        String taskId = "1111";
        String startTime = "07:30";
        String endTime = "07:45";
        Task task = new Task(taskId, "", startTime, endTime);
        
        task.setStartTime("07:25");
        assertEquals(LocalTime.of(7, 40), task.getEndTime());
    }
    
    @Test
    public void setStartTimeWithIntRounding() {
        String taskId = "1111";
        String startTime = "07:30";
        String endTime = "07:45";
        Task task = new Task(taskId, "", startTime, endTime);
        
        task.setStartTime(7, 25);
        assertEquals(LocalTime.of(7, 40), task.getEndTime());
    }
    
    @Test
    public void setStartTimeWithLocalTimeRounding() {
        String taskId = "1111";
        String startTime = "07:30";
        String endTime = "07:45";
        Task task = new Task(taskId, "", startTime, endTime);
        
        task.setStartTime(LocalTime.of(7, 25));
        assertEquals(LocalTime.of(7, 40), task.getEndTime());
    }
    
    @Test
    public void setEndTimeWithStringRounding() {
        String taskId = "1111";
        String startTime = "07:30";
        String endTime = "07:45";
        Task task = new Task(taskId, "", startTime, endTime);
        
        task.setEndTime("07:50");
        assertEquals(LocalTime.of(7, 45), task.getEndTime());
    }
    
    @Test
    public void setEndTimeWithIntRounding() {
        String taskId = "1111";
        String startTime = "07:30";
        String endTime = "07:45";
        Task task = new Task(taskId, "", startTime, endTime);
        
        task.setEndTime(7, 50);
        assertEquals(LocalTime.of(7, 45), task.getEndTime());
    }
    
    @Test
    public void setEndTimeWithLocalTimeRounding() {
        String taskId = "1111";
        String startTime = "07:30";
        String endTime = "07:45";
        Task task = new Task(taskId, "", startTime, endTime);
        
        task.setEndTime(LocalTime.of(7, 50));
        assertEquals(LocalTime.of(7, 45), task.getEndTime());
    }
    
    @Test
    public void taskSetterNull() {
        String validTaskId = "1111";
        Task task = new Task(validTaskId);
        assertThrows(NoTaskIdException.class, () -> 
                task.setTaskId(null));
    }
    
    @Test
    public void taskSetterInvalidTask() {
        String validTaskId = "1111";
        String invalidTaskId = "a3f3f3";
        Task task = new Task(validTaskId);
        assertThrows(InvalidTaskIdException.class, () -> 
                task.setTaskId(invalidTaskId));
    }
    
    @Test
    public void setStartTimeWrongOrderException() {
        String taskId = "1111";
        String startTime = "07:30";
        String endTime = "07:45";
        String invalidStartTime = "08:00";
        Task task = new Task(taskId, "", startTime, endTime);
        assertThrows(NotExpectedTimeOrderException.class, () -> 
                task.setStartTime(invalidStartTime));
    }
    
    @Test
    public void setEndTimeWrongOrderException() {
        String taskId = "1111";
        String startTime = "07:30";
        String endTime = "07:45";
        String invalidEndTime = "07:00";
        Task task = new Task(taskId, "", startTime, endTime);
        assertThrows(NotExpectedTimeOrderException.class, () -> 
                task.setEndTime(invalidEndTime));
    }
    
    @Test
    public void getMinPerTaskEmptyTimeFieldException() {
        String taskId = "1111";
        Task task = new Task(taskId);
        assertThrows(EmptyTimeFieldException.class, () -> 
                task.getMinPerTask());
    }
    
    @Test
    public void setStartTimeRounding() {
        String taskId = "1111";
        String startTime = "07:30";
        String endTime = "07:45";
        Task task = new Task(taskId, "", startTime, endTime);
        
        task.setStartTime("07:00");
        assertEquals(LocalTime.of(7, 0), task.getStartTime());
    }
    
    @Test
    public void setEndTimeRounding() {
        String taskId = "1111";
        String startTime = "07:30";
        String endTime = "07:45";
        Task task = new Task(taskId, "", startTime, endTime);
        
        task.setEndTime("08:00");
        assertEquals(LocalTime.of(8, 0), task.getEndTime());
    }
    
    @Test
    public void testConstructor() {
        String taskId = "1111";
        String comment = "Test comment.";
        String startTime = "07:30";
        String endTime = "07:45";
        Task task = new Task(taskId, comment, startTime, endTime);
        assertEquals(taskId, task.getTaskId());
        assertEquals(comment, task.getComment());
        assertEquals(startTime, task.getStartTime().toString());
        assertEquals(endTime, task.getEndTime().toString());
    }
}
