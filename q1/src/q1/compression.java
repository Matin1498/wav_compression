package q1;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
public class compression extends JFrame implements ActionListener {
	int count = 0;
	JFrame frame;
	JMenuBar mb;  
	JButton file, exit;
	JMenuItem open,Exit_menu;    
	JLabel input_file;
	JLabel output_file;
	JLabel compression;
	compression(){   
		frame = new JFrame();
	open=new JMenuItem("Open File");  
	open.addActionListener(this);     
	Exit_menu = new JMenuItem("Exit Menu");
	Exit_menu.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			frame.dispose();
		}
	});
	file=new JButton("File"); 
	file.setForeground(Color.white);
	exit=new JButton("Exit");
	exit.setForeground(Color.white);
	file.add(open);             
	exit.add(Exit_menu);
	mb=new JMenuBar();    
	mb.add(file);       
	mb.add(exit);
	mb.setBackground(Color.black);
	input_file=new JLabel("File to compressed : ");  
	input_file.setFont(new Font("Arial", Font.BOLD, 15));
	output_file=new JLabel("File to compressed : "); 
	output_file.setFont(new Font("Arial", Font.BOLD, 15));
	compression = new JLabel("Compression ratio : ");
	compression.setFont(new Font("Arial", Font.BOLD, 15));
    JPanel p = new JPanel();
	frame.add(mb);    
	frame.add(input_file);
	frame.add(output_file);
	frame.add(compression);
	frame.setLayout(new GridLayout(4,0));
	frame.pack();
	}
	
	public static void main(String[] args) {
	    compression om=new compression();    
        om.frame.setSize(600,200);    
        om.frame.setVisible(true);    
        om.frame.setDefaultCloseOperation(EXIT_ON_CLOSE); 

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		count += 1;
		double comp_ratio = 0;
		if(e.getSource()==open){    
			String pfile = null;
		    JFileChooser fc=new JFileChooser();    
		    int i=fc.showOpenDialog(this);    
		    if(i==JFileChooser.APPROVE_OPTION){    
		        File f=fc.getSelectedFile();    
		        if(!f.toString().contains(".wav")) {
					JOptionPane.showMessageDialog(null, "You must enter a valid wav file to compress.", "compress", JOptionPane.ERROR_MESSAGE);
		        }else {
		        wav w = new wav();
		        input_file.setText(input_file.getText()+f.toString());
		        pfile = fc.getSelectedFile().getParent().toString();
		        try {
		        	double length = f.length();
					File out =w.compress(f, pfile, count);
					AudioInputStream in = AudioSystem.getAudioInputStream(f);
					AudioInputStream output = AudioSystem.getAudioInputStream(out);
					double af_length = out.length();
					comp_ratio = af_length / length;
					double compression_ratio = output.getFrameLength()*100/in.getFrameLength();
					compression.setText("Compression ratio: " + String.format("%.4f", comp_ratio));
					output_file.setText(output_file.getText()+out.toString());
				} catch (InterruptedException | UnsupportedAudioFileException | IOException e1) {
					e1.printStackTrace();
				}
		        } 
		    }
		    }    
		}  
	}