import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Carta {

    private int indice;

    public Carta(Random r) {
        indice = r.nextInt(52) + 1;
    }

    public Pinta getPinta() {
        if (indice <= 13) {
            return Pinta.TREBOL;
        } else if (indice <= 26) {
            return Pinta.PICA;
        } else if (indice <= 39) {
            return Pinta.CORAZON;
        } else {
            return Pinta.DIAMANTE;
        }
    }

    public NombreCarta getNombre() {
        int posicion = indice % 13;
        if (posicion == 0) {
            posicion = 13;
        }
        return NombreCarta.values()[posicion - 1];
    }

    public void mostrar(JPanel pnl, int x, int y) {
        String nombreArchivo = "/img/CARTA" + String.valueOf(indice) + ".jpg";
        ImageIcon imagen = new ImageIcon(getClass().getResource(nombreArchivo));

        JLabel lbl = new JLabel(imagen);
        lbl.setBounds(x, y, imagen.getIconWidth(), imagen.getIconHeight());
        pnl.add(lbl);
    }

    public int getValor() {
        NombreCarta nombre = getNombre();
        switch (nombre) {
            case AS:
            case JACK:
            case QUEEN:
            case KING:
                return 10;
            default:
                return nombre.ordinal() + 1;
        }
    }
}
