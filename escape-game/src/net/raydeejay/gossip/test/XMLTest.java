package net.raydeejay.gossip.test;

import java.beans.Encoder;
import java.beans.Expression;
import java.beans.PersistenceDelegate;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class XMLTest {
	public static void main(String[] argv) {
		String[] ss = {"abc", "def"};
		try {
			XMLEncoder e = new XMLEncoder(
							new BufferedOutputStream(
								new FileOutputStream("/tmp/Test.xml")));
			C t = new C();
			C c = new C(t, new C(t, new C()));
			System.out.println("c.c1 == c.c2.c1 ?  " + (c.getC1() == c.getC2().getC1()));

			/////////
			// Specifying the persistence strategy
			e.setPersistenceDelegate(C.class, new PersistenceDelegate() {
				protected Expression instantiate(Object oldInstance, Encoder out) {
					return new Expression(oldInstance,
                              oldInstance.getClass(),
                              "new",
                              new Object[]{ ((C)oldInstance).getC1(), ((C)oldInstance).getC2()});
				}
			});
			/////////

			e.writeObject(c);
			e.close();
		
			//FileInputStream fis = new FileInputStream("Test.xml");
			XMLDecoder xdec = new XMLDecoder(
								new BufferedInputStream(
									new FileInputStream("/tmp/Test.xml")));
			c = (C) xdec.readObject();
			System.out.println("c.c1 == c.c2.c1 ?  " + (c.getC1() == c.getC2().getC1()));

			System.out.println(c);
		} catch(Exception e) {System.out.println(e);}
	}
}