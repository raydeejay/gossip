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
public class Sema {
	//private static final long serialVersionUID = 7526472295622776145L; 
	public synchronized SmallObject get() { 
		if (! hasBeenSet) 
			try {
				wait(); 
			} catch(Exception e) { 
				System.out.println("Sema got exception " + e); 
			}
		return value; 
	}

	public synchronized void set(SmallObject v) { 
		value = v; hasBeenSet = true; notifyAll(); 
	}

	private SmallObject value;
	private boolean hasBeenSet = false;
}
