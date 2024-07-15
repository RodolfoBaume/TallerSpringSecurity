package com.tallerMecanico.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.tallerMecanico.dto.EstatusServicioDto;
import com.tallerMecanico.entity.EstatusServicio;
import com.tallerMecanico.repository.IEstatusServicioRepository;

@Service
public class EstatusServicioService implements IEstatusServicioService {

	@Autowired
	private IEstatusServicioRepository estatusServicioRepository;

	// Consulta todos
	@Transactional(readOnly = true)
	public List<EstatusServicio> findAll() {
		return (List<EstatusServicio>) estatusServicioRepository.findAll(Sort.by("idEstatusServicio"));
	}

	// consulta todos para paginación
	@Transactional(readOnly = true)
	public Page<EstatusServicio> findAllPage(Pageable pageable) {
		return estatusServicioRepository.findAll(pageable);
	}
	
	// consulta por id
	@Transactional(readOnly = true)
	public EstatusServicio findById(Long idEstatusServicio) {
		return estatusServicioRepository.findById(idEstatusServicio).orElse(null);
	}

	// Crear
	@Transactional
	public EstatusServicio createEstatusServicio(EstatusServicioDto estatusServicio) {
		EstatusServicio estatusServicioEntity = new EstatusServicio();
		estatusServicioEntity.setEstatusServicio(estatusServicio.estatusServicio());
		estatusServicioEntity.setDepartamento(estatusServicio.departamento());
		return estatusServicioRepository.save(estatusServicioEntity);
	}

	// Eliminar
	public EstatusServicio deleteEstatusServicio(Long idEstatusServicio) {
		estatusServicioRepository.deleteById(idEstatusServicio);
		return null;
	}

	// Modificar
	@Transactional
	public EstatusServicio updateEstatusServicio(Long idEstatusServicio, EstatusServicioDto estatusServicio) {
		EstatusServicio estatusServicioEntity = estatusServicioRepository.findById(idEstatusServicio)
				.orElseThrow(() -> new NoSuchElementException("Estatus de servicio no encontrado con el ID: " + idEstatusServicio));
		estatusServicioEntity.setEstatusServicio(estatusServicio.estatusServicio());
		estatusServicioEntity.setDepartamento(estatusServicio.departamento());
		return estatusServicioRepository.save(estatusServicioEntity);
	}
	
	//Reportes
	public List<EstatusServicio> getAllEstatusServicios() {
        return estatusServicioRepository.findAll();
    }

    public byte[] generarPDF(List<EstatusServicio> estatusServicios) throws IOException {
        try {
            Document document = new Document();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);

            document.open();
            // Crear un párrafo con el título y centrarlo
            Paragraph titulo = new Paragraph("Listado de Estatus de Servicio");
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            // Agregar salto de línea
            document.add(Chunk.NEWLINE);

            // Crear una tabla con 3 columnas
            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100); // Establecer el ancho de la tabla al 100% del ancho de la página

            // Agregar encabezados
            table.addCell("ID");
            table.addCell("Estatus de Servicio");
            table.addCell("Departamento");

            // Agregar datos a la tabla
            for (EstatusServicio estatus : estatusServicios) {
                table.addCell(String.valueOf(estatus.getIdEstatusServicio()));
                table.addCell(estatus.getEstatusServicio());
                table.addCell(estatus.getDepartamento().getDepartamento()); 
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
