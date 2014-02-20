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


	
	private int iterator=-1;
	public Reactor check() {
		if(reactors==null || reactors.size()==0){return null;}
		iterator++;
		if(iterator>=reactors.size()){iterator=0;}
		if(iterator<0){iterator=0;}
		Reactor reactor=reactors.get(iterator);
		if(reactor.isMarkedForEviction()){
			reactors.remove(iterator);
			iterator--;
			System.out.println("Zombie reactor "+reactor.getName()+" removed!");
			return reactor;
			}else{
				return null;
				}
	}
	
	
}
