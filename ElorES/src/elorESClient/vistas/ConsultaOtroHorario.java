package elorESClient.vistas;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import elorESClient.Cliente;
import elorESClient.modelo.entities.Horarios;
import elorESClient.modelo.entities.Reuniones;
import elorESClient.modelo.entities.Users;
import elorESClient.modelo.message.Message;

public class ConsultaOtroHorario extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JScrollPane scrollPaneHorarios;
	private JTable tablaHorarios;
	private DefaultTableModel modeloHorarios;
	@SuppressWarnings("unused")
	private Users user;
	private Cliente cliente;
	private JScrollPane scrollPaneProfesores;
	private JTable tablaProfesores;
	private DefaultTableModel modeloProfesores;
	
	private Message respuesta;
	private Integer cicloFiltro = null;
	private Integer cursoFiltro = null;
	
	// Mapa para guardar las reuniones por posición [fila][columna]
	private Map<String, Reuniones> reunionesPorCelda = new HashMap<>();
	private JButton btnCiclo;
	private JPopupMenu popupMenuCiclo;
	private JButton btnCurso;
	private JPopupMenu popupMenuCurso;
	private JLabel lblTitulo;
	
	private static final Color COLOR_PENDIENTE = new Color(255, 200, 100);
	private static final Color COLOR_ACEPTADA = new Color(144, 238, 144);
	private static final Color COLOR_CANCELADA = new Color(255, 160, 160);
	private static final Color COLOR_CONFLICTO = new Color(192, 192, 192);

	/**
	 * Create the frame.
	 */
	public ConsultaOtroHorario(Users user, Cliente cliente) {
		
		this.user = user;
		this.cliente = cliente;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1100, 700);
		setTitle("Consultar Horarios de Profesores");
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

		lblTitulo = new JLabel("HORARIOS");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 27));
		lblTitulo.setForeground(new Color(65, 105, 225));
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setBounds(300, 20, 500, 50);
		contentPane.add(lblTitulo);
		
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
		tablaProfesores.setRowHeight(25);
		tablaProfesores.setFont(new Font("Arial", Font.PLAIN, 12));
		tablaProfesores.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
		tablaProfesores.getTableHeader().setBackground(new Color(65, 105, 225));
		tablaProfesores.getTableHeader().setForeground(Color.WHITE);
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
					String nombreProfesor = (String) tablaProfesores.getValueAt(selectedRow, 1);
					String apellidosProfesor = (String) tablaProfesores.getValueAt(selectedRow, 2);
					
					lblTitulo.setText("HORARIO DE " + nombreProfesor.toUpperCase() + " " + apellidosProfesor.toUpperCase());
					actualizarTablaHorario(idProfesor);

				}
			}
		});

		scrollPaneProfesores.setViewportView(tablaProfesores);
		
		scrollPaneHorarios = new JScrollPane();
		scrollPaneHorarios.setBounds(360, 120, 700, 430);
		contentPane.add(scrollPaneHorarios);

		modeloHorarios = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		modeloHorarios.addColumn("");
		modeloHorarios.addColumn("LUNES");
		modeloHorarios.addColumn("MARTES");
		modeloHorarios.addColumn("MIÉRCOLES");
		modeloHorarios.addColumn("JUEVES");
		modeloHorarios.addColumn("VIERNES");

		tablaHorarios = new JTable(modeloHorarios);
		tablaHorarios.setRowHeight(65);
		tablaHorarios.setFont(new Font("Arial", Font.PLAIN, 11));
		tablaHorarios.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
		tablaHorarios.getTableHeader().setBackground(new Color(65, 105, 225));
		tablaHorarios.getTableHeader().setForeground(Color.WHITE);

		tablaHorarios.getColumnModel().getColumn(0).setPreferredWidth(70);
		tablaHorarios.getColumnModel().getColumn(1).setPreferredWidth(125);
		tablaHorarios.getColumnModel().getColumn(2).setPreferredWidth(125);
		tablaHorarios.getColumnModel().getColumn(3).setPreferredWidth(125);
		tablaHorarios.getColumnModel().getColumn(4).setPreferredWidth(125);
		tablaHorarios.getColumnModel().getColumn(5).setPreferredWidth(125);

		tablaHorarios.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value,
					boolean isSelected, boolean hasFocus, int row, int column) {

				Component c = super.getTableCellRendererComponent(table, value,
						isSelected, hasFocus, row, column);

				setHorizontalAlignment(CENTER);
				setVerticalAlignment(CENTER);

				String clave = row + "-" + column;
				Reuniones reunion = reunionesPorCelda.get(clave);

				if (!isSelected) {
					if (column == 0) {
						c.setBackground(new Color(240, 240, 240));
						setFont(new Font("Arial", Font.BOLD, 11));
					} else if (reunion != null) {
						String estado = reunion.getEstado().toLowerCase();
						switch (estado) {
							case "pendiente":
								c.setBackground(COLOR_PENDIENTE);
								break;
							case "aceptada":
								c.setBackground(COLOR_ACEPTADA);
								break;
							case "denegada":
								c.setBackground(COLOR_CANCELADA);
								break;
							case "conflicto":
								c.setBackground(COLOR_CONFLICTO);
								break;
							default:
								c.setBackground(Color.WHITE);
						}
						setFont(new Font("Arial", Font.PLAIN, 10));
					} else {
						c.setBackground(Color.WHITE);
						setFont(new Font("Arial", Font.PLAIN, 10));
					}
				} else {
					c.setBackground(new Color(184, 207, 229));
				}

				return c;
			}
		});

		tablaHorarios.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tablaHorarios.getSelectedRow();
				int col = tablaHorarios.getSelectedColumn();

				if (row != -1 && col > 0) {
					String clave = row + "-" + col;
					Reuniones reunion = reunionesPorCelda.get(clave);

					if (reunion != null) {
						mostrarDetallesReunion(reunion);
					}
				}
			}
		});

		scrollPaneHorarios.setViewportView(tablaHorarios);

		btnCurso = new JButton("Curso");
		btnCurso.setBackground(new Color(65, 105, 225));
		btnCurso.setForeground(Color.WHITE);
		btnCurso.setBounds(833, 74, 102, 35);
		contentPane.add(btnCurso);

		popupMenuCurso = new JPopupMenu();

		JMenuItem menu1 = new JMenuItem("1º");
		popupMenuCurso.add(menu1);
		menu1.addActionListener(e -> {
			cursoFiltro = 1;
			actualizarTablaProfesores();
		});

		JMenuItem menu2 = new JMenuItem("2º");
		popupMenuCurso.add(menu2);
		menu2.addActionListener(e -> {
			cursoFiltro = 2;
			actualizarTablaProfesores();
		});

		JMenuItem menuTodosCursos = new JMenuItem("Todos");
		popupMenuCurso.add(menuTodosCursos);
		menuTodosCursos.addActionListener(e -> {
			cursoFiltro = null;
			actualizarTablaProfesores();
		});

		btnCurso.addActionListener(e -> {
			popupMenuCurso.show(btnCurso, 0, btnCurso.getHeight());

		});
		
		btnCiclo = new JButton("Ciclo");
		btnCiclo.setBackground(new Color(65, 105, 225));
		btnCiclo.setForeground(Color.WHITE);
		btnCiclo.setBounds(958, 74, 102, 35);
		contentPane.add(btnCiclo);

		popupMenuCiclo = new JPopupMenu();

		JMenuItem menuDAM = new JMenuItem("DAM");
		popupMenuCiclo.add(menuDAM);
		menuDAM.addActionListener(e -> {
			cicloFiltro = 1;
			actualizarTablaProfesores();
		});

		JMenuItem menuDAW = new JMenuItem("DAW");
		popupMenuCiclo.add(menuDAW);
		menuDAW.addActionListener(e -> {
			cicloFiltro = 2;
			actualizarTablaProfesores();
		});

		JMenuItem menuOTROS = new JMenuItem("OTROS");
		popupMenuCiclo.add(menuOTROS);
		menuOTROS.addActionListener(e -> {
			cicloFiltro = 3;
			actualizarTablaProfesores();
		});

		JMenuItem menuASIR = new JMenuItem("ASIR");
		popupMenuCiclo.add(menuASIR);
		menuASIR.addActionListener(e -> {
			cicloFiltro = 4;
			actualizarTablaProfesores();
		});

		JMenuItem menuSMR = new JMenuItem("SMR");
		popupMenuCiclo.add(menuSMR);
		menuSMR.addActionListener(e -> {
			cicloFiltro = 5;
			actualizarTablaProfesores();
		});
		
		JMenuItem menuTodosCiclos = new JMenuItem("Todos");
		popupMenuCiclo.add(menuTodosCiclos);
		menuTodosCiclos.addActionListener(e -> {
			cicloFiltro = null;
			actualizarTablaProfesores();
		});

		btnCiclo.addActionListener(e -> {
			popupMenuCiclo.show(btnCiclo, 0, btnCiclo.getHeight());
		});

		JButton btnLimpiar = new JButton("Limpiar");
		btnLimpiar.setBackground(new Color(220, 220, 220));
		btnLimpiar.setForeground(Color.BLACK);
		btnLimpiar.setBounds(708, 74, 102, 35);
		btnLimpiar.addActionListener(e -> {
			cicloFiltro = null;
			cursoFiltro = null;
			actualizarTablaProfesores();
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
		btnVolver.setForeground(Color.WHITE);
		btnVolver.setBackground(new Color(65, 105, 225));
		btnVolver.setBounds(958, 600, 102, 40);
		contentPane.add(btnVolver);
		
		actualizarTablaProfesores();
		mostrarMensajeInicial();
	}
	
	/**
	 * Muestra un mensaje inicial en la tabla de horarios
	 */
	private void mostrarMensajeInicial() {
		modeloHorarios.setRowCount(0);
		reunionesPorCelda.clear();
		for (int i = 1; i <= 6; i++) {
			modeloHorarios.addRow(new Object[]{"Hora " + i, "", "", "", "", ""});
		}
	}

	/**
	 * Actualiza la tabla de horarios con los datos del profesor seleccionado
	 */
	private void actualizarTablaHorario(Integer idProfesor) {
		modeloHorarios.setRowCount(0);
		reunionesPorCelda.clear();
		
		System.out.println("[HORARIO] Cargando horario del profesor ID: " + idProfesor);
		for (int i = 1; i <= 6; i++) {
			modeloHorarios.addRow(new Object[]{"Hora " + i, "", "", "", "", ""});
		}

		respuesta = cliente.getHorario(idProfesor);

		if (respuesta != null && "OK".equals(respuesta.getEstado())) {
			List<Horarios> horarios = respuesta.getHorarioList();

			if (horarios != null && !horarios.isEmpty()) {
				for (Horarios h : horarios) {
					int fila = h.getHora() - 1;
					int columna = getColumnaDia(h.getDia());

					if (fila >= 0 && fila < 6 && columna > 0 && columna <= 5) {
						String nombreModulo = "";

						if (h.getModulos() != null && h.getModulos().getNombre() != null) {
							nombreModulo = h.getModulos().getNombre();
						}

						if (h.getObservaciones() != null) {
							if (h.getObservaciones().contains("Tutoria")) {
								nombreModulo = "Tutoría";
							} else if (h.getObservaciones().contains("Guardia")) {
								nombreModulo = "Guardia";
							}
						}

						modeloHorarios.setValueAt(nombreModulo, fila, columna);
					}
				}
			}
		}

		Message respuestaReuniones = cliente.getReunionesProfesor(idProfesor);

		if (respuestaReuniones != null && "OK".equals(respuestaReuniones.getEstado())) {
			List<Reuniones> reuniones = respuestaReuniones.getReunionesList();

			if (reuniones != null && !reuniones.isEmpty()) {
				System.out.println("[HORARIO] Procesando " + reuniones.size() + " reuniones");

				for (Reuniones r : reuniones) {
					if (r.getFecha() != null) {
						LocalDateTime fecha = r.getFecha();
						int hora = fecha.getHour();

						int slot = convertirHoraASlot(hora);
						DayOfWeek dayOfWeek = fecha.getDayOfWeek();
						int columna = obtenerColumnaPorDia(dayOfWeek);

						if (slot >= 1 && slot <= 6 && columna > 0 && columna <= 5) {
							int fila = slot - 1;

							String contenidoActual = (String) modeloHorarios.getValueAt(fila, columna);
							String contenidoNuevo;
							String estadoOriginal = r.getEstado();
							
							if (contenidoActual == null || contenidoActual.trim().isEmpty()) {
								contenidoNuevo = contenidoActual + "\n/ Reunión";
								r.setEstado("conflicto");
							} else {
								contenidoNuevo = "Reunión";
							}

							modeloHorarios.setValueAt(contenidoNuevo, fila, columna);

							String clave = fila + "-" + columna;
							reunionesPorCelda.put(clave, r);

							System.out.println("[HORARIO] Reunión añadida: Fila=" + fila + ", Col=" + columna + ", Estado=" + r.getEstado());
						}
					}
				}
			}
		}

		tablaHorarios.repaint();
		System.out.println("[HORARIO] Carga completada");
	}

	/**
	 * Convierte la hora (0-23) a slot de horario (1-6)
	 */
	private int convertirHoraASlot(int hora) {
		if (hora >= 8 && hora < 14) {
			return hora - 7;
		}
		return -1;
	}

	/**
	 * Obtiene la columna correspondiente al DayOfWeek
	 */
	private int obtenerColumnaPorDia(DayOfWeek day) {
		switch (day) {
			case MONDAY:
				return 1;
			case TUESDAY:
				return 2;
			case WEDNESDAY:
				return 3;
			case THURSDAY:
				return 4;
			case FRIDAY:
				return 5;
			default:
				return -1;
		}
	}

	/**
	 * Muestra los detalles de una reunión en un diálogo
	 */
	private void mostrarDetallesReunion(Reuniones reunion) {
		StringBuilder detalles = new StringBuilder();
		detalles.append("═══ DETALLES DE LA REUNIÓN ═══\n\n");

		if (reunion.getTitulo() != null && !reunion.getTitulo().isEmpty()) {
			detalles.append("Título: ").append(reunion.getTitulo()).append("\n");
		}

		if (reunion.getAsunto() != null && !reunion.getAsunto().isEmpty()) {
			detalles.append("Asunto: ").append(reunion.getAsunto()).append("\n");
		}

		if (reunion.getAlumnos() != null) {
			detalles.append("Alumno: ").append(reunion.getAlumnos().getNombre())
				.append(" ").append(reunion.getAlumnos().getApellidos()).append("\n");
		}

		if (reunion.getAula() != null && !reunion.getAula().isEmpty()) {
			detalles.append("Aula: ").append(reunion.getAula()).append("\n");
		}

		detalles.append("Estado: ").append(reunion.getEstado()).append("\n");
		detalles.append("Fecha: ").append(reunion.getFecha()).append("\n");

		JOptionPane.showMessageDialog(this,
				detalles.toString(),
				"Detalles de la Reunión",
				JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Convierte el día a número de columna
	 */
	private int getColumnaDia(String dia) {
		if (dia == null) return 0;
		
		switch (dia.toUpperCase()) {
			case "LUNES":
				return 1;
			case "MARTES":
				return 2;
			case "MIERCOLES":
			case "MIÉRCOLES":
				return 3;
			case "JUEVES":
				return 4;
			case "VIERNES":
				return 5;
			default:
				return 0;
		}
	}

	/**
	 * Carga la lista de todos los profesores
	 */
	private void actualizarTablaProfesores() {
		modeloProfesores.setRowCount(0);

		System.out.println("[FILTROS] Ciclo: " + cicloFiltro + ", Curso: " + cursoFiltro);
		
		respuesta = cliente.getTeachersByFilters(cicloFiltro, cursoFiltro);

		if (respuesta != null && "OK".equals(respuesta.getEstado())) {
			List<Users> listTeachers = respuesta.getUsersList();
			if (listTeachers != null && !listTeachers.isEmpty()) {
				for (Users teacher : listTeachers) {
					modeloProfesores.addRow(new Object[]{
						teacher.getId(), 
						teacher.getNombre(), 
						teacher.getApellidos()
					});
				}
				System.out.println("[PROFESORES] Cargados " + listTeachers.size() + " profesores");
			} else {
				JOptionPane.showMessageDialog(this, 
					"No se encontraron profesores", 
					"Información", 
					JOptionPane.INFORMATION_MESSAGE);
				
				System.out.println("[PROFESORES] No se encontraron profesores con esos filtros");
			}
		} else {
			JOptionPane.showMessageDialog(this, 
				"Error al cargar la lista de profesores", 
				"Error", 
				JOptionPane.ERROR_MESSAGE);
			
			System.err.println("[PROFESORES] Error al obtener profesores");
		}

		tablaProfesores.revalidate();
		tablaProfesores.repaint();
	}

}
