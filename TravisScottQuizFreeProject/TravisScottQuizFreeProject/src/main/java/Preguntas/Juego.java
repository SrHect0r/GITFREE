package Preguntas;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import logic.Player;
import util.SQL;

public class Juego {
    private JPanel rootPanel;
    private JLabel preguntaLabel;
    private JButton respuesta1Button;
    private JButton respuesta2Button;
    private JButton respuesta3Button;
    private JButton respuesta4Button;
    private JLabel vidasLabel;
    private JLabel tiempoLabel;

    private int vidas = 3;
    private int tiempo = 30;
    private Timer timer;

    private final List<String[]> preguntas;
    private int indicePregunta = 0;
    private String[] preguntaActual;
    private int puntuacion = 0;
    private final String nombreJugador;

    // Nueva variable para guardar el índice correcto después del shuffle
    private int indiceRespuestaCorrecta;

    public JPanel getRootPanel() {
        return rootPanel;
    }

    public Juego(JFrame frame, Player player, String categoria) {
        this.nombreJugador = player.getName();
        iniciarTemporizador(frame, player);

        Map<String, List<String[]>> preguntasPorCategoria = LectorPreguntas.cargarPreguntas();
        preguntas = preguntasPorCategoria.getOrDefault(categoria.toLowerCase(), new ArrayList<>());
        Collections.shuffle(preguntas);

        if (preguntas.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No hay preguntas disponibles para esta categoría.");
            frame.dispose();
            return;
        }

        mostrarSiguientePregunta();

        // Cambiado para pasar índices en lugar de texto
        respuesta1Button.addActionListener(e -> verificarRespuesta(frame, player, 1));
        respuesta2Button.addActionListener(e -> verificarRespuesta(frame, player, 2));
        respuesta3Button.addActionListener(e -> verificarRespuesta(frame, player, 3));
        respuesta4Button.addActionListener(e -> verificarRespuesta(frame, player, 4));
    }

    private void iniciarTemporizador(JFrame frame, Player player) {
        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tiempo--;
                tiempoLabel.setText("Tiempo: " + tiempo + "s");
                if (tiempo == 0) {
                    timer.stop();
                    perderVida(frame, player);
                }
            }
        });
        timer.start();
    }

    private void mostrarSiguientePregunta() {
        if (indicePregunta >= preguntas.size()) {
            timer.stop();
            JOptionPane.showMessageDialog(rootPanel, "¡Has completado todas las preguntas!\nPuntuación: " + puntuacion);
            //guardarQuestion(nombreJugador, puntuacion);
            return;
        }

        preguntaActual = preguntas.get(indicePregunta++);
        preguntaLabel.setText(preguntaActual[0]);

        List<String> opciones = Arrays.asList(
                preguntaActual[1],
                preguntaActual[2],
                preguntaActual[3],
                preguntaActual[4]
        );
        Collections.shuffle(opciones);

        respuesta1Button.setText(opciones.get(0));
        respuesta2Button.setText(opciones.get(1));
        respuesta3Button.setText(opciones.get(2));
        respuesta4Button.setText(opciones.get(3));

        // Encontrar el índice de la respuesta correcta en las opciones mezcladas
        String respuestaCorrecta = preguntaActual[5].trim();
        indiceRespuestaCorrecta = -1;
        for (int i = 0; i < opciones.size(); i++) {
            if (opciones.get(i).trim().equalsIgnoreCase(respuestaCorrecta)) {
                indiceRespuestaCorrecta = i;
                break;
            }
        }

        vidasLabel.setText("Vidas: " + vidas);
        tiempoLabel.setText("Tiempo: " + tiempo);
    }

    // Ahora recibe el índice del botón seleccionado
    private void verificarRespuesta(JFrame frame, Player player, int respuestaSeleccionada) {
        if (preguntaActual == null) {
            JOptionPane.showMessageDialog(frame, "Error: no hay pregunta actual.");
            return;
        }

        int respuestaCorrecta;
        try {
            respuestaCorrecta = Integer.parseInt(preguntaActual[5]);  // índice correcto
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Error en formato de respuesta correcta.");
            return;
        }

        if (respuestaSeleccionada == respuestaCorrecta) {
            JOptionPane.showMessageDialog(rootPanel, "¡Correcto!");
            puntuacion++;
            tiempo = 30;
            mostrarSiguientePregunta();
        } else {
            perderVida(frame, player);
        }
    }


    private void perderVida(JFrame frame, Player player) {
        vidas--;
        if (vidas <= 0) {
            timer.stop();
            JOptionPane.showMessageDialog(frame, "Se acabaron tus vidas.\nPuntuación: " + puntuacion);
            //guardarQuestion(nombreJugador, puntuacion);
            frame.dispose();
            SQL.result(player);
        } else {
            tiempo = 30;
            vidasLabel.setText("Vidas: " + vidas);
            mostrarSiguientePregunta();
        }
    }
}
