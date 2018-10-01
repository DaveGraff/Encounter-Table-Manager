/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encounter.tables;

import java.util.ArrayList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author david
 */
public class TableView {
    private VBox vbox;
    private ArrayList<Table> tables;
    public TableView(ArrayList<Table> tables){
        this.tables = tables;
    }
    
    /*
    Displays TableView page
    */
    public VBox render(){
        VBox innerpane = new VBox();
        for(Table table: tables){
            Label name = new Label(table.getName());
            Button edit = new Button("Edit");
            edit.setOnAction(e -> edit(table));
            Button remove = new Button("Remove");
            remove.setOnAction(e -> remove(table));
            HBox row = new HBox(name, edit, remove);
            innerpane.getChildren().add(row);
        }
        
        Button addTable = new Button("Add Table");
        addTable.setOnAction(e -> add());
        ToolBar toolbar = new ToolBar(addTable);
        VBox newVBox = new VBox(innerpane, toolbar);
        return newVBox;
    }
    
    /*
    Bring up separate edit window
    */
    private void edit(Table table){
        
        ScrollPane pane = table.tableView();
        render();
    }
    
    /*
    Removes shown table
    */
    private void remove(Table table){
        tables.remove(table);
        render();
    }
    
    /*
    Creates a new table & puts in settings w/ edit
    */
    private void add(){
        Stage newStage = new Stage();
        TextField name = new TextField("Enter Name");
        Button cancel = new Button("Cancel");cancel.setCancelButton(true);
        Button continueButton = new Button("Continue");continueButton.setDefaultButton(true);
        VBox root = new VBox(name, new HBox(cancel, continueButton));
        cancel.setOnAction(e -> newStage.close());
        continueButton.setOnAction(e -> {
            Table temp = new Table(name.getText());
            edit(temp);
            newStage.close();
        });
        Scene newScene = new Scene(root);
        newScene.getStylesheets().add(this.getClass().getResource("NiceEncounter.css").toExternalForm());
        newStage.setScene(newScene);
        newStage.showAndWait();
        render();
    }
}
