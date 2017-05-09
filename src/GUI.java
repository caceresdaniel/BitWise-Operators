import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUI extends Application {

	HBox buttons = new HBox();
	HBox imageContainer = new HBox();
	HBox messageBox = new HBox();
	HBox label1 = new HBox();
	HBox label2 = new HBox();
	VBox vbox = new VBox();
	HBox labels = new HBox();

	BorderPane pane = new BorderPane();

	File file;

	ImageView imgview = new ImageView();

	SubPPMImage sub;

	Button sepButt = new Button("Sepia");
	Button negButt = new Button("Negative");
	Button enButt = new Button("Hide Message");
	Button grayButt = new Button("Gray Scale");
	Button grabButt = new Button("Select File");
	Button readButt = new Button("Read Message");

	@Override
	public void start(Stage primaryStage) throws Exception {

		primaryStage.setTitle("BitWise Operators");

		Scene sc = new Scene(pane);
		sc.getStylesheets().add("styles/style.css");

		buttons.getChildren().addAll(grabButt, enButt, readButt, grayButt, negButt, sepButt);

		labels.getChildren().addAll(label1, label2);

		vbox.getChildren().addAll(labels, imageContainer, messageBox);

		pane.setTop(buttons);
		pane.setCenter(vbox);

		// adding style classes to buttons/labels/panes
		buttons.getStyleClass().add("hbox");
		sepButt.getStyleClass().add("button");
		negButt.getStyleClass().add("button");
		enButt.getStyleClass().add("button");
		grayButt.getStyleClass().add("button");
		grabButt.getStyleClass().add("button");
		readButt.getStyleClass().add("button");
		pane.getStyleClass().add("pane");
		label1.getStyleClass().add("lbl");

		// calling button action methods
		grabButton();
		sepButton();
		grayButton();
		negButton();
		enButton();
		readButton();

		primaryStage.setScene(sc);
		primaryStage.show();
	}

	/*
	 * Calls JFileChooser to grab file which then saves the image data to the
	 * PPMImage class File is then turned into image to show on GUI
	 */
	private void grabButton() {
		grabButt.setOnMouseClicked(e -> {
			JFileChooser fc = new JFileChooser();
			int val = fc.showOpenDialog(null);

			if (val == JFileChooser.APPROVE_OPTION) {
				file = fc.getSelectedFile();
				sub = new SubPPMImage(file);
			}

			try {
				Image image = SwingFXUtils.toFXImage(ImageIO.read(file), null);

				imgview.setImage(image);
				imgview.setFitWidth(550);
				imgview.setFitHeight(700);

				this.messageBox.getChildren().clear();
				imageContainer.getChildren().clear();
				imageContainer.getChildren().add(imgview);
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			// Creating label to show which image is the original
			Label labelForOrigin = new Label("Original");
			labelForOrigin.getStyleClass().add("text2");

			// clearing things for when the GUI is reset if not cleared
			// labels would be added every time and would look nasty
			label1.getChildren().clear();
			labels.getChildren().clear();
			label1.getChildren().add(labelForOrigin);
			labels.getChildren().add(label1);

		});
	}

	/*
	 * calls the hideMessage() method in SubPPMImage class to hide the desired
	 * message in the image
	 */
	private void enButton() {
		enButt.setOnMouseClicked(e -> {
			String message = JOptionPane.showInputDialog(null, "Message you wish to hide: ");
			sub.hideMessage(message);

			// calls method that adds the new edited image to the GUI
			newImage();
		});
	}

	/*
	 * calls method to read the message inside the image
	 */
	private void readButton() {
		readButt.setOnMouseClicked(e -> {
			String message = sub.recoverMessage();

			// Creating labels to show the message that has been hidden in image
			Label msg = new Label(message);
			msg.getStyleClass().add("txt");
			Label lbl = new Label("Hidden Message: ");
			lbl.getStyleClass().add("text2");

			this.messageBox.getChildren().clear();
			this.messageBox.getChildren().addAll(lbl, msg);
		});
	}

	/*
	 * calls method to change the filter of the image to Sepia
	 */
	private void sepButton() {
		sepButt.setOnMouseClicked(e -> {
			sub.sepia();
			newImage();
		});
	}

	/*
	 * calls method to change the filter of the image to Gray
	 */
	private void grayButton() {
		grayButt.setOnMouseClicked(e -> {
			sub.grayscale();
			newImage();
		});
	}

	/*
	 * calls method to change the filter of the image to Negative
	 */
	private void negButton() {
		negButt.setOnMouseClicked(e -> {
			sub.negative();
			newImage();

		});
	}

	/*
	 * this method creates the new file with the desired file name the user
	 * chooses after the new file is created it is then added to the GUI
	 */
	private void newImage() {
		String fileName = JOptionPane.showInputDialog(null, "File Name: ");
		sub.writeImage(fileName);
		File newFile = new File(fileName);
		try {

			Image image = SwingFXUtils.toFXImage(ImageIO.read(newFile), null);
			ImageView newImage = new ImageView();
			newImage.setImage(image);
			newImage.setFitWidth(550);
			newImage.setFitHeight(700);

			this.messageBox.getChildren().clear();
			imageContainer.getChildren().clear();
			imageContainer.getChildren().addAll(imgview, newImage);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		// Creating the label to show that this is the new image after the
		// changes
		Label lblForNew = new Label("After Changes");
		lblForNew.getStyleClass().add("text2");

		label2.getChildren().clear();
		label2.getChildren().add(lblForNew);
		labels.getChildren().add(label2);
	}

	public static void main(String[] args) {
		launch(args);
	}

}
