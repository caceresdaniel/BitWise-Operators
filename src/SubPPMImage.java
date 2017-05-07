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
		char[] bits = getPixelData();

		for (int i = 0; i < bits.length; i += 3) {
			char red = bits[i];
			char green = bits[i + 1];
			char blue = bits[i + 2];
			bits[i] = (char) ((red * .299) + (green * .587) + (blue * .114));
			bits[i + 1] = (char) ((red * .299) + (green * .587) + (blue * .114));
			bits[i + 2] = (char) ((red * .299) + (green * .587) + (blue * .114));
		}
	}

	public void sepia() {
		char[] bits = getPixelData();

		for (int i = 0; i < bits.length; i += 3) {
			char red = bits[i];
			char green = bits[i + 1];
			char blue = bits[i + 2];
			bits[i] = (char) ((red * .393) + (green * .769) + (blue * .189));
			bits[i + 1] = (char) ((red * .349) + (green * .686) + (blue * .168));
			bits[i + 2] = (char) ((red * .272) + (green * .534) + (blue * .131));

			if (bits[i] > 255) {
				bits[i] = 255;
			}
			if (bits[i + 1] > 255) {
				bits[i + 1] = 255;
			}
			if (bits[i + 2] > 255) {
				bits[i + 2] = 255;
			}
		}
	}

	public void negative() {
		char[] bits = getPixelData();

		for (int i = 0; i < bits.length; i += 3) {
			bits[i] = (char) (255 - bits[i]);
			bits[i + 1] = (char) (255 - bits[i + 1]);
			bits[i + 2] = (char) (255 - bits[i + 2]);
		}
	}
}
