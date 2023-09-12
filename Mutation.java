import java.util.Random;
import java.util.Arrays;

public class Mutation {
    public static int[] bitMutated;
    public static String mutation(String oldSequence, int number_mut){
        bitMutated = new int[number_mut];
        Random rand = new Random(); // Initialization of the object random
        for (int i = 0; i < number_mut; i++) {
            int tempBit = rand.nextInt(oldSequence.length()); // Generate a random number between 0 and the length of the sequence
                for (int j = 0; j < i; j++) {
                    if (bitMutated[j] == tempBit) {
                        tempBit = rand.nextInt(oldSequence.length());
                        j = -1;
                    }
                }
            bitMutated[i] = tempBit;
            //System.out.println("Bit mutated: " + bitMutated[i]);
        }
//        if (bitMutated[0] == bitMutated[1])
//            System.out.println("Error :" + bitMutated[0] + " " + bitMutated[1]);
        String newSequence = oldSequence;
        for (int bit:bitMutated) {
            newSequence = newSequence.substring(0, bit) + (newSequence.charAt(bit) == '0' ? '1' : '0') + newSequence.substring(bit + 1); // replace the bit at the position bitMutated by the opposite
        }
        return newSequence;
    }
}
