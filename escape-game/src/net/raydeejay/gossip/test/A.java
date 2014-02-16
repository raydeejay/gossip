package net.raydeejay.gossip.test;

import java.io.*;
import java.net.*;

public class A {
    public static void main(String[] argv) {
	try {
	    ////////////
	    // Receiving
	    ServerSocket s = new ServerSocket(1978);
	    Socket client = s.accept();
	    BufferedReader r =
			new BufferedReader(new InputStreamReader(client.getInputStream()));
	    System.out.println(r.readLine());


	    ////////////
	    // Emitting
	    Socket socket = null;
	    int ret = 0;
	    while ((socket == null) && (ret<100)){
		try {
		    socket = new Socket("localhost", 1979);
		}
		catch(Exception e) {
		    Thread.currentThread().sleep(45);
		    ret++;
		}
	    }

	    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
 				  
	    out.println("echo!");

	    client.close();
	    s.close();
	}
	catch(Exception e){System.out.println(e);}
    }
}
