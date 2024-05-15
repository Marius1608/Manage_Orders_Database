package start;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.util.List;

/**
 *Clasa pentru crearea capurilor de tabele si popularea acestora
 *Si pentru popularea combobox-urilor
 */
public class TableUtils {

    /**
     * Populeaza un tabel cu datele dintr-o lista de obiecte
     *
     * @param table       Tabelul in care se populeaza datele
     * @param objectList  Lista de obiecte din care se iau datele
     */
    public static void populateTable(JTable table, List<?> objectList) {

        if (!objectList.isEmpty() && table != null) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            model.setColumnCount(0);

            for (Field f : objectList.get(0).getClass().getDeclaredFields()) {
                model.addColumn(f.getName());
            }

            for (Object object : objectList) {
                String[] row = new String[table.getColumnCount()];
                int i = 0;
                for (Field field : object.getClass().getDeclaredFields()) {
                    field.setAccessible(true);
                    try {
                        row[i] = field.get(object).toString();
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                    i++;
                }
                model.addRow(row);
            }
        }
    }

    /**
     * Populeaza un combobox cu elementele dintr-o lista de obiecte
     *
     * @param comboBox    Combobox-ul care trebuie populat
     * @param objectList  Lista de obiecte din care se iau elementele
     */
    public static void populateComboBox(JComboBox comboBox, List<?> objectList) {
        if (!objectList.isEmpty() && comboBox != null) {
            comboBox.removeAllItems();

            for (Object object : objectList) {
                comboBox.addItem(object);
            }
        }
    }
}
