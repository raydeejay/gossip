package ru.sg_studio.escapegame.bindings.libgdx;

import net.raydeejay.escapegame.Reactor;

public class LibGDXTextureUtilities {

	public static void checkLibGDXTexture(Reactor r){
		if(!r.getImage().isLoadedBySide()){
			LibGDXTextureWrapper wrapper = new LibGDXTextureWrapper(r.getClonedTexture());
			r.swapTexture(wrapper);
		}
	}
	
	
}
