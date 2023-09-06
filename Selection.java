public class Selection {
    public static Individuals[] selection(Individuals[] pop, int nbpop) {
        Individuals[] better = new Individuals[2];
        Individuals temp;
        better[0] = pop[0];
        better[1] = pop[1];
        for (int i = 2; i < nbpop; i++) {
            temp = pop[i];
            if (abs(temp.getFitness()) < abs(better[0].getFitness())) {
                temp = better[0];
                better[0] = pop[i];
            }
            if (abs(temp.getFitness()) < abs(better[1].getFitness())) {
                better[1] = temp;
            }
        }
        return better;
    }
    public static long  abs(long a) {
        return (a < 0) ? -a : a;
    }
}
