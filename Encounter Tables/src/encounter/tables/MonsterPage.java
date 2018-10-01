/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encounter.tables;

import java.util.ArrayList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author david
 */
public class MonsterPage {
    ArrayList<Monster> monsterList;
    public MonsterPage(ArrayList<Monster> m){
        monsterList = m;
    }
    
    public VBox render(){
        final VBox thisScreen = new VBox();
        monsterList.forEach(e -> {
            Label name = new Label(e.getName());
            Button edit = new Button("Edit");
            edit.setOnAction(x -> {
                editMonster(e);
                render();
            });
            Button remove = new Button("Remove");
            remove.setOnAction(x ->{
                removeMonster(e);
                render();
            });
            HBox row = new HBox(name, edit, remove);
            thisScreen.getChildren().add(row);
        });
        Button add = new Button("Add Monster");
        ToolBar toolbar = new ToolBar(add);
        thisScreen.getChildren().add(toolbar);
        return thisScreen;
    }
    
    /*
    Edits the stats of a given monster, saves after
    */
    public void editMonster(Monster m){
        
    }
    
    /*
    Removes a monster from the monster list.
    Checks for certainty & updates .ser file
    */
    public Monster removeMonster(Monster m){
        
        return m;
    }
    
    /*
    
    */
    public void addMonster(Monster m){
        
    }
}
