package Assignment2.client.views.view2;

import Assignment2.client.model.ModelClient;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableObjectValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Assignment2.shared.transferobjects.Message;
import java.beans.PropertyChangeEvent;
import java.util.List;

public class ChatViewModel
{

    private StringProperty usersList, newMessage, youLabel, amountLabel;
    private ObservableList<String> allMessages;

    private ModelClient model;
    private int count = 0;

    public ChatViewModel(ModelClient model)
    {
        this.model = model;
        System.out.println(model.getIds().toString());
        usersList = new SimpleStringProperty( model.getIds().toString());
        youLabel = new SimpleStringProperty( model.getAlias());
        amountLabel = new SimpleStringProperty(String.valueOf(incrementCount()));
        newMessage = new SimpleStringProperty();
        allMessages = FXCollections.observableArrayList();
        model.addPropertyChangeListener("newMessage", this::onNewMessage);
        model.addPropertyChangeListener("newId", this::onNewIds);
        model.addPropertyChangeListener("newDirectMessage", this::onNewMessage);
    }

    private void onNewIds(PropertyChangeEvent evt)
    {
        Platform.runLater(() ->
        {
            usersList.set( model.getIds() + " " );
            youLabel.set( model.getAlias());

        });
    }


    public String incrementCount()
    {
        List<String> arr = model.getIds();

         switch (arr.size())
         {
             case 0:
                 return "no one";

             case 1:
                 return arr.size() + " chatter";

             default:
                 return arr.size() + " chatters";

         }
    }





    private void onNewMessage(PropertyChangeEvent evt)
    {
        Platform.runLater(() ->
        {
            allMessages.add(String.valueOf(evt.getNewValue()));
        });
    }

    public void addNewMessage(Message msg)
    {
        model.sendMessage(msg);
    }    
    
    public void addToFirstMessage(Message message)
    {
        model.sendDirectMessage(message);
    }

    public String getAlias()
    {
        return model.getAlias();
    }


    public StringProperty usersListProperty()
    {
        return usersList;
    }



    public StringProperty newMessageProperty()
    {
        return newMessage;
    }

    public void showMessages()
    {
        ObservableList<String> messagesList = FXCollections.observableArrayList();
        allMessages = FXCollections.observableArrayList(messagesList);
    }

    public ObservableList<String> observableMessages()
    {
        return allMessages;
    }


    public ObservableObjectValue<String> amountLabelProperty()
    {
        return amountLabel;
    }

    public ObservableObjectValue<String> youLabelProperty()
    {
        return youLabel;
    }
}
