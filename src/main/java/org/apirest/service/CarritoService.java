package org.apirest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apirest.modelo.Carrito;
import org.apirest.modelo.ItemCarrito;
import org.apirest.repository.CarritoRepo;
import org.apirest.repository.ItemCarritoRepo;

public class CarritoService {

    private final CarritoRepo carritoRepo;
    private final ItemCarritoRepo itemCarritoRepo;

    public CarritoService(CarritoRepo carritoRepo, ItemCarritoRepo itemCarritoRepo) {
        this.carritoRepo = carritoRepo;
        this.itemCarritoRepo = itemCarritoRepo;
    }

    // Obtener todos los carritos
    public List<Carrito> getAllCarritos() {
        return carritoRepo.getAll();
    }

    // Obtener carrito por id
    public Carrito getCarritoById(int id) {
        return carritoRepo.getById(id).orElse(null);
    }

    // Obtener carrito por idCliente
    public Carrito getCarritoByIdCliente(int idCliente) {
        return carritoRepo.getByIdCliente(idCliente).orElse(null);
    }

    //TODO: Considerar el carrito unico por cliente o mas de uno por cliente.

    // crear carrito
    public Carrito crearCarrito(Carrito carrito) {
        if (carrito.getIdCliente() == 0) {
            throw new IllegalArgumentException("El idCliente no puede ser 0");
        }

        // Inicar el carrito con una lista vacia
        List<ItemCarrito> items = carrito.getItems();
        if (items == null) {
            items = new ArrayList<>();
        }
        carrito.setItems(items);
        return carritoRepo.add(carrito);
    }

    // actualizar carrito
    public Carrito actualizarCarrito(int id, Carrito carritoActualizado) {
        carritoActualizado.setId(id);
        return carritoRepo.update(carritoActualizado);
    }

    // eliminar carrito
    public boolean eliminarCarrito(int id) {
        return carritoRepo.delete(id);
    }

    // eliminar carrito por idCliente
    public boolean eliminarCarritoPorIdCliente(int idCliente) {
        return carritoRepo.deleteByIdCliente(idCliente);
    }

    //Agregar item al carrito
    public Optional<Carrito> agregarItemAlCarrito(int idCarrito, ItemCarrito item) {
        Optional<Carrito> carritoOpt = carritoRepo.getById(idCarrito);
        if (carritoOpt.isPresent()) {
            Carrito carrito = carritoOpt.get();
            List<ItemCarrito> items = carrito.getItems();

            // Asignar el idCarrito al item antes de procesarlo
            item.setIdCarrito(idCarrito);

            // Verificar si el item ya existe en el carrito
            Optional<ItemCarrito> itemExistenteOpt = items.stream()
                    .filter(i -> i.getIdProducto() == item.getIdProducto())
                    .findFirst();

            if (itemExistenteOpt.isPresent()) {
                // Si existe, actualizar la cantidad
                ItemCarrito itemExistente = itemExistenteOpt.get();
                int cantidadAAgregar = item.getCantidad();
                if (cantidadAAgregar <= 0) { // Si la cantidad proporcionada es 0 o negativa, se asume 1 por defecto.
                    cantidadAAgregar = 1;
                }
                itemExistente.setCantidad(itemExistente.getCantidad() + cantidadAAgregar);
            } else {
                // Si no existe, agregarlo a la lista
                ItemCarrito nuevoItem = itemCarritoRepo.crearItem(item);
                nuevoItem.setCantidad(1); // Asignar cantidad inicial
                items.add(nuevoItem);
            }
            carrito.setItems(items);
            return Optional.of(carritoRepo.update(carrito));
        }
        return Optional.empty();
    }

    //Actualizar cantidad de un item del carrito
    public Optional<ItemCarrito> actualizarCantidadItem(int idCarrito, int idItem, int nuevaCantidad) {
        Optional<Carrito> carritoOpt = carritoRepo.getById(idCarrito);
        if (carritoOpt.isPresent()) {
            Carrito carrito = carritoOpt.get();
            List<ItemCarrito> items = carrito.getItems();
            Optional<ItemCarrito> itemExistenteOpt = items.stream()
                    .filter(i -> i.getId() == idItem)
                    .findFirst();

            if (itemExistenteOpt.isPresent()) {
                ItemCarrito itemExistente = itemExistenteOpt.get();
                itemExistente.setCantidad(itemExistente.getCantidad() + nuevaCantidad);
                if (itemExistente.getCantidad() <= 0) {
                    items.remove(itemExistente); // Eliminar el item si la cantidad es 0 o negativa
                    throw new IllegalArgumentException("La cantidad no puede ser menor o igual a 0");
                }
                carrito.setItems(items);
                carritoRepo.update(carrito);
                return Optional.of(itemExistente);
            } else {
                return Optional.empty(); // Item no encontrado en el carrito
            }
        }
        return Optional.empty(); // Carrito no encontrado
    }
    
    //Eliminar item del carrito
    public Optional<Carrito> eliminarItemDelCarrito(int idCarrito, ItemCarrito item) {
        Optional<Carrito> carrito = carritoRepo.getById(idCarrito);
        if (carrito.isPresent()) {
            List<ItemCarrito> items = carrito.get().getItems();
            items.removeIf(i -> i.getId() == item.getId());
            carrito.get().setItems(items);
            return Optional.of(carritoRepo.update(carrito.get()));
        }
        return Optional.empty();
    }

    // Obtener total del carrito
    public double obtenerTotalDelCarrito(int idCarrito) {
        Optional<Carrito> carrito = carritoRepo.getById(idCarrito);
        if (carrito.isPresent()) {
            double total = carrito.get().getItems().stream()
                    .mapToDouble(item -> item.getPrecioUnitario() * item.getCantidad())
                    .sum();
            return total;
        }
        return 0.0;
    }

}
