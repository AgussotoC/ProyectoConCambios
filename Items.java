public class Items{

    String nombre;
    String descripcion;
    public Items(String nombre, String descripcion){
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
    public String getNombre(){
        return nombre;
    }
    public String getDescripcion(){
        return descripcion;
    }
    public String toString(){
        return nombre + ": " + descripcion;
    }
}