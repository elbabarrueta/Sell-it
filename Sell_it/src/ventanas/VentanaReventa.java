package ventanas;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;

import javax.swing.*;

import clases.Entrada;
import clases.Evento;
import clases.Usuario;



public class VentanaReventa extends JFrame{
	private Usuario usuario;
	private List<String> entradasCompradas;

	private JComboBox cbEntradas = new JComboBox();   //Lista con entradas disponibles para vender
	private JTextField tfCant = new JTextField();
	private JTextField tfPrecio = new JTextField();
	
	public VentanaReventa(Usuario usu) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(600, 500);
		setLocationRelativeTo(null);
		setTitle("Reventa");
		
		//Creamos los paneles
		JPanel pSuperior = new JPanel(new BorderLayout());
		this.add(pSuperior, BorderLayout.NORTH);
		
		JPanel pCentral = new JPanel(new GridLayout(2,2));
		this.add(pCentral, BorderLayout.CENTER);
		
		JPanel pInferior = new JPanel(new BorderLayout());
		this.add(pInferior, BorderLayout.SOUTH);
		
		//Panel Superior
		JButton bMiperfil = new JButton("MI PERFIL");
		pSuperior.add(bMiperfil, BorderLayout.EAST);
		
		//Panel Central
		JLabel lEntrada = new JLabel("Seleccionar entrada");
		pCentral.add(lEntrada);
		pCentral.add(cbEntradas);
		/**										
		JLabel lCant = new JLabel("Cantidad");	Si podemos revender mas de una entrada a la vez, si no se borra
		pCentral.add(lCant);
		pCentral.add(tfCant);
		**/
		JLabel lPrecio = new JLabel("Precio");
		pCentral.add(lPrecio);
		pCentral.add(tfPrecio);
		
		//Panel Inferior
		JButton bSubir = new JButton("Subir entrada");
		pInferior.add(bSubir, BorderLayout.EAST);
		JButton bVprincipal = new JButton("Ventana Principal");
		pInferior.add(bVprincipal, BorderLayout.WEST);
		
		
		bMiperfil.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				VentanaPerfilUsuario vPerfilEntidad = new VentanaPerfilUsuario(usu, entradasCompradas);
			}
		});
		
		bVprincipal.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				VentanaPrincipal vPrincipal = new VentanaPrincipal();
			}
		});
		
		bSubir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (cbEntradas.getSelectedItem() == null) {
		            JOptionPane.showMessageDialog(null, "Selecciona una entrada antes de subirla", "Error", JOptionPane.ERROR_MESSAGE);
		            return;
		        }
		        String precioText = tfPrecio.getText();
		        try {
			        double precio = Double.parseDouble(precioText);			            
			        JOptionPane.showMessageDialog(null, "Entrada subida exitosamente");
		        }catch(NumberFormatException ex) {
		        	JOptionPane.showMessageDialog(null, "Ingresa un valor válido para el precio", "Error", JOptionPane.ERROR_MESSAGE);
		        }
								
			}
		});
		
		
	}
	public static void main(String[] args) {
		Usuario usu = new Usuario();
		VentanaReventa v = new VentanaReventa(usu);
		v.setVisible(true);
	}

}
