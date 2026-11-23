
package controller;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import model.Vehiculo;
import repository.VehiculoRepository;
import view.VentanaVehiculos;

public class VehiculoController {

    private final VentanaVehiculos vista;
    private final VehiculoRepository repositorio;

    public VehiculoController(VentanaVehiculos vista) {

        this.vista = vista;
        this.repositorio = new VehiculoRepository();

        iniciarEventos();
        listarVehiculos();
    }

    private void iniciarEventos() {

        vista.btnRegistrar.addActionListener(e -> registrarVehiculo());

        vista.btnConsultar.addActionListener(e -> consultarVehiculo());

        vista.btnListar.addActionListener(e -> listarVehiculos());

        vista.btnActualizar.addActionListener(e -> actualizarVehiculo());

        vista.btnEliminar.addActionListener(e -> eliminarVehiculo());

        vista.btnLimpiar.addActionListener(e -> limpiarCampos());

        vista.btnRegresar.addActionListener(e -> vista.dispose());
    }

    // REGISTRAR VEHICULO
    private void registrarVehiculo() {

        if (!camposCompletos()) {
            return;
        }

        try {

            Vehiculo vehiculo = obtenerDatosFormulario();

            repositorio.crear(vehiculo);

            JOptionPane.showMessageDialog(vista,
                    "Vehiculo registrado correctamente.");

            limpiarCampos();
            listarVehiculos();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    vista,
                    "No se pudo registrar el vehiculo. Verifique los datos.",
                    "Error al registrar",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // CONSULTAR VEHICULO
    private void consultarVehiculo() {

        try {

            int id = obtenerId();

            if (id <= 0) {
                return;
            }

            Vehiculo vehiculo = repositorio.consultar(id);

            mostrarDatos(vehiculo);

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(vista,
                    "Ingrese un ID numerico valido.");

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    vista,
                    "No se pudo consultar el vehiculo.",
                    "Error de consulta",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // LISTAR VEHICULOS
    private void listarVehiculos() {

        try {

            List<Vehiculo> vehiculos = repositorio.listar();

            DefaultTableModel modelo = vista.modeloTabla;

            modelo.setRowCount(0);

            for (Vehiculo vehiculo : vehiculos) {

                modelo.addRow(new Object[]{
                    vehiculo.getId(),
                    vehiculo.getPlaca(),
                    vehiculo.getMarca(),
                    vehiculo.getModelo(),
                    vehiculo.getAnio(),
                    vehiculo.getColor(),
                    vehiculo.getClienteId()
                });
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    vista,
                    "No se pudieron listar los vehiculos.",
                    "Error de consulta",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ACTUALIZAR VEHICULO
    private void actualizarVehiculo() {

        if (!camposCompletos()) {
            return;
        }

        try {

            int id = obtenerId();

            if (id <= 0) {
                return;
            }

            Vehiculo vehiculo = obtenerDatosFormulario();

            repositorio.actualizar(id, vehiculo);

            JOptionPane.showMessageDialog(vista,
                    "Vehiculo actualizado correctamente.");

            limpiarCampos();
            listarVehiculos();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(vista,
                    "Verifique que el ID y el año sean numericos.");

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    vista,
                    "No se pudo actualizar el vehiculo. Verifique los datos.",
                    "Error al actualizar",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ELIMINAR VEHICULO
    private void eliminarVehiculo() {

        try {

            int id = obtenerId();

            if (id <= 0) {
                return;
            }

            int opcion = JOptionPane.showConfirmDialog(
                    vista,
                    "¿Esta seguro de eliminar este vehiculo?",
                    "Confirmar eliminacion",
                    JOptionPane.YES_NO_OPTION
            );

            if (opcion == JOptionPane.YES_OPTION) {

                repositorio.eliminar(id);

                JOptionPane.showMessageDialog(vista,
                        "Vehiculo eliminado correctamente.");

                limpiarCampos();
                listarVehiculos();
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(vista,
                    "Ingrese un ID numerico valido.");

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    vista,
                    "No se pudo eliminar el vehiculo. Compruebe si tiene reparaciones asociadas.",
                    "Error al eliminar",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // OBTENER DATOS DEL FORMULARIO
    private Vehiculo obtenerDatosFormulario() {

        return new Vehiculo(
                0,
                vista.txtPlaca.getText().trim(),
                vista.txtMarca.getText().trim(),
                vista.txtModelo.getText().trim(),
                Integer.parseInt(vista.txtAnio.getText().trim()),
                vista.txtColor.getText().trim(),
                Integer.parseInt(vista.txtClienteId.getText().trim())
        );
    }

    // MOSTRAR DATOS EN LOS CAMPOS
    private void mostrarDatos(Vehiculo vehiculo) {

        vista.txtId.setText(String.valueOf(vehiculo.getId()));
        vista.txtPlaca.setText(vehiculo.getPlaca());
        vista.txtMarca.setText(vehiculo.getMarca());
        vista.txtModelo.setText(vehiculo.getModelo());
        vista.txtAnio.setText(String.valueOf(vehiculo.getAnio()));
        vista.txtColor.setText(vehiculo.getColor());
        vista.txtClienteId.setText(String.valueOf(vehiculo.getClienteId()));
    }

    // OBTENER ID
    private int obtenerId() {

        String textoId = vista.txtId.getText().trim();

        if (textoId.isEmpty()) {
            throw new NumberFormatException();
        }

        return Integer.parseInt(textoId);
    }

    // VALIDAR CAMPOS
    private boolean camposCompletos() {

        if (vista.txtPlaca.getText().trim().isEmpty()
                || vista.txtMarca.getText().trim().isEmpty()
                || vista.txtModelo.getText().trim().isEmpty()
                || vista.txtAnio.getText().trim().isEmpty()
                || vista.txtColor.getText().trim().isEmpty()
                || vista.txtClienteId.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    vista,
                    "Por favor, complete todos los campos.",
                    "Campos vacios",
                    JOptionPane.WARNING_MESSAGE
            );

            return false;
        }

        return true;
    }

    // LIMPIAR CAMPOS
    private void limpiarCampos() {

        vista.txtId.setText("");
        vista.txtPlaca.setText("");
        vista.txtMarca.setText("");
        vista.txtModelo.setText("");
        vista.txtAnio.setText("");
        vista.txtColor.setText("");
        vista.txtClienteId.setText("");
    }
}
