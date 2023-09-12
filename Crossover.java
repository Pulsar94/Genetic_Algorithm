import java.util.Random;

public class Crossover {
    public static int crossoverPoint = -1;
    public static String crossover(String binaryGenes1, String binaryGenes2) {
        String crossoverGenes;
        Random rand = new Random();
        crossoverPoint = rand.nextInt(binaryGenes1.length() + 1); // +1 to include the last index
        // To get the new genes
        crossoverGenes = binaryGenes1.substring(0, crossoverPoint) + binaryGenes2.substring(crossoverPoint);
        //System.out.println("Crossover point: " + crossoverPoint);
        return crossoverGenes;
    }
}
