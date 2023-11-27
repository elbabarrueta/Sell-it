package ventanas;

import java.awt.*;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import clases.Evento;
import clases.Usuario;
import datos.DataSetUsuario;
import es.deusto.prog3.cap06.EjemploMuchosPanelesConScroll.Dato;
import es.deusto.prog3.cap06.EjemploMuchosPanelesConScroll.MiPanel;
import es.deusto.prog3.utils.JLabelGrafico;

public class VentanaPrincipal extends JFrame{

		private JTable tablaEventos;
		private DataSetUsuario dataSetUsuario;
		private VentanaInicio vent;
		private List<VentanaEvento> listaEventos;
		private JPanel pnlCentro;
		
		public VentanaPrincipal(){
			
			JButton bVenta = new JButton("Venta");
			JButton bBuscar = new JButton("Buscar");
			JButton bPerfil = new JButton("Perfil");
			JTextField tfBuscador = new JTextField(20);
			JLabel lblBusca = new JLabel("¡Busca el evento que desees!");
			JPanel pnlNorte = new JPanel();
			JPanel pnlSur = new JPanel();
			
			JScrollPane scrollCentro = new JScrollPane();
			pnlNorte.setLayout(new FlowLayout());
			DefaultTableModel modelo = new DefaultTableModel();
			modelo.addColumn("Código");
			modelo.addColumn("Nombre");
			
			
			tablaEventos = new JTable();
			add( new JScrollPane( tablaEventos ), BorderLayout.CENTER );
			//JTable tbl_buscar = new JTable();
			
			this.add(pnlNorte, BorderLayout.NORTH);
			this.add(pnlSur, BorderLayout.SOUTH);
			pnlSur.add(bVenta);
			pnlSur.add(bPerfil);
			pnlNorte.add(lblBusca);
			pnlNorte.add(tfBuscador);
			pnlNorte.add(bBuscar);
			pnlCentro.setLayout(new BoxLayout(pnlCentro,BoxLayout.Y_AXIS));
			add( new JScrollPane( pnlCentro ) , BorderLayout.CENTER );
			
			
			
			//this.add(tbl_buscar);
			
			//this.add(lblMessi, BorderLayout.WEST);
			
			bPerfil.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					VentanaInicio ventanaI = Main.getVentanaInicio();
					Usuario usuActual = ventanaI.getUsuarioActual();
					String tipoUsu = usuActual.getTipoUsuario();
					if("Usuario corriente".equals(tipoUsu)) {
						dispose();
						VentanaPerfilUsuario ventanaPerfilUsuario = new VentanaPerfilUsuario(usuActual, null);
						//ventanaPerfilUsuario.setVisible(true);
					}else {
						dispose();
						VentanaPerfilEntidad ventanaPerfilEntidad = new VentanaPerfilEntidad(usuActual);	
						//ventanaPerfilEntidad.setVisible(true);
					}
				}
			});
			bVenta.addActionListener(new ActionListener() {
			    @Override
			    public void actionPerformed(ActionEvent e) {
			        VentanaInicio ventanaI = Main.getVentanaInicio();
			        Usuario usuActual = ventanaI.getUsuarioActual();
			        String tipoUsu = usuActual.getTipoUsuario();

			        if ("Usuario corriente".equals(tipoUsu)) {
			            
			            dispose();
			            VentanaReventa ventanaVentaNormal = new VentanaReventa(usuActual);
			            ventanaVentaNormal.setVisible(true);
			        } else {
			           
			            dispose();
			            VentanaVentaEntidad ventanaVentaEntidad = new VentanaVentaEntidad(usuActual);
			            ventanaVentaEntidad.setVisible(true);
			        }
			    }
			});
			
			this.setBounds(400, 150, 600, 600);
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			this.setTitle("Menu Principal");
			this.setVisible(true);
			
		}		
		private String obtenerTipoUsuario(String nom) {
		    HashMap<String, Usuario> usuarioT = dataSetUsuario.getUsuariosGuardados();
		    Set<String> nombresUsuarios = usuarioT.keySet();

		    for(String nombreUsuario : nombresUsuarios) {
		        Usuario usu = usuarioT.get(nombreUsuario);
		        if(usu.getNombreUsuario().equals(nom)) {
		            return usu.getTipoUsuario();
		        }
		    }
		    return JOptionPane.showInputDialog("Usuario no encontrado"); // si no esta en la lista
		}

		
		
		public void cargarUsuariosInicio(DataSetUsuario dataset) {
			this.dataSetUsuario = dataset;
		}
		
		
		/*
		private void mostrarTabla() {
			DefaultTableModel modelo = new DefaultTableModel();
			modelo.addColumn("Código");
			modelo.addColumn("Nombre");
			
			tbl_buscar.setModel(modelo);
			
			tbl_buscar.getColumnModel().getColumn(0).setPreferredWidth(3);
			tbl_buscar.getColumnModel().getColumn(1).setPreferredWidth(150);
			
			String sqlEventosDisponibles = "";
			
			String datos[] = new String[2];
		}
		*/
		
		private int numCol;  
		private final int numColFila = 3;  
		private JPanel pnlActual = null;   
		public void empezarPanel() {
			numCol = 4; 
			pnlCentro.removeAll(); 
		}
		
		public void aniadirEvento( Evento evento ) {
			numCol++;  // Incremento la columna en la que se añade
			if (numCol==numColFila) {  // Si está llena la fila (última columna) se empieza fila nueva
				numCol = 0;
				pnlActual = new JPanel();
				pnlActual.setLayout(new BoxLayout(pnlActual,BoxLayout.X_AXIS));
				pnlCentro.add( pnlActual );
			}
		//	pnlActual.add( new panel( /*titulo evento*/, /*Descripcion evento*/ ) ); 
		}
		public void acabarPanel() {
			pnlCentro.revalidate(); 
		}
		
		private static String[] fotos = new String[] { /*fotos*/ };
		
		//private static class panel extends JPanel {
		//	public panel( String titulo, String descripcion ) {
		//		setLayout( new BorderLayout() );
		//		JLabel lblTitulo = new JLabel( titulo, JLabel.CENTER );
		//		add( lblTitulo, BorderLayout.NORTH );
		//		JTextArea taDescripcion = new JTextArea( descripcion, 2, 3);  // Atención - este marca el tamaño visual que va a "pedir" la textarea, 5 filas y 10 columnas
		//		add( new JScrollPane( taDescripcion ), BorderLayout.CENTER );
				//Aqui hay que decidir que fotos poner para cada evento
			//	JLabelGrafico fotos = new JLabelGrafico( fotos, 50, 80 ); copio la case JLabelGrafico de Andoni??
			//	add( fotos, BosrderLayout.EAST );
		//	}
		//}


		
		
		
		
		public static void main(String[] args) {
			VentanaPrincipal v = new VentanaPrincipal();
		//	v.empezarPanel();
		//	for (int i=0; i<7; i++) {
		//		Evento evento = new Evento( "Evento " + /*Titulo del evento*/, "Decripcion " + /*Descripcion del Evento*/  );
		//		v.aniadirEvento( evento );
		//	}
		//	v.acabarPanel();
		}
	}