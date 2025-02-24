import java.util.Random;

import javax.swing.JPanel;

public class Jugador {

    private int DISTANCIA = 40;
    private int MARGEN = 10;
    private int TOTAL_CARTAS = 10;
    private int MINIMA_CANTIDAD_GRUPO = 2;
    private String MENSAJE_PREDETERMINADO = "No se encontraron comenarios";
    private String MENSAJE_ENCABEZADO = "Se encontraron los siguientes grupos:\n";
    private Carta[] cartas = new Carta[TOTAL_CARTAS];
    private Random r = new Random();

    public void repartir() {
        for (int i = 0; i < TOTAL_CARTAS; i++) {
            cartas[i] = new Carta(r);
        }
    }

    public void mostrar(JPanel pnl) {
        pnl.removeAll();
        int x = MARGEN + (TOTAL_CARTAS - 1) * DISTANCIA;
        for (Carta carta : cartas) {
            carta.mostrar(pnl, x, MARGEN);
            x -= DISTANCIA;
        }

        pnl.repaint();
    }

    public String getGrupos() {
        String mensaje = MENSAJE_PREDETERMINADO;

        int[] contadores = new int[NombreCarta.values().length];
        for (Carta carta : cartas) {
            contadores[carta.getNombre().ordinal()]++;
        }

        // Verificar si hubo grupos
        boolean hayGrupos = false;
        for (int contador : contadores) {
            if (contador >= MINIMA_CANTIDAD_GRUPO) {
                hayGrupos = true;
                break;
            }
        }

        if (hayGrupos) {
            mensaje = MENSAJE_ENCABEZADO;
            int posicion = 0;
            for (int contador : contadores) {
                if (contador >= MINIMA_CANTIDAD_GRUPO) {
                    mensaje += Grupo.values()[contador] + " de " + NombreCarta.values()[posicion] + "\n";
                }
                posicion++;
            }
        }
        return mensaje;
    }

    public int calcularPuntaje() {
        int puntaje = 0;
        for (Carta carta : cartas) {
            puntaje += carta.getValor();
        }
        return puntaje;
    }
}