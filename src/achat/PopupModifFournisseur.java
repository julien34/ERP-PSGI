package achat;

import java.awt.Component;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import jdbc.DatabaseConnection;

public class PopupModifFournisseur extends JDialog{
	private Fournisseur founisseur;
	
	private PreparedStatement pst;
	private Connection cn;
	
	
	public PopupModifFournisseur(Fournisseur f){
		this.founisseur = f;
		
		//On creer un panel :
		JPanel panelModifFournisseur = new JPanel();

		//On créer des champs de texte :
		JTextField txtNom = new JTextField();
		JTextField txtSiret = new JTextField();
		JTextField txtTel = new JTextField();
		JTextField txtAdresse = new JTextField();
		
		//On créer des labels :
		JLabel lblNom = new JLabel("Nom : ");
		JLabel lblSiret = new JLabel("Siret : ");
		JLabel lblTel = new JLabel("Tel : ");
		JLabel lblAdresse = new JLabel("Adresse : ");
		
		//on creer les boutons :
		JButton btnValider = new JButton("Valider");
		JButton btnAnnuler = new JButton("Annuler");
		
		panelModifFournisseur.add(lblNom);
		panelModifFournisseur.add(txtNom);
		
		panelModifFournisseur.add(lblSiret);
		panelModifFournisseur.add(txtSiret);
		
		panelModifFournisseur.add(lblTel);
		panelModifFournisseur.add(txtTel);
		
		panelModifFournisseur.add(lblAdresse);
		panelModifFournisseur.add(txtAdresse);
		
		panelModifFournisseur.add(btnValider);
		panelModifFournisseur.add(btnAnnuler);
		
		txtNom.setText(f.nom);
		txtSiret.setText(f.siret);
		txtTel.setText(f.tel);
		txtAdresse.setText(f.adresse);
		
		this.add(panelModifFournisseur);
		
		this.setVisible(true);
		this.setTitle("Modifier un Fournisseur");
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setAlwaysOnTop(true);
		this.setSize(500, 300);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		
		//Handler valider ou annuler :
		btnValider.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				DatabaseConnection db = new DatabaseConnection();
				cn = db.getCon();
				try {
					pst = cn.prepareStatement("UPDATE Fournisseurs SET nomFournisseur = ?, siret = ?, telFournisseur = ?, adresseFournisseur = ? WHERE refFournisseur = ?");
					pst.setString(1, txtNom.getText());
					pst.setString(2, txtSiret.getText());
					pst.setString(3, txtTel.getText());
					pst.setString(4, txtAdresse.getText());
					pst.setString(5, PopupModifFournisseur.this.founisseur.ref);
					
					pst.executeQuery();
					
					//On ferme la JDialog
					Window window = SwingUtilities.windowForComponent((Component)e.getSource());
					window.dispose();
					
					JOptionPane.showMessageDialog(null, "Fournisseur modifié avec succès.", "Modification de fournisseur", JOptionPane.INFORMATION_MESSAGE);
					
					//PanelFournisseur.this.doLayout();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}	
			}
		});
	}
	
	public static void main(String[] args){
		Fournisseur test = new Fournisseur("01","Julien","SDFGH","dshbfs","jdhfjd");
		PopupModifFournisseur pop = new PopupModifFournisseur(test);
		pop.setVisible(true);
	}
	
}
