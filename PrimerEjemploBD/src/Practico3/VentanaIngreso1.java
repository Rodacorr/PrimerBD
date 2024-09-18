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
    private JList<String> listExamenes;  
    private DefaultListModel<String> listModel; 

    public VentanaIngreso1() throws ClassNotFoundException {
        setTitle("Ingrese resultados de examen");
        setSize(590, 198); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null); 

        JLabel lblExamen = new JLabel("Por favor, seleccione el examen:");
        lblExamen.setBounds(10, 10, 180, 25);
        getContentPane().add(lblExamen);

        listModel = new DefaultListModel<>();

        listExamenes = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(listExamenes);
        scrollPane.setBounds(10, 41, 335, 107);
        getContentPane().add(scrollPane);

        JLabel lblCedula = new JLabel("Cédula:");
        lblCedula.setBounds(366, 38, 80, 25);
        getContentPane().add(lblCedula);

        txtCedula = new JTextField();
        txtCedula.setBounds(445, 36, 119, 29);
        getContentPane().add(txtCedula);

        JLabel lblCalificacion = new JLabel("Calificación:");
        lblCalificacion.setBounds(366, 77, 80, 25);
        getContentPane().add(lblCalificacion);

        txtCalificacion = new JTextField();
        txtCalificacion.setBounds(445, 75, 119, 29);
        getContentPane().add(txtCalificacion);

        btnIngresar = new JButton("Ingresar Resultado");
        btnIngresar.setBounds(385, 123, 160, 25);
        getContentPane().add(btnIngresar);

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
                String examenStr = examen.getCodigo() + " - " + examen.getMateria() + " - " + examen.getPeriodo();
                listModel.addElement(examenStr); 
            }
            
            //Probando listarResutlados
            /*
            AccesoBD acc= new AccesoBD();
            List<Resultado> resu = acc.listarResultados(con, 1234567);
            for(Resultado resultados : resu) {
            	System.out.println(resultados.getCedula());
            	System.out.println(resultados.getCodigo());
            	System.out.println(resultados.getCalificacion());
            }
            */
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
                    JOptionPane.showMessageDialog(VentanaIngreso1.this, "Cédula o calificación inválida.");
                    return;
                }

                int cedula = Integer.parseInt(cedulaText);
                int calificacion = Integer.parseInt(calificacionText);

                if (calificacion < 1 || calificacion > 12) {
                    JOptionPane.showMessageDialog(VentanaIngreso1.this, "La calificación debe estar entre 1 y 12.");
                    return;
                }

                String examenSeleccionado = listExamenes.getSelectedValue();
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

    public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaIngreso1 window = new VentanaIngreso1();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
