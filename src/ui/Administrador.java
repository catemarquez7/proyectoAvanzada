package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import bll.*;
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
        setTitle("House Hunter - Panel de Administración");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 750);
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
        Contenidos.setBounds(0, 178, 984, 533);
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
        lblTitulo.setBounds(350, 10, 250, 30);
        lblTitulo.setFont(new Font("Mongolian Baiti", Font.PLAIN, 20));
        panel.add(lblTitulo);

        JLabel lblMensaje = new JLabel("");
        lblMensaje.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblMensaje.setBounds(50, 400, 700, 25);
        panel.add(lblMensaje);

        String[] columnas = {"ID", "Nombre", "Provincia", "Dirección", "Habitaciones", "Calificación"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        JTable tabla = new JTable(modelo);
        tabla.setFont(new Font("Mongolian Baiti", Font.PLAIN, 13));
        tabla.setRowHeight(25);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20, 60, 930, 270);
        panel.add(scroll);

        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(50, 350, 150, 35);
        btnActualizar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        btnActualizar.addActionListener(e -> {
            actualizarTablaHoteles(modelo);
            lblMensaje.setForeground(new Color(0, 128, 0));
            lblMensaje.setText("Lista de hoteles actualizada correctamente.");
        });
        panel.add(btnActualizar);

        JButton btnCrear = new JButton("Crear Hotel");
        btnCrear.setBounds(230, 350, 150, 35);
        btnCrear.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        btnCrear.addActionListener(e -> {
            lblMensaje.setText("");
            crearPanelCrearHotel(modelo, lblMensaje);
        });
        panel.add(btnCrear);

        JButton btnModificar = new JButton("Modificar");
        btnModificar.setBounds(410, 350, 150, 35);
        btnModificar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        btnModificar.addActionListener(e -> {
            lblMensaje.setText("");
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                lblMensaje.setForeground(Color.RED);
                lblMensaje.setText("Error: Seleccione un hotel de la tabla para modificar.");
                return;
            }
            int id = (int) modelo.getValueAt(fila, 0);
            
           
            crearPanelModificarHotelSeleccion(id, modelo, lblMensaje);
        });
        panel.add(btnModificar);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(590, 350, 150, 35);
        btnEliminar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        btnEliminar.addActionListener(e -> {
            lblMensaje.setText("");
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                lblMensaje.setForeground(Color.RED);
                lblMensaje.setText("Error: Seleccione un hotel de la tabla para eliminar.");
                return;
            }
            int id = (int) modelo.getValueAt(fila, 0);
            int confirm = JOptionPane.showConfirmDialog(panel, "¿Eliminar hotel seleccionado?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    DtoAdministrador.eliminarHotel(id);
                    actualizarTablaHoteles(modelo);
                    lblMensaje.setForeground(new Color(0, 128, 0));
                    lblMensaje.setText("¡Hotel eliminado exitosamente!");
                } catch (Exception ex) {
                    lblMensaje.setForeground(Color.RED);
                    lblMensaje.setText("Error al eliminar hotel.");
                }
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
            modelo.addRow(new Object[]{
                h.getId(), h.getNombre(), h.getProvincia(), h.getDireccion(),
                h.getCant_habitaciones(), String.format("%.2f", h.getCalificacion_promedio())
            });
        }
    }


    private void crearPanelCrearHotel(DefaultTableModel modelo, JLabel lblMensajePadre) {
        JFrame frame = new JFrame("Crear Nuevo Hotel");
        frame.setSize(500, 450);
        frame.setLocationRelativeTo(this);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(179, 217, 255));

        JLabel lblTitulo = new JLabel("Crear Nuevo Hotel");
        lblTitulo.setBounds(150, 20, 200, 30);
        lblTitulo.setFont(new Font("Mongolian Baiti", Font.PLAIN, 20));
        frame.add(lblTitulo);

        JLabel lblError = new JLabel("");
        lblError.setForeground(Color.RED);
        lblError.setBounds(50, 340, 400, 20);
        frame.add(lblError);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(50, 70, 100, 25);
        lblNombre.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        frame.add(lblNombre);
        JTextField txtNombre = new JTextField();
        txtNombre.setBounds(180, 70, 250, 25);
        frame.add(txtNombre);

        JLabel lblProvincia = new JLabel("Provincia:");
        lblProvincia.setBounds(50, 110, 100, 25);
        lblProvincia.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        frame.add(lblProvincia);
        JTextField txtProvincia = new JTextField();
        txtProvincia.setBounds(180, 110, 250, 25);
        frame.add(txtProvincia);

        JLabel lblDireccion = new JLabel("Dirección:");
        lblDireccion.setBounds(50, 150, 100, 25);
        lblDireccion.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        frame.add(lblDireccion);
        JTextField txtDireccion = new JTextField();
        txtDireccion.setBounds(180, 150, 250, 25);
        frame.add(txtDireccion);

        JLabel lblHab = new JLabel("Habitaciones:");
        lblHab.setBounds(50, 190, 120, 25);
        lblHab.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        frame.add(lblHab);
        JSpinner spnHab = new JSpinner(new SpinnerNumberModel(10, 1, 500, 1));
        spnHab.setBounds(180, 190, 100, 25);
        frame.add(spnHab);

        JLabel lblCupo = new JLabel("Cupo Máximo:");
        lblCupo.setBounds(50, 230, 120, 25);
        lblCupo.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        frame.add(lblCupo);
        JSpinner spnCupo = new JSpinner(new SpinnerNumberModel(50, 1, 1000, 10));
        spnCupo.setBounds(180, 230, 100, 25);
        frame.add(spnCupo);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(120, 290, 120, 35);
        btnGuardar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        btnGuardar.addActionListener(e -> {
            lblError.setText("");
            if (txtNombre.getText().trim().isEmpty() || txtProvincia.getText().trim().isEmpty()) {
                lblError.setForeground(Color.RED);
                lblError.setText("Error: Complete todos los campos.");
                return;
            }
            try {
                DtoAdministrador.crearHotel(
                    txtNombre.getText(),
                    txtProvincia.getText(),
                    txtDireccion.getText(),
                    (int) spnHab.getValue(),
                    (int) spnCupo.getValue()
                );
                actualizarTablaHoteles(modelo);
                lblMensajePadre.setForeground(new Color(0, 128, 0));
                lblMensajePadre.setText("Hotel creado exitosamente.");
                frame.dispose();
            } catch (Exception ex) {
                lblError.setForeground(Color.RED);
                lblError.setText("Error al guardar en BBDD.");
            }
        });
        frame.add(btnGuardar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(260, 290, 120, 35);
        btnCancelar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        btnCancelar.addActionListener(e -> frame.dispose());
        frame.add(btnCancelar);

        frame.setVisible(true);
    }

 
    private void crearPanelModificarHotelSeleccion(int idHotel, DefaultTableModel modelo, JLabel lblMensajePadre) {
        JFrame frame = new JFrame("Modificar Hotel: Selección");
        frame.setSize(450, 300);
        frame.setLocationRelativeTo(this);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(179, 217, 255));

        JLabel lblTitulo = new JLabel("¿Qué desea modificar?");
        lblTitulo.setBounds(100, 20, 250, 30);
        lblTitulo.setFont(new Font("Mongolian Baiti", Font.PLAIN, 20));
        frame.add(lblTitulo);

        JLabel lblError = new JLabel("");
        lblError.setForeground(Color.RED);
        lblError.setBounds(50, 200, 350, 20);
        frame.add(lblError);

        JLabel lblOpcion = new JLabel("Seleccione campo:");
        lblOpcion.setBounds(50, 80, 150, 25);
        lblOpcion.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        frame.add(lblOpcion);

        JComboBox<String> cmbOpciones = new JComboBox<>(new String[]{
            "Nombre", "Provincia", "Dirección", "Cant. Habitaciones", "Cupo Máximo"
        });
        cmbOpciones.setBounds(50, 120, 200, 25);
        frame.add(cmbOpciones);

        JButton btnSeleccionar = new JButton("Seleccionar");
        btnSeleccionar.setBounds(260, 120, 120, 35);
        btnSeleccionar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        btnSeleccionar.addActionListener(e -> {
            String campo = (String) cmbOpciones.getSelectedItem();
            if (campo == null) {
                lblError.setText("Debe seleccionar una opción.");
                return;
            }
            frame.dispose();
           
            crearPanelModificarHotelDetalles(idHotel, campo, modelo, lblMensajePadre);
        });
        frame.add(btnSeleccionar);
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(160, 165, 120, 35);
        btnCancelar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        btnCancelar.addActionListener(e -> frame.dispose());
        frame.add(btnCancelar);

        frame.setVisible(true);
    }


    private void crearPanelModificarHotelDetalles(int idHotel, String campo, DefaultTableModel modelo, JLabel lblMensajePadre) {
        List<Hotel> hoteles = DtoAdministrador.verHoteles();
        Hotel hotel = hoteles.stream().filter(h -> h.getId() == idHotel).findFirst().orElse(null);
        if (hotel == null) return;

        JFrame frame = new JFrame("Modificar: " + campo);
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(this);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(179, 217, 255));

        JLabel lblTitulo = new JLabel("Editando: " + campo);
        lblTitulo.setBounds(50, 20, 400, 30);
        lblTitulo.setFont(new Font("Mongolian Baiti", Font.PLAIN, 18));
        frame.add(lblTitulo);

        JLabel lblError = new JLabel("");
        lblError.setForeground(Color.RED);
        lblError.setBounds(50, 230, 400, 20);
        frame.add(lblError);
        
        JLabel lblValorActual = new JLabel("Valor Actual:");
        lblValorActual.setBounds(50, 70, 120, 25);
        lblValorActual.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        frame.add(lblValorActual);
        
        JLabel lblNuevoValor = new JLabel("Nuevo Valor:");
        lblNuevoValor.setBounds(50, 110, 120, 25);
        lblNuevoValor.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        frame.add(lblNuevoValor);

        Component inputComponent = null;
        Object valorActual = "";

    
        switch (campo) {
            case "Nombre":
                valorActual = hotel.getNombre();
                inputComponent = new JTextField(hotel.getNombre());
                break;
            case "Provincia":
                valorActual = hotel.getProvincia();
                inputComponent = new JTextField(hotel.getProvincia());
                break;
            case "Dirección":
                valorActual = hotel.getDireccion();
                inputComponent = new JTextField(hotel.getDireccion());
                break;
            case "Cant. Habitaciones":
                valorActual = hotel.getCant_habitaciones();
                inputComponent = new JSpinner(new SpinnerNumberModel(hotel.getCant_habitaciones(), 1, 500, 1));
                break;
            case "Cupo Máximo":
                valorActual = hotel.getCupo_maximo();
                inputComponent = new JSpinner(new SpinnerNumberModel(hotel.getCupo_maximo(), 1, 1000, 10));
                break;
        }

        JLabel lblCurrent = new JLabel(String.valueOf(valorActual));
        lblCurrent.setBounds(180, 70, 250, 25);
        frame.add(lblCurrent);

        if (inputComponent != null) {
            inputComponent.setBounds(180, 110, 250, 25);
            frame.add(inputComponent);
        }

        final Component finalInput = inputComponent;

        JButton btnGuardar = new JButton("Guardar Cambios");
        btnGuardar.setBounds(120, 170, 150, 35);
        btnGuardar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        btnGuardar.addActionListener(e -> {
            lblError.setText("");
            Object newValue = null;
            
            try {
                if (finalInput instanceof JTextField) {
                    newValue = ((JTextField) finalInput).getText();
                } else if (finalInput instanceof JSpinner) {
                    newValue = ((JSpinner) finalInput).getValue();
                }
                
                if (newValue == null || String.valueOf(newValue).trim().isEmpty()) {
                    lblError.setText("Error: El campo no puede estar vacío.");
                    return;
                }
                
           
                DtoAdministrador.modificarHotelCampo(idHotel, campo, newValue);
                
                actualizarTablaHoteles(modelo);
                lblMensajePadre.setForeground(new Color(0, 128, 0));
                lblMensajePadre.setText("Campo '" + campo + "' actualizado correctamente.");
                frame.dispose();
                
            } catch (Exception ex) {
                lblError.setForeground(Color.RED);
                lblError.setText("Error al guardar: " + ex.getMessage());
            }
        });
        frame.add(btnGuardar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(280, 170, 120, 35);
        btnCancelar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        btnCancelar.addActionListener(e -> frame.dispose());
        frame.add(btnCancelar);

        frame.setVisible(true);
    }


    private JPanel crearPanelPaquetes() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);

        JLabel lblTitulo = new JLabel("Gestión de Paquetes");
        lblTitulo.setBounds(350, 10, 250, 30);
        lblTitulo.setFont(new Font("Mongolian Baiti", Font.PLAIN, 20));
        panel.add(lblTitulo);

      
        JLabel lblMensaje = new JLabel("");
        lblMensaje.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblMensaje.setBounds(50, 400, 700, 25);
        panel.add(lblMensaje);

        JLabel lblHotel = new JLabel("Seleccione Hotel:");
        lblHotel.setBounds(30, 60, 150, 25);
        lblHotel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        panel.add(lblHotel);

        JComboBox<String> cmbHoteles = new JComboBox<>();
        cmbHoteles.setBounds(180, 60, 400, 25);
        cmbHoteles.setFont(new Font("Mongolian Baiti", Font.PLAIN, 13));
        panel.add(cmbHoteles);

        String[] columnas = {"ID", "Hotel", "Inicio", "Fin", "Precio", "Cupo Act", "Cupo Max"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        JTable tabla = new JTable(modelo);
        tabla.setFont(new Font("Mongolian Baiti", Font.PLAIN, 12));
        tabla.setRowHeight(25);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20, 110, 930, 220);
        panel.add(scroll);

        JButton btnCargar = new JButton("Ver Paquetes");
        btnCargar.setBounds(600, 60, 180, 25);
        btnCargar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 13));
        btnCargar.addActionListener(e -> {
            lblMensaje.setText("");
            if (cmbHoteles.getSelectedItem() == null) {
                lblMensaje.setForeground(Color.RED);
                lblMensaje.setText("Error: Debe seleccionar un hotel de la lista.");
                return;
            }
            String sel = (String) cmbHoteles.getSelectedItem();
            int idHotel = Integer.parseInt(sel.split(" - ")[0]);
            actualizarTablaPaquetes(modelo, idHotel);
            
            if (modelo.getRowCount() > 0) {
                lblMensaje.setForeground(new Color(0, 128, 0));
                lblMensaje.setText("Paquetes cargados correctamente.");
            } else {
                lblMensaje.setForeground(Color.RED);
                lblMensaje.setText("No se encontraron paquetes para este hotel.");
            }
        });
        panel.add(btnCargar);

        JButton btnCrear = new JButton("Crear Paquete");
        btnCrear.setBounds(50, 350, 180, 35);
        btnCrear.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        btnCrear.addActionListener(e -> {
            lblMensaje.setText("");
            crearPanelCrearPaquete();
            if (cmbHoteles.getSelectedItem() != null) {
                String sel = (String) cmbHoteles.getSelectedItem();
                int idHotel = Integer.parseInt(sel.split(" - ")[0]);
                actualizarTablaPaquetes(modelo, idHotel);
                lblMensaje.setForeground(new Color(0, 128, 0));
                lblMensaje.setText("Ventana de creación abierta.");
            }
        });
        panel.add(btnCrear);

        JButton btnModificar = new JButton("Modificar");
        btnModificar.setBounds(250, 350, 180, 35);
        btnModificar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        btnModificar.addActionListener(e -> {
            lblMensaje.setText("");
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                lblMensaje.setForeground(Color.RED);
                lblMensaje.setText("Error: Seleccione un paquete de la tabla para modificar.");
                return;
            }
            int idPaquete = (int) modelo.getValueAt(fila, 0);
            crearPanelModificarPaquete(idPaquete);
            if (cmbHoteles.getSelectedItem() != null) {
                String sel = (String) cmbHoteles.getSelectedItem();
                int idHotel = Integer.parseInt(sel.split(" - ")[0]);
                actualizarTablaPaquetes(modelo, idHotel);
                lblMensaje.setForeground(new Color(0, 128, 0));
                lblMensaje.setText("Ventana de modificación abierta.");
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
            modelo.addRow(new Object[]{
                p.getId(),
                p.getHotel().getNombre(),
                p.getInicioDate(),
                p.getFinDate(),
                "$" + p.getPrecio(),
                p.getCupo_actual(),
                p.getCupo_maximo()
            });
        }
    }

    private JPanel crearPanelReservas() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);

        JLabel lblTitulo = new JLabel("Gestión de Reservas");
        lblTitulo.setBounds(350, 10, 250, 30);
        lblTitulo.setFont(new Font("Mongolian Baiti", Font.PLAIN, 20));
        panel.add(lblTitulo);

        // Label Mensajes
        JLabel lblMensaje = new JLabel("");
        lblMensaje.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblMensaje.setBounds(50, 400, 700, 25);
        panel.add(lblMensaje);

        JLabel lblHotel = new JLabel("Seleccione Hotel:");
        lblHotel.setBounds(30, 60, 150, 25);
        lblHotel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        panel.add(lblHotel);

        JComboBox<String> cmbHoteles = new JComboBox<>();
        cmbHoteles.setBounds(180, 60, 400, 25);
        cmbHoteles.setFont(new Font("Mongolian Baiti", Font.PLAIN, 13));
        panel.add(cmbHoteles);

        String[] columnas = {"ID", "Cliente", "Hotel", "Estado", "Monto", "Check-in"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        JTable tabla = new JTable(modelo);
        tabla.setFont(new Font("Mongolian Baiti", Font.PLAIN, 12));
        tabla.setRowHeight(25);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20, 110, 930, 220);
        panel.add(scroll);

        JButton btnCargar = new JButton("Ver Reservas");
        btnCargar.setBounds(600, 60, 180, 25);
        btnCargar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 13));
        btnCargar.addActionListener(e -> {
            lblMensaje.setText("");
            if (cmbHoteles.getSelectedItem() == null) {
                lblMensaje.setForeground(Color.RED);
                lblMensaje.setText("Error: Seleccione un hotel.");
                return;
            }
            String sel = (String) cmbHoteles.getSelectedItem();
            int idHotel = Integer.parseInt(sel.split(" - ")[0]);
            actualizarTablaReservas(modelo, idHotel);
            
            if (modelo.getRowCount() > 0) {
                lblMensaje.setForeground(new Color(0, 128, 0));
                lblMensaje.setText("Reservas cargadas exitosamente.");
            } else {
                lblMensaje.setForeground(Color.RED);
                lblMensaje.setText("No se encontraron reservas para este hotel.");
            }
        });
        panel.add(btnCargar);

        JButton btnModificar = new JButton("Modificar Reserva");
        btnModificar.setBounds(380, 350, 180, 35);
        btnModificar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        btnModificar.addActionListener(e -> {
            lblMensaje.setText("");
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                lblMensaje.setForeground(Color.RED);
                lblMensaje.setText("Error: Seleccione una reserva de la tabla.");
                return;
            }
         
            lblMensaje.setForeground(Color.RED);
            lblMensaje.setText("Funcionalidad en desarrollo: No disponible por el momento.");
        });
        panel.add(btnModificar);

        cargarHotelesEnCombo(cmbHoteles);
        return panel;
    }

    private void actualizarTablaReservas(DefaultTableModel modelo, int idHotel) {
        modelo.setRowCount(0);
        List<Reserva> reservas = DtoAdministrador.verReservas(idHotel);
        for (Reserva r : reservas) {
            modelo.addRow(new Object[]{
                r.getId(),
                r.getCliente().getNombre() + " " + r.getCliente().getApellido(),
                r.getPaquete().getHotel().getNombre(),
                r.getEstado(),
                "$" + r.getMonto_final(),
                r.getFecha_checkin() != null ? r.getFecha_checkin().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "N/A"
            });
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

        // Label Mensajes
        JLabel lblMensaje = new JLabel("");
        lblMensaje.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblMensaje.setBounds(50, 400, 700, 25);
        panel.add(lblMensaje);

        JLabel lblHotel = new JLabel("Seleccione Hotel:");
        lblHotel.setBounds(30, 60, 150, 25);
        lblHotel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        panel.add(lblHotel);

        JComboBox<String> cmbHoteles = new JComboBox<>();
        cmbHoteles.setBounds(180, 60, 400, 25);
        cmbHoteles.setFont(new Font("Mongolian Baiti", Font.PLAIN, 13));
        panel.add(cmbHoteles);

        String[] columnas = {"ID", "Nombre", "Categoría", "Precio", "Duración", "Edad Min", "Edad Max"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        JTable tabla = new JTable(modelo);
        tabla.setFont(new Font("Mongolian Baiti", Font.PLAIN, 12));
        tabla.setRowHeight(25);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20, 110, 930, 220);
        panel.add(scroll);

        JButton btnCargar = new JButton("Ver Actividades");
        btnCargar.setBounds(600, 60, 180, 25);
        btnCargar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 13));
        btnCargar.addActionListener(e -> {
            lblMensaje.setText("");
            if (cmbHoteles.getSelectedItem() == null) {
                lblMensaje.setForeground(Color.RED);
                lblMensaje.setText("Error: Seleccione un hotel.");
                return;
            }
            String sel = (String) cmbHoteles.getSelectedItem();
            int idHotel = Integer.parseInt(sel.split(" - ")[0]);
            actualizarTablaActividades(modelo, idHotel);
            
            if (modelo.getRowCount() > 0) {
                lblMensaje.setForeground(new Color(0, 128, 0));
                lblMensaje.setText("Actividades cargadas correctamente.");
            } else {
                lblMensaje.setForeground(Color.RED);
                lblMensaje.setText("No hay actividades registradas para este hotel.");
            }
        });
        panel.add(btnCargar);

        JButton btnCrear = new JButton("Crear Actividad");
        btnCrear.setBounds(380, 350, 180, 35);
        btnCrear.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        btnCrear.addActionListener(e -> {
            crearPanelCrearActividad();
            if (cmbHoteles.getSelectedItem() != null) {
                String sel = (String) cmbHoteles.getSelectedItem();
                int idHotel = Integer.parseInt(sel.split(" - ")[0]);
                actualizarTablaActividades(modelo, idHotel);
                lblMensaje.setForeground(new Color(0, 128, 0));
                lblMensaje.setText("Ventana de creación de actividad abierta.");
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
            modelo.addRow(new Object[]{
                a.getId(), a.getNombre(), a.getCategoria(), "$" + a.getPrecio(),
                a.getDuracion() + "h", a.getEdad_minima(), a.getEdad_maxima()
            });
        }
    }


    private JPanel crearPanelCuentas() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);

        JLabel lblTitulo = new JLabel("Gestión de Cuentas de Usuario");
        lblTitulo.setBounds(300, 10, 400, 30);
        lblTitulo.setFont(new Font("Mongolian Baiti", Font.PLAIN, 20));
        panel.add(lblTitulo);

        // Label Mensajes
        JLabel lblMensaje = new JLabel("");
        lblMensaje.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblMensaje.setBounds(50, 350, 800, 25);
        panel.add(lblMensaje);

        String[] columnas = {"ID", "Usuario", "Nombre", "Apellido", "DNI", "Tipo", "Estado"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        JTable tabla = new JTable(modelo);
        tabla.setFont(new Font("Mongolian Baiti", Font.PLAIN, 12));
        tabla.setRowHeight(25);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20, 60, 930, 220);
        panel.add(scroll);


        int yButtons = 300;

        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(30, yButtons, 120, 30);
        btnActualizar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        btnActualizar.addActionListener(e -> {
            actualizarTablaCuentas(modelo);
            lblMensaje.setForeground(new Color(0, 128, 0));
            lblMensaje.setText("Tabla de usuarios actualizada.");
        });
        panel.add(btnActualizar);

        JButton btnCrear = new JButton("Crear");
        btnCrear.setBounds(170, yButtons, 120, 30);
        btnCrear.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        btnCrear.addActionListener(e -> {
            crearPanelCrearCuenta();
            actualizarTablaCuentas(modelo);
            lblMensaje.setForeground(new Color(0, 128, 0));
            lblMensaje.setText("Ventana de creación de usuario abierta.");
        });
        panel.add(btnCrear);

        JButton btnBloquear = new JButton("Bloquear");
        btnBloquear.setBounds(310, yButtons, 130, 30);
        btnBloquear.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        btnBloquear.addActionListener(e -> {
            lblMensaje.setText("");
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                lblMensaje.setForeground(Color.RED);
                lblMensaje.setText("Seleccione un usuario para bloquear.");
                return;
            }
            int id = (int) modelo.getValueAt(fila, 0);
            DtoAdministrador.bloquearCuenta(id);
            actualizarTablaCuentas(modelo);
            lblMensaje.setForeground(new Color(0, 128, 0));
            lblMensaje.setText("Cuenta bloqueada exitosamente.");
        });
        panel.add(btnBloquear);

        JButton btnDesbloquear = new JButton("Desbloquear");
        btnDesbloquear.setBounds(460, yButtons, 150, 30);
        btnDesbloquear.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        btnDesbloquear.addActionListener(e -> {
            lblMensaje.setText("");
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                lblMensaje.setForeground(Color.RED);
                lblMensaje.setText("Seleccione un usuario para desbloquear.");
                return;
            }
            int id = (int) modelo.getValueAt(fila, 0);
            DtoAdministrador.desbloquearCuenta(id);
            actualizarTablaCuentas(modelo);
            lblMensaje.setForeground(new Color(0, 128, 0));
            lblMensaje.setText("Cuenta desbloqueada exitosamente.");
        });
        panel.add(btnDesbloquear);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(630, yButtons, 130, 30);
        btnEliminar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        btnEliminar.addActionListener(e -> {
            lblMensaje.setText("");
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                lblMensaje.setForeground(Color.RED);
                lblMensaje.setText("Seleccione un usuario para eliminar.");
                return;
            }
            int id = (int) modelo.getValueAt(fila, 0);
            int confirm = JOptionPane.showConfirmDialog(panel, "¿Eliminar usuario?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                DtoAdministrador.eliminarCuenta(id);
                actualizarTablaCuentas(modelo);
                lblMensaje.setForeground(new Color(0, 128, 0));
                lblMensaje.setText("Usuario eliminado correctamente.");
            }
        });
        panel.add(btnEliminar);

        JButton btnStats = new JButton("Estadísticas");
        btnStats.setBounds(780, yButtons, 150, 30);
        btnStats.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        btnStats.addActionListener(e -> {
            String stats = DtoAdministrador.obtenerEstadisticas();
 
            lblMensaje.setForeground(new Color(0, 128, 0));
            lblMensaje.setText("Mostrando estadísticas.");
            JOptionPane.showMessageDialog(panel, stats, "Estadísticas", JOptionPane.INFORMATION_MESSAGE);
        });
        panel.add(btnStats);

        actualizarTablaCuentas(modelo);
        return panel;
    }

    private void actualizarTablaCuentas(DefaultTableModel modelo) {
        modelo.setRowCount(0);
        List<Usuario> usuarios = DtoAdministrador.verCuentas();
        for (Usuario u : usuarios) {
            String tipo = u.getTipo_usuario().equals("1") ? "Cliente" :
                          u.getTipo_usuario().equals("2") ? "Encargado" : "Administrador";
            modelo.addRow(new Object[]{
                u.getId(), u.getUser(), u.getNombre(), u.getApellido(),
                u.getDni(), tipo, u.getEstado()
            });
        }
    }


    private JPanel crearPanelSistema() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);

        JLabel lblTitulo = new JLabel("Configuración del Sistema");
        lblTitulo.setBounds(300, 30, 400, 30);
        lblTitulo.setFont(new Font("Mongolian Baiti", Font.BOLD, 20));
        panel.add(lblTitulo);

        JButton btnSuspender = new JButton("Suspender/Activar Sistema");
        btnSuspender.setBounds(300, 150, 300, 50);
        btnSuspender.setFont(new Font("Mongolian Baiti", Font.BOLD, 16));
        btnSuspender.addActionListener(e -> crearPanelSuspenderSistema());
        panel.add(btnSuspender);

        JLabel lblInfo = new JLabel("Control total sobre el estado del sistema");
        lblInfo.setBounds(300, 230, 400, 20);
        lblInfo.setFont(new Font("Mongolian Baiti", Font.ITALIC, 14));
        panel.add(lblInfo);

        return panel;
    }



    private void crearPanelCrearHotel() {
        JFrame frame = new JFrame("Crear Nuevo Hotel");
        frame.setSize(500, 450);
        frame.setLocationRelativeTo(this);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(179, 217, 255));

        JLabel lblTitulo = new JLabel("Crear Nuevo Hotel");
        lblTitulo.setBounds(150, 20, 200, 30);
        lblTitulo.setFont(new Font("Mongolian Baiti", Font.PLAIN, 20));
        frame.add(lblTitulo);

    
        JLabel lblError = new JLabel("");
        lblError.setForeground(Color.RED);
        lblError.setBounds(50, 340, 400, 20);
        frame.add(lblError);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(50, 70, 100, 25);
        lblNombre.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        frame.add(lblNombre);
        JTextField txtNombre = new JTextField();
        txtNombre.setBounds(180, 70, 250, 25);
        frame.add(txtNombre);

        JLabel lblProvincia = new JLabel("Provincia:");
        lblProvincia.setBounds(50, 110, 100, 25);
        lblProvincia.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        frame.add(lblProvincia);
        JTextField txtProvincia = new JTextField();
        txtProvincia.setBounds(180, 110, 250, 25);
        frame.add(txtProvincia);

        JLabel lblDireccion = new JLabel("Dirección:");
        lblDireccion.setBounds(50, 150, 100, 25);
        lblDireccion.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        frame.add(lblDireccion);
        JTextField txtDireccion = new JTextField();
        txtDireccion.setBounds(180, 150, 250, 25);
        frame.add(txtDireccion);

        JLabel lblHab = new JLabel("Habitaciones:");
        lblHab.setBounds(50, 190, 120, 25);
        lblHab.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        frame.add(lblHab);
        JSpinner spnHab = new JSpinner(new SpinnerNumberModel(10, 1, 500, 1));
        spnHab.setBounds(180, 190, 100, 25);
        frame.add(spnHab);

        JLabel lblCupo = new JLabel("Cupo Máximo:");
        lblCupo.setBounds(50, 230, 120, 25);
        lblCupo.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        frame.add(lblCupo);
        JSpinner spnCupo = new JSpinner(new SpinnerNumberModel(50, 1, 1000, 10));
        spnCupo.setBounds(180, 230, 100, 25);
        frame.add(spnCupo);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(120, 290, 120, 35);
        btnGuardar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        btnGuardar.addActionListener(e -> {
            lblError.setText("");
            if (txtNombre.getText().trim().isEmpty() || txtProvincia.getText().trim().isEmpty()) {
                lblError.setForeground(Color.RED);
                lblError.setText("Error: Complete todos los campos.");
                return;
            }
            DtoAdministrador.crearHotel(
                txtNombre.getText(),
                txtProvincia.getText(),
                txtDireccion.getText(),
                (int) spnHab.getValue(),
                (int) spnCupo.getValue()
            );
            frame.dispose();
        });
        frame.add(btnGuardar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(260, 290, 120, 35);
        btnCancelar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        btnCancelar.addActionListener(e -> frame.dispose());
        frame.add(btnCancelar);

        frame.setVisible(true);
    }

    private void crearPanelModificarHotel(int idHotel) {
        List<Hotel> hoteles = DtoAdministrador.verHoteles();
        Hotel hotel = hoteles.stream().filter(h -> h.getId() == idHotel).findFirst().orElse(null);
        if (hotel == null) return;

        JFrame frame = new JFrame("Modificar Hotel");
        frame.setSize(500, 250);
        frame.setLocationRelativeTo(this);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(179, 217, 255));

        JLabel lblTitulo = new JLabel("Modificar Hotel");
        lblTitulo.setBounds(170, 20, 200, 30);
        lblTitulo.setFont(new Font("Mongolian Baiti", Font.PLAIN, 20));
        frame.add(lblTitulo);

        JLabel lblNombre = new JLabel("Nuevo Nombre:");
        lblNombre.setBounds(50, 80, 120, 25);
        lblNombre.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        frame.add(lblNombre);
        JTextField txtNombre = new JTextField(hotel.getNombre());
        txtNombre.setBounds(180, 80, 250, 25);
        frame.add(txtNombre);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(120, 140, 120, 35);
        btnGuardar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        btnGuardar.addActionListener(e -> {
            DtoAdministrador.modificarHotel(idHotel, txtNombre.getText());
            frame.dispose();
        });
        frame.add(btnGuardar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(260, 140, 120, 35);
        btnCancelar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        btnCancelar.addActionListener(e -> frame.dispose());
        frame.add(btnCancelar);

        frame.setVisible(true);
    }

    private void crearPanelCrearPaquete() {
        JFrame frame = new JFrame("Crear Nuevo Paquete");
        frame.setSize(550, 550);
        frame.setLocationRelativeTo(this);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(179, 217, 255));

        JLabel lblTitulo = new JLabel("Crear Nuevo Paquete");
        lblTitulo.setBounds(160, 20, 250, 30);
        lblTitulo.setFont(new Font("Mongolian Baiti", Font.PLAIN, 20));
        frame.add(lblTitulo);
        
        // Label error
        JLabel lblError = new JLabel("");
        lblError.setForeground(Color.RED);
        lblError.setBounds(50, 410, 450, 20);
        frame.add(lblError);

        JLabel lblHotel = new JLabel("Hotel:");
        lblHotel.setBounds(50, 70, 100, 25);
        lblHotel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        frame.add(lblHotel);
        JComboBox<String> cmbHotel = new JComboBox<>();
        cmbHotel.setBounds(180, 70, 300, 25);
        cargarHotelesEnCombo(cmbHotel);
        frame.add(cmbHotel);

        JLabel lblActividad = new JLabel("Actividad:");
        lblActividad.setBounds(50, 110, 100, 25);
        lblActividad.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        frame.add(lblActividad);
        JComboBox<String> cmbActividad = new JComboBox<>();
        cmbActividad.setBounds(180, 110, 300, 25);
        frame.add(cmbActividad);

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
        frame.add(lblInicio);
        JTextField txtInicio = new JTextField("2025-01-01");
        txtInicio.setBounds(180, 150, 150, 25);
        frame.add(txtInicio);

        JLabel lblFin = new JLabel("Fecha Fin:");
        lblFin.setBounds(50, 190, 120, 25);
        lblFin.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        frame.add(lblFin);
        JTextField txtFin = new JTextField("2025-01-10");
        txtFin.setBounds(180, 190, 150, 25);
        frame.add(txtFin);

        JLabel lblPrecio = new JLabel("Precio:");
        lblPrecio.setBounds(50, 230, 100, 25);
        lblPrecio.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        frame.add(lblPrecio);
        JSpinner spnPrecio = new JSpinner(new SpinnerNumberModel(1000.0, 0.0, 999999.0, 100.0));
        spnPrecio.setBounds(180, 230, 150, 25);
        frame.add(spnPrecio);

        JLabel lblCupo = new JLabel("Cupo Máximo:");
        lblCupo.setBounds(50, 270, 120, 25);
        lblCupo.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        frame.add(lblCupo);
        JSpinner spnCupo = new JSpinner(new SpinnerNumberModel(10, 1, 100, 1));
        spnCupo.setBounds(180, 270, 100, 25);
        frame.add(spnCupo);

        JLabel lblFormato = new JLabel("Formato fecha: YYYY-MM-DD");
        lblFormato.setBounds(180, 310, 250, 20);
        lblFormato.setFont(new Font("Mongolian Baiti", Font.ITALIC, 12));
        frame.add(lblFormato);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(140, 360, 120, 35);
        btnGuardar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        btnGuardar.addActionListener(e -> {
            lblError.setText("");
            if (cmbHotel.getSelectedItem() == null || cmbActividad.getSelectedItem() == null) {
                lblError.setForeground(Color.RED);
                lblError.setText("Error: Seleccione hotel y actividad.");
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
                lblError.setForeground(Color.RED);
                lblError.setText("Error en los datos (Fecha/Formato).");
            }
        });
        frame.add(btnGuardar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(280, 360, 120, 35);
        btnCancelar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        btnCancelar.addActionListener(e -> frame.dispose());
        frame.add(btnCancelar);

        frame.setVisible(true);
    }

    private void crearPanelModificarPaquete(int idPaquete) {
        // Buscar paquete
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
            if (paquete != null) break;
        }
        
        if (paquete == null) return;
        final Paquete paqueteSeleccionado = paquete;

        JFrame frame = new JFrame("Modificar Paquete");
        frame.setSize(550, 450);
        frame.setLocationRelativeTo(this);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(179, 217, 255));

        JLabel lblTitulo = new JLabel("Modificar Paquete");
        lblTitulo.setBounds(180, 20, 250, 30);
        lblTitulo.setFont(new Font("Mongolian Baiti", Font.PLAIN, 20));
        frame.add(lblTitulo);
        
        JLabel lblError = new JLabel("");
        lblError.setForeground(Color.RED);
        lblError.setBounds(50, 330, 450, 20);
        frame.add(lblError);

        JLabel lblHotel = new JLabel("Hotel:");
        lblHotel.setBounds(50, 70, 100, 25);
        lblHotel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        frame.add(lblHotel);
        JComboBox<String> cmbHotel = new JComboBox<>();
        cmbHotel.setBounds(180, 70, 300, 25);
        cargarHotelesEnCombo(cmbHotel);
        for (int i = 0; i < cmbHotel.getItemCount(); i++) {
            if (cmbHotel.getItemAt(i).startsWith(paqueteSeleccionado.getHotel().getId() + " - ")) {
                cmbHotel.setSelectedIndex(i);
                break;
            }
        }
        frame.add(cmbHotel);

        JLabel lblInicio = new JLabel("Fecha Inicio:");
        lblInicio.setBounds(50, 110, 120, 25);
        lblInicio.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        frame.add(lblInicio);
        JTextField txtInicio = new JTextField(paqueteSeleccionado.getInicioDate().toString());
        txtInicio.setBounds(180, 110, 150, 25);
        frame.add(txtInicio);

        JLabel lblFin = new JLabel("Fecha Fin:");
        lblFin.setBounds(50, 150, 120, 25);
        lblFin.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        frame.add(lblFin);
        JTextField txtFin = new JTextField(paqueteSeleccionado.getFinDate().toString());
        txtFin.setBounds(180, 150, 150, 25);
        frame.add(txtFin);
        
        JLabel lblPrecio = new JLabel("Precio:");
        lblPrecio.setBounds(50, 190, 100, 25);
        lblPrecio.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        frame.add(lblPrecio);
        JSpinner spnPrecio = new JSpinner(new SpinnerNumberModel(paqueteSeleccionado.getPrecio(), 0.0, 999999.0, 100.0));
        spnPrecio.setBounds(180, 190, 150, 25);
        frame.add(spnPrecio);

        JLabel lblFormato = new JLabel("Formato fecha: YYYY-MM-DD");
        lblFormato.setBounds(180, 230, 250, 20);
        lblFormato.setFont(new Font("Mongolian Baiti", Font.ITALIC, 12));
        frame.add(lblFormato);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(140, 280, 120, 35);
        btnGuardar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        btnGuardar.addActionListener(e -> {
            lblError.setText("");
            try {
                String selHotel = (String) cmbHotel.getSelectedItem();
                int idHotel = Integer.parseInt(selHotel.split(" - ")[0]);
                
                LocalDate fechaInicio = LocalDate.parse(txtInicio.getText());
                LocalDate fechaFin = LocalDate.parse(txtFin.getText());
                double precio = (Double) spnPrecio.getValue();
                
                Integer idHabitacion = paqueteSeleccionado.getHabitacion() != null ? paqueteSeleccionado.getHabitacion().getId() : null;
                Integer idActividad = paqueteSeleccionado.getActividad() != null ? paqueteSeleccionado.getActividad().getId() : null;
                
                DtoAdministrador.modificarPaquete(idPaquete, fechaInicio, fechaFin, precio, idHotel, idHabitacion, idActividad);
                frame.dispose();
            } catch (Exception ex) {
                lblError.setForeground(Color.RED);
                lblError.setText("Error en los datos (formato fecha o valores).");
            }
        });
        frame.add(btnGuardar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(280, 280, 120, 35);
        btnCancelar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        btnCancelar.addActionListener(e -> frame.dispose());
        frame.add(btnCancelar);

        frame.setVisible(true);
    }

    private void crearPanelCrearActividad() {
        JFrame frame = new JFrame("Crear Nueva Actividad");
        frame.setSize(550, 650);
        frame.setLocationRelativeTo(this);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(179, 217, 255));

        JLabel lblTitulo = new JLabel("Crear Nueva Actividad");
        lblTitulo.setBounds(150, 20, 300, 30);
        lblTitulo.setFont(new Font("Mongolian Baiti", Font.PLAIN, 20));
        frame.add(lblTitulo);
        
        JLabel lblError = new JLabel("");
        lblError.setForeground(Color.RED);
        lblError.setBounds(50, 580, 450, 20);
        frame.add(lblError);

        JLabel lblHotel = new JLabel("Hotel:");
        lblHotel.setBounds(50, 70, 100, 25);
        lblHotel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        frame.add(lblHotel);
        JComboBox<String> cmbHotel = new JComboBox<>();
        cmbHotel.setBounds(180, 70, 300, 25);
        cargarHotelesEnCombo(cmbHotel);
        frame.add(cmbHotel);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(50, 110, 100, 25);
        lblNombre.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        frame.add(lblNombre);
        JTextField txtNombre = new JTextField();
        txtNombre.setBounds(180, 110, 300, 25);
        frame.add(txtNombre);

        JLabel lblCategoria = new JLabel("Categoría:");
        lblCategoria.setBounds(50, 150, 100, 25);
        lblCategoria.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        frame.add(lblCategoria);
        JComboBox<String> cmbCategoria = new JComboBox<>(new String[]{
            "Deportes", "Cultura", "Aventura", "Relajacion", "Gastronomia", "Otro"
        });
        cmbCategoria.setBounds(180, 150, 200, 25);
        frame.add(cmbCategoria);

        JLabel lblLocacion = new JLabel("Locación:");
        lblLocacion.setBounds(50, 190, 100, 25);
        lblLocacion.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        frame.add(lblLocacion);
        JTextField txtLocacion = new JTextField();
        txtLocacion.setBounds(180, 190, 300, 25);
        frame.add(txtLocacion);

        JLabel lblEdadMin = new JLabel("Edad Mínima:");
        lblEdadMin.setBounds(50, 230, 120, 25);
        lblEdadMin.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        frame.add(lblEdadMin);
        JSpinner spnEdadMin = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
        spnEdadMin.setBounds(180, 230, 100, 25);
        frame.add(spnEdadMin);

        JLabel lblEdadMax = new JLabel("Edad Máxima:");
        lblEdadMax.setBounds(50, 270, 120, 25);
        lblEdadMax.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        frame.add(lblEdadMax);
        JSpinner spnEdadMax = new JSpinner(new SpinnerNumberModel(99, 0, 150, 1));
        spnEdadMax.setBounds(180, 270, 100, 25);
        frame.add(spnEdadMax);

        JLabel lblPrecio = new JLabel("Precio:");
        lblPrecio.setBounds(50, 310, 100, 25);
        lblPrecio.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        frame.add(lblPrecio);
        JSpinner spnPrecio = new JSpinner(new SpinnerNumberModel(500.0, 0.0, 999999.0, 50.0));
        spnPrecio.setBounds(180, 310, 150, 25);
        frame.add(spnPrecio);

        JLabel lblDuracion = new JLabel("Duración (hs):");
        lblDuracion.setBounds(50, 350, 120, 25);
        lblDuracion.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        frame.add(lblDuracion);
        JSpinner spnDuracion = new JSpinner(new SpinnerNumberModel(2.0, 0.5, 24.0, 0.5));
        spnDuracion.setBounds(180, 350, 100, 25);
        frame.add(spnDuracion);

        JLabel lblInicio = new JLabel("Fecha Inicio:");
        lblInicio.setBounds(50, 390, 120, 25);
        lblInicio.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        frame.add(lblInicio);
        JTextField txtInicio = new JTextField("2025-01-01");
        txtInicio.setBounds(180, 390, 150, 25);
        frame.add(txtInicio);

        JLabel lblFin = new JLabel("Fecha Fin:");
        lblFin.setBounds(50, 430, 120, 25);
        lblFin.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        frame.add(lblFin);
        JTextField txtFin = new JTextField("2025-12-31");
        txtFin.setBounds(180, 430, 150, 25);
        frame.add(txtFin);

        JLabel lblRiesgo = new JLabel("Riesgo:");
        lblRiesgo.setBounds(50, 470, 100, 25);
        lblRiesgo.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        frame.add(lblRiesgo);
        JComboBox<String> cmbRiesgo = new JComboBox<>(new String[]{"Si", "No"});
        cmbRiesgo.setBounds(180, 470, 100, 25);
        frame.add(cmbRiesgo);

        JLabel lblFormato = new JLabel("Formato fecha: YYYY-MM-DD");
        lblFormato.setBounds(180, 505, 250, 20);
        lblFormato.setFont(new Font("Mongolian Baiti", Font.ITALIC, 12));
        frame.add(lblFormato);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(140, 540, 120, 35);
        btnGuardar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        btnGuardar.addActionListener(e -> {
            lblError.setText("");
            if (cmbHotel.getSelectedItem() == null || txtNombre.getText().trim().isEmpty()) {
                lblError.setForeground(Color.RED);
                lblError.setText("Error: Complete hotel y nombre.");
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
                
                DtoAdministrador.crearActividad(nombre, categoria, locacion, edadMin, edadMax, 
                    precio, duracion, fechaInicio, fechaFin, idHotel, riesgo);
                frame.dispose();
            } catch (Exception ex) {
                lblError.setForeground(Color.RED);
                lblError.setText("Error en los datos (Formatos o valores).");
            }
        });
        frame.add(btnGuardar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(280, 540, 120, 35);
        btnCancelar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        btnCancelar.addActionListener(e -> frame.dispose());
        frame.add(btnCancelar);

        frame.setVisible(true);
    }

    private void crearPanelCrearCuenta() {
        JFrame frame = new JFrame("Crear Nueva Cuenta");
        frame.setSize(550, 700);
        frame.setLocationRelativeTo(this);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(179, 217, 255));

        JLabel lblTitulo = new JLabel("Crear Nueva Cuenta de Usuario");
        lblTitulo.setBounds(120, 20, 350, 30);
        lblTitulo.setFont(new Font("Mongolian Baiti", Font.PLAIN, 20));
        frame.add(lblTitulo);
        
        JLabel lblError = new JLabel("");
        lblError.setForeground(Color.RED);
        lblError.setBounds(50, 610, 450, 20);
        frame.add(lblError);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(50, 70, 120, 25);
        lblNombre.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        frame.add(lblNombre);
        JTextField txtNombre = new JTextField();
        txtNombre.setBounds(180, 70, 300, 25);
        frame.add(txtNombre);

        JLabel lblApellido = new JLabel("Apellido:");
        lblApellido.setBounds(50, 110, 120, 25);
        lblApellido.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        frame.add(lblApellido);
        JTextField txtApellido = new JTextField();
        txtApellido.setBounds(180, 110, 300, 25);
        frame.add(txtApellido);

        JLabel lblFechaNac = new JLabel("Fecha Nac:");
        lblFechaNac.setBounds(50, 150, 120, 25);
        lblFechaNac.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        frame.add(lblFechaNac);
        JTextField txtFechaNac = new JTextField("2000-01-01");
        txtFechaNac.setBounds(180, 150, 150, 25);
        frame.add(txtFechaNac);

        JLabel lblMail = new JLabel("Email:");
        lblMail.setBounds(50, 190, 120, 25);
        lblMail.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        frame.add(lblMail);
        JTextField txtMail = new JTextField();
        txtMail.setBounds(180, 190, 300, 25);
        frame.add(txtMail);

        JLabel lblDNI = new JLabel("DNI:");
        lblDNI.setBounds(50, 230, 120, 25);
        lblDNI.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        frame.add(lblDNI);
        JSpinner spnDNI = new JSpinner(new SpinnerNumberModel(10000000, 1000000, 99999999, 1));
        spnDNI.setBounds(180, 230, 150, 25);
        frame.add(spnDNI);

        JLabel lblDireccion = new JLabel("Dirección:");
        lblDireccion.setBounds(50, 270, 120, 25);
        lblDireccion.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        frame.add(lblDireccion);
        JTextField txtDireccion = new JTextField();
        txtDireccion.setBounds(180, 270, 300, 25);
        frame.add(txtDireccion);

        JLabel lblUser = new JLabel("Usuario:");
        lblUser.setBounds(50, 310, 120, 25);
        lblUser.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        frame.add(lblUser);
        JTextField txtUser = new JTextField();
        txtUser.setBounds(180, 310, 200, 25);
        frame.add(txtUser);

        JLabel lblPass = new JLabel("Contraseña:");
        lblPass.setBounds(50, 350, 120, 25);
        lblPass.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        frame.add(lblPass);
        JPasswordField txtPass = new JPasswordField();
        txtPass.setBounds(180, 350, 200, 25);
        frame.add(txtPass);

        JLabel lblPregunta = new JLabel("Pregunta Seg:");
        lblPregunta.setBounds(50, 390, 120, 25);
        lblPregunta.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        frame.add(lblPregunta);
        JTextField txtPregunta = new JTextField();
        txtPregunta.setBounds(180, 390, 300, 25);
        frame.add(txtPregunta);

        JLabel lblRespuesta = new JLabel("Respuesta:");
        lblRespuesta.setBounds(50, 430, 120, 25);
        lblRespuesta.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        frame.add(lblRespuesta);
        JTextField txtRespuesta = new JTextField();
        txtRespuesta.setBounds(180, 430, 300, 25);
        frame.add(txtRespuesta);

        JLabel lblTipo = new JLabel("Tipo Usuario:");
        lblTipo.setBounds(50, 470, 120, 25);
        lblTipo.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        frame.add(lblTipo);
        JComboBox<String> cmbTipo = new JComboBox<>(new String[]{
            "1 - Cliente", "2 - Encargado", "3 - Administrador"
        });
        cmbTipo.setBounds(180, 470, 200, 25);
        frame.add(cmbTipo);

        JComboBox<String> cmbHotel = new JComboBox<>();
        cmbHotel.setBounds(180, 510, 300, 25);
        cmbHotel.setVisible(false);
        frame.add(cmbHotel);

        JLabel lblHotel = new JLabel("Hotel:");
        lblHotel.setBounds(50, 510, 120, 25);
        lblHotel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        lblHotel.setVisible(false);
        frame.add(lblHotel);

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
            lblError.setText("");
            if (txtNombre.getText().trim().isEmpty() || txtUser.getText().trim().isEmpty()) {
                lblError.setForeground(Color.RED);
                lblError.setText("Error: Nombre y Usuario son obligatorios.");
                return;
            }
            try {
                String nombre = txtNombre.getText();
                String apellido = txtApellido.getText();
                LocalDate fechaNac = LocalDate.parse(txtFechaNac.getText());
                String mail = txtMail.getText();
                int dni = (int) spnDNI.getValue();
                String direccion = txtDireccion.getText();
                String user = txtUser.getText();
                String pass = new String(txtPass.getPassword());
                String pregunta = txtPregunta.getText();
                String respuesta = txtRespuesta.getText();
                
                String selTipo = (String) cmbTipo.getSelectedItem();
                String tipoUsuario = selTipo.split(" - ")[0];
                
                Usuario nuevoUsuario = new Usuario(nombre, apellido, fechaNac, mail, dni, 
                    direccion, user, pass, pregunta, respuesta);
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
                } else {
                    lblError.setForeground(Color.RED);
                    lblError.setText("Error: No se pudo crear el usuario (posible duplicado).");
                }
            } catch (Exception ex) {
                lblError.setForeground(Color.RED);
                lblError.setText("Error en los datos: Revise fechas y formatos.");
            }
        });
        frame.add(btnGuardar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(280, 560, 120, 35);
        btnCancelar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        btnCancelar.addActionListener(e -> frame.dispose());
        frame.add(btnCancelar);

        frame.setVisible(true);
    }

    private void crearPanelSuspenderSistema() {
        JFrame frame = new JFrame("Suspender/Activar Sistema");
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(this);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(179, 217, 255));

        JLabel lblTitulo = new JLabel("Suspender/Activar Sistema");
        lblTitulo.setBounds(120, 20, 300, 30);
        lblTitulo.setFont(new Font("Mongolian Baiti", Font.PLAIN, 20));
        frame.add(lblTitulo);

        JLabel lblPregunta = new JLabel("¿Desea cambiar el estado del sistema?");
        lblPregunta.setBounds(100, 80, 300, 25);
        lblPregunta.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        frame.add(lblPregunta);
        
   
        JLabel lblAviso = new JLabel("");
        lblAviso.setForeground(new Color(0, 128, 0));
        lblAviso.setBounds(100, 200, 300, 20);
        frame.add(lblAviso);

        JButton btnConfirmar = new JButton("Confirmar");
        btnConfirmar.setBounds(120, 140, 120, 35);
        btnConfirmar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        btnConfirmar.addActionListener(e -> {
            bll.Administrador.suspenderSistema();

            lblAviso.setText("Estado del sistema cambiado.");

            new javax.swing.Timer(1000, ev -> frame.dispose()).start();
        });
        frame.add(btnConfirmar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(260, 140, 120, 35);
        btnCancelar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        btnCancelar.addActionListener(e -> frame.dispose());
        frame.add(btnCancelar);

        frame.setVisible(true);
    }
}
