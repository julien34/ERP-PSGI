/*
 * Decompiled with CFR 0_110.
 */
package production;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import jdbc.DatabaseConnection;
import production.AjouterGamme;
import production.ModifierGamme;

public class PanelGamme
extends JPanel {
    private JPanel panelTable = new JPanel(new BorderLayout(10, 10));
    private JPanel panelBouttons = new JPanel(new FlowLayout(1, 40, 0));
    private JButton ajouter = new JButton("Ajouter");
    private JButton modifier = new JButton("Modifier");
    private JButton supprimer = new JButton("Supprimer");
    private JTable table;
    private JScrollPane scrollPane;
    private final String[] columnNames = new String[]{"nom", "code", "emplacement"};
    private static DefaultTableModel model = new DefaultTableModel(0, 5){
        Class[] types = new Class[]{String.class, String.class, Float.class, Float.class, Float.class};

        public Class getColumnClass(int columnIndex) {
            return Integer.class;
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private int gammeChoisi = -1;
    private AjouterGamme ajouter_p = new AjouterGamme();
    private ModifierGamme modifier_p = new ModifierGamme();

    public void fillTable() {
        model.setDataVector(DatabaseConnection.remplirListeGamme(), this.columnNames);
        this.table.getRowSorter().toggleSortOrder(0);
    }

    public PanelGamme() {
        this.initElements();
        this.initHandlers();
    }

    private void initElements() {
        this.table = new JTable(model);
        this.table.setAutoCreateRowSorter(true);
        this.table.getRowSorter().toggleSortOrder(0);
        this.table.setSelectionMode(0);
        this.scrollPane = new JScrollPane(this.table);
        this.table.setPreferredScrollableViewportSize(new Dimension(540, 224));
        this.add(this.panelTable);
        this.panelTable.add("North", this.scrollPane);
        this.panelTable.add("Center", this.panelBouttons);
        this.panelBouttons.add(this.ajouter);
        this.ajouter.setPreferredSize(new Dimension(140, 26));
        this.panelBouttons.add(this.modifier);
        this.modifier.setPreferredSize(new Dimension(140, 26));
        this.panelBouttons.add(this.supprimer);
        this.supprimer.setPreferredSize(new Dimension(140, 26));
        this.modifier.setEnabled(false);
        this.supprimer.setEnabled(false);
    }

    public void initHandlers() {
        this.table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){

            @Override
            public void valueChanged(ListSelectionEvent e2) {
                if (e2.getValueIsAdjusting()) {
                    return;
                }
                ListSelectionModel selection = (ListSelectionModel)e2.getSource();
                PanelGamme.access$0(PanelGamme.this, selection.getMinSelectionIndex());
                PanelGamme.this.modifier.setEnabled(!selection.isSelectionEmpty());
                PanelGamme.this.supprimer.setEnabled(!selection.isSelectionEmpty());
            }
        });
        this.ajouter.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e2) {
            	ajouter_p.remplirEmplacement();
                PanelGamme.this.panelTable.remove(PanelGamme.this.modifier_p);
                PanelGamme.this.panelTable.add("South", PanelGamme.this.ajouter_p);
                PanelGamme.this.revalidate();
                PanelGamme.this.repaint();
            }
        });
        this.modifier.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e2) {
                String value1 = String.valueOf(model.getValueAt(PanelGamme.this.table.convertRowIndexToModel(PanelGamme.this.gammeChoisi), 0));
                String value2 = String.valueOf(model.getValueAt(PanelGamme.this.table.convertRowIndexToModel(PanelGamme.this.gammeChoisi), 1));
                String value3 = String.valueOf(model.getValueAt(PanelGamme.this.table.convertRowIndexToModel(PanelGamme.this.gammeChoisi), 2));
                PanelGamme.this.panelTable.remove(PanelGamme.this.ajouter_p);
                PanelGamme.this.modifier_p.setVal(value1, value2, value3);
                PanelGamme.this.panelTable.add("South", PanelGamme.this.modifier_p);
                PanelGamme.this.revalidate();
                PanelGamme.this.repaint();
            }
        });
        this.supprimer.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e2) {
                int option = JOptionPane.showConfirmDialog(null, "Confirmer la suppression de la gamme ?", "Suppression de la gamme", 0);
                if (option == 0 && PanelGamme.this.gammeChoisi != -1) {
                    String value1 = String.valueOf(model.getValueAt(PanelGamme.this.table.convertRowIndexToModel(PanelGamme.this.gammeChoisi), 1));
                    String value2 = String.valueOf(model.getValueAt(PanelGamme.this.table.convertRowIndexToModel(PanelGamme.this.gammeChoisi), 2));
                    String nomGamme = DatabaseConnection.getNomGamme(value1, value2);
                    if (DatabaseConnection.requete("DELETE FROM GAMME WHERE nomGamme = '" + nomGamme + "'")) {
                    	model.removeRow(PanelGamme.this.table.convertRowIndexToModel(PanelGamme.this.gammeChoisi));
                    }
                }
            }
        });
        this.ajouter_p.getRetour().addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e2) {
                PanelGamme.this.panelTable.remove(PanelGamme.this.ajouter_p);
            }
        });
        this.modifier_p.getRetour().addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e2) {
                PanelGamme.this.panelTable.remove(PanelGamme.this.modifier_p);
            }
        });
    }

    public void raffraichirLigne(String value3, String value4, String value5) {
        int ligneChoisieTemp = this.gammeChoisi;
        if (this.gammeChoisi != -1) {
            model.setValueAt(value3, this.table.convertRowIndexToModel(ligneChoisieTemp), 0);
            model.setValueAt(value4, this.table.convertRowIndexToModel(ligneChoisieTemp), 1);
            model.setValueAt(value5, this.table.convertRowIndexToModel(ligneChoisieTemp), 2);
        }
    }

    public void raffraichirListe(String value1, String value2, String value3) {
        Object[] obj = new Object[]{value1, value2, value3};
        model.addRow(obj);
    }

    static /* synthetic */ void access$0(PanelGamme panelGamme, int n2) {
        panelGamme.gammeChoisi = n2;
    }

}

