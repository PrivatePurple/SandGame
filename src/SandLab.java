import java.awt.*;
import java.util.*;

public class SandLab
{
  //Step 4,6
  //add constants for particle types here
  public static final int EMPTY = 0;
  public static final int METAL = 1;
  public static final int SAND = 2;
  public static final int WATER = 3;
  
  //do not add any more fields below
  private int[][] grid;
  private SandDisplay display;
  
  
  /**
   * Constructor for SandLab
   * @param numRows The number of rows to start with
   * @param numCols The number of columns to start with;
   */
  public SandLab(int numRows, int numCols)
  {
    String[] names;
    // Change this value to add more buttons
    //Step 4,6
    names = new String[4];
    // Each value needs a name for the button
    names[EMPTY] = "Empty";
    names[METAL] = "Metal";
    names[SAND] = "Sand";
    names[WATER] = "Water";
    
    //1. Add code to initialize the data member grid with same dimensions
    grid = new int[numRows][numCols];
    
    display = new SandDisplay("Falling Sand", numRows, numCols, names);
  }
  
  //called when the user clicks on a location using the given tool
  private void locationClicked(int row, int col, int tool)
  {
    //2. Assign the values associated with the parameters to the grid
   grid[row][col] = tool;
  }

  //copies each element of grid into the display
  public void updateDisplay()
  {
      //Step 3
	  for(int row = 0; row < grid.length; row++)
	  {
		  for(int col = 0; col < grid[0].length; col++)
			{
				int current = grid[row][col];
				
				if(current == EMPTY)
				{
					display.setColor(row,col,Color.BLACK);
				}
				else if(current == METAL)
				{
					display.setColor(row,col,Color.GRAY);
				}
				else if(current == SAND)
				{
					display.setColor(row,col,new Color(255,200,112));
				}
				else if(current == WATER)
				{
					display.setColor(row,col,new Color(116,162,237));
				}
			}
	  }
    
  }

  //Step 5,7
  //called repeatedly.
  //causes one random particle in grid to maybe do something.
  public void step()
  {
    //Remember, you need to access both row and column to specify a spot in the array
    //The scalar refers to how big the value could be
    //int someRandom = (int) (Math.random() * scalar)
    //remember that you need to watch for the edges of the array
    int width = grid[0].length;
    int height = grid.length;
    int randoRow = (int)(Math.random() * height);
    int randoCol = (int)(Math.random() * width);
    
    int current = grid[randoRow][randoCol];
    if (current == SAND && randoRow  != height - 1)
    {
    	if (grid[randoRow + 1][randoCol] == EMPTY)
    	{
    		grid[randoRow][randoCol] = EMPTY;
    		grid [randoRow + 1][randoCol] = SAND;
    	}
    	else if (grid[randoRow + 1][randoCol] == WATER)
    	{
    		int slowFall = (int)(Math.random() * 2);
    		if (slowFall == 0)
    		{
    			grid[randoRow][randoCol] = WATER;
    			grid[randoRow + 1][randoCol] = SAND;
    		}	
    	}
    }
    if (current == WATER)
    {
    	int lastRow = randoRow;
    	
    	int randoDirect = (int)(Math.random() * 2);
    	if(randoRow != height - 2 && grid[randoRow + 1][randoCol] == EMPTY)
    	{
    		grid[randoRow][randoCol] = EMPTY;
    		grid[randoRow + 1][randoCol] = WATER;
    		lastRow = randoRow + 1;
    	}
    	else if(randoDirect == 0 && randoCol > 0 && grid[lastRow][randoCol - 1] == EMPTY)
    	{
    		
    	}
    }
    
  }
  
  //do not modify this method!
  public void run()
  {
    while (true) // infinite loop
    {
      for (int i = 0; i < display.getSpeed(); i++)
      {
        step();
      }
      updateDisplay();
      display.repaint();
      display.pause(1);  //wait for redrawing and for mouse
      int[] mouseLoc = display.getMouseLocation();
      if (mouseLoc != null)  //test if mouse clicked
      {
        locationClicked(mouseLoc[0], mouseLoc[1], display.getTool());
      }
    }
  }
}
