package org.apirest.service;

import java.util.List;

import org.apirest.modelo.Cliente;
import org.apirest.modelo.DetalleMetodoPago;
import org.apirest.modelo.MetodoPago;
import org.apirest.repository.DetalleMetodoPagoRepo;
import org.apirest.repository.MetodoPagoRepo;

public class MetodoPagoService {

    private final MetodoPagoRepo metodoPagoRepo;
    private final DetalleMetodoPagoRepo detalleMetodoPagoRepo;
    private final ClienteService clienteService;

    public MetodoPagoService(MetodoPagoRepo metodoPagoRepo, DetalleMetodoPagoRepo detalleMetodoPagoRepo, ClienteService clienteService) {
        this.metodoPagoRepo = metodoPagoRepo;
        this.detalleMetodoPagoRepo = detalleMetodoPagoRepo;
        this.clienteService = clienteService;
    }

    public MetodoPago crearMetodoPago(MetodoPago metodoPago, List<DetalleMetodoPago> detalles) {
        // Validar metodo de pago y detalles

        if (metodoPago == null || detalles == null || detalles.isEmpty()) {
            throw new IllegalArgumentException("El método de pago y sus detalles no pueden ser nulos o vacíos.");
        }
        metodoPago.setDetalles(detalles);
        return metodoPagoRepo.save(metodoPago);
    }

    public MetodoPago obtenerMetodosCliente (int idCliente) {
        validarCliente(idCliente);
        List<MetodoPago> metodos = metodoPagoRepo.findByClienteId(idCliente);
        if (metodos.isEmpty()) {
            throw new RuntimeException("No se encontraron métodos de pago para el cliente con ID: " + idCliente);
        }
        return metodos.get(0); // Retorna el primer método de pago encontrado
    }

    public MetodoPago actualizarMetodoPago(MetodoPago metodoPago) {
        if (metodoPago == null || metodoPago.getId() <= 0) {
            throw new IllegalArgumentException("El método de pago no puede ser nulo y debe tener un ID válido.");
        }
        return metodoPagoRepo.update(metodoPago);
    }

    // TODO: Ver flujo ya que aqui solo se trae un detalle, pero se pueden tener varios
    public DetalleMetodoPago obtenerDetalleMetodoPago(int idMetodoPago) {
        validarMetodoPago(idMetodoPago);
        DetalleMetodoPago detalle = detalleMetodoPagoRepo.findByMetodoPago(idMetodoPago);
        if (detalle == null) {
            throw new RuntimeException("No se encontró el detalle del método de pago con ID: " + idMetodoPago);
        }
        return detalle;
    }

    public boolean eliminarMetodoPago(int idMetodoPago) {
        validarMetodoPago(idMetodoPago);
        boolean eliminado = metodoPagoRepo.deleteById(idMetodoPago);
        if (!eliminado) {
            throw new RuntimeException("No se pudo eliminar el método de pago con ID: " + idMetodoPago);
        }
        return eliminado;
    }

    public boolean actualizarEstadoMetodoPago(int idMetodoPago, boolean estado) {
        validarMetodoPago(idMetodoPago);
        boolean actualizado = metodoPagoRepo.updateActivo(idMetodoPago, estado);
        if (!actualizado) {
            throw new RuntimeException("No se pudo actualizar el estado del método de pago con ID: " + idMetodoPago);
        }
        return actualizado;
    }


    //Metodo auxiliar para validar cliente y metodo de pago
    private void validarCliente(int idCliente) {
        Cliente cliente = clienteService.listarClientePorId(idCliente);
        if (cliente == null) {
            throw new RuntimeException("Cliente no encontrado con ID: " + idCliente);
        }
    }

    //Metodo auxiliar para validar metodo de pago
    private void validarMetodoPago(int idMetodoPago) {
        MetodoPago metodoPago = metodoPagoRepo.findById(idMetodoPago);
        if (metodoPago == null) {
            throw new RuntimeException("Método de pago no encontrado con ID: " + idMetodoPago);
        }
    }

}
