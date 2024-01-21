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
import clases.EntradaReventa;
import clases.Evento;
import clases.Usuario;

public class VentanaReventa extends JFrame{
	
	private JTextField tfCantidad = new JTextField(); 
	private JLabel lImagen;
	private JLabel lNombre;

	private Evento eventoActual;
	private Entrada ent;
	public static VentanaPrincipal vPrincipal;
	private static BaseDeDatos bd;
	
	public VentanaReventa(Evento ev, VentanaPrincipal vPrincipal) {
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


		
		JPanel pnlCentral = new JPanel();
		pnlCentral.setLayout(new GridLayout(2, 2));
		this.add(pnlCentral, BorderLayout.CENTER);
		
		JPanel pImagen = new JPanel();
		lImagen = new JLabel();
		if(ev.getRutaImg() == null) {
	        ImageIcon imagen = new ImageIcon(getClass().getResource("/imagenes/default.png")); // Ruta de la imagen de perfil
	    	setImagen(imagen);
	    }else {
	    	String rutaImg = ev.getRutaImg();
            ImageIcon imagen = new ImageIcon(rutaImg);
            setImagen(imagen);
	    } 
		pImagen.add(lImagen);
        pnlCentral.add(pImagen);
        
        //conseguir el precio reventa, primer paso
		String[] partes = ev.getNombre().split("-");
        int idReventa = Integer.parseInt(partes[1]);
        double precioReventa = bd.obtenerPrecioEntradaReventa(idReventa);
        
        JButton btnComprar = new JButton("Comprar");
		JPanel pEvento = new JPanel(new GridLayout(6,1));
		lNombre = new JLabel("Nombre: " + partes[0]);
		pEvento.add(lNombre);

		JLabel lFecha = new JLabel("Fecha: " +ev.getFecha());
		pEvento.add(lFecha);
		JLabel lUbicacion = new JLabel("Ubicacion: " +ev.getUbicacion());
		pEvento.add(lUbicacion);
		JLabel lNumEntradas = new JLabel("Entradas disponibles: 1" );
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
            	String correoCreador  = bd.obtenerCorreoEntradaReventa(idReventa);
                 String nombreEvento = eventoActual.getNombre(); // Obtén el nombre del evento actual
                
                 VentanaValoracion ventanaValoracion = new VentanaValoracion(correoCreador, nombreEvento);
                 ventanaValoracion.setVisible(true);
            }
        });
		
		JPanel pCantidad = new JPanel(new GridLayout(4,1));
		JLabel lPrecio = new JLabel("Precio por entrada: " + precioReventa + "€");
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
				VentanaReventa.this.dispose();
				vPrincipal.setVisible(true);
			}
		});
		
		btnComprar.addActionListener(new ActionListener() {

			@Override
		    public void actionPerformed(ActionEvent e) {
		    	
		    	VentanaInicio ventanaI = Main.getVentanaInicio();
				Usuario usuActual = ventanaI.getUsuarioActual();
				VentanaReventa.this.dispose();
				VentanaCompraReventa vc = new VentanaCompraReventa(usuActual, VentanaReventa.this, ent, idReventa);
				vc.setVisible(true);
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
