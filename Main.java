import java.util.Random;
import java.util.Scanner;
public class Main {
    //Iniciacion de clases
    UID uid = new UID();
    Random rand = new Random();
    Scanner scanner = new Scanner(System.in);
    //Variables globales
    static int enemigoActual;
    static boolean armaduraSecreta;
    static boolean armaduraLegendaria = false;
    static boolean enemigoArmaduraLegendaria = false;
    static boolean gano;
    static Items[] items = new Items[15];
    int dañoVeneno = 1;
    //Funcion de combate

    //Comprobantes de agarrar items
    public boolean AgarrarItem(){
        boolean agarroItem = true;
        for(int i = 0; i < uid.num; i++){
            for(int j = 0; j < uid.num; j++){
                if(uid.matriz[i][j] == 3){
                    agarroItem = false;
                    break;
                }
            }
        }
        return agarroItem;
    }
    public boolean AgarrarArma(){
        boolean agarroArma = true;
        for(int i = 0; i < uid.num; i++){
            for(int j = 0; j < uid.num; j++){
                if(uid.matriz[i][j] == 4){
                    agarroArma = false;
                    break;
                }
            }
        }
        return agarroArma;
    }
    public boolean AgarrarDebuff(){
        boolean agarroDebuff = true;
        for(int i = 0; i < uid.num; i++){
            for(int j = 0; j < uid.num; j++){
                if(uid.matriz[i][j] == 'D'){
                    agarroDebuff = false;
                    break;
                }
            }
        }
        return agarroDebuff;
    }
    public boolean AgarrarArmadura(){
        boolean agarroArmadura = true;
        for(int i = 0; i < uid.num; i++){
            for(int j = 0; j < uid.num; j++){
                if(uid.matriz[i][j] == 5){
                    agarroArmadura = false;
                }
            }
        }
        return agarroArmadura;
    }

    //Ejecución del main
    public static void main(String[] args) {
        Random random = new Random();
        Main juego = new Main();
        //Vector y variable necesarias para mover la casilla enemigo
        Items armaduraI = new Items("hola", 0, " ");
        Items armaI = new Items("hola", 0, " ");
        //Creacion del jugador
        Agentes jugador = new Agentes(6,500,70,50, armaI , armaduraI, 0);

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
        boolean puertaTaken = false;
        boolean armaduraTaken = false;

        //Generacion del indice del item, armadura y arma
        int itemAleatorio = random.nextInt(6,8);
        int armaAleatoria = random.nextInt(1,4);
        int armaduraAleatoria = random.nextInt(4,6);
        int armaAleatoriaEnemigo = random.nextInt(8,11);
        int itemAleatorioEnemigo = random.nextInt(11,13);

        while(puertaTaken == false && jugador.getSalud() != 0){
            jugador.statusJugador(jugador);
            juego.uid.imprimirMatriz(); String mover = juego.scanner.nextLine();
            juego.uid.moverPersonaje(mover, jugador); System.out.println();
            if(jugador.getDebuff()){
                jugador.venenoAtaque(juego.dañoVeneno);
                if(jugador.getSalud() != 0){
                    System.out.println("Daño del veneno: " + juego.dañoVeneno);
                    if(juego.dañoVeneno < 10){
                        juego.dañoVeneno += 1;
                    }
                }
            }
            if(enemigos[enemigoActual] != null){
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
            }
            //Comprobantes para ver si el jugador agarró una arma, item, debuff, o entró a la puerta
            if(itemTaken == false){
                if(juego.AgarrarItem() == true){
                    if(juego.uid.matriz[juego.uid.indexiItems][juego.uid.indexjItems] == 6){
                        System.out.println("Has obtenido: ");
                        System.out.println(items[itemAleatorio]);
                        jugador.agregarItemAlInventario(items[itemAleatorio]);
                    }else{
                        System.out.println("El enemigo ha obtenido: ");
                        System.out.println(items[itemAleatorioEnemigo]);
                        enemigos[enemigoActual].agregarItemAlInventario(items[itemAleatorioEnemigo]);
                    }
                    itemTaken = true;
                }
            }
            if(armaTaken == false){
                if(juego.AgarrarArma() == true){
                    if(juego.uid.matriz[juego.uid.indexiArmas][juego.uid.indexjArmas] == 6){
                        System.out.println("¡Agarraste un arma!");
                        System.out.println("Has obtenido:");
                        System.out.println(items[armaAleatoria]);
                        jugador.agregarItemAlInventario(items[armaAleatoria]);
                    } else{
                        System.out.println("El enemigo ha obtenido:");
                        System.out.println(items[armaAleatoriaEnemigo]);
                        enemigos[enemigoActual].agregarItemAlInventario(items[armaAleatoriaEnemigo]);
                    }
                    armaTaken = true;
                }
            }
            if(armaduraTaken == false){
                if(juego.AgarrarArmadura() == true){
                    if(armaduraAleatoria == 4 || armaduraAleatoria == 5){
                        //mostrar la info y ponerle automatico la armadura al jugador
                        if(juego.uid.matriz[juego.uid.indexiArmadura][juego.uid.indexjArmadura] == 6){
                            System.out.println("¡Agarraste una Armadura!");
                            System.out.println("Has obtenido: ");
                            System.out.println(items[armaduraAleatoria]);
                            jugador.armadura.setEfecto(items[armaduraAleatoria].getEfecto());
                            System.out.println("Nueva armadura equipada:" + items[armaduraAleatoria].getNombre());
                            if(items[armaduraAleatoria].getNombre().equalsIgnoreCase("Armadura Secreta")){
                                armaduraSecreta = true;
                            }
                            if(items[armaduraAleatoria].getNombre().equalsIgnoreCase("Armadura Legendaria")){
                                armaduraLegendaria = true;
                            }
                        } else {
                            //Poner el automatico la armadura al enemigo
                            enemigos[enemigoActual].armadura.setEfecto(items[armaduraAleatoria].getEfecto());
                            System.out.println("El enemigo ha equipado" + items[armaduraAleatoria].getNombre());
                            if(items[armaduraAleatoria].getNombre().equalsIgnoreCase("Armadura Legendaria")){
                                enemigoArmaduraLegendaria = true;
                            }
                        }
                        armaduraTaken = true;
                    }
                }
            }

            //Incialización del comprobante para ver si el jugador esta en el area del enemigo
            if(juego.uid.areaEnemigo() == true){
                gano = false;
                System.out.println("Hay combate");
            }
        }
    }
}