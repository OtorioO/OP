package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;
import javafx.geometry.*;
import javafx.scene.text.*;
import javafx.scene.control.*;
import javafx.scene.paint.*;
import javafx.scene.layout.HBox;

public class Main extends Application {

    public void FXMLDocumentController(Stage stageWindow) throws Exception {

        Pane root = (Pane) FXMLLoader.load(getClass().getResource("sample.fxml"));
        stageWindow.setTitle("Информация");
        Scene scene = new Scene(root, 570, 200);
        stageWindow.setScene(scene);
        stageWindow.show();
        Text sect = new Text("HelloWorld!");
        sect.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        root.getChildren().add(sect);
    }
    public void addObj(Pane root)
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
        Label tip = new Label("Наведите->");
        grid.add(tip,0,4);
        Button btn = new Button("Sign in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);
        btn.setTooltip(new Tooltip("Введите:"+"\n"+"admin "+"\n"+"admin"));

        final Label actiontarget = new Label();
        grid.add(actiontarget, 1,5);

        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {actiontarget.setText("");
                if ((pwBox.getText().equals("admin")) && (userTextField.getText().equals("admin"))) {
                    actiontarget.setText("Your password confirmed");
                    actiontarget.setTextFill(Color.rgb(21, 117, 84));
                    try{Stage stageWindow = new Stage();
                        FXMLDocumentController(stageWindow);
                    }
                    catch(Exception ex){}}
                else
                {
                    actiontarget.setText("Your data is incorrect!");
                    actiontarget.setTextFill(Color.rgb(210, 39, 30));
                }
                pwBox.clear();
            }});
        root.getChildren().add(grid);
    }
    @Override
    public void start(Stage primaryStage) throws Exception{
        Pane root = (Pane) FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Secure");
        addObj(root);
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
        // Main f = new Main();
        // f.start(new Stage());
    }
}
