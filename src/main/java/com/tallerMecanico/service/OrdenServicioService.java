package com.tallerMecanico.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.tallerMecanico.dto.OrdenServicioDto;
import com.tallerMecanico.entity.OrdenServicio;
import com.tallerMecanico.projection.IDetalleOrdenServicioProjection;
import com.tallerMecanico.projection.IOrdenServicioDepto;
import com.tallerMecanico.projection.IOrdenServicioProjection;
import com.tallerMecanico.projection.IOrdenServicioSinDetalle;
import com.tallerMecanico.projection.IVehiculoConOrdenClosedView;
import com.tallerMecanico.projection.OrdenServicioProjectionImpl;
import com.tallerMecanico.projection.OrdenServicioProjectionWithDetails;
import com.tallerMecanico.projection.OrdenServicioProjectionWithDetails;
import com.tallerMecanico.repository.IDetalleOrdenServicioRepository;
import com.tallerMecanico.repository.IOrdenServicioRepository;

@Service
public class OrdenServicioService implements IOrdenServicioService {

	@Autowired
	private IOrdenServicioRepository ordenServicioRepository;
	@Autowired
	private IDetalleOrdenServicioRepository detalleOrdenServicioRepository;
	/*
	 * @Autowired public OrdenServicioService(IOrdenServicioRepository
	 * ordenServicioRepository) { this.ordenServicioRepository =
	 * ordenServicioRepository; }
	 */

	public OrdenServicioService(IOrdenServicioRepository ordenServicioRepository,
			IDetalleOrdenServicioRepository detalleOrdenServicioRepository) {
		this.ordenServicioRepository = ordenServicioRepository;
		this.detalleOrdenServicioRepository = detalleOrdenServicioRepository;
	}

	public List<IOrdenServicioSinDetalle> getAllOrdenesServicio() {
		return ordenServicioRepository.findAllProjected();
	}
	
	
	@Transactional(readOnly = true)
	public Slice<IOrdenServicioSinDetalle> getAllOrdenesServicio(Pageable pageable) {
	    return ordenServicioRepository.findAllProjected(pageable);
	}
	
	

	public IOrdenServicioProjection getOrdenServicioById(long ordenServicioId) {
		OrdenServicio ordenServicio = ordenServicioRepository.findById(ordenServicioId)
				.orElseThrow();
		List<IDetalleOrdenServicioProjection> detalles = detalleOrdenServicioRepository
				.findByOrdenServicio(ordenServicio);
		return new OrdenServicioProjectionImpl(ordenServicio, detalles);
	}


	// Consulta todos
	@Transactional(readOnly = true)
	public List<OrdenServicio> findAll() {
		return (List<OrdenServicio>) ordenServicioRepository.findAll(Sort.by("idOrdenServicio"));
	}

	// consulta todos para paginación
	@Transactional(readOnly = true)
	public Page<OrdenServicio> findAllPage(Pageable pageable) {
		return ordenServicioRepository.findAll(pageable);
	}


	// consulta por id
	@Transactional(readOnly = true)
	public OrdenServicio findById(Long idOrdenServicio) {
		return ordenServicioRepository.findById(idOrdenServicio).orElse(null);
	}

	/*
	 * @Transactional(readOnly = true) public OrdenServicio findById(Long
	 * idOrdenServicio) { OrdenServicio ordenServicio =
	 * ordenServicioRepository.findById(idOrdenServicio) .orElseThrow(() -> new
	 * NoSuchElementException("Orden de Servicio no encontrada con el ID: " +
	 * idOrdenServicio)); // Cargar explícitamente el vehículo para evitar que sea
	 * nulo en la serialización ordenServicio.getVehiculo(); // Esto carga el
	 * vehículo asociado a la orden de servicio return ordenServicio; }
	 */

	// Crear
	@Transactional
	public OrdenServicio createOrdenServicio(OrdenServicioDto ordenServicio) {
		OrdenServicio ordenServicioEntity = new OrdenServicio();
		ordenServicioEntity.setFechaOrden(ordenServicio.fechaOrden());
		ordenServicioEntity.setFalla(ordenServicio.falla());
		ordenServicioEntity.setKilometraje(ordenServicio.kilometraje());
		ordenServicioEntity.setObservaciones(ordenServicio.observaciones());
		ordenServicioEntity.setEstatusServicio(ordenServicio.estatusServicio());
		ordenServicioEntity.setFactura(ordenServicio.factura());
		ordenServicioEntity.setVehiculo(ordenServicio.vehiculo());
		ordenServicioEntity.setComentarios(ordenServicio.comentarios());
		ordenServicioEntity.setEmpleado(ordenServicio.empleado());
		System.out.println(ordenServicio.toString());
		return ordenServicioRepository.save(ordenServicioEntity);
		// return new OrdenServicio();
	}

	// Eliminar
	public OrdenServicio deleteOrdenServicio(Long idOrdenServicio) {
		ordenServicioRepository.deleteById(idOrdenServicio);
		return null;
	}

	// Modificar
	@Transactional
	public OrdenServicio updateOrdenServicio(Long idOrdenServicio, OrdenServicioDto ordenServicio) {
		OrdenServicio ordenServicioEntity = ordenServicioRepository.findById(idOrdenServicio).orElseThrow(
				() -> new NoSuchElementException("Orden de Servicio no encontrada con el ID: " + idOrdenServicio));
		ordenServicioEntity.setFechaOrden(ordenServicio.fechaOrden());
		ordenServicioEntity.setFalla(ordenServicio.falla());
		ordenServicioEntity.setKilometraje(ordenServicio.kilometraje());
		ordenServicioEntity.setObservaciones(ordenServicio.observaciones());
		ordenServicioEntity.setEstatusServicio(ordenServicio.estatusServicio());
		ordenServicioEntity.setFactura(ordenServicio.factura());
		ordenServicioEntity.setVehiculo(ordenServicio.vehiculo());
		ordenServicioEntity.setComentarios(ordenServicio.comentarios());
		ordenServicioEntity.setEmpleado(ordenServicio.empleado());

		return ordenServicioRepository.save(ordenServicioEntity);
	}

	@Transactional
	public List<IOrdenServicioDepto> obtenerPorEstatusServicio(String estatus) {
		List<IOrdenServicioDepto> ordenes = null;

		try{
			ordenes = ordenServicioRepository.findByEstatusServicio(estatus);
		}catch(DataAccessException e){
			System.out.println( e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
			System.out.println("ERROR SERVICIO>>>>>>>>");
		}
		return ordenes;
	}
	
	
	
	// paginacion por estatusServicio
	@Transactional
	public Page<IOrdenServicioDepto> obtenerPorEstatusServicio(@Param("estatus") String estatus, Pageable pageable){
		return ordenServicioRepository.findByEstatusServicio(estatus, pageable);
	}  


	// OrdenServicio por departamento
	@Transactional(readOnly = true)
	public List<IOrdenServicioDepto> getOrdenesServicioByDepartamento(Long idDepartamento, Sort sort) {
		return ordenServicioRepository.findByDepartamentoId(idDepartamento, sort);
	}
	
	// paginacion OrdenesServicio por departamento
	@Transactional(readOnly = true)
	public Page<IOrdenServicioDepto> getOrdenesServicioByDepartamento(Long idDepartamento, Pageable pageable){
		return ordenServicioRepository.findByDepartamentoId(idDepartamento, pageable);
	}

	//Reportes 
	/*
	public List<IOrdenServicioProjection> getAllOrdenesServicioRep() {
        return ordenServicioRepository.findAllProjectedBy();
    }
    */
	
	public IOrdenServicioProjection findProjectedById(Long idOrdenServicio) {
        IOrdenServicioProjection ordenServicio = ordenServicioRepository.findProjectedById(idOrdenServicio);
        List<IDetalleOrdenServicioProjection> detalles = ordenServicioRepository.findDetallesByOrdenServicioId(idOrdenServicio);
        
        // Aquí puedes utilizar un DTO para combinar los datos si la proyección no permite setters
        OrdenServicioProjectionWithDetails result = new OrdenServicioProjectionWithDetails(ordenServicio);
        result.setDetalleOrdenServicios(detalles);

        return result;
    }

    public byte[] generarPDF(List<IOrdenServicioProjection> ordenesServicio) throws IOException {
        try {
            Document document = new Document();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);

            document.open();
            // Crear un párrafo con el título y centrarlo
            Paragraph titulo = new Paragraph("Listado de Órdenes de Servicio");
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            // Agregar salto de línea
            document.add(Chunk.NEWLINE);

            // Crear una tabla con 9 columnas
            PdfPTable table = new PdfPTable(9);
            table.setWidthPercentage(100); // Establecer el ancho de la tabla al 100% del ancho de la página

            // Agregar encabezados
            table.addCell("ID");
            table.addCell("Fecha Orden");
            table.addCell("Falla");
            table.addCell("Kilometraje");
            table.addCell("Observaciones");
            table.addCell("Estatus Servicio");
            table.addCell("Comentarios");
            table.addCell("Vehículo");
            table.addCell("Empleado");

            // Agregar datos a la tabla
            for (IOrdenServicioProjection orden : ordenesServicio) {
                table.addCell(String.valueOf(orden.getIdOrdenServicio()));
                table.addCell(orden.getFechaOrden().toString());
                table.addCell(orden.getFalla());
                table.addCell(orden.getKilometraje());
                table.addCell(orden.getObservaciones());
                table.addCell(orden.getEstatusServicio().getEstatusServicio());
                table.addCell(orden.getComentarios());
                table.addCell(orden.getVehiculo().getMatricula());
                table.addCell(orden.getEmpleado().getNombre() + " " + orden.getEmpleado().getApellidoPaterno());
            }

            // Agregar la tabla al documento
            document.add(table);

            // Agregar salto de línea
            document.add(Chunk.NEWLINE);

            // Agregar detalle de cada orden de servicio
            for (IOrdenServicioProjection orden : ordenesServicio) {
                Paragraph detalleTitulo = new Paragraph("Detalles de Orden de Servicio ID: " + orden.getIdOrdenServicio());
                detalleTitulo.setAlignment(Element.ALIGN_CENTER);
                document.add(detalleTitulo);

                // Crear una tabla para los detalles
                PdfPTable detalleTable = new PdfPTable(3);
                detalleTable.setWidthPercentage(100);

                // Agregar encabezados de detalle
                detalleTable.addCell("ID Detalle");
                detalleTable.addCell("Descripción Servicio");
                detalleTable.addCell("Costo");

                // Agregar datos de detalle
                for (IDetalleOrdenServicioProjection detalle : orden.getDetalleOrdenServicios()) {
                    detalleTable.addCell(String.valueOf(detalle.getIdDetalleOrdenServicio()));
                    detalleTable.addCell(detalle.getDescripcionServicio());
                    detalleTable.addCell(String.valueOf(detalle.getCosto()));
                }

                // Agregar la tabla de detalles al documento
                document.add(detalleTable);

                // Agregar salto de línea
                document.add(Chunk.NEWLINE);
            }

            document.close();

            return baos.toByteArray();
        } catch (DocumentException e) {
            // Manejar la excepción
            e.printStackTrace();
            return new byte[0];
        }
    }
}
