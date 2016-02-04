package achat.modeles;

import javax.swing.table.DefaultTableModel;

public class UneditableTableModel extends DefaultTableModel{
	
	public UneditableTableModel(int row, int cols){
		
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
}
