package ventanas;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.DecimalFormat;

import javax.swing.*;

import clases.Usuario;

public class VentanaCompra extends JFrame{
	
	private Usuario usuario;
	
	//Componentes de la ventana
	private JTextField tfNombre;
	private JTextField tfCorreo;
	private JTextField tfTfno;
	private JTextField tfNtarjeta;
	private JComboBox cbMes;
	private JComboBox cbAnyo;
	private JLabel lTiempo;

	private Timer timer;
    private int tiempoRestante;

	
	public VentanaCompra(Usuario usuario) {
		this.usuario = usuario;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(600, 500);
		setLocationRelativeTo(null);
		setTitle("Compra de entrada");
		
		//Creamos los paneles principales
		JPanel pCentral = new JPanel(new GridLayout(2,1));
		this.add(pCentral, BorderLayout.CENTER);
		JPanel pDer = new JPanel(new BorderLayout());
		this.add(pDer, BorderLayout.EAST);
		
		
		//Panel Central
		//Dentro del panel central creamos 2 paneles
		JPanel pDatos = new JPanel(new GridLayout(4,1));
		pCentral.add(pDatos);
		JPanel pPago = new JPanel(new GridLayout(3,1));
		pCentral.add(pPago);
		
		
		//Panel Datos
		JPanel pTitulo = new JPanel();
		JLabel lDatos = new JLabel("Tus Datos");
		pTitulo.add(lDatos, BorderLayout.CENTER);
		pDatos.add(pTitulo);
		
		JPanel pNombre = new JPanel();
		JLabel lNombre = new JLabel("Nombre");
		tfNombre = new JTextField();
		tfNombre.setText(usuario.getNombreUsuario());
		tfNombre.setPreferredSize(new Dimension(200, 20));
		pNombre.add(lNombre);
		pNombre.add(tfNombre);
		pDatos.add(pNombre);
		
		JPanel pCorreo = new JPanel();
		JLabel lCorreo = new JLabel("Correo");
		tfCorreo = new JTextField();
		tfCorreo.setText(usuario.getCorreoUsuario());
		tfCorreo.setPreferredSize(new Dimension(200, 20));
		pCorreo.add(lCorreo);
		pCorreo.add(tfCorreo);
		pDatos.add(pCorreo);
		
		JPanel pTfno = new JPanel();
		JLabel lTfno = new JLabel("Teléfono");
		tfTfno = new JTextField();
		tfTfno.setPreferredSize(new Dimension(200, 20));
		pTfno.add(lTfno);
		pTfno.add(tfTfno);
		pDatos.add(pTfno);
		
		
		//Panel Método de Pago
		JPanel pMetodoPago = new JPanel();
		JLabel lPago = new JLabel("Método de pago");
		pMetodoPago.add(lPago);
		pPago.add(pMetodoPago);
		
		JPanel pNTarjeta = new JPanel();
		JLabel lNtarjeta = new JLabel("Nº tarjeta");
		tfNtarjeta = new JTextField();
		tfNtarjeta.setPreferredSize(new Dimension(200, 20));
		pNTarjeta.add(lNtarjeta);
		pNTarjeta.add(tfNtarjeta);
		pPago.add(pNTarjeta);
		
		JPanel pFCad = new JPanel();
		JLabel lFechaCad = new JLabel("Fecha de caducidad");
		pFCad.add(lFechaCad);
		
		JPanel pFC = new JPanel(new GridLayout(1,1));
		String[] meses = {"MES", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
		cbMes = new JComboBox<>(meses);
		String[] anyos = {"AÑO", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031"};
		cbAnyo = new JComboBox<>(anyos);

 		pFC.add(cbMes);
		pFC.add(cbAnyo);
		pFCad.add(pFC);
		
		pPago.add(pFCad);
		
		
		//Panel Lateral
		//Creamos 2 paneles
		
		JPanel pTiempo = new JPanel(new GridLayout(3,1));
		pDer.add(pTiempo, BorderLayout.NORTH);
		JPanel pConfirmar = new JPanel();
		pDer.add(pConfirmar, BorderLayout.SOUTH);
		
		
		//Panel Tiempo restante y Total
		lTiempo = new JLabel("Tiempo restante: 10:00");
		lTiempo.setHorizontalAlignment(SwingConstants.CENTER);
		pTiempo.add(lTiempo);
		
		tiempoRestante = 600; //10 minutos en segundos
		timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actualizarTiempo();
			}
		});
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				timer.start();
			}
		});
		
		JLabel lTotal = new JLabel("TOTAL: €");
		pTiempo.add(lTotal);
		
		
		//Panel Confirmar
		JButton bConfirmar = new JButton("CONFIRMAR COMPRA");
		pConfirmar.add(bConfirmar);
		
		bConfirmar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				confirmarCompra();
			}
		});
	}

	private void actualizarTiempo() {
		tiempoRestante--;
		if(tiempoRestante >= 0) {
			int minutos = tiempoRestante/60;
			int segundos = tiempoRestante%60;
			
			DecimalFormat formato = new DecimalFormat("00");
			lTiempo.setText("Tiempo restante: " + formato.format(minutos) + ":" + formato.format(segundos));
		}else {
			timer.stop();
			JOptionPane.showMessageDialog(null, "Límite de tiempo excedido. Por favor, inténtalo de nuevo", "Tiempo agotado", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void confirmarCompra() {
		if(tfNombre.getText().equals("")|| tfCorreo.getText().equals("") || tfTfno.getText().equals("") || tfNtarjeta.getText().equals("") || cbMes.getSelectedIndex() == 0 || cbAnyo.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Para confirmar la comra debe introducir todos los datos.");
            return;
		}else {
			JOptionPane.showMessageDialog(null, "¡Compra confirmada!", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
			dispose();
		}
	}
	
	
	public static void main(String[] args) {
		Usuario u = new Usuario("Laura Lopez","laura.lopez@gmail.com","Usuario corriente","abcABC33");
		VentanaCompra v = new VentanaCompra(u);
		v.setVisible(true);
	}

}
