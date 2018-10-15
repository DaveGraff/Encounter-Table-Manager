/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encounter.tables;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *8
 * @author David
 */
public class Table implements Serializable{
    private String name = null;
    private Monster[] table;
    private int monsterNum;//number of monsters currently in the table 
    private ScrollPane tableView = new ScrollPane();
    private Monster selectedMonster = null;
    
    public Table(String n){
        name = n;
        table = new Monster[19];
        monsterNum = 0;
    }
    
    public Table(){
        table = new Monster[19];
        monsterNum = 0;
    }
    
    //Getters & setters
    public String getName(){
        return name;
    }
    
    public void setName(String n){
        name = n;
    }
    /*
    Returns the user expected index, returns null if
    out of bounds or space is empty
    */
    public Monster getMonster(int index){
        index = index - 2;
        return table[index];
    }
    
    //internal roll method
    private int roll(){
        Random rand = new Random();
        //Operator is exclusive
        return rand.nextInt(8) + rand.nextInt(12) + 2;
    }
    
    /*
    Returns a monster from the table, if there is one
    */
    public Monster executeRoll(){
        if(monsterNum > 0){
            Monster spooky = null;
            while(spooky == null){
                spooky = getMonster(roll());
            }
            return spooky;
        } else return null;
    }
    
    /*
    Adds monster to the specified input, replaces current choice 
    if there is one. Index is assumed in 2-20 format
    */
    public void addMonster(Monster input, int index){
        index = index - 2;
        if(index > -1 && index < 19)
            table[index] = input;
        monsterNum++;
    }
    
    /*
    Removes monster at the specified 2-20 index, will return null
    if out of bounds or unpopulated
    */
    public Monster removeMonster(int index){
        index = index - 2;
        if (index > 1 && index < 21){
            Monster temp = table[index];
            table[index] = null;
            if (temp != null)
                monsterNum--;
            return temp;
        }
        else return null;
    }

    /*
    JavaFX view of the table with options & slider *ooh*
    */
    public ScrollPane tableView(ArrayList<Monster> monsterList){
        //For each Monster: Index, Add, Remove, Edit, Make Unique
        VBox innerPane = new VBox();
        tableView.setContent(null);
        for(int i = 0; i < 19; i++){
            final int temp = i + 2;
            boolean isNull = false;
            if (table[i] == null)
                isNull = true;
            Label mName;
            if (isNull)
                mName = new Label("EMPTY");
            else
                mName = new Label(table[i].getName());
            mName.setText(mName.getText() + "\t");//Makes spacing nicer
            Label index = new Label(Integer.toString(temp) + '\t');
            Button add = new Button("Add");
            add.setOnAction(e -> {
                Monster t = monsterSelector(monsterList);
                addMonster(t, temp);
                tableView(monsterList);
            });
            HBox row = new HBox(index, mName, add);
            if (!isNull){
                Button remove = new Button("Remove");
                remove.setOnAction(e -> {
                    removeMonster(temp);
                    tableView(monsterList);
                });
                
                Button edit = new Button("Edit");
                edit.setOnAction(e -> {
                    Monster t = getMonster(temp);
                    t.editMonster();
                    tableView(monsterList);
                });
                row.getChildren().addAll(edit, remove);
            }
            //Implement make unique later?
            innerPane.getChildren().addAll(row);
        }
        
        tableView.setContent(innerPane);
        return tableView;
    }
    
    private Monster monsterSelector(ArrayList<Monster> monsterList){
        Stage newStage = new Stage();
        VBox main = new VBox();
        monsterList.stream().map((monster) -> {
            Button temp = new Button(monster.getName());
            temp.setOnAction(e -> {
                selectedMonster = monster;
                newStage.close();
            });
            return temp;
        }).forEachOrdered((temp) -> {
            main.getChildren().add(temp);
        });
        Scene scene = new Scene(main);
        scene.getStylesheets().add(this.getClass().getResource("NiceEncounter.css").toExternalForm());
        newStage.setScene(scene);
        newStage.showAndWait();
        return selectedMonster;
    }
}
