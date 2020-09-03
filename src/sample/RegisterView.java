package sample;

import java.io.IOException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

import java.security.*;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.SecretKeySpec;

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


public class RegisterView {

    @FXML
    TextField name;
    @FXML
    TextField surname;
    @FXML
    PasswordField password;

    Cipher cipher;
    Key aesKey;
    int kontrol;

    public void SignUp(ActionEvent actionEvent) throws Exception{

        try {
            Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/fruit?useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=Turkey","root","");
            String sql = "INSERT INTO players(name,surname,password) "
                    + "VALUES(?,?,?)";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1,name.getText());
            pstmt.setString(2,surname.getText());
            pstmt.setString(3,encrypt(password.getText()));
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Fruit Janissary");
            alert.setHeaderText(name.getText()+" "+surname.getText());
            if(!name.getText().equals("")&& !password.getText().equals("")){
                 kontrol=pstmt.executeUpdate();
            }
            if(kontrol>0 ){
                alert.setContentText("Kullanıcı Kaydedildi.");
                alert.showAndWait();
                Parent RegisterFxml = FXMLLoader.load(getClass().getResource("sample.fxml"));
                Scene scene=new Scene(RegisterFxml,500,500);

                Stage window=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            }else {
                alert.setContentText("Kayıt Yapılamadı Bilgilerinizi Kontrol Ediniz!");
                alert.showAndWait();
            }
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void PreviousPage(ActionEvent actionEvent) throws IOException {
        Parent RegisterFxml = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scene=new Scene(RegisterFxml,500,500);

        Stage window=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
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
            decrypt(enc);
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
