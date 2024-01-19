package ventanas;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

import BasesDeDatos.BaseDeDatos;
import clases.Entrada;
import clases.Evento;
import clases.Usuario;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class VentanaTablaInformacion extends JFrame {

	private JTable tablaInfo;
    private MiTableModel modeloInfo;
    private BaseDeDatos bdatos;
	private Usuario usuario;
	private List<String> entradas;


    public VentanaTablaInformacion(List<Evento> eventos, Usuario usuario) {
        setTitle("Informacion sobre Eventos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);
		this.usuario = usuario;
		this.bdatos = new BasesDeDatos.BaseDeDatos(); // Ajusta esto según tu lógica de inicialización

        modeloInfo = new MiTableModel();
        tablaInfo = new JTable(modeloInfo);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.add(new JScrollPane(tablaInfo), BorderLayout.CENTER);

        JPanel pInferior = new JPanel();
        JButton boton = new JButton("Actualizar Datos");
        JButton bAnyadir = new JButton( "Añadir" );
		JButton bBorrar = new JButton( "Borrar" );
		JButton bvolver = new JButton("Volver");
		pInferior.add(bvolver);
		pInferior.add( bAnyadir );
		pInferior.add( bBorrar );
        boton.addActionListener(e -> setDatos());
        pInferior.add(boton);

        bvolver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				if(usuario.getTipoUsuario().equals("Usuario entidad")) {
    				VentanaPerfilEntidad v = new VentanaPerfilEntidad(usuario);
    			}else {
//    				List<Entrada> entradas = BaseDeDatos.obtenerEntradasCompradas(usuario);
//        			VentanaPerfilUsuario vPerfilUsuario = new VentanaPerfilUsuario(usuario, entradas);
//        			vPerfilUsuario.setVisible(true);
    			}
			}
		});
        
        bBorrar.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int filaSel = tablaInfo.getSelectedRow();
				if (filaSel >= 0) {
					// Obtén el código del evento de la fila seleccionada
		            int codigoEvento = (int) tablaInfo.getValueAt(filaSel, 0);
		            // Muestra un mensaje de confirmación
		            int opcion = JOptionPane.showConfirmDialog(VentanaTablaInformacion.this,
		                    "¿Seguro que quieres borrar este evento? Si aceptas, el evento dejara de estar en venta", "Confirmar borrado",
		                    JOptionPane.YES_NO_OPTION);
		            if (opcion == JOptionPane.YES_OPTION) {
		                // Remove the event from the database
		                bdatos.borrarEvento(codigoEvento);

		                // Remove the row from the table model
		                modeloInfo.removeRow(filaSel);
		            }
		            
				}
			}
		});
        bAnyadir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
		        VentanaInicio ventanaI = Main.getVentanaInicio();
		        Usuario usuActual = ventanaI.getUsuarioActual();

				VentanaVentaEntidad ventanaVentaEntidad = new VentanaVentaEntidad(usuActual);
	            ventanaVentaEntidad.setVisible(true);
				
			}
		});
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

        // Configuración inicial con los eventos proporcionados
        modeloInfo.setDatos(eventos);
    }

    public void setDatos() {
        // Puedes implementar una lógica de actualización de datos si es necesario
    }

    private static class MiTableModel extends AbstractTableModel {
        private List<Evento> datos;

        public void setDatos(List<Evento> datos) {
            this.datos = datos;
            fireTableDataChanged();
        }

        @Override
        public int getRowCount() {
            return datos != null ? datos.size() : 0;
        }

        @Override
        public int getColumnCount() {
            return 7; // Ajusta esto según la cantidad de columnas en tu modelo de Evento
        }
        private final String[] cabeceras = { "codigo", "nombre", "desc", "fecha", "ubicacion", "nEntradas", "creador"};
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

            Evento evento = datos.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return evento.getCodigo();
                case 1:
                    return evento.getNombre();
                case 2:
                    return evento.getDesc();
                case 3:
                    return evento.getFecha();
                case 4:
                    return evento.getUbicacion();
                case 5:
                    return evento.getnEntradas();
                case 6:
                    return evento.getCreador();
                default:
                    return null;
            }
        }

        // Implementa otras funciones según tu necesidad
     // Add this method to remove a row from the table model
        public void removeRow(int rowIndex) {
            if (datos != null && rowIndex >= 0 && rowIndex < datos.size()) {
                datos.remove(rowIndex);
                fireTableRowsDeleted(rowIndex, rowIndex);
            }
        }
    }
    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new VentanaTablaInformacion(null).setVisible(true));
    }
}