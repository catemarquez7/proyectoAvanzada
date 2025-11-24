package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Confirmar extends JDialog {

    private static final long serialVersionUID = 1L;
    private boolean confirmed = false;

    public Confirmar(String mensaje) {

        setTitle("Confirmación");
        setModal(true); 
        setResizable(false);
        setBounds(100, 100, 450, 220);
        setLocationRelativeTo(null);

        JPanel contentPane = new JPanel();
        contentPane.setBackground(new Color(179, 217, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JLabel lblMensaje = new JLabel("<html><div style='text-align: center;'>" + mensaje + "</div></html>");
        lblMensaje.setFont(new Font("Mongolian Baiti", Font.PLAIN, 16));
        lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
        lblMensaje.setBounds(30, 30, 380, 80);
        contentPane.add(lblMensaje);

        JButton btnSi = new JButton("Sí");
        btnSi.setFont(new Font("Mongolian Baiti", Font.PLAIN, 15));
        btnSi.setBounds(100, 130, 100, 30);
        btnSi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                confirmed = true;
                dispose(); // cerrar
            }
        });
        contentPane.add(btnSi);

        JButton btnNo = new JButton("No");
        btnNo.setFont(new Font("Mongolian Baiti", Font.PLAIN, 15));
        btnNo.setBounds(250, 130, 100, 30);
        btnNo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                confirmed = false;
                dispose(); // cerrar
            }
        });
        contentPane.add(btnNo);
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public static boolean mostrar(String mensaje) {
        Confirmar dialog = new Confirmar(mensaje);
        dialog.setVisible(true); 
        return dialog.isConfirmed();
    }
}
