package ventanas;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import BasesDeDatos.BaseDeDatos;
import clases.Entrada;
import clases.Evento;
import clases.Usuario;

public class VentanaEvento extends JFrame{
	
	private JTextField tfCantidad = new JTextField(); //Se puede cambiar por un JComboBox 
	private JLabel lImagen;
	private JLabel lNombre;//Telmo(lo he puesto aqui para obtener el no)
//	private VentanaCompra ventanaCompra;
	private Evento eventoActual;
	private Entrada ent;
	
	public VentanaEvento(Evento e) {
		
		this.eventoActual = e;
		
		VentanaInicio ventanaI = Main.getVentanaInicio();
		Usuario usuActual = ventanaI.getUsuarioActual();
		double precioEntrada = BaseDeDatos.obtenerPrecioEntrada(e.getCodigo());
		ent = new Entrada(e.getCodigo(), e, usuActual, precioEntrada);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(600, 500);
		setLocationRelativeTo(null);
		setTitle("Entrada");
		setLayout(new BorderLayout());
		//setLayout(new GridLayout(2,2));

		
		JPanel pnlCentral = new JPanel();
		pnlCentral.setLayout(new GridLayout(2,2));
		this.add(pnlCentral, BorderLayout.CENTER);
		
		JPanel pImagen = new JPanel();
		lImagen = new JLabel();
		if(e.getRutaImg() == null) {
	        ImageIcon imagen = new ImageIcon("Sell_it/src/imagenes/default.png"); // Ruta de la imagen de perfil
	    	setImagen(imagen);
	    }else {
	    	String rutaImg = e.getRutaImg();
            ImageIcon imagen = new ImageIcon(rutaImg);
            setImagen(imagen);
	    } 
		pImagen.add(lImagen);
        pnlCentral.add(pImagen);
		
        JButton btnComprar = new JButton("Comprar");
		JPanel pEvento = new JPanel(new GridLayout(4,1));
		lNombre = new JLabel(e.getNombre());
		pEvento.add(lNombre);
		JLabel lFecha = new JLabel(e.getFecha());
		pEvento.add(lFecha);
		JLabel lUbicacion = new JLabel(e.getUbicacion());
		pEvento.add(lUbicacion);
		JLabel lNumEntradas = new JLabel(String.valueOf(e.getnEntradas()));
		pEvento.add(lNumEntradas);
		pnlCentral.add(pEvento);
		
		JPanel pDesc = new JPanel();
		JLabel lDesc = new JLabel("Detalles del evento:");
		JTextArea taDesc = new JTextArea(e.getDesc());
		taDesc.setPreferredSize(new Dimension(200, 180));
		taDesc.setEditable(false);
		taDesc.setLineWrap(true); 
		taDesc.setWrapStyleWord(true); 
		pDesc.add(lDesc);
		pDesc.add(taDesc);
		pnlCentral.add(pDesc);
		
	     
		
		JPanel pCantidad = new JPanel(new GridLayout(4,1));
		JLabel lCantidad = new JLabel("Cantidad:");
		pCantidad.add(lCantidad);
		pCantidad.add(tfCantidad);
		JLabel lPrecio = new JLabel("Precio por cada entrada: " + ent.getPrecio() + "€");
		pCantidad.add(lPrecio);
		JLabel lTotal = new JLabel();
		pCantidad.add(lTotal);
		pnlCentral.add(pCantidad);
		
		JPanel pnlBotones = new JPanel();
		this.add(pnlBotones, BorderLayout.SOUTH);
		pnlBotones.setLayout(new FlowLayout());
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.setFont(new Font("Tahoma", Font.PLAIN, 15));
		pnlBotones.add(btnVolver);
		
		btnComprar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		pnlBotones.add(btnComprar);
		
		btnVolver.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					VentanaEvento.this.dispose();
					VentanaPrincipal v = new VentanaPrincipal();
					v.setVisible(true);
				}
			});
		
		
		btnComprar.addActionListener(new ActionListener() {

			@Override
		    public void actionPerformed(ActionEvent e) {
		    	
		    	VentanaInicio ventanaI = Main.getVentanaInicio();
				Usuario usuActual = ventanaI.getUsuarioActual();
				
		        try {
		            
		            int cantidadComprar = Integer.parseInt(tfCantidad.getText());
		            int entradasDisponibles = Integer.parseInt(lNumEntradas.getText());
		            
		            if (cantidadComprar <= entradasDisponibles) {
		                
		            	VentanaEvento.this.dispose();
						VentanaCompra vc = new VentanaCompra(usuActual, cantidadComprar, VentanaEvento.this, ent);
						vc.setVisible(true);
						
						
		            } else {
		                
		                JOptionPane.showMessageDialog(VentanaEvento.this,
		                        "No hay suficientes entradas",
		                         "Error", JOptionPane.ERROR_MESSAGE);
		            }
		        } catch (NumberFormatException ex) {
		            
		            JOptionPane.showMessageDialog(VentanaEvento.this,
		                    "Ingrese una cantidad válida.",
		                    "Error de formato", JOptionPane.ERROR_MESSAGE);
		        }
		    }
		});

	}
	public int entradasCompradas() {
		int cantidadComprar = Integer.parseInt(tfCantidad.getText());
        
        return cantidadComprar;
        
    };
	
	public void setImagen(ImageIcon imagen) {
		int maxWidth = 200; // Tamaño máximo de ancho
        int maxHeight = 200; // Tamaño máximo de alto
        int newWidth, newHeight;
        Image img = imagen.getImage();
        if (imagen.getIconWidth() > imagen.getIconHeight()) {
            newWidth = maxWidth;
            newHeight = (maxWidth * imagen.getIconHeight()) / imagen.getIconWidth();
        } else {
            newHeight = maxHeight;
            newWidth = (maxHeight * imagen.getIconWidth()) / imagen.getIconHeight();
        }
        // Redimensiona la imagen
        Image newImg = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        imagen = new ImageIcon(newImg);
        lImagen.setIcon(imagen);
	}
	
	public JLabel getNombreEvento() { //Telmo
		return lNombre;
	}
	
	public Evento getEvento() {
		return this.eventoActual;
	}

}
