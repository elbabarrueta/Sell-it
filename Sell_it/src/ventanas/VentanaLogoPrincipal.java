package ventanas;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VentanaLogoPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;

    public VentanaLogoPrincipal() {
        setTitle("Sell It");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon icono = new ImageIcon("Sell_it/src/imagenes/sell_it.png");
        JLabel imagen = new JLabel(icono);
        JLabel mensaje = new JLabel("Cargando Sell It...");
        mensaje.setFont(new Font("Arial", Font.PLAIN, 20));

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(imagen, BorderLayout.CENTER);
        
        JPanel panelSur = new JPanel(new GridBagLayout());
        panelSur.add(mensaje);
        
        panel.add(panelSur, BorderLayout.SOUTH);

        getContentPane().add(panel);

        setSize(400, 300);
        setLocationRelativeTo(null); // Centra la ventana en la pantalla
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);

        // Inicia el hilo de cuenta regresiva
        contarRegresivamente(mensaje);
    }

    private void contarRegresivamente(JLabel mensaje) {
        AtomicInteger segundosRestantes = new AtomicInteger(5);

        Thread hiloCuentaRegresiva = new Thread(() -> {
            while (segundosRestantes.get() > 0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                int segundos = segundosRestantes.decrementAndGet();
                mensaje.setText("Cargando Sell It... " + segundos + " segundos");
            }

            mensaje.setText("Cargando Sell It... ¡Disfruta de tu estancia!");
        });

        hiloCuentaRegresiva.start();
    }
}