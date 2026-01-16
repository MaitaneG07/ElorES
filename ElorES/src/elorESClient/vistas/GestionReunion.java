package elorESClient.vistas;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class GestionReunion extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private int idProfesor;

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
	public GestionReunion() {
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

	}

}
