package MenuCategorias;

import Preguntas.Juego;
import logic.Player;

import javax.swing.*;

public class Categorias {
    private JPanel rootPanel;
    private JButton opcion1Button;
    private JButton opcion2Button;
    private JButton opcion3Button;
    private JButton opcion4Button;

    public JPanel getRootPanel() {
        return rootPanel;
    }

    public Categorias(JFrame frame, Player player) {
        opcion1Button.addActionListener(e -> iniciarJuego(frame, player, "album"));
        opcion2Button.addActionListener(e -> iniciarJuego(frame, player, "collab"));
        opcion3Button.addActionListener(e -> iniciarJuego(frame, player, "vida"));
        opcion4Button.addActionListener(e -> iniciarJuego(frame, player, "canciones"));
    }

    private void iniciarJuego(JFrame frame, Player player, String categoria) {
        frame.setContentPane(new Juego (frame, player, categoria).getRootPanel());
        frame.revalidate();
    }
}
