package changroberts;

import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;

import implementations.ImplConnection;
import structure.Node;

public class Client1 {
	public Client1() {}
	
	public static void main(String[] args) throws RemoteException, UnknownHostException, AlreadyBoundException, NotBoundException {
		//Node node = new Node(1, false, (ArrayList<Integer>) Arrays.asList(2002, 2000));

		ImplConnection implClient = new ImplConnection();
		
		implClient.registerRMI(2001);
		
//		System.out.println("Mensagem Recebido do client 2000" + implClient.loookup(2000));
	}
}
