package repository;

import config.ApiClient;
import java.util.List;
import model.Vehiculo;
import model.Reparacion;

public class VehiculoRepository implements CrudRepository<Vehiculo> {

    private final ApiClient api;

    public VehiculoRepository() {
        api = new ApiClient();
    }

    @Override
    public List<Vehiculo> listar() throws Exception {
        return api.obtenerVehiculos();
    }

    @Override
    public Vehiculo consultar(int id) throws Exception {
        return api.obtenerVehiculo(id);
    }

    @Override
    public Vehiculo crear(Vehiculo vehiculo) throws Exception {
        return api.crearVehiculo(vehiculo);
    }

    @Override
    public Vehiculo actualizar(int id, Vehiculo vehiculo) throws Exception {
        return api.actualizarVehiculo(id, vehiculo);
    }

    @Override
    public void eliminar(int id) throws Exception {
        api.eliminarVehiculo(id);
    }
    
    public List<Reparacion> obtenerReparaciones(int vehiculoId) throws Exception {
        return api.obtenerReparacionesPorVehiculo(vehiculoId);
    }
}
