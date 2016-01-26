package production;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import jdbc.DatabaseConnection;

import jdbc.DatabaseConnection;
import principal.FenetrePrincipale;

public class PanelRecherche extends JPanel {
	
	
	private JScrollPane scrollPane;
	
	
	private static JTextField txtRecherche, txtRecherche1;
	private static JLabel lblRecherche, lblRecherche1;
	
	private static JButton button = new JButton("Valider"), button1 = new JButton("Valider");
	
	private static JTable table;
	
	private final static String[] columnNames = {"code Produit","description","Categorie","prix de vente","prix d'achat","udm","Achat Vente"};
	private JPanel pan = new JPanel(new BorderLayout(20,20));
	private JPanel panRecherche = new JPanel(new GridLayout(2,1)) ;
	private static DefaultTableModel model = new DefaultTableModel(0,7){
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
	
	
	public void fillTable()
	{
		model.setDataVector(DatabaseConnection.remplirListeProduits(), columnNames);
		table.getRowSorter().toggleSortOrder(0);
	}
	
	public static void rechercherNomProduits(){
		
		String recherche;
		recherche =	txtRecherche.getText();
		model.setDataVector(DatabaseConnection.rechercherNomProduits(recherche), columnNames);
		table.getRowSorter().toggleSortOrder(0);
		}
public static void rechercherCatProduits(){
		
		String recherche;
		recherche =	String.valueOf(txtRecherche1.getText());
		model.setDataVector(DatabaseConnection.rechercherNomProduits(recherche), columnNames);
		table.getRowSorter().toggleSortOrder(0);
		}
	
	
	
	public PanelRecherche(){
		
		
		
		
		PanelRecherche.lblRecherche = new JLabel("Rechercher par nom de produits: ");
		txtRecherche = new JTextField(20);
		
		
		
		PanelRecherche.lblRecherche1 = new JLabel("Rechercher par catégorie de produits:");
		txtRecherche1 = new JTextField(20);
		
		//Remplir, configurer et créer la table
				table = new JTable(model);
				table.setAutoCreateRowSorter(true);
				table.getRowSorter().toggleSortOrder(0);
				table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				scrollPane = new JScrollPane(table);
				table.setPreferredScrollableViewportSize(new Dimension(540, 224));
				
				//Ajout des éléments
				add(pan);
				pan.add("Center",scrollPane);
				pan.add("North",panRecherche);
				
				panRecherche.add("North",lblRecherche);
				panRecherche.add("North",txtRecherche);
				panRecherche.add("North",button);
				button.setSize(140,26);
				panRecherche.add("North",lblRecherche1);
				panRecherche.add("North",txtRecherche1);
				panRecherche.add("North",button1);
				
				
				
				button.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						rechercherNomProduits();
						
					}
				});
				button1.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						rechercherCatProduits();
						
					}
				});
				
				setVisible(true);
		
	
	}

	
	


}