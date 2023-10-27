package ventanas;

import javax.swing.*;

import clases.Usuario;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


public class VentanaPerfilUsuario extends JFrame{

	private JPanel panelBotones;
	private JPanel panelPrincipal;
	private JPanel panelInformacion;
	private JLabel lblFotoPerfil;
	
	private Usuario usuario;
	private LocalDate ultimoCambioContrasena;
	
	public VentanaPerfilUsuario(Usuario usuario) {
		
		this.usuario = usuario;
		ultimoCambioContrasena = LocalDate.now(); //para provar ahora, que sea la fecha actual
		
		JFrame frame = new JFrame("Perfil Usuario");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setLayout(new BorderLayout());

	    // Parte superior: nombre, correo y botones de información
	    JPanel topPanel = new JPanel(new FlowLayout());
	    
	    lblFotoPerfil = new JLabel();
        ImageIcon imagenPerfil = new ImageIcon(VentanaPerfilUsuario.class.getResource("perfil.png")); // Ruta de la imagen de perfil
        lblFotoPerfil.setIcon(imagenPerfil);
        lblFotoPerfil.setPreferredSize(new Dimension(100, 100));       
	    
	    JLabel nameLabel = new JLabel("Nombre:");
	    JTextField nameField = new JTextField(20);
	    JLabel emailLabel = new JLabel("Correo:");
	    JTextField emailField = new JTextField(20);
	    JButton infoButton1 = new JButton("En venta");
	    JButton infoButton2 = new JButton("Valoraciones");
	    JButton infoButton3 = new JButton("+ Informacion");
	    topPanel.add(lblFotoPerfil);
	    topPanel.add(nameLabel);
	    topPanel.add(nameField);
	    topPanel.add(emailLabel);
	    topPanel.add(emailField);
	    topPanel.add(infoButton1);
	    topPanel.add(infoButton2);
	    topPanel.add(infoButton3);

	    // Parte central: descripción del usuario
	    JTextArea descriptionArea = new JTextArea(10, 40);
	    JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);

	    // Parte inferior: más botones
	    JPanel bottomPanel = new JPanel();
	    JButton buttonContrasena = new JButton("Cambiar contraseña");
	    JButton button2 = new JButton("Botón 2");
	    JButton button3 = new JButton("Botón 3");
	    bottomPanel.add(buttonContrasena);
	    bottomPanel.add(button2);
	    bottomPanel.add(button3);

	    frame.add(topPanel, BorderLayout.NORTH);
	    frame.add(descriptionScrollPane, BorderLayout.CENTER);
	    frame.add(bottomPanel, BorderLayout.SOUTH);

	    // Agregar acción al botón de información 1
	    infoButton1.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            JOptionPane.showMessageDialog(frame, "Información 1");
	        }
	    });
	    
	    buttonContrasena.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				 // Obtener la última fecha de cambio de contraseña (esto es un valor de ejemplo).
		        LocalDate ultimaCambio = LocalDate.of(2023, 10, 10);
		        LocalDate hoy = LocalDate.now();

		        long diasDesdeUltimoCambio = ChronoUnit.DAYS.between(ultimaCambio, hoy);
		        
		        if (diasDesdeUltimoCambio >= 15) {
		            int respuesta = JOptionPane.showConfirmDialog(frame, "La contraseña se cambió hace más de 15 días. ¿Seguro que deseas cambiarla ahora?",
		                    "Confirmación de Cambio de Contraseña", JOptionPane.YES_NO_OPTION);

		            if (respuesta == JOptionPane.YES_OPTION) {
		                // Código para cambiar la contraseña.
		            	String nuevaContrasena = JOptionPane.showInputDialog(frame, "Introduce la nueva contrasena");
		            	if(nuevaContrasena != null && !nuevaContrasena.isEmpty()) {
		            		usuario.cambiarContrasena(nuevaContrasena);
		            		ultimaCambio = LocalDate.now(); // Actualizar la fecha
		            		JOptionPane.showMessageDialog(frame, "Contraseña cambiada exitosamente.");   
		            	}else {
		            		JOptionPane.showMessageDialog(frame, "Error al cambiar contraseña, vuelve a intentarlo.");
		            	}
		                
		            }
		        } else {
		            JOptionPane.showMessageDialog(frame, "La contraseña solo se puede cambiar una vez cada 15 días.");
		        }
				
			}
		});

	    frame.pack();
	    frame.setVisible(true);
	}	
	public static void main(String[] args) {
		Usuario usuarioNormal = new Usuario("Lucas Gomez Lopez", "lucas.gomez@gmail.com", "Usuario Corriente", "12345678");
		
		VentanaPerfilUsuario vent = new VentanaPerfilUsuario(usuarioNormal);
		
	}

}
