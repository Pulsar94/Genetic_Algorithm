import javax.script.ScriptException;
import java.util.HashMap;
import java.util.Map;
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

            boolean invalidInput = true;
            int individualsNumber = -1; // Initialize to an invalidInput value

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
                        invalidInput = false;
                    }
                } catch (NumberFormatException e) {
                    System.out.println(Const.defaultValue + "That's not an integer. Please enter an integer.");
                }
            } while (invalidInput);
            // endregion

            // region Custom Fitness Function Input
            Display.clear();
            String initialCustomFitnessFunction;
            String customFitnessFunction;

            Scanner scanner = new Scanner(System.in);
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
                invalidInput = !(initialCustomFitnessFunction.contains("x")
                        && initialCustomFitnessFunction.chars().allMatch(c -> Character.isDigit(c) || "x+*-/^()sincoexpMath. ".indexOf(c) != -1));


            } while (invalidInput);
            // endregion

            // region Number of Genes to Mutate Input
            Display.clear();
            int numberGenes = -1;
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
                        System.out.println(Const.defaultValue + "The number of genes to mutate must be between 0 and 8. Please try again.");
                    } else {
                        invalidInput = false;
                    }
                } catch (NumberFormatException e) {
                    System.out.println(Const.defaultValue + "That's not an integer. Please enter an integer.");
                }
            } while (invalidInput);
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

            // region Results
            System.out.println(Const.defaultValue + "\n\nThe best individual is: " + pop[0].getDecimalGenes()
                    + " and his fitness score is: "
                    + pop[0].getFitness());
            System.out.println(Const.defaultValue + "The number of mutation is: " + counter[0]);
            System.out.println(Const.defaultValue + "The number of crossover is: " + counter[1]);
            System.out.println(Const.defaultValue + "\nPress " + Const.red + Const.underline + "Enter"
                    + Const.defaultValue + " to continue or enter " + Const.red + Const.underline + "anything"
                    + Const.defaultValue + " to exit");
            // endregion

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
        int randomResult;

        do {
            // Perform mutation or crossover using lambda :
            randomResult = rand.nextInt(10);
            newpop = (randomResult < (number_mut == 0 ? 0 : 3)) ? Mutation.mutation(best[0].getBinaryGenes(), number_mut) : Crossover.crossover(best[0].getBinaryGenes(), best[1].getBinaryGenes());
            counter[(randomResult < (number_mut == 0 ? 0 : 3)) ? 0 : 1]++;

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
        String newFunction = function; // Declare a final variable

        Map<String, String> replacements = new HashMap<>();
        replacements.put("\\s*([\\w\\s+\\-*/()]+)\\s*\\^\\s*(\\d+)\\s*", "Math.pow($1, $2)");
        replacements.put("\\s*sin\\(\\s*([^\\r)]+)\\s*\\)\\s*", "Math.sin($1)");
        replacements.put("\\s*cos\\(\\s*([^\\)]+)\\s*\\)\\s*", "Math.cos($1)");

        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            newFunction = newFunction.replaceAll(entry.getKey(), entry.getValue());
        }

        return newFunction;
    }
}
