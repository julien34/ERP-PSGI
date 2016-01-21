/*
 * Decompiled with CFR 0_110.
 */
package production;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import jdbc.DatabaseConnection;
import principal.FenetrePrincipale;

public class AjouterGamme
extends JPanel {
    private JPanel panelGrid = new JPanel(new GridLayout(5, 1));
    private JPanel panelFlow1 = new JPanel(new FlowLayout(1));
    private JPanel panelFlow2 = new JPanel(new FlowLayout(1));
    private JPanel panelFlow3 = new JPanel(new FlowLayout(1));
    private JPanel panelFlow4 = new JPanel(new FlowLayout(1));
    JButton retour = new JButton("Retour");

    
    JLabel emplacementLabel = new JLabel("emplacement ");
    DefaultComboBoxModel<String> modelEmplacement = new DefaultComboBoxModel<String>();
    JComboBox<String> emplacement = new JComboBox<String>(modelEmplacement);
    
    public void remplirEmplacement()
    {
    	DatabaseConnection.getEmplacement(modelEmplacement);
    }
    
    public AjouterGamme() {
        this.initElements();
    }

    public JButton getRetour() {
        return this.retour;
    }

    public void initElements() {
        JLabel nomGammeLabel = new JLabel("nom ");
        final JTextField nomGamme = new JTextField(10);
        JLabel codeGameLabel = new JLabel("code ");
        final JTextField codeGamme = new JTextField(10);
        JLabel required = new JLabel("<html><font color='red'>* </font>champs obligatoire</html> ");
        JButton ajouter = new JButton("Ajouter");
        JLabel error = new JLabel("");
        this.add(this.panelGrid);
        this.panelGrid.add(this.panelFlow1);
        this.panelGrid.add(this.panelFlow2);
        this.panelGrid.add(this.panelFlow3);
        this.panelGrid.add(this.panelFlow4);
        this.panelFlow1.add(nomGammeLabel);
        this.panelFlow1.add(nomGamme);
        this.panelFlow1.add(codeGameLabel);
        this.panelFlow1.add(codeGamme);
        this.panelFlow2.add(emplacementLabel);
        this.panelFlow2.add(emplacement);
        this.panelFlow3.add(ajouter);
        this.panelFlow3.add(this.retour);
        ajouter.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e2) {
                String value1 = nomGamme.getText();
                String value2 = codeGamme.getText();
                String value3 = emplacement.getSelectedItem().toString();
                if (DatabaseConnection.requete("INSERT INTO GAMME VALUES('" + value1 + "', '" + value2 + "', '" + value3 + "')")) {
                    FenetrePrincipale.getPanelGamme().raffraichirListe(value1, value2, value3);
                    JOptionPane.showMessageDialog(null, "gamme ajout\ufffd avec succ\ufffds.", "Ajout de gamme", 1);
                } else {
                    JOptionPane.showMessageDialog(null, "Erreur d'ajout de gamme. V\ufffdrifiez les champs.", "Ajout de gamme", 2);
                }
            }
        });
    }

}

