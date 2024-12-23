package q1;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class wav {
	public File compress(File inputFile, String outputDirectory, int count) throws IOException, UnsupportedAudioFileException {
		File outputFile = new File(outputDirectory + "/compressed" + count + ".wav");

		AudioInputStream inputAudioStream = AudioSystem.getAudioInputStream(inputFile);
		AudioFormat sourceFormat = inputAudioStream.getFormat();

		// Target Format for Compression
		AudioFormat targetFormat = new AudioFormat(
				sourceFormat.getEncoding(),
				20000f, // Reduced Sample Rate
				sourceFormat.getSampleSizeInBits(),
				sourceFormat.getChannels(),
				sourceFormat.getFrameSize(),
				20000f,
				sourceFormat.isBigEndian()
		);

		if (!AudioSystem.isConversionSupported(targetFormat, sourceFormat)) {
			throw new UnsupportedAudioFileException("Conversion to the target format is not supported.");
		}

		AudioInputStream compressedStream = AudioSystem.getAudioInputStream(targetFormat, inputAudioStream);

		// Write the compressed audio stream to the output file
		AudioSystem.write(compressedStream, AudioFileFormat.Type.WAVE, outputFile);

		inputAudioStream.close();
		compressedStream.close();

		return outputFile;
	}
}
