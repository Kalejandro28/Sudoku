package Sudoku;
import java.util.Set;
import java.util.TreeSet;

public class Cell {
    private int valorCorrecto;
    private int valorEscogido;
    private boolean indefinido;
    private Set<Integer> notas;

    public Cell(int valorCorrecto) {
        this.valorCorrecto = valorCorrecto;
        valorEscogido = -1;
        indefinido = true;
        notas = new TreeSet<>();
    }
    public Cell(int valorCorrecto, int valorEscogido){
        this.valorCorrecto = valorCorrecto;
        this.valorEscogido = valorEscogido;
        indefinido = false;
        notas = new TreeSet<>();
    }

    public int getValorCorrecto() {
        return valorCorrecto;
    }

    public boolean isIndefinido() {
        return indefinido;
    }

    public int getValorEscogido() {
        return valorEscogido;
    }

    public void setValorEscogido(int valorEscogido) {
        this.valorEscogido = valorEscogido;
    }

    public void setIndefinido(boolean indefinido) {
        this.indefinido = indefinido;
    }

    @Override
    public String toString() {
        return String.valueOf(valorEscogido == -1 ? "." : valorEscogido);
    }
}
