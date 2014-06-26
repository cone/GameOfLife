/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameoflife.components;

/**
 *
 * @author toshiba
 */
public class Cell {
    public static int sideValue = 10;
    private int x = 0;
    private int y = 0;
    private int neighborCount = 0;
    private enum statusTypes{
        ALIVE,
        DEAD
    }
    private statusTypes status;
    
    public Cell(){
        init();
    }
    
    public Cell(int x, int y){
        this.x = x;
        this.y = y;
        init();
    }
    
    private void init(){
        status = statusTypes.DEAD;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    public void addNeighbor(){
        neighborCount++;
    }
    
    public statusTypes getStatus() {
        return status;
    }

    public void setStatus(statusTypes status) {
        this.status = status;
    }
    
    public boolean willLive(){
        if(status == statusTypes.ALIVE)
            return (neighborCount == 2) || (neighborCount == 3);
        if(status == statusTypes.DEAD)
            return (neighborCount == 3);
        return false;
    }

}
