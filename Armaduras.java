public class Armaduras {
    String nombre;
    double vida;;
    String descripcion;
    public Armaduras(String nombre, String descripcion) {
        this.nombre = nombre;
        this.vida = 100;
        this.descripcion = descripcion;
    }
    //getters
    public String getNombre() {
        return nombre;
    }
    public double getVida() {
        return vida;
    }
    public String getDescripcion() {
        return descripcion;
    }

    //Setters
    public void setVida(double vida) {
        this.vida = vida;
    }
    public String toString(){
        return nombre + " descripcion: " + descripcion;
    }
}
