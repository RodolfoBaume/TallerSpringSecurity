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
import com.tallerMecanico.dto.ModeloDto;
import com.tallerMecanico.entity.Modelo;
import com.tallerMecanico.repository.IModeloRepository;

@Service
public class ModeloService implements IModeloService {

	@Autowired
	private IModeloRepository modeloRepository;

	// Consulta todos
	@Transactional(readOnly = true)
	public List<Modelo> findAll() {
		return (List<Modelo>) modeloRepository.findAll(Sort.by("idModelo"));
	}

	// consulta todos para paginación
	@Transactional(readOnly = true)
	public Page<Modelo> findAllPage(Pageable pageable) {
		return modeloRepository.findAll(pageable);
	}

	// consulta por id
	@Transactional(readOnly = true)
	public Modelo findById(Long idModelo) {
		return modeloRepository.findById(idModelo).orElse(null);
	}

	// Crear
	@Transactional
	public Modelo createModelo(ModeloDto modelo) {
		Modelo modeloEntity = new Modelo();
		modeloEntity.setModelo(modelo.modelo());
		modeloEntity.setMarca(modelo.marca());
		return modeloRepository.save(modeloEntity);
	}

	// Eliminar
	public Modelo deleteModelo(Long idModelo) {
		modeloRepository.deleteById(idModelo);
		return null;
	}

	// Modificar
	@Transactional
	public Modelo updateModelo(Long idModelo, ModeloDto modelo) {
		Modelo modeloEntity = modeloRepository.findById(idModelo)
				.orElseThrow(() -> new NoSuchElementException("Modelo no encontrado con el ID: " + idModelo));
		modeloEntity.setModelo(modelo.modelo());
		modeloEntity.setMarca(modelo.marca());
		return modeloRepository.save(modeloEntity);
	}
	
	//Reportes
	public List<Modelo> getAllModelos() {
        return modeloRepository.findAll();
    }

    public byte[] generarPDF(List<Modelo> modelos) throws IOException {
        try {
            Document document = new Document();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);

            document.open();
            // Crear un párrafo con el título y centrarlo
            Paragraph titulo = new Paragraph("Listado de Modelos");
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            // Agregar salto de línea
            document.add(Chunk.NEWLINE);

            // Crear una tabla con 3 columnas
            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100); // Establecer el ancho de la tabla al 100% del ancho de la página

            // Agregar encabezados
            table.addCell("ID");
            table.addCell("Marca");
            table.addCell("Modelo");

            // Agregar datos a la tabla
            for (Modelo modelo : modelos) {
                table.addCell(String.valueOf(modelo.getIdModelo()));
                table.addCell(modelo.getMarca().getMarca()); 
                table.addCell(modelo.getModelo());
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
