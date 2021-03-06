package persistencia;

import implementacion.Factura;
import implementacion.ItemFactura;
import implementacion.Prenda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class AdministradorPersistenciaFactura extends AdministradorPersistencia {
	
	private static AdministradorPersistenciaFactura instancia;
	
	private AdministradorPersistenciaFactura() {
		
	}
	
	public static AdministradorPersistenciaFactura getInstancia() {
		if (instancia == null) {
			instancia = new AdministradorPersistenciaFactura();
		}
		return instancia;
	}
	
	public void insert(Object o) {
		Factura f = (Factura)o;
		try{
			Connection con = Conexion.connect();
			PreparedStatement ps = con.prepareStatement("INSERT INTO "+super.getDatabase()+".dbo.Facturas (nro_factura) VALUES (?)");
			ps.setInt(1, f.getNumeroFactura());
			
			ps.execute();
			
			for (ItemFactura ip : f.getPrendas()){
				ps = con.prepareStatement("INSERT INTO "+super.getDatabase()+".dbo.Facturas_Prendas (nro_factura, codigo_prenda, cantidad) VALUES (?,?,?)");
				ps.setInt(1, f.getNumeroFactura());
				ps.setString(2, ip.getPrenda().getCodigo());
				ps.setFloat(3, ip.getCantidad());
				
				ps.execute();
			}			
			con.close();
		}
		catch (SQLException e){
		}
	}

	public void update(Object o) {
		
	}

	public void delete(Object o) {

	}
	
	public Collection<Factura> obtenerFacturas(){
		Collection<Factura> facturas = new ArrayList<Factura>();
		int contador = 1;
		try{
			Connection con = Conexion.connect();
			PreparedStatement ps = con.prepareStatement("SELECT * FROM "+super.getDatabase()+".dbo.Facturas");
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()){
				Factura f = new Factura();
				f.setNumeroFactura(rs.getInt("nro_factura"));
				Factura.setContador(++contador);
				f.setConfirmada(true);
				
				Collection<ItemFactura> itemsPrenda = new ArrayList<ItemFactura>();
				ps = con.prepareStatement("SELECT * FROM "+super.getDatabase()+".dbo.Facturas_Prendas WHERE nro_factura = ?");
				ps.setInt(1, rs.getInt("nro_factura"));
				ResultSet rsPrendas = ps.executeQuery();
				while (rsPrendas.next()){
					Prenda p = Prenda.buscarPrendaFactura(rsPrendas.getString("codigo_prenda"));
					itemsPrenda.add(new ItemFactura(p,rsPrendas.getFloat("cantidad")));
				}
				
				f.setPrendas(itemsPrenda);
				facturas.add(f);
			}			
			
			con.close();					
		}
		catch (SQLException e){
		}
		return facturas;
	}
	
	public Factura obtenerFactura(int nroFactura) {
		Factura factura = null;
		try {
			Connection con = Conexion.connect();
			PreparedStatement ps = con.prepareStatement("SELECT * FROM "+super.getDatabase()+".dbo.Facturas WHERE nro_factura = ?");
			ps.setInt(1, nroFactura);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()){
				factura = new Factura();
				factura.setNumeroFactura(nroFactura);
				
				Collection<ItemFactura> itemsPrenda = new ArrayList<ItemFactura>();
				ps = con.prepareStatement("SELECT * FROM "+super.getDatabase()+".dbo.Facturas_Prendas WHERE nro_factura = ?");
				ps.setInt(1, nroFactura);
				ResultSet rsPrendas = ps.executeQuery();
				while (rsPrendas.next()){
					Prenda p = Prenda.buscarPrendaFactura(rsPrendas.getString("codigo_prenda"));
					itemsPrenda.add(new ItemFactura(p,rsPrendas.getFloat("cantidad")));
				}
				factura.setPrendas(itemsPrenda);
			}
		} catch (SQLException e) {
		}
		return factura;
	}

}
