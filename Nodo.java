public class Nodo {
    int habitacion;
    Nodo derecho;
    Nodo izquierdo;
    Nodo arriba;
    Nodo abajo;
    public Nodo(int habitacion) {
        this.derecho = this.izquierdo = this.arriba = this.abajo = null;
        this.habitacion = habitacion;

    }
}
