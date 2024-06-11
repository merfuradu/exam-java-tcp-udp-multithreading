package com.merfuradu.java;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

public class TCPClient {
    private int port;
    private Socket socket;

    public TCPClient(int port) {
        this.port = port;
        try {
            socket = new Socket("localhost", port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void startTCPClient() {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                ObjectInputStream reader = new ObjectInputStream(socket.getInputStream())) {

            writer.write("GETFILE\n");
            writer.flush();
            List<Vehicle> list = (List<Vehicle>) reader.readObject();
            System.out.println("\nTest 4.1 list:");
            for (Vehicle car : list)
                System.out.println(car.toString());

            writer.write("GETJSON\n");
            writer.flush();
            System.out.println("\nTest 4.2 JSON:");
            System.out.println(reader.readUTF());

            writer.write("GETDB\n");
            writer.flush();
            System.out.println("\nTest 4.3 DB:");
            System.out.println(reader.readUTF());

            writer.write("EXIT\n");
            writer.flush();
            socket.close();
            System.out.println("Test 4.4 Closed TCP");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        TCPClient client = new TCPClient(50001);
        client.startTCPClient();
    }
}