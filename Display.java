import java.io.IOException;

public class Display {
    public static void waiting() {
        System.out.println(Const.defaultValue + "\nPress " + Const.underline + Const.red + "Enter" + Const.defaultValue + " to continue...");
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
        System.out.println(Const.defaultValue + Const.bold + "Welcome to the Genetic Algorithm program");
        System.out.println("░██████╗░███████╗███╗░░██╗███████╗████████╗██╗░█████╗░     ░█████╗░██╗░░░░░░██████╗░░█████╗░██████╗░██╗████████╗██╗░░██╗███╗░░░███╗\n" +
                "██╔════╝░██╔════╝████╗░██║██╔════╝╚══██╔══╝██║██╔══██╗     ██╔══██╗██║░░░░░██╔════╝░██╔══██╗██╔══██╗██║╚══██╔══╝██║░░██║████╗░████║\n" +
                "██║░░██╗░█████╗░░██╔██╗██║█████╗░░░░░██║░░░██║██║░░╚═╝     ███████║██║░░░░░██║░░██╗░██║░░██║██████╔╝██║░░░██║░░░███████║██╔████╔██║\n" +
                "██║░░╚██╗██╔══╝░░██║╚████║██╔══╝░░░░░██║░░░██║██║░░██╗     ██╔══██║██║░░░░░██║░░╚██╗██║░░██║██╔══██╗██║░░░██║░░░██╔══██║██║╚██╔╝██║\n" +
                "╚██████╔╝███████╗██║░╚███║███████╗░░░██║░░░██║╚█████╔╝     ██║░░██║███████╗╚██████╔╝╚█████╔╝██║░░██║██║░░░██║░░░██║░░██║██║░╚═╝░██║\n" +
                "░╚═════╝░╚══════╝╚═╝░░╚══╝╚══════╝░░░╚═╝░░░╚═╝░╚════╝░     ╚═╝░░╚═╝╚══════╝░╚═════╝░░╚════╝░╚═╝░░╚═╝╚═╝░░░╚═╝░░░╚═╝░░╚═╝╚═╝░░░░░╚═╝"
        );
    }
}
