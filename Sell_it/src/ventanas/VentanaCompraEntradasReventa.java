package ventanas;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import clases.EntradaReventa;
import clases.Usuario;

public class VentanaCompraEntradasReventa extends JFrame{
	private JPanel panelPrincipal;
	private JPanel panelSur;
	private JButton btnCompra;
	private JButton btnVolver;
	private JButton btnValorar;
	private JLabel lblDetallesEvento;
	private JLabel lblTituloDetalesEvento;
	private JLabel lblNombre;
	private JLabel lblFecha;
	private JLabel lblUbicacion;
	private JLabel lblPrecioEntrada;
	
	private EntradaReventa entradaReventaActual;
	private EntradaReventa entradaInformacion;
	
	//Añadir al darle a comprar un mensaje recordadndo que ha comprado una entrada
	//Falta la foto
	public VentanaCompraEntradasReventa() {
		this.setBounds(900,300,400,400);
		this.setTitle("Entrada Reventa");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		panelPrincipal = new JPanel(new BorderLayout());
		panelSur = new JPanel(new BorderLayout());
		
		panelPrincipal.add(panelSur,BorderLayout.SOUTH);
		this.add(panelPrincipal);
		
		lblPrecioEntrada = new JLabel("El precio de la entrada es:");
		lblTituloDetalesEvento = new JLabel("Detalles del evento:");
		
		
		new JButton("Comprar");
		panelSur.add(btnCompra);
		panelSur.add(btnVolver);
		
		
		
		
		btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	VentanaEntradasEnReventaTabla ventanaEntradasEnReventaTabla = new VentanaEntradasEnReventaTabla(null);
                dispose();  
                ventanaEntradasEnReventaTabla.setVisible(true);
                
            }
        });
		
	}
	public void setEntradaReventaActual(EntradaReventa entradaReventaActual) {
        this.entradaReventaActual = entradaReventaActual;
        actualizarPrecio(); 
    }
	private void actualizarPrecio() {
        
        if (entradaReventaActual != null) {
            lblPrecioEntrada.setText("El precio de la entrada es: " + entradaReventaActual.getPrecioReventa());
            
        }
    }
	
	public void setEntradaInformacion(EntradaReventa entradaInformacion) {
        this.entradaInformacion = entradaInformacion;
        actualizarInformacion(); 
    }
	private void actualizarInformacion() {
        
        if (entradaInformacion != null) {
        	lblDetallesEvento.setText( entradaInformacion.getInfo());
            
        }
    }
	
	public static void main(String[] args) {
		VentanaCompraEntradasReventa v1 = new VentanaCompraEntradasReventa();
		v1.setVisible(true);
	}

}
