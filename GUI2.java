import javax.script.ScriptException;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("ALL")
public class GUI2 extends JFrame {
    //region attributes
    private static JFrame frame;
    private static JTextField nbpopTextField;
    private static JTextField nbMutationTextField;
    private static JTextField initialCustomFitnessFunctionTextField;
    private static JCheckBox avoidTwinsCheckBox;
    public static final String[] columnNames = {"Individual",
            "Decimal",
            "Binary",
            "Gen 1", "Gen 2", "Gen 3",
            "Gen 4", "Gen 5", "Gen 6",
            "Gen 7", "Gen 8"};
    private static final int xInitial = 640;
    private static int yInitial = 350;
    private static final int xParent1Final = 14;
    private static final int yParent1Final = 56;
    private static final int xParent2Final = 1250;
    private static final int yParent2Final = 56;
    private static int xParent1 = xInitial;
    private static double yParent1 = yInitial;
    private static int xParent2 = xInitial;
    private static double yParent2 = yInitial;
    private static int ySettings = yInitial;
    private static int yButtons = yInitial;

    private static JLabel nbpopLabel;
    private static JLabel initialCustomFitnessFunctionLabel;
    private static JLabel nbMutationLabel;
    private static JLabel avoidTwinsLabel;

    private static JButton generateButton;

    private static JLabel movingGene1;
    private static JLabel movingGene2;
    private static JLabel movingGene3;
    private static JLabel movingGene4;
    private static JLabel movingGene5;
    private static JLabel movingGene6;
    private static JLabel movingGene7;
    private static JLabel movingGene8;

    private static final int[] gene1LeftPosition = {226, 76};
    private static final int[] gene2LeftPosition = {226 + 55, 76};
    private static final int[] gene3LeftPosition = {226 + 55 + 55, 76};
    private static final int[] gene4LeftPosition = {226 + 55 + 55 + 55, 76};
    private static final int[] gene5LeftPosition = {226 + 55 + 55 + 55 + 55, 76};
    private static final int[] gene6LeftPosition = {226 + 55 + 55 + 55 + 55 + 55, 76};
    private static final int[] gene7LeftPosition = {226 + 55 + 55 + 55 + 55 + 55 + 55, 76};
    private static final int[] gene8LeftPosition = {226 + 55 + 55 + 55 + 55 + 55 + 55 + 55, 76};

    private static final int[] gene1RightPosition = {1462, 76};
    private static final int[] gene2RightPosition = {1462 + 55, 76};
    private static final int[] gene3RightPosition = {1462 + 55 + 55, 76};
    private static final int[] gene4RightPosition = {1462 + 55 + 55 + 55, 76};
    private static final int[] gene5RightPosition = {1462 + 55 + 55 + 55 + 55, 76};
    private static final int[] gene6RightPosition = {1462 + 55 + 55 + 55 + 55 + 55, 76};
    private static final int[] gene7RightPosition = {1462 + 55 + 55 + 55 + 55 + 55 + 55, 76};
    private static final int[] gene8RightPosition = {1462 + 55 + 55 + 55 + 55 + 55 + 55 + 55, 76};

    private static final int[] gene1MiddlePosition = {852, 370};
    private static final int[] gene2MiddlePosition = {852 + 55, 370};
    private static final int[] gene3MiddlePosition = {852 + 55 + 55, 370};
    private static final int[] gene4MiddlePosition = {852 + 55 + 55 + 55, 370};
    private static final int[] gene5MiddlePosition = {852 + 55 + 55 + 55 + 55, 370};
    private static final int[] gene6MiddlePosition = {852 + 55 + 55 + 55 + 55 + 55, 370};
    private static final int[] gene7MiddlePosition = {852 + 55 + 55 + 55 + 55 + 55 + 55, 370};
    private static final int[] gene8MiddlePosition = {852 + 55 + 55 + 55 + 55 + 55 + 55 + 55, 370};

    private static boolean childReady = false;

    private final JScrollPane scrollPane1;
    private final JScrollPane scrollPane2;
    private final JScrollPane scrollPane3;
    private JLayeredPane layeredPane;
    private JTable initialTable;
    private JTable parent1Table;
    private JTable parent2Table;
    private boolean twins = true;
    private final DefaultTableModel tableModel1;
    private final DefaultTableModel tableModel2;
    private final DefaultTableModel tableModel3;
    private int time = 2500;
    boolean applyCustomRenderer = false;
    boolean crossover = false;
    private static Individuals[] pop;
    private static int individualsNumber;
    private int mutationNumber;
    private String customFitnessFunction;
    private DefaultTableModel model;
    private int[] counter = {0, 0};
    //endregion


    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(GUI2::new);
    }


    public static class MyActionListener implements ActionListener {
        private final JScrollPane scrollPane1;
        private final JScrollPane scrollPane2;


        public MyActionListener(JScrollPane scrollPane1, JScrollPane scrollPane2) {
            this.scrollPane1 = scrollPane1;
            this.scrollPane2 = scrollPane2;
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {

            if (xParent1 > xParent1Final)
                xParent1 -= 2;
            if (yParent1 > yParent1Final)
                yParent1 -= 0.96;
            scrollPane1.setBounds(xParent1, (int) yParent1, 653, 43);

            if (xParent2 < xParent2Final)
                xParent2 += 2;
            if (yParent2 > -20 + yParent2Final)
                yParent2 -= 1.02;
            scrollPane2.setBounds(xParent2, 20 + (int) yParent2, 653, 43);

            if (ySettings > 56)
                ySettings--;
            nbpopLabel.setBounds(xInitial, -56 + ySettings, 180, 20);
            nbpopTextField.setBounds(186 + xInitial, -56 + ySettings, 30, 20);
            initialCustomFitnessFunctionLabel.setBounds(221 + xInitial, -56 + ySettings, 170, 20);
            initialCustomFitnessFunctionTextField.setBounds(376 + xInitial, -56 + ySettings, 80, 20);
            nbMutationLabel.setBounds(461 + xInitial, -56 + ySettings, 170, 20);
            nbMutationTextField.setBounds(636 + xInitial, -56 + ySettings, 15, 20);
            avoidTwinsLabel.setBounds(294 - 14 + xInitial, 32 - 56 + ySettings, 68, 16);
            avoidTwinsCheckBox.setBounds(367 - 14 + xInitial, 30 - 56 + ySettings, 21, 21);
            if (yButtons < 600)
                yButtons++;
            generateButton.setBounds(312 - 14 + xInitial, 384 - 56 + 2*yButtons - 350, 62, 26);

            if (xParent1 == xParent1Final && yParent1 == yParent1Final && xParent2 == xParent2Final && yParent2 == yParent2Final && ySettings == 56 && yButtons == 600) {
                ((Timer) arg0.getSource()).stop();
            }

        }
    }


    public GUI2() {
        frame = new JFrame();
        frame.getContentPane().setLayout(null);
        frame.setSize(1950, 1025);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(-10, 0, 1950, 1025);
        frame.setTitle("Genetic Algorithm");
        try {
            Image icon = Toolkit.getDefaultToolkit().getImage("DNA.jpg");
            // Set the window icon
            setIconImage(icon);
        } catch (Exception e) {
            e.printStackTrace();
        }


        frame.setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setPreferredSize(new Dimension(1950, 1025));
        frame.setResizable(false);

        // Add components for nbpop
        nbpopTextField = new JTextField(2);
        nbpopTextField.setText("15");

        // Add components for initialCustomFunction
        initialCustomFitnessFunctionTextField = new JTextField(7);
        initialCustomFitnessFunctionTextField.setText("(x + 3)^2 - 25");

        // Add components for nbpop
        nbMutationTextField = new JTextField(1);
        nbMutationTextField.setText("1");

        // Add components for twins
        avoidTwinsCheckBox = new JCheckBox();
        avoidTwinsCheckBox.setSelected(false);


        // Add a button to generate the initial population
        generateButton = new JButton("Start");
        layeredPane = new JLayeredPane();

        //region initialTable
        initialTable = new JTable() {
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }

            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);

                if (applyCustomRenderer) {
                    if (crossover) {
                        if (row == 0) {
                            return new MyCellRenderer().getTableCellRendererComponent(this, getModel().getValueAt(row, column),
                                    isCellSelected(row, column), false, row, column, 2, childReady);
                        }
                    } else {
                        if (row == 0) {
                            return new MyCellRenderer().getTableCellRendererComponent(this, getModel().getValueAt(row, column),
                                    isCellSelected(row, column), false, row, column, 12, childReady);
                        }
                    }
                }

                return c;
            }
        };

        initialTable.setFont(new Font("Monospaced", Font.BOLD, 14));
        initialTable.setSize(653, 323);
        initialTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        initialTable.setDragEnabled(false);
        initialTable.setRowSelectionAllowed(false);
        initialTable.setCellSelectionEnabled(false);
        initialTable.setShowGrid(true);
        initialTable.setGridColor(Color.BLACK);
        initialTable.setRowHeight(20);


        tableModel3 = new DefaultTableModel(columnNames, 0);
        initialTable.setModel(tableModel3);
        //getContentPane().add(outputTable);

        // Wrap the JTextPane in a JScrollPane to make it scrollable
        scrollPane3 = new JScrollPane(
                initialTable,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,   // Enable vertical scrollbar when needed
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED  // Enable horizontal scrollbar when needed
        );
        //frame.getContentPane().add(scrollPane);
        //scrollPane.setPreferredSize(new Dimension(653, 323));
        scrollPane3.setBounds(xInitial, yInitial, 653, 323);

        // Applying the custom cell renderer to your JTable


        initialTable.getColumnModel().getColumn(0).setPreferredWidth(75);
        initialTable.getColumnModel().getColumn(1).setPreferredWidth(55);
        initialTable.getColumnModel().getColumn(2).setPreferredWidth(80);
        initialTable.getColumnModel().getColumn(3).setPreferredWidth(55);
        initialTable.getColumnModel().getColumn(4).setPreferredWidth(55);
        initialTable.getColumnModel().getColumn(5).setPreferredWidth(55);
        initialTable.getColumnModel().getColumn(6).setPreferredWidth(55);
        initialTable.getColumnModel().getColumn(7).setPreferredWidth(55);
        initialTable.getColumnModel().getColumn(8).setPreferredWidth(55);
        initialTable.getColumnModel().getColumn(9).setPreferredWidth(55);
        initialTable.getColumnModel().getColumn(10).setPreferredWidth(55);

        initialTable.getColumnModel().getColumn(2).setCellRenderer(new MyCellRenderer());
        initialTable.repaint();

        //endregion

        //region parent1Table
        parent1Table = new JTable() {
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }

            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);

                if (applyCustomRenderer) {
                    if (crossover) {
                        if (row == 0) { // Replace with your own conditions
                            return new MyCellRenderer().getTableCellRendererComponent(this, getModel().getValueAt(row, column),
                                    isCellSelected(row, column), false, row, column, 0, childReady);
                        }
                    } else {
                        if (row == 0) { // Replace with your own conditions
                            return new MyCellRenderer().getTableCellRendererComponent(this, getModel().getValueAt(row, column),
                                    isCellSelected(row, column), false, row, column, 10, childReady);
                        }
                    }
                }

                return c;
            }
        };

        parent1Table.setFont(new Font("Monospaced", Font.BOLD, 14));
        parent1Table.setSize(653, 323);
        parent1Table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        parent1Table.setDragEnabled(false);
        parent1Table.setRowSelectionAllowed(false);
        parent1Table.setCellSelectionEnabled(false);
        parent1Table.setShowGrid(true);
        parent1Table.setGridColor(Color.BLACK);
        parent1Table.setRowHeight(20);


        tableModel1 = new DefaultTableModel(columnNames, 0);
        parent1Table.setModel(tableModel1);
        //getContentPane().add(outputTable);

        // Wrap the JTextPane in a JScrollPane to make it scrollable
        scrollPane1 = new JScrollPane(
                parent1Table,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,   // Enable vertical scrollbar when needed
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED  // Enable horizontal scrollbar when needed
        );
        //frame.getContentPane().add(scrollPane1);
        //scrollPane1.setPreferredSize(new Dimension(653, 323));
        scrollPane1.setBounds(xInitial, yInitial, 653, 323);
        scrollPane1.setVisible(false);
        // Applying the custom cell renderer to your JTable


        parent1Table.getColumnModel().getColumn(0).setPreferredWidth(75);
        parent1Table.getColumnModel().getColumn(1).setPreferredWidth(55);
        parent1Table.getColumnModel().getColumn(2).setPreferredWidth(80);
        parent1Table.getColumnModel().getColumn(3).setPreferredWidth(55);
        parent1Table.getColumnModel().getColumn(4).setPreferredWidth(55);
        parent1Table.getColumnModel().getColumn(5).setPreferredWidth(55);
        parent1Table.getColumnModel().getColumn(6).setPreferredWidth(55);
        parent1Table.getColumnModel().getColumn(7).setPreferredWidth(55);
        parent1Table.getColumnModel().getColumn(8).setPreferredWidth(55);
        parent1Table.getColumnModel().getColumn(9).setPreferredWidth(55);
        parent1Table.getColumnModel().getColumn(10).setPreferredWidth(55);

        parent1Table.getColumnModel().getColumn(2).setCellRenderer(new MyCellRenderer());
        parent1Table.repaint();

        //endregion

        //region parent2Table
        parent2Table = new JTable() {
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }

            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);

                if (applyCustomRenderer) {
                    if (crossover) {
                        if (row == 0) {
                            return new MyCellRenderer().getTableCellRendererComponent(this, getModel().getValueAt(row, column),
                                    isCellSelected(row, column), false, row, column, 1, childReady);
                        }
                    } else {
                        if (row == 0) {
                            return new MyCellRenderer().getTableCellRendererComponent(this, getModel().getValueAt(row, column),
                                    isCellSelected(row, column), false, row, column, 11, childReady);
                        }
                    }
                }

                return c;
            }
        };

        parent2Table.setFont(new Font("Monospaced", Font.BOLD, 14));
        parent2Table.setSize(653, 323);
        parent2Table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        parent2Table.setDragEnabled(false);
        parent2Table.setRowSelectionAllowed(false);
        parent2Table.setCellSelectionEnabled(false);
        parent2Table.setShowGrid(true);
        parent2Table.setGridColor(Color.BLACK);
        parent2Table.setRowHeight(20);


        tableModel2 = new DefaultTableModel(columnNames, 0);
        parent2Table.setModel(tableModel2);
        //getContentPane().add(outputTable);

        // Wrap the JTextPane in a JScrollPane to make it scrollable
        scrollPane2 = new JScrollPane(
                parent2Table,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,   // Enable vertical scrollbar when needed
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED  // Enable horizontal scrollbar when needed
        );
        //frame.getContentPane().add(scrollPane2);
        //scrollPane2.setPreferredSize(new Dimension(653, 323));
        scrollPane2.setBounds(xInitial, 20 + yInitial, 653, 323);
        scrollPane2.setVisible(false);
        // Applying the custom cell renderer to your JTable


        parent2Table.getColumnModel().getColumn(0).setPreferredWidth(75);
        parent2Table.getColumnModel().getColumn(1).setPreferredWidth(55);
        parent2Table.getColumnModel().getColumn(2).setPreferredWidth(80);
        parent2Table.getColumnModel().getColumn(3).setPreferredWidth(55);
        parent2Table.getColumnModel().getColumn(4).setPreferredWidth(55);
        parent2Table.getColumnModel().getColumn(5).setPreferredWidth(55);
        parent2Table.getColumnModel().getColumn(6).setPreferredWidth(55);
        parent2Table.getColumnModel().getColumn(7).setPreferredWidth(55);
        parent2Table.getColumnModel().getColumn(8).setPreferredWidth(55);
        parent2Table.getColumnModel().getColumn(9).setPreferredWidth(55);
        parent2Table.getColumnModel().getColumn(10).setPreferredWidth(55);

        parent2Table.getColumnModel().getColumn(2).setCellRenderer(new MyCellRenderer());
        parent2Table.repaint();

        //endregion


        generateButton.addActionListener(e -> {
            String nbpopText = nbpopTextField.getText();
            yInitial = 350;
            xParent1 = xInitial;
            yParent1 = yInitial;
            xParent2 = xInitial;
            yParent2 = yInitial;
            ySettings = yInitial;
            yButtons = yInitial;
            movingGene1.setVisible(false);
            movingGene2.setVisible(false);
            movingGene3.setVisible(false);
            movingGene4.setVisible(false);
            movingGene5.setVisible(false);
            movingGene6.setVisible(false);
            movingGene7.setVisible(false);
            movingGene8.setVisible(false);
            scrollPane3.setVisible(true);
            tableModel3.setRowCount(0);
            scrollPane1.setVisible(false);
            tableModel1.setRowCount(0);
            scrollPane2.setVisible(false);
            tableModel2.setRowCount(0);
            time = 2500;

            // Validation for nbpopTextField
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
                time = 2500;
                counter = new int[]{0, 0};
                generateInitialPopulation();
            } catch (ScriptException ex) {
                throw new RuntimeException(ex);
            }
        });

        generateButton.addActionListener(e -> {
            initialTable.getColumnModel().getColumn(2).setCellRenderer(new MyCellRenderer());
        });

        frame.setLayout(null);
        nbpopLabel = new JLabel("Enter the number of individuals:");
        nbpopLabel.setBounds(xInitial, -56 + yInitial, 180, 20);
        frame.add(nbpopLabel);

        nbpopTextField.setBounds(186 + xInitial, -56 + yInitial, 30, 20);
        frame.add(nbpopTextField);

        initialCustomFitnessFunctionLabel = new JLabel("Enter the Fitness Function:");
        initialCustomFitnessFunctionLabel.setBounds(221 + xInitial, -56 + yInitial, 170, 20);
        frame.add(initialCustomFitnessFunctionLabel);

        initialCustomFitnessFunctionTextField.setBounds(376 + xInitial, -56 + yInitial, 80, 20);
        frame.add(initialCustomFitnessFunctionTextField);

        nbMutationLabel = new JLabel("Enter the number of mutation:");
        nbMutationLabel.setBounds(461 + xInitial, -56 + yInitial, 170, 20);
        frame.add(nbMutationLabel);

        nbMutationTextField.setBounds(636 + xInitial, -56 + yInitial, 15, 20);
        frame.add(nbMutationTextField);

        avoidTwinsLabel = new JLabel("Avoid twins:");
        avoidTwinsLabel.setBounds(294 - 14 + xInitial, 32 - 56 + yInitial, 68, 16);
        frame.add(avoidTwinsLabel);

        avoidTwinsCheckBox.setBounds(367 - 14 + xInitial, 30 - 56 + yInitial, 21, 21);
        frame.add(avoidTwinsCheckBox);

        layeredPane.setBounds(0, 0, 1950, 1025);
        frame.add(layeredPane);

        generateButton.setBounds(312 - 14 + xInitial, 384 - 56 + yInitial, 62, 26);
        frame.add(generateButton);


        //region movingGenes

        movingGene1 = new JLabel("0");
        movingGene1.setFont(new Font("Monospaced", Font.BOLD, 14));
        frame.add(movingGene1);
        movingGene1.setVisible(false);

        movingGene2 = new JLabel("0");
        movingGene2.setFont(new Font("Monospaced", Font.BOLD, 14));
        frame.add(movingGene2);
        movingGene2.setVisible(false);

        movingGene3 = new JLabel("0");
        movingGene3.setFont(new Font("Monospaced", Font.BOLD, 14));
        frame.add(movingGene3);
        movingGene3.setVisible(false);

        movingGene4 = new JLabel("0");
        movingGene4.setFont(new Font("Monospaced", Font.BOLD, 14));
        frame.add(movingGene4);
        movingGene4.setVisible(false);

        movingGene5 = new JLabel("0");
        movingGene5.setFont(new Font("Monospaced", Font.BOLD, 14));
        frame.add(movingGene5);
        movingGene5.setVisible(false);

        movingGene6 = new JLabel("0");
        movingGene6.setFont(new Font("Monospaced", Font.BOLD, 14));
        frame.add(movingGene6);
        movingGene6.setVisible(false);

        movingGene7 = new JLabel("0");
        movingGene7.setFont(new Font("Monospaced", Font.BOLD, 14));
        frame.add(movingGene7);
        movingGene7.setVisible(false);

        movingGene8 = new JLabel("0");
        movingGene8.setFont(new Font("Monospaced", Font.BOLD, 14));
        frame.add(movingGene8);
        movingGene8.setVisible(false);

        //endregion


        // Add tables to layered pane, specifying Z-index
        layeredPane.add(scrollPane2, Integer.valueOf(1));
        layeredPane.add(scrollPane1, Integer.valueOf(2));
        layeredPane.add(scrollPane3, Integer.valueOf(3));
        layeredPane.add(movingGene1, Integer.valueOf(4));
        layeredPane.add(movingGene2, Integer.valueOf(5));
        layeredPane.add(movingGene3, Integer.valueOf(6));
        layeredPane.add(movingGene4, Integer.valueOf(7));
        layeredPane.add(movingGene5, Integer.valueOf(8));
        layeredPane.add(movingGene6, Integer.valueOf(9));
        layeredPane.add(movingGene7, Integer.valueOf(10));
        layeredPane.add(movingGene8, Integer.valueOf(11));

        layeredPane.setVisible(true);

        frame.pack();
        frame.setVisible(true);
    }

    private void generateInitialPopulation() throws ScriptException {
        scrollPane1.setVisible(false);
        scrollPane2.setVisible(false);
        scrollPane3.setVisible(true);
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

            model = (DefaultTableModel) initialTable.getModel();
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

            scrollPane3.setBounds(xParent1, yInitial, 653, 323);
            frame.add(scrollPane3);

            Timer timer = new Timer(2500 + 500 + 200 * individualsNumber, e -> {
                scrollPane1.setVisible(true);
                scrollPane2.setVisible(true);
                scrollPane3.setVisible(false);

                String binaryString = initialTable.getValueAt(0, 2).toString();
                Object[] rowData = new Object[11];
                rowData[0] = initialTable.getValueAt(0, 0);
                rowData[1] = initialTable.getValueAt(0, 1).toString();
                rowData[2] = binaryString;
                for (int j = 3; j <= 10; j++) {
                    rowData[j] = initialTable.getValueAt(0, j).toString();
                }
                tableModel1.addRow(rowData);

                binaryString = initialTable.getValueAt(1, 2).toString();
                rowData = new Object[11];
                rowData[0] = initialTable.getValueAt(1, 0);
                rowData[1] = initialTable.getValueAt(1, 1).toString();
                rowData[2] = binaryString;
                for (int j = 3; j <= 10; j++) {
                    rowData[j] = initialTable.getValueAt(1, j).toString();
                }
                tableModel2.addRow(rowData);


                Timer timer2 = new Timer(1000 / 120, new MyActionListener(scrollPane1, scrollPane2));
                timer2.start();
                ((Timer) e.getSource()).stop();  // Stop the timer
            });
            timer.start();

            timer = new Timer(2500 + 7000 + 200 * individualsNumber, e -> {
                tableModel3.setRowCount(0); // Clear existing rows

                String binaryString = "";
                Object[] rowData = new Object[11];
                rowData[0] = "";
                rowData[1] = "";
                rowData[2] = binaryString;
                for (int j = 3; j <= 10; j++) {
                    rowData[j] = "";
                }
                tableModel3.addRow(rowData);
                scrollPane3.setBounds(xInitial, yInitial, 653, 43);
                scrollPane3.setVisible(true);
                ((Timer) e.getSource()).stop();
            });
            timer.start();

            timer = new Timer(2500 + 7000 + 200 * individualsNumber, e -> {
                Individuals Result;
                if (Crossover.crossoverPoint == -1) {
                    // Mutation case

                    String parent = parent1Table.getValueAt(0, 2).toString();
                    // Create a new Individual object with the mutated string
                    Individuals MResult = pop[0];
                    try {
                        MResult = new Individuals(Mutation.mutation(parent, mutationNumber), 2, customFitnessFunction);
                    } catch (ScriptException ex) {
                        throw new RuntimeException(ex);
                    }
                    JLabel[] movingGenes = {movingGene1, movingGene2, movingGene3, movingGene4, movingGene5, movingGene6, movingGene7, movingGene8};
                    int[][] genePositions = {gene1LeftPosition, gene2LeftPosition, gene3LeftPosition, gene4LeftPosition, gene5LeftPosition, gene6LeftPosition, gene7LeftPosition, gene8LeftPosition};

                    int[] arrayGenes = MResult.getArrayGenes();

                    Set<Integer> mutatedIndices = new HashSet<>();
                    for (int index : Mutation.bitMutated) {
                        mutatedIndices.add(index);
                    }

                    for (int i = 0; i < movingGenes.length; i++) {
                        movingGenes[i].setText(arrayGenes[i] + "");
                        movingGenes[i].setBounds(
                                genePositions[i][0],
                                genePositions[i][1],
                                20, 20
                        );
                        movingGenes[i].setForeground(mutatedIndices.contains(i) ? Color.GREEN : Color.BLACK); // Color the mutated genes green
                        movingGenes[i].setVisible(true);
                    }

                    int[][] middlePositions = {gene1MiddlePosition, gene2MiddlePosition, gene3MiddlePosition, gene4MiddlePosition, gene5MiddlePosition, gene6MiddlePosition, gene7MiddlePosition, gene8MiddlePosition};
                    Timer timer2 = new Timer(1000 / 120, e1 -> {

                        for (int i = 0; i < movingGenes.length; i++) {
                            JLabel movingGene = movingGenes[i];
                            int[] middlePosition = middlePositions[i];
                            if (movingGene.getX() < middlePosition[0]) {
                                movingGene.setBounds(movingGene.getX() + 2, movingGene.getY(), 20, 20);
                            } else if (movingGene.getX() > middlePosition[0]) {
                                movingGene.setBounds(movingGene.getX() - 2, movingGene.getY(), 20, 20);
                            }
                            if (movingGene.getY() < middlePosition[1]) {
                                movingGene.setBounds(movingGene.getX(), (int) (movingGene.getY() + 1), 20, 20);
                            }
                        }

                    });
                    timer2.start();


                    Result = MResult;
                } else {
                    //crossover case

                    String result = Crossover.crossover(parent1Table.getValueAt(0, 2).toString(), parent2Table.getValueAt(0, 2).toString());
                    Individuals CResult = null;
                    try {
                        CResult = new Individuals(result, 2, customFitnessFunction);
                    } catch (ScriptException ex) {
                        throw new RuntimeException(ex);
                    }

                    JLabel[] movingGenes = {movingGene1, movingGene2, movingGene3, movingGene4, movingGene5, movingGene6, movingGene7, movingGene8};
                    int[][] geneLeftPositions = {gene1LeftPosition, gene2LeftPosition, gene3LeftPosition, gene4LeftPosition, gene5LeftPosition, gene6LeftPosition, gene7LeftPosition, gene8LeftPosition};
                    int[][] geneRightPositions = {gene1RightPosition, gene2RightPosition, gene3RightPosition, gene4RightPosition, gene5RightPosition, gene6RightPosition, gene7RightPosition, gene8RightPosition};

                    int crossoverPoint = Crossover.crossoverPoint;
                    int[] arrayGenes = CResult.getArrayGenes();

                    for (int i = 0; i < movingGenes.length; i++) {
                        movingGenes[i].setText(arrayGenes[i] + "");
                        movingGenes[i].setBounds(
                                i < crossoverPoint ? geneLeftPositions[i][0] : geneRightPositions[i][0],
                                i < crossoverPoint ? geneLeftPositions[i][1] : geneRightPositions[i][1],
                                20, 20
                        );
                        movingGenes[i].setForeground(i < crossoverPoint ? Color.RED : Color.BLUE);
                        movingGenes[i].setVisible(true);
                    }
                    System.out.println(Crossover.crossoverPoint);


                    int[][] middlePositions = {gene1MiddlePosition, gene2MiddlePosition, gene3MiddlePosition, gene4MiddlePosition, gene5MiddlePosition, gene6MiddlePosition, gene7MiddlePosition, gene8MiddlePosition};
                    Timer timer2 = new Timer(1000 / 120, e1 -> {

                        for (int i = 0; i < movingGenes.length; i++) {
                            JLabel movingGene = movingGenes[i];
                            int[] middlePosition = middlePositions[i];
                            if (movingGene.getX() < middlePosition[0]) {
                                movingGene.setBounds(movingGene.getX() + 2, movingGene.getY(), 20, 20);
                            } else if (movingGene.getX() > middlePosition[0]) {
                                movingGene.setBounds(movingGene.getX() - 2, movingGene.getY(), 20, 20);
                            }
                            if (movingGene.getY() < middlePosition[1]) {
                                movingGene.setBounds(movingGene.getX(), (int) (movingGene.getY() + 1), 20, 20);
                            }
                        }


                    });
                    timer2.start();

                    Result = CResult;

                }
                Individuals finalCResult = Result;
                Timer timer2 = new Timer(4400, e1 -> {
                    tableModel3.setRowCount(0); // Clear existing rows
                    initialTable.setVisible(true);
                    String binaryString = finalCResult.getBinaryGenes();
                    Object[] rowData = new Object[11];
                    rowData[0] = (Crossover.crossoverPoint == -1 ? "M" + counter[0] : "C" + counter[1]++);
                    rowData[1] = finalCResult.getDecimalGenes();
                    rowData[2] = binaryString;
                    for (int j = 3; j <= 10; j++) {
                        rowData[j] = finalCResult.getArrayGenes()[j - 3];
                    }
                    tableModel3.addRow(rowData);

                    movingGene1.setVisible(true);
                    movingGene2.setVisible(true);
                    movingGene3.setVisible(true);
                    movingGene4.setVisible(true);
                    movingGene5.setVisible(true);
                    movingGene6.setVisible(true);
                    movingGene7.setVisible(true);
                    movingGene8.setVisible(true);

                    childReady = true;
                    applyCustomRenderer = true;
                    ((Timer) e1.getSource()).stop();
                });
                timer2.start();

                ((Timer) e.getSource()).stop();
            });
            timer.start();
        }
    }

    private void evolvingPopulation() {
        twins = !avoidTwinsCheckBox.isSelected();
        applyCustomRenderer = false;
        Timer timer = new Timer(time - 500, e -> {

            getTwoBestIndividuals();
            ((Timer) e.getSource()).stop();  // Stop the timer
        });
        timer.start();

        new Timer(time + 500 + 200 * individualsNumber, e -> {
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
                    System.out.println(pop[2].getBinaryGenes());
                }
            } while (!twins && (pop[2].getDecimalGenes() == best[0].getDecimalGenes() || pop[2].getDecimalGenes() == best[1].getDecimalGenes()));

            pop[0] = best[0];
            pop[1] = best[1];
            individualsNumber = 3;
            crossoverRowData[1] = pop[2].getDecimalGenes();
            crossoverRowData[2] = pop[2].getBinaryGenes();
            int[] crossoverGenes = pop[2].getArrayGenes();
            for (int j = 3; j <= 10; j++) {
                crossoverRowData[j] = crossoverGenes[j - 3];
            }
            tableModel3.addRow(crossoverRowData);
            ((Timer) e.getSource()).stop();  // Stop the initial delay timer
        }).start();

        new Timer(2500 + 7000 + 200 * individualsNumber, e -> {
            applyCustomRenderer = true;
            ((Timer) e.getSource()).stop();
        }).start();
        time = 0;
    }

    /**
     * To get the two best individuals
     */
    private void getTwoBestIndividuals() {
        Individuals[] best = Selection.selection(pop, pop.length);
        System.out.println(best[0].getDecimalGenes() + "    " + best[1].getDecimalGenes());
        AtomicInteger nbBest0 = new AtomicInteger();
        AtomicInteger nbBest1 = new AtomicInteger();
        AtomicInteger count = new AtomicInteger();
        Timer timer1 = new Timer(200, e1 -> {
            model = (DefaultTableModel) initialTable.getModel();
            if (tableModel3.getRowCount() > 2) {
                if ((Integer) tableModel3.getValueAt(count.get(), 1) == best[0].getDecimalGenes() && nbBest0.get() == 0) {
                    //System.out.println(tableModel.getValueAt(count.get(), 1)+"kept");
                    count.getAndIncrement();
                    nbBest0.getAndIncrement();
                } else if ((Integer) tableModel3.getValueAt(count.get(), 1) == best[1].getDecimalGenes() && nbBest1.get() == 0) {
                    //System.out.println(tableModel.getValueAt(count.get(), 1)+"kept");
                    count.getAndIncrement();
                    nbBest1.getAndIncrement();
                } else {
                    //System.out.println(tableModel.getValueAt(count.get(), 1)+"deleted");
                    tableModel3.removeRow(count.get());
                }
            } else {
                if ((Integer) tableModel3.getValueAt(0, 1) != best[0].getDecimalGenes()) {  //exchange the two first rows
                    Object[] exchangeRowData = new Object[11];
                    exchangeRowData[0] = tableModel3.getValueAt(0, 0);
                    exchangeRowData[1] = best[1].getDecimalGenes();
                    exchangeRowData[2] = best[1].getBinaryGenes();
                    for (int j = 3; j <= 10; j++) {
                        exchangeRowData[j] = best[1].getArrayGenes()[j - 3];
                    }
                    tableModel3.removeRow(0);
                    tableModel3.addRow(exchangeRowData);

                }
                ((Timer) e1.getSource()).stop();
            }
        });
        timer1.start();
    }

    static class MyCellRenderer extends DefaultTableCellRenderer {

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column, int irow, boolean childReady) {

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
                } else if (irow == 2 && childReady) {
                    String coloredPart = binaryString.substring(0, Crossover.crossoverPoint == -1 ? 0 : Crossover.crossoverPoint);
                    String middle = binaryString.substring(Crossover.crossoverPoint == -1 ? 0 : Crossover.crossoverPoint, Crossover.crossoverPoint == -1 ? 8 : Crossover.crossoverPoint);
                    String rest = binaryString.substring(Crossover.crossoverPoint == -1 ? 8 : Crossover.crossoverPoint);
                    setText("<html><font color='red'>" + coloredPart + "</font><font color='black'>" + middle + "</font><font color='blue'>" + rest + "</html>");
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
                if (column - 3 < (Crossover.crossoverPoint == -1 ? 0 : Crossover.crossoverPoint) && irow == 0) {
                    setForeground(Color.RED);
                } else if (column - 3 >= (Crossover.crossoverPoint == -1 ? 8 : Crossover.crossoverPoint) && irow == 1) {
                    setForeground(Color.BLUE);
                } else if (irow == 10) {
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