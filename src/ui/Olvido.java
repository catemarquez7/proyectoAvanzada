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
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

public class Olvido extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField inpuser;
	private JTextField inppreg;
	private JTextField inpresp;
	private JPasswordField inppass;
	private JPasswordField inppass2;

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
		
		JLabel Usuario = new JLabel("Nombre de usuario");
		Usuario.setFont(new Font("Mongolian Baiti", Font.PLAIN, 15));
		Usuario.setBounds(292, 155, 179, 14);
		contentPane.add(Usuario);
		
		inpuser = new JTextField();
		inpuser.setColumns(10);
		inpuser.setBounds(292, 180, 179, 25);
		contentPane.add(inpuser);
		
		JLabel preg = new JLabel("Pregunta de recuperación");
		preg.setFont(new Font("Mongolian Baiti", Font.PLAIN, 15));
		preg.setBounds(132, 238, 179, 14);
		contentPane.add(preg);
		
		inppreg = new JTextField();
		inppreg.setColumns(10);
		inppreg.setBounds(132, 263, 179, 25);
		contentPane.add(inppreg);
		
		JLabel lblRta = new JLabel("Respuesta de recuperación");
		lblRta.setFont(new Font("Mongolian Baiti", Font.PLAIN, 15));
		lblRta.setBounds(440, 238, 179, 14);
		contentPane.add(lblRta);
		
		inpresp = new JTextField();
		inpresp.setColumns(10);
		inpresp.setBounds(440, 263, 179, 25);
		contentPane.add(inpresp);
		
		JLabel lblPass = new JLabel("Nueva Contraseña");
		lblPass.setFont(new Font("Mongolian Baiti", Font.PLAIN, 15));
		lblPass.setBounds(132, 299, 179, 14);
		contentPane.add(lblPass);
		
		inppass = new JPasswordField();
		inppass.setBounds(132, 324, 179, 25);
		contentPane.add(inppass);
		
		JLabel Pass2 = new JLabel("Confirmar Contraseña");
		Pass2.setFont(new Font("Mongolian Baiti", Font.PLAIN, 15));
		Pass2.setBounds(440, 299, 179, 14);
		contentPane.add(Pass2);
		
		inppass2 = new JPasswordField();
		inppass2.setBounds(440, 324, 179, 25);
		contentPane.add(inppass2);
		
		JButton btnConf = new JButton("Confirmar contraseña");
		btnConf.setForeground(Color.BLACK);
		btnConf.setFont(new Font("Mongolian Baiti", Font.PLAIN, 15));
		btnConf.setBackground(Color.WHITE);
		btnConf.setBounds(292, 389, 188, 37);
		contentPane.add(btnConf);
	}

}//fin
