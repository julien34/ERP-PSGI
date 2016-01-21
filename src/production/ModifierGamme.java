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
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import jdbc.DatabaseConnection;
import principal.FenetrePrincipale;

public class ModifierGamme
extends JPanel {
    private JPanel panelGrid = new JPanel(new GridLayout(5, 1));
    private JPanel panelFlow1 = new JPanel(new FlowLayout(1));
    private JPanel panelFlow2 = new JPanel(new FlowLayout(1));
    private JPanel panelFlow3 = new JPanel(new FlowLayout(1));
    private JPanel panelFlow4 = new JPanel(new FlowLayout(1));
    private String value1;
    private String value2;
    private String value3;
    JButton retour = new JButton("Retour");
    JLabel nomGammeLabel = new JLabel("nom ");
    JTextField nomGamme = new JTextField(10);
    JLabel codeGammeLabel = new JLabel("code ");
    JTextField codeGamme = new JTextField(10);
    JLabel emplacementGammeLabel = new JLabel("emplacement ");
    JTextField emplacementGamme = new JTextField(10);
    JButton modifier = new JButton("Modifier");
    JLabel error = new JLabel("");

    public String notNull(String s2) {
        if (s2 == "null") {
            return "";
        }
        return s2;
    }

    public void setVal(String val1, String val2, String val3) {
        this.value1 = val1;
        this.value1 = this.notNull(this.value1);
        this.value2 = val2;
        this.value2 = this.notNull(this.value2);
        this.value3 = val3;
        this.value3 = this.notNull(this.value3);
        this.nomGamme.setText(value1);
        this.codeGamme.setText(this.value2);
        this.emplacementGamme.setText(this.value3);
    }

    public ModifierGamme() {
        this.initElements();
    }

    public JButton getRetour() {
        return this.retour;
    }

    public void initElements() {
        this.add(this.panelGrid);
        this.panelGrid.add(this.panelFlow1);
        this.panelGrid.add(this.panelFlow2);
        this.panelGrid.add(this.panelFlow3);
        this.panelGrid.add(this.panelFlow4);
        this.panelFlow1.add(this.codeGammeLabel);
        this.panelFlow1.add(this.codeGamme);
        this.panelFlow2.add(this.emplacementGammeLabel);
        this.panelFlow2.add(this.emplacementGamme);
        this.panelFlow3.add(this.modifier);
        this.panelFlow3.add(this.retour);
        this.panelFlow4.add(this.error);
        this.modifier.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e2) {
                String value1 = ModifierGamme.this.codeGamme.getText();
                String value2 = ModifierGamme.this.emplacementGamme.getText();
                String nomGamme =  ModifierGamme.this.nomGamme.getText();
                if (DatabaseConnection.requete("UPDATE GAMME SET codegamme = '" + value1 + "', emplacementgamme = '" + value2 + "' WHERE nomgamme = '" + nomGamme + "'")) {
                    FenetrePrincipale.getPanelGamme().raffraichirLigne(nomGamme, value1, value2);
                    JOptionPane.showMessageDialog(null, "Gamme modifi\u00e9 avec succ\u00e8s.", "Modification de gamme", 1);
                } else {
                    JOptionPane.showMessageDialog(null, "Erreur de modification de la gamme. V\ufffdrifiez vos champs.", "Modification de gamme", 2);
                }
            }
        });
    }

}

