public class Lista {
    Nodo inicio;
    Nodo [][] habitacion;

    public Lista(){
        inicio = null;
    }
    public void insertarInicio(Nodo nuevoNodo, String wasd){
       if(inicio == null){
            inicio = nuevoNodo;
       }else{
            Nodo ultimo = inicio;
            while(ultimo.abajo == null){
                ultimo = ultimo.abajo;
            }
       }
    }
    public void insertarNodo(Nodo habitacionActual, String direccion, Nodo nuevoNodo){
        switch(direccion.toLowerCase()){
            case "w":
                //arriba
                if(habitacionActual.arriba != null){
                    habitacionActual.arriba = nuevoNodo;
                    nuevoNodo.abajo = habitacionActual;
                }
                break;
            case "s":
                // abajo
                habitacionActual.abajo = nuevoNodo;
                nuevoNodo.arriba = habitacionActual;
                break;
            case "a":
                //izquierda
                habitacionActual.izquierdo = nuevoNodo;
                nuevoNodo.derecho = habitacionActual;
                break;
            case "d":
                // derecha
                habitacionActual.derecho = nuevoNodo;
                nuevoNodo.izquierdo = habitacionActual;
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
