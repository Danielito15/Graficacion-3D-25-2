package control;

public class Circunferencia2D {
	
	/**
     * Genera un arreglo de 2xN: primera fila con x, segunda fila con y.
     * @param radio      Radio de la circunferencia.
     * @param pasoAngulo Paso angular en radianes.
     * @return           Array[2][N] con coordenadas (x[], y[]).
     */
    public static float[][] generarCircunferencia(float radio, float pasoAngulo) {
        int numPuntos = (int) Math.ceil((2 * Math.PI) / pasoAngulo);
        float[][] coordenadas = new float[2][numPuntos];

        int i = 0;
        for (float angulo = 0.0f; angulo < 2 * Math.PI && i < numPuntos; angulo += pasoAngulo) {
            coordenadas[0][i] = (float) Math.cos(angulo) * radio; // x
            coordenadas[1][i] = (float) Math.sin(angulo) * radio; // y
            i++;
        }

        return coordenadas;
    }

}
