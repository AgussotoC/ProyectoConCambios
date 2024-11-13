public class Arbol {
    Nodo raiz;

    public Arbol(){
        raiz = null;
    }
    public void insertar(int cuarto, Object dato){
        raiz = insertarRecursivo(raiz, cuarto, dato);
    }
    private Nodo insertarRecursivo(Nodo raiz,int cuarto, Object dato){
        if(raiz == null){
            raiz = new Nodo(cuarto ,dato);
            return raiz;
        }
        if (cuarto < raiz.cuarto) {
            raiz.izquierdo = insertarRecursivo(raiz, cuarto, dato);
        }else if(cuarto > raiz.cuarto){
            raiz.derecho = insertarRecursivo(raiz, cuarto, dato);
        }
        return raiz;
    }
}
