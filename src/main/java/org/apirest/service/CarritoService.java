package org.apirest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apirest.modelo.Carrito;
import org.apirest.modelo.ItemCarrito;
import org.apirest.modelo.Producto; // Añadir import
import org.apirest.repository.CarritoRepo;
import org.apirest.repository.ItemCarritoRepo;

public class CarritoService {

    private final CarritoRepo carritoRepo;
    private final ItemCarritoRepo itemCarritoRepo;
    private final ProductoService productoService; // Añadir ProductoService

    public CarritoService(CarritoRepo carritoRepo, ItemCarritoRepo itemCarritoRepo, ProductoService productoService) { // Modificar constructor
        this.carritoRepo = carritoRepo;
        this.itemCarritoRepo = itemCarritoRepo;
        this.productoService = productoService; // Asignar ProductoService
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
        if (carrito.getIdCliente() == 0) {
            throw new IllegalArgumentException("El idCliente no puede ser 0");
        }

        // Verificar si ya existe un carrito para este cliente
        Optional<Carrito> carritoExistente = carritoRepo.getByIdCliente(carrito.getIdCliente());
        if (carritoExistente.isPresent()) {
            throw new IllegalStateException("El cliente con id " + carrito.getIdCliente() + " ya tiene un carrito asignado.");
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
        if (!carritoOpt.isPresent()) {
            return Optional.empty(); // Carrito no encontrado
        }

        Producto producto = productoService.listarProductoPorId(item.getIdProducto());
        if (producto == null) {
            throw new IllegalArgumentException("El producto con id " + item.getIdProducto() + " no existe.");
        }

        Carrito carrito = carritoOpt.get();
        List<ItemCarrito> items = carrito.getItems();

        Optional<ItemCarrito> itemExistenteOpt = items.stream()
                .filter(i -> i.getIdProducto() == item.getIdProducto())
                .findFirst();

        if (itemExistenteOpt.isPresent()) {
            // El item ya existe en el carrito, indicar al usuario que use el método de actualización
            throw new IllegalStateException("El producto '" + producto.getNombre() + "' ya existe en el carrito. Utilice el método de actualizar cantidad para modificarlo.");
        }

        // Asignar precio unitario del producto al item del carrito
        item.setPrecioUnitario(producto.getPrecio());
        item.setIdCarrito(idCarrito);

        // Si se solicita cantidad <= 0 para un nuevo item, se interpreta como agregar 1.
        int cantidadParaNuevoItem = (item.getCantidad() <= 0) ? 1 : item.getCantidad();

        if (cantidadParaNuevoItem > producto.getCantidad()) {
            if (producto.getCantidad() > 0) {
                // Se puede agregar, pero menos de lo solicitado porque no hay suficiente stock.
                 cantidadParaNuevoItem = producto.getCantidad(); // Ajustar a lo disponible
            } else {
                // No hay stock del producto para agregarlo por primera vez
                throw new IllegalArgumentException("No hay stock disponible para el producto '" + producto.getNombre() + "'.");
            }
        }
        
        // Solo crear y agregar si la cantidad final a agregar es positiva
        if (cantidadParaNuevoItem > 0) {
            item.setCantidad(cantidadParaNuevoItem);
            ItemCarrito nuevoItem = itemCarritoRepo.crearItem(item);
            items.add(nuevoItem);
        } else {
             // Esto podría ocurrir si producto.getCantidad() era 0 y se intentó agregar.
             throw new IllegalArgumentException("No se pudo agregar el producto '" + producto.getNombre() + "' debido a falta de stock o cantidad inválida.");
        }

        carrito.setItems(items);
        return Optional.of(carritoRepo.update(carrito));
    }

    //Actualizar cantidad de un item del carrito
    public Optional<ItemCarrito> actualizarCantidadItem(int idCarrito, int idItem, int cantidadAModificar) {
        Optional<Carrito> carritoOpt = carritoRepo.getById(idCarrito);
        if (!carritoOpt.isPresent()) {
            return Optional.empty(); // Carrito no encontrado
        }

        Carrito carrito = carritoOpt.get();
        List<ItemCarrito> items = carrito.getItems();
        Optional<ItemCarrito> itemExistenteOpt = items.stream()
                .filter(i -> i.getId() == idItem)
                .findFirst();

        if (!itemExistenteOpt.isPresent()) {
            return Optional.empty(); // Item no encontrado en el carrito
        }

        ItemCarrito itemExistente = itemExistenteOpt.get();
        Producto producto = productoService.listarProductoPorId(itemExistente.getIdProducto());

        if (producto == null) {
            throw new IllegalStateException("El producto asociado al item del carrito no fue encontrado, idProducto: " + itemExistente.getIdProducto());
        }

        if (cantidadAModificar == 0) {
            // Si la cantidad a modificar es 0, no se hace nada y se devuelve el item tal cual.
            return Optional.of(itemExistente);
        }

        int cantidadActual = itemExistente.getCantidad();
        int nuevaCantidadCalculada = cantidadActual + cantidadAModificar;

        if (nuevaCantidadCalculada <= 0) {
            // Si la nueva cantidad calculada es 0 o negativa, eliminar el item del carrito
            items.remove(itemExistente);
            itemCarritoRepo.eliminarItem(itemExistente.getId()); 
            carrito.setItems(items);
            carritoRepo.update(carrito);
            return Optional.empty(); // Retornar empty ya que el item fue eliminado
        }

        // Si se está aumentando la cantidad (cantidadAModificar > 0)
        // Validar que la nueva cantidad no exceda el stock del producto
        if (cantidadAModificar > 0 && nuevaCantidadCalculada > producto.getCantidad()) {
            throw new IllegalArgumentException("La cantidad resultante (" + nuevaCantidadCalculada +
                                               ") excede el stock disponible (" + producto.getCantidad() +
                                               ") para el producto '" + producto.getNombre() + "'.");
        }

        // Actualizar la cantidad del item
        itemExistente.setCantidad(nuevaCantidadCalculada);
        // itemCarritoRepo.actualizarItem(itemExistente); // Si necesitas persistir el cambio a nivel de ItemCarrito individualmente
        
        carrito.setItems(items); // La lista 'items' ya contiene la referencia actualizada a 'itemExistente'
        carritoRepo.update(carrito);
        return Optional.of(itemExistente);
    }

    //Eliminar item del carrito
    public Optional<Carrito> eliminarItemDelCarrito(int idCarrito, ItemCarrito item) { // Podría recibir int idItem en lugar de ItemCarrito item
        Optional<Carrito> carritoOpt = carritoRepo.getById(idCarrito);
        if (carritoOpt.isPresent()) {
            Carrito carrito = carritoOpt.get();
            List<ItemCarrito> items = carrito.getItems();
            
            // Se busca el item por ID para asegurarse de que pertenece a la lista actual antes de intentar eliminarlo de la persistencia.
            Optional<ItemCarrito> itemAEliminarOpt = items.stream().filter(i -> i.getId() == item.getId()).findFirst();
            if (itemAEliminarOpt.isPresent()) {
                items.removeIf(i -> i.getId() == item.getId());
                itemCarritoRepo.eliminarItem(item.getId()); // Asumiendo que tienes un método para eliminar el item de la persistencia
                carrito.setItems(items);
                return Optional.of(carritoRepo.update(carrito));
            } else {
                return carritoOpt;
            }
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
