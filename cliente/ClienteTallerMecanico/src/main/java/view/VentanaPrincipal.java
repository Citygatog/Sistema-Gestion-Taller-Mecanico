
package view;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {

    public JButton btnClientes;
    public JButton btnVehiculos;
    public JButton btnReparaciones;
    public JButton btnConsultas;
    public JButton btnSalir;

    public VentanaPrincipal() {

        setTitle("Sistema de gestion de taller mecanico");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout(10, 10));

        // Titulo
        JLabel lblTitulo = new JLabel(
                "SISTEMA DE GESTION DE TALLER MECANICO",
                SwingConstants.CENTER
        );

        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(5, 1, 10, 10));

        btnClientes = new JButton("Gestionar clientes");
        btnVehiculos = new JButton("Gestionar vehiculos");
        btnReparaciones = new JButton("Gestionar reparaciones");
        btnConsultas = new JButton("Consultas adicionales");
        btnSalir = new JButton("Salir");

        panelBotones.add(btnClientes);
        panelBotones.add(btnVehiculos);
        panelBotones.add(btnReparaciones);
        panelBotones.add(btnConsultas);
        panelBotones.add(btnSalir);

        panelPrincipal.add(panelBotones, BorderLayout.CENTER);

        add(panelPrincipal);

        setVisible(true);
    }
}
