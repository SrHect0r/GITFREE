package Introducion;

import MenuCategorias.Categorias;
import javax.swing.*;
import java.awt.*;
import logic.Player;
import util.SQL;

public class NombreUsuario {
    private JPanel rootPanel;
    private JTextField nameTextField;
    private JButton continuarButton;
    private Player player = new Player();

    public JPanel getRootPanel() {
        return rootPanel;
    }

    public NombreUsuario(JFrame frame) {
        frame.setSize(700, 700);
        frame.setPreferredSize(new Dimension(500, 500));

        continuarButton.addActionListener(e -> {
            String nombre = nameTextField.getText().trim();

            if (!nombre.isEmpty()) {
                player.setName(nombre);

                SQL.iniciarsesion(player);

                frame.setContentPane(new Categorias(frame, player).getRootPanel());
                frame.revalidate();
            } else {
                JOptionPane.showMessageDialog(frame, "Por favor, ingresa tu nombre");
            }
        });
    }
}