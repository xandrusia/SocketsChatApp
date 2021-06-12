package Assignment2.server.model;

import Assignment2.shared.transferobjects.Message;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class ModelManagerServer implements ModelServer
{

    private PropertyChangeSupport pcs;
    private List<Message> messages;
    private ArrayList<String> arrayOfIds = new ArrayList<>();

    public ModelManagerServer()
    {
        pcs = new PropertyChangeSupport(this);
        messages = new ArrayList<>();
    }

    @Override
    public void setId(String id)
    {
        arrayOfIds.add(id);
        pcs.firePropertyChange("newId", null, arrayOfIds);
    }

    @Override
    public List<String> getIds()
    {
        return arrayOfIds;
    }

    @Override
    public List<Message> getAllMessages()
    {
        return new ArrayList<>(messages);
    }

    @Override
    public void sendMessage(Message msg)
    {
        messages.add(msg);
        pcs.firePropertyChange("newMessage", null, msg.toString());
    }

    @Override
    public void sendDirectMessage(Message msg)
    {
        messages.add(msg);
        pcs.firePropertyChange("newDirectMessage", messages.get(0).getAlias(), msg.toString());
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
