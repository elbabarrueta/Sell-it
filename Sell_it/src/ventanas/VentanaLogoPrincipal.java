package src.ventanas;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class VentanaLogoPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;

    public VentanaLogoPrincipal() {
        setTitle("Sell It");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon icono = new ImageIcon("Sell_it/src/imagenes/sell_it.png");
        JLabel imagen = new JLabel(icono);
        JLabel mensaje = new JLabel("Cargando Sell It...");
        mensaje.setFont(new Font("Arial", Font.PLAIN, 20));

        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(imagen, BorderLayout.CENTER);

        JPanel panelSur = new JPanel(new GridBagLayout());
        panelSur.add(mensaje);
        panelSur.add(progressBar);

        panel.add(panelSur, BorderLayout.SOUTH);

        getContentPane().add(panel);

        setSize(400, 300);
        setLocationRelativeTo(null); // Centra la ventana en la pantalla
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);

        // Inicia el hilo de cuenta regresiva y progreso aleatorio
        contarRegresivamente(mensaje, progressBar);
    }

    private void contarRegresivamente(JLabel mensaje, JProgressBar progressBar) {
        AtomicInteger segundosRestantes = new AtomicInteger(4);

        Thread hiloCuentaRegresiva = new Thread(() -> {
            Random random = new Random();

            while (segundosRestantes.get() > 0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                int segundos = segundosRestantes.decrementAndGet();
                mensaje.setText("Cargando Sell It... " + segundos + " segundos");

                // Simula un progreso aleatorio en la barra de progreso
                int progreso = 100 - (segundos * 25); // Incrementa progresivamente cada segundo
                progreso += random.nextInt(11); // Agrega un valor aleatorio entre 0 y 10

                progressBar.setValue(progreso);
            }

            mensaje.setText("Cargando Sell It... ¡Disfruta de tu estancia!");
            progressBar.setValue(100); // Asegura que la barra de progreso alcance el 100% al final
        });

        hiloCuentaRegresiva.start();
    }

    public static void main(String[] args) {
        new VentanaLogoPrincipal();
    }
}
