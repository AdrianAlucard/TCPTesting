package tcptest;
import java.io.*;
import java.net.*;

/**
 * Simple client connects sends a sentence periodically and outputs the
 * response. This is an adaption of the code provided by the Computer
 * Networking: A Top Down Approach book by Kurose and Ross
 * 
 * @author Chad Williams
 */
public class TCPClient extends Thread {

  private int serverPort;
  int bytesRead;
  int current = 0;
  FileOutputStream outFile = null;
  BufferedOutputStream buffOut = null;
  public TCPClient(String name, int serverPort) {
    super(name);
    this.serverPort = serverPort;
  }

  /**
   * Start the thread to connect and begin sending
   */
  @Override
  public void run() {
    Socket clientSocket = null;
    try {
      
      
      System.out.println("CLIENT opening socket");
      //INSERT IP HERE
      clientSocket = new Socket("192.168.1.96", serverPort);
      System.out.println("CLIENT connected to server");
      
      //get file
      byte[] bRecv = new byte[9447213];
      InputStream is = clientSocket.getInputStream();
      File f = new File("C:\\Users\\Owner\\Music\\Test\\test.m4a");
      if(!f.exists())
          f.createNewFile();
      outFile = new FileOutputStream(f);
      buffOut = new BufferedOutputStream(outFile);
      bytesRead = is.read(bRecv, 0, bRecv.length);
      current = bytesRead;
      int available = -1;
      while((available = is.read(bRecv))>0){
          buffOut.write(bRecv, 0, available);
      }
      /*
      do{
        bytesRead = is.read(bRecv, current, bRecv.length-current);
        if(bytesRead>=0)
            current+=bytesRead;
        
      }while(bytesRead > -1);
*/
      //buffOut.write(bRecv, 0, current);
      //DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
     
     // buffOut.write(bRecv, 0, current);
      buffOut.flush();
      buffOut.close();
      System.out.println("Closing Connection");
      clientSocket.close();
      System.out.println(this.getName() + " closed connection to server");
    } catch (Exception e) {
      e.printStackTrace();
      try {
        if (clientSocket != null) {
          clientSocket.close();
        }
      } catch (Exception cse) {
        // ignore exception here
      }
    }
  }
}
