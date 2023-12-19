package ventanas;

import java.awt.*;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXSearchField;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.HighlighterFactory;

import BasesDeDatos.BaseDeDatos;
import clases.Evento;
import clases.JLabelGrafico;
import clases.Usuario;

public class VentanaPrincipal extends JFrame{

		private JXTable tablaEventos;
//		private DataSetUsuario dataSetUsuario;
		private VentanaInicio vent;
		private List<VentanaEvento> listaEventos;
		private JPanel pnlCentro = new JPanel();
	    private JXSearchField searchField;
	    private static JLabel lblImagen;
	    private static VentanaPrincipal vPrincipal;
		
	    private static BaseDeDatos baseDeDatos; // Nueva referencia a la clase BaseDeDatos
		
	    public VentanaPrincipal(){
			
	    	baseDeDatos = new BaseDeDatos();
	    	cargarEventosDesdeBD();
	    	
			JButton bVenta = new JButton("Venta");
//			JButton bBuscar = new JButton("Buscar");
			JButton bPerfil = new JButton("Perfil");
//			JTextField tfBuscador = new JTextField(20);
//			JLabel lblBusca = new JLabel("¡Busca el evento que desees!");
			JPanel pnlNorte = new JPanel();
			JPanel pnlSur = new JPanel();
			
			searchField = new JXSearchField("¡Busca el evento que desees!");
	        pnlCentro.add(searchField, BorderLayout.NORTH);
			
			JScrollPane scrollCentro = new JScrollPane();
			pnlNorte.setLayout(new FlowLayout());
			DefaultTableModel modelo = new DefaultTableModel();
			modelo.addColumn("Código");
			modelo.addColumn("Nombre");
			
			
//			tablaEventos = new JTable();
//			add( new JScrollPane( tablaEventos ), BorderLayout.CENTER );
	        // Configuración de la tabla con SwingX
			tablaEventos = new JXTable();
	        tablaEventos.setHighlighters(HighlighterFactory.createSimpleStriping());
	        tablaEventos.setColumnControlVisible(true);
	        add(new JScrollPane(tablaEventos), BorderLayout.CENTER);
			//JTable tbl_buscar = new JTable();
			
			this.add(pnlNorte, BorderLayout.NORTH);
			this.add(pnlSur, BorderLayout.SOUTH);
			pnlSur.add(bVenta);
			pnlSur.add(bPerfil);
//			pnlNorte.add(lblBusca);
//			pnlNorte.add(tfBuscador);
			pnlNorte.add(searchField);
//			pnlNorte.add(bBuscar);
			pnlCentro.setLayout(new BoxLayout(pnlCentro,BoxLayout.Y_AXIS));
			add( new JScrollPane( pnlCentro ) , BorderLayout.CENTER );
		
			//this.add(tbl_buscar);

			searchField.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
//	                String textoBusqueda = tfBuscador.getText().toLowerCase();
	                String textoBusqueda = searchField.getText().toLowerCase();
	            	List<Evento> eventosFiltrados = filtrarEventosPorPalabrasClave(textoBusqueda);
	                actualizarVisualizacionEventos(eventosFiltrados);
	            }
	        });
			
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
			
//			List<Evento> eventosBD = BaseDeDatos.obtenerListaEventos();
//			for(Evento e: eventosBD) {
//				aniadirEvento(e);
//			}
			
			this.setBounds(55, 50, 1200, 600);
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			this.setTitle("Menu Principal");
			this.setVisible(true);
			vPrincipal = this;
		}		
	    

	    public void cargarEventosDesdeBD() {
	    	empezarPanel();
	        List<Evento> listaEventos = BaseDeDatos.obtenerListaEventos();
	        System.out.println("Número de eventos recuperados: " + listaEventos.size());

	        aniadirEventoDesdeBD(listaEventos);
	        acabarPanel();
	    }
	    
	    private List<Evento> filtrarEventosPorPalabrasClave(String palabrasClave) {
	        List<Evento> eventosFiltrados = new ArrayList<>();
	        for (Evento evento : BaseDeDatos.obtenerListaEventos()) {
	            String nombreEvento = evento.getNombre().toLowerCase();
	            String descripcionEvento = evento.getDesc().toLowerCase();

	            // Verificar si el texto de búsqueda está presente en el nombre o la descripción
	            if (nombreEvento.contains(palabrasClave) || descripcionEvento.contains(palabrasClave)) {
	                eventosFiltrados.add(evento);
	            }
	        }
	        return eventosFiltrados;
	    }
	    
	 // Método para actualizar la visualización de eventos en la ventana
	    private void actualizarVisualizacionEventos(List<Evento> eventos) {
	    	pnlActual = null;
	    	empezarPanel();
	        aniadirEventoDesdeBD(eventos);
	        acabarPanel();
	        pnlCentro.repaint();
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
		private final int NUM_COLS = 3;  
		private JPanel pnlActual = null;   
		public void empezarPanel() {
			numCol = 2; 
			pnlCentro.removeAll(); 
		}
		
		 public void aniadirEventoDesdeBD(List<Evento> eventos) {
			 for (Evento evento : eventos) {
			        if (numCol == NUM_COLS || pnlActual == null) {
			            numCol = 0;
			            pnlActual = new JPanel();
			            pnlActual.setLayout(new BoxLayout(pnlActual, BoxLayout.X_AXIS));
			            pnlCentro.add(pnlActual);
			        }
			        
			        if (pnlActual != null) {
			        	Mipanel panel = new Mipanel(evento);
			        	panel.setPreferredSize(new Dimension(350, 300));
			            pnlActual.add(panel);
			            numCol++;
			        }
			    }
		    }
		public void acabarPanel() {
			pnlCentro.revalidate();
			pnlCentro.repaint();
		}
		
//		private static String[] fotos = new String[] {  };//TEngo ue poner unas fotos que me he descargado

		private static class Mipanel extends JPanel {
			public Mipanel(Evento evento) {
		        setLayout(null);
		        addMouseListener(new MouseAdapter() {
		        	@Override
					public void mouseClicked(MouseEvent e) {
						VentanaEvento v = new VentanaEvento(evento);
						v.setVisible(true);
						vPrincipal.dispose();
					}
				});
		        
		        String titulo = evento.getNombre();
		        String fecha = evento.getFecha();
		        String rutaImagen = evento.getRutaImg();
		        
		        JLabel lblNombre = new JLabel(titulo);
				lblNombre.setForeground(Color.BLACK);
				lblNombre.setFont(new Font("Eras Demi ITC", Font.PLAIN, 16));
				lblNombre.setBounds(10, 235, 331, 34);
				add(lblNombre);
				
				JLabel lblFecha = new JLabel(fecha);
				lblFecha.setForeground(Color.BLACK);
				lblFecha.setFont(new Font("Eras Demi ITC", Font.PLAIN, 12));
				lblFecha.setBounds(10, 215, 331, 34);
				add(lblFecha);
				
		        
//		        JLabel lblTitulo = new JLabel(titulo, JLabel.CENTER);
//		        add(lblTitulo, BorderLayout.NORTH);
//		        JTextArea taDescripcion = new JTextArea(descripcion, 2, 3);
//		        taDescripcion.setEditable(false);
//		        taDescripcion.setRows(1);  // Establece el número de filas deseado
//		        taDescripcion.setColumns(10);  // Establece el número de columnas deseado
//		        add(taDescripcion, BorderLayout.CENTER);
//		        add(new JScrollPane(taDescripcion), BorderLayout.CENTER);

		        // Mostrar imagen a la derecha
		        lblImagen = new JLabel();
		        cargarImagen(rutaImagen);
//		        lblImagen.setBounds(0, 0, 252, 182);
		        lblImagen.setBounds(10, 14, 331, 204);
				add(lblImagen);
		    }

		    private void cargarImagen(String rutaImagen) {
		    	ImageIcon imagen;
		    	if (rutaImagen != null) {
		            try {
		                imagen = new ImageIcon(rutaImagen);
		                fotoPerfil(imagen);
		                System.out.println("Imagen cargada correctamente: " + rutaImagen);
		            } catch (Exception e) {
		                System.err.println("Error al cargar la imagen: " + rutaImagen);
		                e.printStackTrace();
		                imagen = obtenerImagenPorDefecto();

		            }
		        } else {
		            // No hay ruta de imagen válida, usar imagen por defecto
		            System.out.println("No hay ruta de imagen válida proporcionada. Usando imagen por defecto.");
	                imagen = obtenerImagenPorDefecto();

		        }
		        fotoPerfil(imagen);

		    }
		    private ImageIcon obtenerImagenPorDefecto() {
		        return new ImageIcon("Sell_it/src/imagenes/default.png");
		    }
		}
		
		private static  void fotoPerfil(ImageIcon imagenPerfil) {
	        int maxWidth = 330; // Tamaño máximo de ancho
	        int maxHeight = 200; // Tamaño máximo de alto
	        int newWidth, newHeight;
	        Image img = imagenPerfil.getImage();
	        if (imagenPerfil.getIconWidth() > imagenPerfil.getIconHeight()) {
	            newWidth = maxWidth;
	            newHeight = (maxWidth * imagenPerfil.getIconHeight()) / imagenPerfil.getIconWidth();
	        } else {
	            newHeight = maxHeight;
	            newWidth = (maxHeight * imagenPerfil.getIconWidth()) / imagenPerfil.getIconHeight();
	        }
	        // Redimensiona la imagen
	        Image newImg = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
	        imagenPerfil = new ImageIcon(newImg);
	        lblImagen.setIcon(imagenPerfil);
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
		}

	}