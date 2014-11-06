package gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import view.ItemPrendaView;
import view.PrendaView;
import controlador.Controlador;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
@SuppressWarnings("serial")
public class VentanaGenerarFactura extends javax.swing.JFrame implements ActionListener{
	private JLabel lNroFactura;
	private JLabel tNroFactura;
	private JButton buttonConfirmar;
	private JButton buttonAgregar;
	private JSpinner spinnerCantidad;
	private JComboBox comboBoxPrendas;
	private JLabel tPrecio;
	private JLabel lPrecio;
	private DefaultTableModel modelo;
	private JTable tablaPrendas;
	private Collection<ItemPrendaView> itemsPrenda = new ArrayList<ItemPrendaView>();

	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
		new VentanaGenerarFactura();
	}
	
	public VentanaGenerarFactura() {
		comportamiento();
		initGUI();
	}
	
	private void initGUI() {
		try {
			getContentPane().setLayout(null);
			{
				lNroFactura = new JLabel();
				getContentPane().add(lNroFactura);
				lNroFactura.setText("N� factura:");
				lNroFactura.setBounds(12, 12, 68, 17);
			}
			{
				tNroFactura = new JLabel();
				getContentPane().add(tNroFactura);
				tNroFactura.setBounds(92, 12, 288, 17);
				tNroFactura.setBorder(new LineBorder(new java.awt.Color(0,0,0), 1, false));
				tNroFactura.setText(Integer.toString(Controlador.getControlador().getNroFactura()));
			}
			{
				lPrecio = new JLabel();
				getContentPane().add(lPrecio);
				lPrecio.setText("Precio:");
				lPrecio.setBounds(12, 35, 42, 17);
			}
			{
				tPrecio = new JLabel();
				getContentPane().add(tPrecio);
				tPrecio.setBounds(92, 35, 131, 17);
				tPrecio.setBorder(new LineBorder(new java.awt.Color(0,0,0), 1, false));
				tPrecio.setText("$"+0);
			}
			{
				comboBoxPrendas = new JComboBox();
				getContentPane().add(comboBoxPrendas);
				for (PrendaView pv : Controlador.getControlador().getPrendasView())
					comboBoxPrendas.addItem(pv.getCodigo());
				comboBoxPrendas.setBounds(12, 64, 211, 21);
				comboBoxPrendas.setSelectedIndex(-1);
			}
			{
				spinnerCantidad = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));
				getContentPane().add(spinnerCantidad);
				spinnerCantidad.setBounds(229, 63, 50, 24);
			}
			{
				buttonAgregar = new JButton();
				getContentPane().add(buttonAgregar);
				buttonAgregar.setText("Agregar");
				buttonAgregar.setBounds(285, 63, 96, 24);
				buttonAgregar.addActionListener(this);
			}
			{

				modelo = 
						new DefaultTableModel(
								new String[][] {},
								new String[] { "Nombre", "Cantidad", "Precio"});
				tablaPrendas = new JTable(modelo);
				JScrollPane scrollPaneTabla = new JScrollPane(tablaPrendas);
				tablaPrendas.setFillsViewportHeight(true); 
				tablaPrendas.getTableHeader().setReorderingAllowed(false);
				getContentPane().add(scrollPaneTabla);	
				scrollPaneTabla.setBounds(12, 97, 368, 271);
			}
			{
				buttonConfirmar = new JButton();
				getContentPane().add(buttonConfirmar);
				buttonConfirmar.setText("Confirmar");
				buttonConfirmar.setBounds(274, 377, 106, 24);
				buttonConfirmar.addActionListener(this);
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void comportamiento(){
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(400, 445);
		setLocationRelativeTo(null);
		this.setVisible(true);
		this.setResizable(false);
		this.setTitle("Generar Factura");
		
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==buttonAgregar){
			if (!noDuplicados(comboBoxPrendas.getSelectedItem().toString())){
				ItemPrendaView ipv = new ItemPrendaView(Controlador.getControlador().obtenerPrendaView(comboBoxPrendas.getSelectedItem().toString()),(float)(Integer)spinnerCantidad.getValue());
				itemsPrenda.add(ipv);
				Object [] fila = {ipv.getPrenda().getNombre(), ipv.getCantidad(),"$"+ipv.getSubtotal()};
				modelo.addRow (fila);
				tPrecio.setText("$"+ipv.getSubtotal());
			}
			else 
				JOptionPane.showMessageDialog(this.getComponent(0), "No ingrese prendas duplicadas.","Error",JOptionPane.ERROR_MESSAGE);
		}		
		if (e.getSource()==buttonConfirmar){
			if (!itemsPrenda.isEmpty()){
				Controlador.getControlador().generarFactura(itemsPrenda);
				modelo.setRowCount(0);
				tNroFactura.setText(Integer.toString(Controlador.getControlador().getNroFactura()));
				tPrecio.setText("$"+0);
				itemsPrenda = new ArrayList<ItemPrendaView>();
			}
			else
				JOptionPane.showMessageDialog(this.getComponent(0), "Por favor ingrese al menos 1 prenda.","Error",JOptionPane.ERROR_MESSAGE);
		}
	}

	private boolean noDuplicados(String codigoPrenda){
		for (ItemPrendaView ipv : itemsPrenda)
			if (ipv.getPrenda().sosLaPrenda(codigoPrenda))
				return true;
		return false;
	}
}