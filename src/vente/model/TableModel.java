package vente.model;

import javax.swing.table.DefaultTableModel;

/**
 * @author Manelli
 * tableau modele
 */
public class TableModel extends DefaultTableModel{
	
	public TableModel(int row, int cols){
		
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
}
