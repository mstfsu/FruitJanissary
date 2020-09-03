package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;

public class StartGame {
        static Stage window;
        static Scene scene;
        Game game=new Game();
        GameFinish gameFinish=new GameFinish();
    public void StartGame(ActionEvent event) throws IOException {
        scene=new Scene(game.CreateContent(),500,500);
        window=(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    public void GoRegister(ActionEvent actionEvent) throws IOException{
        Parent RegisterFxml = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scene=new Scene(RegisterFxml,500,500);
        Stage window=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    public void ShowScores(ActionEvent actionEvent) throws IOException, SQLException {
        Scene scene=new Scene(gameFinish.CreateContent(0),500,500);
        Stage window=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    public void ShowYourScore(ActionEvent actionEvent) throws IOException, SQLException {
        Scene scene=new Scene(gameFinish.CreateContent(Controller.user_id),500,500);
        Stage window=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
}
