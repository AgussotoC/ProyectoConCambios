import java.util.Random;
import java.util.Scanner;
public class UID{
    Scanner scanner = new Scanner(System.in);
    Random rand = new Random();
    Agentes agentes;
    Inventario inventario;
    //Coordenadas de cada entidad (exceptuando al jugador)
    int indexiItems;
    int indexjItems;
    int indexiArmas;
    int indexjArmas;
    int indexiDebuff;
    int indexjDebuff;
    int indexiArmadura;
    int indexjArmadura;
    //Coordenadas de los enemigos
    int[] indexiEnemigos;
    int[] indexjEnemigos;
    //Creacion de objteos
    Armas[] armas = new Armas[3];
    Armaduras[] armaduras = new Armaduras[3];
    Items[] items = new Items[6];
    Lista lista = new Lista();
    //Creacion de la matriz y su tamaño
    int[][] matriz;
    int num = rand.nextInt(8, 17); //Tamaño de la matriz
    /* Forma de leer la matriz de enteros:
    Vacio: 0
    Paredes: 1
    Enemigos: 2
    Items: 3
    Armas: 4
    Armaduras: 5
    Jugador: 6
    boss 7
    salida 8
    */
    //Datos para spawnear entidades
    boolean esPosible = false;
    int mChiquita = num -1; //Area dentro de las paredes
    int range = ((num - 2) * 4) - 4; //area dentro de las paredes es (disminuye en 2 bloques)
    int rng = rand.nextInt(1, range + 1);

    Agentes[] enemigos;
    int[] spawnEnemigos = null; //ver cuantas entidades de enemigos se crean
    public UID(int numCuarto, String wasd){
        Armaduras armaduraI = new Armaduras("Sin armadura","Defensa Base");
        Armas armaI = new Armas("Sin arma", "Ataque base");
        Items buffI = new Items("Sin buff", 1, "Sin afecto");
        Items debuffI = new Items("Sin debuff", 1, "Sin afecto");
        decidirNumEnemigos();
        if(enemigos.length == 0){
            enemigos = null;
        } else{
            spawnEnemigos = new int[enemigos.length];
            for(int i = 0; i < enemigos.length; i ++){
                int vidaAleatoria = rand.nextInt(100,300);
                int ataqueAleatorio = rand.nextInt(50,125);
                int defensaAleatoria = rand.nextInt(50,70);
                enemigos[i] = new Agentes(2, vidaAleatoria, ataqueAleatorio, defensaAleatoria, armaI , armaduraI, buffI, debuffI);
                spawnEnemigos[i] = enemigos[i].getIcono();
            }
        }
        generacionItems();
        generarAreaMatriz(numCuarto, wasd);
        encontrarCoordenadasEntidades();
    }
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
    public  int[][] generarAreaMatriz(int numCuarto, String wasd){
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
        if(numCuarto == 1){
            generarParedesIniciales();
        } else { //Metodo para generar habitacion que no sea la primera
            generarParedes(wasd);
        }
        return matriz;
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

    public void generarParedes(String wasd){
        int contador = 0;
        int generacion = 1; //probailidad de 100%, se mide con 1/5
        int genMaximo = 5;
        boolean yaHayPared = false;
        int paredRandom;
        int[] yaSeHizoPared = {0 , 0, 0, 0};
        switch(wasd){ //Asegurar que siempre haya una puerta con la habitacion anterior
            case "w": //Se crea una puerta abajo
                paredRandom = 2;
                yaSeHizoPared[0] = 2; break;
            case "s": //Se crea una puerte arriba
                paredRandom = 1;
                yaSeHizoPared[0] = 1; break;
            case "d": //Se crea una puerta a la derecha
                paredRandom = 3;
                yaSeHizoPared[0] = 3; break;
            case "a": //Se crea una puerta a la izquierda
                paredRandom = 4;
                yaSeHizoPared[0] = 4; break;
            default:
                paredRandom = rand.nextInt(1,5);
        }
        for(int k = 0; k < 4; k++){
            if(generacion <= genMaximo){
                switch (paredRandom){
                    case 1://Pared de arrriba
                        matriz[0][num/2] = 0; break;
                    case 2: //Pared de abajo
                        matriz[num -1][num/2] = 0; break;
                    case 3: //Pared de la izquierda
                        matriz[num/2][0] = 0; break;
                    case 4: //Pared de la derecha
                        matriz[num/2][num - 1] = 0; break;
                }
                switch(genMaximo){
                    case 5:
                        genMaximo = 3; break; //60% de probabilidad
                    case 3:
                        genMaximo = 2; break; //40% de probabilidad
                    case 2:
                        genMaximo = 1; break;//20% de probabilidad
                }
                generacion = rand.nextInt(1, 6);
                paredRandom = rand.nextInt(1,5);
            } else{
                break;
            }
        }
        switch(wasd){ //Asegurar que siempre haya una puerta con la habitacion anterior
            case "w":
                reubicarJugador(num-1, num/2); break;
            case "s":
                reubicarJugador(0, num/2); break;
            case "d":
                reubicarJugador(num/2, 0); break;
            case "a":
                reubicarJugador(num/2, num-1); break;
        }
    }

    public void reubicarJugador(int coordenadaI, int coordenadaJ){
        int indexi = 0;
        int indexj = 0;
        for(int i = 0; i < num; i++){
            for(int j = 0; j < num; j++){
                if(matriz[i][j] == 6){
                    indexi = i;
                    indexj = j;
                }
            }
        }
        matriz[indexi][indexj] = 0;
        matriz[coordenadaI][coordenadaJ] = 6;
    }

    //metodo para saber las coordenadas de los items que se pueden agarrar
    public void encontrarCoordenadasEntidades(){
        char[] entidadesAgarrables = {4, 3, 5};
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
                                indexiArmadura = i;
                                indexjArmadura = j;
                                break;
                        }
                    }
                }
            }
        }
    }

    //Metodo para imprimir la matriz en la consola
    public void imprimirMatriz(Agentes jugador){
        String[] itemsLogo = {"A", "B", "C"};
        String[] itemsImprimidos = {"-", "-", "-"};
        int contador = 0;
        for(String item : itemsLogo){
            if(jugador.inventario.objetos[contador] != null){
                itemsImprimidos[contador] = item;
            }
            contador++;
        }
        int ultimaFila = num-1;
        for(int i = 0; i < num; i++){
            for(int j = 0; j < num; j++){
                switch(matriz[i][j]){
                    case 0:
                        System.out.print(" "); break;
                    case 1:
                        System.out.print("#");
                        if(j == num -1){
                            switch (i){
                                case 0:
                                    System.out.printf(" Items...[%s][%s][%s]", itemsImprimidos[0], itemsImprimidos[1], itemsImprimidos[2]);
                                    break;
                                case 1:
                                    System.out.printf(" Vida...%d", (int)jugador.getSalud());
                                    break;
                                case 2:
                                    System.out.printf(" Armadura...%s", jugador.armadura.descripcion); break;
                                case 3:
                                    System.out.printf(" Arma...%s", jugador.arma.descripcion); break;
                            }
                            if(i == num-3){
                                System.out.printf(" Ataque...%d", (int)jugador.getAtaque());
                            }
                            if(i == num-2){
                                System.out.printf(" Buff...%s", jugador.buff.nombre);
                            }
                            if(i == num-1){
                                System.out.printf(" Debuff...%s", jugador.debuff.nombre);
                            }
                        }
                        break;
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
    public void moverPersonaje(String wasdm, Agentes agente){
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
            switch(wasdm){
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
                case "m":
                    System.out.println("1) Mapa");
                    System.out.println("2) inventario");
                    int opcion = scanner.nextInt();
                    if (opcion == 1) {
                        System.out.println("it works");
                        lista.imprimirMapa(lista.gethabitacionActual());
                    }else if (opcion == 2) {
                        System.out.println("inventario");
                        agente.mostrarInventario();
                    }
                    break;
                default:
                    System.out.println("Invalido intente de nuevo");
                    break;
            }
        }
        /*if(enemigos != null){
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
        }*/
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

    public void encontrarEquipable(Agentes jugador){
        //Jugador encontró arma
        if(jugador.getIcono() == matriz[indexiArmas][indexjArmas]){
            Armas armaObtenida = armas[rand.nextInt(3)];
            System.out.println("Has obtenido: \n" + armaObtenida);
            jugador.setArma(armaObtenida);
        }
        //Jugador encontró armadura
        else if(jugador.getIcono() == matriz[indexiArmadura][indexjArmadura]){
            Armaduras armaObtenida = armaduras[rand.nextInt(3)];
            System.out.println("Has obtenido: \n" + armaObtenida);
            jugador.setArmadura(armaObtenida);
        }
        //Jugador encontró item
        else if (jugador.getIcono() == matriz[indexiItems][indexjItems]){
            Items itemObtenido = items[rand.nextInt(6)];
            System.out.println("Has obtenido: \n" + itemObtenido);
            jugador.agregarItemAlInventario(itemObtenido);
        }
        if(enemigos != null){
            for(Agentes enemigo : enemigos){
                int contador = 0;
                //enemigo encontró arma
                if(enemigo.getIcono() == matriz[indexiArmas][indexjArmas]){
                    Armas armaObtenida = armas[rand.nextInt(4)];
                    System.out.println("El enemigo ha obtenido: \n" + armaObtenida);
                    enemigo.setArma(armaObtenida);
                }
                //enemigo encontró armadura
                else if(enemigo.getIcono() == matriz[indexiArmadura][indexjArmadura]){
                    Armaduras armaObtenida = armaduras[rand.nextInt(4)];
                    System.out.println("El enemigo ha obtenido: \n" + armaObtenida);
                    enemigo.setArmadura(armaObtenida);
                }
                //enemigo encontró item
                else if (enemigo.getIcono() == matriz[indexiItems][indexjItems]){
                    Items itemObtenido = items[rand.nextInt(4)];
                    System.out.println("El enemigo ha obtenido: \n" + itemObtenido);
                    enemigo.agregarItemAlInventario(itemObtenido);
                }
                contador += 1;
            }
        }
    }

    private void generacionItems(){
        //armas
        armas[0] = new Armas("Arma basica", "+20% de daño");
        armas[1] = new Armas("Arma secreta", "ataque base +50% de la vida del enemigo");
        armas[2] = new Armas("Arma legendaria", "+100% de daño");

        //Armaduras
        armaduras[0] = new Armaduras("Armadura basica", "-20% daño recibido, -2% duracion por golpe");
        armaduras[1] = new Armaduras("Armadura secreta", "-30% daño recibido, duracion al 100% despues de cada batalla");
        armaduras[2] = new Armaduras("Armasura legendaria", "-50% daño recibido");

        //Items
        double aumentoAtaque = rand.nextInt(10,21);
        //Buffs
        items[0] = new Items("Mancuerna", aumentoAtaque,"Aumenta el daño entre 10-20%");
        items[1] = new Items("Mascarilla",15 ,"Aumenta la defensa en un 15%");
        items[2] = new Items("Sangre", 20, "El 20% de tu daño se te añade a la vida");
        //Debuffs
        items[3] = new Items("Quebrar", 15, "Reduce la defensa del enemigo en un 15%");
        items[4] = new Items("Veneno", 1, "Quita vida en cada turno hasta 10 vida, empieza en 1");
        items[5] = new Items("Reduccion",20, "Reduce el daño del enemigo en un 20%");
    }

    public void asignarJugadorAPared(String wasd){
        for(int i = 0; i < num; i++){
            for(int j = 0; j < num; j++){
                if(matriz[i][j] == 6){
                    matriz[i][j] = 0;
                }
            }
        }
        switch (wasd){
            case "w":
                matriz[num-1][num/2] = 6; break;
            case "a":
                matriz[num/2][num-1] = 6; break;
            case "s":
                matriz[0][num/2] = 6; break;
            case "d":
                matriz[num/2][0] = 6; break;
        }
    }

    public void reasignarPosicionJugador(String wasd){
        for(int i = 0; i < num; i++){
            for(int j = 0; j < num; j++){
                if(matriz[i][j] == 6){
                    matriz[i][j] = 0;
                }
            }
        }
        switch (wasd){
            case "w":
                matriz[num-1][num/2] = 6; break;
            case "a":
                matriz[num/2][num-1] = 6; break;
            case "s":
                matriz[0][num/2] = 6; break;
            case "d":
                matriz[num/2][0] = 6; break;
        }
    }

    /*public void sistemaDeBatalla(Agentes jugador, Agentes enemigo) {
        //Agregar Contador para los turnos; 1 jugador, 0 enemigo
        boolean pelea = true;
        boolean defensa = false;
        //Ver si el jugador o enemigo tiene equipada el Arma
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
                                System.out.println("Ingrese el numero de Item");
                                int seleccionItem = scanner.nextInt() - 1;
                                if (jugador.inventario.objetos[seleccionItem].getNombre().equalsIgnoreCase("Reducir defensa")) {
                                    enemigo.setBuff(jugador.inventario.objetos[seleccionItem].getEfecto());
                                    System.out.println(enemigo.getDefensa());
                                    System.out.println("Se ha usado exitosamente");
                                    jugador.inventario.objetos[seleccionItem] = null;
                                } else if (jugador.inventario.objetos[seleccionItem].getNombre().equalsIgnoreCase("Aumentar daño")) {
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
                
                /* 
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

    } */
}
