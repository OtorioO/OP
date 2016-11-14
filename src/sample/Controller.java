package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Controller {


    @FXML Label can; //label name
    @FXML Button textbtn;
    @FXML TextArea textA;
    @FXML VBox boxT;
    String name= new String("Я");
    @FXML ScrollPane scrollT;
    @FXML TabPane tabdial;

    @FXML  private void setValueToLabel(){
       // Label ante = new Label();
        boxT.getChildren().addAll(new Label(name+": " + textA.getText()));
    };
    @FXML private void adddial()
    {
        VBox tb = new VBox();
        ScrollPane ts = new ScrollPane(tb);
        Tab t = new Tab("Диалог",ts);
        tabdial.getTabs().add(t);
    }

}
