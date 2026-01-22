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
	private int idProfesor;
	private JButton btnCiclo;
	private JPopupMenu popupMenuCiclo;
	private JMenuItem menuDAM;
	private JMenuItem menuDAW;
	private JMenuItem menuOTROS;
	private JMenuItem menuASIR;
	private JMenuItem menuSMR;
	private AbstractButton btnCurso;
	private JPopupMenu popupMenuCurso;
	private Users user;
	private Message respuesta;
	private Cliente cliente;
	private JLabel lblImagenAlumno;
	private Integer cicloFiltro = null;
	private Integer cursoFiltro = null;
	private Integer cicloSeleccionado;
	private Integer cursoSeleccionado;
	private JMenuItem menu1;
	private JMenuItem menu2;

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
					actualizarTablaDetallesAlumno(modeloDetallesAlumno, idAlumno);

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
		tablaDetallesAlumno.getColumnModel().getColumn(0).setMinWidth(0);
		tablaDetallesAlumno.getColumnModel().getColumn(0).setMaxWidth(0);
		tablaDetallesAlumno.getColumnModel().getColumn(0).setWidth(0);
		scrollPaneDetallesAlumno.setViewportView(tablaDetallesAlumno);

		JLabel lblTitulo = new JLabel("CONSULTAR ALUMNOS");
		lblTitulo.setForeground(new Color(65, 105, 225));
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setBounds(263, 11, 307, 79);
		contentPane.add(lblTitulo);

		btnCiclo = new JButton();
		btnCiclo.setText("Ciclo");
		btnCiclo.setBackground(new Color(65, 105, 225));
		btnCiclo.setForeground(Color.WHITE);
		btnCiclo.setBounds(727, 78, 102, 40);
		contentPane.add(btnCiclo);

		popupMenuCiclo = new JPopupMenu();

		menuDAM = new JMenuItem("DAM");
		popupMenuCiclo.add(menuDAM);
		menuDAM.addActionListener(e -> {
			cicloSeleccionado = 1;
			actualizarTablaAlumnos(modeloAlumnos, 1, null);
		});

		menuDAW = new JMenuItem("DAW");
		popupMenuCiclo.add(menuDAW);
		menuDAW.addActionListener(e -> {
			cicloSeleccionado = 2;
			actualizarTablaAlumnos(modeloAlumnos, 2, null);
		});

		menuOTROS = new JMenuItem("OTROS");
		popupMenuCiclo.add(menuOTROS);
		menuOTROS.addActionListener(e -> {
		    cicloFiltro = null;
		    cursoFiltro = null;
			actualizarTablaAlumnos(modeloAlumnos, 3, null);
		});

		menuASIR = new JMenuItem("ASIR");
		popupMenuCiclo.add(menuASIR);
		menuASIR.addActionListener(e -> {
			cicloSeleccionado = 4;
			actualizarTablaAlumnos(modeloAlumnos, 4, null);
		});

		menuSMR = new JMenuItem("SMR");
		popupMenuCiclo.add(menuSMR);
		menuSMR.addActionListener(e -> {
			cicloSeleccionado = 5;
			actualizarTablaAlumnos(modeloAlumnos, 5, null);
		});

		btnCiclo.addActionListener(e -> {
			popupMenuCiclo.show(btnCiclo, 0, btnCiclo.getHeight());
		});

		btnCurso = new JButton();
		btnCurso.setText("Curso");
		btnCurso.setBackground(new Color(65, 105, 225));
		btnCurso.setForeground(Color.WHITE);
		btnCurso.setBounds(612, 78, 102, 40);
		contentPane.add(btnCurso);

		popupMenuCurso = new JPopupMenu();

		menu1 = new JMenuItem("1");
		popupMenuCurso.add(menu1);
		menu1.addActionListener(e -> {
			cursoSeleccionado = 1;
			actualizarTablaAlumnos(modeloAlumnos, null, 1);
		});

		menu2 = new JMenuItem("2");
		popupMenuCurso.add(menu2);
		menu2.addActionListener(e -> {
			cursoSeleccionado = 2;
			actualizarTablaAlumnos(modeloAlumnos, null, 4);
		});

		btnCurso.addActionListener(e -> {
			popupMenuCurso.show(btnCurso, 0, btnCurso.getHeight());

		});

		JButton btnVolver = new JButton();
		btnVolver.setText("VOLVER");
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

		actualizarTablaAlumnos(modeloAlumnos, null, null);

	}

	protected void actualizarTablaDetallesAlumno(DefaultTableModel modeloDetallesAlumno, int idAlumno) {
		modeloDetallesAlumno.setRowCount(0);

		respuesta = cliente.getAllStudentsById(this.user.getId());

		if (respuesta != null && "OK".equals(respuesta.getEstado())) {
			List<Users> listStudents = respuesta.getUsersList();
			if (listStudents != null && !listStudents.isEmpty()) {
				for (Users alumno : listStudents) {
					if (alumno.getId().equals(idAlumno)) {
						modeloDetallesAlumno
								.addRow(new Object[] { alumno.getId(), alumno.getNombre(), alumno.getApellidos(),
										alumno.getEmail(), alumno.getTelefono1(), alumno.getTelefono2() });
						if (user.getArgazkiaUrl() != null) {
							ImageIcon foto = new ImageIcon(getClass().getResource(user.getArgazkiaUrl()));
							Image imagen = foto.getImage().getScaledInstance(131, 107, Image.SCALE_SMOOTH);
							lblImagenAlumno.setIcon(new ImageIcon(imagen));
						} else {
							lblImagenAlumno.setText("No tiene foto");
						}
					}
				}
			} else {
				System.out.println("No se encontraron alumnos");
			}
		} else {
			System.err.println("Error al obtener alumnos: " + respuesta.getMensaje());
		}
	}

	private void actualizarTablaAlumnos(DefaultTableModel modeloAlumnos, Integer ciclo, Integer curso) {
		 modeloAlumnos.setRowCount(0);

		 respuesta = cliente.getAllStudentsById(this.user.getId());

		    if (respuesta != null && "OK".equals(respuesta.getEstado())) {
		        List<Users> alumnos = respuesta.getUsersList();

		        if (alumnos != null) {
		            for (Users alumno : alumnos) {
		                modeloAlumnos.addRow(new Object[] {
		                    alumno.getId(),
		                    alumno.getNombre(),
		                    alumno.getApellidos()
		                });
		            }
		        }
		    } else {
		        System.err.println("Error al obtener alumnos");
		    }

		    tablaAlumnos.revalidate();
		    tablaAlumnos.repaint();
	}
}