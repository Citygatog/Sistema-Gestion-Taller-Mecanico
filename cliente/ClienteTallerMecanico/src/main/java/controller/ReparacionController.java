
package controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import model.Reparacion;
import repository.ReparacionRepository;
import view.VentanaReparaciones;

public class ReparacionController {

    private final VentanaReparaciones vista;
    private final ReparacionRepository repositorio;

    public ReparacionController(VentanaReparaciones vista) {

        this.vista = vista;
        this.repositorio = new ReparacionRepository();

        iniciarEventos();
        listarReparaciones();
    }

    private void iniciarEventos() {

        vista.btnRegistrar.addActionListener(e -> registrarReparacion());

        vista.btnConsultar.addActionListener(e -> consultarReparacion());

        vista.btnListar.addActionListener(e -> listarReparaciones());

        vista.btnActualizar.addActionListener(e -> actualizarReparacion());

        vista.btnEliminar.addActionListener(e -> eliminarReparacion());

        vista.btnLimpiar.addActionListener(e -> limpiarCampos());

        vista.btnRegresar.addActionListener(e -> vista.dispose());
    }

    // REGISTRAR REPARACION
    private void registrarReparacion() {

        if (!camposCompletos()) {
            return;
        }

        try {

            Reparacion reparacion = obtenerDatosFormulario();

            repositorio.crear(reparacion);

            JOptionPane.showMessageDialog(vista,
                    "Reparacion registrada correctamente.");

            limpiarCampos();
            listarReparaciones();

        } catch (DateTimeParseException e) {

            JOptionPane.showMessageDialog(vista,
                    "La fecha debe tener el formato AAAA-MM-DD.");

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(vista,
                    "Verifique que el costo y el ID del vehiculo sean numericos.");

        } catch (IllegalArgumentException e) {

            JOptionPane.showMessageDialog(vista,
                    e.getMessage());

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    vista,
                    "No se pudo registrar la reparacion. Verifique los datos y que el vehiculo exista.",
                    "Error al registrar",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // CONSULTAR REPARACION
    private void consultarReparacion() {

        try {

            int id = obtenerId();

            if (id <= 0) {
                return;
            }

            Reparacion reparacion = repositorio.consultar(id);

            mostrarDatos(reparacion);

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(vista,
                    "Ingrese un ID numerico valido.");

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    vista,
                    "No se pudo consultar la reparacion.",
                    "Error de consulta",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // LISTAR REPARACIONES
    private void listarReparaciones() {

        try {

            List<Reparacion> reparaciones = repositorio.listar();

            DefaultTableModel modelo = vista.modeloTabla;

            modelo.setRowCount(0);

            for (Reparacion reparacion : reparaciones) {

                modelo.addRow(new Object[]{
                    reparacion.getId(),
                    reparacion.getFechaIngreso(),
                    reparacion.getDescripcion(),
                    reparacion.getCosto(),
                    reparacion.getEstado(),
                    reparacion.getObservaciones(),
                    reparacion.getVehiculoId()
                });
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    vista,
                    "No se pudieron listar las reparaciones.",
                    "Error de consulta",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ACTUALIZAR REPARACION
    private void actualizarReparacion() {

        if (!camposCompletos()) {
            return;
        }

        try {

            int id = obtenerId();

            if (id <= 0) {
                return;
            }

            Reparacion reparacion = obtenerDatosFormulario();

            repositorio.actualizar(id, reparacion);

            JOptionPane.showMessageDialog(vista,
                    "Reparacion actualizada correctamente.");

            limpiarCampos();
            listarReparaciones();

        } catch (DateTimeParseException e) {

            JOptionPane.showMessageDialog(vista,
                    "La fecha debe tener el formato AAAA-MM-DD.");

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(vista,
                    "Verifique que el ID, el costo y el ID del vehiculo sean numericos.");

        } catch (IllegalArgumentException e) {

            JOptionPane.showMessageDialog(vista,
                    e.getMessage());

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    vista,
                    "No se pudo actualizar la reparacion. Verifique los datos.",
                    "Error al actualizar",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ELIMINAR REPARACION
    private void eliminarReparacion() {

        try {

            int id = obtenerId();

            if (id <= 0) {
                return;
            }

            int opcion = JOptionPane.showConfirmDialog(
                    vista,
                    "¿Esta seguro de eliminar esta reparacion?",
                    "Confirmar eliminacion",
                    JOptionPane.YES_NO_OPTION
            );

            if (opcion == JOptionPane.YES_OPTION) {

                repositorio.eliminar(id);

                JOptionPane.showMessageDialog(vista,
                        "Reparacion eliminada correctamente.");

                limpiarCampos();
                listarReparaciones();
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(vista,
                    "Ingrese un ID numerico valido.");

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    vista,
                    "No se pudo eliminar la reparacion.",
                    "Error al eliminar",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // OBTENER DATOS DEL FORMULARIO
    private Reparacion obtenerDatosFormulario() {

        LocalDate fecha = LocalDate.parse(
                vista.txtFecha.getText().trim()
        );

        BigDecimal costo = new BigDecimal(
                vista.txtCosto.getText().trim()
        );

        int vehiculoId = Integer.parseInt(
                vista.txtVehiculoId.getText().trim()
        );

        if (costo.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                    "El costo no puede ser negativo."
            );
        }

        if (vehiculoId <= 0) {
            throw new IllegalArgumentException(
                    "El ID del vehiculo debe ser mayor que cero."
            );
        }

        return new Reparacion(
                0,
                fecha,
                vista.txtDescripcion.getText().trim(),
                costo,
                vista.cmbEstado.getSelectedItem().toString(),
                vista.txtObservaciones.getText().trim(),
                vehiculoId
        );
    }

    // MOSTRAR DATOS EN LOS CAMPOS
    private void mostrarDatos(Reparacion reparacion) {

        vista.txtId.setText(String.valueOf(reparacion.getId()));

        vista.txtFecha.setText(
                reparacion.getFechaIngreso().toString()
        );

        vista.txtDescripcion.setText(reparacion.getDescripcion());

        vista.txtCosto.setText(reparacion.getCosto().toString());

        vista.cmbEstado.setSelectedItem(reparacion.getEstado());

        vista.txtObservaciones.setText(reparacion.getObservaciones());

        vista.txtVehiculoId.setText(
                String.valueOf(reparacion.getVehiculoId())
        );
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

        if (vista.txtFecha.getText().trim().isEmpty()
                || vista.txtDescripcion.getText().trim().isEmpty()
                || vista.txtCosto.getText().trim().isEmpty()
                || vista.txtObservaciones.getText().trim().isEmpty()
                || vista.txtVehiculoId.getText().trim().isEmpty()) {

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
        vista.txtFecha.setText("");
        vista.txtDescripcion.setText("");
        vista.txtCosto.setText("");
        vista.cmbEstado.setSelectedIndex(0);
        vista.txtObservaciones.setText("");
        vista.txtVehiculoId.setText("");
    }
}
