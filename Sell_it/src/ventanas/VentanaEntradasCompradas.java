package ventanas;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.*;

public class VentanaEntradasCompradas extends JFrame{

	public VentanaEntradasCompradas(List<String> entradasCompradas) {
		setTitle("Productos comprados");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(300, 200);
		
		JTextArea entradasA = new JTextArea(10, 20);
		entradasA.setEditable(false);
		JScrollPane entradaScoll = new JScrollPane(entradasA);
		
		for(String entrada: entradasCompradas) {
			entradasA.append(entrada + "\n");
		}
		getContentPane().add(entradaScoll, BorderLayout.CENTER);
		setVisible(true);
	}
}
