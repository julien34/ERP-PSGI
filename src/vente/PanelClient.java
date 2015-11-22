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

import jdbc.DatabaseConnection;
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
	private JTable tableClient;
	private JScrollPane scrollPane;
	private final String[] nomColonnes = {"Id","Nom","Prenom","Adresseclient","Emailclient","Telehponeclient","codeCategorieclient"};
	private static DefaultTableModel modelTableClient = new DefaultTableModel(0,7){
		Class[] types = {String.class, String.class, String.class,String.class,String.class,String.class,String.class};
		
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
	
	public void remplirtableClient(){
		modelTableClient.setDataVector(DatabaseConnection.remplirListeClient(), nomColonnes);
		tableClient.getRowSorter().toggleSortOrder(0);
	}
	
	public PanelClient(FenetrePrincipale framePrincipale){
		this.framePrincipale = framePrincipale;
		initElements();
		initHandlers();
	}
	
	private void initElements() {
		tableClient = new JTable(modelTableClient);
		tableClient.setAutoCreateRowSorter(true); //permet de trier les colonnes
		tableClient.getRowSorter().toggleSortOrder(0);
		tableClient.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane = new JScrollPane(tableClient);
		tableClient.setPreferredScrollableViewportSize(dimensionTable);
		
		add(contenu);
		contenu.add("North",scrollPane);
		contenu.add("Center",panelBouttons);
		
		panelBouttons.add(bt_ajouter);
		bt_ajouter.setPreferredSize(dimensionBouttons);
		panelBouttons.add(bt_modifier);
		bt_modifier.setPreferredSize(dimensionBouttons);
		panelBouttons.add(bt_supprimer);
		bt_supprimer.setPreferredSize(dimensionBouttons);
	}
	
	public void initHandlers(){
		bt_ajouter.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
			new AjouterClient(framePrincipale);
			}
		});
	}
	/*public void refreshLigneTableClient(String id, String nom, String prenom, String adresse, String email, String tel, String categorie){
		int id2 = Integer.parseInt(id);
		int categ = Integer.parseInt(categorie);
		
	}*/
	
	public void refreshListeTableClient(String id, String nom, String prenom, String adresse, String email, String tel, String categorie){
		int id2 = Integer.parseInt(id);
		int categ = Integer.parseInt(categorie);
		Object[] obj = {id2, nom, prenom, adresse, email, tel, categ};
		modelTableClient.addRow(obj);
	}
}
