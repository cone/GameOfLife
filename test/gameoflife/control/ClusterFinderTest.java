/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameoflife.control;

import gameoflife.components.Cell;
import java.util.ArrayList;
import java.util.HashMap;
import junit.framework.TestCase;

/**
 *
 * @author Carlos Gutierrez
 */
public class ClusterFinderTest extends TestCase {
    ClusterFinder clusterFinder;
    private static final int[][] patternCoords = {
        {8,10},{7,9},{7,8},{8,7},{9,8},{9,9},{15,9},{15,8}, {16,7},
        {17,8},{17,9},{16,10},{11,1},{12,1},{13,1},{11,6},{12,6},
        {13,6},{11,11},{12,11},{13,11},{11,16},{12,16},{13,16},{14,13},
        {14,14},{10,13},{10,14},{14,4},{14,3},{10,3},{10,4}
    };
    
    public ClusterFinderTest(String testName) {
        super(testName);
        HashMap<String, Cell> cells = new HashMap<>();
        fillCells(cells);
        addPattern(cells,patternCoords);
        clusterFinder = new ClusterFinder(cells); 
    }
    
    private void fillCells(HashMap<String, Cell> cells){
        for(int x=0; x<20; x++){
            for(int y=0; y<20; y++){
                cells.put(Cell.genKey(x, y), new Cell(x,y));
            }
        }
    }
    
    private void addPattern(HashMap<String, Cell> cells, int[][] coords){
        int[] xy;
        Cell aux;
        for (int[] coord : coords) {
            xy = coord;
            aux = cells.get(Cell.genKey(xy[0], xy[1]));
            aux.setStatus(Cell.statusTypes.ALIVE);
        }
    }

    /**
     * Test of reset method, of class ClusterFinder.
     */
    public void testReset() {
        System.out.println("reset");
        clusterFinder.findClusters();
        clusterFinder.reset();
        assertEquals(0, clusterFinder.getClusters().size());
    }

    /**
     * Test of findClusters method, of class ClusterFinder.
     */
    public void testFindClusters() {
        System.out.println("findClusters");
        int nclusters = clusterFinder.findClusters().size();
        assertEquals(10, nclusters);
    }

    /**
     * Test of getClusterMembers method, of class ClusterFinder.
     */
    public void testGetClusterMembers() {
        System.out.println("getClusterMembers");
        Cell cell = new Cell(8,10);
        ArrayList<Cell> cc = new ArrayList<>();
        clusterFinder.getClusterMembers(cell, cc);
        assertEquals(6, cc.size());
    }

    /**
     * Test of getMessageString method, of class ClusterFinder.
     */
    public void testGetMessageString() {
        System.out.println("getMessageString");
        clusterFinder.findClusters();
        String result = clusterFinder.getMessageString();
        assertEquals(false, result.isEmpty());
    }

    /**
     * Test of getNumberOfClusters method, of class ClusterFinder.
     */
    public void testGetNumberOfClusters() {
        System.out.println("getNumberOfClusters");
        clusterFinder.findClusters();
        int result = clusterFinder.getNumberOfClusters();
        assertEquals(10, result);
    }

    /**
     * Test of getClusters method, of class ClusterFinder.
     */
    public void testGetClusters() {
        System.out.println("getClusters");
        clusterFinder.findClusters();
        assertEquals(10, clusterFinder.getClusters().size());
    }
    
}
