package Assignment2.shared.transferobjects;

import java.io.Serializable;

public class Message implements Serializable
{
    private String text, alias;

    public Message(String txt, String clientUsername)
    {
        text = txt;
        alias = clientUsername;
    }

    public String getText()
    {
        return text;
    }

    public String getAlias()
    {
        return alias;
    }

    public String toString()
    {
        return alias + ": " + text;
    }
}
