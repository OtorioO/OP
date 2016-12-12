package sample;

import Bubble.BubbleSpec;
import Bubble.BubbledLabel;
import Client.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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

import javax.swing.text.DefaultCaret;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

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
        model.setMyStatus(1, new UniversalListener() {
            @Override
            public void handlerEvent(int typeResponse) {

            }
        });
        setUserList();
        setUsernameLabel("Misha");
        listenermenu();
        findlist();

    }

    public void listenermenu()
    {
        model.regGetListDialogListener(new GetListDialogListener() {
            @Override
            public void handlerEvent(ArrayList<Message> messageArrayList) {
                if(messageArrayList != null)
                {
                for(int i=0;i<messageArrayList.size();i++)
                {
                    BubbledLabel bl6 = new BubbledLabel();HBox x = new HBox();
                    if (!messageArrayList.get(i).contact.login.equals(usernameLabel.getText())) {
                    bl6.setText(messageArrayList.get(i).contact.login + ": " + messageArrayList.get(i).text+"\n"
                            +messageArrayList.get(i).time );
                    bl6.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
                        bl6.setBubbleSpec(BubbleSpec.FACE_LEFT_CENTER);
                    }
                    else {
                        bl6.setText( messageArrayList.get(i).text+"\n"
                                +messageArrayList.get(i).time );
                        bl6.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN,
                                null, null)));
                        x.setAlignment(Pos.TOP_RIGHT);
                        bl6.setBubbleSpec(BubbleSpec.FACE_RIGHT_CENTER);
                    }

                    x.getChildren().addAll(bl6);
                    Platform.runLater(() -> {
                    chatPane.getItems().add(x);});

                }
            }}
         });

        userList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {

                userList.setCellFactory(lv -> {
                    ListCell<String> cell = new ListCell<>();
                    ContextMenu delmenu = new ContextMenu();
                    MenuItem deleteItem = new MenuItem();
                    String nam = newValue.toString();
                    String res = nam.substring(0,nam.indexOf('('));
                    deleteItem.textProperty().bind(Bindings.format("Удалить \"%s\"", res));
                    deleteItem.setOnAction(event -> {
                        Contact delcon = new Contact();
                        delcon.login = cell.getItem();
                        model.deleteContact(delcon);
                        userList.getItems().remove(cell.getItem());
                        chatPane.getItems().clear();

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
                    chatPane.getItems().clear();


                    return cell;
                });

                String nam = newValue.toString();
                String res = nam.substring(0,nam.indexOf('('));
                Contact contd = new Contact();
                contd.login = res;
                model.getListDialog(contd);
                Timer timerline = new Timer();
                TimerTask task = new TimerTask() {
                    public void run() {
                        ArrayList<Message> tempmess = new ArrayList<Message>();
                        GetListDialogListener ff = new GetListDialogListener() {
                            @Override
                            public void handlerEvent(ArrayList<Message> messageArrayList) {
                                if (messageArrayList != null) {
                                    for (int i = 0; i < messageArrayList.size(); i++) {
                                        tempmess.addAll(messageArrayList);
                                    }

                                    for (int i = 0; i < tempmess.size(); i++) {
                                        BubbledLabel bl6 = new BubbledLabel();
                                        HBox x = new HBox();
                                            bl6.setText(res + ": " + tempmess.get(i).text+"\n"
                                                    +messageArrayList.get(i).time );
                                            bl6.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
                                            bl6.setBubbleSpec(BubbleSpec.FACE_LEFT_CENTER);
                                            x.getChildren().addAll(bl6);
                                            chatPane.getItems().add(x);
                                    }

                                }
                            }
                        };
                        model.getUpdateDialog(contd, ff);

                    }
                };

                Platform.runLater(() -> {
                    timerline.schedule(task, 5000, 5000);
                });
            }
        });}




    public void findlist()
    {
        model.regFindContactsListener(new GetListContactListener() {
            @Override
            public void handleEvent(ArrayList<Contact> contactArrayList) {
                Platform.runLater(() -> { userList.getItems().clear();});
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

                    Platform.runLater(() -> { userList.setItems(finuser);});
                }
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
                                String nam = newValue.toString();
                                String res = nam.substring(0,nam.indexOf('('));
                                addItem.textProperty().bind(Bindings.format("Добавить \"%s\"", res));
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
                userList.getItems().clear();
                setUserList();
            }
        });


    }



    public void sendButtonAction() throws IOException {
        Message msg = new Message();
        msg.contact = new Contact();
        String nam = userList.getSelectionModel().getSelectedItem().toString();
        String res = nam.substring(0,nam.indexOf('('));
        msg.contact.login = res;
        msg.text = messageBox.getText();
        Date date = new Date(System.currentTimeMillis());
        msg.date = new SimpleDateFormat("yyyy-MM-dd").format(date);
        msg.time = new SimpleDateFormat("HH:mm:ss").format(date);
        if (!messageBox.getText().isEmpty()) {
            model.sendMessage(msg);

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
                bl6.setText(msg.contact.name + ": " + msg.text+"\n"
                        +msg.time );
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
                bl6.setText(msg.text+"\n"
                        +msg.time );
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
        yourMessages.setOnSucceeded(event ->
                chatPane.getItems().add(yourMessages.getValue()));
        try {
            if (msg.contact.login.equals(usernameLabel.getText())) {
                othersMessages.run();
               /* Thread t2 = new Thread(yourMessages);
                t2.setDaemon(true);
                t2.start();*/
            } else {
                yourMessages.run();

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
        final int[] counton = {0};
        model.regGetListContactListener(new GetListContactListener() {
            @Override
            public void handleEvent(ArrayList<Contact> contactArrayList) {
                if(contactArrayList != null)
                {
                for(int i=0;i<contactArrayList.size();i++)
                {
                    userslog.add(contactArrayList.get(i).login +"("+ contactArrayList.get(i).countOfMes+")           "+
                            (contactArrayList.get(i).status ==1 ? "Online": "Offline"));
                    if(contactArrayList.get(i).status ==1) counton[0]++;
                }}
                else {System.out.println("Список пустой");}
            }
        });
        model.getListContact();

        Platform.runLater(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {System.out.println("Таймер");
                e.printStackTrace();
            }
            //userslog.add("Pony");userslog.add("Top");userslog.add("Nope");
            ObservableList<String> usersList = FXCollections.observableArrayList(userslog);
            userList.setItems(usersList);
            setOnlineLabel(String.valueOf(counton[0]));
        });
    }
    public void setOnlineLabel(String usercount) {
        Platform.runLater(() -> onlineCountLabel.setText(usercount));
    }


}
