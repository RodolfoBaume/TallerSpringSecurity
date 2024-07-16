package com.tallerMecanico.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
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
import com.tallerMecanico.entity.Factura;
import com.tallerMecanico.projection.IFacturaProjection;
import com.tallerMecanico.projection.IFacturaReporte;
import com.tallerMecanico.repository.IFacturaRepository;

@Service
public class FacturaService implements IFacturaService {

	@Autowired
	private IFacturaRepository facturaRepository;

	@Override
	public List<IFacturaProjection> findBy() {
		return facturaRepository.findBy();
	}

	// consulta todos para paginación
	@Transactional(readOnly = true)
	public Page<IFacturaProjection> findPage(Pageable pageable) {
		return facturaRepository.findAllFacturas(pageable);
	}

	// consulta por id
	@Transactional(readOnly = true)
	public Factura findById(Long idFactura) {
		return facturaRepository.findById(idFactura).orElse(null);
	}

	@Transactional(readOnly = true)
	public IFacturaProjection findFacturaById(Long idFactura) {
		return facturaRepository.findFacturaById(idFactura);
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

}
