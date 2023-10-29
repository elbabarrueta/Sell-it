package ventanas;

import java.awt.*;
import javax.swing.*;

public class VentanaCompra extends JFrame{
	
	//Componentes
	private JTextField tfNombre = new JTextField();
	private JTextField tfApellidos = new JTextField();
	private JTextField tfeMail = new JTextField();
	private JTextField tfTfno = new JTextField();
	private JTextField tfNtarjeta = new JTextField();
	private JTextField tfMes = new JTextField("Mes");
	private JTextField tfAnyo = new JTextField("Año");


	
	public VentanaCompra() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(600, 500);
		setLocationRelativeTo(null);
		setTitle("Compra de entrada");
		
		//Creamos los paneles principales
		JPanel pCentral = new JPanel(new GridLayout(2,1));
		this.add(pCentral, BorderLayout.CENTER);
		JPanel pIzq = new JPanel(new BorderLayout());
		this.add(pIzq, BorderLayout.EAST);
		
		
		//Panel Central
		//Dentro del panel central creamos 2 paneles
		JPanel pDatos = new JPanel(new GridLayout(5,2));
		pCentral.add(pDatos);
		JPanel pPago = new JPanel(new GridLayout(3,2));
		pCentral.add(pPago);
		
		
		//Panel Datos
		JLabel lDatos = new JLabel("Tus Datos");
		JLabel lUsuario = new JLabel("Nombre Ususario");
		pDatos.add(lDatos);
		pDatos.add(lUsuario);
		
		JLabel lNombre = new JLabel("Nombre");
		pDatos.add(lNombre);
		pDatos.add(tfNombre);

		JLabel lApellidos = new JLabel("Apellidos");
		pDatos.add(lApellidos);
		pDatos.add(tfApellidos);

		JLabel leMail = new JLabel("eMail");
		pDatos.add(leMail);
		pDatos.add(tfeMail);

		JLabel lTfno = new JLabel("Teléfono");
		pDatos.add(lTfno);
		pDatos.add(tfTfno);
		
		
		//Panel Método de Pago
		JLabel lPago = new JLabel("Método de pago");
		pPago.add(lPago);
		JLabel l = new JLabel("");
		pPago.add(l);
		JLabel lNtarjeta = new JLabel("Nº tarjeta");
		pPago.add(lNtarjeta);
		pPago.add(tfNtarjeta);
		JLabel lFechaCad = new JLabel("Fecha de caducidad");
		pPago.add(lFechaCad);
		
		JPanel pFC = new JPanel(new GridLayout(1,1));
		pFC.add(tfMes);
		pFC.add(tfAnyo);
		pPago.add(pFC);
		
		
		//Panel Lateral
		//Creamos 2 paneles
		JPanel pTiempo = new JPanel(new GridLayout(3,1));
		pIzq.add(pTiempo, BorderLayout.NORTH);
		JPanel pConfirmar = new JPanel();
		pIzq.add(pConfirmar, BorderLayout.SOUTH);
		
		
		//Panel Tiempo restante y Total
		JLabel lTiempo = new JLabel("Tiempo restante");
		pTiempo.add(lTiempo);
		
		JLabel lTotal = new JLabel("TOTAL: €");
		pTiempo.add(lTotal);
		
		
		//Panel Confirmar
		JButton bConfirmar = new JButton("CONFIRMAR COMPRA");
		pConfirmar.add(bConfirmar);
		
		
		
	}
	public static void main(String[] args) {
		VentanaCompra v = new VentanaCompra();
		v.setVisible(true);
	}

}
