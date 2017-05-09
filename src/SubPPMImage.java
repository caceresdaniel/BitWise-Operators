import java.io.File;

public class SubPPMImage extends PPMImage {

	public SubPPMImage(File arg0) {
		super(arg0);
	}

	/*
	 * method that adds the message into the image by editing the LSB of each
	 * pixel value
	 */
	public void hideMessage(String message) {
		// Mask used to turn bit off
		char maskOn = (char) 1 << 0;
		// Mask used to turn bit off
		char maskOff = (char) ~(1 << 0);

		// Added null at the end of string to be able to tell the end of message
		// when decrypting
		message += "\0";

		char[] bits = getPixelData();

		// Count used to keep track of the bit being added to pixels LSB
		int count = 8;
		// This number lets us know at which bit we last edited/going to be
		// edited
		int bitIndex = 0;

		for (int index = 0; index < message.length(); index++) {
			// count is reset every time it has gone through a full byte
			count = 8;
			for (int i = 0; i < 8; i++) {
				// Mask to see if the bit at COUNT is on or off
				char mask = (char) (1 << ((count) - 1));

				// First checks to see if the bit is off
				// If it is off the LSB at the pixel it is at is turned off
				if ((message.charAt(index) & mask) == 0) {
					bits[bitIndex] = (char) (bits[bitIndex] & maskOff);
				}
				// If the bit is not off it means it is on
				// So it then turns the LSB on at the pixel it is currently on
				else {
					bits[bitIndex] = (char) (bits[bitIndex] | maskOn);
				}
				// Increases bitIndex so that it moves on to the next pixel
				bitIndex++;
				// Count is decreased when that bit of the letter being
				// encrypted has been processed
				count--;
			}
		}
	}

	/*
	 * Decrypts the message hidden in the image by creating an empty character
	 * that empty character is then modified depending on the LSB of the pixels
	 * the character after going through 8 pixels (since a character consists of
	 * 8 bits) is then appended to a string and starts the process all over
	 */
	public String recoverMessage() {
		char[] bits = getPixelData();
		// Mask to tell whether the LSB of the pixel is on or off
		char mask = (char) 1 << 0;

		// Empty string where the characters are to be appended too
		String message = "";

		// Count that signifies a complete byte when it reaches 8
		int count = 0;

		// Count that is used as the condition to break out of the loop
		int zeroCount = 0;

		// creation of the null character to be edited
		char character = '\0';

		for (int i = 0; i < bits.length; i++) {
			// When Zero count reaches 8 it means we have found 8 off bits
			// which signifies we have found the null character/end of the
			// message
			if (zeroCount == 8) {
				break;
			}
			// The count is checked to see if it has reached 8
			// If it has reached 8 that means that character is complete
			// And is ready to be added to the string
			if (count % 8 == 0 && count != 0) {
				// shifts to the right one to fix the extra left shift
				character = (char) (character >> 1);
				// character added to message
				message += character;
				// character is reset back to null for the next character to be
				// decrypted
				character = '\0';
			}
			// Checks if the bit is on and off if the bit is on that means the
			// LSB of the character needs to be turned on
			// it is then shifted to the left once for the next bit of the
			// character ZeroCount is also set to zero signifying that it is not
			// the null character
			if ((bits[i] & mask) > 0) {
				character = (char) (character | mask);
				character = (char) (character << 1);
				zeroCount = 0;
				count++;
			}
			// If the bit is off it is simply shifted to the left
			else if ((bits[i] & mask) == 0) {
				character = (char) (character << 1);
				zeroCount++;
				count++;
			}
		}
		return message;
	}

	/*
	 * Simple Pixel editing to make the filter of the image gray
	 */
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

	/*
	 * Simple Pixel editing to make the filter of the image sepia
	 */
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

	/*
	 * Simple Pixel editing to make the filter of the image negative
	 */
	public void negative() {
		char[] bits = getPixelData();

		for (int i = 0; i < bits.length; i += 3) {
			bits[i] = (char) (255 - bits[i]);
			bits[i + 1] = (char) (255 - bits[i + 1]);
			bits[i + 2] = (char) (255 - bits[i + 2]);
		}
	}
}
