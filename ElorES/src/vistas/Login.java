package vistas;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.Color;

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

	/**
	 * Launch the application.
	 */
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

	/**
	 * Create the frame.
	 */
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
                //Solo acceder con profesor
            	Menu pantallaMenu = new Menu();
            	pantallaMenu.setVisible(true);
            	dispose();
            }
        });
        contentPane.add(btnLogin);
        
        // Botón para reconectar manualmente si falla
        btnReconectar = new JButton("Reconectar");
        btnReconectar.setBackground(new Color(65, 105, 225));
        btnReconectar.setForeground(Color.WHITE);
        btnReconectar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnReconectar.setBounds(333, 370, 179, 30);
        btnReconectar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Metodo para conectar con el servidor
            }
        });
        contentPane.add(btnReconectar);
        
        // Indicador de conexión
        lblEstado = new JLabel("Estado: Desconectado");
        lblEstado.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblEstado.setBounds(20, 520, 300, 20);
        contentPane.add(lblEstado);
        
        // Conectar cuando la ventana se haya mostrado completamente
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                // Conectar en un hilo separado para no bloquear la interfaz
                new Thread(() -> {
                    try {
                        Thread.sleep(500); // Espera medio segundo
                      //Metodo para conectar con el servidor
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }).start();
            }
        });
	}

}
