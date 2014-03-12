package ru.sg_studio;

import ru.sg_studio.gossip.ErrorDescriptor;
import ru.sg_studio.gossip.ErrorDescriptor.ErrorTypeMaster;
import ru.sg_studio.gossip.ErrorDescriptor.ErrorTypeSlave;

public abstract class RootObject {

	private ErrorDescriptor error = new ErrorDescriptor();
	
	protected void setError(ErrorTypeMaster master, ErrorTypeSlave slave){
		error.setError(master, slave);
	}
	public String getErrorAsString(){
		return error.getAsString();
	}
	public boolean isErrored(){
		return error.isErrored();
	}
	public int isErroredSTV(){
		return isErrored()?1:0;
	}	
	
}
