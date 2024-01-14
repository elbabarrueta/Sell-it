package ventanas;

import java.awt.BorderLayout;


import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.mindrot.jbcrypt.BCrypt;

import BasesDeDatos.BaseDeDatos;
import clases.Usuario;

public class VentanaInicio extends JFrame {
	
	/**
	 * 
	 */
	private Usuario usuarioActual;
	private static CustomPasswordField txtContrasenia;
	private static final long serialVersionUID = 1L;
	static BaseDeDatos base;
	public static HashMap<String, Usuario> mapaUsu;


	//logger de prueba
    private static Logger logger = Logger.getLogger(VentanaInicio.class.getName());
	
    public VentanaInicio() {
		super();
		
		try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
// logger de prueba
        	logger.log(Level.SEVERE, "Error al configurar el look and feel", e);
        	JOptionPane.showMessageDialog(null, "Error al cargar la ventana: contacta con los informaticos.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }

// logger de prueba
        logger.log(Level.INFO, "Inicializando la ventana de inicio");
		
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
		ImageIcon imagenOjo = new ImageIcon(getClass().getResource("/imagenes/eye_closed_icon.png"));
		txtContrasenia.getButton().setIcon(fotoBoton(imagenOjo));
		txtContrasenia.getButton().setBackground(Color.WHITE);
		txtContrasenia.setEchoChar('\u2022');
		JLabel etiquetaUsuario = new JLabel("Correo:");
		JLabel etiquetaContrasenia = new JLabel("Contraseña:");

		
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
		
		panelCentro.add(etiquetaUsuario);
		panelCentro.add(txtUsuario);
		panelCentro.add(etiquetaContrasenia);
		panelCentro.add(txtContrasenia);	
		
		JPanel panel = new JPanel();
		panelCentro.add(panel);
		panel.add(botonIniciarSesion);
//		
		JLabel politicaEnlace = new JLabel("Política de Privacidad");
        politicaEnlace.setForeground(Color.BLUE); // Color azul para indicar un enlace
        politicaEnlace.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        politicaEnlace.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mostrarPoliticaPrivacidad();
            }
        });
        panel.add(politicaEnlace, BorderLayout.SOUTH);
//
		//Eventos
	
		botonRegistroUsuario.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
//				registrarUsuario();
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
			char[] contraseniaChar = txtContrasenia.getPassword();
	        String contrasenia = new String(contraseniaChar);
			String correo = txtUsuario.getText();

            if (!validarCorreo(correo)) {
                // El correo es válido, puedes realizar acciones adicionales aquí
                JOptionPane.showMessageDialog(null, "Correo con formato válido");
                return;
            }
            
			if (verificarCredenciales(correo, contrasenia)) {
				if (mostrarCondicionesDeUso()) {
					usuarioActual = mapaUsu.get(correo);
					JOptionPane.showMessageDialog(null, "Bienvenido de nuevo " + obtenerNombreUsuario(correo));
					VentanaPrincipal v = new VentanaPrincipal();
					dispose();
//					VentanaLogoPrincipal ventanaLogo = new VentanaLogoPrincipal();
//					ventanaLogo.dispose();
					v.setExtendedState(JFrame.MAXIMIZED_BOTH);
					v.setVisible(true);
			        // Realiza acciones adicionales cuando el inicio de sesión sea exitoso
				 }
			}  else if (correo.isEmpty() || contrasenia.isEmpty()){
				JOptionPane.showMessageDialog(null, "Alguno de los campos está vacío "); 
			} else {
				JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
			}
		});
		
		try {
			BaseDeDatos.main(null);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		base = new BaseDeDatos();
		base.verUsuarios();
		base.verEvento();
		mapaUsu = base.crearMapa();
		
	}
	
 // Condiciones de uso al iniciar sesion
 	private boolean mostrarCondicionesDeUso() {
         JTextArea textArea = new JTextArea(
                 "¡Bienvenido a Sell-it!\n\n" +
                         "Por favor, lea y acepte los siguientes términos y condiciones antes de continuar:\n\n" +
                         "1. Al utilizar esta aplicación, usted acepta cumplir con los términos y condiciones establecidos.\n" +
                         "2. Sell-it no se hace responsable por pérdidas o daños derivados del uso de la aplicación.\n" +
                         "3. Los usuarios deben proporcionar información precisa durante el registro.\n" +
                         "4. La venta de entradas está sujeta a disponibilidad y términos específicos de los eventos.\n" +
                         "5. La información del usuario se utilizará de acuerdo con nuestra política de privacidad.\n\n" +
                         "Al hacer clic en Aceptar, confirma que ha leído y acepta estos términos y condiciones."
         );
         textArea.setEditable(false);

         JScrollPane scrollPane = new JScrollPane(textArea);
         scrollPane.setPreferredSize(new Dimension(600, 400));

         JPanel messagePanel = new JPanel(new BorderLayout());
         messagePanel.add(scrollPane, BorderLayout.CENTER);

         int option = JOptionPane.showOptionDialog(
                 this,
                 messagePanel,
                 "Condiciones de Uso",
                 JOptionPane.DEFAULT_OPTION,
                 JOptionPane.INFORMATION_MESSAGE,
                 null,
                 new Object[]{"Aceptar", "Cancelar"},
                 "Aceptar"
         );

         return option == 0; // Devuelve true si el usuario hizo clic en "Aceptar"
     }
 //	
 	private void mostrarPoliticaPrivacidad() {
         try {
         	JTextArea textArea = new JTextArea(

                     "Política de Privacidad para la Aplicación Sell-it!\n\n"+
                     	"Fecha de entrada en vigencia: 1 de enero de 2024 \n\n"+
                     	"¡Bienvenido a Sell-it! Agradecemos tu interés y confianza en nuestra aplicación. Esta Política de Privacidad tiene como objetivo explicar cómo recopilamos, utilizamos y protegemos la información personal que puedas proporcionar durante el uso de nuestra aplicación.\n\n"+
                     		
                     		"1. Información que Recopilamos:\n"+
                     		"Al utilizar nuestra aplicación, podemos recopilar la siguiente información:\n\n"+
                     		"1.1 Información del Usuario:\n"+
                     			"Nombres, direcciones de correo electrónico y otros datos proporcionados voluntariamente por los usuarios.\n\n"+
                            "2. Propósito de la Recopilación:\n"+ 
                     			"La información del usuario se recopila con fines específicos, tales como procesar transacciones, personalizar la experiencia del usuario y enviar notificaciones relevantes.\n\n"+
                     		"3. Consentimiento:\n"+
                     			"Al hacer clic en Aceptar, confirmas que has leído y aceptas estos términos.\n\n"+
                     		"4. Seguridad:\n"+
                     			"Implementamos medidas de seguridad, incluido el cifrado de datos, para proteger la información del usuario contra accesos no autorizados y garantizar su confidencialidad.\n\n"+
                     		"5. Derechos del Usuario:\n"+
                     			"Los usuarios tienen derechos sobre sus datos personales, incluido el acceso, corrección, eliminación y portabilidad de los mismos. Para ejercer estos derechos, por favor, contáctanos.\n\n"+
                     		"6. Contacto:\n"+
                     			"Para consultas relacionadas con la privacidad, puedes comunicarte con nosotros a través de sellitcontac@gmail.es .\n\n"+
                     		"7. Políticas Futuras:\n"+
                     			"Esta política puede actualizarse. Te notificaremos sobre cambios importantes. Al continuar usando la aplicación después de dichas modificaciones, aceptas los términos actualizados.\n\n"+
                     		
                     		"Gracias por confiar en Sell-it! Si tienes alguna pregunta o inquietud sobre nuestra política de privacidad, no dudes en contactarnos."
         	);
             textArea.setEditable(false);

             JScrollPane scrollPane = new JScrollPane(textArea);
             scrollPane.setPreferredSize(new Dimension(600, 400));

             JPanel messagePanel = new JPanel(new BorderLayout());
             messagePanel.add(scrollPane, BorderLayout.CENTER);

             int option = JOptionPane.showOptionDialog(
                     this,
                     messagePanel,
                     "Política de Privacidad",
                     JOptionPane.DEFAULT_OPTION,
                     JOptionPane.INFORMATION_MESSAGE,
                     null,
                     new Object[]{"Aceptar"},
                     "Aceptar"
             );
         }catch (Exception e) {
             logger.log(Level.SEVERE, "Error al mostrar la política de privacidad", e);
         }
 		
     }
 //

    
	public static boolean validarCorreo(String correo) {
        if (correo == null || correo.isEmpty()) {
            return false; // Correo nulo o vacío es inválido
        }

        // Expresión regular para validar un correo electrónico
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        return correo.matches(regex);
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
	
//	private boolean verificarUsuarioEsCorrecto(String correo, String contrasenia) {
//		if(bd)
//	}
//	
	private boolean  verificarCredenciales (String correo, String contrasenia) {
		 if (mapaUsu.containsKey(correo)) {
            Usuario u = base.getUsuarioPorCorreo(correo);
	        String hashAlmacenado = u.getContrasena();
	        if(hashAlmacenado.startsWith("$2a$")) {
		        if (BCrypt.checkpw(contrasenia, hashAlmacenado)) {
		            return true; // La contraseña es correcta
		        } else {
		            return false; // La contraseña es incorrecta
		        }
	        }else {			//Esta parte es paara comprobar con los usuarios de prueba
	        	if (contrasenia.equals(hashAlmacenado)) {
	                return true; // La contraseña sin encriptar es correcta
	            } else {
	                return false; // La contraseña sin encriptar es incorrecta
	            }
	        }
		 } else {
			 return false;// El correo no está registrado, la autenticación falla
		 }
	}
	
	public String obtenerNombreUsuario(String correo) {
		if (mapaUsu.containsKey(correo)) {
	        Usuario usuario = mapaUsu.get(correo);
	        return usuario.getNombreUsuario();
	    } else {
	        return "Nombre de usuario no encontrado";
	    }
	}

	public static void cargarUsuariosInicio() {		
		
//		try { 
//			// Cargar usuarios directamente desde la base de datos 
//			List<Usuario> usuarios = bd.verUsuarios(); 
//			// Convertir la lista de usuarios a un DataSetUsuario si es necesario 
//			DataSetUsuario dataset = new DataSetUsuario(); 
//			dataset.setUsuarios(usuarios); 
//			// Actualizar la referencia en VentanaInicio si es necesario 
//			VentanaInicio.cargarUsuariosInicio(dataset); 
//		} catch (Exception e) { 
//			logger.log(Level.SEVERE, "Error al cargar usuarios desde la base de datos", e); 
//			JOptionPane.showMessageDialog(null, "Error al cargar usuarios desde la base de datos", "ERROR", JOptionPane.ERROR_MESSAGE); 
//		} 
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
                		ImageIcon imagenOjo = new ImageIcon("src/imagenes/eye_closed_icon.png");
                		txtContrasenia.getButton().setIcon(fotoBoton(imagenOjo));
                    } else {
                		ImageIcon imagenOjo = new ImageIcon("src/imagenes/eye_opened_icon.png");
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
