package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import bll.Usuario;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.ImageIcon;

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
		contentPane.setBackground(new Color(179, 217, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Iniciar sesión");
		lblNewLabel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 30));
		lblNewLabel.setBounds(291, 75, 165, 37);
		contentPane.add(lblNewLabel);
		
		inputUsuario = new JTextField();
		inputUsuario.setBounds(102, 298, 179, 25);
		contentPane.add(inputUsuario);
		inputUsuario.setColumns(10);
		
		JLabel Usuario = new JLabel("Usuario");
		Usuario.setFont(new Font("Mongolian Baiti", Font.PLAIN, 15));
		Usuario.setBounds(102, 273, 83, 14);
		contentPane.add(Usuario);
		
		JLabel Pass = new JLabel("Contraseña");
		Pass.setFont(new Font("Mongolian Baiti", Font.PLAIN, 15));
		Pass.setBounds(466, 273, 83, 14);
		contentPane.add(Pass);
		
		inputPass = new JPasswordField();
		inputPass.setBounds(466, 298, 179, 25);
		contentPane.add(inputPass);
		
		JLabel lblerror = new JLabel("");
		lblerror.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblerror.setForeground(new Color(0, 0, 0));
		lblerror.setBounds(279, 406, 191, 25);
		contentPane.add(lblerror);
		
		//Botones
		JButton btnIniciar = new JButton("Ingresar");
		btnIniciar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 15));
		
		btnIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    char[] passwordChars = inputPass.getPassword(); 
			    String password = new String(passwordChars); 
			    Usuario encontrado = bll.Usuario.login(inputUsuario.getText(), password); 
			    
			    if (encontrado != null) {
			        dispose();
			        bll.Usuario.redirigir(encontrado); 
			    }else {
					lblerror.setText("Error, intente nuevamente.");
				}
			    
			}
		});//fin
		btnIniciar.setBounds(269, 334, 92, 25);
		contentPane.add(btnIniciar);
		
		JButton btnRegistrarse = new JButton("Registrarse");
		btnRegistrarse.setFont(new Font("Mongolian Baiti", Font.PLAIN, 15));
		btnRegistrarse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				
				Registro frameRegistro = new Registro();
				frameRegistro.setVisible(true);
			}
		});//fin
		btnRegistrarse.setBounds(371, 334, 107, 25);
		contentPane.add(btnRegistrarse);
		
		JButton btnOlvido = new JButton("Olvidé mi contraseña");
		btnOlvido.setFont(new Font("Mongolian Baiti", Font.PLAIN, 15));
		btnOlvido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose(); 
				
				Olvido frameOlvido = new Olvido();
				frameOlvido.setVisible(true); 
			}
		});//fin
		btnOlvido.setBounds(279, 370, 191, 25);
		contentPane.add(btnOlvido);
		
		JLabel lblNewLabel_1 = new JLabel("");
		ImageIcon originalIcon = new ImageIcon("C:\\Users\\victo\\Downloads\\househ.png");
		Image originalImage = originalIcon.getImage();
		int newWidth = 300;
		int newHeight = 200;
		Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
		originalIcon.setImage(scaledImage);
		lblNewLabel_1.setIcon(originalIcon);
		lblNewLabel_1.setBounds(232, 123, 293, 139);
		contentPane.add(lblNewLabel_1);
		
		
		
		
	}
}
