import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class ChatServer {
    private static final int PORT = 12345;
    private static final Map<String, List<ClientHandler>> chatRooms = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("Chat server started on port " + PORT);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new ClientHandler(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ClientHandler implements Runnable {
        private final Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        private String username;
        private String roomName;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                out.println("Enter username:");
                username = in.readLine();
                out.println("Enter room name to join or create:");
                roomName = in.readLine();

                synchronized (chatRooms) {
                    chatRooms.putIfAbsent(roomName, new ArrayList<>());
                    chatRooms.get(roomName).add(this);
                }

                broadcast(username + " joined the room.");

                String message;
                while ((message = in.readLine()) != null) {
                    if (message.equalsIgnoreCase("/exit")) break;
                    broadcast(timeStamp() + username + ": " + message);
                }
            } catch (IOException e) {
                System.out.println("Client error: " + username);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                synchronized (chatRooms) {
                    if (chatRooms.containsKey(roomName)) {
                        chatRooms.get(roomName).remove(this);
                        broadcast(username + " left the room.");
                    }
                }
            }
        }

        private void broadcast(String message) {
            synchronized (chatRooms) {
                for (ClientHandler client : chatRooms.get(roomName)) {
                    client.out.println(message);
                }
            }
        }

        private String timeStamp() {
            return "[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] ";
        }
    }
}

