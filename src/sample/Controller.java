package sample;

import Bubble.BubbleSpec;
import Bubble.BubbledLabel;
import Client.Contact;
import Client.Message;
import Client.Report;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    @FXML private TextArea messageBox;
    @FXML private Label usernameLabel;
    @FXML private Label onlineCountLabel;
    @FXML private ListView userList;
    @FXML ListView chatPane;
    @FXML ListView statusList;
    @FXML BorderPane borderPane;
    @FXML ComboBox statusComboBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUserList();
        setUsernameLabel("otorio");

    }
    public void sendButtonAction() throws IOException {
        Message msg = new Message();
        msg.contact.name = this.usernameLabel.getText();
        msg.text = messageBox.getText();
        if (!messageBox.getText().isEmpty()) {
            //посылка сообщ
            setValueToLabel(msg);
            messageBox.clear();
        }
    }
    public synchronized  void setValueToLabel(Message msg){
       Task<HBox> othersMessages = new Task<HBox>(){
            @Override
            public HBox call() throws Exception {
                BubbledLabel bl6 = new BubbledLabel();
                bl6.setText(msg.contact.name + ": " + msg.text);
                bl6.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
                HBox x = new HBox();
                bl6.setBubbleSpec(BubbleSpec.FACE_LEFT_CENTER);
                x.getChildren().addAll(bl6);
               // logger.debug("ONLINE USERS: " + Integer.toString(msg.getUserlist().size()));
                //setOnlineLabel(Integer.toString(msg.getOnlineCount()));
                return x;
            }
        };
        othersMessages.setOnSucceeded(event -> {
            chatPane.getItems().add(othersMessages.getValue());
        });
        Task<HBox> yourMessages = new Task<HBox>() {
            @Override
            public HBox call() throws Exception {
                BubbledLabel bl6 = new BubbledLabel();
                bl6.setText(msg.text);
                bl6.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN,
                        null, null)));
                HBox x = new HBox();
                x.setMaxWidth(chatPane.getWidth() - 20);
                x.setAlignment(Pos.TOP_RIGHT);
                bl6.setBubbleSpec(BubbleSpec.FACE_RIGHT_CENTER);
                x.getChildren().addAll(bl6);
                //setOnlineLabel(Integer.toString(msg.getOnlineCount()));
                return x;
            }
        };
        yourMessages.setOnSucceeded(event -> chatPane.getItems().add(yourMessages.getValue()));
        try {
            if (msg.contact.name.equals(usernameLabel.getText())) {
                Thread t2 = new Thread(yourMessages);
                t2.setDaemon(true);
                t2.start();
            } else {
                Thread t = new Thread(othersMessages);
                t.setDaemon(true);
                t.start();
            }
        }catch (Exception ex){
            System.out.println("потоки сообщ");}
    };
    public void setUsernameLabel(String username) {
        try {
            this.usernameLabel.setText(username);

        }catch (Exception ex){System.out.println("err присвоение логина");}
    }
    public void setUserList() {
        ArrayList<String> users = new ArrayList<String>();
        users.add("Dfcz");
        users.add("Paha");
        users.add("Vacz");
        Platform.runLater(() -> {
            ObservableList<String> usersList = FXCollections.observableList(users);
            userList.setItems(usersList);
            //setOnlineLabel(String.valueOf(msg.getUserlist().size()));
        });
    }
    public void setOnlineLabel(String usercount) {
        Platform.runLater(() -> onlineCountLabel.setText(usercount));
    }
}
