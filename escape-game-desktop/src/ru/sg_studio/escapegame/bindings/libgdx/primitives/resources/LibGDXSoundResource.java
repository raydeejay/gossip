package ru.sg_studio.escapegame.bindings.libgdx.primitives.resources;

import com.badlogic.gdx.Gdx;

import ru.sg_studio.RootObject;
import ru.sg_studio.escapegame.IProxiedObject;
import ru.sg_studio.escapegame.bindings.libgdx.primitives.LibGDXProto;
import ru.sg_studio.escapegame.primitives.resources.Sound;

public class LibGDXSoundResource extends LibGDXProto implements IProxiedObject {

	protected Sound coreobject;
	
	public LibGDXSoundResource(String string) {
		coreobject = new Sound(string);
	}

	@Override
	public RootObject getBinded() {
		return coreobject;
	}

	@Override
	public void trySyncGraphicalObject() {
		//Nothing to sync
	}
	
	com.badlogic.gdx.audio.Sound resource;

	@Override
	public void sendLoadSignal() {
		tryLoad();
	}
	private void tryLoad(){
		//TODO: Will crash on error
		resource = Gdx.audio.newSound(Gdx.files.internal(coreobject.getPath()));
	}
	
}
