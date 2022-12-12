/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chess;

import java.util.LinkedList;
import javax.swing.ImageIcon;

/**
 *
 * @author Typan
 */
public class piece {
    
    String name;
    int piecex;
    int piecey;
    boolean black;
    private boolean killed = false;
    LinkedList<piece> piecelist;
    
    
    public piece(String name, int piecex, int piecey, boolean black, LinkedList<piece> piecelist)
    {
        this.name = name;
        this.piecex = piecex;
        this.piecey = piecey;
        this.black = black;
        this.piecelist = piecelist;
        piecelist.add(this);
    }
    
    public String getname(){
    
        return this.name;
    
    }
    
    public void move(int piecex, int piecey ){
        for(piece p: piecelist){
            if(p.piecex == piecex && p.piecey == piecey){
                p.remove();
            }
            
        }
        
        this.piecex = piecex;
        this.piecey = piecey;
        
    }
    
    public void remove()
    {
       piecelist.remove(this);
    }   
    
    
   
    
    
    
}
