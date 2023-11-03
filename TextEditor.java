import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.plaf.metal.*;
import javax.swing.text.*;
import javax.swing.event.*;
import javax.swing.filechooser.*;
import java.awt.image.*;

class TextEditor extends JFrame implements ActionListener {
	
    // Text components,GUI components used in TEXTEDITOR
	JTextArea t;
    JScrollPane scrollPane;
	JButton fontColorButton;
	JComboBox<String> fontBox;
	JFrame f;
    Font currentFont; // Store the current font settings

	// Constructor
	TextEditor()
	{
		// Create a frame
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f = new JFrame("TEXTEDITOR");
        this.setLayout(new FlowLayout());
		this.setLocationRelativeTo(null);
		try {
			// Set metal look and feel and provide cross-plateform appearance
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");

			// Set current theme to ocean
			MetalLookAndFeel.setCurrentTheme(new OceanTheme());
		}
		catch (Exception e) {
		}

		// Text component
		t = new JTextArea();        
        t.setLineWrap(true);
		t.setWrapStyleWord(true);
		t.setFont(new Font("Arial",Font.PLAIN,20));

        currentFont = t.getFont(); //stores the current font settings

        scrollPane = new JScrollPane(t); 
		scrollPane.setPreferredSize(new Dimension(450,450)); //set the preffered size of scrollPane
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        fontColorButton = new JButton("Color");
		fontColorButton.addActionListener(this);//invokes an action performed

		fontBox = new JComboBox<>(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
        fontBox.addActionListener(this);//trigger an action
		fontBox.setSelectedItem("Arial");

		// Creates a menubar
		JMenuBar mb = new JMenuBar();

		// Creates a menu for menu items
		JMenu m1 = new JMenu("File");

		// Create menu items with different labels
		JMenuItem mi1 = new JMenuItem("New");
		JMenuItem mi2 = new JMenuItem("Open");
		JMenuItem mi3 = new JMenuItem("Save");
		JMenuItem mi4 = new JMenuItem("Print");

		// Add action listener to each menu items
		mi1.addActionListener(this);//when clicked action is performed
		mi2.addActionListener(this);
		mi3.addActionListener(this);
		mi4.addActionListener(this);

        //Adding menu items to 'File' Menu
		m1.add(mi1);
		m1.add(mi2);
		m1.add(mi3);
		m1.add(mi4);

		// Creates a menu for menu items
		JMenu m2 = new JMenu("Edit");

		// Create menu items
		JMenuItem mi5 = new JMenuItem("cut");
		JMenuItem mi6 = new JMenuItem("copy");
		JMenuItem mi7 = new JMenuItem("paste");
		JMenuItem mi13 = new JMenuItem("selectAll");
        JMenuItem mi8 = new JMenuItem("color");
       

		// Add action listener
		mi5.addActionListener(this);
		mi6.addActionListener(this);
		mi7.addActionListener(this);
		mi13.addActionListener(this);
        mi8.addActionListener(this);

		m2.add(mi5);
		m2.add(mi6);
		m2.add(mi7);
		m2.add(mi13);
        m2.add(mi8);

		JMenuItem m3 = new JMenu("Font");

		JMenuItem mi9 = new JMenuItem("Fontsize");
		
         
		mi9.addActionListener(this);
	
		m3.add(mi9);
       
		JMenuItem m4 = new JMenu("Themes");

		JMenuItem mi11 = new JMenuItem("Moonlight");
		JMenuItem mi12 = new JMenuItem("DarkTheme");
		JMenuItem mi15 = new JMenuItem("Green");
        
        //Action performed when these items are clicked,they will trigger actions in actionPerformed method
		mi11.addActionListener(this);
		mi12.addActionListener(this);
		mi15.addActionListener(this);
    
		m4.add(mi11);
		m4.add(mi12);
		m4.add(mi15);
        
		JMenuItem mc = new JMenuItem("close");

		mc.addActionListener(this);

        //Adding the menus in menubar
		mb.add(m1);
		mb.add(m2);
		mb.add(m3);
		mb.add(m4);
		mb.add(mc);
        mb.add(fontBox);
		f.setJMenuBar(mb);
		f.setSize(800,700); //sets the size of frame
		f.setVisible(true);
		f.add(scrollPane);
        f.setLocationRelativeTo(null); //center location
        this.add(fontColorButton);		
	}

	// If a button is pressed
	@Override //method is defined in superclass
	public void actionPerformed(ActionEvent e)
	{
		String s = e.getActionCommand();//allows to determine which action is triggered
		if (s.equals("cut")) {
			t.cut();
		}

		if (s.equals("copy")) {
			t.copy();
		}

		if (s.equals("paste")) {
			t.paste();
		}

		if (s.equals("selectAll")) {
			t.selectAll();
		}

		if (s.equals("Save")) {
			// Create an object of JFileChooser class
			JFileChooser j = new JFileChooser("f:");

			// Invoke the showsSaveDialog function to show the save dialog
			int r = j.showSaveDialog(null);
            

			if (r == JFileChooser.APPROVE_OPTION) 
            {

				// Set the label to the path of the selected directory
				File fi = new File(j.getSelectedFile().getAbsolutePath());

				try {
					// Create a file writer
					FileWriter wr = new FileWriter(fi, false); //used to efficiently write characters to the output
					// Create buffered writer to write
					BufferedWriter w = new BufferedWriter(wr);

					// Write
					w.write(t.getText());

					w.flush();
					w.close(); //free up resouces an ensures all data is written
				}

                //handles exception
				catch (Exception evt) {
					JOptionPane.showMessageDialog(f, evt.getMessage());
				}
			}
			// If the user cancelled the operation
			else
				JOptionPane.showMessageDialog(f, "the user cancelled the operation");
		}

		if (s.equals("Print")) {
			try {
				// print the file
				t.print();
			}
			catch (Exception evt) {
				JOptionPane.showMessageDialog(f, evt.getMessage());
			}
		}

		if (s.equals("Open")) {
			// Create an object of JFileChooser class
			JFileChooser j = new JFileChooser("f:");

			// Invoke the showsOpenDialog function to show the save dialog
			int r = j.showOpenDialog(null);

			// If the user selects a file
			if (r == JFileChooser.APPROVE_OPTION) {
				// Set the label to the path of the selected directory
				File fi = new File(j.getSelectedFile().getAbsolutePath());

				try {
					// two string,read and store the contents
					String s1 = "", sl = "";

					// File reader 
					FileReader fr = new FileReader(fi);

					// Buffered reader line by line reading
					BufferedReader br = new BufferedReader(fr);

					// Initialize sl
					sl = br.readLine();  //initial read s1(first line of file)

					// Take the input from the file till all covers
					while ((s1 = br.readLine()) != null) {
						sl = sl + "\n" + s1;
					}

					// Updates the text area yo display contents of opened file.
					t.setText(sl);
				}
				catch (Exception evt) {
					JOptionPane.showMessageDialog(f, evt.getMessage());
				}
			}
			// If the user cancelled the operation
			else
				JOptionPane.showMessageDialog(f, "the user cancelled the operation");
		}

		if (s.equals("New")) {
			t.setText("");  //it clears text are by setting empty string
		}

		if (s.equals("close")) {
			f.setVisible(false);
		}

        if(s.equals("color")) {
			JColorChooser colorChooser = new JColorChooser();
			Color color = colorChooser.showDialog(null,"Choose a color",Color.black);
			t.setForeground(color);
		}
		
        //selects different font name from combo box
		if(e.getSource() == fontBox) {
			t.setFont(new Font((String)fontBox.getSelectedItem(),Font.PLAIN,t.getFont().getSize()));		
		}

        //sets background color as dark theme
		if(s.equals("DarkTheme")){
			t.setBackground(Color.DARK_GRAY);
			t.setForeground(Color.WHITE);
		}

		if(s.equals("Moonlight")){
			t.setBackground(Color.WHITE);
			t.setForeground(Color.black);
		}

		if(s.equals("Green")){
			t.setBackground(Color.GREEN);
			t.setForeground(Color.WHITE);
		}
		
		if(s.equals("Fontsize")){
			String sizeOfFont = JOptionPane.showInputDialog(f,"Enter Font Size",JOptionPane.OK_CANCEL_OPTION);
			if(sizeOfFont != null){
					 int convertSizeOfFont = Integer.parseInt(sizeOfFont);
					 Font font = new Font(Font.SANS_SERIF,Font.PLAIN,convertSizeOfFont);
					 t.setFont(font);
			}
		}
	}

	// Main class
	public static void main(String args[])
	{
	     new TextEditor();
	}
}