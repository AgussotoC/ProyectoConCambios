public class Lista {
    Nodo inicio;

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
    public Nodo insertarNodo(Nodo habitacionActual, String direccion, Nodo nuevoNodo){
        switch(direccion.toLowerCase()){
            case "w":
                //arriba
                habitacionActual.arriba = nuevoNodo;
                nuevoNodo.abajo = habitacionActual;

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
        return habitacionActual;
    }
    public void imprimirMapa(int opcion, Nodo habitacionActual){
        int[][] mapa = new int[6][6];
        System.out.println("Mapa mazmorra");
        for(int i = 0; i < mapa[0].length ; i ++){
            for(int j = 0; j < mapa[1].length ; j ++){
                if(habitacionActual != null){
                    if(i == 1 && j == 1){
                        if(habitacionActual.arriba == null){
                            System.out.println("[N]");
                        }else{
                            System.out.println("[" + habitacionActual.arriba.getNumeroCuarto() + "]");
                        } 
                    }else if(i == 2 && j == 3){
                        System.out.println(" | ");
                    }else if (i == 3 && j == 3) {
                        System.out.print("[" + habitacionActual.getNumeroCuarto()+ "]");
                    }else if (i == 4 && j == 3) {
                        System.out.println("     |   ");
                    }else if(i == 5 && j == 3){
                        if(habitacionActual.abajo == null){
                            System.out.println("  [N]  ");
                        }else{
                            System.out.println(" [" + habitacionActual.abajo.getNumeroCuarto()+ "] ");
                        }
                    }else if (i == 3 && j == 1 ) {
                        if(habitacionActual.izquierdo == null){
                            System.out.print("[N]");
                        }else{
                            System.out.print("[" + habitacionActual.izquierdo.getNumeroCuarto()+ "]");
                        }
                    }else if(i == 3 && j == 2){
                        System.out.print("-");
                    }else if(i == 3 && j == 4){
                        System.out.print("-");
                    }else if(i == 3 && j == 5){
                        if(habitacionActual.derecho == null){
                            System.out.println("[N]");
                        }else{
                            System.out.println("[" + habitacionActual.derecho.getNumeroCuarto()+ "]");
                        }
                    }else{
                        System.out.print(" ");
                    }
                }
            }
        }
    }
}
    /* 
    public void imprimirHabitaciones(){
        Nodo actual = inicio;
        while(actual != null){
            actual.imprimirNodos();
            actual = actual.derecho;
        }
    }*/
    

