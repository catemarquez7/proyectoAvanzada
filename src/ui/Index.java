package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import bll.Usuario;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Index extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField inputUsuario;
	private JPasswordField inputPass;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Index frame = new Index();
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
	public Index() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 770, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Iniciar sesión");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblNewLabel.setBounds(282, 105, 179, 37);
		contentPane.add(lblNewLabel);
		
		inputUsuario = new JTextField();
		inputUsuario.setBounds(282, 186, 179, 25);
		contentPane.add(inputUsuario);
		inputUsuario.setColumns(10);
		
		JLabel Usuario = new JLabel("Usuario");
		Usuario.setFont(new Font("Tahoma", Font.PLAIN, 15));
		Usuario.setBounds(282, 161, 83, 14);
		contentPane.add(Usuario);
		
		JLabel Pass = new JLabel("Contraseña");
		Pass.setFont(new Font("Tahoma", Font.PLAIN, 15));
		Pass.setBounds(282, 239, 83, 14);
		contentPane.add(Pass);
		
		inputPass = new JPasswordField();
		inputPass.setBounds(282, 264, 179, 25);
		contentPane.add(inputPass);
		
		JButton btnIniciar = new JButton("Ingresar");
		btnIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Usuario encontrado = Usuario.login(inputUsuario.getText(), inputPass.getText());
				if (encontrado != null) {
					JOptionPane.showMessageDialog(null, "Bienvenido " + encontrado);
				}else {
					JOptionPane.showMessageDialog(null, "Error al ingresar");

				}
			}
		});
		btnIniciar.setBounds(325, 300, 89, 23);
		contentPane.add(btnIniciar);
		
		
	}
}
