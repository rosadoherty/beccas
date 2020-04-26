    import java.io.*;
    import java.net.ServerSocket;
    import java.net.Socket;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.Scanner;

    public class IterativeServer {

        public static void main(String[] args) {
            // determine if port to listen on is specified by user else use default
            int portNumber = 4444; // default port number
            if (args.length == 1) {
                portNumber = Integer.parseInt(args[0]); // user specified port number
            }
            System.out.println("Server Started");
            int clientIdNumber = 0;

            // create serverSocket to listen on
            try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
                //while(true) {
                // accept client connection
                Socket clientSocket = serverSocket.accept();
                try (
                        ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(clientSocket.getInputStream()));
                        PrintWriter os = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {
                    {
                        System.out.println("Client Accepted");
                        clientIdNumber++; // give id number to connecting client

                        Object object = is.readObject();
                        List<Integer> integerList = (ArrayList<Integer>) object;
                        System.out.println("Client: " + clientIdNumber + " Integer List: " + integerList);

                        List<Integer> sortedList;
                        sortedList = SubsequenceLogic.longestConsecutiveIntegerList(integerList);
                        os.println("Length of the longest consecutive subsequence is: " + sortedList.size()
                                + " Longest subsequence is: " + sortedList);
                        os.flush();
                    }
                } // end while true
            } catch (Exception e) {
                System.out.println("Exception:" + e.getMessage());
            } // end catch
        } // end main
    }