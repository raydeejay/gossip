package ru.sg_studio.escapegame.bindings.libgdx.helpers;

import java.util.Hashtable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class LibGDXFontDictionary {

	private static LibGDXFontDictionary instance;
	
	private Hashtable<String,BitmapFont> fonts = new Hashtable<String,BitmapFont>();
	
	public static BitmapFont pop(String fontpath){
		LibGDXFontDictionary me = getInstance();
		if(me.fonts.containsKey(fontpath)){
			return me.fonts.get(fontpath);
		}else{
			BitmapFont font = me.fontload(fontpath);
			me.fonts.put(fontpath, font);
			return font;
		}
	}
	
	
	private static LibGDXFontDictionary getInstance() {
		if(instance==null){
			instance = new LibGDXFontDictionary();
		}
		return instance;
	}


	private BitmapFont fontload(String fontpath) {
		//TODO: Crash protection
		BitmapFont font = new BitmapFont(Gdx.files.internal(fontpath+".fnt"),
		         Gdx.files.internal(fontpath+".png"), false);
		return font;
	}	
	
}
