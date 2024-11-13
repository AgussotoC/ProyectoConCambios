import java.util.Random;
import java.util.Scanner;
public class UID{
    Scanner scanner = new Scanner(System.in);
    Random rand = new Random();
    Agentes agentes;
    Items[] items;
    Inventario inventario;
    int indexiItems;
    int indexjItems;
    int indexiArmas;
    int indexjArmas;
    int indexiDebuff;
    int indexjDebuff;
    int indexiArmadura;
    int indexjArmadura;

    int[] indexiEnemigos;
    int[] indexjEnemigos;

    int[][] matriz;
    int num = rand.nextInt(8, 17); //Tamaño de la matriz
    Agentes[] enemigos;
    boolean esPosible = false;
    int mChiquita = num -1;
    int range = ((num - 2) * 4) - 4; //area dentro de las paredes es disminuye en 2 bloques, y la puerta puede estar en las 4 paredes, esto en otras pal es el perímetro
    int rng = rand.nextInt(1, range + 1);
    /*
    Vacio: 0
    Paredes: 1
    Enemigos: 2
    Items: 3
    Armas: 4
    Armaduras: 5
    Jugador: 6
    */
    
    private void decidirNumEnemigos(){
        int prob = rand.nextInt(1,101);
        int max = 75;
        int numEnemigos = 0;
        for(int i = 0; i < 4; i++){
            if(prob <= max){
                numEnemigos += 1;
                switch (max){
                    case 75:
                        max = 25; break;
                    case 25:
                        max = 10; break;
                    case 10:
                        max = 2; break;
                }
            } else{
                break;
            }
        }
        enemigos = new Agentes[numEnemigos];
        indexjEnemigos =  new int[numEnemigos];
        indexiEnemigos = new int[numEnemigos];
    }
    int[] spawnEnemigos = null; //ver cuantas entidades de enemigos se crean
    public UID(){
        Items armaduraI = new Items("hola", 0, " ");
        Items armaI = new Items("hola", 0, " ");
        decidirNumEnemigos();
        if(enemigos.length == 0){
            enemigos = null;
        } else{
            spawnEnemigos = new int[enemigos.length];
            for(int i = 0; i < enemigos.length; i ++){
                int vidaAleatoria = rand.nextInt(100,300);
                int ataqueAleatorio = rand.nextInt(50,125);
                int defensaAleatoria = rand.nextInt(50,70);
                enemigos[i] = new Agentes(2, vidaAleatoria, ataqueAleatorio, defensaAleatoria, armaI , armaduraI, 0);
                spawnEnemigos[i] = enemigos[i].getIcono();
            }
        }

    }
    private void generarEntidades(int[] entidades){
        for(int k = 0; k < entidades.length; k++)
        {
            esPosible = false;
            range = (num - 2) * (num - 2);
            rng = rand.nextInt(1 , range + 1);
            for(int i = 0; i < mChiquita; i++)
            {
                for(int j =  0; j < mChiquita; j++)
                {
                    if(matriz[i][j] != 0)
                    {
                        range -= 1;
                    } else if(rng == 1)
                    {
                        matriz[i][j] = entidades[k];
                        esPosible = true;
                        break;
                    } else
                    {
                        range -= 1;
                        if(range < 1){
                            range = 2;
                        }
                        rng = rand.nextInt(1, range + 1);
                    }
                }
                if(esPosible)
                {
                    break;
                }
            }
        }
        esPosible = false;
        range = (num - 2) * (num - 2);
        rng = rand.nextInt(1 , range + 1);
    }
    private void generarEnemigos(int[] enemigos){
        int contador = 0;
        if(enemigos != null){
            for(int k = 0; k < enemigos.length; k++)
            {
                esPosible = false;
                range = (num - 2) * (num - 2);
                rng = rand.nextInt(1 , range + 1);
                for(int i = 0; i < mChiquita; i++)
                {
                    for(int j =  0; j < mChiquita; j++)
                    {
                        if(matriz[i][j] != 0)
                        {
                            range -= 1;
                        } else if(rng == 1)
                        {
                            matriz[i][j] = enemigos[k];
                            indexiEnemigos[contador] = i;
                            indexjEnemigos[contador] = j;
                            contador++;
                            esPosible = true;
                            break;
                        } else
                        {
                            range -= 1;
                            if(range < 1){
                                range = 2;
                            }
                            rng = rand.nextInt(1, range + 1);
                        }
                    }
                    if(esPosible)
                    {
                        break;
                    }
                }
            }
            esPosible = false;
            range = (num - 2) * (num - 2);
            rng = rand.nextInt(1 , range + 1);
        }
    }

    //Metodo para generar aleatoriamente la dungeon
    public void generarAreaMatriz(int cuarto){
        matriz = new int[num][num];
        //Definir las paredes de los costados
        for(int i = 0; i < num; i++){
            for(int j = 0; j < num; j++){
                if(i == 0 || i == num - 1){
                    matriz[i][j] = 1;
                } else if(j == 0 || j == num - 1){
                    matriz[i][j] = 1;
                } else{
                    matriz[i][j] = 0;
                }
            }
        }
        //Generar entidades
        int[] entidades = {6, 4, 3, 5};
        generarEntidades(entidades);
        generarEnemigos(spawnEnemigos);
        //Generar puertas
        if(cuarto == 1){
            generarParedesIniciales();
        } else{
            //Crear metodo para generar habitacion que no sea la primera, la que depende de nodos
        }
    }

    //metodo para crear las puertas de la primera habitacion
    public void generarParedesIniciales(){
        int generacion = 1; //probailidad de 100%, se mide con 1/5
        int genMaximo = 5;
        int maxPared = 5;
        int paredRandom = rand.nextInt(1,5);
        for(int k = 0; k < 4; k++){
            if(generacion <= genMaximo){
                switch (paredRandom){
                    case 1:
                        matriz[0][num/2] = 0; break;
                    case 2:
                        matriz[num -1][num/2] = 0; break;
                    case 3:
                        matriz[num/2][0] = 0; break;
                    case 4:
                        matriz[num/2][num - 1] = 0; break;
                }
                switch(genMaximo){
                    case 5:
                        genMaximo = 3; break;
                    case 3:
                        genMaximo = 2; break;
                    case 2:
                        genMaximo = 1; break;
                }
                generacion = rand.nextInt(1, 6);
                paredRandom = rand.nextInt(1,5);
            } else{
                break;
            }
        }
    }

    public void generarParedes(Nodo actual){
        int generacion = 1; //probailidad de 100%, se mide con 1/5
        int genMaximo = 5;
        int maxPared = 5;
        int paredRandom = 1;
        for(int k = 0; k < 4; k++){
            if(generacion <= genMaximo){
                for(int i = 0; i < num; i++){
                    for(int j = 0; j < num; j++){
                        if(paredRandom == rand.nextInt(1, maxPared)){
                            if(i == 0 || i == num - 1){
                                matriz[i][num/2] = 0;
                            } else if(j == 0 || j == num - 1){
                                matriz[num/2][j] = 0;
                            } else{
                                maxPared -= 1;
                            }
                        }
                    }
                }
                switch(genMaximo){
                    case 5:
                        genMaximo -= 2; break;
                    case 3:
                        genMaximo -= 1; break;
                    case 2:
                        genMaximo -= 1; break;
                }
                generacion = rand.nextInt(1, 6);
            } else{
                break;
            }
        }
    }

    //metodo para saber las coordenadas de los items que se pueden agarrar
    public void encontrarCoordenadasEntidades(){
        char[] entidadesAgarrables = {4 , 3, 5};
        for(int k = 0; k < entidadesAgarrables.length; k++){
            for(int i = 0; i < num; i++){
                for(int j = 0; j < num; j++){
                    if(matriz[i][j] == entidadesAgarrables[k]){
                        switch (k){
                            case 0:
                                indexiArmas = i;
                                indexjArmas = j;
                                break;
                            case 1:
                                indexiItems = i;
                                indexjItems = j;
                                break;
                            case 2:
                                indexiDebuff = i;
                                indexjDebuff = j;
                                break;
                            case 3:
                                indexiArmadura = i;
                                indexjArmadura = j;
                        }
                    }
                }
            }
        }
    }

    //Metodo para imprimir la matriz en la consola
    public void imprimirMatriz(){
        for(int i = 0; i < num; i++){
            for(int j = 0; j < num; j++){
                switch(matriz[i][j]){
                    case 0:
                        System.out.print(" "); break;
                    case 1:
                        System.out.print("#"); break;
                    case 2:
                        System.out.print("E"); break;
                    case 3:
                        System.out.print("I"); break;
                    case 4:
                        System.out.print("A"); break;
                    case 5:
                        System.out.print("R"); break;
                    case 6:
                        System.out.print("@"); break;
                }
            }
            System.out.println();
        }
    }

    //comprobante que si la entidad puede estar ahí (revisar si hay pared por ejemplo)
    public boolean sePuede(int espacio){
        boolean sePuede = true;
        if(espacio == 3 || espacio == 4 || espacio == 5){
            sePuede = true;
        } else if(espacio != 0){
            sePuede = false;
        }
        return sePuede;
    }

    //comprobante diferente para el jugador, ya que este puede entrar a la puerta
    public boolean sePuedeJugador(int espacio){
        boolean sePuede = true;
        if(espacio == 3 || espacio == 4 || espacio == 5){
            sePuede = true;
        } else if(espacio != 0){
            sePuede = false;
        }
        return sePuede;
    }

    //metodo para mover el personaje
    public void moverPersonaje(String wasd, Agentes agente){
        int indexi = 0;
        int indexj = 0;
        for(int i = 0; i < num; i++){
            for(int j = 0; j < num; j++){
                if(matriz[i][j] == agente.getIcono()){
                    indexi = i;
                    indexj = j;
                }
            }
        }
        if(agente.getIcono() == 6){
            switch(wasd){
                case "w":
                    if(sePuedeJugador(matriz[indexi -1][indexj]) == true){
                        matriz[indexi][indexj] = 0;
                        matriz[indexi - 1][indexj] = agente.getIcono();
                    }
                    break;
                case "a":
                    if(sePuedeJugador(matriz[indexi][indexj - 1]) == true){
                        matriz[indexi][indexj] = 0;
                        matriz[indexi][indexj - 1] = agente.getIcono();
                    }
                    break;
                case "s":
                    if(sePuedeJugador(matriz[indexi + 1][indexj]) == true){
                        matriz[indexi][indexj] = 0;
                        matriz[indexi + 1][indexj] = agente.getIcono();
                    }
                    break;
                case "d":
                    if(sePuedeJugador(matriz[indexi][indexj + 1]) == true){
                        matriz[indexi][indexj] = 0;
                        matriz[indexi][indexj + 1] = agente.getIcono();
                    }
                    break;
                default:
                    System.out.println("Invalido intente de nuevo");
                    break;
            }
        }
        if(enemigos != null){
            for (Agentes enemigo : enemigos) {
                if(enemigo == null){
                    continue;
                }//mmm
                String[] moverEnemigo = {"w", "a", "s", "d"};
                String moverE = moverEnemigo[rand.nextInt(moverEnemigo.length)];
                int contador = 0;
                switch (moverE) {
                    case "w":
                        if (sePuede(matriz[indexiEnemigos[contador] - 1][indexjEnemigos[contador]]) == true) {
                            matriz[indexiEnemigos[contador]][indexjEnemigos[contador]] = 0;
                            matriz[indexiEnemigos[contador] - 1][indexjEnemigos[contador]] = enemigo.getIcono();
                        }
                        break;
                    case "a":
                        if (sePuede(matriz[indexiEnemigos[contador]][indexjEnemigos[contador] - 1]) == true) {
                            matriz[indexiEnemigos[contador]][indexjEnemigos[contador]] = 0;
                            matriz[indexiEnemigos[contador]][indexjEnemigos[contador] - 1] = enemigo.getIcono();
                        }
                        break;
                    case "s":
                        if (sePuede(matriz[indexiEnemigos[contador] + 1][indexjEnemigos[contador]]) == true) {
                            matriz[indexiEnemigos[contador]][indexjEnemigos[contador]] = 0;
                            matriz[indexiEnemigos[contador] + 1][indexjEnemigos[contador]] = enemigo.getIcono();
                        }
                        break;
                    case "d":
                        if (sePuede(matriz[indexiEnemigos[contador]][indexjEnemigos[contador] + 1]) == true) {
                            matriz[indexiEnemigos[contador]][indexjEnemigos[contador]] = 0;
                            matriz[indexiEnemigos[contador]][indexjEnemigos[contador] + 1] = enemigo.getIcono();
                        }
                        break;
                }
            }
        }
    }

    //Comprobación si el jugador esta en el area de 1x1 del enemigo para iniciar combate
    public boolean areaEnemigo(Agentes jugador){
        boolean hayCombate = false;
        if(enemigos != null) {
            int contador = 0;
            int indexiJugador = 0;
            int indexjJugador = 0;
            //Encontrar al jugador
            for (int i = 0; i < num; i++) {
                for (int j = 0; j < num; j++) {
                    if (matriz[i][j] == 6) {
                        indexiJugador = i;
                        indexjJugador = j;
                    }
                }
            }
            //Revisar si el jugador esta en el area del enemigo
            for (Agentes enemigo : enemigos) {
                for (int i = indexiEnemigos[contador] - 1; i <= indexiEnemigos[contador] + 1; i++) {
                    for (int j = indexjEnemigos[contador] - 1; j <= indexjEnemigos[contador] + 1; j++) {
                        if (indexiJugador == i) {
                            if (indexjJugador == j) {
                                hayCombate = true;
                            }
                        }
                    }
                }
                contador++;
            }
        }
        return hayCombate;
    }

   /*public void sistemaDeBatalla(Agentes jugador, Agentes enemigo) {
        //Agregar Contador para los turnos; 1 jugador, 0 enemigo
        boolean pelea = true;
        boolean defensa = false;
        //Ver si el jugador o enemigo tiene equipada el Arma
        if (jugador.armadura.getNombre() == "Armadura Legendaria") {
            enemigo.setAtaque(enemigo.getAtaque() - (enemigo.getAtaque() * 0.50));
        }
        if(enemigo.armadura.getNombre() == "Armadura Legendaria"){
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
                enemigo.statusEnemigos(enemigo);
            }
            System.out.println("1) Atacar");
            System.out.println("2) Ver inventario");
            System.out.println("3) Defender");
            try {
                int opcion = scanner.nextInt();
                switch (opcion) {
                    //Atacar
                    case 1:
                        jugador.atacar(enemigo);
                        System.out.println("Has hecho " + jugador.getAtaque() + " de daño");
                        if (enemigo.getSalud() == 0) {
                            System.out.println("Has derrotado al enemigo!");
                            enemigo.setIcono(0);
                            enemigo = null;
                            for (int i = 0; i < num; i++) {
                                for (int j = 0; j < num; j++) {
                                    if (matriz[i][j] == 'E') {
                                        matriz[i][j] = ' ';
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
                                    enemigo.setBuff(jugador.inventario.objetos[seleccionItem].getEfecto());
                                    System.out.println(enemigo.getDefensa());
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
                        jugador.defender(enemigo.getAtaque());
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
            if (enemigo != null && defensa != true) {
                if (probalidad == 6 && enemigo.inventario.objetos != null) {
                    for (int i = 0; i < enemigo.inventario.objetos.length ; i++) {
                        if(enemigo.inventario.objetos[i] == null){
                            continue;
                        }
                        if(enemigo.inventario.objetos[i].getNombre().equalsIgnoreCase("Reducir defensa")){
                            jugador.setBuff(enemigo.inventario.objetos[i].getEfecto());
                            System.out.println("El enemigo ha equipado: " + enemigo.inventario.objetos[i].getNombre());
                        }else{
                            enemigo.setAtaque(enemigo.inventario.objetos[i].getEfecto());
                            System.out.println("El enemigo ha equipado: " + enemigo.inventario.objetos[i].getNombre());
                        }
                    }
                }
                //Ataque del enemigo
                enemigo.atacar(jugador);
                System.out.println("Has recibido " + enemigo.getAtaque() + " de daño");
                if (jugador.getSalud() == 0) {
                    System.out.println("Te han derrotado");
                    pelea = false;
                }
                //Sistema de veneno en combate
                if (enemigo.getSalud() != 0) {
                    if (enemigo.getDebuff() == true) {
                        enemigo.venenoAtaque(dañoVeneno);
                        System.out.println("El veneno le hizo un daño adicional al enemigo de: " + dañoVeneno);
                        if (enemigo.getSalud() == 0) {
                            enemigo = null;
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

    }*/
}
