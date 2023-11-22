package ventanas;

import java.awt.BorderLayout;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;


public class VentanaTablaInformacion extends JFrame{

	private JTable tablaInfo;
	private MiTableModel modeloInfo;
	
	public VentanaTablaInformacion() {
		setTitle("Informacion sobre....");
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		setSize( 800, 600 );
		setLocationRelativeTo( null );
		
		tablaInfo = new JTable();
		add( new JScrollPane( tablaInfo ), BorderLayout.CENTER );
		JPanel pInferior = new JPanel();
		JButton boton = new JButton("Actualizar Datos");
		boton.addActionListener(e -> setDatos());
		pInferior.add(boton);
		add( pInferior, BorderLayout.SOUTH );

	}
	public void setDatos() {
		modeloInfo = new MiTableModel();
		tablaInfo.setModel( modeloInfo );
	}
	private class MiTableModel implements TableModel{
		
		// con datos de prueba para ver como seria la tabla
		@Override
		public int getRowCount() {
			return 5;
		}

		@Override
		public int getColumnCount() {
			return 5;
		}

		private final String[] cabeceras = { "A", "B", "C", "D", "E" };
		@Override
		public String getColumnName(int columnIndex) {
			return cabeceras[columnIndex];
		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {
			return null;
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			System.out.println( "isCellEditable" );
			if (columnIndex == 0) {
				return false;
			}
			return true;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			return null;
		}

		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void addTableModelListener(TableModelListener l) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void removeTableModelListener(TableModelListener l) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
