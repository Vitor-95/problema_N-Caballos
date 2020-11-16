package n_reinas;

import java.util.Arrays;

public class Reinas {

    private int NUM_REINAS;
    private int[] solution;

    public Reinas(int NUM_REINAS) {
        this.NUM_REINAS = NUM_REINAS;
        solution = new int[NUM_REINAS];
        init();
        // String strArray = Arrays.toString(solution);
        // System.out.println(strArray);
    }

    public void init() {
        for (int i = 0; i < solution.length; i++) {
            solution[i] = -1;
        }
    }

    public void searchSolution() {
        init();
        backtracking(solution, 0);
    }

    public boolean backtracking(int[] solucion, int reina) {
        boolean success = false;
        // CONDICION PARA EVALUAR SI HEMOS PROBADO TODAS LAS REINAS
        if (reina < NUM_REINAS) {// CASO BASE
            do {
                solucion[reina]++;// [0,-1,-1,-1] // [0,2,-1,-1]
                // Es para determinar las soluciones parciales
                boolean valid = isValid(solution, reina);
                String strSol = Arrays.toString(solucion);
                if(valid&&(reina==NUM_REINAS-1)){
                    System.out.println(strSol+" SOLUCION");
                }
                
                /*
                System.out.println(strSol + " " + (valid ? "SOL PARCIAL " : "")
                        + (valid && (reina == NUM_REINAS - 1) ? "SOLUTION" : ""));*/
                if (valid) {
                    // reina = reina + 1;
                    success = backtracking(solucion, reina + 1);
                }
            } while (solution[reina] < (NUM_REINAS - 1) && (!success));
            solucion[reina] = -1;
        } else {

        }
        return success;
    }

    // ESTUDIAR Y EXPLICAR LA SIGUIENTE CLASE COMO ES QUE SE DETERMINA
    // SI LA RESTRINCCION SE CUMPLE O NO (FILA Y DIAGONALES)
    public boolean isValid(int[] solution, int reina) {
        boolean ok = true;
        for (int i = 0; i < reina; i++) {
            if ((solution[i] == solution[reina]) || (Math.abs(solution[i] - solution[reina]) == Math.abs(i - reina))) {
                ok = false;
                break;
            }
        }
        return ok;
    }

    public static void main(String[] args) {
        Reinas reina = new Reinas(4);
        reina.searchSolution();
    }
}