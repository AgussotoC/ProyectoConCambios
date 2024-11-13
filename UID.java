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
    */
<<<<<<< HEAD
    
=======

    //Datos para spawnear entidades
    boolean esPosible = false;
    int mChiquita = num -1; //Area dentro de las paredes
    int range = ((num - 2) * 4) - 4; //area dentro de las paredes es (disminuye en 2 bloques)
    int rng = rand.nextInt(1, range + 1);

    Agentes[] enemigos;
    int[] spawnEnemigos = null; //ver cuantas entidades de enemigos se crean
    public UID(){
        Armaduras armaduraI = new Armaduras("Sin armadura","Defensa Base");
        Armas armaI = new Armas("Sin arma", "Ataque base");
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
>>>>>>> 741517ed750950c334b0093e45f4720ff80c959a
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

    public void encontrarEquipable(Agentes jugador){
        //Jugador encontró arma
        if(jugador.getIcono() == matriz[indexiArmas][indexjArmas]){
            armas[0] = new Armas("Arma basica", "Aumenta el daño en un 20%");
            armas[1] = new Armas("Arma secreta", "Hace 50% al enemigo más el ataque base");
            armas[2] = new Armas("Arma legendaria", "Aumenta el daño en 100%");
            Armas armaObtenida = armas[rand.nextInt(3)];
            System.out.println("Has obtenido: \n" + armaObtenida);
            jugador.setArma(armaObtenida);
        }
        //Jugador encontró armadura
        else if(jugador.getIcono() == matriz[indexiArmadura][indexjArmadura]){
            armaduras[0] = new Armaduras("Arma basica", "Aumenta el daño en un 20%");
            armaduras[1] = new Armaduras("Arma secreta", "Hace 50% al enemigo más el ataque base");
            armaduras[2] = new Armaduras("Arma legendaria", "Aumenta el daño en 100%");
            Armaduras armaObtenida = armaduras[rand.nextInt(3)];
            System.out.println("Has obtenido: \n" + armaObtenida);
            jugador.setArmadura(armaObtenida);
        }
        //Jugador encontró item
        else if (jugador.getIcono() == matriz[indexiItems][indexjItems]){
            double aumentoAtaquee = rand.nextInt(10,21);
            items[0] = new Items("Mancuerna", aumentoAtaquee,"Aumenta el daño entre 10-20%");
            items[1] = new Items("Mascarilla",15 ,"Aumenta la defensa en un 15%");
            items[2] = new Items("Sangre", 20, "El 20% de tu daño se te añade a la vida");
            items[3] = new Items("Quebrar", 15, "Reduce la defensa del enemigo en un 15%");
            items[4] = new Items("Veneno", 1, "Quita vida en cada turno hasta 10 vida, empieza en 1");
            items[5] = new Items("Reduccion",20, "Reduce el daño del enemigo en un 20%");
            Items itemObtenido = items[rand.nextInt(6)];
            System.out.println("Has obtenido: \n" + itemObtenido);
            jugador.agregarItemAlInventario(itemObtenido);
        }
        if(enemigos != null){
            for(Agentes enemigo : enemigos){
                int contador = 0;
                //enemigo encontró arma
                if(enemigo.getIcono() == matriz[indexiArmas][indexjArmas]){
                    armas[0] = new Armas("Arma basica", "Aumenta el daño en un 20%");
                    armas[1] = new Armas("Arma secreta", "Hace 50% al enemigo más el ataque base");
                    armas[2] = new Armas("Arma legendaria", "Aumenta el daño en 100%");
                    Armas armaObtenida = armas[rand.nextInt(4)];
                    System.out.println("El enemigo ha obtenido: \n" + armaObtenida);
                    enemigo.setArma(armaObtenida);
                }
                //enemigo encontró armadura
                else if(enemigo.getIcono() == matriz[indexiArmadura][indexjArmadura]){
                    armaduras[0] = new Armaduras("Arma basica", "Aumenta el daño en un 20%");
                    armaduras[1] = new Armaduras("Arma secreta", "Hace 50% al enemigo más el ataque base");
                    armaduras[2] = new Armaduras("Arma legendaria", "Aumenta el daño en 100%");
                    Armaduras armaObtenida = armaduras[rand.nextInt(4)];
                    System.out.println("El enemigo ha obtenido: \n" + armaObtenida);
                    enemigo.setArmadura(armaObtenida);
                }
                //enemigo encontró item
                else if (enemigo.getIcono() == matriz[indexiItems][indexjItems]){
                    double aumentoAtaquee = rand.nextInt(10,21);
                    items[0] = new Items("Mancuerna", aumentoAtaquee,"Aumenta el daño entre 10-20%");
                    items[1] = new Items("Mascarilla",15 ,"Aumenta la defensa en un 15%");
                    items[2] = new Items("Sangre", 20, "El 20% de tu daño se te añade a la vida");
                    items[3] = new Items("Quebrar", 15, "Reduce la defensa del enemigo en un 15%");
                    items[4] = new Items("Veneno", 1, "Quita vida en cada turno hasta 10 vida, empieza en 1");
                    items[5] = new Items("Reduccion",20, "Reduce el daño del enemigo en un 20%");
                    Items itemObtenido = items[rand.nextInt(4)];
                    System.out.println("El enemigo ha obtenido: \n" + itemObtenido);
                    enemigo.agregarItemAlInventario(itemObtenido);
                }
                contador += 1;
            }
        }
    }

    /*private Items atributosItem(Agentes actual){
        Items[] items = new Items[15];
        for(int i = 0; i< items.length; i++){
            //Creacion de los items, variable porcentaje para las pociones y sus buffos random
            double min = 0.05;
            double max = 0.10;
            double porcentajes = random.nextDouble(min,max) + 0.01;
            items[0] = new Items("Ninguno", 0,"Nada");
            //Creacion de las armas
            items[1] = new Items("Arma Basica", actual.setAtaque(actual.getAtaque() * 1.20), "Ataque normal");
            items[2] = new Items("Arma Secreta",(enemigo.getSalud()/2 + jugador.getAtaque()),"Reduce en un 50% la vida actual del enemigo actual");
            //Creacion de las armaduras
            items[4] = new Items("Armadura Secreta", 200, "Se regenera despues de cada partida");
            items[5] = new Items("Armadura Legendaria", 200, "Reduce un 50% del ataque recibido");
            //creacion de los Items
            items[6] = new Items("Aumentar Ataque", (Math.round(jugador.getAtaque() + (jugador.getAtaque() * porcentajes))),"Aumenta el ataque en un rango de 5 a 10%");
            items[7] = new Items("Reducir Defensa",(enemigo.getDefensa() - enemigo.getDefensa() * 0.15),"Recuce la defensa del enemigo en un 15%");
            // creacion de las armas del enemigo
            items[8] = new Items("Arma Secreta",(jugador.getSalud()/2 + enemigos.getAtaque()),"Reduce en un 50% la vida actual del enemigo actual");
            items[9] = new Items("Arma Basica", enemigos.getAtaque(), "Ataque normal");
            items[10] = new Items("Arma Legendaria", (enemigos.getAtaque()*2),"Realiza el doble de daño que el ataque normal");
            //creacion de los Items del enemigo
            items[11] = new Items("Aumentar Ataque", (Math.round(enemigos.getAtaque() + (enemigos.getAtaque() * porcentajes))),"Aumenta el ataque en un rango de 5 a 10%");
            items[12] = new Items("Reducir Defensa",(jugador.getDefensa() - jugador.getDefensa() * 0.15),"Recuce la defensa del enemigo en un 15%");
        }
    }*/

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
