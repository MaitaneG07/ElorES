package elorESClient.vistas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import elorESClient.Cliente;
import elorESClient.modelo.entities.Users;
import elorESClient.modelo.message.Message;

public class ConsultaAlumnos extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JScrollPane scrollPaneAlumnos;
	private DefaultTableModel modeloAlumnos;
	private JTable tablaAlumnos;
	private JScrollPane scrollPaneDetallesAlumno;
	private DefaultTableModel modeloDetallesAlumno;
	private JTable tablaDetallesAlumno;
	private JButton btnCiclo;
	private JPopupMenu popupMenuCiclo;
	private AbstractButton btnCurso;
	private JPopupMenu popupMenuCurso;
	private Users user;
	private Message respuesta;
	private Cliente cliente;
	private JLabel lblImagenAlumno;
	private Integer cicloFiltro = null;
	private Integer cursoFiltro = null;

	/**
	 * Create the frame.
	 * 
	 * @param cliente2
	 * 
	 * @param users
	 */
	public ConsultaAlumnos(Users user, Cliente cliente) {

		this.user = user;
		this.cliente = cliente;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 855, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTitulo = new JLabel("CONSULTAR ALUMNOS");
		lblTitulo.setForeground(new Color(65, 105, 225));
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setBounds(263, 11, 307, 50);
		contentPane.add(lblTitulo);

		JLabel lblLogo = new JLabel();
		lblLogo.setBounds(10, 11, 131, 107);

		ImageIcon icon = new ImageIcon(getClass().getResource("/elorESClient/images/logoElorrieta.png"));
		Image img = icon.getImage().getScaledInstance(131, 107, Image.SCALE_SMOOTH);
		lblLogo.setIcon(new ImageIcon(img));

		contentPane.add(lblLogo);

		scrollPaneAlumnos = new JScrollPane();
		scrollPaneAlumnos.setBounds(162, 131, 574, 149);
		contentPane.add(scrollPaneAlumnos);

		modeloAlumnos = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		modeloAlumnos.addColumn("id");
		modeloAlumnos.addColumn("Nombre");
		modeloAlumnos.addColumn("Apellidos");

		tablaAlumnos = new JTable(modeloAlumnos);
		tablaAlumnos.setFont(new Font("Arial", Font.PLAIN, 11));
		tablaAlumnos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
		tablaAlumnos.getTableHeader().setBackground(new Color(65, 105, 225));
		tablaAlumnos.getTableHeader().setForeground(Color.WHITE);
		tablaAlumnos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		tablaAlumnos.getColumnModel().getColumn(0).setMinWidth(0);
		tablaAlumnos.getColumnModel().getColumn(0).setMaxWidth(0);
		tablaAlumnos.getColumnModel().getColumn(0).setWidth(0);

		// Agregar MouseListener para detectar el un clic y obtener el id
		tablaAlumnos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1 && tablaAlumnos.getSelectedRow() != -1) {
					int selectedRow = tablaAlumnos.getSelectedRow();
					int idAlumno = (int) tablaAlumnos.getValueAt(selectedRow, 0);
					actualizarTablaDetallesAlumno(idAlumno);

				}
			}
		});

		scrollPaneAlumnos.setViewportView(tablaAlumnos);

		scrollPaneDetallesAlumno = new JScrollPane();
		scrollPaneDetallesAlumno.setBounds(162, 351, 574, 149);
		contentPane.add(scrollPaneDetallesAlumno);

		modeloDetallesAlumno = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		lblImagenAlumno = new JLabel();
		lblImagenAlumno.setBounds(10, 371, 131, 110);
		contentPane.add(lblImagenAlumno);

		modeloDetallesAlumno.addColumn("id");
		modeloDetallesAlumno.addColumn("Nombre");
		modeloDetallesAlumno.addColumn("Apellidos");
		modeloDetallesAlumno.addColumn("Email");
		modeloDetallesAlumno.addColumn("Telefono 1");
		modeloDetallesAlumno.addColumn("Telefono 2");

		tablaDetallesAlumno = new JTable(modeloDetallesAlumno);
		tablaDetallesAlumno.setFont(new Font("Arial", Font.PLAIN, 11));
		tablaDetallesAlumno.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
		tablaDetallesAlumno.getTableHeader().setBackground(new Color(65, 105, 225));
		tablaDetallesAlumno.getTableHeader().setForeground(Color.WHITE);
		tablaDetallesAlumno.getColumnModel().getColumn(0).setMinWidth(0);
		tablaDetallesAlumno.getColumnModel().getColumn(0).setMaxWidth(0);
		tablaDetallesAlumno.getColumnModel().getColumn(0).setWidth(0);
		scrollPaneDetallesAlumno.setViewportView(tablaDetallesAlumno);

		btnCiclo = new JButton();
		btnCiclo.setText("Ciclo");
		btnCiclo.setBackground(new Color(65, 105, 225));
		btnCiclo.setForeground(Color.WHITE);
		btnCiclo.setBounds(727, 70, 102, 35);
		contentPane.add(btnCiclo);

		popupMenuCiclo = new JPopupMenu();

		JMenuItem menuDAM = new JMenuItem("DAM");
		popupMenuCiclo.add(menuDAM);
		menuDAM.addActionListener(e -> {
			cicloFiltro = 1;
			actualizarTablaAlumnos();
		});

		JMenuItem menuDAW = new JMenuItem("DAW");
		popupMenuCiclo.add(menuDAW);
		menuDAW.addActionListener(e -> {
			cicloFiltro = 2;
			actualizarTablaAlumnos();
		});

		JMenuItem menuOTROS = new JMenuItem("OTROS");
		popupMenuCiclo.add(menuOTROS);
		menuOTROS.addActionListener(e -> {
			cicloFiltro = 3;
			actualizarTablaAlumnos();
		});

		JMenuItem menuASIR = new JMenuItem("ASIR");
		popupMenuCiclo.add(menuASIR);
		menuASIR.addActionListener(e -> {
			cicloFiltro = 4;
			actualizarTablaAlumnos();
		});

		JMenuItem menuSMR = new JMenuItem("SMR");
		popupMenuCiclo.add(menuSMR);
		menuSMR.addActionListener(e -> {
			cicloFiltro = 5;
			actualizarTablaAlumnos();
		});

		JMenuItem menuTodosCiclos = new JMenuItem("Todos");
		popupMenuCiclo.add(menuTodosCiclos);
		menuTodosCiclos.addActionListener(e -> {
			cicloFiltro = null;
			actualizarTablaAlumnos();
		});

		btnCiclo.addActionListener(e -> {
			popupMenuCiclo.show(btnCiclo, 0, btnCiclo.getHeight());
		});

		btnCurso = new JButton();
		btnCurso = new JButton("Curso");
		btnCurso.setBackground(new Color(65, 105, 225));
		btnCurso.setForeground(Color.WHITE);
		btnCurso.setBounds(612, 70, 102, 35);
		contentPane.add(btnCurso);

		popupMenuCurso = new JPopupMenu();

		JMenuItem menu1 = new JMenuItem("1º");
		popupMenuCurso.add(menu1);
		menu1.addActionListener(e -> {
			cursoFiltro = 1;
			actualizarTablaAlumnos();
		});

		JMenuItem menu2 = new JMenuItem("2º");
		popupMenuCurso.add(menu2);
		menu2.addActionListener(e -> {
			cursoFiltro = 2;
			actualizarTablaAlumnos();
		});

		JMenuItem menuTodosCursos = new JMenuItem("Todos");
		popupMenuCurso.add(menuTodosCursos);
		menuTodosCursos.addActionListener(e -> {
			cursoFiltro = null;
			actualizarTablaAlumnos();
		});

		btnCurso.addActionListener(e -> {
			popupMenuCurso.show(btnCurso, 0, btnCurso.getHeight());
		});

		JButton btnLimpiar = new JButton("Limpiar");
		btnLimpiar.setBackground(new Color(220, 220, 220));
		btnLimpiar.setForeground(Color.BLACK);
		btnLimpiar.setBounds(497, 70, 102, 35);
		btnLimpiar.addActionListener(e -> {
			cicloFiltro = null;
			cursoFiltro = null;
			actualizarTablaAlumnos();
		});

		contentPane.add(btnLimpiar);

		JButton btnVolver = new JButton("VOLVER");
		btnVolver.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Menu pantallaMenu = new Menu(user, cliente);
				pantallaMenu.setVisible(true);
				dispose();
			}
		});
		btnVolver.setBackground(new Color(65, 105, 225));
		btnVolver.setForeground(Color.WHITE);
		btnVolver.setBounds(727, 514, 102, 40);
		contentPane.add(btnVolver);

		actualizarTablaAlumnos();

	}

	/**
	 * Actualiza la tabla de detalles del alumno seleccionado
	 */
	private void actualizarTablaDetallesAlumno(int idAlumno) {
		modeloDetallesAlumno.setRowCount(0);

		respuesta = cliente.getStudentsByFilters(user.getId(), cicloFiltro, cursoFiltro);

		if (respuesta != null && "OK".equals(respuesta.getEstado())) {
			List<Users> listStudents = respuesta.getUsersList();
			if (listStudents != null && !listStudents.isEmpty()) {
				for (Users alumno : listStudents) {
					if (alumno.getId().equals(idAlumno)) {
						modeloDetallesAlumno
								.addRow(new Object[] { alumno.getId(), alumno.getNombre(), alumno.getApellidos(),
										alumno.getEmail(), alumno.getTelefono1(), alumno.getTelefono2() });

						if (alumno.getArgazkiaUrl() != null) {
							try {
								ImageIcon foto = new ImageIcon(getClass().getResource(alumno.getArgazkiaUrl()));
								Image imagen = foto.getImage().getScaledInstance(131, 110, Image.SCALE_SMOOTH);
								lblImagenAlumno.setIcon(new ImageIcon(imagen));
							} catch (Exception e) {
								lblImagenAlumno.setText("Foto no disponible");

							}
						} else {
							lblImagenAlumno.setText("Sin foto");
						}
					} else {
						System.out.println("No se encontraron alumnos");
					}
				}
			}
		}
	}

	/**
	 * Actualiza la tabla de alumnos con los filtros actuales
	 */
	private void actualizarTablaAlumnos() {
		modeloAlumnos.setRowCount(0);
		modeloDetallesAlumno.setRowCount(0);
		lblImagenAlumno.setIcon(null);
		lblImagenAlumno.setText("");

		System.out.println("[FILTROS] Ciclo: " + cicloFiltro + ", Curso: " + cursoFiltro);

		respuesta = cliente.getStudentsByFilters(user.getId(), cicloFiltro, cursoFiltro);

		if (respuesta != null && "OK".equals(respuesta.getEstado())) {
			List<Users> alumnos = respuesta.getUsersList();

			if (alumnos != null && !alumnos.isEmpty()) {
				for (Users alumno : alumnos) {
					modeloAlumnos.addRow(new Object[] { alumno.getId(), alumno.getNombre(), alumno.getApellidos() });
				}
				System.out.println("[ALUMNOS] Cargados " + alumnos.size() + " alumnos");
			} else {
				JOptionPane.showMessageDialog(this, "No se encontraron alumnos", "Información",
						JOptionPane.INFORMATION_MESSAGE);

				System.out.println("[ALUMNOS] No se encontraron alumnos con esos filtros");
			}
		} else {
			JOptionPane.showMessageDialog(this, "Error al cargar la lista de alumnos", "Error",
					JOptionPane.ERROR_MESSAGE);

			System.err.println("[ERROR] Error al obtener alumnos");
		}

		tablaAlumnos.revalidate();
		tablaAlumnos.repaint();
	}
}