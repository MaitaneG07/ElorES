package elorESClient.vistas;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import elorESClient.modelo.entities.Users;

public class ConsultaOtroHorario extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private int idProfesor;
	private JTable tablaHorarios;
	private DefaultTableModel modeloHorarios;
	private JScrollPane scrollPaneHorarios;
	private JMenuItem menuNerea;
	private JPopupMenu popupMenuNombre;
	private JButton btnNombre;
	private JMenuItem menuRomán;
	private JMenuItem menuIker;
	private JMenuItem menuOier;
	private JMenuItem menuJorge;
	private JMenuItem menuNereaG;
	private JMenuItem menuAsier;
	private JMenuItem menuMikel;
	private JMenuItem menuJose;
	private JMenuItem menuAitziber;

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
	 * @param users 
	 */
	public ConsultaOtroHorario(Users user) {
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
        
        scrollPaneHorarios = new JScrollPane();
        scrollPaneHorarios.setBounds(106, 179, 630, 332);
		contentPane.add(scrollPaneHorarios);

		modeloHorarios = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		modeloHorarios.addColumn("id");
		modeloHorarios.addColumn("");
		modeloHorarios.addColumn("LUNES");
		modeloHorarios.addColumn("MARTES");
		modeloHorarios.addColumn("MIÉRCOLES");
		modeloHorarios.addColumn("JUEVES");
		modeloHorarios.addColumn("VIERNES");

		tablaHorarios = new JTable(modeloHorarios);
		tablaHorarios.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		tablaHorarios.getColumnModel().getColumn(0).setMinWidth(0);
		tablaHorarios.getColumnModel().getColumn(0).setMaxWidth(0);
		tablaHorarios.getColumnModel().getColumn(0).setWidth(0);

		// Agregar MouseListener para detectar el un clic y obtener el id
		tablaHorarios.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1 && tablaHorarios.getSelectedRow() != -1) {
					int selectedRow = tablaHorarios.getSelectedRow();
					
					// AQUÍ ES LO MISMO QUE LA OTRA PANTALLA, PERO COGIENDO EL NOMBRE Y EL APELLIDO SELECCIONADO, BUSCARLO EN LA BBDD Y 
					// OBTENER EL HORARIO DE ESA PERSONANECESITAMOS EL ID DEL PROFESOR PARA SABER QUÉ ASIGNATURAS TIENE EN QUÉ DÍAS. 
					// CUANDO SEPAMOS EL ID Y TENGAMOS DE LA TABLA HORARIO EL ID DE
					// MODULO, SABREMOS QUÉ ASIGNATURA TIENE. SELECT * FROM horarios WHERE profe_id = idProfesor;. Y UNA VEZ OBTENIDA LA INFO CON LA SELECT ANTERIOR,
					// HAY QUE QUEDARSE CON EL DÍA Y CON EL modulo_id, PARA LUEGO HACER SELECT nombre, nombre_eus FROM modulos 
					// WHERE modulo_id = el id obtenido de la select anterior.
				}
			}
		});

		scrollPaneHorarios.setViewportView(tablaHorarios);
		
		btnNombre = new JButton();
		btnNombre.setBackground(new Color(65, 105, 225));
		btnNombre.setForeground(Color.WHITE);
		btnNombre.setBounds(727, 78, 102, 40);
		contentPane.add(btnNombre);

		popupMenuNombre = new JPopupMenu();

		menuRomán = new JMenuItem("Roman Lopez");
		popupMenuNombre.add(menuRomán);
		menuRomán.addActionListener(e -> {
//			actualizarTablaAlumnos(modeloAlumnos, "DAM");
		});

		menuIker = new JMenuItem("Iker Martinez");
		popupMenuNombre.add(menuIker);
		menuIker.addActionListener(e -> {
//			actualizarTablaAlumnos(modeloAlumnos, "DAW");
		});
		
		menuOier = new JMenuItem("Oier Gomez");
		popupMenuNombre.add(menuOier);
		menuOier.addActionListener(e -> {
//			actualizarTablaAlumnos("OTROS");
		});

		menuJorge = new JMenuItem("Jorge Lopez");
		popupMenuNombre.add(menuJorge);
		menuJorge.addActionListener(e -> {
//			actualizarTablaAlumnos(modeloAlumnos, "ASIR");
		});

		menuNereaG = new JMenuItem("Nerea Gomez");
		popupMenuNombre.add(menuNereaG);
		menuNereaG.addActionListener(e -> {
//			actualizarTablaAlumnos(modeloAlumnos, "SMR");
		});
		
		menuAsier = new JMenuItem("Asier Garcia");
		popupMenuNombre.add(menuAsier);
		menuAsier.addActionListener(e -> {
//			actualizarTablaAlumnos(modeloAlumnos, "SMR");
		});
		
		menuMikel = new JMenuItem("Mikel Orego");
		popupMenuNombre.add(menuMikel);
		menuMikel.addActionListener(e -> {
//			actualizarTablaAlumnos(modeloAlumnos, "SMR");
		});
		
		menuJose = new JMenuItem("Jose Paz");
		popupMenuNombre.add(menuJose);
		menuJose.addActionListener(e -> {
//			actualizarTablaAlumnos(modeloAlumnos, "SMR");
		});
		
		menuAitziber = new JMenuItem("Aitziber Andes");
		popupMenuNombre.add(menuAitziber);
		menuAitziber.addActionListener(e -> {
//			actualizarTablaAlumnos(modeloAlumnos, "SMR");
		});
		
		menuNerea = new JMenuItem("Nerea Barbar");
		popupMenuNombre.add(menuNerea);
		menuNerea.addActionListener(e -> {
//			actualizarTablaAlumnos(modeloAlumnos, "SMR");
		});

		btnNombre.addActionListener(e -> {
			popupMenuNombre.show(btnNombre, 0, btnNombre.getHeight());
		});
		
		JButton btnVolver = new JButton();
		btnVolver.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Menu pantallaMenu = new Menu(user);
				pantallaMenu.setVisible(true);
				dispose();
			}
		});
		btnVolver.setForeground(Color.WHITE);
		btnVolver.setBackground(new Color(65, 105, 225));
		btnVolver.setBounds(727, 514, 102, 40);
		contentPane.add(btnVolver);
	}

}
