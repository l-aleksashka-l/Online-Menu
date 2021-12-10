import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class NetClientThread {
    public static void main(String[] args) {
        try {
            String str;
            // установка соединения с сервером
            Socket s = new Socket("127.0.0.1", 8071);
            //или Socket s = new Socket("ИМЯ_СЕРВЕРА", 8071);
            PrintStream ps = new PrintStream(s.getOutputStream());
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(s.getInputStream()));
            ps.println("Accept");
            read(br);
            String string;
            Scanner scanner = new Scanner(System.in);
            boolean tof = true;
            while (tof) {
                if ((string = scanner.next()).equals("end") || (string).equals("End")) {
                    ps.println(string);
                    tof = false;
                } else {
                    ps.println(string);
                }
            }
            System.out.println("--------------Your check--------------\n");

            read(br);
            s.close();

        } catch (UnknownHostException e) {
            // если не удалось соединиться с сервером
            System.out.println("Unknown adress");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error I/О thread");
            e.printStackTrace();
        }
    }

    static void read(BufferedReader br) {
        String str = null;
        while (true) {
            try {
                if (((str = br.readLine())).length() == 0) {

                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(str);
        }
    }
}

