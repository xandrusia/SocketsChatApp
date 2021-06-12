package Assignment2.client;

import Assignment2.client.core.ViewHandler;
import javafx.application.Application;
import javafx.stage.Stage;

public class Assignment2 extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        ViewHandler.getInstance().start();
    }
}
