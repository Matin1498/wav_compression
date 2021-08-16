package q1;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class wav {
	public static File compress(File f, String pfile, int count) throws InterruptedException, UnsupportedAudioFileException, IOException {
	    File file = f;
	    File output = new File(pfile + "/compressed" + count + ".wav");

	    AudioInputStream ais;
	    AudioInputStream eightKhzInputStream = null;
	    ais = AudioSystem.getAudioInputStream(file);
	    AudioFormat sourceFormat = ais.getFormat();
	    if (ais.getFormat().getSampleRate() != 22050f) {
	        AudioFileFormat sourceFileFormat = AudioSystem.getAudioFileFormat(file);
	        AudioFileFormat.Type targetFileType = sourceFileFormat.getType();

	        AudioFormat targetFormat = new AudioFormat(
	                sourceFormat.getEncoding(),20000f,sourceFormat.getSampleSizeInBits(),sourceFormat.getChannels(),
	                sourceFormat.getFrameSize(),
	                20000f,
	                sourceFormat.isBigEndian());
	        if (!AudioSystem.isFileTypeSupported(targetFileType) || ! AudioSystem.isConversionSupported(targetFormat, sourceFormat)) {
	              throw new IllegalStateException("Conversion not supported!");
	        }
	        eightKhzInputStream = AudioSystem.getAudioInputStream(targetFormat, ais);
	        int nWrittenBytes = 0;
	        nWrittenBytes = AudioSystem.write(eightKhzInputStream, targetFileType, output);
	    }
	    return output;
	}
}
