public class Nodo {
    UID uid;
    Nodo derecho;
    Nodo izquierdo;
    Nodo arriba;
    Nodo abajo;
    int numCuarto;
    String tipoHabitacion;
    public Nodo(UID uid, int numCuarto) {
        this.derecho = this.izquierdo = this.arriba = this.abajo = null;
        this.uid = uid;
        this.numCuarto = numCuarto;
        this.tipoHabitacion = "normal";

    }
    public int getNumeroCuarto(){
        return numCuarto;
    }
    public String getTipoHabitacion(){
        return tipoHabitacion;
    }
    public void setTipoHabitacion(String nuevoTipo){
        tipoHabitacion = nuevoTipo;
    }
}
