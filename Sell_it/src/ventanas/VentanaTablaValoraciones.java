package ventanas;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import BasesDeDatos.BaseDeDatos;
import clases.Usuario;
import clases.Valoracion;

public class VentanaTablaValoraciones extends JFrame{
	 	private JTable tablaInfo;
	    private MiTableModel modeloInfor;
	    private Usuario usuario;
	public VentanaTablaValoraciones(List<Valoracion> val, Usuario usuario) {
		setTitle("Informacion sobre Eventos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);
		this.usuario = usuario;

        modeloInfor = new MiTableModel();
        tablaInfo = new JTable(modeloInfor);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.add(new JScrollPane(tablaInfo), BorderLayout.CENTER);

        JPanel pInferior = new JPanel();
        JButton boton = new JButton("Volver atras");
        pInferior.add(boton);

        boton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				 // Cerrar la ventana actual
		        dispose();
		        VentanaPerfilEntidad.mostrarEventosEnVentaDelUsuario(usuario);
		        
				
			}
		});
        add(panelPrincipal, BorderLayout.CENTER);
        add(pInferior, BorderLayout.SOUTH);
        
//        cargarEntradasCompradas();
//        this.setVisible(true);
        
        // Configuración inicial con los eventos proporcionados
        modeloInfor.setDatos(val);
    }

    
//    private void cargarEntradasCompradas() {
//        List<Entrada> entradasCompradas = BaseDeDatos.obtenerEntradasCompradas(usuario);
//        modeloInfor.setDatos(entradasCompradas);
//    }

    private static class MiTableModel extends AbstractTableModel {
        private List<Valoracion> datos;
        private final String[] cabeceras = { "usuario_revisor", "puntuacion", "comentario"};

        public void setDatos(List<Valoracion> datos) {
            this.datos = datos;
            fireTableDataChanged();
        }

        @Override
        public int getRowCount() {
            return datos != null ? datos.size() : 0;
        }

        @Override
        public int getColumnCount() {
            return cabeceras.length; // Ajusta esto según la cantidad de columnas en tu modelo de Evento
        }
		@Override
		public String getColumnName(int columnIndex) {
//			System.out.println( "getColumnName " + columnIndex );
			return cabeceras[columnIndex];
		}
		@Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            if (datos == null || rowIndex < 0 || rowIndex >= datos.size()) {
                return null;
            }

            Valoracion valoracion = datos.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return valoracion.getUsuarioRevisor();
                case 1:
                    return valoracion.getPuntuacion();
                case 2:
                    return valoracion.getComentario();
                default:
                    return null;
            }
		}
    }
    
    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new VentanaTablaInformacion(null).setVisible(true));
    }
	
}
