import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class GUI extends Application {

	HBox buttons = new HBox();
	HBox imageContainer = new HBox();

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

		primaryStage.setTitle("BitWise Encoding?");

		Scene sc = new Scene(pane);
		sc.getStylesheets().add("styles/style.css");

		buttons.getChildren().addAll(grabButt, enButt, readButt, grayButt, negButt, sepButt);

		pane.setTop(buttons);
		pane.setCenter(imageContainer);
		buttons.getStyleClass().add("hbox");
		sepButt.getStyleClass().add("button");
		negButt.getStyleClass().add("button");
		enButt.getStyleClass().add("button");
		grayButt.getStyleClass().add("button");
		grabButt.getStyleClass().add("button");
		readButt.getStyleClass().add("button");
		pane.getStyleClass().add("pane");

		grabButton();
		sepButton();
		grayButton();
		negButton();
		enButton();
		readButton();

		primaryStage.setScene(sc);
		primaryStage.show();
	}

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

				imageContainer.getChildren().clear();
				imageContainer.getChildren().add(imgview);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
	}
	
	private void enButton(){
		enButt.setOnMouseClicked(e->{
			String message = JOptionPane.showInputDialog(null, "Message you wish to hide: ");
			sub.hideMessage(message);
			newImage();
		});
	}
	
	private void readButton(){
		readButt.setOnMouseClicked(e->{
			
		});
	}

	private void sepButton() {
		sepButt.setOnMouseClicked(e -> {
			sub.sepia();
			newImage();
		});
	}

	private void grayButton() {
		grayButt.setOnMouseClicked(e -> {
			sub.grayscale();
			newImage();
		});
	}

	private void negButton() {
		negButt.setOnMouseClicked(e -> {
			sub.negative();
			newImage();
		});
	}

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

			imageContainer.getChildren().clear();
			imageContainer.getChildren().addAll(imgview, newImage);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
