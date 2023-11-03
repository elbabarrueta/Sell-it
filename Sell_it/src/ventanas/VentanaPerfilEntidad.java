package ventanas;

import javax.swing.*;

import clases.Usuario;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class VentanaPerfilEntidad {
	
	private Usuario usuario;
	private LocalDate ultimoCambioContrasena;
	private List<String> entradasCompradas;
	
	public VentanaPerfilEntidad(Usuario usuario) {
		
		this.entradasCompradas = entradasCompradas;
		this.usuario = usuario;
		ultimoCambioContrasena = LocalDate.now();
		
        JFrame frame = new JFrame("Perfil entidad");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Parte superior: nombre, correo y botones de información
        JPanel topPanel = new JPanel(new FlowLayout());
        JLabel nameLabel = new JLabel("Nombre:");
        JTextField nameField = new JTextField(20);
        JLabel emailLabel = new JLabel("Correo:");
        JTextField emailField = new JTextField(20);
        nameField.setText(usuario.getNombreUsuario());
		emailField.setText(usuario.getCorreoUsuario());
		nameField.setEditable(false);
	    emailField.setEditable(false);
        
        JButton infoButton1 = new JButton("En venta");
        JButton infoButton2 = new JButton("Valoraciones");
        JButton infoButton3 = new JButton("Notificaciones");
        topPanel.add(nameLabel);
        topPanel.add(nameField);
        topPanel.add(emailLabel);
        topPanel.add(emailField);
        topPanel.add(infoButton1);
        topPanel.add(infoButton2);
        topPanel.add(infoButton3);

        // Parte central: descripción del usuario
        JTextArea descriptionArea = new JTextArea(5, 20);
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);

        // Parte inferior: más botones
        JPanel bottomPanel = new JPanel();
        JButton botonContrasena = new JButton("Cambiar Contraseña");
        JButton botonEditar = new JButton("Editar Perfil");
        JButton botonCompras = new JButton("Compras");
        bottomPanel.add(botonContrasena);
        bottomPanel.add(botonEditar);
        bottomPanel.add(botonCompras);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(descriptionScrollPane, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Agregar acción al botón de información 1
        infoButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "En estos momentos no tienes ningún artículo en venta");
            }
        });
        
        botonContrasena.addActionListener(new ActionListener() {
			
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
	    
	    botonEditar.addActionListener(new ActionListener() {
			
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
        

        frame.pack();
        frame.setVisible(true);
        
	}
	
    public static void main(String[] args) {
    	Usuario usuarioEntidad = new Usuario("Discoteca", "discoteca@gmail.com", "Usuario Entidad", "12345678");
    	VentanaPerfilEntidad vEntidad = new VentanaPerfilEntidad(usuarioEntidad);
    }
}

