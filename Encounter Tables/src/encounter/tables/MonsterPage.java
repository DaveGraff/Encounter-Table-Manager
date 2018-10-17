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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author david
 */
public class MonsterPage {
    private ArrayList<Monster> monsterList;
    private VBox thisPage;
    
    public MonsterPage(ArrayList<Monster> m, VBox changeableSpace){
        monsterList = m;
        thisPage = changeableSpace;
    }
    
    public VBox render(){
        thisPage.getChildren().clear();
        VBox monsters = new VBox();
        monsterList.forEach(e -> {
            Label name = new Label(e.getName());
            name.setText(name.getText() + "\t\t\t");
            name.setMaxWidth(100);
            Button edit = new Button("Edit");
            edit.setOnAction(x -> {
                editMonster(e);
                render();
                save();
            });
            Button remove = new Button("Remove");
            remove.setOnAction(x ->{
                removeMonster(e);
                render();
            });
            HBox row = new HBox(name, edit, remove);
            monsters.getChildren().add(row);
        });
        Button add = new Button("Add Monster");
        add.setOnAction(e -> {
            addMonster();
            render();});
        ToolBar toolbar = new ToolBar(add);
        thisPage.getChildren().addAll(new ScrollPane(monsters), toolbar);
        return thisPage;
    }
    
    /*
    Edits the stats of a given monster, saves after
    */
    public void editMonster(Monster m){
        m.editMonster();
    }
    
    /*
    Removes a monster from the monster list.
    Checks for certainty & updates .ser file
    */
    public Monster removeMonster(Monster m){
        monsterList.remove(m);
        save();
        return m;
    }
    
    /*
    
    */
    public void addMonster(){
        Monster temp = new Monster();
        temp.editMonster();
        monsterList.add(temp);
        save();
    }
    
    private void save(){
        try{
            FileOutputStream fos = new FileOutputStream(new File("Monster_List.txt"));
            ObjectOutputStream writer = new ObjectOutputStream(fos);
            
            writer.writeObject(monsterList);
            
            writer.close();
            fos.close();
        } catch(Exception e){}//Should never happen
    }
}
