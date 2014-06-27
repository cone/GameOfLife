/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameoflife.control;

import gameoflife.components.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author toshiba
 */
public class Algorithm{
    private HashMap<String, Cell> cells = new HashMap();
    private ArrayList<Cell> toLive = new ArrayList();
    private ArrayList<Cell> toDie = new ArrayList();
    private int panelSideValue;
    private int alivecells = 0;
    
    public Algorithm(int panelSideValue){
       this.panelSideValue = panelSideValue;
    }
    
    public int getPanelSideValue(){
        return panelSideValue;
    }
    
    public HashMap<String, Cell> getCells() {
        return cells;
    }
    
    public void addCell(Cell cell){
        if(!cells.containsValue(cell)){
            cells.put(cell.getKey(),cell);
        }
    }
    
    public void toggleCell(Cell cell){
        if(cell.isAlive()){
            cell.setStatus(Cell.statusTypes.DEAD);
            alivecells--;
        }
        else{
            cell.setStatus(Cell.statusTypes.ALIVE);
            alivecells++;
        }
    }
    
    public boolean createnextGen(){
        int sides = panelSideValue/Cell.sideValue;
        toLive.clear();
        toDie.clear();
        int[] xy;
        Cell cell;
        Cell aux;
        for (int i = 0; i < sides; i++) {
            for (int j = 0; j < sides; j++) {
                cell = cells.get(Cell.genKey(i, j));
                for(int i1=0; i1<Cell.surroundingCoords.length; i1++){
                    xy = Cell.surroundingCoords[i1];
                    aux = cells.get(Cell.genKey(i+xy[0], j+xy[1]));
                    if(aux != null && aux.isAlive()){
                        cell.addNeighbor();
                    }
                }
                if(cell.willLive()){
                    if(!cell.isAlive()){
                        toLive.add(cell);
                    }
                }
                else{
                    if(cell.isAlive()){
                        toDie.add(cell);
                    }
                }
                cell.resetNeighborCount();
            }
        }
        toLive.stream().forEach((liveCell) -> {
            liveCell.setStatus(Cell.statusTypes.ALIVE);
            alivecells++;
        });
        toDie.stream().forEach((deadCell) -> {
            deadCell.setStatus(Cell.statusTypes.DEAD);
            alivecells--;
        });
        if(toLive.isEmpty() && toDie.isEmpty()){
            return false;
        }
       return true;
   }
    
    public int getAliveCells(){
        return alivecells;
    }
}
