public class Lista {
    Nodo inicio;
    Nodo [][] habitacion;
    UID uid = new UID();

    public Lista(){
        inicio = null;
    }
    public void insertarInicio(int[][] matriz){
        Nodo nuevoNodo = new Nodo(matriz);
       if(inicio == null){
            inicio = nuevoNodo;
       }else{
            Nodo ultimo = inicio;
            while(ultimo.abajo == null){
                ultimo = ultimo.abajo;
            }
       }
    }
    public void insertarHabitaciones(Nodo habitacionActual, String direccion, int[][] dato){
        Nodo nuevahabitacion = new Nodo(dato);
        switch(direccion.toLowerCase()){
            case "W":
                //arriba
                if(habitacionActual.arriba != null){
                    habitacionActual.arriba = nuevahabitacion;
                    nuevahabitacion.abajo = habitacionActual;
                }
                break;
            case "s":
                // abajo
                habitacionActual.abajo = nuevahabitacion;
                nuevahabitacion.arriba = habitacionActual;
                break;
            case "a":
                //izquierda
                habitacionActual.izquierdo = nuevahabitacion;
                nuevahabitacion.derecho = habitacionActual;
                break;
            case "d":
                // derecha
                habitacionActual.derecho = nuevahabitacion;
                nuevahabitacion.izquierdo = habitacionActual;
                break;

        }
    }
    public void imprimirHabitaciones(){
        Nodo actual = inicio;
        while(actual != null){
            actual.imprimirNodos();
            actual = actual.derecho;
        }
    }
    
}
