/*
 * Decompiled with CFR 0_110.
 */
package production;

import java.awt.Choice;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import achat.modeles.Categorie;
import jdbc.DatabaseConnection;
import principal.FenetrePrincipale;

public class AjouterOperation extends JPanel {
    private JPanel panelGrid = new JPanel(new GridLayout(5, 1));
    private JPanel panelFlow1 = new JPanel(new FlowLayout(1));
    private JPanel panelFlow2 = new JPanel(new FlowLayout(1));
    private JPanel panelFlow3 = new JPanel(new FlowLayout(1));
    private JPanel panelFlow4 = new JPanel(new FlowLayout(1));
    JButton retour = new JButton("Retour");
    JLabel nomLabel = new JLabel("nom ");
    final JTextField nom = new JTextField(10);
    JLabel sequenceLabel = new JLabel("s\ufffdquence ");
    final JTextField sequence = new JTextField(10);
    JLabel nbCyclesLabel = new JLabel("nombre de cycles ");
    final JTextField nbCycles = new JTextField(10);
    JLabel nbHeuresLabel = new JLabel("nombre d'heures ");
    final JTextField nbHeures = new JTextField(10);
    JLabel required = new JLabel("<html><font color='red'>* </font>champs obligatoire</html> ");
    JButton ajouter = new JButton("Ajouter");
    JLabel error = new JLabel("");
    
    JLabel gammeLabel = new JLabel("gamme ");
    JLabel centreTravailLabel = new JLabel("centre de travail ");
    DefaultComboBoxModel<String> modelGamme = new DefaultComboBoxModel<String>();
    DefaultComboBoxModel<String> modelCentre = new DefaultComboBoxModel<String>();
    JComboBox<String> gamme = new JComboBox<String>(modelGamme);
    JComboBox<String> comboCentre = new JComboBox<String>(modelCentre);
    
    public void remplirCentre()
    {
    	DatabaseConnection.getCentre(modelCentre);
    }
    
    public void remplirGamme()
    {
    	DatabaseConnection.getGamme(modelGamme);
    }
    
    public AjouterOperation() {
        this.initElements();
    }

    public JButton getRetour() {
        return this.retour;
    }

    public void initElements() {
		Dimension tailleGamme = gamme.getPreferredSize();
		tailleGamme.width = 60;
		gamme.setPreferredSize(tailleGamme);
		Dimension tailleCentre = comboCentre.getPreferredSize();
		tailleCentre.width = 60;
		comboCentre.setPreferredSize(tailleCentre);
		
        this.add(this.panelGrid);
        this.panelGrid.add(this.panelFlow1);
        this.panelGrid.add(this.panelFlow2);
        this.panelGrid.add(this.panelFlow3);
        this.panelGrid.add(this.panelFlow4);
        this.panelFlow1.add(nomLabel);
        this.panelFlow1.add(nom);
        this.panelFlow1.add(sequenceLabel);
        this.panelFlow1.add(sequence);
        this.panelFlow2.add(nbCyclesLabel);
        this.panelFlow2.add(nbCycles);
        this.panelFlow2.add(nbHeuresLabel);
        this.panelFlow2.add(nbHeures);
        this.panelFlow2.add(gammeLabel);
        this.panelFlow2.add(gamme);
        this.panelFlow3.add(centreTravailLabel);
        this.panelFlow3.add(comboCentre);
        this.panelFlow4.add(ajouter);
        this.panelFlow4.add(this.retour);
        
        ajouter.addActionListener(new ActionListener(){


        	
            @Override
            public void actionPerformed(ActionEvent e2) {
                String value1 = nom.getText();
                String value2 = sequence.getText();
                String value3 = nbCycles.getText();
                String value4 = nbHeures.getText();
                String value5 = gamme.getSelectedItem().toString();
                String value6 = comboCentre.getSelectedItem().toString();
                if (DatabaseConnection.requete("INSERT INTO OPERATIONS VALUES('" + value1 + "', '" + value2 + "', '" + value3 + "', '" + value4 + "', '" + value5 + "', '" + value6 + "')")) {
                    FenetrePrincipale.getPanelOperation().raffraichirListe(value1, value2, value3, value4, value5, value6);
                    JOptionPane.showMessageDialog(null, "Op\ufffdration ajout\ufffd avec succ\ufffds.", "Ajout d'op\ufffdration", 1);
                } else {
                    JOptionPane.showMessageDialog(null, "Erreur d'ajout de l'op\ufffdration. V\ufffdrifiez les champs.", "Ajout d'op\ufffdration", 2);
                }
            }
        });
    }

}

