package com.merfuradu.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class TCPServerSocketMultiT implements Runnable {
    private ArrayList<ClientHandler> clients;

    public TCPServerSocketMultiT() {
        this.clients = new ArrayList<>();
    }

    public void run() {
        try (ServerSocket server = new ServerSocket(50001)) {
            System.out.println("The server is waiting");
            while (true) {
                Socket client = server.accept();
                ClientHandler handler = new ClientHandler(client);
                clients.add(handler);
                new Thread(new ClientHandler(client)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class ClientHandler implements Runnable {
        private Socket client;
        private BufferedReader in;
        private PrintWriter out;

        public ClientHandler(Socket client) throws IOException {
            this.client = client;

            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        }

        @Override
        public void run() {
            try {
                out.println("Welcome to the server");
                String messageFromClient;
                while ((messageFromClient = in.readLine()) != null) {
                    if (messageFromClient.equals("/exit")) {
                        System.out.println("The client has disconnected.");
                        break;
                    }
                    System.out.println("Client said " + messageFromClient);
                    out.println("You said: " + messageFromClient);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        TCPServerSocketMultiT server = new TCPServerSocketMultiT();
        new Thread(server).start();
    }

}// end of the class
