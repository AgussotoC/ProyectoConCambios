public class Armas {
    String nombre;
    String descripcion;
    public Armas(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
    public String getNombre() {
        return nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public String toString(){
        return nombre + " descripcion: " + descripcion;
    }
}
