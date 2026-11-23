
package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VentanaConsultas extends JFrame {

    public JTextField txtIdVehiculo;
    public JComboBox<String> cmbEstado;

    public JButton btnBuscarVehiculo;
    public JButton btnFiltrarEstado;
    public JButton btnLimpiar;
    public JButton btnRegresar;

    public JTable tablaReparaciones;
    public DefaultTableModel modeloTabla;

    public VentanaConsultas() {

        setTitle("Consultas adicionales");
        setSize(850, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));

        panelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );

        // PANEL DE CONSULTAS
        JPanel panelConsultas = new JPanel(new GridLayout(2, 1, 5, 5));

        // CONSULTA POR VEHICULO
        JPanel panelVehiculo = new JPanel(new FlowLayout(FlowLayout.LEFT));

        panelVehiculo.setBorder(
                BorderFactory.createTitledBorder(
                        "Consultar reparaciones por vehiculo"
                )
        );

        txtIdVehiculo = new JTextField(10);
        btnBuscarVehiculo = new JButton("Buscar");

        panelVehiculo.add(new JLabel("ID del vehiculo:"));
        panelVehiculo.add(txtIdVehiculo);
        panelVehiculo.add(btnBuscarVehiculo);

        // FILTRO POR ESTADO
        JPanel panelEstado = new JPanel(new FlowLayout(FlowLayout.LEFT));

        panelEstado.setBorder(
                BorderFactory.createTitledBorder(
                        "Filtrar reparaciones por estado"
                )
        );

        cmbEstado = new JComboBox<>(new String[]{
            "Pendiente",
            "En proceso",
            "Finalizada",
            "Cancelada"
        });

        btnFiltrarEstado = new JButton("Filtrar");

        panelEstado.add(new JLabel("Estado:"));
        panelEstado.add(cmbEstado);
        panelEstado.add(btnFiltrarEstado);

        panelConsultas.add(panelVehiculo);
        panelConsultas.add(panelEstado);

        panelPrincipal.add(panelConsultas, BorderLayout.NORTH);

        // TABLA DE RESULTADOS
        modeloTabla = new DefaultTableModel(
                new String[]{
                    "ID", "Fecha", "Descripcion",
                    "Costo", "Estado", "Observaciones",
                    "ID Vehiculo"
                }, 0
        );

        tablaReparaciones = new JTable(modeloTabla) {

            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        JScrollPane scrollTabla = new JScrollPane(tablaReparaciones);

        panelPrincipal.add(scrollTabla, BorderLayout.CENTER);

        // BOTONES INFERIORES
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));

        btnLimpiar = new JButton("Limpiar");
        btnRegresar = new JButton("Regresar al menu");

        panelBotones.add(btnLimpiar);
        panelBotones.add(btnRegresar);

        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal);

        setVisible(true);
    }
}
