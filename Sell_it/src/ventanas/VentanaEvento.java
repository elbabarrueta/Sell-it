package ventanas;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

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
	public static VentanaPrincipal vPrincipal;
	
	public VentanaEvento(Evento ev, VentanaPrincipal vPrincipal) {
		this.vPrincipal = vPrincipal;
		this.eventoActual = ev;
		
		VentanaInicio ventanaI = Main.getVentanaInicio();
		Usuario usuActual = ventanaI.getUsuarioActual();
		double precioEntrada = BaseDeDatos.obtenerPrecioEntrada(ev.getCodigo());
		ent = BaseDeDatos.obtenerEntradaDeEvento(ev.getCodigo());
		
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
		if(ev.getRutaImg() == null) {
	        ImageIcon imagen = new ImageIcon("Sell_it/src/imagenes/default.png"); // Ruta de la imagen de perfil
	    	setImagen(imagen);
	    }else {
	    	String rutaImg = ev.getRutaImg();
            ImageIcon imagen = new ImageIcon(rutaImg);
            setImagen(imagen);
	    } 
		pImagen.add(lImagen);
        pnlCentral.add(pImagen);
		
        JButton btnComprar = new JButton("Comprar");
		JPanel pEvento = new JPanel(new GridLayout(6,1));
		lNombre = new JLabel("Nombre: " +ev.getNombre());
		pEvento.add(lNombre);
		JLabel lFecha = new JLabel("Fecha: " +ev.getFecha());
		pEvento.add(lFecha);
		JLabel lUbicacion = new JLabel("Ubicacion: " +ev.getUbicacion());
		pEvento.add(lUbicacion);
		JLabel lNumEntradas = new JLabel("Entradas disponibles: " +String.valueOf(ev.getnEntradas()));
		pEvento.add(lNumEntradas);
		pnlCentral.add(pEvento);
		
		JPanel pDesc = new JPanel();
		JLabel lDesc = new JLabel("Detalles del evento:");
		JTextArea taDesc = new JTextArea(ev.getDesc());
		taDesc.setPreferredSize(new Dimension(200, 180));
		taDesc.setEditable(false);
		taDesc.setLineWrap(true); 
		taDesc.setWrapStyleWord(true); 
		pDesc.add(lDesc);
		pDesc.add(taDesc);
		pnlCentral.add(pDesc);

	    JButton botonVal  = new JButton("Valora el creador");
		pEvento.add(botonVal);
        pEvento.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0)); // Añade espacio debajo del botón
        botonVal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	 String correoCreador = eventoActual.getCreador(); // Obtén el correo del creador del evento actual
                 String nombreEvento = eventoActual.getNombre(); // Obtén el nombre del evento actual
                
                 VentanaValoracion ventanaValoracion = new VentanaValoracion(correoCreador, nombreEvento);
                 ventanaValoracion.setVisible(true);
            }
        });
		
		JPanel pCantidad = new JPanel(new GridLayout(5,1));
		JLabel lCompra = new JLabel("<html>Para comprar entradas,"+ "<br/>"+"agrega la cantidad que quieres comprar"+"<br/>"+"y pulsa el boton comprar."+ "</html>");
		pCantidad.add(lCompra);
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
				vPrincipal.setVisible(true);
			}
		});
		
		btnComprar.addActionListener(new ActionListener() {

			@Override
		    public void actionPerformed(ActionEvent e) {
		    	
		    	VentanaInicio ventanaI = Main.getVentanaInicio();
				Usuario usuActual = ventanaI.getUsuarioActual();
				
		        try {
		            
		            int cantidadComprar = Integer.parseInt(tfCantidad.getText());
		            int entradasDisponibles = eventoActual.getnEntradas();
		            
		            if (cantidadComprar <= entradasDisponibles && cantidadComprar > 0) {
		                
		            	VentanaEvento.this.dispose();
						VentanaCompra vc = new VentanaCompra(usuActual, cantidadComprar, VentanaEvento.this, ent);
						vc.setVisible(true);
						
						
		            } else if (cantidadComprar <= 0) {
		            	JOptionPane.showMessageDialog(VentanaEvento.this,
		                        "No puedes comprar ese numero de entradas",
		                         "Error", JOptionPane.ERROR_MESSAGE);
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
		try {
	        int cantidadComprar = Integer.parseInt(tfCantidad.getText());
	        return cantidadComprar;
		} catch (NumberFormatException e) {
	        
	        System.err.println("Error: Ingresa un número válido");
	        return 0; 
	    }
        
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
