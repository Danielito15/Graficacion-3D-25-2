package renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public class DisplayManager {
	
	//Atributos de la clase
	
	private static final int WIDTH = 1440;								//Ancho de la ventana
	private static final int HEIGHT = 1080;								//Altura de la ventana
	private static final int FPS_CAP = 120;								//Tasa de refresco
	private static final String TITLE = "Pantalla ultra chida";			//Titulo que tendra la ventana
	
	//Metodos de la clase
	
	public static void createDisplay() {										//Genera la ventana de nuestro game
		ContextAttribs attribs = new ContextAttribs(3,
		2).withForwardCompatible(true).withProfileCore(true);
		
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create(new PixelFormat(), attribs);
			Display.setTitle(TITLE);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		GL11.glViewport(0, 0, WIDTH, HEIGHT);
	}
	
	public static void updateDisplay() {										//Actualiza el display a una frecuencia designada
		Display.sync(FPS_CAP);
		Display.update();
	}
	
	public static void closeDisplay() {											//Destruye la ventana
		Display.destroy();
	}

}
