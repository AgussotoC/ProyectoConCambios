public class Arbol {
    Nodo raiz;

    public Arbol(){
        raiz = null;
    }
    public void insertar(int numCuarto, Object[][] matriz){
        raiz = insertarRecursivo(raiz, numCuarto, matriz);
    }
    private Nodo insertarRecursivo(Nodo raiz,int numCuarto, Object[][] matriz){
        if(raiz == null){
            raiz = new Nodo(numCuarto ,matriz);
            return raiz;
        }
        if (numCuarto < raiz.numCuarto) {
            raiz.izquierdo = insertarRecursivo(raiz, numCuarto, matriz);
        }else if(numCuarto > raiz.numCuarto){
            raiz.derecho = insertarRecursivo(raiz, numCuarto, matriz);
        }
        return raiz;
    }
    public void recorridoPreOrden(Nodo nodo){
        if (nodo == null) {
            return;
        }
        System.out.println(nodo.matriz + "|");
        recorridoPreOrden(nodo.izquierdo);
        recorridoPreOrden(nodo.derecho);
    }
}
