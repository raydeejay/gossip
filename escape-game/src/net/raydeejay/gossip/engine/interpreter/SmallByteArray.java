package net.raydeejay.gossip.engine.interpreter;

/**
 * Athena interpreter. 
 * <p>
 * Originally based on Tim Budd's work with Little Smalltalk. 
 * Description of <code>net.raydeejay.gossip.engine.interpreter</code> summarizes the differences.
 * 
 * @see <a href="package-summary.html">net.raydeejay.gossip.engine.interpreter</a>
 * @author Timothy A. Budd (<a href="http://web.engr.oregonstate.edu/~budd">web.engr.oregonstate.edu/~budd</a>)
 */
public class SmallByteArray extends SmallObject {
	private static final long serialVersionUID = 138005696073071606L; 

	public byte [ ] values;
	
    public SmallByteArray() {super();}
    
    public SmallByteArray (SmallObject cl, int size) 
	{ super(cl, 0); values = new byte[size]; }
	
	public SmallByteArray (SmallObject cl, String text) {
		super(cl, 0);
		int size = text.length();
		values = new byte[size];
		for (int i = 0; i < size; i++)
			values[i] = (byte) text.charAt(i);
	}
	
	public String toString () {
		// we assume its a string, tho not always true...
		return new String(values); 
	}
	
	public SmallObject copy (SmallObject cl) {
		SmallByteArray newObj = new SmallByteArray(cl, values.length);
		for (int i = 0; i < values.length; i++) {
			newObj.values[i] = values[i];
		}
		return newObj;
	} 
}
