import java.util.Random;
import java.util.Scanner;
public class Main {
    //Iniciacion de clases
    int numCuarto = 1;
    Random rand = new Random();
    UID uid = new UID(numCuarto, "w", false, false);
    static Scanner scanner = new Scanner(System.in);
    //Variables globales
    static Items[] items = new Items[15];
    int fila = 50;
    int columna = 50;
    Nodo[][] mazmorra = new Nodo[fila][columna];
    
    int probalidadBoss = 10;
    int probalidadSalida = 5;
    boolean hayBoss = false;
    boolean haySalida = false;
    
    int numeroDeBoss = 0;
    int numeroDeSalida = 0;

    int tickVeneno = 1;
    int venenoTurnos = 1;

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
        if(indexi > 0 && mazmorra[indexi - 1][indexj] != null){
            actual.arriba = mazmorra[indexi - 1][indexj];
            actual.arriba.abajo = actual;
        }
        
        
        if(indexj > 0 && mazmorra[indexi][indexj - 1] != null){
            actual.izquierdo = mazmorra[indexi][indexj - 1];
            actual.izquierdo.derecho = actual;
        }
        
        
        if(indexi < fila - 1 && mazmorra[indexi + 1][indexj] != null){
            actual.abajo = mazmorra[indexi + 1][indexj];
            actual.abajo.arriba = actual;
        }
        
        
        if(indexj < fila - 1 && mazmorra[indexi][indexj + 1] != null){
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
    public void mostrarMapa(Lista lista, Agentes agente, Nodo actuall){
        System.out.println("1) Mapa");
        System.out.println("2) inventario");
        int opcion = scanner.nextInt();
        if (opcion == 1) {
            lista.imprimirMapa(actuall);
            System.out.println("");
        }else if (opcion == 2) {
            System.out.println("inventario");
            agente.mostrarInventario();
        }
    }
    //Ejecución del main
    private void comprobarBoss() {
        if (!hayBoss && rand.nextInt(100) + 1 <= probalidadBoss) {
            hayBoss = true;
        } else if(!hayBoss){
            probalidadBoss += 7;
        }
    }
    private void comprobarSalida(){
        if(!haySalida && rand.nextInt(100) + 1 <= probalidadSalida){
            haySalida = true;
        } else if(!hayBoss){
            probalidadSalida += 5;
        }
    }
    public static void main(String[] args) {
        Main juego = new Main();
        Lista lista = new Lista();
        //Vector y variable necesarias para mover la casilla enemigo
        Armaduras armaduraI = new Armaduras("Sin armadura", "Sin armadura");
        Armas armaI = new Armas("Sin arma","Sin arma");
        Items buffI = new Items("Sin buff", "Sin afecto");
        Items debuffI = new Items("Sin debuff",  "Sin afecto");
        //Creacion del jugador
        Agentes jugador = new Agentes(6,500,70,50, armaI , armaduraI, buffI, debuffI);
        
        //Creacion de matriz y encontrar coordenadas de las entidades
        
        juego.uid.encontrarCoordenadasEntidades();
        
        //Ver si salió de la habitación
        jugador.setLlave(false);
        String mover = "w";
        Nodo actual = new Nodo(juego.uid, juego.numCuarto);
        lista.insertarInicio(actual, mover);
        juego.mazmorra[25][25] = actual;



        //Ejecucion principal del juego
        while(!jugador.getWin() && jugador.getSalud() != 0){
            juego.revisarConexiones(actual);
            actual.uid.imprimirMatriz(jugador); mover = juego.scanner.nextLine();
            try{
                if(mover.equalsIgnoreCase("m")){
                    juego.mostrarMapa(lista, jugador, actual);
                    juego.scanner.nextLine(); //Come el espacio muerto
                }else{
                    actual.uid.moverPersonaje(mover, jugador); System.out.println();
                    if(jugador.debuff.getNombre().equalsIgnoreCase("Veneno")){
                        jugador.setSalud(jugador.getSalud() - juego.tickVeneno);
                        juego.tickVeneno += 1;
                        juego.venenoTurnos += 1;
                        if(juego.venenoTurnos > 10){
                            Items sinEfecto = new Items("Sin efecto", "Sin efecto");
                            jugador.setDebuff(sinEfecto);
                            juego.venenoTurnos = 1;
                        }
                    }
                }
                //Comprobantes para ver si el jugador agarró una arma, item, debuff, o entró a la puerta
                actual.uid.encontrarEquipable(jugador);
                
                //Incialización del comprobante para ver si el jugador esta en el area del enemigo
                if(actual.uid.areaEnemigo(jugador));
                if(actual.getTipoHabitacion().equalsIgnoreCase("jefe") && actual.uid.areaBoss(jugador) == true);
            } catch (ArrayIndexOutOfBoundsException opcion){
                switch (mover){
                    case "w":
                    System.out.println("Se fue a la habitacion de arriba");
                    if(actual.arriba == null)
                    {
                        if(juego.numeroDeBoss == 0){
                            juego.comprobarBoss();
                        }
                        if(!juego.hayBoss){
                            if(juego.numeroDeSalida == 0){
                                juego.comprobarSalida();
                            }
                        }
                        juego.numCuarto++;
                        UID uidNuevo = new UID(juego.numCuarto, mover, juego.hayBoss, juego.haySalida);
                        Nodo nuevoNodo = new Nodo(uidNuevo, juego.numCuarto);
                        if(!juego.hayBoss){
                            if(juego.haySalida){
                                juego.numeroDeSalida += 1;
                                System.out.println("Has encontrado la salida");
                                nuevoNodo.setTipoHabitacion("salida");
                                juego.haySalida = false;
                            }
                        }
                        if(juego.hayBoss){
                            juego.numeroDeBoss += 1;
                            System.out.println("Has encontrado al jefe");
                            nuevoNodo.setTipoHabitacion("jefe");
                            juego.hayBoss = false;
                        }
                        lista.insertarNodo(actual, mover, nuevoNodo);
                        actual = nuevoNodo;
                        juego.agregarAMatriz(actual, mover);
                    }
                    else
                    {
                        actual = actual.arriba;
                        actual.uid.reasignarPosicionJugadorCuartoViejo(mover);
                    }
                    break;
                    
                    case "d":
                    System.out.println("Se fue a la habitacion de la derecha");
                    if(actual.derecho == null)
                    {
                        if(juego.numeroDeBoss == 0){
                            juego.comprobarBoss();
                        }
                        if(!juego.hayBoss){
                            if(juego.numeroDeSalida == 0){
                                juego.comprobarSalida();
                            }
                        }
                        juego.numCuarto++;
                        UID uidNuevo = new UID(juego.numCuarto, mover, juego.hayBoss, juego.haySalida);
                        Nodo nuevoNodo = new Nodo(uidNuevo, juego.numCuarto);
                        if(!juego.hayBoss){
                            if(juego.haySalida){
                                juego.numeroDeSalida += 1;
                                System.out.println("Has encontrado la salida");
                                nuevoNodo.setTipoHabitacion("salida");
                                juego.haySalida = false;
                            }
                        }
                        if(juego.hayBoss){
                            juego.numeroDeBoss += 1;
                            System.out.println("Has encontrado al jefe");
                            nuevoNodo.setTipoHabitacion("jefe");
                            juego.hayBoss = false;
                        }
                        lista.insertarNodo(actual, mover, nuevoNodo);
                        actual = nuevoNodo;
                        juego.agregarAMatriz(actual, mover);
                        
                    }
                    else
                    {
                        actual = actual.derecho;
                        actual.uid.reasignarPosicionJugadorCuartoViejo(mover);
                    }
                    break;
                    case "s":
                    System.out.println("Se fue a la habitacion de abajo");
                    if(actual.abajo == null)
                    {
                        if(juego.numeroDeBoss == 0){
                            juego.comprobarBoss();
                        }
                        if(!juego.hayBoss){
                            if(juego.numeroDeSalida == 0){
                                juego.comprobarSalida();
                            }
                        }
                        juego.numCuarto++;
                        UID uidNuevo = new UID(juego.numCuarto, mover, juego.hayBoss, juego.haySalida);
                        Nodo nuevoNodo = new Nodo(uidNuevo, juego.numCuarto);
                        if(!juego.hayBoss){
                            if(juego.haySalida){
                                juego.numeroDeSalida += 1;
                                System.out.println("Has encontrado la salida");
                                nuevoNodo.setTipoHabitacion("salida");
                                juego.haySalida = false;
                            }
                        }
                        if(juego.hayBoss){
                            juego.numeroDeBoss += 1;
                            System.out.println("Has encontrado al jefe");
                            nuevoNodo.setTipoHabitacion("jefe");
                            juego.hayBoss = false;
                        }
                        lista.insertarNodo(actual, mover, nuevoNodo);
                        actual = nuevoNodo;
                        juego.agregarAMatriz(actual, mover);
                        
                    }
                    else
                    {
                        actual = actual.abajo;
                        actual.uid.reasignarPosicionJugadorCuartoViejo(mover);
                    }
                    break;
                    case "a":
                    System.out.println("Se fue a la habitacion de la izquierda");
                    if(actual.izquierdo == null)
                    {
                        if(juego.numeroDeBoss == 0){
                            juego.comprobarBoss();
                        }
                        if(!juego.hayBoss){
                            if(juego.numeroDeSalida == 0){
                                juego.comprobarSalida();
                            }
                        }
                        juego.numCuarto++;
                        UID uidNuevo = new UID(juego.numCuarto, mover, juego.hayBoss, juego.haySalida);
                        Nodo nuevoNodo = new Nodo(uidNuevo, juego.numCuarto);
                        if(!juego.hayBoss){
                            if(juego.haySalida){
                                juego.numeroDeSalida += 1;
                                System.out.println("Has encontrado la salida");
                                nuevoNodo.setTipoHabitacion("salida");
                                juego.haySalida = false;
                            }
                        }
                        if(juego.hayBoss){
                            juego.numeroDeBoss += 1;
                            System.out.println("Has encontrado al jefe");
                            nuevoNodo.setTipoHabitacion("jefe");
                            juego.hayBoss = false;
                        }
                        lista.insertarNodo(actual, mover, nuevoNodo);
                        actual = nuevoNodo;
                        juego.agregarAMatriz(actual, mover);
                        
                    }
                    else
                    {
                        actual = actual.izquierdo;
                        actual.uid.reasignarPosicionJugadorCuartoViejo(mover);
                    }
                    break;
                }
            }
        }
        if (jugador.getWin() == true) {
            System.out.println("-------Has ganado!-------");
        }
    }
}