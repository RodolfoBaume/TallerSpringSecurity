package com.tallerMecanico.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.tallerMecanico.dto.ClienteDto;
import com.tallerMecanico.entity.Cliente;
import com.tallerMecanico.entity.Usuario;
import com.tallerMecanico.projection.ClienteClosedViewImpl;
import com.tallerMecanico.projection.IClienteClosedView;
import com.tallerMecanico.projection.IClienteProjection;
import com.tallerMecanico.projection.IVehiculoClosedView;
import com.tallerMecanico.repository.IClienteRepository;
import com.tallerMecanico.repository.IUsuarioRepository;

@Service
public class ClienteService implements IClienteService {

	@Autowired
	private IClienteRepository clienteRepository;

	@Autowired
	private IUsuarioRepository usuarioRepository;

	// Consulta todos
	@Transactional(readOnly = true)
	public List<Cliente> findAll() {
		return (List<Cliente>) clienteRepository.findAll(Sort.by("idCliente"));
	}

	// consulta todos para paginación
	@Transactional(readOnly = true)
	public Page<Cliente> findAllPage(Pageable pageable) {
		return clienteRepository.findAll(pageable);
	}

	// consulta por id
	@Transactional(readOnly = true)
	public Cliente findById(Long idCliente) {
		return clienteRepository.findById(idCliente).orElse(null);
	}

	// Crear
	@Transactional
	public Cliente createCliente(ClienteDto cliente, Long idUsuario) {
		Cliente clienteEntity = new Cliente();
		clienteEntity.setNombre(cliente.nombre());
		clienteEntity.setApellidoPaterno(cliente.apellidoPaterno());
		clienteEntity.setApellidoMaterno(cliente.apellidoMaterno());
		clienteEntity.setDomicilio(cliente.domicilio());
		clienteEntity.setTelefono(cliente.telefono());

		// Asignar el idUsuario al cliente
		Usuario usuario = new Usuario(); // Debes cargar el usuario del repositorio utilizando su id, o bien asegurarte
											// de que el clienteDto contenga la información completa del usuario
		usuario.setIdUsuario(idUsuario);
		clienteEntity.setUsuario(usuario);

		// clienteEntity.setVehiculos(cliente.vehiculos());

		return clienteRepository.save(clienteEntity);
	}

	// Eliminar
	/*
	 * public Cliente deleteCliente(Long idCliente) {
	 * clienteRepository.deleteById(idCliente); return null; }
	 */

	// Eliminar Cliente y usuario
	@Transactional
	public Cliente deleteCliente(Long idCliente) {
		// Buscar el cliente por su ID
		Cliente cliente = clienteRepository.findById(idCliente).orElse(null);
		if (cliente == null) {
			// Si el cliente no existe, retornar o manejar el caso según corresponda
			return cliente;
		}

		// Obtener el usuario asociado al cliente
		Usuario usuario = cliente.getUsuario();
		if (usuario != null) {
			// Eliminar los roles asignados al usuario
			usuario.getRol().clear(); // Eliminar todos los roles asignados al usuario
			// Guardar el usuario para que se actualicen las relaciones
			usuarioRepository.save(usuario);
			// Eliminar el usuario
			usuarioRepository.delete(usuario);
		}

		// Ahora se puede eliminar el cliente
		clienteRepository.delete(cliente);
		return cliente;
	}

	// Modificar
	@Transactional
	public Cliente updateCliente(Long idCliente, ClienteDto cliente) {
		Cliente clienteEntity = clienteRepository.findById(idCliente)
				.orElseThrow(() -> new NoSuchElementException("Cliente no encontrado con el ID: " + idCliente));
		clienteEntity.setNombre(cliente.nombre());
		clienteEntity.setApellidoPaterno(cliente.apellidoPaterno());
		clienteEntity.setApellidoMaterno(cliente.apellidoMaterno());
		clienteEntity.setDomicilio(cliente.domicilio());
		clienteEntity.setTelefono(cliente.telefono());
		// clienteEntity.setVehiculos(cliente.vehiculos());
		return clienteRepository.save(clienteEntity);
	}

	public List<Cliente> buscarClientesPorNombreApellidoPaternoApellidoMaternoTelefono(String searchTerm) {
		return clienteRepository.findByNombreApellidoPaternoApellidoMaternoTelefonoLike(searchTerm.toLowerCase());
	}

	// Clientes paginación con vehiculo
	public Page<IClienteClosedView> getAllClientesWithVehiculos(Pageable pageable) {
		Page<IClienteClosedView> clientesProxies = clienteRepository.findAllClientes(pageable);
		List<IClienteClosedView> clientes = new ArrayList<>();

		for (IClienteClosedView clienteProxy : clientesProxies) {
			ClienteClosedViewImpl cliente = new ClienteClosedViewImpl();
			cliente.setIdCliente(clienteProxy.getIdCliente());
			cliente.setNombre(clienteProxy.getNombre());
			cliente.setApellidoPaterno(clienteProxy.getApellidoPaterno());
			cliente.setApellidoMaterno(clienteProxy.getApellidoMaterno());
			cliente.setDomicilio(clienteProxy.getDomicilio());
			cliente.setTelefono(clienteProxy.getTelefono());
			cliente.setUsuario(clienteProxy.getUsuario());

			List<IVehiculoClosedView> vehiculos = clienteRepository.findVehiculosByClienteId(cliente.getIdCliente());
			cliente.setVehiculos(vehiculos);

			clientes.add(cliente);
		}
		return new PageImpl<>(clientes, pageable, clientesProxies.getTotalElements());
	}

	// Clientes
	public List<IClienteClosedView> getAllClientesWithVehiculos() {
		List<IClienteClosedView> clientesProxies = clienteRepository.findAllClientes();
		List<IClienteClosedView> clientes = new ArrayList<>();

		for (IClienteClosedView clienteProxy : clientesProxies) {
			ClienteClosedViewImpl cliente = new ClienteClosedViewImpl();
			cliente.setIdCliente(clienteProxy.getIdCliente());
			cliente.setNombre(clienteProxy.getNombre());
			cliente.setApellidoPaterno(clienteProxy.getApellidoPaterno());
			cliente.setApellidoMaterno(clienteProxy.getApellidoMaterno());
			cliente.setDomicilio(clienteProxy.getDomicilio());
			cliente.setTelefono(clienteProxy.getTelefono());
			cliente.setUsuario(clienteProxy.getUsuario());

			List<IVehiculoClosedView> vehiculos = clienteRepository.findVehiculosByClienteId(cliente.getIdCliente());
			cliente.setVehiculos(vehiculos);

			clientes.add(cliente);
		}
		return clientes;
	}

	public Page<IClienteClosedView> getAllClientesWithVehiculosPage(Pageable pageable) {
		Page<IClienteClosedView> clientesProxies = clienteRepository.findAllClientes(pageable);

		return clientesProxies.map(clienteProxy -> {
			ClienteClosedViewImpl cliente = new ClienteClosedViewImpl();
			cliente.setIdCliente(clienteProxy.getIdCliente());
			cliente.setNombre(clienteProxy.getNombre());
			cliente.setApellidoPaterno(clienteProxy.getApellidoPaterno());
			cliente.setApellidoMaterno(clienteProxy.getApellidoMaterno());
			cliente.setDomicilio(clienteProxy.getDomicilio());
			cliente.setTelefono(clienteProxy.getTelefono());
			cliente.setUsuario(clienteProxy.getUsuario());

			List<IVehiculoClosedView> vehiculos = clienteRepository.findVehiculosByClienteId(cliente.getIdCliente());
			cliente.setVehiculos(vehiculos);

			return cliente;
		});
	}

	// Reporte
	public List<IClienteProjection> getAllClientes(String orderBy, String orderDirection) {
        Sort.Direction direction = Sort.Direction.fromString(orderDirection);
        Sort sort = Sort.by(direction, orderBy);
        return clienteRepository.findAllProjectedBy(sort);
    }

    public byte[] generarPDF(List<IClienteProjection> clientes) throws IOException {
        try {
            Document document = new Document();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);

            document.open();
            // Crear un párrafo con el título y centrarlo
            Paragraph titulo = new Paragraph("Listado de Clientes");
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            // Agregar salto de línea
            document.add(Chunk.NEWLINE);

            // Crear una tabla con 6 columnas
            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100); // Establecer el ancho de la tabla al 100% del ancho de la página

            // Agregar encabezados
            table.addCell("ID");
            table.addCell("Nombre");
            table.addCell("Apellido Paterno");
            table.addCell("Apellido Materno");
            table.addCell("Domicilio");
            table.addCell("Telefono");

            // Agregar datos a la tabla
            for (IClienteProjection cliente : clientes) {
                table.addCell(String.valueOf(cliente.getIdCliente()));
                table.addCell(cliente.getNombre());
                table.addCell(cliente.getApellidoPaterno());
                table.addCell(cliente.getApellidoMaterno());
                table.addCell(cliente.getDomicilio());
                table.addCell(cliente.getTelefono());
            }

            // Agregar la tabla al documento
            document.add(table);

            document.close();

            return baos.toByteArray();
        } catch (DocumentException e) {
            // Manejar la excepción
            e.printStackTrace();
            return new byte[0];
        }
    }
}
