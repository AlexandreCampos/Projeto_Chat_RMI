import java.rmi.*;


public interface InterfaceProc extends Remote {
	
	public void atribuiMensagem(String _msgm) throws RemoteException;
	
}
