package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import bll.Hotel;
import bll.Paquete;
import bll.Reserva;
import bll.Review;
import bll.Usuario;
import dll.DtoAdministrador;
import dll.DtoCliente;

public class Cliente extends JFrame {
    private Usuario usuario;
    private bll.Cliente cliente;
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    public Cliente(Usuario usuario, bll.Cliente cliente2) {
        this.cliente = cliente2;
        this.usuario = usuario;
        iniciar(this.usuario, this.cliente);
    }

    public void iniciar(Usuario usuario, bll.Cliente cliente) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 770, 650);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(179, 217, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        try {
            if (cliente.getReservas() != null) {
                cliente.getReservas().clear();
            } else {
                cliente.setReservas(new java.util.LinkedList<Reserva>());
            }
            
            if (cliente.getReservasPasadas() != null) {
                cliente.getReservasPasadas().clear();
            } else {
                cliente.setReservasPasadas(new java.util.LinkedList<Reserva>());
            }
            
            if (cliente.getReviews() != null) {
                cliente.getReviews().clear();
            } else {
                cliente.setReviews(new java.util.LinkedList<Review>());
            }
            
            // cargar datos de la bbdd
            DtoCliente.cargarReservasExistentes(usuario, cliente);
            DtoCliente.cargarReviewsExistentes(usuario, cliente);
        } catch (Exception ex) {
        	MessageDialog.mostrar("Error al cargar datos del usuario.", MessageDialog.ERROR);
            ex.printStackTrace();
        }

        JTabbedPane Contenidos = new JTabbedPane(JTabbedPane.TOP);
        Contenidos.setBounds(0, 178, 754, 433);
        Contenidos.setFont(new Font("Mongolian Baiti", Font.PLAIN, 15));
        contentPane.add(Contenidos);

        // PAQUETES RECOMENDADOS 
        JPanel panelPaquetesReco = crearPanelPaquetesRecomendados();
        Contenidos.addTab("Paquetes recomendados", null, panelPaquetesReco, null);

        // TODOS LOS PAQUETES 
        JPanel panelPaquetes = crearPanelTodosPaquetes();
        Contenidos.addTab("Paquetes", null, panelPaquetes, null);

        // MIS RESERVAS 
        JPanel panelReservas = crearPanelReservas();
        Contenidos.addTab("Mis reservas", null, panelReservas, null);

        // RESEÑAS 
        JPanel panelReviews = crearPanelReviews();
        Contenidos.addTab("Reseñas", null, panelReviews, null);

        // PREFERENCIAS 
        JPanel panelPreferencias = crearPanelPreferencias();
        Contenidos.addTab("Preferencias", null, panelPreferencias, null);

        // EXTRAS
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
        if (imageUrl != null) {
            ImageIcon originalIcon = new ImageIcon(imageUrl);
            Image originalImage = originalIcon.getImage();
            Image scaledImage = originalImage.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            Perfil.setIcon(new ImageIcon(scaledImage));
        }
        Perfil.setBounds(10, 11, 148, 153);
        contentPane.add(Perfil);

        JLabel lblbienvenido = new JLabel("Bienvenido " + usuario.getNombre());
        lblbienvenido.setFont(new Font("Mongolian Baiti", Font.PLAIN, 18));
        lblbienvenido.setBounds(168, 65, 300, 14);
        contentPane.add(lblbienvenido);

        JLabel lblHotel = new JLabel("House Hunter: elegí tus mejores vacaciones.");
        lblHotel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 20));
        lblHotel.setBounds(168, 29, 485, 25);
        contentPane.add(lblHotel);
    }


    // PANEL PAQUETES RECOMENDADOS 
    private JPanel crearPanelPaquetesRecomendados() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);

        JLabel label = new JLabel("Paquetes recomendados según sus preferencias.");
        label.setBounds(150, 10, 400, 20);
        label.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        panel.add(label);

        // error
        JLabel lblError = new JLabel("");
        lblError.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblError.setForeground(Color.RED);
        lblError.setBounds(150, 200, 500, 20);
        panel.add(lblError);

        final List<Paquete> paquetes;
        try {
            paquetes = DtoCliente.verPaquetesReco(usuario, cliente);
        } catch (Exception ex) {
            lblError.setText("Error al cargar paquetes recomendados.");
            ex.printStackTrace();
            return panel;
        }

        // Verificar si no tiene preferencias (null)
        if (paquetes == null) {
            lblError.setForeground(new Color(100, 100, 100));
            lblError.setText("Para ver recomendaciones personalizadas, configure sus preferencias.");
            
            JButton btnIrPreferencias = new JButton("Configurar Preferencias");
            btnIrPreferencias.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
            btnIrPreferencias.setBounds(250, 250, 250, 35);
            btnIrPreferencias.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Cliente.this.setVisible(false);
                    DialogPreferencias dialog = new DialogPreferencias(usuario, cliente, Cliente.this);
                    dialog.setVisible(true);
                }
            });
            panel.add(btnIrPreferencias);
            
            return panel;
        }

        String[] columnas = {"Hotel", "Actividad", "Precio", "Inicio", "Fin", "Cupo"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Verificar si tiene preferencias pero no hay paquetes disponibles
        if (paquetes.isEmpty()) {
            lblError.setText("No hay paquetes disponibles en este momento para sus preferencias.");
        }

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

        JTable tabla = new JTable(modelo);
        tabla.setFont(new Font("Mongolian Baiti", Font.PLAIN, 12));
        tabla.setRowHeight(25);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20, 40, 700, 280);
        panel.add(scroll);

        // reservar paquete recomendado
        JButton btnReservar = new JButton("Reservar Paquete");
        btnReservar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        btnReservar.setBounds(280, 335, 180, 30);
        btnReservar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblError.setText(""); 
                lblError.setForeground(Color.RED);
                
                int filaSeleccionada = tabla.getSelectedRow();
                if (filaSeleccionada == -1) {
                    lblError.setText("Seleccione un paquete de la tabla.");
                } else {
                    try {
                        boolean confirm = Confirmar.mostrar("¿Está seguro de que desea reservar este paquete?");
                        if (!confirm) return;

                        Paquete paqueteSeleccionado = paquetes.get(filaSeleccionada);
                        boolean reservado = DtoCliente.reservarPaquete(usuario, paqueteSeleccionado, cliente);
                        if (reservado) {
                            lblError.setForeground(new Color(0, 128, 0));
                            lblError.setText("Paquete reservado exitosamente!");
                            
                            new Thread(() -> {
                                try {
                                    Thread.sleep(1000);
                                    contentPane.removeAll();
                                    iniciar(usuario, cliente);
                                    contentPane.revalidate();
                                    contentPane.repaint();
                                } catch (InterruptedException ex) {
                                    ex.printStackTrace();
                                }
                            }).start();
                        } else {
                            lblError.setForeground(Color.RED);
                            lblError.setText("No se pudo reservar el paquete.");
                        }
                    } catch (Exception ex) {
                        lblError.setForeground(Color.RED);
                        lblError.setText("Error al procesar la reserva.");
                        ex.printStackTrace();
                    }
                }
            }
        });
        panel.add(btnReservar);

        return panel;
    }

    // PANEL TODOS LOS PAQUETES 
    private JPanel crearPanelTodosPaquetes() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);

        JLabel label = new JLabel("Seleccione un hotel para ver sus paquetes disponibles:");
        label.setBounds(180, 10, 400, 20);
        label.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        panel.add(label);

        // error
        JLabel lblError = new JLabel("");
        lblError.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblError.setForeground(Color.RED);
        lblError.setBounds(250, 370, 300, 20);
        panel.add(lblError);

        List<Hotel> hoteles = null;
        try {
            hoteles = DtoAdministrador.verHoteles();
        } catch (Exception ex) {
            lblError.setText("Error al cargar hoteles.");
            ex.printStackTrace();
            return panel;
        }

        if (hoteles.isEmpty()) {
            lblError.setText("No hay hoteles registrados.");
            return panel;
        }

        JComboBox<String> comboHoteles = new JComboBox<>();
        for (Hotel h : hoteles) {
            comboHoteles.addItem(h.getId() + " - " + h.getNombre() + " (" + h.getProvincia() + ")");
        }
        comboHoteles.setFont(new Font("Mongolian Baiti", Font.PLAIN, 13));
        comboHoteles.setBounds(150, 40, 400, 25);
        panel.add(comboHoteles);

        // Tabla de paquetes
        String[] columnas = {"ID", "Hotel", "Actividad", "Precio", "Inicio", "Fin"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable tabla = new JTable(modelo);
        tabla.setFont(new Font("Mongolian Baiti", Font.PLAIN, 12));
        tabla.setRowHeight(25);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20, 110, 700, 210);
        panel.add(scroll);

        final List<Paquete>[] paquetesArray = new List[1];

        JButton btnCargar = new JButton("Ver Paquetes");
        btnCargar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        btnCargar.setBounds(300, 75, 150, 25);
        btnCargar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblError.setText(""); 
                String seleccion = (String) comboHoteles.getSelectedItem();
                if (seleccion != null) {
                    try {
                        int idHotel = Integer.parseInt(seleccion.split(" - ")[0]);
                        List<Paquete> paquetes = DtoCliente.verPaquetes(idHotel);
                        paquetesArray[0] = paquetes;
                        
                        modelo.setRowCount(0);
                        if (paquetes.isEmpty()) {
                            lblError.setText("No hay paquetes para este hotel.");
                            return;
                        }

                        for (Paquete p : paquetes) {
                            Object[] fila = {
                                p.getId(),
                                p.getHotel().getNombre(),
                                p.getActividad() != null ? p.getActividad().getNombre() : "N/A",
                                "$" + String.format("%.2f", p.getPrecio()),
                                p.getInicioDate(),
                                p.getFinDate()
                            };
                            modelo.addRow(fila);
                        }
                    } catch (Exception ex) {
                        lblError.setForeground(Color.RED);
                        lblError.setText("Error al cargar paquetes.");
                        ex.printStackTrace();
                    }
                }
            }
        });
        panel.add(btnCargar);

     // boton para reservar
        JButton btnReservar = new JButton("Reservar Paquete");
        btnReservar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        btnReservar.setBounds(280, 335, 180, 30);
        btnReservar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblError.setText("");
                lblError.setForeground(Color.RED);
                
                if (paquetesArray[0] == null || paquetesArray[0].isEmpty()) {
                    lblError.setText("Primero cargue los paquetes.");
                    return;
                }

                int filaSeleccionada = tabla.getSelectedRow();
                if (filaSeleccionada == -1) {
                    lblError.setText("Seleccione un paquete de la tabla.");
                } else {
                    try {
                        boolean confirm = Confirmar.mostrar("¿Está seguro de que desea reservar este paquete?");
                        if (!confirm) return;

                        Paquete paqueteSeleccionado = paquetesArray[0].get(filaSeleccionada);
                        boolean reservado = DtoCliente.reservarPaquete(usuario, paqueteSeleccionado, cliente);
                        if (reservado) {
                            lblError.setForeground(new Color(0, 128, 0));
                            lblError.setText("Paquete reservado exitosamente!");
                            
                            new Thread(() -> {
                                try {
                                    Thread.sleep(1000);
                                    contentPane.removeAll();
                                    iniciar(usuario, cliente);
                                    contentPane.revalidate();
                                    contentPane.repaint();
                                } catch (InterruptedException ex) {
                                    ex.printStackTrace();
                                }
                            }).start();
                        } else {
                            lblError.setText("No se pudo reservar el paquete.");
                        }
                    } catch (Exception ex) {
                        lblError.setText("Error al procesar la reserva.");
                        ex.printStackTrace();
                    }
                }
            }
        });
        panel.add(btnReservar);


        return panel;
    }

    // PANEL RESERVAS
   
    private JPanel crearPanelReservas() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);

        // Validar que las listas no sean null
        if (cliente.getReservas() == null) {
            cliente.setReservas(new java.util.LinkedList<Reserva>());
        }
        if (cliente.getReservasPasadas() == null) {
            cliente.setReservasPasadas(new java.util.LinkedList<Reserva>());
        }

        // Reservas activas
        JLabel labelActivas = new JLabel("Mis Reservas Activas");
        labelActivas.setBounds(100, 10, 200, 20);
        labelActivas.setFont(new Font("Mongolian Baiti", Font.BOLD, 15));
        panel.add(labelActivas);

        String[] columnasActivas = {"Hotel", "Actividad", "Precio"};
        DefaultTableModel modeloActivas = new DefaultTableModel(columnasActivas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        try {
            for (Reserva r : cliente.getReservas()) {
                Object[] fila = {
                    r.getPaquete().getHotel().getNombre(),
                    r.getPaquete().getActividad().getNombre(),
                    "$" + String.format("%.2f", r.getPaquete().getPrecio())
                };
                modeloActivas.addRow(fila);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JTable tablaActivas = new JTable(modeloActivas);
        tablaActivas.setFont(new Font("Mongolian Baiti", Font.PLAIN, 12));
        tablaActivas.setRowHeight(25);
        JScrollPane scrollActivas = new JScrollPane(tablaActivas);
        scrollActivas.setBounds(20, 40, 340, 250);
        panel.add(scrollActivas);

        // Botón cancelar 
        JButton btnCancelar = new JButton("Cancelar Reserva");
        btnCancelar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 13));
        btnCancelar.setBounds(90, 300, 180, 30);
        panel.add(btnCancelar);

        // Label de error para reservas activas
        JLabel lblErrorActivas = new JLabel("");
        lblErrorActivas.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblErrorActivas.setForeground(Color.RED);
        lblErrorActivas.setBounds(30, 335, 320, 20);
        panel.add(lblErrorActivas);

        if (cliente.getReservas().isEmpty()) {
            lblErrorActivas.setText("No tiene reservas activas.");
        }

        // Historial
        JLabel labelHistorial = new JLabel("Historial de Reservas");
        labelHistorial.setBounds(480, 10, 200, 20);
        labelHistorial.setFont(new Font("Mongolian Baiti", Font.BOLD, 15));
        panel.add(labelHistorial);

        String[] columnasHistorial = {"Hotel", "Actividad", "Precio"};
        DefaultTableModel modeloHistorial = new DefaultTableModel(columnasHistorial, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        try {
            for (Reserva r : cliente.getReservasPasadas()) {
                Object[] fila = {
                    r.getPaquete().getHotel().getNombre(),
                    r.getPaquete().getActividad().getNombre(),
                    "$" + String.format("%.2f", r.getPaquete().getPrecio())
                };
                modeloHistorial.addRow(fila);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JTable tablaHistorial = new JTable(modeloHistorial);
        tablaHistorial.setFont(new Font("Mongolian Baiti", Font.PLAIN, 12));
        tablaHistorial.setRowHeight(25);
        JScrollPane scrollHistorial = new JScrollPane(tablaHistorial);
        scrollHistorial.setBounds(380, 40, 340, 250);
        panel.add(scrollHistorial);

        // Label de info para historial
        JLabel lblInfoHistorial = new JLabel("");
        lblInfoHistorial.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblInfoHistorial.setForeground(new Color(100, 100, 100));
        lblInfoHistorial.setBounds(400, 300, 320, 20);
        panel.add(lblInfoHistorial);

        if (cliente.getReservasPasadas().isEmpty()) {
            lblInfoHistorial.setText("No tiene reservas finalizadas.");
        } else {
            lblInfoHistorial.setText("Total finalizadas: " + cliente.getReservasPasadas().size());
        }

        // Boton cancelar
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblErrorActivas.setText(""); 
                lblErrorActivas.setForeground(Color.RED);
                
                if (cliente.getReservas().isEmpty()) {
                    lblErrorActivas.setText("No hay reservas para cancelar.");
                    return;
                }

                int filaSeleccionada = tablaActivas.getSelectedRow();
                if (filaSeleccionada == -1) {
                    lblErrorActivas.setText("Seleccione una reserva.");
                } else {
                	boolean confirm = Confirmar.mostrar("¿Está seguro de cancelar esta reserva?");
                	if (confirm) {
                	    try {
                	        Reserva reservaSeleccionada = cliente.getReservas().get(filaSeleccionada);
                	        boolean cancelada = DtoCliente.cancelarReserva(usuario, cliente, reservaSeleccionada);
                	        if (cancelada) {
                	            lblErrorActivas.setForeground(new Color(0, 128, 0));
                	            lblErrorActivas.setText("¡Reserva cancelada exitosamente!");
                	            
                	            // Remover de la tabla visualmente
                	            modeloActivas.removeRow(filaSeleccionada);
                	            
                	            // Actualizar mensaje si quedó vacío
                	            if (cliente.getReservas().isEmpty()) {
                	                lblErrorActivas.setForeground(Color.RED);
                	                lblErrorActivas.setText("No tiene reservas activas.");
                	            }
                	        } else {
                	            lblErrorActivas.setText("No se pudo cancelar la reserva.");
                	        }
                	    } catch (Exception ex) {
                	        lblErrorActivas.setText("Error al cancelar la reserva.");
                	        ex.printStackTrace();
                	    }
                	}

                }
            }
        });

        return panel;
    }
    
    
    
    
    // PANEL RESEÑAS 
    private JPanel crearPanelReviews() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);

        JLabel label = new JLabel("Mis Reseñas");
        label.setBounds(320, 10, 150, 20);
        label.setFont(new Font("Mongolian Baiti", Font.BOLD, 16));
        panel.add(label);

        JLabel lblError = new JLabel("");
        lblError.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblError.setForeground(new Color(100, 100, 100));
        lblError.setBounds(220, 260, 350, 20);
        panel.add(lblError);

        String[] columnas = {"Hotel", "Actividad", "Puntaje", "Descripción"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        try {
            if (cliente.getReviews().isEmpty()) {
                lblError.setText("No tiene reseñas registradas.");
            }

            for (Review r : cliente.getReviews()) {
                Object[] fila = {
                    r.getHotel().getNombre(),
                    r.getReserva().getPaquete().getActividad().getNombre(),
                    r.getPuntaje(),
                    r.getDescripcion()
                };
                modelo.addRow(fila);
            }
        } catch (Exception ex) {
            lblError.setText("Error al cargar reseñas.");
            ex.printStackTrace();
        }

        JTable tabla = new JTable(modelo);
        tabla.setFont(new Font("Mongolian Baiti", Font.PLAIN, 12));
        tabla.setRowHeight(25);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20, 50, 700, 150);
        panel.add(scroll);

        // Botones
        JButton btnEscribir = new JButton("Escribir Reseña");
        btnEscribir.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        btnEscribir.setBounds(180, 220, 160, 30);
        btnEscribir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                lblError.setText(""); 
                lblError.setForeground(Color.RED);

                if (cliente.getReservasPasadas().isEmpty()) {
                    lblError.setText("No tiene reservas finalizadas para reseñar.");
                    return;
                }

                Cliente.this.setVisible(false);

                DialogEscribirResena dialog = new DialogEscribirResena(usuario, cliente, Cliente.this);
                dialog.setVisible(true);
            }
        });


        panel.add(btnEscribir);

        JButton btnEliminar = new JButton("Eliminar Reseña");
        btnEliminar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        btnEliminar.setBounds(380, 220, 160, 30);
        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblError.setText(""); 
                lblError.setForeground(Color.RED);
                
                if (cliente.getReviews().isEmpty()) {
                    lblError.setText("No hay reseñas para eliminar.");
                    return;
                }

                int filaSeleccionada = tabla.getSelectedRow();
                if (filaSeleccionada == -1) {
                    lblError.setText("Seleccione una reseña.");
                } else {
                	boolean confirm = Confirmar.mostrar("¿Eliminar esta reseña?");
                	if (confirm) {
                	    try {
                	        Review reviewSeleccionada = cliente.getReviews().get(filaSeleccionada);
                	        boolean eliminada = DtoCliente.borrarReview(usuario, cliente, reviewSeleccionada);
                	        if (eliminada) {
                	            lblError.setForeground(new Color(0, 128, 0));
                	            lblError.setText("Reseña eliminada!");
                	            
                	            new Thread(() -> {
                	                try {
                	                    Thread.sleep(1000);
                	                    contentPane.removeAll();
                	                    iniciar(usuario, cliente);
                	                    contentPane.revalidate();
                	                    contentPane.repaint();
                	                } catch (InterruptedException ex) {
                	                    ex.printStackTrace();
                	                }
                	            }).start();
                	            
                	        } else {
                	            lblError.setText("No se pudo eliminar la reseña.");
                	        }
                	    } catch (Exception ex) {
                	        lblError.setText("Error al eliminar la reseña.");
                	        ex.printStackTrace();
                	    }
                	}

                }
            }
        });
        panel.add(btnEliminar);

        return panel;
    }

    // PANEL PREFERENCIAS
    private JPanel crearPanelPreferencias() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);

        JLabel label = new JLabel("Mis Preferencias");
        label.setBounds(300, 20, 200, 25);
        label.setFont(new Font("Mongolian Baiti", Font.BOLD, 18));
        panel.add(label);

        JLabel lblError = new JLabel("");
        lblError.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblError.setForeground(Color.RED);
        lblError.setBounds(220, 300, 350, 20);
        panel.add(lblError);

        // Mostrar preferencias 
        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        textArea.setEditable(false);
        
        try {
            String preferencias = DtoCliente.mostrarPreferencias(usuario);
            if (preferencias == null || preferencias.isEmpty()) {
                lblError.setText("Error al cargar preferencias.");
                textArea.setText("No se pudieron cargar las preferencias.");
            } else {
                textArea.setText(preferencias);
            }
        } catch (Exception ex) {
            lblError.setText("Error al cargar preferencias.");
            textArea.setText("Error al obtener datos.");
            ex.printStackTrace();
        }

        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setBounds(150, 70, 450, 150);
        panel.add(scroll);

        // Botón para modificar preferencias
        JButton btnModificar = new JButton("Modificar Preferencias");
        btnModificar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        btnModificar.setBounds(250, 240, 250, 35);

        btnModificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblError.setText(""); 
                lblError.setForeground(Color.RED);
                
                Cliente.this.setVisible(false);
                
                DialogPreferencias dialog = new DialogPreferencias(usuario, cliente, Cliente.this);
                dialog.setVisible(true);
            }
        });

        panel.add(btnModificar);

        JLabel lblInfo = new JLabel("Las preferencias ayudan a recomendar paquetes personalizados.");
        lblInfo.setBounds(170, 330, 450, 20);
        lblInfo.setFont(new Font("Mongolian Baiti", Font.ITALIC, 12));
        panel.add(lblInfo);

        return panel;
    }
}