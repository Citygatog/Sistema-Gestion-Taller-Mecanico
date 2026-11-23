
package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VentanaReparaciones extends JFrame {

    public JTextField txtId;
    public JTextField txtFecha;
    public JTextField txtDescripcion;
    public JTextField txtCosto;
    public JComboBox<String> cmbEstado;
    public JTextField txtObservaciones;
    public JTextField txtVehiculoId;

    public JButton btnRegistrar;
    public JButton btnConsultar;
    public JButton btnListar;
    public JButton btnActualizar;
    public JButton btnEliminar;
    public JButton btnLimpiar;
    public JButton btnRegresar;

    public JTable tablaReparaciones;
    public DefaultTableModel modeloTabla;

    public VentanaReparaciones() {

        setTitle("Gestion de reparaciones");
        setSize(950, 580);
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
        txtFecha = new JTextField();
        txtDescripcion = new JTextField();
        txtCosto = new JTextField();
        cmbEstado = new JComboBox<>(new String[]{
            "Pendiente",
            "En proceso",
            "Finalizada",
            "Cancelada"
        });
        txtObservaciones = new JTextField();
        txtVehiculoId = new JTextField();

        panelFormulario.add(new JLabel("ID:"));
        panelFormulario.add(txtId);

        panelFormulario.add(new JLabel("Fecha de ingreso (AAAA-MM-DD):"));
        panelFormulario.add(txtFecha);

        panelFormulario.add(new JLabel("Descripcion:"));
        panelFormulario.add(txtDescripcion);

        panelFormulario.add(new JLabel("Costo:"));
        panelFormulario.add(txtCosto);

        panelFormulario.add(new JLabel("Estado:"));
        panelFormulario.add(cmbEstado);

        panelFormulario.add(new JLabel("Observaciones:"));
        panelFormulario.add(txtObservaciones);

        panelFormulario.add(new JLabel("ID del vehiculo:"));
        panelFormulario.add(txtVehiculoId);

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

        // PANEL CENTRAL
        JPanel panelCentral = new JPanel(new BorderLayout(10, 10));

        panelCentral.add(panelBotones, BorderLayout.NORTH);
        panelCentral.add(scrollTabla, BorderLayout.CENTER);

        panelPrincipal.add(panelCentral, BorderLayout.CENTER);

        add(panelPrincipal);

        setVisible(true);
    }
}
