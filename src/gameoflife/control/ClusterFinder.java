/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameoflife.control;

import gameoflife.components.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author Carlos Gutierrez
 */
public class ClusterFinder {
    private HashMap<String, Cell> board;
    private ArrayList<ArrayList<Cell>> clusters;
    private ArrayList<Cell> currentCluster;
    public String STATUS_STRING = "Number of Clusters: ";
    
    public ClusterFinder(HashMap<String, Cell> board){
        clusters = new ArrayList<>();
        this.board = board;
    }
    
    /**
     * Method for reseting the cluster array
     * and setting the "inCluster" property to "false"
     * to the cells in those clusters
     */
    public void reset(){
        clusters.stream().forEach((cluster)->{
            cluster.stream().forEach((cell)->{
                cell.setInCluster(false);
            });
        });
        clusters.clear();
    }
    
    /**
     * Method that starts the search fro clusters
     * @return The clusters array
     */
    public ArrayList<ArrayList<Cell>> findClusters(){
        reset();
        Iterator i = board.entrySet().iterator();
        Cell aux;
        while(i.hasNext()) {
           Map.Entry<String, Cell> pairs = (Map.Entry)i.next();
           aux = pairs.getValue();
           //check if it is alive and the start a recoursive search for neighbors(cluster members)
           if(aux.isAlive() && !aux.isInCluster()){
               //create a new empty cluster
               currentCluster = new ArrayList<>();
               //add the "root node" to the cluster and mark it as already in a cluster
               //so it will get ignored when searching for other clusters an save some 
               //computing clicles
               currentCluster.add(aux);
               aux.setInCluster(true);
               //start seaching for other cluster members
               getClusterMembers(aux, currentCluster);
               //after we added all the members to the hasmap(cluster)
               //add the cluster to the clusters array
               clusters.add(currentCluster);
           }
        }
        return clusters;
    }
    
    /**
     * Recoursive method that treats the cluster
     * as a tree so it iterates through the nodes(neighbors)
     * adding the child nodes to the cluster(Array)
     * until it finds no more nodes.
     * @param cell the "root node"
     * @param currentCluster the Array that represents the cluster
     */
    public void getClusterMembers(Cell cell, ArrayList<Cell> currentCluster){
        int[] xy;
        Cell aux;
        for(int i1=0; i1<Cell.surroundingCoords.length; i1++){
            xy = Cell.surroundingCoords[i1];
            aux = board.get(Cell.genKey(cell.getX()+xy[0], cell.getY()+xy[1]));
            if(aux != null && aux.isAlive() && !aux.isInCluster()){
                aux.setInCluster(true);
                currentCluster.add(aux);
                getClusterMembers(aux, currentCluster);
            }
        }
    }
    
    /**
     * Method that constructs the message string fot the clusters report
     * @return The clusters report message
     */
    public String getMessageString(){
        String separator = "-----------------------";
        StringBuilder msgBuilder = new StringBuilder("Number of clusters = ");
        msgBuilder.append(clusters.size());
        msgBuilder.append("\n");
        for(int i=0; i<clusters.size(); i++){
            ArrayList<Cell> cluster = clusters.get(i);
            msgBuilder.append(separator);
            msgBuilder.append("\n");
            msgBuilder.append("Members of cluster No. ");
            msgBuilder.append((i+1));
            msgBuilder.append("\n");
            cluster.stream().forEach((cell)->{
                msgBuilder.append(cell.getKey());
                msgBuilder.append("\n");
            });
        };
        return msgBuilder.toString();
    }
    
    /**
     * Method for getting the number of clusters obtained
     * @return The current number of clusters
     */
    public int getNumberOfClusters(){
        return clusters.size();
    }
    
    /**
     * Method that return the array
     * that holds the clusters obtained
     * @return the cluster array
     */
    public ArrayList getClusters(){
        return clusters;
    }
    
    /**
     * Method that returns the current cluster
     * Useful to obtain the current cluster that
     * is beign processed
     * @return the current cluster
     */
    public ArrayList<Cell> getCurrentCluster(){
        return currentCluster;
    }
}
