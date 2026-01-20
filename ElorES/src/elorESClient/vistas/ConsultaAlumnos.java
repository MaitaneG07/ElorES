package elorESClient.vistas;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Font;

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
	public ConsultaAlumnos() {
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
		modeloAlumnos.addColumn("Email");

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
//					actualizarTablaDetallesAlumno(modeloDetallesAlumno, idProfesor);

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
		
		//AQUI MOSTRAREMOS LA IMAGEN DEL ALUMNO SELECCIONADO
//        JLabel lblImagenAlumno = new JLabel();
//        lblImagenAlumno.setBounds(10, 371, 131, 110);
//
//        ImageIcon iconoAlumno = new ImageIcon(getClass().getResource());
//        Image imgage = icon.getImage().getScaledInstance(131, 107, Image.SCALE_SMOOTH);
//        lblImagenAlumno.setIcon(new ImageIcon(img));
//
//        contentPane.add(lblImagenAlumno);
        
		modeloDetallesAlumno.addColumn("id");
		modeloDetallesAlumno.addColumn("Nombre");
		modeloDetallesAlumno.addColumn("Apellidos");

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

//		actualizarTablaAlumnos(modeloAlumnos, null);

		btnCiclo = new JButton();
		btnCiclo.setBackground(new Color(65, 105, 225));
		btnCiclo.setForeground(Color.WHITE);
		btnCiclo.setBounds(727, 78, 102, 40);
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

		btnCurso = new JButton();
		btnCurso.setBackground(new Color(65, 105, 225));
		btnCurso.setForeground(Color.WHITE);
		btnCurso.setBounds(612, 78, 102, 40);
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
		
		JButton btnVolver = new JButton();
		btnVolver.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Menu pantallaMenu = new Menu();
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
