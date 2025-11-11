package ui;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import dll.DtoUsuario;

public class Olvido extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField inpuser;
	private JTextField inpresp;
	private JPasswordField inppass;
	private JPasswordField inppass2;
	private JLabel lblMensaje;

	private String respesperada = null;
	private String usertemp = null;

	private void mostrarMensaje(String mensaje, Color color) {
		lblMensaje.setForeground(color);
		lblMensaje.setText(mensaje);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Olvido frame = new Olvido();
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
	public Olvido() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 770, 650);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(179, 217, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblRec = new JLabel("Recuperar contraseña");
		lblRec.setFont(new Font("Mongolian Baiti", Font.PLAIN, 30));
		lblRec.setBounds(257, 94, 257, 37);
		contentPane.add(lblRec);

		JLabel Logo = new JLabel("");
		Logo.setBackground(new Color(255, 255, 255));
		java.net.URL imageUrl = getClass().getResource("/img/logohouse.png");
		ImageIcon originalIcon;
		if (imageUrl != null) {
			originalIcon = new ImageIcon(imageUrl);
		} else {
			originalIcon = new ImageIcon();
		}
		Image originalImage = originalIcon.getImage();
		int newWidth = 80;
		int newHeight = 100;
		Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
		originalIcon.setImage(scaledImage);
		Logo.setIcon(originalIcon);
		Logo.setBounds(24, 11, 83, 103);
		contentPane.add(Logo);

		JLabel Usuario = new JLabel("Nombre de usuario");
		Usuario.setFont(new Font("Mongolian Baiti", Font.PLAIN, 15));
		Usuario.setBounds(196, 178, 179, 14);
		contentPane.add(Usuario);

		inpuser = new JTextField();
		inpuser.setColumns(10);
		inpuser.setBounds(196, 203, 179, 25);
		contentPane.add(inpuser);

		JLabel preg = new JLabel("Pregunta de recuperación");
		preg.setFont(new Font("Mongolian Baiti", Font.PLAIN, 15));
		preg.setBounds(51, 298, 179, 14);
		contentPane.add(preg);

		JLabel lblRta = new JLabel("Respuesta de recuperación");
		lblRta.setFont(new Font("Mongolian Baiti", Font.PLAIN, 15));
		lblRta.setBounds(372, 298, 179, 14);
		contentPane.add(lblRta);

		inpresp = new JTextField();
		inpresp.setColumns(10);
		inpresp.setBounds(372, 323, 179, 25);
		contentPane.add(inpresp);

		JLabel lblPass = new JLabel("Nueva Contraseña (min 8 chars)");
		lblPass.setFont(new Font("Mongolian Baiti", Font.PLAIN, 15));
		lblPass.setBounds(51, 431, 250, 14);
		contentPane.add(lblPass);

		inppass = new JPasswordField();
		inppass.setBounds(51, 456, 179, 25);
		contentPane.add(inppass);

		JLabel Pass2 = new JLabel("Confirmar Contraseña");
		Pass2.setFont(new Font("Mongolian Baiti", Font.PLAIN, 15));
		Pass2.setBounds(372, 431, 179, 14);
		contentPane.add(Pass2);

		inppass2 = new JPasswordField();
		inppass2.setBounds(372, 456, 179, 25);
		contentPane.add(inppass2);

		JButton btnConf = new JButton("Confirmar contraseña");
		btnConf.setForeground(Color.BLACK);
		btnConf.setFont(new Font("Mongolian Baiti", Font.PLAIN, 15));
		btnConf.setBackground(Color.WHITE);
		btnConf.setBounds(562, 456, 167, 25);
		contentPane.add(btnConf);

		JButton btnAtrs = new JButton("Atrás");
		btnAtrs.setFont(new Font("Mongolian Baiti", Font.PLAIN, 15));
		btnAtrs.setBounds(547, 515, 72, 25);
		contentPane.add(btnAtrs);

		JLabel preg_1 = new JLabel("...");
		preg_1.setFont(new Font("Mongolian Baiti", Font.PLAIN, 15));
		preg_1.setBounds(51, 323, 280, 30);
		contentPane.add(preg_1);

		JButton btnsend = new JButton("Enviar Usuario");
		btnsend.setFont(new Font("Mongolian Baiti", Font.PLAIN, 15));
		btnsend.setBounds(436, 203, 130, 25);
		contentPane.add(btnsend);

		JButton btnsend_1 = new JButton("Enviar Respuesta");
		btnsend_1.setFont(new Font("Mongolian Baiti", Font.PLAIN, 15));
		btnsend_1.setBounds(562, 323, 167, 25);
		contentPane.add(btnsend_1);

		lblMensaje = new JLabel("");
		lblMensaje.setFont(new Font("Mongolian Baiti", Font.BOLD, 14));
		lblMensaje.setBounds(132, 492, 500, 18);
		contentPane.add(lblMensaje);

		inpresp.setEnabled(false);
		btnsend_1.setEnabled(false);
		inppass.setEnabled(false);
		inppass2.setEnabled(false);
		btnConf.setEnabled(false);

		preg_1.setText("...");

		btnsend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String user = inpuser.getText();
				mostrarMensaje("", Color.BLACK);

				if (user.trim().isEmpty()) {
					mostrarMensaje("Ingrese su nombre de usuario.", Color.black);
					return;
				}

				String[] recoveryData = DtoUsuario.busquedaPregunta(user);

				if (recoveryData != null) {
					usertemp = user;
					String question = recoveryData[0];
					respesperada = recoveryData[1];

					preg_1.setText(question);

					inpresp.setEnabled(true);
					btnsend_1.setEnabled(true);

					inpuser.setEnabled(false);
					btnsend.setEnabled(false);

					mostrarMensaje("Usuario encontrado. Responda la pregunta para continuar.", Color.black);
				} else {
					preg_1.setText("...");
					mostrarMensaje("Error: Usuario no encontrado.", Color.black);
				}
			}
		});

		btnsend_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String respuesta = inpresp.getText();
				mostrarMensaje("", Color.BLACK);

				if (respuesta.trim().isEmpty()) {
					mostrarMensaje("Ingrese la respuesta de seguridad.", Color.black);
					return;
				}

				String respuestaLimpia = respuesta.trim();

				if (respuestaLimpia.equals(respesperada)) {
					mostrarMensaje("Respuesta correcta. Ingrese y confirme su nueva contraseña.", new Color(0, 100, 0));

					inpresp.setEnabled(false);
					btnsend_1.setEnabled(false);

					inppass.setEnabled(true);
					inppass2.setEnabled(true);
					btnConf.setEnabled(true);
				} else {
					mostrarMensaje("Error: Respuesta incorrecta. Intente de nuevo.", Color.RED);
					inpresp.setText("");
				}
			}
		});

		btnConf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String pass1 = new String(inppass.getPassword());
				String pass2 = new String(inppass2.getPassword());
				mostrarMensaje("", Color.BLACK);

				if (pass1.length() < 8) {
					mostrarMensaje("Error: La contraseña debe tener al menos 8 caracteres.", Color.black);
					return;
				}

				if (!pass1.equals(pass2)) {
					mostrarMensaje("Error: Las contraseñas no coinciden.", Color.black);
					return;
				}

				boolean actualizado = DtoUsuario.actualizarPass(usertemp, pass1);

				if (actualizado) {
					mostrarMensaje("Contraseña actualizada con éxito. Redirigiendo...", new Color(0, 100, 0));

					dispose();
					try {
						Index frameIndex = new Index();
						frameIndex.setVisible(true);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				} else {
					mostrarMensaje("Error: Falló al actualizar la contraseña en la base de datos.", Color.black);
				}

			}
		});

		btnAtrs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();

				try {
					Index frameIndex = new Index();
					frameIndex.setVisible(true);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

		});

	}
}