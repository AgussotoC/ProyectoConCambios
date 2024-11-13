public class Nodo {
    Object[][] habitacion;
    Nodo derecho;
    Nodo izquierdo;
    Nodo arriba;
    Nodo abajo;
    int NumHabitacion;
    /*char[][] matriz;
    UID uid = new UID();*/
    public Nodo(int NumHabitacion , Object[][] habitacion) {
        this.NumHabitacion = NumHabitacion;
        this.derecho = this.izquierdo = this.arriba = this.abajo = null;
        this.habitacion = habitacion;

    }
}
