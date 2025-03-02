import java.util.Random;
import javax.swing.JPanel;

public class Jugador {

    private int DISTANCIA = 40;
    private int MARGEN = 10;
    private int TOTAL_CARTAS = 10;
    private int MINIMA_CANTIDAD_GRUPO = 2;
    private String MENSAJE_PREDETERMINADO = "No se encontraron grupos";
    private String MENSAJE_ENCABEZADO = "Se encontraron los siguientes grupos:\n";
    private String MENSAJE_ESCALERAS = "El jugador tiene las siguientes escaleras:\n";
    private String MENSAJE_NO_ESCALERAS = "No hay escaleras en las siguientes pintas:\n";
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

    public String getEscaleras() {
        String mensaje = MENSAJE_ESCALERAS;
        String mensajeNoEscaleras = MENSAJE_NO_ESCALERAS;
        boolean hayEscalerasGenerales = false; 
        
        for (Pinta pinta : Pinta.values()) {
            boolean[] cartasEnEscalera = new boolean[NombreCarta.values().length];
            boolean escaleraEncontrada = false;
            String escaleraActual = "";
    
            for (NombreCarta nombre : NombreCarta.values()) {
                for (Carta carta : cartas) {
                    if (carta.getPinta() == pinta && carta.getNombre() == nombre) {
                        cartasEnEscalera[nombre.ordinal()] = true;
                    }
                }
            }

            for (int i = 0; i < cartasEnEscalera.length - 1; i++) {
                if (cartasEnEscalera[i]) {
                    if (escaleraActual.isEmpty()) {
                        escaleraActual = NombreCarta.values()[i].name();
                    } else {
                        escaleraActual += ", " + NombreCarta.values()[i].name();
                    }
    
                    if (escaleraActual.split(", ").length >= 2) { 
                        escaleraEncontrada = true;
                    }
                } else {
                    if (escaleraEncontrada) {
                        mensaje += "Pinta " + pinta.name() + ": " + escaleraActual + "\n";
                        hayEscalerasGenerales = true;
                    }
                    escaleraActual = "";
                    escaleraEncontrada = false;
                }
            }
    
            if (escaleraEncontrada) {
                mensaje += "Pinta " + pinta.name() + ": " + escaleraActual + "\n";
                hayEscalerasGenerales = true;
            }

            if (!escaleraEncontrada && !hayEscalerasGenerales) {
                mensajeNoEscaleras += pinta.name() + "\n";
            }
        }
    
        return (hayEscalerasGenerales ? mensaje + "\n" : "") + (mensajeNoEscaleras.trim().isEmpty() ? "" : mensajeNoEscaleras);
    }
    
    private boolean perteneceAEscalera(Carta carta) {
        for (Pinta pinta : Pinta.values()) {
            boolean[] cartasEnEscalera = new boolean[NombreCarta.values().length];

            for (NombreCarta nombre : NombreCarta.values()) {
                for (Carta c : cartas) {
                    if (c.getPinta() == pinta && c.getNombre() == nombre) {
                        cartasEnEscalera[nombre.ordinal()] = true;
                    }
                }
            }

            int contadorEscalera = 0;
            for (int i = 0; i < cartasEnEscalera.length; i++) {
                if (cartasEnEscalera[i]) {
                    contadorEscalera++;
                    if (contadorEscalera >= 2) {
                        if (carta.getPinta() == pinta &&
                                (carta.getNombre().ordinal() >= i - 1 && carta.getNombre().ordinal() <= i)) {
                            return true;
                        }
                    }
                } else {
                    contadorEscalera = 0;
                }
            }
        }
        return false;
    }

    private int calcularValorCarta(NombreCarta nombre) {
        if (nombre == NombreCarta.AS || nombre == NombreCarta.JACK ||
                nombre == NombreCarta.QUEEN || nombre == NombreCarta.KING) {
            return 10;
        } else {
            return nombre.ordinal() + 1;
        }
    }

    public int calcularPuntaje() {
        int puntaje = 0;

        for (Carta carta : cartas) {
            if (!perteneceAEscalera(carta)) {
                puntaje += calcularValorCarta(carta.getNombre());
            }
        }
        return puntaje;
    }
}