package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import bll.*;
import dll.DtoAdministrador;
import dll.DtoUsuario;

public class Administrador extends JFrame {
	private Usuario usuario;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public Administrador(Usuario usuario) {
		this.usuario = usuario;
		iniciar(this.usuario);
	}

	public void iniciar(Usuario usuario) {
		setTitle("House Hunter - Panel de Administración");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 770, 650);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(179, 217, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel Perfil = new JLabel("");
		Perfil.setBackground(Color.WHITE);
		java.net.URL imageUrl = getClass().getResource("/img/admin.png");
		if (imageUrl != null) {
			ImageIcon originalIcon = new ImageIcon(imageUrl);
			Image scaledImage = originalIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
			Perfil.setIcon(new ImageIcon(scaledImage));
		}
		Perfil.setBounds(10, 11, 148, 153);
		contentPane.add(Perfil);

		JLabel lblHotel = new JLabel("House Hunter: Panel de Administración");
		lblHotel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 22));
		lblHotel.setBounds(168, 29, 500, 25);
		contentPane.add(lblHotel);

		JLabel lblbienvenido = new JLabel("Bienvenido, " + usuario.getNombre());
		lblbienvenido.setFont(new Font("Mongolian Baiti", Font.PLAIN, 18));
		lblbienvenido.setBounds(168, 65, 400, 20);
		contentPane.add(lblbienvenido);

		JButton btnclose = new JButton("Cerrar sesión");
		btnclose.setBounds(850, 11, 124, 30);
		btnclose.setFont(new Font("Mongolian Baiti", Font.PLAIN, 15));
		btnclose.addActionListener(e -> {
			dispose();
			Index frameIndex = new Index();
			frameIndex.setVisible(true);
		});
		contentPane.add(btnclose);

		JTabbedPane Contenidos = new JTabbedPane(JTabbedPane.TOP);
		Contenidos.setBounds(0, 178, 984, 483);
		Contenidos.setFont(new Font("Mongolian Baiti", Font.PLAIN, 15));
		contentPane.add(Contenidos);

		Contenidos.addTab("Hoteles", crearPanelHoteles());
		Contenidos.addTab("Paquetes", crearPanelPaquetes());
		Contenidos.addTab("Reservas", crearPanelReservas());
		Contenidos.addTab("Actividades", crearPanelActividades());
		Contenidos.addTab("Cuentas", crearPanelCuentas());
		Contenidos.addTab("Sistema", crearPanelSistema());
	}

	private JPanel crearPanelHoteles() {
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setLayout(null);

		JLabel lblTitulo = new JLabel("Gestión de Hoteles");
		lblTitulo.setBounds(301, 9, 175, 30);
		lblTitulo.setFont(new Font("Mongolian Baiti", Font.PLAIN, 20));
		panel.add(lblTitulo);

		String[] columnas = { "ID", "Nombre", "Provincia", "Dirección", "Habitaciones", "Calificación" };
		DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
		JTable tabla = new JTable(modelo);
		tabla.setFont(new Font("Mongolian Baiti", Font.PLAIN, 13));
		tabla.setRowHeight(25);

		tabla.getColumnModel().getColumn(0).setPreferredWidth(40);
		tabla.getColumnModel().getColumn(1).setPreferredWidth(150);
		tabla.getColumnModel().getColumn(2).setPreferredWidth(100);
		tabla.getColumnModel().getColumn(3).setPreferredWidth(180);
		tabla.getColumnModel().getColumn(4).setPreferredWidth(100);
		tabla.getColumnModel().getColumn(5).setPreferredWidth(100);

		tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		JScrollPane scrollPane = new JScrollPane(tabla);
		scrollPane.setBounds(30, 50, 700, 280);
		panel.add(scrollPane);

		JButton btnActualizar = new JButton("Actualizar");
		btnActualizar.setBounds(30, 342, 150, 35);
		btnActualizar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		btnActualizar.addActionListener(e -> actualizarTablaHoteles(modelo));
		panel.add(btnActualizar);

		JButton btnCrear = new JButton("Crear Hotel");
		btnCrear.setBounds(210, 342, 150, 35);
		btnCrear.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		btnCrear.addActionListener(e -> {
			crearPanelCrearHotel();
			actualizarTablaHoteles(modelo);
		});
		panel.add(btnCrear);

		JButton btnModificar = new JButton("Modificar");
		btnModificar.setBounds(401, 342, 150, 35);
		btnModificar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		btnModificar.addActionListener(e -> {
			int fila = tabla.getSelectedRow();
			if (fila == -1) {
				JOptionPane.showMessageDialog(panel, "Seleccione un hotel de la tabla", "Aviso",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			int id = (int) modelo.getValueAt(fila, 0);
			crearPanelModificarHotel(id);
			actualizarTablaHoteles(modelo);
		});
		panel.add(btnModificar);

		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(580, 341, 150, 35);
		btnEliminar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		btnEliminar.addActionListener(e -> {
			int fila = tabla.getSelectedRow();
			if (fila == -1) {
				JOptionPane.showMessageDialog(panel, "Seleccione un hotel de la tabla", "Aviso",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			int id = (int) modelo.getValueAt(fila, 0);
			int confirm = JOptionPane.showConfirmDialog(panel, "¿Eliminar hotel seleccionado?", "Confirmar",
					JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				DtoAdministrador.eliminarHotel(id);
				actualizarTablaHoteles(modelo);
			}
		});
		panel.add(btnEliminar);

		actualizarTablaHoteles(modelo);
		return panel;
	}

	private void actualizarTablaHoteles(DefaultTableModel modelo) {
		modelo.setRowCount(0);
		List<Hotel> hoteles = DtoAdministrador.verHoteles();
		for (Hotel h : hoteles) {
			modelo.addRow(new Object[] { h.getId(), h.getNombre(), h.getProvincia(), h.getDireccion(),
					h.getCant_habitaciones(), String.format("%.2f", h.getCalificacion_promedio()) });
		}
	}

	private void crearPanelCrearHotel() {
		JFrame frame = new JFrame("Crear Nuevo Hotel");
		frame.setSize(500, 400);
		frame.setLocationRelativeTo(this);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setBackground(new Color(179, 217, 255));

		JLabel lblTitulo = new JLabel("Crear Nuevo Hotel");
		lblTitulo.setBounds(150, 20, 200, 30);
		lblTitulo.setFont(new Font("Mongolian Baiti", Font.PLAIN, 20));
		frame.getContentPane().add(lblTitulo);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(50, 70, 100, 25);
		lblNombre.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(lblNombre);
		JTextField txtNombre = new JTextField();
		txtNombre.setBounds(180, 70, 250, 25);
		txtNombre.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(txtNombre);

		JLabel lblProvincia = new JLabel("Provincia:");
		lblProvincia.setBounds(50, 110, 100, 25);
		lblProvincia.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(lblProvincia);
		JTextField txtProvincia = new JTextField();
		txtProvincia.setBounds(180, 110, 250, 25);
		txtProvincia.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(txtProvincia);

		JLabel lblDireccion = new JLabel("Dirección:");
		lblDireccion.setBounds(50, 150, 100, 25);
		lblDireccion.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(lblDireccion);
		JTextField txtDireccion = new JTextField();
		txtDireccion.setBounds(180, 150, 250, 25);
		txtDireccion.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(txtDireccion);

		JLabel lblHab = new JLabel("Habitaciones:");
		lblHab.setBounds(50, 190, 120, 25);
		lblHab.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(lblHab);
		JSpinner spnHab = new JSpinner(new SpinnerNumberModel(10, 1, 500, 1));
		spnHab.setBounds(180, 190, 100, 25);
		spnHab.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(spnHab);

		JLabel lblCupo = new JLabel("Cupo Máximo:");
		lblCupo.setBounds(50, 230, 120, 25);
		lblCupo.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(lblCupo);
		JSpinner spnCupo = new JSpinner(new SpinnerNumberModel(50, 1, 1000, 10));
		spnCupo.setBounds(180, 230, 100, 25);
		spnCupo.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(spnCupo);

		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(120, 290, 120, 35);
		btnGuardar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		btnGuardar.addActionListener(e -> {
			if (txtNombre.getText().trim().isEmpty() || txtProvincia.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(frame, "Complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			DtoAdministrador.crearHotel(txtNombre.getText(), txtProvincia.getText(), txtDireccion.getText(),
					(int) spnHab.getValue(), (int) spnCupo.getValue());
			frame.dispose();
		});
		frame.getContentPane().add(btnGuardar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(260, 290, 120, 35);
		btnCancelar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		btnCancelar.addActionListener(e -> frame.dispose());
		frame.getContentPane().add(btnCancelar);

		frame.setVisible(true);
	}

	private void crearPanelModificarHotel(int idHotel) {
		List<Hotel> hoteles = DtoAdministrador.verHoteles();
		Hotel hotel = hoteles.stream().filter(h -> h.getId() == idHotel).findFirst().orElse(null);
		if (hotel == null)
			return;

		JFrame frame = new JFrame("Modificar Hotel");
		frame.setSize(500, 250);
		frame.setLocationRelativeTo(this);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setBackground(new Color(179, 217, 255));

		JLabel lblTitulo = new JLabel("Modificar Hotel");
		lblTitulo.setBounds(170, 20, 200, 30);
		lblTitulo.setFont(new Font("Mongolian Baiti", Font.PLAIN, 20));
		frame.getContentPane().add(lblTitulo);

		JLabel lblNombre = new JLabel("Nuevo Nombre:");
		lblNombre.setBounds(50, 80, 120, 25);
		lblNombre.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(lblNombre);
		JTextField txtNombre = new JTextField(hotel.getNombre());
		txtNombre.setBounds(180, 80, 250, 25);
		txtNombre.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(txtNombre);

		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(120, 140, 120, 35);
		btnGuardar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		btnGuardar.addActionListener(e -> {
			DtoAdministrador.modificarHotel(idHotel, txtNombre.getText());
			frame.dispose();
		});
		frame.getContentPane().add(btnGuardar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(260, 140, 120, 35);
		btnCancelar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		btnCancelar.addActionListener(e -> frame.dispose());
		frame.getContentPane().add(btnCancelar);

		frame.setVisible(true);
	}

	private JPanel crearPanelPaquetes() {
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setLayout(null);

		JLabel lblTitulo = new JLabel("Gestión de Paquetes");
		lblTitulo.setBounds(300, 10, 250, 30);
		lblTitulo.setFont(new Font("Mongolian Baiti", Font.PLAIN, 20));
		panel.add(lblTitulo);

		JLabel lblHotel = new JLabel("Seleccione Hotel:");
		lblHotel.setBounds(30, 60, 150, 25);
		lblHotel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		panel.add(lblHotel);

		JComboBox<String> cmbHoteles = new JComboBox<>();
		cmbHoteles.setBounds(180, 60, 350, 25);
		cmbHoteles.setFont(new Font("Mongolian Baiti", Font.PLAIN, 13));
		panel.add(cmbHoteles);

		JButton btnCargar = new JButton("Ver Paquetes");
		btnCargar.setBounds(550, 60, 150, 25);
		btnCargar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 13));
		btnCargar.addActionListener(e -> {
			if (cmbHoteles.getSelectedItem() == null)
				return;
			String sel = (String) cmbHoteles.getSelectedItem();
			int idHotel = Integer.parseInt(sel.split(" - ")[0]);
			DefaultTableModel modelo = null;
			actualizarTablaPaquetes(modelo, idHotel);
		});

		panel.add(btnCargar);

		String[] columnas = { "ID", "Hotel", "Inicio", "Fin", "Precio", "Cupo Act", "Cupo Max" };
		DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
		JTable tabla = new JTable(modelo);
		tabla.setFont(new Font("Mongolian Baiti", Font.PLAIN, 12));
		tabla.setRowHeight(25);

		tabla.getColumnModel().getColumn(0).setPreferredWidth(40);
		tabla.getColumnModel().getColumn(1).setPreferredWidth(180);
		tabla.getColumnModel().getColumn(2).setPreferredWidth(90);
		tabla.getColumnModel().getColumn(3).setPreferredWidth(90);
		tabla.getColumnModel().getColumn(4).setPreferredWidth(80);
		tabla.getColumnModel().getColumn(5).setPreferredWidth(80);
		tabla.getColumnModel().getColumn(6).setPreferredWidth(80);

		tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		JScrollPane scroll = new JScrollPane(tabla);
		scroll.setBounds(30, 110, 700, 220);
		panel.add(scroll);

		JButton btnCrear = new JButton("Crear Paquete");
		btnCrear.setBounds(156, 350, 180, 35);
		btnCrear.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		btnCrear.addActionListener(e -> {
			crearPanelCrearPaquete();
			if (cmbHoteles.getSelectedItem() != null) {
				String sel = (String) cmbHoteles.getSelectedItem();
				int idHotel = Integer.parseInt(sel.split(" - ")[0]);
				actualizarTablaPaquetes(modelo, idHotel);
			}
		});
		panel.add(btnCrear);

		JButton btnModificar = new JButton("Modificar");
		btnModificar.setBounds(413, 350, 180, 35);
		btnModificar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		btnModificar.addActionListener(e -> {
			int fila = tabla.getSelectedRow();
			if (fila == -1) {
				JOptionPane.showMessageDialog(panel, "Seleccione un paquete", "Aviso", JOptionPane.WARNING_MESSAGE);
				return;
			}
			int idPaquete = (int) modelo.getValueAt(fila, 0);
			crearPanelModificarPaquete(idPaquete);
			if (cmbHoteles.getSelectedItem() != null) {
				String sel = (String) cmbHoteles.getSelectedItem();
				int idHotel = Integer.parseInt(sel.split(" - ")[0]);
				actualizarTablaPaquetes(modelo, idHotel);
			}
		});
		panel.add(btnModificar);

		cargarHotelesEnCombo(cmbHoteles);

		return panel;
	}

	private void cargarHotelesEnCombo(JComboBox<String> combo) {
		combo.removeAllItems();
		List<Hotel> hoteles = DtoAdministrador.verHoteles();
		for (Hotel h : hoteles) {
			combo.addItem(h.getId() + " - " + h.getNombre() + " (" + h.getProvincia() + ")");
		}
	}

	private void actualizarTablaPaquetes(DefaultTableModel modelo, int idHotel) {
		modelo.setRowCount(0);
		List<Paquete> paquetes = DtoAdministrador.verPaquetes(idHotel);
		for (Paquete p : paquetes) {
			modelo.addRow(new Object[] { p.getId(), p.getHotel().getNombre(), p.getInicioDate(), p.getFinDate(),
					"$" + p.getPrecio(), p.getCupo_actual(), p.getCupo_maximo() });
		}
	}

	private void crearPanelCrearPaquete() {
		JFrame frame = new JFrame("Crear Nuevo Paquete");
		frame.setSize(550, 550);
		frame.setLocationRelativeTo(this);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setBackground(new Color(179, 217, 255));

		JLabel lblTitulo = new JLabel("Crear Nuevo Paquete");
		lblTitulo.setBounds(160, 20, 250, 30);
		lblTitulo.setFont(new Font("Mongolian Baiti", Font.PLAIN, 20));
		frame.getContentPane().add(lblTitulo);

		JLabel lblHotel = new JLabel("Hotel:");
		lblHotel.setBounds(50, 70, 100, 25);
		lblHotel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(lblHotel);
		JComboBox<String> cmbHotel = new JComboBox<>();
		cmbHotel.setBounds(180, 70, 300, 25);
		cmbHotel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 13));
		cargarHotelesEnCombo(cmbHotel);
		frame.getContentPane().add(cmbHotel);

		JLabel lblActividad = new JLabel("Actividad:");
		lblActividad.setBounds(50, 110, 100, 25);
		lblActividad.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(lblActividad);
		JComboBox<String> cmbActividad = new JComboBox<>();
		cmbActividad.setBounds(180, 110, 300, 25);
		cmbActividad.setFont(new Font("Mongolian Baiti", Font.PLAIN, 13));
		frame.getContentPane().add(cmbActividad);

		cmbHotel.addActionListener(e -> {
			if (cmbHotel.getSelectedItem() != null) {
				String sel = (String) cmbHotel.getSelectedItem();
				int idHotel = Integer.parseInt(sel.split(" - ")[0]);
				cmbActividad.removeAllItems();
				List<Actividad> actividades = DtoAdministrador.obtenerActividadesPorHotel(idHotel);
				for (Actividad a : actividades) {
					cmbActividad.addItem(a.getId() + " - " + a.getNombre() + " (" + a.getCategoria() + ")");
				}
			}
		});

		JLabel lblInicio = new JLabel("Fecha Inicio:");
		lblInicio.setBounds(50, 150, 120, 25);
		lblInicio.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(lblInicio);
		JTextField txtInicio = new JTextField("2025-01-01");
		txtInicio.setBounds(180, 150, 150, 25);
		txtInicio.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(txtInicio);

		JLabel lblFin = new JLabel("Fecha Fin:");
		lblFin.setBounds(50, 190, 120, 25);
		lblFin.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(lblFin);
		JTextField txtFin = new JTextField("2025-01-10");
		txtFin.setBounds(180, 190, 150, 25);
		txtFin.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(txtFin);

		JLabel lblPrecio = new JLabel("Precio:");
		lblPrecio.setBounds(50, 230, 100, 25);
		lblPrecio.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(lblPrecio);
		JSpinner spnPrecio = new JSpinner(new SpinnerNumberModel(1000.0, 0.0, 999999.0, 100.0));
		spnPrecio.setBounds(180, 230, 150, 25);
		spnPrecio.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(spnPrecio);

		JLabel lblCupo = new JLabel("Cupo Máximo:");
		lblCupo.setBounds(50, 270, 120, 25);
		lblCupo.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(lblCupo);
		JSpinner spnCupo = new JSpinner(new SpinnerNumberModel(10, 1, 100, 1));
		spnCupo.setBounds(180, 270, 100, 25);
		spnCupo.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(spnCupo);

		JLabel lblFormato = new JLabel("Formato fecha: YYYY-MM-DD");
		lblFormato.setBounds(180, 310, 250, 20);
		lblFormato.setFont(new Font("Mongolian Baiti", Font.ITALIC, 12));
		frame.getContentPane().add(lblFormato);

		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(140, 360, 120, 35);
		btnGuardar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		btnGuardar.addActionListener(e -> {
			if (cmbHotel.getSelectedItem() == null || cmbActividad.getSelectedItem() == null) {
				JOptionPane.showMessageDialog(frame, "Seleccione hotel y actividad", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				String selHotel = (String) cmbHotel.getSelectedItem();
				int idHotel = Integer.parseInt(selHotel.split(" - ")[0]);
				String selAct = (String) cmbActividad.getSelectedItem();
				int idActividad = Integer.parseInt(selAct.split(" - ")[0]);

				LocalDate fechaInicio = LocalDate.parse(txtInicio.getText());
				LocalDate fechaFin = LocalDate.parse(txtFin.getText());
				double precio = (Double) spnPrecio.getValue();
				int cupo = (int) spnCupo.getValue();

				DtoAdministrador.crearPaquete(fechaInicio, fechaFin, precio, idHotel, idActividad, cupo);
				frame.dispose();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(frame, "Error en los datos: " + ex.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		});
		frame.getContentPane().add(btnGuardar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(280, 360, 120, 35);
		btnCancelar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		btnCancelar.addActionListener(e -> frame.dispose());
		frame.getContentPane().add(btnCancelar);

		frame.setVisible(true);
	}

	private void crearPanelModificarPaquete(int idPaquete) {
		List<Hotel> hoteles = DtoAdministrador.verHoteles();
		Paquete paquete = null;
		for (Hotel h : hoteles) {
			List<Paquete> paquetes = DtoAdministrador.verPaquetes(h.getId());
			for (Paquete p : paquetes) {
				if (p.getId() == idPaquete) {
					paquete = p;
					break;
				}
			}
			if (paquete != null)
				break;
		}

		if (paquete == null)
			return;

		final Paquete paqueteSeleccionado = paquete;

		JFrame frame = new JFrame("Modificar Paquete");
		frame.setSize(550, 450);
		frame.setLocationRelativeTo(this);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setBackground(new Color(179, 217, 255));

		JLabel lblTitulo = new JLabel("Modificar Paquete");
		lblTitulo.setBounds(180, 20, 250, 30);
		lblTitulo.setFont(new Font("Mongolian Baiti", Font.PLAIN, 20));
		frame.getContentPane().add(lblTitulo);

		JLabel lblHotel = new JLabel("Hotel:");
		lblHotel.setBounds(50, 70, 100, 25);
		lblHotel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(lblHotel);
		JComboBox<String> cmbHotel = new JComboBox<>();
		cmbHotel.setBounds(180, 70, 300, 25);
		cmbHotel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 13));
		cargarHotelesEnCombo(cmbHotel);
		for (int i = 0; i < cmbHotel.getItemCount(); i++) {
			if (cmbHotel.getItemAt(i).startsWith(paqueteSeleccionado.getHotel().getId() + " - ")) {
				cmbHotel.setSelectedIndex(i);
				break;
			}
		}
		frame.getContentPane().add(cmbHotel);

		JLabel lblInicio = new JLabel("Fecha Inicio:");
		lblInicio.setBounds(50, 110, 120, 25);
		lblInicio.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(lblInicio);
		JTextField txtInicio = new JTextField(paqueteSeleccionado.getInicioDate().toString());
		txtInicio.setBounds(180, 110, 150, 25);
		txtInicio.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(txtInicio);

		JLabel lblFin = new JLabel("Fecha Fin:");
		lblFin.setBounds(50, 150, 120, 25);
		lblFin.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(lblFin);
		JTextField txtFin = new JTextField(paqueteSeleccionado.getFinDate().toString());
		txtFin.setBounds(180, 150, 150, 25);
		txtFin.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(txtFin);

		JLabel lblPrecio = new JLabel("Precio:");
		lblPrecio.setBounds(50, 190, 100, 25);
		lblPrecio.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(lblPrecio);
		JSpinner spnPrecio = new JSpinner(
				new SpinnerNumberModel(paqueteSeleccionado.getPrecio(), 0.0, 999999.0, 100.0));
		spnPrecio.setBounds(180, 190, 150, 25);
		spnPrecio.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(spnPrecio);

		JLabel lblFormato = new JLabel("Formato fecha: YYYY-MM-DD");
		lblFormato.setBounds(180, 230, 250, 20);
		lblFormato.setFont(new Font("Mongolian Baiti", Font.ITALIC, 12));
		frame.getContentPane().add(lblFormato);

		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(140, 280, 120, 35);
		btnGuardar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		btnGuardar.addActionListener(e -> {
			try {
				String selHotel = (String) cmbHotel.getSelectedItem();
				int idHotel = Integer.parseInt(selHotel.split(" - ")[0]);

				LocalDate fechaInicio = LocalDate.parse(txtInicio.getText());
				LocalDate fechaFin = LocalDate.parse(txtFin.getText());
				double precio = (Double) spnPrecio.getValue();

				Integer idHabitacion = paqueteSeleccionado.getHabitacion() != null
						? paqueteSeleccionado.getHabitacion().getId()
						: null;
				Integer idActividad = paqueteSeleccionado.getActividad() != null
						? paqueteSeleccionado.getActividad().getId()
						: null;

				DtoAdministrador.modificarPaquete(idPaquete, fechaInicio, fechaFin, precio, idHotel, idHabitacion,
						idActividad);
				frame.dispose();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(frame, "Error en los datos: " + ex.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		});
		frame.getContentPane().add(btnGuardar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(280, 280, 120, 35);
		btnCancelar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		btnCancelar.addActionListener(e -> frame.dispose());
		frame.getContentPane().add(btnCancelar);

		frame.setVisible(true);
	}

	private JPanel crearPanelReservas() {
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setLayout(null);

		JLabel lblTitulo = new JLabel("Gestión de Reservas");
		lblTitulo.setBounds(350, 10, 250, 30);
		lblTitulo.setFont(new Font("Mongolian Baiti", Font.PLAIN, 20));
		panel.add(lblTitulo);

		JLabel lblHotel = new JLabel("Seleccione Hotel:");
		lblHotel.setBounds(30, 60, 150, 25);
		lblHotel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		panel.add(lblHotel);

		JComboBox<String> cmbHoteles = new JComboBox<>();
		cmbHoteles.setBounds(180, 60, 400, 25);
		cmbHoteles.setFont(new Font("Mongolian Baiti", Font.PLAIN, 13));
		panel.add(cmbHoteles);

		String[] columnas = { "ID", "Cliente", "Hotel", "Estado", "Monto", "Check-in" };
		DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
		JTable tabla = new JTable(modelo);
		tabla.setFont(new Font("Mongolian Baiti", Font.PLAIN, 12));
		tabla.setRowHeight(25);

		tabla.getColumnModel().getColumn(0).setPreferredWidth(40);
		tabla.getColumnModel().getColumn(1).setPreferredWidth(180);
		tabla.getColumnModel().getColumn(2).setPreferredWidth(90);
		tabla.getColumnModel().getColumn(3).setPreferredWidth(90);
		tabla.getColumnModel().getColumn(4).setPreferredWidth(80);
		tabla.getColumnModel().getColumn(5).setPreferredWidth(80);

		tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		JScrollPane scroll = new JScrollPane(tabla);
		scroll.setBounds(30, 110, 700, 220);
		panel.add(scroll);

		JButton btnCargar = new JButton("Ver Reservas");
		btnCargar.setBounds(600, 60, 141, 25);
		btnCargar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 13));
		btnCargar.addActionListener(e -> {
			if (cmbHoteles.getSelectedItem() == null)
				return;
			String sel = (String) cmbHoteles.getSelectedItem();
			int idHotel = Integer.parseInt(sel.split(" - ")[0]);
			actualizarTablaReservas(modelo, idHotel);
		});
		panel.add(btnCargar);

		JButton btnModificar = new JButton("Modificar Reserva");
		btnModificar.setBounds(292, 350, 180, 35);
		btnModificar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		btnModificar.addActionListener(e -> {
			int fila = tabla.getSelectedRow();
			if (fila == -1) {
				JOptionPane.showMessageDialog(panel, "Seleccione una reserva", "Aviso", JOptionPane.WARNING_MESSAGE);
				return;
			}
			JOptionPane.showMessageDialog(panel, "Funcionalidad en desarrollo", "Info",
					JOptionPane.INFORMATION_MESSAGE);
		});
		panel.add(btnModificar);

		cargarHotelesEnCombo(cmbHoteles);

		return panel;
	}

	private void actualizarTablaReservas(DefaultTableModel modelo, int idHotel) {
		modelo.setRowCount(0);
		List<Reserva> reservas = DtoAdministrador.verReservas(idHotel);
		for (Reserva r : reservas) {
			modelo.addRow(new Object[] { r.getId(), r.getCliente().getNombre() + " " + r.getCliente().getApellido(),
					r.getPaquete().getHotel().getNombre(), r.getEstado(), "$" + r.getMonto_final(),
					r.getFecha_checkin() != null
							? r.getFecha_checkin().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
							: "N/A" });
		}
	}

	private JPanel crearPanelActividades() {
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setLayout(null);

		JLabel lblTitulo = new JLabel("Gestión de Actividades");
		lblTitulo.setBounds(330, 10, 300, 30);
		lblTitulo.setFont(new Font("Mongolian Baiti", Font.PLAIN, 20));
		panel.add(lblTitulo);

		JLabel lblHotel = new JLabel("Seleccione Hotel:");
		lblHotel.setBounds(30, 60, 150, 25);
		lblHotel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		panel.add(lblHotel);

		JComboBox<String> cmbHoteles = new JComboBox<>();
		cmbHoteles.setBounds(180, 60, 400, 25);
		cmbHoteles.setFont(new Font("Mongolian Baiti", Font.PLAIN, 13));
		panel.add(cmbHoteles);

		String[] columnas = { "ID", "Nombre", "Categoría", "Precio", "Duración", "Edad Min", "Edad Max" };
		DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
		JTable tabla = new JTable(modelo);
		tabla.setFont(new Font("Mongolian Baiti", Font.PLAIN, 12));
		tabla.setRowHeight(25);

		tabla.getColumnModel().getColumn(0).setPreferredWidth(40);
		tabla.getColumnModel().getColumn(1).setPreferredWidth(180);
		tabla.getColumnModel().getColumn(2).setPreferredWidth(90);
		tabla.getColumnModel().getColumn(3).setPreferredWidth(90);
		tabla.getColumnModel().getColumn(4).setPreferredWidth(80);
		tabla.getColumnModel().getColumn(5).setPreferredWidth(80);
		tabla.getColumnModel().getColumn(6).setPreferredWidth(80);

		tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		JScrollPane scroll = new JScrollPane(tabla);
		scroll.setBounds(30, 110, 700, 220);
		panel.add(scroll);

		JButton btnCargar = new JButton("Ver Actividades");
		btnCargar.setBounds(600, 60, 130, 25);
		btnCargar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 13));
		btnCargar.addActionListener(e -> {
			if (cmbHoteles.getSelectedItem() == null)
				return;
			String sel = (String) cmbHoteles.getSelectedItem();
			int idHotel = Integer.parseInt(sel.split(" - ")[0]);
			actualizarTablaActividades(modelo, idHotel);
		});
		panel.add(btnCargar);

		JButton btnCrear = new JButton("Crear Actividad");
		btnCrear.setBounds(296, 351, 180, 35);
		btnCrear.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		btnCrear.addActionListener(e -> {
			crearPanelCrearActividad();
			if (cmbHoteles.getSelectedItem() != null) {
				String sel = (String) cmbHoteles.getSelectedItem();
				int idHotel = Integer.parseInt(sel.split(" - ")[0]);
				actualizarTablaActividades(modelo, idHotel);
			}
		});
		panel.add(btnCrear);

		cargarHotelesEnCombo(cmbHoteles);

		return panel;
	}

	private void actualizarTablaActividades(DefaultTableModel modelo, int idHotel) {
		modelo.setRowCount(0);
		List<Actividad> actividades = DtoAdministrador.obtenerActividadesPorHotel(idHotel);
		for (Actividad a : actividades) {
			modelo.addRow(new Object[] { a.getId(), a.getNombre(), a.getCategoria(), "$" + a.getPrecio(),
					a.getDuracion() + "h", a.getEdad_minima(), a.getEdad_maxima() });
		}
	}

	private void crearPanelCrearActividad() {
		JFrame frame = new JFrame("Crear Nueva Actividad");
		frame.setSize(550, 650);
		frame.setLocationRelativeTo(this);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setBackground(new Color(179, 217, 255));

		JLabel lblTitulo = new JLabel("Crear Nueva Actividad");
		lblTitulo.setBounds(150, 20, 300, 30);
		lblTitulo.setFont(new Font("Mongolian Baiti", Font.PLAIN, 20));
		frame.getContentPane().add(lblTitulo);

		JLabel lblHotel = new JLabel("Hotel:");
		lblHotel.setBounds(50, 70, 100, 25);
		lblHotel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(lblHotel);
		JComboBox<String> cmbHotel = new JComboBox<>();
		cmbHotel.setBounds(180, 70, 300, 25);
		cmbHotel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 13));
		cargarHotelesEnCombo(cmbHotel);
		frame.getContentPane().add(cmbHotel);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(50, 110, 100, 25);
		lblNombre.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(lblNombre);
		JTextField txtNombre = new JTextField();
		txtNombre.setBounds(180, 110, 300, 25);
		txtNombre.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(txtNombre);

		JLabel lblCategoria = new JLabel("Categoría:");
		lblCategoria.setBounds(50, 150, 100, 25);
		lblCategoria.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(lblCategoria);
		JComboBox<String> cmbCategoria = new JComboBox<>(
				new String[] { "Deportes", "Cultura", "Aventura", "Relajacion", "Gastronomia", "Otro" });
		cmbCategoria.setBounds(180, 150, 200, 25);
		cmbCategoria.setFont(new Font("Mongolian Baiti", Font.PLAIN, 13));
		frame.getContentPane().add(cmbCategoria);

		JLabel lblLocacion = new JLabel("Locación:");
		lblLocacion.setBounds(50, 190, 100, 25);
		lblLocacion.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(lblLocacion);
		JTextField txtLocacion = new JTextField();
		txtLocacion.setBounds(180, 190, 300, 25);
		txtLocacion.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(txtLocacion);

		JLabel lblEdadMin = new JLabel("Edad Mínima:");
		lblEdadMin.setBounds(50, 230, 120, 25);
		lblEdadMin.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(lblEdadMin);
		JSpinner spnEdadMin = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
		spnEdadMin.setBounds(180, 230, 100, 25);
		spnEdadMin.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(spnEdadMin);

		JLabel lblEdadMax = new JLabel("Edad Máxima:");
		lblEdadMax.setBounds(50, 270, 120, 25);
		lblEdadMax.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(lblEdadMax);
		JSpinner spnEdadMax = new JSpinner(new SpinnerNumberModel(99, 0, 150, 1));
		spnEdadMax.setBounds(180, 270, 100, 25);
		spnEdadMax.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(spnEdadMax);

		JLabel lblPrecio = new JLabel("Precio:");
		lblPrecio.setBounds(50, 310, 100, 25);
		lblPrecio.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(lblPrecio);
		JSpinner spnPrecio = new JSpinner(new SpinnerNumberModel(500.0, 0.0, 999999.0, 50.0));
		spnPrecio.setBounds(180, 310, 150, 25);
		spnPrecio.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(spnPrecio);

		JLabel lblDuracion = new JLabel("Duración (hs):");
		lblDuracion.setBounds(50, 350, 120, 25);
		lblDuracion.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(lblDuracion);
		JSpinner spnDuracion = new JSpinner(new SpinnerNumberModel(2.0, 0.5, 24.0, 0.5));
		spnDuracion.setBounds(180, 350, 100, 25);
		spnDuracion.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(spnDuracion);

		JLabel lblInicio = new JLabel("Fecha Inicio:");
		lblInicio.setBounds(50, 390, 120, 25);
		lblInicio.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(lblInicio);
		JTextField txtInicio = new JTextField("2025-01-01");
		txtInicio.setBounds(180, 390, 150, 25);
		txtInicio.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(txtInicio);

		JLabel lblFin = new JLabel("Fecha Fin:");
		lblFin.setBounds(50, 430, 120, 25);
		lblFin.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(lblFin);
		JTextField txtFin = new JTextField("2025-12-31");
		txtFin.setBounds(180, 430, 150, 25);
		txtFin.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(txtFin);

		JLabel lblRiesgo = new JLabel("Riesgo:");
		lblRiesgo.setBounds(50, 470, 100, 25);
		lblRiesgo.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(lblRiesgo);
		JComboBox<String> cmbRiesgo = new JComboBox<>(new String[] { "Si", "No" });
		cmbRiesgo.setBounds(180, 470, 100, 25);
		cmbRiesgo.setFont(new Font("Mongolian Baiti", Font.PLAIN, 13));
		frame.getContentPane().add(cmbRiesgo);

		JLabel lblFormato = new JLabel("Formato fecha: YYYY-MM-DD");
		lblFormato.setBounds(180, 505, 250, 20);
		lblFormato.setFont(new Font("Mongolian Baiti", Font.ITALIC, 12));
		frame.getContentPane().add(lblFormato);

		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(140, 540, 120, 35);
		btnGuardar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		btnGuardar.addActionListener(e -> {
			if (cmbHotel.getSelectedItem() == null || txtNombre.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(frame, "Complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				String selHotel = (String) cmbHotel.getSelectedItem();
				int idHotel = Integer.parseInt(selHotel.split(" - ")[0]);

				String nombre = txtNombre.getText();
				String categoria = (String) cmbCategoria.getSelectedItem();
				String locacion = txtLocacion.getText();
				int edadMin = (int) spnEdadMin.getValue();
				int edadMax = (int) spnEdadMax.getValue();
				double precio = (Double) spnPrecio.getValue();
				double duracion = (Double) spnDuracion.getValue();
				LocalDate fechaInicio = LocalDate.parse(txtInicio.getText());
				LocalDate fechaFin = LocalDate.parse(txtFin.getText());
				String riesgo = (String) cmbRiesgo.getSelectedItem();

				DtoAdministrador.crearActividad(nombre, categoria, locacion, edadMin, edadMax, precio, duracion,
						fechaInicio, fechaFin, idHotel, riesgo);
				frame.dispose();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(frame, "Error en los datos: " + ex.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		});
		frame.getContentPane().add(btnGuardar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(280, 540, 120, 35);
		btnCancelar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		btnCancelar.addActionListener(e -> frame.dispose());
		frame.getContentPane().add(btnCancelar);

		frame.setVisible(true);
	}

	private JPanel crearPanelCuentas() {
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setLayout(null);

		JLabel lblTitulo = new JLabel("Gestión de Cuentas de Usuario");
		lblTitulo.setBounds(249, 11, 259, 30);
		lblTitulo.setFont(new Font("Mongolian Baiti", Font.PLAIN, 20));
		panel.add(lblTitulo);

		String[] columnas = { "ID", "Usuario", "Nombre", "Apellido", "DNI", "Tipo", "Estado" };
		DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
		JTable tabla = new JTable(modelo);
		tabla.setFont(new Font("Mongolian Baiti", Font.PLAIN, 12));
		tabla.setRowHeight(25);

		tabla.getColumnModel().getColumn(0).setPreferredWidth(40);
		tabla.getColumnModel().getColumn(1).setPreferredWidth(180);
		tabla.getColumnModel().getColumn(2).setPreferredWidth(90);
		tabla.getColumnModel().getColumn(3).setPreferredWidth(90);
		tabla.getColumnModel().getColumn(4).setPreferredWidth(80);
		tabla.getColumnModel().getColumn(5).setPreferredWidth(80);
		tabla.getColumnModel().getColumn(6).setPreferredWidth(80);

		tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		JScrollPane scroll = new JScrollPane(tabla);
		scroll.setBounds(30, 81, 700, 249);
		panel.add(scroll);

		JButton btnActualizar = new JButton("Actualizar");
		btnActualizar.setBounds(10, 357, 120, 30);
		btnActualizar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		btnActualizar.addActionListener(e -> actualizarTablaCuentas(modelo));
		panel.add(btnActualizar);

		JButton btnCrear = new JButton("Crear");
		btnCrear.setBounds(150, 357, 120, 30);
		btnCrear.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		btnCrear.addActionListener(e -> {
			crearPanelCrearCuenta();
			actualizarTablaCuentas(modelo);
		});
		panel.add(btnCrear);

		JLabel lblMensaje = new JLabel("");
		lblMensaje.setBounds(113, 38, 540, 25);
		lblMensaje.setFont(new Font("Mongolian Baiti", Font.BOLD, 13));
		lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
		lblMensaje.setVisible(false);
		panel.add(lblMensaje);

		JButton btnBloquear = new JButton("Bloquear");
		btnBloquear.setBounds(290, 357, 130, 30);
		btnBloquear.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		btnBloquear.addActionListener(e -> {
			int fila = tabla.getSelectedRow();
			if (fila == -1) {
				lblMensaje.setText("Seleccione un usuario de la tabla");
				lblMensaje.setForeground(new Color(255, 152, 0));
				lblMensaje.setVisible(true);
				return;
			}

			int id = (int) modelo.getValueAt(fila, 0);

			String estado = modelo.getValueAt(fila, 5).toString();

			if (estado.equalsIgnoreCase("Bloqueado") || estado.equalsIgnoreCase("true")) {
				lblMensaje.setText("La cuenta ya está bloqueada");
				lblMensaje.setForeground(new Color(244, 67, 54));
				lblMensaje.setVisible(true);
				return;
			}

			DtoAdministrador.bloquearCuenta(id);
			actualizarTablaCuentas(modelo);

			lblMensaje.setText("Cuenta bloqueada exitosamente");
			lblMensaje.setForeground(new Color(76, 175, 80));
			lblMensaje.setVisible(true);
		});
		panel.add(btnBloquear);

		JButton btnDesbloquear = new JButton("Desbloquear");
		btnDesbloquear.setBounds(440, 357, 150, 30);
		btnDesbloquear.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		btnDesbloquear.addActionListener(e -> {
			int fila = tabla.getSelectedRow();
			if (fila == -1) {
				lblMensaje.setText("Seleccione un usuario de la tabla");
				lblMensaje.setForeground(new Color(255, 152, 0));
				lblMensaje.setVisible(true);
				return;
			}

			int id = (int) modelo.getValueAt(fila, 0);

			String estado = modelo.getValueAt(fila, 5).toString();

			if (estado.equalsIgnoreCase("Activo") || estado.equalsIgnoreCase("false")
					|| estado.equalsIgnoreCase("Desbloqueado")) {
				lblMensaje.setText("La cuenta ya está desbloqueada");
				lblMensaje.setForeground(new Color(244, 67, 54));
				lblMensaje.setVisible(true);
				return;
			}

			DtoAdministrador.desbloquearCuenta(id);
			actualizarTablaCuentas(modelo);

			lblMensaje.setText("Cuenta desbloqueada exitosamente");
			lblMensaje.setForeground(new Color(76, 175, 80));
			lblMensaje.setVisible(true);
		});
		panel.add(btnDesbloquear);

		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(610, 357, 130, 30);
		btnEliminar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		btnEliminar.addActionListener(e -> {
			int fila = tabla.getSelectedRow();
			if (fila == -1) {
				JOptionPane.showMessageDialog(panel, "Seleccione un usuario", "Aviso", JOptionPane.WARNING_MESSAGE);
				return;
			}
			int id = (int) modelo.getValueAt(fila, 0);
			int confirm = JOptionPane.showConfirmDialog(panel, "¿Eliminar usuario?", "Confirmar",
					JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				DtoAdministrador.eliminarCuenta(id);
				actualizarTablaCuentas(modelo);
			}
		});
		panel.add(btnEliminar);

		JButton btnStats = new JButton("Estadísticas");
		btnStats.setBounds(580, 23, 150, 30);
		btnStats.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		btnStats.addActionListener(e -> {
			crearPanelEstadisticas();
		});
		panel.add(btnStats);

		actualizarTablaCuentas(modelo);
		return panel;
	}

	private void crearPanelEstadisticas() {

		JFrame frame = new JFrame("Estadísticas del Sistema");
		frame.setSize(600, 550);
		frame.setLocationRelativeTo(this);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setBackground(new Color(179, 217, 255));

		JLabel lblTitulo = new JLabel("Estadísticas del Sistema");
		lblTitulo.setBounds(150, 20, 300, 30);
		lblTitulo.setFont(new Font("Mongolian Baiti", Font.BOLD, 22));
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lblTitulo);

		JPanel panelStats = new JPanel();
		panelStats.setBounds(50, 70, 500, 350);
		panelStats.setBackground(Color.WHITE);
		panelStats.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
		panelStats.setLayout(null);
		frame.getContentPane().add(panelStats);

		String statsString = DtoAdministrador.obtenerEstadisticas();
		String[] lineas = statsString.split("\n");

		int yPos = 30;
		int spacing = 50;

		for (int i = 0; i < lineas.length; i++) {
			String linea = lineas[i];
			if (linea.contains(":")) {

				String[] partes = linea.split(":");
				String etiqueta = partes[0].trim();
				String valor = (partes.length > 1) ? partes[1].trim() : "0";

				JLabel lblEtiqueta = new JLabel(etiqueta + ":");
				lblEtiqueta.setBounds(50, yPos, 300, 25);

				JLabel lblValor = new JLabel(valor);
				lblValor.setBounds(380, yPos, 100, 25);

				if (linea.contains("Total de usuarios")) {
					lblEtiqueta.setFont(new Font("Mongolian Baiti", Font.BOLD, 16));
					lblValor.setFont(new Font("Mongolian Baiti", Font.PLAIN, 16));
					lblValor.setForeground(new Color(33, 150, 243));

				} else if (linea.contains("Clientes") || linea.contains("Encargados")
						|| linea.contains("Administradores")) {

					lblEtiqueta.setText("  • " + etiqueta + ":");
					lblEtiqueta.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
					lblValor.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));

				} else if (linea.contains("reservas")) {
					lblEtiqueta.setFont(new Font("Mongolian Baiti", Font.BOLD, 16));
					lblValor.setFont(new Font("Mongolian Baiti", Font.PLAIN, 16));
					lblValor.setForeground(new Color(76, 175, 80));

					if (i > 0) {
						JSeparator separator = new JSeparator();
						separator.setBounds(50, yPos - 15, 400, 2);
						panelStats.add(separator);
					}

				} else if (linea.contains("hoteles")) {
					lblEtiqueta.setFont(new Font("Mongolian Baiti", Font.BOLD, 16));
					lblValor.setFont(new Font("Mongolian Baiti", Font.PLAIN, 16));
					lblValor.setForeground(new Color(255, 152, 0));
				}

				panelStats.add(lblEtiqueta);
				panelStats.add(lblValor);

				yPos += spacing;
			}
		}
		frame.setVisible(true);

		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.setBounds(225, 440, 150, 35);
		btnCerrar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		btnCerrar.addActionListener(e -> frame.dispose());
		frame.getContentPane().add(btnCerrar);

		frame.setVisible(true);
	}

	private void actualizarTablaCuentas(DefaultTableModel modelo) {
		modelo.setRowCount(0);
		List<Usuario> usuarios = DtoAdministrador.verCuentas();
		for (Usuario u : usuarios) {
			String tipo = u.getTipo_usuario().equals("1") ? "Cliente"
					: u.getTipo_usuario().equals("2") ? "Encargado" : "Administrador";
			modelo.addRow(new Object[] { u.getId(), u.getUser(), u.getNombre(), u.getApellido(), u.getDni(), tipo,
					u.getEstado() });
		}
	}

	private void crearPanelCrearCuenta() {
		JFrame frame = new JFrame("Crear Nueva Cuenta");
		frame.setSize(550, 700);
		frame.setLocationRelativeTo(this);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setBackground(new Color(179, 217, 255));

		JLabel lblTitulo = new JLabel("Crear Nueva Cuenta de Usuario");
		lblTitulo.setBounds(120, 20, 350, 30);
		lblTitulo.setFont(new Font("Mongolian Baiti", Font.PLAIN, 20));
		frame.getContentPane().add(lblTitulo);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(50, 70, 120, 25);
		lblNombre.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(lblNombre);
		JTextField txtNombre = new JTextField();
		txtNombre.setBounds(180, 70, 300, 25);
		txtNombre.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(txtNombre);

		JLabel lblApellido = new JLabel("Apellido:");
		lblApellido.setBounds(50, 110, 120, 25);
		lblApellido.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(lblApellido);
		JTextField txtApellido = new JTextField();
		txtApellido.setBounds(180, 110, 300, 25);
		txtApellido.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(txtApellido);

		JLabel lblFechaNac = new JLabel("Fecha Nac:");
		lblFechaNac.setBounds(50, 150, 120, 25);
		lblFechaNac.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(lblFechaNac);

		JDateChooser dateChooserFechaNac = new JDateChooser();
		dateChooserFechaNac.setBounds(180, 150, 150, 25);
		dateChooserFechaNac.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		dateChooserFechaNac.setDateFormatString("yyyy-MM-dd");

		Calendar cal = Calendar.getInstance();
		cal.set(2000, Calendar.JANUARY, 1);
		dateChooserFechaNac.setDate(cal.getTime());

		frame.getContentPane().add(dateChooserFechaNac);

		JLabel lblMail = new JLabel("Email:");
		lblMail.setBounds(50, 190, 120, 25);
		lblMail.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(lblMail);
		JTextField txtMail = new JTextField();
		txtMail.setBounds(180, 190, 300, 25);
		txtMail.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(txtMail);

		JLabel lblDNI = new JLabel("DNI:");
		lblDNI.setBounds(50, 230, 120, 25);
		lblDNI.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(lblDNI);
		JSpinner spnDNI = new JSpinner(new SpinnerNumberModel(10000000, 1000000, 99999999, 1));
		spnDNI.setBounds(180, 230, 150, 25);
		spnDNI.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(spnDNI);

		JLabel lblDireccion = new JLabel("Dirección:");
		lblDireccion.setBounds(50, 270, 120, 25);
		lblDireccion.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(lblDireccion);
		JTextField txtDireccion = new JTextField();
		txtDireccion.setBounds(180, 270, 300, 25);
		txtDireccion.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(txtDireccion);

		JLabel lblUser = new JLabel("Usuario:");
		lblUser.setBounds(50, 310, 120, 25);
		lblUser.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(lblUser);
		JTextField txtUser = new JTextField();
		txtUser.setBounds(180, 310, 200, 25);
		txtUser.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(txtUser);

		JLabel lblPass = new JLabel("Contraseña:");
		lblPass.setBounds(50, 350, 120, 25);
		lblPass.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(lblPass);
		JPasswordField txtPass = new JPasswordField();
		txtPass.setBounds(180, 350, 200, 25);
		txtPass.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(txtPass);

		JLabel lblPregunta = new JLabel("Pregunta Seg:");
		lblPregunta.setBounds(50, 390, 120, 25);
		lblPregunta.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(lblPregunta);
		JTextField txtPregunta = new JTextField();
		txtPregunta.setBounds(180, 390, 300, 25);
		txtPregunta.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(txtPregunta);

		JLabel lblRespuesta = new JLabel("Respuesta:");
		lblRespuesta.setBounds(50, 430, 120, 25);
		lblRespuesta.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(lblRespuesta);
		JTextField txtRespuesta = new JTextField();
		txtRespuesta.setBounds(180, 430, 300, 25);
		txtRespuesta.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(txtRespuesta);

		JLabel lblTipo = new JLabel("Tipo Usuario:");
		lblTipo.setBounds(50, 470, 120, 25);
		lblTipo.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		frame.getContentPane().add(lblTipo);
		JComboBox<String> cmbTipo = new JComboBox<>(
				new String[] { "1 - Cliente", "2 - Encargado", "3 - Administrador" });
		cmbTipo.setBounds(180, 470, 200, 25);
		cmbTipo.setFont(new Font("Mongolian Baiti", Font.PLAIN, 13));
		frame.getContentPane().add(cmbTipo);

		JComboBox<String> cmbHotel = new JComboBox<>();
		cmbHotel.setBounds(180, 510, 300, 25);
		cmbHotel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 13));
		cmbHotel.setVisible(false);
		frame.getContentPane().add(cmbHotel);

		JLabel lblHotel = new JLabel("Hotel:");
		lblHotel.setBounds(50, 510, 120, 25);
		lblHotel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		lblHotel.setVisible(false);
		frame.getContentPane().add(lblHotel);

		cmbTipo.addActionListener(e -> {
			String sel = (String) cmbTipo.getSelectedItem();
			if (sel.startsWith("2")) {
				lblHotel.setVisible(true);
				cmbHotel.setVisible(true);
				cargarHotelesEnCombo(cmbHotel);
			} else {
				lblHotel.setVisible(false);
				cmbHotel.setVisible(false);
			}
		});

		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(140, 560, 120, 35);
		btnGuardar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		btnGuardar.addActionListener(e -> {
			if (txtNombre.getText().trim().isEmpty() || txtUser.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(frame, "Complete todos los campos obligatorios", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				String nombre = txtNombre.getText();
				String apellido = txtApellido.getText();
				LocalDate fechaNac = dateChooserFechaNac.getDate().toInstant().atZone(ZoneId.systemDefault())
						.toLocalDate();
				String mail = txtMail.getText();
				int dni = (int) spnDNI.getValue();
				String direccion = txtDireccion.getText();
				String user = txtUser.getText();
				String pass = new String(txtPass.getPassword());
				String pregunta = txtPregunta.getText();
				String respuesta = txtRespuesta.getText();
				String selTipo = (String) cmbTipo.getSelectedItem();
				String tipoUsuario = selTipo.split(" - ")[0];

				Usuario nuevoUsuario = new Usuario(nombre, apellido, fechaNac, mail, dni, direccion, user, pass,
						pregunta, respuesta);
				nuevoUsuario.setTipo_usuario(tipoUsuario);
				nuevoUsuario.setFecha_creacion(LocalDate.now());
				nuevoUsuario.setEstado("activo");

				if (DtoAdministrador.crearUsuario(nuevoUsuario)) {
					if (tipoUsuario.equals("2") && cmbHotel.getSelectedItem() != null) {
						String selHotel = (String) cmbHotel.getSelectedItem();
						int idHotel = Integer.parseInt(selHotel.split(" - ")[0]);
						DtoAdministrador.crearEncargado(nuevoUsuario.getId(), idHotel);
					}
					frame.dispose();
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(frame, "Error en los datos: " + ex.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		});
		frame.getContentPane().add(btnGuardar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(280, 560, 120, 35);
		btnCancelar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		btnCancelar.addActionListener(e -> frame.dispose());
		frame.getContentPane().add(btnCancelar);

		frame.setVisible(true);
	}

	private JPanel crearPanelSistema() {
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setLayout(null);

		JLabel lblTitulo = new JLabel("Gestión del Sistema");
		lblTitulo.setBounds(300, 10, 250, 30);
		lblTitulo.setFont(new Font("Mongolian Baiti", Font.PLAIN, 20));
		panel.add(lblTitulo);

		JPanel panelInfo = new JPanel();
		panelInfo.setBounds(26, 51, 706, 84);
		panelInfo.setBackground(new Color(240, 240, 240));
		panelInfo.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		panelInfo.setLayout(null);

		JLabel lblEstadoTitulo = new JLabel("Estado del Sistema:");
		lblEstadoTitulo.setBounds(20, 15, 200, 25);
		lblEstadoTitulo.setFont(new Font("Mongolian Baiti", Font.BOLD, 14));
		panelInfo.add(lblEstadoTitulo);

		JLabel lblEstadoActual = new JLabel();
		lblEstadoActual.setBounds(154, 15, 510, 25);
		lblEstadoActual.setFont(new Font("Mongolian Baiti", Font.BOLD, 15));
		panelInfo.add(lblEstadoActual);

		JLabel lblDescripcion = new JLabel();
		lblDescripcion.setBounds(20, 44, 676, 40);
		lblDescripcion.setFont(new Font("Mongolian Baiti", Font.PLAIN, 12));
		lblDescripcion.setForeground(Color.DARK_GRAY);
		panelInfo.add(lblDescripcion);

		panel.add(panelInfo);

		JPanel panelAccion = new JPanel();
		panelAccion.setBounds(26, 161, 706, 180);
		panelAccion.setBackground(new Color(250, 250, 250));
		panelAccion.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		panelAccion.setLayout(null);

		JLabel lblPregunta = new JLabel();
		lblPregunta.setBounds(103, 11, 510, 30);
		lblPregunta.setFont(new Font("Mongolian Baiti", Font.BOLD, 14));
		lblPregunta.setHorizontalAlignment(SwingConstants.CENTER);
		panelAccion.add(lblPregunta);

		JLabel lblAdvertencia = new JLabel();
		lblAdvertencia.setBounds(89, 44, 510, 40);
		lblAdvertencia.setFont(new Font("Mongolian Baiti", Font.ITALIC, 12));
		lblAdvertencia.setForeground(new Color(200, 0, 0));
		lblAdvertencia.setHorizontalAlignment(SwingConstants.CENTER);
		panelAccion.add(lblAdvertencia);

		JLabel lblMensaje = new JLabel();
		lblMensaje.setBounds(89, 81, 510, 25);
		lblMensaje.setFont(new Font("Mongolian Baiti", Font.BOLD, 13));
		lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
		lblMensaje.setVisible(false);
		panelAccion.add(lblMensaje);

		JButton btnConfirmar = new JButton();
		btnConfirmar.setBounds(188, 117, 120, 35);
		btnConfirmar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		btnConfirmar.setFocusPainted(false);
		panelAccion.add(btnConfirmar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(381, 117, 120, 35);
		btnCancelar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		btnCancelar.setBackground(Color.LIGHT_GRAY);
		btnCancelar.setFocusPainted(false);
		panelAccion.add(btnCancelar);

		panel.add(panelAccion);

		Runnable actualizarUI = () -> {
			lblMensaje.setVisible(false);

			if (DtoUsuario.chequeoSuspension()) {
				lblEstadoActual.setText("SISTEMA SUSPENDIDO");
				lblEstadoActual.setForeground(new Color(244, 67, 54));
				lblDescripcion.setText("El sistema está fuera de servicio. Los usuarios no pueden acceder.");

				lblPregunta.setText("¿Desea reactivar el sistema?");
				lblAdvertencia.setText("El sistema volverá a estar operativo para todos los usuarios");
				lblAdvertencia.setForeground(new Color(76, 175, 80));

				btnConfirmar.setText("Reactivar");
				btnConfirmar.setBackground(new Color(76, 175, 80));
				btnConfirmar.setForeground(Color.WHITE);

			} else {
				lblEstadoActual.setText("SISTEMA OPERATIVO");
				lblEstadoActual.setForeground(new Color(76, 175, 80));
				lblDescripcion.setText("El sistema está funcionando normalmente. Todos los servicios disponibles.");

				lblPregunta.setText("¿Desea suspender el sistema?");
				lblAdvertencia.setText("ADVERTENCIA: El programa quedará fuera de usopara todos los usuarios");
				lblAdvertencia.setForeground(new Color(244, 67, 54));

				btnConfirmar.setText("Suspender");
				btnConfirmar.setBackground(new Color(244, 67, 54));
				btnConfirmar.setForeground(Color.WHITE);
			}
		};

		btnConfirmar.addActionListener(e -> {
			if (DtoUsuario.chequeoSuspension()) {
				DtoAdministrador.activarSistema();
				lblMensaje.setText("Sistema reactivado exitosamente");
				lblMensaje.setForeground(new Color(76, 175, 80));
			} else {
				DtoAdministrador.suspenderSistema();
				lblMensaje.setText("Sistema suspendido exitosamente");
				lblMensaje.setForeground(new Color(244, 67, 54));
			}

			lblMensaje.setVisible(true);
			actualizarUI.run();
		});

		btnCancelar.addActionListener(e -> {
			lblMensaje.setText("Operación cancelada");
			lblMensaje.setForeground(Color.GRAY);
			lblMensaje.setVisible(true);

			Timer timer = new Timer(2000, evt -> lblMensaje.setVisible(false));
			timer.setRepeats(false);
			timer.start();
		});
		actualizarUI.run();

		return panel;
	}

}
