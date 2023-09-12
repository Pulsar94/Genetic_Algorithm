public class Selection {
    /**
     * Selects the two best individuals from the population based on their fitness values.
     *
     * @param pop   An array of Individuals representing the population.
     * @param nbpop The number of individuals in the population.
     * @return An array containing the two best Individuals.
     */
    public static Individuals[] selection(Individuals[] pop, int nbpop) {
        // Create an array to store the two best Individuals.
        Individuals[] better = new Individuals[2];
        // Create a temporary variable to hold an Individual during comparisons.
        Individuals temp;
        // Initialize better[0] and better[1] with the first two Individuals from the population.
        better[0] = pop[0];
        better[1] = pop[1];

        // Iterate through the rest of the population.
        for (int i = 2; i < nbpop; i++) {
            // Store the current Individual in the 'temp' variable.
            temp = pop[i];

            // Compare the fitness values of 'temp' and 'better[0]'.
            // If 'temp' has a lower absolute fitness value, update 'temp' and 'better[0]'.
            if (abs(temp.getFitness()) < abs(better[0].getFitness())) {
                temp = better[0];
                better[0] = pop[i];
            }

            // Compare the fitness values of 'temp' and 'better[1]'.
            // If 'temp' has a lower absolute fitness value, update 'better[1]'.
            if (abs(temp.getFitness()) < abs(better[1].getFitness())) {
                better[1] = temp;
            }
        }

        // Return the array containing the two best Individuals.
        return better;
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
