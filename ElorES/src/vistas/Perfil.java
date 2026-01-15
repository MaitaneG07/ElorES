package vistas;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Perfil extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblNombre;
	private JLabel lblTelefono2;
	private JLabel lblTelefono1;
	private JLabel lblDireccion;
	private JLabel lblDni;
	private JLabel lblEmail;
	private JLabel lblApellidos;
	private JLabel lblFoto;
	private JLabel lbl_Nombre;
	private JLabel lbl_Apellidos;
	private JLabel lbl_Email;
	private JLabel lbl_Dni;
	private JLabel lbl_Direccion;
	private JLabel lbl_Telefono1;
	private JLabel lbl_Telefono2;
	private JButton btnVolver;

	/**
	 * Create the frame.
	 */
	public Perfil() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 855, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        lblNombre = new JLabel("Nombre");
        lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
        lblNombre.setForeground(new Color(65, 105, 225));
        lblNombre.setBounds(384, 99, 106, 20);
        contentPane.add(lblNombre);
        
        lblApellidos = new JLabel("Apellidos");
        lblApellidos.setHorizontalAlignment(SwingConstants.CENTER);
        lblApellidos.setForeground(new Color(65, 105, 225));
        lblApellidos.setBounds(384, 158, 106, 17);
        contentPane.add(lblApellidos);
        
        lblEmail = new JLabel("Email");
        lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
        lblEmail.setForeground(new Color(65, 105, 225));
        lblEmail.setBounds(384, 217, 106, 17);
        contentPane.add(lblEmail);
        
        lblDni = new JLabel("DNI");
        lblDni.setHorizontalAlignment(SwingConstants.CENTER);
        lblDni.setForeground(new Color(65, 105, 225));
        lblDni.setBounds(384, 276, 106, 17);
        contentPane.add(lblDni);
        
        lblDireccion = new JLabel("Dirección");
        lblDireccion.setHorizontalAlignment(SwingConstants.CENTER);
        lblDireccion.setForeground(new Color(65, 105, 225));
        lblDireccion.setBounds(384, 335, 106, 17);
        contentPane.add(lblDireccion);
        
        lblTelefono1 = new JLabel("Teléfono 1");
        lblTelefono1.setHorizontalAlignment(SwingConstants.CENTER);
        lblTelefono1.setForeground(new Color(65, 105, 225));
        lblTelefono1.setBounds(384, 397, 106, 17);
        contentPane.add(lblTelefono1);
        
        lblTelefono2 = new JLabel("Teléfono 2");
        lblTelefono2.setHorizontalAlignment(SwingConstants.CENTER);
        lblTelefono2.setForeground(new Color(65, 105, 225));
        lblTelefono2.setBounds(384, 456, 106, 17);
        contentPane.add(lblTelefono2);
        
        
        //LABELS PARA MOSTRAR EL CONTENIDO 
        
        lblFoto = new JLabel("Aquí va la foto");
        lblFoto.setBounds(58, 168, 284, 233);
        contentPane.add(lblFoto);
        
        lbl_Nombre = new JLabel("");
        lbl_Nombre.setBounds(546, 99, 106, 17);
        contentPane.add(lbl_Nombre);
        
        lbl_Apellidos = new JLabel("");
        lbl_Apellidos.setBounds(546, 158, 106, 17);
        contentPane.add(lbl_Apellidos);
        
        lbl_Email = new JLabel("");
        lbl_Email.setBounds(546, 217, 106, 17);
        contentPane.add(lbl_Email);
        
        lbl_Dni = new JLabel("");
        lbl_Dni.setBounds(546, 276, 106, 17);
        contentPane.add(lbl_Dni);
        
        lbl_Direccion = new JLabel("");
        lbl_Direccion.setBounds(546, 335, 106, 17);
        contentPane.add(lbl_Direccion);
        
        lbl_Telefono1 = new JLabel("");
        lbl_Telefono1.setBounds(546, 397, 106, 17);
        contentPane.add(lbl_Telefono1);
        
        lbl_Telefono2 = new JLabel("");
        lbl_Telefono2.setBounds(546, 457, 106, 17);
        contentPane.add(lbl_Telefono2);
        
        btnVolver = new JButton("VOLVER");
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
        btnVolver.setBounds(697, 512, 106, 31);
        contentPane.add(btnVolver);

        /* Foto
         * email
         * username
         * nomobre
         * apellidos
         * DNI
         * direccion
         * telefono1
         * telefono2
         * */
        
        
	}
}
