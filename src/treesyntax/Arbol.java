/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treesyntax;

import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author Visitante
 */
    public class Arbol {
    public Nodo raiz;
    
    public Arbol(){
        raiz=null;
    }

    public Nodo getRaiz() {
        return raiz;
    }

    public void setRaiz(Nodo raiz) {
        this.raiz = raiz;
    }
    
    boolean isOperator(char c) { 
        if (c == '+' || c == '.'
                || c == '*' || c == '|'
                || c == '-') { 
            return true; 
        } 
        return false; 
    }
    
    public ArrayList<Nodo> getPreordenNodos(Nodo raiz){
        return getPreordenN_(raiz,new ArrayList<Nodo>());
    }
    private ArrayList<Nodo> getPreordenN_(Nodo padre,ArrayList<Nodo> ar){
        if(padre==null)
            return null;
        ar.add(padre);
        if(!padre.estaIzquierdoVacio())
            getPreordenN_(padre.getHijo_izq(),ar);
        if(!padre.estaDerechoVacio())
            getPreordenN_(padre.getHijo_der(),ar);
        return ar;
    }
   

    Nodo MakeTree(char[] postfix){
        Stack<Nodo> st = new Stack();
        Nodo t, t1, t2; 
  
        // Traverse through every character of 
        // input expression 
        for (int i = 0; i < postfix.length; i++) { 
  
            // If operand, simply push into stack 
            if (!isOperator(postfix[i])) { 
                t = new Nodo(postfix[i]); 
                st.push(t); 
            } else // operator 
            { 
                t = new Nodo(postfix[i]); 
                if (postfix[i] == '|' || postfix[i] == '.') {
                    t1 = st.pop();
                    t.hijo_der = t1;
                    t2 = st.pop();
                    t.hijo_izq = t2;
                }else{
                    t1 = st.pop();
                    t.hijo_izq = t1;
                }
               
                st.push(t); 
            } 
        } 
  
        //  only element will be root of expression 
        // tree 
        t = st.peek(); 
        st.pop(); 
  
        return t; 
    }
}






