package q1;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

public class compression extends JFrame implements ActionListener {
	int count = 0;
	JFrame frame;
	JMenuBar mb;
	JMenu fileMenu;
	JMenuItem open, exitMenu;
	JLabel inputFileLabel;
	JLabel outputFileLabel;
	JLabel compressionLabel;

	compression() {
		frame = new JFrame("Compression Application");

		// Menu Items
		open = new JMenuItem("Open File");
		open.addActionListener(this);

		exitMenu = new JMenuItem("Exit");
		exitMenu.addActionListener(e -> frame.dispose());

		// Menu
		fileMenu = new JMenu("File");
		fileMenu.add(open);
		fileMenu.add(exitMenu);

		mb = new JMenuBar();
		mb.add(fileMenu);
		mb.setBackground(Color.black);

		// Labels
		inputFileLabel = new JLabel("File to be compressed: ");
		inputFileLabel.setFont(new Font("Arial", Font.BOLD, 15));

		outputFileLabel = new JLabel("Compressed file output: ");
		outputFileLabel.setFont(new Font("Arial", Font.BOLD, 15));

		compressionLabel = new JLabel("Compression ratio: ");
		compressionLabel.setFont(new Font("Arial", Font.BOLD, 15));

		// Frame layout
		frame.setJMenuBar(mb);
		frame.setLayout(new GridLayout(3, 1));
		frame.add(inputFileLabel);
		frame.add(outputFileLabel);
		frame.add(compressionLabel);
		frame.setSize(600, 200);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		new compression();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == open) {
			JFileChooser fileChooser = new JFileChooser();
			int result = fileChooser.showOpenDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fileChooser.getSelectedFile();
				if (!selectedFile.getName().endsWith(".wav")) {
					JOptionPane.showMessageDialog(null, "Please select a valid .wav file.", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				inputFileLabel.setText("File to be compressed: " + selectedFile.getAbsolutePath());
				try {
					wav wavCompressor = new wav();
					String outputDir = selectedFile.getParent();
					File compressedFile = wavCompressor.compress(selectedFile, outputDir, ++count);

					AudioInputStream originalStream = AudioSystem.getAudioInputStream(selectedFile);
					AudioInputStream compressedStream = AudioSystem.getAudioInputStream(compressedFile);

					double originalLength = selectedFile.length();
					double compressedLength = compressedFile.length();
					double compressionRatio = compressedLength / originalLength;

					outputFileLabel.setText("Compressed file output: " + compressedFile.getAbsolutePath());
					compressionLabel.setText(String.format("Compression ratio: %.4f", compressionRatio));
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "An error occurred during compression.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
}
