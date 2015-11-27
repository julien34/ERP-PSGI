package achat.popup;

import java.awt.Component;
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
		
		//On créer un panel
		JPanel panelAjoutFournisseur = new JPanel();
		
		//On ajoute les label
		JLabel lblNom = new JLabel("Nom : ");
		JLabel lblSiret = new JLabel("Siret : ");
		JLabel lblTel = new JLabel("Tél : ");
		JLabel lblAdresse = new JLabel("Adresse : ");
		
		//On ajoute les JTextField
		JTextField txtNom = new JTextField(10);
		JTextField txtSiret = new JTextField(10);
		JTextField txtTel = new JTextField(10);
		JTextField txtAdresse = new JTextField(10);
		
		//on ajoute les boutons OK et annuler
		JButton btnValider = new JButton("Valider");
		JButton btnAnnuler = new JButton("Annuler");
		
		//On ajoute tout les components au panel :
		panelAjoutFournisseur.add(lblNom);
		panelAjoutFournisseur.add(txtNom);
		
		panelAjoutFournisseur.add(lblSiret);
		panelAjoutFournisseur.add(txtSiret);
		
		panelAjoutFournisseur.add(lblTel);
		panelAjoutFournisseur.add(txtTel);
		
		panelAjoutFournisseur.add(lblAdresse);
		panelAjoutFournisseur.add(txtAdresse);
		
		panelAjoutFournisseur.add(btnValider);
		panelAjoutFournisseur.add(btnAnnuler);
		
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
					
					//On cherche quel est la référence du fn ajouté
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
