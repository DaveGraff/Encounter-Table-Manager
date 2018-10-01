/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encounter.tables;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author David
 */
public class EncounterTables extends Application {
    ArrayList<Monster> monsterDex = new ArrayList<>(); //import later
    ArrayList<Table> tableList = new ArrayList<>();    //import later 
    VBox changing = render();
    
    @Override
    public void start(Stage primaryStage) {
        Button main = new Button("Main");
        main.setOnAction(e -> {
            changing.getChildren().clear();
            changing.getChildren().add(render());
        });
        Button tableView = new Button("Tables");
        tableView.setOnAction(e ->{
            TableView table = new TableView(tableList);
            changing.getChildren().clear();
            changing.getChildren().add(table.render());
        });
        Button monsterList = new Button("MonsterDex");
        monsterList.setOnAction(e -> {
            MonsterPage temp = new MonsterPage(monsterDex);
            changing.getChildren().clear();
            changing.getChildren().add(temp.render());
        });
        ToolBar toolbar = new ToolBar(main, tableView, monsterList);
        
        
        VBox root = new VBox(toolbar, changing);
        Scene scene = new Scene(root, 750, 600);
        scene.getStylesheets().add(this.getClass().getResource("NiceEncounter.css").toExternalForm());
        primaryStage.setTitle("Encounter Table Manager");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    private VBox render(){
        Button roll = new Button("Roll!");
        TextArea result = new TextArea();
        result.setDisable(true);
        VBox test = new VBox(roll, result);
        return test;
    }
}
