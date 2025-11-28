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
import javax.swing.JComboBox;
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
						c.setBackground(new Color(200, 255, 200)); 
					} else if ("ocupada".equalsIgnoreCase(estado)) {
						c.setBackground(new Color(255, 200, 200)); 
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

		JButton btnRealizarCheckin = new JButton("Realizar Check-in");
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

		JButton btnRealizarCheckout = new JButton("Realizar check-out");
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
		JPanel panelPromociones = new JPanel();
		panelPromociones.setLayout(null);
		panelPromociones.setBackground(Color.WHITE);

		//sub-pestañas 
		JTabbedPane tabsPromociones = new JTabbedPane(JTabbedPane.LEFT);
		tabsPromociones.setBounds(10, 10, 730, 385);
		tabsPromociones.setFont(new Font("Mongolian Baiti", Font.PLAIN, 13));
		panelPromociones.add(tabsPromociones);

		//VER PROMOCIONES
		JPanel subPanelVer = new JPanel();
		subPanelVer.setLayout(null);
		subPanelVer.setBackground(Color.WHITE);

		JLabel lblTituloVer = new JLabel("PROMOCIONES ACTIVAS");
		lblTituloVer.setBounds(20, 10, 300, 30);
		lblTituloVer.setFont(new Font("Mongolian Baiti", Font.BOLD, 18));
		subPanelVer.add(lblTituloVer);

		//Tabla de promociones
		String[] columnasPromo = {"ID", "Nombre", "Descuento %", "Fecha Inicio", "Fecha Fin", "Estado"};
		DefaultTableModel modelPromo = new DefaultTableModel(columnasPromo, 0) {
		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		JTable tablePromociones = new JTable(modelPromo);
		tablePromociones.setFont(new Font("Mongolian Baiti", Font.PLAIN, 12));
		tablePromociones.getTableHeader().setFont(new Font("Mongolian Baiti", Font.BOLD, 12));
		tablePromociones.setRowHeight(25);

		tablePromociones.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
		    public java.awt.Component getTableCellRendererComponent(JTable table, Object value, 
		            boolean isSelected, boolean hasFocus, int row, int column) {
		        java.awt.Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		        
		        if (!isSelected && column == 5) {
		            String estado = (String) value;
		            if ("VIGENTE".equalsIgnoreCase(estado)) {
		                c.setBackground(new Color(200, 255, 200));
		                c.setForeground(new Color(0, 100, 0));
		            } else if ("Expirada".equalsIgnoreCase(estado)) {
		                c.setBackground(new Color(255, 200, 200));
		                c.setForeground(new Color(150, 0, 0));
		            } else {
		                c.setBackground(new Color(255, 255, 200));
		                c.setForeground(new Color(100, 100, 0));
		            }
		        } else if (!isSelected) {
		            c.setBackground(Color.WHITE);
		            c.setForeground(Color.BLACK);
		        }
		        
		        return c;
		    }
		});

		JScrollPane scrollPromo = new JScrollPane(tablePromociones);
		scrollPromo.setBounds(20, 50, 540, 250);
		subPanelVer.add(scrollPromo);

		JButton btnRefrescarPromo = new JButton("Actualizar lista");
		btnRefrescarPromo.setBounds(20, 310, 200, 35);
		btnRefrescarPromo.setFont(new Font("Mongolian Baiti", Font.BOLD, 14));
		btnRefrescarPromo.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        modelPromo.setRowCount(0);
		        if (encargado != null) {
		            List<bll.Promocion> promociones = DtoEncargado.verPromocionesDelHotel(encargado.getId_hotel());
		            
		            for (bll.Promocion p : promociones) {
		                String estado;
		                if (p.estaVigente()) {
		                    estado = "VIGENTE";
		                } else if (p.getEstado().equals("activa")) {
		                    LocalDate hoy = LocalDate.now();
		                    estado = hoy.isBefore(p.getFechaInicio()) ? "Pendiente" : "Expirada";
		                } else {
		                    estado = "Inactiva";
		                }
		                
		                Object[] fila = {p.getId(), p.getNombre(), p.getPorcentajeDescuento() + "%", 
		                               p.getFechaInicio(), p.getFechaFin(), estado};
		                modelPromo.addRow(fila);
		            }
		        }
		    }
		});
		subPanelVer.add(btnRefrescarPromo);

		JLabel lblInfoVer = new JLabel("Total: 0 promociones");
		lblInfoVer.setBounds(240, 315, 300, 25);
		lblInfoVer.setFont(new Font("Mongolian Baiti", Font.PLAIN, 13));
		subPanelVer.add(lblInfoVer);

		// Actualizar contador
		btnRefrescarPromo.addActionListener(e -> {
		    lblInfoVer.setText("Total: " + modelPromo.getRowCount() + " promociones");
		});

		tabsPromociones.addTab("Ver Promociones", subPanelVer);

		//CREAR PROMOCIÓN 
		JPanel subPanelCrear = new JPanel();
		subPanelCrear.setLayout(null);
		subPanelCrear.setBackground(Color.WHITE);

		JLabel lblTituloCrear = new JLabel("CREAR NUEVA PROMOCIÓN");
		lblTituloCrear.setBounds(20, 10, 350, 30);
		lblTituloCrear.setFont(new Font("Mongolian Baiti", Font.BOLD, 18));
		subPanelCrear.add(lblTituloCrear);

		// Campos del formulario
		JLabel lblNombrePromo = new JLabel("Nombre de la promoción:");
		lblNombrePromo.setBounds(50, 60, 200, 25);
		lblNombrePromo.setFont(new Font("Mongolian Baiti", Font.BOLD, 14));
		subPanelCrear.add(lblNombrePromo);

		javax.swing.JTextField txtNombrePromo = new javax.swing.JTextField();
		txtNombrePromo.setBounds(50, 90, 450, 30);
		txtNombrePromo.setFont(new Font("Mongolian Baiti", Font.PLAIN, 13));
		subPanelCrear.add(txtNombrePromo);

		JLabel lblDescripcionPromo = new JLabel("Descripción:");
		lblDescripcionPromo.setBounds(50, 130, 200, 25);
		lblDescripcionPromo.setFont(new Font("Mongolian Baiti", Font.BOLD, 14));
		subPanelCrear.add(lblDescripcionPromo);

		javax.swing.JTextArea txtDescripcionPromo = new javax.swing.JTextArea();
		txtDescripcionPromo.setFont(new Font("Mongolian Baiti", Font.PLAIN, 13));
		txtDescripcionPromo.setLineWrap(true);
		txtDescripcionPromo.setWrapStyleWord(true);
		JScrollPane scrollDesc = new JScrollPane(txtDescripcionPromo);
		scrollDesc.setBounds(50, 160, 450, 60);
		subPanelCrear.add(scrollDesc);

		JLabel lblDescuentoPromo = new JLabel("Porcentaje de descuento (%):");
		lblDescuentoPromo.setBounds(50, 230, 220, 25);
		lblDescuentoPromo.setFont(new Font("Mongolian Baiti", Font.BOLD, 14));
		subPanelCrear.add(lblDescuentoPromo);

		javax.swing.JSpinner spinnerDescuento = new javax.swing.JSpinner(
		    new javax.swing.SpinnerNumberModel(10, 1, 100, 1));
		spinnerDescuento.setBounds(280, 230, 80, 30);
		spinnerDescuento.setFont(new Font("Mongolian Baiti", Font.PLAIN, 13));
		subPanelCrear.add(spinnerDescuento);

		JLabel lblFechaInicio = new JLabel("Fecha de inicio:");
		lblFechaInicio.setBounds(50, 270, 150, 25);
		lblFechaInicio.setFont(new Font("Mongolian Baiti", Font.BOLD, 14));
		subPanelCrear.add(lblFechaInicio);

		// DatePicker simple con JSpinner
		javax.swing.JSpinner spinnerFechaInicio = new javax.swing.JSpinner(
		    new javax.swing.SpinnerDateModel());
		javax.swing.JSpinner.DateEditor editorInicio = new javax.swing.JSpinner.DateEditor(
		    spinnerFechaInicio, "dd/MM/yyyy");
		spinnerFechaInicio.setEditor(editorInicio);
		spinnerFechaInicio.setBounds(200, 270, 150, 30);
		spinnerFechaInicio.setFont(new Font("Mongolian Baiti", Font.PLAIN, 13));
		subPanelCrear.add(spinnerFechaInicio);

		JLabel lblFechaFin = new JLabel("Fecha de fin:");
		lblFechaFin.setBounds(50, 310, 150, 25);
		lblFechaFin.setFont(new Font("Mongolian Baiti", Font.BOLD, 14));
		subPanelCrear.add(lblFechaFin);

		javax.swing.JSpinner spinnerFechaFin = new javax.swing.JSpinner(
		    new javax.swing.SpinnerDateModel());
		javax.swing.JSpinner.DateEditor editorFin = new javax.swing.JSpinner.DateEditor(
		    spinnerFechaFin, "dd/MM/yyyy");
		spinnerFechaFin.setEditor(editorFin);
		spinnerFechaFin.setBounds(200, 310, 150, 30);
		spinnerFechaFin.setFont(new Font("Mongolian Baiti", Font.PLAIN, 13));
		subPanelCrear.add(spinnerFechaFin);

		// Mensaje de resultado
		JLabel lblMensajeCrear = new JLabel("");
		lblMensajeCrear.setBounds(50, 350, 450, 25);
		lblMensajeCrear.setFont(new Font("Mongolian Baiti", Font.BOLD, 12));
		lblMensajeCrear.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		subPanelCrear.add(lblMensajeCrear);

		// Botones
		JButton btnGuardarPromo = new JButton("CREAR PROMOCIÓN");
		btnGuardarPromo.setBounds(370, 270, 180, 40);
		btnGuardarPromo.setFont(new Font("Mongolian Baiti", Font.BOLD, 14));
		btnGuardarPromo.setBackground(new Color(255, 255, 255));
		btnGuardarPromo.setForeground(Color.WHITE);
		btnGuardarPromo.setFocusPainted(false);
		btnGuardarPromo.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        lblMensajeCrear.setText("");
		        
		        String nombre = txtNombrePromo.getText().trim();
		        String descripcion = txtDescripcionPromo.getText().trim();
		        int descuento = (Integer) spinnerDescuento.getValue();
		        
		        if (nombre.isEmpty()) {
		            lblMensajeCrear.setForeground(Color.RED);
		            lblMensajeCrear.setText("El nombre es obligatorio");
		            return;
		        }
		        
		        try {
		            java.util.Date dateInicio = (java.util.Date) spinnerFechaInicio.getValue();
		            java.util.Date dateFin = (java.util.Date) spinnerFechaFin.getValue();
		            
		            LocalDate fechaInicio = dateInicio.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
		            LocalDate fechaFin = dateFin.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
		            
		            if (fechaFin.isBefore(fechaInicio)) {
		                lblMensajeCrear.setForeground(Color.RED);
		                lblMensajeCrear.setText("La fecha de fin debe ser posterior a la fecha de inicio");
		                return;
		            }
		            
		            // Crear la promoción usando el método BLL modificado
		            boolean exito = bll.Encargado.crearPromocion(
		                encargado.getId_hotel(), nombre, descripcion, descuento, fechaInicio, fechaFin);
		            
		            if (exito) {
		                lblMensajeCrear.setForeground(new Color(0, 150, 0));
		                lblMensajeCrear.setText("Promoción creada exitosamente");
		                
		                // Limpiar formulario
		                txtNombrePromo.setText("");
		                txtDescripcionPromo.setText("");
		                spinnerDescuento.setValue(10);
		                
		                // Actualizar tabla en la primera pestaña
		                btnRefrescarPromo.doClick();
		            } else {
		                lblMensajeCrear.setForeground(Color.RED);
		                lblMensajeCrear.setText("Error al crear la promoción");
		            }
		            
		        } catch (Exception ex) {
		            lblMensajeCrear.setForeground(Color.RED);
		            lblMensajeCrear.setText("Error: " + ex.getMessage());
		        }
		    }
		});
		subPanelCrear.add(btnGuardarPromo);

		JButton btnLimpiarForm = new JButton("Limpiar");
		btnLimpiarForm.setBounds(370, 315, 180, 30);
		btnLimpiarForm.setFont(new Font("Mongolian Baiti", Font.PLAIN, 13));
		btnLimpiarForm.addActionListener(e -> {
		    txtNombrePromo.setText("");
		    txtDescripcionPromo.setText("");
		    spinnerDescuento.setValue(10);
		    lblMensajeCrear.setText("");
		});
		subPanelCrear.add(btnLimpiarForm);

		tabsPromociones.addTab("Crear Promoción", subPanelCrear);

		//EDITAR PROMOCIÓN
		JPanel subPanelEditar = new JPanel();
		subPanelEditar.setLayout(null);
		subPanelEditar.setBackground(Color.WHITE);

		JLabel lblTituloEditar = new JLabel("EDITAR PROMOCIÓN");
		lblTituloEditar.setBounds(20, 10, 300, 30);
		lblTituloEditar.setFont(new Font("Mongolian Baiti", Font.BOLD, 18));
		subPanelEditar.add(lblTituloEditar);

		JLabel lblSeleccionarPromo = new JLabel("Seleccionar promoción:");
		lblSeleccionarPromo.setBounds(50, 50, 200, 25);
		lblSeleccionarPromo.setFont(new Font("Mongolian Baiti", Font.BOLD, 14));
		subPanelEditar.add(lblSeleccionarPromo);

		javax.swing.JComboBox<String> comboPromocionesEditar = new javax.swing.JComboBox<>();
		comboPromocionesEditar.setBounds(50, 80, 450, 30);
		comboPromocionesEditar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 13));
		subPanelEditar.add(comboPromocionesEditar);

		//Campos de edición 
		JLabel lblEditNombre = new JLabel("Nombre:");
		lblEditNombre.setBounds(50, 125, 200, 25);
		lblEditNombre.setFont(new Font("Mongolian Baiti", Font.BOLD, 14));
		subPanelEditar.add(lblEditNombre);

		javax.swing.JTextField txtEditNombre = new javax.swing.JTextField();
		txtEditNombre.setBounds(50, 155, 450, 30);
		txtEditNombre.setFont(new Font("Mongolian Baiti", Font.PLAIN, 13));
		txtEditNombre.setEnabled(false);
		subPanelEditar.add(txtEditNombre);

		JLabel lblEditDescripcion = new JLabel("Descripción:");
		lblEditDescripcion.setBounds(50, 195, 200, 25);
		lblEditDescripcion.setFont(new Font("Mongolian Baiti", Font.BOLD, 14));
		subPanelEditar.add(lblEditDescripcion);

		javax.swing.JTextArea txtEditDescripcion = new javax.swing.JTextArea();
		txtEditDescripcion.setFont(new Font("Mongolian Baiti", Font.PLAIN, 13));
		txtEditDescripcion.setLineWrap(true);
		txtEditDescripcion.setWrapStyleWord(true);
		txtEditDescripcion.setEnabled(false);
		JScrollPane scrollEditDesc = new JScrollPane(txtEditDescripcion);
		scrollEditDesc.setBounds(50, 225, 450, 50);
		subPanelEditar.add(scrollEditDesc);

		JLabel lblEditDescuento = new JLabel("Descuento (%):");
		lblEditDescuento.setBounds(50, 285, 150, 25);
		lblEditDescuento.setFont(new Font("Mongolian Baiti", Font.BOLD, 14));
		subPanelEditar.add(lblEditDescuento);

		javax.swing.JSpinner spinnerEditDescuento = new javax.swing.JSpinner(
		    new javax.swing.SpinnerNumberModel(10, 1, 100, 1));
		spinnerEditDescuento.setBounds(200, 285, 80, 30);
		spinnerEditDescuento.setFont(new Font("Mongolian Baiti", Font.PLAIN, 13));
		spinnerEditDescuento.setEnabled(false);
		subPanelEditar.add(spinnerEditDescuento);

		// Mensaje
		JLabel lblMensajeEditar = new JLabel("");
		lblMensajeEditar.setBounds(50, 325, 450, 25);
		lblMensajeEditar.setFont(new Font("Mongolian Baiti", Font.BOLD, 12));
		lblMensajeEditar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		subPanelEditar.add(lblMensajeEditar);

		// Botón cargar
		JButton btnCargarPromo = new JButton("Cargar");
		btnCargarPromo.setBounds(510, 80, 89, 30);
		btnCargarPromo.setFont(new Font("Mongolian Baiti", Font.BOLD, 13));
		btnCargarPromo.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        lblMensajeEditar.setText("");
		        
		        if (comboPromocionesEditar.getSelectedItem() == null || 
		            comboPromocionesEditar.getSelectedItem().toString().equals("No hay promociones")) {
		            return;
		        }
		        
		        String seleccion = comboPromocionesEditar.getSelectedItem().toString();
		        int idPromo = Integer.parseInt(seleccion.split(" - ")[0].replace("ID: ", ""));
		        
		        // Obtener la promoción
		        List<bll.Promocion> promociones = DtoEncargado.verPromocionesDelHotel(encargado.getId_hotel());
		        bll.Promocion promoSeleccionada = null;
		        
		        for (bll.Promocion p : promociones) {
		            if (p.getId() == idPromo) {
		                promoSeleccionada = p;
		                break;
		            }
		        }
		        
		        if (promoSeleccionada != null) {
		            txtEditNombre.setText(promoSeleccionada.getNombre());
		            txtEditDescripcion.setText(promoSeleccionada.getDescripcion());
		            spinnerEditDescuento.setValue((int)promoSeleccionada.getPorcentajeDescuento());
		            
		            //Habilita
		            txtEditNombre.setEnabled(true);
		            txtEditDescripcion.setEnabled(true);
		            spinnerEditDescuento.setEnabled(true);
		        }
		    }
		});
		subPanelEditar.add(btnCargarPromo);

		// Botón guardar cambios
		JButton btnGuardarEdicion = new JButton("Guardar cambios");
		btnGuardarEdicion.setBounds(300, 285, 200, 40);
		btnGuardarEdicion.setFont(new Font("Mongolian Baiti", Font.BOLD, 14));
		btnGuardarEdicion.setBackground(new Color(210, 225, 240));
		btnGuardarEdicion.setForeground(new Color(30, 30, 30));
		btnGuardarEdicion.setFocusPainted(false);
		btnGuardarEdicion.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        lblMensajeEditar.setText("");
		        
		        if (comboPromocionesEditar.getSelectedItem() == null || 
		            comboPromocionesEditar.getSelectedItem().toString().equals("No hay promociones")) {
		            lblMensajeEditar.setForeground(Color.RED);
		            lblMensajeEditar.setText("Seleccione una promoción primero");
		            return;
		        }
		        
		        String nombre = txtEditNombre.getText().trim();
		        String descripcion = txtEditDescripcion.getText().trim();
		        int descuento = (Integer) spinnerEditDescuento.getValue();
		        
		        if (nombre.isEmpty()) {
		            lblMensajeEditar.setForeground(Color.RED);
		            lblMensajeEditar.setText("El nombre no puede estar vacío");
		            return;
		        }
		        
		        String seleccion = comboPromocionesEditar.getSelectedItem().toString();
		        int idPromo = Integer.parseInt(seleccion.split(" - ")[0].replace("ID: ", ""));
		        
		        boolean exitoNombre = DtoEncargado.editarPromocion(idPromo, "nombre", nombre, encargado.getId_hotel());
		        boolean exitoDesc = DtoEncargado.editarPromocion(idPromo, "descripcion", descripcion, encargado.getId_hotel());
		        boolean exitoPorc = DtoEncargado.editarPromocion(idPromo, "porcentaje", String.valueOf(descuento), encargado.getId_hotel());
		        
		        if (exitoNombre && exitoDesc && exitoPorc) {
		            lblMensajeEditar.setForeground(new Color(0, 150, 0));
		            lblMensajeEditar.setText("✓ Promoción actualizada exitosamente");
		            
		            //Actualizar combo y tabla
		            cargarComboPromociones(comboPromocionesEditar);
		            btnRefrescarPromo.doClick();
		        } else {
		            lblMensajeEditar.setForeground(Color.RED);
		            lblMensajeEditar.setText("Error al actualizar la promoción");
		        }
		    }
		});
		subPanelEditar.add(btnGuardarEdicion);

		//Cargar inicial
		cargarComboPromociones(comboPromocionesEditar);

		tabsPromociones.addTab("Editar Promoción", subPanelEditar);
		
		//APLICAR PROMOCIÓN 
		JPanel subPanelAplicar = new JPanel();
		subPanelAplicar.setLayout(null);
		subPanelAplicar.setBackground(Color.WHITE);

		JLabel lblTituloAplicar = new JLabel("APLICAR PROMOCIÓN A RESERVA ACTIVA/PENDIENTE");
		lblTituloAplicar.setBounds(20, 10, 500, 30);
		lblTituloAplicar.setFont(new Font("Mongolian Baiti", Font.BOLD, 18));
		subPanelAplicar.add(lblTituloAplicar);

		JLabel lblSelReserva = new JLabel("Seleccionar Reserva:");
		lblSelReserva.setBounds(50, 50, 350, 25);
		lblSelReserva.setFont(new Font("Mongolian Baiti", Font.BOLD, 14));
		subPanelAplicar.add(lblSelReserva);

		javax.swing.JComboBox<String> comboReservasAplicar = new javax.swing.JComboBox<>();
		comboReservasAplicar.setBounds(50, 80, 550, 30);
		comboReservasAplicar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 13));
		subPanelAplicar.add(comboReservasAplicar);

		JLabel lblSelPromo = new JLabel("Seleccionar Promoción VIGENTE:");
		lblSelPromo.setBounds(50, 130, 350, 25);
		lblSelPromo.setFont(new Font("Mongolian Baiti", Font.BOLD, 14));
		subPanelAplicar.add(lblSelPromo);

		javax.swing.JComboBox<String> comboPromoAplicar = new javax.swing.JComboBox<>();
		comboPromoAplicar.setBounds(50, 160, 550, 30);
		comboPromoAplicar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 13));
		subPanelAplicar.add(comboPromoAplicar);

		// Mensaje
		JLabel lblMensajeAplicar = new JLabel("");
		lblMensajeAplicar.setBounds(50, 210, 550, 25);
		lblMensajeAplicar.setFont(new Font("Mongolian Baiti", Font.BOLD, 12));
		lblMensajeAplicar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		subPanelAplicar.add(lblMensajeAplicar);

		JButton btnAplicarPromo = new JButton("Aplicar descuento");
		btnAplicarPromo.setEnabled(false);
		btnAplicarPromo.setBounds(200, 250, 250, 40);
		btnAplicarPromo.setFont(new Font("Mongolian Baiti", Font.BOLD, 14));
		btnAplicarPromo.setBackground(new Color(210, 225, 240));
		btnAplicarPromo.setForeground(new Color(30, 30, 30));
		btnAplicarPromo.setFocusPainted(false);
		btnAplicarPromo.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        lblMensajeAplicar.setText("");

		        if (comboReservasAplicar.getSelectedItem() == null || 
		            comboReservasAplicar.getSelectedItem().toString().contains("No hay reservas")) {
		            lblMensajeAplicar.setForeground(Color.RED);
		            lblMensajeAplicar.setText("Seleccione una reserva válida.");
		            return;
		        }

		        if (comboPromoAplicar.getSelectedItem() == null || 
		            comboPromoAplicar.getSelectedItem().toString().contains("No hay promociones")) {
		            lblMensajeAplicar.setForeground(Color.RED);
		            lblMensajeAplicar.setText("Seleccione una promoción vigente.");
		            return;
		        }

		        try {
		            
		            String selReserva = comboReservasAplicar.getSelectedItem().toString();
		            
		            int idReserva = Integer.parseInt(selReserva.split(" \\|")[0].replace("ID: ", ""));
		           
		            bll.Reserva reservaSeleccionada = null;
		            List<bll.Reserva> todasReservas = DtoEncargado.verReservas(encargado.getId_hotel());
		            for (bll.Reserva r : todasReservas) {
		                if (r.getId() == idReserva) {
		                    reservaSeleccionada = r;
		                    break;
		                }
		            }
		            
		            if (reservaSeleccionada == null || reservaSeleccionada.getPaquete() == null) {
		                lblMensajeAplicar.setForeground(Color.RED);
		                lblMensajeAplicar.setText("Error: No se pudo encontrar el paquete asociado a la reserva.");
		                return;
		            }
		            int idPaquete = reservaSeleccionada.getPaquete().getId();

		            String selPromo = comboPromoAplicar.getSelectedItem().toString();
		            int idPromocion = Integer.parseInt(selPromo.split(" - ")[0].replace("ID: ", ""));

		            boolean exito = bll.Encargado.aplicarPromocionAPaquete(
		                                encargado.getId_hotel(), 
		                                idPromocion, 
		                                idPaquete, 
		                                lblMensajeAplicar);

		            if (exito) {
		                
		                cargarReservas();
		                btnRefrescarPromo.doClick(); 
		               
		                cargarReservasParaAplicarPromo(comboReservasAplicar);
		            }
		            

		        } catch (NumberFormatException ex) {
		            lblMensajeAplicar.setForeground(Color.RED);
		            lblMensajeAplicar.setText("Error al leer el ID: Formato de selección incorrecto.");
		        } catch (Exception ex) {
		            lblMensajeAplicar.setForeground(Color.RED);
		            lblMensajeAplicar.setText("Error inesperado: " + ex.getMessage());
		        }
		    }
		});
		subPanelAplicar.add(btnAplicarPromo);

		//Cargar inicial
		cargarReservasParaAplicarPromo(comboReservasAplicar);
		cargarPromocionesVigentesEnCombo(comboPromoAplicar);

		tabsPromociones.addTab("Aplicar Promo", subPanelAplicar);
		
		
		
		//ELIMINAR PROMOCIÓN 
		JPanel subPanelEliminar = new JPanel();
		subPanelEliminar.setLayout(null);
		subPanelEliminar.setBackground(Color.WHITE);

		JLabel lblTituloEliminar = new JLabel("ELIMINAR PROMOCIÓN");
		lblTituloEliminar.setBounds(20, 10, 300, 30);
		lblTituloEliminar.setFont(new Font("Mongolian Baiti", Font.BOLD, 18));
		subPanelEliminar.add(lblTituloEliminar);

		JLabel lblAdvertencia = new JLabel("Esta acción no se puede deshacer");
		lblAdvertencia.setBounds(20, 45, 300, 25);
		lblAdvertencia.setFont(new Font("Mongolian Baiti", Font.BOLD, 13));
		subPanelEliminar.add(lblAdvertencia);

		JLabel lblSelEliminar = new JLabel("Seleccionar promoción a eliminar:");
		lblSelEliminar.setBounds(50, 90, 300, 25);
		lblSelEliminar.setFont(new Font("Mongolian Baiti", Font.BOLD, 14));
		subPanelEliminar.add(lblSelEliminar);

		javax.swing.JComboBox<String> comboEliminar = new javax.swing.JComboBox<>();
		comboEliminar.setBounds(50, 120, 450, 30);
		comboEliminar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 13));
		subPanelEliminar.add(comboEliminar);

		// Detalles de la promoción seleccionada
		javax.swing.JTextArea txtDetallesEliminar = new javax.swing.JTextArea();
		txtDetallesEliminar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 12));
		txtDetallesEliminar.setEditable(false);
		txtDetallesEliminar.setLineWrap(true);
		txtDetallesEliminar.setWrapStyleWord(true);
		txtDetallesEliminar.setText("Seleccione una promoción para ver sus detalles...");
		txtDetallesEliminar.setBackground(new Color(255, 250, 240));
		JScrollPane scrollDetalles = new JScrollPane(txtDetallesEliminar);
		scrollDetalles.setBounds(50, 165, 450, 120);
		subPanelEliminar.add(scrollDetalles);

		// Mensaje
		JLabel lblMensajeEliminar = new JLabel("");
		lblMensajeEliminar.setBounds(50, 295, 450, 25);
		lblMensajeEliminar.setFont(new Font("Mongolian Baiti", Font.BOLD, 12));
		lblMensajeEliminar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		subPanelEliminar.add(lblMensajeEliminar);

		// Botón eliminar
		JButton btnEliminar = new JButton("Eliminar promoción");
		btnEliminar.setBounds(50, 313, 450, 40);
		btnEliminar.setFont(new Font("Mongolian Baiti", Font.BOLD, 14));
		btnEliminar.setBackground(new Color(210, 225, 240));
		btnEliminar.setForeground(new Color(30, 30, 30));
		btnEliminar.setFocusPainted(false);
		btnEliminar.setEnabled(false);

		btnEliminar.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        lblMensajeEliminar.setText("");
		        
		        if (comboEliminar.getSelectedItem() == null || 
		            comboEliminar.getSelectedItem().toString().equals("No hay promociones")) {
		            return;
		        }
		        
		        //Confirmación de eliminación
		        int confirmar = javax.swing.JOptionPane.showConfirmDialog(
		            subPanelEliminar,
		            "¿Está seguro que desea eliminar esta promoción?\n" +
		            "Esta acción no se puede deshacer.",
		            "Confirmar eliminación",
		            javax.swing.JOptionPane.YES_NO_OPTION,
		            javax.swing.JOptionPane.WARNING_MESSAGE
		        );
		        
		        if (confirmar != javax.swing.JOptionPane.YES_OPTION) {
		            return;
		        }
		        
		        String seleccion = comboEliminar.getSelectedItem().toString();
		        int idPromo = Integer.parseInt(seleccion.split(" - ")[0].replace("ID: ", ""));
		        
		        boolean exito = bll.Encargado.eliminarPromocion(encargado.getId_hotel(), idPromo);
		        
		        if (exito) {
		            lblMensajeEliminar.setForeground(new Color(0, 150, 0));
		            lblMensajeEliminar.setText("Promoción eliminada exitosamente");
		            
		            
		            cargarComboEliminar(comboEliminar);
		            txtDetallesEliminar.setText("Promoción eliminada. Seleccione otra si desea continuar.");
		            btnEliminar.setEnabled(false);
		            
		            
		            btnRefrescarPromo.doClick();
		        } else {
		            lblMensajeEliminar.setText("Error al eliminar la promoción");
		        }
		    }
		});
		subPanelEliminar.add(btnEliminar);

		
		comboEliminar.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        lblMensajeEliminar.setText("");
		        
		        if (comboEliminar.getSelectedItem() == null || 
		            comboEliminar.getSelectedItem().toString().equals("No hay promociones")) {
		            txtDetallesEliminar.setText("No hay promociones disponibles para eliminar.");
		            btnEliminar.setEnabled(false);
		            return;
		        }
		        
		        String seleccion = comboEliminar.getSelectedItem().toString();
		        int idPromo = Integer.parseInt(seleccion.split(" - ")[0].replace("ID: ", ""));
		        
		        List<bll.Promocion> promociones = DtoEncargado.verPromocionesDelHotel(encargado.getId_hotel());
		        
		        for (bll.Promocion p : promociones) {
		            if (p.getId() == idPromo) {
		                String detalles = "DETALLES DE LA PROMOCIÓN\n\n";
		                detalles += "ID: " + p.getId() + "\n";
		                detalles += "Nombre: " + p.getNombre() + "\n";
		                detalles += "Descuento: " + p.getPorcentajeDescuento() + "%\n";
		                detalles += "Vigencia: " + p.getFechaInicio() + " al " + p.getFechaFin() + "\n";
		                detalles += "Estado: " + (p.estaVigente() ? "VIGENTE" : "No vigente") + "\n\n";
		                
		                if (p.getDescripcion() != null && !p.getDescripcion().isEmpty()) {
		                    detalles += "Descripción:\n" + p.getDescripcion();
		                }
		                
		                txtDetallesEliminar.setText(detalles);
		                btnEliminar.setEnabled(true);
		                break;
		            }
		        }
		    }
		});

		// Cargar inicial
		cargarComboEliminar(comboEliminar);

		tabsPromociones.addTab("Eliminar", subPanelEliminar);

		//VER PAQUETES CON PROMOCIONES 
		JPanel subPanelPaquetes = new JPanel();
		subPanelPaquetes.setLayout(null);
		subPanelPaquetes.setBackground(Color.WHITE);

		JLabel lblTituloPaquetes = new JLabel("PAQUETES CON PROMOCIONES");
		lblTituloPaquetes.setBounds(20, 10, 350, 30);
		lblTituloPaquetes.setFont(new Font("Mongolian Baiti", Font.BOLD, 18));
		subPanelPaquetes.add(lblTituloPaquetes);

		String[] columnasPaquetes = { "ID Paquete", "Habitación", "Fecha", "Precio Original", "Promoción", "Precio Final" };
		DefaultTableModel modelPaquetes = new DefaultTableModel(columnasPaquetes, 0) {
		    private static final long serialVersionUID = 1L;
		    
		    @Override
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};

		JTable tablePaquetes = new JTable(modelPaquetes);
		tablePaquetes.setFont(new Font("Mongolian Baiti", Font.PLAIN, 11));
		tablePaquetes.getTableHeader().setFont(new Font("Mongolian Baiti", Font.BOLD, 11));
		tablePaquetes.setRowHeight(25);

		tablePaquetes.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
		    private static final long serialVersionUID = 1L;
		    
		    @Override
		    public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
		            boolean hasFocus, int row, int column) {
		        java.awt.Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		        
		        if (!isSelected) {
		            String promocion = (String) table.getValueAt(row, 4);
		            if (promocion != null && !promocion.equals("-")) {
		                c.setBackground(new Color(255, 250, 200));
		            } else {
		                c.setBackground(Color.WHITE);
		            }
		        }
		        
		        return c;
		    }
		});

		JScrollPane scrollPaquetes = new JScrollPane(tablePaquetes);
		scrollPaquetes.setBounds(20, 50, 540, 280);
		subPanelPaquetes.add(scrollPaquetes);

		JLabel lblInfoPaquetes = new JLabel("Total: 0 paquetes");
		lblInfoPaquetes.setBounds(220, 345, 300, 25);
		lblInfoPaquetes.setFont(new Font("Mongolian Baiti", Font.PLAIN, 13));
		subPanelPaquetes.add(lblInfoPaquetes);

		JButton btnRefrescarPaquetes = new JButton("Refresh");
		btnRefrescarPaquetes.setBounds(20, 340, 180, 35);
		btnRefrescarPaquetes.setFont(new Font("Mongolian Baiti", Font.BOLD, 13));
		btnRefrescarPaquetes.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        modelPaquetes.setRowCount(0);
		        if (encargado != null) {
		            List<bll.Paquete> paquetes = DtoEncargado.verPaquetesConPromociones(encargado.getId_hotel());
		            
		            for (bll.Paquete p : paquetes) {
		                String habitacion = p.getHabitacion() != null ? "Nro " + p.getHabitacion().getNumero() : "N/A";
		                String fecha = p.getInicioDate() + " - " + p.getFinDate();
		                
		                String promocion = "-";
		                double precioOriginal = p.getPrecioOriginal();
		                double precioFinal = p.getPrecio();
		                
		                if (p.getPromocion() != null) {
		                    promocion = p.getPromocion().getNombre() + " (" + p.getPromocion().getPorcentajeDescuento() + "%)";
		                }
		                
		                Object[] fila = { 
		                    p.getId(), 
		                    habitacion, 
		                    fecha, 
		                    "$" + String.format("%.2f", precioOriginal),
		                    promocion, 
		                    "$" + String.format("%.2f", precioFinal) 
		                };
		                modelPaquetes.addRow(fila);
		            }
		            lblInfoPaquetes.setText("Total: " + modelPaquetes.getRowCount() + " paquetes");
		        }
		    }
		});
		subPanelPaquetes.add(btnRefrescarPaquetes);

		tabsPromociones.addTab("Paquetes", subPanelPaquetes);
		
		Contenidos.addTab("Promociones", null, panelPromociones, null);

		
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

	private void cargarPromocionesEnCombo(JComboBox<String> combo) {
	    if (encargado == null || combo == null) {
	        return;
	    }

	    //Limpia elementos existentes
	    combo.removeAllItems();

	    //promociones del hotel
	    List<bll.Promocion> promociones = DtoEncargado.verPromocionesDelHotel(encargado.getId_hotel());

	    if (promociones == null || promociones.isEmpty()) {
	        combo.addItem("No hay promociones");
	        return;
	    }

	    for (bll.Promocion p : promociones) {
	    	String item = String.format("ID: %d - %s (%d%%)", 
                    p.getId(), 
                    p.getNombre(), 
                    (int) p.getPorcentajeDescuento());
	        combo.addItem(item);
	    }
	}
	
	private void cargarComboPromociones(JComboBox<String> comboPromocionesEditar) {
		cargarPromocionesEnCombo(comboPromocionesEditar);
	}

	private void cargarComboEliminar(JComboBox<String> comboEliminar) {
		cargarPromocionesEnCombo(comboEliminar);
		if (comboEliminar.getItemCount() > 0 && 
		        !comboEliminar.getItemAt(0).equals("No hay promociones")) {
		         comboEliminar.setSelectedIndex(0); 
		    }
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
	
	private void cargarReservasParaAplicarPromo(javax.swing.JComboBox<String> combo) {
	    if (encargado == null || combo == null) {
	        return;
	    }

	    combo.removeAllItems();

	    
	    List<bll.Reserva> reservas = DtoEncargado.verReservas(encargado.getId_hotel());

	    boolean hayReservas = false;
	    for (bll.Reserva r : reservas) {
	        
	        if ("pendiente".equalsIgnoreCase(r.getEstado()) || "activa".equalsIgnoreCase(r.getEstado())) {
	            
	            String promoActual = (r.getPaquete().getPromocion() != null) ? 
	                                 " (PROMO: " + r.getPaquete().getPromocion().getNombre() + ")" : "";
	            
	            String item = "ID: " + r.getId() + " | Cliente: " + r.getCliente().getNombre() + " "
	                    + r.getCliente().getApellido() + " | Hab: "
	                    + r.getPaquete().getHabitacion().getNumero() + " | Estado: " + r.getEstado() + promoActual;
	            combo.addItem(item);
	            hayReservas = true;
	        }
	    }

	    if (!hayReservas) {
	        combo.addItem("No hay reservas Pendientes/Activas");
	    }
	}

	private void cargarPromocionesVigentesEnCombo(JComboBox<String> combo) {
	    if (encargado == null || combo == null) {
	        return;
	    }

	    combo.removeAllItems();

	    List<bll.Promocion> promociones = DtoEncargado.verPromocionesDelHotel(encargado.getId_hotel());

	    boolean hayVigentes = false;
	    if (promociones != null) {
	        for (bll.Promocion p : promociones) {
	            // Solo agrega si está VIGENTE
	            if (p.estaVigente()) {
	                String item = String.format("ID: %d - %s (%d%%)", 
	                                            p.getId(), 
	                                            p.getNombre(), 
	                                            (int) p.getPorcentajeDescuento());
	                combo.addItem(item);
	                hayVigentes = true;
	            }
	        }
	    }
	    
	    if (!hayVigentes) {
	        combo.addItem("No hay promociones vigentes");
	    }
	}
}