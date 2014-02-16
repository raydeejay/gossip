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
public class SmallJavaObject extends SmallObject {
	private static final long serialVersionUID = 7526472295622776141L; 
	public SmallJavaObject(SmallObject cls, Object v) 
	{ super(cls, 0); value = v; }
	
	public Object value;
}
