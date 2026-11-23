package repository;

import config.ApiClient;
import java.util.List;
import model.Reparacion;

public class ReparacionRepository implements CrudRepository<Reparacion> {

    private final ApiClient api;

    public ReparacionRepository() {
        api = new ApiClient();
    }

    @Override
    public List<Reparacion> listar() throws Exception {
        return api.obtenerReparaciones();
    }

    @Override
    public Reparacion consultar(int id) throws Exception {
        return api.obtenerReparacion(id);
    }

    @Override
    public Reparacion crear(Reparacion reparacion) throws Exception {
        return api.crearReparacion(reparacion);
    }

    @Override
    public Reparacion actualizar(int id, Reparacion reparacion) throws Exception {
        return api.actualizarReparacion(id, reparacion);
    }

    @Override
    public void eliminar(int id) throws Exception {
        api.eliminarReparacion(id);
    }
    public List<Reparacion> obtenerPorEstado(String estado) throws Exception {
        return api.obtenerReparacionesPorEstado(estado);
    }
}
