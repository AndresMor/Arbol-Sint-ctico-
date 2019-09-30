/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treesyntax;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
    private Integer posicionFin;
    private Set<Integer> siguientePos[];
    private Set<String> alfabeto;
    private HashMap<Integer, String> hojas;

    public Arbol() {
        raiz = null;
        this.contHojas = 0;
        this.alfabeto = new HashSet<>();
        this.hojas = new HashMap<>();
    }

    public Integer getPosicionFin() {
        return posicionFin;
    }

    public void hacerEstructuraSiguiente(int conHojas) {
        this.siguientePos = new Set[contHojas];
        for (int i = 0; i < contHojas; i++) {
            this.siguientePos[i] = new HashSet<>();
        }
    }

    public Integer getContHojas() {
        return contHojas;
    }

    public void setContHojas(Integer contHojas) {
        this.contHojas = contHojas;
    }

    public Nodo getRaiz() {
        return raiz;
    }

    public void setRaiz(Nodo raiz) {
        this.raiz = raiz;
    }

    public HashMap<Integer, String> getHojas() {
        return hojas;
    }

    boolean isOperator(char c) {
        if (c == '+' || c == '.'
                || c == '*' || c == '|'
                || c == '?') {
            return true;
        }
        return false;
    }

    public Set<String> getAlfabeto() {
        return alfabeto;
    }

    public void setAlfabeto(Set<String> alfabeto) {
        this.alfabeto = alfabeto;
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

    public Set<Integer>[] getSiguientePos() {
        return siguientePos;
    }

    public void setSiguientePos(Set<Integer>[] siguientePos) {
        this.siguientePos = siguientePos;
    }

    private void hacerSiguientePos(Nodo nodo) {
        Object ultimaPosN[] = nodo.getUltimaPos().toArray();
        Set<Integer> primeraPosN = nodo.getPrimeraPos();
        switch (nodo.getValor()) {
            case '.':
                Object ultimaPosC1[] = nodo.getHijo_izq().getUltimaPos().toArray();
                Set<Integer> primeraPosC2 = nodo.getHijo_der().getPrimeraPos();
                for (int i = 0; i < ultimaPosC1.length; i++) {
                    this.siguientePos[(Integer) ultimaPosC1[i] - 1].addAll(primeraPosC2);
                }
                break;
            case '*':
                for (int i = 0; i < ultimaPosN.length; i++) {
                    this.siguientePos[(Integer) ultimaPosN[i] - 1].addAll(primeraPosN);
                }
                break;
            case '+':
                for (int i = 0; i < ultimaPosN.length; i++) {
                    this.siguientePos[(Integer) ultimaPosN[i] - 1].addAll(primeraPosN);
                }
                break;
            /*
            case '?':
                for (int i = 0; i < ultimaPosN.length; i++) {
                    this.siguientePos[(Integer) ultimaPosN[i] - 1].addAll(primeraPosN);
                }
                break;
             */
        }
    }

    public void preorden(Nodo nodo) {
        if (nodo == null) {
            return;
        }
        hacerSiguientePos(nodo);
        preorden(nodo.getHijo_izq());
        preorden(nodo.getHijo_der());
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
                st.push(t);
                if (postfix[i] == '&') {
                    t.anulable = true;
                } else {
                    this.contHojas++;
                }
                if (postfix[i] != '&' && postfix[i] != '#') {
                    this.hojas.put(this.contHojas, Character.toString(postfix[i]));
                }
                if (postfix[i] == '#') {
                    this.posicionFin = this.contHojas;
                }
                this.alfabeto.add(Character.toString(postfix[i]));
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
                // SiguientePos
                st.push(t);
            }
        }

        //  only element will be root of expression 
        // tree 
        t = st.peek();
        this.alfabeto.remove("#");
        this.alfabeto.remove("&");
        st.pop();

        return t;
    }

}