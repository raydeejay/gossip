package ru.sg_studio.escapegame;

import java.util.ArrayList;

import ru.sg_studio.escapegame.primitives.GraphicalEntity;
import net.raydeejay.escapegame.Reactor;

public class RenderableDepot {

	ArrayList<GraphicalEntity> graphicals = new ArrayList<GraphicalEntity>();
	
	
	public void addGraphical(GraphicalEntity graphical){
		if(graphical==null){
			System.out.println("Null reactors are not inspected in depot!");
			return;
		}
		if(graphical.getCommonBindedProxy()==null){
			System.out.println("No CBP on reactor "+graphical.getName());
		}
		graphicals.add(graphical);
	}


	public void clear() {
		graphicals.clear();
	}
	
	public GraphicalEntity[] getGraphical(){
		GraphicalEntity[] gest = new GraphicalEntity[graphicals.size()];
		gest = graphicals.toArray(gest);
		return gest;
	}


	
	private int iterator=-1;
	public GraphicalEntity check() {
		if(graphicals==null || graphicals.size()==0){return null;}
		iterator++;
		if(iterator>=graphicals.size()){iterator=0;}
		if(iterator<0){iterator=0;}
		GraphicalEntity graphical=graphicals.get(iterator);
		if(graphical.isMarkedForEviction()){
			graphicals.remove(iterator);
			iterator--;
			System.out.println("Zombie reactor "+graphical.getName()+" removed!");
			return graphical;
			}else{
				return null;
				}
	}
	
	
}
