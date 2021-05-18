package changroberts;

import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import implementations.ImplConnection;
import structure.Node;

public class Client extends ImplConnection {
	
	public Client() {}
	
	public static void main(String[] args) throws RemoteException, UnknownHostException, AlreadyBoundException {
			
//		ImplConnection.setPaths(l);

		ImplConnection implClient = new ImplConnection();
		
		LinkedList<Node> l = new LinkedList<Node>();

		l.add(new Node(3,false,2002));
		l.add(new Node(4,false,2003));
		l.add(new Node(5,false,2004));
		l.add(new Node(1,false,2000));
		l.add(new Node(2,false,2001));

		implClient.registerRMI(2000); 

		try {
			implClient.sendMessage(2001, "Mensagem de teste",l);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
