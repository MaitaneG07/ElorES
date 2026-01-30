package elorESClient.vistas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;

import elorESClient.Cliente;
import elorESClient.modelo.entities.Users;
import elorESClient.modelo.message.Message;

public class GestionReunion extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	@SuppressWarnings("unused")
	private Users user;
	@SuppressWarnings("unused")
	private Cliente cliente;
	private JTextField textFieldEstado;
	private JTextField textFieldTitulo;
	private JTextField textFieldAsunto;
	private JTextField textFieldAula;
	private Message respuesta;
	private JComboBox<Users> comboBoxAlumnos;
	private JDateChooser dateChooser;
	private JSpinner spinnerHora;
	
	/**
	 * Create the frame.
	 * @param user 
	 * @param cliente 
	 */
	public GestionReunion(Users user, Cliente cliente) {
		
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
        
        JLabel lblTitulo = new JLabel("CREAR REUNIONES");
        lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 36));
        lblTitulo.setForeground(new Color(65, 105, 225));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(305, 33, 372, 85);
        contentPane.add(lblTitulo);
        
        JLabel lblAula = new JLabel("Aula: ");
        lblAula.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblAula.setForeground(new Color(65, 105, 225));
        lblAula.setHorizontalAlignment(SwingConstants.CENTER);
        lblAula.setBounds(186, 415, 131, 27);
        contentPane.add(lblAula);
        
        JLabel lblEstado = new JLabel("Estado: ");
        lblEstado.setHorizontalAlignment(SwingConstants.CENTER);
        lblEstado.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblEstado.setForeground(new Color(65, 105, 225));
        lblEstado.setBounds(186, 141, 131, 27);
        contentPane.add(lblEstado);
        
        JLabel lblTtulo = new JLabel("Título: ");
        lblTtulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTtulo.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblTtulo.setForeground(new Color(65, 105, 225));
        lblTtulo.setBounds(186, 196, 131, 27);
        contentPane.add(lblTtulo);
        
        JLabel lblAsunto = new JLabel("Asunto: ");
        lblAsunto.setHorizontalAlignment(SwingConstants.CENTER);
        lblAsunto.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblAsunto.setForeground(new Color(65, 105, 225));
        lblAsunto.setBounds(186, 250, 131, 27);
        contentPane.add(lblAsunto);
        
        JLabel lblAlumno = new JLabel("Alumno: ");
        lblAlumno.setHorizontalAlignment(SwingConstants.CENTER);
        lblAlumno.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblAlumno.setForeground(new Color(65, 105, 225));
        lblAlumno.setBounds(186, 484, 131, 27);
        contentPane.add(lblAlumno);
        
        comboBoxAlumnos = new JComboBox<Users>();
        comboBoxAlumnos.setBounds(337, 488, 211, 23);
        contentPane.add(comboBoxAlumnos);
        
        textFieldEstado = new JTextField();
        textFieldEstado.setBounds(337, 143, 310, 26);
        contentPane.add(textFieldEstado);
        textFieldEstado.setColumns(10);
        
        textFieldTitulo = new JTextField();
        textFieldTitulo.setBounds(337, 196, 310, 27);
        contentPane.add(textFieldTitulo);
        textFieldTitulo.setColumns(10);
        
        textFieldAsunto = new JTextField();
        textFieldAsunto.setBounds(337, 250, 310, 107);
        contentPane.add(textFieldAsunto);
        textFieldAsunto.setColumns(10);
        
        textFieldAula = new JTextField();
        textFieldAula.setColumns(10);
        textFieldAula.setBounds(337, 415, 310, 27);
        contentPane.add(textFieldAula);
        
		JButton btnVolver = new JButton("VOLVER");
		btnVolver.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Menu pantallaMenu = new Menu(user, cliente);
				pantallaMenu.setVisible(true);
				dispose();
			}
		});
		btnVolver.setForeground(Color.WHITE);
		btnVolver.setBackground(new Color(0, 0, 255));
		btnVolver.setBounds(726, 506, 103, 47);
		contentPane.add(btnVolver);
		
		JButton btnEnviar = new JButton("ENVIAR");
		btnEnviar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				createReunion();
				limpiarCampos();
			}
		});
		btnEnviar.setForeground(Color.WHITE);
		btnEnviar.setBackground(new Color(50, 205, 50));
		btnEnviar.setBounds(726, 434, 103, 47);
		contentPane.add(btnEnviar);
		
		dateChooser = new JDateChooser();
		dateChooser.setDateFormatString("yyyy-MM-dd");
		dateChooser.setBounds(337, 379, 150, 25);
		contentPane.add(dateChooser);
		
		spinnerHora = new JSpinner(
			    new SpinnerDateModel()
			);

			JSpinner.DateEditor timeEditor =
			        new JSpinner.DateEditor(spinnerHora, "HH:mm");
			spinnerHora.setEditor(timeEditor);
			spinnerHora.setBounds(500, 379, 80, 25);
			contentPane.add(spinnerHora);
			
			JLabel lblFechaHora = new JLabel("Fecha y Hora: ");
			lblFechaHora.setHorizontalAlignment(SwingConstants.CENTER);
			lblFechaHora.setForeground(new Color(65, 105, 225));
			lblFechaHora.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblFechaHora.setBounds(186, 379, 131, 27);
			contentPane.add(lblFechaHora);

		generarComboBox();
	}

	protected void limpiarCampos() {
		textFieldEstado.setText("");
		textFieldTitulo.setText("");
		textFieldAsunto.setText("");
		textFieldAula.setText("");
	}

	/**
	 * funcion para obtener los campos para crear la reunion
	 */
	private void createReunion() {

	    String estado = textFieldEstado.getText().trim();
	    String titulo = textFieldTitulo.getText().trim();
	    String asunto = textFieldAsunto.getText().trim();
	    String aula = textFieldAula.getText().trim();
	    Users alumno = (Users) comboBoxAlumnos.getSelectedItem();
	    Date fecha = dateChooser.getDate();
	    Date hora = (Date) spinnerHora.getValue();

	    if (estado.isEmpty() || titulo.isEmpty() || aula.isEmpty() || alumno == null || fecha == null) {
	    	JOptionPane.showMessageDialog(this,
	    			"Campos obligatorios incompletos",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
	        System.out.println("Campos obligatorios incompletos");
	        return;
	    }

	    LocalDate localDate = fecha.toInstant()
	            .atZone(ZoneId.systemDefault())
	            .toLocalDate();

	    LocalTime localTime = hora.toInstant()
	            .atZone(ZoneId.systemDefault())
	            .toLocalTime()
	            .withSecond(0)
	            .withNano(0);

	    LocalDateTime fechaHora = LocalDateTime.of(localDate, localTime);

	    if (fechaHora.isBefore(LocalDateTime.now())) {
	    	JOptionPane.showMessageDialog(this,
	    			"La reunión no puede ser en el pasado",
                    "Error en la fecha",
                    JOptionPane.ERROR_MESSAGE);
	        System.out.println("La reunión no puede ser en el pasado");
	        return;
	    }

	    Integer idAlumnoSeleccionado = alumno.getId();

	    respuesta = cliente.createReunion(
	            estado,
	            titulo,
	            asunto,
	            aula,
	            idAlumnoSeleccionado,
	            fechaHora,
	            user.getId()
	    );
	}
	
	/**
	 * Rellena el comboBox con el nombre y apellidos obtenidos de la bbdd
	 */
	private void generarComboBox() {
		respuesta = cliente.getAllStudents();
		
		if (respuesta != null && "OK".equals(respuesta.getEstado())) {
			List<Users> listStudents = respuesta.getUsersList();
			if (listStudents != null && !listStudents.isEmpty()) {
				for (Users alumno : listStudents) {
					comboBoxAlumnos.addItem(alumno);
				}
			}
		}
	}
}
