import java.io.*;
import java.net.*;

public class ChatClient {
    private static final String SERVER_ADDRESS = "192.168.137.80";  // Replace with your server IP
    private static final int SERVER_PORT = 12345;

    // ANSI color codes
    private static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String CYAN = "\u001B[36m";
    private static final String PURPLE = "\u001B[35m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";

    public static void main(String[] args) {
        clearScreen();
        showBanner();

        try (
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        ) {
            // Thread to receive messages from server
            new Thread(() -> {
                String serverMessage;
                try {
                    while ((serverMessage = in.readLine()) != null) {
                        System.out.println("\n" + CYAN + "[Server] " + RESET + serverMessage);
                        System.out.print(GREEN + "[You] " + RESET);
                    }
                } catch (IOException e) {
                    System.out.println(RED + "\nDisconnected from server." + RESET);
                }
            }).start();

            // Main thread handles user input
            System.out.print(GREEN + "[You] " + RESET);
            String userMessage;
            while ((userMessage = userInput.readLine()) != null) {
                out.println(userMessage);
                if (userMessage.equalsIgnoreCase("/exit")) break;
                System.out.print(GREEN + "[You] " + RESET);
            }

        } catch (IOException e) {
            System.out.println(RED + "Connection error: " + e.getMessage() + RESET);
        }
    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void showBanner() {
        String banner = PURPLE +
                "   _____ _           _   _____ _           _      \n" +
                "  / ____| |         | | |_   _| |         | |     \n" +
                " | |    | |__   __ _| |_  | | | |__   __ _| |_ ___\n" +
                " | |    | '_ \\ / _` | __| | | | '_ \\ / _` | __/ __|\n" +
                " | |____| | | | (_| | |_ _| |_| | | | (_| | |_\\__ \\\n" +
                "  \\_____|_| |_|\\__,_|\\__|_____|_| |_|\\__,_|\\__|___/\n" +
                RESET;
        System.out.println(banner);
        System.out.println(YELLOW + "Welcome to the Java CLI Chat Client!" + RESET);
        System.out.println(BLUE + "Type your message and hit Enter to chat. Type /exit to leave the chat.\n" + RESET);
    }
}

