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
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Menu extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnPerfil;
	private JButton btnConsultarAlumnos;
	private JButton btnHorario;
	private JButton btnOtrosHorarios;
	private JButton btnReuniones;
	private JButton btnSalir;
	private int idProfesor;

	/**
	 * Establece el ID del cliente y su nivel.
	 * 
	 * @param idCliente ID único del cliente
	 * @param nivel     Nivel de experiencia del cliente
	 */
	public void setIdProfesor(int idProfesor) {
		this.idProfesor = idProfesor;
		System.out.println("Seteando ID Profesor: " + idProfesor);
	}
	
	/**
	 * Create the frame.
	 */
	public Menu() {
        
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
                Perfil pantallaPerfil = new Perfil();
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
                ConsultaAlumnos pantallaConsultaAlumnos = new ConsultaAlumnos();
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
                ConsultaHorario pantallaConsultaHorario = new ConsultaHorario();
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
                ConsultaOtroHorario pantallaConsultaOtroHorario = new ConsultaOtroHorario();
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
                GestionReunion pantallaGestionReunion = new GestionReunion();
                pantallaGestionReunion.setVisible(true);
                dispose();
            }
        });
        contentPane.add(btnReuniones);
        
        btnSalir = new JButton("Salir del Programa");
        btnSalir.setBackground(new Color(65, 105, 225));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnSalir.setBounds(333, 570, 179, 10);
        btnSalir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Login pantallaLogin = new Login();
                pantallaLogin.setVisible(true);
                dispose();
            }
        });
        contentPane.add(btnSalir);
	}
}
