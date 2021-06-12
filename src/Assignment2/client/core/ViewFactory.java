package Assignment2.client.core;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Assignment2.client.views.ViewsController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ViewFactory
{
    private static Map<String, Scene> scenes;
    private static Stage stage;

    static
    {
        scenes = new HashMap<>();
    }

    public static void init(Stage theStage) throws IOException
    {
        stage = theStage;
        createScene("LogIn");
        createScene("Chat");
    }

    public static void createScene(String sceneName) throws IOException
    {
        Scene scene = null;
        if(sceneName.equals("Chat"))
        {
            System.out.println("Create chat");
            Parent root = loadFXML("../views/view2/Chat.fxml");

            stage.setTitle("Chat");
            scene = new Scene(root);
        } else if (sceneName.equals("LogIn"))
        {
            System.out.println("Create Login");
            Parent root = loadFXML("../views/view1/Login.fxml");


            stage.setTitle("LogIn");
            scene = new Scene(root);
        }
        scenes.put(sceneName, scene);
    }

    private static Parent loadFXML(String path) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ViewFactory.class.getResource(path));
        Parent root = loader.load();

        ViewsController ctrl = loader.getController();
        ctrl.init(ViewHandler.getInstance(), ViewModelFactory.getInstance());
        return root;
    }

    public static Scene getScene(String sceneName)
    {
        return scenes.get(sceneName);
    }
}