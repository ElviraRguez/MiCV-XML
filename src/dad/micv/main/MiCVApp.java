package dad.micv.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
 
public class MiCVApp extends Application{
	private static Stage primaryStage;
	private MainController controller;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		MiCVApp.primaryStage = primaryStage;
		controller = new MainController();
		
		primaryStage.setTitle("MiCV");
		primaryStage.setScene(new Scene(controller.getView(), 640, 480));
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/dad/micv/resources/ic_cv.png")));
		primaryStage.show();
	}

	public static Stage getPrimaryStage() {
		return primaryStage;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}