package repository;

import config.ApiClient;
import java.util.List;
import model.Cliente;

public class ClienteRepository implements CrudRepository<Cliente> {

    private final ApiClient api;

    public ClienteRepository() {
        api = new ApiClient();
    }

    // LISTAR CLIENTES
    @Override
    public List<Cliente> listar() throws Exception {
        return api.obtenerClientes();
    }

    // LOS DEMÁS MÉTODOS LOS IMPLEMENTAREMOS DESPUÉS
    @Override
    public Cliente consultar(int id) throws Exception {
        return api.obtenerCliente(id);
    }

    @Override
    public Cliente crear(Cliente cliente) throws Exception {
        return api.crearCliente(cliente);
    }

    @Override
    public Cliente actualizar(int id, Cliente cliente) throws Exception {
        return api.actualizarCliente(id, cliente);
    }

    @Override
    public void eliminar(int id) throws Exception {
        api.eliminarCliente(id);
    }
}
