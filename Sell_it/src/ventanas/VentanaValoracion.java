package ventanas;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import BasesDeDatos.BaseDeDatos;
import clases.Usuario;
import clases.Valoracion;

public class VentanaValoracion extends JFrame {

    private JTextField tfComentario;
    private JComboBox<Integer> cbCalificacion;
    private JCheckBox chkAnonimo;  // Checkbox para la opción de ser anónimo
    private String nombreEvento;
    private String correoUsuarioCreador;

    public VentanaValoracion(String correoUsuarioCreador, String nombreEvento) {
    	this.correoUsuarioCreador = correoUsuarioCreador;
        this.nombreEvento = nombreEvento;
    	
    	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 350);
        setLocationRelativeTo(null);
        setTitle("Valoración");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));
        JLabel labelInfoEvento = new JLabel("Evento: " + nombreEvento);
        JLabel labelInfoUsuario = new JLabel("Creador del evento: " + correoUsuarioCreador);

        JLabel labelCrea = new JLabel("Valora a " + nombreEvento + "del 1 al 5");
        JLabel labelComentario = new JLabel("Comentario:");
        tfComentario = new JTextField();
        JLabel labelCalificacion = new JLabel("Calificación:");
        Integer[] calificaciones = {1, 2, 3, 4, 5};
        cbCalificacion = new JComboBox<>(calificaciones);
        chkAnonimo = new JCheckBox("Anónimo");  
        
        panel.add(labelInfoEvento);
        panel.add(labelInfoUsuario);
        panel.add(labelComentario);
        panel.add(tfComentario);
        panel.add(labelCalificacion);
        panel.add(cbCalificacion);
        panel.add(chkAnonimo);
        
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

     // Obtén el usuario revisor desde la instancia de VentanaInicio
        VentanaInicio ventanaInicio = Main.getVentanaInicio();
     // Comprueba si la opción de ser anónimo está seleccionada
        String correoUsuarioRevisor = chkAnonimo.isSelected() ? "Anónimo" : ventanaInicio.getUsuarioActual().getCorreoUsuario();

        // Obtén el usuario valorado desde el correo almacenado en correoUsuarioCreador
        Usuario usuarioValorado = BaseDeDatos.getUsuarioPorCorreo(correoUsuarioCreador); 

        int id = Valoracion.obtenerId();
        // Guarda la valoracion
        BaseDeDatos.insertarValoracion(id, correoUsuarioRevisor, usuarioValorado.getCorreoUsuario(), calificacion, comentario);

        // Cerrar la ventana después de guardar la valoración
        dispose();
    }
}