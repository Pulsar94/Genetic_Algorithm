import java.util.Random;

public class Population {
    private int decimalGenes;
    Population() {
        Random rand = new Random();
        decimalGenes = rand.nextInt(255);
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
