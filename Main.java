
public class Main {
    public static void main(String args[]){
        Individuals[] pop = new Individuals[5];

        for (int i=0;i<5;i++){
            pop[i]= new Individuals();
        }
        Individuals[] fin;
        fin=Selection.selection(pop,5);
        for(int i=0;i<5;i++){
            System.out.println(pop[i].getDecimalGenes()+"  "+pop[i].getFitness());
        }
        System.out.println(fin[0].getDecimalGenes()+" // "+fin[1].getDecimalGenes());

    }
}
