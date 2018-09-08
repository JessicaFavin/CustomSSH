import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class CustomSSH {

    private static BufferedReader reader;
    private static PrintWriter writer;
    private static HashMap<String, String> usersList;

    private boolean authentication(){
        boolean valid = false;
        try {
            writer.write("Login : ");
            writer.flush();
            String login = reader.readLine();
            writer.write("Password : ");
            writer.flush();
            String password = reader.readLine();
            valid = checkCredentials(login,password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(!valid) {
            authentication();
        }
        return valid;

    }

    private boolean checkCredentials(String login, String password) {
        return false;
    }

    private void loadCredentials() {
        String csvFile = "users.csv";
        BufferedReader br = null;
        String line = "";
        usersList = new HashMap<>();
        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                String[] country = line.split(":");

                System.out.println("Country [code= " + country[4] + " , name=" + country[5] + "]");

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        int port = 4584;
        ServerSocket srvSock;
        Socket socket;


        //create server socket and wait for connexion
        try {
            System.out.println("Establishing the server socket...");
            srvSock = new ServerSocket(port);
            System.out.println("Server socket established to "+srvSock.getInetAddress().getHostName()+" at port number "+srvSock.getLocalPort()+".");
            try {
                socket = srvSock.accept();
                System.out.println("New connection stablished with "+socket.getInetAddress().getHostName()+" (port " + socket.getPort()+"), trying to communicate...");
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            } catch (Exception e) {
                System.out.println("Problem with client.\n");
            }
        } catch (Exception e) {
            System.out.println("Failed to create server socket:");
            e.printStackTrace();
        }
        //authentication

        //launch local command line
        //manage in/out
    }
}
