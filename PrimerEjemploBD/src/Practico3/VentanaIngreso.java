package Practico3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class VentanaIngreso extends JFrame {
    private JComboBox<String> comboExamenes;
    private JTextField txtCedula;
    private JTextField txtCalificacion;
    private JButton btnIngresar;
    private AccesoBD accesoBD;

    public VentanaIngreso() throws ClassNotFoundException {
        setTitle("Ingrese resultados de examen");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Por favor, seleccione el examen:"), gbc);

        comboExamenes = new JComboBox<>();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(comboExamenes, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Cédula:"), gbc);

        txtCedula = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        add(txtCedula, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Calificación:"), gbc);

        txtCalificacion = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(txtCalificacion, gbc);
        gbc.gridwidth = 1;

        btnIngresar = new JButton("Ingresar Resultado");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.CENTER;
        add(btnIngresar, gbc);

        txtCedula.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        txtCalificacion.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        accesoBD = new AccesoBD();
        cargarExamenes();
        btnIngresar.addActionListener(new IngresarResultadoListener());

        setVisible(true);
    }

    private void cargarExamenes() throws ClassNotFoundException {
        try {
            ConexionBD conexionBD = new ConexionBD();
            Connection con = conexionBD.getConnection();

            List<Examen> examenes = accesoBD.listarExamenes(con);
            for (Examen examen : examenes) {
                comboExamenes.addItem(examen.getCodigo() + " - " + examen.getMateria() + " - " + examen.getPeriodo());
            }

            conexionBD.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private class IngresarResultadoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String cedulaText = txtCedula.getText().trim();
                String calificacionText = txtCalificacion.getText().trim();

                if (!cedulaText.matches("\\d+") || !calificacionText.matches("\\d+")) {
                    JOptionPane.showMessageDialog(VentanaIngreso.this, "Cédula o calificación inválida.");
                    return;
                }

                int cedula = Integer.parseInt(cedulaText);
                int calificacion = Integer.parseInt(calificacionText);

                if (calificacion < 1 || calificacion > 12) {
                    JOptionPane.showMessageDialog(VentanaIngreso.this, "La calificación debe estar entre 1 y 12.");
                    return;
                }

                String examenSeleccionado = (String) comboExamenes.getSelectedItem();
                if (examenSeleccionado == null) {
                    JOptionPane.showMessageDialog(VentanaIngreso.this, "Debe seleccionar un examen.");
                    return;
                }

                String codigoExamen = examenSeleccionado.split(" - ")[0];

                Resultado resultado = new Resultado(cedula, codigoExamen, calificacion);

                ConexionBD conexionBD = new ConexionBD();
                Connection con = conexionBD.getConnection();

                accesoBD.ingresarResultado(con, resultado);

                JOptionPane.showMessageDialog(VentanaIngreso.this, "Resultado ingresado exitosamente.");
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(VentanaIngreso.this, "Error al ingresar el resultado.");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new VentanaIngreso();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }
}
