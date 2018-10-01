/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encounter.tables;

import java.util.Random;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *8
 * @author David
 */
public class Table {
    private String name;
    private Monster[] table;
    private int monsterNum;//number of monsters currently in the table 
    
    public Table(String n){
        name = n;
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
        if(index > 1 && index < 21)
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
    public ScrollPane tableView(){
        //For each Monster: Index, Add, Remove, Edit, Make Unique
        VBox innerPane = new VBox();
        for(int i = 0; i < 19; i++){
            final int temp = i + 2;
            Label index = new Label(Integer.toString(i));
            Button add = new Button("Add");
            add.setOnAction(e -> {
                Monster t = new Monster();
                t.editMonster();
                addMonster(t, temp-2);
            });
            Button remove = new Button("Remove");
            remove.setOnAction(e -> removeMonster(temp-2));
            Button edit = new Button("Edit");
            edit.setOnAction(e -> {
                Monster t = getMonster(temp);
                t.editMonster();
            });
            //Implement make unique later?
            HBox row = new HBox(index, add, remove, edit);
            innerPane.getChildren().add(row);
        }
        
        ScrollPane viewer = new ScrollPane(innerPane);        
        return viewer;
    }
}
