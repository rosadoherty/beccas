import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SubsequenceProgramConcurrentServer extends Thread {
    // This server acts as a multi-threaded server.
    // A number of clients can connect at once.
    private final Socket clientSocket; // client socket for each thread connected
    int clientIdNumber;

    // Constructor
    SubsequenceProgramConcurrentServer(Socket clientSocket, int clientIdNumber) {
        this.clientSocket = clientSocket;
        this.clientIdNumber = clientIdNumber;
    } // end constructor

    // run method for thread
    @Override
    public void run() {
        try (ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(clientSocket.getInputStream()));
             PrintWriter os = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())))) {
            System.out.println("New Server Thread Running");

            Object object = is.readObject();
            List<Integer> integerList = (ArrayList<Integer>) object;
            System.out.println("Client: " + clientIdNumber + " Integer List: " + integerList);

            List<Integer> sortedList;
            sortedList = SubsequenceLogic.longestConsecutiveIntegerList(integerList);
            os.println("Length of the longest consecutive subsequence is: " + sortedList.size()
                    + " Longest subsequence is: " + sortedList);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    } // end run
    public static void main(String[] args) {
        // determine if port to listen on is specified by user else use default
        int portNumber = 7000; // default port number

        if (args.length == 1) {
            portNumber = Integer.parseInt(args[0]); // user specified port number
        }
        int clientIdNumber = 0;
        System.out.println("Guess Number Concurrent Server Started");
        // create serverSocket to listen on
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client Accepted from " + clientSocket.getInetAddress());
                clientIdNumber++; // give id number to connecting client
                // spawn a new thread to handle new client
                SubsequenceProgramConcurrentServer concurrentServerThread = new SubsequenceProgramConcurrentServer(clientSocket, clientIdNumber);
                System.out.println("About to start new thread");
                concurrentServerThread.start();
            } // end while true
        } catch (Exception e) {
            System.out.println("Exception:" + e.getMessage());
        } // end catch
    }
}
