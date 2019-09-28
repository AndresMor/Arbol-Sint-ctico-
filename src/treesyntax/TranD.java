/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treesyntax;

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

    // Termianr TransD
    public void hacerTranD(Arbol arbol) {
        Set<Integer> U = arbol.getRaiz().getPrimeraPos();
        Set<Integer> siguientePos[] = arbol.getSiguientePos();
        Set<String> alfabeto = arbol.getAlfabeto();
        Integer estado = 0;
        this.estadosD.put(U.toString(), estado);
        generarPosiciones(arbol);
        while (true) {
            for (String letra : alfabeto) {
                Set<Integer> union = new HashSet<>();
                for (Integer pos : U) {
                    if (this.conjuntoHojas.get(letra).contains(pos)) {
                        for (Integer posiciones : siguientePos[pos - 1]) {
                            union.add(posiciones);
                        }
                    }
                }
                if (!estadosD.containsKey(union.toString())) {
                    // Corregir problema estados
                    estado++;
                    estadosD.put(union.toString(), estado);
                    this.tranD.put(estado, (HashMap<String, String>) 
                            new HashMap<>().put(letra, Integer.toString(estado)));
                }
            }
        }
    }

}
