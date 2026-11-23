
package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VentanaClientes extends JFrame {

    public JTextField txtId;
    public JTextField txtNombres;
    public JTextField txtApellidos;
    public JTextField txtDocumento;
    public JTextField txtTelefono;
    public JTextField txtCorreo;

    public JButton btnRegistrar;
    public JButton btnConsultar;
    public JButton btnListar;
    public JButton btnActualizar;
    public JButton btnEliminar;
    public JButton btnLimpiar;
    public JButton btnRegresar;

    public JTable tablaClientes;
    public DefaultTableModel modeloTabla;

    public VentanaClientes() {

        setTitle("Gestion de clientes");
        setSize(850, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );

        // FORMULARIO
        JPanel panelFormulario = new JPanel(new GridLayout(6, 2, 10, 8));

        txtId = new JTextField();
        txtNombres = new JTextField();
        txtApellidos = new JTextField();
        txtDocumento = new JTextField();
        txtTelefono = new JTextField();
        txtCorreo = new JTextField();

        panelFormulario.add(new JLabel("ID:"));
        panelFormulario.add(txtId);

        panelFormulario.add(new JLabel("Nombres:"));
        panelFormulario.add(txtNombres);

        panelFormulario.add(new JLabel("Apellidos:"));
        panelFormulario.add(txtApellidos);

        panelFormulario.add(new JLabel("Documento:"));
        panelFormulario.add(txtDocumento);

        panelFormulario.add(new JLabel("Telefono:"));
        panelFormulario.add(txtTelefono);

        panelFormulario.add(new JLabel("Correo:"));
        panelFormulario.add(txtCorreo);

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
                    "ID", "Nombres", "Apellidos",
                    "Documento", "Telefono", "Correo"
                }, 0
        );

        tablaClientes = new JTable(modeloTabla) {
        @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        JScrollPane scrollTabla = new JScrollPane(tablaClientes);

        // PANEL CENTRAL
        JPanel panelCentral = new JPanel(new BorderLayout(10, 10));

        panelCentral.add(panelBotones, BorderLayout.NORTH);
        panelCentral.add(scrollTabla, BorderLayout.CENTER);

        panelPrincipal.add(panelCentral, BorderLayout.CENTER);

        add(panelPrincipal);

        setVisible(true);
    }
}
