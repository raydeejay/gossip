package ru.sg_studio.escapegame;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import net.raydeejay.escapegame.Reactor;

public class SmallInterpreterInterfacer {

	public static InputStream ReadInjarFile(String internalName) {
		internalName="/"+internalName;
		System.out.println("Marshalling file access for "+internalName+" file");
		
		InputStream stream = SmallInterpreterInterfacer.class.getResourceAsStream(internalName);
		
		return stream;
	}

	public static void RefreshTextureMetadataFor(Reactor reactor) {
		//if(true){return;}
		ImageInputStream in = null;
		try {
			System.out.println("Presetting Reactor for texture: "+reactor.getImage().getFilename());
		in = ImageIO.createImageInputStream(reactor.getImage().getFilename());
		if(in==null){
			in = ImageIO.createImageInputStream(SmallInterpreterInterfacer.class.getResourceAsStream("/"+reactor.getImage().getFilename()));
		}
		

			final Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("png");
		   // final Iterator<ImageReader> readers = ImageIO.getImageReaders(in);
		    if (readers.hasNext()) {
		        ImageReader reader = readers.next();
		        try {
		            reader.setInput(in);
		            reactor.setWidth(reader.getWidth(0));
		            reactor.setHeight(reader.getHeight(0));
		           //return ;
		        } finally {
		            reader.dispose();
		        }
		    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		    if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
	}

}
