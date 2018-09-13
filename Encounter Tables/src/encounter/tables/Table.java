/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encounter.table;

import java.util.Random;

/**
 *
 * @author David
 */
public class Table {
    private Monster[] table = new Monster[19];
    private int monsterNum = 0;//number of monsters currently in the table 
    
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
            return temp;
        }
        else return null;
    }
}
