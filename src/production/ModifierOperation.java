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

public class ModifierOperation
extends JPanel {
    private JPanel panelGrid = new JPanel(new GridLayout(5, 1));
    private JPanel panelFlow1 = new JPanel(new FlowLayout(1));
    private JPanel panelFlow2 = new JPanel(new FlowLayout(1));
    private JPanel panelFlow3 = new JPanel(new FlowLayout(1));
    private JPanel panelFlow4 = new JPanel(new FlowLayout(1));
    private JPanel panelFlow5 = new JPanel(new FlowLayout(1));
    private String value0;
    private String value1;
    private String value2;
    private String value3;
    private String value4;
    private String value5;
    private String value6;
    private String nomOperation;
    JButton retour = new JButton("Retour");
    JLabel nomLabel = new JLabel("nom ");
    JTextField nom = new JTextField(10);
    JLabel sequenceLabel = new JLabel("s\ufffdquence ");
    JTextField sequence = new JTextField(10);
    JLabel nbCyclesLabel = new JLabel("nombre de cycles ");
    JTextField nbCycles = new JTextField(10);
    JLabel nbHeuresLabel = new JLabel("nombre d'heures ");
    JTextField nbHeures = new JTextField(10);
    JLabel gammeLabel = new JLabel("gamme ");
    JTextField gamme = new JTextField(10);
    JLabel centreTravailLabel = new JLabel("centre de travail ");
    JTextField centreTravail = new JTextField(10);
    JButton modifier = new JButton("Modifier");
    JLabel error = new JLabel("");

    public String notNull(String s2) {
        if (s2 == "null") {
            return "";
        }
        return s2;
    }

    public void setVal(String val0, String val1, String val2, String val3, String val4, String val5) {
        this.value0 = val0;
        this.value0 = this.notNull(this.value0);
        this.value1 = val1;
        this.value1 = this.notNull(this.value1);
        this.value2 = val2;
        this.value2 = this.notNull(this.value2);
        this.value3 = val3;
        this.value3 = this.notNull(this.value3);
        this.value4 = val4;
        this.value4 = this.notNull(this.value4);
        this.value5 = val5;
        this.value5 = this.notNull(this.value5);
        this.nom.setText(this.value0);
        this.sequence.setText(this.value1);
        this.nbCycles.setText(this.value2);
        this.nbHeures.setText(this.value3);
        this.gamme.setText(this.value4);
        this.centreTravail.setText(this.value5);
    }

    public ModifierOperation() {
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
        this.panelGrid.add(this.panelFlow5);
        this.panelFlow1.add(this.nomLabel);
        this.panelFlow1.add(this.nom);
        this.panelFlow1.add(this.sequenceLabel);
        this.panelFlow1.add(this.sequence);
        this.panelFlow1.add(this.nbCyclesLabel);
        this.panelFlow1.add(this.nbCycles);
        this.panelFlow2.add(this.nbHeuresLabel);
        this.panelFlow2.add(this.nbHeures);
        this.panelFlow2.add(this.gammeLabel);
        this.panelFlow2.add(this.gamme);
        this.panelFlow3.add(this.centreTravailLabel);
        this.panelFlow3.add(this.centreTravail);
        this.panelFlow4.add(this.modifier);
        this.panelFlow4.add(this.retour);
        this.modifier.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e2) {
                String value2 = ModifierOperation.this.sequence.getText();
                String value3 = ModifierOperation.this.nbCycles.getText();
                String value4 = ModifierOperation.this.nbHeures.getText();
                String value5 = ModifierOperation.this.gamme.getText();
                String value6 = ModifierOperation.this.centreTravail.getText();
                String nomOperation = ModifierOperation.this.nom.getText();
                if (DatabaseConnection.requete("UPDATE OPERATIONS SET seqoperation = '" + value2 + "', nbcycle = '" + value3 + "', nbheures = '" + value4 + "', gammedecompose = '" + value5 + "', centretravaileffectue = '" + value6 + "' WHERE nomoperation = '" + nomOperation + "'")) {
                    FenetrePrincipale.getPanelOperation().raffraichirLigne(nomOperation, value2, value3, value4, value5, value6);
                    JOptionPane.showMessageDialog(null, "Op\ufffdration modifi\ufffd avec succ\ufffds.", "Modification d'op\ufffdration", 1);
                } else {
                    JOptionPane.showMessageDialog(null, "Erreur de modification de l'op\ufffdration. V\ufffdrifiez vos champs.", "Modification d'op\ufffdration", 2);
                }
            }
        });
    }

}

