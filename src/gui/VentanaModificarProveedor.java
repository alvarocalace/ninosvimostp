package gui;
import implementacion.Proveedor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import view.ProveedorView;
import controlador.Controlador;

@SuppressWarnings("serial")
public class VentanaModificarProveedor extends javax.swing.JFrame implements ActionListener{
	private JComboBox proveedores;
	private JLabel nombre;
	private JTextField tNombre;
	private JLabel cuit;
	private JButton modificar;
	private JCheckBox activo;

	public static void main(String[] args) {
		new VentanaModificarProveedor();
	}
	
	public VentanaModificarProveedor() {
		comportamiento();
		componentes();
	}
	
	private void comportamiento(){
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Modificar Proveedor");		
		setSize(363, 150);
		setLocationRelativeTo(null);
		this.setVisible(true);
		this.setResizable(false);
		getContentPane().setLayout(null);
	}
	
	private void componentes() {
		try {
			{

				proveedores = new JComboBox();
				getContentPane().add(proveedores);
				proveedores.setBounds(78, 12, 265, 24);
				for (Proveedor p : Controlador.getControlador().getProveedores())
					proveedores.addItem(p.getCuit());
				proveedores.setSelectedIndex(-1);
				proveedores.addActionListener(this);
			}
			{
				modificar = new JButton();
				getContentPane().add(modificar);
				modificar.setText("Modificar");
				modificar.setBounds(253, 81, 90, 24);
				modificar.addActionListener(this);
			}
			{
				nombre = new JLabel();
				getContentPane().add(nombre);
				nombre.setText("Nombre:");
				nombre.setBounds(12, 48, 54, 17);
			}
			{
				activo = new JCheckBox();
				getContentPane().add(activo);
				activo.setText("Activo");
				activo.setBounds(12, 81, 80, 17);
			}
			{
				tNombre = new JTextField();
				getContentPane().add(tNombre);
				tNombre.setBounds(78, 45, 265, 24);
			}
			{
				cuit = new JLabel();
				getContentPane().add(cuit);
				cuit.setText("CUIT:");
				cuit.setBounds(12, 16, 35, 17);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==proveedores){
			if (proveedores.getSelectedItem()!=null){
				ProveedorView proveedorView = Controlador.getControlador().obtenerProveedorView(proveedores.getSelectedItem().toString());
				tNombre.setText(proveedorView.getNombre());
				activo.setSelected(proveedorView.isActivo());
			}
		}
		if (e.getSource()==modificar){
			if (proveedores.getSelectedItem()!=null && !tNombre.getText().equals("")){
				ProveedorView proveedorView = new ProveedorView(tNombre.getText(), proveedores.getSelectedItem().toString(), activo.isSelected());
				Controlador.getControlador().modificarProveedor(proveedorView);
			}
			else 					
				JOptionPane.showMessageDialog(this.getComponent(0), "Por favor seleccione un proveedor y complete correctamente los campos.","Error",JOptionPane.ERROR_MESSAGE);
		}
		
	}
}

