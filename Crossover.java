import java.util.Random;
@SuppressWarnings("ALL")
public class Crossover {
    public static int crossoverPoint = -1;

    /**
     * Performs genetic crossover on two binary gene sequences.
     *
     * @param binaryGenes1 The binary gene sequence of the first individual.
     * @param binaryGenes2 The binary gene sequence of the second individual.
     * @return The new binary gene sequence resulting from crossover.
     */
    public static String crossover(String binaryGenes1, String binaryGenes2) {
        String crossoverGenes;
        Random rand = new Random();
        crossoverPoint = rand.nextInt(binaryGenes1.length() + 1); // +1 to include the last index

        crossoverGenes = String.join("", binaryGenes1.substring(0, crossoverPoint), binaryGenes2.substring(crossoverPoint));

        return crossoverGenes;
    }
}
