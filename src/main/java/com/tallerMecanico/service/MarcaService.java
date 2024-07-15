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
import com.tallerMecanico.dto.MarcaDto;
import com.tallerMecanico.entity.Marca;
import com.tallerMecanico.repository.IMarcaRepository;

@Service
public class MarcaService implements IMarcaService {

	@Autowired
	private IMarcaRepository marcaRepository;

	// Consulta todos
	@Transactional(readOnly = true)
	public List<Marca> findAll() {
		return (List<Marca>) marcaRepository.findAll(Sort.by("Marca"));
	}

	// consulta todos para paginación
	@Transactional(readOnly = true)
	public Page<Marca> findAllPage(Pageable pageable) {
		return marcaRepository.findAll(pageable);
	}

	// consulta por id
	@Transactional(readOnly = true)
	public Marca findById(Long idMarca) {
		return marcaRepository.findById(idMarca).orElse(null);
	}

	// Crear
	@Transactional
	public Marca createMarca(MarcaDto marca) {
		Marca marcaEntity = new Marca();
		marcaEntity.setMarca(marca.marca());
		return marcaRepository.save(marcaEntity);
	}

	// Eliminar
	public Marca deleteMarca(Long idMarca) {
		marcaRepository.deleteById(idMarca);
		return null;
	}

	// Modificar
	@Transactional
	public Marca updateMarca(Long idMarca, MarcaDto marca) {
		Marca marcaEntity = marcaRepository.findById(idMarca)
				.orElseThrow(() -> new NoSuchElementException("Marca no encontrada con el ID: " + idMarca));
		marcaEntity.setMarca(marca.marca());
		return marcaRepository.save(marcaEntity);
	}
	
	//Reportes
	public List<Marca> getAllMarcas() {
        return marcaRepository.findAll();
    }

    public byte[] generarPDF(List<Marca> marcas) throws IOException {
        try {
            Document document = new Document();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);

            document.open();
            // Crear un párrafo con el título y centrarlo
            Paragraph titulo = new Paragraph("Listado de Marcas");
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            // Agregar salto de línea
            document.add(Chunk.NEWLINE);

            // Crear una tabla con 2 columnas
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100); // Establecer el ancho de la tabla al 100% del ancho de la página

            // Agregar encabezados
            table.addCell("ID");
            table.addCell("Marca");

            // Agregar datos a la tabla
            for (Marca marca : marcas) {
                table.addCell(String.valueOf(marca.getIdMarca()));
                table.addCell(marca.getMarca());
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
