package ui;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.JEditorPane;

public class Encargado extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtReservas;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Encargado frame = new Encargado();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Encargado() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 770, 650);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(179, 217, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane Contenidos = new JTabbedPane(JTabbedPane.TOP);
		Contenidos.setBounds(0, 178, 754, 433);
		Contenidos.setFont(new Font("Mongolian Baiti", Font.PLAIN, 15));
		contentPane.add(Contenidos);
		
		//RESERVAS
		JPanel panelReservas = new JPanel();
		panelReservas.setLayout(new java.awt.GridLayout(1, 3, 10, 0));
		panelReservas.setBackground(Color.WHITE);

		JPanel panelTerminadas = new JPanel();
		panelTerminadas.setBackground(new Color(255, 255, 255)); 
		panelTerminadas.setLayout(null);
		JLabel label = new JLabel("Reservas pendientes");
		label.setBounds(128, 5, 112, 17);
		label.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		panelTerminadas.add(label);

		JPanel panelPendientes = new JPanel();
		panelPendientes.setBackground(new Color(255, 255, 255)); 
		panelPendientes.setLayout(null);
		JLabel label_2 = new JLabel("Reservas finalizadas");
		label_2.setBounds(128, 5, 113, 17);
		label_2.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		panelPendientes.add(label_2);

		panelReservas.add(panelTerminadas);
		
		//contenido de reservas
		JLabel listpendientes = new JLabel("");
		listpendientes.setBounds(10, 25, 349, 323);
		panelTerminadas.add(listpendientes);
		panelReservas.add(panelPendientes);
		
		JLabel listfinalizadas = new JLabel("");
		listfinalizadas.setBounds(10, 27, 349, 321);
		panelPendientes.add(listfinalizadas);

		Contenidos.addTab("Reservas", null, panelReservas, null);
		
		JPanel panelHabitaciones = new JPanel();
		Contenidos.addTab("Habitaciones", null, panelHabitaciones, null);
		
		JPanel panelDCliente = new JPanel();
		Contenidos.addTab("Datos Clientes", null, panelDCliente, null);
		
		JPanel panelCheckin = new JPanel();
		Contenidos.addTab("Check-in", null, panelCheckin, null);
		
		JPanel panelCheckout = new JPanel();
		Contenidos.addTab("Check-out", null, panelCheckout, null);
		
		JPanel panelVista = new JPanel();
		Contenidos.addTab("Vistas", null, panelVista, null);
		
		JPanel panelPromociones = new JPanel();
		Contenidos.addTab("Promociones", null, panelPromociones, null);
		
		
		JButton btnclose = new JButton("Cerrar sesión");
		btnclose.setBounds(620, 11, 124, 25);
		btnclose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				
				Index frameIndex = new Index();
				frameIndex.setVisible(true);
			}
		});
		btnclose.setFont(new Font("Mongolian Baiti", Font.PLAIN, 15));
		contentPane.add(btnclose);
		
		JLabel Perfil = new JLabel("");
		Perfil.setBackground(new Color(255, 255, 255));
		java.net.URL imageUrl = getClass().getResource("/img/encargado.png");
		ImageIcon originalIcon;
		originalIcon = new ImageIcon(imageUrl);
		Image originalImage = originalIcon.getImage();
		int newWidth = 120;
		int newHeight = 120;
		Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
		originalIcon.setImage(scaledImage);
		Perfil.setIcon(originalIcon);
		Perfil.setBounds(10, 11, 148, 153);
		contentPane.add(Perfil);
		
		JLabel lblbienvenido = new JLabel("Bienvenido");
		lblbienvenido.setFont(new Font("Mongolian Baiti", Font.PLAIN, 18));
		lblbienvenido.setBounds(168, 65, 137, 14);
		contentPane.add(lblbienvenido);
		
		JLabel lblHotel = new JLabel("Administracion Hotel - ");
		lblHotel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 20));
		lblHotel.setBounds(168, 29, 366, 25);
		contentPane.add(lblHotel);
		
	}
}
