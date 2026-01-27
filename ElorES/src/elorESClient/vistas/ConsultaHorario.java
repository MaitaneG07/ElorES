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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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

public class ConsultaHorario extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JScrollPane scrollPaneHorario;
	private DefaultTableModel modeloHorario;
	private JTable tablaHorario;
	private Users user;
	private Cliente cliente;
	
	// Mapa para guardar las reuniones por posición [fila][columna]
	private Map<String, Reuniones> reunionesPorCelda = new HashMap<>();
	
	// Colores para las reuniones
	private static final Color COLOR_PENDIENTE = new Color(255, 200, 100);
	private static final Color COLOR_ACEPTADA = new Color(144, 238, 144);
	private static final Color COLOR_CANCELADA = new Color(255, 160, 160);
	private static final Color COLOR_CONFLICTO = new Color(192, 192, 192);

	public ConsultaHorario(Users user, Cliente cliente) {

		this.user = user;
		this.cliente = cliente;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 855, 650);
		setTitle("Mi Horario - " + user.getNombre() + " " + user.getApellidos());
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

		JLabel lblTitulo = new JLabel("MI HORARIO");
		lblTitulo.setForeground(new Color(65, 105, 225));
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 32));
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setBounds(233, 36, 368, 50);
		contentPane.add(lblTitulo);

		scrollPaneHorario = new JScrollPane();
		scrollPaneHorario.setBounds(108, 139, 630, 390);
		contentPane.add(scrollPaneHorario);

		modeloHorario = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		modeloHorario.addColumn("");
		modeloHorario.addColumn("LUNES");
		modeloHorario.addColumn("MARTES");
		modeloHorario.addColumn("MIÉRCOLES");
		modeloHorario.addColumn("JUEVES");
		modeloHorario.addColumn("VIERNES");

		tablaHorario = new JTable(modeloHorario);
		tablaHorario.setRowHeight(60);
		tablaHorario.setFont(new Font("Arial", Font.PLAIN, 11));
		tablaHorario.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
		tablaHorario.getTableHeader().setBackground(new Color(65, 105, 225));
		tablaHorario.getTableHeader().setForeground(Color.WHITE);

		// Ajustar el ancho de las columnas
		tablaHorario.getColumnModel().getColumn(0).setPreferredWidth(70);
		tablaHorario.getColumnModel().getColumn(1).setPreferredWidth(110);
		tablaHorario.getColumnModel().getColumn(2).setPreferredWidth(110);
		tablaHorario.getColumnModel().getColumn(3).setPreferredWidth(110);
		tablaHorario.getColumnModel().getColumn(4).setPreferredWidth(110);
		tablaHorario.getColumnModel().getColumn(5).setPreferredWidth(110);

		// Renderer personalizado con colores según reuniones
		tablaHorario.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value,
					boolean isSelected, boolean hasFocus, int row, int column) {

				Component c = super.getTableCellRendererComponent(table, value,
						isSelected, hasFocus, row, column);

				setHorizontalAlignment(CENTER);
				setVerticalAlignment(CENTER);

				// Obtener la clave para buscar si hay reunión
				String clave = row + "-" + column;
				Reuniones reunion = reunionesPorCelda.get(clave);

				if (!isSelected) {
					if (column == 0) {
						// Primera columna (Hora X)
						c.setBackground(new Color(240, 240, 240));
						setFont(new Font("Arial", Font.BOLD, 11));
					} else if (reunion != null) {
						// Hay reunión, aplicar color según estado
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
						// Sin reunión
						c.setBackground(Color.WHITE);
						setFont(new Font("Arial", Font.PLAIN, 10));
					}
				} else {
					c.setBackground(new Color(184, 207, 229));
				}

				return c;
			}
		});

		// Click en la celda para ver detalles de reunión
		tablaHorario.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tablaHorario.getSelectedRow();
				int col = tablaHorario.getSelectedColumn();

				if (row != -1 && col > 0) {
					String clave = row + "-" + col;
					Reuniones reunion = reunionesPorCelda.get(clave);

					if (reunion != null) {
						mostrarDetallesReunion(reunion);
					}
				}
			}
		});

		scrollPaneHorario.setViewportView(tablaHorario);

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
		btnVolver.setBounds(727, 560, 102, 40);
		contentPane.add(btnVolver);

		cargarHorario();
	}

	private void cargarHorario() {
		// Limpiar
		modeloHorario.setRowCount(0);
		reunionesPorCelda.clear();

		System.out.println("[CLIENTE] Iniciando carga de horario");

		// Crear filas vacías
		for (int i = 1; i <= 6; i++) {
			modeloHorario.addRow(new Object[]{"Hora " + i, "", "", "", "", ""});
		}

		// 1. Cargar horarios (asignaturas)
		Message respuesta = cliente.getHorario(user.getId());

		if (respuesta != null && "OK".equals(respuesta.getEstado())) {
			List<Horarios> horarios = respuesta.getHorarioList();

			if (horarios != null && !horarios.isEmpty()) {
				System.out.println("[CLIENTE] Procesando " + horarios.size() + " horarios");
				
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

						modeloHorario.setValueAt(nombreModulo, fila, columna);
					}
				}
			} else {
				System.out.println("[CLIENTE] No hay horarios para este profesor");
			}
		} else {
			System.err.println("[CLIENTE ERROR] No se pudo cargar el horario");
		}

		// 2. Cargar reuniones
		Message respuestaReuniones = cliente.getReunionesProfesor(user.getId());

		if (respuestaReuniones != null && "OK".equals(respuestaReuniones.getEstado())) {
			List<Reuniones> reuniones = respuestaReuniones.getReunionesList();

			if (reuniones != null && !reuniones.isEmpty()) {
				System.out.println("[CLIENTE] Procesando " + reuniones.size() + " reuniones");

				for (Reuniones r : reuniones) {
					if (r.getFecha() != null) {
						LocalDateTime fecha = r.getFecha();
						int hora = fecha.getHour();

						// Convertir hora a slot (1-6)
						int slot = convertirHoraASlot(hora);
						DayOfWeek dayOfWeek = fecha.getDayOfWeek();
						int columna = obtenerColumnaPorDia(dayOfWeek);

						if (slot >= 1 && slot <= 6 && columna > 0 && columna <= 5) {
							int fila = slot - 1;

							// Obtener contenido actual
							String contenidoActual = (String) modeloHorario.getValueAt(fila, columna);
							String contenidoNuevo;

							if (contenidoActual == null || contenidoActual.trim().isEmpty()) {
								contenidoNuevo = "Reunión";
							} else {
								contenidoNuevo = contenidoActual + "\n/ Reunión";
							}

							modeloHorario.setValueAt(contenidoNuevo, fila, columna);

							// Guardar reunión en el mapa
							String clave = fila + "-" + columna;
							reunionesPorCelda.put(clave, r);

							System.out.println("[CLIENTE] Reunión añadida: Fila=" + fila + ", Col=" + columna + ", Estado=" + r.getEstado());
						}
					}
				}
			}
		}

		tablaHorario.repaint();
		System.out.println("[CLIENTE] Carga completada");
	}

	/**
	 * Convierte la hora (0-23) a slot de horario (1-6)
	 */
	private int convertirHoraASlot(int hora) {
		// Asumiendo horarios:
		// Hora 1: 8:00-9:00
		// Hora 2: 9:00-10:00
		// Hora 3: 10:00-11:00
		// Hora 4: 11:00-12:00
		// Hora 5: 12:00-13:00
		// Hora 6: 13:00-14:00
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
}