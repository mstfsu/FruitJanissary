package sample;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.sql.*;

public class Controller {
    @FXML
    TextField name;
    @FXML
    PasswordField password;

    static int user_id;

    Cipher cipher;
    Key aesKey;
    String decryptPass;

    public void RegisterView(ActionEvent actionEvent) throws Exception{

        Parent RegisterFxml = FXMLLoader.load(getClass().getResource("registerView.fxml"));
        Scene scene=new Scene(RegisterFxml,500,500);

        Stage window=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }
    public void OpenGame(ActionEvent actionEvent) throws Exception{
        /*Statement stmt;
        Connection connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/fruit?useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=Turkey","root","");

       /* String sql = "SELECT name, password,id FROM players";
        stmt = connection.createStatement();
        boolean Kontrol=true;

        ResultSet rs = stmt.executeQuery(sql);

        while(rs.next()){
            encrypt(password.getText());
           if(rs.getString("name").equals(name.getText())&&decryptPass.equals(password.getText())){
                user_id=Integer.parseInt(rs.getString("id"));
                Kontrol=true;
            }
        }*/
        if (true){
            Parent RegisterFxml = FXMLLoader.load(getClass().getResource("StartGame.fxml"));
            Scene scene=new Scene(RegisterFxml,500,500);
            Stage window=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }else {

            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Fruit Janissary");
            alert.setContentText("Girdiğiniz Bilgiler Hatalı Lütfen Tekrar Deneyinizi.");
            alert.setHeaderText("Giriş Başarısız.");
            alert.showAndWait();
        }
        //rs.close();
    }
    public  String encrypt(String pass) throws Exception {
        String enc=null;
        try {
            String text = pass;
            String key = "Bar12345Bar12345";
            aesKey = new SecretKeySpec(key.getBytes(), "AES");
            cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            byte[] encrypted = cipher.doFinal(text.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b: encrypted) {
                sb.append((char)b);
            }
            enc = sb.toString();
            decryptPass=decrypt(enc);
        }catch (Exception e){

        }
        return  enc;

    }
    public  String decrypt(String pass) {
        String decrypted=null;
        try {

            byte[] bb = new byte[pass.length()];
            for (int i=0; i<pass.length(); i++) {
                bb[i] = (byte) pass.charAt(i);
            }
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            decrypted = new String(cipher.doFinal(bb));
        }catch (Exception e){

        }
        return decrypted;
    }
}
