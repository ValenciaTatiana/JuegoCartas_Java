import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

public class FrmJuego extends JFrame{

    JPanel pnlJugador1, pnlJugador2;
    JTabbedPane tpJugadores;

    public FrmJuego(){
        setTitle("Jueguemos al Apuntado!!");
        setSize(700, 200);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);

        JButton btnRepartir = new JButton("Repartir");
        btnRepartir.setBounds(10, 10, 100, 25);
        getContentPane().add(btnRepartir);

        JButton btnVerificar = new JButton("Verificar");
        btnVerificar.setBounds(120, 10, 100, 25);
        getContentPane().add(btnVerificar);

        tpJugadores = new JTabbedPane();
        tpJugadores.setBounds(10, 40, 656, 150);
        getContentPane().add(tpJugadores);

        pnlJugador1 = new JPanel();
        pnlJugador1.setBackground(new Color(16, 139, 37));
        pnlJugador1.setLayout(null);

        pnlJugador2 = new JPanel();
        pnlJugador2.setBackground(new Color(0, 255, 255));
        pnlJugador2.setLayout(null);

        tpJugadores.addTab("Andrés Gutierréz", pnlJugador1);
        tpJugadores.addTab("Tatiana Cuadros", pnlJugador2);

        btnRepartir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repartirCartas();
            }
        });

        btnVerificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verificarJugador();
            }
        });
    }

    Jugador jugador1 = new Jugador();
    Jugador jugador2 = new Jugador();

    private void repartirCartas() {
        jugador1.repartir();
        jugador1.mostrar(pnlJugador1);
        jugador2.repartir();
        jugador2.mostrar(pnlJugador2);
    }

    private void verificarJugador() {
        int pestañaSeleccionada = tpJugadores.getSelectedIndex();
        int puntaje;
        String jugadorNombre;
        String grupos;
        
        if (pestañaSeleccionada == 0) {
            puntaje = jugador1.calcularPuntaje();
            jugadorNombre = "Andrés Gutiérrez";
            grupos = jugador1.getGrupos();
        } else {
            puntaje = jugador2.calcularPuntaje();
            jugadorNombre = "Tatiana Cuadros";
            grupos = jugador2.getGrupos();
        }
    
        String mensaje = "Puntaje de " + jugadorNombre + ": " + puntaje + "\n\n" + grupos;
    
        JOptionPane.showMessageDialog(null, mensaje, "Información del Jugador", JOptionPane.INFORMATION_MESSAGE);
    }
}