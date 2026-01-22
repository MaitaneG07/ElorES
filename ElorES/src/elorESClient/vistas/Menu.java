package elorESClient.vistas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import elorESClient.Cliente;
import elorESClient.modelo.entities.Users;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Menu extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnPerfil;
	private JButton btnConsultarAlumnos;
	private JButton btnHorario;
	private JButton btnOtrosHorarios;
	private JButton btnReuniones;
	private int idProfesor;
	private Users user;
	private Cliente cliente;
	
	/**
	 * Create the frame.
	 * @param cliente 
	 * @param users 
	 */
	public Menu(Users user, Cliente cliente) {
		
		this.user = user;
		this.cliente = cliente;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 855, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
		
        JLabel lblLogo = new JLabel();
        lblLogo.setBounds(10, 11, 131, 107);

        ImageIcon icon = new ImageIcon(getClass().getResource("/elorESClient/images/logoElorrieta.png"));
        Image img = icon.getImage().getScaledInstance(131, 107, Image.SCALE_SMOOTH);
        lblLogo.setIcon(new ImageIcon(img));

        contentPane.add(lblLogo);
        
        btnPerfil = new JButton("Consultar mi Perfil");
        btnPerfil.setBackground(new Color(65, 105, 225));
        btnPerfil.setForeground(Color.WHITE);
        btnPerfil.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnPerfil.setBounds(333, 148, 201, 35);
        btnPerfil.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Perfil pantallaPerfil = new Perfil(user, cliente);
                pantallaPerfil.setVisible(true);
                dispose();
            }
        });
        contentPane.add(btnPerfil);
        
        btnConsultarAlumnos = new JButton("Consultar Alumnos");
        btnConsultarAlumnos.setBackground(new Color(65, 105, 225));
        btnConsultarAlumnos.setForeground(Color.WHITE);
        btnConsultarAlumnos.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnConsultarAlumnos.setBounds(333, 212, 201, 35);
        btnConsultarAlumnos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ConsultaAlumnos pantallaConsultaAlumnos = new ConsultaAlumnos(user, cliente);
                pantallaConsultaAlumnos.setVisible(true);
                dispose();
            }
        });
        contentPane.add(btnConsultarAlumnos);
        
        btnHorario = new JButton("Consultar mi Horario");
        btnHorario.setBackground(new Color(65, 105, 225));
        btnHorario.setForeground(Color.WHITE);
        btnHorario.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnHorario.setBounds(333, 276, 201, 35);
        btnHorario.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ConsultaHorario pantallaConsultaHorario = new ConsultaHorario(user, cliente);
                pantallaConsultaHorario.setVisible(true);
                dispose();
            }
        });
        contentPane.add(btnHorario);
        
        btnOtrosHorarios = new JButton("Consultar Otros Horarios");
        btnOtrosHorarios.setBackground(new Color(65, 105, 225));
        btnOtrosHorarios.setForeground(Color.WHITE);
        btnOtrosHorarios.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnOtrosHorarios.setBounds(333, 339, 201, 35);
        btnOtrosHorarios.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ConsultaOtroHorario pantallaConsultaOtroHorario = new ConsultaOtroHorario(user, cliente);
                pantallaConsultaOtroHorario.setVisible(true);
                dispose();
            }
        });
        contentPane.add(btnOtrosHorarios);
        
        btnReuniones = new JButton("Gestionar Reuniones");
        btnReuniones.setBackground(new Color(65, 105, 225));
        btnReuniones.setForeground(Color.WHITE);
        btnReuniones.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnReuniones.setBounds(333, 403, 201, 35);
        btnReuniones.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GestionReunion pantallaGestionReunion = new GestionReunion(user, cliente);
                pantallaGestionReunion.setVisible(true);
                dispose();
            }
        });
        contentPane.add(btnReuniones);
        
        JButton btnSalir = new JButton("SALIR");
        btnSalir.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		JOptionPane.showMessageDialog(Menu.this,
                        "Será desconectado del servidor",
                        "Advertencia",
                        JOptionPane.WARNING_MESSAGE);
        		cliente.desconectar();
        		Login pantallaLogin = new Login();
        		pantallaLogin.setVisible(true);
        		dispose();
        	}
        });
        btnSalir.setBackground(new Color(255, 0, 0));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setBounds(708, 502, 121, 48);
        contentPane.add(btnSalir);
	}
}
