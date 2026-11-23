package config;

import java.util.List;
import model.Reparacion;
import java.math.BigDecimal;

import com.fasterxml.jackson.core.type.TypeReference;

import model.Cliente;
import model.Vehiculo;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ApiClient {

    // Dirección de nuestra API FastAPI
    private static final String URL_BASE = "http://127.0.0.1:8000";

    // Cliente encargado de realizar las peticiones HTTP
    private final HttpClient clienteHttp;

    // Objeto encargado de convertir JSON y objetos Java
    private final ObjectMapper conversorJson;

    public ApiClient() {
    clienteHttp = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .build();
    conversorJson = new ObjectMapper();
    // Configurar el manejo de fechas
    conversorJson.registerModule(new JavaTimeModule());
    // Enviar las fechas como texto y no como arreglos
    conversorJson.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    // Realizar una petición GET
    public String get(String ruta) throws Exception {

        HttpRequest solicitud = HttpRequest.newBuilder()
                .uri(URI.create(URL_BASE + ruta))
                .GET()
                .build();

        HttpResponse<String> respuesta = clienteHttp.send(
                solicitud,
                HttpResponse.BodyHandlers.ofString()
        );

        if (respuesta.statusCode() < 200 || respuesta.statusCode() >= 300) {
            throw new RuntimeException(
                    "Error HTTP " + respuesta.statusCode()
                    + ": " + respuesta.body()
            );
        }

        return respuesta.body();
    }
    // Consultar todos los clientes y convertir el JSON en objetos Java
    public List<Cliente> obtenerClientes() throws Exception {
        // Realizar la petición GET
        String respuestaJson = get("/clientes/");
        // Convertir el JSON en una lista de objetos Cliente
        List<Cliente> clientes = conversorJson.readValue(
                respuestaJson,
                new TypeReference<List<Cliente>>() {}
        );
        return clientes;
    }
    
    // Consultar un cliente por su ID
    public Cliente obtenerCliente(int id) throws Exception {

        String respuestaJson = get("/clientes/" + id);

        Cliente cliente = conversorJson.readValue(
                respuestaJson,
                Cliente.class
        );

        return cliente;
    }
    
    // Registrar un nuevo cliente
    public Cliente crearCliente(Cliente cliente) throws Exception {
    // Convertir el objeto Cliente en JSON
    String json = conversorJson.writeValueAsString(cliente);
    HttpRequest solicitud = HttpRequest.newBuilder()
            .uri(URI.create(URL_BASE + "/clientes/"))
            .version(HttpClient.Version.HTTP_1_1)
            .header("Content-Type", "application/json")
            .header("Accept", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(json))
            .build();

    HttpResponse<String> respuesta = clienteHttp.send(
            solicitud,
            HttpResponse.BodyHandlers.ofString()
    );
    if (respuesta.statusCode() < 200 || respuesta.statusCode() >= 300) {
        throw new RuntimeException(
                "Error HTTP " + respuesta.statusCode()
                + ": " + respuesta.body()
        );
    
    }
    return conversorJson.readValue(respuesta.body(), Cliente.class);
    }
    
    // Actualizar los datos de un cliente
    public Cliente actualizarCliente(int id, Cliente cliente) throws Exception {
    // Convertir el objeto Cliente en JSON
    String json = conversorJson.writeValueAsString(cliente);
    // Construir la solicitud PUT
    HttpRequest solicitud = HttpRequest.newBuilder()
            .uri(URI.create(URL_BASE + "/clientes/" + id))
            .header("Content-Type", "application/json")
            .header("Accept", "application/json")
            .PUT(HttpRequest.BodyPublishers.ofString(json))
            .build();
    // Enviar la solicitud
    HttpResponse<String> respuesta = clienteHttp.send(
            solicitud,
            HttpResponse.BodyHandlers.ofString()
    );
    // Comprobar si ocurrió un error
    if (respuesta.statusCode() < 200 || respuesta.statusCode() >= 300) {
        throw new RuntimeException(
                "Error HTTP " + respuesta.statusCode()
                + ": " + respuesta.body()
        );
    }
    // Convertir la respuesta JSON en un objeto Cliente
    return conversorJson.readValue(respuesta.body(), Cliente.class);
    }
    
    // Eliminar un cliente
    public void eliminarCliente(int id) throws Exception {
    // Construir la solicitud DELETE
        HttpRequest solicitud = HttpRequest.newBuilder()
                .uri(URI.create(URL_BASE + "/clientes/" + id))
                .DELETE()
                .build();
    // Enviar la solicitud
        HttpResponse<String> respuesta = clienteHttp.send(
                solicitud,
                HttpResponse.BodyHandlers.ofString()
        );
    // Comprobar si ocurrió un error
        if (respuesta.statusCode() < 200 || respuesta.statusCode() >= 300) {
            throw new RuntimeException(
                    "Error HTTP " + respuesta.statusCode()
                            + ": " + respuesta.body()
            );
        }
    }
    
    // Consultar todos los vehículos
    public List<Vehiculo> obtenerVehiculos() throws Exception {
    // Solicitar los vehículos a la API
        String respuestaJson = get("/vehiculos/");
        // Convertir el JSON en una lista de vehículos
        List<Vehiculo> vehiculos = conversorJson.readValue(
                respuestaJson,
                new TypeReference<List<Vehiculo>>() {}
        );
        return vehiculos;
    }
    
// Consultar un vehículo por su ID
    public Vehiculo obtenerVehiculo(int id) throws Exception {
        // Solicitar el vehículo a la API
        String respuestaJson = get("/vehiculos/" + id);
        // Convertir el JSON en un objeto Vehiculo
        Vehiculo vehiculo = conversorJson.readValue(
                respuestaJson,
                Vehiculo.class
        );
        return vehiculo;
    }
    
    // Registrar un nuevo vehículo
    public Vehiculo crearVehiculo(Vehiculo vehiculo) throws Exception {

        // Convertir el objeto Vehiculo en JSON
        String json = conversorJson.writeValueAsString(vehiculo);

        // Construir la solicitud POST
        HttpRequest solicitud = HttpRequest.newBuilder()
                .uri(URI.create(URL_BASE + "/vehiculos/"))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        // Enviar la solicitud
        HttpResponse<String> respuesta = clienteHttp.send(
                solicitud,
                HttpResponse.BodyHandlers.ofString()
        );

        // Comprobar si ocurrió un error
        if (respuesta.statusCode() < 200 || respuesta.statusCode() >= 300) {
            throw new RuntimeException(
                    "Error HTTP " + respuesta.statusCode()
                    + ": " + respuesta.body()
            );
        }

        // Convertir la respuesta JSON en un objeto Vehiculo
        return conversorJson.readValue(respuesta.body(), Vehiculo.class);
    }
    // Actualizar los datos de un vehículo
    public Vehiculo actualizarVehiculo(int id, Vehiculo vehiculo) throws Exception {

        // Convertir el objeto Vehiculo en JSON
        String json = conversorJson.writeValueAsString(vehiculo);

        // Construir la solicitud PUT
        HttpRequest solicitud = HttpRequest.newBuilder()
                .uri(URI.create(URL_BASE + "/vehiculos/" + id))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        // Enviar la solicitud
        HttpResponse<String> respuesta = clienteHttp.send(
                solicitud,
                HttpResponse.BodyHandlers.ofString()
        );

        // Comprobar si ocurrió un error
        if (respuesta.statusCode() < 200 || respuesta.statusCode() >= 300) {
            throw new RuntimeException(
                    "Error HTTP " + respuesta.statusCode()
                    + ": " + respuesta.body()
            );
        }

        // Convertir la respuesta JSON en un objeto Vehiculo
        return conversorJson.readValue(respuesta.body(), Vehiculo.class);
    }
    // Eliminar un vehículo
    public void eliminarVehiculo(int id) throws Exception {

        // Construir la solicitud DELETE
        HttpRequest solicitud = HttpRequest.newBuilder()
                .uri(URI.create(URL_BASE + "/vehiculos/" + id))
                .DELETE()
                .build();

        // Enviar la solicitud
        HttpResponse<String> respuesta = clienteHttp.send(
                solicitud,
                HttpResponse.BodyHandlers.ofString()
        );

        // Comprobar si ocurrió un error
        if (respuesta.statusCode() < 200 || respuesta.statusCode() >= 300) {
            throw new RuntimeException(
                    "Error HTTP " + respuesta.statusCode()
                    + ": " + respuesta.body()
            );
        }
    }
    public List<Reparacion> obtenerReparaciones() throws Exception {
        String respuesta = get("/reparaciones/");
        return conversorJson.readValue(
                respuesta,
                new TypeReference<List<Reparacion>>() {}
        );
    }
    public Reparacion obtenerReparacion(int id) throws Exception {
        String respuesta = get("/reparaciones/" + id);
        return conversorJson.readValue(
                respuesta,
                Reparacion.class
        );
    }
    public Reparacion crearReparacion(Reparacion reparacion) throws Exception {
        String json = conversorJson.writeValueAsString(reparacion);
        HttpRequest solicitud = HttpRequest.newBuilder()
                .uri(URI.create(URL_BASE + "/reparaciones/"))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json, StandardCharsets.UTF_8))
                .build();
        HttpResponse<String> respuesta = clienteHttp.send(
                solicitud,
                HttpResponse.BodyHandlers.ofString()
        );
        if (respuesta.statusCode() >= 200 && respuesta.statusCode() < 300) {
            return conversorJson.readValue(respuesta.body(), Reparacion.class);
        }
        throw new Exception("Error al registrar reparación. Código HTTP: "
                + respuesta.statusCode() + " - " + respuesta.body());
    }
    public Reparacion actualizarReparacion(int id, Reparacion reparacion) throws Exception {

        String json = conversorJson.writeValueAsString(reparacion);

        HttpRequest solicitud = HttpRequest.newBuilder()
                .uri(URI.create(URL_BASE + "/reparaciones/" + id))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json, StandardCharsets.UTF_8))
                .build();

        HttpResponse<String> respuesta = clienteHttp.send(
                solicitud,
                HttpResponse.BodyHandlers.ofString()
        );

        if (respuesta.statusCode() >= 200 && respuesta.statusCode() < 300) {
            return conversorJson.readValue(respuesta.body(), Reparacion.class);
        }

        throw new Exception("Error al actualizar reparación. Código HTTP: "
                + respuesta.statusCode() + " - " + respuesta.body());
    }
    public void eliminarReparacion(int id) throws Exception {

        HttpRequest solicitud = HttpRequest.newBuilder()
                .uri(URI.create(URL_BASE + "/reparaciones/" + id))
                .DELETE()
                .build();

        HttpResponse<String> respuesta = clienteHttp.send(
                solicitud,
                HttpResponse.BodyHandlers.ofString()
        );

        if (respuesta.statusCode() != 204) {
            throw new Exception("Error al eliminar reparación. Código HTTP: "
                    + respuesta.statusCode() + " - " + respuesta.body());
        }
    }
    
    public List<Reparacion> obtenerReparacionesPorVehiculo(int vehiculoId) throws Exception {
        String respuesta = get("/vehiculos/" + vehiculoId + "/reparaciones");
        return conversorJson.readValue(
                respuesta,
                new TypeReference<List<Reparacion>>() {}
        );
    }
    public List<Reparacion> obtenerReparacionesPorEstado(String estado) throws Exception {
        String respuesta = get("/reparaciones/?estado=" + estado);
        return conversorJson.readValue(
                respuesta,
                new TypeReference<List<Reparacion>>() {}
        );
    }
}
