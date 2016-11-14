package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;
import javafx.geometry.*;
import javafx.scene.text.*;
import javafx.scene.control.*;
import javafx.scene.paint.*;
import javafx.scene.layout.HBox;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.awt.*;

public class Main extends Application {
    private static Stage primaryStageObj;
    public void SecondWind(Stage stageWindow) throws Exception {

        Pane rootSec = (Pane) FXMLLoader.load(getClass().getResource("/sample/chat.fxml"));
        stageWindow.setTitle("Чат");
        Scene scene = new Scene(rootSec, 600, 400);
        stageWindow.setScene(scene);
        stageWindow.show();
        chatObj(rootSec);
    }

    public void RegWind(Stage stageWindow) throws Exception {

        Pane rootReg = (Pane) FXMLLoader.load(getClass().getResource("sample.fxml"));
        stageWindow.initStyle(StageStyle.TRANSPARENT);
        stageWindow.setTitle("Регистрация");
        Scene scene = new Scene(rootReg, 300, 240);
        stageWindow.setScene(scene);
        stageWindow.show();
        regObj(rootReg);
    }

    public void chatObj(Pane root)
    {


    }

    public void regObj(Pane root)
    {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        TextField userTextField = new TextField();

        userTextField.setPromptText("Введите уникальный логин");
        userTextField.setFocusTraversable(false);
        grid.add(userTextField, 0, 1);
        TextField nameTextField = new TextField();
        nameTextField.setPromptText("Введите свое имя");
        nameTextField.setFocusTraversable(false);
        grid.add(nameTextField, 0, 2);
        PasswordField  passTextField = new  PasswordField();
        passTextField.setPromptText("Введите пароль");
        passTextField.setFocusTraversable(false);
        grid.add(passTextField, 0, 3);
        PasswordField  passrepTextField = new  PasswordField();
        passrepTextField.setPromptText("Повторите пароль");
        passrepTextField.setFocusTraversable(false);
        grid.add(passrepTextField, 0, 4);
        userTextField.setPrefSize(200,nameTextField.getPrefHeight());

        Button btnReg = new Button("Регистрация");
        Button btnExit = new Button("Закрыть");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btnReg);
        hbBtn.getChildren().add(btnExit);
        grid.add(hbBtn, 0, 5);
        btnExit.setOnAction(new EventHandler<ActionEvent>() {

            @Override
             public void handle(ActionEvent e) {
                 Stage stage = (Stage) btnExit.getScene().getWindow();
                 stage.close();
             } });
        btnReg.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                //Послать клиенту данные о юзере
            } });

        root.getChildren().add(grid);
    }

    public void authObj(Pane root)
    {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("User Name:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);
        Button btnReg = new Button("Регистрация");
        grid.add(btnReg,0,4);
        Button btnAuth = new Button("Sign in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btnAuth);
        grid.add(hbBtn, 1, 4);
        btnAuth.setTooltip(new Tooltip("Введите:"+"\n"+"admin "+"\n"+"admin"));

        final Label actiontarget = new Label();
        grid.add(actiontarget, 1,5);

        btnReg.setOnAction(new EventHandler<ActionEvent>() {

                                @Override
                                public void handle(ActionEvent e) {
                                    try{Stage stageWindowR = new Stage();
                                        RegWind(stageWindowR);
                                    }
                                    catch(Exception ex){}}

                            });

        btnAuth.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {actiontarget.setText("");
               // if ((pwBox.getText().equals("admin")) && (userTextField.getText().equals("admin"))) {
                    try{Stage stageWindow = new Stage();
                        SecondWind(stageWindow);
                    }
                    catch(Exception ex){System.out.println("Произошло ещё какое-то исключение"); }
                 //} else{
                    actiontarget.setText("Your data is incorrect!");
                    actiontarget.setTextFill(Color.rgb(210, 39, 30));
               // }
                pwBox.clear();
            }});
        root.getChildren().add(grid);
    }
    @Override
    public void start(Stage primaryStage) throws Exception{

        Pane root = (Pane) FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Secure");
        authObj(root);
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                System.out.println("Stage is closing");
            }
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
    public static Stage getPrimaryStage() {
        return primaryStageObj;
    }
}



