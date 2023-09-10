import javax.script.ScriptException;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String args[]) throws ScriptException {
        Scanner sc = new Scanner(System.in);
        int continueProgram = 1;

        System.out.println(Const.defaultValue + Const.bold + "Welcome to the Genetic Algorithm program");
        System.out.println(Const.defaultValue + "Press " + Const.red + Const.underline + "Enter\033[0m" + Const.defaultValue + " to continue");
        sc.nextLine();
        do {
            clearScreen();
            System.out.println(Const.defaultValue + "Enter the number of individuals in the population or press " + Const.red + Const.underline + "Enter\033[0m" + Const.defaultValue + " for default:");

            // Handling individualsNumber
            int valid = 0;
            int individualsNumber = -1; // Initialize to an invalid value

            do {
                String individualsNumberStr = sc.nextLine();
                if (individualsNumberStr.isEmpty()) {
                    individualsNumber = 15;
                    break;
                }

                try {
                    individualsNumber = Integer.parseInt(individualsNumberStr);
                    if (individualsNumber <= 0) {
                        System.out.println(Const.defaultValue + "The population size must be a positive integer. Please try again.");
                    } else {
                        valid = 1;
                    }
                } catch (NumberFormatException e) {
                    System.out.println(Const.defaultValue + "That's not an integer. Please enter an integer.");
                }
            } while (valid == 0);


            String initialCustomFitnessFunction;
            String customFitnessFunction;


            Scanner scanner = new Scanner(System.in);
            valid = 0;

            System.out.println(Const.defaultValue + "Enter your custom fitness function using 'x' as the variable [e.g., (x + 3)^2 - 25] or press " + Const.red + Const.underline + "Enter" + Const.defaultValue + " for default:");
            do {
                initialCustomFitnessFunction = scanner.nextLine();

                // Use default value if user entered nothing
                if (initialCustomFitnessFunction.isEmpty()) {
                    initialCustomFitnessFunction = "(x + 3)^2 - 25";
                    break;
                }
                if (!initialCustomFitnessFunction.contains("x")) {
                    System.out.println(Const.defaultValue + "Your custom fitness function must contain the variable 'x'");
                    continue; // Loop will restart, skipping the following lines
                }

                for (char c : initialCustomFitnessFunction.toCharArray()) {
                    if (!Character.isDigit(c) && "x+*-/^() ".indexOf(c) == -1) {
                        System.out.println(Const.defaultValue + "Invalid character detected: " + c);
                        valid = 0;
                        break; // Exit the 'for' loop
                    }
                    valid = 1;
                }

            } while (valid == 0);

            System.out.println(Const.defaultValue + "Using a population size of " + individualsNumber);
            System.out.println(Const.defaultValue + "Using the following fitness function: " + initialCustomFitnessFunction + "\n");

            if (initialCustomFitnessFunction.contains("^")) {
                customFitnessFunction = reformatFunction(initialCustomFitnessFunction);
            } else {
                customFitnessFunction = initialCustomFitnessFunction;
            }


            Individuals[] pop = new Individuals[individualsNumber];
            for (int i = 0; i < individualsNumber; i++) {
                pop[i] = new Individuals(customFitnessFunction);
            }

            Individuals[] best;
            int[] counter = {0, 0};

            best = Selection.selection(pop, individualsNumber);

            //use the newton algorithm to find the root of the function
            if (best[0].getFitness() == 0) {
                System.out.println(Const.defaultValue + "The best individual is: " + best[0].getDecimalGenes() + " and his fitness score is: "
                        + best[0].getFitness());
                System.exit(0);
            }


            if (Individuals.findRoot(customFitnessFunction) == null) {
                System.out.println(Const.defaultValue + "There is no root found for the following function between 0 and 255" +
                        "\nf(x) -> " + initialCustomFitnessFunction);
                while (best[0].getFitness() != 0 && best[1].getFitness() != 0 && counter[0] + counter[1] < 1000000) {
                    evolution(pop, individualsNumber, counter, customFitnessFunction);
                }
            } else {
                System.out.println(Const.defaultValue + "The root of the function is: " + Individuals.findRoot(customFitnessFunction));
                while (best[0].getFitness() != 0 && best[1].getFitness() != 0  && counter[0] + counter[1] < 1000000) {
                    evolution(pop, individualsNumber, counter, customFitnessFunction);
                }
            }


            System.out.println(Const.defaultValue + "The best individual is: " + best[0].getDecimalGenes() + " and his fitness score is: "
                    + best[0].getFitness());
            System.out.println(Const.defaultValue + "The number of mutation is: " + counter[0]);
            System.out.println(Const.defaultValue + "The number of crossover is: " + counter[1]);
            System.out.println(Const.defaultValue + "\nPress " + Const.red + Const.underline + "Enter" + Const.defaultValue + " to continue or press " + Const.red + Const.underline + "0" + Const.defaultValue + " to exit");
        } while (sc.nextLine().equals(""));

        sc.close();
    }

    public static void evolution(Individuals[] pop, int individualsNumber, int[] counter, String customFitnessFunction) throws ScriptException {
        Individuals[] best = Selection.selection(pop, individualsNumber);
        Random rand = new Random();
        String newpop;
        if (rand.nextInt(10) < 3) {
            // System.out.println("Mutation");
            Mutation mut = new Mutation();
            newpop = mut.mutation(best[0].getBinaryGenes(), 1);
            counter[0]++;
        } else {
            // System.out.println("Crossover");
            Crossover cross = new Crossover();
            newpop = cross.crossover(best[0].getBinaryGenes(), best[1].getBinaryGenes());
            counter[1]++;
        }
        pop[0] = best[0];
        pop[1] = best[1];
        pop[2] = new Individuals(newpop, 2, customFitnessFunction);
        individualsNumber = 3;
    }

    public static String reformatFunction(String function) {
        String regex = "\\s*([\\w\\s+\\-*/()]+)\\s*\\^\\s*(\\d+)\\s*";
        return function.replaceAll(regex, "Math.pow($1, $2)");
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
