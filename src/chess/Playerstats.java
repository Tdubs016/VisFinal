/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chess;

/**
 *
 * @author Typan
 */
    public class Playerstats {
        private int turn;
        public static Playerstats stats = new Playerstats();  
    
    
    public int getturn(){
        return turn;
    }
    public void setturn(int a){
        turn = a;
        
    }
    public void setgame(){
        int max = 2;
        int min = 1;
        turn = (int)(Math.random()*(max-min+1)+min);
    }
}
