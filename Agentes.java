public class Agentes
{
    //Atributos de la clase
    //Características únicas del personaje
    private int icono;
    private double salud;
    private double ataque;
    private double defensa;
    boolean llave;

    /*Características compartidas y modificables por toda entidad
      también ocupa la creaciones de clases de items para funcionar, lo cual lo ideal sería hacerlo en el main*/

    public Armas arma;
    public Armaduras armadura;
    public Items buff;
    public Items debuff;
    public Inventario inventario;
    public int indexi;
    public int indexj;

    //Constructor del jugador y enemigos
    public Agentes(int icono, double salud, double ataque, double defensa, Armas arma, Armaduras armadura, Items buff, Items debuff){
        this.icono = icono;
        this.salud = salud;
        this.ataque = ataque;
        this.defensa = defensa;
        this.arma = arma;
        this.armadura = armadura;
        this.buff = buff;
        this.debuff = debuff;
        this.inventario = new Inventario(3);
        this.llave = false;
        this.indexi = 0;
        this.indexj = 0;
    } 
    public void setIndexI(int i){
        indexi = i;
    }
    public void setIndexJ(int j){
        indexj = j;
    }
    public int getIndexJ(){
       return  indexj;
    }
    public int getIndexI(){
        return  indexi;
     }
    
    public void agregarItemAlInventario(Items item){
        inventario.agregarItem(item);
    }
    public void mostrarInventario(){
        inventario.mostrarInventario(); 
    }
    public void setLlave(int opcion){
        if(opcion == 1 ){
            this.llave = true;
        }
    }
    public boolean getLlave(){
        return llave;
    }

    //Getters características únicas
    public double getSalud(){
        return salud;
    }
    public double getAtaque(){
        return ataque;
    }
    public double getDefensa(){
        return defensa;
    }

    public String getArmaduraNombre(){
        return armadura.getNombre();
    }
    public String getArmaduraDesc(){
        return armadura.getDescripcion();
    }

    public int getIcono(){
        return icono;
    }

    //Setters
    public void setSalud(double efecto){
        this.salud = efecto;
    }
    public void setAtaque(double efecto){
        this.ataque = efecto;
    }
    public void setArmadura(Armaduras armaduraNueva){
        this.armadura = armaduraNueva;
    }
    public void setArma(Armas armaNueva){
        this.arma = armaNueva;
    }
    public void setBuff(double efecto){
        this.defensa = efecto;
    }
    public void setDebuff(Items debuff){
        this.debuff = debuff;
    }
    public void setArmaduraEfecto(){

    }
    public void setIcono(int ingreso){
        this.icono = ingreso;
    }
    //Metodo que recibe el daño y lo reduce a la salud
    public double recibirDaño(double daño){
        if(armadura.getVida() == 0){
            salud -= daño;
            if(salud < 0){
                setSalud(0);
            }
        } else{
            switch (armadura.getNombre()){
                case "Armadura basica":
                    daño -= daño * 0.20;
                    armadura.setVida(armadura.vida - 2);
                    break;
                case "Armadura secreta":
                    daño -= daño * 0.30;
                    armadura.setVida(armadura.vida - 2);
                    break;
                case "Armadura legendaria":
                    daño -= daño * 0.50;
                    break;
            }
            if(armadura.getVida() > 0){
                salud -= daño;
                if(armadura.getVida() <= 0){
                    armadura.setVida(0);
                }
            }
        }
        return salud;
    }
    public double recibirDañoBoss(double daño){
        if(defensa > 0){
            defensa -= daño;
            if(defensa < 0){
                defensa = 0;
            }
        }
        if(defensa == 0){
            salud -= daño;
            if(salud < 0){
                setSalud(0);
            }
        
        }
        return salud;
    }
    public void atacarABoss(Agentes Boss){
        double daño;
        switch (arma.getNombre()){
            case "Arma basica":
                daño = ataque + ataque * 0.20;
                break;
            case "Arma secreta":
                daño = Boss.getSalud()/2 + ataque;
                break;
            case "Arma legendaria":
                daño = ataque * 2;
                break;
            default:
                daño = ataque;
        }
        Boss.recibirDañoBoss(daño);
        if(Boss.getSalud() < 0){
            Boss.setSalud(0);
        }
    }

    public void AccionesBoss(Agentes Boss,Agentes objetivo, int probalidad){
        if(Boss.salud >= 300 && probalidad >= 60){
            System.out.println("El Boss te ataco");
            double daño;
            daño = Boss.ataque;
            objetivo.recibirDaño(daño);
            if(objetivo.getSalud() < 0){
                objetivo.setSalud(0);
            }
        }else if(Boss.salud >= 300 && probalidad <= 40){
            System.out.println("El Boss se defendio");
            defenderBoss(objetivo.ataque);
            if(Boss.defensa > 0){
                Boss.defensa += objetivo.ataque;
            }else if (Boss.salud > 0 && Boss.defensa <= 0){
                Boss.salud += objetivo.ataque;
            }
        }else if (Boss.salud < 300 && probalidad <= 40) {
            System.out.println("El Boss te ataco");
            double daño;
            daño = Boss.ataque;
            objetivo.recibirDaño(daño);
            if(objetivo.getSalud() < 0){
                objetivo.setSalud(0);
            }
        }else if (Boss.salud < 300 && probalidad >= 60) {
            System.out.println("El Boss se defendio");
            defenderBoss(objetivo.ataque);
            if(Boss.defensa <= 0){
                Boss.defensa += objetivo.ataque;
            }else{
                Boss.salud += objetivo.ataque;
            }
        }
    }
    //Metodo de ataque
    public double  atacar(Agentes objetivo){
        double daño;
        switch (arma.getNombre()){
            case "Arma basica":
                daño = ataque * 0.20;
                break;
            case "Arma secreta":
                daño = objetivo.getSalud()/2 + ataque;
                break;
            case "Arma legendaria":
                daño = ataque * 2;
                break;
            default:
                daño = ataque;
        }
        objetivo.recibirDaño(daño);
        if(objetivo.getSalud() < 0){
            objetivo.setSalud(0);
        }
        return objetivo.getSalud();
    }
    public void aplicarItems(Agentes objetivo, String nombreItem, double probalidad){
        switch (nombreItem){
            //aplicables a uno mismo
            case "Mancuerna":
                objetivo.ataque += objetivo.ataque * probalidad;
                System.out.println(nombreItem + " Se aplico correctamente ");
                break;
            case "Mascarilla":
                objetivo.defensa += objetivo.defensa * 0.15;
                System.out.println(nombreItem + " Se aplico correctamente ");
                break;
            case "Sangre":
                objetivo.salud += objetivo.ataque * 20;
                System.out.println(nombreItem + " Se aplico correctamente ");
                break;
            // aplicables al oponente
            case "Quebrar":
                objetivo.defensa -= objetivo.defensa * 0.15;
                System.out.println(nombreItem + " Se aplico correctamente ");
                break;
            case "Veneno":
                objetivo.salud -= probalidad;
                System.out.println(nombreItem + " Se aplico correctamente ");
                break;
            case "Reduccion":
                objetivo.ataque -= objetivo.ataque * 0.20;
                System.out.println(nombreItem + " Se aplico correctamente ");
                break;

        }
    }

    //Metodo de defensa, defender bloquea 25% del ataque recibido
    public void defender(double daño){
        recibirDaño(daño - (daño*0.75));
    }
    public void defenderBoss(double daño){
        recibirDañoBoss(daño - (daño*0.75));
    }

    public void venenoAtaque(double daño){
        double comprobante = getSalud() - daño;
        if(comprobante < 0){
            setSalud(0);
        } else{
            setSalud(comprobante);
        }

    }
    public void mostrarAtributos(Agentes jugador){
        System.out.println("Jugador: " + " Vida: " + jugador.getSalud() + " Arma: " + jugador.arma  + " Ataque: " + jugador.getAtaque() +  " Defensa: " + jugador.getDefensa() + " Armadura: " + jugador.armadura.getNombre() + " Llave " + jugador.getLlave());
    }
    public void statusJugador(Agentes jugador){
        System.out.println("Jugador: "+ " Vida: " + jugador.getSalud() + " Defensa: " + jugador.getDefensa() + "Armadura: " + jugador.armadura.getNombre());
    }
    public void statusEnemigos(Agentes enemigos){
        System.out.println("Enemigo: " + " Vida: " + enemigos.getSalud() + " Defensa: " + enemigos.getDefensa() + " Efecto de Armadura: " + enemigos.arma.getNombre());
    }
    public void statusBoss(Agentes Boss){
        System.out.println("Boss: " + " Vida: " + Boss.getSalud() + " Defensa: " + Boss.getDefensa());
    }
}