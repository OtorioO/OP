package sample;

import Bubble.BubbleSpec;
import Bubble.BubbledLabel;
import Client.*;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    Model model = new Model();
    @FXML private TextArea messageBox;
    @FXML private Label usernameLabel;
    @FXML private Label onlineCountLabel;
    @FXML private ListView userList;
    @FXML ListView chatPane;
    @FXML ListView statusList;
    @FXML BorderPane borderPane;
    @FXML ComboBox statusComboBox;
    @FXML TextField fanfare;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setUserList();
        setUsernameLabel("otorio");
        findlist();
        listenermenu();
    }

    public void listenermenu()
    {

        userList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener()  {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {

                userList.setCellFactory(lv -> {
                    ListCell<String> cell = new ListCell<>();
                    ContextMenu delmenu = new ContextMenu();
                    MenuItem addItem = new MenuItem();
                    addItem.textProperty().bind(Bindings.format("Добавить \"%s\"", cell.itemProperty()));
                    addItem.setOnAction(event -> {
                        String item = cell.getItem();
                        // code to edit item...
                    });
                    MenuItem deleteItem = new MenuItem();
                    deleteItem.textProperty().bind(Bindings.format("Удалить \"%s\"", cell.itemProperty()));
                    deleteItem.setOnAction(event -> {
                        Contact delcon = new Contact();
                        delcon.login = cell.getItem();
                        model.deleteContact(delcon);
                        userList.getItems().remove(cell.getItem());
                        // System.out.println(cell.getItem());
                        //del item
                    });
                    delmenu.getItems().add(deleteItem);
                    cell.textProperty().bind(cell.itemProperty());
                    cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                        if (isNowEmpty) {
                            cell.setContextMenu(null);
                        } else {
                            cell.setContextMenu(delmenu);
                        }
                    });
                    return cell ;
                });
                //System.out.println();
            }

        });

    }

    public void findlist()
    {
        model.regFindContactsListener(new GetListContactListener() {
            @Override
            public void handleEvent(ArrayList<Contact> contactArrayList) {
                if (contactArrayList.isEmpty())
                { ObservableList<String> nouser = FXCollections.observableArrayList("Пользователей не найдено");
                    userList.setItems(nouser); }
                else{
                ArrayList<String> log = new ArrayList<String>();
                for(int i=0;i<contactArrayList.size();i++)
                {
                    log.add(contactArrayList.get(i).login);
                }
                ObservableList<String> finuser = FXCollections.observableArrayList(log);
                userList.setItems(finuser);}
            }
        });
            fanfare.addEventFilter(KeyEvent.KEY_PRESSED, ke -> {
                if (ke.getCode().equals(KeyCode.ENTER) & !fanfare.getText().isEmpty()) {
                    System.out.println("пошел поиск");
                    Contact fin = new Contact();
                    fin.login = fanfare.getText();
                    model.findContacts(fin);
                    //ObservableList<String> finuser = FXCollections.observableArrayList("aa","22");
                    //userList.setItems(finuser);
                    ke.consume();
                    userList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener()  {
                        @Override
                        public void changed(ObservableValue observable, Object oldValue, Object newValue) {

                            userList.setCellFactory(lv -> {
                                ListCell<String> cell = new ListCell<>();
                                ContextMenu admenu = new ContextMenu();
                                MenuItem addItem = new MenuItem();
                                addItem.textProperty().bind(Bindings.format("Добавить \"%s\"", cell.itemProperty()));
                                addItem.setOnAction(event -> {
                                    Contact adcon = new Contact();
                                    adcon.login = cell.getItem();
                                    model.addContact(adcon);
                                });
                                admenu.getItems().add(addItem);
                                cell.textProperty().bind(cell.itemProperty());
                                cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                                    if (isNowEmpty) {
                                        cell.setContextMenu(null);
                                    } else {
                                        cell.setContextMenu(admenu);
                                    }
                                });
                                return cell ;
                            });
                            //System.out.println();
                        }

                    });
                }
            });

        fanfare.addEventFilter(KeyEvent.KEY_PRESSED, ke -> {
            if (ke.getCode().equals(KeyCode.BACK_SPACE) & fanfare.getText().isEmpty()) {
                setUserList();

            }
        });


    }


    public void sendButtonAction() throws IOException {
        Message msg = new Message();
        msg.contact = new Contact();
        msg.contact.name = usernameLabel.getText();
        msg.contact.name = "otorio";
        msg.text = messageBox.getText();
        if (!messageBox.getText().isEmpty()) {
            //посылка сообщ
            try{
                setValueToLabel(msg);
            }
            catch (Exception e){e.printStackTrace();}
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
                yourMessages.run();
               /* Thread t2 = new Thread(yourMessages);
                t2.setDaemon(true);
                t2.start();*/
            } else {
                othersMessages.run();
                /*Thread t = new Thread(othersMessages);
                t.setDaemon(true);
                t.start();*/
            }
        }catch (Exception ex){
            System.out.println("потоки сообщ");}
    };
    public void setUsernameLabel(String username) {
        try {
            usernameLabel.setText(username);

        }catch (Exception ex){System.out.println("err присвоение логина");}
    }
    public void setUserList() {
        ArrayList<String> userslog = new ArrayList<String>();
        model.regGetListContactListener(new GetListContactListener() {
            @Override
            public void handleEvent(ArrayList<Contact> contactArrayList) {
                for(int i=0;i<contactArrayList.size();i++)
                {
                    userslog.add(contactArrayList.get(i).login);
                }
            }
        });
        model.getListContact();
        Platform.runLater(() -> {
            userslog.add("Pony");userslog.add("Top");userslog.add("Nope");
            ObservableList<String> usersList = FXCollections.observableArrayList(userslog);
            userList.setItems(usersList);
            setOnlineLabel(String.valueOf(userslog.size()));
        });
    }
    public void setOnlineLabel(String usercount) {
        Platform.runLater(() -> onlineCountLabel.setText(usercount));
    }
}
