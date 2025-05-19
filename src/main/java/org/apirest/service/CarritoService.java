package org.apirest.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.apirest.modelo.Carrito;
import org.apirest.modelo.ItemCarrito;
import org.apirest.repository.CarritoRepo;

public class CarritoService {

    private final CarritoRepo carritoRepo;
    public CarritoService(CarritoRepo carritoRepo) {
        this.carritoRepo = carritoRepo;
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

    // crear carrito
    public Carrito crearCarrito(Carrito carrito) {
        carrito.setFechaCreacion(LocalDate.now());
        carrito.setFechaModificacion(LocalDate.now());
        return carritoRepo.add(carrito);
    }

    // actualizar carrito
    public Carrito actualizarCarrito(int id, Carrito carrito) {
        carrito.setFechaModificacion(LocalDate.now());
        return carritoRepo.update(id, carrito);
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
        Optional<Carrito> carrito = carritoRepo.getById(idCarrito);
        if (carrito.isPresent()) {
            List<ItemCarrito> items = carrito.get().getItems();
            items.add(item);
            carrito.get().setItems(items);
            return Optional.of(carritoRepo.update(idCarrito, carrito.get()));
        }
        return Optional.empty();
    }

    //Eliminar item del carrito
    public Optional<Carrito> eliminarItemDelCarrito(int idCarrito, ItemCarrito item) {
        Optional<Carrito> carrito = carritoRepo.getById(idCarrito);
        if (carrito.isPresent()) {
            List<ItemCarrito> items = carrito.get().getItems();
            items.remove(item);
            carrito.get().setItems(items);
            return Optional.of(carritoRepo.update(idCarrito, carrito.get()));
        }
        return Optional.empty();
    }

    //Actualizar cantidad de un item del carrito
    public Optional<Carrito> actualizarCantidadItemDelCarrito(int idCarrito, ItemCarrito item, int nuevaCantidad) {
        Optional<Carrito> carrito = carritoRepo.getById(idCarrito);
        if (carrito.isPresent()) {
            List<ItemCarrito> items = carrito.get().getItems();
            for (ItemCarrito i : items) {
                if (i.equals(item)) {
                    i.setCantidad(nuevaCantidad);
                    break;
                }
            }
            carrito.get().setItems(items);
            return Optional.of(carritoRepo.update(idCarrito, carrito.get()));
        }
        return Optional.empty();
    }

    // Obtener total del carrito
    public Optional<Double> obtenerTotalDelCarrito(int idCarrito) {
        Optional<Carrito> carrito = carritoRepo.getById(idCarrito);
        if (carrito.isPresent()) {
            double total = carrito.get().getItems().stream()
                    .mapToDouble(item -> item.getPrecioUnitario() * item.getCantidad())
                    .sum();
            return Optional.of(total);
        }
        return Optional.empty();
    }

}
