public class Autoassociator {
	 private int[][] weights;
	    private int trainingCapacity;
	    
	    public Autoassociator(CourseArray courses) {
	        int numOfCourses = courses.length() - 1;
	        weights = new int[numOfCourses][numOfCourses];
	        for (int i = 0; i < numOfCourses; i++) {
	            Arrays.fill(weights[i], 0);
	        }
	        this.trainingCapacity = numOfCourses;
	    }
	    
	    public int getTrainingCapacity() {
	        return trainingCapacity;
	    }
	    
	    public void training(int[] pattern) {
	        for (int i = 0; i < weights.length; i++) {
	            for (int j = 0; j < weights[i].length; j++) {
	                if (i != j) {
	                    weights[i][j] += 2 * (pattern[i] * pattern[j]) - 1;
	                }
	            }
	        }
	    }
	    
	    public int unitUpdate(int[] neurons) {
	        int neuronIndex = (int) (Math.random() * neurons.length);
	        int sum = 0;
	        for (int i = 0; i < weights.length; i++) {
	            sum += weights[neuronIndex][i] * neurons[i];
	        }
	        neurons[neuronIndex] = sum > 0 ? 1 : -1;
	        return neuronIndex;
	    }
	    
	    public void unitUpdate(int[] neurons, int index) {
	        int sum = 0;
	        for (int i = 0; i < weights.length; i++) {
	            sum += weights[index][i] * neurons[i];
	        }
	        neurons[index] = sum > 0 ? 1 : -1;
	    }
	    
	    public void chainUpdate(int[] neurons, int steps) {
	        for (int i = 0; i < steps; i++) {
	            unitUpdate(neurons);
	        }
	    }
	    
	    public void fullUpdate(int[] neurons) {
	        boolean changed;
	        do {
	            changed = false;
	            int[] oldState = Arrays.copyOf(neurons, neurons.length);
	            for (int i = 0; i < neurons.length; i++) {
	                unitUpdate(neurons, i);
	            }
	            if (!Arrays.equals(oldState, neurons)) {
	                changed = true;
	            }
	        } while (changed);
	    }
}
