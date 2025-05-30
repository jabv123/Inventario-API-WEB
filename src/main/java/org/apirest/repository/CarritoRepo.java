package org.apirest.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.apirest.modelo.Carrito;

public class CarritoRepo {

    private final List<Carrito> carritos = Collections.synchronizedList(new ArrayList<>());
    private final AtomicInteger id = new AtomicInteger(1);

    //Obtener todos los carritos
    public List<Carrito> getAll() {
        return carritos;
    }

    //Obtener carrito por id
    public Optional<Carrito> getById(int id) {
        return carritos.stream()
                .filter(carrito -> carrito.getId() == id)
                .findFirst();
    }

    //Obtener carrito por idCliente
    public Optional<Carrito> getByIdCliente(int idCliente) {
        return carritos.stream()
                .filter(carrito -> carrito.getIdCliente() == idCliente)
                .findFirst();
    }

    //Agregar carrito
    public Carrito add(Carrito carrito) {
        carrito.setId(id.getAndIncrement());
        carrito.setFechaCreacion(LocalDate.now());
        carritos.add(carrito);
        return carrito;
    }

    //Actualizar carrito
    public Carrito update(Carrito carrito) {
        for (int i = 0; i < carritos.size();i ++){
            Carrito c = carritos.get(i);
            if (c.getId() == carrito.getId()) {
                // Actualizar solo los campos que no son nulos en el carrito entrante
                if (carrito.getIdCliente() != 0) {
                    c.setIdCliente(carrito.getIdCliente());
                }
                if (carrito.getItems() != null) {
                    c.setItems(carrito.getItems());
                }
                c.setFechaModificacion(LocalDate.now());
                return c; // Devolver el carrito actualizado
            }
        }
        return null;
    }

    //Eliminar carrito
    public boolean delete(int id) {
        Optional<Carrito> carrito = getById(id);
        if (carrito.isPresent()) {
            carritos.remove(carrito.get());
            return true;
        }
        return false;
    }

    //Eliminar carrito por idCliente
    public boolean deleteByIdCliente(int idCliente) {
        Optional<Carrito> carrito = getByIdCliente(idCliente);
        if (carrito.isPresent()) {
            carritos.remove(carrito.get());
            return true;
        }
        return false;
    }
    
}
