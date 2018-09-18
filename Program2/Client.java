import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

	public static void main (String[] args) {
		MethodInterface method = null,method1;
		String name = "rmi://localhost:8000/Server",ServerPath=null; 
		String words[],clientpath=null,filename = null;
		String PA1_Server = System.getenv("PA1_Server");
		String[] Server_Path = PA1_Server.split(":");
		String options;
		options = args[0];
		String wrong="Wrong type with incorrect inputs";
		try {   
			Registry myreg = LocateRegistry.getRegistry("127.0.0.1",8000);
			method = (MethodInterface)Naming.lookup(name);
			words=method.say().split(": ");
			ServerPath=words[1];
		}catch (Exception e) {
			System.out.println("methodClient exception: " + e);
		}

		if (args.length>=1) {
			switch(options) {
				case "upload": if(args.length==3) {
							   		try {
							   			clientpath=args[1];
							   			filename=args[2];
										upload(method,clientpath,filename,ServerPath);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
							   }		
								else {
									System.out.println(wrong);
								}
							   break;
				case "download":if(args.length==3) { 
									clientpath=args[1];
									filename=args[2];
									download(method,filename,ServerPath,clientpath);
				   				}		
								else {
									System.out.println(wrong);
								}
				   				break;	
				case "mkdir": if(args.length==2) { 
									ServerPath=args[1];
									//filename=args[2];
									mkdir(method,ServerPath, name);
							  }		
								else {
									System.out.println(wrong);
								}
				   				break;
				case "rmdir": if(args.length==2) { 
									ServerPath=args[1];
									//filename=args[2];
									rmdir(method,ServerPath, name);
								}		
								else {
									System.out.println(wrong);
								}
				   				break;
				case "remove": if(args.length==2) { 
									ServerPath=args[1];
									//filename=args[2];
									remove(method,ServerPath, name);
								}		
								else {
									System.out.println(wrong);
								}
   								break;
				case "shutdown": if(args.length==1) { 
									shutdown(method);
								}		
								else {
									System.out.println(wrong);
								}
   								break;
				case "dir": if(args.length==2) { 
								System.out.println("Server File:");
								dir(name,ServerPath);
							}		
							else {
								System.out.println(wrong);
							}
							break;
			}
		}
		   
	}

	private static void dir(String name, String path) {
		// TODO Auto-generated method stub
		MethodInterface method1;
		String[] filename = null;
		try {
			method1 = (MethodInterface)Naming.lookup(name);
			filename = method1.dir(path);
		} catch (MalformedURLException | RemoteException | NotBoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for(String  i:filename) {
		System.out.println(i);
		}
	}

	private static void mkdir(MethodInterface method, String serverpath, String name) {
		// TODO Auto-generated method stub
		try {
			method.mkdir(serverpath,name);
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void rmdir(MethodInterface method, String serverpath, String name) {
		// TODO Auto-generated method stub
		try {
			method.rmdir(serverpath,name);
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void remove(MethodInterface method, String serverpath, String name) {
		// TODO Auto-generated method stub
		try {
			method.remove(serverpath,name);
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void shutdown(MethodInterface method) {
		// TODO Auto-generated method stub
		try {
			method.shutdown();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Server is closed");
	}

	private static void download(MethodInterface method, String filename,String ServerPath,String clientpath) {
		// TODO Auto-generated method stub
		byte[] done = null;
		try {
			done = method.download(ServerPath, filename);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		File directory = new File(clientpath+filename);
		   String finish="finished download...";
		   try {
			FileOutputStream file1 = new FileOutputStream(directory);
			byte [] serverdata=done;
			try {
				file1.write(serverdata);
				file1.flush();
				file1.close();
				System.out.println(finish);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("file not found");
			e.printStackTrace();
		}
	}

	private static void upload(MethodInterface method, String clientpath, String filename,String path) throws IOException {
		// TODO Auto-generated method stub
		String filepath=clientpath+filename;
		File uploadfile = new File(filepath);
		int length=(int) uploadfile.length();
		int buffersize=(int) uploadfile.length();
		byte [] buffer=new byte[buffersize];
		path=path+filename;
		try {
			FileInputStream fis = new FileInputStream(uploadfile);
				fis.read(buffer,0,length);
				String done=method.upload(buffer, path, length);
				System.out.println(done);
			
			//**System.out.println(line); 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//**buffer

	}
}
