import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;

public class Method  extends UnicastRemoteObject implements MethodInterface{
	 private String message;
	public Method(String msg) throws RemoteException{
		// TODO Auto-generated constructor stub
		message = msg; 
	}
	   public String say() throws RemoteException {      
		   return message;   
	   }
	public String[] dir(String path)throws RemoteException {
		   File directory = new File(path);
			ArrayList<String> names = new ArrayList<String>(Arrays.asList(directory.list()));
		   return directory.list();
	   }
	   public String upload(byte []data,String path,int length) throws RemoteException{
		   File directory = new File(path);
		   String done="Done writing data...";
		   try {
			FileOutputStream file1 = new FileOutputStream(directory);
			byte [] serverdata=data	;
			try {
				file1.write(data);
				file1.flush();
				file1.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   System.out.println("upload is done");
		   return done;
	   }
	   public byte[] download(String path,String name) throws RemoteException{
		   String filepath=path+name;
			File downloadfile = new File(filepath);
			int length=(int) downloadfile.length();
			int buffersize=(int) downloadfile.length();
			byte [] buffer=new byte[buffersize];
			path=path+name;
			FileInputStream fis=null;
			try {
				fis = new FileInputStream(downloadfile);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				fis.read(buffer);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("download is done");
		return buffer;
		   
	   }
	   public String shutdown()throws RemoteException{
		   String close="shutdown server";
		   try {
			Naming.unbind("rmi://localhost:8000/HelloServer");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   UnicastRemoteObject.unexportObject(this, true);
		   System.out.println("Server is closed");
		   return close;
	   }
	@Override
	public void mkdir(String serverpath, String Filename) throws RemoteException {
		// TODO Auto-generated method stub
		File newfile =new File(serverpath);
		if(!newfile.exists())
		newfile.mkdir();
	}
	@Override
	public void rmdir(String serverpath, String Filename) throws RemoteException {
		// TODO Auto-generated method stub
		File newfile =new File(serverpath);
		if(newfile.exists()) {
			newfile.delete();
		}
	}
	@Override
	public void remove(String serverpath, String Filename) throws RemoteException {
		// TODO Auto-generated method stub
		File newfile =new File(serverpath);
		if(newfile.exists()) {
			newfile.delete();
		}
	}


}
