package com.tallerMecanico.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.tallerMecanico.dto.FacturaDto;
import com.tallerMecanico.dto.FacturaOrdenDto;
import com.tallerMecanico.dto.ReporteMesesDto;
import com.tallerMecanico.dto.VentasPorMesDTO;
import com.tallerMecanico.entity.DetalleFactura;
import com.tallerMecanico.entity.DetalleOrdenServicio;
import com.tallerMecanico.entity.EstatusServicio;
import com.tallerMecanico.entity.Factura;
import com.tallerMecanico.entity.OrdenServicio;
import com.tallerMecanico.projection.FacturaClosedViewImpl;
import com.tallerMecanico.projection.IDetalleFacturaProjection;
import com.tallerMecanico.projection.IFacturaClosedView;
import com.tallerMecanico.projection.IFacturaProjection;
import com.tallerMecanico.projection.IFacturaReporte;
import com.tallerMecanico.repository.IDetalleFacturaRepository;
import com.tallerMecanico.repository.IDetalleOrdenServicioRepository;
import com.tallerMecanico.repository.IFacturaRepository;
import com.tallerMecanico.repository.IOrdenServicioRepository;

@Service
public class FacturaService implements IFacturaService {

	@Autowired
	private IFacturaRepository facturaRepository;
	
	@Autowired
    private IOrdenServicioRepository ordenServicioRepository;

    @Autowired
    private IDetalleFacturaRepository detalleFacturaRepository;
    
    @Autowired
    private IDetalleOrdenServicioRepository detalleOrdenServicioRepository;
    
    @Autowired
    private IEstatusServicioService estatusServicioRepository;
	

	@Override
	public List<IFacturaProjection> findBy() {
		return facturaRepository.findBy();
	}
	
	/*
	// busca por id solo factura sin detalle
	@Transactional(readOnly = true)
	public IFacturaProjection findFacturaById(Long idFactura) {
		return facturaRepository.findFacturaById(idFactura);
	}
	*/


	@Transactional(readOnly=true)
	public List<?> obtenerTotalPorMeses(){
		return facturaRepository.obtenerReportePorMeses();

	}
	
	//Factura con detalle por Id
	@Transactional(readOnly = true)
	public IFacturaClosedView getFacturaWithDetalleById(Long idFactura) {
	    // Obtener la factura especificada por ID
	    IFacturaClosedView facturaProxy = facturaRepository.findFacturaById(idFactura);
	    if (facturaProxy == null) {
	        return null; // O manejar de otra manera, como lanzar una excepción
	    }

	    // Crear una nueva instancia de FacturaClosedViewImpl y establecer los valores
	    FacturaClosedViewImpl factura = new FacturaClosedViewImpl();
	    factura.setIdFactura(facturaProxy.getIdFactura());
	    factura.setFechaFactura(facturaProxy.getFechaFactura());
	    factura.setMonto(facturaProxy.getMonto());

	    // Establecer los datos del cliente
	    factura.setNombre(facturaProxy.getNombre());
	    factura.setApellidoPaterno(facturaProxy.getApellidoPaterno());
	    factura.setApellidoMaterno(facturaProxy.getApellidoMaterno());
	    factura.setDomicilio(facturaProxy.getDomicilio());
	    factura.setTelefono(facturaProxy.getTelefono());

	    // Obtener los detalles de la factura y establecerlos en la factura
	    List<IDetalleFacturaProjection> detalleFacturas = facturaRepository.findDetalleFacturaByFacturaId(factura.getIdFactura());
	    factura.setDetalleFactura(detalleFacturas);

	    return factura;
	}
	
	//consulta factura con detalle
	@Transactional(readOnly = true)
	public List<IFacturaClosedView> getAllFacturasWithDetalle(){
		List<IFacturaClosedView> facturasProxies = facturaRepository.findAllFacturas();
		List<IFacturaClosedView> facturas = new ArrayList<>();
		
		for(IFacturaClosedView facturaProxy : facturasProxies) {
			FacturaClosedViewImpl factura = new FacturaClosedViewImpl();
			factura.setIdFactura(facturaProxy.getIdFactura());
			factura.setFechaFactura(facturaProxy.getFechaFactura());
			factura.setMonto(facturaProxy.getMonto());
			
			List<IDetalleFacturaProjection> detalleFacturas = facturaRepository.findDetalleFacturaByFacturaId(factura.getIdFactura());
			factura.setDetalleFactura(detalleFacturas);
			
			facturas.add(factura);
		}
		return facturas;
	}
	
	// consulta todos para paginación
	@Transactional(readOnly = true)
	public Page<IFacturaClosedView> getAllFacturasWithDetalle(Pageable pageable){
		Page<IFacturaClosedView> facturasProxies = facturaRepository.findAllFacturas(pageable);
		
		return facturasProxies.map(facturaProxy -> {
			FacturaClosedViewImpl factura = new FacturaClosedViewImpl();
			factura.setIdFactura(facturaProxy.getIdFactura());
			factura.setFechaFactura(facturaProxy.getFechaFactura());
			factura.setMonto(facturaProxy.getMonto());
			
			List<IDetalleFacturaProjection> detalleFacturas = facturaRepository.findDetalleFacturaByFacturaId(factura.getIdFactura());
			factura.setDetalleFactura(detalleFacturas);
			
			return factura;	
		});
		
	}

	// consulta por id
	@Transactional(readOnly = true)
	public Factura findById(Long idFactura) {
		return facturaRepository.findById(idFactura).orElse(null);
	}
	

	// Crear
	@Transactional
	public Factura createFactura(FacturaDto factura) {
		Factura facturaEntity = new Factura();
		facturaEntity.setFechaFactura(factura.fechaFactura());
		facturaEntity.setMonto(factura.monto());
		facturaEntity.setOrdenServicio(factura.ordenServicio());
		System.out.println(factura.toString());
		return facturaRepository.save(facturaEntity);
	}

	// Eliminar
	public Factura deleteFactura(Long idFactura) {
		facturaRepository.deleteById(idFactura);
		return null;
	}

	// Modificar
	@Transactional
	public Factura updateFactura(Long idFactura, FacturaDto factura) {
		Factura facturaEntity = facturaRepository.findById(idFactura)
				.orElseThrow(() -> new NoSuchElementException("Factura no encontrada con el ID: " + idFactura));
		facturaEntity.setFechaFactura(factura.fechaFactura());
		facturaEntity.setMonto(factura.monto());
		facturaEntity.setOrdenServicio(factura.ordenServicio());

		return facturaRepository.save(facturaEntity);
	}

	public List<IFacturaReporte> getFacturasByDateRange(Date startDate, Date endDate) {
        return facturaRepository.findFacturasInRange(startDate, endDate);
    }

    public byte[] generarPDF(List<IFacturaReporte> facturas, Date startDate, Date endDate) throws IOException {
        try {
            Document document = new Document();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);

            document.open();
            
            // Formatear las fechas para el título
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String fechaInicio = sdf.format(startDate);
            String fechaFin = sdf.format(endDate);

            // Crear una fuente para el título
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
            
            // Crear un párrafo con el título, aplicar la fuente y centrarlo
            Paragraph titulo = new Paragraph("Reporte de Facturación del " + fechaInicio + " al " + fechaFin, titleFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            // Agregar salto de línea
            document.add(Chunk.NEWLINE);

            // Crear una tabla con 7 columnas
            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100); // Establecer el ancho de la tabla al 100% del ancho de la página

            // Agregar encabezados
            addTableHeader(table);

            // Calcular el total
            double total = 0;

            // Crear un formateador de moneda
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("es", "MX"));

            
            // Agregar datos a la tabla
            for (IFacturaReporte factura : facturas) {
                table.addCell(createCell(String.valueOf(factura.getIdFactura()), BaseColor.WHITE));
                table.addCell(createCell(factura.getFechaFactura().toString(), BaseColor.WHITE));
                table.addCell(createCell(currencyFormat.format(factura.getMonto()), BaseColor.WHITE));                table.addCell(createCell(factura.getClienteNombre() + " " + factura.getClienteApellidoPaterno() + " " + factura.getClienteApellidoMaterno(), BaseColor.WHITE));
                table.addCell(createCell(factura.getVehiculoVin(), BaseColor.WHITE));
                table.addCell(createCell(factura.getVehiculoMatricula(), BaseColor.WHITE));
                table.addCell(createCell(factura.getVehiculoModelo(), BaseColor.WHITE));
                total += factura.getMonto();
            }

            // Agregar una fila para el total
            table.addCell(createCell("", BaseColor.LIGHT_GRAY));
            table.addCell(createCell("Total:", BaseColor.LIGHT_GRAY));
            table.addCell(createCell(currencyFormat.format(total), BaseColor.LIGHT_GRAY));
            table.addCell(createCell("", BaseColor.LIGHT_GRAY));
            table.addCell(createCell("", BaseColor.LIGHT_GRAY));
            table.addCell(createCell("", BaseColor.LIGHT_GRAY));
            table.addCell(createCell("", BaseColor.LIGHT_GRAY));
            
            

            // Agregar la tabla al documento
            document.add(table);

            
         // Crear un párrafo con el total, aplicar la fuente y centrarlo
            Paragraph tituloTotal = new Paragraph("Total " + currencyFormat.format(total), titleFont);
            tituloTotal.setAlignment(Element.ALIGN_RIGHT);
            document.add(tituloTotal);
            
            document.close();

            return baos.toByteArray();
        } catch (DocumentException e) {
            // Manejar la excepción
            e.printStackTrace();
            return new byte[0];
        }
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("ID Factura", "Fecha Factura", "Monto", "Cliente", "VIN Vehículo", "Matrícula Vehículo", "Modelo Vehículo")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    private PdfPCell createCell(String content, BaseColor bgColor) {
        PdfPCell cell = new PdfPCell(new Phrase(content));
        cell.setBackgroundColor(bgColor);
        cell.setPadding(5);
        return cell;
    }


 // insercion de factura con ordenServicio
 	@Transactional
 	public FacturaOrdenDto crearFactura(long ordenServicioId) {
 		OrdenServicio ordenServicio = ordenServicioRepository.findById(ordenServicioId)
 				.orElseThrow(() -> new RuntimeException("Orden de Servicio no encontrada"));

 		// Obtener los detalles de la orden de servicio
 		List<DetalleOrdenServicio> detallesOrden = detalleOrdenServicioRepository
 				.findByOrdenServicio_IdOrdenServicio(ordenServicioId);

 		// Calcular el monto total de la factura
 		double montoTotal = detallesOrden.stream().mapToDouble(DetalleOrdenServicio::getCosto).sum();

 		Factura factura = new Factura();
 		factura.setFechaFactura(new Date()); // Usar la fecha actual para la factura
 		// factura.setMonto(ordenServicio.getDetalleOrdenServicios().stream().mapToDouble(DetalleOrdenServicio::getCosto).sum());
 		factura.setMonto(montoTotal);
 		factura.setOrdenServicio(ordenServicio);

 		Factura savedFactura = facturaRepository.save(factura);

 		// Crear los detalles de la factura
 		for (DetalleOrdenServicio detalleOrden : detallesOrden) {
 			DetalleFactura detalleFactura = new DetalleFactura();
 			detalleFactura.setDescripcionServicio(detalleOrden.getDescripcionServicio());
 			detalleFactura.setCosto(detalleOrden.getCosto());
 			detalleFactura.setFactura(savedFactura);
 			detalleFacturaRepository.save(detalleFactura);
 		}

 		
 		// Obtener el estatus de servicio con ID 6 con estado Entregado
 	    EstatusServicio estatusServicio = estatusServicioRepository.findById((long) 6);
 	    if (estatusServicio == null) {
 	        throw new RuntimeException("Estatus de Servicio no encontrado");
 	    }

 	    // Actualizar el estatus de la orden de servicio
 	    ordenServicio.setEstatusServicio(estatusServicio);
 	    ordenServicioRepository.save(ordenServicio);

 		return convertToDTO(savedFactura);
 	}

 	private FacturaOrdenDto convertToDTO(Factura factura) {
 		FacturaOrdenDto facturaDTO = new FacturaOrdenDto();
 		facturaDTO.setIdFactura(factura.getIdFactura());
 		facturaDTO.setFechaFactura(factura.getFechaFactura());
 		facturaDTO.setMonto(factura.getMonto());
 		return facturaDTO;
 	}
 	
 	
 	public List<VentasPorMesDTO> obtenerVentasPorMes(Date fechaInicio, Date fechaFin) {
        List<VentasPorMesDTO> ventasPorMes = facturaRepository.findVentasPorMes(fechaInicio, fechaFin);
        return rellenarMesesFaltantes(ventasPorMes, fechaInicio, fechaFin);
    }

    private List<VentasPorMesDTO> rellenarMesesFaltantes(List<VentasPorMesDTO> ventasPorMes, Date fechaInicio, Date fechaFin) {
        Map<String, VentasPorMesDTO> ventasMap = ventasPorMes.stream()
            .collect(Collectors.toMap(
                dto -> dto.getYear() + "-" + dto.getMonth(),
                dto -> dto
            ));

        List<VentasPorMesDTO> resultado = new ArrayList<>();

        LocalDate inicio = new java.sql.Date(fechaInicio.getTime()).toLocalDate();
        LocalDate fin = new java.sql.Date(fechaFin.getTime()).toLocalDate();

        for (LocalDate date = inicio.withDayOfMonth(1); !date.isAfter(fin); date = date.plusMonths(1)) {
            String key = date.getYear() + "-" + date.getMonthValue();
            VentasPorMesDTO dto = ventasMap.getOrDefault(key, new VentasPorMesDTO(date.getYear(), date.getMonthValue(), 0.0));
            resultado.add(dto);
        }

        return resultado;
    }
    
 	/*
 	public List<VentasPorMesDTO> getVentasPorMes() {
        return facturaRepository.findVentasPorMes().entrySet().stream()
                .map(entry -> new VentasPorMesDTO(entry.getKey().getMonthValue(), entry.getValue()))
                .toList();
    }
    */
}
