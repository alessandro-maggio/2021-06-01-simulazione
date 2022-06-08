package it.polito.tdp.genes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.genes.model.Genes;


public class GenesDao {
	
	public Map<String, Genes> getAllGenes(){
		
		String sql = "SELECT DISTINCT GeneID, Essential, Chromosome FROM Genes";
		Map<String, Genes> result = new HashMap<String, Genes>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Genes genes = new Genes(res.getString("GeneID"), 
						res.getString("Essential"), 
						res.getInt("Chromosome"));
				result.put( genes.getGeneId(), genes);
			} 
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	public List<Genes> getEssenziali(Map<String, Genes> idMap){
		
		String sql = "select distinct g.`GeneID` as id "
				+ "from genes g "
				+ "where g.`Essential`= 'essential' ";
		
		List<Genes> result = new ArrayList<Genes>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				
				result.add(idMap.get(res.getString("id")));
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public List<Coppia> getArchi(Map<String, Genes> idMap){
		
		
		
		String sql = "select i.`GeneID1` as g1, i.`GeneID2` as g2, i.`Expression_Corr` as peso, g1.`Chromosome` as c1, g2.`Chromosome` as c2 "
				+ "from interactions i, genes g1, genes g2 "
				+ "where i.`GeneID1`= g1.`GeneID` and i.`GeneID2`= g2.`GeneID` and g1.`Essential`=\"essential\" and i.`GeneID1`<> i.`GeneID2` "
				+ "and g2.`Essential`= \"essential\" "
				+ "and g1.`Essential`=\"essential\" and g2.`Essential`= 'essential' "
				+"group by i.`GeneID1`, i.`GeneID2`, i.`Expression_Corr` , g1.`Chromosome` , g2.`Chromosome`";
		
		List<Coppia> result= new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				
				double peso=0;
				
				if(res.getInt("c1") == res.getInt("c2")) {
					peso= Math.abs(res.getDouble("peso"))*2;
				}
				else {
					peso= Math.abs(res.getDouble("peso"));
				}
				
				Coppia coppia= new Coppia(idMap.get(res.getString("g1")), idMap.get(res.getString("g2")), peso);
				result.add(coppia);
				
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		
		
		
	}
	


	
}
