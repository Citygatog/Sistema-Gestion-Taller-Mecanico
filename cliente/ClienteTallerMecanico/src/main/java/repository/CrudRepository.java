
package repository;

import java.util.List;

/**
 * Define las operaciones CRUD que deben implementar
 * los repositorios de cada modelo.
 *
 * @param <T> Tipo de objeto que manejará el repositorio.
 */
public interface CrudRepository<T> {

    // Consultar todos los registros
    List<T> listar() throws Exception;

    // Consultar un registro por su ID
    T consultar(int id) throws Exception;

    // Registrar un nuevo objeto
    T crear(T objeto) throws Exception;

    // Actualizar un registro existente
    T actualizar(int id, T objeto) throws Exception;

    // Eliminar un registro
    void eliminar(int id) throws Exception;
}
