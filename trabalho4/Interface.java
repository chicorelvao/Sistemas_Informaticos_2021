

package trabalho4;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;


public interface Interface extends Remote{

	public boolean loginVerify(String mail, String password) throws RemoteException;
	
	public int logUpRoutine(String newname, String newmail,String newpassword, String newaff) throws RemoteException;
	
	public Client whosClient(String passowrd) throws RemoteException ;
		
	public Client requestPubs(Client user) throws RemoteException;
	
	public Client printPubs(Client user, boolean order) throws RemoteException;
	
	public Client performance(Client user, int in) throws RemoteException;
	
	public boolean addNewPub(String title, String journal, String[] authors, int[] numbers,String user) throws RemoteException;
	
	public Client removePub(Client user, int DOI) throws RemoteException, IOException;
	
	public boolean isThisClientRegistred(String userEmail) throws RemoteException, IOException;
	
	public void writeSymbolAdding(String title, String journal, String[] authors, int[] numbers, String user_toAdd) throws IOException;

}