package elorESClient.vistas;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import elorESClient.Cliente;
import elorESClient.modelo.message.Message;

public class Login extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JLabel lblEstado;
    private JPasswordField txtPassword;
    private JTextField txtUsuario;
    private JLabel lblTitulo;
    private JLabel lblUsuario;
    private JLabel lblPassword;
    private JButton btnLogin;
    private JButton btnReconectar;
    private Cliente cliente;
	private Message respuesta;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Login frame = new Login();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Login() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 855, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        lblTitulo = new JLabel("ELORRIETA");
        lblTitulo.setForeground(new Color(65, 105, 225));
        lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 36));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(216, 35, 408, 122);
        contentPane.add(lblTitulo);

        lblUsuario = new JLabel("Usuario:");
        lblUsuario.setForeground(new Color(65, 105, 225));
        lblUsuario.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblUsuario.setBounds(250, 168, 70, 20);
        contentPane.add(lblUsuario);

        txtUsuario = new JTextField();
        txtUsuario.setBounds(333, 168, 179, 25);
        contentPane.add(txtUsuario);
        txtUsuario.setColumns(10);

        lblPassword = new JLabel("Contraseña:");
        lblPassword.setForeground(new Color(65, 105, 225));
        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblPassword.setBounds(230, 254, 90, 20);
        contentPane.add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(333, 254, 179, 25);
        contentPane.add(txtPassword);

        btnLogin = new JButton("Iniciar Sesión");
        btnLogin.setBackground(new Color(65, 105, 225));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnLogin.setBounds(333, 320, 179, 35);
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                iniciarSesion();
            }
        });
        contentPane.add(btnLogin);
        
        btnReconectar = new JButton("Reconectar");
        btnReconectar.setBackground(new Color(65, 105, 225));
        btnReconectar.setForeground(Color.WHITE);
        btnReconectar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnReconectar.setBounds(333, 370, 179, 30);
        btnReconectar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                conectarAlServidor(); // CORREGIDO
            }
        });
        contentPane.add(btnReconectar);
        
        lblEstado = new JLabel("Estado: Desconectado");
        lblEstado.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblEstado.setBounds(20, 520, 300, 20);
        contentPane.add(lblEstado);

        JLabel lblLogo = new JLabel();
        lblLogo.setBounds(10, 11, 131, 107);

        ImageIcon icon = new ImageIcon(getClass().getResource("/elorESClient/images/logoElorrieta.png"));
        Image img = icon.getImage().getScaledInstance(131, 107, Image.SCALE_SMOOTH);
        lblLogo.setIcon(new ImageIcon(img));

        contentPane.add(lblLogo);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                new Thread(() -> {
                    try {
                        Thread.sleep(500);
                        conectarAlServidor();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }).start();
            }
        });
    }
    
    /**
     * Conecta con el servidor
     */
    private void conectarAlServidor() {
        lblEstado.setText("Estado: Conectando...");
        
//        String ipServidor = "10.5.104.31";
        String ipServidor = "localhost";
        int puerto = 8080;
        
        new Thread(() -> {
            cliente = new Cliente(ipServidor, puerto);
            
            if (cliente.conectar()) {
                System.out.println("Conexión establecida con el servidor");
                SwingUtilities.invokeLater(() -> {
                    lblEstado.setText("Estado: Conectado");
                });
            } else {
                SwingUtilities.invokeLater(() -> {
                    lblEstado.setText("Estado: Error de conexión");
                    JOptionPane.showMessageDialog(Login.this,
                        "No se pudo conectar con el servidor.",
                        "Error de Conexión",
                        JOptionPane.ERROR_MESSAGE);
                });
            }
        }).start();
    }

    /**
     * Maneja el inicio de sesión
     */
    protected void iniciarSesion() {
        if (cliente == null || !cliente.estaConectado()) {
            JOptionPane.showMessageDialog(this,
                "No hay conexión, intente reconectar.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String usuario = txtUsuario.getText();
        String password = new String(txtPassword.getPassword());
        
        if (usuario.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Por favor complete todos los campos",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        new Thread(() -> {
            try {
                System.out.println("Enviando credenciales al servidor...");
                
                respuesta = cliente.login(usuario, password);
                
                SwingUtilities.invokeLater(() -> {
                    
                	if (respuesta != null && "OK".equals(respuesta.getEstado())) {
                		System.out.println("La respuesta del servidor es: " + respuesta.getEstado());
                		System.out.println("Datos del usuario: " + respuesta.getUserData());
                		
                        JOptionPane.showMessageDialog(this,
                            "Inicio de sesión exitoso",
                            "Éxito",
                            JOptionPane.INFORMATION_MESSAGE);
                        abrirMenu();
                        
                    } else if (respuesta != null && "ERROR".equals(respuesta.getEstado())) {
                        JOptionPane.showMessageDialog(this,
                            respuesta.getMensaje(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this,
                            "No se recibió respuesta del servidor",
                            "Error de Comunicación",
                            JOptionPane.ERROR_MESSAGE);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                SwingUtilities.invokeLater(() -> {
                    
                    JOptionPane.showMessageDialog(this,
                        "Error al comunicarse con el servidor",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                });
            }
        }).start();
    }

    private void abrirMenu() {
        Menu pantallaMenu = new Menu(respuesta.getUserData(), cliente);
        pantallaMenu.setVisible(true);
        dispose();
    }
}