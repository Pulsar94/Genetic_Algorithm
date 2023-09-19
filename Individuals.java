import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@SuppressWarnings("ALL")
public class Individuals {
    // region attributes
    private static final ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
    private int decimalGenes;
    private int fitness;
    private static String fitnessFunction;
    // endregion

    /**
     * Constructs a new individual with random decimal genes and evaluates its fitness.
     *
     * @param fitnessFunction The fitness function to evaluate.
     * @throws ScriptException If a script error occurs during fitness evaluation.
     */
    Individuals(String fitnessFunction) throws ScriptException {
        Individuals.fitnessFunction = fitnessFunction;
        Random rand = new Random();
        decimalGenes = rand.nextInt(256);
        evaluateFitness();
    }

    /**
     * Constructs a new individual with specified decimal genes and evaluates its fitness.
     *
     * @param decimalGenes    The decimal genes of the individual.
     * @param fitnessFunction The fitness function to evaluate.
     * @throws ScriptException If a script error occurs during fitness evaluation.
     */
    Individuals(int decimalGenes, String fitnessFunction) throws ScriptException {
        this.decimalGenes = decimalGenes;
        Individuals.fitnessFunction = fitnessFunction;
        evaluateFitness();
    }

    /**
     * Constructs a new individual with binary string genes, evaluates its fitness, and converts them to decimal genes.
     *
     * @param binaryStringGenes The binary string representation of genes.
     * @param base              The base for conversion (e.g., 2 for binary).
     * @param fitnessFunction   The fitness function to evaluate.
     * @throws ScriptException If a script error occurs during fitness evaluation.
     */
    Individuals(String binaryStringGenes, int base, String fitnessFunction) throws ScriptException {
        Individuals.fitnessFunction = fitnessFunction;
        this.decimalGenes = Integer.parseInt(binaryStringGenes, base);
        evaluateFitness();
    }

    /**
     * Evaluates the fitness of the individual using the fitness function.
     *
     * @throws ScriptException If a script error occurs during fitness evaluation.
     */
    private void evaluateFitness() throws ScriptException {
        synchronized (engine) {
            engine.put("x", getDecimalGenes());
            setFitness(engine.eval(String.format("Math.floor(%s)", fitnessFunction)) instanceof Integer ? (Integer) engine.eval(String.format("Math.floor(%s)", fitnessFunction)) : ((Double) engine.eval(String.format("Math.floor(%s)", fitnessFunction))).intValue());
        }
    }

    /**
     * Evaluates a mathematical expression with a given value of x.
     *
     * @param expression The mathematical expression to evaluate.
     * @param x          The value of x.
     * @return The result of the expression evaluation.
     * @throws ScriptException If a script error occurs during evaluation.
     */
    public static double evaluateExpression(String expression, int x) throws ScriptException {
        synchronized (engine) {
            engine.put("x", x);
            return (Double) engine.eval(expression);
        }
    }

    /**
     * Finds a root of a mathematical function within the range [0, 255].
     *
     * @param function The mathematical function to find the root for.
     * @return The root if found, or null if no root is found.
     * @throws ScriptException If a script error occurs during evaluation.
     */
    public static Integer findRoot(String function) throws ScriptException {
        for (int x = 0; x <= 255; x++) {
            double f_x = evaluateExpression(function, x);
            if (Math.abs(f_x) < 1e-7) { // Assuming a small enough value is practically a root
                return x;
            }
        }
        return null; // No root found
    }

    /**
     * Gets the fitness value of the individual.
     *
     * @return The fitness value.
     */
    public int getFitness() {
        return fitness;
    }

    /**
     * Sets the fitness value of the individual.
     *
     * @param fitness The new fitness value to set.
     */
    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    /**
     * Gets the decimal representation of the individual's genes.
     *
     * @return The decimal genes.
     */
    public int getDecimalGenes() {
        return decimalGenes;
    }

    /**
     * Sets the decimal genes of the individual.
     *
     * @param decimalGenes The new decimal genes to set.
     */
    public void setDecimalGenes(int decimalGenes) {
        this.decimalGenes = decimalGenes;
    }

    /**
     * Gets the binary representation of the individual's genes.
     *
     * @return The binary genes as a string.
     */
    public String getBinaryGenes() {
        String binaryString = Integer.toBinaryString(decimalGenes);
        String paddingZeros = Stream.generate(() -> "0").limit(8 - binaryString.length()).collect(Collectors.joining());
        return paddingZeros + binaryString;
    }


    /**
     * Gets the individual's genes as an array of integers.
     *
     * @return An array representing the individual's genes.
     */
    public int[] getArrayGenes() {
        return getBinaryGenes().chars()
                .map(Character::getNumericValue)
                .toArray();
    }


    /**
     * Sets the binary genes of the individual.
     *
     * @param binaryGenes The new binary genes to set as a string.
     */
    public void setBinaryGenes(String binaryGenes) {
        decimalGenes = Integer.parseInt(binaryGenes, 2);
    }
}
