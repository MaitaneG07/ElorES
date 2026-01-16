package elorESClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Cliente {

	private Socket socket;
    private PrintWriter salida;
    private BufferedReader entrada;
    private String ipServidor;
    private int puerto;
    
    public Cliente(String ipServidor, int puerto) {
        this.ipServidor = ipServidor;
        this.puerto = puerto;
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
            
            // Leer el mensaje de bienvenida del servidor
            String bienvenida = entrada.readLine();
            System.out.println("Servidor dice: " + bienvenida);
            
            return true;
            
        } catch (IOException e) {
            System.out.println("Error de conexión: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Envía un mensaje al servidor
     */
    public void enviarMensaje(String mensaje) {
        if (salida != null) {
            salida.println(mensaje);
            salida.flush(); 
            System.out.println("Mensaje enviado: " + mensaje);
        }
    }
    
    /**
     * Recibe un mensaje del servidor con timeout
     */
    public String recibirMensaje() {
        try {
            if (entrada != null) {
                String respuesta = entrada.readLine();
                System.out.println("Respuesta recibida: " + respuesta);
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
     * Envía un mensaje y espera respuesta
     */
    public String enviarYRecibir(String mensaje) {
        enviarMensaje(mensaje);
        return recibirMensaje();
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
