package shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Light;
import toolbox.Maths;

public class StaticShader extends ShaderProgram{ 
	
	private static final String VERTEX_FILE = "src/shaders/vertexShader.txt";
	private static final String FRAGMENT_FILE = "src/shaders/fragmentShader.txt";
	
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_lightPosition;
	private int location_lightColor;
	private int location_shienDamper;
	private int location_reflectivity;
	private int location_cameraPosition;
	
	public StaticShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");				//Leo la lista de atributos posicion cero y la ingreos en vartex shader como "posicion"
		super.bindAttribute(1, "textureCoords");
		super.bindAttribute(2, "normal");
	}
	
	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocations("transformationMatrix");
		location_projectionMatrix = super.getUniformLocations("projectionMatrix");
		location_viewMatrix = super.getUniformLocations("viewMatrix");
		location_lightPosition = super.getUniformLocations("lightPosition");
		location_lightColor =	super.getUniformLocations("lightColor");
		location_shienDamper = super.getUniformLocations("shienDamper");
		location_reflectivity = super.getUniformLocations("reflectivity");
		location_cameraPosition = super.getUniformLocations("cameraPosition");
	}
	
	public void loadShineVariables(float damper, float reflectivity) {
		super.loadFloat(location_shienDamper, damper);
		super.loadFloat(location_reflectivity, reflectivity);
	}
	
	public void loadTranformationMatrix(Matrix4f matrix) {
		super.loadMatrix(location_transformationMatrix, matrix);
	}
	
	public void loadLight(Light light){
		super.loadVector(location_lightPosition, light.getPosition());
		super.loadVector(location_lightColor, light.getColour());
	}
	
	public void loadProjectionMatrix(Matrix4f projection){
		super.loadMatrix(location_projectionMatrix, projection);
	}
	
	public void loadViewMatrix(Camera camera) {
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		super.loadMatrix(location_viewMatrix, viewMatrix);
	}
	
	public void loadCameraPosition(Vector3f cameraPos){
	    super.loadVector(location_cameraPosition, cameraPos);
	}
}
