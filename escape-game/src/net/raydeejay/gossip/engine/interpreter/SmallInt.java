package net.raydeejay.gossip.engine.interpreter;

/**
 * Athena interpreter. 
 * <p>
 * Originally based on Tim Budd's work with Little Smalltalk. 
 * Description of <code>net.raydeejay.gossip.engine.interpreter</code> summarizes the differences.
 * 
 * @see <a href="package-summary.html">net.raydeejay.gossip.engine.interpreter</a>
 * @author Timothy A. Budd (<a href="http://web.engr.oregonstate.edu/~budd">web.engr.oregonstate.edu/~budd</a>)
 * @author Alexandre Bergel (<a href="mailto:alexandre@bergel.eu">alexandre@bergel.eu</a>)
 */
public class SmallInt extends SmallObject {
	private static final long serialVersionUID = 7456907425828960821L; 
	public int value;
	
    public SmallInt() {super();}
    
	public SmallInt (SmallObject integerClass, int v) 
	{ super (integerClass, 0); value = v; }
	
	public int toJavaInteger() {return value;}
	
	public String toString () { return "SmallInt: "+ value; }
}
