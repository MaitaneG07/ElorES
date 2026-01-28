package elorESClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.time.LocalDateTime;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

import elorESClient.modelo.entities.Reuniones;
import elorESClient.modelo.message.Message;
import elorESClient.modelo.util.CryptoClient;

public class Cliente {

	private Socket socket;
	private PrintWriter salida;
	private BufferedReader entrada;
	@SuppressWarnings("unused")
	private String ipServidor;
	private int puerto;
	private Gson gson;

	public Cliente(String ipServidor, int puerto) {
		this.ipServidor = ipServidor;
		this.puerto = puerto;
		// Configurar Gson con adaptadores para LocalDateTime
		this.gson = new GsonBuilder()
				.registerTypeAdapter(LocalDateTime.class,
						(JsonSerializer<LocalDateTime>) (src, typeOfSrc, context) -> context.serialize(src.toString()))
				.registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, typeOfT,
						context) -> LocalDateTime.parse(json.getAsString()))
				.create();
	}

	/**
	 * Conecta con el servidor
	 * 
	 * @return true si la conexión es exitosa
	 */
	public boolean conectar() {
		try {
            socket = new Socket(ipServidor, puerto);
//			socket = new Socket("localhost", puerto);
			socket.setSoTimeout(5000);

			System.out.println("Conectado al servidor");

			salida = new PrintWriter(socket.getOutputStream(), true);
			entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			recibirClavePublica();
			
			return true;

		} catch (IOException e) {
			System.out.println("Error de conexión: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Recibe y configura la clave pública RSA del servidor
	 */
	private void recibirClavePublica() {
	    try {
	        String mensajeJson = recibirMensaje();
	        
	        if (mensajeJson != null) {
	            Message mensaje = gson.fromJson(mensajeJson, Message.class);
	            
	            if ("PUBLIC_KEY".equals(mensaje.getTipo())) {
	                String publicKeyBase64 = mensaje.getMensaje();
	                CryptoClient.setServerPublicKey(publicKeyBase64);
	                System.out.println("[CLIENTE] Clave pública del servidor recibida y configurada");
	            } else {
	                System.err.println("[CLIENTE ERROR] Se esperaba PUBLIC_KEY pero se recibió: " + mensaje.getTipo());
	            }
	        }
	    } catch (Exception e) {
	        System.err.println("[CLIENTE ERROR] Error al recibir clave pública: " + e.getMessage());
	        e.printStackTrace();
	    }
	}

	/**
	 * Envía un mensaje JSON al servidor
	 */
	public void enviarMensaje(String mensajeJson) {
		if (salida != null) {
			salida.println(mensajeJson);
			salida.flush();
			System.out.println("[ENVIADO] " + mensajeJson);
		}
	}

	/**
	 * Recibe un mensaje JSON del servidor con timeout
	 */
	public String recibirMensaje() {
		try {
			if (entrada != null) {
				String respuesta = entrada.readLine();
				System.out.println("[RECIBIDO] " + respuesta);
				return respuesta;
			}
		} catch (SocketTimeoutException e) {
			System.out.println("Timeout esperando respuesta del servidor");
		} catch (IOException e) {
			System.out.println("Error recibiendo mensaje: " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Realiza el login y devuelve el objeto Message con la respuesta
	 */
	public Message login(String usuario, String password) {
	    try {
	        if (!CryptoClient.isKeyConfigured()) {
	            System.err.println("[LOGIN ERROR] La clave pública del servidor no está configurada");
	            Message error = new Message();
	            error.setEstado("ERROR");
	            error.setMensaje("Error de seguridad: no se puede encriptar");
	            return error;
	        }
	        
	        String passwordEncriptada = CryptoClient.encriptar(password);
	        
	        if (passwordEncriptada == null) {
	            System.err.println("[LOGIN ERROR] No se pudo encriptar la contraseña");
	            Message error = new Message();
	            error.setEstado("ERROR");
	            error.setMensaje("Error al encriptar credenciales");
	            return error;
	        }
	        
	        System.out.println("[LOGIN] Contraseña encriptada correctamente");
	        
	        Message mensajeLogin = Message.crearLogin(usuario, passwordEncriptada);
	        
	        String jsonLogin = gson.toJson(mensajeLogin);
	        System.out.println("[LOGIN] Enviando credenciales...");
	        
	        enviarMensaje(jsonLogin);
	        
	        String respuestaJson = recibirMensaje();
	        
	        if (respuestaJson != null) {
	            Message respuesta = gson.fromJson(respuestaJson, Message.class);
	            if ("OK".equals(respuesta.getEstado())) {
	                System.out.println("[LOGIN EXITOSO] " + respuesta.getMensaje());
	            } else {
	                System.out.println("[LOGIN FALLIDO] " + respuesta.getMensaje());
	            }
	            
	            return respuesta;
	        }
	        
	    } catch (Exception e) {
	        System.err.println("[ERROR] Error en login: " + e.getMessage());
	        e.printStackTrace();
	    }
	    
	    return null;
	}

	/**
	 * Realiza la búsqueda de todos los usuarios
	 * 
	 * @param idProfesor
	 * @return Lista de usuarios
	 */
	public Message getAllStudentsById(int idProfesor) {
		try {
			Message mensajeAllUsers = Message.createListStudentsById(idProfesor);

			String jsonAllUsers = gson.toJson(mensajeAllUsers);
			System.out.println("[GET_ALUMNOS_BY_PROFESOR] Solicitando alumnos del profesor: " + idProfesor);

			enviarMensaje(jsonAllUsers);

			String respuestaJson = recibirMensaje();

			if (respuestaJson != null) {
				Message respuesta = gson.fromJson(respuestaJson, Message.class);
				if ("OK".equals(respuesta.getEstado())) {
					System.out.println("[EXITOSO] " + respuesta.getMensaje());
					if (respuesta.getUsersList() != null) {
						System.out.println("[DATOS] Se recibieron " + respuesta.getUsersList().size() + " alumnos.");
					}
				} else {
					System.out.println("[FALLIDO] " + respuesta.getMensaje());
				}

				return respuesta;
			}

		} catch (Exception e) {
			System.err.println("[ERROR] Error obteniendo alumnos: " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Obtiene todos los profesores
	 * 
	 * @return lista de profesores
	 */
	public Message getAllTeachers() {
		try {
			Message mensaje = Message.createListTeachers();
			String json = gson.toJson(mensaje);

			System.out.println("[GET_TEACHERS] Solicitando todos los profesores");

			enviarMensaje(json);
			String respuestaJson = recibirMensaje();

			if (respuestaJson != null) {
				Message respuesta = gson.fromJson(respuestaJson, Message.class);
				System.out.println("[RESPUESTA] " + respuesta.getMensaje());
				return respuesta;
			}

		} catch (Exception e) {
			System.err.println("[ERROR] " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * NUEVO: Obtiene alumnos de un profesor con filtros opcionales de ciclo y curso
	 * 
	 * @param idProfesor ID del profesor
	 * @param cicloId    ID del ciclo (null para todos)
	 * @param curso      Número de curso (null para todos)
	 * @return Message con la lista de alumnos filtrados
	 */
	public Message getStudentsByFilters(int idProfesor, Integer cicloId, Integer curso) {
		try {
			Message mensajeFilterStudents = Message.createListStudentsByProfesorAndFilters(idProfesor, cicloId, curso);

			String jsonFilterStudents = gson.toJson(mensajeFilterStudents);
			System.out.println("[GET_ALUMNOS_FILTRADOS] Solicitando alumnos del profesor: " + idProfesor
					+ " filtrados por: " + cicloId + " y " + curso);

			// Log detallado de los filtros
			StringBuilder logFiltros = new StringBuilder();
			logFiltros.append("[GET_ALUMNOS_FILTRADOS] Profesor: ").append(idProfesor);

			enviarMensaje(jsonFilterStudents);

			String respuestaJson = recibirMensaje();

			if (respuestaJson != null) {
				Message respuesta = gson.fromJson(respuestaJson, Message.class);
				if ("OK".equals(respuesta.getEstado())) {
					System.out.println("[EXITOSO] " + respuesta.getMensaje());
					if (respuesta.getUsersList() != null) {
						System.out.println("[DATOS] Se recibieron " + respuesta.getUsersList().size() + " alumnos.");
					}
				} else {
					System.out.println("[FALLIDO] " + respuesta.getMensaje());
				}

				return respuesta;
			}

		} catch (Exception e) {
			e.printStackTrace();
			Message error = new Message();
			error.setEstado("ERROR");
			error.setMensaje("Error al obtener alumnos filtrados: " + e.getMessage());
			return error;
		}
		return null;
	}

	/**
	 * NUEVO: Obtiene profesores filtros opcionales de ciclo y curso
	 * 
	 * @param cicloId ID del ciclo (null para todos)
	 * @param curso   Número de curso (null para todos)
	 * @return Message con la lista de profesores filtrados
	 */
	public Message getTeachersByFilters(Integer cicloId, Integer curso) {
		try {
			Message mensajeFiltrado = Message.createListTeachersByFilters(cicloId, curso);

			String jsonFiltrado = gson.toJson(mensajeFiltrado);

			StringBuilder logFiltros = new StringBuilder();
			logFiltros.append("[GET_PROFESORES_FILTRADOS]");

			if (cicloId != null) {
				logFiltros.append(", Ciclo: ").append(cicloId);
			} else {
				logFiltros.append(", Ciclo: TODOS");
			}

			if (curso != null) {
				logFiltros.append(", Curso: ").append(curso);
			} else {
				logFiltros.append(", Curso: TODOS");
			}

			System.out.println(logFiltros.toString());

			enviarMensaje(jsonFiltrado);

			String respuestaJson = recibirMensaje();

			if (respuestaJson != null) {
				Message respuesta = gson.fromJson(respuestaJson, Message.class);

				if ("OK".equals(respuesta.getEstado())) {
					System.out.println("[EXITOSO] " + respuesta.getMensaje());
					if (respuesta.getUsersList() != null) {
						System.out.println("[DATOS] Se recibieron " + respuesta.getUsersList().size()
								+ " profesores con los filtros aplicados.");
					} else {
						System.out.println("[DATOS] No se recibieron profesores.");
					}
				} else {
					System.out.println("[FALLIDO] " + respuesta.getMensaje());
				}

				return respuesta;
			}

		} catch (Exception e) {
			System.err.println("[ERROR] Error obteniendo alumnos filtrados: " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Obtiene reuniones de un profesor
	 * 
	 * @param idProfesor
	 * @return lista de reuniones
	 */
	public Message getReunionesProfesor(int idProfesor) {
		try {
			Message mensaje = Message.createGetReunionesProfesor(idProfesor);
			String json = gson.toJson(mensaje);

			System.out.println("[GET_REUNIONES] Pidiendo reuniones del profesor: " + idProfesor);

			enviarMensaje(json);
			String respuestaJson = recibirMensaje();

			if (respuestaJson != null) {
				Message respuesta = gson.fromJson(respuestaJson, Message.class);
				System.out.println("[RESPUESTA] " + respuesta.getMensaje());
				return respuesta;
			}

		} catch (Exception e) {
			System.err.println("[ERROR] " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Obtiene el horario de un profesor
	 * 
	 * @param idProfesor
	 * @return lista con el horario de un profesor
	 */
	public Message getHorario(int idProfesor) {
		try {
			Message mensajeHorario = Message.createHorario(idProfesor);

			String jsonHorario = gson.toJson(mensajeHorario);
			System.out.println("[GET_HORARIO_PROFESOR] Solicitando el horario del profesor: " + idProfesor);

			enviarMensaje(jsonHorario);

			String respuestaJson = recibirMensaje();

			if (respuestaJson != null) {
				Message respuesta = gson.fromJson(respuestaJson, Message.class);
				if ("OK".equals(respuesta.getEstado())) {
					System.out.println("[EXITOSO] " + respuesta.getMensaje());
					if (respuesta.getHorarioList() != null) {
						System.out.println("[DATOS] Se recibieron " + respuesta.getHorarioList().size() + " horarios");
					}
				} else {
					System.out.println("[FALLIDO] " + respuesta.getMensaje());
				}

				return respuesta;
			}

		} catch (Exception e) {
			System.err.println("[ERROR] Error obteniendo horario: " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Actualiza el estado de una reunión en el servidor
	 * 
	 * @param reunion La reunión con los datos actualizados
	 * @return Message con el resultado de la operación
	 */
	public Message actualizarReunion(Reuniones reunion) {
		try {
			Message mensaje = Message.createActualizarReunion(reunion);

			String json = gson.toJson(mensaje);
			System.out.println("[ACTUALIZAR_REUNION] Enviando actualización de reunión ID: " + reunion.getIdReunion()
					+ " - Nuevo estado: " + reunion.getEstado());

			enviarMensaje(json);

			String respuestaJson = recibirMensaje();

			if (respuestaJson != null) {
				Message respuesta = gson.fromJson(respuestaJson, Message.class);

				if ("OK".equals(respuesta.getEstado())) {
					System.out.println("[EXITOSO] " + respuesta.getMensaje());
				} else {
					System.out.println("[FALLIDO] " + respuesta.getMensaje());
				}

				return respuesta;
			}

		} catch (Exception e) {
			System.err.println("[ERROR] Error al actualizar reunión: " + e.getMessage());
			e.printStackTrace();

			Message error = new Message();
			error.setEstado("ERROR");
			error.setMensaje("Error de comunicación al actualizar reunión: " + e.getMessage());
			return error;
		}

		return null;
	}

	/**
	 * Cierra la conexión
	 */
	public void desconectar() {
		try {
			if (socket != null)
				socket.close();
			if (salida != null)
				salida.close();
			if (entrada != null)
				entrada.close();
			System.out.println("Desconectado del servidor");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean estaConectado() {
		return socket != null && socket.isConnected() && !socket.isClosed();
	}

}