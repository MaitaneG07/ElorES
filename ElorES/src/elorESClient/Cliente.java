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

import elorESClient.modelo.message.Message;

public class Cliente {

	private Socket socket;
    private PrintWriter salida;
    private BufferedReader entrada;
    private String ipServidor;
    private int puerto;
    private Gson gson;
    
    public Cliente(String ipServidor, int puerto) {
        this.ipServidor = ipServidor;
        this.puerto = puerto;
     // Configurar Gson con adaptadores para LocalDateTime
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, 
                    (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context) -> 
                        context.serialize(src.toString()))
                .registerTypeAdapter(LocalDateTime.class, 
                    (JsonDeserializer<LocalDateTime>) (json, typeOfT, context) -> 
                        LocalDateTime.parse(json.getAsString()))
                .create();
    }
    
    /**
     * Conecta con el servidor
     * @return true si la conexión es exitosa
     */
    public boolean conectar() {
    	try {
//            socket = new Socket(ipServidor, puerto);
            socket = new Socket("localhost", puerto);
            socket.setSoTimeout(5000); 
            
            System.out.println("Conectado al servidor");
            
            salida = new PrintWriter(socket.getOutputStream(), true);
            entrada = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
            );
            
            return true;
            
        } catch (IOException e) {
            System.out.println("Error de conexión: " + e.getMessage());
            e.printStackTrace();
            return false;
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
            Message mensajeLogin = Message.crearLogin(usuario, password);
            
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
                    if(respuesta.getUsersList() != null) {
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
     * NUEVO: Obtiene alumnos de un profesor con filtros opcionales de ciclo y curso
     * @param idProfesor ID del profesor
     * @param cicloId ID del ciclo (null para todos)
     * @param curso Número de curso (null para todos)
     * @return Message con la lista de alumnos filtrados
     */
    public Message getStudentsByFilters(int idProfesor, Integer cicloId, Integer curso) {
        try {
            Message mensajeFiltrado = Message.createListStudentsByProfesorAndFilters(
                idProfesor, 
                cicloId, 
                curso
            );
            
            String jsonFiltrado = gson.toJson(mensajeFiltrado);
            
            StringBuilder logFiltros = new StringBuilder();
            logFiltros.append("[GET_ALUMNOS_FILTRADOS] Profesor: ").append(idProfesor);
            
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
                        System.out.println("[DATOS] Se recibieron " + 
                            respuesta.getUsersList().size() + " alumnos con los filtros aplicados.");
                    } else {
                        System.out.println("[DATOS] No se recibieron alumnos.");
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
     * NUEVO: Obtiene profesores filtros opcionales de ciclo y curso
     * @param cicloId ID del ciclo (null para todos)
     * @param curso Número de curso (null para todos)
     * @return Message con la lista de profesores filtrados
     */
    public Message getTeachersByFilters(Integer cicloId, Integer curso) {
        try {
            Message mensajeFiltrado = Message.createListTeachersByFilters(
                cicloId, 
                curso
            );
            
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
                        System.out.println("[DATOS] Se recibieron " + 
                            respuesta.getUsersList().size() + " profesores con los filtros aplicados.");
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
     * Obtiene el horario de un profesor
     * @param idProfesor
     * @return horario del profesor
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
                    if(respuesta.getUsersList() != null) {
                    	System.out.println("[DATOS] Se recibió el mensaje");
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
     * Obtiene todos los profesores
     * @return lista de profesores
     */
    public Message getAllTeachers() {
    	try {
            Message mensajeAllTeachers = Message.createListTeachers();
            
            String jsonAllTeachers = gson.toJson(mensajeAllTeachers);
            System.out.println("[GET_PROFESORES] Solicitando profesores.");
            
            enviarMensaje(jsonAllTeachers);
            
            String respuestaJson = recibirMensaje();
            
            if (respuestaJson != null) {
                Message respuesta = gson.fromJson(respuestaJson, Message.class);
                if ("OK".equals(respuesta.getEstado())) {
                    System.out.println("[EXITOSO] " + respuesta.getMensaje());
                    if(respuesta.getUsersList() != null) {
                    	System.out.println("[DATOS] Se recibieron " + respuesta.getUsersList().size() + " profesores.");
                    }
                } else {
                    System.out.println("[FALLIDO] " + respuesta.getMensaje());
                }
                
                return respuesta;
            }
            
        } catch (Exception e) {
            System.err.println("[ERROR] Error obteniendo profesores: " + e.getMessage());
            e.printStackTrace();
        }
    	return null;
    }
    
    /**
     * Obtiene las reuniones de un profesor
     * @param idProfesor
     * @return listado de reuniones
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
     * Cierra la conexión
     */
    public void desconectar() {
        try {
            if (socket != null) socket.close();
            if (salida != null) salida.close();
            if (entrada != null) entrada.close();
            System.out.println("Desconectado del servidor");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public boolean estaConectado() {
        return socket != null && socket.isConnected() && !socket.isClosed();
    }
    
}