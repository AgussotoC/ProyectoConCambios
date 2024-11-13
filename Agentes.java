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
    public double getArmaduraEfecto(){
        return armadura.getReduccion();
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
            if(armadura.getVida() > 0){
                double armaduraRestante = getArmaduraEfecto() - daño;
                if(armaduraRestante < 0){
                    armadura.setVida(0);
                } else {
                    armadura.setVida(armaduraRestante);
                }
            }
        }
        return salud;
    }

    //Metodo de ataque
    public double  atacar(Agentes objetivo){
        double daño = ataque;
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
        System.out.println("Jugador: "+ " Vida: " + jugador.getSalud() + " Defensa: " + jugador.getDefensa() + " Efecto de Armadura: " + jugador.getArmaduraEfecto());
    }
    public void statusEnemigos(Agentes enemigos){
        System.out.println("Enemigo: " + " Vida: " + enemigos.getSalud() + " Defensa: " + enemigos.getDefensa() + " Efecto de Armadura: " + enemigos.getArmaduraEfecto());
    }
}