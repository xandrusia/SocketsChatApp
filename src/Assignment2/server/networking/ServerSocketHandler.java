package Assignment2.server.networking;

import Assignment2.server.model.ModelServer;
import Assignment2.shared.transferobjects.Message;
import Assignment2.shared.transferobjects.Request;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
 import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ServerSocketHandler implements Runnable
{
    private Socket socket;
    private ModelServer model;

    private ObjectOutputStream outToClient;
    private ObjectInputStream inFromClient;

    public ServerSocketHandler(Socket socket, ModelServer model)
    {
        this.socket = socket;
        this.model = model;

        try {
            outToClient = new ObjectOutputStream(socket.getOutputStream());
            inFromClient = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run()
    {
        try
        {
            Request request = (Request)inFromClient.readObject();
            if("Listener".equals(request.getType())) {
                model.addPropertyChangeListener("newId", this::onNewId);
                model.addPropertyChangeListener("newMessage", this::onNewMessage);
                model.addPropertyChangeListener("newDirectMessage", this::onNewDirectMessage);
            }
            else if("getAllMessages".equals(request.getType()))
            {
                List<Message> messagesList = model.getAllMessages();
                outToClient.writeObject(new Request("getAllMessages", messagesList));
            }
            else if("sendMessage".equals(request.getType()))
            {
                String[] arr = ((String)request.getArg()).split(": ");
                Message newMessage = new Message(arr[1], arr[0]);
                model.sendMessage(newMessage);
                outToClient.writeObject(new Request("sendMessage", "message sent"));
            }

            else if("directMessage".equals(request.getType()))
            {
                String[] array = ((String)request.getArg()).split(": ");
                Message newMessage = new Message(array[1], array[0]);
                model.sendDirectMessage(newMessage);
                outToClient.writeObject(new Request("directMessage", "message sent"));
            }
            else if("setId".equals(request.getType()))
            {
                model.setId((String)request.getArg());
                outToClient.writeObject(new Request("setId", "id set"));
            }
            else if("getIds".equals(request.getType()))
            {
                List<String> alliases = model.getIds();
                outToClient.writeObject(new Request("getIds", alliases));
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void onNewId(PropertyChangeEvent evt)
    {
        try
        {
            outToClient.writeObject(new Request(evt.getPropertyName(), evt.getNewValue()));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onNewMessage(PropertyChangeEvent evt)
    {
        try {
            outToClient.writeObject(new Request(evt.getPropertyName(), evt.getNewValue()));
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void onNewDirectMessage(PropertyChangeEvent evt)
    {
        try
        {
            String packedUsernameAndMessage = evt.getOldValue() + ";;;;" + evt.getNewValue();
            outToClient.writeObject(new Request(evt.getPropertyName(), packedUsernameAndMessage));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
