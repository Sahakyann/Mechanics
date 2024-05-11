import java.io.*;
import java.util.*;

public class TimeTableTestRunner {
	public static void main(String[] args) throws IOException {
        int[] shifts = {1, 2, 3, 4, 5};
        int[] iterations = {10, 20, 30, 40, 50}; 
        int[] slots = {5, 10, 15, 20};
        
        FileWriter fw = new FileWriter("timetable_log.txt", true);
        PrintWriter log = new PrintWriter(fw);
        
        for (int slot : slots) {
            for (int shift : shifts) {
                for (int iteration : iterations) {
                    TimeTable table = new TimeTable(slot, shift, iteration); 
                    table.runScheduling(); 
                    log.println("Slots: " + slot + ", Shifts: " + shift + ", Iterations: " + iteration +
                                ", Clashes: " + table.getLastClashCount());
                }
            }
        }
        
        log.close();
    }
}
