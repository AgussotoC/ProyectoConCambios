public class Armaduras {
    String nombre;
    double vida;
    double reduccion;
    double probablidadDeSalir;
    String descripcion;
    public Armaduras(String nombre, double reduccion, double probablidadDeSalir, String descripcion) {
        this.nombre = nombre;
        this.vida = 100;
        this.reduccion = reduccion;
        this.probablidadDeSalir = probablidadDeSalir;
        this.descripcion = descripcion;
    }
    //getters
    public String getNombre() {
        return nombre;
    }
    public double getVida() {
        return vida;
    }
    public double getReduccion() {
        return reduccion;
    }
    public double getProbablidadDeSalir() {
        return probablidadDeSalir;
    }
    public String getDescripcion() {
        return descripcion;
    }

    //Setters
    public void setVida(double vida) {
        this.vida = vida;
    }
    public void setProbablidadDeSalir(double probablidadDeSalir) {
        this.probablidadDeSalir = probablidadDeSalir;
    }
}
