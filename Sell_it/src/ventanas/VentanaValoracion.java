package ventanas;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import BasesDeDatos.BaseDeDatos;
import clases.Usuario;
import clases.Valoracion;

public class VentanaValoracion extends JFrame {

    private JTextField tfComentario;
    private JComboBox<Integer> cbCalificacion;
    private JCheckBox chkAnonimo;  
    private String nombreEvento;
    private String correoUsuarioCreador;
    private Map<Integer, String> calificacionesMap;

    /**
     * Constructor de la clase VentanaValoracion.
     * @param correoUsuarioCreador Correo del usuario creador del evento.
     * @param nombreEvento Nombre del evento a valorar.
     */
    public VentanaValoracion(String correoUsuarioCreador, String nombreEvento) {
    	this.correoUsuarioCreador = correoUsuarioCreador;
        this.nombreEvento = nombreEvento;
    	
    	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 350);
        setLocationRelativeTo(null);
        setTitle("Valoración");

        // Crear y configurar el panel principal
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Creación de etiquetas informativas
        JLabel labelInfoEvento = new JLabel("Evento: " + nombreEvento);
        JLabel labelInfoUsuario = new JLabel("Creador del evento: " + correoUsuarioCreador);
        JLabel labelCrea = new JLabel("<html>Valora a <font color='blue'>" + correoUsuarioCreador + "</font> del 1 al 5</html>");
        
        // Componentes de entrada
        JLabel labelComentario = new JLabel("Comentario:");
        tfComentario = new JTextField();
        JLabel labelCalificacion = new JLabel("Calificación:");
        
        // Configuración del mapa de calificaciones
        calificacionesMap = new HashMap<>();
        calificacionesMap.put(1, "1 - Descontento");
        calificacionesMap.put(2, "2 - Neutral");
        calificacionesMap.put(3, "3 - Satisfactorio");
        calificacionesMap.put(4, "4 - Contento");
        calificacionesMap.put(5, "5 - Muy contento");
        
        Integer[] calificaciones = {1, 2, 3, 4, 5};        
        cbCalificacion = new JComboBox<>(calificaciones);
        chkAnonimo = new JCheckBox("Anónimo");  
        
        // Agregar componentes al panel
        panel.add(labelInfoEvento);
        panel.add(labelInfoUsuario);
        panel.add(labelCrea);
        panel.add(new JLabel()); // Espacio en blanco
        panel.add(labelComentario);
        panel.add(tfComentario);
        panel.add(labelCalificacion);
        panel.add(cbCalificacion);
        panel.add(chkAnonimo);
        
        // Botón para guardar la valoración
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> guardarValoracion());
        panel.add(new JLabel()); // Espacio en blanco
        panel.add(btnGuardar);
        
        // Botón para mostrar información de valoraciones
        JButton btnMostrarInfo = new JButton("Método de valoración");
        btnMostrarInfo.addActionListener(e -> mostrarInfoValoraciones());
        panel.add(btnMostrarInfo);
        
        add(panel);
    }

    // Método para mostrar información detallada sobre las valoraciones
    private void mostrarInfoValoraciones() {
        // Mensaje con la información de las valoraciones
        StringBuilder infoValoraciones = new StringBuilder("Información de Valoraciones:\n");

        // Itera sobre el mapa de valoraciones y agrega la información al StringBuilder
        for (Map.Entry<Integer, String> entry : calificacionesMap.entrySet()) {
            int calificacion = entry.getKey();
            String descripcion = entry.getValue();
            infoValoraciones.append(calificacion).append(": ").append(descripcion).append("\n");
        }

        // Muestra el mensaje con la información
        JOptionPane.showMessageDialog(this, infoValoraciones.toString(), "Información de Valoraciones", JOptionPane.INFORMATION_MESSAGE);
    }
    
    // Método para guardar la valoración
    private void guardarValoracion() {
        String comentario = tfComentario.getText();
        int calificacion = (int) cbCalificacion.getSelectedItem();

        // Obtención del usuario revisor desde la instancia de VentanaInicio
        VentanaInicio ventanaInicio = Main.getVentanaInicio();
        // Comprobacion de si la opción de ser anónimo está seleccionada
        String correoUsuarioRevisor = chkAnonimo.isSelected() ? "Anónimo" : ventanaInicio.getUsuarioActual().getCorreoUsuario();

        // Obtención del usuario valorado desde el correo almacenado en correoUsuarioCreador
        Usuario usuarioValorado = BaseDeDatos.getUsuarioPorCorreo(correoUsuarioCreador); 

        int id = Valoracion.obtenerId();
        // Guarda la valoracion en la base de datos
        BaseDeDatos.insertarValoracion(id, correoUsuarioRevisor, usuarioValorado.getCorreoUsuario(), calificacion, comentario);

        // Cierra la ventana después de guardar la valoración
        dispose();
    }
}