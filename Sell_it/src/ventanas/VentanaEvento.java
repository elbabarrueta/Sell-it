package ventanas;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import clases.Entrada;
import clases.Evento;
import clases.Usuario;

public class VentanaEvento extends JFrame{
	
	private JTextField tfCantidad = new JTextField(); //Se puede cambiar por un JComboBox 
	private JLabel lImagen;
	private JLabel lNombre;//Telmo(lo he puesto aqui para obtener el no)
	private VentanaCompra ventanaCompra;
	
	
	public VentanaEvento(Evento e) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(600, 500);
		setLocationRelativeTo(null);
		setTitle("Entrada");
		setLayout(new GridLayout(2,2));
		
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
        this.add(pImagen);
		
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
		this.add(pEvento);
		
		JPanel pDesc = new JPanel();
		JLabel lDesc = new JLabel("Detalles del evento:");
		JTextArea taDesc = new JTextArea(e.getDesc());
		taDesc.setPreferredSize(new Dimension(200, 180));
		taDesc.setEditable(false);
		taDesc.setLineWrap(true); 
		taDesc.setWrapStyleWord(true); 
		pDesc.add(lDesc);
		pDesc.add(taDesc);
		this.add(pDesc);
		
		
		JPanel pCantidad = new JPanel(new GridLayout(4,1));
		JLabel lCantidad = new JLabel("Cantidad:");
		pCantidad.add(lCantidad);
		pCantidad.add(tfCantidad);
		JLabel lPrecio = new JLabel("Precio Total:");
		pCantidad.add(lPrecio);
		JLabel lTotal = new JLabel();
		pCantidad.add(lTotal);
		this.add(pCantidad);
		
		btnComprar.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        try {
		            
		            int cantidadComprar = Integer.parseInt(tfCantidad.getText());
		            int entradasDisponibles = Integer.parseInt(lNumEntradas.getText());

		            
		            if (cantidadComprar <= entradasDisponibles) {
		                
		                ventanaCompra.setVisible(true);
		            } else {
		                
		                JOptionPane.showMessageDialog(VentanaEvento.this,
		                        "Error",
		                         "no hay suficientes entradas", JOptionPane.ERROR_MESSAGE);
		            }
		        } catch (NumberFormatException ex) {
		            
		            JOptionPane.showMessageDialog(VentanaEvento.this,
		                    "Ingrese una cantidad válida.",
		                    "Error de formato", JOptionPane.ERROR_MESSAGE);
		        }
		    }
		});

	    
		
		
		
	}
	
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

}
