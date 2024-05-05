import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame implements ActionListener {
    private JTextField startWordField, endWordField, modeField;
    private JTextArea outputArea;

    public GUI() {
        setTitle("Path Finder");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.add(new JLabel("Start Word:"));
        startWordField = new JTextField();
        inputPanel.add(startWordField);

        inputPanel.add(new JLabel("End Word:"));
        endWordField = new JTextField();
        inputPanel.add(endWordField);

        inputPanel.add(new JLabel("Mode (UCS, GBFS, A*):"));
        modeField = new JTextField();
        inputPanel.add(modeField);

        JButton findButton = new JButton("Find Path");
        findButton.addActionListener(this);
        inputPanel.add(findButton);

        add(inputPanel, BorderLayout.NORTH);

        outputArea = new JTextArea();
        add(new JScrollPane(outputArea), BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent e) {
        String startWord = startWordField.getText().toLowerCase();
        String endWord = endWordField.getText().toLowerCase();
        String mode = modeField.getText().toUpperCase();

        boolean showResults = true;

        DictSet dictset = new DictSet();
        dictset.loadDictFromFile("src/dictionary.txt");

        if (!dictset.getDict().contains(startWord) || !dictset.getDict().contains(endWord)) {
            outputArea.setText("Invalid input. Words are not in dictionary.");
            showResults = false;
        }
        if (startWord.length() != endWord.length()) {
            outputArea.setText("Invalid input. Words are of different length.");
            showResults = false;
        }
        if (!(mode.equals("UCS") || mode.equals("GBFS") || mode.equals("A*"))) {
            outputArea.setText("Invalid mode. Please enter UCS, GBFS, or A*.");
            showResults = false;
        }

        PathFinder pf = new PathFinder();

        if (showResults) {
            long startTime = System.currentTimeMillis();
            pf.findPath(startWord, endWord, dictset, mode);
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;

            if (pf.getResultPath().size() == 0) {
                outputArea.setText("No path was found between start word and end word.\n");
            } else {
                outputArea.setText("Path: " + pf.getResultPath() + "\n");
            }
            outputArea.append("Nodes visited: " + pf.getNumVisited() + "\n");
            outputArea.append("Execution time: " + executionTime + " ms");
        }
    }
}
