package ventanas;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class VentanaValoracion extends JFrame {

    private JTextField tfComentario;
    private JComboBox<Integer> cbCalificacion;
    private String correoUsuarioCreador;
    private String nombreEvento;

    public VentanaValoracion(String correoUsuarioCreador, String nombreEvento) {
    	this.correoUsuarioCreador = correoUsuarioCreador;
        this.nombreEvento = nombreEvento;
    	
    	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 350);
        setLocationRelativeTo(null);
        setTitle("Valoración");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));
        JLabel labelInfoEvento = new JLabel("Evento: " + nombreEvento);
        JLabel labelInfoUsuario = new JLabel("Creador del evento: " + correoUsuarioCreador);

        JLabel labelCrea = new JLabel("Valora a " + nombreEvento + "del 1 al 5");
        JLabel labelComentario = new JLabel("Comentario:");
        tfComentario = new JTextField();
        JLabel labelCalificacion = new JLabel("Calificación:");
        Integer[] calificaciones = {1, 2, 3, 4, 5};
        cbCalificacion = new JComboBox<>(calificaciones);

        panel.add(labelInfoEvento);
        panel.add(labelInfoUsuario);
        panel.add(labelComentario);
        panel.add(tfComentario);
        panel.add(labelCalificacion);
        panel.add(cbCalificacion);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarValoracion();
            }
        });
        panel.add(btnGuardar);

        add(panel);
    }

    private void guardarValoracion() {
        String comentario = tfComentario.getText();
        int calificacion = (int) cbCalificacion.getSelectedItem();

        // Aquí puedes realizar la lógica para guardar la valoración en la base de datos
        // Por ejemplo, podrías crear una instancia de la clase Valoracion y guardarla
        // en tu base de datos.

        // Valoracion valoracion = new Valoracion(comentario, calificacion, usuarioActual.getCorreo());
        // BaseDeDatos.guardarValoracion(valoracion);

        // Puedes cerrar la ventana después de guardar la valoración
        dispose();
    }
}