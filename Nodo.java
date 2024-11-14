public class Nodo {
    int[][] habitacion;
    Nodo derecho;
    Nodo izquierdo;
    Nodo arriba;
    Nodo abajo;
    public Nodo(int[][] habitacion) {
        this.derecho = this.izquierdo = this.arriba = this.abajo = null;
        this.habitacion = habitacion;

    }
    public void imprimirNodos(){
        /*for(int i =0 ;i < habitacion[0].length; i++){
            for(int j = 0; j < habitacion[1].length; j ++){
                System.out.print(habitacion[i][j]);
            }
        }
        System.out.println("");
    }*/
}
