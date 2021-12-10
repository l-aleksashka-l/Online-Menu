import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class NetServerThread {
    private static ArrayList<Menu> employees = new ArrayList<>();

    public static void main(String[] args) throws ParserConfigurationException{
        try {

            InetAddress ad = InetAddress.getByName("127.0.0.1");
            ServerSocket serv = new ServerSocket(8071, 5, ad);
            System.out.println("initialized");
            while (true) {
                //ожидание клиента
                Socket sock = serv.accept();
                System.out.println(
                        sock.getInetAddress().getHostName()
                                + " connected");
                ServerThread server = new ServerThread(sock);
                server.start();


                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser parser = null;
                try {
                    parser = factory.newSAXParser();
                    XMLHandler handler = new XMLHandler();
                    parser.parse(new File("XML_menu.xml"), handler);

                } catch (SAXException e) {

                }


                ArrayList<String> StarterStr = new ArrayList<>();
                ArrayList<String> MainCoursesStr = new ArrayList<>();
                ArrayList<String> DessertesStr = new ArrayList<>();
                ArrayList<String> DrinksStr = new ArrayList<>();
                for (Menu menu : employees) {
                    while (menu.getNumber().charAt(0) == 1) {
                        StarterStr.add(menu.getName() + menu.getSale());
                    }
                    while (menu.getNumber().charAt(0) == 2) {
                        MainCoursesStr.add(menu.getName() + menu.getSale());
                    }
                    while (menu.getNumber().charAt(0) == 3) {
                        DessertesStr.add(menu.getName() + menu.getSale());
                    }
                    while (menu.getNumber().charAt(0) == 4) {
                        DrinksStr.add(menu.getName() + menu.getSale());
                    }
                }


            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    static class XMLHandler extends DefaultHandler {
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (qName.equals("dish")) {
                String number = attributes.getValue("number");
                String name = attributes.getValue("name");
                String sale = attributes.getValue("sale");
                employees.add(new Menu(number, name, sale));
            }
        }
    }
}

class Menu {
    String number;
    String name;
    String sale;

    public Menu(String number, String name, String sale) {
        this.number = number;
        this.name = name;
        this.sale = sale;
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public String getSale() {
        return sale;
    }
}


class ServerThread extends Thread {
    private PrintStream os;//передача
    private BufferedReader is, is2;//чтение
    private InetAddress addr;//адрес клиента

    public ServerThread(Socket s) throws IOException {
        os = new PrintStream(s.getOutputStream());
        is = new BufferedReader(new InputStreamReader(
                s.getInputStream()));
        addr = s.getInetAddress();
    }

    public void run() {
        double i = 0;
        String str;
        String StartersStr[] = {"Garlic Bread", "Soup of the day", "Olives", "Prawn salad", "Mozzarella salad"};
        double StartersDouble[] = {3.50, 4.99, 3.99, 4.99, 4.50};
        String MainCoursesStr[] = {"Margherita pizza", "Roast chicken salad", "Lasagne and salad", "Cheeseburger and fries", "Fish and chips"};
        double MainCoursesDouble[] = {7.99, 6.50, 7.50, 6.99, 7.50};
        String DessertesStr[] = {"Ice cream", "Chocolate cheesecake", "Fruit trifle", "Jam roly poly", "Cinnamon waffle"};
        double DessertesDouble[] = {3.99, 4.50, 3.99, 4.00, 4.99};
        String DrinksStr[] = {"Red/white wine", "Beer", "Cola", "Lemonade", "Orange juice", "Apple juice", "Champagne"};
        double DrinksDouble[] = {3.50, 3.99, 2.99, 2.99, 3.50, 3.50, 9.00};
        try {
            while (true) {
                str = is.readLine();
                if ("Accept".equals(str)) {
                    os.println("\t\t Toxic Restaurant" +
                            "\n\t\t\t  Menu" +
                            "\n\t\t\tStarters" +
                            "\n" + StartersStr[0] + ".................... " + StartersDouble[0] + "0$" +
                            "\n" + StartersStr[1] + "................. " + StartersDouble[1] + "$" +
                            "\n" + StartersStr[2] + ".......................... " + StartersDouble[2] + "$" +
                            "\n" + StartersStr[3] + "..................... " + StartersDouble[3] + "$" +
                            "\n" + StartersStr[4] + "................ " + StartersDouble[4] + "0$" +
                            "\n\t" +
                            "\n" + "\t\t  Main Courses" +
                            "\n" + MainCoursesStr[0] + "................ " + MainCoursesDouble[0] + "$" +
                            "\n" + MainCoursesStr[1] + "............. " + MainCoursesDouble[1] + "0$" +
                            "\n" + MainCoursesStr[2] + "............... " + MainCoursesDouble[2] + "0$" +
                            "\n" + MainCoursesStr[3] + ".......... " + MainCoursesDouble[3] + "$" +
                            "\n" + MainCoursesStr[4] + ".................. " + MainCoursesDouble[4] + "0$" +
                            "\n\t\n" + "\t\t   Desserts" +
                            "\n" + DessertesStr[0] + "....................... " + DessertesDouble[0] + "$" +
                            "\n" + DessertesStr[1] + "............ " + DessertesDouble[1] + "0$" +
                            "\n" + DessertesStr[2] + ".................... " + DessertesDouble[2] + "$" +
                            "\n" + DessertesStr[3] + "................... " + DessertesDouble[3] + "0$" +
                            "\n" + DessertesStr[4] + "................. " + DessertesDouble[4] + "$" +
                            "\n\t\n" + "\t\t    Drinks" +
                            "\n" + DrinksStr[0] + ".................. " + DrinksDouble[0] + "0$" +
                            "\n" + DrinksStr[1] + "............................ " + DrinksDouble[1] + "$" +
                            "\n" + DrinksStr[2] + "............................ " + DrinksDouble[2] + "$" +
                            "\n" + DrinksStr[3] + "........................ " + DrinksDouble[3] + "$" +
                            "\n" + DrinksStr[4] + ".................... " + DrinksDouble[4] + "0$" +
                            "\n" + DrinksStr[5] + "..................... " + DrinksDouble[5] + "0$" +
                            "\n" + DrinksStr[6] + "....................... " + DrinksDouble[6] + "0$" +
                            "\n\t" +
                            "\n" + "Example by order: " +
                            "\n" + "If you want a beer write 4.2 (for close write 'End')" + "\n\t\n\t\n");
                } else if (str != null && (str.equals("1.1") || str.equals("1.2") || str.equals("1.3") || str.equals("1.4") || str.equals("1.5") || str.equals("2.1") || str.equals("2.2") || str.equals("2.3") || str.equals("2.4") || str.equals("2.5") || str.equals("3.1") || str.equals("3.2") || str.equals("3.3") || str.equals("3.4") || str.equals("3.5") || str.equals("4.1") || str.equals("4.2") || str.equals("4.3") || str.equals("4.4") || str.equals("4.5") || str.equals("4.6") || str.equals("4.7"))) {
                    switch (str.charAt(0)) {
                        case '1' -> {
                            switch (str.charAt(2)) {
                                case '1' -> {
                                    os.println(StartersStr[0] + ".................... " + StartersDouble[0] + "0$");
                                    i += StartersDouble[0];
                                }
                                case '2' -> {
                                    os.println(StartersStr[1] + "................. " + StartersDouble[1] + "$");
                                    i += StartersDouble[1];
                                }
                                case '3' -> {
                                    os.println(StartersStr[2] + ".......................... " + StartersDouble[2] + "$");
                                    i += StartersDouble[2];
                                }
                                case '4' -> {
                                    os.println(StartersStr[3] + "..................... " + StartersDouble[3] + "$");
                                    i += StartersDouble[3];
                                }
                                case '5' -> {
                                    os.println(StartersStr[4] + "................ " + StartersDouble[4] + "0$");
                                    i += StartersDouble[4];
                                }
                            }

                        }
                        case '2' -> {
                            switch (str.charAt(2)) {
                                case '1' -> {
                                    os.println(MainCoursesStr[0] + "................ " + MainCoursesDouble[0] + "$");
                                    i += MainCoursesDouble[0];
                                }
                                case '2' -> {
                                    os.println(MainCoursesStr[1] + "............. " + MainCoursesDouble[1] + "0$");
                                    i += MainCoursesDouble[1];
                                }
                                case '3' -> {
                                    os.println(MainCoursesStr[2] + "............... " + MainCoursesDouble[2] + "0$");
                                    i += MainCoursesDouble[2];
                                }
                                case '4' -> {
                                    os.println(MainCoursesStr[3] + ".......... " + MainCoursesDouble[3] + "$");
                                    i += MainCoursesDouble[3];
                                }
                                case '5' -> {
                                    os.println(MainCoursesStr[4] + ".................. " + MainCoursesDouble[4] + "0$");
                                    i += MainCoursesDouble[4];
                                }
                            }
                        }
                        case '3' -> {
                            switch ((str.charAt(2))) {
                                case '1' -> {
                                    os.println(DessertesStr[0] + "....................... " + DessertesDouble[0] + "$");
                                    i += DessertesDouble[0];
                                }
                                case '2' -> {
                                    os.println(DessertesStr[1] + "............ " + DessertesDouble[1] + "0$");
                                    i += DessertesDouble[1];
                                }
                                case '3' -> {
                                    os.println(DessertesStr[2] + ".................... " + DessertesDouble[2] + "$");
                                    i += DessertesDouble[2];
                                }
                                case '4' -> {
                                    os.println(DessertesStr[3] + "................... " + DessertesDouble[3] + "0$");
                                    i += DessertesDouble[3];
                                }
                                case '5' -> {
                                    os.println(DessertesStr[4] + "................. " + DessertesDouble[4] + "$");
                                    i += DessertesDouble[4];
                                }
                            }
                        }
                        case '4' -> {
                            switch ((str.charAt(2))) {
                                case '1' -> {
                                    os.println(DrinksStr[0] + ".................. " + DrinksDouble[0] + "0$");
                                    i += DrinksDouble[0];
                                }
                                case '2' -> {
                                    os.println(DrinksStr[1] + "............................ " + DrinksDouble[1] + "$");
                                    i += DrinksDouble[1];
                                }
                                case '3' -> {
                                    os.println(DrinksStr[2] + "............................ " + DrinksDouble[2] + "$");
                                    i += DrinksDouble[2];
                                }
                                case '4' -> {
                                    os.println(DrinksStr[3] + "........................ " + DrinksDouble[3] + "$");
                                    i += DrinksDouble[3];
                                }
                                case '5' -> {
                                    os.println(DrinksStr[4] + ".................... " + DrinksDouble[4] + "0$");
                                    i += DrinksDouble[4];
                                }
                                case '6' -> {
                                    os.println(DrinksStr[5] + "..................... " + DrinksDouble[5] + "0$");
                                    i += DrinksDouble[5];
                                }
                                case '7' -> {
                                    os.println(DrinksStr[6] + "....................... " + DrinksDouble[6] + "0$");
                                    i += DrinksDouble[6];
                                }

                            }
                        }
                    }
                } else if (str != null && (str.equals("End") || str.equals("end"))) {
                    os.println("\t\nPrice: " + i + "$\n");
                } else {
                    os.println("Can't find " + str);
                }


            }

        } catch (IOException e) {
            //если клиент не отвечает, соединение с ним разрывается
            System.out.println("Disconnect");
        } finally {
            disconnect();//уничтожение потока
        }
    }

    public void disconnect() {
        try {
            System.out.println(addr.getHostName() +
                    " disconnected");
            os.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.interrupt();
        }
    }
}
