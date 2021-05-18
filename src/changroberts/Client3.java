package changroberts;

import implementations.ImplConnection;

import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client3 {

public Client3() {}
	
	public static void main(String[] args) throws RemoteException, UnknownHostException, AlreadyBoundException, NotBoundException {
		//Node node = new Node(1, false, (ArrayList<Integer>) Arrays.asList(2000, 2001));
		
		ImplConnection implClient = new ImplConnection();
		
		implClient.registerRMI(2003);
		
//		System.out.println("Mensagem Recebido do client 2000" + implClient.loookup(2000));
	}
}
