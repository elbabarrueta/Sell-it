package ventanas;

import java.awt.*;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import clases.Usuario;

public class VentanaPrincipal extends JFrame{

		private JTable tablaEventos;
		private DataSetUsuario dataSetUsuario;
		private VentanaInicio vent;
		
		public VentanaPrincipal(){
			
			JButton bVenta = new JButton("Venta");
			JButton bBuscar = new JButton("Buscar");
			JButton bPerfil = new JButton("Perfil");
			JTextField tfBuscador = new JTextField(20);
			JLabel lblBusca = new JLabel("¡Busca el evento que desees!");
			JPanel pnlNorte = new JPanel();
			JPanel pnlSur = new JPanel();
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
		public static void main(String[] args) {
			VentanaPrincipal v = new VentanaPrincipal();
		}
	}