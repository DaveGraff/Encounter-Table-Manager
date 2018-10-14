/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encounter.tables;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
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
    private ArrayList<Table> tables;
    private VBox thisPage;
    
    public TableView(ArrayList<Table> tables, VBox page){
        this.tables = tables;
        thisPage = page;
    }
    
    /*
    Displays TableView page
    */
    public VBox render(){
        thisPage.getChildren().clear();
        //VBox innerpane = new VBox();
        for(Table table: tables){
            Label name = new Label(table.getName());
            Button edit = new Button("Edit");
            edit.setOnAction(e -> edit(table));
            Button remove = new Button("Remove");
            remove.setOnAction(e -> remove(table));
            HBox row = new HBox(name, edit, remove);
            thisPage.getChildren().add(row);
        }
        
        Button addTable = new Button("Add Table");
        addTable.setOnAction(e -> add());
        ToolBar toolbar = new ToolBar(addTable);
        
        thisPage.getChildren().add(toolbar);
        return thisPage;
    }
    
    /*
    Bring up separate edit window
    */
    private void edit(Table table){
        Stage viewStage = new Stage();
        ScrollPane pane = table.tableView();
        pane.setMaxHeight(500);
        pane.setMinWidth(350);
        
        Button save = new Button("Save");save.setDefaultButton(true);
        save.setOnAction(e ->{
            save();
            render();
            viewStage.close();
        });
        Button cancel = new Button("Cancel");cancel.setCancelButton(true);
        cancel.setOnAction(e -> viewStage.close());
        ToolBar toolbar = new ToolBar(save, cancel);
        VBox container = new VBox(pane, toolbar);
        
        Scene scene = new Scene(container);
        scene.getStylesheets().add(this.getClass().getResource("NiceEncounter.css").toExternalForm());
        
        viewStage.setScene(scene);
        viewStage.showAndWait();
        render();
    }
    
    /*
    Removes shown table
    */
    private void remove(Table table){
        tables.remove(table);
        render();
        save();
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
            tables.add(temp);
            save();
            newStage.close();
        });
        Scene newScene = new Scene(root);
        newScene.getStylesheets().add(this.getClass().getResource("NiceEncounter.css").toExternalForm());
        newStage.setScene(newScene);
        newStage.showAndWait();
        render();
    }
    
    private void save(){
        try{
            FileOutputStream fos = new FileOutputStream(new File("Tables.txt"));
            ObjectOutputStream writer = new ObjectOutputStream(fos);
            
            writer.writeObject(tables);
            
            writer.close();
            fos.close();
        } catch(Exception e){}//Should never happen
    }
}
