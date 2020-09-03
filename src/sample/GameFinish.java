package sample;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class GameFinish {
    Parent View;
    Pane pane;
    ScrollPane scroll;

    public Parent CreateContent(int id) throws IOException, SQLException {
        View = FXMLLoader.load(getClass().getResource("GameFinish.fxml"));
        Database database=new Database();
        database.GetData(id);
        int xName=40;
        int yName=10;
        int xSkor=220;
        int ySkor=10;
        int xTime=370;
        int yTime=10;
        pane=new Pane();
        pane.setPrefSize(500,database.name.size()*30);
        scroll=new ScrollPane();
        scroll.setLayoutY(38);
        scroll.setPrefSize(500,300);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        for(int i=0; i<database.name.size();i++){
            Label skor=new Label();
            skor.setLayoutY(yName);
            skor.setLayoutX(xName);
            skor.setMaxWidth(100);
            skor.setFont(Font.font(20));
            skor.setText(database.name.get(i));
            pane.getChildren().add(skor);
            yName+=25;
        }
        for(int i=0; i<database.skor.size();i++){
            Label skor=new Label();
            skor.setLayoutY(ySkor);
            skor.setLayoutX(xSkor);
            skor.setMaxWidth(100);
            skor.setFont(Font.font(20));
            skor.setStyle("-fx-font-weight: bold");
            skor.setText(database.skor.get(i));
            pane.getChildren().add(skor);
            ySkor+=25;
        }
        for(int i=0; i<database.time.size();i++){
            Label skor=new Label();
            skor.setLayoutY(yTime);
            skor.setLayoutX(xTime);
            skor.setMaxWidth(100);
            skor.setFont(Font.font(20));
            skor.setStyle("-fx-font-weight: bold");
            skor.setText(database.time.get(i));
            pane.getChildren().add(skor);
            yTime+=25;
        }
        scroll.setContent(pane);
        ((Pane)View).getChildren().add(scroll);
        return View;
    }
    public void PreviousPage(ActionEvent actionEvent) throws IOException{
        Parent RegisterFxml = FXMLLoader.load(getClass().getResource("StartGame.fxml"));
        Scene scene=new Scene(RegisterFxml,500,500);
        Stage window=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
}
