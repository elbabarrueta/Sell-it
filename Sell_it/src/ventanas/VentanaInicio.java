package ventanas;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.HashMap;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import clases.Usuario;
import datos.DataSetUsuario;

public class VentanaInicio extends JFrame {
	
	/**
	 * 
	 */
	private static DataSetUsuario dataSetUsuario;
	private Usuario usuarioActual;
	
	private static CustomPasswordField txtContrasenia;
	
	private static final long serialVersionUID = 1L;

	public VentanaInicio() {
		super();
		
		//Características de la ventana principal
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setBounds(500,300, 500, 250);
		this.setTitle("Sell-It");
		
		//Ceracion de paneles 
		JPanel panelVentanaInicio = new JPanel(new BorderLayout());
		JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel panelNorte = new JPanel(new BorderLayout());
		JPanel panelCentro = new JPanel(new GridLayout(3,2));
		
		panelVentanaInicio.add(panelSur, BorderLayout.SOUTH);
		panelVentanaInicio.add(panelNorte, BorderLayout.NORTH);
		panelVentanaInicio.add(panelCentro,BorderLayout.CENTER);
		
		panelNorte.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelSur.setLayout(new FlowLayout(FlowLayout.CENTER));

		
		this.getContentPane().add(panelVentanaInicio);

		//Creacion de los JTextFields, JLabels, JButtons y JPasswordField
		
		JTextField txtUsuario = new JTextField();
		txtContrasenia = new CustomPasswordField();
		ImageIcon imagenOjo = new ImageIcon("Sell_it/src/imagenes/eye_closed_icon.png");
		txtContrasenia.getButton().setIcon(fotoBoton(imagenOjo));
		txtContrasenia.getButton().setBackground(Color.WHITE);
		txtContrasenia.setEchoChar('*');
		JLabel etiquetaUsuario = new JLabel("Correo:");
		JLabel etiquetaContrasenia = new JLabel("Contraseña:");

		JLabel mostrarContra = new JLabel("Mostrar Contraseña");		
		mostrarContra.addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseClicked(MouseEvent e) {
	                mostrarOcultarContraseña();
	            }
	    });
		
		JButton botonRegistroEntidad = new JButton("Registro Entidad");
		JButton botonRegistroUsuario = new JButton("Registro Usuario");
		JButton botonIniciarSesion = new JButton("Iniciar Sesion");
		
		JLabel etiquetaBienvenido = new JLabel("Bienvenido a Sell-It");
		
		JLabel etiquetaPregunta = new JLabel("¿No tienes cuenta?");
		
		
		//Añadimos los elementos previamente creados a los paneles
		
		panelNorte.add(etiquetaBienvenido,BorderLayout.NORTH);
		panelSur.add(etiquetaPregunta);
		panelSur.add(botonRegistroEntidad);
		panelSur.add(botonRegistroUsuario);
	//	panelSur.add(botonIniciarSesion);
		
		panelCentro.add(etiquetaUsuario);
		panelCentro.add(txtUsuario);
		panelCentro.add(etiquetaContrasenia);
		panelCentro.add(txtContrasenia);	
		
		JPanel panel = new JPanel();
		panelCentro.add(panel);
		panel.add(botonIniciarSesion);
		//panel.add(mostrarContra);		

		//Eventos
	
		botonRegistroUsuario.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				VentanaRegistroUsuario VEntanaRegistroUsuario = new VentanaRegistroUsuario();
				dispose();
				VEntanaRegistroUsuario.setVisible(true);	
			}
		});
		
		botonRegistroEntidad.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				VentanaRegistroEntidad ventanaRegistroEntidad = new VentanaRegistroEntidad();
				dispose();
				ventanaRegistroEntidad.setVisible(true);
			}
		});
	
		botonIniciarSesion.addActionListener((e)->{
			String correo = txtUsuario.getText();
			String contrasenia = txtContrasenia.getText();
			
	/**		Usuario u = 
			if(u == null) {
				JOptionPane.showMessageDialog(null, "Usuario incorrecto");
			}
			else if(u.getContrasena().equals(contrasenia)) {
				JOptionPane.showMessageDialog(null, "Bienvenido de nuevo "+ u.getNombre());
				//VentaPrincipal.setVisible(true);
			}
	**/

			 if (verificarCredenciales(correo, contrasenia)) {
				 usuarioActual = dataSetUsuario.getMapaUsu().get(correo);
			     JOptionPane.showMessageDialog(null, "Bienvenido de nuevo " + obtenerNombreUsuario(correo));
			     VentanaPrincipal v = new VentanaPrincipal();
			     dispose();
			     v.setVisible(true);
			        // Realiza acciones adicionales cuando el inicio de sesión sea exitoso
			 }  else if (correo.isEmpty() || contrasenia.isEmpty()){
				 JOptionPane.showMessageDialog(null, "Alguno de los campos está vacío "); 
			 } else {
			     JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
			 }
		});
		
	}
	
	public Usuario getUsuarioActual() {
		return usuarioActual;
	}
	
	private void mostrarOcultarContraseña() {
        // Obtener la contraseña actual
        char[] contraseña = txtContrasenia.getPassword();

        // Cambiar el estado de visualización de la contraseña
        if (txtContrasenia.getEchoChar() == 0) {
        	txtContrasenia.setEchoChar('*');
        } else {
        	txtContrasenia.setEchoChar((char) 0);
        }
        txtContrasenia.setText(new String(contraseña));
    }
	
//	private boolean verificarCredenciales(String iD, String contrasenia) {
//		 List<Usuario> usuarios = dataSetUsuario.getUsuariosGuardados(); // Obtén la lista de usuarios cargados desde el archivo
//
//		 for (Usuario usuario : usuarios) {
//			 if (usuario.getNombreUsuario().equals(iD) && usuario.getContrasena().equals(contrasenia)) {
//				 return true; // Las credenciales coinciden
//			 }
//		 }
//		 return false;
//	    
//	}
	
	private boolean  verificarCredenciales (String correo, String contrasenia) {
		 if (dataSetUsuario.getMapaUsu().containsKey(correo)) {
	            Usuario u = dataSetUsuario.getUsuarioPorCorreo(correo);
	            if(u != null) {
	            	if (u.getContrasena().equals(contrasenia)) {
		                return true;
		            } else {
		                return false;
		            }
		        } else {
		            return false;
		        }
	     	}
		 return false;
	            
	}
	
	public String obtenerNombreUsuario(String correo) {
	    if (dataSetUsuario.getMapaUsu().containsKey(correo)) {
	        Usuario usuario = dataSetUsuario.getMapaUsu().get(correo);
	        return usuario.getNombreUsuario();
	    } else {
	        return "Nombre de usuario no encontrado";
	    }
	}

	public static void cargarUsuariosInicio(DataSetUsuario dataset) {
		dataSetUsuario = dataset;
	}
	
	private static ImageIcon fotoBoton(ImageIcon imagenOjo) {
        int maxWidth = 20; // Tamaño máximo de ancho
        int maxHeight = 20; // Tamaño máximo de alto
        int newWidth, newHeight;
        Image img = imagenOjo.getImage();
        if (imagenOjo.getIconWidth() > imagenOjo.getIconHeight()) {
            newWidth = maxWidth;
            newHeight = (maxWidth * imagenOjo.getIconHeight()) / imagenOjo.getIconWidth();
        } else {
            newHeight = maxHeight;
            newWidth = (maxHeight * imagenOjo.getIconWidth()) / imagenOjo.getIconHeight();
        }
        // Redimensiona la imagen
        Image newImg = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        imagenOjo = new ImageIcon(newImg);
        return imagenOjo;
	}
	
	
	private static class CustomPasswordField extends JPasswordField {
        private JButton button;

        public CustomPasswordField() {
            super();
            button = new JButton();
            setLayout(new BorderLayout());
            add(button, BorderLayout.EAST);
            button.setPreferredSize(new Dimension(30, 10));

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    char echoChar = getEchoChar();
                    if (echoChar == 0) {
                        setEchoChar('\u2022'); // Ocultar contraseña (punto negro)
                		ImageIcon imagenOjo = new ImageIcon("Sell_it/src/imagenes/eye_closed_icon.png");
                		txtContrasenia.getButton().setIcon(fotoBoton(imagenOjo));
                    } else {
                		ImageIcon imagenOjo = new ImageIcon("Sell_it/src/imagenes/eye_opened_icon.png");
                		txtContrasenia.getButton().setIcon(fotoBoton(imagenOjo));
                        setEchoChar((char) 0); // Mostrar contraseña
                    }
                }
            });
        }

        public JButton getButton() {
            return button;
        }
    }

}
