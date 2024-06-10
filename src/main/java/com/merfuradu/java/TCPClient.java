package com.merfuradu.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class TCPClient {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int port = 50001;
        String serverAddress = "localhost";

        try {
            Socket socket = new Socket(serverAddress, port);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String messageFromServer;
            while ((messageFromServer = in.readLine()) != null) {
                System.out.println("Server says: " + messageFromServer);
                String scanned = scanner.nextLine();
                if (scanned.equalsIgnoreCase("/exit")) {
                    out.println(scanned);
                    break;
                }
                out.println(scanned);
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }// end main
}
