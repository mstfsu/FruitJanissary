package sample;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class Game  {

    Parent gameView;

    ArrayList<Fruit> fruits =new ArrayList<>();
    ArrayList<Fruit> RemovedFruits =new ArrayList<>();
    ArrayList<Bomb> bombs =new ArrayList<>();
    ArrayList<Integer> CreatedTime =new ArrayList<>();

    boolean kontrol=true;
    boolean StopBasıldı=false;

    static int DbSkor;
    static int DbTime;

    Timeline timeline;
    Timeline passingTime;
    Timeline AddDeleteImage;
    Timeline Kontrol;

    Image deleteImage;
    Image ımage;
    Image splash;
    Image orangesplash;
    Image pomesplash;


    int Score=0;
    int PassingFruit=0;
    Label timeLabel;

    ImageView ımageView;
    ImageView ımageDelete1;
    ImageView ımageDelete2;
    ImageView ımageDelete3;

    Random rn=new Random();
    Alert alert=new Alert(Alert.AlertType.INFORMATION);
    Stage stage;

    Button stop=new Button("stop");
    Button play=new Button("Resume");
    Button ShowScore=new Button("Show Score Table");

    Label x1;
    Label x2;
    Label x3;
    Label finishScore;
    int time=0;
    Database database=new Database();


    public Parent CreateContent() throws IOException {
        gameView = FXMLLoader.load(getClass().getResource("GameView.fxml"));
        play.setLayoutX(50);

        x1=new Label();
        x2=new Label();
        x3=new Label();

        splash=new Image("file:splash.png");
        orangesplash=new Image("file:orangesplash.png");
        pomesplash=new Image("file:splashpomegranate.png");
        ImageView ımageKnife=new ImageView();
        ımageDelete1=new ImageView();
        ımageDelete2=new ImageView();
        ımageDelete3=new ImageView();
        Image image=new Image("file:knife.png");
        deleteImage=new Image("file:delete.png");
        ımageKnife.setImage(image);
        ımageKnife.setFitHeight(40);
        ımageKnife.setFitWidth(40);
        ((Pane)gameView).getChildren().add(ımageKnife);
        Label label=new Label();
        label.setLayoutX(400);
        label.setMaxWidth(50);
        label.setLayoutY(2);
        label.setFont(Font.font(20));
        label.setTextFill(Color.WHITE);
        label.setStyle("-fx-font-weight: bold");

        timeLabel=new Label();
        timeLabel.setLayoutX(150);
        timeLabel.setMaxWidth(50);
        timeLabel.setLayoutY(43);
        timeLabel.setFont(Font.font(20));
        timeLabel.setTextFill(Color.WHITE);
        timeLabel.setStyle("-fx-font-weight: bold");

        finishScore=new Label();
        finishScore.setLayoutX(180);
        finishScore.setMaxWidth(200);
        finishScore.setLayoutY(200);
        finishScore.setFont(Font.font(20));
        finishScore.setTextFill(Color.WHITE);
        finishScore.setStyle("-fx-font-weight: bold");

        ((Pane)gameView).getChildren().add(stop);
        ((Pane)gameView).getChildren().add(label);
        ((Pane)gameView).getChildren().add(x1);
        ((Pane)gameView).getChildren().add(x2);
        ((Pane)gameView).getChildren().add(x3);
        ((Pane)gameView).getChildren().add(timeLabel);
        ((Pane)gameView).getChildren().add(play);
        ((Pane)gameView).getChildren().add(ShowScore);
        ((Pane)gameView).getChildren().add(finishScore);

        ShowScore.setLayoutX(200);
        ShowScore.setLayoutY(250);
        ShowScore.setVisible(false);

        gameView.setOnMouseDragged((evt)->{
            ımageKnife.setX(evt.getX()-30);
            ımageKnife.setY(evt.getY()-2);
            if(kontrol) {
                try {
                    SliceObject(label,ımageKnife,evt);
                } catch (IOException  e) {
                    e.printStackTrace();
                }
            }
        });
        AddDeleteImage=new Timeline(new KeyFrame(Duration.millis(1),e->{
            AddDeleteImage();
            DbSkor=Score;
            if(PassingFruit==3){
                AddDeleteImage.stop();
                ClearAllNode();
                finishScore.setText("Your Score is "+Score);
                ShowScore.setVisible(true);
                database.InsertData(Controller.user_id,Score,time);
                ClearPane();
            }

        }));

        AddDeleteImage.setCycleCount(Animation.INDEFINITE);
        AddDeleteImage.play();

        ShowScore.setOnMousePressed((evt)->{
            try {
                PlayAgain(evt);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        passingTime=new Timeline(new KeyFrame(Duration.seconds(1),e->{
            time+=1;
            DbTime=time;
            timeLabel.setText(String.valueOf(time));
            for (int i=0;i<RemovedFruits.size();i++){
                ((Pane)gameView).getChildren().remove(RemovedFruits.get(i).getImageView());

            }
        }));
        passingTime.setCycleCount(Animation.INDEFINITE);
        passingTime.play();

        Kontrol=new Timeline(new KeyFrame(Duration.millis(100),e->{
                for (int i = 0; i < fruits.size(); i++) {
                    if ((int) fruits.get(i).pathTransition.getCurrentTime().toSeconds() == fruits.get(i).hız) {
                        PassingFruit++;
                        fruits.remove(i);
                    }
            }
        }));
        Kontrol.setCycleCount(Animation.INDEFINITE);
        Kontrol.play();

        timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                    ClearPane();
                    if(kontrol||PassingFruit!=3){
                    int FruitKind=rn.nextInt(5);
                    if (FruitKind==0){
                        ımage=new Image("file:applee.png");
                        ımageView=new ImageView(ımage);
                        Fruit fruit = new apple(ımage, ımageView);
                        CreateFruits(fruit);
                    }else if(FruitKind==1){
                        ımage=new Image("file:watermelon.png");
                        ımageView=new ImageView(ımage);
                        Fruit fruit = new Watermelon(ımage, ımageView);
                        CreateFruits(fruit);
                    }else if(FruitKind==3){
                        ımage=new Image("file:orange.png");
                        ımageView=new ImageView(ımage);
                        Fruit fruit = new Orange(ımage, ımageView);
                        CreateFruits(fruit);
                    }else if(FruitKind==4){
                        ımage=new Image("file:pomegranate.png");
                        ımageView=new ImageView(ımage);
                        Fruit fruit = new Pomegranate(ımage, ımageView);
                        CreateFruits(fruit);
                    }else {
                        ımage=new Image("file:bombyeni.png");
                        ımageView=new ImageView(ımage);
                        Bomb bomb=new FirstBomb(ımage,ımageView);
                        CreateBomb(ımage,ımageView,bomb);
                    }
                    }
                })
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        stop.setOnAction(e->{
            stopGame();
            StopBasıldı=true;
        });
        play.setOnAction(e->{
            if(StopBasıldı==true){
                ResumeGame();
                StopBasıldı=false;
            }else{
                System.out.println("oyun duraklatılmadı...");
            }
        });

        return gameView;
    }
    public void CreateBomb(Image ımage,ImageView ımageView,Bomb bomb){
        if(kontrol) {
            bomb.getImageView().setFitHeight(70);
            bomb.getImageView().setFitWidth(70);
            bomb.pathTransition.play();
            ((Pane)gameView).getChildren().add(bomb.getImageView());
            bombs.add(bomb);
        }
    }
    public void ClearPane(){
        if(fruits.size()>6){
            for (int i=0; i<fruits.size()-3;i++){
                ((Pane)gameView).getChildren().remove(fruits.get(i).getImageView());
                fruits.remove(i);
            }
            for (int i=0; i<bombs.size()-2;i++){

                ((Pane)gameView).getChildren().remove(bombs.get(i).getImageView());
                bombs.remove(i);
            }
        }
    }
    public void CreateFruits(Fruit fruit){
        if(kontrol) {
            fruit.getImageView().setFitHeight(70);
            fruit.getImageView().setFitWidth(70);
            ((Pane)gameView).getChildren().add(fruit.getImageView());
            fruits.add(fruit);
            CreatedTime.add(time);
        }
    }
    public void stopGame(){
        kontrol=false;
        for (int i=0; i<fruits.size();i++){
            fruits.get(i).pathTransition.pause();
            fruits.get(i).rt.pause();
        }
        for (int i=0; i<bombs.size();i++){
            bombs.get(i).pathTransition.pause();
            bombs.get(i).rt.pause();
        }
        timeline.stop();
        passingTime.stop();
    }
    public  void ResumeGame() {
        if(kontrol==false) {
            for (int i = 0; i < fruits.size(); i++) {
                fruits.get(i).pathTransition.play();
                fruits.get(i).rt.play();
            }
            for (int i = 0; i < bombs.size(); i++) {
                bombs.get(i).pathTransition.play();
                bombs.get(i).rt.play();
            }
        }
        timeline.play();
        passingTime.play();
        kontrol=true;
    }
    public void RestartGame( Event actionEvent) throws IOException {
        Scene scene=new Scene(CreateContent(),500,500);
        Stage window=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    public void ExitGame(ActionEvent actionEvent) throws IOException, SQLException {

            Parent StartGameFxml = FXMLLoader.load(getClass().getResource("StartGame.fxml"));
            Scene scene=new Scene(StartGameFxml,500,500);
            stage=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

    }
    public void PlayAgain(Event actionEvent) throws IOException, SQLException {

        GameFinish gameFinish=new GameFinish();

        Scene scene=new Scene(gameFinish.CreateContent(Controller.user_id),500,500);
        Stage window=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void SliceObject(Label label, ImageView ımageView,Event event) throws IOException {
        for (int i=0;i<fruits.size();i++){
            String meyve=fruits.get(i).getClass().toString();
            if(fruits.get(i).slice(ımageView,fruits.get(i))&&meyve.substring(13, fruits.get(i).getClass().toString().length() ).equals("Orange")){
                Score+=5;
                label.setText(String.valueOf(Score));
                fruits.get(i).getImageView().setImage(orangesplash);
                fruits.get(i).pathTransition.stop();
                fruits.get(i).rt.pause();
                RemovedFruits.add(fruits.get(i));
                fruits.remove(i);
            }else if(fruits.get(i).slice(ımageView,fruits.get(i))&&meyve.substring(13, fruits.get(i).getClass().toString().length() ).equals("Watermelon")){
                Score+=10;
                label.setText(String.valueOf(Score));
                ımage=new Image("file:splash.png");
                fruits.get(i).getImageView().setImage(splash);
                fruits.get(i).pathTransition.stop();
                fruits.get(i).rt.pause();
                RemovedFruits.add(fruits.get(i));
                fruits.remove(i);
            }else if(fruits.get(i).slice(ımageView,fruits.get(i))&&meyve.substring(13, fruits.get(i).getClass().toString().length() ).equals("apple")){
                Score+=15;
                label.setText(String.valueOf(Score));
                ımage=new Image("file:splash.png");
                fruits.get(i).getImageView().setImage(splash);
                fruits.get(i).pathTransition.stop();
                fruits.get(i).rt.pause();
                RemovedFruits.add(fruits.get(i));
                fruits.remove(i);
            }else if(fruits.get(i).slice(ımageView,fruits.get(i))&&meyve.substring(13, fruits.get(i).getClass().toString().length() ).equals("Pomegranate")){
                Score+=20;
                label.setText(String.valueOf(Score));
                fruits.get(i).getImageView().setImage(splash);
                fruits.get(i).pathTransition.stop();
                fruits.get(i).rt.pause();
                RemovedFruits.add(fruits.get(i));
                fruits.remove(i);
            }
        }
        for (int i=0;i<bombs.size();i++){
            if(bombs.get(i).slice(ımageView,bombs.get(i))){
                stopGame();
                database.InsertData(Controller.user_id,Score,time);
                alert.setTitle("Fruit Janissary");
                alert.setHeaderText("Bombayı kestiniz");
                alert.setContentText("Başlangıç Ekranına Döneceksiniz. Skorunuz: "+ Score);
                alert.showAndWait();
                Parent StartGameFxml = FXMLLoader.load(getClass().getResource("StartGame.fxml"));
                Scene scene=new Scene(StartGameFxml,500,500);
                stage=(Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }
        }
    }
    public void AddDeleteImage(){
        if(PassingFruit==1){
            ımageDelete1.setImage(deleteImage);
            x1.setGraphic(ımageDelete1);
            x1.setLayoutX(450);
            x1.setLayoutY(30);
            x1.setVisible(true);
        }else if(PassingFruit==2){
            ımageDelete2.setImage(deleteImage);
            x2.setGraphic(ımageDelete2);
            x2.setLayoutX(410);
            x2.setLayoutY(30);
            x2.setVisible(true);
        }else if(PassingFruit==3){
            ımageDelete3.setImage(deleteImage);
            x3.setGraphic(ımageDelete3);
            x3.setLayoutX(370);
            x3.setLayoutY(30);
            x3.setVisible(true);
            stopGame();
            Kontrol.stop();
        }else if (PassingFruit>3){
            PassingFruit=3;
        }
    }
    public void ClearAllNode(){
            stopGame();
            for (int i=0; i<fruits.size();i++){
                ((Pane)gameView).getChildren().remove(fruits.get(i).getImageView());
                fruits.remove(i);
            }
            for (int i=0; i<bombs.size();i++){

                ((Pane)gameView).getChildren().remove(bombs.get(i).getImageView());
                bombs.remove(i);
            }

    }
}
