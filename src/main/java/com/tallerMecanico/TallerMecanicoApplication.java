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

			Boolean demo = true; 
 
				executeSqlScript("data/common/catalogos.sql");
				loadCsvDataToMarcaTable("data/common/marcaAutos.csv");
				loadCsvDataToModeloTable("data/common/modelosAutos.csv");
				loadCsvDataToDepartamentosTable("data/common/departamentos.csv");
				loadCsvDataToEstatusServicioTable("data/common/estatusServicio.csv");
				executeSqlScript("data/common/empleados.sql");

				if (demo){

					executeSqlScript("data/demo/clientes.sql");
					loadCsvDataToVehiculosTable("data/demo/vehiculos.csv");
					loadCsvDataToOrdenesTable("data/demo/ordenes.csv");

				}
		
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

					
					// Verificar si el registro ya existe en la tabla
					String searchQuery = "SELECT COUNT(*) FROM estatus_servicio WHERE id_estatus_servicio = " + idEstatusServicio;
					int count = jdbcTemplate.queryForObject(searchQuery, Integer.class);
					
					// Si no existe, insertar el registro en la tabla
					if (count == 0) {
						String insertQuery = "INSERT INTO estatus_servicio (id_estatus_servicio,  estatus_servicio, departamento_id) VALUES (" + idEstatusServicio
						+ ", '" + estatusServicio + "', "  + departamentoId + ")";
						System.out.println("- "+idEstatusServicio + " " + estatusServicio + " " + departamentoId);
						jdbcTemplate.update(insertQuery);
					}
				}
			}

			

			reader.close();
		} catch (IOException e) {
			throw new RuntimeException("Error al cargar datos desde el archivo CSV a la tabla 'estatusServicio'", e);
		}
	}


	// Carga tabla de Vehiculos por medio de archivo CSV
	private void loadCsvDataToVehiculosTable(String csvFileName) throws SQLException {
		try {
			Resource resource = resourceLoader.getResource("classpath:" + csvFileName);
			InputStream inputStream = resource.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

			// Omitir la primera línea que contiene los encabezados
			String line = reader.readLine(); // Leer la primera línea y descartarla

			while ((line = reader.readLine()) != null) {
				String[] data = line.split(",");
				if (data.length == 9) {
					long idVehiculo = Long.parseLong(data[0].trim());
					String anio_modelo = 	data[1].trim();
					String color = 			data[2].trim();
					String imagen = 		data[3].trim();
					String matricula = 		data[4].trim();
					String vin = 			data[5].trim();
					String cliente_id = 	data[6].trim();
					String modelo_id = 		data[7].trim();
					String tipo_motor_id = 	data[8].trim();

					// Verificar si el registro ya existe en la tabla
					String searchQuery = "SELECT COUNT(*) FROM vehiculos WHERE id_vehiculo = " + idVehiculo;
					int count = jdbcTemplate.queryForObject(searchQuery, Integer.class);

					// Si no existe, insertar el registro en la tabla
					if (count == 0) {
						String insertQuery = "INSERT INTO vehiculos" 
						+ "(id_vehiculo, anio_modelo, color, imagen, matricula, vin, cliente_id, modelo_id, tipo_motor_id) VALUES"
						+ " (" + idVehiculo + ", " + anio_modelo + ", '" + color + "', '" + imagen + "', '" + matricula + "', '" 
						+ vin + "', " + cliente_id + ", " + modelo_id + ", " + tipo_motor_id
								+ ")";
						System.out.println(insertQuery);
						jdbcTemplate.update(insertQuery);
					}
				}
			}

			reader.close();
		} catch (IOException e) {
			throw new RuntimeException("Error al cargar datos desde el archivo CSV a la tabla 'marcas'", e);
		}
	}


	// Carga tabla de Vehiculos por medio de archivo CSV
	private void loadCsvDataToOrdenesTable(String csvFileName) throws SQLException {
		try {
			Resource resource = resourceLoader.getResource("classpath:" + csvFileName);
			InputStream inputStream = resource.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

			// Omitir la primera línea que contiene los encabezados
			String line = reader.readLine(); // Leer la primera línea y descartarla

			while ((line = reader.readLine()) != null) {
				String[] data = line.split(",");
				if (data.length == 9) {
					long idOrdenServicio = Long.parseLong(data[0].trim());
					String comentarios = 	data[1].trim();
					String falla = 			data[2].trim();
					String fecha_orden = 		data[3].trim();
					String kilometraje = 		data[4].trim();
					String observaciones = 			data[5].trim();
					String empleado_id = 	data[6].trim();
					String estatus_servicio_id = 		data[7].trim();
					String vehiculo_id = 	data[8].trim();

					// Verificar si el registro ya existe en la tabla
					String searchQuery = "SELECT COUNT(*) FROM ordenes_servicios WHERE id_orden_servicio = " + idOrdenServicio;
					int count = jdbcTemplate.queryForObject(searchQuery, Integer.class);

					// Si no existe, insertar el registro en la tabla
					if (count == 0) {
						String insertQuery = "INSERT INTO ordenes_servicios" 
						+ "(id_orden_servicio, comentarios, falla, fecha_orden, kilometraje, observaciones, empleado_id, estatus_servicio_id, vehiculo_id) VALUES"
						+ " (" + idOrdenServicio + ", '" + comentarios + "', '" + falla + "', '" + fecha_orden + "', '" + kilometraje + "', '" 
						+ observaciones + "', " + empleado_id + ", " + estatus_servicio_id + ", " + vehiculo_id
								+ ")";
						System.out.println(insertQuery);
						jdbcTemplate.update(insertQuery);
					}
				}
			}

			reader.close();
		} catch (IOException e) {
			throw new RuntimeException("Error al cargar datos desde el archivo CSV a la tabla 'marcas'", e);
		}
	}

}
