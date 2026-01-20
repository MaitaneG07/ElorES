package elorESClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;

import com.google.gson.Gson;

import elorESClient.modelo.entities.message.Message;

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
        this.gson = new Gson();
    }
    
    /**
     * Conecta con el servidor
     * @return true si la conexión es exitosa
     */
    public boolean conectar() {
    	try {
            socket = new Socket(ipServidor, puerto);
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