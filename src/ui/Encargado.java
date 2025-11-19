package ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
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

import bll.Reserva;
import dll.DtoEncargado;

public class Encargado extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private bll.Encargado encargado;
	private JTable tablePendientes;
	private JTable tableFinalizadas;
	private JTable tableHabitaciones;
	private DefaultTableModel modelPendientes;
	private DefaultTableModel modelFinalizadas;
	private DefaultTableModel modelHabitaciones;
	private javax.swing.JComboBox<String> comboReservasCheckin;
	private javax.swing.JComboBox<String> comboReservasCheckout;

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

	public Encargado() {
		this(null);
	}

	public Encargado(bll.Encargado encargado) {
		this.encargado = encargado;
		initComponents();

		// Cargar datos
		if (encargado != null) {
			lblbienvenido.setText("Bienvenido " + encargado.getNombre());
			lblHotel.setText("Administración Hotel - " + encargado.getId_hotel());
			cargarReservas();
			cargarHabitaciones();
			cargarReservasCheckin(comboReservasCheckin);
			cargarReservasCheckout(comboReservasCheckout);
		}
	}

	private void initComponents() {
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

		// PANEL RESERVAS
		JPanel panelReservas = new JPanel();
		panelReservas.setLayout(new java.awt.GridLayout(1, 2, 10, 0));
		panelReservas.setBackground(Color.WHITE);

		// Panel Pendientes
		JPanel panelPendientes = new JPanel();
		panelPendientes.setBackground(new Color(255, 255, 255));
		panelPendientes.setLayout(null);

		JLabel labelPendientes = new JLabel("Reservas Pendientes");
		labelPendientes.setBounds(10, 5, 200, 25);
		labelPendientes.setFont(new Font("Mongolian Baiti", Font.BOLD, 16));
		panelPendientes.add(labelPendientes);

		// Tabla
		String[] columnasPendientes = { "ID", "Cliente", "DNI", "Habitación", "Precio" };
		modelPendientes = new DefaultTableModel(columnasPendientes, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tablePendientes = new JTable(modelPendientes);
		tablePendientes.setFont(new Font("Mongolian Baiti", Font.PLAIN, 12));
		tablePendientes.getTableHeader().setFont(new Font("Mongolian Baiti", Font.BOLD, 12));

		JScrollPane scrollPendientes = new JScrollPane(tablePendientes);
		scrollPendientes.setBounds(10, 35, 349, 323);
		panelPendientes.add(scrollPendientes);

		// Panel Finalizadas
		JPanel panelFinalizadas = new JPanel();
		panelFinalizadas.setBackground(new Color(255, 255, 255));
		panelFinalizadas.setLayout(null);

		JLabel labelFinalizadas = new JLabel("Reservas Finalizadas");
		labelFinalizadas.setBounds(10, 5, 200, 25);
		labelFinalizadas.setFont(new Font("Mongolian Baiti", Font.BOLD, 16));
		panelFinalizadas.add(labelFinalizadas);

		// Tabla
		String[] columnasFinalizadas = { "ID", "Cliente", "DNI", "Habitación", "Monto Final" };
		modelFinalizadas = new DefaultTableModel(columnasFinalizadas, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tableFinalizadas = new JTable(modelFinalizadas);
		tableFinalizadas.setFont(new Font("Mongolian Baiti", Font.PLAIN, 12));
		tableFinalizadas.getTableHeader().setFont(new Font("Mongolian Baiti", Font.BOLD, 12));

		JScrollPane scrollFinalizadas = new JScrollPane(tableFinalizadas);
		scrollFinalizadas.setBounds(10, 35, 349, 323);
		panelFinalizadas.add(scrollFinalizadas);

		panelReservas.add(panelPendientes);
		panelReservas.add(panelFinalizadas);

		Contenidos.addTab("Reservas", null, panelReservas, null);

		// PANEL HABITACIONES
		JPanel panelHabitaciones = new JPanel();
		panelHabitaciones.setLayout(null);
		panelHabitaciones.setBackground(Color.WHITE);

		JLabel labelHabitaciones = new JLabel("Habitaciones del Hotel");
		labelHabitaciones.setBounds(20, 10, 300, 30);
		labelHabitaciones.setFont(new Font("Mongolian Baiti", Font.BOLD, 18));
		panelHabitaciones.add(labelHabitaciones);

		// Tabla
		String[] columnasHabitaciones = { "ID", "Número", "Tipo", "Camas", "Precio/Noche", "Estado" };
		modelHabitaciones = new DefaultTableModel(columnasHabitaciones, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tableHabitaciones = new JTable(modelHabitaciones);
		tableHabitaciones.setFont(new Font("Mongolian Baiti", Font.PLAIN, 13));
		tableHabitaciones.getTableHeader().setFont(new Font("Mongolian Baiti", Font.BOLD, 13));
		tableHabitaciones.setRowHeight(25);

		javax.swing.table.DefaultTableCellRenderer centerRenderer = new javax.swing.table.DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(javax.swing.JLabel.CENTER);
		tableHabitaciones.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);

		tableHabitaciones.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				java.awt.Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						column);

				if (!isSelected) {
					String estado = (String) table.getValueAt(row, 5);
					if ("disponible".equalsIgnoreCase(estado)) {
						c.setBackground(new Color(200, 255, 200)); // Verde
					} else if ("ocupada".equalsIgnoreCase(estado)) {
						c.setBackground(new Color(255, 200, 200)); // Rojo
					} else {
						c.setBackground(Color.WHITE);
					}
				}

				return c;
			}
		});

		JScrollPane scrollHabitaciones = new JScrollPane(tableHabitaciones);
		scrollHabitaciones.setBounds(20, 50, 700, 320);
		panelHabitaciones.add(scrollHabitaciones);

		Contenidos.addTab("Habitaciones", null, panelHabitaciones, null);

		// PANEL CHECK-IN
		JPanel panelCheckin = new JPanel();
		panelCheckin.setLayout(null);
		panelCheckin.setBackground(Color.WHITE);

		JLabel labelCheckin = new JLabel("REALIZAR CHECK-IN");
		labelCheckin.setBounds(250, 20, 300, 35);
		labelCheckin.setFont(new Font("Mongolian Baiti", Font.BOLD, 20));
		panelCheckin.add(labelCheckin);

		JLabel lblInfoCheckin = new JLabel("Seleccione una reserva pendiente y complete los datos:");
		lblInfoCheckin.setBounds(180, 60, 400, 25);
		lblInfoCheckin.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		panelCheckin.add(lblInfoCheckin);

		// Reservas pendientes
		JLabel lblReservaCheckin = new JLabel("Reserva:");
		lblReservaCheckin.setBounds(100, 110, 100, 25);
		lblReservaCheckin.setFont(new Font("Mongolian Baiti", Font.BOLD, 15));
		panelCheckin.add(lblReservaCheckin);

		javax.swing.JComboBox<String> comboReservasCheckin = new javax.swing.JComboBox<>();
		comboReservasCheckin.setBounds(100, 140, 550, 30);
		comboReservasCheckin.setFont(new Font("Mongolian Baiti", Font.PLAIN, 13));
		panelCheckin.add(comboReservasCheckin);
		this.comboReservasCheckin = comboReservasCheckin;

		JLabel lblDniCheckin = new JLabel("DNI del Cliente:");
		lblDniCheckin.setBounds(100, 200, 150, 25);
		lblDniCheckin.setFont(new Font("Mongolian Baiti", Font.BOLD, 15));
		panelCheckin.add(lblDniCheckin);

		javax.swing.JTextField txtDniCheckin = new javax.swing.JTextField();
		txtDniCheckin.setBounds(100, 230, 250, 30);
		txtDniCheckin.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		panelCheckin.add(txtDniCheckin);

		JLabel lblTarjeta = new JLabel("Tarjeta de Resguardo:");
		lblTarjeta.setBounds(400, 200, 200, 25);
		lblTarjeta.setFont(new Font("Mongolian Baiti", Font.BOLD, 15));
		panelCheckin.add(lblTarjeta);

		javax.swing.JTextField txtTarjeta = new javax.swing.JTextField();
		txtTarjeta.setBounds(400, 230, 250, 30);
		txtTarjeta.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		panelCheckin.add(txtTarjeta);

		// Mensajes
		JLabel lblMensajeCheckin = new JLabel("");
		lblMensajeCheckin.setBounds(100, 275, 550, 25);
		lblMensajeCheckin.setFont(new Font("Mongolian Baiti", Font.PLAIN, 13));
		lblMensajeCheckin.setForeground(Color.RED);
		panelCheckin.add(lblMensajeCheckin);

		JButton btnRealizarCheckin = new JButton("REALIZAR CHECK-IN");
		btnRealizarCheckin.setBounds(250, 310, 250, 40);
		btnRealizarCheckin.setFont(new Font("Mongolian Baiti", Font.BOLD, 16));
		btnRealizarCheckin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblMensajeCheckin.setText("");

				if (comboReservasCheckin.getSelectedItem() == null
						|| comboReservasCheckin.getSelectedItem().toString().equals("No hay reservas pendientes")) {
					lblMensajeCheckin.setForeground(Color.RED);
					lblMensajeCheckin.setText("No hay reservas pendientes para check-in");
					return;
				}

				String dniTexto = txtDniCheckin.getText().trim();
				String tarjeta = txtTarjeta.getText().trim();

				if (dniTexto.isEmpty() || tarjeta.isEmpty()) {
					lblMensajeCheckin.setForeground(Color.RED);
					lblMensajeCheckin.setText("Complete todos los campos (DNI y Tarjeta)");
					return;
				}

				String seleccion = (String) comboReservasCheckin.getSelectedItem();
				int idReserva = Integer.parseInt(seleccion.split(" ")[1]);

				boolean exito = DtoEncargado.realizarCheckin(idReserva, dniTexto, tarjeta, encargado.getId_hotel(),
						lblMensajeCheckin);

				if (exito) {
					txtDniCheckin.setText("");
					txtTarjeta.setText("");
					cargarReservas();
					cargarHabitaciones();
					cargarReservasCheckin(comboReservasCheckin);
					cargarReservasCheckout(comboReservasCheckout);
				}
			}
		});
		panelCheckin.add(btnRealizarCheckin);

		Contenidos.addTab("Check-in", null, panelCheckin, null);

		// PANEL CHECK-OUT
		JPanel panelCheckout = new JPanel();
		panelCheckout.setLayout(null);
		panelCheckout.setBackground(Color.WHITE);

		JLabel labelCheckout = new JLabel("REALIZAR CHECK-OUT");
		labelCheckout.setBounds(240, 20, 320, 35);
		labelCheckout.setFont(new Font("Mongolian Baiti", Font.BOLD, 20));
		panelCheckout.add(labelCheckout);

		JLabel lblInfoCheckout = new JLabel("Seleccione una reserva activa para finalizar la estadía:");
		lblInfoCheckout.setBounds(170, 60, 450, 25);
		lblInfoCheckout.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		panelCheckout.add(lblInfoCheckout);

		JLabel lblReservaCheckout = new JLabel("Reserva:");
		lblReservaCheckout.setBounds(100, 110, 100, 25);
		lblReservaCheckout.setFont(new Font("Mongolian Baiti", Font.BOLD, 15));
		panelCheckout.add(lblReservaCheckout);

		javax.swing.JComboBox<String> comboReservasCheckout = new javax.swing.JComboBox<>();
		comboReservasCheckout.setBounds(100, 140, 550, 30);
		comboReservasCheckout.setFont(new Font("Mongolian Baiti", Font.PLAIN, 13));
		panelCheckout.add(comboReservasCheckout);
		this.comboReservasCheckout = comboReservasCheckout;

		// Mensajes
		JLabel lblMensajeCheckout = new JLabel("");
		lblMensajeCheckout.setBounds(100, 185, 550, 25);
		lblMensajeCheckout.setFont(new Font("Mongolian Baiti", Font.PLAIN, 13));
		lblMensajeCheckout.setForeground(Color.RED);
		panelCheckout.add(lblMensajeCheckout);

		JButton btnRealizarCheckout = new JButton("REALIZAR CHECK-OUT");
		btnRealizarCheckout.setBounds(250, 220, 250, 40);
		btnRealizarCheckout.setFont(new Font("Mongolian Baiti", Font.BOLD, 16));
		btnRealizarCheckout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblMensajeCheckout.setText("");

				if (comboReservasCheckout.getSelectedItem() == null
						|| comboReservasCheckout.getSelectedItem().toString().equals("No hay reservas activas")) {
					lblMensajeCheckout.setForeground(Color.RED);
					lblMensajeCheckout.setText("No hay reservas activas para check-out");
					return;
				}

				String seleccion = (String) comboReservasCheckout.getSelectedItem();
				int idReserva = Integer.parseInt(seleccion.split(" ")[1]);

				boolean exito = DtoEncargado.realizarCheckout(idReserva, encargado.getId_hotel(), lblMensajeCheckout);

				if (exito) {
					cargarReservas();
					cargarHabitaciones();
					cargarReservasCheckin(comboReservasCheckin);
					cargarReservasCheckout(comboReservasCheckout);
				}
			}
		});
		panelCheckout.add(btnRealizarCheckout);

		Contenidos.addTab("Check-out", null, panelCheckout, null);

		//PANEL VISTAS
		JPanel panelVista = new JPanel();
		panelVista.setLayout(null);
		panelVista.setBackground(Color.WHITE);

		JLabel lblTituloVistas = new JLabel("PAQUETES Y ACTIVIDADES");
		lblTituloVistas.setBounds(250, 10, 300, 30);
		lblTituloVistas.setFont(new Font("Mongolian Baiti", Font.BOLD, 20));
		panelVista.add(lblTituloVistas);

		javax.swing.JTextArea txtAreaVistas = new javax.swing.JTextArea();
		txtAreaVistas.setFont(new Font("Mongolian Baiti", Font.PLAIN, 13));
		txtAreaVistas.setEditable(false);
		txtAreaVistas.setLineWrap(true);
		txtAreaVistas.setWrapStyleWord(true);
		txtAreaVistas.setText("Seleccione una opción para ver la información...");

		JScrollPane scrollVistas = new JScrollPane(txtAreaVistas);
		scrollVistas.setBounds(61, 55, 600, 250);
		panelVista.add(scrollVistas);

		//Botón Ver Paquetes
		JButton btnverPaquetes = new JButton("VER PAQUETES");
		btnverPaquetes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (encargado != null) {
					bll.Encargado.verPaquetesDelHotel(encargado.getId_hotel(), txtAreaVistas);
				}
			}
		});
		btnverPaquetes.setFont(new Font("Mongolian Baiti", Font.BOLD, 16));
		btnverPaquetes.setBounds(61, 325, 250, 40);
		panelVista.add(btnverPaquetes);

		//Botón Ver Actividades
		JButton btnverActividades = new JButton("VER ACTIVIDADES");
		btnverActividades.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (encargado != null) {
					bll.Encargado.verActividades(encargado.getId_hotel(), txtAreaVistas);
				}
			}
		});
		btnverActividades.setFont(new Font("Mongolian Baiti", Font.BOLD, 16));
		btnverActividades.setBounds(411, 325, 250, 40);
		panelVista.add(btnverActividades);

		Contenidos.addTab("Vistas", null, panelVista, null);

		// PANEL PROMOCIONES
		JPanel panelPromociones = new JPanel();
		panelPromociones.setLayout(null);
		panelPromociones.setBackground(Color.WHITE);

		// Título principal
		JLabel lblTituloPromociones = new JLabel("GESTIÓN DE PROMOCIONES");
		lblTituloPromociones.setBounds(230, 10, 350, 35);
		lblTituloPromociones.setFont(new Font("Mongolian Baiti", Font.BOLD, 20));
		panelPromociones.add(lblTituloPromociones);

		JLabel lblSubtitulo = new JLabel("Administre las promociones y descuentos de su hotel");
		lblSubtitulo.setBounds(200, 45, 400, 25);
		lblSubtitulo.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		lblSubtitulo.setForeground(new Color(100, 100, 100));
		panelPromociones.add(lblSubtitulo);

		// Área de texto para mostrar información
		javax.swing.JTextArea txtAreaPromociones = new javax.swing.JTextArea();
		txtAreaPromociones.setFont(new Font("Mongolian Baiti", Font.PLAIN, 12));
		txtAreaPromociones.setEditable(false);
		txtAreaPromociones.setLineWrap(true);
		txtAreaPromociones.setWrapStyleWord(true);
		txtAreaPromociones.setText("Seleccione una opción para gestionar promociones...");
		txtAreaPromociones.setBackground(new Color(250, 250, 250));

		JScrollPane scrollPromociones = new JScrollPane(txtAreaPromociones);
		scrollPromociones.setBounds(30, 85, 690, 180);
		scrollPromociones.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(200, 200, 200)));
		panelPromociones.add(scrollPromociones);

		// Mensajes de estado
		JLabel lblMensajePromo = new JLabel("");
		lblMensajePromo.setBounds(30, 275, 690, 25);
		lblMensajePromo.setFont(new Font("Mongolian Baiti", Font.BOLD, 13));
		lblMensajePromo.setForeground(Color.BLUE);
		lblMensajePromo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		panelPromociones.add(lblMensajePromo);

		// FILA 1 DE BOTONES
		// Botón Crear Promoción
		JButton btnCrearPromocion = new JButton("CREAR PROMOCIÓN");
		btnCrearPromocion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblMensajePromo.setText("");
				if (encargado != null) {
					txtAreaPromociones.setText("Creando nueva promoción...\n\n");
					try {
						// Llamar al método estático del BLL
						bll.Encargado.crearPromocion(encargado.getId_hotel());
						
						// Actualizar la vista
						lblMensajePromo.setForeground(new Color(0, 150, 0));
						lblMensajePromo.setText("Operación completada. Use 'Ver Promociones' para verificar.");
					} catch (Exception ex) {
						lblMensajePromo.setForeground(Color.RED);
						lblMensajePromo.setText("Error al crear promoción");
						txtAreaPromociones.setText("Error: " + ex.getMessage());
					}
				}
			}
		});
		btnCrearPromocion.setFont(new Font("Mongolian Baiti", Font.BOLD, 13));
		btnCrearPromocion.setBounds(30, 310, 220, 35);
		btnCrearPromocion.setFocusPainted(false);
		panelPromociones.add(btnCrearPromocion);

		// Botón Ver Promociones
		JButton btnVerPromociones = new JButton("VER PROMOCIONES");
		btnVerPromociones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblMensajePromo.setText("");
				if (encargado != null) {
					List<bll.Promocion> promociones = DtoEncargado.verPromocionesDelHotel(encargado.getId_hotel());
					
					if (promociones.isEmpty()) {
						txtAreaPromociones.setText("No hay promociones registradas para su hotel.\n\n"
								+ "Use el botón 'CREAR PROMOCIÓN' para agregar una nueva.");
						lblMensajePromo.setForeground(new Color(200, 100, 0));
						lblMensajePromo.setText("No hay promociones disponibles");
					} else {
						String texto = "PROMOCIONES DE MI HOTEL\n";
						
						for (bll.Promocion p : promociones) {
							texto += "ID: " + p.getId() + "\n";
							texto += "Nombre: " + p.getNombre() + "\n";
							texto += "Descuento: " + p.getPorcentajeDescuento() + "%\n";
							texto += "Vigencia: " + p.getFechaInicio() + " al " + p.getFechaFin() + "\n";
							texto += "Estado: " + p.getEstado();
							
							if (p.estaVigente()) {
								texto += "VIGENTE\n";
							} else if (p.getEstado().equals("activa")) {
								LocalDate hoy = LocalDate.now();
								if (hoy.isBefore(p.getFechaInicio())) {
									texto += " (Aún no comenzó)\n";
								} else {
									texto += " (Expirada)\n";
								}
							} else {
								texto += "\n";
							}
							
							if (p.getDescripcion() != null && !p.getDescripcion().isEmpty()) {
								texto += "Descripción: " + p.getDescripcion() + "\n";
							}
							texto += "\n";
						}
						
						txtAreaPromociones.setText(texto);
						txtAreaPromociones.setCaretPosition(0);
						lblMensajePromo.setForeground(new Color(0, 100, 200));
						lblMensajePromo.setText("Mostrando " + promociones.size() + " promoción(es)");
					}
				}
			}
		});
		btnVerPromociones.setFont(new Font("Mongolian Baiti", Font.BOLD, 13));
		btnVerPromociones.setBounds(265, 310, 220, 35);
		btnVerPromociones.setFocusPainted(false);
		panelPromociones.add(btnVerPromociones);

		// Botón Aplicar Promoción
		JButton btnAplicarPromocion = new JButton("APLICAR A PAQUETE");
		btnAplicarPromocion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblMensajePromo.setText("");
				if (encargado != null) {
					txtAreaPromociones.setText("Aplicando promoción a paquete...\n\n");
					try {
						// Llamar al método estático del BLL
						bll.Encargado.aplicarPromocionAPaquete(encargado.getId_hotel());
						
						lblMensajePromo.setForeground(new Color(0, 150, 0));
						lblMensajePromo.setText("✓ Operación completada");
					} catch (Exception ex) {
						lblMensajePromo.setForeground(Color.RED);
						lblMensajePromo.setText("Error al aplicar promoción");
						txtAreaPromociones.setText("Error: " + ex.getMessage());
					}
				}
			}
		});
		btnAplicarPromocion.setFont(new Font("Mongolian Baiti", Font.BOLD, 13));
		btnAplicarPromocion.setBounds(500, 310, 220, 35);
		btnAplicarPromocion.setFocusPainted(false);
		panelPromociones.add(btnAplicarPromocion);

		// FILA 2 DE BOTONES
		// Botón Editar Promoción
		JButton btnEditarPromocion = new JButton("EDITAR PROMOCIÓN");
		btnEditarPromocion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblMensajePromo.setText("");
				if (encargado != null) {
					txtAreaPromociones.setText("Editando promoción...\n\n");
					try {
						// Llamar al método estático del BLL
						bll.Encargado.editarPromocion(encargado.getId_hotel());
						
						lblMensajePromo.setForeground(new Color(0, 150, 0));
						lblMensajePromo.setText("Operación completada. Use 'Ver Promociones' para verificar.");
					} catch (Exception ex) {
						lblMensajePromo.setForeground(Color.RED);
						lblMensajePromo.setText("Error al editar promoción");
						txtAreaPromociones.setText("Error: " + ex.getMessage());
					}
				}
			}
		});
		btnEditarPromocion.setFont(new Font("Mongolian Baiti", Font.BOLD, 13));
		btnEditarPromocion.setBounds(30, 355, 220, 35);
		btnEditarPromocion.setFocusPainted(false);
		panelPromociones.add(btnEditarPromocion);

		// Botón Eliminar Promoción
		JButton btnEliminarPromocion = new JButton("ELIMINAR PROMOCIÓN");
		btnEliminarPromocion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblMensajePromo.setText("");
				if (encargado != null) {
					txtAreaPromociones.setText("Eliminando promoción...\n\n");
					try {
						// Llamar al método estático del BLL
						bll.Encargado.eliminarPromocion(encargado.getId_hotel());
						
						lblMensajePromo.setForeground(new Color(0, 150, 0));
						lblMensajePromo.setText("Operación completada. Use 'Ver Promociones' para verificar.");
					} catch (Exception ex) {
						lblMensajePromo.setForeground(Color.RED);
						lblMensajePromo.setText("Error al eliminar promoción");
						txtAreaPromociones.setText("Error: " + ex.getMessage());
					}
				}
			}
		});
		btnEliminarPromocion.setFont(new Font("Mongolian Baiti", Font.BOLD, 13));
		btnEliminarPromocion.setBounds(265, 355, 220, 35);
		btnEliminarPromocion.setFocusPainted(false);
		panelPromociones.add(btnEliminarPromocion);

		// Botón Ver Paquetes con Promociones
		JButton btnVerPaquetesPromo = new JButton("VER PAQUETES + PROMOS");
		btnVerPaquetesPromo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblMensajePromo.setText("");
				if (encargado != null) {
					List<bll.Paquete> paquetes = DtoEncargado.verPaquetesConPromociones(encargado.getId_hotel());
					
					if (paquetes.isEmpty()) {
						txtAreaPromociones.setText("No hay paquetes disponibles en su hotel.");
						lblMensajePromo.setForeground(new Color(200, 100, 0));
						lblMensajePromo.setText("No hay paquetes disponibles");
					} else {
						String texto = "PAQUETES CON INFORMACIÓN DE PROMOCIONES\n";
						
						for (bll.Paquete p : paquetes) {
							texto += "ID Paquete: " + p.getId() + "\n";
							texto += "Fecha: " + p.getInicioDate() + " al " + p.getFinDate() + "\n";
							
							if (p.getHabitacion() != null) {
								texto += "Habitación: Nro " + p.getHabitacion().getNumero() + "\n";
							}
							
							if (p.getActividad() != null) {
								texto += "Actividad: " + p.getActividad().getNombre() + "\n";
							}
							
							if (p.getPromocion() != null) {
								texto += "\n★ PROMOCIÓN APLICADA: " + p.getPromocion().getNombre() + "\n";
								texto += "  Descuento: " + p.getPromocion().getPorcentajeDescuento() + "%\n";
								texto += "  Precio original: $" + String.format("%.2f", p.getPrecioOriginal()) + "\n";
								texto += "  Precio con descuento: $" + String.format("%.2f", p.getPrecio()) + "\n";
								
								if (p.getPromocion().estaVigente()) {
									texto += "  Estado: VIGENTE\n";
								} else {
									texto += "  Estado: NO VIGENTE\n";
								}
							} else {
								texto += "\nSin promoción aplicada\n";
								texto += "Precio: $" + String.format("%.2f", p.getPrecio()) + "\n";
							}
							
							texto += "\n";
						}
						
						txtAreaPromociones.setText(texto);
						txtAreaPromociones.setCaretPosition(0);
						lblMensajePromo.setForeground(new Color(0, 100, 200));
						lblMensajePromo.setText("Mostrando " + paquetes.size() + " paquete(s)");
					}
				}
			}
		});
		btnVerPaquetesPromo.setFont(new Font("Mongolian Baiti", Font.BOLD, 13));
		btnVerPaquetesPromo.setBounds(500, 355, 220, 35);
		btnVerPaquetesPromo.setFocusPainted(false);
		panelPromociones.add(btnVerPaquetesPromo);

		Contenidos.addTab("Promociones", null, panelPromociones, null);

		// BOTÓN CERRAR SESIÓN
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

		// PERFIL
		JLabel Perfil = new JLabel("");
		Perfil.setBackground(new Color(255, 255, 255));
		java.net.URL imageUrl = getClass().getResource("/img/encargado.png");
		if (imageUrl != null) {
			ImageIcon originalIcon = new ImageIcon(imageUrl);
			Image originalImage = originalIcon.getImage();
			int newWidth = 120;
			int newHeight = 120;
			Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
			originalIcon.setImage(scaledImage);
			Perfil.setIcon(originalIcon);
		}
		Perfil.setBounds(10, 11, 148, 153);
		contentPane.add(Perfil);

		lblbienvenido = new JLabel("Bienvenido");
		lblbienvenido.setFont(new Font("Mongolian Baiti", Font.PLAIN, 18));
		lblbienvenido.setBounds(168, 65, 300, 25);
		contentPane.add(lblbienvenido);

		lblHotel = new JLabel("Administración Hotel");
		lblHotel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 20));
		lblHotel.setBounds(168, 29, 366, 25);
		contentPane.add(lblHotel);
	}

	private JLabel lblbienvenido;
	private JLabel lblHotel;

	private void cargarReservas() {
		if (encargado == null) {
			return;
		}

		modelPendientes.setRowCount(0);
		modelFinalizadas.setRowCount(0);

		List<Reserva> reservas = DtoEncargado.verReservas(encargado.getId_hotel());

		if (reservas.isEmpty()) {
			return;
		}

		// Separar por estado
		for (Reserva r : reservas) {
			String nombreCliente = r.getCliente().getNombre() + " " + r.getCliente().getApellido();
			int dni = r.getCliente().getDni();
			int numeroHab = r.getPaquete().getHabitacion().getNumero();

			if ("pendiente".equalsIgnoreCase(r.getEstado()) || "activa".equalsIgnoreCase(r.getEstado())) {
				Object[] fila = { r.getId(), nombreCliente, dni, numeroHab,
						String.format("$%.2f", r.getPaquete().getPrecio()) };
				modelPendientes.addRow(fila);
			} else if ("finalizada".equalsIgnoreCase(r.getEstado())) {
				Object[] fila = { r.getId(), nombreCliente, dni, numeroHab,
						String.format("$%.2f", r.getMonto_final()) };
				modelFinalizadas.addRow(fila);
			}
		}
	}

	public void recargarReservas() {
		cargarReservas();
	}

	private void cargarHabitaciones() {
		if (encargado == null) {
			return;
		}

		modelHabitaciones.setRowCount(0);

		List<bll.Habitacion> habitaciones = DtoEncargado.verHabitaciones(encargado.getId_hotel());

		if (habitaciones.isEmpty()) {
			return;
		}

		for (bll.Habitacion h : habitaciones) {
			Object[] fila = { h.getId(), h.getNumero(), h.getTipo(), h.getCant_camas(),
					String.format("$%.2f", h.getPrecio_noche()), h.getEstado() };
			modelHabitaciones.addRow(fila);
		}
	}

	public void recargarHabitaciones() {
		cargarHabitaciones();
	}

	private void cargarReservasCheckin(javax.swing.JComboBox<String> combo) {
		if (encargado == null || combo == null) {
			return;
		}

		combo.removeAllItems();

		List<Reserva> reservas = DtoEncargado.verReservas(encargado.getId_hotel());

		boolean hayReservas = false;
		for (Reserva r : reservas) {
			if ("pendiente".equalsIgnoreCase(r.getEstado())) {
				String item = "ID: " + r.getId() + " | " + r.getCliente().getNombre() + " "
						+ r.getCliente().getApellido() + " | DNI: " + r.getCliente().getDni() + " | Hab: "
						+ r.getPaquete().getHabitacion().getNumero() + " | Estado: " + r.getEstado();
				combo.addItem(item);
				hayReservas = true;
			}
		}

		if (!hayReservas) {
			combo.addItem("No hay reservas pendientes");
		}
	}

	private void cargarReservasCheckout(javax.swing.JComboBox<String> combo) {
		if (encargado == null || combo == null) {
			return;
		}

		combo.removeAllItems();

		List<Reserva> reservas = DtoEncargado.verReservas(encargado.getId_hotel());

		boolean hayReservas = false;
		for (Reserva r : reservas) {
			if ("activa".equalsIgnoreCase(r.getEstado())) {
				String item = "ID: " + r.getId() + " | " + r.getCliente().getNombre() + " "
						+ r.getCliente().getApellido() + " | DNI: " + r.getCliente().getDni() + " | Hab: "
						+ r.getPaquete().getHabitacion().getNumero();
				combo.addItem(item);
				hayReservas = true;
			}
		}

		if (!hayReservas) {
			combo.addItem("No hay reservas activas");
		}
	}
}