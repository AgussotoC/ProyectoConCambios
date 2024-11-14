import java.util.Scanner;
public class Main {
    //Iniciacion de clases
    UID uid = new UID();
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
        
        lista.insertarInicio(juego.uid.generarAreaMatriz(1));
        juego.uid.encontrarCoordenadasEntidades();
        lista.imprimirHabitaciones();

        //Ver si salió de la habitación

        boolean puertaTaken = false;

        while(puertaTaken == false && jugador.getSalud() != 0){
            jugador.statusJugador(jugador);
            juego.uid.imprimirMatriz(); String mover = juego.scanner.nextLine();
            try{
                juego.uid.moverPersonaje(mover, jugador); System.out.println();
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
                juego.uid.encontrarEquipable(jugador);

                //Incialización del comprobante para ver si el jugador esta en el area del enemigo
                if(juego.uid.areaEnemigo(jugador) == true){
                    gano = false;
                    System.out.println("Hay combate");
                }
            } catch (ArrayIndexOutOfBoundsException a){
                switch (mover){
                    case "w":
                        System.out.println("Se fue a la habitacion de arriba");
                        puertaTaken = true; break;
                    case "d":
                        System.out.println("Se fue a la habitacion de la derecha");
                        puertaTaken = true; break;
                    case "s":
                        System.out.println("Se fue a la habitacion de abajo");
                        puertaTaken = true; break;
                    case "a":
                        System.out.println("Se fue a la habitacion de la izquierda");
                        puertaTaken = true; break;
                }
                /*
                Ya trabajando con nodos sería algo así en cada case:
                (el ejemplo esta en el case de la derecha, pero aplica para cada uno con su correspondiente case)
                if(actual.derecha != null)
                {
                    Nodo nuevoNodo = new Nodo(numCuarto),
                    nuevoNodo.izquierda = actual;
                    actual.derecha = nuevoNodo;
                    actual = nuevoNodo
                }
                else
                {
                    actual = actual.derecha;
                }
                */
            }
        }
    }
}