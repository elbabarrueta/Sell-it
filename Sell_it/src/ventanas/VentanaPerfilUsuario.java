package ventanas;

import javax.swing.*;


import clases.Usuario;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.List;


public class VentanaPerfilUsuario extends JFrame{

	private JPanel panelBotones;
	private JPanel panelPrincipal;
	private JPanel panelInformacion;
	private JLabel lblFotoPerfil;
	private JTextField nameField;
	private JTextField emailField;
	
	private Usuario usuario;
	private LocalDate ultimoCambioContrasena;
	private List<String> entradasCompradas;
	
	public VentanaPerfilUsuario(Usuario usuario, List<String> entradasCompradas) {
		
		this.entradasCompradas = entradasCompradas;
		this.usuario = usuario;
		ultimoCambioContrasena = LocalDate.now(); //para provar ahora, que sea la fecha actual
		
		JFrame frame = new JFrame("Perfil Usuario");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setLayout(new BorderLayout());

	    // Parte superior: nombre, correo y botones de información
	    JPanel topPanel = new JPanel(new FlowLayout());
	    
	    lblFotoPerfil = new JLabel();
        ImageIcon imagenPerfil = new ImageIcon(VentanaPerfilUsuario.class.getResource("perfil1.png")); // Ruta de la imagen de perfil
        
        int maxWidth = 100; // Tamaño máximo de ancho
        int maxHeight = 100; // Tamaño máximo de alto
        int newWidth, newHeight;
        Image img = imagenPerfil.getImage();
        if (imagenPerfil.getIconWidth() > imagenPerfil.getIconHeight()) {
            newWidth = maxWidth;
            newHeight = (maxWidth * imagenPerfil.getIconHeight()) / imagenPerfil.getIconWidth();
        } else {
            newHeight = maxHeight;
            newWidth = (maxHeight * imagenPerfil.getIconWidth()) / imagenPerfil.getIconHeight();
        }
        // Redimensiona la imagen
        Image newImg = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        imagenPerfil = new ImageIcon(newImg);
        
        lblFotoPerfil.setIcon(imagenPerfil);
            
	    
	    JLabel nameLabel = new JLabel("Nombre:");
	    nameField = new JTextField(20);
	    JLabel emailLabel = new JLabel("Correo:");
	    emailField = new JTextField(20);
	    nameField.setText(usuario.getNombreUsuario());
		emailField.setText(usuario.getCorreoUsuario());
		nameField.setEditable(false);
	    emailField.setEditable(false);
	    
	    JButton infoButton1 = new JButton("En venta");
	    JButton infoButton2 = new JButton("Valoraciones");
	    JButton infoButton3 = new JButton("Notificaciones");
	    topPanel.add(lblFotoPerfil);
	    topPanel.add(nameLabel);
	    topPanel.add(nameField);
	    topPanel.add(emailLabel);
	    topPanel.add(emailField);
	    topPanel.add(infoButton1);
	    topPanel.add(infoButton2);
	    topPanel.add(infoButton3);

	    // Parte central: descripción del usuario
	    JTextArea descriptionArea = new JTextArea(5, 10);
	    JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);
	    descriptionArea.setEditable(true);
	//    descriptionArea.setPreferredSize(new Dimension(200, 100));
	    
	    // Parte inferior: más botones
	    JPanel bottomPanel = new JPanel();
	    JButton buttonContrasena = new JButton("Cambiar contraseña");
	    JButton buttonEditar = new JButton("Editar Perfil");
	    JButton buttonProductosCompados = new JButton("Mis compras");
	    bottomPanel.add(buttonContrasena);
	    bottomPanel.add(buttonEditar);
	    bottomPanel.add(buttonProductosCompados);

	    frame.add(topPanel, BorderLayout.NORTH);
	    frame.add(descriptionScrollPane, BorderLayout.CENTER);
	    frame.add(bottomPanel, BorderLayout.SOUTH);

	    
	    infoButton1.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            JOptionPane.showMessageDialog(frame, "En estos momentos no tienes ningun articulo en venta");
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
	    
	    JButton botonGuardarCambios = new JButton("Guardar cambios");
	    botonGuardarCambios.setVisible(false);
	    botonGuardarCambios.setBackground(Color.gray);
	    botonGuardarCambios.setForeground(Color.black);
	    
	    buttonEditar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				nameField.setEditable(true);
	            emailField.setEditable(true);
	         // Después de editar, habilitamos el botón "Guardar Cambios"
	            botonGuardarCambios.setVisible(true);
			}
		});
	    
	    botonGuardarCambios.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String NomNuevo = nameField.getText();
				String CorreoNuevo = emailField.getText();
				usuario.setNombreUsuario(NomNuevo);
				usuario.setCorreoUsuario(CorreoNuevo);	
/*				
// hacer algo asi pero con el MAPA de USUARIOS				
				 // Buscar al usuario en la lista y actualizar sus datos
                for (Usuario usuarioEnLista : usuariosBase) {
                    if (usuarioEnLista.getNombreUsuario().equals(usuario.getNombreUsuario())) {
                        usuarioEnLista.setNombreUsuario(nuevoNombre);
                        usuarioEnLista.setCorreoUsuario(nuevoCorreo);
                        break; // Terminar la búsqueda una vez que se haya encontrado el usuario
                    }
                }
*/				
				nameField.setEditable(false);
				emailField.setEditable(false);
				botonGuardarCambios.setVisible(false);
			}
		});
	    bottomPanel.add(botonGuardarCambios);
   
	    buttonProductosCompados.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
		//		 String entrada = JOptionPane.showInputDialog(frame, "Introduce el nombre de la entrada comprada:");
		//	        if (entrada != null && !entrada.isEmpty()) {
		//	            entradasCompradas.add(entrada);
			            // Actualizar la lista de entradas compradas antes de mostrar la nueva ventana
			            VentanaEntradasCompradas entradasC = new VentanaEntradasCompradas(entradasCompradas);
			            entradasC.setVisible(true);
		//	        }
			}
		});
	    

	    infoButton3.addActionListener(new ActionListener() {
	    	@Override
	    	public void actionPerformed(ActionEvent e) {
	    		// Simulemos que aquí se obtienen notificaciones del sistema o de otros usuarios.
	    		List<String> notificaciones = obtenerNotificaciones();

	    		// Construir un mensaje de notificación a partir de las notificaciones.
	    		StringBuilder notificacionMessage = new StringBuilder();
	    		notificacionMessage.append("Notificaciones:\n");
	    		for (String notificacion : notificaciones) {
	    			notificacionMessage.append("- ").append(notificacion).append("\n");
	    		}

        
	    		JOptionPane.showMessageDialog(frame, notificacionMessage.toString(), "Notificaciones", JOptionPane.INFORMATION_MESSAGE);
	    	}
	    });
	    

	    frame.pack();
	    frame.setVisible(true);
	}	
	
	private List<String> obtenerNotificaciones() {
	   
	    List<String> notificaciones = new ArrayList<>();
	    notificaciones.add("Nueva oferta para tu producto.");
	    notificaciones.add("¡Has vendido un artículo!");
	    notificaciones.add("Nuevo mensaje de un comprador interesado.");
	    return notificaciones;
	}
	
	public static void main(String[] args) {
		Usuario usuarioNormal = new Usuario("Lucas Gomez Lopez", "lucas.gomez@gmail.com", "Usuario Corriente", "12345678");
		List<String> entradasCompradas = new ArrayList<>();
		
		VentanaPerfilUsuario vent = new VentanaPerfilUsuario(usuarioNormal, entradasCompradas);
		
	}

}
