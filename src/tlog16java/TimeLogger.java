package tlog16java;

import java.util.ArrayList;
import java.util.List;

public class TimeLogger {
    private List<WorkMonth> months;

    public TimeLogger() {
        months = new ArrayList<>();
    }
    
    public boolean isNewMonth(WorkMonth workMonth) {
        for (WorkMonth checkable : months) {
            if (workMonth.getDate().
                    equals(checkable.getDate())) {
                return false;
            }
        }
        return true;
    }
    
    public void addMonth(WorkMonth workMonth) {
        if (this.isNewMonth(workMonth)) {
            months.add(workMonth);
        }
    }
    
    public WorkMonth getMonth(int index) {
        return months.get(index);
    }
}
