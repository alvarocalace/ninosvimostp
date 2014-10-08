package gui;
import implementacion.Material;
import implementacion.Proveedor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import controlador.Controlador;

public class VentanaBajaProveedor extends javax.swing.JFrame implements ActionListener{
	private JComboBox proveedores;
	private JButton eliminar;
	private JLabel cuit;

	public static void main(String[] args) {
		new VentanaBajaProveedor();
	}
	
	public VentanaBajaProveedor() {
		comportamiento();
		componentes();
	}
	
	private void comportamiento(){
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Baja Proveedor");		
		this.setSize(335, 117);
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
				proveedores.setBounds(70, 12, 243, 24);	
				for (Proveedor p : Controlador.getControlador().getProveedores())
					proveedores.addItem(p.getCuit());
				proveedores.setSelectedIndex(-1);
			}
			{
				cuit = new JLabel();
				getContentPane().add(cuit);
				cuit.setText("CUIT:");
				cuit.setBounds(12, 12, 54, 24);
			}
			{
				eliminar = new JButton();
				getContentPane().add(eliminar);
				eliminar.setText("Eliminar");
				eliminar.setBounds(222, 48, 91, 24);
				eliminar.addActionListener(this);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==eliminar){
			if (proveedores.getSelectedItem()!=null){
				if (Controlador.getControlador().obtenerProveedor(proveedores.getSelectedItem().toString()).isActivo()==true)
					Controlador.getControlador().eliminarProveedor(proveedores.getSelectedItem().toString());
				//proveedores.removeItemAt(proveedores.getSelectedIndex());
				else
					JOptionPane.showMessageDialog(this.getComponent(0),"El proveedor seleccionado ya est� inactivo.","Error",JOptionPane.ERROR_MESSAGE);
			}
			else
				JOptionPane.showMessageDialog(this.getComponent(0),"Por favor elija un proveedor a eliminar.","Error",JOptionPane.ERROR_MESSAGE);
		}
	}
}
