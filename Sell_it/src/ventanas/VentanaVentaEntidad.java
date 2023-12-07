package ventanas;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import BasesDeDatos.BaseDeDatos;
import clases.Entrada;
import clases.Evento;
import clases.Usuario;

public class VentanaVentaEntidad extends JFrame{
	private Usuario usuario;
	
	private JTextField tfNombre = new JTextField();
	private JTextField tfDesc = new JTextField();
	private JTextField tfFecha = new JTextField();
	private JTextField tfUbicacion = new JTextField();
	private JTextField tfCant = new JTextField();
	private JTextField tfPrecio = new JTextField();
	private JButton bFoto = new JButton("Subir foto");
	private String rutaImg;
	
	public VentanaVentaEntidad(Usuario usu) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(600, 500);
		setLocationRelativeTo(null);
		setTitle("Venta Entidad");
		
		//Creamos los paneles
		JPanel pSuperior = new JPanel(new BorderLayout());
		this.add(pSuperior, BorderLayout.NORTH);
		
		JPanel pCentral = new JPanel(new GridLayout(7,2));
		this.add(pCentral, BorderLayout.CENTER);
		
		JPanel pInferior = new JPanel(new BorderLayout());
		this.add(pInferior, BorderLayout.SOUTH);
		
		//Panel Superior
		JButton bMiperfil = new JButton("MI PERFIL");
		pSuperior.add(bMiperfil, BorderLayout.EAST);
		
		//Panel Cental
		JLabel lNombre = new JLabel("Nombre del evento");
		pCentral.add(lNombre);
		pCentral.add(tfNombre);
		
		JLabel lDesc = new JLabel("Descripción del evento");
		pCentral.add(lDesc);
		pCentral.add(tfDesc);
		
		JLabel lFecha = new JLabel("Fecha del evento");
		pCentral.add(lFecha);
		pCentral.add(tfFecha);
		
		JLabel lUbicacion = new JLabel("Ubicación");
		pCentral.add(lUbicacion);
		pCentral.add(tfUbicacion);
		
		JLabel lCant = new JLabel("Cantidad de entradas disponibles");
		pCentral.add(lCant);
		pCentral.add(tfCant);
		
		JLabel lPrecio = new JLabel("Precio por entrada");
		pCentral.add(lPrecio);
		pCentral.add(tfPrecio);
		
		JLabel lFoto = new JLabel("Quieres añadir una foto?");
		pCentral.add(lFoto);
		pCentral.add(bFoto);
		rutaImg = null;
		
		
		//Panel Inferior
		JButton bSubir = new JButton("Subir entrada");
		pInferior.add(bSubir, BorderLayout.EAST);
		JButton bVprincipal = new JButton("Ventana Principal");
		pInferior.add(bVprincipal, BorderLayout.WEST);
		
		
		bFoto.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
	            FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos imagen (.jpg, .png)", "jpg", "png");
	            chooser.setFileFilter(filtro);
	            int result = chooser.showOpenDialog(null);
	            if (result == JFileChooser.APPROVE_OPTION) {
	                File selectedFile = chooser.getSelectedFile();
	                rutaImg = selectedFile.getAbsolutePath();
	            }else {
            		JOptionPane.showMessageDialog(null, "Error al subir imagen, vuelve a intentarlo.");
	            }
			}
		});
		
		bMiperfil.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				VentanaPerfilEntidad vPerfilEntidad = new VentanaPerfilEntidad(usu);
			}
		});
		
		bVprincipal.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				VentanaPrincipal vPrincipal = new VentanaPrincipal();
				dispose();
			}
		});
		
		bSubir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String nombre = tfNombre.getText();
				String desc = tfDesc.getText();
				String fecha = tfFecha.getText();
				String ubicacion = tfUbicacion.getText();
				//ArrayList<Entrada> entradas = new ArrayList<Entrada>();
				String cantText = tfCant.getText();
		        String precioText = tfPrecio.getText();
				
				if (nombre.isEmpty() || desc.isEmpty() || fecha.isEmpty() || ubicacion.isEmpty() || cantText.isEmpty() || precioText.isEmpty()) {
		            JOptionPane.showMessageDialog(null, "Por favor, completa todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
		            return;
		        }
				try {
					int cantidad = Integer.parseInt(cantText);
					double precio = Double.parseDouble(precioText);			
					Evento evento = new Evento(nombre, desc, fecha, ubicacion, cantidad);
					if(rutaImg != null) {
						evento.setRutaImg(rutaImg);
					}
					//Añadir evento
					
					BaseDeDatos.anadirEventoNuevo(evento);
					//Crear entradas
	                for(int i=0; i<cantidad; i++) {
	                	Entrada entrada = new Entrada(evento, null, precio);
	                	BaseDeDatos.anadirEntradaNueva(entrada);
	                }
	                
					JOptionPane.showMessageDialog(null, "Evento subido exitosamente");
				}catch(NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Ingresa valores válidos para la cantidad y el precio", "Error", JOptionPane.ERROR_MESSAGE);
				}
								
			}
		});
		
			
	}
	public static void main(String[] args) {
		Usuario usu = new Usuario();
		VentanaVentaEntidad v = new VentanaVentaEntidad(usu);
		v.setVisible(true);
	}
}
