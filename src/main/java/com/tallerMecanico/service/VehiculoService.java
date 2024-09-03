package com.tallerMecanico.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.tallerMecanico.dto.VehiculoDto;
import com.tallerMecanico.entity.Vehiculo;
import com.tallerMecanico.projection.IVehiculoConOrdenClosedView;
import com.tallerMecanico.projection.IVehiculoReporte;
import com.tallerMecanico.projection.IVehiculoSinOrden;
import com.tallerMecanico.repository.IVehiculoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class VehiculoService implements IVehiculoService {

	@Autowired
	private IVehiculoRepository vehiculoRepository;

	// Consulta todos
	@Transactional(readOnly = true)
	public List<Vehiculo> findAll() {
		return (List<Vehiculo>) vehiculoRepository.findAll(Sort.by("idVehiculo"));
	}

	// vehiculos con orden de servicio
	/*
	 * @Transactional(readOnly = true) public List<Vehiculo>
	 * obtenerTodosLosVehiculosConOrdenServicio() { return
	 * vehiculoRepository.findAllWithOrdenServicio(); }
	 */

	@Transactional
	public List<IVehiculoConOrdenClosedView> findBy() {
		return vehiculoRepository.findBy();
	}

	@Transactional(readOnly = true)
	public Page<IVehiculoConOrdenClosedView> findBy(Pageable pageable) {
		return vehiculoRepository.findBy(pageable);
	}

	// vehiculo por id
	// @Transactional
	public IVehiculoConOrdenClosedView getVehiculoById(long id) {
		return vehiculoRepository.findByIdVehiculo(id)
				.orElseThrow(() -> new EntityNotFoundException("Vehiculo not found with id " + id));
	}

	// para admin o empleados
	@Transactional(readOnly = true)
	public IVehiculoConOrdenClosedView findByIdVehiculo(Long idVehiculo) {
		return vehiculoRepository.findByIdVehiculo(idVehiculo)
				.orElseThrow(() -> new EntityNotFoundException("Vehiculo not found with id " + idVehiculo));
	}

	// para clientes
	@Transactional(readOnly = true)
	public IVehiculoConOrdenClosedView findByIdVehiculoAndClienteId(Long idVehiculo, Long idCliente) {
		return vehiculoRepository.findByIdVehiculoAndClienteId(idVehiculo, idCliente)
				.orElseThrow(() -> new EntityNotFoundException(
						"Vehiculo not found with id " + idVehiculo + " for cliente " + idCliente));
	}

	// consulta todos para paginación
	@Transactional(readOnly = true)
	public Page<Vehiculo> findAllPage(Pageable pageable) {
		return vehiculoRepository.findAll(pageable);
	}

	// consulta por id
	@Transactional(readOnly = true)
	public Vehiculo findById(Long idVehiculo) {
		return vehiculoRepository.findById(idVehiculo).orElse(null);
	}

	// Crear
	@Transactional
	public Vehiculo createVehiculo(VehiculoDto vehiculo) {
		Vehiculo vehiculoEntity = new Vehiculo();
		vehiculoEntity.setVin(vehiculo.vin());
		vehiculoEntity.setMatricula(vehiculo.matricula());
		vehiculoEntity.setAnioModelo(vehiculo.anioModelo());
		vehiculoEntity.setColor(vehiculo.color());
		vehiculoEntity.setTipoMotor(vehiculo.tipoMotor());
		vehiculoEntity.setImagen(vehiculo.imagen());
		vehiculoEntity.setModelo(vehiculo.modelo());
		// vehiculoEntity.setOrdenServicio(vehiculo.ordenServicio());
		vehiculoEntity.setCliente(vehiculo.cliente());
		return vehiculoRepository.save(vehiculoEntity);
	}

	// Eliminar
	public Vehiculo deleteVehiculo(Long idVehiculo) {
		vehiculoRepository.deleteById(idVehiculo);
		return null;
	}

	// Modificar
	@Transactional
	public Vehiculo updateVehiculo(Long idVehiculo, VehiculoDto vehiculo) {
		Vehiculo vehiculoEntity = vehiculoRepository.findById(idVehiculo)
				.orElseThrow(() -> new NoSuchElementException("Vehiculo no encontrado con el ID: " + idVehiculo));
		vehiculoEntity.setVin(vehiculo.vin());
		vehiculoEntity.setMatricula(vehiculo.matricula());
		vehiculoEntity.setAnioModelo(vehiculo.anioModelo());
		vehiculoEntity.setColor(vehiculo.color());
		vehiculoEntity.setTipoMotor(vehiculo.tipoMotor());
		vehiculoEntity.setImagen(vehiculo.imagen());
		vehiculoEntity.setModelo(vehiculo.modelo());
		// vehiculoEntity.setOrdenServicio(vehiculo.ordenServicio());
		// vehiculoEntity.setCliente(vehiculo.cliente());
		return vehiculoRepository.save(vehiculoEntity);
	}

	@Transactional(readOnly = true)
	public List<IVehiculoConOrdenClosedView> getVehiculosByOrdenServicioEstatus(String estatus) {
		return vehiculoRepository.findVehiculosByOrdenServicioEstatus(estatus);
	}
	
	@Transactional(readOnly = true)
	public Page<IVehiculoConOrdenClosedView> getVehiculosByOrdenServicioEstatus(String estatus, Pageable pageable){
		return vehiculoRepository.findVehiculosByOrdenServicioEstatus(estatus, pageable);	
	}
	
	
	//Reporte
	/*
	public List<IVehiculoSinOrden> getAllVehiculos() {
		return vehiculoRepository.findAllProjectedBy();
	}
	*/
	public List<IVehiculoSinOrden> getAllVehiculos(Integer anioModelo, String marca) {
	    if (anioModelo != null && marca != null) {
	        return vehiculoRepository.findByAnioModeloAndMarca(anioModelo, marca);
	    } else if (anioModelo != null) {
	        return vehiculoRepository.findByAnioModelo(anioModelo);
	    } else if (marca != null) {
	        return vehiculoRepository.findByMarca(marca);
	    } else {
	        return vehiculoRepository.findAllProjectedBy();
	    }
	}
	
	
	public byte[] generarPDF1(List<IVehiculoSinOrden> vehiculos, LocalDate fechaInicio, LocalDate fechaFin) throws IOException {
	    try {
	        Document document = new Document();
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        PdfWriter.getInstance(document, baos);

	        document.open();

	        // Crear un párrafo con el título y centrarlo
	        Paragraph titulo = new Paragraph("Listado de Vehículos");
	        titulo.setAlignment(Element.ALIGN_CENTER);
	        document.add(titulo);

	        // Agregar subtítulo con el rango de fechas si existen
	        if (fechaInicio != null && fechaFin != null) {
	            Paragraph subtitulo = new Paragraph("Período: " + fechaInicio + " - " + fechaFin);
	            subtitulo.setAlignment(Element.ALIGN_CENTER);
	            document.add(subtitulo);
	        }

	        // Agregar salto de línea
	        document.add(Chunk.NEWLINE);

	        // Crear una tabla con 9 columnas
	        PdfPTable table = new PdfPTable(9);
	        table.setWidthPercentage(100); // Establecer el ancho de la tabla al 100% del ancho de la página

	        // Agregar encabezados
	        table.addCell("ID");
	        table.addCell("VIN");
	        table.addCell("Matrícula");
	        table.addCell("Año Modelo");
	        table.addCell("Color");
	        table.addCell("Imagen");
	        table.addCell("Tipo Motor");
	        table.addCell("Modelo");
	        table.addCell("Cliente");

	        // Agregar datos a la tabla
	        for (IVehiculoSinOrden vehiculo : vehiculos) {
	            table.addCell(String.valueOf(vehiculo.getIdVehiculo()));
	            table.addCell(vehiculo.getVin());
	            table.addCell(vehiculo.getMatricula());
	            table.addCell(String.valueOf(vehiculo.getAnioModelo()));
	            table.addCell(vehiculo.getColor());

	            if (isValidImageFile(vehiculo.getImagen())) {
	                try {
	                    Image imagen = Image.getInstance(vehiculo.getImagen());
	                    imagen.scaleToFit(50, 50); // Ajustar el tamaño de la imagen
	                    PdfPCell imagenCell = new PdfPCell(imagen, true);
	                    table.addCell(imagenCell);
	                } catch (MalformedURLException e) {
	                    e.printStackTrace();
	                    table.addCell(""); // Agregar una celda vacía en caso de error
	                }
	            } else {
	                table.addCell("");
	            }

	            table.addCell(vehiculo.getTipoMotor().getTipoMotor());
	            table.addCell(vehiculo.getModelo().getModelo());
	            table.addCell(vehiculo.getCliente().getNombre() + " " + vehiculo.getCliente().getApellidoPaterno());
	        }

	        // Agregar la tabla al documento
	        document.add(table);

	        document.close();

	        return baos.toByteArray();
	    } catch (DocumentException e) {
	        e.printStackTrace();
	        return new byte[0];
	    }
	}
	
	
	
	public byte[] generarPDF(List<IVehiculoReporte> vehiculos, LocalDate fechaInicio, LocalDate fechaFin) throws IOException {
	    try {
	        Document document = new Document();
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        PdfWriter.getInstance(document, baos);

	        document.open();

	        // Crear un párrafo con el título y centrarlo
	        Paragraph titulo = new Paragraph("Listado de Vehículos");
	        titulo.setAlignment(Element.ALIGN_CENTER);
	        document.add(titulo);

	        // Agregar subtítulo con el rango de fechas si existen
	        if (fechaInicio != null && fechaFin != null) {
	            Paragraph subtitulo = new Paragraph("Período: " + fechaInicio + " - " + fechaFin);
	            subtitulo.setAlignment(Element.ALIGN_CENTER);
	            document.add(subtitulo);
	        }

	        // Agregar salto de línea
	        document.add(Chunk.NEWLINE);

	        // Crear una tabla con 9 columnas
	        PdfPTable table = new PdfPTable(9);
	        table.setWidthPercentage(100); // Establecer el ancho de la tabla al 100% del ancho de la página

	        // Agregar encabezados
	        table.addCell("VIN");
	        table.addCell("Matrícula");
	        table.addCell("Año Modelo");
	        table.addCell("Color");
	        table.addCell("Tipo Motor");
	        table.addCell("Modelo");
	        table.addCell("Cliente");
	        table.addCell("Número de Servicio");
	        table.addCell("Costo Total");

	        // Agregar datos a la tabla
	        for (IVehiculoReporte vehiculo : vehiculos) {
	            table.addCell(vehiculo.getVin());
	            table.addCell(vehiculo.getMatricula());
	            table.addCell(String.valueOf(vehiculo.getAnioModelo()));
	            table.addCell(vehiculo.getColor());
	            table.addCell(vehiculo.getTipoMotor());
	            table.addCell(vehiculo.getModelo());
	            table.addCell(vehiculo.getCliente());
	            table.addCell(String.valueOf(vehiculo.getNumeroServicio()));
	            table.addCell(vehiculo.getCostoTotal() != null ? String.valueOf(vehiculo.getCostoTotal()) : "0.00"); // Manejo de nulos
	        }

	        // Agregar la tabla al documento
	        document.add(table);

	        document.close();

	        return baos.toByteArray();
	    } catch (DocumentException e) {
	        e.printStackTrace();
	        return new byte[0];
	    }
	}
	
	
	/*
	private boolean isValidImageUrl(String imageUrl) {
		// Implementa la lógica para verificar si la URL de la imagen es válida

		return imageUrl != null && !imageUrl.isEmpty();
	}
	*/

	private boolean isValidImageFile(String imagePath) {
        // Verifica si el archivo existe y no está vacío
        File file = new File(imagePath);
        return file.exists() && !file.isDirectory();
    }

	public List<IVehiculoReporte> obtenerReporteVehiculos(Date fechaInicio, Date fechaFin) {
        return vehiculoRepository.findVehiculosAtendidosPorPeriodo(fechaInicio, fechaFin);
    }


}
