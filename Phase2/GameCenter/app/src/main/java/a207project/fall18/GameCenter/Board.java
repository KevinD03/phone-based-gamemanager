package a207project.fall18.GameCenter;

import java.io.Serializable;
import java.util.Observable;

/**
 * A supper class Board
 */
public class Board extends Observable implements Serializable {


    /**
     * An array of objects
     */
    private Object[][] tiles;


    /**
     * @return get the tiles array
     */
    public Object[][] getTiles() {
        return tiles;
    }

    /**
     * @param tiles tiles being set
     */
    public void setTiles(Object[][] tiles) {
        this.tiles = tiles;
    }








}
