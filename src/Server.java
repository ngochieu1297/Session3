import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Server {
    private static ServerSocket server;
    private static int port = 8080;

    public static void main(String args[]) throws IOException, ClassNotFoundException {
        server = new ServerSocket(port);
        while (true) {
            System.out.println("Waiting for client request");
            Socket socket = server.accept();
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            String message = (String) objectInputStream.readObject();
            System.out.println("Message Received: " + message);
            String result = Result(message);
            System.out.println("Send " + result);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(result);
            objectInputStream.close();
            objectOutputStream.close();
            socket.close();

            if (message.equalsIgnoreCase("exit")) break;
        }
        System.out.println("Shutting down Socket server!!");
        server.close();
    }
    private static String Result(String input){
        int max = 0, start = -1, end = -1;
        List<Integer>integers = Arrays.asList(input.split(" ")).stream()
                .map(i->Integer.parseInt(i))
                .collect(Collectors.toList());
        for (int i = 0; i < integers.size(); i++) {
            int tong = integers.get(i);
            for (int j = i+1; j < integers.size(); j++) {
                tong += integers.get(j);
                if(tong > max){
                    max = tong;
                    start = i + 1;
                    end = j + 1;
                }
            }
        }
        return String.format("Tong: %d, Vi tri bat dau: %d, Vi tri ket thuc: %d",max,start,end);
    }
}