/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encounter.tables;

import java.io.Serializable;
import java.util.Random;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author David
 */
public class Monster implements Serializable{
    private String name;
    private int specialHP;          //-1 by default, for monsters w/ fixed HP values
    private int hpMod;              //added after rolls, default 0
    private int hpDice;             //Dice type, default 8
    private int hpNum;              // # of dice to be rolled
    private int numDice;            //Dice type for number appearing
    private int numDiceRoll;        //Dice # for appearing
    private int numMod;             //Mod for rolls
    private String description;     //Extra info: ac, TT, appearance
    
    public Monster(String name, int numDice, int numDiceRoll, int numMod, String description){
        this.name = name;
        this.numDice = numDice;
        this.numDiceRoll = numDiceRoll;
        this.numMod = numMod;
        this.description = description;
    }
    
    //For Opening a stats screen
    public Monster(){
        name = "name";
        specialHP = -1;
        hpMod = 0;
        hpDice = 8;
        hpNum = 3;
        numDice = 4;
        numDiceRoll = 7;
        numMod = 0;
        description = "description";
    }
    
    //Getters & Setters
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public int getSpecialHP() {return specialHP;}
    public void setSpecialHP(int specialHP) {this.specialHP = specialHP;}
    public int getHpMod() {return hpMod;}
    public void setHpMod(int hpMod) {this.hpMod = hpMod;}
    public int getHpDice() {return hpDice;}
    public void setHpDice(int hpDice) {this.hpDice = hpDice;}
    public int getHpNum() {return hpNum;}
    public void setHpNum(int hpNum) {this.hpNum = hpNum;}
    public int getNumDice() {return numDice;}
    public void setNumDice(int numDice) {this.numDice = numDice;}
    public int getNumDiceRoll() {return numDiceRoll;}
    public void setNumDiceRoll(int numDiceRoll) {this.numDiceRoll = numDiceRoll;}
    public int getNumMod() {return numMod;}
    public void setNumMod(int numMod) {this.numMod = numMod;}
    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}
    
    /*
    Internal roll method
    */
    private int roll(int totalRolls, int rollSize){
        Random rand = new Random();
        int total = 0;
        for(int i = 0; i < totalRolls; i++)
            total += rand.nextInt(rollSize) + 1;
        return total;
    }
    
    /*
    Internal roll for hp & appearing
    */
    private int numSet(int dice, int diceType, int diceMod){
        return roll(dice, diceType) + diceMod;
    }
    
    /*
    Public return for any given roll of a monster
    */
    public String rollMonster(){
        String data = "".concat(name + "\n");
        int appearing = numSet(numDice, numDiceRoll, numMod);
        String hp = "HP: ";
        for(int i = 0; i < appearing; i++){
            if(specialHP == -1)
                hp = hp.concat(" " + Integer.toString(numSet(hpNum, hpDice, hpMod)));
            else hp = Integer.toString(specialHP);
        }
        data = data.concat(hp + "\n");
        data = data.concat(description);
        return data;
    }
    
    /*
    Creates screen to create and edit monsters
    A new monster should be created first, and then edited
    */
    public void editMonster(){
        Stage editStage = new Stage();
        editStage.setTitle("Edit Monster: " + name);
        TextField nameField = new TextField(name); nameField.setMaxWidth(200);
        TextField specHPField = new TextField(Integer.toString(specialHP));
        TextField hpModField = new TextField(Integer.toString(hpMod));
        TextField hpDiceField = new TextField(Integer.toString(hpDice));
        TextField hpNumField = new TextField(Integer.toString(hpNum));
        TextField numDiceField = new TextField(Integer.toString(numDice));
        TextField numDiceRollField = new TextField(Integer.toString(numDiceRoll));
        TextField numModField = new TextField(Integer.toString(numMod));
        TextArea descriptionField = new TextArea(description);
        
        VBox fields = new VBox(nameField, new HBox(new Label("Special HP\t\t\t"), specHPField));
        fields.getChildren().add(new HBox(new Label("HP Die Type\t\t\t"), hpDiceField));
        fields.getChildren().add(new HBox(new Label("# of Die to be Rolled\t"), hpNumField));
        fields.getChildren().add(new HBox(new Label("HP Modifier\t\t\t"), hpModField));
        fields.getChildren().add(new HBox(new Label("# Appearing Die Type\t"), numDiceField));
        fields.getChildren().add(new HBox(new Label("# of Die to be Rolled\t"), numDiceRollField));
        fields.getChildren().add(new HBox(new Label("# Appearing Modifier\t"), numModField));
        fields.getChildren().add(descriptionField);
        
        Button cancel = new Button("Cancel");cancel.setCancelButton(true);
        cancel.setOnAction(e -> editStage.close());
        Button save = new Button("Save");save.setDefaultButton(true);
        save.setOnAction(e -> {
            try{
                specialHP = Integer.parseInt(specHPField.getText());
                hpDice = Integer.parseInt(hpDiceField.getText());
                hpNum = Integer.parseInt(hpNumField.getText());
                hpMod = Integer.parseInt(hpModField.getText());
                numDice = Integer.parseInt(numDiceField.getText());
                numDiceRoll = Integer.parseInt(numDiceRollField.getText());
                numMod = Integer.parseInt(numModField.getText());
                name = nameField.getText();
                description = descriptionField.getText();
                editStage.close();
            } catch(NumberFormatException x){
                Button ok = new Button("OK");ok.setDefaultButton(true);
                Stage warning = new Stage(); warning.setTitle("Error");
                Scene scene = new Scene(new VBox(new Label("Error: # Appearing & HP fields only accept integers"), ok));
                scene.getStylesheets().add(this.getClass().getResource("NiceEncounter.css").toExternalForm());
                warning.setScene(scene);
                ok.setOnAction(z -> warning.close());
                warning.showAndWait();
            }
        });
        ToolBar toolbar = new ToolBar(save, cancel);
        
        VBox container = new VBox(fields, toolbar);
        
        Scene scene = new Scene(container);
        scene.getStylesheets().add(this.getClass().getResource("NiceEncounter.css").toExternalForm());
        editStage.setScene(scene);
        editStage.showAndWait();
    }
}
