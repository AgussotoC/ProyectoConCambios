public class Nodo {
    Object dato;
    Nodo derecho;
    Nodo izquierdo;
    Nodo arriba;
    Nodo abajo;
    int cuarto;
    /*char[][] matriz;
    UID uid = new UID();*/
    public Nodo(int cuarto , Object dato ) {
        this.cuarto = cuarto;
        this.derecho = this.izquierdo = this.arriba = this.abajo = null;
        this.dato = dato;

    }
}
