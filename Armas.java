public class Armas {
    String nombre;
    double dañoAdicional;
    double probabilidadDeSalir;
    String descripcion;
    public Armas(String nombre, double dañoAdicional, double probabilidadDeSalir, String descripcion) {
        this.nombre = nombre;
        this.dañoAdicional = dañoAdicional;
        this.probabilidadDeSalir = probabilidadDeSalir;
        this.descripcion = descripcion;
    }
    public String getNombre() {
        return nombre;
    }
    public double getDañoAdicional() {
        return dañoAdicional;
    }
    public double getProbabilidadDeSalir() {
        return probabilidadDeSalir;
    }
    public String getDescripcion() {
        return descripcion;
    }
}
