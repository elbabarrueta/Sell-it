package ventanas;

import java.awt.BorderLayout;




import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.AbstractBorder;
import javax.swing.text.JTextComponent;

import org.mindrot.jbcrypt.BCrypt;

import BasesDeDatos.BaseDeDatos;
import clases.Usuario;

public class VentanaRegistroUsuario extends JFrame {
	
	private JTextField txtNombre,txtCorreo;
	private static CustomPasswordField txtContrasenia, txtConfirmar;	
	private static final long serialVersionUID = 1L;

	/**
     * Constructor de la clase VentanaRegistroUsuario.
     */
	public VentanaRegistroUsuario() {

		this.setTitle("Registro de Usuario");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		// Tamaño de la pantalla
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // Tamaño de la ventana
        int ventanaAncho = 400;
        int ventanaAlto = 300;

        // Calcular la posición para centrar la ventana
        int posX = (screenSize.width - ventanaAncho) / 2;
        int posY = (screenSize.height - ventanaAlto) / 2;

        // Establecer la posición de la ventana
        this.setBounds(posX, posY, ventanaAncho, ventanaAlto);
		
		JPanel panelRegistroUsuario = new JPanel(new BorderLayout());
		JPanel panelSur = new JPanel(new BorderLayout());
		JPanel panelNorte = new JPanel(new BorderLayout());
		JPanel panelCentro = new JPanel(new GridLayout(6,1));
		panelRegistroUsuario.add(panelCentro,BorderLayout.CENTER);
		panelRegistroUsuario.add(panelSur,BorderLayout.SOUTH);
		panelRegistroUsuario.add(panelNorte,BorderLayout.NORTH);
		panelNorte.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelSur.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		this.add(panelRegistroUsuario);
		
		JLabel lblPanelNorte = new JLabel("Rellene las siguientes casillas:");
		panelNorte.add(lblPanelNorte,BorderLayout.NORTH);
		
        JPanel pNom = new JPanel();
		txtNombre = new JTextField();
		aplicarEstiloCampo(txtNombre, "Nombre");
        pNom.add(txtNombre);

        JPanel pCorreo = new JPanel();
        txtCorreo = new JTextField();
        aplicarEstiloCampo(txtCorreo, "Correo");
        pCorreo.add(txtCorreo);

        JPanel pContr = new JPanel();
        txtContrasenia = new CustomPasswordField();
        aplicarEstiloCampo(txtContrasenia, "Contraseña");
        txtContrasenia.setEchoChar((char) 0);
        pContr.add(txtContrasenia);

        JPanel pConf = new JPanel();
        txtConfirmar = new CustomPasswordField();
        aplicarEstiloCampo(txtConfirmar, "Confirmar contraseña");
        txtConfirmar.setEchoChar((char) 0);
        pConf.add(txtConfirmar);
        
		JPanel pTipo = new JPanel();
		JLabel lblTipo = new JLabel("Tipo de usuario: ");
		JTextField txtTipo = new JTextField();
		txtTipo.setText("Usuario corriente");
		txtTipo.setEditable(false);
		pTipo.add(lblTipo);
		pTipo.add(txtTipo);
		
		panelCentro.add(pNom);
		panelCentro.add(pCorreo);
		panelCentro.add(pContr);
		panelCentro.add(pConf);
		panelCentro.add(pTipo);
		
		JButton btnRegistro = new JButton("Registrarse");
		panelSur.add(btnRegistro);
		JButton btnVolver = new JButton("Volver");
        panelSur.add(btnVolver);

		//Eventos
        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	VentanaInicio ventanaInicio = new VentanaInicio();
                dispose();  // Cierra la ventana actual
                ventanaInicio.setVisible(true);
                Main.setVentanaInicio(ventanaInicio);
            }
        });
      
        // Validar la contraseña ingresada por el usuario.
		btnRegistro.addActionListener((e)->{
			Pattern patronContrasenia = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$");
            char[] contrasenia = txtContrasenia.getPassword();
            char[] confirmada = txtConfirmar.getPassword();
            String nombre = txtNombre.getText();
            String correo = txtCorreo.getText();
            String tipo = txtTipo.getText();
            String imagenP = "Sell_it/src/imagenes/perfil.png";
            String descripcion = "Descripción vacía";
            if (nombre.equals("Nombre") || correo.equals("Correo") || contrasenia.length == 0 || confirmada.length == 0) {
                JOptionPane.showMessageDialog(null, "Para registrarse, debe introducir datos en todas las casillas.");
                return;
            }
            char[] confirmarContrasenia = txtConfirmar.getPassword();
            if (!Arrays.equals(contrasenia, confirmarContrasenia)) {
                JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String cont = new String(contrasenia);
            Matcher matcher = patronContrasenia.matcher(cont);
            if (!matcher.matches()) {
                StringBuilder mensajeError = new StringBuilder("La contraseña no cumple con los requisitos:\n");
                if (!matcher.matches()) {
                    if (!contraseniaCumpleRequisito("[A-Z]", cont)) {
                        mensajeError.append("- Debe contener al menos una letra mayúscula.\n");
                    }
                    if (!contraseniaCumpleRequisito("[a-z]", cont)) {
                        mensajeError.append("- Debe contener al menos una letra minúscula.\n");
                    }
                    if (!contraseniaCumpleRequisito("\\d", cont)) {
                        mensajeError.append("- Debe contener al menos un dígito.\n");
                    }
                    if (!contraseniaCumpleRequisito("[@$!%*?&]", cont)) {
                        mensajeError.append("- Debe contener al menos un carácter especial (@$!%*?&).\n");
                    }
                    mensajeError.append("- Debe tener al menos 6 caracteres.\n");

                    JOptionPane.showMessageDialog(null, mensajeError.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
            	// Hash de la contraseña
                String hashContrasenia = BCrypt.hashpw(cont, BCrypt.gensalt());

                // Crear un nuevo usuario
                Usuario u = new Usuario(nombre, correo, tipo, hashContrasenia, imagenP, descripcion);

                // Establecer la fecha actual como último cambio de contraseña
                u.cambiarContrasena(cont);

                // Añadir usuario a la base de datos
                BaseDeDatos.anadirUsuarioNuevo(u);
                JOptionPane.showMessageDialog(null, "Usuario registrado exitosamente");
                limpiarCampos();
                
                // Mostrar la ventana de inicio
                VentanaInicio ventanaInicio = new VentanaInicio();
                dispose();  // Cierra la ventana actual
                ventanaInicio.setVisible(true);
                Main.setVentanaInicio(ventanaInicio);
    			
            }
		});
	}
	
	/**
     * Método para comprobar si la contraseña cumple con un requisito específico.
     * @param regex Expresión regular que representa el requisito.
     * @param contrasenia Contraseña a verificar.
     * @return true si la contraseña cumple con el requisito, false de lo contrario.
     */
	private boolean contraseniaCumpleRequisito(String regex, String contrasenia) {
        return Pattern.compile(regex).matcher(contrasenia).find();
    }

		
	public void mostrarOcultarContraseña() {
        // Obtener la contraseña actual
        char[] contraseña = txtContrasenia.getPassword();

        // Cambiar el estado de visualización de la contraseña
        if (txtContrasenia.getEchoChar() == 0) {
        	txtContrasenia.setEchoChar('\u2022');
        } else {
        	txtContrasenia.setEchoChar((char) 0);
        }
        txtContrasenia.setText(new String(contraseña));
    }
	
	/**
     * Método para limpiar los campos de entrada.
     */
	private void limpiarCampos() {
		txtNombre.setText("");
		txtCorreo.setText("");		
		txtContrasenia.setText("");
		txtConfirmar.setText("");
	}
	
	/**
     * Método para aplicar un estilo visual a los campos de texto.
     * @param textField Campo de texto al que se aplicará el estilo.
     * @param texto Texto de ejemplo para el campo.
     */
	private void aplicarEstiloCampo(JTextComponent textField, String texto) {
        textField.setForeground(new Color(169, 169, 169));
        textField.setPreferredSize(new Dimension(350, 30));
        textField.setText(texto);
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(texto)) {
                    textField.setText("");
                    if(textField instanceof JPasswordField) {
                    	 ((JPasswordField) textField).setEchoChar('\u2022');
                    }
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(texto);
                    if(textField instanceof JPasswordField) {
                    	((JPasswordField) textField).setEchoChar((char) 0); 
                    }
                    textField.setForeground(new Color(169, 169, 169));
                }
            }
        });
        textField.setBorder(new RoundBorder(new Color(51, 255, 233), 20));
    }
	
	/**
     * Método para ajustar el tamaño de un icono.
     * @param icon Icono a ajustar.
     * @return ImageIcon ajustado.
     */
	 private static ImageIcon ajustarIcon(ImageIcon icon) {
	        int maxWidth = 20; // Tamaño máximo de ancho
	        int maxHeight = 20; // Tamaño máximo de alto
	        int newWidth, newHeight;
	        Image img = icon.getImage();
	        if (icon.getIconWidth() > icon.getIconHeight()) {
	            newWidth = maxWidth;
	            newHeight = (maxWidth * icon.getIconHeight()) / icon.getIconWidth();
	        } else {
	            newHeight = maxHeight;
	            newWidth = (maxHeight * icon.getIconWidth()) / icon.getIconHeight();
	        }
	        // Redimensiona la imagen
	        Image newImg = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
	        icon = new ImageIcon(newImg);
	        return icon;
	    }

	 /**
	  * Clase interna para representar un borde redondeado.
	  */
	 private static class RoundBorder extends AbstractBorder {
	        private final Color borderColor;
	        private final int roundRadius;

	        /**
	         * Constructor de la clase RoundBorder.
	         * @param borderColor Color del borde.
	         * @param roundRadius Radio de redondeo.
	         */
	        public RoundBorder(Color borderColor, int roundRadius) {
	            this.borderColor = borderColor;
	            this.roundRadius = roundRadius;
	        }

	        @Override
	        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
	            Graphics2D g2 = (Graphics2D) g.create();
	            g2.setColor(borderColor);
	            g2.drawRoundRect(x, y, width - 1, height - 1, roundRadius, roundRadius);
	            g2.dispose();
	        }
	 }
	 
	 /**
	  * Clase interna para representar un campo de contraseña personalizado.
	  */
     private static class CustomPasswordField extends JPasswordField {
        private JButton button;

        /**
         * Constructor de la clase CustomPasswordField.
         */
        public CustomPasswordField() {
            super();
            button = new JButton();
            setLayout(new BorderLayout());
            add(button, BorderLayout.EAST);
            button.setPreferredSize(new Dimension(30, 10));
            ImageIcon imagenOjo = new ImageIcon(getClass().getResource("/imagenes/eye_closed_icon.png"));
    		button.setIcon((ajustarIcon(imagenOjo)));
    		button.setBackground(Color.WHITE);
    		
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    char echoChar = getEchoChar();
                    if (echoChar == 0) {
                        setEchoChar('\u2022'); // Ocultar contraseña (punto negro)
                		ImageIcon imagenOjo = new ImageIcon(getClass().getResource("/imagenes/eye_closed_icon.png"));
                		button.setIcon((ajustarIcon(imagenOjo)));
                    } else {
                		ImageIcon imagenOjo = new ImageIcon(getClass().getResource("/imagenes/eye_opened_icon.png"));
                		button.setIcon((ajustarIcon(imagenOjo)));
                        setEchoChar((char) 0); // Mostrar contraseña
                    }
                }
            });
        }

        /**
         * Método para obtener el botón asociado al campo de contraseña.
         * @return JButton asociado al campo de contraseña.
         */
        public JButton getButton() {
            return button;
        }
    }
	
}
