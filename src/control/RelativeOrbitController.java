package control;

import entities.Entity;
import org.lwjgl.util.vector.Vector3f;

public class RelativeOrbitController {

    private final Entity orbitingEntity;  // La luna u objeto que orbita
    private final Entity centralEntity;   // El planeta u objeto central

    private final float radius;
    private final float angularStep;      // paso angular en radianes por frame
    private final float height;
    private final boolean loop;

    private float angle = 0.0f;

    public RelativeOrbitController(Entity orbitingEntity, Entity centralEntity, float radius, float angularStep, float height, boolean loop) {
        this.orbitingEntity = orbitingEntity;
        this.centralEntity = centralEntity;
        this.radius = radius;
        this.angularStep = angularStep;
        this.height = height;
        this.loop = loop;
    }

    public void update() {
        angle += angularStep;
        if (angle > 2 * Math.PI) {
            angle = loop ? 0 : (float) (2 * Math.PI); // detener en el último punto si no es loop
        }

        // Obtener posición actual del cuerpo central
        Vector3f centro = centralEntity.getPosition();

        float x = (float) Math.cos(angle) * radius + centro.x;
        float z = (float) Math.sin(angle) * radius + centro.z;

        orbitingEntity.setPosition(new Vector3f(x, height, z));
    }

    public void reset() {
        angle = 0;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getAngle() {
        return angle;
    }
}
