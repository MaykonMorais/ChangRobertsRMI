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
	public void sendMessage(Integer port, String message, Queue<Node> paths) throws RemoteException {

		// inicial :: 2001   fila :: 2002 2000 2001
		// 2002 2000 2001
		// 2001 -> 2002(x) >> 2000
		try {
			Registry registro = LocateRegistry.getRegistry(port);
			
			IConnection stub = (IConnection) registro.lookup("Node"+port);
			
			// Servidor do proximo
			stub.receiveMessage(message,paths);
		} catch(Exception e) {

			//Movimenta fila
			Node next = paths.remove();
			paths.add(next);

			election(String.valueOf(port),port,paths);
			e.printStackTrace();
		}
	}

	@Override
	public void election(String msg,Integer port, Queue<Node> paths) throws RemoteException{

		String m[] = msg.split(";");

		if(m[m.length-1].equalsIgnoreCase(String.valueOf(port))){

			try {
				Registry registro = LocateRegistry.getRegistry(port);

				IConnection stub = (IConnection) registro.lookup("Node" + port);

				// Servidor do proximo
				stub.receiveElection(String.valueOf(port), paths);
			} catch (Exception e) {

				//Movimenta fila
				Node next = paths.remove();
				paths.add(next);

				election(String.valueOf(port), port, paths);
				e.printStackTrace();
			}
		}else{

			//se minha porta de atual for igual ao topo da eleição

			System.out.println("Eleição" + port);
		}
	}

	@Override
	public void receiveElection(String message,Queue<Node> paths) throws RemoteException, NotBoundException, InterruptedException {

		Node next = paths.remove();
		paths.add(next);

		System.err.println(message+";"+next.getNeighbours());
		String msg = message+";"+next.getNeighbours();
		Thread.sleep(100);

		System.out.println("Proxima Porta: " + next.getNeighbours());
		this.election(msg,next.getNeighbours(),paths);
	}

	@Override
	public void receiveMessage(String message,Queue<Node> paths) throws RemoteException, NotBoundException, InterruptedException {
		System.out.println("Mensagem Recebida: " + message);

		Node next = paths.remove();
		paths.add(next);
		
		Thread.sleep(100);
		
		System.out.println("Proxima Porta: " + next.getNeighbours());
		this.sendMessage(next.getNeighbours(), message+1,paths);
	}	
}
