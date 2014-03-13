package net.raydeejay.escapegame;

import ru.sg_studio.escapegame.ContextedFactory;
import ru.sg_studio.escapegame.bindings.libgdx.LibGDXGameContext;
import ru.sg_studio.escapegame.bindings.libgdx.LibGDXObjectProvider;
import ru.sg_studio.escapegame.bindings.libgdx.LibGDXWindow;

public class Main {
	public static void main(String[] args) {

		ContextedFactory.instance().setContextedObjectProvider(new LibGDXObjectProvider());
		
		LibGDXWindow window = new LibGDXWindow(800, 480,"escape-game");
		LibGDXGameContext context = new LibGDXGameContext(window);
		window.configure(false,context);
		window.run();
		
		
	}
}
