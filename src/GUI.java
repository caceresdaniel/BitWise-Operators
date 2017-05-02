import java.io.File;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GUI extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		primaryStage.setTitle("BitWise Encoding?");
		
		Pane pane = new Pane();
		Scene sc = new Scene(pane, 1000, 1000);
		File file = new File("cat.ppm");
		Image image = SwingFXUtils.toFXImage(ImageIO.read(file), null);
		
		ImageView imgview = new ImageView();
		
		imgview.setImage(image);
		
		pane.getChildren().add(imgview);
		
		primaryStage.setScene(sc);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
