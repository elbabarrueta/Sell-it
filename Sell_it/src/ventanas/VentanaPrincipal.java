package ventanas;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
//import javax.swing.table.DefaultTableModel;

public class VentanaPrincipal extends JFrame{

		private JTable tablaEventos;
		
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
			//ImageIcon imgMessi = new ImageIcon("\"C:\\Users\\diego\\Downloads\\fotomessi.jpg\"");
			//JLabel lblMessi = new JLabel(imgMessi);
			
			this.add(pnlNorte, BorderLayout.NORTH);
			this.add(pnlSur, BorderLayout.SOUTH);
			pnlSur.add(bVenta);
			pnlSur.add(bPerfil);
			pnlNorte.add(lblBusca);
			pnlNorte.add(tfBuscador);
			pnlNorte.add(bBuscar);
			//this.add(tbl_buscar);
			
			//this.add(lblMessi, BorderLayout.WEST);
			
			this.setBounds(400, 150, 600, 600);
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			this.setTitle("Menu Principal");
			this.setVisible(true);
			
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