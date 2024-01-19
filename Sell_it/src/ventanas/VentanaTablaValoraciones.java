package ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.util.Comparator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import BasesDeDatos.BaseDeDatos;
import clases.Usuario;
import clases.Valoracion;

public class VentanaTablaValoraciones extends JFrame{
	 	private JTable tablaInfo;
	    private MiTableModel modeloInfor;
	    private Usuario usuario;
	    private JButton cambiarColorBoton;
	    private JButton ordenarPuntuacionBoton;

	    
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
        JButton boton = new JButton("Volver");
        pInferior.add(boton);

        cambiarColorBoton = new JButton("Cambiar Color");
        ordenarPuntuacionBoton = new JButton("Ordenar por Puntuación");
        pInferior.add(ordenarPuntuacionBoton);

        ordenarPuntuacionBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ordenarPorPuntuacion();
            }
        });
        boton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
		        dispose();
		        if(usuario.getTipoUsuario().equals("Usuario entidad")) {
		            VentanaPerfilEntidad v = new VentanaPerfilEntidad(usuario);
			        v.setVisible(true);
		        } else {
		            VentanaPerfilUsuario vpu = new VentanaPerfilUsuario(usuario, null);
			        vpu.setVisible(true);
		        }	
			}
		});
        add(panelPrincipal, BorderLayout.CENTER);
        add(pInferior, BorderLayout.SOUTH);
        
        this.setVisible(true);
        
        // Configuración inicial con las valoraciones proporcionados
        modeloInfor.setDatos(val);
        
        tablaInfo.setDefaultRenderer(Integer.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            	Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                JLabel label = (JLabel) component;
                label.setHorizontalAlignment(SwingConstants.CENTER);  // Centra el texto horizontalmente en la celda

                // Cambiar el color de fondo para puntuaciones 1 o 2
                int puntuacion = (int) value;
                if (puntuacion == 1 || puntuacion == 2) {
                    component.setBackground(new Color(255, 204, 204));  // Cambia este color según tus preferencias
                } else {
                    // Restaurar el color de fondo predeterminado para otras puntuaciones
                    component.setBackground(table.getBackground());
                }

                return component;
            }
        });
     // Establecer el fondo predeterminado para todo el modelo
        tablaInfo.setBackground(tablaInfo.getBackground());
        
        tablaInfo.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int filaEnTabla = tablaInfo.rowAtPoint(e.getPoint());
                int colEnTabla = tablaInfo.columnAtPoint(e.getPoint());
                if (colEnTabla == 1) {    
                    int puntuacion = (int) tablaInfo.getValueAt(filaEnTabla, colEnTabla);
                    if(puntuacion >= 3) {
                        tablaInfo.setToolTipText(String.format("Puntuación positiva: APROBADO", puntuacion));
                    } else {
                        tablaInfo.setToolTipText(String.format("Puntuación negativa: SUSPENDIDO", puntuacion));
                    }
                } else {
                    tablaInfo.setToolTipText(null);
                }
            }
        });
       
	}

	private void ordenarPorPuntuacion() {
        modeloInfor.ordenarPorPuntuacion();
    }
	
    private static class MiTableModel extends AbstractTableModel {
        private List<Valoracion> datos;
        private final String[] cabeceras = { "usuario_revisor", "puntuacion", "comentario"};

        public void setDatos(List<Valoracion> datos) {
            this.datos = datos;
            fireTableDataChanged();
        }
        public void ordenarPorPuntuacion() {
            if (datos != null) {
                datos.sort(Comparator.comparingInt(Valoracion::getPuntuacion).reversed());
                fireTableDataChanged();
            }
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
		@Override
        public Class<?> getColumnClass(int columnIndex) {
            return columnIndex == 1 ? Integer.class : Object.class;
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }
    }
    
    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new VentanaTablaInformacion(null).setVisible(true));
    }
	
}
