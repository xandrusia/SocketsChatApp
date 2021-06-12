package Assignment2.client.networking;

import Assignment2.shared.transferobjects.Message;
import Assignment2.shared.transferobjects.Request;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class SocketClient implements Client
{
    private PropertyChangeSupport pcs;
    private ObjectOutputStream outToServer;
    private ObjectInputStream inFromServer;

    public SocketClient()
    {
        pcs = new PropertyChangeSupport(this);
    }

    @Override
    public void startClient()
    {
            Thread thread = new Thread(() -> listenToServer());
            thread.start();
    }

    private void listenToServer()
    {
        try(Socket socket = new Socket("localhost", 1235))
        {
            outToServer = new ObjectOutputStream(socket.getOutputStream());
            inFromServer = new ObjectInputStream(socket.getInputStream());

            outToServer.writeObject(new Request("Listener", null));
            while(true)
            {
                Request request = (Request) inFromServer.readObject();
                //this message will be only send yo the earliest joined user
                if(String.valueOf(request.getArg()).contains(";;;;"))
                {
                    String [] array = String.valueOf(request.getArg()).split(";;;;");
                    pcs.firePropertyChange(request.getType(), array[0], array[1]);
                }
                else
                {
                    pcs.firePropertyChange(request.getType(), null, request.getArg());
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setId(String id)
    {
        try
        {
            request(id, "setId");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getIds()
    {
        try
        {
            Request response = request(null, "getIds");
            return (List<String>)response.getArg();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Message> getAllMessages()
    {
        try
        {
            Request response = request(null, "getMessageList");
            return (List<Message>)response.getArg();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void sendMessage(Message msg)
    {
        try
        {
            String message = msg.getAlias() + ": " +msg.getText();
            request(message, "sendMessage");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendDirectMessage(Message msg)
    {
        try
        {
            String message = msg.getAlias() + ": " +msg.getText();
            request(message, "directMessage");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Request request(String arg, String type) throws IOException, ClassNotFoundException
    {
        Socket socket = new Socket("localhost", 1235);
        ObjectOutputStream outToServer = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream inFromServer = new ObjectInputStream(socket.getInputStream());
        outToServer.writeObject(new Request(type, arg));
        Request request = (Request)inFromServer.readObject();
        pcs.firePropertyChange(request.getType(), null, request.getArg());
        return request;
    }

    @Override
    public void addPropertyChangeListener(String name, PropertyChangeListener listener)
    {
        pcs.addPropertyChangeListener(name, listener);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        pcs.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(String name, PropertyChangeListener listener)
    {
        pcs.removePropertyChangeListener(name, listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener)
    {
        pcs.removePropertyChangeListener(listener);
    }
}
