
import java.util.ArrayList;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import javax.swing.JOptionPane;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TuringMachine 
{
    //classwide TM instance for making accessing easier
    private static  TuringMachine tm;
    private static String startState;
    private static String acceptState;
    private static String rejectState;
    private static ArrayList<String> states;
    private static Tape tape;
    private static ArrayList<Character> inputAlphabet = new ArrayList<Character>();
    private static ArrayList<Transition> transitions;

    public static Boolean run (String input, File theFile)
    {
        try
        {
            //initialize the tape
            tape = new Tape();
            tape.initialize(input);

            BufferedReader reader = new BufferedReader(new FileReader(theFile));

            //lines 1-4, always formatted as such
            startState = reader.readLine();
            acceptState = reader.readLine();
            rejectState = reader.readLine();

            //since inputAlphabet has to be an ArrayList, this takes the line formatted as
            //chars with commas in between and puts them into an array and then the list
            //works if the alphabet is only one char as well
            String[] symbols = reader.readLine().split(",");
            for (String s : symbols)
            {
                inputAlphabet.add(s.trim().charAt(0));
            }
            
            System.out.println("Input Alphabet: " + inputAlphabet);
            transitions = new ArrayList<>();
            String line;

            //reads until no more lines
            while ((line = reader.readLine()) != null) 
            {

            //remove whitespace & skip empty lines
            line = line.trim();
            if (line.isEmpty()) continue;

            //version of slitting the line via Arrays and split(" ")
            //had errors with accidentally skipping lines with any blanks
            /*
             * //splits the line into parts
            //q1 (a,a,R) q2 ---> [q1], [(a,a,R)], [q2]
            String[] parts = line.split(" ");
            //just an additional check it's the right length
            if (parts.length == 3) 
            {
                String fromState = parts[0];
                //remove ()
                String transition = parts[1].substring(1, parts[1].length() - 1); 
                String toState = parts[2];

                String[] transParts = transition.split(",");

                //the extra checks on not empty help prvent IO exceptions when dealing with blanks
                if (transParts.length == 3)
                {
                    String readS = transParts[0];
                    String writeS = transParts[1];
                    String moveS = transParts[2];

                    //allows for ' '
                    if (!readS.equals("") && !writeS.equals("") && !moveS.equals(""))
                    {
                        char readSymbol = transParts[0].charAt(0);
                        char writeSymbol = transParts[1].charAt(0);
                        char move = transParts[2].charAt(0);

                        Transition t = new Transition(fromState, readSymbol, writeSymbol, move, toState);
                        transitions.add(t);

                        System.out.println("Parsed transition: " + fromState + 
    " (" + readSymbol + "," + writeSymbol + "," + move + ") " + toState);

                    }
                }
            }
             */
            Pattern pattern = Pattern.compile("^(\\S+)\\s+\\((.),(.),(.)\\)\\s+(\\S+)$");
            Matcher matcher = pattern.matcher(line);

            if (matcher.matches()) 
                {
                    String fromState = matcher.group(1);
                    //can be ' '
                    char readSymbol = matcher.group(2).charAt(0);  
                    //can be ' ' 
                    char writeSymbol = matcher.group(3).charAt(0); 
                    //L or R
                    char move = matcher.group(4).charAt(0);         
                    String toState = matcher.group(5);

                    Transition t = new Transition(fromState, readSymbol, writeSymbol, move, toState);
                    transitions.add(t);

                    //debugging line jic
                    //System.out.println("Parsed transition: " + fromState +
                    //    " (" + readSymbol + "," + writeSymbol + "," + move + ") " + toState);
                }
            
            }
            reader.close();
            //reading the file is done now

            //The actual check for if the given input is accepted by the TM or not
            String currState = startState;

            while (true)
            {
                char currSymbol = tape.read();

                //find a matching transition
                Transition matched = null;
                for (Transition t : transitions)
                {
                    if (t.getFromState().equals(currState) &&
                    t.getReadSymbol() == currSymbol)
                    {
                        matched = t;
                        break;
                    }
                }

                if (matched == null)
                {
                    JOptionPane.showMessageDialog(null, "Input invalid: no match");
                    return false;
                }

                //else if matched != ull, aka a match is found
                tape.write(matched.getWriteSymbol());
                tape.move(matched.getMove());
                currState = matched.getToState();

                if (currState.equals(acceptState))
                {
                    JOptionPane.showMessageDialog(null, "Input Accepted");
                    return true;
                }
                else if (currState.equals(rejectState))
                {
                    JOptionPane.showMessageDialog(null, "Input Rejected");
                    return false;
                }
            }

        } 
        catch (FileNotFoundException e) 
        {
            JOptionPane.showMessageDialog(null, "Error: File not found!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } 
        catch (IOException i) 
        {
            JOptionPane.showMessageDialog(null, "Error reading the file: \n" +i.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

    }
}

