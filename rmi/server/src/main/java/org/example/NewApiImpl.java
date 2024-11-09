package org.example;

import java.io.IOException;
import java.rmi.RemoteException;

public class NewApiImpl implements NewApi{
    @Override
    public void run() throws RemoteException {
        System.out.println("run..");
    }
}
