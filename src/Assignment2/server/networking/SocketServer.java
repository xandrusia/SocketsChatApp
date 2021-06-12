package Assignment2.server.networking;

import Assignment2.server.model.ModelServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SocketServer
{
    private ModelServer model;

    public SocketServer(ModelServer model)
    {
        this.model = model;
    }

    public void startServer()
    {
        try {
            ServerSocket welcomeSocket = new ServerSocket(1235);
            List<ServerSocketHandler> handlers = new ArrayList<>();
            while (true) {
                Socket socket = welcomeSocket.accept();
                ServerSocketHandler handler = new ServerSocketHandler(socket, model);
                handlers.add(handler);
                System.out.println("handlers:" + handlers.size());
                new Thread(handler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
