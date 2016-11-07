/* NOMES
 * Alexandre R.Campos
 * Misael
 * Ricardo
 */


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.*;
import java.rmi.server.*;
import java.util.ArrayList;


// INCLUIR LEIA-ME

public class Servidor extends UnicastRemoteObject implements InterfaceServidor {
	
	Object obj;
	ArrayList<InterfaceProc> clientesConectados;	
	private int idCliente;
	
	
	protected Servidor() throws RemoteException {
		
		clientesConectados = new ArrayList<InterfaceProc>();
		idCliente = -1;
	}

	 
	public static void main(String args[]) throws RemoteException{
		
		try {
			// Disponibiliza os servicos do servidor			
			Servidor servidor = new Servidor();						
			Naming.rebind("servidor", servidor);
			System.out.println("Servidor no ar...");		
			
		} catch (Exception e) {

			System.out.println("Problema: " + e);			
		}
	}
	
	/*	 
	 * Estabelece conexao com um novo cliente
	 * idCliente eh incrementado +1
	 * retorna um novo idCliente para o novo cliente
	 */
	public int estabeleceConexao(String ipCliente) throws RemoteException{
			
		idCliente ++;
		
		try {
			// Acessa os servicos do cliente (Proc)
			obj  = Naming.lookup("rmi://" + ipCliente + "/cliente");
			clientesConectados.add((InterfaceProc) obj);			
		} catch (Exception e) {
			System.out.println("Exception " + e.toString());
		}
				
		return idCliente;
	}
	
	/* Deleta o usuario do chat
	 * _idCliente informa qual o usuario que vai ser deletado 
	 */
	public void liberaConexao(int _idCliente) throws RemoteException{
		
		clientesConectados.set(_idCliente, null);		
	}
	
	/* Envia msg para todos os clientes
	 * exceto para o "_idCliente" e...
	 * exceto para o NULL (usuario que foi deletado) 
	 */
	public void sendToAll(String _msgm, int _idCliente, String _nome) throws RemoteException{
		
		
		//System.out.println("id cliente: " + _idCliente);
		for(int i = 0; i <= idCliente; i++){			
			String mensagem_completa = "";			
			if((clientesConectados.get(i) != null) && (i != _idCliente)){				
				//System.out.println("i: " + i);
				mensagem_completa = "Cliente " + _nome + " disse: " + _msgm;
				clientesConectados.get(i).atribuiMensagem(mensagem_completa);
			}
		}
	}

}
