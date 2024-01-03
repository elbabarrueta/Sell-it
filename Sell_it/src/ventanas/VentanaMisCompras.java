package ventanas;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

import BasesDeDatos.BaseDeDatos;
import clases.Evento;
import clases.Usuario;
import clases.Entrada;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class VentanaMisCompras extends JFrame {

    private JTable tablaInfo;
    private EntradaTableModel modeloInfo;
    private List<String> entradasCompradas;
    private Usuario usuario;

    public VentanaMisCompras(Usuario usuario) {
        setTitle("Mis Compras");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);
        this.usuario = usuario;

        modeloInfo = new EntradaTableModel();
        tablaInfo = new JTable(modeloInfo);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.add(new JScrollPane(tablaInfo), BorderLayout.CENTER);
        
        JButton bMiPerfil = new JButton("Perfil");
        panelPrincipal.add(bMiPerfil, BorderLayout.SOUTH);
        // ... (configuración de otros paneles y botones si es necesario)

        add(panelPrincipal, BorderLayout.CENTER);
        
        // Cargar las entradas compradas por el usuario
        cargarEntradasCompradas();
        this.setVisible(true);
        
        bMiPerfil.addActionListener(new ActionListener() {
    		
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			dispose();
//    			VentanaMisCompras.this.dispose();
//    			VentanaPerfilUsuario vPerfilUsuario = new VentanaPerfilUsuario(usuario, entradasCompradas);
//    			vPerfilEntidad.setVisible(true);
    		}
    	});
    }

    private void cargarEntradasCompradas() {
        List<Entrada> entradasCompradas = BaseDeDatos.obtenerEntradasCompradas(usuario);
        modeloInfo.setDatos(entradasCompradas);
    }


    private class EntradaTableModel extends AbstractTableModel {
        private List<Entrada> datos;
        
        private final String[] columnNames = {"Código", "Evento", "Propietario", "Precio"};

        public void setDatos(List<Entrada> datos) {
            this.datos = datos;
            fireTableDataChanged();
        }

        @Override
        public int getRowCount() {
            return datos != null ? datos.size() : 0;
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            if (datos == null || rowIndex >= datos.size()) {
                return null;
            }
            Entrada entrada = datos.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return entrada.getCod();
                case 1:
                    return entrada.getEventoAsociado().getNombre();
                case 2:
                    return entrada.getPropietario().getNombreUsuario();
                case 3:
                    return entrada.getPrecio();
                default:
                    return null;
            }
        }
        
        //Esto es por si necesitamos borrar una fila
        public void removeRow(int rowIndex) {
            if (datos != null && rowIndex >= 0 && rowIndex < datos.size()) {
                datos.remove(rowIndex);
                fireTableRowsDeleted(rowIndex, rowIndex);
            }
        }
    }
    
    public static void main(String[] args) {
        Usuario usuario = new Usuario();
        new VentanaMisCompras(usuario).setVisible(true);
    }
}

