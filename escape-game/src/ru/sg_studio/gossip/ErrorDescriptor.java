package ru.sg_studio.gossip;

public class ErrorDescriptor {

	private ErrorTypeMaster master;
	private ErrorTypeSlave slave;
	
	private boolean isErrored=false;
	
	public boolean isErrored(){
		return isErrored;
	}
	
	public void setError(ErrorTypeMaster master, ErrorTypeSlave slave){
		this.master=master;
		this.slave=slave;
		this.isErrored=true;
	}
	public String getAsString(){
		return "Error: "+master.toString()+", with reason: "+slave.toString();
	}
	
	public enum ErrorTypeMaster{
		FontNotAvailable
	}
	public enum ErrorTypeSlave{
		FileNotFound, //All files are non existant
		FilesArePartiallyAvailable //Fileset is missing some files
	}	
	
}
