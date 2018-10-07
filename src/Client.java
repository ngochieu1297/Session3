import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Client {
    private static final int PORT = 8080;
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        int range;
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhap kich thuoc mang : ");
        range = Integer.parseInt(sc.nextLine());
        int arr[] = new int[range];
        for(int i = 0; i < range; i++) {
            System.out.print(String.format("Nhap gia tri cho phan tu thu %d :", i));
            arr[i] = Integer.parseInt(sc.nextLine());
        }
        String sendData=Arrays.stream(arr)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(" "));
        InetAddress host = InetAddress.getLocalHost();
        Socket socket;
        ObjectOutputStream objectOutputStream;
        ObjectInputStream objectInputStream;
        socket = new Socket(host.getHostName(), PORT);
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        System.out.println("Sending request to Socket Server");

        objectOutputStream.writeObject(sendData);
        objectInputStream = new ObjectInputStream(socket.getInputStream());
        String message = (String) objectInputStream.readObject();
        System.out.println("Message: " +sendData);
        System.out.println(message);
        objectInputStream.close();
        objectOutputStream.close();
    }
}