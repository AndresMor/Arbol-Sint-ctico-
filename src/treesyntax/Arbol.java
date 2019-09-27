/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treesyntax;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 *
 * @author Visitante
 */
public class Arbol {

    public Nodo raiz;
    private Integer contHojas;

    public Arbol() {
        raiz = null;
        this.contHojas = 0;
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
                || c == '?') {
            return true;
        }
        return false;
    }

    public ArrayList<Nodo> getPreordenNodos(Nodo raiz) {
        return getPreordenN_(raiz, new ArrayList<Nodo>());
    }

    private ArrayList<Nodo> getPreordenN_(Nodo padre, ArrayList<Nodo> ar) {
        if (padre == null) {
            return null;
        }
        ar.add(padre);
        if (!padre.estaIzquierdoVacio()) {
            getPreordenN_(padre.getHijo_izq(), ar);
        }
        if (!padre.estaDerechoVacio()) {
            getPreordenN_(padre.getHijo_der(), ar);
        }
        return ar;
    }

    /*
    private void hacerAnulable(Nodo nodo, boolean operador){
        if(operador){
            
        }else{
            
        }
    }
     */
    private void hacerUltimaPos(Nodo nodo, boolean operador) {
        if (operador) {
            Set<Integer> c = null;
            Set<Integer> c2 = null;
            Set<Integer> union = null;
            if (nodo.getHijo_der() != null) {
                c2 = new HashSet<>(nodo.getHijo_der().getUltimaPos());
                union = new HashSet<>(nodo.getHijo_izq().getUltimaPos());
                union.addAll(c2);
            }
            switch (nodo.getValor()) {
                case '|':
                    nodo.setUltimaPos(union);
                    break;
                case '.':
                    if (nodo.getHijo_der().getAnulable()) {
                        nodo.setUltimaPos(union);
                    } else {
                        nodo.setUltimaPos(c2);
                    }
                    break;
                case '*':
                    c = new HashSet<>(nodo.getHijo_izq().getUltimaPos());
                    nodo.setUltimaPos(c);
                    break;
                case '+':
                    c = new HashSet<>(nodo.getHijo_izq().getUltimaPos());
                    nodo.setUltimaPos(c);
                    break;
                case '?':
                    c = new HashSet<>(nodo.getHijo_izq().getUltimaPos());
                    nodo.setUltimaPos(c);
                    break;

            }
        } else {
            if (nodo.getValor() != '&') {
                nodo.getUltimaPos().add(this.contHojas);
            }
        }
    }

    private void hacerPrimeraPos(Nodo nodo, boolean operador) {
        if (operador) {
            Set<Integer> c = null;
            Set<Integer> c1 = null;
            Set<Integer> c2 = null;
            Set<Integer> union = null;
            if (nodo.getHijo_der() != null) {
                c1 = new HashSet<>(nodo.getHijo_izq().getPrimeraPos());
                c2 = new HashSet<>(nodo.getHijo_der().getPrimeraPos());
                union = new HashSet<>(nodo.getHijo_izq().getPrimeraPos());
                union.addAll(c2);
            }
            switch (nodo.getValor()) {
                case '|':
                    nodo.setPrimeraPos(union);
                    break;
                case '.':
                    if (nodo.getHijo_izq().getAnulable()) {
                        nodo.setPrimeraPos(union);
                    } else {
                        nodo.setPrimeraPos(c1);
                    }
                    break;
                case '*':
                    c = new HashSet<>(nodo.getHijo_izq().getPrimeraPos());
                    nodo.setPrimeraPos(c);
                    break;
                case '+':
                    c = new HashSet<>(nodo.getHijo_izq().getPrimeraPos());
                    nodo.setPrimeraPos(c);
                    break;
                case '?':
                    c = new HashSet<>(nodo.getHijo_izq().getPrimeraPos());
                    nodo.setPrimeraPos(c);
                    break;

            }
        } else {
            if (nodo.getValor() != '&') {
                nodo.getPrimeraPos().add(this.contHojas);
            }
        }
    }

    Nodo MakeTree(char[] postfix) {
        Stack<Nodo> st = new Stack();
        Nodo t, t1, t2;

        // Traverse through every character of 
        // input expression 
        for (int i = 0; i < postfix.length; i++) {

            // If operand, simply push into stack 
            if (!isOperator(postfix[i])) {
                t = new Nodo(postfix[i]);
                this.contHojas++;
                st.push(t);
                if (postfix[i] == '&') {
                    t.anulable = true;
                }
                hacerPrimeraPos(t, false);
                hacerUltimaPos(t, false);
            } else // operator 
            {
                t = new Nodo(postfix[i]);
                if (postfix[i] == '|' || postfix[i] == '.') {// operador | y .
                    t1 = st.pop();
                    t.hijo_der = t1;
                    t2 = st.pop();
                    t.hijo_izq = t2;
                } else {// Un solo hijo
                    t1 = st.pop();
                    t.hijo_izq = t1;
                }
                if (postfix[i] == '*' || postfix[i] == '?') {
                    t.anulable = true;
                } else {
                    t.anulable = false;
                }
                if (postfix[i] == '+') {
                    t.anulable = t.getHijo_izq().anulable;
                }
                hacerPrimeraPos(t, true);
                hacerUltimaPos(t, true);
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
