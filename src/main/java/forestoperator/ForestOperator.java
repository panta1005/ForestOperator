package forestoperator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Paolo
 */
public class ForestOperator extends Application {
    private static final String VERSION = "ForestOperator 1.0";
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("StartScene.fxml"));
       // Parent root = FXMLLoader.load(getClass().getResource("/view/MainScene.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle(VERSION);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public static String getVersion(){
        return VERSION;
    }
    
}
