
package controller;

import config.ApiClient;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import model.Reparacion;
import view.VentanaConsultas;

public class ConsultasController {

    private final VentanaConsultas vista;
    private final ApiClient api;

    public ConsultasController(VentanaConsultas vista) {

        this.vista = vista;
        this.api = new ApiClient();

        iniciarEventos();
    }

    private void iniciarEventos() {

        vista.btnBuscarVehiculo.addActionListener(e -> buscarPorVehiculo());

        vista.btnFiltrarEstado.addActionListener(e -> filtrarPorEstado());

        vista.btnLimpiar.addActionListener(e -> limpiarTabla());

        vista.btnRegresar.addActionListener(e -> vista.dispose());
    }

    // CONSULTAR REPARACIONES POR VEHICULO
    private void buscarPorVehiculo() {

        try {

            String textoId = vista.txtIdVehiculo.getText().trim();

            if (textoId.isEmpty()) {

                JOptionPane.showMessageDialog(
                        vista,
                        "Ingrese el ID del vehiculo.",
                        "Campo vacio",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            int id = Integer.parseInt(textoId);

            if (id <= 0) {

                JOptionPane.showMessageDialog(
                        vista,
                        "El ID debe ser mayor que cero."
                );

                return;
            }

            List<Reparacion> reparaciones =
                    api.obtenerReparacionesPorVehiculo(id);

            mostrarReparaciones(reparaciones);

            if (reparaciones.isEmpty()) {

                JOptionPane.showMessageDialog(
                        vista,
                        "Este vehiculo no tiene reparaciones registradas."
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    vista,
                    "Ingrese un ID numerico valido."
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    vista,
                    "No se pudieron consultar las reparaciones.",
                    "Error de consulta",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // FILTRAR REPARACIONES POR ESTADO
    private void filtrarPorEstado() {

        try {

            String estado = vista.cmbEstado.getSelectedItem().toString();

            List<Reparacion> reparaciones =
                    api.obtenerReparacionesPorEstado(estado);

            mostrarReparaciones(reparaciones);

            if (reparaciones.isEmpty()) {

                JOptionPane.showMessageDialog(
                        vista,
                        "No hay reparaciones con el estado seleccionado."
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    vista,
                    "No se pudieron filtrar las reparaciones.",
                    "Error de consulta",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // MOSTRAR RESULTADOS EN LA TABLA
    private void mostrarReparaciones(List<Reparacion> reparaciones) {

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
    }

    // LIMPIAR RESULTADOS
    private void limpiarTabla() {

        vista.modeloTabla.setRowCount(0);
        vista.txtIdVehiculo.setText("");
        vista.cmbEstado.setSelectedIndex(0);
    }
}
