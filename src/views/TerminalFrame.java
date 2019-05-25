package views;

import assembler.structure.Symbol;
import misc.utils.Converter;
import misc.utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class TerminalFrame extends JFrame {

    private JPanel rootPanel;
    private JTextArea addressPane;
    private JTable symbolTablePane;
    private JTextPane objectCodePane;

    private JScrollPane objectCodeScrollPane;
    private JScrollPane addressScrollPane;
    private JScrollPane symbolTableScrollPane;

    private ArrayList<String> addresses;
    private ArrayList<Symbol> symbolTable;
    private ArrayList<String> objectCode;

    TerminalFrame(ArrayList<String> addresses, ArrayList<Symbol> symbolTable, ArrayList<String> objectCode) {
        this.addresses = addresses;
        this.symbolTable = symbolTable;
        this.objectCode = objectCode;

        setSize(new Dimension(750, 750));
        setResizable(false);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dimension.width / 2 - this.getSize().width / 2, dimension.height / 2 - this.getSize().height / 2);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        rootPanel.setBackground(Theme.Dark.BACKGROUND_OUTER);

        addressPane.setCaretColor(Theme.Dark.FOREGROUND);
        addressPane.setForeground(Theme.Dark.FOREGROUND);
        addressPane.setBackground(Theme.Dark.BACKGROUND_INNER);

        symbolTablePane.setForeground(Theme.Dark.FOREGROUND);
        symbolTablePane.setBackground(Theme.Dark.BACKGROUND_INNER);

        objectCodePane.setCaretColor(Theme.Dark.FOREGROUND);
        objectCodePane.setForeground(Theme.Dark.FOREGROUND);
        objectCodePane.setBackground(Theme.Dark.BACKGROUND_INNER);

        addressScrollPane.setBackground(Theme.Dark.BACKGROUND_INNER);
        symbolTableScrollPane.setBackground(Theme.Dark.BACKGROUND_INNER);
        objectCodeScrollPane.setBackground(Theme.Dark.BACKGROUND_INNER);

        addressScrollPane.setForeground(Theme.Dark.BACKGROUND_INNER);
        symbolTableScrollPane.setForeground(Theme.Dark.BACKGROUND_INNER);
        objectCodeScrollPane.setForeground(Theme.Dark.BACKGROUND_INNER);


        add(rootPanel);
    }

    void run() {
        initAddressPane();
        initObjectCodePane();
    }

    void initAddressPane() {
        StringBuilder addresses = new StringBuilder();
        for (String address : this.addresses)
            addresses.append(address).append("\n");
        addressPane.setText(addresses.toString());
    }

    void initObjectCodePane() {
        StringBuilder objectCodes = new StringBuilder();
        for (String objectCode : this.objectCode)
            objectCodes.append(objectCode).append("\n");
        objectCodePane.setText(objectCodes.toString());
    }

    private void createUIComponents() {
        String[] columnNames = {"Symbol", "Address", "Value"};
        Object[][] data = new Object[symbolTable.size()][3];
        for (int i = 0; i < symbolTable.size(); i++) {
            Symbol symbol = symbolTable.get(i);
            data[i][0] = symbol.getLabel();
            data[i][1] = Utils.addHexadecimalNotation(Converter.Decimal.toHexadecimal(symbol.getAddress()));
            data[i][2] = symbol.getValue();
        }
        symbolTablePane = new JTable(data, columnNames);
    }
}
