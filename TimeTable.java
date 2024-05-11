import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TimeTable extends JFrame implements ActionListener {

	private JPanel screen = new JPanel(), tools = new JPanel();
	private JButton tool[];
	private JTextField field[];
	private CourseArray courses;
	private Color CRScolor[] = {Color.RED, Color.GREEN, Color.BLACK};
    private int shifts;
	private int iterations;
	private int slots;
	private int lastClashCount;
	
	 public TimeTable(int slots, int shifts, int iterations) {
	        super("Dynamic Time Table");
	        setSize(500, 800);
	        setLayout(new FlowLayout());

	        this.slots = slots;
	        this.shifts = shifts;
	        this.iterations = iterations;

	        screen.setPreferredSize(new Dimension(400, 800));
	        add(screen);

	        setTools();
	        add(tools);
	        JButton continueButton = new JButton("Continue");
	        continueButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                continueScheduling();
	            }
	        });
	        add(continueButton);
	        setSize(300, 200);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setVisible(true);
	        
	        courses = new CourseArray(Integer.parseInt(field[1].getText()) + 1, this.slots);
	    }
	
	public TimeTable() {
		super("Dynamic Time Table");
		setSize(500, 800);
		setLayout(new FlowLayout());
		
		screen.setPreferredSize(new Dimension(400, 800));
		add(screen);
		
		setTools();
		add(tools);
		JButton continueButton = new JButton("Continue");
		continueButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				continueScheduling();
			}
		});
		add(continueButton);
		setSize(300, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	
	public void runScheduling() {
	
	    courses = new CourseArray(Integer.parseInt(field[1].getText()) + 1, this.slots);
	    courses.readClashes(field[2].getText());

	    int minClashes = Integer.MAX_VALUE;
	    int stepAtMinClashes = 0;
	   
	    for (int iteration = 1; iteration <= this.iterations; iteration++) {
	        courses.iterate(this.shifts); 
	        draw();

	        int currentClashes = courses.clashesLeft();
	        if (currentClashes < minClashes) {
	            minClashes = currentClashes;
	            stepAtMinClashes = iteration;
	            this.lastClashCount = currentClashes;
	        }
	    }
	    System.out.println("Completed " + this.iterations + " iterations with " +
	                       "shifts: " + this.shifts + ", slots: " + this.slots +
	                       ", minimum clashes: " + minClashes + " at iteration " + stepAtMinClashes);
	}
	public void setTools() {
		String capField[] = {"Slots:", "Courses:", "Clash File:", "Iters:", "Shift:"};
		field = new JTextField[capField.length];
		
		String capButton[] = {"Load", "Start", "Step", "Print", "Exit"};
		tool = new JButton[capButton.length];
		
		tools.setLayout(new GridLayout(2 * capField.length + capButton.length, 1));
		
		for (int i = 0; i < field.length; i++) {
			tools.add(new JLabel(capField[i]));
			field[i] = new JTextField(5);
			tools.add(field[i]);
		}
		
		for (int i = 0; i < tool.length; i++) {
			tool[i] = new JButton(capButton[i]);
			tool[i].addActionListener(this);
			tools.add(tool[i]);
		}
		
		field[0].setText(String.valueOf(slots));
        field[1].setText("381"); 
        field[2].setText("sta-f-83.stu");
        field[3].setText(String.valueOf(iterations));
        field[4].setText(String.valueOf(shifts));
	}
	
	public void draw() {
		Graphics g = screen.getGraphics();
		int width = Integer.parseInt(field[0].getText()) * 10;
		for (int courseIndex = 1; courseIndex < courses.length(); courseIndex++) {
			g.setColor(CRScolor[courses.status(courseIndex) > 0 ? 0 : 1]);
			g.drawLine(0, courseIndex, width, courseIndex);
			g.setColor(CRScolor[CRScolor.length - 1]);
			g.drawLine(10 * courses.slot(courseIndex), courseIndex, 10 * courses.slot(courseIndex) + 10, courseIndex);
		}
	}
	
	private int getButtonIndex(JButton source) {
		int result = 0;
		while (source != tool[result]) result++;
		return result;
	}
	
	 public int getLastClashCount() {
	        return this.lastClashCount;
	 }
	
	public void actionPerformed(ActionEvent click) {
		int min, step, clashes;
		
		switch (getButtonIndex((JButton) click.getSource())) {
		case 0:
			int slots = Integer.parseInt(field[0].getText());
			courses = new CourseArray(Integer.parseInt(field[1].getText()) + 1, slots);
			courses.readClashes(field[2].getText());
			draw();
			break;
		case 1:
			min = Integer.MAX_VALUE;
			step = 0;
			for (int i = 1; i < courses.length(); i++) courses.setSlot(i, 0);
			
			for (int iteration = 1; iteration <= Integer.parseInt(field[3].getText()); iteration++) {
				courses.iterate(Integer.parseInt(field[4].getText()));
				draw();
				clashes = courses.clashesLeft();
				if (clashes < min) {
					min = clashes;
					step = iteration;
				}
			}
			System.out.println("Shift = " + field[4].getText() + "\tMin clashes = " + min + "\tat step " + step);
			setVisible(true);
			break;
		case 2:
			courses.iterate(Integer.parseInt(field[4].getText()));
			draw();
			break;
		case 3:
			System.out.println("Exam\tSlot\tClashes");
			for (int i = 1; i < courses.length(); i++)
				System.out.println(i + "\t" + courses.slot(i) + "\t" + courses.status(i));
			break;
		case 4:
			System.exit(0);
		}
	}

	private void continueScheduling() {
		int shiftsAllowed = Integer.parseInt(field[4].getText());
		int initialClashes = courses.clashesLeft();
		int minClashes = initialClashes;
		int step = 0;

		while (true) {
			courses.iterate(shiftsAllowed);
			draw();
			int currentClashes = courses.clashesLeft();
			if (currentClashes < minClashes) {
				minClashes = currentClashes;
				step++;
			} else {
				break;
			}
		}
		}

	public static void main(String[] args) {
		int slots = 20;
	    int shifts = 5;
	    int iterations = 50;

	    TimeTable table = new TimeTable(slots, shifts, iterations);
		table.runScheduling();
	}
}
