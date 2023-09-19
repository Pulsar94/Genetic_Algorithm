import javax.script.ScriptException;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("ALL")
public class GUI extends JFrame {
    //region attributes
    private JFrame frame;
    private final JTextField nbpopTextField;
    private final JTextField nbMutationTextField;
    private final JTextField initialCustomFitnessFunctionTextField;
    private final JCheckBox avoidTwinsCheckBox;
    private JTable outputTable;
    private boolean twins = true;
    private final DefaultTableModel tableModel;
    private int time = 2500;
    boolean applyCustomRenderer = false;
    boolean crossover = false;
    private Individuals[] pop;
    private int individualsNumber;
    private int mutationNumber;
    private String customFitnessFunction;
    private DefaultTableModel model;
    private int[] counter = {0, 0};
    //endregion

    /**
     * The entry point for the application.
     * <p>
     * This method initializes the GUI by invoking the GUI constructor in the Event Dispatch Thread.
     * </p>
     *
     * @param args Command-line arguments. Not used in this application.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUI::new);
    }

    /**
     * Initializes the Genetic Algorithm GUI.
     *
     * The GUI includes fields for setting the number of individuals, fitness function, and number of mutations.
     * It also features a JTable to display the state of the algorithm and buttons to control the simulation.
     *
     * @throws RuntimeException If a ScriptException occurs during fitness evaluation.
     */
    public GUI() throws RuntimeException {
        setBounds(700, 150, 700, 480);
        setTitle("Genetic Algorithm");
        try {
            Image icon = Toolkit.getDefaultToolkit().getImage("DNA.jpg");
            // Set the window icon
            setIconImage(icon);
        } catch (Exception e) {
            e.printStackTrace();
        }


        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setPreferredSize(new Dimension(700, 480));
        setResizable(false);

        // Add components for nbpop
        add(new JLabel("Enter the number of individuals:"));
        nbpopTextField = new JTextField(2);
        add(nbpopTextField);
        nbpopTextField.setText("15");

        // Add components for initialCustomFunction
        add(new JLabel("Enter the Fitness Function:"));
        initialCustomFitnessFunctionTextField = new JTextField(7);
        add(initialCustomFitnessFunctionTextField);
        initialCustomFitnessFunctionTextField.setText("(x + 3)^2 - 25");

        // Add components for nbpop
        add(new JLabel("Enter the number of mutation:"));
        nbMutationTextField = new JTextField(1);
        add(nbMutationTextField);
        nbMutationTextField.setText("1");

        // Add components for twins
        add(new JLabel("Avoid twins:"));
        avoidTwinsCheckBox = new JCheckBox();
        add(avoidTwinsCheckBox);
        avoidTwinsCheckBox.setSelected(false);


        String[] columnNames = {"Individual",
                "Decimal",
                "Binary",
                "Gen 1", "Gen 2", "Gen 3",
                "Gen 4", "Gen 5", "Gen 6",
                "Gen 7", "Gen 8"};

        //region table
        outputTable = new JTable() {
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);

                if (applyCustomRenderer) {
                    if(crossover) {
                        if (row == 0) {
                            return new MyCellRenderer().getTableCellRendererComponent(this, getModel().getValueAt(row, column),
                                    isCellSelected(row, column), false, row, column, 0);
                        } else if (row == 1) {
                            return new MyCellRenderer().getTableCellRendererComponent(this, getModel().getValueAt(row, column),
                                    isCellSelected(row, column), false, row, column, 1);
                        } else if (row == 2) {
                            return new MyCellRenderer().getTableCellRendererComponent(this, getModel().getValueAt(row, column),
                                    isCellSelected(row, column), false, row, column, 2);
                        }
                    }else {
                        if (row == 0) {
                            return new MyCellRenderer().getTableCellRendererComponent(this, getModel().getValueAt(row, column),
                                    isCellSelected(row, column), false, row, column, 10);
                        }else if (row == 1) {
                            return new MyCellRenderer().getTableCellRendererComponent(this, getModel().getValueAt(row, column),
                                    isCellSelected(row, column), false, row, column, 11);
                        }else if (row == 2) {
                            return new MyCellRenderer().getTableCellRendererComponent(this, getModel().getValueAt(row, column),
                                    isCellSelected(row, column), false, row, column, 12);
                        }
                    }
                }

                return c;
            }
        };


        outputTable.setFont(new Font("Monospaced", Font.BOLD, 14));
        outputTable.setSize(653, 323);
        outputTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        outputTable.setDragEnabled(false);
        outputTable.setRowSelectionAllowed(false);
        outputTable.setCellSelectionEnabled(false);
        outputTable.setShowGrid(true);
        outputTable.setGridColor(Color.BLACK);
        outputTable.setRowHeight(20);


        model = new DefaultTableModel(columnNames, 0); // 0 means no rows initially
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
        scrollPane.setPreferredSize(new Dimension(653, 323));



        outputTable.getColumnModel().getColumn(0).setPreferredWidth(75);
        outputTable.getColumnModel().getColumn(1).setPreferredWidth(55);
        outputTable.getColumnModel().getColumn(2).setPreferredWidth(80);
        outputTable.getColumnModel().getColumn(3).setPreferredWidth(55);
        outputTable.getColumnModel().getColumn(4).setPreferredWidth(55);
        outputTable.getColumnModel().getColumn(5).setPreferredWidth(55);
        outputTable.getColumnModel().getColumn(6).setPreferredWidth(55);
        outputTable.getColumnModel().getColumn(7).setPreferredWidth(55);
        outputTable.getColumnModel().getColumn(8).setPreferredWidth(55);
        outputTable.getColumnModel().getColumn(9).setPreferredWidth(55);
        outputTable.getColumnModel().getColumn(10).setPreferredWidth(55);

        outputTable.getColumnModel().getColumn(2).setCellRenderer(new MyCellRenderer());
        outputTable.repaint();

        //endregion

        // Add a button to generate the initial population
        JButton generateButton = new JButton("Start");
        add(generateButton);


        //add a next button
        JButton nextButton = new JButton("Next");
        add(nextButton);

        nextButton.addActionListener(e -> evolvingPopulation());
        nextButton.addActionListener(e -> {
            outputTable.getColumnModel().getColumn(2).setCellRenderer(new MyCellRenderer());
        });

        JButton skipButton = new JButton("Skip");

        nextButton.setEnabled(false);
        skipButton.setEnabled(false);

        add(skipButton);

        skipButton.addActionListener(e -> {
            try {
                evolvedPopulation();
            } catch (ScriptException ex) {
                throw new RuntimeException(ex);
            }
        });
        skipButton.addActionListener(e -> {
            outputTable.getColumnModel().getColumn(2).setCellRenderer(new MyCellRenderer());
        });

        generateButton.addActionListener(e -> {
            // Validation for nbpopTextField
            String nbpopText = nbpopTextField.getText();
            try {
                int value = Integer.parseInt(nbpopText);
                if (value < 3) {
                    JOptionPane.showMessageDialog(null, "The value of the number of individuals should be greater than or equal to 3");
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid integer for the number of individuals");
                return;
            }

            // Validation for nbMutationTextField
            String nbMutationText = nbMutationTextField.getText();
            try {
                int value = Integer.parseInt(nbMutationText);
                if (value < 0 || value > 8) {
                    JOptionPane.showMessageDialog(null, "The value of number of mutation should be between 0 and 8");
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid integer for the number of mutations");
                return;
            }

            // Validation for initialCustomFitnessFunctionString
            String customFitnessText = initialCustomFitnessFunctionTextField.getText();
            if (!customFitnessText.matches("[0-9+\\-*/^x() ]+")) {
                JOptionPane.showMessageDialog(null, "Only numbers, operators, '^', and 'x' are allowed in Custom Fitness");
                return;
            }
            try {
                nextButton.setEnabled(true);
                skipButton.setEnabled(true);
                time = 2500;
                counter = new int[]{0, 0};
                generateInitialPopulation();
            } catch (ScriptException ex) {
                throw new RuntimeException(ex);
            }
        });

        generateButton.addActionListener(e -> {
            outputTable.getColumnModel().getColumn(2).setCellRenderer(new MyCellRenderer());
        });

        pack();
        setVisible(true);
    }

    /**
     * Generates the initial population for the Genetic Algorithm.
     *
     * This method reads user inputs for the number of individuals, the number of mutations, and the custom fitness function.
     * It then initializes an array of Individuals objects and fills a JTable to display their state.
     *
     * @throws ScriptException If the fitness evaluation fails.
     */
    private void generateInitialPopulation() throws ScriptException {
        String nbpopString = nbpopTextField.getText();
        String nbMutationString = nbMutationTextField.getText();
        String initialCustomFitnessFunctionString = initialCustomFitnessFunctionTextField.getText();
        twins = !avoidTwinsCheckBox.isSelected();

        if (nbpopString.equals("") || nbMutationString.equals("") || initialCustomFitnessFunctionString.equals("")) {
            JOptionPane.showMessageDialog(frame, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            applyCustomRenderer = false;

            individualsNumber = Integer.parseInt(nbpopString);
            mutationNumber = Integer.parseInt(nbMutationString);
            if (initialCustomFitnessFunctionString.contains("^")) {
                customFitnessFunction = Main.reformat(initialCustomFitnessFunctionString);
            } else {
                customFitnessFunction = initialCustomFitnessFunctionString;
            }

            pop = new Individuals[individualsNumber];
            for (int i = 0; i < individualsNumber; i++) {
                pop[i] = new Individuals(customFitnessFunction);
                for (int j = 0; j < i; j++) {
                    if (pop[i].getDecimalGenes() == pop[j].getDecimalGenes()) {
                        if (!twins) {
                            i--;
                            break;
                        } else {
                            pop[i] = new Individuals(customFitnessFunction);
                            j = -1;
                        }
                    }
                }
            }

            model = (DefaultTableModel) outputTable.getModel();
            model.setRowCount(0); // Clear existing rows

            for (int i = 0; i < individualsNumber; i++) {

                String binaryString = pop[i].getBinaryGenes();
                Object[] rowData = new Object[11];
                rowData[0] = (i + 1);
                rowData[1] = pop[i].getDecimalGenes();
                rowData[2] = binaryString;

                int[] genes = pop[i].getArrayGenes();
                for (int j = 3; j <= 10; j++) {
                    rowData[j] = genes[j - 3]; // Filling in Gen 1 to Gen 8
                }

                model.addRow(rowData);

            }
            evolvingPopulation();
        }

    }

    /**
     * Evolves the existing population by performing selection, crossover, and mutation.
     *
     * The method uses a timer to sequentially perform genetic operations and update the table accordingly.
     * Selection is performed to get two best individuals. A crossover or mutation operation is then
     * performed based on certain conditions, resulting in a new individual that is added to the population.
     * The table is updated to display the new state of the population.
     */
    private void evolvingPopulation() {
        twins = !avoidTwinsCheckBox.isSelected();
        applyCustomRenderer = false;
        Timer timer = new Timer(time-500, e -> {

            getTwoBestIndividuals();
            ((Timer)e.getSource()).stop();  // Stop the timer
        });
        timer.start();

        new Timer(time + 500 + 200* individualsNumber, e -> {
            // Adding the crossover row
            Individuals[] best = Selection.selection(pop, individualsNumber);
            Object[] crossoverRowData = new Object[11];

            Random rand = new Random();

            do {
                if (rand.nextInt(10) < (mutationNumber == 0 ? 0 : 3)) {
                    counter[0]++;
                    crossoverRowData[0] = "M" + counter[0];
                    crossover = false;
                    try {
                        pop[2] = new Individuals(Mutation.mutation(best[0].getBinaryGenes(), mutationNumber), 2, customFitnessFunction);
                    } catch (ScriptException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    counter[1]++;
                    crossoverRowData[0] = "C" + counter[1];
                    crossover = true;
                    try {
                        pop[2] = new Individuals(Crossover.crossover(best[0].getBinaryGenes(), best[1].getBinaryGenes()), 2, customFitnessFunction);
                    } catch (ScriptException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }while (!twins && (pop[2].getDecimalGenes() == best[0].getDecimalGenes() || pop[2].getDecimalGenes() == best[1].getDecimalGenes()));

            pop[0] = best[0];
            pop[1] = best[1];
            individualsNumber = 3;
            crossoverRowData[1] = pop[2].getDecimalGenes();
            crossoverRowData[2] = pop[2].getBinaryGenes();
            int[] crossoverGenes = pop[2].getArrayGenes();
            for (int j = 3; j <= 10; j++) {
                crossoverRowData[j] = crossoverGenes[j - 3];
            }
            model.addRow(crossoverRowData);
            ((Timer)e.getSource()).stop();  // Stop the initial delay timer
        }).start();

        new Timer(time + 500 + 200* individualsNumber, e -> {
            applyCustomRenderer = true;
            ((Timer)e.getSource()).stop();
        }).start();
        time = 0;
    }

    /**
     * Continuously evolves the population until certain termination conditions are met.
     *
     * This method iteratively applies genetic algorithms on the population to find the best two individuals.
     * The population undergoes a series of evolution operations until the fitness of the two best individuals is zero.
     * The state of the two best individuals is then added to the table.
     *
     * @throws ScriptException if there is an issue with script execution during evolution.
     */
    private void evolvedPopulation() throws ScriptException {
        twins = !avoidTwinsCheckBox.isSelected();
        applyCustomRenderer = false;
        model.setRowCount(0); // Clear existing rows
        while (pop[0].getFitness() != 0 && pop[1].getFitness() != 0 && counter[0] + counter[1] < 10000) {
            pop = Main.evolution(pop, individualsNumber, counter, customFitnessFunction, mutationNumber, twins);
            individualsNumber = 3;
        }
        Object[] crossoverRowData = new Object[11];
        crossoverRowData[0] = "Best 1";
        crossoverRowData[1] = pop[0].getDecimalGenes();
        crossoverRowData[2] = pop[0].getBinaryGenes();
        int[] crossoverGenes = pop[0].getArrayGenes();
        for (int j = 3; j <= 10; j++) {
            crossoverRowData[j] = crossoverGenes[j - 3];
        }
        model.addRow(crossoverRowData);
        crossoverRowData[0] = "Best 2";
        crossoverRowData[1] = pop[1].getDecimalGenes();
        crossoverRowData[2] = pop[1].getBinaryGenes();
        crossoverGenes = pop[1].getArrayGenes();
        for (int j = 3; j <= 10; j++) {
            crossoverRowData[j] = crossoverGenes[j - 3];
        }
        model.addRow(crossoverRowData);

    }

    /**
     * Identifies and updates the two best individuals in the population within the output table.
     *
     * This method first finds the two best individuals from the population using selection algorithms.
     * Then, it updates the output table by removing rows that do not correspond to the best individuals.
     * A timer is used to control the rate of these updates.
     */
    private void getTwoBestIndividuals() {
        Individuals[] best = Selection.selection(pop, pop.length);
        AtomicInteger nbBest0 = new AtomicInteger();
        AtomicInteger nbBest1 = new AtomicInteger();
        AtomicInteger count = new AtomicInteger();
        Timer timer1 = new Timer(200, e1 -> {
            model = (DefaultTableModel) outputTable.getModel();
            if (tableModel.getRowCount() > 2) {
                if((Integer)tableModel.getValueAt(count.get(), 1) == best[0].getDecimalGenes() && nbBest0.get() == 0){
                    count.getAndIncrement();
                    nbBest0.getAndIncrement();
                }else if((Integer)tableModel.getValueAt(count.get(), 1) == best[1].getDecimalGenes() && nbBest1.get() == 0) {
                    count.getAndIncrement();
                    nbBest1.getAndIncrement();
                }
                else{
                    tableModel.removeRow(count.get());
                }
            } else {
                if((Integer)tableModel.getValueAt(0, 1) != best[0].getDecimalGenes()){  //exchange the two first rows
                    Object[] exchangeRowData = new Object[11];
                    exchangeRowData[0] = tableModel.getValueAt(0,0);
                    exchangeRowData[1] = best[1].getDecimalGenes();
                    exchangeRowData[2] = best[1].getBinaryGenes();
                    for (int j = 3; j <= 10; j++) {
                        exchangeRowData[j] = best[1].getArrayGenes()[j - 3];
                    }
                    tableModel.removeRow(0);
                    model.addRow(exchangeRowData);

                }
                ((Timer) e1.getSource()).stop();
            }
        });
        timer1.start();
    }

    /**
     * Custom table cell renderer that colors the cell content based on genetic operations.
     *
     * This renderer class extends the DefaultTableCellRenderer to provide custom coloring
     * for certain cells in a JTable. The cells are colored based on the row and column
     * conditions to represent various genetic mutations and crossovers.
     */
    static class MyCellRenderer extends DefaultTableCellRenderer {
        /**
         * Overrides the getTableCellRendererComponent method to apply custom coloring logic.
         *
         * @param table The JTable.
         * @param value The value to be rendered.
         * @param isSelected Whether the cell is selected.
         * @param hasFocus Whether the cell has focus.
         * @param row The row index.
         * @param column The column index.
         * @param irow Additional row indicator for special cases.
         * @return The colored table cell component.
         */
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                        int row, int column, int irow) {

            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (column == 2) {
                String binaryString = value.toString();
                if (irow == 0) {
                    String coloredPart = binaryString.substring(0, Crossover.crossoverPoint == -1 ? 0 : Crossover.crossoverPoint);
                    String rest = binaryString.substring(Crossover.crossoverPoint == -1 ? 8 : Crossover.crossoverPoint, 8);
                    setText("<html><font color='red'>" + coloredPart + "</font><font color='black'>" + rest + "</font></html>");
                } else if (irow == 1) {
                    String rest = binaryString.substring(0, Crossover.crossoverPoint == -1 ? 0 : Crossover.crossoverPoint);
                    String coloredPart = binaryString.substring(Crossover.crossoverPoint == -1 ? 8 : Crossover.crossoverPoint);
                    setText("<html><font color='black'>" + rest + "</font><font color='blue'>" + coloredPart + "</font></html>");
                } else if (irow == 2) {
                    String redPart = binaryString.substring(0, Crossover.crossoverPoint == -1 ? 0 : Crossover.crossoverPoint);
                    String bluePart = binaryString.substring(Crossover.crossoverPoint == -1 ? 8 : Crossover.crossoverPoint,8);
                    setText("<html><font color='red'>" + redPart + "</font><font color='blue'>" + bluePart + "</font></html>");
                } else if (irow == 10 || irow == 12) {
                    StringBuilder coloredString = new StringBuilder();
                    int start = 0;

                    Arrays.sort(Mutation.bitMutated);
                    for (int mutatedIndex : Mutation.bitMutated) {
                        // Make sure the index is within range before accessing binaryString
                        if (mutatedIndex >= 0 && mutatedIndex < binaryString.length()) {

                            String pre = binaryString.substring(start, mutatedIndex);
                            String mutatedBit = binaryString.substring(mutatedIndex, mutatedIndex + 1);
                            coloredString.append("<font color='black'>").append(pre).append("</font>");
                            coloredString.append("<font color='green'>").append(mutatedBit).append("</font>");
                            start = mutatedIndex + 1;
                        }
                    }
                    if (start < binaryString.length()) {
                        String post = binaryString.substring(start);
                        coloredString.append("<font color='black'>").append(post).append("</font>");
                    }

                    setText("<html>" + coloredString + "</html>");
                }

            } else if (column >= 3 && column <= 10) {
                if (column - 3 < (Crossover.crossoverPoint == -1 ? 0 : Crossover.crossoverPoint) && (irow == 0 || irow == 2)) {
                    setForeground(Color.RED);
                } else if (column - 3 >= (Crossover.crossoverPoint == -1 ? 8 : Crossover.crossoverPoint) && (irow == 1 || irow == 2)) {
                    setForeground(Color.BLUE);
                } else if (irow == 10 || irow == 12) {
                    boolean isMutated = false;
                    for (int mutatedIndex : Mutation.bitMutated) {
                        if (column - 3 == mutatedIndex) {
                            isMutated = true;
                            break;
                        }
                    }
                    if (isMutated) {
                        setForeground(Color.GREEN); // Setting color to green for mutated genes
                    } else {
                        setForeground(Color.BLACK); // Setting color to black for non-mutated genes
                    }
                }
            }

            return c;
        }
    }
}
