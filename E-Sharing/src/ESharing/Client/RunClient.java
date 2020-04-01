package ESharing.Client;

import java.rmi.RMISecurityManager;

public class RunClient
{
    public static void main(String[] args)
    {
        System.setProperty("java.security.policy", "security.policy");

        if (System.getSecurityManager() == null)
        {
            System.setSecurityManager(new RMISecurityManager());
        }
    }
}
