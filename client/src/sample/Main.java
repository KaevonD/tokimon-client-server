package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * This class creates the visual interface that
 * the user interacts with. Makes all the buttons
 * and manages the
 */
public class Main extends Application {

    public GridPane gridpane = new GridPane();
    ArrayList<Button> tokiButtons = new ArrayList<Button>();
    //making buttons
    Button add = new Button("Add Tokimon");
    Button removeToki = new Button ("Remove Tokimon");
    Button changeStats = new Button("Change Stats");
    //making labels
    Label textBox = new Label("""
                Add a new tokimon by entering all the stats into the\s
                boxes below and press add tokimon. Make sure that you\s
                have all the information filled in before you press it.\s
                The tokimon are displayed to be bigger the larger their\s
                strength is. To delete a tokimon click on one then press\s
                the remove tokimon button. To change a tokimon's stats,\s
                click on a tokimon, then change its stats in the boxes below\s
                then click the change stats button. To view a tokimon's stats,\s
                click on them.""");
    Label idText = new Label("id:");
    Label idText2 = new Label("");
    Label nameText = new Label("Name:");
    Label weightText = new Label("Weight:");
    Label heightText = new Label("Height:");
    Label abilityText = new Label("Ability:");
    Label strengthText = new Label("Strength:");
    Label colorText = new Label("Color:");
    //making textFields
    TextField nameField = new TextField();
    TextField weightField = new TextField();
    TextField heightField = new TextField();
    TextField abilityField = new TextField();
    TextField strengthField = new TextField();
    TextField colorField = new TextField();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Tokidex");


        //setting field sizes
        nameField.setMinSize(100,10);
        weightField.setMinSize(100,10);
        heightField.setMinSize(100,10);
        abilityField.setMinSize(100,10);
        strengthField.setMinSize(100,10);
        colorField.setMinSize(100,10);
        //setting label sizes
        idText.setMinSize(100,50);
        idText2.setMinSize(100,10);
        nameText.setMinSize(100,50);
        weightText.setMinSize(100,50);
        heightText.setMinSize(100,50);
        abilityText.setMinSize(100,50);
        strengthText.setMinSize(100,50);
        colorText.setMinSize(100,50);
        textBox.setMinSize(100,150);
        //setting button sizes
        add.setMinSize(100,50);
        removeToki.setMinSize(100,50);
        changeStats.setMinSize(100,50);
        //adding buttons
        gridpane.add(add,10,30);
        gridpane.add(removeToki,20,30);
        gridpane.add(changeStats,30,30);
        //adding textboxes
        gridpane.add(textBox,40,30);
        gridpane.add(idText2,20,40);
        gridpane.add(idText,10,40);
        gridpane.add(nameText, 10, 45);
        gridpane.add(weightText,10,50);
        gridpane.add(heightText,10,55);
        gridpane.add(abilityText,10,60);
        gridpane.add(strengthText,10,65);
        gridpane.add(colorText,10,70);
        //adding fields
        gridpane.add(nameField, 20, 45);
        gridpane.add(weightField,20,50);
        gridpane.add(heightField,20,55);
        gridpane.add(abilityField,20,60);
        gridpane.add(strengthField,20,65);
        gridpane.add(colorField,20,70);

        gridpane.setHgap(5);
        gridpane.setVgap(4);
        gridpane.setAlignment(Pos.TOP_LEFT);
        gridpane.setPadding(new Insets(10));

        try {
            ServerInteraction.returnAllTokimon();
        }catch(Exception e){
            e.printStackTrace();
        }

        Scene scene = new Scene(gridpane,1200,1000);
        primaryStage.setScene(scene);
        primaryStage.show();
        refreshDisplay();
        primaryStage.setResizable(false);

        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    ServerInteraction.addToki(nameField.getText(),Integer.parseInt(weightField.getText()),Integer.parseInt(heightField.getText()),Integer.parseInt(strengthField.getText()),abilityField.getText(),colorField.getText());
                    ServerInteraction.returnAllTokimon();
                    clearText();
                    refreshDisplay();

                }catch(Exception e){
                    e.printStackTrace();
                }

            }
        });

        changeStats.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try{
                    ServerInteraction.changeStats(nameField.getText(), weightField.getText(), heightField.getText(), strengthField.getText(), abilityField.getText(), colorField.getText());
                    ServerInteraction.returnAllTokimon();
                    clearText();
                    refreshDisplay();
                } catch(IOException e){
                    e.printStackTrace();
                }
            }
        });

        removeToki.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    ServerInteraction.deleteToki();
                    ServerInteraction.returnAllTokimon();
                    clearText();
                    refreshDisplay();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void refreshDisplay() {
        for(Button curr : tokiButtons) {
            gridpane.getChildren().remove(curr);
        }
        tokiButtons.clear();

        int inc = 0;

        for(Object obj : ServerInteraction.tokimonList) {
            JSONObject temp = (JSONObject)obj;
            tokiButtons.add(new Button((String)temp.get("name")));
            tokiButtons.get(inc).setMinSize(100,(int)Math.toIntExact((long)(temp.get("strength")))*2);
            tokiButtons.get(inc).setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    try {
                        JSONObject temp2 = ServerInteraction.getOneTokimon((int)Math.toIntExact((long)(temp.get("id"))));
                        idText2.setText(String.valueOf(temp2.get("id")));
                        weightField.setText(String.valueOf(temp2.get("weight")));
                        heightField.setText(String.valueOf(temp2.get("height")));
                        nameField.setText((String)temp2.get("name"));
                        abilityField.setText((String)temp2.get("ability"));
                        strengthField.setText(String.valueOf(temp2.get("strength")));
                        colorField.setText((String)temp2.get("color"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            gridpane.add(tokiButtons.get(inc),(inc + 1)*5 + 5,10);
            inc++;
        }

    }

    public void clearText() {
        idText2.setText("");
        weightField.setText("");
        heightField.setText("");
        nameField.setText("");
        abilityField.setText("");
        strengthField.setText("");
        colorField.setText("");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
