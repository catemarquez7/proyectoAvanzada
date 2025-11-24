package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

import bll.Reserva;
import bll.Usuario;
import bll.Cliente;
import dll.DtoCliente;

public class DialogEscribirResena extends JDialog {

	public DialogEscribirResena(Usuario usuario, Cliente cliente, ui.Cliente ventanaPrincipal) {

        setTitle("Escribir Reseña");
        setSize(770, 650);
        setModal(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(179, 217, 255)); 
        panel.setLayout(null);
        setContentPane(panel);

        JLabel titulo = new JLabel("Nueva Reseña");
        titulo.setFont(new Font("Mongolian Baiti", Font.BOLD, 22));
        titulo.setBounds(300, 20, 200, 30);
        panel.add(titulo);

        JLabel lblReserva = new JLabel("Reserva:");
        lblReserva.setFont(new Font("Mongolian Baiti", Font.PLAIN, 16));
        lblReserva.setBounds(180, 100, 120, 25);
        panel.add(lblReserva);

        JComboBox<String> comboReserva = new JComboBox<>();
        comboReserva.setFont(new Font("Mongolian Baiti", Font.PLAIN, 15));
        comboReserva.setBounds(280, 100, 300, 28);
        panel.add(comboReserva);

        // Llenar reservas pasadas
        for (Reserva r : cliente.getReservasPasadas()) {
            comboReserva.addItem(
                r.getPaquete().getHotel().getNombre() + " - " +
                r.getPaquete().getActividad().getNombre()
            );
        }

        JLabel lblPuntaje = new JLabel("Puntaje (1-5):");
        lblPuntaje.setFont(new Font("Mongolian Baiti", Font.PLAIN, 16));
        lblPuntaje.setBounds(180, 160, 120, 25);
        panel.add(lblPuntaje);

        String[] puntajes = {"1", "2", "3", "4", "5"};
        JComboBox<String> comboPuntaje = new JComboBox<>(puntajes);
        comboPuntaje.setFont(new Font("Mongolian Baiti", Font.PLAIN, 15));
        comboPuntaje.setBounds(280, 160, 60, 28);
        panel.add(comboPuntaje);

        JLabel lblDescripcion = new JLabel("Descripción:");
        lblDescripcion.setFont(new Font("Mongolian Baiti", Font.PLAIN, 16));
        lblDescripcion.setBounds(180, 220, 120, 25);
        panel.add(lblDescripcion);

        JTextArea txtDescripcion = new JTextArea();
        txtDescripcion.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        txtDescripcion.setBorder(new LineBorder(Color.GRAY));
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);

        JScrollPane scrollDesc = new JScrollPane(txtDescripcion);
        scrollDesc.setBounds(280, 220, 300, 120);
        panel.add(scrollDesc);

        JLabel lblError = new JLabel("");
        lblError.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblError.setForeground(Color.RED);
        lblError.setBounds(200, 360, 400, 25);
        panel.add(lblError);

        // Botón Cancelar
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 16));
        btnCancelar.setBounds(220, 420, 150, 35);
        panel.add(btnCancelar);

        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                ventanaPrincipal.setVisible(true);
            }
        });

        // Botón Guardar
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 16));
        btnGuardar.setBounds(420, 420, 150, 35);
        panel.add(btnGuardar);

        btnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblError.setText("");
                lblError.setForeground(Color.RED);

                if (comboReserva.getSelectedIndex() == -1) {
                    lblError.setText("Debe seleccionar una reserva.");
                    return;
                }
                String descripcion = txtDescripcion.getText().trim();
                if (descripcion.isEmpty()) {
                    lblError.setText("La descripción no puede estar vacía.");
                    return;
                }
                try {
                    String seleccion = (String) comboReserva.getSelectedItem();
                    Reserva reservaSeleccionada = null;

                    for (Reserva r : cliente.getReservasPasadas()) {
                        String texto = r.getPaquete().getHotel().getNombre() + " - " +
                                       r.getPaquete().getActividad().getNombre();
                        if (texto.equals(seleccion)) {
                            reservaSeleccionada = r;
                            break;
                        }
                    }
                    if (DtoCliente.reviewExistente(usuario, reservaSeleccionada)) {
                        lblError.setText("Ya realizó una reseña para esta reserva.");
                        return;
                    }
                    double puntaje = Double.parseDouble((String) comboPuntaje.getSelectedItem());
                    
                    // Deshabilitar botones para evitar doble click
                    btnGuardar.setEnabled(false);
                    btnCancelar.setEnabled(false);
                    
                    boolean guardado = DtoCliente.escribirReview(
                        usuario, cliente, reservaSeleccionada, descripcion, puntaje);

                    if (guardado) {
                        lblError.setForeground(new Color(0, 128, 0));
                        lblError.setText("¡Reseña guardada exitosamente!");
                        
                        new Thread(() -> {
                            try {
                                Thread.sleep(1000);
                                
                                SwingUtilities.invokeLater(() -> {
                                    dispose(); 
                                    ventanaPrincipal.dispose();
                                    
                                    ui.Cliente nuevaVentana = new ui.Cliente(usuario, cliente);
                                    nuevaVentana.setVisible(true);
                                });
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }).start();
                    } else {
                        lblError.setText("No se pudo guardar la reseña.");
                        btnGuardar.setEnabled(true);
                        btnCancelar.setEnabled(true);
                    }

                } catch (Exception ex) {
                    lblError.setText("Error procesando la reseña.");
                    ex.printStackTrace();
                    btnGuardar.setEnabled(true);
                    btnCancelar.setEnabled(true);
                }
            }
        });

        // Manejar cierre de ventana con X
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ventanaPrincipal.setVisible(true);
            }
        });
    }
}