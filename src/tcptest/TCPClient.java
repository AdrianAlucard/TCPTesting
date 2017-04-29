package tcptest;
import java.io.*;
import java.net.*;

/**
 * Simple client connects sends a sentence periodically and outputs the
 * response. This is an adaption of the code provided by the Computer
 * Networking: A Top Down Approach book by Kurose and Ross
 * 
 * @author Adrian and Jeff
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
      
     
      DataInputStream is = new DataInputStream(clientSocket.getInputStream());
 
      byte[] bRecv = new byte[562536351];
     // BufferedInputStream is = new BufferedInputStream(in);
      File f = new File("C:\\Users\\Owner\\Music\\Test\\test3.mp4");
      if(!f.exists())
          f.createNewFile();
      outFile = new FileOutputStream(f);
      buffOut = new BufferedOutputStream(outFile);
      
      is.readFully(bRecv);
      /*
      bytesRead = is.read(bRecv, 0, bRecv.length);
      System.out.println("Bytes Read is " + bytesRead);
      current = bytesRead;
      int total = 0;
      int available = -1;
      while((available = is.read(bRecv))>0){
         buffOut.write(bRecv, 0, available);
         buffOut.flush();
      }
*/
      buffOut.write(bRecv);
      
      is.close();
      buffOut.flush();
      //buffOut.close();
      
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
