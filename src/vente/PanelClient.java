package vente;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import principal.FenetrePrincipale;

/**
 * 
 * @author Simon
 * Classes qui genere l'interface 
 */
public class PanelClient extends JPanel{
	
	
	private static FenetrePrincipale framePrincipale;
	
	private JPanel contenu = new JPanel(new BorderLayout(10,10));
	private JPanel panelBouttons = new JPanel(new FlowLayout(FlowLayout.CENTER,40,0));

	private JButton bt_ajouter = new JButton("Ajouter");
	private JButton bt_modifier = new JButton("Modifier");
	private JButton bt_supprimer = new JButton("Supprimer");
	
	/**
	 * GridLayout qui contient les informations des Clients
	 */
	private JTable jtableClient;
	private JScrollPane scrollPane;
	private final String[] nomColonnes = {"Id","Nom","Prenom","Adresse","Email","Telehpone","Categorie"};
	private static DefaultTableModel modelTableClient = new DefaultTableModel(0,7){
		Class[] types = {Integer.class, String.class, String.class,String.class,String.class,String.class,Integer.class};
		
        @Override
        public Class getColumnClass(int columnIndex) 
        {
            return Integer.class;
        }
        
        @Override
        public boolean isCellEditable(int row, int column)
        {  
            return false;  
        }
	};
	private Dimension dimensionBouttons = new Dimension(140,26);
	private Dimension dimensionTable = new Dimension(540, 224);
	
	public void remplirJTableClient(){
		
	}
	
	public PanelClient(FenetrePrincipale framePrincipale){
		this.framePrincipale = framePrincipale;
		init();
	}
	
	private void init() {
		jtableClient = new JTable(modelTableClient);
		jtableClient.setAutoCreateRowSorter(true); //permet de trier les colonnes
		jtableClient.getRowSorter().toggleSortOrder(0);
		jtableClient.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane = new JScrollPane(jtableClient);
		jtableClient.setPreferredScrollableViewportSize(dimensionTable);
		
		add(contenu);
		contenu.add(scrollPane);
		contenu.add(panelBouttons);
		
		panelBouttons.add(bt_ajouter);
		bt_ajouter.setPreferredSize(dimensionBouttons);
		panelBouttons.add(bt_modifier);
		bt_modifier.setPreferredSize(dimensionBouttons);
		panelBouttons.add(bt_supprimer);
		bt_supprimer.setPreferredSize(dimensionBouttons);
		
		bt_ajouter.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
			new AjouterClient(framePrincipale);
			}
		});
	}
}
