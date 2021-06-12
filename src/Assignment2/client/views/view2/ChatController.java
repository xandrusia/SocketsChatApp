package Assignment2.client.views.view2;

import javafx.scene.control.Button;
import Assignment2.client.core.ViewHandler;
import Assignment2.client.core.ViewModelFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import Assignment2.client.views.ViewsController;
import Assignment2.shared.transferobjects.Message;

import java.io.IOException;

public class ChatController implements ViewsController
{

    @FXML
    private ListView<String> messageListView;
    @FXML
    private Label usersList, youLabel, amountLabel;
    @FXML
    private TextField messageTextField;
    @FXML
    private Button sendToAll;
    @FXML
    private Button sendToUser;



    private ChatViewModel vm;
    private ViewHandler vh;

    @Override
    public void init(ViewHandler vh, ViewModelFactory vmf) throws IOException
    {
        this.vh = vh;
        this.vm = vmf.getChatViewModel();

        youLabel.textProperty().bind(vm.youLabelProperty());
        amountLabel.textProperty().bind(vm.amountLabelProperty());
        usersList.textProperty().bind(vm.usersListProperty());
        vm.showMessages();
        messageListView.setItems(vm.observableMessages());
        messageTextField.textProperty().bindBidirectional(vm.newMessageProperty());


    }

    public void onSendToAllButton(ActionEvent actionEvent)
    {
        if(!messageTextField.equals(""))
        {
            vm.addNewMessage(new Message(messageTextField.getText(), vm.getAlias()));
            messageTextField.clear();
        }
    }

    public void onSendToUserButton(ActionEvent actionEvent)
    {
        if(!messageTextField.equals(""))
        {
            vm.addToFirstMessage(new Message(messageTextField.getText(), vm.getAlias()));
            messageTextField.clear();
        }
    }
}
