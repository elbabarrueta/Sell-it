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

	// Logger de prueba para registrar infromación y errores
    private static Logger logger = Logger.getLogger(VentanaInicio.class.getName());
	
    // Constructor de la clase
    public VentanaInicio() {
		super();
		
		try {
            // Configuración del aspecto de la interfaz gráfica utilizando Nimbus Look and Feel
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
        	logger.log(Level.SEVERE, "Error al configurar el look and feel", e);
        	JOptionPane.showMessageDialog(null, "Error al cargar la ventana: contacta con los informaticos.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        // Registro de información al inicializar la ventana de inicio
        logger.log(Level.INFO, "Inicializando la ventana de inicio");
		
		//Características de la ventana principal
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setBounds(500,300, 500, 250);
		this.setTitle("Sell-It");
		
        // Creación de paneles para organizar la interfaz gráfica
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

        // Configuración de elementos gráficos como etiquetas, campos de texto y botones
		JTextField txtUsuario = new JTextField();
		txtContrasenia = new CustomPasswordField();
		ImageIcon imagenOjo = new ImageIcon(getClass().getResource("/imagenes/eye_closed_icon.png"));
		txtContrasenia.getButton().setIcon(fotoBoton(imagenOjo));
		txtContrasenia.getButton().setBackground(Color.WHITE);
		txtContrasenia.setEchoChar('\u2022');
		JLabel etiquetaUsuario = new JLabel("Correo:");
		JLabel etiquetaContrasenia = new JLabel("Contraseña:");

        // Creación de botones y etiquetas
		JButton botonRegistroEntidad = new JButton("Registro Entidad");
		JButton botonRegistroUsuario = new JButton("Registro Usuario");
		JButton botonIniciarSesion = new JButton("Iniciar Sesion");
		
		JLabel etiquetaBienvenido = new JLabel("Bienvenido a Sell-It");
		JLabel etiquetaPregunta = new JLabel("¿No tienes cuenta?");
		
		// Adicción de elementos a paneles
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

        // Configuración de enlace para mostrar la política de privacidad
		JLabel politicaEnlace = new JLabel("Política de Privacidad");
        politicaEnlace.setForeground(Color.BLUE); // Color azul para indicar un enlace
        politicaEnlace.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        politicaEnlace.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mostrarPoliticaPrivacidad();
            }
        });
        panel.add(politicaEnlace, BorderLayout.SOUTH);

        // Configuración de eventos para los botones
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
			char[] contraseniaChar = txtContrasenia.getPassword();
	        String contrasenia = new String(contraseniaChar);
			String correo = txtUsuario.getText();

            if (!validarCorreo(correo)) {
                JOptionPane.showMessageDialog(null, "Correo con formato inválido");
                return;
            }
            
			if (verificarCredenciales(correo, contrasenia)) {
				if (mostrarCondicionesDeUso()) {
					usuarioActual = mapaUsu.get(correo);
					JOptionPane.showMessageDialog(null, "Bienvenido de nuevo " + obtenerNombreUsuario(correo));
					VentanaPrincipal v = new VentanaPrincipal();
					dispose();
					v.setVisible(true);
				 }
			}  else if (correo.isEmpty() || contrasenia.isEmpty()){
				JOptionPane.showMessageDialog(null, "Alguno de los campos está vacío "); 
			} else {
				JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
			}
		});
		
        // Configuración de la base de datos y carga de usuarios
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
                		" Condiciones Generales:\n\n"+
                			 "1 - Registro y Cuentas de Usuario:"+
                			 "Los usuarios deben registrarse en la aplicación proporcionando información precisa y completa.\n"+
                			 "Cada usuario puede tener un solo perfil, y la información del perfil debe mantenerse actualizada.\n\n"+
                			 "2 - Categorías de Usuarios:\n\n"+
                			 "La aplicación puede distinguir entre usuarios normales y entidades. Las entidades pueden ser organizadores de eventos, empresas u otros.\n\n"+
                			 "3 - Compra y Venta de Entradas:\n\n"+
                			 "Los usuarios normales pueden comprar y vender entradas para eventos.\n"+
                			 "Las entidades pueden listar y gestionar eventos, así como vender entradas a través de la plataforma.\n\n"+
                			 "4 - Información del Evento:\n\n"+
                			 "Las entidades deben proporcionar información precisa y completa sobre los eventos que organizan.\n"+
                			 "Los usuarios deben revisar cuidadosamente los detalles del evento antes de comprar las entradas.\n\n"+
                			 "5 - Tarifas y Pagos:\n\n"+
                			 "Pueden aplicarse tarifas por transacciones, ya sea al comprar o vender entradas.\n"+
                			 "Los pagos deben procesarse de manera segura utilizando métodos de pago confiables.\n\n"+
                			 "6 - Responsabilidad de la Plataforma:\n\n"+
                			 "La aplicación actúa como un facilitador y no es responsable de la calidad del evento o la conducta de los usuarios y entidades.\n\n"+
                			 "7 - Contenido Generado por el Usuario:\n\n"+
                			 "Los usuarios y entidades son responsables del contenido que generan, incluyendo descripciones de eventos y comentarios.\n\n"+
                			 "8 - Privacidad y Seguridad:\n\n"+
                			 "La aplicación debe cumplir con normativas de privacidad y seguridad para proteger la información personal de los usuarios.\n\n"+
                			 "9 - Uso Adecuado:\n\n"+
                			 "Los usuarios deben utilizar la aplicación de manera ética y legal.\n"+
                			 "Cualquier comportamiento abusivo, fraudulento o ilegal resultará en la suspensión o eliminación de la cuenta.\n\n"+
                			 "10 - Modificaciones en los Términos:\n\n"+
                			 "La aplicación se reserva el derecho de modificar los términos y condiciones, y los usuarios serán notificados de tales cambios.\n\n"+
                			 "11 - Resolución de Disputas:\n\n"+
                			 "Se debe establecer un proceso claro para la resolución de disputas entre usuarios y entidades, posiblemente a través de un servicio de atención al cliente.\n\n"+
                			 "12 - Terminación de Cuenta:\n\n"+
                			 "La aplicación puede suspender o terminar cuentas que violen los términos y condiciones.\n\n"+
                			 "13 - Comunicaciones y Notificaciones:\n\n"+
                			 "Los usuarios pueden recibir comunicaciones y notificaciones importantes a través de la aplicación.\n\n"+
                			 "14 - Derechos de Propiedad Intelectual:\n\n"+
                			 "Los usuarios y entidades deben respetar los derechos de propiedad intelectual relacionados con la aplicación y su contenido.\n\n"+
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
         return option == 0; 
     }
 
 	// Método para mostrar la política de privacidad
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
 
 	// Método para validar la estructura de un correo electrónico
	public static boolean validarCorreo(String correo) {
        if (correo == null || correo.isEmpty()) {
            return false; 
        }
        // Expresión regular para validar un correo electrónico
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return correo.matches(regex);
    }
	
	// Método para obtener el usuario actual
	public Usuario getUsuarioActual() {
		return usuarioActual;
	}
	
	// Método para mostrar u ocultar la contraseña en un campo de texto
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
	
	// Método para verificar las credenciales de un usuario
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
	        }else {			
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
	
	// Método para obtener el nombre de usuario a partir del correo
	public String obtenerNombreUsuario(String correo) {
		if (mapaUsu.containsKey(correo)) {
	        Usuario usuario = mapaUsu.get(correo);
	        return usuario.getNombreUsuario();
	    } else {
	        return "Nombre de usuario no encontrado";
	    }
	}

	// Método para redimensionar una imagen para un botón
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
	
    // Clase interna para el campo de contraseña personalizado con botón
	private static class CustomPasswordField extends JPasswordField {
        private JButton button;

        // Constructor de la clase interna
        public CustomPasswordField() {
            super();
            button = new JButton();
            setLayout(new BorderLayout());
            add(button, BorderLayout.EAST);
            button.setPreferredSize(new Dimension(30, 10));

            // Evento del botón para mostrar/ocultar la contraseña
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
        // Método para obtener el botón asociado al campo de contraseña
        public JButton getButton() {
            return button;
        }
    }

}
