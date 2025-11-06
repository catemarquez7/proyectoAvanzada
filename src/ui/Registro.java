package ui;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.Image;

import com.toedter.calendar.JDateChooser;
import java.time.ZoneId;
import java.time.LocalDate;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Registro extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField inpNombre;
	private JTextField inpApellido;

	private JTextField inpUser;
	private JTextField inpDireccion;
	private JTextField inpDni;
	private JTextField inpMail;

	private JPasswordField inpPass;
	private JPasswordField inpConpass;

	private JTextField inpPreg;
	private JTextField inpRta;

	private JDateChooser dateChooserNac;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Registro frame = new Registro();
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
	public Registro() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 770, 650);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(179, 217, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblRegistrarse = new JLabel("Registrarse");
		lblRegistrarse.setBackground(new Color(240, 240, 240));
		lblRegistrarse.setFont(new Font("Mongolian Baiti", Font.PLAIN, 30));
		lblRegistrarse.setBounds(311, 85, 134, 37);
		contentPane.add(lblRegistrarse);

		inpNombre = new JTextField();
		inpNombre.setColumns(10);
		inpNombre.setBounds(127, 182, 179, 25);
		contentPane.add(inpNombre);

		JLabel Nombre = new JLabel("Nombre");
		Nombre.setFont(new Font("Mongolian Baiti", Font.PLAIN, 15));
		Nombre.setBounds(127, 157, 83, 14);
		contentPane.add(Nombre);

		inpApellido = new JTextField();
		inpApellido.setColumns(10);
		inpApellido.setBounds(127, 243, 179, 25);
		contentPane.add(inpApellido);

		JLabel Apellido = new JLabel("Apellido");
		Apellido.setFont(new Font("Mongolian Baiti", Font.PLAIN, 15));
		Apellido.setBounds(127, 218, 83, 14);
		contentPane.add(Apellido);

		dateChooserNac = new JDateChooser();
		dateChooserNac.setBounds(127, 302, 179, 25);
		contentPane.add(dateChooserNac);

		JLabel Nac = new JLabel("Fecha de nacimiento");
		Nac.setFont(new Font("Mongolian Baiti", Font.PLAIN, 15));
		Nac.setBounds(127, 277, 179, 14);
		contentPane.add(Nac);

		inpUser = new JTextField();
		inpUser.setColumns(10);
		inpUser.setBounds(127, 363, 179, 25);
		contentPane.add(inpUser);

		JLabel Usuario = new JLabel("Nombre de usuario");
		Usuario.setFont(new Font("Mongolian Baiti", Font.PLAIN, 15));
		Usuario.setBounds(127, 338, 179, 14);
		contentPane.add(Usuario);

		inpDireccion = new JTextField();
		inpDireccion.setColumns(10);
		inpDireccion.setBounds(440, 243, 179, 25);
		contentPane.add(inpDireccion);

		JLabel Direccion = new JLabel("Direccion");
		Direccion.setFont(new Font("Mongolian Baiti", Font.PLAIN, 15));
		Direccion.setBounds(440, 218, 83, 14);
		contentPane.add(Direccion);

		inpDni = new JTextField();
		inpDni.setColumns(10);
		inpDni.setBounds(440, 182, 179, 25);
		contentPane.add(inpDni);

		JLabel Dni = new JLabel("Dni");
		Dni.setFont(new Font("Mongolian Baiti", Font.PLAIN, 15));
		Dni.setBounds(440, 157, 83, 14);
		contentPane.add(Dni);

		inpMail = new JTextField();
		inpMail.setColumns(10);
		inpMail.setBounds(440, 302, 179, 25);
		contentPane.add(inpMail);

		JLabel Mail = new JLabel("Mail");
		Mail.setFont(new Font("Mongolian Baiti", Font.PLAIN, 15));
		Mail.setBounds(440, 277, 179, 14);
		contentPane.add(Mail);

		// --- Cambio a JPasswordField ---
		inpPass = new JPasswordField();
		inpPass.setBounds(440, 363, 179, 25);
		contentPane.add(inpPass);

		JLabel Pass = new JLabel("Contraseña");
		Pass.setFont(new Font("Mongolian Baiti", Font.PLAIN, 15));
		Pass.setBounds(440, 338, 179, 14);
		contentPane.add(Pass);

		JLabel pass2 = new JLabel("Confirmar Contraseña");
		pass2.setFont(new Font("Mongolian Baiti", Font.PLAIN, 15));
		pass2.setBounds(440, 399, 179, 14);
		contentPane.add(pass2);

		inpPreg = new JTextField();
		inpPreg.setColumns(10);
		inpPreg.setBounds(127, 424, 179, 25);
		contentPane.add(inpPreg);

		// --- Cambio a JPasswordField ---
		inpConpass = new JPasswordField();
		inpConpass.setBounds(440, 424, 179, 25);
		contentPane.add(inpConpass);

		JLabel preg = new JLabel("Pregunta de recuperación");
		preg.setFont(new Font("Mongolian Baiti", Font.PLAIN, 15));
		preg.setBounds(127, 399, 179, 14);
		contentPane.add(preg);

		JLabel lblRespuestaDeRecuperacin = new JLabel("Respuesta de recuperación");
		lblRespuestaDeRecuperacin.setFont(new Font("Mongolian Baiti", Font.PLAIN, 15));
		lblRespuestaDeRecuperacin.setBounds(127, 460, 179, 14);
		contentPane.add(lblRespuestaDeRecuperacin);

		inpRta = new JTextField();
		inpRta.setColumns(10);
		inpRta.setBounds(127, 485, 179, 25);
		contentPane.add(inpRta);

		JButton btnConfirmarRegistro = new JButton("Confirmar registro");
		btnConfirmarRegistro.setBackground(new Color(255, 255, 255));
		btnConfirmarRegistro.setForeground(new Color(0, 0, 0));

		// --- Lógica del botón con JDateChooser ---
		btnConfirmarRegistro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// 1. Obtener la contraseña y confirmación
				String pass = new String(inpPass.getPassword());
				String conPass = new String(inpConpass.getPassword());

				if (!pass.equals(conPass)) {
					JOptionPane.showMessageDialog(contentPane, "Las contraseñas no coinciden. Por favor, verifique.",
							"Error de Contraseña", JOptionPane.ERROR_MESSAGE);
					return;
				}

				java.util.Date fechaUtil = dateChooserNac.getDate();
				if (fechaUtil == null) {
					JOptionPane.showMessageDialog(contentPane, "Debe seleccionar su fecha de nacimiento.", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				LocalDate fecha_nac_local = fechaUtil.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

				try {
					int dni = Integer.parseInt(inpDni.getText());

					boolean exito = bll.Usuario.registrarse(inpNombre.getText(), inpApellido.getText(),
							fecha_nac_local, inpMail.getText(), dni, inpDireccion.getText(), inpUser.getText(), pass,
							inpPreg.getText(), inpRta.getText());

					if (exito) {
						JOptionPane.showMessageDialog(contentPane, "¡Registro completado! Ya puedes iniciar sesión.",
								"Éxito", JOptionPane.INFORMATION_MESSAGE);

						dispose();
						Index frameIndex = new Index();
						frameIndex.setVisible(true);
					}

				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(contentPane, "El DNI no tiene formato correcto o es muy largo.",
							"Error de Formato", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnConfirmarRegistro.setFont(new Font("Mongolian Baiti", Font.PLAIN, 15));
		btnConfirmarRegistro.setBounds(440, 479, 188, 37);
		contentPane.add(btnConfirmarRegistro);
		
		JLabel lblNewLabel_1 = new JLabel("");
		ImageIcon originalIcon = new ImageIcon("C:\\Users\\victo\\Downloads\\logohouse.png");
		Image originalImage = originalIcon.getImage();
		int newWidth = 80;
		int newHeight = 100;
		Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
		originalIcon.setImage(scaledImage);
		lblNewLabel_1.setIcon(originalIcon);
		lblNewLabel_1.setBounds(24, 11, 83, 103);
		contentPane.add(lblNewLabel_1);
		
	}
}