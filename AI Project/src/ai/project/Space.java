/**
 * @author Adam Silva and Miguel Silva
 */

package ai.project;

public class Space {
    
    // TRUE = SPACE
    // FALSE = WALL
    private boolean goUp = false;
    private boolean goDown = false;
    private boolean goLeft = false;
    private boolean goRight = false;
    private int spaceNumber = 0;
    private int line = 0; 
    private int column = 0;

    public Space(boolean goUp, boolean goLeft, boolean goDown, boolean goRight, int line, int column) 
    {
        this.goUp = goUp;
        this.goLeft = goLeft;
        this.goDown = goDown;
        this.goRight = goRight;
        this.line = line;
        this.column = column;
    }

    public boolean hasSpaceDown() 
    {
        return goDown;
    }

    public boolean hasSpaceLeft() 
    {
        return goLeft;
    }

    public boolean hasSpaceRight() 
    {
        return goRight;
    }

    public boolean hasSpaceUp() 
    {
        return goUp;
    }
    
    public int getSpaceNumber()
    {
        return spaceNumber;
    }
    
    public void setSpaceNumber(int spaceNumber)
    {
        this.spaceNumber = spaceNumber;
    }
    
    public int getLine()
    {
        return line;
    }
    
    public int getColumn()
    {
        return column;
    }
}
