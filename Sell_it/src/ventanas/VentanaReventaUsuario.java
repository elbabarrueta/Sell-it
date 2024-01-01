package ventanas;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;

import javax.swing.*;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.jdesktop.swingx.painter.MattePainter;

import clases.Entrada;
import clases.Evento;
import clases.Usuario;



public class VentanaReventaUsuario extends JFrame{
	private Usuario usuario;
	private List<String> entradasCompradas;
	private JTextField tfNombre = new JTextField();

	private JComboBox cbEntradas = new JComboBox();   //Lista con entradas disponibles para vender
//	private JTextField tfCant = new JTextField();
	private JTextField tfPrecio = new JTextField();
	
	
	public VentanaReventaUsuario(Usuario usu) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(450, 400);
		setLocationRelativeTo(null);
		setTitle("Reventa");
		
		//Creamos los paneles
		
		
		JPanel pSuperior = new JPanel(new BorderLayout());
		this.add(pSuperior, BorderLayout.NORTH);
		
		
		JLabel fillerLabel = new JLabel("Bienvenido a la Ventana de Reventa");
        fillerLabel.setFont(new Font("Sans Serif", Font.BOLD, 15));
        fillerLabel.setHorizontalAlignment(JLabel.CENTER);
        fillerLabel.setVerticalAlignment(JLabel.CENTER);
        pSuperior.add(fillerLabel, BorderLayout.CENTER);
        
		
    
		JPanel pCentral = new JPanel(new GridLayout(2, 2, 10, 10));
        pCentral.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		this.add(pCentral, BorderLayout.CENTER);
		
		
		
		
		JPanel pInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pInferior.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
		this.add(pInferior, BorderLayout.SOUTH);
		
		//Panel Superior
		JButton bMiperfil = new JButton("MI PERFIL");
		bMiperfil.setForeground(new Color(70, 70, 70)); // Gris oscuro
        bMiperfil.setBackground(new Color(176, 224, 230)); // Turquesa pálido
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
		bSubir.setBackground(new Color(230, 230, 250)); // Lavanda
        pInferior.add(bSubir);
//		pInferior.add(bSubir, BorderLayout.EAST);
		JButton bVprincipal = new JButton("Ventana Principal");
//		pInferior.add(bVprincipal, BorderLayout.WEST);
		bVprincipal.setForeground(new Color(255, 69, 0)); // Rojo anaranjado
        bVprincipal.setBackground(new Color(255, 228, 181)); // Melocotón claro
        pInferior.add(bVprincipal);

        // Configuración adicional
        AutoCompleteDecorator.decorate(cbEntradas); // Añadir funcionalidad de autocompletado
        
        
		
		bMiperfil.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				VentanaReventaUsuario.this.dispose();
				VentanaPerfilUsuario vPerfilEntidad = new VentanaPerfilUsuario(usu, entradasCompradas);
//				vPerfilEntidad.setVisible(true);
			}
		});
		
		bVprincipal.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				VentanaReventaUsuario.this.dispose();
				VentanaPrincipal vPrincipal = new VentanaPrincipal();
				vPrincipal.setVisible(true);
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
		            double precioReventa = Double.parseDouble(precioText);

		            // Obtener la entrada seleccionada
		            // Suponemos que la entrada tiene un atributo "precioOriginal"
		            double precioEntidad = obtenerPrecioEntrada();

		            // Validar que el precio de reventa sea mayor que el precio original
		            if (precioEntidad <= precioReventa) {
		                JOptionPane.showMessageDialog(null, "El precio de reventa debe ser menor que el precio original", "Error", JOptionPane.ERROR_MESSAGE);
		            } else {
		                JOptionPane.showMessageDialog(null, "Entrada subida exitosamente");
		            }
		        } catch (NumberFormatException ex) {
		            JOptionPane.showMessageDialog(null, "Ingresa un valor válido para el precio de reventa", "Error", JOptionPane.ERROR_MESSAGE);
		        }
		        
		    /*    try {
			        double precio = Double.parseDouble(precioText);			            
			        JOptionPane.showMessageDialog(null, "Entrada subida exitosamente");
		        }catch(NumberFormatException ex) {
		        	JOptionPane.showMessageDialog(null, "Ingresa un valor válido para el precio", "Error", JOptionPane.ERROR_MESSAGE);
		        }*/
								
			}
		});
		setVisible(true);
	}
	
	private double obtenerPrecioEntrada() {
        VentanaVentaEntidad v1 = new VentanaVentaEntidad(usuario);
        return v1.obtenerPrecioEntrada();
    }
	
	public static void main(String[] args) {
		Usuario usu = new Usuario();
		VentanaReventaUsuario v = new VentanaReventaUsuario(usu);
		v.setVisible(true);
	}
	
}
