import javax.script.ScriptException;
import java.util.Random;
import java.util.Scanner;

public class Main {
    /**
     * Main method to execute the Genetic Algorithm program.
     *
     * @param args Command-line arguments (not used).
     * @throws ScriptException If a script error occurs.
     */
    public static void main(String[] args) throws ScriptException {

        // region Initialization
        boolean twins;
        Scanner sc = new Scanner(System.in);
        Display.clear();
        Display.waiting();
        sc.nextLine();
        // endregion

        do {
            // region Individuals Number Input
            Display.clear();
            System.out.println(Const.defaultValue + "Enter the number of individuals in the population or press "
                    + Const.red + Const.underline + "Enter\033[0m" + Const.defaultValue + " for default (15):");

            int valid = 0;
            int individualsNumber = -1; // Initialize to an invalid value

            // Loop to choose population size
            do {
                String individualsNumberStr = sc.nextLine();
                if (individualsNumberStr.isEmpty()) {
                    individualsNumber = 15;
                    break;
                }

                try {
                    individualsNumber = Integer.parseInt(individualsNumberStr);
                    if (individualsNumber <= 2) {
                        System.out.println(Const.defaultValue
                                + "The population size must be at least 3. Please try again.");
                    } else {
                        valid = 1;
                    }
                } catch (NumberFormatException e) {
                    System.out.println(Const.defaultValue + "That's not an integer. Please enter an integer.");
                }
            } while (valid == 0);
            // endregion

            // region Custom Fitness Function Input
            Display.clear();
            String initialCustomFitnessFunction;
            String customFitnessFunction;

            Scanner scanner = new Scanner(System.in);
            valid = 0;
            System.out.println(Const.defaultValue
                    + "Enter your custom fitness function using 'x' as the variable [e.g., (x + 3)^2 - 25] or press "
                    + Const.red + Const.underline + "Enter" + Const.defaultValue + " for default:");
            do {
                initialCustomFitnessFunction = scanner.nextLine();

                // Use default value if user entered nothing
                if (initialCustomFitnessFunction.isEmpty()) {
                    initialCustomFitnessFunction = "(x + 3)^2 - 25";
                    break;
                }
                if (!initialCustomFitnessFunction.contains("x")) {
                    System.out
                            .println(Const.defaultValue + "Your custom fitness function must contain the variable 'x'");
                    continue; // Loop will restart, skipping the following lines
                }

                for (char c : initialCustomFitnessFunction.toCharArray()) {
                    if (!Character.isDigit(c) && "x+*-/^()sincoexpMath. ".indexOf(c) == -1) {
                        System.out.println(Const.defaultValue + "Invalid character detected: " + c);
                        valid = 0;
                        break; // Exit the 'for' loop
                    }
                    valid = 1;
                }

            } while (valid == 0);
            // endregion

            // region Number of Genes to Mutate Input
            Display.clear();
            int numberGenes = 1;
            System.out.println(Const.defaultValue + "Enter the number of genes to mutate or press " + Const.red
                    + Const.underline + "Enter" + Const.defaultValue + " for default (1):");
            do {
                String numberGenesStr = scanner.nextLine();
                if (numberGenesStr.isEmpty()) {
                    numberGenes = 1;
                    break;
                }
                try {
                    numberGenes = Integer.parseInt(numberGenesStr);
                    if (numberGenes < 0 || numberGenes > 8) {
                        System.out.println(Const.defaultValue
                                + "The number of genes to mutate must be between 0 and 8. Please try again.");
                    } else {
                        valid = 1;
                    }
                } catch (NumberFormatException e) {
                    System.out.println(Const.defaultValue + "That's not an integer. Please enter an integer.");
                }
            } while (valid == 0);
            // endregion

            // region Twins Input
            Display.clear();
            if (individualsNumber < 256) {
                System.out.println(Const.defaultValue + "Do you want twins? (y/n) Press " + Const.red + Const.underline
                        + "Enter" + Const.defaultValue + " for default (y):");
                do {
                    String twinsStr = scanner.nextLine();

                    if (twinsStr.equalsIgnoreCase("n")) {
                        twins = false;
                        break;
                    } else if (twinsStr.equalsIgnoreCase("y")) {
                        twins = true;
                        break;
                    } else if (twinsStr.isEmpty()) {
                        twins = true;
                        break;
                    }
                    System.out.println(Const.defaultValue + "Please enter y or n");
                } while (true);
            } else twins = true;
            // endregion

            // region Initial Display
            Display.clear();

            System.out.println(Const.defaultValue + "Using the following fitness function: " + Const.bold + Const.red + initialCustomFitnessFunction + "\n");
            // Reformat the custom fitness function for evaluation
            customFitnessFunction = reformat(initialCustomFitnessFunction);

            // Create an array of Individuals with binary genes
            System.out.println(Const.defaultValue + "Using a population size of " + Const.underline + Const.red + individualsNumber + Const.defaultValue + ":\n");
            Individuals[] pop = new Individuals[individualsNumber];
            for (int i = 0; i < individualsNumber; i++) {
                pop[i] = new Individuals(customFitnessFunction);
                for (int j = 0; j < i; j++) {
                    if (!twins) {
                        if (pop[i].getDecimalGenes() == pop[j].getDecimalGenes()) {
//                            System.out.println("doing first population");
                            i--;
                            break;
                        }
                    }
                }
                System.out.println(Const.underline + Const.cyan + "Individual " + (i + 1) + Const.defaultValue + ": " + pop[i].getDecimalGenes() + " - " + pop[i].getBinaryGenes() + " -> " + pop[i].getFitness());
            }
            // endregion

            // region Evolution
            // Initialize variables for genetic algorithm
            Individuals[] best;
            int[] counter = { 0, 0 };

            // Perform initial selection
            best = Selection.selection(pop, individualsNumber);

            // Use the Newton algorithm to find the root of the function
            if (best[0].getFitness() == 0) {
                System.out.println(Const.defaultValue + "The best individual is: " + best[0].getDecimalGenes()
                        + " and his fitness score is: "
                        + best[0].getFitness());
                System.exit(0);
            }

            // Check if a root exists for the fitness function
            if (Individuals.findRoot(customFitnessFunction) == null) {
                System.out.println(Const.defaultValue
                        + "\nThere is no root found for the following function between 0 and 255\nf(x) -> "
                        + initialCustomFitnessFunction);
            } else {
                System.out.println(Const.defaultValue + "\nThe root of the function is: "
                        + Individuals.findRoot(customFitnessFunction));
            }

            // Run the genetic algorithm until a termination condition is met
            while (pop[0].getFitness() != 0 && pop[1].getFitness() != 0 && counter[0] + counter[1] < 10000) {
                pop = evolution(pop, individualsNumber, counter, customFitnessFunction, numberGenes, twins);
                individualsNumber = 3;
            }
            // endregion

            // Print results
            System.out.println(Const.defaultValue + "\n\nThe best individual is: " + pop[0].getDecimalGenes()
                    + " and his fitness score is: "
                    + pop[0].getFitness());
            System.out.println(Const.defaultValue + "The number of mutation is: " + counter[0]);
            System.out.println(Const.defaultValue + "The number of crossover is: " + counter[1]);
            System.out.println(Const.defaultValue + "\nPress " + Const.red + Const.underline + "Enter"
                    + Const.defaultValue + " to continue or press " + Const.red + Const.underline + "any key"
                    + Const.defaultValue + " to exit");

        } while (sc.nextLine().isEmpty());

        Display.clear();
        System.out.println(Const.bold + "Thank you for using our program!" + Const.defaultValue);
        sc.close();
    }

    /**
     * Perform evolution (mutation or crossover) on the population.
     *
     * @param pop                   The population of Individuals.
     * @param individualsNumber     The number of individuals in the population.
     * @param counter               An array to count mutation and crossover
     *                              operations.
     * @param customFitnessFunction The custom fitness function.
     * @param number_mut            The number of genes to mutate.
     * @return The updated population of Individuals.
     * @throws ScriptException If a script error occurs.
     */
    public static Individuals[] evolution(Individuals[] pop, int individualsNumber, int[] counter, String customFitnessFunction, int number_mut, boolean twins) throws ScriptException {
        Individuals[] best = Selection.selection(pop, individualsNumber);
        Random rand = new Random();
        String newpop;

        do {
            if (rand.nextInt(10) < (number_mut == 0 ? 0 : 3)) {
                newpop = Mutation.mutation(best[0].getBinaryGenes(), number_mut);
                counter[0]++;
            } else {
                newpop = Crossover.crossover(best[0].getBinaryGenes(), best[1].getBinaryGenes());
                counter[1]++;
            }

            pop[0] = best[0];
            pop[1] = best[1];
            pop[2] = new Individuals(newpop, 2, customFitnessFunction);
        } while (!twins && (pop[2].getDecimalGenes() == best[0].getDecimalGenes()
                || pop[2].getDecimalGenes() == best[1].getDecimalGenes()));

        return pop;
    }

    /**
     * Reformat the custom fitness function by replacing mathematical operators and
     * functions with Java-compatible ones.
     *
     * @param function The custom fitness function as a string.
     * @return The reformatted fitness function.
     */
    public static String reformat(String function) {
        // Replace ^ with Math.pow
        String regexPow = "\\s*([\\w\\s+\\-*/()]+)\\s*\\^\\s*(\\d+)\\s*";
        function = function.replaceAll(regexPow, "Math.pow($1, $2)");

        // Replace sin()
        String regexSin = "\\s*sin\\(\\s*([^\\r)]+)\\s*\\)\\s*";
        function = function.replaceAll(regexSin, "Math.sin($1)");

        // Replace cos()
        String regexCos = "\\s*cos\\(\\s*([^\\)]+)\\s*\\)\\s*";
        function = function.replaceAll(regexCos, "Math.cos($1)");

        // Replace exp()
        String regexExp = "\\s*exp\\(\\s*([^\\)]+)\\s*\\)\\s*";
        function = function.replaceAll(regexExp, "Math.exp($1)");

        // Replace pi
        String regexPi = "\\bpi\\b";
        function = function.replaceAll(regexPi, String.valueOf(Math.PI));

        return function;
    }
}
