import java.net.URL;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

class Server {
	public static void main (String[] argv) {
		MethodInterface method;
		String ServerPath="C:\\Users\\LarrySu\\eclipse-workspace\\RmiTest\\src\\";
		URL location = Server.class.getProtectionDomain().getCodeSource().getLocation();
		String filepath = location.getPath();
		try {       
			LocateRegistry.createRegistry(8000);
			Naming.rebind("rmi://localhost:8000/Server",new Method("Server Path: "+filepath));
			System.out.println("Hello Server is ready.");  
			

		}catch (Exception e) {
			System.out.println("Hello Server failed: " + e);      
		}
	}
}
