# BitWise-Operators
PPMImage API

    Constructor:
        PPMImage(File image): Takes a File object that is a PPM image.  Loads the image data into the object.  The image must be a PPM image or the program 			will throw a Runtime Error.
    Public Methods:
        writeImage(String filename): Takes a string which is the name of the file to write the image data to.  This will create a new file with the given name 				and write all of the image data into the file.  If you only give the file name, then image will be written to the current working directory of 				the project.
        getPixelData(): Returns the char array that holds the color values of each pixel.  I used a char array because it is the closest thing in Java to an 				unsigned byte datatype since char in Java cannot be negative.  char is 2 bytes in Java so be sure to only work with the lower byte and lower 8 				bits (least significant byte and least significant bits.)

Restrictions

    You may not use the java BitSet class or really any Java class which has to do with Bit manipulation.  You may only use the bitwise operators to alter the 		bits of a value.
    You may not use any Java classes which have to do with working with images.
    You may not make alterations to the PPMImage class.

General Description

For this assignment you will be implementing a program which can take a PPMImage and hide a "secret" message in the image.  You will also implement functions to manipulate the colors of the image.

The PPM Image Format

Digital images are represented as a sequence of elements known as pixels. The pixels are arranged in a series of rows and columns. Several file formats have been created over the years to store images for later viewing. Some of these file formats are PNG, JPEG, GIF, and so on. Another such format is the PPM file format. The PPM file format provides a fairly straightforward way of accessing image data for manipulation. A PPM Image has the following format:

    A "magic number" for identifying the file type. A PPM image's magic number is the two characters "P6". This is the number we will be using for this 		project.
    A whitespace character (this can be an actual space, or line feed, for us this is the linefeed "\n" character).
    A width formatted as ASCII characters in decimal.
    A whitespace character (an actual space).
    A height, again in ASCII decimal.
    A whitespace character (the linefeed "\n" character).
    The maximum color value (maxval), again in ASCII decimal. Must be less than 65536 and more than zero. For our purposes the maxval will always be 255.
    A single whitespace character (the linefeed "\n" character).
    A raster of height rows, in order from top to bottom. Each row consists of width pixels, in order from left to right. Each pixel is a triplet of red, 		green, and blue channels, in that order. Each channel is represented in pure binary by either 1 or 2 bytes. If the maxval is less than 256, it is 1 		byte. Otherwise, it is 2 bytes. The most significant byte is first. For our purposes we will be working with values between 0 ~ 255, so each channel of 		a pixel (red, green, or blue) will only take up one byte. **See section on how to handle the raster values for this assignment.

If we were to look at a PPM image file inside of a text editor such as Notepad++, we would see something like the following:

    The red box is the magic number.
    The green boxes are the white spaces, in this case they should be spaces or line feeds NOT carriage returns.
    The blue box is the width of the image.
    The orange box is the height of the image.
    The purple box is the maxvalue of each pixel channel.
    The yellow box is the actual pixel data. it looks like a jumbled mess because the pixel data is written to the file using binary file I/O and the text 		editor will try to render the byte values as their ASCII character equivalent.

For more information on the PPM file format, see the link HERE.
PPMImage Subclass

Create a subclass of my PPMImage class.  Your subclass will add the following methods:

    hideMessage(String message): This method takes a string and hides the string within the bits of the image's pixel data.  See below for explanation on how 		to accomplish this.
    recoverMessage(): This method retrieves and returns a hidden message from a PPMImage.
    grayscale(): This function takes no parameters and returns no values. It converts all of the pixel data so that the resulting image is no longer in color, 		but in grayscale. See below for explanation.
    sepia(): This function takes no parameters and returns no values. It converts all of the pixel data so that the resulting image is no longer in color but 		in sepia color tones. See below for explanation.
    negative(): This function takes no parameters and returns no values. It converts all of the pixel data so that the resulting image is no longer the 		original colors but the negative. See below for explanation.
    NOTE: The color manipulation functions should not be called on an image that has the hidden message.  This will corrupt the message because the values of 		the pixels will be changed.

Steganography: Hiding and Recovering a Secret Message

Steganography the hiding of information within image data. In this assignment, you are to use your PPMImage Subclass to allow the user to "hide" a string of any length within your image data. How can this be accomplished you ask?? Why using bitwise manipulation, of course!

Consider the following Image and assume that the three pixels next to each other have the same color. So each channel (RGB value) would look like the following:

The colors of the pixels highlighted in the image above are represented using the RGB color model. In this model, three separate color values (Red, Green, and Blue) are blended together to create the final color of the pixel. The PPM images that we will work with will limit the range of values that can be represented in each color channel to 256 different values (0 ~ 255). Thus, each pixel of your PPM image data can be represented using three characters since a char is 2 bytes and a byte can hold values between 0 and 255.

Considering the three pixels in the image above as a sequence of bits, we can arrive at the following diagram:

Now if we use the LSB (Least Significant Bit) of each of these channels, we would want to alter the following bits:


Why would we want to do this? To encode a secret message in the channels of the image! This is what Steganography is all about. By changing ONLY the LSB of each channel, we are making changes to the pixel which are imperceptible to the human eye. You can actually use this knowledge to encode a secret string message into the image.

Now, suppose you wanted to encode the letter 'S' into our image. We know that a unsigned char is 2 bytes (8 bits) but we will treat it as only one byte.  This means you need 8 channels to encode one letter of the secret message. Consider the binary representation of the ASCII code for uppercase letter 'S':

We can alter the LSBs of the first 8 channels in our 3 pixels to match the bits of the letter 'S'. (The last channel is left over and would most likely be used as part of the next character's encoding.) Altering the pixels would look something like this: (Note: some of the LSBs do stay the same because they match the same corresponding bits of the letter.)

Now the 8 bits of 'S' have been scattered across the individual channels of the pixels. Now imagine spreading the bits of an entire phrase or document across multiple pixels and color channels in the entire image. Doing so would effectively hid your data in an image without any adverse effects on the image itself.  The LSB only changes the color value by +/- 1 which to the human eye is not detectable.

In your program, you will prompt the user for a phrase to be hidden inside of a PPM image. You can assume that the phrase will be short enough to store within the dimensions of the PPM image. Be sure to add the null terminating character '\0' to the end of the string that you wish to hide.  The binary value of this character is 00000000. This character will be of extreme importance during the decoding process. Once the data has been hidden, you should write your image data to a new PPM image file. Be sure not to overwrite the original PPM image.

Your program should also allow the user to extract hidden messages from PPM images. To do so, you should reverse the process used to hide the data. Extract the LSB from every color channel to construct 8- bit characters. When you extract the null terminating character '\0', you should stop searching for additional data and report the message that you have uncovered.
Image Color Manipulation

Another part of your program will also be responsible for manipulating the color values of your PPM images. NOTE: These following operations DO NOT use bitwise manipulation. These formulas simply change the numeric values of the pixels.

Grayscale Images: To convert an image to grayscale coloring use the following formula. NOTE: R, G, B stands for the original red, green, and blue values respectively.

redResult = (R * .299) + (G * .587) + (B * .114)
greenResult = (R * .299) + (G * .587) + (B * .114)
blueResult = (R * .299) + (G * .587) + (B * .114)

Sepia Images: To convert an image to sepia tone coloring use the following formula. NOTE: R, G, B stands for the original red, green, and blue values respectively.

redResult = (R * .393) + (G *.769) + (B * .189)
greenResult = (R * .349) + (G *.686) + (B * .168)
blueResult = (R * .272) + (G *.534) + (B * .131)

NOTE: If a resulting value is greater than 255, then just set it to be 255.

Negative Images: To convert an image to its negative coloring, use the following formula. NOTE: R, G, B stands for the original red, green, and blue values respectively.

redResult = 255 - R
greenResult = 255 - G
blueResult = 255 – B
GUI

JavaFX does not natively support display of PPM images.  Marco found a solution that I would like to share with the class.

Download the following jar file:

    Java Advanced Imaging Library

Include this jar file in your program.  After this you can use two methods to convert a PPM image into something that JavaFX can display:

Image image = SwingFXUtils.toFXImage(ImageIO.read(ppm_image_file), null);

Here the ImageIO.read() method takes a file object (that should be a .ppm image) and converts it to a BufferedImage.  This this point you pass the BufferedImage to the SwingFXUtils.toFXImage() method to get an Image object that JavaFX can work with.

After that, just use the normal JavaFX ImageView and so on to display your images.
Grading

    100 Points: Correctly hide / recover a secret message.
        NOTE To get credit, both operations must be correct.  Also, your encoded image must be able to be decoded by my program, and your program should be      		  able to decode an image created by my program.
    25 Points: Correctly convert an image to Grayscale.
    25 Points: Correctly convert an image to Sepia.
    25 Points: Correctly convert an image to Negative.
    25 Points: Create a GUI to show the results.  Use JFileChooser to select any input files.
     
count: 8test: 0
count: 7test: 1
count: 6test: 2
count: 5test: 3
count: 4test: 4
count: 3test: 5
count: 2test: 6
count: 1test: 7

SCRAPPED CODE - MIGHT NEED LATER

	for (int index = 0; index < message.length(); index++) {
			for (int i = 0; i < message.length() * 8; i++) {
				if (i % 8 == 0 && count != 8) {
					char mask = (char) (1 << (count - 1));
					if ((message.charAt(index) & mask) == 0) {

					}
					count = 8;
				} else {

					count--;
				}
			}
		}
		
		
MORE SCRAPPED CODE

public String recoverMessage() {
		char[] bits = getPixelData();

		char mask = (char) 1 << 0;

		int count = 0;
		
		String message = null;
		
		ArrayList<Integer> onesAndZeros = new ArrayList<>();

		for (int i = 0; i < bits.length; i++) {
			if (count == 8) {
				break;
			} else if ((bits[i] & mask) == 0) {
				onesAndZeros.add(0);
				count++;
			} else {
				onesAndZeros.add(1);
				count = 0;
			}
		}
		
	//	message = rmHelper(onesAndZeros);
		
		System.out.println(onesAndZeros.toString());
		
		
		return message;
	}
	
	private String rmHelper(ArrayList<Integer> onesAndZeros){
		
		String message = null;
		
		int count = 8;
		
		for(int i = 0; i < onesAndZeros.size(); i++){
			if(onesAndZeros.get(i % 8 - count).equals(1)){
				int num = (int) Math.pow(2, (i % 8) + count - i);	
			}
			
		}
		
		
		return message;
	}