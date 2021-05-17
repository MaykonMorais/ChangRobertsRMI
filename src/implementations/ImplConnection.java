package implementations;

import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import interfaces.IConnection;
import structure.Node;

public class ImplConnection implements IConnection{	
	
	//static Queue<Integer> paths = new LinkedList<Integer>();
	// 2001 2002 2000
	// 2002 2000 2001
	// 2001 2002 2000
	
	@Override
	public void registerRMI(Integer port) throws RemoteException, UnknownHostException, AlreadyBoundException {
		ImplConnection client = new ImplConnection();
		
		// Serviços Exportados do skeleton
		IConnection stub = (IConnection) UnicastRemoteObject.exportObject(client, 0);
		
		// Criação do Registro do Servidor
		LocateRegistry.createRegistry(port);
		Registry registro = LocateRegistry.getRegistry(port);
		
		registro.bind("Node" + port, stub);
		
		System.out.println("Cliente Rodando...\n");
	}
	
	@Override
	public String hello() {
		return "Hello";
	}
	
	// Procurar o próximo nó que ele vai enviar a mensagem
	public String loookup(Integer port) throws RemoteException, NotBoundException {
		Registry registro = LocateRegistry.getRegistry(port);
				
		IConnection stub = (IConnection) registro.lookup("Node"+port);
		
		String resposta = stub.hello();
		
		return resposta;		
	}

	@Override
	public void sendMessage(Integer port, String message, Queue<Node> paths)  {
		
		// 2002 2000 2001
		// 2000 -> 2001(x)
		try {
			Registry registro = LocateRegistry.getRegistry(port);
			
			IConnection stub = (IConnection) registro.lookup("Node"+port);
			
			// Servidor do proximo
			stub.receiveMessage(message,paths);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void receiveMessage(String message,Queue<Node> paths) throws RemoteException, NotBoundException, InterruptedException {
		System.out.println("Mensagem Recebida: " + message);

		Node next = paths.remove();
		paths.add(next);
		
		Thread.sleep(100);
		
		System.out.println("Proxima Porta: " + next);
		this.sendMessage(next.getNeighbours(), message+1,paths);
	}	
}
