package Practico3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class VentanaIngreso1 extends JFrame {
    private JTextField txtCedula;
    private JTextField txtCalificacion;
    private JButton btnIngresar;
    private AccesoBD accesoBD;
    private JList<String> listExamenes;  // JList para mostrar los exámenes
    private DefaultListModel<String> listModel; // Modelo de datos para el JList

    public VentanaIngreso1() throws ClassNotFoundException {
        setTitle("Ingrese resultados de examen");
        setSize(590, 198); // Tamaño ajustado
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null); // Usamos null layout para posicionamiento absoluto

        // Etiqueta y JList para seleccionar el examen
        JLabel lblExamen = new JLabel("Por favor, seleccione el examen:");
        lblExamen.setBounds(10, 10, 180, 25);
        getContentPane().add(lblExamen);

        // Modelo de datos para el JList
        listModel = new DefaultListModel<>();
        // JList para mostrar los exámenes
        listExamenes = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(listExamenes);
        scrollPane.setBounds(10, 41, 335, 107);
        getContentPane().add(scrollPane);

        // Etiqueta y campo para la cédula
        JLabel lblCedula = new JLabel("Cédula:");
        lblCedula.setBounds(366, 38, 80, 25);
        getContentPane().add(lblCedula);

        txtCedula = new JTextField();
        txtCedula.setBounds(445, 36, 119, 29);
        getContentPane().add(txtCedula);

        // Etiqueta y campo para la calificación
        JLabel lblCalificacion = new JLabel("Calificación:");
        lblCalificacion.setBounds(366, 77, 80, 25);
        getContentPane().add(lblCalificacion);

        txtCalificacion = new JTextField();
        txtCalificacion.setBounds(445, 75, 119, 29);
        getContentPane().add(txtCalificacion);

        // Botón de ingresar resultado
        btnIngresar = new JButton("Ingresar Resultado");
        btnIngresar.setBounds(385, 123, 160, 25);
        getContentPane().add(btnIngresar);

        // Bordes para los campos de texto, similar a la imagen
        txtCedula.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        txtCalificacion.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        accesoBD = new AccesoBD();
        cargarExamenes(); // Cargar la lista de exámenes
        btnIngresar.addActionListener(new IngresarResultadoListener());

        setVisible(true);
    }

    // Método para cargar los exámenes en el JList
    private void cargarExamenes() throws ClassNotFoundException {
        try {
            ConexionBD conexionBD = new ConexionBD();
            Connection con = conexionBD.getConnection();

            List<Examen> examenes = accesoBD.listarExamenes(con);
            for (Examen examen : examenes) {
                String examenStr = examen.getCodigo() + " - " + examen.getMateria() + " - " + examen.getPeriodo();
                listModel.addElement(examenStr);    // Agregar al modelo de la JList
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
                    JOptionPane.showMessageDialog(VentanaIngreso1.this, "Cédula o calificación inválida.");
                    return;
                }

                int cedula = Integer.parseInt(cedulaText);
                int calificacion = Integer.parseInt(calificacionText);

                if (calificacion < 1 || calificacion > 12) {
                    JOptionPane.showMessageDialog(VentanaIngreso1.this, "La calificación debe estar entre 1 y 12.");
                    return;
                }

                String examenSeleccionado = listExamenes.getSelectedValue();  // Obtener el examen seleccionado del JList
                if (examenSeleccionado == null) {
                    JOptionPane.showMessageDialog(VentanaIngreso1.this, "Debe seleccionar un examen.");
                    return;
                }

                String codigoExamen = examenSeleccionado.split(" - ")[0];

                Resultado resultado = new Resultado(cedula, codigoExamen, calificacion);

                ConexionBD conexionBD = new ConexionBD();
                Connection con = conexionBD.getConnection();

                accesoBD.ingresarResultado(con, resultado);

                JOptionPane.showMessageDialog(VentanaIngreso1.this, "Resultado ingresado exitosamente.");
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(VentanaIngreso1.this, "Error al ingresar el resultado.");
            }
        }
    }

    // Método main para ejecutar la ventana
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new VentanaIngreso1();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }
}
