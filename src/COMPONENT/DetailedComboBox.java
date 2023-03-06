package COMPONENT;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.plaf.basic.*;
import javax.swing.plaf.metal.*;
import javax.swing.table.*;

public class DetailedComboBox extends JComboBox {

    public static enum Alignment {
        LEFT, RIGHT
    }

    private List<List<? extends Object>> tableData;
    private String[] columnNames;
    private int[] columnWidths;
    private int displayColumn;
    private Alignment popupAlignment = Alignment.LEFT;

    @SuppressWarnings("unchecked")
    public DetailedComboBox(String[] colNames, int[] colWidths,
            int displayColumnIndex) {
        super();
        this.columnNames = colNames;
        this.columnWidths = colWidths;
        this.displayColumn = displayColumnIndex;

        setUI(new TableComboBoxUI());
        setEditable(false);
    }

    @SuppressWarnings("unchecked")
    public void setPopupAlignment(Alignment alignment) {
        popupAlignment = alignment;
    }

    @SuppressWarnings("unchecked")
    public void setTableData(List<List<? extends Object>> tableData) {
        this.tableData = (tableData == null
                ? new ArrayList<List<? extends Object>>() : tableData);

        // even though the incoming data is for the table, we must also
        // populate the combobox's data, so first clear the previous list.
        removeAllItems();

        // then load the combobox with data from the appropriate column
        Iterator<List<? extends Object>> iter = this.tableData.iterator();
        while (iter.hasNext()) {
            List<? extends Object> rowData = iter.next();
            addItem(rowData.get(displayColumn));
        }
    }

    @SuppressWarnings("unchecked")
    public List<? extends Object> getSelectedRow() {
        return tableData.get(getSelectedIndex());
    }

    @SuppressWarnings("unchecked")
    private class TableComboBoxUI extends MetalComboBoxUI {

        @Override
        protected ComboPopup createPopup() {
            return new TableComboPopup(comboBox, this);
        }

        /**
         * Return the JList component
         */
        public JList getList() {
            return listBox;
        }
    }

    @SuppressWarnings("unchecked")
    private class TableComboPopup extends BasicComboPopup
            implements ListSelectionListener, ItemListener {

        private final JTable table;

        private TableComboBoxUI comboBoxUI;
        private PopupTableModel tableModel;
        private JScrollPane scroll;

        @SuppressWarnings("unchecked")
        public TableComboPopup(JComboBox combo, TableComboBoxUI ui) {
            super(combo);
            this.comboBoxUI = ui;

            tableModel = new PopupTableModel();
            table = new JTable(tableModel);
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            table.getTableHeader().setReorderingAllowed(false);
            table.setRowHeight(35);
            table.setRowMargin(5);
            table.setFont(new Font("Segoe UI", Font.ITALIC, 17));
            TableColumnModel tableColumnModel = table.getColumnModel();
            tableColumnModel.setColumnSelectionAllowed(false);

            for (int index = 0; index < table.getColumnCount(); index++) {
                TableColumn tableColumn = tableColumnModel.getColumn(index);
                tableColumn.setPreferredWidth(columnWidths[index]);
            }

            scroll = new JScrollPane(table);
            scroll.setHorizontalScrollBarPolicy(
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

            ListSelectionModel selectionModel = table.getSelectionModel();
            selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            selectionModel.addListSelectionListener(this);
            combo.addItemListener(this);

            table.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent event) {
                    Point p = event.getPoint();
                    int row = table.rowAtPoint(p);

                    comboBox.setSelectedIndex(row);
                    hide();
                }
            });

            table.setBackground(UIManager.getColor("ComboBox.listBackground"));
            table.setForeground(UIManager.getColor("ComboBox.listForeground"));
        }

        @Override
        public void show() {
            if (isEnabled()) {
                super.removeAll();

                int scrollWidth = table.getPreferredSize().width
                        + ((Integer) UIManager.get("ScrollBar.width")).intValue() + 1;
                int scrollHeight = comboBoxUI.getList().
                        getPreferredScrollableViewportSize().height;
                scroll.setPreferredSize(new Dimension(scrollWidth, scrollHeight + 90));

                super.add(scroll);

                ListSelectionModel selectionModel = table.getSelectionModel();
                selectionModel.removeListSelectionListener(this);
                selectRow();
                selectionModel.addListSelectionListener(this);

                int scrollX = 0;
                int scrollY = comboBox.getBounds().height;

                if (popupAlignment == Alignment.LEFT) {
                    scrollX = comboBox.getBounds().width - scrollWidth;
                }

                show(comboBox, scrollX, scrollY);
            }
        }

        @SuppressWarnings("unchecked")
        public void valueChanged(ListSelectionEvent event) {
            comboBox.setSelectedIndex(table.getSelectedRow());
        }

        @SuppressWarnings("unchecked")
        public void itemStateChanged(ItemEvent event) {
            if (event.getStateChange() != ItemEvent.DESELECTED) {
                ListSelectionModel selectionModel = table.getSelectionModel();
                selectionModel.removeListSelectionListener(this);
                selectRow();
                selectionModel.addListSelectionListener(this);
            }
        }

        @SuppressWarnings("unchecked")
        private void selectRow() {
            int index = comboBox.getSelectedIndex();

            if (index != -1) {
                table.setRowSelectionInterval(index, index);
                table.scrollRectToVisible(table.getCellRect(index, 0, true));
            }
        }
    }

    /**
     * A model for the popup table's data
     */
    @SuppressWarnings("unchecked")
    private class PopupTableModel extends AbstractTableModel {

        /**
         * Return the # of columns in the drop-down table
         */
        @SuppressWarnings("unchecked")
        public int getColumnCount() {
            return columnNames.length;
        }

        /**
         * Return the # of rows in the drop-down table
         */
        @SuppressWarnings("unchecked")
        public int getRowCount() {
            return tableData == null ? 0 : tableData.size();
        }

        /**
         * Determine the value for a given cell
         */
        @SuppressWarnings("unchecked")
        public Object getValueAt(int row, int col) {
            if (tableData == null || tableData.size() == 0) {
                return "";
            }

            return tableData.get(row).get(col);
        }

        @Override
        public boolean isCellEditable(int row, int col) {
            return false;
        }

        /**
         * Pull the column names out of the tableInfo object for the header
         */
        @Override
        public String getColumnName(int column) {
            String columnName = null;

            if (column >= 0 && column < columnNames.length) {
                columnName = columnNames[column].toString();
            }

            return (columnName == null) ? super.getColumnName(column) : columnName;
        }
    }
}
