package net.raydeejay.gossip.engine.interpreter;

import java.io.Serializable;

/**
 * Athena interpreter. 
 * <p>
 * Originally based on Tim Budd's work with Little Smalltalk. 
 * Description of <code>net.raydeejay.gossip.engine.interpreter</code> summarizes the differences.
 * 
 * @see <a href="package-summary.html">net.raydeejay.gossip.engine.interpreter</a>
 * @author Timothy A. Budd (<a href="http://web.engr.oregonstate.edu/~budd">web.engr.oregonstate.edu/~budd</a>)
 */
public class SmallObject implements Serializable {
	private static final long serialVersionUID = 7043560671102645481L; 

	public SmallObject objClass;
	public SmallObject [ ] data;

    public int id; // used to speed up image save, usually null
    public SmallObject () { objClass = null; data = null; }
	
	public SmallObject (SmallObject cl, int size) 
	{ objClass = cl; data = new SmallObject [size]; }
	
	public SmallObject copy(SmallObject cl) { return this; }
	
    public SmallObject duplicate() {
        SmallObject d = new SmallObject();
        d.objClass = objClass;
        d.data = data;
        return d;
    }
    
	/**
	 * Convert a Smalltalk object into a SmallJavaObject.
	 *
	 * @param cls the Smalltalk class SmallJavaObject	
	 */
	public SmallJavaObject toJava(SmallObject cls) { return new SmallJavaObject(cls, this); }
}
