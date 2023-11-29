package ventanas;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class VentanaTablaInformacion extends JFrame {

    private JTable tablaInfo;
    private MiTableModel modeloInfo;
    private JTextField nombreField, descField, fechaField, ubicacionField, nEntradasField, precioField, rutaImgField;

    public VentanaTablaInformacion() {
        setTitle("Informacion sobre Eventos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        modeloInfo = new MiTableModel();
        tablaInfo = new JTable(modeloInfo);

        // Crear campos de entrada
        nombreField = new JTextField();
        descField = new JTextField();
        fechaField = new JTextField();
        ubicacionField = new JTextField();
        nEntradasField = new JTextField();
        precioField = new JTextField();
        rutaImgField = new JTextField();

        // Crear panel para los campos de entrada
        JPanel formPanel = new JPanel(new GridLayout(7, 2));
        formPanel.add(new JLabel("Nombre:"));
        formPanel.add(nombreField);
        formPanel.add(new JLabel("Descripción:"));
        formPanel.add(descField);
        formPanel.add(new JLabel("Fecha:"));
        formPanel.add(fechaField);
        formPanel.add(new JLabel("Ubicación:"));
        formPanel.add(ubicacionField);
        formPanel.add(new JLabel("Número de Entradas:"));
        formPanel.add(nEntradasField);
        formPanel.add(new JLabel("Precio:"));
        formPanel.add(precioField);
        formPanel.add(new JLabel("Ruta de la Imagen:"));
        formPanel.add(rutaImgField);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.add(new JScrollPane(tablaInfo), BorderLayout.CENTER);
        panelPrincipal.add(formPanel, BorderLayout.SOUTH);

        JPanel pInferior = new JPanel();
        JButton boton = new JButton("Actualizar Datos");
        boton.addActionListener(e -> setDatos());
        pInferior.add(boton);

        add(panelPrincipal, BorderLayout.CENTER);
        add(pInferior, BorderLayout.SOUTH);
    }

    public void setDatos() {
        // Simula la obtención de datos reales de tu aplicación (de la base de datos, por ejemplo)
        List<Evento> eventos = obtenerDatosDeEventos();
        modeloInfo.setDatos(eventos);
    }

    private List<Evento> obtenerDatosDeEventos() {
        // Implementa la lógica para obtener tus datos reales de eventos
        // Aquí se devuelve una lista de eventos de prueba
        List<Evento> eventos = new ArrayList<>();
        eventos.add(new Evento("Concierto", "01/01/2023", "19:00", "Lugar A"));
        eventos.add(new Evento("Exposición", "05/02/2023", "15:30", "Lugar B"));
        // Agrega más eventos según sea necesario
        return eventos;
    }

    private class MiTableModel extends AbstractTableModel {

        private List<Evento> datos = new ArrayList<>();
        private String[] columnNames = {"Nombre", "Fecha", "Hora", "Lugar"};

        public void setDatos(List<Evento> datos) {
            this.datos = datos;
            fireTableDataChanged(); // Notifica a la tabla que los datos han cambiado
        }

        @Override
        public int getRowCount() {
            return datos.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public String getColumnName(int columnIndex) {
            return columnNames[columnIndex];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return String.class; // Puedes ajustar el tipo de datos según tus necesidades
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false; // No permitir edición
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Evento evento = datos.get(rowIndex);
            switch (columnIndex) {
                case 0: return evento.getNombre();
                case 1: return evento.getFecha();
                case 2: return evento.getHora();
                case 3: return evento.getLugar();
                default: return "";
            }
        }
    }

    private static class Evento {
        private String nombre;
        private String fecha;
        private String hora;
        private String lugar;

        public Evento(String nombre, String fecha, String hora, String lugar) {
            this.nombre = nombre;
            this.fecha = fecha;
            this.hora = hora;
            this.lugar = lugar;
        }

        public String getNombre() {
            return nombre;
        }

        public String getFecha() {
            return fecha;
        }

        public String getHora() {
            return hora;
        }

        public String getLugar() {
            return lugar;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaTablaInformacion().setVisible(true));
    }
}