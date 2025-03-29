import java.util.ArrayList;

public class Tape 
{
    private ArrayList<Character> cells;
    private int headPosition;

    //constructor, initializes empty
    public Tape() 
    {
        cells = new ArrayList<>();
        headPosition = 0;
    }

    //prevents going out of bounds errors
    private void ensureBounds() 
    {
        while (headPosition >= cells.size()) 
        {
            cells.add(' ');
        }
    }

    public void initialize(String input) 
    {
        //clears the tape in case it isnt't
        cells.clear();
        //adds each input char from the input string to tape
        for (char c : input.toCharArray()) 
        {
            cells.add(c);
        }

        //add a blank at the end in case we go too far
        cells.add(' ');
        headPosition = 0;
    }

    //reads symbol at current headPosition
    public char read() 
    {
        ensureBounds();
        return cells.get(headPosition);
    }

    //writes to the cell at current headPosition
    public void write(char symbol) 
    {
        ensureBounds();
        cells.set(headPosition, symbol);
    }

    //moves headPosition
    public void move(char direction) 
    {
        if (direction == 'R') 
        {
            headPosition++;
            ensureBounds();
        } 
        else if (direction == 'L') 
        {
            if (headPosition > 0) headPosition--;
            else cells.add(0, ' ');
        }
    }

    
}

