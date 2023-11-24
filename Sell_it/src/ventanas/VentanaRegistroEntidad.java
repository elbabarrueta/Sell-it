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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;

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

import clases.Datos;
import clases.Usuario;
import datos.DataSetUsuario;

public class VentanaRegistroEntidad extends JFrame{
	
	private JPasswordField txtContrasenia, txtConfirmar;
	private JTextField txtNombre,txtCorreo;
//	private JComboBox comboTipo;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VentanaRegistroEntidad() {
		
		this.setBounds(300,400,400,300);
		this.setTitle("Registro Entidad");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		
		JPanel panelRegistroEntidad = new JPanel(new BorderLayout());
		JPanel panelNorte = new JPanel(new BorderLayout());
		JPanel panelSur = new JPanel(new BorderLayout());
		JPanel panelCentro = new JPanel(new GridLayout(6,2));
		panelRegistroEntidad.add(panelCentro,BorderLayout.CENTER);
		panelRegistroEntidad.add(panelSur,BorderLayout.SOUTH);
		panelRegistroEntidad.add(panelNorte,BorderLayout.NORTH);
		panelNorte.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelSur.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		this.add(panelRegistroEntidad);
		
		JLabel lblPanelNorte = new JLabel("Rellene las siguientes casillas:");
		panelNorte.add(lblPanelNorte, BorderLayout.NORTH);

		//JLabel lblNombre = new JLabel("Nombre de la Empresa:");
        JPanel pNom = new JPanel();
		txtNombre = new JTextField();
		aplicarEstiloCampo(txtNombre, "Nombre de la empresa");
        pNom.add(txtNombre);

		//JLabel lblCorreo = new JLabel("Direccion de correo:");
        JPanel pCorreo = new JPanel();
        txtCorreo = new JTextField();
        aplicarEstiloCampo(txtCorreo, "Correo de empresa");
        pCorreo.add(txtCorreo);

		//JLabel lblContrasenia = new JLabel("Contraseña:");
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
		JLabel lblTipo = new JLabel("Tipo de usuario");		
		JTextField txtTipo = new JTextField();
		txtTipo.setText("Usuario entidad");
		txtTipo.setEditable(false);
		pTipo.add(lblTipo);
		pTipo.add(txtTipo);
//		String[] tipoUsu = {"Usuario entidad", "Usuario corriente"};
//		comboTipo = new JComboBox<>(tipoUsu);
//		
//		JLabel mostrarContra = new JLabel("Mostrar Contraseña");		
//		mostrarContra.addMouseListener(new MouseAdapter() {
//	            @Override
//	            public void mouseClicked(MouseEvent e) {
//	                mostrarOcultarContraseña();
//	            }
//	    });
//		
		panelCentro.add(pNom);
		panelCentro.add(pCorreo);
		panelCentro.add(pContr);
		panelCentro.add(pConf);
		panelCentro.add(pTipo);
		
		JButton btnRegistro = new JButton("Registrarse");
		panelSur.add(btnRegistro);
		
	
		

		
		//Eventos
		
		
		btnRegistro.addActionListener((e)->{
			
			String contrasenia = txtContrasenia.getText();
			String nombre = txtNombre.getText();
			String correo = txtCorreo.getText();
			String tipo = txtTipo.getText();
			
			if(nombre.isEmpty() || correo.isEmpty() || contrasenia.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Para registrarse, debe introducir datos en todas las casillas.");
				return;
			}
			if (contrasenia.length() <= 5) {
		        JOptionPane.showMessageDialog(null, "La contraseña debe tener más de 5 caracteres.");
		        return; 
		    }
			if (!correo.contains("@")) {
		        JOptionPane.showMessageDialog(null, "La dirección de correo no es válida. Debe contener el carácter '@'.");
		        return;
		    }
			
			Usuario u = new Usuario(nombre,correo,tipo,contrasenia);
			u.setUltimaCambioContrasena(LocalDate.now());
			
			if( DataSetUsuario.buscarUsu(correo)== null) {
				DataSetUsuario.anyadirUsuario(u);
				JOptionPane.showMessageDialog(null, "Bienvenido a Sell-IT");
				System.out.println("\t" + u);
				// Cerrar la ventana actual
				VentanaInicio v = new VentanaInicio();
				dispose();
		        v.setVisible(true);
		        Main.setVentanaInicio(v);
			}
			else {
				JOptionPane.showMessageDialog(null, "Usuario existente, introduce otro correo y nombre");
			}
		//	System.out.println("\t" + u);
			limpiarCampos();
		});
		
	}
	
	public void mostrarOcultarContraseña() {
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
	private void limpiarCampos() {
		txtNombre.setText("");
		txtCorreo.setText("");	
		txtContrasenia.setText("");
		txtConfirmar.setText("");
	}
		
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
                    	 ((JPasswordField) textField).setEchoChar('*');
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
	
	private static class RoundBorder extends AbstractBorder {
        private final Color borderColor;
        private final int roundRadius;

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
 
 private static class CustomPasswordField extends JPasswordField {
    private JButton button;

    public CustomPasswordField() {
        super();
        button = new JButton();
        setLayout(new BorderLayout());
        add(button, BorderLayout.EAST);
        button.setPreferredSize(new Dimension(30, 10));
        ImageIcon imagenOjo = new ImageIcon("Sell_it/src/imagenes/eye_closed_icon.png");
		button.setIcon((ajustarIcon(imagenOjo)));
		button.setBackground(Color.WHITE);
		
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                char echoChar = getEchoChar();
                if (echoChar == 0) {
                    setEchoChar('\u2022'); // Ocultar contraseña (punto negro)
            		ImageIcon imagenOjo = new ImageIcon("Sell_it/src/imagenes/eye_closed_icon.png");
            		button.setIcon((ajustarIcon(imagenOjo)));
                } else {
            		ImageIcon imagenOjo = new ImageIcon("Sell_it/src/imagenes/eye_opened_icon.png");
            		button.setIcon((ajustarIcon(imagenOjo)));
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
