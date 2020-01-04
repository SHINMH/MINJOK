package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {
	private Stage primaryStage; //메인 스테이지

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("리뷰의 민족");
		Image image = new Image("/image/AppIcon.png");
		
		this.primaryStage.getIcons().add(image); //프로그램 아이콘 설정
		initLoginLayout();
	}

	public void initLoginLayout() {
		// stage에 scene을 설정하고. stage를 화면에 보여준다.
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/view/LoginView.fxml"));
			Scene scene = new Scene(root, 1000, 600);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//프로그램 진입점
	public static void main(String[] args) {
		launch(args);
	}
}
