import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class UID{
    Scanner scanner = new Scanner(System.in);
    Random rand = new Random();
    Agentes agentes;
    Inventario inventario;
    boolean veneno;
    //Coordenadas de cada entidad (exceptuando al jugador)
    int indexiItems;
    int indexjItems;
    int indexiArmas;
    int indexjArmas;
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
    Agentes Boss = new Agentes(7, 600, 100, 300, null, null, null, null);
    public UID(int numCuarto, String wasd, boolean hayBoss, boolean haySalida){
        Armaduras armaduraI = new Armaduras("Sin armadura","Defensa Base");
        Armas armaI = new Armas("Sin arma", "Ataque base");
        Items buffI = new Items("Sin buff",  "Sin afecto");
        Items debuffI = new Items("Sin debuff",  "Sin afecto");
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
        generarAreaMatriz(numCuarto, wasd, hayBoss, haySalida);
        encontrarCoordenadasEntidades();
        if(enemigos != null){
            encontrarCoordenadasEnemigos(enemigos);
        }
        if (Boss != null) {
            encontrarCordenadasBoss();
        }
    }
    /*public UID(String wasd){
    System.out.println(" Has entrado a la Habitacion de Boss, Ten cuidado!");
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
    matriz[num/2][num/2] = 7; //Se crea al Boss en el centro
    switch(wasd){ //Reubica al jugador en la entrada del nuevo cuarto
    case "w":
    reubicarJugador(num-1, num/2); break;
    case "s":
    reubicarJugador(0, num/2); break;
    case "d":
    reubicarJugador(num/2, 0); break;
    case "a":
    reubicarJugador(num/2, num-1); break;
    }
    }*/
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
    public  int[][] generarAreaMatriz(int numCuarto, String wasd, boolean hayBoss, boolean haySalida){
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
        if(hayBoss){ //Es el cuarto del boss
            generarHBoss(wasd);
        } else if(haySalida){ //es el cuarto de la salida
            
        } else{ //Es un cuarto normal
            int[] entidades = {6, 4, 3, 5};
            generarEntidades(entidades);
            generarEnemigos(spawnEnemigos);
            //Generar puertas
            if(numCuarto == 1){
                generarParedesIniciales();
            } else { //Metodo para generar habitacion que no sea la primera
                generarParedes(wasd);
            }
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
        boolean condicion = true;
        int contador = 0;
        int generacion = 1; //probailidad de 100%, se mide con 1/5
        int genMaximo = 5;
        boolean yaHayPared = false;
        int paredRandom;
        int[] paredHechas = {0 , 0, 0, 0};
        switch(wasd){ //Asegurar que siempre haya una puerta con la habitacion anterior
            case "w": //Se crea una puerta abajo
            paredRandom = 2;
            paredHechas[0] = 2; break;
            case "s": //Se crea una puerte arriba
            paredRandom = 1;
            paredHechas[0] = 1; break;
            case "d": //Se crea una puerta a la derecha
            paredRandom = 3;
            paredHechas[0] = 3; break;
            case "a": //Se crea una puerta a la izquierda
            paredRandom = 4;
            paredHechas[0] = 4; break;
            default:
            paredRandom = rand.nextInt(1,5);
        }
        for(int k = 0; k < 4; k++){
            contador++;
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
                    genMaximo = 5; break; //60% de probabilidad
                    case 3:
                    genMaximo = 2; break; //40% de probabilidad
                    case 2:
                    genMaximo = 1; break;//20% de probabilidad
                }
                generacion = rand.nextInt(1, 6);
                paredRandom = rand.nextInt(1,5);
                while (condicion){
                    if(k == 3){
                        condicion = false;
                    }
                    else if(paredRandom == paredHechas[0] || paredRandom == paredHechas[1] ||
                    paredRandom == paredHechas[2] || paredRandom == paredHechas[3]){
                        paredRandom = rand.nextInt(1,5);
                    } else{
                        paredHechas[contador] = paredRandom;
                        condicion = false;
                    }
                }
                condicion = true;
            } else{
                break;
            }
        }
        switch(wasd){ //Reubica al jugador en la entrada del nuevo cuarto
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
    
    private void generarHBoss(String wasd){
        switch(wasd){ //Reubica al jugador en la entrada del nuevo cuarto
            case "w":
            reubicarJugador(num-1, num/2); break;
            case "s":
            reubicarJugador(0, num/2); break;
            case "d":
            reubicarJugador(num/2, 0); break;
            case "a":
            reubicarJugador(num/2, num-1); break;
        }
        matriz[num/2][num/2] = 7; //Se crea al Boss en el centro
        if(matriz[0][0] == 0){
            matriz[0][0] = 1;
        }
    }
    
    private void generarSalida(){
        
    }
    
    public void encontrarCordenadasBoss(){
        for(int i = 0; i < num; i++){
            for(int j = 0; j < num; j++){
                if(matriz[i][j] == 7){
                    Boss.indexi = i;
                    Boss.indexj = j;
                }
            }
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
    public void encontrarCoordenadasEnemigos(Agentes[] enemigos){
        int contadorEnemigos = 0;
        for(int i = 0; i < num; i++){
            for(int j = 0; j < num; j++){
                if(matriz[i][j] == 2){
                    if(contadorEnemigos < enemigos.length){
                        enemigos[contadorEnemigos].setIndexI(i); 
                        enemigos[contadorEnemigos].setIndexJ(j);
                        indexiEnemigos[contadorEnemigos] = i;
                        indexjEnemigos[contadorEnemigos] = j;
                        contadorEnemigos ++;
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
        for(int i = 0; i < num; i++){
            for(int j = 0; j < num; j++){
                switch(matriz[i][j]){
                    case 0: //espacio en blanco
                    System.out.print(" "); break;
                    case 1: //print de las paredes
                    System.out.print("#");
                    if(j == num -1){ // Print de la vizualizacion de los atributos
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
                    System.out.print("E"); break; //Enemigo
                    case 3:
                    System.out.print("I"); break; //Item
                    case 4:
                    System.out.print("A"); break; //Arma
                    case 5:
                    System.out.print("R"); break; //Armadura
                    case 6:
                    System.out.print("@"); break; //Jugador
                    case 7:
                    System.out.print("B"); break; //Boss
                    case 8:
                    System.out.print("S"); break; //Salida
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
            }
        }
        if(enemigos != null){
            //Falta probalidad de moverse hacia jugador!
            for (Agentes enemigo : enemigos) {
                if(enemigo == null){
                    continue;
                }
                String[] moverEnemigo = {"w", "a", "s", "d"};
                String moverE = moverEnemigo[rand.nextInt(moverEnemigo.length)];
                int indexiEnemigo = enemigo.getIndexI();
                int indexjEnemigo = enemigo.getIndexJ();
                switch (moverE) {
                    case "w":
                    if (indexiEnemigo > 0 && sePuede(matriz[indexiEnemigo - 1][indexjEnemigo]) == true) {
                        matriz[indexiEnemigo][indexjEnemigo] = 0;
                        matriz[indexiEnemigo - 1][indexjEnemigo] = enemigo.getIcono();
                        enemigo.setIndexI(indexiEnemigo - 1);
                    }
                    break;
                    case "a":
                    if (indexjEnemigo > 0 && sePuede(matriz[indexiEnemigo][indexjEnemigo - 1])) {
                        matriz[indexiEnemigo][indexjEnemigo] = 0;
                        matriz[indexiEnemigo][indexjEnemigo - 1] = enemigo.getIcono();
                        enemigo.setIndexJ(indexjEnemigo - 1);
                    }
                    break;
                    case "s":
                    if (indexiEnemigo < num - 1 && sePuede(matriz[indexiEnemigo + 1][indexjEnemigo])) {
                        matriz[indexiEnemigo][indexjEnemigo] = 0;
                        matriz[indexiEnemigo + 1][indexjEnemigo] = enemigo.getIcono();
                        enemigo.setIndexI(indexiEnemigo + 1);
                    }
                    break;
                    case "d":
                    if (indexjEnemigo < num - 1 && sePuede(matriz[indexiEnemigo][indexjEnemigo + 1])) {
                        matriz[indexiEnemigo][indexjEnemigo] = 0;
                        matriz[indexiEnemigo][indexjEnemigo + 1] = enemigo.getIcono();
                        enemigo.setIndexJ(indexjEnemigo + 1);
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
            for (int i = 0; i< enemigos.length; i ++) {
                if(enemigos[i] == null){
                    continue;
                }
                for(int j = enemigos[i].indexi -1 ; j <= enemigos[i].indexi +1 ; j++){
                    for(int k = enemigos[i].indexj -1 ; k <= enemigos[i].indexj +1 ; k++){
                        if (indexiJugador == j && indexjJugador == k) {
                            hayCombate = true;
                            sistemaDeBatalla(jugador, i,false); // Inicia combate con el enemigo cercano
                            return true;
                        }
                    }
                }
            }
        }
        return hayCombate;
    }
    public boolean areaBoss(Agentes jugador){
        boolean hayCombate = false;
        if(Boss != null){
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
            for(int j = Boss.indexi -1 ; j <= Boss.indexi +1 ; j++){
                for(int k = Boss.indexj -1 ; k <= Boss.indexj +1 ; k++){
                    if (indexiJugador == j && indexjJugador == k) {
                        hayCombate = true;
                        sistemaDeBatalla(jugador, 0,true); // Inicia combate con el enemigo cercano
                        return true;
                    }
                }
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
                if(enemigo == null){
                    continue;
                }
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
        armaduras[2] = new Armaduras("Armadura legendaria", "-50% daño recibido");
        
        //Items
        double aumentoAtaque = rand.nextInt(10,21);
        //Buffs
        items[0] = new Items("Mancuerna","Aumenta el daño entre 10-20%");
        items[1] = new Items("Mascarilla","Aumenta la defensa en un 15%");
        items[2] = new Items("Sangre","El 20% de tu daño se te añade a la vida");
        //Debuffs
        items[3] = new Items("Quebrar","Reduce la defensa del enemigo en un 15%");
        items[4] = new Items("Veneno", "Quita vida en cada turno hasta 10 vida, empieza en 1");
        items[5] = new Items("Reduccion","Reduce el daño del enemigo en un 20%");
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
    public void utilizarItemJugador(Agentes jugador ,int seleccionItem, int i){
        double probalidad = rand.nextDouble(0.10,0.20);
        int Default = 0;
        if(jugador.inventario.objetos[seleccionItem].getNombre().equalsIgnoreCase("Mancuerna")){
            jugador.aplicarItems(jugador, jugador.inventario.objetos[seleccionItem].getNombre(), probalidad);
            jugador.inventario.objetos[seleccionItem] = null;
        }else if(jugador.inventario.objetos[seleccionItem].getNombre().equalsIgnoreCase("Mascarilla")){
            jugador.aplicarItems(jugador, jugador.inventario.objetos[seleccionItem].getNombre(), Default);
            jugador.inventario.objetos[seleccionItem] = null;
        }else if(jugador.inventario.objetos[seleccionItem].getNombre().equalsIgnoreCase("Sangre")){
            jugador.aplicarItems(jugador, jugador.inventario.objetos[seleccionItem].getNombre(), Default);
            jugador.inventario.objetos[seleccionItem] = null;
        }else if(jugador.inventario.objetos[seleccionItem].getNombre().equalsIgnoreCase("Quebrar")){
            jugador.aplicarItems(enemigos[i], jugador.inventario.objetos[seleccionItem].getNombre(), Default);
            jugador.inventario.objetos[seleccionItem] = null;
        }else if(jugador.inventario.objetos[seleccionItem].getNombre().equalsIgnoreCase("Veneno")){
            jugador.aplicarItems(enemigos[i], jugador.inventario.objetos[seleccionItem].getNombre(), Default);
            veneno = true;
            jugador.inventario.objetos[seleccionItem] = null;
        }else if(jugador.inventario.objetos[seleccionItem].getNombre().equalsIgnoreCase("Reduccion")){
            jugador.aplicarItems(enemigos[i], jugador.inventario.objetos[seleccionItem].getNombre(), Default);
            jugador.inventario.objetos[seleccionItem] = null;
        }
    }
    public void utilizarItemEnemigo(int i, Agentes jugador){
        int indiceInventarioE = rand.nextInt(enemigos[i].inventario.objetos.length);
        double probalidad = rand.nextDouble(0.10,0.20);
        int Default = 0;
        for (int b = 0; b < enemigos[i].inventario.objetos.length ; b++) {
            if(enemigos[i].inventario.objetos[b] == null){
                continue;
            }
            if(enemigos[i].inventario.objetos[indiceInventarioE].getNombre().equalsIgnoreCase("Mancuerna")){
                enemigos[i].aplicarItems(enemigos[i], enemigos[i].inventario.objetos[indiceInventarioE].getNombre(), probalidad);
                enemigos[i].inventario.objetos[indiceInventarioE] = null;
            }else if(enemigos[i].inventario.objetos[indiceInventarioE].getNombre().equalsIgnoreCase("Mascarilla")){
                enemigos[i].aplicarItems(enemigos[i], enemigos[i].inventario.objetos[indiceInventarioE].getNombre(), Default);
                enemigos[i].inventario.objetos[indiceInventarioE] = null;
            }else if(enemigos[i].inventario.objetos[indiceInventarioE].getNombre().equalsIgnoreCase("Sangre")){
                enemigos[i].aplicarItems(enemigos[i], enemigos[i].inventario.objetos[indiceInventarioE].getNombre(), Default);
                enemigos[i].inventario.objetos[indiceInventarioE] = null;
            }else if(enemigos[i].inventario.objetos[indiceInventarioE].getNombre().equalsIgnoreCase("Quebrar")){
                enemigos[i].aplicarItems(jugador, enemigos[i].inventario.objetos[indiceInventarioE].getNombre(), probalidad);
                enemigos[i].inventario.objetos[indiceInventarioE] = null;
            }else if(enemigos[i].inventario.objetos[indiceInventarioE].getNombre().equalsIgnoreCase("Veneno")){
                enemigos[i].aplicarItems(jugador, enemigos[i].inventario.objetos[indiceInventarioE].getNombre(), Default);
                enemigos[i].inventario.objetos[indiceInventarioE] = null;
            }else if(enemigos[i].inventario.objetos[indiceInventarioE].getNombre().equalsIgnoreCase("Reduccion")){
                enemigos[i].aplicarItems(jugador, enemigos[i].inventario.objetos[indiceInventarioE].getNombre(), Default);
                enemigos[i].inventario.objetos[indiceInventarioE] = null;
            }
            
        }
    }
    public void sistemaDeBatalla(Agentes jugador, int i, boolean boss){
        System.out.println("Hay combate");
        boolean pelea = true;
        boolean defensa = false;
        int probalidadSoltarItemA;
        //Ver si el jugador o enemigo tiene equipada el Arma
        while (pelea) {
            if(boss == false){
                //probabilidad que el enemigo pueda soltar un item 
                probalidadSoltarItemA = rand.nextInt(0,100);
                //interfaz de batalla
                System.out.println("----Status----");
                if(jugador != null){
                    jugador.statusJugador(jugador);
                }
                if(enemigos[i] != null){
                    enemigos[i].statusEnemigos(enemigos[i]);
                }
                System.out.println("1) Atacar");
                System.out.println("2) Ver inventario");
                System.out.println("3) Defender");
                try {
                    int opcion = scanner.nextInt();
                    switch (opcion) {
                        //Atacar
                        case 1:
                            jugador.atacar(enemigos[i]);
                            System.out.println("Has hecho " + jugador.getAtaque() + " de daño");
                            if (enemigos[i].getSalud() == 0) {
                                System.out.println("Has derrotado al enemigo!");
                                enemigos[i].setIcono(0);
                                /*if (enemigos[i].inventario.objetos == null && probalidadSoltarItemA > 15){
                                matriz[enemigos[i].indexi][enemigos[i].indexj] = 3;
                                }
                                if(enemigos[i].inventario.objetos != null){
                                matriz[enemigos[i].indexi][enemigos[i].indexj] = enemigos[i].inventario.objetos[indiceInventarioE].getIconoItem();
                                }else{
                                matriz[enemigos[i].indexi][enemigos[i].indexj] = 0;
                                }*/
                                matriz[enemigos[i].indexi][enemigos[i].indexj] = 0;
                                enemigos[i] = null;
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
                                    utilizarItemJugador(jugador, seleccionItem, i);
                                }
                            }
                        break;
                        //defender
                        case 3:
                            System.out.println("Te has defendido en un 25% del daño ");
                            jugador.defender(enemigos[i].getAtaque());
                            defensa = true;
                            break;
                            default:
                            System.out.println("ingrese una opcion valida");
                            break;
                    }
                 //Fin del switch
                } catch(InputMismatchException opcion) {
                    //Sacado de internet, el try catch no se repetía con el loop
                    System.out.printf("%s no es un numero, pierde el turno.%n", scanner.next());
                }
                //Acciones del enemigo
                if (enemigos[i] != null && defensa != true) {
                    if (enemigos[i].getSalud() <= enemigos[i].getSalud()/2) {
                       utilizarItemEnemigo(i, jugador);
                    }
                    //Ataque del enemigo
                    enemigos[i].atacar(jugador);
                    System.out.println("Has recibido " + enemigos[i].getAtaque() + " de daño");
                    if (jugador.getSalud() == 0) {
                        System.out.println("Te han derrotado");
                        pelea = false;
                    }
                }
            }else{
                int probalidadDeAcciones = rand.nextInt(100) +1 ;
                System.out.println("----Status----");
                if(jugador != null){
                    jugador.statusJugador(jugador);
                }
                if(Boss != null){
                    Boss.statusBoss(Boss);
                }
                System.out.println("1) Atacar");
                System.out.println("2) Ver inventario");
                System.out.println("3) Defender");
                int opcion = scanner.nextInt();
                switch (opcion) {
                    //Atacar
                    case 1:
                    jugador.atacarABoss(Boss);
                    System.out.println("Has hecho " + jugador.getAtaque() + " de daño");
                    if (Boss.getSalud() == 0) {
                        System.out.println("Has derrotado al Boss!");
                        Boss.setIcono(0);// caracter para la llave
                        /*if (enemigos[i].inventario.objetos == null && probalidadSoltarItemA > 15){
                        matriz[enemigos[i].indexi][enemigos[i].indexj] = 3;
                        }
                        if(enemigos[i].inventario.objetos != null){
                        matriz[enemigos[i].indexi][enemigos[i].indexj] = enemigos[i].inventario.objetos[indiceInventarioE].getIconoItem();
                        }else{
                        matriz[enemigos[i].indexi][enemigos[i].indexj] = 0;
                        }*/
                        matriz[Boss.indexi][Boss.indexj] = 0;
                        Boss = null;
                        pelea = false;
                    }
                    break;
                    case 2:
                    //ver inventario
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
                            utilizarItemJugador(jugador, seleccionItem, i);
                        }
                    }
                        break;
                    //defender
                    case 3:
                        System.out.println("Te has defendido en un 25% del daño ");
                        jugador.defender(Boss.getAtaque());
                        defensa = true;
                        break;
                        default:
                        System.out.println("ingrese una opcion valida");
                        break;
                }
                //Acciones del Boss
                if(Boss != null){
                    Boss.AccionesBoss(Boss, jugador, probalidadDeAcciones);
                    if (jugador.getSalud() == 0) {
                        System.out.println("Te han derrotado");
                        pelea = false;
                    }
                }
            }
        }
    }
}
