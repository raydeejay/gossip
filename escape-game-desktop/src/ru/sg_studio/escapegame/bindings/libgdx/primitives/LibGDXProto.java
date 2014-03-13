package ru.sg_studio.escapegame.bindings.libgdx.primitives;

import ru.sg_studio.escapegame.IProxiedObject;

import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class LibGDXProto extends Actor implements IProxiedObject {

	protected LibGDXProto(){
		trySyncGraphicalObject();
	}

}
