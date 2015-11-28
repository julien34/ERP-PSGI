package achat.popup;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import achat.Fournisseur;
import achat.PanelFournisseur;
import jdbc.DatabaseConnection;

public class PopupAjoutFournisseur extends JDialog{
	
	public PopupAjoutFournisseur(){
		
		//On créer un panel principal et les autres qui iront dedans
		JPanel panelAjoutFournisseur = new JPanel(new GridLayout(5,1,5,5));
		JPanel panelNom = new JPanel();
		JPanel panelSiret = new JPanel();
		JPanel panelTel = new JPanel();
		JPanel panelAdresse = new JPanel();
		JPanel panelBoutons = new JPanel();
		
		
		//On créer les label
		JLabel lblNom = new JLabel("Nom : ");
		JLabel lblSiret = new JLabel("Siret : ");
		JLabel lblTel = new JLabel("Tél : ");
		JLabel lblAdresse = new JLabel("Adresse : ");
		
		//On créer les JTextField
		JTextField txtNom = new JTextField(10);
		JTextField txtSiret = new JTextField(10);
		JTextField txtTel = new JTextField(10);
		JTextField txtAdresse = new JTextField(10);
		
		//on créer les boutons OK et annuler
		JButton btnValider = new JButton("Valider");
		JButton btnAnnuler = new JButton("Annuler");
		
		
		//On ajoute tout les components au panel :
		panelNom.add(lblNom);
		panelNom.add(txtNom);
		
		panelSiret.add(lblSiret);
		panelSiret.add(txtSiret);
		
		panelTel.add(lblTel);
		panelTel.add(txtTel);
		
		panelAdresse.add(lblAdresse);
		panelAdresse.add(txtAdresse);
		
		panelBoutons.add(btnValider);
		panelBoutons.add(btnAnnuler);
		
		//On ajoute les panel au panel en grid
		panelAjoutFournisseur.add(panelNom);
		panelAjoutFournisseur.add(panelSiret);
		panelAjoutFournisseur.add(panelTel);
		panelAjoutFournisseur.add(panelAdresse);
		panelAjoutFournisseur.add(panelBoutons);
		
		this.add(panelAjoutFournisseur);
		
		this.setVisible(true);
		this.setTitle("Ajouter un Fournisseur");
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setSize(500, 300);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		//handler des boutons valider et annuler
		btnValider.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				DatabaseConnection db = new DatabaseConnection();
				Connection cn = db.getCon();
				try {
					PreparedStatement pst = cn.prepareStatement("INSERT INTO Fournisseurs(refFournisseur, nomFournisseur, siret, telFournisseur, adresseFournisseur) Values(seqRefFournisseur.NEXTVAL,?,?,?,?)");
					
					pst.setString(1, txtNom.getText());
					pst.setString(2, txtSiret.getText());
					pst.setString(3, txtTel.getText());
					pst.setString(4, txtAdresse.getText());
					
					pst.executeQuery();
					
					//On cherche quelle est la référence du fn ajouté
					DatabaseConnection db1 = new DatabaseConnection();
					cn = db1.getCon();
					PreparedStatement pst2 = cn.prepareStatement("SELECT * FROM Fournisseurs WHERE nomFournisseur = ? AND siret = ? AND telFournisseur = ? AND adresseFournisseur = ?");
					
					pst2.setString(1, txtNom.getText());
					pst2.setString(2, txtSiret.getText());
					pst2.setString(3, txtTel.getText());
					pst2.setString(4, txtAdresse.getText());
					
					ResultSet rs = pst2.executeQuery();
					
					//On trouve la refFournisseur qui a été attribué
					String ref = null;
					while(rs.next()){
						ref = rs.getString("refFournisseur");
					}
					
					//on créer un nouveau fournisseur pour l'ajouter à la liste
					Fournisseur f = new Fournisseur(ref,txtNom.getText(),txtSiret.getText(),txtTel.getText(),txtAdresse.getText());
					
					PanelFournisseur.majTableau(f);
					
					Window window = SwingUtilities.windowForComponent((Component)e.getSource());
					window.dispose();
					
					//On affiche un message de validation
					JOptionPane.showMessageDialog(null, "Fournisseur ajouté avec succès","Vous venez d'ajouter le fournisseur "+txtNom.getText()+".",JOptionPane.INFORMATION_MESSAGE);

					
				} catch(SQLException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Erreur dans la saisie des informations", "Ajout de fournisseur", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		btnAnnuler.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Window window = SwingUtilities.windowForComponent((Component)e.getSource());
				window.dispose();
				
			}
		});
	}
}
