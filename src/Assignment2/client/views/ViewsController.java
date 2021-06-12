package Assignment2.client.views;

import Assignment2.client.core.ViewHandler;
import Assignment2.client.core.ViewModelFactory;

import java.io.IOException;

public interface ViewsController
{
    void init(ViewHandler vh, ViewModelFactory vmf) throws IOException;
}
