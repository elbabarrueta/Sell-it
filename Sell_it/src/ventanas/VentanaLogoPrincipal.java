package ventanas;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VentanaLogoPrincipal extends JFrame{
	
//	public static void main(String[] args) {
//		crearVentanaSell_It();
//	}

	private static void crearVentanaSell_It() {
        JFrame ventana = new JFrame("Sell It");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon icono = new ImageIcon("Sell_it\\src\\imagenes\\sell_it.png");
        JLabel imagen = new JLabel(icono);
//        JPanel pnlImagen = new JPanel();
//        JPanel pnlCarga = new JPanel();
		//JLabel cargando = new JLabel("Cargando Sell It...");
//		public void hilo{
//        for(int i = 0; i<5; i++) {
//		   cont.inc(1);
//        	Thread.sleep(1000);
//        	int s = i;
//        }
//		pnlImagen.add(imagen);
//		pnlCarga.add(cargando);
        ventana.getContentPane().add(imagen, BorderLayout.CENTER);
      //  ventana.add(cargando, BorderLayout.SOUTH);
        ventana.setSize(400, 300);
        ventana.setExtendedState(JFrame.MAXIMIZED_BOTH);
        ventana.setVisible(true);
        
    }
	
	private int valor;
	
	public void inc( int incremento ) {
		int temporal = valor;
		temporal = temporal + incremento;
		valor = temporal;
	}
}
