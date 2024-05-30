package com.tallerMecanico;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;


@SpringBootApplication
public class TallerMecanicoApplication implements ApplicationRunner{

	
	private final DataSource dataSource;
	private final JdbcTemplate jdbcTemplate;


	@Autowired
	public TallerMecanicoApplication(DataSource dataSource, JdbcTemplate jdbcTemplate) {
		this.dataSource = dataSource;
		this.jdbcTemplate = jdbcTemplate;
	}

	@Autowired
	private ResourceLoader resourceLoader;
	
	public static void main(String[] args) {
        SpringApplication.run(TallerMecanicoApplication.class, args);
    }

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// Ejecutar el script SQL
				executeSqlScript("data.sql");
				loadCsvDataToMarcaTable("marcaAutos.csv");
				loadCsvDataToModeloTable("modelosAutos.csv");
				loadCsvDataToDepartamentosTable("departamentos.csv");
				loadCsvDataToEstatusServicioTable("estatusServicio.csv");

				executeSqlScript("data2.sql");
		
	}
	
	

	private void executeSqlScript(String scriptFileName) throws SQLException {
		try (Connection connection = dataSource.getConnection()) {
			// Leer el script SQL desde el archivo
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(getClass().getClassLoader().getResourceAsStream(scriptFileName)));
			StringBuilder scriptContent = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				scriptContent.append(line).append("\n");
			}
			// Ejecutar el script SQL
			// System.out.println("--->>>>" + scriptContent.toString());
			jdbcTemplate.execute(scriptContent.toString());
		} catch (IOException e) {
			throw new RuntimeException("Error al leer el archivo " + scriptFileName, e);
		}
	}

	// Carga tabla de marcas por medio de archivo CSV
	private void loadCsvDataToMarcaTable(String csvFileName) throws SQLException {
		try {
			Resource resource = resourceLoader.getResource("classpath:" + csvFileName);
			InputStream inputStream = resource.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

			// Omitir la primera línea que contiene los encabezados
			String line = reader.readLine(); // Leer la primera línea y descartarla

			while ((line = reader.readLine()) != null) {
				String[] data = line.split(",");
				if (data.length == 2) {
					long idMarca = Long.parseLong(data[0].trim());
					String marca = data[1].trim();

					// Verificar si el registro ya existe en la tabla
					String searchQuery = "SELECT COUNT(*) FROM marcas WHERE id_marca = " + idMarca;
					int count = jdbcTemplate.queryForObject(searchQuery, Integer.class);

					// Si no existe, insertar el registro en la tabla
					if (count == 0) {
						String insertQuery = "INSERT INTO marcas (id_marca, marca) VALUES (" + idMarca + ", '" + marca
								+ "')";
						jdbcTemplate.update(insertQuery);
					}
				}
			}

			reader.close();
		} catch (IOException e) {
			throw new RuntimeException("Error al cargar datos desde el archivo CSV a la tabla 'marcas'", e);
		}
	}

	// Carga tabla de modelos por medio de archivo CSV
	private void loadCsvDataToModeloTable(String csvFileName) throws SQLException {
		try {
			Resource resource = resourceLoader.getResource("classpath:" + csvFileName);
			InputStream inputStream = resource.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

			// Omitir la primera línea que contiene los encabezados
			String line = reader.readLine(); // Leer la primera línea y descartarla

			while ((line = reader.readLine()) != null) {
				String[] data = line.split(",");
				if (data.length == 3) { // Asegurarse que hay 3 elementos
					long idModelo = Long.parseLong(data[0].trim());
					long marcaId = Long.parseLong(data[1].trim()); // Verificar que este sea el índice correcto
					String modelo = data[2].trim();

					// Verificar si el registro ya existe en la tabla
					String searchQuery = "SELECT COUNT(*) FROM modelos WHERE id_modelo = " + idModelo;
					int count = jdbcTemplate.queryForObject(searchQuery, Integer.class);

					// Si no existe, insertar el registro en la tabla
					if (count == 0) {
						String insertQuery = "INSERT INTO modelos (id_modelo, marca_id, modelo) VALUES (" + idModelo
								+ ", " + marcaId + ", '" + modelo + "')";
						jdbcTemplate.update(insertQuery);
					}
				}
			}

			reader.close();
		} catch (IOException e) {
			throw new RuntimeException("Error al cargar datos desde el archivo CSV a la tabla 'modelos'", e);
		}
	}
    

	// Carga tabla de departamentos por medio de archivo CSV
	private void loadCsvDataToDepartamentosTable(String csvFileName) throws SQLException {
		try {
			Resource resource = resourceLoader.getResource("classpath:" + csvFileName);
			InputStream inputStream = resource.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

			// Omitir la primera línea que contiene los encabezados
			String line = reader.readLine(); // Leer la primera línea y descartarla

			while ((line = reader.readLine()) != null) {
				String[] data = line.split(",");
				if (data.length == 2) {
					long idDepartamento = Long.parseLong(data[0].trim());
					String departamento = data[1].trim();

					// Verificar si el registro ya existe en la tabla
					String searchQuery = "SELECT COUNT(*) FROM departamentos WHERE id_departamento = " + idDepartamento;
					int count = jdbcTemplate.queryForObject(searchQuery, Integer.class);

					// Si no existe, insertar el registro en la tabla
					if (count == 0) {
						String insertQuery = "INSERT INTO departamentos (id_departamento, departamento) VALUES (" + idDepartamento + ", '" + departamento
								+ "')";
						jdbcTemplate.update(insertQuery);
					}
				}
			}

			reader.close();
		} catch (IOException e) {
			throw new RuntimeException("Error al cargar datos desde el archivo CSV a la tabla 'marcas'", e);
		}
	}

	// Carga tabla de estatusServicio por medio de archivo CSV
	private void loadCsvDataToEstatusServicioTable(String csvFileName) throws SQLException {
		try {
			Resource resource = resourceLoader.getResource("classpath:" + csvFileName);
			InputStream inputStream = resource.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

			// Omitir la primera línea que contiene los encabezados
			String line = reader.readLine(); // Leer la primera línea y descartarla

			while ((line = reader.readLine()) != null) {
				String[] data = line.split(",");
				if (data.length == 3) { // Asegurarse que hay 3 elementos
					long idEstatusServicio = Long.parseLong(data[0].trim());
					String estatusServicio = data[1].trim();
					long departamentoId = Long.parseLong(data[2].trim()); // Verificar que este sea el índice correcto

					System.out.println("xxxxxx:  "+idEstatusServicio + " " + estatusServicio + " " + departamentoId);

					// Verificar si el registro ya existe en la tabla
					String searchQuery = "SELECT COUNT(*) FROM estatus_servicio WHERE id_estatus_servicio = " + idEstatusServicio;
					int count = jdbcTemplate.queryForObject(searchQuery, Integer.class);

					// Si no existe, insertar el registro en la tabla
					if (count == 0) {
						String insertQuery = "INSERT INTO estatus_servicio (id_estatus_servicio,  estatus_servicio, departamento_id) VALUES (" + idEstatusServicio
								+ ", '" + estatusServicio + "', "  + departamentoId + ")";
						jdbcTemplate.update(insertQuery);
					}
				}
			}

			reader.close();
		} catch (IOException e) {
			throw new RuntimeException("Error al cargar datos desde el archivo CSV a la tabla 'estatusServicio'", e);
		}
	}


}
