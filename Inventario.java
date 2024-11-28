public class Inventario {
    private int Capacidad;
    private int cantidad;
    public Items[] objetos;

    //Constructor de Items para el inventario
    public Inventario(int Capacidad){ 
        this.Capacidad = Capacidad;
        this.objetos = new Items[Capacidad];
        this.cantidad = 0;
    }
    //Agrega al inventario
    public void agregarItem(Items item){
            if(cantidad < Capacidad){
                objetos[cantidad] = item;
                cantidad++;
            }else{
                System.out.println("El inventario esta lleno");
            }
    }
    // imprime el inventario
    public void mostrarInventario(){
        System.out.println("Items:");
        for(int i = 0; i < cantidad; i++){
            if(objetos[i] != null)
                System.out.println((i+1) + ":" + objetos[i]);
        }
    }
}