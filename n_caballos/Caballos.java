package n_caballos;

import java.util.Arrays;

public class Caballos {

    private int NUM_CABALLOS;
    private int[] solution;

    public Caballos(int NUM_CABALLOS) {
        this.NUM_CABALLOS = NUM_CABALLOS;
        solution = new int[NUM_CABALLOS];
        init();
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

    public boolean backtracking(int[] solucion, int caballo) {
        boolean success = false;
        if (caballo < NUM_CABALLOS) {
            do {
                solucion[caballo]++;
                boolean valid = isValid(solution, caballo);
                String strSol = Arrays.toString(solucion);
               if (valid && (caballo ==NUM_CABALLOS - 1)) {
                    System.out.println(strSol + " SOLUCION");
                }

                
                 /*System.out.println(strSol + " " + (valid ? "SOL PARCIAL " : "") + (valid &&
                 (caballo == NUM_CABALLOS - 1) ? "SOLUTION" : ""));*/
                 
                if (valid) {

                    success = backtracking(solucion, caballo + 1);
                }
            } while (solution[caballo] < (NUM_CABALLOS - 1) && (!success));
            solucion[caballo] = -1;
        } else {

        }
        return success;
    }


    public boolean isValid(int[] solution, int caballo) {
        //boolean ok = true;
        /*for (int i = 0; i < caballo; i++) {
            if (solution[caballo]==solution[i]-2||solution[caballo]==solution[i]-1||
                solution[caballo]==solution[i]+2||solution[caballo]==solution[i]+1){
                ok = false;
                break;
            }
        }*/
            if(caballo-1>=0){
                if (solution[caballo]==solution[caballo-1]-2||solution[caballo]==solution[caballo-1]+2){
                    return false;
                }
            }
            if(caballo - 2 >= 0) {
                if (solution[caballo]==solution[caballo-2]-1||solution[caballo]==solution[caballo-2]+1){
                    return false;
                }
            }
            
        return true;
    }

    public static void main(String[] args) {
        Caballos caballo = new Caballos(4);
        caballo.searchSolution();
    }
}