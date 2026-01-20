package elorESClient.vistas;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class ConsultaHorario extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private int idProfesor;
	private JScrollPane scrollPaneHorario;
	private DefaultTableModel modeloHorario;
	private JTable tablaHorario;

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
	public ConsultaHorario() {
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
        
        scrollPaneHorario = new JScrollPane();
        scrollPaneHorario.setBounds(79, 162, 630, 332);
		contentPane.add(scrollPaneHorario);

		modeloHorario = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		modeloHorario.addColumn("id");
		modeloHorario.addColumn("");
		modeloHorario.addColumn("LUNES");
		modeloHorario.addColumn("MARTES");
		modeloHorario.addColumn("MIÉRCOLES");
		modeloHorario.addColumn("JUEVES");
		modeloHorario.addColumn("VIERNES");

		tablaHorario = new JTable(modeloHorario);
		tablaHorario.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		tablaHorario.getColumnModel().getColumn(0).setMinWidth(0);
		tablaHorario.getColumnModel().getColumn(0).setMaxWidth(0);
		tablaHorario.getColumnModel().getColumn(0).setWidth(0);

		// Agregar MouseListener para detectar el un clic y obtener el id
		tablaHorario.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1 && tablaHorario.getSelectedRow() != -1) {
					int selectedRow = tablaHorario.getSelectedRow();
					
					// NECESITAMOS EL ID DEL PROFESOR PARA SABER QUÉ ASIGNATURAS TIENE EN QUÉ DÍAS. CUANDO SEPAMOS EL ID Y TENGAMOS DE LA TABLA HORARIO EL ID DE
					// MODULO, SABREMOS QUÉ ASIGNATURA TIENE. SELECT * FROM horarios WHERE profe_id = idProfesor;. Y UNA VEZ OBTENIDA LA INFO CON LA SELECT ANTERIOR,
					// HAY QUE QUEDARSE CON EL DÍA Y CON EL modulo_id, PARA LUEGO HACER SELECT nombre, nombre_eus FROM modulos 
					// WHERE modulo_id = el id obtenido de la select anterior.
				}
			}
		});

		scrollPaneHorario.setViewportView(tablaHorario);
		
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
