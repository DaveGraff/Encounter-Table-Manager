/*
Dave Graff 2018
 */
package encounter.tables;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author David
 */
public class EncounterTables extends Application {
    ArrayList<Monster> monsterDex; //import later
    ArrayList<Table> tableList;    //import later 
    VBox changing;
    Table selectedTable = null;
    
    @Override
    public void start(Stage primaryStage) {
        loadFromFile();
        changing = render();
        Button main = new Button("Main");
        main.setOnAction(e -> {
            changing.getChildren().clear();
            changing.getChildren().add(render());
        });
        Button tableView = new Button("Tables");
        tableView.setOnAction(e ->{
            TableView table = new TableView(tableList, changing, monsterDex);
            table.render();
        });
        Button monsterList = new Button("MonsterDex");
        monsterList.setOnAction(e -> {
            MonsterPage temp = new MonsterPage(monsterDex, changing);
            temp.render();
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
    
    /*
    Renders the main page
    */
    private VBox render(){
        ArrayList<Table> notEmpty = new ArrayList<>();
        tableList.forEach(e -> {
            if(!(e.getMonsterNum() < 1))
                notEmpty.add(e);
        });
        ComboBox<Table> pickTable = new ComboBox<>(FXCollections.observableList(notEmpty));
                
        Button roll = new Button("Roll!");
        TextArea result = new TextArea();
        roll.setOnAction(e -> {
            selectedTable = pickTable.getSelectionModel().getSelectedItem();
            if (selectedTable != null){
                Monster selected = selectedTable.executeRoll();
                result.setText(selected.rollMonster());
            }
        });
        VBox test = new VBox(pickTable, roll, result);
        return test;
    }
    
    /*
    Loads monsterDex and TableList from their
    respective files. Returns an empty ArrayList
    if nothing is found
    */
    private void loadFromFile(){
        FileInputStream fis;
        ObjectInputStream reader;
        try{
            fis = new FileInputStream(new File("Monster_List.txt"));
            reader = new ObjectInputStream(fis);
            
            monsterDex = (ArrayList<Monster>) reader.readObject();
            reader.close();
            fis.close();
        } catch(Exception e){//In case something goes wrong
            monsterDex = new ArrayList<>();
        }
        try{
            fis = new FileInputStream(new File("Tables.txt"));
            reader = new ObjectInputStream(fis);
            
            tableList = (ArrayList<Table>) reader.readObject();
            reader.close();
            fis.close();
        } catch(Exception e){
            tableList = new ArrayList<>();
        }
    }
}
