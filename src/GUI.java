import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GUI extends Application{
	
	File file = new File("negativecat.ppm");
	SubPPMImage sub = new SubPPMImage(file);

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		primaryStage.setTitle("BitWise Encoding?");
		
		Pane pane = new Pane();
		Scene sc = new Scene(pane);
		Image image = SwingFXUtils.toFXImage(ImageIO.read(file), null);
		
		ImageView imgview = new ImageView();
		
		imgview.setImage(image);
		
		pane.getChildren().add(imgview);
		
		//main();
		
		primaryStage.setScene(sc);
		primaryStage.show();
	}
	
	public void main(){

		sub.negative();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
