import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String args[]) {
        System.out.println("Enter the number of individuals in the population");
        Scanner sc = new Scanner(System.in);
        int nbpop = sc.nextInt();

        Individuals[] pop = new Individuals[nbpop];
        for (int i = 0; i < nbpop; i++) {
            pop[i] = new Individuals();
        }

        //display pop
        for (int i = 0; i < nbpop; i++) {
            System.out.println(pop[i].getDecimalGenes());
        }
        Individuals[] best;
        String newpop;
        int[] counter = {0, 0};

        best = Selection.selection(pop, nbpop);
        //change this do while loop to a while loop
        while (best[0].getFitness() > 0 && best[1].getFitness() > 0) {
            best = Selection.selection(pop, nbpop);
            Random rand = new Random();
            if (rand.nextInt(10) < 3) {
                //System.out.println("Mutation");
                Mutation mut = new Mutation();
                newpop = mut.mutation(best[0].getBinaryGenes(), 1);
                counter[0]++;
            } else {
                //System.out.println("Crossover");
                Crossover cross = new Crossover();
                newpop = cross.crossover(best[0].getBinaryGenes(), best[1].getBinaryGenes());
                counter[1]++;
            }
            pop[0] = best[0];
            pop[1] = best[1];
            pop[2] = new Individuals(newpop, 2);
            nbpop = 3;

        }

        System.out.println("The best individual is: " + pop[0].getDecimalGenes() + " and his fitness score is: " + pop[0].getFitness());
        System.out.println("The number of mutation is: " + counter[0]);
        System.out.println("The number of crossover is: " + counter[1]);
    }
}
