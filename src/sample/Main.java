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
import java.awt.Desktop;
import java.net.URI;
import java.net.URL;
import java.net.URISyntaxException;

public class Main extends Application {

    public static void openWebpage(URI uri) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
            } catch (Exception e) {
                e.printStackTrace();
                //far
            }
        }
    }

    public static void openWebpage(URL url) {
        try {
            openWebpage(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void FXMLDocumentController(Stage stageWindow) throws Exception {

        Pane root = (Pane) FXMLLoader.load(getClass().getResource("sample.fxml"));
        stageWindow.setTitle("Информация");
        Scene scene = new Scene(root, 570, 200);
        stageWindow.setScene(scene);
        stageWindow.show();
        Text sect = new Text("HelloW!");
        sect.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        root.getChildren().add(sect);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Pane root = (Pane) FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Secure");

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

        Button btn = new Button("Sign in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);

        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setText("Sign in button pressed");
                try{Stage stageWindow = new Stage();
                    FXMLDocumentController(stageWindow);
                }
                catch(Exception ex){}

                }
        });
        root.getChildren().add(grid);
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
