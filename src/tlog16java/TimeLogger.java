package tlog16java;

import java.util.ArrayList;
import java.util.List;

public class TimeLogger {
    private List<WorkMonth> months;

    public TimeLogger() {
        months = new ArrayList<>();
    }
    
    public boolean isNewMonth(WorkMonth workMonth) {
        boolean isNotNewMonth = months.stream()
                .anyMatch(checkable -> workMonth.getDate()
                        .equals(checkable.getDate())
                );
        return !isNotNewMonth;
    }
    
    public void addMonth(WorkMonth workMonth) {
        if (this.isNewMonth(workMonth)) {
            months.add(workMonth);
        }
    }
    
    public WorkMonth getMonth(int index) {
        return months.get(index);
    }

    public List<WorkMonth> getMonths() {
        return new ArrayList<>(months);
    }
    
    public void printMonths() {
        for (int i = 0; i < months.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, months.get(i).getDate());
        }
    }
}
