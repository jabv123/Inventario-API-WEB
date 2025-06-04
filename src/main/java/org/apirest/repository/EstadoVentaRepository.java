package org.apirest.repository;

import org.apirest.modelo.EstadoVenta;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class EstadoVentaRepository {
    private static EstadoVentaRepository instance;
    private final Map<String, EstadoVenta> estados;
    private final AtomicInteger idCounter;

    // Constructor privado para implementar el patrón Singleton
    private EstadoVentaRepository() {
        this.estados = new ConcurrentHashMap<>();
        this.idCounter = new AtomicInteger(1);
        initializeDefaultStates(); // Inicializa algunos estados por defecto al iniciar
    }

    // Método estático para obtener la única instancia de la clase (Singleton)
    public static EstadoVentaRepository getInstance() {
        if (instance == null) {
            instance = new EstadoVentaRepository();
        }
        return instance;
    }

    // Inicializa estados de venta predefinidos
    private void initializeDefaultStates() {
        save(new EstadoVenta("1", "creada"));
        save(new EstadoVenta("2", "pagada"));
        save(new EstadoVenta("3", "enviada"));
        save(new EstadoVenta("4", "cancelada"));
    }

    // Guarda un nuevo estado de venta o actualiza uno existente
    public EstadoVenta save(EstadoVenta estado) {
        // Asigna un nuevo ID si el estado no tiene uno
        if (estado.getId() == null || estado.getId().isEmpty()) {
            estado.setId(String.valueOf(idCounter.getAndIncrement()));
        }
        estados.put(estado.getId(), estado); // Almacena el estado en el mapa
        return estado;
    }

    // Busca un estado de venta por su ID
    public Optional<EstadoVenta> findById(String id) {
        return Optional.ofNullable(estados.get(id)); // Devuelve un Optional para manejar la ausencia
    }

    // Obtiene todos los estados de venta
    public List<EstadoVenta> findAll() {
        return new ArrayList<>(estados.values()); // Devuelve una nueva lista con todos los estados
    }

    // Busca un estado de venta por su nombre (ignorando mayúsculas/minúsculas)
    public Optional<EstadoVenta> findByNombre(String nombreEstado) {
        return estados.values().stream()
                .filter(estado -> estado.getNombreEstado().equalsIgnoreCase(nombreEstado))
                .findFirst(); // Encuentra el primer estado que coincida
    }

    // Elimina un estado de venta por su ID
    public boolean deleteById(String id) {
        return estados.remove(id) != null; // Devuelve true si se eliminó, false si no se encontró
    }

    // Actualiza un estado de venta existente
    public EstadoVenta update(String id, EstadoVenta estadoActualizado) {
        if (estados.containsKey(id)) {
            estadoActualizado.setId(id); // Asegura que el ID del objeto actualizado coincida con el ID de la ruta
            estados.put(id, estadoActualizado); // Reemplaza el estado existente
            return estadoActualizado;
        }
        return null; // Devuelve null si el estado no se encontró para actualizar
    }
}
