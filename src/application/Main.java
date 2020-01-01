package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

	private Stage primaryStage;
	private AnchorPane rootLayout;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("리뷰의 민족");

		initLoginLayout();
	}
	public static void pause() {
		try {
		      System.in.read();
		    } catch (IOException e) { }
		  
	}
	
	public void initLoginLayout() {
		// fxml 파일에서 상위 레이아웃을 가져온다.
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../view/Login.fxml"));
			Scene scene = new Scene(root, 1000, 600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    public Stage getPrimaryStage() {
        return primaryStage;
    }


	public static void main(String[] args) {
		launch(args);
	}
}
