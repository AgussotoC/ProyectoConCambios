import java.util.Random;
import java.util.Scanner;
public class Main {
    //Iniciacion de clases
    UID uid = new UID();
    Scanner scanner = new Scanner(System.in);
    //Variables globales
    static boolean gano;
    static Items[] items = new Items[15];
    int dañoVeneno = 1;
    public Main(){
        //Creacion de matriz y encontrar coordenadas de las entidades
        uid.generarAreaMatriz(1);
        uid.encontrarCoordenadasEntidades();
    }
    //Ejecución del main
    public static void main(String[] args) {
        Main juego = new Main();
        //Vector y variable necesarias para mover la casilla enemigo
        Armaduras armaduraI = new Armaduras("Sin armadura", "Defensa Base");
        Armas armaI = new Armas("Sin arma","Ataque base");
        Items buffI = new Items("Sin buff", 1, "Sin afecto");
        Items debuffI = new Items("Sin debuff", 1, "Sin afecto");
        //Creacion del jugador
        Agentes jugador = new Agentes(6,500,70,50, armaI , armaduraI, buffI, debuffI);


        //Creacion del banco de enemigos
        Agentes[] enemigos = new Agentes[6];
        for(int i = 0; i < enemigos.length; i ++){
            int vidaAleatoria = random.nextInt(100,300);
            int ataqueAleatorio = random.nextInt(50,125);
            int defensaAleatoria = random.nextInt(50,70);
            enemigos[i] = new Agentes('E', vidaAleatoria, ataqueAleatorio, defensaAleatoria, armaI , armaduraI, 0);
        }

        //Elección aleatoria del banco de enemigos para el juego (enemigos[enemigoActual])
        enemigoActual = random.nextInt(enemigos.length);

        //Creacion de Items
        for(int i = 0; i< items.length; i++){
            //Creacion de los items, variable porcentaje para las pociones y sus buffos random
            double min = 0.05;
            double max = 0.10;
            double porcentajes = random.nextDouble(min,max) + 0.01;
            items[0] = new Items("Ninguno", 0,"Nada");
            //Creacion de las armas
            items[1] = new Items("Arma Secreta",(enemigos[enemigoActual].getSalud()/2 + jugador.getAtaque()),"Reduce en un 50% la vida actual del enemigo actual");
            items[2] = new Items("Arma Basica", jugador.getAtaque(), "Ataque normal");
            items[3] = new Items("Arma Legendaria", (jugador.getAtaque()*2),"Realiza el doble de daño que el ataque normal");
            //Creacion de las armaduras
            items[4] = new Items("Armadura Secreta", 200, "Se regenera despues de cada partida");
            items[5] = new Items("Armadura Legendaria", 200, "Reduce un 50% del ataque recibido");
            //creacion de los Items
            items[6] = new Items("Aumentar Ataque", (Math.round(jugador.getAtaque() + (jugador.getAtaque() * porcentajes))),"Aumenta el ataque en un rango de 5 a 10%");
            items[7] = new Items("Reducir Defensa",(enemigos[enemigoActual].getDefensa() - enemigos[enemigoActual].getDefensa() * 0.15),"Recuce la defensa del enemigo en un 15%");
            // creacion de las armas del enemigo
            items[8] = new Items("Arma Secreta",(jugador.getSalud()/2 + enemigos[enemigoActual].getAtaque()),"Reduce en un 50% la vida actual del enemigo actual");
            items[9] = new Items("Arma Basica", enemigos[enemigoActual].getAtaque(), "Ataque normal");
            items[10] = new Items("Arma Legendaria", (enemigos[enemigoActual].getAtaque()*2),"Realiza el doble de daño que el ataque normal");
            //creacion de los Items del enemigo
            items[11] = new Items("Aumentar Ataque", (Math.round(enemigos[enemigoActual].getAtaque() + (enemigos[enemigoActual].getAtaque() * porcentajes))),"Aumenta el ataque en un rango de 5 a 10%");
            items[12] = new Items("Reducir Defensa",(jugador.getDefensa() - jugador.getDefensa() * 0.15),"Recuce la defensa del enemigo en un 15%");
        }
        //Creacion de matriz y encontrar coordenadas de las entidades
        
        juego.uid.generarAreaMatriz(1);
        juego.uid.encontrarCoordenadasEntidades();

        //Comprobantes de que sí un objeto fue agarrado
        boolean itemTaken = false;
        boolean armaTaken = false;
        boolean debuffTaken = false;

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