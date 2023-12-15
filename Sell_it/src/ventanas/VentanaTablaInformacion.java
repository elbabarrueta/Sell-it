package src.ventanas;
import javax.swing.*;

import javax.swing.table.AbstractTableModel;

import src.clases.Evento;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class VentanaTablaInformacion extends JFrame {

	private JTable tablaInfo;
    private MiTableModel modeloInfo;

    public VentanaTablaInformacion(List<Evento> eventos) {
        setTitle("Informacion sobre Eventos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);

        modeloInfo = new MiTableModel();
        tablaInfo = new JTable(modeloInfo);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.add(new JScrollPane(tablaInfo), BorderLayout.CENTER);

        JPanel pInferior = new JPanel();
        JButton boton = new JButton("Actualizar Datos");
        boton.addActionListener(e -> setDatos());
        pInferior.add(boton);

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
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaTablaInformacion(null).setVisible(true));
    }
}