import java.util.Random;

public class Mutation {
    public static int[] bitMutated;
    public static String mutation(String oldSequence, int number_mut){
        System.out.println("old sequence :"+oldSequence);
        bitMutated = new int[number_mut];
        Random rand = new Random(); // Initialization of the object random
        bitMutated[0] = rand.nextInt(oldSequence.length()); // random number between 0 and the length of the sequence
        for (int i = 1; i < number_mut; i++) {
            bitMutated[i] = rand.nextInt(oldSequence.length()); // random number between 0 and the length of the sequence
            for (int j = 0; j < i; j++) {
                if (bitMutated[i] == bitMutated[j]) { // if the random number is already in the array, generate a new one
                    bitMutated[i] = rand.nextInt(oldSequence.length());
                    j = 0; // reset the loop
                }
            }
        }
        String newSequence = oldSequence;
        for (int bit:bitMutated) {
            newSequence = oldSequence.substring(0, bitMutated[0]) + (oldSequence.charAt(bitMutated[0]) == '0' ? '1' : '0') + oldSequence.substring(bitMutated[0] + 1); // replace the bit at the position bitMutated by the opposite
        }
        System.out.println("new sequence :"+newSequence);
        return newSequence;


//        Random rand = new Random(); // Initialization of the object random
//        String[] little_seq; // Initialization of an array of string
//        StringBuilder seq_mutated = new StringBuilder(); // The sequence mutated, to which we can append character at the end
//        int count_mut = 0; // count of mutation in progress
//        little_seq = old_sequence.split(""); // split the string in argument in an array of string
//        //System.out.println("The sequence to mutate is: " + old_sequence);
//
//        for (String i:little_seq) {
//            int int_random = rand.nextInt(2); // random number between 0 and 1
//            if ((int_random == 1) && (count_mut < number_mut)){ // if random is equal to 1 and the count of mutation lower than the mutation attend, authorize the mutation
//                if (i.equals("0")){ // invert the value of genes
//                    i = "1";
//                } else if (i.equals("1")){
//                    i = "0";
//                } else {
//                    System.out.println("Something wrong with the mutation");
//                }
//                count_mut++; // increment the count of mutation
//            }
//            seq_mutated.append(i); // add every genes in the sequence mutated
//        }
//        //System.out.println("The sequence mutated is: " + seq_mutated.toString());
//        return seq_mutated.toString(); // convert the StringBuilder to a string
    }
}
