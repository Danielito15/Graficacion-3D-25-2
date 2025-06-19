package renderEngine;

import java.util.ArrayList;
import java.util.List;

public class PolygonGenerator {

    public static void createPolygon(int sides, float[] vertices, int[] indices) {
        if (sides < 3) throw new IllegalArgumentException("El polígono debe tener al menos 3 lados");

        float angleStep = (float) (2 * Math.PI / sides);

        // Definir el centro del polígono
        vertices[0] = 0.0f;  // X (centro)
        vertices[1] = 0.0f;  // Y (centro)
        vertices[2] = 0.0f;  // Z (plano XY)

        // Generar los vértices en el borde del polígono
        for (int i = 0; i < sides; i++) {
            float angle = i * angleStep;
            vertices[(i + 1) * 3] = (float) Math.cos(angle) * 0.5f;  // X
            vertices[(i + 1) * 3 + 1] = (float) Math.sin(angle) * 0.5f;  // Y
            vertices[(i + 1) * 3 + 2] = 0.0f;  // Z
        }

        // Generar los índices para formar triángulos desde el centro
        for (int i = 0; i < sides; i++) {
            indices[i * 3] = 0;          // Centro
            indices[i * 3 + 1] = i + 1;  // Punto actual
            indices[i * 3 + 2] = (i + 1) % sides + 1; // Punto siguiente (conexión circular)
        }
    }
    
    public static void createPrism(int sides, float depth, float[] vertices, int[] indices) {
        if (sides < 3) throw new IllegalArgumentException("El polígono debe tener al menos 3 lados");

        float angleStep = (float) (2 * Math.PI / sides);
        int vertexCount = 0;

        // Generar vértices de la base inferior (Z negativa)
        for (int i = 0; i < sides; i++) {
            float angle = i * angleStep;
            vertices[vertexCount * 3] = (float) Math.cos(angle) * 0.5f;  // X
            vertices[vertexCount * 3 + 1] = (float) Math.sin(angle) * 0.5f;  // Y
            vertices[vertexCount * 3 + 2] = -depth / 2;  // Z (base inferior)
            vertexCount++;
        }

        // Generar vértices de la base superior (Z positiva) **desplazada en X**
        for (int i = 0; i < sides; i++) {
            float angle = i * angleStep;
            vertices[vertexCount * 3] = (float) Math.cos(angle) * 0.5f + 0.25f;  // X desplazado
            vertices[vertexCount * 3 + 1] = (float) Math.sin(angle) * 0.5f;  // Y
            vertices[vertexCount * 3 + 2] = depth / 2;  // Z (base superior)
            vertexCount++;
        }

        int indexCount = 0;

        // Generar triángulos de la base inferior
        for (int i = 1; i < sides - 1; i++) {
            indices[indexCount++] = 0;
            indices[indexCount++] = i;
            indices[indexCount++] = i + 1;
        }

        // Generar triángulos de la base superior
        int topOffset = sides;
        for (int i = 1; i < sides - 1; i++) {
            indices[indexCount++] = topOffset;
            indices[indexCount++] = topOffset + i;
            indices[indexCount++] = topOffset + i + 1;
        }

        // Generar caras laterales (conectando base inferior y superior)
        for (int i = 0; i < sides; i++) {
            int next = (i + 1) % sides;
            indices[indexCount++] = i;
            indices[indexCount++] = next;
            indices[indexCount++] = topOffset + i;

            indices[indexCount++] = topOffset + i;
            indices[indexCount++] = next;
            indices[indexCount++] = topOffset + next;
        }
    }
    
    public static SphereData createSphere(float radius, int slices, int stacks) {
        List<Float> vertexList = new ArrayList<>();
        List<Float> textureCoordList = new ArrayList<>();
        List<Float> normalList = new ArrayList<>();
        List<Integer> indexList = new ArrayList<>();

        for (int i = 0; i <= stacks; i++) {
            float phi = (float) (Math.PI * i / stacks);  // Ángulo de latitud
            for (int j = 0; j <= slices; j++) {
                float theta = (float) (2.0 * Math.PI * j / slices);  // Ángulo de longitud

                // Calculando las coordenadas 3D (x, y, z)
                float x = (float) (radius * Math.sin(phi) * Math.cos(theta));
                float y = (float) (radius * Math.cos(phi));
                float z = (float) (radius * Math.sin(phi) * Math.sin(theta));

                // Agregar los vértices
                vertexList.add(x);
                vertexList.add(y);
                vertexList.add(z);

                // Calculando las coordenadas de textura (u, v)
                float u = (float) j / slices;  // Coordenada U
                float v = (float) i / stacks;  // Coordenada V
                
                // Agregar las coordenadas de textura
                textureCoordList.add(u);
                textureCoordList.add(v);
                
                // Normal del vértice (normalizada)
                float length = (float) Math.sqrt(x * x + y * y + z * z);
                normalList.add(x / length);
                normalList.add(y / length);
                normalList.add(z / length);
            }
        }

        // Generar los índices para los triángulos
        for (int i = 0; i < stacks; i++) {
            for (int j = 0; j < slices; j++) {
                int first = (i * (slices + 1)) + j;
                int second = first + slices + 1;

                indexList.add(first);
                indexList.add(second);
                indexList.add(first + 1);

                indexList.add(second);
                indexList.add(second + 1);
                indexList.add(first + 1);
            }
        }

        // Convertir las listas en arrays
        float[] vertices = new float[vertexList.size()];
        float[] textureCoords = new float[textureCoordList.size()];
        float[] normals = new float[normalList.size()];
        int[] indices = new int[indexList.size()];

        for (int i = 0; i < vertexList.size(); i++) {
            vertices[i] = vertexList.get(i);
        }
        for (int i = 0; i < textureCoordList.size(); i++) {
            textureCoords[i] = textureCoordList.get(i);
        }
        for (int i = 0; i < normalList.size(); i++) {
            normals[i] = normalList.get(i);
        }
        for (int i = 0; i < indexList.size(); i++) {
            indices[i] = indexList.get(i);
        }

        return new SphereData(vertices, textureCoords, normals, indices);
    }


    public static class SphereData {
        public float[] vertices;
        public float[] textureCoords;
        public float[] normals;
        public int[] indices;

        public SphereData(float[] vertices, float[] textureCoords, float[] normals, int[] indices) {
            this.vertices = vertices;
            this.textureCoords = textureCoords;
            this.normals = normals;
            this.indices = indices;
        }
    }
    
    public static void createTexturedCube(float size, float[] vertices, float[] texCoords, int[] indices) {
        float half = size / 2f;

        // 24 vértices (4 por cara × 6 caras) para que cada cara tenga su propio mapeo UV
        float[] v = {
            // Back face
            -half, -half, -half,
             half, -half, -half,
             half,  half, -half,
            -half,  half, -half,

            // Front face
            -half, -half,  half,
             half, -half,  half,
             half,  half,  half,
            -half,  half,  half,

            // Left face
            -half, -half, -half,
            -half,  half, -half,
            -half,  half,  half,
            -half, -half,  half,

            // Right face
             half, -half, -half,
             half,  half, -half,
             half,  half,  half,
             half, -half,  half,

            // Top face
            -half,  half, -half,
             half,  half, -half,
             half,  half,  half,
            -half,  half,  half,

            // Bottom face
            -half, -half, -half,
             half, -half, -half,
             half, -half,  half,
            -half, -half,  half
        };

        float[] uv = {
            // Cada cara mapeada con (0,0) a (1,1)
            0, 0, 1, 0, 1, 1, 0, 1,   // Back
            0, 0, 1, 0, 1, 1, 0, 1,   // Front
            0, 0, 1, 0, 1, 1, 0, 1,   // Left
            0, 0, 1, 0, 1, 1, 0, 1,   // Right
            0, 0, 1, 0, 1, 1, 0, 1,   // Top
            0, 0, 1, 0, 1, 1, 0, 1    // Bottom
        };

        int[] idx = {
             0,  1,  2,  2,  3,  0,  // Back
             4,  5,  6,  6,  7,  4,  // Front
             8,  9, 10, 10, 11,  8,  // Left
            12, 13, 14, 14, 15, 12,  // Right
            16, 17, 18, 18, 19, 16,  // Top
            20, 21, 22, 22, 23, 20   // Bottom
        };

        System.arraycopy(v, 0, vertices, 0, v.length);
        System.arraycopy(uv, 0, texCoords, 0, uv.length);
        System.arraycopy(idx, 0, indices, 0, idx.length);
    }
}
