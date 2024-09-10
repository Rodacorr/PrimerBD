package Practico3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

//USAR VENTANA INGRESO 1 ES MAS LINDA
public class VentanaIngreso extends JFrame {
    private JComboBox<String> comboExamenes;
    private JTextField txtCedula;
    private JTextField txtCalificacion;
    private JButton btnIngresar;
    private AccesoBD accesoBD;

    public VentanaIngreso() throws ClassNotFoundException {
        setTitle("Ingrese resultados de examen");
        setSize(400, 200); // Tamaño ajustado
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null); // Usamos null layout para posicionamiento absoluto

        // Etiqueta y JComboBox para seleccionar el examen
        JLabel lblExamen = new JLabel("Por favor, seleccione el examen:");
        lblExamen.setBounds(10, 10, 180, 25);
        add(lblExamen);

        comboExamenes = new JComboBox<>();
        comboExamenes.setBounds(200, 10, 180, 25);
        add(comboExamenes);

        // Etiqueta y campo para la cédula
        JLabel lblCedula = new JLabel("Cédula:");
        lblCedula.setBounds(10, 50, 80, 25);
        add(lblCedula);

        txtCedula = new JTextField();
        txtCedula.setBounds(200, 50, 180, 25);
        add(txtCedula);

        // Etiqueta y campo para la calificación
        JLabel lblCalificacion = new JLabel("Calificación:");
        lblCalificacion.setBounds(10, 90, 80, 25);
        add(lblCalificacion);

        txtCalificacion = new JTextField();
        txtCalificacion.setBounds(200, 90, 180, 25);
        add(txtCalificacion);

        // Botón de ingresar resultado
        btnIngresar = new JButton("Ingresar Resultado");
        btnIngresar.setBounds(120, 130, 160, 25);
        add(btnIngresar);

        // Bordes para los campos de texto, similar a la imagen
        txtCedula.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        txtCalificacion.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        accesoBD = new AccesoBD();
        cargarExamenes(); // Cargar la lista de exámenes
        btnIngresar.addActionListener(new IngresarResultadoListener());

        setVisible(true);
    }

    // Método para cargar los exámenes en el JComboBox
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

    // Listener para el botón de ingresar resultado
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

    // Método main para ejecutar la ventana
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
