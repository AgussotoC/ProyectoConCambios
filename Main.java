import java.util.Scanner;
public class Main {
    //Iniciacion de clases
    int numCuarto = 1;
    UID uid = new UID(numCuarto, "w");
    static Scanner scanner = new Scanner(System.in);
    //Variables globales
    static boolean gano;
    static Items[] items = new Items[15];
    int fila = 50;
    int columna = 50;
    Nodo[][] mazmorra = new Nodo[fila][columna];
    int dañoVeneno = 1;
    private void revisarConexiones(Nodo actual){
        int indexi = 0;
        int indexj = 0;
        for (int i = 0; i < fila; i++) {
            for (int j = 0; j < columna; j++) {
                if(mazmorra[i][j] == actual){
                    indexi = i;
                    indexj = j;
                }
            }
        }
                if(mazmorra[indexi - 1][indexj] != null){
                    actual.arriba = mazmorra[indexi - 1][indexj];
                    actual.arriba.abajo = actual;
                }


                if(mazmorra[indexi][indexj - 1] != null){
                    actual.izquierdo = mazmorra[indexi][indexj - 1];
                    actual.izquierdo.derecho = actual;
                }


                if(mazmorra[indexi + 1][indexj] != null){
                    actual.abajo = mazmorra[indexi + 1][indexj];
                    actual.abajo.arriba = actual;
                }


                if(mazmorra[indexi][indexj + 1] != null){
                    actual.derecho = mazmorra[indexi][indexj + 1];
                    actual.derecho.izquierdo = actual;
                }

    }

    private void agregarAMatriz(Nodo actual, String wasd){
        switch (wasd){
            case "w":
                for(int i = 0; i < fila; i++){
                    for(int j = 0; j < columna; j++){
                        if(mazmorra[i][j] == actual.abajo){
                            mazmorra[i-1][j] = actual;
                            break;
                        }
                    }
                }
                break;
            case "a":
                for(int i = 0; i < fila; i++){
                    for(int j = 0; j < columna; j++){
                        if(mazmorra[i][j] == actual.derecho){
                            mazmorra[i][j-1] = actual;
                            break;
                        }
                    }
                }
                break;
            case "s":
                for(int i = 0; i < fila; i++){
                    for(int j = 0; j < columna; j++){
                        if(mazmorra[i][j] == actual.arriba){
                            mazmorra[i+1][j] = actual;
                            break;
                        }
                    }
                }
                break;
            case "d":
                for(int i = 0; i < fila; i++){
                    for(int j = 0; j < columna; j++){
                        if(mazmorra[i][j] == actual.izquierdo){
                            mazmorra[i][j+1] = actual;
                            break;
                        }
                    }
                }
                break;
        }
    }
    //Ejecución del main
    public static void main(String[] args) {
        Main juego = new Main();
        Lista lista = new Lista();
        //Vector y variable necesarias para mover la casilla enemigo
        Armaduras armaduraI = new Armaduras("Sin armadura", "Sin armadura");
        Armas armaI = new Armas("Sin arma","Sin arma");
        Items buffI = new Items("Sin buff", 1, "Sin afecto");
        Items debuffI = new Items("Sin debuff", 1, "Sin afecto");
        //Creacion del jugador
        Agentes jugador = new Agentes(6,500,70,50, armaI , armaduraI, buffI, debuffI);

        //Creacion de matriz y encontrar coordenadas de las entidades

        juego.uid.encontrarCoordenadasEntidades();

        //Ver si salió de la habitación
        boolean puertaTaken = false;
        jugador.setLlave(0);
        String mover = "w";
        Nodo actual = new Nodo(juego.uid, juego.numCuarto);
        lista.insertarInicio(actual, mover);
        juego.mazmorra[25][25] = actual;
       


        //Ejecucion principal del juego
        while(puertaTaken == false && jugador.getSalud() != 0){
            juego.revisarConexiones(actual);
            actual.uid.imprimirMatriz(jugador); mover = juego.scanner.nextLine();
            try{
                if(mover.equalsIgnoreCase("m")){
                    juego.mostrarMapa(lista, jugador, actual);
                }else{
                    actual.uid.moverPersonaje(mover, jugador); System.out.println();
                }
                /*if(jugador.getDebuff()){
                    jugador.venenoAtaque(juego.dañoVeneno);
                    if(jugador.getSalud() != 0){
                        System.out.println("Daño del veneno: " + juego.dañoVeneno);
                        if(juego.dañoVeneno < 10){
                            juego.dañoVeneno += 1;
                        }
                    }
                }*/
            /*if(enemigos[enemigoActual] != null){
                if(enemigos[enemigoActual].getDebuff()){
                    enemigos[enemigoActual].venenoAtaque(juego.dañoVeneno);
                    if(enemigos[enemigoActual].getSalud() == 0){
                        enemigos[enemigoActual] = null;
                        for (int i = 0; i < juego.uid.num; i++) {
                            for (int j = 0; j < juego.uid.num; j++) {
                                if (juego.uid.matriz[i][j] == 2) {
                                    juego.uid.matriz[i][j] = 0;
                                }
                            }
                        }
                    }
                    if(juego.dañoVeneno < 10){
                        juego.dañoVeneno += 1;
                    }
                }
            }*/
                //Comprobantes para ver si el jugador agarró una arma, item, debuff, o entró a la puerta
                actual.uid.encontrarEquipable(jugador);

                //Incialización del comprobante para ver si el jugador esta en el area del enemigo
                if(actual.uid.areaEnemigo(jugador) == true){
                    gano = false;
                    System.out.println("Hay combate");
                }
            } catch (ArrayIndexOutOfBoundsException a){
                switch (mover){
                    case "w":
                        System.out.println("Se fue a la habitacion de arriba");
                        if(actual.arriba == null)
                        {
                            juego.numCuarto++;
                            UID uidNuevo = new UID(juego.numCuarto, mover);
                            Nodo nuevoNodo = new Nodo(uidNuevo, juego.numCuarto);
                            lista.insertarNodo(actual, mover, nuevoNodo);
                            actual = nuevoNodo;
                            juego.agregarAMatriz(actual, mover);
                        }
                        else
                        {
                            actual = actual.arriba;
                            actual.uid.reasignarPosicionJugador(mover);
                        }
                        break;

                    case "d":
                        System.out.println("Se fue a la habitacion de la derecha");
                        if(actual.derecho == null)
                        {
                            juego.numCuarto++;
                            UID uidNuevo = new UID(juego.numCuarto, mover);
                            Nodo nuevoNodo = new Nodo(uidNuevo, juego.numCuarto);
                            lista.insertarNodo(actual, mover, nuevoNodo);
                            actual = nuevoNodo;
                            juego.agregarAMatriz(actual, mover);
                        }
                        else
                        {
                            actual = actual.derecho;
                            actual.uid.reasignarPosicionJugador(mover);
                        }
                        break;
                    case "s":
                        System.out.println("Se fue a la habitacion de abajo");
                        if(actual.abajo == null)
                        {
                            juego.numCuarto++;
                            UID uidNuevo = new UID(juego.numCuarto, mover);
                            Nodo nuevoNodo = new Nodo(uidNuevo, juego.numCuarto);
                            lista.insertarNodo(actual, mover, nuevoNodo);
                            actual = nuevoNodo;
                            juego.agregarAMatriz(actual, mover);
                        }
                        else
                        {
                            actual = actual.abajo;
                            actual.uid.reasignarPosicionJugador(mover);
                        }
                        break;
                    case "a":
                        System.out.println("Se fue a la habitacion de la izquierda");
                        if(actual.izquierdo == null)
                        {
                            juego.numCuarto++;
                            UID uidNuevo = new UID(juego.numCuarto, mover);
                            Nodo nuevoNodo = new Nodo(uidNuevo, juego.numCuarto);
                            lista.insertarNodo(actual, mover, nuevoNodo);
                            actual = nuevoNodo;
                            juego.agregarAMatriz(actual, mover);
                        }
                        else
                        {
                            actual = actual.izquierdo;
                            actual.uid.reasignarPosicionJugador(mover);
                        }
                        break;
                }
            }
        }
    }
    public void mostrarMapa(Lista lista, Agentes agente, Nodo actuall){
        System.out.println("1) Mapa");
        System.out.println("2) inventario");
        int opcion = scanner.nextInt();
        if (opcion == 1) {
            lista.imprimirMapa(actuall);
        }else if (opcion == 2) {
            System.out.println("inventario");
            agente.mostrarInventario();
        }
    }
}