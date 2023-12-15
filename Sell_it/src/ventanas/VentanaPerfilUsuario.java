package ventanas;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.filechooser.FileNameExtensionFilter;

import BasesDeDatos.BaseDeDatos;
import clases.Notificacion;
import clases.Usuario;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.List;


public class VentanaPerfilUsuario extends JFrame{

//	private JPanel panelBotones;
//	private JPanel panelPrincipal;
//	private JPanel panelInformacion;
	private JLabel lblFotoPerfil;
	private JTextField nameField;
	private JTextField emailField;
	private ImageIcon imagenPerfil;
	private JTextArea descriptionArea;
	
	private Usuario usuario;
	private LocalDate ultimoCambioContrasena;
	private List<String> entradasCompradas;
	
	public VentanaPerfilUsuario(Usuario usuario, List<String> entradasCompradas) {
		
		this.entradasCompradas = entradasCompradas;
		this.usuario = usuario;
		ultimoCambioContrasena = LocalDate.now(); //para provar ahora, que sea la fecha actual
		
		BaseDeDatos base = new BaseDeDatos();

		JFrame frame = new JFrame("Perfil Usuario");
	    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    frame.setLayout(new BorderLayout());

	    // Parte superior: nombre, correo y botones de información
	    JPanel topPanel = new JPanel(new FlowLayout());
	    
		lblFotoPerfil = new JLabel();
	    if(usuario.getImgPerfil() == null) {
	    	imagenPerfil = new ImageIcon("Sell_it/src/imagenes/perfil.png"); // Ruta de la imagen de perfil
	    	fotoPerfil(imagenPerfil);
	    }else {
	    	String rutaImg = usuario.getImgPerfil();
	    	imagenPerfil = new ImageIcon(rutaImg);
			usuario.setImgPerfil(rutaImg);
			fotoPerfil(imagenPerfil);
	    }
	    
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
	    descriptionArea = new JTextArea("Ingresa información util sobre ti para completar tu perfil en la aplicación...", 5, 10);
	    descriptionArea.setMargin(new java.awt.Insets(10, 10, 10, 10));
	    JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);
	    descriptionArea.setLineWrap(true);
	    descriptionArea.setWrapStyleWord(true);
	    descriptionArea.setEditable(false);
	    
	    // Parte inferior: más botones
	    JPanel bottomPanel = new JPanel();
	    JButton buttonContrasena = new JButton("Cambiar contraseña");
	    JButton buttonEditar = new JButton("Editar Perfil");
	    JButton buttonProductosCompados = new JButton("Mis compras");
        JButton botonVentanaP = new JButton("Ventana Principal");
        
        botonVentanaP.setBackground(Color.LIGHT_GRAY);        
        //Personalizar la letra del boton
        Font font = new Font("Montserrat", Font.BOLD, 14);
        botonVentanaP.setFont(font);
        
	    bottomPanel.add(buttonContrasena);
	    bottomPanel.add(buttonEditar);
	    bottomPanel.add(buttonProductosCompados);
        bottomPanel.add(botonVentanaP);
	    
	    frame.add(topPanel, BorderLayout.NORTH);
	    frame.add(descriptionScrollPane, BorderLayout.CENTER);
	    frame.add(bottomPanel, BorderLayout.SOUTH);

	    
	    infoButton1.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	    //        JOptionPane.showMessageDialog(frame, "En estos momentos no tienes ningun articulo en venta");
//	        	VentanaTablaInformacion infV = new VentanaTablaInformacion();
//	        	infV.setVisible(true);
	        }
	    });
	    
	    botonVentanaP.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				VentanaPrincipal v = new VentanaPrincipal();
			}
		});
	    
	    buttonContrasena.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
		        LocalDate hoy = LocalDate.now();
		    //    LocalDate ultimaCambio = LocalDate.of(2023, 10, 10);
		        
		        if(usuario.getUltimaCambioContrasena() != null) {
		        	long diasDesdeUltimoCambio = ChronoUnit.DAYS.between(usuario.getUltimaCambioContrasena(), hoy);
			        System.out.println(diasDesdeUltimoCambio);
			        if (diasDesdeUltimoCambio >= 15) {
			            int respuesta = JOptionPane.showConfirmDialog(frame, "La contraseña se cambió hace más de 15 días. ¿Seguro que deseas cambiarla ahora?",
			                    "Confirmación de Cambio de Contraseña", JOptionPane.YES_NO_OPTION);

			            
			            if (respuesta == JOptionPane.YES_OPTION) {
			                // Código para cambiar la contraseña.
			                String nuevaContrasena = JOptionPane.showInputDialog(frame, "Introduce la nueva contrasena");
			                if (nuevaContrasena != null && !nuevaContrasena.isEmpty()) {
			                    usuario.cambiarContrasena(nuevaContrasena);

			                    // Actualizar la contraseña en la base de datos
			                    BaseDeDatos base = new BaseDeDatos();
			                    base.modificarUsuarioYaRegistradoContrasena(usuario);

			                    JOptionPane.showMessageDialog(frame, "Contraseña cambiada exitosamente.");
			                			                    
			                } else {
			                    JOptionPane.showMessageDialog(frame, "Error al cambiar contraseña, vuelve a intentarlo.");
			                }
			            }
/*
			            if (respuesta == JOptionPane.YES_OPTION) {
			                // Código para cambiar la contraseña.
			            	String nuevaContrasena = JOptionPane.showInputDialog(frame, "Introduce la nueva contrasena");
			            	if(nuevaContrasena != null && !nuevaContrasena.isEmpty()) {
			            		usuario.cambiarContrasena(nuevaContrasena);
			           // 		ultimaCambio = LocalDate.now(); // Actualizar la fecha
			            		
//			            		String nuevoNombre = nameField.getText();
//			     		        String nuevoCorreo = emailField.getText();
//			    		        Usuario usuarioActualizado = new Usuario(nuevoNombre, nuevoCorreo, "tipoUsuario", nuevaContrasena);
//			    		        
//			    		        BaseDeDatos.main(null);
//			    				BaseDeDatos base = new BaseDeDatos();
//			    				base.modificarUsuarioYaRegistrado(usuarioActualizado);
		            		
			            		JOptionPane.showMessageDialog(frame, "Contraseña cambiada exitosamente.");   
			            	}else {
			            		JOptionPane.showMessageDialog(frame, "Error al cambiar contraseña, vuelve a intentarlo.");
			            	}
			                
			            }
			            
	*/		        } else {
			            JOptionPane.showMessageDialog(frame, "La contraseña solo se puede cambiar una vez cada 15 días.");
			        }
		        }
	
			}
		});
	    
	    JButton botonGuardarCambios = new JButton("Guardar cambios");
	    botonGuardarCambios.setVisible(false);
	    botonGuardarCambios.setBackground(Color.gray);
	    botonGuardarCambios.setForeground(Color.black);
	    
	    JButton botonCambiarFoto = new JButton("Cambiar foto de perfil");
	    botonCambiarFoto.setVisible(false);
	    
	    buttonEditar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
		        // Restablece la edición de los campos
				nameField.setEditable(true);
		        setEditableDescripcion(true);
	            botonCambiarFoto.setVisible(true);
	            // Quitamos botones para que no haya demasiados a la vez
	            botonVentanaP.setVisible(false);
	            buttonContrasena.setVisible(false);
	            buttonEditar.setVisible(false);
	            buttonProductosCompados.setVisible(false);
	            // Después de editar, habilitamos el botón "Guardar Cambios"
	            botonGuardarCambios.setVisible(true);
			}
		});
	    
	    botonCambiarFoto.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

	            JFileChooser chooser = new JFileChooser();
	            FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos imagen (.jpg, .png)", "jpg", "png");
	            chooser.setFileFilter(filtro);
	            int result = chooser.showOpenDialog(frame);
	            if (result == JFileChooser.APPROVE_OPTION) {
	                File selectedFile = chooser.getSelectedFile();
	                String rutaImg = selectedFile.getAbsolutePath();
	                
	                // Imprimir la ruta de la imagen para verificar
	                System.out.println("Ruta de la imagen seleccionada: " + rutaImg);
	          	                
	                usuario.setImgPerfil(rutaImg);
	                imagenPerfil = new ImageIcon(rutaImg);
	                fotoPerfil(imagenPerfil);
	             // Actualizar la ruta de la imagen en la base de datos
	                BaseDeDatos base = new BaseDeDatos();
	                base.modificarUsuarioImagenPerfil(usuario);
	            }else {
            		JOptionPane.showMessageDialog(frame, "Error al cambiar imagen, vuelve a intentarlo.");
	            }
			}
		});
	    
	    botonGuardarCambios.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String NomNuevo = nameField.getText();
				String imagen = usuario.getImgPerfil();

				// Crea un objeto Usuario con los datos actualizados
				Usuario usuarioActualizado = new Usuario(NomNuevo, usuario.getCorreoUsuario(), "tipoUsuario", usuario.getContrasena(), imagen);

				String nuevaDescripcion = descriptionArea.getText();
		        descriptionArea.setText(nuevaDescripcion);
		        setEditableDescripcion(false);

				
//			    // Llama al método para modificar el usuario en la base de datos
				BaseDeDatos base = new BaseDeDatos();
				base.modificarUsuarioYaRegistrado(usuarioActualizado);
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
				botonGuardarCambios.setVisible(false);
				botonCambiarFoto.setVisible(false);
				// Volvemos a poner los botones
				botonVentanaP.setVisible(true);
	            buttonContrasena.setVisible(true);
	            buttonEditar.setVisible(true);
	            buttonProductosCompados.setVisible(true);
			}
		});
	    bottomPanel.add(botonGuardarCambios);
	    bottomPanel.add(botonCambiarFoto);
   
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
//	    		// Simulemos que aquí se obtienen notificaciones del sistema o de otros usuarios.
//	    		List<String> notificaciones = obtenerNotificaciones();
//
//	    		// Construir un mensaje de notificación a partir de las notificaciones.
//	    		StringBuilder notificacionMessage = new StringBuilder();
//	    		notificacionMessage.append("Notificaciones:\n");
//	    		for (String notificacion : notificaciones) {
//	    			notificacionMessage.append("- ").append(notificacion).append("\n");
//	    		}
	    		mostrarNotificaciones();
	    	}
	    });
	    

	    frame.pack();
	    frame.setVisible(true);
	}	
	private void setEditableDescripcion(boolean editable) {
		descriptionArea.setEditable(editable);
	}
	
//	private List<String> obtenerNotificaciones() {
//	   
//	    List<String> notificaciones = new ArrayList<>();
//	    notificaciones.add("Nueva oferta para tu producto.");
//	    notificaciones.add("¡Has vendido un artículo!");
//	    notificaciones.add("Nuevo mensaje de un comprador interesado.");
//	    return notificaciones;
//	}
	private void mostrarNotificaciones() {
    	usuario.cargarNotificacionesDesdeBD();
        List<Notificacion> notificaciones = usuario.getNotificaciones();
        StringBuilder notificacionMessage = new StringBuilder();
        notificacionMessage.append("Notificaciones:\n");
        for (Notificacion notificacion : notificaciones) {
        	if(notificacion.isLeido() == false) {
                notificacionMessage.append("- ").append(notificacion.getMensaje()).append("\n");
        	}
        }
        JOptionPane.showMessageDialog(this, notificacionMessage.toString(), "Notificaciones", JOptionPane.INFORMATION_MESSAGE);
        
        for (Notificacion notificacion : notificaciones) {
        	if (notificacion.isLeido() == false) {
                notificacion.setLeido(true);
                int id = notificacion.getId();
                Main.getVentanaInicio().base.marcarLeidoBD(id, usuario.getCorreoUsuario());
            }
       }
    }
	
	private void fotoPerfil(ImageIcon imagenPerfil) {
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
	}
	
	public static void main(String[] args) {
		
//		List<String> entradasCompradas = new ArrayList<>();
//		VentanaInicio ventanaI = Main.getVentanaInicio();
//		Usuario usuActual = ventanaI.getUsuarioActual();
//    	Usuario usuarioNormal = new Usuario(usuActual.getNombreUsuario(), usuActual.getCorreoUsuario(), usuActual.getTipoUsuario(), usuActual.getContrasena());
//		
//    	VentanaPerfilUsuario vent = new VentanaPerfilUsuario(usuarioNormal, entradasCompradas);

	}

}
