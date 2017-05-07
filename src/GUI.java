import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GUI extends Application {

	HBox buttons = new HBox();
	HBox imageContainer = new HBox();

	Pane pane = new Pane();

	File file;

	ImageView imgview = new ImageView();

	SubPPMImage sub;

	Button sepButt = new Button("Sepia");
	Button negButt = new Button("Negative");
	Button grayButt = new Button("Gray Scale");
	Button enButt = new Button("Hide Message");
	Button grabButt = new Button("Select File");
	Button readButt = new Button("Read Message");

	@Override
	public void start(Stage primaryStage) throws Exception {

		primaryStage.setTitle("BitWise Encoding?");

		Scene sc = new Scene(pane, 1000, 1000);

		buttons.getChildren().addAll(grabButt, enButt, readButt, grayButt, negButt, sepButt);
		
		pane.getChildren().addAll(buttons, imageContainer);

		grabButton();
		sepButton();
		grayButton();
		negButton();

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
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
	}

	private void sepButton() {
		sepButt.setOnMouseClicked(e -> {
			sub.sepia();
		});
	}

	private void grayButton() {
		grayButt.setOnMouseClicked(e -> {
			sub.grayscale();
		});
	}

	private void negButton() {
		negButt.setOnMouseClicked(e -> {
			sub.negative();
		});
	}

	public static void main(String[] args) {
		launch(args);
	}

}
