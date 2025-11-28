package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import bll.Usuario;
import bll.Cliente;
import bll.Preferencias;
import dll.DtoCliente;
import repository.Actividades_categoria;

public class DialogPreferencias extends JDialog {

    public DialogPreferencias(Usuario usuario, Cliente cliente, JFrame ventanaPrincipal) {

        setTitle("Modificar Preferencias");
        setSize(770, 650);
        setModal(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(179, 217, 255));
        panel.setLayout(null);
        setContentPane(panel);

        JLabel titulo = new JLabel("Modificar Preferencias");
        titulo.setFont(new Font("Mongolian Baiti", Font.BOLD, 22));
        titulo.setBounds(260, 20, 300, 30);
        panel.add(titulo);

        // Duración
        JLabel lblDur = new JLabel("Horas de actividad:");
        lblDur.setFont(new Font("Mongolian Baiti", Font.PLAIN, 16));
        lblDur.setBounds(180, 120, 160, 25);
        panel.add(lblDur);

        JTextField txtDuracion = new JTextField();
        txtDuracion.setFont(new Font("Mongolian Baiti", Font.PLAIN, 15));
        txtDuracion.setBounds(350, 120, 100, 28);
        panel.add(txtDuracion);

        // Categoría - Usar enum
        JLabel lblCategoria = new JLabel("Categoría:");
        lblCategoria.setFont(new Font("Mongolian Baiti", Font.PLAIN, 16));
        lblCategoria.setBounds(180, 180, 160, 25);
        panel.add(lblCategoria);

        Actividades_categoria[] categorias = Actividades_categoria.values();
        JComboBox<Actividades_categoria> comboCategoria = new JComboBox<>(categorias);
        comboCategoria.setFont(new Font("Mongolian Baiti", Font.PLAIN, 15));
        comboCategoria.setBounds(350, 180, 160, 28);
        panel.add(comboCategoria);

        // Riesgo
        JLabel lblRiesgo = new JLabel("Riesgo:");
        lblRiesgo.setFont(new Font("Mongolian Baiti", Font.PLAIN, 16));
        lblRiesgo.setBounds(180, 240, 160, 25);
        panel.add(lblRiesgo);

        String[] riesgos = {"Si", "No"};
        JComboBox<String> comboRiesgo = new JComboBox<>(riesgos);
        comboRiesgo.setFont(new Font("Mongolian Baiti", Font.PLAIN, 15));
        comboRiesgo.setBounds(350, 240, 100, 28);
        panel.add(comboRiesgo);

        JLabel lblError = new JLabel("");
        lblError.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblError.setForeground(Color.RED);
        lblError.setBounds(200, 330, 400, 25);
        panel.add(lblError);

        // Cargar preferencias actuales del usuario
        cargarPreferenciasActuales(usuario, txtDuracion, comboCategoria, comboRiesgo, lblError);

        // Botón Cancelar
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 16));
        btnCancelar.setBounds(180, 400, 150, 35);
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
        btnGuardar.setBounds(420, 400, 150, 35);
        panel.add(btnGuardar);

        btnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblError.setText("");
                lblError.setForeground(Color.RED);

                try {
                    String durText = txtDuracion.getText().trim();
                    if (durText.isEmpty()) {
                        lblError.setText("Complete todos los campos.");
                        return;
                    }

                    double dur = Double.parseDouble(durText);
                    if (dur <= 0) {
                        lblError.setText("La duración debe ser mayor a 0.");
                        return;
                    }

                    Actividades_categoria categoriaEnum = (Actividades_categoria) comboCategoria.getSelectedItem();
                    String categoria = categoriaEnum.name(); // Convertir enum a String
                    String riesgo = (String) comboRiesgo.getSelectedItem();

                    Preferencias prefs = new Preferencias();
                    prefs.setCliente(usuario.getId());
                    prefs.setDuracion(dur);
                    prefs.setCategoria(categoria);
                    prefs.setRiesgo(riesgo);

                    boolean ok = DtoCliente.ingresarPreferencias(prefs);

                    if (ok) {
                        lblError.setForeground(new Color(0, 128, 0));
                        lblError.setText("¡Preferencias guardadas exitosamente!");

                        // Deshabilitar botones para evitar doble click
                        btnGuardar.setEnabled(false);
                        btnCancelar.setEnabled(false);

                        // Esperar un momento y luego recargar
                        new Thread(() -> {
                            try {
                                Thread.sleep(1000);

                                SwingUtilities.invokeLater(() -> {
                                    dispose();
                                    ventanaPrincipal.dispose();

                                    // Crear nueva ventana Cliente con datos actualizados
                                    ui.Cliente nuevaVentana = new ui.Cliente(usuario, cliente);
                                    nuevaVentana.setVisible(true);
                                });

                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        }).start();

                    } else {
                        lblError.setText("No se pudo actualizar las preferencias.");
                    }

                } catch (NumberFormatException ex) {
                    lblError.setText("La duración debe ser un número válido.");
                } catch (Exception ex) {
                    lblError.setText("Error al procesar los datos.");
                    ex.printStackTrace();
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

    // Método para cargar las preferencias actuales del usuario
    private void cargarPreferenciasActuales(Usuario usuario, JTextField txtDuracion, 
                                           JComboBox<Actividades_categoria> comboCategoria, 
                                           JComboBox<String> comboRiesgo, JLabel lblError) {
        try {
            Preferencias prefsActuales = DtoCliente.obtenerPreferencias(usuario.getId());
            
            if (prefsActuales != null) {
                // Cargar duración
                txtDuracion.setText(String.valueOf(prefsActuales.getDuracion()));
                
                // Cargar categoría
                try {
                    Actividades_categoria cat = Actividades_categoria.valueOf(prefsActuales.getCategoria().toLowerCase());
                    comboCategoria.setSelectedItem(cat);
                } catch (IllegalArgumentException e) {
                    // Si la categoría no existe en el enum, dejar la primera opción
                    System.out.println("Categoría no válida: " + prefsActuales.getCategoria());
                }
                
                // Cargar riesgo
                comboRiesgo.setSelectedItem(prefsActuales.getRiesgo());
            }
        } catch (Exception ex) {
            lblError.setForeground(new Color(100, 100, 100));
            lblError.setText("No se pudieron cargar preferencias previas.");
            ex.printStackTrace();
        }
    }
}