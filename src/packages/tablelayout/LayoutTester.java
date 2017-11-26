package tablelayout;

import java.awt.Color;
import java.awt.Point;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Test/example class for the TableLayout class
 * @author gilesholdsworth
 */
public class LayoutTester {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Tester");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		TableLayout frameLayout = new TableLayout(TableLayout.COLUMNS);
		frame.setLayout(frameLayout);

		JPanel mainPanel = new JPanel();
		TableLayout myLayout = new TableLayout(TableLayout.ROWS);
		mainPanel.setLayout(myLayout);

		// Create components which will not abide by rows/columns and will be drawn over the top (as long as they are added before any row or column components
		JLabel overlayLabel = new JLabel("OVERLAY");
		mainPanel.add(overlayLabel, new Point(200,200));

		JLabel overlayLabel2 = new JLabel("OVERLAY2");
		overlayLabel2.setOpaque(true);
		frame.add(overlayLabel2, new Point(700,500));


		// Create and add 3 rows
		JLabel panel1 = new JLabel("Test");
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		panel1.setBackground(Color.RED);
		panel1.setOpaque(true);
		panel2.setBackground(Color.GREEN);
		panel3.setBackground(Color.BLUE);
		mainPanel.add(panel1, 50.0);
		mainPanel.add(panel2, 25.0);
		mainPanel.add(panel3, 25.0);

		// Create and add 3 columns to the 3rd row
		TableLayout panel3Layout = new TableLayout(TableLayout.COLUMNS);
		panel3.setLayout(panel3Layout);

		JPanel panel4 = new JPanel();
		JPanel panel5 = new JPanel();
		//JLabel panel6 = new JLabel("<html>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br>test<br></html>");
		JLabel panel6 = new JLabel("test");
		panel4.setBackground(new Color(255,255,0));
		panel5.setBackground(new Color(255,0,255));
		panel6.setBackground(new Color(0,255,255));
		panel6.setOpaque(true);

		panel3.add(panel4, 33.4);
		panel3.add(panel5, 33.3);
		panel3.add(panel6, 33.3);



		frame.add(mainPanel, 50.0);
		frame.add(new JPanel(), 50.0);
		
		System.out.println(frame.getPreferredSize());


		//frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		frame.pack();
		frame.setVisible(true);


	}
}
