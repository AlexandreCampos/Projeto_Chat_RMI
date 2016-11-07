/* NOMES
 * Alexandre R.Campos
 * Misael
 * Ricardo
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.*;
import java.rmi.server.*;
import java.util.Scanner;


public class Proc extends UnicastRemoteObject implements InterfaceProc{	

	public Proc() throws RemoteException {}
		
	public static void main (String args[]) throws RemoteException{
		
		int idCliente;		
		String msgmDigitada;
		
		try {
			
			// Disponibiliza os servicos do cliente
			Proc cliente = new Proc();
			Naming.rebind("cliente", cliente);
			System.out.println("Cliente iniciado");
			
						
			Scanner entrada = new Scanner(System.in);
			BufferedReader entrada2 = new BufferedReader(new InputStreamReader(System.in));
			
			System.out.println("Digite seu nome: ");
			String nome = entrada.next();
			
			String hostname = args[0];
			String ip = args[1];
			
			
			// Acessa os servicos do servidor			
			Remote referenciaRemota = Naming.lookup("rmi://" + hostname + "/servidor");
			InterfaceServidor servidor = (InterfaceServidor) referenciaRemota;
			
			// cliente rebe o seu id
			idCliente = servidor.estabeleceConexao(ip);
			System.out.println("Id do cliente: " + idCliente);
						
			
			// Loop para cliente ler msg e enviar para todos			
			msgmDigitada = entrada2.readLine();
			while(msgmDigitada.compareTo("sair") != 0){
				servidor.sendToAll(msgmDigitada, idCliente, nome);
				msgmDigitada = entrada2.readLine();
				/*if (msgmDigitada.compareTo("id") == 0){
					System.out.println("id do usuario: " + idCliente);
				}*/
			}
			
			System.out.println("Saiu do chat.");
			servidor.liberaConexao(idCliente);
			System.exit(1);
			
		} catch (Exception e) {
			System.out.println("Exception: " + e.toString());
		}
	}
	
		
	// imprime a msg no respectivo cliente
	public void atribuiMensagem(String _msgm) throws RemoteException{
		
		System.out.println(_msgm);
	}
		
}
