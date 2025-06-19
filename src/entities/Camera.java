package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	
	private Vector3f position = new Vector3f(0, 0 , 3f);
	private float pitch;
	private float yaw;
	private float roll;
	
	public void move() {
		// Movimiento básico
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			position.z -= 0.008f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			position.x -= 0.008f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			position.x += 0.008f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			position.z += 0.008f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			position.y += 0.008f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			position.y -= 0.008f;
		}

		// Reubicar y orientar la cámara al presionar U
		if (Keyboard.isKeyDown(Keyboard.KEY_U)) {
			this.position.set(0, 3.2f , 0);
			this.pitch = 90f; // mira hacia abajo por eje Y
			this.yaw = 0f;
			this.roll = 0f;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_J)) {
			this.position.set(0, 0 , 3f);
			this.pitch = 0f; 
			this.yaw = 0f;
			this.roll = 0f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_L)) {
			this.position.set(1.2f, 1.85f , 2.3f);
			this.pitch = 40f; 
			this.yaw = -20f;
			this.roll = 0f;
		}
	}
	
	public Vector3f getPosition() {
		return position;
	}
	
	public float getPithc() {
		return pitch;
	}
	
	public float getYaw() {
		return yaw;
	}
	
	public float getRoll() {
		return roll;
	}
}
