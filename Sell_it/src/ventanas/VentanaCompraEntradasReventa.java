package ventanas;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import BasesDeDatos.BaseDeDatos;
import clases.Entrada;
import clases.EntradaReventa;
import clases.Evento;
import clases.Usuario;

public class VentanaCompraEntradasReventa extends JFrame{
	private JPanel panelPrincipal;
	private JPanel panelSur;
	private JPanel panelEste;
	private JPanel panelOeste;
	private JButton btnCompra;
	private JButton btnVolver;
	private JButton btnValorar;
	private JLabel lblDetallesEvento;
	private JLabel lblTituloDetallesEvento;
	private JLabel lblCodigoEntrada;
	private JLabel lblCorreoUsuario;
	private JLabel lblPrecioEntrada;
	private JLabel lImagen;
	private static BaseDeDatos baseDeDatos;
	private EntradaReventa entradaReventaActual;
	private List<EntradaReventa> entradasReventaEnBD;
	private VentanaEvento ventanaEvento;
	private Evento eventoActual;
	
	
	
	//Falta la foto
	public VentanaCompraEntradasReventa() {
		this.setBounds(900,300,400,400);
		this.setTitle("Entrada Reventa");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.baseDeDatos = new BaseDeDatos();
		this.ventanaEvento = ventanaEvento;
		this.eventoActual= ventanaEvento.getEvento();
		
		
		panelPrincipal = new JPanel(new BorderLayout());
		panelSur = new JPanel(new BorderLayout());
		panelEste = new JPanel(new BorderLayout());
		panelOeste = new JPanel(new BorderLayout());
		panelOeste.setLayout(new BoxLayout(panelOeste, BoxLayout.Y_AXIS));
		panelEste.setLayout(new BoxLayout(panelEste, BoxLayout.Y_AXIS));

		
		panelPrincipal.add(panelSur,BorderLayout.SOUTH);
		panelPrincipal.add(panelEste,BorderLayout.EAST);
		panelPrincipal.add(panelOeste,BorderLayout.WEST);
		this.add(panelPrincipal);
		
		
		btnVolver = new JButton("Volver");
		btnCompra = new JButton("Comprar");
		panelSur.add(btnCompra);
		panelSur.add(btnVolver);
		
		panelEste.add(lblTituloDetallesEvento);
		panelEste.add(lblDetallesEvento);
		panelOeste.add(lblCodigoEntrada);
		panelOeste.add(lblCorreoUsuario);
		panelOeste.add(lblPrecioEntrada);
		
		
		lblPrecioEntrada = new JLabel("El precio de la entrada es:" + entradaReventaActual.getPrecioReventa());
		lblTituloDetallesEvento = new JLabel("Detalles del evento:");
		lblCodigoEntrada = new JLabel("Codigo Entrada:" + entradaReventaActual.getCodigoEntrada());
		lblCorreoUsuario = new JLabel("Correo Electronico: " + entradaReventaActual.getCorreoUsuario());
		lblDetallesEvento = new JLabel(entradaReventaActual.getInfo());
		lImagen = new JLabel();
		
		
		
		
		
		if(eventoActual.getRutaImg() == null) {
	        ImageIcon imagen = new ImageIcon("Sell_it/src/imagenes/default.png"); 
	    	setImagen(imagen);
	    }else {
	    	String rutaImg = ev.getRutaImg();
            ImageIcon imagen = new ImageIcon(rutaImg);
            setImagen(imagen);}
		
		
		
		
		btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	VentanaEntradasEnReventaTabla ventanaEntradasEnReventaTabla = new VentanaEntradasEnReventaTabla(null);
                dispose();  
                ventanaEntradasEnReventaTabla.setVisible(true);
                
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
	
	
	public static void main(String[] args) {
		VentanaCompraEntradasReventa v1 = new VentanaCompraEntradasReventa();
		v1.setVisible(true);
	}

}
