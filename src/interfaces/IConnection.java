package interfaces;

import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Queue;

import structure.Node;

public interface IConnection extends Remote {
	public void registerRMI(Integer port) throws RemoteException, UnknownHostException, AlreadyBoundException;
	public String hello() throws RemoteException;
	
	public void sendMessage(Integer port, String message, Queue<Node> paths) throws RemoteException, NotBoundException,InterruptedException;
	public void receiveMessage(String message, Queue<Node> paths) throws RemoteException, NotBoundException, InterruptedException;
	
}
