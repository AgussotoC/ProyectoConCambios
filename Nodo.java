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
    public void imprimirNodos() {
        /*for(int i =0 ;i < habitacion[0].length; i++){
            for(int j = 0; j < habitacion[1].length; j ++){
                System.out.print(habitacion[i][j]);
            }
        }
        System.out.println("");
    }*/
    }
}
