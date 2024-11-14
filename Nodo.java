public class Nodo {
    UID uid;
    Nodo derecho;
    Nodo izquierdo;
    Nodo arriba;
    Nodo abajo;
    int numCuarto;
    public Nodo(UID uid, int numCuarto) {
        this.derecho = this.izquierdo = this.arriba = this.abajo = null;
        this.uid = uid;
        this.numCuarto = numCuarto;

    }
    public int getNumeroCuarto(){
        return numCuarto;
    }
}
