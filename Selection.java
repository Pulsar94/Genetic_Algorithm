import java.util.Comparator;
import java.util.stream.*;
@SuppressWarnings("ALL")
public class Selection {
    /**
     * Selects the two best individuals from the population based on their fitness values.
     *
     * @param pop   An array of Individuals representing the population.
     * @param nbpop The number of individuals in the population.
     * @return An array containing the two best Individuals.
     */

    public static Individuals[] selection(Individuals[] pop, int nbpop) {
        return Stream.of(pop)
                .sorted(Comparator.comparingLong(a -> abs(a.getFitness())))
                .limit(2)
                .toArray(Individuals[]::new);

    }


    /**
     * Calculates the absolute value of a long integer.
     *
     * @param a The long integer for which to calculate the absolute value.
     * @return The absolute value of the input long integer.
     */
    public static long abs(long a) {
        return (a < 0) ? -a : a;
    }
}
