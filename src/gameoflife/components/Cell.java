package gameoflife.components;

/**
 *
 * @author Carlos Gutierrez
 */
public class Cell {
    public static int sideValue = 10;
    private int x = 0;
    private int y = 0;
    private int neighborCount = 0;
    public enum statusTypes{
        ALIVE,
        DEAD
    }
    private statusTypes status;
    public static int[][] surroundingCoords = {
        {-1,-1},
        {-1,0},
        {-1,1},
        {0,-1},
        {0,1},
        {1,-1},
        {1,0},
        {1,1}
    };
    
    public Cell(){
        init();
    }
    
    public Cell(int x, int y){
        this.x = x;
        this.y = y;
        init();
    }
    
    /**
     * Initializing the cell as dead
     */
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
    
    /**
     * Used to retreive the key that will 
     * be used to identify this cell in the 
     * cell hash
     * @return The cell key value
     */
    public String getKey(){
        return String.valueOf(x)+","+String.valueOf(y);
    }
    
    /**\
     * Static method used to generate a cell key
     * using its coordenates
     * @param x
     * @param y
     * @return The cell key
     */
    public static String genKey(int x, int y){
        return String.valueOf(x)+","+String.valueOf(y);
    }
    
    public void addNeighbor(){
        neighborCount++;
    }
    
    public void resetNeighborCount(){
        neighborCount=0;
    }
    
    public statusTypes getStatus() {
        return status;
    }
    
    public boolean isAlive(){
        return status == statusTypes.ALIVE;
    }

    public void setStatus(statusTypes status) {
        this.status = status;
    }
    
    /**
     * Used to determine if the cell will
     * pass to the next generation
     * @return If the cell passes to the next generation
     */
    public boolean willLive(){
        if(status == statusTypes.ALIVE)
            return (neighborCount == 2) || (neighborCount == 3);
        if(status == statusTypes.DEAD)
            return (neighborCount == 3);
        return false;
    }

}
