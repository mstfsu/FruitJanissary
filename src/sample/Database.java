package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.sql.*;
import java.util.ArrayList;

public class Database {
    int kontrol;
    ArrayList<String> name=new ArrayList<>();
    ArrayList<String> skor=new ArrayList<>();
    ArrayList<String> time=new ArrayList<>();

    public void InsertData(int id,int skor,int time){
        try {
            Connection connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/fruit?useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=Turkey","root","");
            String sql = "INSERT INTO game(player_id,skor,süre ) "
                    + "VALUES(?,?,?)";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1,String.valueOf(id));
            pstmt.setString(2,String.valueOf(skor));
            pstmt.setString(3,String.valueOf(time));

            kontrol=pstmt.executeUpdate();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void GetData(int id) throws SQLException {
        Statement stmt;
        Connection connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/fruit?useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=Turkey","root","");

        String sql = "SELECT name,skor,süre FROM `game` INNER JOIN players WHERE players.id=game.player_id "+(id!=0?"and players.id="+id:"")+" ORDER BY game.skor DESC";
        stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while(rs.next()){
            name.add(rs.getString("name"));
            skor.add(rs.getString("skor"));
            time.add(rs.getString("süre"));
        }

        rs.close();
    }
}
