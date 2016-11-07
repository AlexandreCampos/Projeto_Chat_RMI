import java.rmi.Remote;
import java.rmi.RemoteException;


public interface InterfaceServidor extends Remote{
	
	public int estabeleceConexao(String ipCliente) throws RemoteException;
	public void liberaConexao(int _idCliente) throws RemoteException;
	public void sendToAll(String _msgm, int _idCliente, String _nome) throws RemoteException;
	
}
