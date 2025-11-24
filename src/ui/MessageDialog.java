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

public class MessageDialog extends JDialog {

    private static final long serialVersionUID = 1L;
    private boolean closed = false;

    public static final int SUCCESS = 1;
    public static final int ERROR = 2;
    public static final int INFO = 3;

    public MessageDialog(String mensaje, int tipo) {

        setTitle(getTituloPorTipo(tipo));
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
        lblMensaje.setForeground(getColorPorTipo(tipo));
        lblMensaje.setBounds(30, 30, 380, 80);
        contentPane.add(lblMensaje);

        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.setFont(new Font("Mongolian Baiti", Font.PLAIN, 15));
        btnAceptar.setBounds(165, 130, 120, 30);
        btnAceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                closed = true;
                dispose();
            }
        });
        contentPane.add(btnAceptar);
    }

    private String getTituloPorTipo(int tipo) {
        switch (tipo) {
            case SUCCESS:
                return "Éxito";
            case ERROR:
                return "Error";
            case INFO:
                return "Información";
            default:
                return "Mensaje";
        }
    }

    private Color getColorPorTipo(int tipo) {
        switch (tipo) {
            case SUCCESS:
                return new Color(0, 128, 0);
            case ERROR:
                return Color.RED;
            case INFO:
                return new Color(100, 100, 100);
            default:
                return Color.BLACK;
        }
    }

    public boolean isClosed() {
        return closed;
    }

    public static void mostrar(String mensaje, int tipo) {
        MessageDialog dialog = new MessageDialog(mensaje, tipo);
        dialog.setVisible(true); 
    }
}
