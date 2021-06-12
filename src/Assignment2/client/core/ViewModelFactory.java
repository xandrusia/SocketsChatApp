package Assignment2.client.core;

import Assignment2.client.views.view2.ChatViewModel;
import Assignment2.client.views.view1.LoginViewModel;

import java.io.IOException;

public class ViewModelFactory
{
    private static ViewModelFactory instance = new ViewModelFactory();

    public static ViewModelFactory getInstance()
    {
        return instance;
    }

    private LoginViewModel loginViewModel;
    private ChatViewModel chatViewModel;

    private ViewModelFactory()
    {

    }

    public LoginViewModel getLoginViewModel() throws IOException
    {
        if(loginViewModel == null)
        {
            loginViewModel = new LoginViewModel(ModelFactory.getInstance().getModel());
        }
        return loginViewModel;
    }

    public ChatViewModel getChatViewModel() throws IOException
    {
        if(chatViewModel == null)
        {
            chatViewModel = new ChatViewModel(ModelFactory.getInstance().getModel());
        }
        return chatViewModel;
    }
}
