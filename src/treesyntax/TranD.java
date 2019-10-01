/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treesyntax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author ferch5003
 */
public class TranD {

    private HashMap<String, Integer> estadosD;
    private HashMap<Integer, HashMap<String, String>> tranD;
    private HashMap<String, Set<Integer>> conjuntoHojas;

    public TranD() {
        this.estadosD = new HashMap<>();
        this.tranD = new HashMap<>();
        this.conjuntoHojas = new HashMap<>();
    }

    public HashMap<String, Integer> getEstadosD() {
        return estadosD;
    }

    public void setEstados(String conjunto, Integer estado) {
        this.estadosD.put(conjunto, estado);
    }

    public HashMap<Integer, HashMap<String, String>> getTranD() {
        return tranD;
    }

    public void setMovimientos(Integer estado, HashMap<String, String> letra) {
        this.tranD.put(estado, letra);
    }

    private void generarPosiciones(Arbol arbol) {
        Set<String> alfabeto = arbol.getAlfabeto();
        HashMap hojas = arbol.getHojas();
        for (String letra : alfabeto) {
            this.conjuntoHojas.put(letra, new HashSet<>());
            hojas.forEach((llave, valor) -> {
                if (letra.equals(valor)) {
                    this.conjuntoHojas.get(letra).add((Integer) llave);
                }
            });
        }
    }

    // Crea estadosD y tranD, estadosD es un HashMap<String, Integer> y
    // tranD tiene es un HashMap<Integer, HashMap<String, String>>
    public void hacerTranD(Arbol arbol) {
        ArrayList<Set<Integer>> U = new ArrayList<>();
        U.add(arbol.getRaiz().getPrimeraPos());
        Set<Integer> siguientePos[] = arbol.getSiguientePos();
        Set<String> alfabeto = arbol.getAlfabeto();
        Integer estado = 1;
        this.estadosD.put(U.stream().findFirst().get().toString(), estado);
        generarPosiciones(arbol);
        while (true) {
            if (U.isEmpty()) {
                break;
            }
            HashMap<String, String> contenido = new HashMap<>();
            for (String letra : alfabeto) {
                Set<Integer> union = new HashSet<>();
                Set<Integer> T = U.stream().findFirst().get();
                T.stream().filter((pos) -> (this.conjuntoHojas.get(letra).contains(pos))).forEachOrdered((pos) -> {
                    siguientePos[pos - 1].forEach((posiciones) -> {
                        union.add(posiciones);
                    });
                });
                if (union.isEmpty()) {
                    continue;
                }
                // (ab|cd)*d*c?
                /*
                this.estadosD.forEach((k,v)->{
                    System.out.println("k: " + k + "\nv: " + v);
                });
                 */
                if (!this.estadosD.containsKey(union.toString())) {
                    estado++;
                    this.estadosD.put(union.toString(), estado);
                    contenido.put(letra, Integer.toString(estado));
                    this.tranD.put(this.estadosD.get(T.toString()), contenido);
                    U.add(union);
                } else {

                    contenido.put(letra, Integer.toString(this.estadosD.get(union.toString())));
                    this.tranD.put(this.estadosD.get(T.toString()), contenido);
                }
            }
            Set<Integer> removerPrimero = U.stream().findFirst().get();
            U.remove(removerPrimero);
            if (!this.tranD.containsKey(estado)) {
                this.tranD.put(estado, new HashMap<>());
            };
        }
    }

}
