package vistas;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class ConsultaAlumnos extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JScrollPane scrollPaneAlumnos;
	private DefaultTableModel modeloAlumnos;
	private JTable tablaAlumnos;
	private JScrollPane scrollPaneDetallesAlumno;
	private DefaultTableModel modeloDetallesAlumno;
	private JTable tablaDetallesAlumno;

	/**
	 * Create the frame.
	 */
	public ConsultaAlumnos() {
		
        JLabel lblLogo = new JLabel();
        lblLogo.setBounds(10, 11, 131, 107);

        ImageIcon icon = new ImageIcon(getClass().getResource("/images/logoElorrieta.png"));
        Image img = icon.getImage().getScaledInstance(131, 107, Image.SCALE_SMOOTH);
        lblLogo.setIcon(new ImageIcon(img));

        contentPane.add(lblLogo);
        
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 855, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

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
		
		modeloDetallesAlumno.addColumn("id");
		modeloDetallesAlumno.addColumn("Nombre");
		modeloDetallesAlumno.addColumn("Apellidos");
		modeloDetallesAlumno.addColumn("Foto");

		tablaDetallesAlumno = new JTable(modeloDetallesAlumno);
		tablaDetallesAlumno.getColumnModel().getColumn(0).setMinWidth(0);
		tablaDetallesAlumno.getColumnModel().getColumn(0).setMaxWidth(0);
		tablaDetallesAlumno.getColumnModel().getColumn(0).setWidth(0);
		scrollPaneDetallesAlumno.setViewportView(tablaDetallesAlumno);

//		actualizarTablaAlumnos(modeloAlumnos, null);


	}

//	private void actualizarTablaAlumnos(DefaultTableModel modeloAlumnos, User alumno) {
//		// TODO Auto-generated method stub
//		
//	}

}
