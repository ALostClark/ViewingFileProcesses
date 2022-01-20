package clark;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static Scene scene;
    private static Stage stage;
    private static String[] startArgs;

    @Override
    public void start(Stage primaryStage) throws Exception{

        scene = new Scene(loadFXML("ui/sample.fxml"));

        Main.stage = primaryStage;
        stage.setScene(scene);
        stage.setTitle("File ");
        stage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }


    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml));
        return fxmlLoader.load();
    }

    public static String[] getStartArgs() {return startArgs;}

    public static Stage getStage() {
        return stage;
    }
}



