package net.raydeejay.gossip.engine.wrapper;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.LinkedList;

/**
 * Define a Java WeakSet
 * 
 * @see <a href="package-summary.html">net.raydeejay.gossip.engine.wrapper</a>
 * @author Alexandre Bergel (<a href="mailto:alexandre@bergel.eu">alexandre@bergel.eu</a>)
 */
public class WeakSet extends Object implements Serializable {
	private static final long serialVersionUID = 1L;
    private LinkedList<WeakReference<Object>> set = new LinkedList<WeakReference<Object>>();

	public WeakSet() {}
	
    public void add(Object obj) {
		this.clean();
		this.set.add(new WeakReference(obj));
	}
	
	private void clean() {
		LinkedList<WeakReference<Object>> elementsToRemove = new LinkedList<WeakReference<Object>>();
		for(WeakReference<Object> ref: this.set)
			if(ref.get() == null) elementsToRemove.add(ref);
		for(WeakReference<Object> ref: elementsToRemove)
			this.set.remove(ref);
	}
	
	public boolean includes(Object obj) {
		for(WeakReference<Object> ref: this.set) {
			if (ref.get() != null && ref.get().equals(obj)) {
//System.out.println("WEAKSET: return true");			
				return true;
			}
		}
//System.out.println("WEAKSET: return false");			
		return false;
	}
	
	public Integer size() {
		int res = 0;
		for(WeakReference<Object> ref: this.set)
			if (ref.get() != null) res ++;

		return res;
	}
}
