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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class VentanaTablaInformacion extends JFrame {

	private JTable tablaInfo;
    private MiTableModel modeloInfo;
    private BaseDeDatos bdatos;
	private Usuario usuario;
	private List<String> entradas;


    public VentanaTablaInformacion(List<Evento> eventos, Usuario usuario) {
        // Configuración de la ventana
    	setTitle("Informacion sobre Eventos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);
        
        // Inicialización de variables
		this.usuario = usuario;
		this.bdatos = new BasesDeDatos.BaseDeDatos();

        // Creación del modelo y la tabla
        modeloInfo = new MiTableModel();
        tablaInfo = new JTable(modeloInfo);

        // Configuración del diseño de la interfaz
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.add(new JScrollPane(tablaInfo), BorderLayout.CENTER);

        JPanel pInferior = new JPanel();
        JButton boton = new JButton("Ordenar segun fecha");
        JButton bAnyadir = new JButton( "Añadir" );
		JButton bBorrar = new JButton( "Borrar" );
		JButton bvolver = new JButton("Volver");
		pInferior.add(bvolver);
		pInferior.add( bAnyadir );
		pInferior.add( bBorrar );
        pInferior.add(boton);

        bvolver.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
    			VentanaPerfilEntidad v = new VentanaPerfilEntidad(usuario);
			}
		});
        
       // Configuracion del boton para borrar un evento seleccionado
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
		                // Eliminar evento de la BD
		                bdatos.borrarEvento(codigoEvento);
		                // Eliminar fila del modelo
		                modeloInfo.removeRow(filaSel);
		            }  
				}
			}
		});
        
        // Configuración de boton para añadir un nuevo evento
        bAnyadir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
		        VentanaInicio ventanaI = Main.getVentanaInicio();
		        Usuario usuActual = ventanaI.getUsuarioActual();

		        dispose();
				VentanaVentaEntidad ventanaVentaEntidad = new VentanaVentaEntidad(usuActual);
	            ventanaVentaEntidad.setVisible(true);
			}
		});
        
        // Configuración del botón para ordenar los eventos por fecha
        boton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
		        // Ordenar los eventos por fecha
	            Collections.sort(modeloInfo.datos, Comparator.comparing(Evento::getFecha));
	            // Notificar al modelo de la tabla que los datos han cambiado
	            modeloInfo.fireTableDataChanged();
			}
		});
        
        // Agregar componentes a la ventana
        add(panelPrincipal, BorderLayout.CENTER);
        add(pInferior, BorderLayout.SOUTH);

        // Configuración inicial con los eventos proporcionados
        modeloInfo.setDatos(eventos);
    }

    // Clase interna para el modelo de la tabla
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
            return 7; 
        }
        private final String[] cabeceras = { "codigo", "nombre", "desc", "fecha", "ubicacion", "nEntradas", "creador"};
		@Override
		public String getColumnName(int columnIndex) {
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

		// Eliminar fila del modelo
        public void removeRow(int rowIndex) {
            if (datos != null && rowIndex >= 0 && rowIndex < datos.size()) {
                datos.remove(rowIndex);
                fireTableRowsDeleted(rowIndex, rowIndex);
            }
        }
    }
    
}