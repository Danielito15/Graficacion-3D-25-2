package control;

import entities.Entity;
import org.lwjgl.util.vector.Vector3f;

public class OrbitController {

    private final Entity entity;
    private final float[][] orbitCoordinates; // [2][N]: x, z
    private int currentIndex = 0;

    private final float height; // constante para y, opcional
    private final boolean loop; // si debe reiniciar al final

    public OrbitController(Entity entity, float[][] orbitCoordinates, float height, boolean loop) {
        this.entity = entity;
        this.orbitCoordinates = orbitCoordinates;
        this.height = height;
        this.loop = loop;
    }

    public void update() {
        if (currentIndex >= orbitCoordinates[0].length) {
            if (loop) {
                currentIndex = 0;
            } else {
                return; // no hacer nada si no debe reiniciar
            }
        }

        float x = orbitCoordinates[0][currentIndex];
        float z = orbitCoordinates[1][currentIndex];

        entity.setPosition(new Vector3f(x, height, z));

        currentIndex++;
    }

    public void reset() {
        currentIndex = 0;
    }

    public boolean isFinished() {
        return currentIndex >= orbitCoordinates[0].length;
    }

    public void setIndex(int index) {
        this.currentIndex = index % orbitCoordinates[0].length;
    }

    public int getIndex() {
        return currentIndex;
    }
}
