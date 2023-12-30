package ventanas;

import java.awt.*;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import org.jdesktop.swingx.JXBusyLabel;
import org.jdesktop.swingx.JXErrorPane;
import org.jdesktop.swingx.JXHyperlink;
import org.jdesktop.swingx.JXLabel;

import BasesDeDatos.BaseDeDatos;
import clases.Entrada;
import clases.Evento;
import clases.Usuario;

public class VentanaCompra extends JFrame{
	
	private Usuario usuario;
	private Entrada ent;

	private Evento evento;
	private Evento eventoActual;
	
	//Componentes de la ventana
	private JTextField tfNombre;
	private JTextField tfCorreo;
	private JTextField tfTfno;
	private JTextField tfNtarjeta;
	private JComboBox cbMes;
	private JComboBox cbAnyo;
	private JLabel lTiempo;
	private JTextField tfCCV;

	private JPanel pNTarjeta;

	private Timer timer;
    private int tiempoRestante;
    private JXBusyLabel busyLabel;
    private JXErrorPane errorPane;
    private VentanaEvento vEvento;
	private int cantidadCompra;
	private List<Entrada> entradasEnBD;
    private static BaseDeDatos baseDeDatos;
    private static VentanaPrincipal vPrincipal;
	
	public VentanaCompra(Usuario usuario, int cantidadCompra, VentanaEvento vEvento, Entrada entrada) {
    	this.baseDeDatos = new BaseDeDatos();
	    this.vEvento = vEvento;
		this.usuario = usuario;
		this.cantidadCompra = cantidadCompra;
		this.ent = entrada;
		vPrincipal = vEvento.vPrincipal;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(800, 500);
		setLocationRelativeTo(null);
		setTitle("Compra de entrada");
		
		//Obtenemos el evento al que hacemos referencia
		this.eventoActual = vEvento.getEvento();
		
		//Creamos los paneles principales
		JPanel pCentral = new JPanel(new GridLayout(2,1));
		this.add(pCentral, BorderLayout.CENTER);
		JPanel pDer = new JPanel(new BorderLayout());
		this.add(pDer, BorderLayout.EAST);
		
		
		//Panel Central
		//Dentro del panel central creamos 2 paneles
		JPanel pDatos = new JPanel(new GridLayout(4,1));
		pCentral.add(pDatos);
		JPanel pPago = new JPanel(new GridLayout(5,1));
		pCentral.add(pPago);
		
		
		//Panel Datos
		JPanel pTitulo = new JPanel();
		JXLabel lTitulo = new JXLabel();
        lTitulo.setText("<html><h2>Tus Datos</h2></html>");
		pTitulo.add(lTitulo, BorderLayout.CENTER);
		pDatos.add(pTitulo);
		
		JPanel pNombre = new JPanel();
		JLabel lNombre = new JLabel("Nombre");
		tfNombre = new JTextField();
		tfNombre.setText(usuario.getNombreUsuario());
		pNombre.add(lNombre);
		pNombre.add(tfNombre);
		pDatos.add(pNombre);
		
		JPanel pCorreo = new JPanel();
		JLabel lCorreo = new JLabel("Correo");
		tfCorreo = new JTextField();
		tfCorreo.setText(usuario.getCorreoUsuario());
		pCorreo.add(lCorreo);
		pCorreo.add(tfCorreo);
		pDatos.add(pCorreo);
		
		JPanel pTfno = new JPanel();
		JLabel lTfno = new JLabel("Teléfono");
		tfTfno = new JTextField();
		pTfno.add(lTfno);
		pTfno.add(tfTfno);
		pDatos.add(pTfno);
		
		
		//Panel Método de Pago
		JPanel pMetodoPago = new JPanel();
		JXLabel lMetodoPago = new JXLabel();
        lMetodoPago.setText("<html><h2>Método de Pago</h2></html>");
        pMetodoPago.add(lMetodoPago);
		pPago.add(pMetodoPago);
		
		pNTarjeta = new JPanel();
		JLabel lNtarjeta = new JLabel("Nº tarjeta");
		tfNtarjeta = new JTextField();
		pNTarjeta.add(lNtarjeta);
		pNTarjeta.add(tfNtarjeta);
		pPago.add(pNTarjeta);
				
		JPanel pFCad = new JPanel();
		JLabel lFechaCad = new JLabel("Fecha de caducidad");
		pFCad.add(lFechaCad);
		pPago.add(pFCad);
		
		//Componente para el CCV
		JPanel pCCV = new JPanel();
		JLabel lCCV = new JLabel("CCV");
		tfCCV = new JTextField();
		pCCV.add(lCCV);
		pCCV.add(tfCCV);
		pPago.add(pCCV);
//		
		JPanel pBotonD = new JPanel();
		JButton botonVerificar = new JButton("Verificar Tarjeta");
		pBotonD.add(botonVerificar);
		pPago.add(pBotonD);
		
		botonVerificar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (verificarCampoCCV() && verificarCampoTarjeta()) {
		            agregarCheck(tfCCV);
					agregarCheck(tfNtarjeta);
		        }
				if(tfCCV.getBackground() == Color.RED || tfNtarjeta.getBackground() == Color.RED) {
			        mostrarError(errorPane, "El número de TARJETA debe tener 16 dígitos y en CCV 3 dígitos.\n Además solo debe contener números.");
				}else {
					// Símbolo de check (✔)
				    String checkSymbol = "\u2713";
				    JOptionPane.showMessageDialog(null, "Campos Correctos" + " " + checkSymbol, "Éxito", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
//		
		
		this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                ajustarComponentes();
            }
		});
		
		JPanel pFC = new JPanel(new GridLayout(1,1));
		String[] meses = {"MES", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
		cbMes = new JComboBox<>(meses);
		String[] anyos = {"AÑO", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031"};
		cbAnyo = new JComboBox<>(anyos);

 		pFC.add(cbMes);
		pFC.add(cbAnyo);
		pFCad.add(pFC);
		
		
		//Panel Lateral
		//Creamos 2 paneles
		
		JPanel pTiempo = new JPanel(new GridLayout(3,1));
		pDer.add(pTiempo, BorderLayout.NORTH);
		JPanel pConfirmar = new JPanel();
		pDer.add(pConfirmar, BorderLayout.SOUTH);

		// Añadir enlace a terminus y condiciones usando JXHyperlink
        JXHyperlink hyperlinkTerminos = new JXHyperlink();
        hyperlinkTerminos.setText("Términos y Condiciones");
        hyperlinkTerminos.addActionListener(e -> mostrarTerminos());
        pConfirmar.add(hyperlinkTerminos);

        // Añadir indicador de actividad con JXBusyLabel;
        busyLabel = new JXBusyLabel();
        busyLabel.setBusy(true);
        busyLabel.setPreferredSize(new Dimension(25, 25));
        pConfirmar.add(busyLabel);
		
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
		
		JXLabel lTotal = new JXLabel();
		int precioTotal = (int) (cantidadCompra*ent.getPrecio());
        lTotal.setText("<html><h2>TOTAL: "+ precioTotal + "€</h2></html>");
        pTiempo.add(lTotal);
		
		
		//Panel Confirmar
		JButton bConfirmar = new JButton("Confirmar compra");
		bConfirmar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		pConfirmar.add(bConfirmar);
		
		JButton bVolver = new JButton("Volver");
		bVolver.setFont(new Font("Tahoma", Font.PLAIN, 15));
		pConfirmar.add(bVolver);
		
		bVolver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				 busyLabel.setBusy(false);
				 VentanaCompra.this.dispose();
				 VentanaEvento ve = new VentanaEvento(eventoActual, vPrincipal);
				 ve.setVisible(true);
			}
		});
		
		bConfirmar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				confirmarCompra();
			}
		});
		
		ajustarComponentes();
	}
	
	
	private boolean verificarCampoTarjeta() {
	    // Verificar longitud y si son solo números
	    String numeroTarjeta = tfNtarjeta.getText();
	    String ccv = tfCCV.getText();

	    if (numeroTarjeta.length() != 16 || !esNumero(numeroTarjeta)) {
	        tfNtarjeta.setBackground(Color.RED);
	        return false;
	    }
	    return true;
	}
	private boolean verificarCampoCCV() {
		// Verificar longitud y si son solo números
	    String numeroTarjeta = tfNtarjeta.getText();
	    String ccv = tfCCV.getText();
		if (ccv.length() != 3 || !esNumero(ccv)) {
	        tfCCV.setBackground(Color.RED);
	        return false;
	    }
	    return true;
	}
	private boolean esNumero(String str) {
	    // Verificar si una cadena contiene solo números
	    return str.matches("\\d+");
	}

	private void mostrarTerminos() {
	    // Crear un JTextArea para mostrar los términos y condiciones
	    JTextArea textArea = new JTextArea(
	            "Términos y Condiciones:\n\n" +
	                    "1. Al utilizar esta aplicación, aceptas cumplir con estos términos y condiciones.\n" +
	                    "2. Utiliza esta aplicación de acuerdo con las leyes y regulaciones locales.\n" +
	                    "3. La información proporcionada en esta aplicación es solo para fines informativos.\n" +
	                    "4. Nos reservamos el derecho de modificar, suspender o descontinuar la aplicación en cualquier momento.\n" +
	                    "5. Protege tu información de inicio de sesión y no compartas tus credenciales con otros usuarios.\n" +
	                    "6. No realices acciones que puedan dañar la integridad o el rendimiento de la aplicación.\n" +
	                    "7. El incumplimiento de estos términos puede resultar en la suspensión o eliminación de tu cuenta.\n" +
	                    "8. Estos términos y condiciones están sujetos a cambios sin previo aviso.\n" +
	                    "9. Para obtener más información, contacta con nuestro servicio de soporte."
	    );

	    // Configurar el JTextArea
	    textArea.setEditable(false);
	    textArea.setLineWrap(true);
	    textArea.setWrapStyleWord(true);

	    // Colocar el JTextArea en un JScrollPane para permitir el desplazamiento
	    JScrollPane scrollPane = new JScrollPane(textArea);
	    scrollPane.setPreferredSize(new Dimension(400, 300));

	    // Mostrar el cuadro de diálogo con los términos y condiciones
	    JOptionPane.showMessageDialog(this, scrollPane, "Términos y Condiciones", JOptionPane.INFORMATION_MESSAGE);
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
			VentanaCompra.this.dispose();
			Evento eventoActual = vEvento.getEvento();
			VentanaEvento ve = new VentanaEvento(eventoActual, vPrincipal);
			ve.setVisible(true);
		}
	}
	
	private void confirmarCompra() {
		
//		if(tfNombre.getText().equals("")|| tfCorreo.getText().equals("") || tfTfno.getText().equals("") || tfNtarjeta.getText().equals("") || cbMes.getSelectedIndex() == 0 || cbAnyo.getSelectedIndex() == 0) {
//            JOptionPane.showMessageDialog(null, "Para confirmar la comra debe introducir todos los datos.");
//            return;
//		}else {
//			JOptionPane.showMessageDialog(null, "¡Compra confirmada!", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
//			dispose();
//		}
		
		
		if (tfNombre.getText().isEmpty() || tfCorreo.getText().isEmpty() || tfTfno.getText().isEmpty() ||
	            tfNtarjeta.getText().isEmpty() || cbMes.getSelectedIndex() == 0 || cbAnyo.getSelectedIndex() == 0) {
	        JOptionPane.showMessageDialog(null, "Para confirmar la compra debe introducir todos los datos.");
	        return;
	    }
		
		String numeroTarjeta = tfNtarjeta.getText();
		String ccv = tfCCV.getText();
	    boolean esNumeroTarjetaValido = numeroTarjeta.length() == 16;
	    boolean esCCVValido = ccv.length() == 3;

	    if (!esNumeroTarjetaValido || !esCCVValido) {
	        mostrarError(errorPane, "Error al confirmar la compra.\nInténtalo de nuevo.\nRecuerda que el número de TARJETA debe tener 16 dígitos y el CCV debe tener 3 dígitos.");
	    }
	    if(!validarCorreo(tfCorreo.getText())) {
	    	mostrarError(errorPane, "Correo incorrecto. Vuelve a provar");
	    }
	    if(verificarCampoTelefono() == true) {
	    	vPrincipal = new VentanaPrincipal();
//	    	
//	    	Connection connection = DriverManager.getConnection("jdbc:sqlite:usuarios.db", "usuario", "contraseña");
//	    	Statement statement = connection.createStatement();
//	    	ResultSet resultSet = statement.executeQuery("SELECT nEntradas FROM Evento ");
//
//	    	int valorBD = 0;
//
//	    	if (resultSet.next()) {
//	    	    valorBD = resultSet.getInt("columna");
//	    	}
//	    	VentanaEvento instancia = new VentanaEvento(evento);
//			int nEntradasCompradas = instancia.entradasCompradas();
//			int nEntradasDisponibles = valorBD - nEntradasCompradas;
//			String updateQuery = "UPDATE Evento SET nEntradas = ? ";
//			try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
//			    preparedStatement.setInt(1, nEntradasDisponibles);
//			    preparedStatement.executeUpdate();
//			} catch (SQLException e) {
//			    e.printStackTrace(); 
//			}
//			connection.close();
	    	
	    	int nEntradasActualizado = vEvento.getEvento().getnEntradas() - cantidadCompra;
    		int codigoEventoActual = vEvento.getEvento().getCodigo();
//    		System.out.println("Codigo evento actual " + codigoEventoActual);
//    		Evento evento = BaseDeDatos.obtenerEventoPorCodigo(codigoEventoActual);
    		Evento evento = vEvento.getEvento();
//    		System.out.println("Evento actual " + evento);
	    	entradasEnBD = BaseDeDatos.obtenerListaEntradasSinComprarPorEvento(codigoEventoActual);
//	    	System.out.println("Entradas en BD " + entradasEnBD);
	    	
	    	// Contador para rastrear cuántas entradas se han marcado como compradas
	    	int entradasMarcadasComoCompradas = 0;

	    	for (Entrada e : entradasEnBD) {
	    	    if (entradasMarcadasComoCompradas < cantidadCompra) {
	    	    	// Obtener el correo del propietario desde la base de datos
//	    	        String propietarioCorreo = baseDeDatos.obtenerPropietarioCorreoEntrada(e.getCod());
    	            int codigoEntrada = e.getCod();
//    	            System.out.println("Entrada a comprar: " + e);
    	            baseDeDatos.marcarEntradaComoComprada(codigoEntrada, usuario.getCorreoUsuario());
    	            entradasMarcadasComoCompradas++;	
	    	    } else {
	    	        // Si ya se han marcado la cantidad necesaria de entradas, salir del bucle
	    	        break;
	    	    }
	    	}
//	    	System.out.println("Entradas compradas: " + BaseDeDatos.obtenerListaEntradasSinComprarPorEvento(codigoEventoActual));
//    		for(Entrada e: entradasEnBD) {
//	    		for(int i=0; i<cantidadCompra; i++) {
//	    			if(e.getEventoAsociado().getCodigo() == eventoActual.getCodigo()) {
//	    				if(e.getPropietario() == null) {
//	    					int codigoEntrada = e.getCod();
////	    					System.out.println(codigoEntrada + " y...... " + usuario);
//	    					baseDeDatos.marcarEntradaComoComprada(codigoEntrada, usuario.getCorreoUsuario());
////	    			    	usuario.getEntradasCompradas().add(e);
//	    				}
//	    			}
//	    		}
//    		}
	    	baseDeDatos.updateNEntradas(nEntradasActualizado, eventoActual.getCodigo());
//	    	System.out.println("Entradas de usuario " + usuario.getEntradasCompradas());
	    	tfTfno.setBackground(new Color(240, 255, 240));
	        JOptionPane.showMessageDialog(null, "Los datos introducidos son correctos", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
	        JOptionPane.showMessageDialog(null, "¡Compra confirmada!", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
	        dispose();
	        vPrincipal.setVisible(true);
	    }else {
	        JOptionPane.showMessageDialog(null, "Comprueba que el telefono introducido tiene 9 digitos", "Error", JOptionPane.INFORMATION_MESSAGE);
	    }

	}
	private boolean verificarCampoTelefono() {
	    String telefono = tfTfno.getText();
	    if (telefono.length() != 9 || !esNumero(telefono)) {
	        tfTfno.setBackground(Color.RED);
	        return false;
	    }
	    return true;
	}
	private void agregarCheck(JTextField textField) {
		String textoCampo = textField.getText();
	   
//		textField.setText(textoCampo + " " + checkSymbol);
		textField.setText(textoCampo);
	    textField.setEditable(false);
	    textField.setBackground(new Color(240, 255, 240)); // Cambiar el fondo a verde claro si se cumple la condición   
	}
	
	private void mostrarError(Component parent, String mensaje) {
	    JOptionPane.showMessageDialog(parent, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	private void ajustarComponentes() {
	    tfNombre.setPreferredSize(new Dimension(200, 40));
	    tfCorreo.setPreferredSize(new Dimension(200, 40));
	    tfTfno.setPreferredSize(new Dimension(200, 40));
	    tfNtarjeta.setPreferredSize(new Dimension(200, 40));
	    tfCCV.setPreferredSize(new Dimension(80, 40));
	    revalidate();
	    repaint();
	}

	private boolean validarCorreo(String correo) {
        if (correo == null || correo.isEmpty()) {
        	tfCorreo.setBackground(Color.RED);
            return false; // Correo nulo o vacío es inválido
        }

        // Expresión regular para validar un correo electrónico
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        
        if(correo.matches(regex)) {
        	return true;
        } else {
        	tfCorreo.setBackground(Color.RED);
        	return false;
        }
    }
	
	public static void main(String[] args) {
		Usuario u = new Usuario("Laura Lopez","laura.lopez@gmail.com","Usuario corriente","abcABC33", "", "");
		VentanaCompra v = new VentanaCompra(u, 0, null, null);
		v.setVisible(true);
	}

}
