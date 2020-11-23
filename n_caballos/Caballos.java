package n_caballos;

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

public class Caballos extends JFrame{

    private static final long serialVersionUID=1;
    private int NUM_CABALLOS;
    private int[] solution;
    private final static int SIZE=600;
    private JPanel panelBoard;
    private JPanel panelResult;
    private List<int[]> listSolutions=null;
    private JButton[][] btnCells;
    private int sizeSol;
    private int sol;
    public Caballos(int NUM_CABALLOS) {
        this.NUM_CABALLOS = NUM_CABALLOS;
        solution = new int[NUM_CABALLOS];
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
    public void resetCaballos(int NUM_CABALLOS) {
            this.NUM_CABALLOS = NUM_CABALLOS;
            solution = new int[NUM_CABALLOS];
            sol=0;
            init();
    }
    
    public JPanel getBoard(){
        panelBoard.removeAll();
        panelBoard.revalidate();
        panelBoard.repaint();
        btnCells=new JButton[NUM_CABALLOS][NUM_CABALLOS];
        GridLayout gestor=new GridLayout(NUM_CABALLOS,NUM_CABALLOS);
        panelBoard.setLayout(gestor);
        for(int i=0;i<NUM_CABALLOS;i++){
            for(int j=0;j<NUM_CABALLOS;j++){
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
        JLabel lbnumCaballos=new JLabel("NUMERO DE CABALLOS: ");
        panelNorth.add(lbnumCaballos);
        JTextField txtNumCaballos=new JTextField(3);
        panelNorth.add(txtNumCaballos);
        JButton btnGo=new JButton("GO");
        btnGo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String strNumCaballos=txtNumCaballos.getText();
                NUM_CABALLOS=Integer.parseInt(strNumCaballos);
                resetCaballos(NUM_CABALLOS);
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
                    int[] sClone =solucion.clone();
                    listSolutions.add(sClone);
                }

                
                 
                 
                if (valid) {

                    success = backtracking(solucion, caballo + 1);
                }
            } while (solution[caballo] < (NUM_CABALLOS - 1) && (!success));
            solucion[caballo] = -1;
        } else {

        }
        sizeSol=listSolutions.size();
        return success;
    }


    public boolean isValid(int[] solution, int caballo) {
       
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
        //caballo.searchSolution();
    }
}