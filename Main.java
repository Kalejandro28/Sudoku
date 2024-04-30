package Sudoku;

import java.util.InputMismatchException;
import java.util.Scanner;

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
        iniciarTablero("9F6T3F1T7F4T2F5T8F1F7F8T3T2F5T6T4F9F2T5F4F6F8F9F7F3F1T8T2F1F4T3F7T5F9F6T4F9F6T8F5F2F3T1F7F7T3F5F9T6F1T8F2F4T5T8F9F7F1F3F4F6F2T3F1F7T2T4F6T9T8F5F6F4T2F5T9F8T1F7T3F");
        System.out.println("---- SUDOKU ----");
        reglas();
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
        System.out.println("---- 1. ...");
        System.out.println("---- 2. ...");
        System.out.println("---- 3. ...");
        System.out.println("---- 4. ...");
        System.out.println("---- 5. ...");
        System.out.println("---- 6. ...");
        System.out.println("---- 7. Tiene 3 vidas, despues de cada intento fallido pierde 1 vida");
        System.out.println("---- 8. Si se queda sin vidas, es Game Over");
        System.out.println("---- 9. Si resuelve el Sudoku con al menos 1 vida, ha ganado");
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
