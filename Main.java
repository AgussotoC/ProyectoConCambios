import java.util.Scanner;
public class Main {
    //Iniciacion de clases
    int numCuarto = 1;
    UID uid = new UID(numCuarto);
    Scanner scanner = new Scanner(System.in);
    //Variables globales
    static boolean gano;
    static Items[] items = new Items[15];
    int dañoVeneno = 1;
    //Ejecución del main
    public static void main(String[] args) {
        Main juego = new Main();
        Lista lista = new Lista();
        //Vector y variable necesarias para mover la casilla enemigo
        Armaduras armaduraI = new Armaduras("Sin armadura", "Defensa Base");
        Armas armaI = new Armas("Sin arma","Ataque base");
        Items buffI = new Items("Sin buff", 1, "Sin afecto");
        Items debuffI = new Items("Sin debuff", 1, "Sin afecto");
        //Creacion del jugador
        Agentes jugador = new Agentes(6,500,70,50, armaI , armaduraI, buffI, debuffI);

        //Creacion de matriz y encontrar coordenadas de las entidades

        juego.uid.encontrarCoordenadasEntidades();
        lista.imprimirHabitaciones();

        //Ver si salió de la habitación
        boolean puertaTaken = false;

        String mover = "W";
        Nodo actual = new Nodo(juego.uid, juego.numCuarto);
        lista.insertarInicio(actual, mover);

        //Ejecucion principal del juego
        while(puertaTaken == false && jugador.getSalud() != 0){
            jugador.statusJugador(jugador);
            actual.uid.imprimirMatriz(); mover = juego.scanner.nextLine();
            try{
                actual.uid.moverPersonaje(mover, jugador); System.out.println();
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
                            UID uidNuevo = new UID(juego.numCuarto);
                            Nodo nuevoNodo = new Nodo(uidNuevo, juego.numCuarto);
                            lista.insertarNodo(actual, mover, nuevoNodo);
                            actual = nuevoNodo;
                        }
                        else
                        {
                            actual = actual.arriba;
                        }
                        break;

                    case "d":
                        System.out.println("Se fue a la habitacion de la derecha");
                        if(actual.derecho == null)
                        {
                            juego.numCuarto++;
                            UID uidNuevo = new UID(juego.numCuarto);
                            Nodo nuevoNodo = new Nodo(uidNuevo, juego.numCuarto);
                            lista.insertarNodo(actual, mover, nuevoNodo);
                            actual = nuevoNodo;
                        }
                        else
                        {
                            actual = actual.derecho;
                        }
                        break;
                    case "s":
                        System.out.println("Se fue a la habitacion de abajo");
                        if(actual.abajo == null)
                        {
                            juego.numCuarto++;
                            UID uidNuevo = new UID(juego.numCuarto);
                            Nodo nuevoNodo = new Nodo(uidNuevo, juego.numCuarto);
                            lista.insertarNodo(actual, mover, nuevoNodo);
                            actual = nuevoNodo;
                        }
                        else
                        {
                            actual = actual.abajo;
                        }
                        break;
                    case "a":
                        System.out.println("Se fue a la habitacion de la izquierda");
                        if(actual.izquierdo == null)
                        {
                            juego.numCuarto++;
                            UID uidNuevo = new UID(juego.numCuarto);
                            Nodo nuevoNodo = new Nodo(uidNuevo, juego.numCuarto);
                            lista.insertarNodo(actual, mover, nuevoNodo);
                            actual = nuevoNodo;
                        }
                        else
                        {
                            actual = actual.izquierdo;
                        }
                        break;
                }
            }
        }
    }
}