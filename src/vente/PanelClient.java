package vente;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import jdbc.DatabaseConnection;
import principal.FenetrePrincipale;
import production.AjouterProduits;
import production.ModifierProduits;
import vente.ModifierClients;

/**
 * 
 * @author Simon
 * Classes qui genere l'interface 
 */
public class PanelClient extends JPanel{
	
	
	private static FenetrePrincipale framePrincipale;
	
	private JPanel contenu = new JPanel(new BorderLayout(10,10));
	private JPanel panelBouttons = new JPanel(new FlowLayout(FlowLayout.CENTER,40,0));
	private JPanel panelRecherche;

	private JButton bt_ajouter = new JButton("Ajouter");
	private JButton bt_modifier = new JButton("Modifier");
	private JButton bt_supprimer = new JButton("Supprimer");
	
	private JButton bt_rech= new JButton("Rechercher");
	
	private JTextField txtRecherche;
	private JLabel lblRecherche;
	
	private JPanel panelGrid = new JPanel();
	
	private int clientChoisi = -1;
	//private AjouterClient ajouter_p = new AjouterClient();
	//private ModifierClients modifier_p = new ModifierClients(null);
	
	/**
	 * GridLayout qui contient les informations des Clients
	 */
	private static JTable tableClient;
	
	private JScrollPane scrollPane;
	private final static String[] nomColonnes = {"Id","Nom","Prenom","Adresseclient","Emailclient","Telehponeclient","codeCategorieclient"};
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
	
	public static void remplirtableClient(){
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
		
		this.panelGrid = new JPanel(new GridLayout(2,1));
		
		this.panelRecherche = new JPanel();
		lblRecherche = new JLabel("Rechercher : ");
		txtRecherche = new JTextField(20);
		
		add(panelRecherche);
		
		add(panelGrid);
		
		add(contenu);
		contenu.add("North",panelRecherche);
		contenu.add("Center",scrollPane);
		contenu.add("South",panelBouttons);
		
		
		//add(panelRecherche);
		
		panelBouttons.add(bt_ajouter);
		bt_ajouter.setPreferredSize(dimensionBouttons);
		panelBouttons.add(bt_modifier);
		bt_modifier.setPreferredSize(dimensionBouttons);
		panelBouttons.add(bt_supprimer);
		bt_supprimer.setPreferredSize(dimensionBouttons);
		
		
		
		panelRecherche.add(lblRecherche);
		panelRecherche.add(txtRecherche);
		panelRecherche.add(bt_rech);
	
		panelGrid.add(contenu);
	}
	
	public void initHandlers(){
		
		tableClient.getSelectionModel().addListSelectionListener(new ListSelectionListener() 
		{
			public void valueChanged(ListSelectionEvent e) 
		    {
				if (e.getValueIsAdjusting()) return;			        
		        ListSelectionModel selection = (ListSelectionModel) e.getSource();
		        clientChoisi = selection.getMinSelectionIndex();
		        
		        //Désactiver certains boutons si on ne selectionne aucune ligne
		        bt_modifier.setEnabled(!selection.isSelectionEmpty());
		        bt_supprimer.setEnabled(!selection.isSelectionEmpty());
		    }
		});
		
		bt_ajouter.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
			new AjouterClient(framePrincipale);
			}
		});
		
		bt_modifier.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{ 
			    String id = String.valueOf(modelTableClient.getValueAt(tableClient.convertRowIndexToModel(clientChoisi), 0));
			    String nom = String.valueOf(modelTableClient.getValueAt(tableClient.convertRowIndexToModel(clientChoisi), 1));
			    String prenom = String.valueOf(modelTableClient.getValueAt(tableClient.convertRowIndexToModel(clientChoisi), 2));
			    String adresse = String.valueOf(modelTableClient.getValueAt(tableClient.convertRowIndexToModel(clientChoisi), 3));
			    String mail = String.valueOf(modelTableClient.getValueAt(tableClient.convertRowIndexToModel(clientChoisi), 4));
			    String tel = String.valueOf(modelTableClient.getValueAt(tableClient.convertRowIndexToModel(clientChoisi), 5));
			    String categ = String.valueOf(modelTableClient.getValueAt(tableClient.convertRowIndexToModel(clientChoisi), 6));
			    
			    Client client = new Client(id,nom,prenom,adresse,mail,tel,categ);
			    //ModifierClients
				new ModifierClients(framePrincipale, client);
				//revalidate();
			}
		});
		
		bt_supprimer.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int option = JOptionPane.showConfirmDialog(null, "Confirmer la suppression du client ?", "Suppression de client", JOptionPane.YES_NO_OPTION);
				if(option == JOptionPane.YES_OPTION)
				{
					if (clientChoisi != -1)
					{
						String id = String.valueOf(modelTableClient.getValueAt(tableClient.convertRowIndexToModel(clientChoisi), 0));
						Integer idClient = Integer.parseInt(id);
						if (DatabaseConnection.requete("DELETE FROM vente_clients WHERE IDCLIENT = '"+idClient+"'")){
							modelTableClient.removeRow(tableClient.convertRowIndexToModel(clientChoisi));
							JOptionPane.showMessageDialog(null, "Client supprimer avec succès.", "Suppression de client", JOptionPane.INFORMATION_MESSAGE);
						}
						else{
							System.out.println("Suppression du client interrompu");
						}
					}
				}
			}
		});	
		
		
	}
	public void refreshListeTableClient(String id, String nom, String prenom, String adresse, String email, String tel, String categorie){
		//int id2 = Integer.parseInt(id);
		//int categ = Integer.parseInt(categorie);
		Object[] obj = {id, nom, prenom, adresse, email, tel, categorie};
		modelTableClient.addRow(obj);
	}
	
	public void raffraichirLigne(String id, String nom, String prenom, String adresse, String email, String tel, String categorie)
	{
		int ligneChoisieTemp = clientChoisi;
		if (clientChoisi != -1)
		{	
			modelTableClient.setValueAt(id, tableClient.convertRowIndexToModel(ligneChoisieTemp), 0);		
			modelTableClient.setValueAt(nom, tableClient.convertRowIndexToModel(ligneChoisieTemp), 1);
			modelTableClient.setValueAt(prenom, tableClient.convertRowIndexToModel(ligneChoisieTemp), 2);
			modelTableClient.setValueAt(adresse, tableClient.convertRowIndexToModel(ligneChoisieTemp), 3);
			modelTableClient.setValueAt(email, tableClient.convertRowIndexToModel(ligneChoisieTemp), 4);
			modelTableClient.setValueAt(tel, tableClient.convertRowIndexToModel(ligneChoisieTemp), 5);
			modelTableClient.setValueAt(categorie, tableClient.convertRowIndexToModel(ligneChoisieTemp), 6);
		}
	}
}

