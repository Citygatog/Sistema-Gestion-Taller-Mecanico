
package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VentanaVehiculos extends JFrame {

    public JTextField txtId;
    public JTextField txtPlaca;
    public JTextField txtMarca;
    public JTextField txtModelo;
    public JTextField txtAnio;
    public JTextField txtColor;
    public JTextField txtClienteId;

    public JButton btnRegistrar;
    public JButton btnConsultar;
    public JButton btnListar;
    public JButton btnActualizar;
    public JButton btnEliminar;
    public JButton btnLimpiar;
    public JButton btnRegresar;

    public JTable tablaVehiculos;
    public DefaultTableModel modeloTabla;

    public VentanaVehiculos() {

        setTitle("Gestion de vehiculos");
        setSize(900, 570);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));

        panelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );

        // FORMULARIO
        JPanel panelFormulario = new JPanel(new GridLayout(7, 2, 10, 8));

        txtId = new JTextField();
        txtPlaca = new JTextField();
        txtMarca = new JTextField();
        txtModelo = new JTextField();
        txtAnio = new JTextField();
        txtColor = new JTextField();
        txtClienteId = new JTextField();

        panelFormulario.add(new JLabel("ID:"));
        panelFormulario.add(txtId);

        panelFormulario.add(new JLabel("Placa:"));
        panelFormulario.add(txtPlaca);

        panelFormulario.add(new JLabel("Marca:"));
        panelFormulario.add(txtMarca);

        panelFormulario.add(new JLabel("Modelo:"));
        panelFormulario.add(txtModelo);

        panelFormulario.add(new JLabel("Año:"));
        panelFormulario.add(txtAnio);

        panelFormulario.add(new JLabel("Color:"));
        panelFormulario.add(txtColor);

        panelFormulario.add(new JLabel("ID del cliente:"));
        panelFormulario.add(txtClienteId);

        panelPrincipal.add(panelFormulario, BorderLayout.NORTH);

        // BOTONES
        JPanel panelBotones = new JPanel(new GridLayout(2, 4, 8, 8));

        btnRegistrar = new JButton("Registrar");
        btnConsultar = new JButton("Consultar");
        btnListar = new JButton("Listar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");
        btnRegresar = new JButton("Regresar al menu");

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnConsultar);
        panelBotones.add(btnListar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnRegresar);

        // TABLA
        modeloTabla = new DefaultTableModel(
                new String[]{
                    "ID", "Placa", "Marca", "Modelo",
                    "Año", "Color", "ID Cliente"
                }, 0
        );

        tablaVehiculos = new JTable(modeloTabla) {

            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        JScrollPane scrollTabla = new JScrollPane(tablaVehiculos);

        // PANEL CENTRAL
        JPanel panelCentral = new JPanel(new BorderLayout(10, 10));

        panelCentral.add(panelBotones, BorderLayout.NORTH);
        panelCentral.add(scrollTabla, BorderLayout.CENTER);

        panelPrincipal.add(panelCentral, BorderLayout.CENTER);

        add(panelPrincipal);

        setVisible(true);
    }
}
