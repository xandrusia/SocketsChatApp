package Assignment2.server;

import Assignment2.server.model.ModelManagerServer;
import Assignment2.server.networking.SocketServer;

public class RunServer
{
    public static void main(String[] args)
    {
        SocketServer ss = new SocketServer(new ModelManagerServer());
        ss.startServer();
    }
}
