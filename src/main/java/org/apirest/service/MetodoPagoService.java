package org.apirest.service;

import java.util.ArrayList;
import java.util.List;

import org.apirest.modelo.Cliente;
import org.apirest.modelo.DetalleMetodoPago;
import org.apirest.modelo.MetodoPago;
import org.apirest.repository.DetalleMetodoPagoRepo;
import org.apirest.repository.MetodoPagoRepo;

import io.javalin.http.NotFoundResponse;

public class MetodoPagoService {

    private final MetodoPagoRepo metodoPagoRepo;
    private final DetalleMetodoPagoRepo detalleMetodoPagoRepo;
    private final ClienteService clienteService;

    public MetodoPagoService(MetodoPagoRepo metodoPagoRepo, DetalleMetodoPagoRepo detalleMetodoPagoRepo, ClienteService clienteService) {
        this.metodoPagoRepo = metodoPagoRepo;
        this.detalleMetodoPagoRepo = detalleMetodoPagoRepo;
        this.clienteService = clienteService;
    }

    public MetodoPago crearMetodoPago(MetodoPago metodoPago) {

        if (metodoPago == null) {
            throw new IllegalArgumentException("El método de pago no puede ser nulo.");
        }

        // Validar metodo de pago y detalles
        validarCliente(metodoPago.getIdCliente());

        if (metodoPago.getDetalles() == null || metodoPago.getDetalles().isEmpty()) {
            throw new IllegalArgumentException("El método de pago debe tener al menos un detalle.");
        }

        MetodoPago metodoPagoGuardado = metodoPagoRepo.save(metodoPago);

        // Guardar detalles del método de pago
        List<DetalleMetodoPago> detallesGuardados = new ArrayList<>();
        for (DetalleMetodoPago detalle : metodoPago.getDetalles()) {
            if (detalle == null) {
                throw new IllegalArgumentException("Los detalles del método de pago no pueden ser nulos.");
            }

            if(detalle.getValor() == null || detalle.getValor().isEmpty()) {
                throw new IllegalArgumentException("El valor del detalle del método de pago no puede ser nulo o vacío.");
            }

            if(detalle.getClave() == null || detalle.getClave().isEmpty()) {
                throw new IllegalArgumentException("La clave del detalle del método de pago no puede ser nula o vacía.");
            }

            detalle.setIdMetodoPago(metodoPagoGuardado.getId());
            DetalleMetodoPago detalleGuardado = detalleMetodoPagoRepo.save(detalle);
            detallesGuardados.add(detalleGuardado);
        }

        metodoPagoGuardado.setDetalles(detallesGuardados);
        return metodoPagoGuardado;

    }

    public List<MetodoPago> obtenerTodosMetodoPago() {
        List<MetodoPago> metodos = metodoPagoRepo.findAll();
        if (metodos.isEmpty()) {
            throw new IllegalArgumentException("No se encontraron métodos de pago.");
        }
        return metodos;
    }

    public List<MetodoPago> obtenerMetodosCliente (int idCliente) {
        validarCliente(idCliente);
        List<MetodoPago> metodos = metodoPagoRepo.findByClienteId(idCliente);
        if (metodos.isEmpty()) {
            throw new IllegalArgumentException("No se encontraron métodos de pago para el cliente con ID: " + idCliente);
        }
        return metodos; // Retorna todos los métodos de pago encontrados
    }

    // Actualizar método de pago incluyendo sus detalles
    public MetodoPago actualizarMetodoPago(MetodoPago metodoPago) {
        validarMetodoPago(metodoPago.getId());
        
        // Actualizar el método de pago principal
        MetodoPago metodoPagoActualizado = metodoPagoRepo.update(metodoPago);
        
        // Si se proporcionan detalles, actualizarlos
        if (metodoPago.getDetalles() != null && !metodoPago.getDetalles().isEmpty()) {
            // Eliminar los detalles existentes
            detalleMetodoPagoRepo.deleteByMetodoPago(metodoPago.getId());
            
            // Agregar los nuevos detalles
            List<DetalleMetodoPago> detallesGuardados = new ArrayList<>();
            for (DetalleMetodoPago detalle : metodoPago.getDetalles()) {
                if (detalle == null) {
                    throw new IllegalArgumentException("Los detalles del método de pago no pueden ser nulos.");
                }
                
                if (detalle.getValor() == null || detalle.getValor().isEmpty()) {
                    throw new IllegalArgumentException("El valor del detalle del método de pago no puede ser nulo o vacío.");
                }
                
                if (detalle.getClave() == null || detalle.getClave().isEmpty()) {
                    throw new IllegalArgumentException("La clave del detalle del método de pago no puede ser nula o vacía.");
                }
                
                detalle.setIdMetodoPago(metodoPago.getId());
                DetalleMetodoPago detalleGuardado = detalleMetodoPagoRepo.save(detalle);
                detallesGuardados.add(detalleGuardado);
            }
            metodoPagoActualizado.setDetalles(detallesGuardados);
        }
        
        return metodoPagoActualizado;
    }

    public List<DetalleMetodoPago> obtenerDetalleMetodoPago(int idMetodoPago) {
        validarMetodoPago(idMetodoPago);
        List<DetalleMetodoPago> detalles = detalleMetodoPagoRepo.findByMetodoPago(idMetodoPago);
        if (detalles == null || detalles.isEmpty()) {
            throw new IllegalArgumentException("No se encontró el detalle del método de pago con ID: " + idMetodoPago);
        }
        return detalles;
    }

    public boolean eliminarMetodoPago(int idMetodoPago) {
        validarMetodoPago(idMetodoPago);
        boolean eliminado = metodoPagoRepo.deleteById(idMetodoPago);
        if (!eliminado) {
            throw new IllegalArgumentException("No se pudo eliminar el método de pago con ID: " + idMetodoPago);
        }
        return eliminado;
    }

    public boolean actualizarEstadoMetodoPago(int idMetodoPago, boolean estado) {
        validarMetodoPago(idMetodoPago);

        // Verificar si el estado a actualizar es el mismo que el actual
        MetodoPago metodoPago = metodoPagoRepo.findById(idMetodoPago);
        if (metodoPago.isActivo() == estado) {
            throw new IllegalArgumentException("El estado del método de pago con ID: " + idMetodoPago + " ya es " + estado);
        }

        boolean actualizado = metodoPagoRepo.updateActivo(idMetodoPago, estado);
        if (!actualizado) {
            throw new IllegalArgumentException("No se pudo actualizar el estado del método de pago con ID: " + idMetodoPago);
        }
        return actualizado;
    }

    //Metodo auxiliar para validar cliente
    private void validarCliente(int idCliente) {
        Cliente cliente = clienteService.listarClientePorId(idCliente);
        if (cliente == null) {
            throw new NotFoundResponse("Cliente no encontrado con ID: " + idCliente);
        }
    }

    //Metodo auxiliar para validar metodo de pago
    private void validarMetodoPago(int idMetodoPago) {
        MetodoPago metodoPago = metodoPagoRepo.findById(idMetodoPago);
        if (metodoPago == null) {
            throw new NotFoundResponse("Método de pago no encontrado con ID: " + idMetodoPago);
        }
    }

}
