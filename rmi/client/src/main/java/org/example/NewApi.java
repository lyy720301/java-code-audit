package org.example;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface NewApi extends Remote {
    void run() throws RemoteException;
}
