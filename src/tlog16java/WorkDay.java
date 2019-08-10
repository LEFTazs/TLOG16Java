package tlog16java;

import java.time.LocalDate;
import java.util.List;

public class WorkDay {
    private List<Task> tasks;
    private long requiredMinPerDay;
    private LocalDate actualDay;
    private long sumPerDay;
}
