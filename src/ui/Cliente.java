package ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import bll.Paquete;
import bll.Usuario;
import dll.DtoCliente;

public class Cliente extends JFrame {
	  private Usuario usuario;
	  private bll.Cliente cliente;



	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	
	public Cliente(Usuario usuario, bll.Cliente cliente2) {
		this.cliente = cliente2;
        this.usuario = usuario;
        iniciar(this.usuario, this.cliente);
    }
	
	
	public void iniciar(Usuario usuario,bll. Cliente cliente) {
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
		
		JPanel panelPaquetesReco = new JPanel();

		
		panelPaquetesReco.setLayout(new java.awt.GridLayout(1, 1, 10, 0));
		panelPaquetesReco.setBackground(Color.WHITE);

		JPanel panelRecomendados = new JPanel();
		panelRecomendados.setBackground(new Color(255, 255, 255)); 
		panelRecomendados.setLayout(null);
		JLabel label = new JLabel("Paquetes recomendados segun sus preferencias.");
		label.setBounds(128, 5, 388, 17);
		label.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		panelRecomendados.add(label);
		
		
		List<Paquete> paquetes = DtoCliente.verPaquetesReco(usuario, cliente);

		String[] columnas = {"Hotel", "Actividad", "Precio", "Inicio", "Fin", "Cupo"};
		

		// Crear el modelo de tabla
		DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

		// Rellenar la tabla con los paquetes
		for (Paquete p : paquetes) {
		    Object[] fila = {
		        p.getHotel().getNombre(),
		        p.getActividad().getNombre(),
		        "$" + String.format("%.2f", p.getPrecio()),
		        p.getInicioDate(),
		        p.getFinDate(),
		        p.getCupo_actual() + "/" + p.getCupo_maximo()
		    };
		    modelo.addRow(fila);
		}

		// Crear la JTable y agregarle el modelo
		JTable tabla = new JTable(modelo);
		tabla.setFont(new Font("Mongolian Baiti", Font.PLAIN, 13));
		tabla.setRowHeight(25);

		// Agregar la tabla dentro de un JScrollPane (para que tenga scroll)
		JScrollPane scroll = new JScrollPane(tabla);
		scroll.setBounds(20, 40, 700, 300);
		panelRecomendados.add(scroll);
		
		panelPaquetesReco.add(panelRecomendados);
		
		Contenidos.addTab("Paquetes recomendados", null, panelPaquetesReco, null);
		
		
		
		
		JPanel panelPaquetes = new JPanel();
		//aca crear todo el contenido
		Contenidos.addTab("Paquetes", null, panelPaquetes, null);
		
		JPanel panelReservas = new JPanel();
		//aca crear todo el contenido
		Contenidos.addTab("Mis reservas", null, panelReservas, null);
		
		JPanel panelReviews = new JPanel();
		//aca crear todo el contenido
		Contenidos.addTab("Reseñas", null, panelReviews, null);
		
		JPanel panelPreferencias = new JPanel();
		//aca crear todo el contenido
		Contenidos.addTab("Preferencias", null, panelPreferencias, null);
		
		
		
		
		
		//extras
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
		java.net.URL imageUrl = getClass().getResource("/img/cliente.png");
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
		
		JLabel lblHotel = new JLabel("House Hunter: reservá tus mejores vacaciones. ");
		lblHotel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 20));
		lblHotel.setBounds(168, 29, 485, 25);
		contentPane.add(lblHotel);
		
		
		
		
	}//fin

}
