package ru.sg_studio.escapegame;

import java.util.ArrayList;

import net.raydeejay.escapegame.Reactor;

public class ReactorDepot {

	ArrayList<Reactor> reactors = new ArrayList<Reactor>();
	
	
	public void addReactor(Reactor reactor){
		if(reactor==null){
			System.out.println("Null reactors are not inspected in depot!");
			return;
		}
		if(reactor.getCommonBindedProxy()==null){
			System.out.println("No CBP on reactor "+reactor.getName());
		}
		reactors.add(reactor);
	}


	public void clear() {
		reactors.clear();
	}
	
	public Reactor[] getReactors(){
		Reactor[] rst = new Reactor[reactors.size()];
		rst = reactors.toArray(rst);
		return rst;
	}
	
	
}
