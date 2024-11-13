import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
public class Jugabilidad {
    Random rand = new Random();
    Scanner scanner = new Scanner(System.in);
    UID uid = new UID();
    int dañoVeneno = 1;
    public void sistemaDeBatalla(Agentes jugador, Agentes[] enemigo, int enemigoActual) {
        //Agregar Contador para los turnos; 1 jugador, 0 enemigo
        boolean pelea = true;
        boolean defensa = false;
        //Ver si el jugador o enemigo tiene equipada el Arma
        if (jugador.armadura.getNombre() == "Armadura Legendaria") {
            enemigo[enemigoActual].setAtaque(enemigo[enemigoActual].getAtaque() - (enemigo[enemigoActual].getAtaque() * 0.50));
        }
        if(enemigo[enemigoActual].armadura.getNombre() == "Armadura Legendaria"){
            jugador.setAtaque(jugador.getAtaque() - (jugador.getAtaque() * 0.50));
        }
        while (pelea) {
            //probabilidad que el enemigo pueda usar un item del inventario
            int probalidad = rand.nextInt(5,8);

            //interfaz de batalla
            System.out.println("----Status----");
            if(jugador != null){
                jugador.statusJugador(jugador);
            }
            if(enemigo != null){
                enemigo[enemigoActual].statusEnemigos(enemigo[enemigoActual]);
            }
            System.out.println("1) Atacar");
            System.out.println("2) Ver inventario");
            System.out.println("3) Defender");
            try {
                int opcion = scanner.nextInt();
                switch (opcion) {
                    //Atacar
                    case 1:
                        jugador.atacar(enemigo[enemigoActual]);
                        System.out.println("Has hecho " + jugador.getAtaque() + " de daño");
                        if (enemigo[enemigoActual].getSalud() == 0) {
                            System.out.println("Has derrotado al enemigo!");
                            enemigo[enemigoActual].setIcono(' ');
                            enemigo[enemigoActual] = null;
                            for (int i = 0; i < uid.num; i++) {
                                for (int j = 0; j < uid.num; j++) {
                                    if (uid.matriz[i][j] == 'E') {
                                        uid.matriz[i][j] = ' ';
                                    }
                                }
                            }
                            pelea = false;
                        }
                        break;

                    //ver inventario
                    case 2:
                        if (jugador.inventario.objetos[0] == null) {
                            System.out.println("No tienes objetos en el inventario");
                        } else {
                            jugador.mostrarInventario();
                            System.out.println("Desea equipar un objeto?");
                            System.out.println("1) Si");
                            System.out.println("2) No");
                            int op = scanner.nextInt();
                            if (op == 1) {
                                System.out.println("1 Armas");
                                System.out.println("2 buffs");
                                int opp = scanner.nextInt();
                                System.out.println("Ingrese el numero de Item");
                                int seleccionItem = scanner.nextInt() - 1;
                                if (opp == 1) {
                                    jugador.setAtaque(jugador.inventario.objetos[seleccionItem].getEfecto());
                                    System.out.println("Se ha equipado exitosamente");
                                    System.out.println("Ahora tu ataque es de: " + jugador.getAtaque());
                                    jugador.inventario.objetos[seleccionItem] = null;
                                } else if (opp == 2 && jugador.inventario.objetos[seleccionItem].getNombre().equalsIgnoreCase("Reducir defensa")) {
                                    enemigo[enemigoActual].setBuff(jugador.inventario.objetos[seleccionItem].getEfecto());
                                    System.out.println(enemigo[enemigoActual].getDefensa());
                                    System.out.println("Se ha usado exitosamente");
                                    jugador.inventario.objetos[seleccionItem] = null;
                                } else if (opp == 2 && jugador.inventario.objetos[seleccionItem].getNombre().equalsIgnoreCase("Aumentar daño")) {
                                    jugador.setAtaque(jugador.inventario.objetos[seleccionItem].getEfecto());
                                    System.out.println("Se ha equipado exitosamente");
                                    System.out.println("Ahora tu ataque es de: " + jugador.getAtaque());
                                    jugador.inventario.objetos[seleccionItem] = null;
                                }

                            }
                        }
                        break;

                    //defender
                    case 3:
                        System.out.println("Te has defendido en un 25% del daño ");
                        jugador.defender(enemigo[enemigoActual].getAtaque());
                        defensa = true;
                        break;
                    default:
                        System.out.println("ingrese una opcion valida");
                        break;

                } //Fin del switch
            } catch(InputMismatchException opcion) {
                //Sacado de internet, el try catch no se repetía con el loop
                System.out.printf("%s no es un numero, pierde el turno.%n", scanner.next());
            }
            //Acciones del enemigo
            if (enemigo[enemigoActual] != null && defensa != true) {
                if (probalidad == 6 && enemigo[enemigoActual].inventario.objetos != null) {
                    for (int i = 0; i < enemigo[enemigoActual].inventario.objetos.length ; i++) {
                        if(enemigo[enemigoActual].inventario.objetos[i] == null){
                            continue;
                        }
                        if(enemigo[enemigoActual].inventario.objetos[i].getNombre().equalsIgnoreCase("Reducir defensa")){
                            jugador.setBuff(enemigo[enemigoActual].inventario.objetos[i].getEfecto());
                            System.out.println("El enemigo ha equipado: " + enemigo[enemigoActual].inventario.objetos[i].getNombre());
                        }else{
                            enemigo[enemigoActual].setAtaque(enemigo[enemigoActual].inventario.objetos[i].getEfecto());
                            System.out.println("El enemigo ha equipado: " + enemigo[enemigoActual].inventario.objetos[i].getNombre());
                        }
                    }
                }
                //Ataque del enemigo
                enemigo[enemigoActual].atacar(jugador);
                System.out.println("Has recibido " + enemigo[enemigoActual].getAtaque() + " de daño");
                if (jugador.getSalud() == 0) {
                    System.out.println("Te han derrotado");
                    pelea = false;
                }
                //Sistema de veneno en combate
                if (enemigo[enemigoActual].getSalud() != 0) {
                    if (enemigo[enemigoActual].getDebuff() == true) {
                        enemigo[enemigoActual].venenoAtaque(dañoVeneno);
                        System.out.println("El veneno le hizo un daño adicional al enemigo de: " + dañoVeneno);
                        if (enemigo[enemigoActual].getSalud() == 0) {
                            enemigo[enemigoActual] = null;
                            pelea = false;
                        }
                        if (dañoVeneno < 10) {
                            dañoVeneno += 1;
                        }
                    }
                    }
                if (jugador.getSalud() != 0) {
                    if (jugador.getDebuff()) {
                        jugador.venenoAtaque(dañoVeneno);
                        System.out.println("El veneno te hizo un daño adicional de: " + dañoVeneno);
                        if (jugador.getSalud() == 0) {
                            System.out.println("Te han derrotado");
                            pelea = false;
                        }
                        if (dañoVeneno < 10) {
                            dañoVeneno += 1;
                        }
                    }
                }
            }
        }
        if (jugador.armadura.getNombre() == "Armadura Secreta") {
            jugador.armadura.setEfecto(200);
        }

    } 
    public void setDañoArmaSecreta(Agentes objetivo, Agentes propio){
        propio.setAtaque(objetivo.getSalud()/2 + propio.getAtaque());

    }
}
