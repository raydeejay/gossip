package ru.sg_studio.escapegame;

import java.util.LinkedList;
import java.util.Queue;

public class MessagingQueue {

	Queue<Runnable> queue = new LinkedList<Runnable>();
	
	public void push(Runnable r){
		queue.add(r);
	}
	
	public Runnable pop(){
		return queue.poll();
	}
	
	
	
}
