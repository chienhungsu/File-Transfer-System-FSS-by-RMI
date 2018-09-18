import java.rmi.Remote;
import java.rmi.RemoteException;
public interface MethodInterface extends Remote {
	public String say() throws RemoteException;
	public String[] dir(String path)throws java.rmi.RemoteException;	  
	public String upload(byte []data,String path,int length) throws RemoteException;
	public byte [] download(String path,String name) throws RemoteException;
	public String shutdown()throws RemoteException;
	public void mkdir(String serverpath, String Filename) throws RemoteException;
	public void rmdir(String serverpath, String Filename) throws RemoteException;
	public void remove(String serverpath, String Filename) throws RemoteException;

}
