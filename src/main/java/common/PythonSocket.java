package common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by ercan on 11.06.2017.
 */
public class PythonSocket {
    public static Socket pythonSocket;

    static{
        new Thread(new Runnable() {
            public void run() {
                try {
                    ServerSocket serverSocket = new ServerSocket(6666);
                    pythonSocket = serverSocket.accept();
                    System.out.println("soket bağlandı.");
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
            }
        }).start();
    }

    public static boolean askForPrediction(){
        try{
            PrintWriter printWriter = new PrintWriter(pythonSocket.getOutputStream(),true);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(pythonSocket.getInputStream()));

            String message = "ready";
            printWriter.println(message);
            String status = bufferedReader.readLine();
            //System.out.println(prediction);

            return true;
        } catch(Exception ex){
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            return false;
        }
    }



}
