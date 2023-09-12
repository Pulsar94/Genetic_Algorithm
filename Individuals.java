import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Random;

public class Individuals {
    private static final ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
    private int decimalGenes;
    private int fitness;
    private static String fitnessFunction;

    public Individuals(String fitnessFunction) throws ScriptException {
        Individuals.fitnessFunction = fitnessFunction;
        Random rand = new Random();
        decimalGenes = rand.nextInt(256);
        evaluateFitness();
    }

    public Individuals(int decimalGenes, String fitnessFunction) throws ScriptException {
        this.decimalGenes = decimalGenes;
        Individuals.fitnessFunction = fitnessFunction;
        evaluateFitness();
    }

    Individuals(String binaryStringGenes, int base, String fitnessFunction) throws ScriptException {
        Individuals.fitnessFunction = fitnessFunction;
        this.decimalGenes = Integer.parseInt(binaryStringGenes, base);
        evaluateFitness();
    }

    private void evaluateFitness() throws ScriptException {
        synchronized(engine) {
            engine.put("x", getDecimalGenes());
            setFitness(engine.eval(String.format("Math.floor(%s)", fitnessFunction)) instanceof Integer ? (Integer) engine.eval(String.format("Math.floor(%s)", fitnessFunction)) : ((Double) engine.eval(String.format("Math.floor(%s)", fitnessFunction))).intValue());
        }
    }

    public static double evaluateExpression(String expression, int x) throws ScriptException {
        synchronized(engine) {
            engine.put("x", x);
            return (Double) engine.eval(expression);
        }
    }

    public static Integer findRoot(String function) throws ScriptException {
        for (int x = 0; x <= 255; x++) {
            double f_x = evaluateExpression(function, x);

            if (Math.abs(f_x) < 1e-7) { // Assuming a small enough value is practically a root
                return x;
            }
        }
        return null; // No root found
    }

    public int getFitness() {
        return fitness;
    }
    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    public int getDecimalGenes() {
        return decimalGenes;
    }

    public void setDecimalGenes(int decimalGenes) {
        this.decimalGenes = decimalGenes;
    }

    public String getBinaryGenes() {
        StringBuilder binaryGenes = new StringBuilder(Integer.toBinaryString(decimalGenes));
        for (int i = binaryGenes.length(); i < 8; i++) {
            binaryGenes.insert(0, "0");
        }
        return binaryGenes.toString();
    }

    public int[] getArrayGenes() {
        String binaryGenes = getBinaryGenes(); // Existing method that returns binary representation
        int[] genes = new int[8]; // Assuming 8 genes
        for (int i = 0; i < binaryGenes.length(); i++) {
            genes[i] = Character.getNumericValue(binaryGenes.charAt(i));
        }
        return genes;
    }

    public void setBinaryGenes(String binaryGenes) {
        decimalGenes = Integer.parseInt(binaryGenes, 2);
    }

}
