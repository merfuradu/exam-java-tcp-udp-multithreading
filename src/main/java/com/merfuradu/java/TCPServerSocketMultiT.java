package com.merfuradu.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TCPServerSocketMultiT {
    // private ArrayList<ClientHandler> clients;
    private ServerSocket server;
    private VectorThread vt;
    private File f;

    public TCPServerSocketMultiT(int port) throws IOException {
        // this.clients = new ArrayList<>();
        this.server = new ServerSocket(port);
    }

    public int getPort() {
        return this.server.getLocalPort();
    }

    public void setPort(int port) throws IOException {
        this.server = new ServerSocket(port);
    }

    public void setFileName(String newFName) {
        if (f != null) {
            this.f = new File(newFName);
        } else {
            this.f = null;
        }
    }

    public void startTCPServer() throws IOException {
        System.out.println("The server is open and waiting for connections");

        while (true) {
            Socket clientSocket = server.accept();

            new Thread(() -> {
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                    PrintWriter outt = new PrintWriter(clientSocket.getOutputStream(), true);
                    outt.println("Welcome to the server");
                    VectorThread vt = new VectorThread(this.f.getAbsolutePath());
                    ArrayList<Vehicle> vehicles = vt.getCarList();
                    List<Car> cars = new ArrayList<>();

                    for (Vehicle veh : vehicles) {
                        if (veh instanceof Car) {
                            cars.add((Car) veh);
                        }
                    }
                    String messageFromClient;
                    while ((messageFromClient = in.readLine()) != null) {
                        if ("EXIT".equals(messageFromClient)) {
                            System.out.println("EXIT command received, we closed the connection.");
                            break;
                        } else if (messageFromClient.equals("GETFILE")) {
                            out.writeObject(cars);
                            out.flush();
                            System.out.println("The cars list was sent to the client");
                        } else if (messageFromClient.equals("GETDB")) {
                            try {
                                UtilsDAO.setConnection();
                                String data = UtilsDAO.selectData(UtilsDAO.getConnection());
                                out.writeObject(data);
                                out.flush();
                                System.out.println("The database informations were sent to the client!");
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        } else {
                            outt.println("You said: " + messageFromClient);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        clientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    // public void run() {
    // try {
    // if (this.server != null) {
    // System.out.println("The server is waiting");
    // while (true) {
    // Socket client = server.accept();
    // ClientHandler handler = new ClientHandler(client);
    // clients.add(handler);
    // new Thread(new ClientHandler(client)).start();
    // }
    // }
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // }

    // public class ClientHandler implements Runnable {
    // private Socket client;
    // private BufferedReader in;
    // private PrintWriter out;

    // public ClientHandler(Socket client) throws IOException {
    // this.client = client;

    // out = new PrintWriter(client.getOutputStream(), true);
    // in = new BufferedReader(new InputStreamReader(client.getInputStream()));
    // }

    // @Override
    // public void run() {
    // try {
    // out.println("Welcome to the server");
    // String messageFromClient;
    // while ((messageFromClient = in.readLine()) != null) {
    // if (messageFromClient.equals("/exit")) {
    // System.out.println("The client has disconnected.");
    // break;
    // }
    // System.out.println("Client said " + messageFromClient);
    // out.println("You said: " + messageFromClient);
    // }

    // } catch (IOException e) {
    // e.printStackTrace();
    // } finally {
    // try {
    // client.close();
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // }
    // }
    // }

    public static void main(String[] args) {
        try {
            int port = 50001;
            TCPServerSocketMultiT server = new TCPServerSocketMultiT(port);
            server.setFileName("carList.txt");
            server.startTCPServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}// end of the class
