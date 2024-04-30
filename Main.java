package Sudoku;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author Alejandro Crespo Perez
 */
public class Main {

    private static Scanner sc = new Scanner(System.in);

    private static Player player = new Player(3);

    private static Cell[][] tablero = new Cell[9][9];


    public static void main(String[] args) {
        play();
    }

    /**
     * Metodo principal del juego
     */
    public static void play(){
        // iniciarTableroPrueba();
        System.out.println("---- SUDOKU ----");
        reglas();
        String dificultad;
        do {
            System.out.println("Que dificultad desea ? ");
            System.out.println(" - (D)ificil  /  (M)edio  /  (F)acil  -");
            dificultad = sc.nextLine();
            if(dificultad.equals("D") || dificultad.equals("M")  ||dificultad.equals("F")){
                break;
            }else{
                System.out.println("Porfavor introduzca una dificultad valida (D, M o F)");
            }
        }while(true);
        int N = 9, K;
        switch (dificultad){
            case "D":
                K = 60;
                break;
            case "M":
                K = 40;
                break;
            case "F":
                K = 20;
                break;
            default:
                K = 30;
        }
        SudokuGenerator sudoku = new SudokuGenerator(N, K);
        iniciarTablero(sudoku.parse());

        do {
            mostrarTablero();
            int opcion = opciones();

            switch (opcion) {
                case 1:
                    introducirValor();
                    break;
                case 2:
                    addNota();
                    break;
                case 3:
                    verNotas();
                    break;
            }

            if(player.getVidas() <= 0 || comprobarVictoria()){
                break;
            }
        }while(true);
        if(comprobarVictoria()){
            mostrarTablero();
            System.out.println("Enhorabuena !!!");
        }else{
            mostrarTablero();
            System.out.println("Game Over, Try Again !!!");
        }
    }

    /**
     * Metodo para intentar intruducir un valor a la celda
     * Se introducira si es correcto. Sino quitara una vida
     */
    public static void introducirValor(){
        int row = 0;
        int col = 0;
        int valor = 0;
        do {
            try {
                System.out.println("----  Escoja fila  ----");
                row = sc.nextInt();
                System.out.println("---- Escoja columna ----");
                col = sc.nextInt();
                System.out.println("---- Escoja un numero del 1 al 9 ----");
                valor = sc.nextInt();
                if(!((row < 10 && row > 0) && (col < 10 && col > 0)) || !tablero[row-1][col-1].isIndefinido()){
                    System.out.println("Esta celda no existe o esta ya respuesta");
                }if(!(valor > 0 && valor < 10)){
                    System.out.println("El valor introducido no es valido");
                }
            } catch (InputMismatchException e) {
                System.out.println("Introduzca un numero valido del 1 al 9");
                sc.nextLine();
            }
        }while (!((row > 0 && row < 10) && (col > 0 && col < 10)) || !tablero[row-1][col-1].isIndefinido());
        if(tablero[row-1][col-1].getValorCorrecto() == valor){
            tablero[row-1][col-1].setValorEscogido(valor);
            tablero[row-1][col-1].setIndefinido(false);
        }else{
            System.out.println("El valor no es correcto");
            player.restarVida();
            System.out.println("Vidas restantes: " + player.getVidas());
        }

    }

    /**
     * Metodo para añadir notas/apuntes a cierta celda
     */
    public static void addNota(){
        int row = 0;
        int col = 0;
        do{
            try{
                System.out.println("----  Escoja fila  ----");
                row = sc.nextInt();
                System.out.println("---- Escoja columna ----");
                col = sc.nextInt();
                if(!((row < 10 && row > 0) && (col < 10 && col > 0)) || !tablero[row-1][col-1].isIndefinido()){
                    System.out.println("Esta celda no existe o esta ya respuesta");
                }
            }catch (InputMismatchException e){
                System.out.println("Introduzca un numero valido del 1 al 9");
                sc.nextLine();
            }
        }while(!((row > 0 && row < 10) && (col > 0 && col < 10)) || !tablero[row-1][col-1].isIndefinido());
        int contador = 1;
        int nota = -1;
        boolean salir = false;
        do{
            try {
                System.out.println("Nota " + contador + ": ");
                nota = sc.nextInt();
                if(nota > 0 && nota < 10) {
                    tablero[row - 1][col - 1].getNotas().add(nota);
                    contador++;
                }else{
                    System.out.println("Nota no valida (del 1 al 9)");
                }
                System.out.println("Notas : " + tablero[row - 1][col - 1].getNotas());
                System.out.println("Desea seguir añadiendo notas? (Y)es/(N)o");
                String s = sc.next();
                salir = s.equals("N");
            }catch (InputMismatchException e){
                System.out.println("Introduzca un numero valido del 1 al 9");
                // sc.nextLine();
                System.out.println("Desea seguir añadiendo notas? (Y)es/(N)o");
                String s = sc.nextLine();
                salir = s.equals("N");
            }
        }while(!salir);
    }

    /**
     * Metodo para ver las notas/apuntes de cierta celda
     */
    public static void verNotas(){
        int row = 0;
        int col = 0;
        do{
            try{
                System.out.println("----  Escoja fila  ----");
                row = sc.nextInt();
                System.out.println("---- Escoja columna ----");
                col = sc.nextInt();
                if(!((row < 10 && row > 0) && (col < 10 && col > 0)) || !tablero[row-1][col-1].isIndefinido()){
                    System.out.println("Esta celda no existe o esta ya respuesta");
                }
                System.out.println("Notas de la celda [" + row + "," + col + "] : " + tablero[row - 1][col - 1].getNotas());
            }catch (InputMismatchException e){
                System.out.println("Introduzca un numero valido del 1 al 9");
                sc.nextLine();
            }
        }while(!((row > 0 && row < 10) && (col > 0 && col < 10)) || !tablero[row-1][col-1].isIndefinido());
    }

    /**
     * Metodo que comprueba si el tablero a sido completado
     * @return true si se ha completado, sino false
     */
    public static boolean comprobarVictoria(){
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero.length; j++) {
                if(tablero[i][j].getValorCorrecto() != tablero[i][j].getValorEscogido()){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Reglas del Juego
     */
    public static void reglas(){
        System.out.println("---- REGLAS DEL SUDOKU ----");
        System.out.println("---- 1. No se pueden repetir el mismo numero en un mismo cuadrante (3x3)");
        System.out.println("---- 2. No se pueden repetir el mismo numero en una misma fila");
        System.out.println("---- 3. No se pueden repetir el mismo numero en una misma columna");
        System.out.println("---- 4. Tiene 3 vidas, despues de cada intento fallido pierde 1 vida");
        System.out.println("---- 5. Si se queda sin vidas, es Game Over");
        System.out.println("---- 6. Si resuelve el Sudoku con al menos 1 vida, ha ganado");
        System.out.println("---- 7. Puede añadir notas/apuntes en cada celda sin perder vidas");
    }

    /**
     * Menu de opciones
     * @return 1,2 o 3 segun la opcion escogida
     */
    public static int opciones(){
        int opc;
        do {
            try {
                System.out.println("---- Desea introducir un valor, ver las notas de una celda, o añadir notas a una celda? ----");
                System.out.println("---- 1. Introducir valor ");
                System.out.println("---- 2. Añadir Notas ");
                System.out.println("---- 3. Ver Notas ");
                opc = sc.nextInt();
                if(opc == 1 || opc == 2 || opc == 3){break;}
                System.out.println("Escoja un modo de juego valido (1, 2 o 3)");
            }catch (InputMismatchException e){
                System.out.println("Escoja un modo de juego valido (1, 2 o 3)");
                sc.nextLine();
            }
        }while(true);
        return opc;
    }

    /**
     * Creacion del tablero
     * @param tableroParse String a parsear con la informacion del tablero
     */
    public static void iniciarTablero(String tableroParse){
        if(tableroParse.length() == (tablero.length * tablero.length * 2))
        {
            for (int i = 0; i < tableroParse.length(); i+=2) {
                int value = Integer.parseInt(String.valueOf(tableroParse.charAt(i)));
                if(tableroParse.charAt(i+1) == 'T'){
                    tablero[(i/2)/9][(i/2)%9] = new Cell(value, value);
                }else{
                    tablero[(i/2)/9][(i/2)%9] = new Cell(value);
                }
            }
        }
    }

    /**
     * Metodo para mostrar el estado del tablero en la terminal
     */
    public static void mostrarTablero(){
        System.out.println("    1 2 3   4 5 6   7 8 9  ");
        System.out.println("  +-------|-------|-------+");
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero.length; j++) {
                if(j == 0) System.out.print((i+1) + " ");
                if(j%3 == 0){
                    System.out.print("| " + tablero[i][j] + " ");
                }else{
                    System.out.print(tablero[i][j] + " ");
                }

            }
            System.out.println("|");
            if((i+1)%3 == 0 && i+1 != tablero.length) {
                System.out.println("  |-------|-------|-------|");
            }
        }
        System.out.println("  +-------|-------|-------+");
    }
}
