    import java.io.*;
    import java.net.Socket;
    import java.net.UnknownHostException;
    import java.util.ArrayList;
    import java.util.Arrays;
    import java.util.List;
    import java.util.Scanner;

    public class Client {

        public static void main(String[] args) throws IOException {
            String hostName = "localhost"; // default host name
            int hostPort = 7000; // default host port

            // assign host machine name and port to connect to
            if (args.length != 0) {
                if (args[0] != null) {
                    hostName = args[0]; // user specified machine
                }
                if (args[1] != null) {
                    hostPort = Integer.parseInt(args[1]); // user specified port
                }
            }

            System.out.println("Connecting to Server...");
            // connect to server and extract input and output streams
            try (Socket serverSocket = new Socket(hostName, hostPort);
                 ObjectOutputStream os = new ObjectOutputStream(new BufferedOutputStream(serverSocket.getOutputStream()));
                 BufferedReader is = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()))) {

                // create client input stream for user input
                Scanner input = new Scanner(System.in);
                System.out.println("Enter integer list values to be sent to the server (enter letter to signify finish you are complete)");
                List<Integer> integerList = new ArrayList<>();

                while (input.hasNextInt()) {
                    int i = input.nextInt();
                    integerList.add(i);
                }
                System.out.println(integerList);
                os.writeObject(integerList);;
                os.flush();

                String response = is.readLine();
                System.out.println(response);
        } // end main
    }}