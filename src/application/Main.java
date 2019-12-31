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

	public void showProdDetailView() {
		try {
			// fxml 파일에서 상위 레이아웃을 가져온다.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../view/ProdDetailView.fxml"));
			rootLayout = (AnchorPane) loader.load();

			// 상위 레이아웃을 포함하는 scene을 보여준다.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showJoinView() {
		try {
			// fxml 파일에서 상위 레이아웃을 가져온다.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../view/JoinView.fxml"));
			rootLayout = (AnchorPane) loader.load();

			// 상위 레이아웃을 포함하는 scene을 보여준다.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	 /**
     * 메인 스테이지를 반환한다.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }


	public static void main(String[] args) {
		launch(args);
	}
}
