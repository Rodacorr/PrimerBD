package Practico3;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class VentanaResultados {

	private JFrame frame;
	private JTextField txtCedula;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaResultados window = new VentanaResultados();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public VentanaResultados() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		txtCedula = new JTextField();
		txtCedula.setBounds(66, 29, 86, 20);
		frame.getContentPane().add(txtCedula);
		txtCedula.setColumns(10);
		
		JLabel lblCedula = new JLabel("Cedula: ");
		lblCedula.setBounds(10, 32, 46, 14);
		frame.getContentPane().add(lblCedula);
		
		
		JLabel lblResuEstudiante = new JLabel("Resultados del estudiante: ");
		lblResuEstudiante.setBounds(195, 32, 197, 14);
		frame.getContentPane().add(lblResuEstudiante);
		
		table = new JTable();
		table.setBounds(195, 71, 229, 179);
		frame.getContentPane().add(table);
		
		DefaultTableModel tableModel = new DefaultTableModel();
		tableModel.addColumn("Cedula");
		tableModel.addColumn("Codigo");
		tableModel.addColumn("Calificacion");
		table.setModel(tableModel);
	
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int Cedu = Integer.parseInt(txtCedula.getText().trim());
				
				ConexionBD conexionBD;
				try {
					conexionBD = new ConexionBD();
					Connection con = conexionBD.getConnection();
			            
		            AccesoBD accesoBD = new AccesoBD();
		    
		            List<Resultado> Resu = accesoBD.listarResultados(con, Cedu);
		            //Resu = accesoBD.listarResultados(con, Cedu);
					
		            for (Resultado r1 : Resu) {

		            	Object [] datos= new Object[3];
						datos[0] = Cedu;
						datos[1] =  r1.getCodigo();
						datos[2] = r1.getCalificacion();
						tableModel.addRow(datos);
		            }			
					con.close();
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	           
			}
		});
		
		btnBuscar.setBounds(25, 82, 89, 23);
		frame.getContentPane().add(btnBuscar);
		
		JLabel lblNewLabel = new JLabel("Cedula");
		lblNewLabel.setBounds(195, 57, 46, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblCodigo = new JLabel("Codigo");
		lblCodigo.setBounds(273, 57, 46, 14);
		frame.getContentPane().add(lblCodigo);
		
		JLabel lblCalificacion = new JLabel("Calificacion");
		lblCalificacion.setBounds(349, 57, 75, 14);
		frame.getContentPane().add(lblCalificacion);
		
	}
}
