import javax.script.ScriptException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Comparator;

public class GUI extends JFrame {
    private JTextField nbpopTextField;
    private JTextField initialCustomFitnessFunctionTextField;
    private JTable outputTable;
    DefaultTableModel tableModel;
    private Timer timer;


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GUI();
            }
        });
    }

    public GUI() {
        setBounds(0, 0, 600, 600);
        setTitle("Genetic Algorithm");
        try {
            // Initialize an Image object with the path to your icon
            Image icon = Toolkit.getDefaultToolkit().getImage("pngtree-dna-icon-design-png-image_1499059.jpg");

            // Set the window icon
            setIconImage(icon);
        } catch (Exception e) {
            e.printStackTrace();
        }

        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setPreferredSize(new Dimension(600, 600));
        setResizable(false);

        // Add components for nbpop
        add(new JLabel("Enter nbpop:"));
        nbpopTextField = new JTextField(10);
        add(nbpopTextField);
        nbpopTextField.setText("15");

        // Add components for initialCustomFunction
        add(new JLabel("Enter initialCustomFunction:"));
        initialCustomFitnessFunctionTextField = new JTextField(10);
        add(initialCustomFitnessFunctionTextField);
        initialCustomFitnessFunctionTextField.setText("(x + 3)^2 - 25");

        // Add a button to generate the initial population
        JButton generateButton = new JButton("Start");
        add(generateButton);
        generateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    generateInitialPopulation();
                } catch (ScriptException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        String[] columnNames = {"Individual",
                "Decimal",
                "Binary",
                "Gen 1", "Gen 2", "Gen 3",
                "Gen 4", "Gen 5", "Gen 6",
                "Gen 7", "Gen 8"};

        outputTable = new JTable() {
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };
        outputTable.setFont(new Font("Monospaced", Font.PLAIN, 12));
        outputTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        outputTable.setDragEnabled(false);
        outputTable.setRowSelectionAllowed(false);
        outputTable.setCellSelectionEnabled(false);
        outputTable.setShowGrid(true);
        outputTable.setGridColor(Color.BLACK);
        outputTable.setRowHeight(20);


        DefaultTableModel model = new DefaultTableModel(columnNames, 0); // 0 means no rows initially
        outputTable.setModel(model);

        tableModel = new DefaultTableModel(columnNames, 0);
        outputTable.setModel(tableModel);
        //getContentPane().add(outputTable);

        // Wrap the JTextPane in a JScrollPane to make it scrollable
        JScrollPane scrollPane = new JScrollPane(
                outputTable,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,   // Enable vertical scrollbar when needed
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED  // Enable horizontal scrollbar when needed
        );
        getContentPane().add(scrollPane);


        pack();
        setVisible(true);
    }


    private void generateInitialPopulation() throws ScriptException {
        int individualsNumbers = Integer.parseInt(nbpopTextField.getText());
        String initialCustomFitnessFunction = initialCustomFitnessFunctionTextField.getText();
        String customFitnessFunction;
        if (initialCustomFitnessFunction.contains("^")) {
            customFitnessFunction = Main.reformatFunction(initialCustomFitnessFunction);
        } else {
            customFitnessFunction = initialCustomFitnessFunction;
        }

        Individuals[] pop = new Individuals[individualsNumbers];
        for (int i = 0; i < individualsNumbers; i++) {
            pop[i] = new Individuals(customFitnessFunction);
            //System.out.println(pop[i].getDecimalGenes());
        }

        DefaultTableModel model = (DefaultTableModel) outputTable.getModel();
        model.setRowCount(0); // Clear existing rows

        for (int i = 0; i < individualsNumbers; i++) {

            String binaryString = pop[i].getBinaryGenes();
            Object[] rowData = new Object[11];
            rowData[0] = (i + 1);
            rowData[1] = pop[i].getDecimalGenes();
            //System.out.println(pop[i].getDecimalGenes());
            rowData[2] = binaryString;

            int[] genes = pop[i].getArrayGenes();
            for (int j = 3; j <= 10; j++) {
                rowData[j] = genes[j - 3]; // Filling in Gen 1 to Gen 8
            }

            model.addRow(rowData);
        }


        Timer timer = new Timer(5000, e -> {
            updateAndSortTable(pop);
            ((Timer)e.getSource()).stop();  // Stop the timer
        });
        timer.start();

        new Timer(10000, e -> {
            Timer timer1 = new Timer(500, e1 -> {
                if (tableModel.getRowCount() > 2) {
                    tableModel.removeRow(tableModel.getRowCount() - 1);
                } else {
                    ((Timer) e1.getSource()).stop();
                }
            });
            timer1.start();
            ((Timer)e.getSource()).stop();  // Stop the initial delay timer
        }).start();
    }

    //TODO : Try to animate the sorting
    //       Finish the GUI

    private void updateAndSortTable(Individuals[] pop) {
        // Sort 'pop' array based on 'Decimal' value
        Arrays.sort(pop, Comparator.comparing(Individuals::getDecimalGenes));


        // Clear existing rows
        tableModel.setRowCount(0);

        // Add sorted rows to table model
        for (int i = 0; i < pop.length; i++) {
            Individuals ind = pop[i];
            Object[] row = new Object[]{
                    (i + 1),
                    ind.getDecimalGenes(),
                    ind.getBinaryGenes(),
                    ind.getArrayGenes()[0],
                    ind.getArrayGenes()[1],
                    ind.getArrayGenes()[2],
                    ind.getArrayGenes()[3],
                    ind.getArrayGenes()[4],
                    ind.getArrayGenes()[5],
                    ind.getArrayGenes()[6],
                    ind.getArrayGenes()[7]
            };
            tableModel.addRow(row);
        }

        // Try to notify the table that the data has changed
        tableModel.fireTableDataChanged();
    }


}
