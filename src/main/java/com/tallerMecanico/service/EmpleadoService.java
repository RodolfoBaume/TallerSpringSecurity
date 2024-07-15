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
import com.tallerMecanico.dto.EmpleadoDto;
import com.tallerMecanico.entity.Empleado;
import com.tallerMecanico.entity.Usuario;
import com.tallerMecanico.repository.IEmpleadoRepository;
import com.tallerMecanico.repository.IUsuarioRepository;

@Service
public class EmpleadoService implements IEmpleadoService {

	@Autowired
	private IEmpleadoRepository empleadoRepository;
	
	@Autowired
	private IUsuarioRepository usuarioRepository;

	// Consulta todos
	@Transactional(readOnly = true)
	public List<Empleado> findAll() {
		return (List<Empleado>) empleadoRepository.findAll(Sort.by("idEmpleado"));
	}
	
	// consulta todos para paginación
	@Transactional(readOnly = true)
	public Page<Empleado> findAllPage(Pageable pageable) {
		return empleadoRepository.findAll(pageable);
	}

	// consulta por id
	@Transactional(readOnly = true)
	public Empleado findById(Long idEmpleado) {
		return empleadoRepository.findById(idEmpleado).orElse(null);
	}

	// Crear
	@Transactional
	public Empleado createEmpleado(EmpleadoDto empleado, Long idUsuario) {
		Empleado empleadoEntity = new Empleado();
		empleadoEntity.setNombre(empleado.nombre());
		empleadoEntity.setApellidoPaterno(empleado.apellidoPaterno());
		empleadoEntity.setApellidoMaterno(empleado.apellidoMaterno());
		empleadoEntity.setNss(empleado.nss());
		empleadoEntity.setCurp(empleado.curp());
		empleadoEntity.setRfc(empleado.rfc());
		empleadoEntity.setPuesto(empleado.puesto());
		empleadoEntity.setObservaciones(empleado.observaciones());

		// Asignar el idUsuario al empleado
		Usuario usuario = new Usuario(); // Debes cargar el usuario del repositorio utilizando su id, o bien asegurarte
											// de que el clienteDto contenga la información completa del usuario
		usuario.setIdUsuario(idUsuario);
		empleadoEntity.setUsuario(usuario);

		return empleadoRepository.save(empleadoEntity);
	}

	// Eliminar
	/*
	 * public Empleado deleteEmpleado(Long idEmpleado) {
	 * empleadoRepository.deleteById(idEmpleado); return null; }
	 */

	// Eliminar Empleado y usuario
	@Transactional
	public Empleado deleteEmpleado(Long idEmpleado) {
		// Buscar el empleado por su ID
		Empleado empleado = empleadoRepository.findById(idEmpleado).orElse(null);
		if (empleado == null) {
			// Si el empelado no existe, retornar o manejar el caso según corresponda
			return empleado;
		}

		// Obtener el usuario asociado al cliente
		Usuario usuario = empleado.getUsuario();
		if (usuario != null) {
			// Eliminar los roles asignados al usuario
			usuario.getRol().clear(); // Eliminar todos los roles asignados al usuario
			// Guardar el usuario para que se actualicen las relaciones
			usuarioRepository.save(usuario);
			// Eliminar el usuario
			usuarioRepository.delete(usuario);
		}

		// Ahora se puede eliminar el empleado
		empleadoRepository.delete(empleado);
		return empleado;
	}

	
	// Modificar
	@Transactional
	public Empleado updateEmpleado(Long idEmpleado, EmpleadoDto empleado) {
		Empleado empleadoEntity = empleadoRepository.findById(idEmpleado)
				.orElseThrow(() -> new NoSuchElementException("Empleado no encontrado con el ID: " + idEmpleado));
		empleadoEntity.setNombre(empleado.nombre());
		empleadoEntity.setApellidoPaterno(empleado.apellidoPaterno());
		empleadoEntity.setApellidoMaterno(empleado.apellidoMaterno());
		empleadoEntity.setNss(empleado.nss());
		empleadoEntity.setCurp(empleado.curp());
		empleadoEntity.setRfc(empleado.rfc());
		empleadoEntity.setPuesto(empleado.puesto());
		empleadoEntity.setObservaciones(empleado.observaciones());
		return empleadoRepository.save(empleadoEntity);
	}
	
	//Reportes
	public List<Empleado> getAllEmpleados() {
        return empleadoRepository.findAll();
    }

    public byte[] generarPDF(List<Empleado> empleados) throws IOException {
        try {
            Document document = new Document();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);

            document.open();
            // Crear un párrafo con el título y centrarlo
            Paragraph titulo = new Paragraph("Listado de Empleados");
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            // Agregar salto de línea
            document.add(Chunk.NEWLINE);

            // Crear una tabla con 9 columnas
            PdfPTable table = new PdfPTable(9);
            table.setWidthPercentage(100); // Establecer el ancho de la tabla al 100% del ancho de la página

            // Agregar encabezados
            table.addCell("ID");
            table.addCell("Nombre");
            table.addCell("Apellido Paterno");
            table.addCell("Apellido Materno");
            table.addCell("NSS");
            table.addCell("CURP");
            table.addCell("RFC");
            table.addCell("Puesto");
            table.addCell("Usuario");

            // Agregar datos a la tabla
            for (Empleado empleado : empleados) {
                table.addCell(String.valueOf(empleado.getIdEmpleado()));
                table.addCell(empleado.getNombre());
                table.addCell(empleado.getApellidoPaterno());
                table.addCell(empleado.getApellidoMaterno());
                table.addCell(String.valueOf(empleado.getNss()));
                table.addCell(empleado.getCurp());
                table.addCell(empleado.getRfc());
                table.addCell(empleado.getPuesto());
                table.addCell(empleado.getUsuario().getUsername()); // Suponiendo que Usuario tiene getUsername()
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
