package ventanas;

import java.awt.*;




import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import BasesDeDatos.BaseDeDatos;
import clases.Evento;
import clases.JLabelGrafico;
import clases.Usuario;
import datos.DataSetEvento;
import datos.DataSetUsuario;


public class VentanaPrincipal extends JFrame{

		private JTable tablaEventos;
//		private DataSetUsuario dataSetUsuario;
		private VentanaInicio vent;
		private List<VentanaEvento> listaEventos;
		private JPanel pnlCentro = new JPanel();
		
	    private static BaseDeDatos baseDeDatos; // Nueva referencia a la clase BaseDeDatos
		
	    public VentanaPrincipal(){
			
	    	baseDeDatos = new BaseDeDatos();
	    	
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
			            VentanaReventaUsuario ventanaVentaNormal = new VentanaReventaUsuario(usuActual);
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
	    
	    public void cargarEventosDesdeBD() {
	    	empezarPanel();
	        List<Evento> listaEventos = BaseDeDatos.obtenerListaEventos(baseDeDatos.getConnection());
	        System.out.println("Número de eventos recuperados: " + listaEventos.size());

	        aniadirEventoDesdeBD(listaEventos);
	        acabarPanel();
	    }
	    
		private String obtenerTipoUsuario(String nom) {
//		    HashMap<String, Usuario> usuarioT = dataSetUsuario.getUsuariosGuardados();
//		    Set<String> nombresUsuarios = usuarioT.keySet();
//
//		    for(String nombreUsuario : nombresUsuarios) {
//		        Usuario usu = usuarioT.get(nombreUsuario);
//		        if(usu.getNombreUsuario().equals(nom)) {
//		            return usu.getTipoUsuario();
//		        }
//		    }
//		    return JOptionPane.showInputDialog("Usuario no encontrado"); // si no esta en la lista
//		
			Usuario usuario = baseDeDatos.getUsuarioPorCorreo(nom);
			if(usuario != null) {
				return usuario.getTipoUsuario();
			}else {
	           return JOptionPane.showInputDialog("Usuario no encontrado");
			}
		}

//		public void cargarUsuariosInicio(DataSetUsuario dataset) {
//			this.dataSetUsuario = dataset;
//		}
		
		
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
			numCol = 2; 
			pnlCentro.removeAll(); 
		}
		
		 public void aniadirEventoDesdeBD(List<Evento> eventos) {
		        numCol++;
		        if (numCol == numColFila) {
		            numCol = 0;
		            pnlActual = new JPanel();
		            pnlActual.setLayout(new BoxLayout(pnlActual, BoxLayout.X_AXIS));
		            pnlCentro.add(pnlActual);
		        }
//		        pnlActual = new JPanel();
//	            pnlActual.setLayout(new BoxLayout(pnlActual, BoxLayout.X_AXIS));
//	            pnlCentro.add(pnlActual);
		        for(Evento evento: eventos) {
//		        	System.out.println("evento: "+ evento);
		        	String tituloEvento = evento.getNombre();
			        String descripcionEvento = evento.getDesc();
				    System.out.println("AñadirEventoDesdeBD:"+ evento);

			        pnlActual.add(new Mipanel(tituloEvento, descripcionEvento));
		        }
		    }
		public void acabarPanel() {
			pnlCentro.revalidate(); 
		}
		
		private static String[] fotos = new String[] {  };//TEngo ue poner unas fotos que me he descargado

		
		private static class Mipanel extends JPanel {
			public Mipanel( String titulo, String descripcion ) {
				setLayout( new BorderLayout() );
				JLabel lblTitulo = new JLabel( titulo, JLabel.CENTER );
				add( lblTitulo, BorderLayout.NORTH );
				JTextArea taDescripcion = new JTextArea( descripcion, 2, 3); 
				add( new JScrollPane( taDescripcion ), BorderLayout.CENTER );
//				String foto = fotos[(new Random()).nextInt(fotos.length)];
				//JLabelGrafico grafico = new JLabelGrafico(foto, 50, 80 ); No se porque me da error
			//	add( grafico, BorderLayout.EAST );
			}
		}
		/*public static List<Evento> obtenerListaEvento(Connection con){
			String sql = "SELECT * FROM Evento";
			List<Evento> l = new ArrayList<>();
			try {
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(sql);
				while(rs.next()) {
					String codigo = rs.getString("codigo");
					String nombre = rs.getString("nombre");
					String desc = rs.getString("desc");
					String fecha = rs.getString("fecha");
					String ubicacion = rs.getString("ubicacion");
					int nEntradas = rs.getInt("nEntradas");
					double precio = rs.getDouble("precio");
					String rutaImg = rs.getString("rutaImg");
					Evento e = new Evento(codigo, nombre,desc,fecha,ubicacion,nEntradas,precio,rutaImg);
					l.add(e);
				}
				rs.close();
				st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return l;
		}*/

		public static void main(String[] args) throws IOException {
//			VentanaPrincipal v = new VentanaPrincipal();
//		    v.empezarPanel();
//		    
//		    DataSetEvento dataset = new DataSetEvento("evento.txt");
//		    HashMap<String, Evento> mapa = DataSetEvento.getMapaEvento();
//		    for(Evento e: mapa.values()) {
//		    	v.aniadirEvento(e);
//		    }
//
//		    for (int i = 0; i < 7; i++) {
//		        
//		        String nombreEvento = "Evento " + (i + 1);
//		        String descripcionEvento = "Descripción del evento " + (i + 1);
//
//		        
//		        Evento evento = new Evento(nombreEvento, descripcionEvento, "Fecha", "Ubicación", 100, 50.0);
//
//		       
//		        v.aniadirEvento(evento);
//		    }
//
//		    v.acabarPanel();
		}

	}