import java.io.*;
import java.util.*;
import java.util.Random;

public class TimeTableTestRunner {
	public static void main(String[] args) throws IOException {

		Random random = new Random();
		int randomTests = 10;
		FileWriter fw = new FileWriter("timetable_log.txt", true);
		PrintWriter log = new PrintWriter(fw);
		for (int i = 0; i < randomTests; i++) {
			int randomSlot = 5 + random.nextInt(15); // Random slots between 5 and 20
			int randomShift = 1 + random.nextInt(19); // Random shifts between 1 and 20
			int randomIteration = 10 + random.nextInt(991); // Random iterations between 10 and 1000

			TimeTable randomTable = new TimeTable(randomSlot, randomShift, randomIteration);
			randomTable.runSchedulingWithAutoassociator();
			log.println("Slots: " + randomSlot + ", Shifts: " + randomShift + ", Iterations: " + randomIteration
					+ ", Clashes: " + randomTable.getLastClashCount());
		}

	    log.close();
        /*int[] shifts = {1, 2, 3, 4, 5};
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
        
        log.close();*/
    }
}
