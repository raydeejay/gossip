package net.raydeejay.gossip.test;

import java.io.*;
import java.net.*;

public class B {
    public static void main(String[] argv) {
	try {
	    ////////////
	    // Emitting
	    Socket client = new Socket("localhost", 1978);
	    PrintWriter out = new PrintWriter(client.getOutputStream(), true);
				  
	    out.println("Hello!");

	    ////////////
	    // Receiving
	    ServerSocket s = new ServerSocket(1979);
	    Socket sock = s.accept();
	    BufferedReader r = 
		new BufferedReader(new InputStreamReader(sock.getInputStream()));
	    System.out.println("Reply: "+r.readLine());

	    client.close();
	}
	catch(Exception e){System.out.println(e);}
    }
}
