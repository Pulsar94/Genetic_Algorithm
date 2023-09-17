import java.io.IOException;

/**
 * The Display class provides methods for displaying messages and controlling the console display.
 */
public class Display {

    /**
     * Displays a message and waits for the user to press Enter to continue.
     * This method reads input from the console.
     */
    public static void waiting() {
        System.out.println(Const.defaultValue + "Press " + Const.underline + Const.red + "Enter" + Const.defaultValue + " to continue...");
        try {
            System.in.read();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Clears the console screen by running the "cls" command on Windows systems.
     * This method uses a ProcessBuilder to execute the command.
     */
    public static void clear() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            System.out.println(e);
        }
        System.out.println("░██████╗░███████╗███╗░░██╗███████╗████████╗██╗░█████╗░     ░█████╗░██╗░░░░░░██████╗░░█████╗░██████╗░██╗████████╗██╗░░██╗███╗░░░███╗\n" +
                "██╔════╝░██╔════╝████╗░██║██╔════╝╚══██╔══╝██║██╔══██╗     ██╔══██╗██║░░░░░██╔════╝░██╔══██╗██╔══██╗██║╚══██╔══╝██║░░██║████╗░████║\n" +
                "██║░░██╗░█████╗░░██╔██╗██║█████╗░░░░░██║░░░██║██║░░╚═╝     ███████║██║░░░░░██║░░██╗░██║░░██║██████╔╝██║░░░██║░░░███████║██╔████╔██║\n" +
                "██║░░╚██╗██╔══╝░░██║╚████║██╔══╝░░░░░██║░░░██║██║░░██╗     ██╔══██║██║░░░░░██║░░╚██╗██║░░██║██╔══██╗██║░░░██║░░░██╔══██║██║╚██╔╝██║\n" +
                "╚██████╔╝███████╗██║░╚███║███████╗░░░██║░░░██║╚█████╔╝     ██║░░██║███████╗╚██████╔╝╚█████╔╝██║░░██║██║░░░██║░░░██║░░██║██║░╚═╝░██║\n" +
                "░╚═════╝░╚══════╝╚═╝░░╚══╝╚══════╝░░░╚═╝░░░╚═╝░╚════╝░     ╚═╝░░╚═╝╚══════╝░╚═════╝░░╚════╝░╚═╝░░╚═╝╚═╝░░░╚═╝░░░╚═╝░░╚═╝╚═╝░░░░░╚═╝\n"
        );
    }
}
