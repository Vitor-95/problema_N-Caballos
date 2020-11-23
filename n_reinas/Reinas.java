package n_reinas;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Reinas extends JFrame{
    
    private static final long serialVersionUID=1;

    private int NUM_REINAS;
    private int[] solution;
    private final static int SIZE=600;
    private JPanel panelBoard;
    private JPanel panelResult;
    private List<int[]> listSolutions=null;
    private JButton[][] btnCells;
    private int sizeSol;
    private int sol;
    public Reinas(int NUM_REINAS) {
        this.NUM_REINAS = NUM_REINAS;
        solution = new int[NUM_REINAS];
        //sizeSol=listSolutions.size();
        init();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(SIZE,SIZE);
        
        BorderLayout gestorLayout=new BorderLayout();
        
        setLayout(gestorLayout);

        panelBoard=new JPanel();
        panelResult=new JPanel();
        add(BorderLayout.NORTH,getOptions());
        add(BorderLayout.SOUTH,getResult());
        add(BorderLayout.CENTER,getBoard());
        

        setLocationRelativeTo(this);
        setVisible(true);

    }
    public void resetReinas(int NUM_REINAS) {
        this.NUM_REINAS = NUM_REINAS;
        solution = new int[NUM_REINAS];
        sol=0;
        init();
    }

    public JPanel getBoard(){
        panelBoard.removeAll();
        panelBoard.revalidate();
        panelBoard.repaint();
        btnCells=new JButton[NUM_REINAS][NUM_REINAS];
        GridLayout gestor=new GridLayout(NUM_REINAS,NUM_REINAS);
        panelBoard.setLayout(gestor);
        for(int i=0;i<NUM_REINAS;i++){
            for(int j=0;j<NUM_REINAS;j++){
                JButton cell=new JButton("");
                cell.setBackground(Color.WHITE);
                if((i+j)%2==0){
                    cell.setBackground(Color.BLACK);
                }
                cell.setEnabled(false);
                btnCells[i][j]=cell;
                panelBoard.add(cell);
            }
        }
        panelBoard.revalidate();
        panelBoard.repaint();
        return panelBoard;
    }

    public JPanel getOptions(){
        JPanel panelNorth=new JPanel();
        FlowLayout gestor=new FlowLayout();
        panelNorth.setLayout(gestor);
        JLabel lbnumReinas=new JLabel("NUMERO DE REINAS: ");
        panelNorth.add(lbnumReinas);
        JTextField txtNumReinas=new JTextField(3);
        panelNorth.add(txtNumReinas);
        JButton btnGo=new JButton("GO");
        btnGo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String strNumReinas=txtNumReinas.getText();
                NUM_REINAS=Integer.parseInt(strNumReinas);
                resetReinas(NUM_REINAS);
                getBoard();
                listSolutions=new LinkedList<>();
                searchSolution();
                sizeSol=listSolutions.size();
                getResult();
                paintSolution(listSolutions.get(0));
            }
        });
        panelNorth.add(btnGo);
        return panelNorth;
    }
    
    public void paintSolution(int[] s){
        for(int i=0;i<s.length;i++){
            btnCells[s[i]][i].setText("X");
        }
    }
    public JPanel getResult(){
        //panelResult=new JPanel();
        panelResult.removeAll();
        panelResult.revalidate();
        panelResult.repaint();
        
        JLabel lbnmsjCantidad=new JLabel("Cant Solucion: ");
        //JLabel lbnCantidad=new JLabel("0");
        JLabel lbnCantidad=new JLabel(Integer.toString(sizeSol));
        JButton btnPreview=new JButton("<<");
        btnPreview.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getBoard();
                sol=sol-1;
                paintSolution(listSolutions.get(sol));
                getResult();
            }
        });
        JLabel  lbnNumSolCurrent=new JLabel(Integer.toString(sol+1));
        JButton btnNext= new JButton(">>");
        btnNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getBoard();
                sol=sol+1;
                paintSolution(listSolutions.get(sol));
                getResult();
            }
        });
        panelResult.add(lbnmsjCantidad);
        panelResult.add(lbnCantidad);
        panelResult.add(btnPreview);
        panelResult.add(lbnNumSolCurrent);
        panelResult.add(btnNext);
        panelResult.revalidate();
        panelResult.repaint();


        return panelResult;
    }

    public void init() {
        for (int i = 0; i < solution.length; i++) {
            solution[i] = -1;
        }
    }

    public void searchSolution() {
        init();
        backtracking(solution, 0);
        
        //System.out.println("CANTIDAD: "+sizeSol);
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
                    //System.out.println(strSol+" SOLUCION");
                    int[] sClone =solucion.clone();
                    listSolutions.add(sClone);
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
        sizeSol=listSolutions.size();
        return success;
    }

    
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
        //reina.searchSolution();
    }
}