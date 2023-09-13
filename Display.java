import java.io.IOException;

public class Display {
    public static void waiting() {
        System.out.println("\nPress enter to continue...");
        try {
            System.in.read();
        } catch (IOException e) {
            System.out.println(e);
        }
        clear();
    }

    public static void clear() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            System.out.println(e);
        }
    }
}
