import java.util.Random;

public class Crossover {
    public String crossover(String binaryGenes1, String binaryGenes2) {
        String crossoverGenes = "";
        Random rand = new Random();
        int crossoverPoint = rand.nextInt(binaryGenes1.length() + 1);
        System.out.println("Crossover point: " + crossoverPoint);
        crossoverGenes = binaryGenes1.substring(0, crossoverPoint) + binaryGenes2.substring(crossoverPoint);
        return crossoverGenes;
    }
}
