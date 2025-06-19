package shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import toolbox.Maths;

public class SunShader extends ShaderProgram {

    private static final String VERTEX_FILE = "src/shaders/sunVertexShader.txt";
    private static final String FRAGMENT_FILE = "src/shaders/sunFragmentShader.txt";

    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int location_emissionColor;


    public SunShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
    }

    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix = super.getUniformLocations("transformationMatrix");
        location_projectionMatrix = super.getUniformLocations("projectionMatrix");
        location_viewMatrix = super.getUniformLocations("viewMatrix");
        location_emissionColor = super.getUniformLocations("emissionColor");
    }

    public void loadTransformationMatrix(Matrix4f matrix) {
        super.loadMatrix(location_transformationMatrix, matrix);
    }

    public void loadProjectionMatrix(Matrix4f matrix) {
        super.loadMatrix(location_projectionMatrix, matrix);
    }

    public void loadViewMatrix(Camera camera) {
        Matrix4f viewMatrix = Maths.createViewMatrix(camera);
        super.loadMatrix(location_viewMatrix, viewMatrix);
    }
    
    public void loadEmissionColor(Vector3f color) {
        super.loadVector(location_emissionColor, color);
    }

}

