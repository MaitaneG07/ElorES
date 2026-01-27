package elorESClient.vistas;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import elorESClient.Cliente;
import elorESClient.modelo.entities.Horarios;
import elorESClient.modelo.entities.Users;
import java.awt.Component;
import javax.swing.table.DefaultTableCellRenderer;
import elorESClient.modelo.message.Message;

public class ConsultaHorario extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private int idProfesor;
	private JScrollPane scrollPaneHorario;
	private DefaultTableModel modeloHorario;
	private JTable tablaHorario;
	private Users user;
	private Cliente cliente;
	private Message respuesta;

	/**
	 * Create the frame.
	 * 
	 * @param cliente
	 * @param users
	 */
	public ConsultaHorario(Users user, Cliente cliente) {

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
		modeloHorario.addColumn("");
		modeloHorario.addColumn("LUNES");
		modeloHorario.addColumn("MARTES");
		modeloHorario.addColumn("MIÉRCOLES");
		modeloHorario.addColumn("JUEVES");
		modeloHorario.addColumn("VIERNES");

		tablaHorario = new JTable(modeloHorario);
		tablaHorario.setRowHeight(50);

		// Ajustar el ancho de las columnas
		tablaHorario.getColumnModel().getColumn(0).setPreferredWidth(80);  // Columna "Hora X"
		tablaHorario.getColumnModel().getColumn(1).setPreferredWidth(150); // LUNES
		tablaHorario.getColumnModel().getColumn(2).setPreferredWidth(150); // MARTES
		tablaHorario.getColumnModel().getColumn(3).setPreferredWidth(150); // MIÉRCOLES
		tablaHorario.getColumnModel().getColumn(4).setPreferredWidth(150); // JUEVES
		tablaHorario.getColumnModel().getColumn(5).setPreferredWidth(150); // VIERNES

		// Para que el texto largo se ajuste en varias líneas, usa un renderer personalizado
		tablaHorario.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
		    private static final long serialVersionUID = 1L;
		    
		    @Override
		    public Component getTableCellRendererComponent(JTable table, Object value,
		            boolean isSelected, boolean hasFocus, int row, int column) {
		        
		        Component c = super.getTableCellRendererComponent(table, value, 
		                isSelected, hasFocus, row, column);
		        
		        // Centrar el texto
		        setHorizontalAlignment(CENTER);
		        setVerticalAlignment(CENTER);
		        
		        return c;
		    }
		});

		scrollPaneHorario.setViewportView(tablaHorario);

		scrollPaneHorario.setViewportView(tablaHorario);

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
		btnVolver.setForeground(Color.WHITE);
		btnVolver.setBackground(new Color(65, 105, 225));
		btnVolver.setBounds(727, 514, 102, 40);
		contentPane.add(btnVolver);

		cargarHorario(modeloHorario);
	}

	private void cargarHorario(DefaultTableModel modeloHorario2) {
	    System.out.println("[CLIENTE] Iniciando carga de horario");
	    
	    // 1. Crear las 6 filas vacías (sin el id)
	    for (int i = 1; i <= 6; i++) {
	        modeloHorario.addRow(new Object[] { "Hora " + i, "", "", "", "", "" });
	    }
	    
	    System.out.println("[CLIENTE] Filas creadas, pidiendo horario al servidor");

	    // 2. Pedir al servidor
	    respuesta = cliente.getHorario(user.getId());
	    
	    System.out.println("[CLIENTE] Respuesta recibida: " + (respuesta != null ? "OK" : "NULL"));
	    
	    if (respuesta != null) {
	        System.out.println("[CLIENTE] Estado: " + respuesta.getEstado());
	        System.out.println("[CLIENTE] Mensaje: " + respuesta.getMensaje());
	    }

	    // 3. Comprobar
	    if (respuesta == null || !respuesta.getEstado().equals("OK")) {
	        JOptionPane.showMessageDialog(this, "Error al cargar el horario");
	        return;
	    }

	    // 4. Obtener lista
	    List<Horarios> horarios = respuesta.getHorarioList();
	    
	    System.out.println("[CLIENTE] Lista de horarios: " + (horarios != null ? horarios.size() + " elementos" : "NULL"));

	    if (horarios == null || horarios.isEmpty()) {
	        JOptionPane.showMessageDialog(this, "No hay horarios disponibles");
	        return;
	    }

	    // 5. Rellenar
	    System.out.println("[CLIENTE] Rellenando tabla...");
	    for (Horarios h : horarios) {
	        System.out.println("[CLIENTE] Procesando: Día=" + h.getDia() + ", Hora=" + h.getHora());
	        
	        int fila = h.getHora() - 1;
	        int columna = getColumnaDia(h.getDia());
	        
	        System.out.println("[CLIENTE] Fila=" + fila + ", Columna=" + columna);

	        String nombreModulo = h.getModulos().getNombre();
	        
	        System.out.println("[CLIENTE] Módulo: " + nombreModulo);

	        if (h.getObservaciones() != null) {
	            if (h.getObservaciones().contains("Tutoria")) {
	                nombreModulo = "Tutoría";
	            } else if (h.getObservaciones().contains("Guardia")) {
	                nombreModulo = "Guardia";
	            }
	        }

	        modeloHorario.setValueAt(nombreModulo, fila, columna);
	        System.out.println("[CLIENTE] Valor insertado en tabla");
	    }
	    
	    System.out.println("[CLIENTE] Carga completada");
	}

	// Método para convertir el día en número de columna
	private int getColumnaDia(String dia) {
		switch (dia.toUpperCase()) {
		case "LUNES":
			return 1;
		case "MARTES":
			return 2;
		case "MIERCOLES":
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
