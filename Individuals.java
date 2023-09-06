import java.util.Random;

public class Individuals {
    private int decimalGenes;
    private final int fitness;
    Individuals() {
        Random rand = new Random();
        decimalGenes = rand.nextInt(256);
        fitness = (int)Math.pow((decimalGenes + 3),2) - 25;
    }
    Individuals(int decimalGenes) {
        this.decimalGenes = decimalGenes;
        fitness = (int)Math.pow((decimalGenes + 3),2) - 25;
    }
    Individuals(String binaryStringGenes, int base) {
        decimalGenes = Integer.parseInt(binaryStringGenes, base);
        fitness = (int)Math.pow((decimalGenes + 3),2) - 25;
    }

    public int getFitness() {
        return fitness;
    }
    public int getDecimalGenes() {
        return decimalGenes;
    }
    public void setDecimalGenes(int decimalGenes) {
        this.decimalGenes = decimalGenes;
    }
    public String getBinaryGenes() {
        return Integer.toBinaryString(decimalGenes);
    }
    public void setBinaryGenes(String binaryGenes) {
        decimalGenes = Integer.parseInt(binaryGenes, 2);
    }


}
