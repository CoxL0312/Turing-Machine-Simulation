import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.io.File;

public class Main {
    public static void main(String[] args) 
    {

        //HELLO

        
        JFileChooser fileSelect = new JFileChooser();
        int result = fileSelect.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) 
        {
            File selectedFile = fileSelect.getSelectedFile();

            // Prompt for input string
            String input = JOptionPane.showInputDialog("Enter input string for the Turing Machine:");

            boolean success = TuringMachine.run(input, selectedFile);

            if (success) 
            {
                JOptionPane.showMessageDialog(null, "TuringMachine Ran!");
            } 
        } 
        else 
        {
            JOptionPane.showMessageDialog(null, "File selection error.",
                    "Error", JOptionPane.WARNING_MESSAGE);
        }
    }
}

