package ventanas;

import javax.swing.*;



import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import BasesDeDatos.BaseDeDatos;
import clases.*;

import org.mindrot.jbcrypt.BCrypt;

import BasesDeDatos.BaseDeDatos;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class VentanaPerfilEntidad extends JFrame{
	
	private Usuario usuario;
	private LocalDate ultimoCambioContrasena;
	private List<String> entradasCompradas;
	private JLabel lblFotoPerfil;
	private JTextArea descriptionArea;
	private ImageIcon imagenPerfil;
	private JPanel contentPane;
	
	public VentanaPerfilEntidad(Usuario usuario) {
		
		this.entradasCompradas = new ArrayList<>();
		this.usuario = usuario;
		ultimoCambioContrasena = LocalDate.now();
		
        this.setTitle("Perfil entidad");
        this.setBounds(100, 100, 641, 484);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(6, 5, 5, 5));
		contentPane.setLayout(null);
        this.setContentPane(contentPane);
     // Añadir esta línea para centrar la ventana
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        

        
	    lblFotoPerfil = new JLabel();
	    lblFotoPerfil.setBounds(38, 10, 119, 95);
		contentPane.add(lblFotoPerfil);
	    if(usuario.getImgPerfil() == null) {
	        ImageIcon imagenPerfil = new ImageIcon("Sell_it/src/imagenes/perfilE.png"); // Ruta de la imagen de perfil
	    	fotoPerfil(imagenPerfil);
	    }else {
	    	String rutaImg = usuario.getImgPerfil();
	    	imagenPerfil = new ImageIcon(rutaImg);
			usuario.setImgPerfil(rutaImg);
			fotoPerfil(imagenPerfil);
	    }        
        	    		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNombre.setBounds(38, 115, 84, 49);
		contentPane.add(lblNombre);
		
		JLabel lblCorreo = new JLabel("Correo:");
		lblCorreo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblCorreo.setBounds(38, 165, 73, 28);
		contentPane.add(lblCorreo);
		
		JTextField nameField = new JTextField();
		nameField.setBounds(117, 127, 195, 30);
		contentPane.add(nameField);
		nameField.setColumns(10);
		
		JTextField txtCorreo = new JTextField();
		txtCorreo.setBounds(117, 163, 195, 30);
		contentPane.add(txtCorreo);
		txtCorreo.setColumns(10);
		
		nameField.setText(usuario.getNombreUsuario());
		txtCorreo.setText(usuario.getCorreoUsuario());
		nameField.setEditable(false);
		txtCorreo.setEditable(false);
		
		JButton buttonContrasena = new JButton("Cambiar Contraseña");
		buttonContrasena.setFont(new Font("Tahoma", Font.PLAIN, 15));
		buttonContrasena.setBounds(38, 256, 167, 30);
		contentPane.add(buttonContrasena);
		
		JButton botonEditar = new JButton("Editar Perfil");
		botonEditar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		botonEditar.setBounds(38, 216, 167, 30);
		contentPane.add(botonEditar);
		
		JButton botonCompras = new JButton("Mis compras");
		botonCompras.setFont(new Font("Tahoma", Font.PLAIN, 15));
		botonCompras.setBounds(382, 78, 167, 28);
		contentPane.add(botonCompras);
		
		JButton botonVentanaP = new JButton("Volver atras");
		botonVentanaP.setFont(new Font("Tahoma", Font.PLAIN, 15));
		botonVentanaP.setBounds(464, 399, 140, 28);
		contentPane.add(botonVentanaP);
		
		JTextArea descriptionArea = new JTextArea();
		descriptionArea.setEditable(false);
		descriptionArea.setWrapStyleWord(true);
		descriptionArea.setLineWrap(true);
		descriptionArea.setText(BaseDeDatos.getDescripcionUsu(usuario));
		descriptionArea.setBounds(232, 210, 372, 179);
		contentPane.add(descriptionArea);
		
		JButton botonEnVenta = new JButton("Articulos En Venta");
		botonEnVenta.setFont(new Font("Tahoma", Font.PLAIN, 15));
		botonEnVenta.setBounds(382, 115, 167, 30);
		contentPane.add(botonEnVenta);
		
		JButton btnNotificaciones = new JButton("Notificaciones");
		btnNotificaciones.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNotificaciones.setBounds(382, 40, 167, 30);
		contentPane.add(btnNotificaciones);
		
		JButton btnValoraciones = new JButton("Valoraciones");
		btnValoraciones.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnValoraciones.setBounds(382, 155, 167, 28);
		contentPane.add(btnValoraciones);
		
		btnValoraciones.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				
				// Obtén valoraciones del usuario
			    List<Valoracion> valoracionesUsuario = BaseDeDatos.obtenerValoracionesPorUsuario(usuario);
			    // Crear la nueva ventana con la tabla de valoraciones
			    VentanaTablaValoraciones ventanaValoraciones = new VentanaTablaValoraciones(valoracionesUsuario, usuario);
			    ventanaValoraciones.setVisible(true);
			}
		});
		botonCompras.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				VentanaMisCompras vc = new VentanaMisCompras(usuario);
				vc.setVisible(true);
			}
		});
        botonEnVenta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	dispose();
                mostrarEventosEnVentaDelUsuario(usuario);
            }
        });
        
        botonVentanaP.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				VentanaPrincipal v = new VentanaPrincipal();
			}
		});
        btnNotificaciones.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				obtenerNotificaciones();
			}
		});
        
        buttonContrasena.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Botón de cambiar contraseña presionado.");

		        // Verificar en la base de datos la fecha de último cambio de contraseña
		        BaseDeDatos base = new BaseDeDatos();
		        LocalDate ultimaCambioDesdeBD = base.obtenerUltimoCambioContrasena(usuario);

		        if (ultimaCambioDesdeBD != null) {
		            LocalDate hoy = LocalDate.now();
		            long diasDesdeUltimoCambio = ChronoUnit.DAYS.between(ultimaCambioDesdeBD, hoy);
		            System.out.println("Días desde el último cambio: " + diasDesdeUltimoCambio);

		            if (diasDesdeUltimoCambio >= 15) {
		                int respuesta = JOptionPane.showConfirmDialog(VentanaPerfilEntidad.this,
		                        "La contraseña se cambió hace más de 15 días. ¿Seguro que deseas cambiarla ahora?",
		                        "Confirmación de Cambio de Contraseña", JOptionPane.YES_NO_OPTION);

		                if (respuesta == JOptionPane.YES_OPTION) {
		                    try {
		                        // Código para cambiar la contraseña.
		                        String nuevaContrasena = JOptionPane.showInputDialog(VentanaPerfilEntidad.this,
		                                "Introduce la nueva contraseña");

		                        if (nuevaContrasena != null && !nuevaContrasena.isEmpty()) {
		                            usuario.cambiarContrasena(nuevaContrasena);
		                            // Hash de la nueva contraseña
		                            String hashNuevaContrasena = BCrypt.hashpw(nuevaContrasena, BCrypt.gensalt());

		                            // Cambiar la contraseña y actualizar la fecha en el usuario
		                            usuario.cambiarContrasena(hashNuevaContrasena);
		                            usuario.setUltimaCambioContrasena(LocalDate.now());
		                            // Actualizar la contraseña y la fecha en la base de datos
		                            base.modificarUsuarioYaRegistradoContrasena(usuario);

		                            JOptionPane.showMessageDialog(VentanaPerfilEntidad.this,
		                                    "Contraseña cambiada exitosamente.");
		                        } else {
		                            System.out.println("Contraseña vacía o cancelada.");
		                        }
		                    } catch (Exception ex) {
		                        ex.printStackTrace();
		                        JOptionPane.showMessageDialog(VentanaPerfilEntidad.this,
		                                "Error al cambiar contraseña: " + ex.getMessage());
		                    }
		                }
		            } else {
		                JOptionPane.showMessageDialog(VentanaPerfilEntidad.this,
		                        "La contraseña solo se puede cambiar una vez cada 15 días. \nDías desde el último cambio: " + diasDesdeUltimoCambio);
		            }
		        } else {
		            System.out.println("No se pudo obtener la fecha de último cambio desde la base de datos.");
		        }
		    }
		});
        
        JPanel panelBotones = new JPanel();
        panelBotones.setBounds(38, 296, 167, 141);
		contentPane.add(panelBotones);
		
        JButton botonGuardarCambios = new JButton("Guardar cambios");
	    botonGuardarCambios.setVisible(false);
	    botonGuardarCambios.setBackground(Color.gray);
	    botonGuardarCambios.setForeground(Color.black);
	    
	    JButton botonCambiarFoto = new JButton("Cambiar foto de perfil");
	    botonCambiarFoto.setVisible(false);
	    
	    botonEditar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				nameField.setEditable(true);
				descriptionArea.setEditable(true);
	            botonCambiarFoto.setVisible(true);
	            // Quitamos botones para que no haya demasiados a la vez
	            botonVentanaP.setVisible(false);
	            buttonContrasena.setVisible(false);
	            botonEditar.setVisible(false);
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
	            int result = chooser.showOpenDialog(VentanaPerfilEntidad.this);
	            if (result == JFileChooser.APPROVE_OPTION) {
	                File selectedFile = chooser.getSelectedFile();
	                String rutaImg = selectedFile.getAbsolutePath();
	                
	                // Imprimir la ruta de la imagen para verificar
	                System.out.println("Ruta de la imagen seleccionada: " + rutaImg);
	                
	                usuario.setImgPerfil(rutaImg);	// ¡Importante! Establecer la nueva ruta de la imagen en el usuario
	                ImageIcon imagenPerfil = new ImageIcon(rutaImg);
	                fotoPerfil(imagenPerfil);
	             // Actualizar la ruta de la imagen en la base de datos
	                BaseDeDatos base = new BaseDeDatos();
	                base.modificarUsuarioImagenPerfil(usuario);
	            }else {
            		JOptionPane.showMessageDialog(VentanaPerfilEntidad.this, "Error al cambiar imagen, vuelve a intentarlo.");
	            }
			}
		});
	    
	    botonGuardarCambios.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String NomNuevo = nameField.getText();
				String imagen = usuario.getImgPerfil();
				String descripcion = descriptionArea.getText();
				// Crea un objeto Usuario con los datos actualizados
				Usuario usuarioActualizado = new Usuario(NomNuevo, usuario.getCorreoUsuario(), "tipoUsuario", usuario.getContrasena(), imagen, descripcion);
				
//			    // Llama al método para modificar el usuario en la base de datos
				BaseDeDatos base = new BaseDeDatos();
//				base.modificarUsuarioYaRegistrado(usuario);
				base.modificarUsuarioYaRegistrado(usuarioActualizado);
				base.modificarDescripcionUsuario(usuarioActualizado);
			
				nameField.setEditable(false);
				botonGuardarCambios.setVisible(false);
				botonCambiarFoto.setVisible(false);
				// Volvemos a poner los botones
				botonVentanaP.setVisible(true);
				buttonContrasena.setVisible(true);
	            botonEditar.setVisible(true);
	            botonCompras.setVisible(true);
			}
		});
	    panelBotones.add(botonGuardarCambios);
        panelBotones.add(botonCambiarFoto);

        this.setVisible(true);
        
	}
	private void obtenerNotificaciones() {
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
	
	public static void mostrarEventosEnVentaDelUsuario(Usuario usuario) {
		// Obtén eventos en venta del usuario
        List<Evento> eventosEnVentaDelUsuario = BaseDeDatos.obtenerEventosEnVentaDelUsuario(usuario);

        // Muestra la ventana con los eventos en venta del usuario
        VentanaTablaInformacion ventanaTabla = new VentanaTablaInformacion(eventosEnVentaDelUsuario, usuario);
        ventanaTabla.setVisible(true);
    }
	
    public static void main(String[] args) {

    }
}

