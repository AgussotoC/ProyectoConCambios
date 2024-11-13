public class Nodo {
    int cuarto;
    Nodo derecha;
    Nodo izquierda;
    Nodo arriba;
    Nodo abajo;
    char[][] matriz;
    UID uid = new UID();
    public Nodo(int cuarto, char[][] matriz) {
        this.cuarto = cuarto;
        this.derecha = this.izquierda = this.arriba = this.abajo = null;
        this.matriz = uid.generarAreaMatriz();
    }

}
