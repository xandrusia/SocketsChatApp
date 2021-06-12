package Assignment2.client.model;

import Assignment2.client.networking.Client;
import Assignment2.shared.transferobjects.Message;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.List;

public class ModelManagerClient implements ModelClient
{
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private Client client;
    private String alias;

    public ModelManagerClient(Client client) throws IOException
    {
        this.client = client;
        client.startClient();
        client.addPropertyChangeListener("newMessage", this::onNewMessage);
        client.addPropertyChangeListener("newId", this::onNewId);
        client.addPropertyChangeListener("newDirectMessage", this::onNewDirectMessage);
    }

    private void onNewDirectMessage(PropertyChangeEvent evt)
    {
        String [] arr = String.valueOf(evt.getNewValue()).split(": ");

        if((alias.equals(evt.getOldValue()) || arr[0].equals(alias)))
        {
            pcs.firePropertyChange("newDirectMessage", evt.getOldValue(), evt.getNewValue());
        }
    }

    public void onNewMessage(PropertyChangeEvent evt)
    {
        pcs.firePropertyChange("newMessage", null, evt.getNewValue());
    }

    public void onNewId(PropertyChangeEvent evt)
    {
        pcs.firePropertyChange("newId", null, evt.getNewValue());
    }

    @Override
    public void setId(String id)
    {
        client.setId(id);
        alias = id;
    }

    @Override
    public List<String> getIds()
    {
        return client.getIds();
    }

    @Override
    public List<Message> getAllMessages()
    {
        return client.getAllMessages();
    }

    @Override
    public void sendMessage(Message msg)
    {
        client.sendMessage(msg);
    }



    @Override
    public void sendDirectMessage(Message msg)
    {
        client.sendDirectMessage(msg);
    }

    @Override
    public String getAlias()
    {
        return alias;
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
