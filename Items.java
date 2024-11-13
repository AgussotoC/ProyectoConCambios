public class Items
{
    String nombre;
    double efecto;
    String descripcion;
    public Items(String nombre, double efecto, String descripcion){
        this.nombre = nombre;
        this.efecto = efecto;
        this.descripcion = descripcion;
    
    }
    public String getNombre(){
        return nombre;
    }
    public double getEfecto(){
        return efecto;
    }
    public String getDescripcion(){
        return descripcion;
    }
    public void setEfecto(double ingreso){
        this.efecto = ingreso;

    }
    public String toString(){
        return nombre + " descripcion: " + descripcion;
    }
}