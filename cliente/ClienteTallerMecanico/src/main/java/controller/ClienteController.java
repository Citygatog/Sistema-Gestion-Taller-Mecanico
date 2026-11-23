
package controller;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import model.Cliente;
import repository.ClienteRepository;
import view.VentanaClientes;

public class ClienteController {

    private final VentanaClientes vista;
    private final ClienteRepository repositorio;

    public ClienteController(VentanaClientes vista) {

        this.vista = vista;
        this.repositorio = new ClienteRepository();

        iniciarEventos();
        listarClientes();
    }

    private void iniciarEventos() {

        vista.btnRegistrar.addActionListener(e -> registrarCliente());

        vista.btnConsultar.addActionListener(e -> consultarCliente());

        vista.btnListar.addActionListener(e -> listarClientes());

        vista.btnActualizar.addActionListener(e -> actualizarCliente());

        vista.btnEliminar.addActionListener(e -> eliminarCliente());

        vista.btnLimpiar.addActionListener(e -> limpiarCampos());
        
        vista.btnRegresar.addActionListener(e -> vista.dispose());
    }

    // REGISTRAR CLIENTE
    private void registrarCliente() {

        // Comprobar que los campos estén completos
        if (vista.txtNombres.getText().trim().isEmpty()
                || vista.txtApellidos.getText().trim().isEmpty()
                || vista.txtDocumento.getText().trim().isEmpty()
                || vista.txtTelefono.getText().trim().isEmpty()
                || vista.txtCorreo.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    vista,
                    "Por favor, complete todos los campos.",
                    "Campos vacios",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            Cliente cliente = obtenerDatosFormulario();

            repositorio.crear(cliente);

            JOptionPane.showMessageDialog(vista,
                    "Cliente registrado correctamente.");

            limpiarCampos();
            listarClientes();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    vista,
                    "No se pudo registrar el cliente. Verifique los datos e intente nuevamente.",
                    "Error al registrar",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // CONSULTAR CLIENTE
    private void consultarCliente() {

        try {

            int id = Integer.parseInt(vista.txtId.getText());

            Cliente cliente = repositorio.consultar(id);

            mostrarDatos(cliente);

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(vista,
                    "Ingrese un ID valido.");

        } catch (Exception e) {

            JOptionPane.showMessageDialog(vista,
                    "No se pudo consultar el cliente:\n" + e.getMessage());
        }
    }

    // LISTAR CLIENTES
    private void listarClientes() {

        try {

            List<Cliente> clientes = repositorio.listar();

            DefaultTableModel modelo = vista.modeloTabla;

            modelo.setRowCount(0);

            for (Cliente cliente : clientes) {

                modelo.addRow(new Object[]{
                    cliente.getId(),
                    cliente.getNombres(),
                    cliente.getApellidos(),
                    cliente.getDocumento(),
                    cliente.getTelefono(),
                    cliente.getCorreo()
                });
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(vista,
                    "No se pudieron listar los clientes:\n" + e.getMessage());
        }
    }

    // ACTUALIZAR CLIENTE
    private void actualizarCliente() {

        try {

            int id = Integer.parseInt(vista.txtId.getText());

            Cliente cliente = obtenerDatosFormulario();

            repositorio.actualizar(id, cliente);

            JOptionPane.showMessageDialog(vista,
                    "Cliente actualizado correctamente.");

            limpiarCampos();
            listarClientes();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(vista,
                    "Ingrese un ID valido.");

        } catch (Exception e) {

            JOptionPane.showMessageDialog(vista,
                    "No se pudo actualizar el cliente:\n" + e.getMessage());
        }
    }

    // ELIMINAR CLIENTE
    private void eliminarCliente() {

        try {

            int id = Integer.parseInt(vista.txtId.getText());

            int opcion = JOptionPane.showConfirmDialog(
                    vista,
                    "¿Esta seguro de eliminar este cliente?",
                    "Confirmar eliminacion",
                    JOptionPane.YES_NO_OPTION
            );

            if (opcion == JOptionPane.YES_OPTION) {

                repositorio.eliminar(id);

                JOptionPane.showMessageDialog(vista,
                        "Cliente eliminado correctamente.");

                limpiarCampos();
                listarClientes();
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(vista,
                    "Ingrese un ID valido.");

        } catch (Exception e) {

            JOptionPane.showMessageDialog(vista,
                    "No se pudo eliminar el cliente:\n" + e.getMessage());
        }
    }

    // OBTENER DATOS DEL FORMULARIO
    private Cliente obtenerDatosFormulario() {

        return new Cliente(
                0,
                vista.txtNombres.getText(),
                vista.txtApellidos.getText(),
                vista.txtDocumento.getText(),
                vista.txtTelefono.getText(),
                vista.txtCorreo.getText()
        );
    }

    // MOSTRAR DATOS EN LOS CAMPOS
    private void mostrarDatos(Cliente cliente) {

        vista.txtId.setText(String.valueOf(cliente.getId()));
        vista.txtNombres.setText(cliente.getNombres());
        vista.txtApellidos.setText(cliente.getApellidos());
        vista.txtDocumento.setText(cliente.getDocumento());
        vista.txtTelefono.setText(cliente.getTelefono());
        vista.txtCorreo.setText(cliente.getCorreo());
    }

    // LIMPIAR CAMPOS
    private void limpiarCampos() {

        vista.txtId.setText("");
        vista.txtNombres.setText("");
        vista.txtApellidos.setText("");
        vista.txtDocumento.setText("");
        vista.txtTelefono.setText("");
        vista.txtCorreo.setText("");
    }
}
