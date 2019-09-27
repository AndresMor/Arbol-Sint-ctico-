/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treesyntax;

/**
 *
 * @author Visitante
 */
public class Nodo {
    public char valor;
    public Nodo hijo_izq, hijo_der;

    public Nodo(char valor) {
        this.valor = valor;
        this.hijo_izq = null;
        this.hijo_der = null;
    }
    
    public char getValor() {
        return valor;
    }

    public void setValor(char valor) {
        this.valor = valor;
    }

    public Nodo getHijo_izq() {
        return hijo_izq;
    }

    public void setHijo_izq(Nodo hijo_izq) {
        this.hijo_izq = hijo_izq;
    }

    public Nodo getHijo_der() {
        return hijo_der;
    }

    public void setHijo_der(Nodo hijo_der) {
        this.hijo_der = hijo_der;
    }
    
    public boolean estaIzquierdoVacio(){
        return this.hijo_izq==null;
    }
    
    public boolean estaDerechoVacio(){
        return this.hijo_der==null;
    }
    
    public int NodosCompletos(Nodo n){
        if (n==null) {
            return 0;
        }else{
            if (n.hijo_izq != null && n.hijo_der != null) {
                return NodosCompletos(n.hijo_izq) + NodosCompletos(n.hijo_der) + 1;
            }
            return NodosCompletos(n.hijo_izq) + NodosCompletos(n.hijo_der) ;
        }
    }
    
}


