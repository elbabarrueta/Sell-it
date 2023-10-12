package ventanas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaPerfilEntidad {
	
	public VentanaPerfilEntidad() {
        JFrame frame = new JFrame("Mi Perfil");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Parte superior: nombre, correo y botones de información
        JPanel topPanel = new JPanel(new FlowLayout());
        JLabel nameLabel = new JLabel("Nombre:");
        JTextField nameField = new JTextField(20);
        JLabel emailLabel = new JLabel("Correo:");
        JTextField emailField = new JTextField(20);
        JButton infoButton1 = new JButton("En venta");
        JButton infoButton2 = new JButton("Valoraciones");
        JButton infoButton3 = new JButton("+ Informacion");
        topPanel.add(nameLabel);
        topPanel.add(nameField);
        topPanel.add(emailLabel);
        topPanel.add(emailField);
        topPanel.add(infoButton1);
        topPanel.add(infoButton2);
        topPanel.add(infoButton3);

        // Parte central: descripción del usuario
        JTextArea descriptionArea = new JTextArea(10, 40);
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);

        // Parte inferior: más botones
        JPanel bottomPanel = new JPanel();
        JButton button1 = new JButton("Botón 1");
        JButton button2 = new JButton("Botón 2");
        JButton button3 = new JButton("Botón 3");
        bottomPanel.add(button1);
        bottomPanel.add(button2);
        bottomPanel.add(button3);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(descriptionScrollPane, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Agregar acción al botón de información 1
        infoButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Información 1");
            }
        });

        frame.pack();
        frame.setVisible(true);
	}
    public static void main(String[] args) {
    	VentanaPerfilEntidad vEntidad = new VentanaPerfilEntidad();
    }
}

