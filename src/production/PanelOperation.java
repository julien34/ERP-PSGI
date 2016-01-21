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
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.synth.SynthSeparatorUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import jdbc.DatabaseConnection;
import production.AjouterOperation;
import production.ModifierOperation;

public class PanelOperation
extends JPanel {
    private JPanel panelTable = new JPanel(new BorderLayout(10, 10));
    private JPanel panelBouttons = new JPanel(new FlowLayout(1, 40, 0));
    private JButton ajouter = new JButton("Ajouter");
    private JButton modifier = new JButton("Modifier");
    private JButton supprimer = new JButton("Supprimer");
    private JTable table;
    private JScrollPane scrollPane;
    private final String[] columnNames = new String[]{"nom", "sequence", "nombre de cycle", "nombre d'heures", "gamme", "centre de travail"};
    private static DefaultTableModel model = new DefaultTableModel(0, 6){
        Class[] types = new Class[]{String.class, String.class, Float.class, Float.class, Float.class};

        public Class getColumnClass(int columnIndex) {
            return Integer.class;
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private int operationChoisi = -1;
    private AjouterOperation ajouter_o = new AjouterOperation();
    private ModifierOperation modifier_o = new ModifierOperation();

    public void fillTable() {
        model.setDataVector(DatabaseConnection.remplirListeOperation(), this.columnNames);
        this.table.getRowSorter().toggleSortOrder(0);
    }

    public PanelOperation() {
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
                PanelOperation.access$0(PanelOperation.this, selection.getMinSelectionIndex());
                PanelOperation.this.modifier.setEnabled(!selection.isSelectionEmpty());
                PanelOperation.this.supprimer.setEnabled(!selection.isSelectionEmpty());
            }
        });
        this.ajouter.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e2) {
            	ajouter_o.remplirCentre();
            	ajouter_o.remplirGamme();
                PanelOperation.this.panelTable.remove(PanelOperation.this.modifier_o);
                PanelOperation.this.panelTable.add("South", PanelOperation.this.ajouter_o);
                PanelOperation.this.revalidate();
                PanelOperation.this.repaint();
            }
        });
        this.modifier.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e2) {
                String value1 = String.valueOf(model.getValueAt(PanelOperation.this.table.convertRowIndexToModel(PanelOperation.this.operationChoisi), 0));
                String value2 = String.valueOf(model.getValueAt(PanelOperation.this.table.convertRowIndexToModel(PanelOperation.this.operationChoisi), 1));
                String value3 = String.valueOf(model.getValueAt(PanelOperation.this.table.convertRowIndexToModel(PanelOperation.this.operationChoisi), 2));
                String value4 = String.valueOf(model.getValueAt(PanelOperation.this.table.convertRowIndexToModel(PanelOperation.this.operationChoisi), 3));
                String value5 = String.valueOf(model.getValueAt(PanelOperation.this.table.convertRowIndexToModel(PanelOperation.this.operationChoisi), 4));
                String value6 = String.valueOf(model.getValueAt(PanelOperation.this.table.convertRowIndexToModel(PanelOperation.this.operationChoisi), 5));
                PanelOperation.this.panelTable.remove(PanelOperation.this.ajouter_o);
                PanelOperation.this.modifier_o.setVal(value1, value2, value3, value4, value5, value6);
                PanelOperation.this.panelTable.add("South", PanelOperation.this.modifier_o);
                PanelOperation.this.revalidate();
                PanelOperation.this.repaint();
            }
        });
        this.supprimer.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e2) {
                String nomOperation;
                nomOperation = String.valueOf(model.getValueAt(PanelOperation.this.table.convertRowIndexToModel(PanelOperation.this.operationChoisi), 0));
                int option = JOptionPane.showConfirmDialog(null, "Confirmer la suppression de l'op\ufffdration ?", "Suppression de l'op\ufffdration", 0);
                if (option == 0 && PanelOperation.this.operationChoisi != -1 && DatabaseConnection.requete("DELETE FROM OPERATIONS WHERE nomOperation = "+"'"+nomOperation+"'")) {
                	model.removeRow(PanelOperation.this.table.convertRowIndexToModel(PanelOperation.this.operationChoisi));
                }
            }
        });
        this.ajouter_o.getRetour().addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e2) {
                PanelOperation.this.panelTable.remove(PanelOperation.this.ajouter_o);
            }
        });
        this.modifier_o.getRetour().addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e2) {
                PanelOperation.this.panelTable.remove(PanelOperation.this.modifier_o);
            }
        });
    }

    public void raffraichirLigne(String value1, String value2, String value3, String value4, String value5, String value6) {
        int ligneChoisieTemp = this.operationChoisi;
        if (this.operationChoisi != -1) {
            model.setValueAt(value1, this.table.convertRowIndexToModel(ligneChoisieTemp), 0);
            model.setValueAt(value2, this.table.convertRowIndexToModel(ligneChoisieTemp), 1);
            model.setValueAt(value3, this.table.convertRowIndexToModel(ligneChoisieTemp), 2);
            model.setValueAt(value4, this.table.convertRowIndexToModel(ligneChoisieTemp), 3);
            model.setValueAt(value5, this.table.convertRowIndexToModel(ligneChoisieTemp), 4);
            model.setValueAt(value6, this.table.convertRowIndexToModel(ligneChoisieTemp), 5);
        }
    }

    public void raffraichirListe(String value1, String value2, String value3, String value4, String value5, String value6) {
        Object[] obj = new Object[]{value1, value2, value3, value4, value5, value6};
        model.addRow(obj);
    }

    static /* synthetic */ void access$0(PanelOperation panelOperation, int n2) {
        panelOperation.operationChoisi = n2;
    }

}

