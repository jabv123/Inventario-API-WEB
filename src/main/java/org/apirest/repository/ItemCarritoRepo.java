package org.apirest.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apirest.modelo.ItemCarrito;

public class ItemCarritoRepo {

    private final List<ItemCarrito> itemsCarrito = Collections.synchronizedList(new ArrayList<>());
    private final AtomicInteger id = new AtomicInteger(1);

    //Crear item
    public ItemCarrito crearItem(ItemCarrito item) {
        item.setId(id.getAndIncrement());
        itemsCarrito.add(item);
        return item;
    }
}
