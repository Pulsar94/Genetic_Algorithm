import java.util.Random;

public class Mutation {
    public String mutation(String old_sequence, int number_mut){
        Random rand = new Random(); // Initialization of the object random
        String[] little_seq; // Initialization of an array of string
        StringBuilder seq_mutated = new StringBuilder(); // The sequence mutated, to which we can append character at the end
        int count_mut = 0; // count of mutation in progress
        little_seq = old_sequence.split(""); // split the string in argument in an array of string

        for (String i:little_seq) {
            int int_random = rand.nextInt(2); // random number between 0 and 1
            if ((int_random == 1) && (count_mut < number_mut)){ // if random is equal to 1 and the count of mutation lower than the mutation attend, authorize the mutation
                if (i.equals("0")){ // invert the value of genes
                    i = "1";
                } else if (i.equals("1")){
                    i = "0";
                } else {
                    System.out.println("Something wrong with the mutation");
                }
                count_mut++; // increment the count of mutation
            }
            seq_mutated.append(i); // add every genes in the sequence mutated
        }
        return seq_mutated.toString(); // convert the StringBuilder to a string
    }
}
