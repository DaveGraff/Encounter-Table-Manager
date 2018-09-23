/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encounter.tables;

import java.util.Random;
import javafx.stage.Stage;

/**
 *
 * @author David
 */
public class Monster {
    private String name;
    private int specialHP;          //null by default, for monsters w/ fixed HP values
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
        String hp = "";
        for(int i = 0; i < appearing; i++){
            hp = hp.concat(" " + Integer.toString(numSet(hpNum, hpDice, hpMod)));
        }
        data = data.concat(hp + "\n");
        data = data.concat(description);
        return data;
    }
    /*
    Creates screen to create and edit monsters
    A new monster should be created first, and then edited
    */
    public Stage editMonster(){
        Stage editStage = new Stage();
        
        
        return editStage;
    }
}
