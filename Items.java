public class Items{

    String nombre;
    String descripcion;
    int icono;
    public Items(String nombre, String descripcion){
        this.nombre = nombre;
        this.descripcion = descripcion;
        icono = 3;
    }
    public String getNombre(){
        return nombre;
    }
    public String getDescripcion(){
        return descripcion;
    }
    public int getIconoItem(){
        return icono;
    }
    public String toString(){
        return nombre + ": " + descripcion;
    }
}