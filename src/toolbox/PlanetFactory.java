package toolbox;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import models.TexturedModel;
import renderEngine.Loader;
import renderEngine.PolygonGenerator;
import renderEngine.RawModel;
import textures.ModelTexture;

public class PlanetFactory {
	
	public static final Map<String, Float> RADIOS = new HashMap<>();
	static {
	    RADIOS.put("star",    100f);
	    RADIOS.put("sol",     0.5f);
	    RADIOS.put("mercurio", 0.0175f * 2.5f);
	    RADIOS.put("venus",    0.0453f * 2.5f);
	    RADIOS.put("tierra",   0.0458f * 2.5f);
	    RADIOS.put("marte",    0.0243f * 2.5f);
	    RADIOS.put("luna",     0.0150f * 2f);
	    RADIOS.put("fobos",    0.0100f * 2.5f);
	    RADIOS.put("deimos",   0.0062f * 2.5f);
	}


    public static TexturedModel createPlanetModel(Loader loader, RawModel raw, String textureName, float shine, float reflectivity) {
        TexturedModel tm = new TexturedModel(raw, new ModelTexture(loader.loadTexture(textureName)));
        tm.getTexture().setShineDamper(shine);
        tm.getTexture().setReflectivity(reflectivity);
        return tm;
    }

    public static Entity createEntity(TexturedModel model, float x, float y, float z, float scale) {
        return new Entity(model, new Vector3f(x, y, z), 0, 0, 0, scale);
    }
    
    // Método para crear la esfera con normales incluidas
    public static RawModel updateModel(Loader loader, int slices, int stacks) {
        loader.cleanUp();
        RawModel model;

        float radius = 0.3f;

        // Crear esfera con vértices, texturas, normales e índices
        PolygonGenerator.SphereData sphere = PolygonGenerator.createSphere(radius, slices, stacks);

        model = loader.loadToVAO(
            sphere.vertices,
            sphere.textureCoords,
            sphere.normals,
            sphere.indices
        );

        return model;
    }
    
    // Método para crear un cubo con coordenadas de textura y sin normales
    public static RawModel createTexturedCubeModel(Loader loader, float size) {
        float[] vertices = new float[24 * 3];
        float[] texCoords = new float[24 * 2];
        int[] indices = new int[36];

        // Llama a la función que genera los datos del cubo
        PolygonGenerator.createTexturedCube(size, vertices, texCoords, indices);

        // Cargar el cubo al VAO (sin normales)
        return loader.loadToVAOCube(vertices, texCoords, indices);
    }
}
