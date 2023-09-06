import java.util.Random;

public class Population {
    private int decimalGenes;
    private int fitness;
    Population() {
        Random rand = new Random();
        decimalGenes = rand.nextInt(255);
        fitness = (decimalGenes + 3)^2 - 25;
    }
    Population(int decimalGenes) {
        this.decimalGenes = decimalGenes;
        fitness = (decimalGenes + 3)^2 - 25;
    }
    Population(String binaryStringGenes, int base) {
        decimalGenes = Integer.parseInt(binaryStringGenes, base);
        fitness = (decimalGenes + 3)^2 - 25;
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
