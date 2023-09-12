import javax.script.ScriptException;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class GUI extends JFrame {
    private final JTextField nbpopTextField;
    private final JTextField initialCustomFitnessFunctionTextField;
    private JTable outputTable;
    private final DefaultTableModel tableModel;
    private int time = 2500;
    boolean applyCustomRenderer = false;
    boolean crossover = false;
    private Individuals[] pop;
    private int individualsNumber;
    private String customFitnessFunction;
    private DefaultTableModel model;
    private int[] counter = {0, 0};


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GUI();
            }
        });
    }

    public GUI() {
        setBounds(700, 150, 600, 900);
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

        setPreferredSize(new Dimension(600, 825));
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

        MyCellRenderer myCellRenderer = new MyCellRenderer();

        generateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    counter = new int[]{0, 0};
                    generateInitialPopulation();
                } catch (ScriptException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        generateButton.addActionListener(e -> {
            // Set your custom cell renderer here
            outputTable.getColumnModel().getColumn(2).setCellRenderer(new MyCellRenderer());
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
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);

                if (applyCustomRenderer) {
                    if(crossover) {
                        if (row == 0) { // Replace with your own conditions
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
                        if (row == 0) { // Replace with your own conditions
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

        //region table
        outputTable.setFont(new Font("Monospaced", Font.BOLD, 14));
        outputTable.setSize(523, 700);
        outputTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        outputTable.setDragEnabled(false);
        outputTable.setRowSelectionAllowed(false);
        outputTable.setCellSelectionEnabled(false);
        outputTable.setShowGrid(true);
        outputTable.setGridColor(Color.BLACK);
        outputTable.setRowHeight(20);
        //endregion



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
        scrollPane.setPreferredSize(new Dimension(533, 700));

        // Applying the custom cell renderer to your JTable


        outputTable.getColumnModel().getColumn(0).setPreferredWidth(75);
        outputTable.getColumnModel().getColumn(1).setPreferredWidth(55);
        outputTable.getColumnModel().getColumn(2).setPreferredWidth(80);
        outputTable.getColumnModel().getColumn(3).setPreferredWidth(40);
        outputTable.getColumnModel().getColumn(4).setPreferredWidth(40);
        outputTable.getColumnModel().getColumn(5).setPreferredWidth(40);
        outputTable.getColumnModel().getColumn(6).setPreferredWidth(40);
        outputTable.getColumnModel().getColumn(7).setPreferredWidth(40);
        outputTable.getColumnModel().getColumn(8).setPreferredWidth(40);
        outputTable.getColumnModel().getColumn(9).setPreferredWidth(40);
        outputTable.getColumnModel().getColumn(10).setPreferredWidth(40);

        outputTable.getColumnModel().getColumn(2).setCellRenderer(new MyCellRenderer());
        outputTable.repaint();

        //add a next button
        JButton nextButton = new JButton("Next");
        add(nextButton);
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                evolvingPopulation();
            }
        });
        nextButton.addActionListener(e -> {
            // Set your custom cell renderer here
            outputTable.getColumnModel().getColumn(2).setCellRenderer(new MyCellRenderer());
        });

        JButton skipButton = new JButton("Skip");
        add(skipButton);
        skipButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    evolvedPopulation();
                } catch (ScriptException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        skipButton.addActionListener(e -> {
            // Set your custom cell renderer here
            outputTable.getColumnModel().getColumn(2).setCellRenderer(new MyCellRenderer());
        });



        pack();
        setVisible(true);
    }


    private void generateInitialPopulation() throws ScriptException {
        applyCustomRenderer = false;

        individualsNumber = Integer.parseInt(nbpopTextField.getText());
        String initialCustomFitnessFunction = initialCustomFitnessFunctionTextField.getText();
        if (initialCustomFitnessFunction.contains("^")) {
            customFitnessFunction = Main.reformat(initialCustomFitnessFunction);
        } else {
            customFitnessFunction = initialCustomFitnessFunction;
        }

        pop = new Individuals[individualsNumber];
        for (int i = 0; i < individualsNumber; i++) {
            pop[i] = new Individuals(customFitnessFunction);
            //System.out.println(pop[i].getDecimalGenes());
        }

        model = (DefaultTableModel) outputTable.getModel();
        model.setRowCount(0); // Clear existing rows

        for (int i = 0; i < individualsNumber; i++) {

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
        evolvingPopulation();
    }

    private void evolvingPopulation() {
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
            if (rand.nextInt(10) < 3) {
                counter[0]++;
                crossoverRowData[0] = "M" + counter[0];
                crossover = false;
                try {
                    pop[2] = new Individuals(Mutation.mutation(best[0].getBinaryGenes(), 2), 2,customFitnessFunction);
                    System.out.println("Mutations index : "+Mutation.bitMutated[0] + ";" + Mutation.bitMutated[1]);
                } catch (ScriptException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                counter[1]++;
                crossoverRowData[0] = "C" + counter[1];
                crossover = true;
                try {
                    pop[2] = new Individuals(Crossover.crossover(best[0].getBinaryGenes(), best[1].getBinaryGenes()), 2,customFitnessFunction);
                } catch (ScriptException ex) {
                    throw new RuntimeException(ex);
                }
                System.out.println(pop[2].getBinaryGenes());
            }
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

        new Timer(time + 500 + 200* individualsNumber, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                applyCustomRenderer = true;
                ((Timer)e.getSource()).stop();
            }
        }).start();
        time = 0;

    }
    private void evolvedPopulation() throws ScriptException {
        model.setRowCount(0); // Clear existing rows
        while (pop[0].getFitness() != 0 && pop[1].getFitness() != 0 && counter[0] + counter[1] < 1000000) {
            pop = Main.evolution(pop, individualsNumber, counter, customFitnessFunction, 1);
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


    Timer animationTimer;
    Individuals[] targetState;

    private void getTwoBestIndividuals() {
        Individuals[] best = Selection.selection(pop, pop.length);
        System.out.println(best[0].getDecimalGenes()+"    "+best[1].getDecimalGenes());
        AtomicInteger nbBest0 = new AtomicInteger();
        AtomicInteger nbBest1 = new AtomicInteger();
        AtomicInteger count = new AtomicInteger();
        Timer timer1 = new Timer(200, e1 -> {
            model = (DefaultTableModel) outputTable.getModel();
            if (tableModel.getRowCount() > 2) {
                if((Integer)tableModel.getValueAt(count.get(), 1) == best[0].getDecimalGenes() && nbBest0.get() == 0){
                    //System.out.println(tableModel.getValueAt(count.get(), 1)+"kept");
                    count.getAndIncrement();
                    nbBest0.getAndIncrement();
                }else if((Integer)tableModel.getValueAt(count.get(), 1) == best[1].getDecimalGenes() && nbBest1.get() == 0) {
                    //System.out.println(tableModel.getValueAt(count.get(), 1)+"kept");
                    count.getAndIncrement();
                    nbBest1.getAndIncrement();
                }
                else{
                    //System.out.println(tableModel.getValueAt(count.get(), 1)+"deleted");
                    tableModel.removeRow(count.get());
                }
            } else {
                if((Integer)tableModel.getValueAt(0, 1) != best[0].getDecimalGenes()){  //exchange the two first rows
                    Object[] exchangeRowData = new Object[11];
                    exchangeRowData[0] = tableModel.getValueAt(0,0);;
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

    // Custom cell renderer
    static class MyCellRenderer extends DefaultTableCellRenderer {

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column, int irow) {

            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (column == 2) {
                String binaryString = value.toString();
                if(irow == 0){
                    String coloredPart = binaryString.substring(0, Crossover.crossoverPoint == -1 ? 0 : Crossover.crossoverPoint);
                    String rest = binaryString.substring(Crossover.crossoverPoint == -1 ? 8 : Crossover.crossoverPoint, 8);
                    setText("<html><font color='red'>" + coloredPart + "</font><font color='black'>" + rest + "</font></html>");
                } else if (irow == 1){
                    String rest = binaryString.substring(0, Crossover.crossoverPoint == -1 ? 0 : Crossover.crossoverPoint);
                    String coloredPart = binaryString.substring(Crossover.crossoverPoint == -1 ? 8 : Crossover.crossoverPoint);
                    setText("<html><font color='black'>" + rest + "</font><font color='blue'>" + coloredPart + "</font></html>");
                }else if (irow == 2){
                    String coloredPart = binaryString.substring(0, Crossover.crossoverPoint == -1 ? 0 : Crossover.crossoverPoint);
                    String middle = binaryString.substring(Crossover.crossoverPoint == -1 ? 0 : Crossover.crossoverPoint, Crossover.crossoverPoint == -1 ? 8 : Crossover.crossoverPoint);
                    String rest = binaryString.substring(Crossover.crossoverPoint == -1 ? 8 : Crossover.crossoverPoint);
                    setText("<html><font color='red'>" + coloredPart + "</font><font color='black'>" + middle + "</font><font color='blue'>" + rest + "</html>");
                } else if (irow == 10 || irow == 12) {
                    StringBuilder coloredString = new StringBuilder();
                    int start = 0;

                    for (int mutatedIndex : Mutation.bitMutated) {
                        // Make sure the index is within range before accessing binaryString
                        if (mutatedIndex >= 0 && mutatedIndex < binaryString.length()) {
                            String pre = binaryString.substring(start, mutatedIndex);
                            String mutatedBit = binaryString.substring(mutatedIndex, mutatedIndex + 1);
                            coloredString.append("<font color='black'>").append(pre).append("</font>");
                            coloredString.append("<font color='red'>").append(mutatedBit).append("</font>");
                            start = mutatedIndex + 1;
                        }
                    }

                    // Add this line to handle the case when start is greater than or equal to the string length.
                    if (start < binaryString.length()) {
                        String post = binaryString.substring(start);
                        coloredString.append("<font color='black'>").append(post).append("</font>");
                    }

                    setText("<html>" + coloredString.toString() + "</html>");
                }
            }
            return c;
        }
    }
}
