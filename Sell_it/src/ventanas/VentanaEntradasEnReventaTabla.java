package ventanas;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import BasesDeDatos.BDEventos;
import clases.EntradaReventa;
import java.awt.*;
import java.util.List;

public class VentanaEntradasEnReventaTabla extends JFrame {
    private JTable tablaEntradas;
    private ModeloEntradasReventa modeloTabla;

    public VentanaEntradasEnReventaTabla() {
        setTitle("Entradas en Reventa");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        modeloTabla = new ModeloEntradasReventa();
        tablaEntradas = new JTable(modeloTabla);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.add(new JScrollPane(tablaEntradas), BorderLayout.CENTER);
        
        JButton bVolver = new JButton("Volver");
        panelPrincipal.add(bVolver, BorderLayout.SOUTH);
        // ... (configuración de otros paneles y botones si es necesario)

        add(panelPrincipal, BorderLayout.CENTER);
        
        cargarDatosEntradas();
        this.setVisible(true);
        
        bVolver.addActionListener(e -> {
            // Lógica para volver a la ventana anterior
        });
    }

    private void cargarDatosEntradas() {
        List<EntradaReventa> entradasReventa = BDEventos.obtenerEntradasReventa();
        modeloTabla.setDatos(entradasReventa);
    }

    private class ModeloEntradasReventa extends AbstractTableModel {
        private List<EntradaReventa> datos;
        private final String[] columnNames = {"Código Entrada", "Precio Reventa", "Correo del Vendedor"};

        public void setDatos(List<EntradaReventa> datos) {
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
            EntradaReventa entrada = datos.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return entrada.getCodigoEntrada();
                case 1:
                    return entrada.getPrecioReventa();
                case 2:
                    return entrada.getCorreoUsuario();
                default:
                    return null;
            }
        }
    }

    public static void main(String[] args) {
        new VentanaEntradasEnReventaTabla().setVisible(true);
    }
}
