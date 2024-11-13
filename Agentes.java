public class Agentes
{
    //Atributos de la clase
    //Características únicas del personaje
    private int icono;
    private double salud;
    private double ataque;
    private double defensa;

    /*Características compartidas y modificables por toda entidad
      también ocupa la creaciones de clases de items para funcionar, lo cual lo ideal sería hacerlo en el main*/

    public Armas arma;
    public Armaduras armadura;
    public double buffs;
    private boolean debuff;
    public Inventario inventario;

    //Constructor del jugador y enemigos
    public Agentes(int icono, double salud, double ataque, double defensa, Armas arma, Armaduras armadura, double buffs){
        this.icono = icono;
        this.salud = salud;
        this.ataque = ataque;
        this.defensa = defensa;
        this.arma = arma;
        this.armadura = armadura;
        this.buffs = buffs;
        this.debuff = false;
        this.inventario = new Inventario(3);
    } 
    public void agregarItemAlInventario(Items item){
        inventario.agregarItem(item);
    }
    public void mostrarInventario(){
        inventario.mostrarInventario(); 
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
    public boolean getDebuff(){
        return debuff;
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
    public void setDebuff(boolean agarroVeneno){
        this.debuff = agarroVeneno;
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
                    daño = daño * 0.80;
                    armadura.setVida(armadura.getVida() - 2);
                    break;
                case "Armadura secreta":
                    daño = daño * 0.70;
                    armadura.setVida(armadura.getVida() - 2);
                    break;
                case "Armadura legendaria":
                    daño = daño * 0.50;
                    break;
            }
            if(armadura.getVida() > 0){
                salud = - daño;
                if(armadura.getVida() < 0){
                    armadura.setVida(0);
                }
            }
        }
        return salud;
    }

    //Metodo de ataque
    public double  atacar(Agentes objetivo){
        double daño;
        switch (arma.getNombre()){
            case "Arma basica":
                daño = ataque * 1.20;
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

    //Metodo de defensa, defender bloquea 25% del ataque recibido
    public void defender(double daño){
        recibirDaño(daño*0.75);
    }

    public void venenoAtaque(double daño){
        double comprobante = getSalud() - daño;
        if(comprobante < 0){
            setSalud(0);
        } else{
            setSalud(comprobante);
        }

    }
    public void statusJugador(Agentes jugador){
        System.out.println("Jugador: "+ " Vida: " + jugador.getSalud() + " Defensa: " + jugador.getDefensa() + "Armadura: " + jugador.armadura.getNombre());
    }
    public void statusEnemigos(Agentes enemigos){
        System.out.println("Enemigo: " + " Vida: " + enemigos.getSalud() + " Defensa: " + enemigos.getDefensa() + " Efecto de Armadura: " + enemigos.arma.getNombre());
    }
}