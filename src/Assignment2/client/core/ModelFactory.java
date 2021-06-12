package Assignment2.client.core;

import Assignment2.client.model.ModelClient;
import Assignment2.client.model.ModelManagerClient;

import java.io.IOException;

public class ModelFactory
{
    private static ModelFactory instance = new ModelFactory();
    public static ModelFactory getInstance()
    {
        return instance;
    }

    private ModelClient model;
    private ModelFactory(){}

    public ModelClient getModel() throws IOException
    {
        if(model == null)
        {
            model = new ModelManagerClient(ClientFactory.getInstance().getClient());
        }
        return model;
    }
}
