package Assignment2.server.model;

import Assignment2.shared.transferobjects.Message;
import Assignment2.shared.util.PropertyChangeSubject;

import java.util.List;

public interface ModelServer extends PropertyChangeSubject
{
    void setId(String id);
    List<String> getIds();
    List<Message> getAllMessages();
    void sendMessage(Message msg);

    void sendDirectMessage(Message msg);
}
