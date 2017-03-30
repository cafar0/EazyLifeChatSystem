package client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Created by Antoniu on 30-Mar-17.
 */
public class StartClientService {
    private String name;

    public void startClientService(String name) {
        if (this.name == null) {
            this.name = name;
            try {
                ClientServiceI csi = new ClientService();
                System.out.println("the name to start " + name);
                Naming.rebind(name, csi);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void stopClientService() {
        if (name != null) {
            try {
                Naming.unbind(name);
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (NotBoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }
}
