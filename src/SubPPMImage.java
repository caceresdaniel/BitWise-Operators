import java.io.File;

public class SubPPMImage extends PPMImage {

	public SubPPMImage(File arg0) {
		super(arg0);
	}

	public void hideMessage(String message) {

	}

	public String recoverMessage() {
		return null;
	}

	public void grayscale() {

	}

	public void sepia() {

	}

	public void negative() {
		char[] bits = getPixelData();
		
		

		for (int i = 0; i < bits.length; i += 3) {
			bits[i] = (char) (255 - bits[i]);
			bits[i + 1] = (char) (255 - bits[i + 1]);
			bits[i + 2] = (char) (255 - bits[i + 2]);
		}
		
		

		writeImage("negativecat.ppm");
	}
}
