import javax.script.ScriptException;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class GUI extends JFrame {
    private final JTextField nbpopTextField;
    private final JTextField initialCustomFitnessFunctionTextField;
    private JTable outputTable;
    DefaultTableModel tableModel;
    private Timer timer;
    boolean applyCustomRenderer = false;  // Instance variable

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GUI();
            }
        });
    }

    public GUI() {
        setBounds(0, 0, 600, 800);
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

        setPreferredSize(new Dimension(600, 800));
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
                    if (row == 0) { // Replace with your own conditions
                        return new MyCellRenderer().getTableCellRendererComponent(this, getModel().getValueAt(row, column),
                                isCellSelected(row, column), false, row, column, 0);
                    } else if (row == 1) {
                        return new MyCellRenderer().getTableCellRendererComponent(this, getModel().getValueAt(row, column),
                                isCellSelected(row, column), false, row, column, 1);
                    } else if(row == 2){
                        return new MyCellRenderer().getTableCellRendererComponent(this, getModel().getValueAt(row, column),
                                isCellSelected(row, column), false, row, column, 2);
                    }
                }

                return c;
            }
        };

        outputTable.setFont(new Font("Monospaced", Font.BOLD, 14));
        outputTable.setSize(523, 700);
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


        pack();
        setVisible(true);
    }


    private void generateInitialPopulation() throws ScriptException {
        applyCustomRenderer = false;

        int individualsNumbers = Integer.parseInt(nbpopTextField.getText());
        String initialCustomFitnessFunction = initialCustomFitnessFunctionTextField.getText();
        String customFitnessFunction;
        if (initialCustomFitnessFunction.contains("^")) {
            customFitnessFunction = Main.reformat(initialCustomFitnessFunction);
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

        Timer timer = new Timer(2000, e -> {
            //updateAndSortTable(pop);
            //animateTableSorting(pop);
            getTwoBestIndividuals(pop);
            ((Timer)e.getSource()).stop();  // Stop the timer
        });
        timer.start();

        new Timer(3000 + 200*individualsNumbers, e -> {
            // Adding the crossover row
            Individuals Result = null;
            Individuals[] best = Selection.selection(pop, pop.length);
            Random rand = new Random();
            if (rand.nextInt(10) < 3) {
                System.out.println("Mutation");
                try {
                    Result = new Individuals(Mutation.mutation(best[0].getBinaryGenes(), 1), 2,customFitnessFunction);
                } catch (ScriptException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                System.out.println("Crossover");
                try {
                    Result = new Individuals(Crossover.crossover(best[0].getBinaryGenes(), best[1].getBinaryGenes()), 2,customFitnessFunction);
                } catch (ScriptException ex) {
                    throw new RuntimeException(ex);
                }
                System.out.println(Result.getBinaryGenes());
            }

            Object[] crossoverRowData = new Object[11];
            crossoverRowData[0] = "Crossover";
            crossoverRowData[1] = Result.getDecimalGenes();
            crossoverRowData[2] = Result.getBinaryGenes();
            int[] crossoverGenes = Result.getArrayGenes();
            for (int j = 3; j <= 10; j++) {
                crossoverRowData[j] = crossoverGenes[j - 3];
            }
            model.addRow(crossoverRowData);
        ((Timer)e.getSource()).stop();  // Stop the initial delay timer
        }).start();

        new Timer(3000 + 200*individualsNumbers, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                applyCustomRenderer = true;
                ((Timer)e.getSource()).stop();
            }
        }).start();

    }


    Timer animationTimer;
    Individuals[] targetState;

    private void getTwoBestIndividuals(Individuals[] pop) {
        Individuals[] best = Selection.selection(pop, pop.length);
        System.out.println(best[0].getDecimalGenes()+"    "+best[1].getDecimalGenes());
        AtomicInteger nbBest0 = new AtomicInteger();
        AtomicInteger nbBest1 = new AtomicInteger();
        AtomicInteger count = new AtomicInteger();
        Timer timer1 = new Timer(200, e1 -> {
            DefaultTableModel model = (DefaultTableModel) outputTable.getModel();
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
        private boolean colorText = true;

        public void setColorText(boolean colorText) {
            this.colorText = colorText;
        }
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column, int irow) {

            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (column == 2) {
                String binaryString = value.toString();

                if (!colorText) {
                    // set all text to black
                    setText("<html><font color='black'>" + value.toString() + "</font></html>");
                }else if(irow == 0){
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
                }else{
                    String all = binaryString.substring(0,8);
                    setText("<html><font color='black'>"+all+"</font></html>");
                }
            }
            return c;
        }
    }






    /*private void animateTableSorting(final Individuals[] pop) {
        // Sort the 'pop' array based on 'Decimal' value
        targetState = pop.clone();
        Arrays.sort(targetState, Comparator.comparing(Individuals::getDecimalGenes));

        animationTimer = new Timer(100, new ActionListener() {
        int frame = 0;
        int totalFrames = 10; // Choose the number of frames that gives you the desired speed

            @Override
            public void actionPerformed(ActionEvent e) {
                if (frame >= totalFrames) {
                    // Stop the animation
                    ((Timer) e.getSource()).stop();
                    updateTableForFrame(frame, totalFrames, pop, targetState);
                    //updateAndSortTable(targetState); // Ensure the final state is correct
                    return;
                }
                // Calculate the next frame and update the table
                updateTableForFrame(frame, totalFrames, pop, targetState);
                frame++;
            }
        });

        animationTimer.start();
    }

    private void updateTableForFrame(int frame, int totalFrames, Individuals[] initialState, Individuals[] finalState) {
        // Clear existing rows
        tableModel.setRowCount(0);
        // Calculate and set the data for this frame
        for (int i = 0; i < initialState.length; i++) {
            // Here, calculate the data for row 'i' based on its initial and final state
            // and the current frame. For example:
            int initialDecimal = initialState[i].getDecimalGenes();
            int finalDecimal = finalState[i].getDecimalGenes();
            int animatedDecimal = initialDecimal + (finalDecimal - initialDecimal) * frame / totalFrames;

            // Add row to table model
            Individuals ind = initialState[i];
            Object[] row = new Object[]{
                    (i + 1),
                    animatedDecimal,
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

            // Notify the table that the data has changed
            tableModel.fireTableDataChanged();

        }

    }*/
}
