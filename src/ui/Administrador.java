package ui;

import java.awt.Color;
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
import bll.Hotel;
import bll.Usuario;
import dll.DtoAdministrador;

public class Administrador extends JFrame {
	private Usuario usuario;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public Administrador(Usuario usuario) {
		this.usuario = usuario;
		iniciar(this.usuario);
	}

	public void iniciar(Usuario usuario) {
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

		JPanel panelHoteles = new JPanel();
		panelHoteles.setBackground(Color.WHITE);
		panelHoteles.setLayout(null);

		JLabel lblHoteles = new JLabel("Gestión de Hoteles");
		lblHoteles.setBounds(280, 10, 200, 25);
		lblHoteles.setFont(new Font("Mongolian Baiti", Font.BOLD, 18));
		panelHoteles.add(lblHoteles);

		// Tabla de hoteles
		String[] columnasHoteles = { "ID", "Nombre", "Provincia", "Dirección", "Habitaciones", "Calificación" };
		DefaultTableModel modeloHoteles = new DefaultTableModel(columnasHoteles, 0);
		JTable tablaHoteles = new JTable(modeloHoteles);
		tablaHoteles.setFont(new Font("Mongolian Baiti", Font.PLAIN, 13));
		tablaHoteles.setRowHeight(25);
		JScrollPane scrollHoteles = new JScrollPane(tablaHoteles);
		scrollHoteles.setBounds(20, 50, 700, 250);
		panelHoteles.add(scrollHoteles);

		// Botones
		JButton btnVerHoteles = new JButton("Actualizar");
		btnVerHoteles.setBounds(50, 320, 120, 30);
		btnVerHoteles.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		btnVerHoteles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actualizarTablaHoteles(modeloHoteles);
			}
		});
		panelHoteles.add(btnVerHoteles);

		JButton btnCrearHotel = new JButton("Crear Hotel");
		btnCrearHotel.setBounds(190, 320, 120, 30);
		btnCrearHotel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		btnCrearHotel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bll.Administrador.crearHotel();
				actualizarTablaHoteles(modeloHoteles);
			}
		});
		panelHoteles.add(btnCrearHotel);

		JButton btnModificarHotel = new JButton("Modificar");
		btnModificarHotel.setBounds(330, 320, 120, 30);
		btnModificarHotel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		btnModificarHotel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bll.Administrador.modificarHotel();
				actualizarTablaHoteles(modeloHoteles);
			}
		});
		panelHoteles.add(btnModificarHotel);

		JButton btnEliminarHotel = new JButton("Eliminar");
		btnEliminarHotel.setBounds(470, 320, 120, 30);
		btnEliminarHotel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		btnEliminarHotel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bll.Administrador.eliminarHotel();
				actualizarTablaHoteles(modeloHoteles);
			}
		});
		panelHoteles.add(btnEliminarHotel);

		Contenidos.addTab("Hoteles", null, panelHoteles, null);

		JPanel panelPaquetes = new JPanel();
		panelPaquetes.setBackground(Color.WHITE);
		panelPaquetes.setLayout(null);

		JLabel lblPaquetes = new JLabel("Gestión de Paquetes");
		lblPaquetes.setBounds(270, 10, 250, 25);
		lblPaquetes.setFont(new Font("Mongolian Baiti", Font.BOLD, 18));
		panelPaquetes.add(lblPaquetes);

		JButton btnVerPaquetes = new JButton("Ver Paquetes");
		btnVerPaquetes.setBounds(100, 80, 150, 35);
		btnVerPaquetes.setFont(new Font("Mongolian Baiti", Font.PLAIN, 15));
		btnVerPaquetes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bll.Administrador.verPaquetes();
			}
		});
		panelPaquetes.add(btnVerPaquetes);

		JButton btnCrearPaquete = new JButton("Crear Paquete");
		btnCrearPaquete.setBounds(280, 80, 150, 35);
		btnCrearPaquete.setFont(new Font("Mongolian Baiti", Font.PLAIN, 15));
		btnCrearPaquete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bll.Administrador.crearPaquete();
			}
		});
		panelPaquetes.add(btnCrearPaquete);

		JButton btnModificarPaquete = new JButton("Modificar Paquete");
		btnModificarPaquete.setBounds(460, 80, 170, 35);
		btnModificarPaquete.setFont(new Font("Mongolian Baiti", Font.PLAIN, 15));
		btnModificarPaquete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bll.Administrador.modificarPaquete();
			}
		});
		panelPaquetes.add(btnModificarPaquete);

		JLabel lblInfoPaquetes = new JLabel("Seleccione una opción para gestionar los paquetes del sistema");
		lblInfoPaquetes.setBounds(150, 180, 450, 20);
		lblInfoPaquetes.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		panelPaquetes.add(lblInfoPaquetes);

		Contenidos.addTab("Paquetes", null, panelPaquetes, null);

		JPanel panelReservas = new JPanel();
		panelReservas.setBackground(Color.WHITE);
		panelReservas.setLayout(null);

		JLabel lblReservas = new JLabel("Gestión de Reservas");
		lblReservas.setBounds(270, 10, 250, 25);
		lblReservas.setFont(new Font("Mongolian Baiti", Font.BOLD, 18));
		panelReservas.add(lblReservas);

		JButton btnVerReservas = new JButton("Ver Reservas");
		btnVerReservas.setBounds(180, 100, 160, 35);
		btnVerReservas.setFont(new Font("Mongolian Baiti", Font.PLAIN, 15));
		btnVerReservas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bll.Administrador.verReservas();
			}
		});
		panelReservas.add(btnVerReservas);

		JButton btnModificarReserva = new JButton("Modificar Reserva");
		btnModificarReserva.setBounds(360, 100, 180, 35);
		btnModificarReserva.setFont(new Font("Mongolian Baiti", Font.PLAIN, 15));
		btnModificarReserva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bll.Administrador.modificarReserva();
			}
		});
		panelReservas.add(btnModificarReserva);

		JLabel lblInfoReservas = new JLabel("Consulte y modifique las reservas del sistema");
		lblInfoReservas.setBounds(200, 200, 400, 20);
		lblInfoReservas.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		panelReservas.add(lblInfoReservas);

		Contenidos.addTab("Reservas", null, panelReservas, null);

		JPanel panelActividades = new JPanel();
		panelActividades.setBackground(Color.WHITE);
		panelActividades.setLayout(null);

		JLabel lblActividades = new JLabel("Gestión de Actividades");
		lblActividades.setBounds(260, 10, 250, 25);
		lblActividades.setFont(new Font("Mongolian Baiti", Font.BOLD, 18));
		panelActividades.add(lblActividades);

		JButton btnCrearActividad = new JButton("Crear Actividad");
		btnCrearActividad.setBounds(260, 100, 180, 35);
		btnCrearActividad.setFont(new Font("Mongolian Baiti", Font.PLAIN, 15));
		btnCrearActividad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bll.Administrador.crearActividad();
			}
		});
		panelActividades.add(btnCrearActividad);

		JLabel lblInfoActividades = new JLabel("Cree nuevas actividades para los hoteles");
		lblInfoActividades.setBounds(220, 200, 350, 20);
		lblInfoActividades.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		panelActividades.add(lblInfoActividades);

		Contenidos.addTab("Actividades", null, panelActividades, null);

		JPanel panelCuentas = new JPanel();
		panelCuentas.setBackground(Color.WHITE);
		panelCuentas.setLayout(null);

		JLabel lblCuentas = new JLabel("Gestión de Cuentas de Usuario");
		lblCuentas.setBounds(230, 10, 300, 25);
		lblCuentas.setFont(new Font("Mongolian Baiti", Font.BOLD, 18));
		panelCuentas.add(lblCuentas);

		// Tabla de usuarios
		String[] columnasCuentas = { "ID", "Usuario", "Nombre", "Apellido", "DNI", "Tipo", "Estado" };
		DefaultTableModel modeloCuentas = new DefaultTableModel(columnasCuentas, 0);
		JTable tablaCuentas = new JTable(modeloCuentas);
		tablaCuentas.setFont(new Font("Mongolian Baiti", Font.PLAIN, 13));
		tablaCuentas.setRowHeight(25);
		JScrollPane scrollCuentas = new JScrollPane(tablaCuentas);
		scrollCuentas.setBounds(20, 50, 700, 200);
		panelCuentas.add(scrollCuentas);

		// Botones
		JButton btnActualizarCuentas = new JButton("Actualizar");
		btnActualizarCuentas.setBounds(30, 270, 110, 30);
		btnActualizarCuentas.setFont(new Font("Mongolian Baiti", Font.PLAIN, 13));
		btnActualizarCuentas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actualizarTablaCuentas(modeloCuentas);
			}
		});
		panelCuentas.add(btnActualizarCuentas);

		JButton btnCrearCuenta = new JButton("Crear");
		btnCrearCuenta.setBounds(155, 270, 110, 30);
		btnCrearCuenta.setFont(new Font("Mongolian Baiti", Font.PLAIN, 13));
		btnCrearCuenta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bll.Administrador.crearCuenta();
				actualizarTablaCuentas(modeloCuentas);
			}
		});
		panelCuentas.add(btnCrearCuenta);

		JButton btnBloquear = new JButton("Bloquear");
		btnBloquear.setBounds(280, 270, 110, 30);
		btnBloquear.setFont(new Font("Mongolian Baiti", Font.PLAIN, 13));
		btnBloquear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bll.Administrador.gestionarCuentas();
				actualizarTablaCuentas(modeloCuentas);
			}
		});
		panelCuentas.add(btnBloquear);

		JButton btnEliminarCuenta = new JButton("Eliminar");
		btnEliminarCuenta.setBounds(405, 270, 110, 30);
		btnEliminarCuenta.setFont(new Font("Mongolian Baiti", Font.PLAIN, 13));
		btnEliminarCuenta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bll.Administrador.gestionarCuentas();
				actualizarTablaCuentas(modeloCuentas);
			}
		});
		panelCuentas.add(btnEliminarCuenta);

		JButton btnEstadisticas = new JButton("Estadísticas");
		btnEstadisticas.setBounds(530, 270, 130, 30);
		btnEstadisticas.setFont(new Font("Mongolian Baiti", Font.PLAIN, 13));
		btnEstadisticas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String stats = DtoAdministrador.obtenerEstadisticas();
				javax.swing.JOptionPane.showMessageDialog(null, stats, "ESTADÍSTICAS DEL SISTEMA",
						javax.swing.JOptionPane.INFORMATION_MESSAGE);
			}
		});
		panelCuentas.add(btnEstadisticas);

		Contenidos.addTab("Cuentas", null, panelCuentas, null);

		JPanel panelSistema = new JPanel();
		panelSistema.setBackground(Color.WHITE);
		panelSistema.setLayout(null);

		JLabel lblSistema = new JLabel("Configuración del Sistema");
		lblSistema.setBounds(240, 10, 300, 25);
		lblSistema.setFont(new Font("Mongolian Baiti", Font.BOLD, 18));
		panelSistema.add(lblSistema);

		JButton btnSuspenderSistema = new JButton("Suspender/Activar Sistema");
		btnSuspenderSistema.setBounds(240, 100, 230, 35);
		btnSuspenderSistema.setFont(new Font("Mongolian Baiti", Font.PLAIN, 15));
		btnSuspenderSistema.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bll.Administrador.suspenderSistema();
			}
		});
		panelSistema.add(btnSuspenderSistema);

		JLabel lblInfoSistema = new JLabel("Control total del sistema");
		lblInfoSistema.setBounds(270, 200, 250, 20);
		lblInfoSistema.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		panelSistema.add(lblInfoSistema);

		Contenidos.addTab("Sistema", null, panelSistema, null);

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
		java.net.URL imageUrl = getClass().getResource("/img/admin.png");
		ImageIcon originalIcon = new ImageIcon(imageUrl);
		Image originalImage = originalIcon.getImage();
		int newWidth = 120;
		int newHeight = 120;
		Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
		originalIcon.setImage(scaledImage);
		Perfil.setIcon(originalIcon);
		Perfil.setBounds(10, 11, 148, 153);
		contentPane.add(Perfil);

		JLabel lblbienvenido = new JLabel("Bienvenido Administrador");
		lblbienvenido.setFont(new Font("Mongolian Baiti", Font.PLAIN, 18));
		lblbienvenido.setBounds(168, 65, 300, 14);
		contentPane.add(lblbienvenido);

		JLabel lblHotel = new JLabel("House Hunter: Panel de Administración");
		lblHotel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 20));
		lblHotel.setBounds(168, 29, 450, 25);
		contentPane.add(lblHotel);

		// Cargar datos iniciales
		actualizarTablaHoteles(modeloHoteles);
		actualizarTablaCuentas(modeloCuentas);
	}

	// Método para actualizar tabla de hoteles
	private void actualizarTablaHoteles(DefaultTableModel modelo) {
		modelo.setRowCount(0);
		List<Hotel> hoteles = DtoAdministrador.verHoteles();
		for (Hotel h : hoteles) {
			Object[] fila = { h.getId(), h.getNombre(), h.getProvincia(), h.getDireccion(), h.getCant_habitaciones(),
					String.format("%.2f", h.getCalificacion_promedio()) };
			modelo.addRow(fila);
		}
	}

	// Método para actualizar tabla de cuentas
	private void actualizarTablaCuentas(DefaultTableModel modelo) {
		modelo.setRowCount(0);
		List<Usuario> usuarios = DtoAdministrador.verCuentas();
		for (Usuario u : usuarios) {
			String tipoTexto = "";
			switch (u.getTipo_usuario()) {
			case "1":
				tipoTexto = "Cliente";
				break;
			case "2":
				tipoTexto = "Encargado";
				break;
			case "3":
				tipoTexto = "Administrador";
				break;
			}
			Object[] fila = { u.getId(), u.getUser(), u.getNombre(), u.getApellido(), u.getDni(), tipoTexto,
					u.getEstado() };
			modelo.addRow(fila);
		}
	}
}