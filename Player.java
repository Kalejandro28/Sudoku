package Sudoku;

public class Player {
    private int vidas;

    public Player(int vidas){
        this.vidas = vidas;
    }

    public int getVidas() {
        return vidas;
    }

    public void restarVida(){
        vidas--;
    }
}
