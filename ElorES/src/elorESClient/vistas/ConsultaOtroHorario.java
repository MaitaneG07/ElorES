package elorESClient.vistas;

import java.awt.Color;
import java.awt.Font;
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
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import elorESClient.Cliente;
import elorESClient.modelo.entities.Users;

public class ConsultaOtroHorario extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JMenuItem menuAsier;
	private JMenuItem menuMikel;
	private JMenuItem menuJose;
	private JMenuItem menuAitziber;
	private JMenuItem menuNerea;
	private JPopupMenu popupMenuNombre;
	private JMenuItem menuNereaG;
	private JMenuItem menuJorge;
	private JMenuItem menuIker;
	private JMenuItem menuOier;
	private JMenuItem menuRoman;
	private JScrollPane scrollPaneHorarios;
	private JTable tablaHorarios;
	private DefaultTableModel modeloHorarios;
	private Users user;
	private Cliente cliente;
	private JButton btnCurso;
	private JPopupMenu popupMenuCurso;
	private JMenuItem menuDAM;
	private JMenuItem menuDAW;
	private JMenuItem menuOTROS;
	private JMenuItem menuASIR;
	private JButton btnCiclo;
	private JPopupMenu popupMenuCiclo;
	private JMenuItem menuSMR;
	private JLabel lblTitulo;
	private JScrollPane scrollPaneProfesores;
	private JTable tablaProfesores;
	private DefaultTableModel modeloProfesores;

	/**
	 * Create the frame.
	 */
	public ConsultaOtroHorario(Users user, Cliente cliente) {
		
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
        
        scrollPaneHorarios = new JScrollPane();
        scrollPaneHorarios.setBounds(375, 149, 452, 332);
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
		
		scrollPaneProfesores = new JScrollPane();
		scrollPaneProfesores.setBounds(10, 149, 343, 332);
		contentPane.add(scrollPaneProfesores);

		modeloProfesores = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		modeloProfesores.addColumn("id");
		modeloProfesores.addColumn("Nombre");
		modeloProfesores.addColumn("Apellidos");

		tablaProfesores = new JTable(modeloProfesores);
		tablaProfesores.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		tablaProfesores.getColumnModel().getColumn(0).setMinWidth(0);
		tablaProfesores.getColumnModel().getColumn(0).setMaxWidth(0);
		tablaProfesores.getColumnModel().getColumn(0).setWidth(0);

		// Agregar MouseListener para detectar el un clic y obtener el id
		tablaProfesores.addMouseListener(new MouseAdapter() {
			private Object modeloProfesores;

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1 && tablaProfesores.getSelectedRow() != -1) {
					int selectedRow = tablaProfesores.getSelectedRow();
					int idProfesor = (int) tablaProfesores.getValueAt(selectedRow, 0);
//					actualizarTablaHorarios(modeloProfesores, idAlumno);

				}
			}
		});

		scrollPaneProfesores.setViewportView(tablaProfesores);
		
		btnCurso = new JButton();
		btnCurso.setText("Curso");
		btnCurso.setBackground(new Color(65, 105, 225));
		btnCurso.setForeground(Color.WHITE);
		btnCurso.setBounds(551, 98, 102, 40);
		contentPane.add(btnCurso);

		popupMenuCurso = new JPopupMenu();

		// Hay 5 ids de ciclo
		// Este uno es el id 1 de ciclo
		menuDAM = new JMenuItem("1");
		popupMenuCurso.add(menuDAM);
		menuDAM.addActionListener(e -> {
//			actualizarTablaAlumnos(modeloAlumnos, "1");
		});

		// Este uno es el id 2 de ciclo
		menuDAW = new JMenuItem("1");
		popupMenuCurso.add(menuDAW);
		menuDAW.addActionListener(e -> {
//			actualizarTablaAlumnos(modeloAlumnos, "1");
		});

		// Este dos es el id 3 de ciclo
		menuOTROS = new JMenuItem("2");
		popupMenuCurso.add(menuOTROS);
		menuOTROS.addActionListener(e -> {
//			actualizarTablaAlumnos("2");
		});

		// Este dos es el id 4 de ciclo
		menuASIR = new JMenuItem("2");
		popupMenuCurso.add(menuASIR);
		menuASIR.addActionListener(e -> {
//			actualizarTablaAlumnos(modeloAlumnos, "2");
		});

		btnCurso.addActionListener(e -> {
			popupMenuCurso.show(btnCurso, 0, btnCurso.getHeight());

		});
		
		btnCiclo = new JButton();
		btnCiclo.setText("Ciclo");
		btnCiclo.setBackground(new Color(65, 105, 225));
		btnCiclo.setForeground(Color.WHITE);
		btnCiclo.setBounds(706, 98, 102, 40);
		contentPane.add(btnCiclo);

		popupMenuCiclo = new JPopupMenu();

		menuDAM = new JMenuItem("DAM");
		popupMenuCiclo.add(menuDAM);
		menuDAM.addActionListener(e -> {
//			actualizarTablaAlumnos(modeloAlumnos, "DAM");
		});

		menuDAW = new JMenuItem("DAW");
		popupMenuCiclo.add(menuDAW);
		menuDAW.addActionListener(e -> {
//			actualizarTablaAlumnos(modeloAlumnos, "DAW");
		});

		menuOTROS = new JMenuItem("OTROS");
		popupMenuCiclo.add(menuOTROS);
		menuOTROS.addActionListener(e -> {
//			actualizarTablaAlumnos("OTROS");
		});

		menuASIR = new JMenuItem("ASIR");
		popupMenuCiclo.add(menuASIR);
		menuASIR.addActionListener(e -> {
//			actualizarTablaAlumnos(modeloAlumnos, "ASIR");
		});

		menuSMR = new JMenuItem("SMR");
		popupMenuCiclo.add(menuSMR);
		menuSMR.addActionListener(e -> {
//			actualizarTablaAlumnos(modeloAlumnos, "SMR");
		});

		btnCiclo.addActionListener(e -> {
			popupMenuCiclo.show(btnCiclo, 0, btnCiclo.getHeight());
		});

		popupMenuNombre = new JPopupMenu();

//		menuRoman = new JMenuItem("Roman Lopez");
//		popupMenuNombre.add(menuRoman);
//		menuRoman.addActionListener(e -> {
////			actualizarTablaAlumnos(modeloAlumnos, "DAM");
//		});
//
//		menuIker = new JMenuItem("Iker Martinez");
//		popupMenuNombre.add(menuIker);
//		menuIker.addActionListener(e -> {
////			actualizarTablaAlumnos(modeloAlumnos, "DAW");
//		});
//		
//		menuOier = new JMenuItem("Oier Gomez");
//		popupMenuNombre.add(menuOier);
//		menuOier.addActionListener(e -> {
////			actualizarTablaAlumnos("OTROS");
//		});
//
//		menuJorge = new JMenuItem("Jorge Lopez");
//		popupMenuNombre.add(menuJorge);
//		menuJorge.addActionListener(e -> {
////			actualizarTablaAlumnos(modeloAlumnos, "ASIR");
//		});
//
//		menuNereaG = new JMenuItem("Nerea Gomez");
//		popupMenuNombre.add(menuNereaG);
//		menuNereaG.addActionListener(e -> {
////			actualizarTablaAlumnos(modeloAlumnos, "SMR");
//		});
//		
//		menuAsier = new JMenuItem("Asier Garcia");
//		popupMenuNombre.add(menuAsier);
//		menuAsier.addActionListener(e -> {
////			actualizarTablaAlumnos(modeloAlumnos, "SMR");
//		});
//		
//		menuMikel = new JMenuItem("Mikel Orego");
//		popupMenuNombre.add(menuMikel);
//		menuMikel.addActionListener(e -> {
////			actualizarTablaAlumnos(modeloAlumnos, "SMR");
//		});
//		
//		menuJose = new JMenuItem("Jose Paz");
//		popupMenuNombre.add(menuJose);
//		menuJose.addActionListener(e -> {
////			actualizarTablaAlumnos(modeloAlumnos, "SMR");
//		});
//		
//		menuAitziber = new JMenuItem("Aitziber Andes");
//		popupMenuNombre.add(menuAitziber);
//		menuAitziber.addActionListener(e -> {
////			actualizarTablaAlumnos(modeloAlumnos, "SMR");
//		});
//		
//		menuNerea = new JMenuItem("Nerea Barbar");
//		popupMenuNombre.add(menuNerea);
//		menuNerea.addActionListener(e -> {
////			actualizarTablaAlumnos(modeloAlumnos, "SMR");
//		});
//
//		btnNombre.addActionListener(e -> {
//			popupMenuNombre.show(btnNombre, 0, btnNombre.getHeight());
//		});
		
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
		btnVolver.setBackground(new Color(65, 105, 225));
		btnVolver.setBounds(727, 514, 102, 40);
		contentPane.add(btnVolver);
		
		lblTitulo = new JLabel("HORARIOS");
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 27));
		lblTitulo.setForeground(new Color(65, 105, 225));
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setBounds(177, 11, 476, 76);
		contentPane.add(lblTitulo);
	}

}
