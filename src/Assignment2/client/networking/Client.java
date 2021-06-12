package Assignment2.client.networking;

import Assignment2.shared.transferobjects.Message;
import Assignment2.shared.util.PropertyChangeSubject;

import java.io.IOException;
import java.util.List;

public interface Client extends PropertyChangeSubject
{
    void setId(String id);
    List<String> getIds();
    List<Message> getAllMessages();
    void sendMessage(Message msg);
    void sendDirectMessage(Message msg);

    void startClient() throws IOException;
}
