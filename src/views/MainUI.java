package views;

import assembler.core.ObjectCodeGenerator;
import assembler.core.OutputGenerator;
import assembler.core.Program;
import org.jetbrains.annotations.Nullable;
import parser.Parser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainUI extends JFrame {

    private static final String TITLE = "SIC/XE Assembler";

    private JPanel rootPanel;
    private JTextPane editorTextPane;
    private JButton btnRun;
    private JButton btnDebug;

    public MainUI() {
        setTitle(TITLE);
        setSize(new Dimension(500, 500));
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dimension.width / 2 - this.getSize().width / 2, dimension.height / 2 - this.getSize().height / 2);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        rootPanel.setBackground(new Color(60, 63, 65));

        editorTextPane.setCaretColor(new Color(165, 179, 193));
        editorTextPane.setForeground(new Color(165, 179, 193));
        editorTextPane.setBackground(new Color(43, 43, 43));
        editorTextPane.setMargin(new Insets(5, 5, 5, 5));

        UIUtils.addImageIcon(btnRun, "assets/run.png");
        UIUtils.addImageIcon(btnDebug, "assets/debug.png");
        UIUtils.addUndoFunctionality(editorTextPane);

        initFunctionality();
        initLookAndFeel();
        initMenuBar();

        add(rootPanel);
    }

    private void initLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        if (getOS() != null && getOS().equals("MAC"))
            System.setProperty("apple.laf.useScreenMenuBar", "true");
    }

    private void initMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem load = new JMenuItem("Load");
        fileMenu.add(load);
        JMenuItem newFile = new JMenuItem("New");
        fileMenu.add(newFile);
        JMenuItem save = new JMenuItem("Save");
        fileMenu.add(save);

        JMenu assembleMenu = new JMenu("Assemble");
        JMenuItem assemble = new JMenuItem("Assemble");
        assembleMenu.add(assemble);

        JMenu preferenceMenu = new JMenu("Preference");
        JMenu themeMenu = new JMenu("Theme");
        JMenuItem dark = new JMenuItem("Dark");
        JMenuItem light = new JMenuItem("Light");
        themeMenu.add(dark);
        themeMenu.add(light);
        preferenceMenu.add(themeMenu);

        dark.addActionListener(e -> {
            rootPanel.setBackground(new Color(60, 63, 65));
            editorTextPane.setCaretColor(new Color(165, 179, 193));
            editorTextPane.setForeground(new Color(165, 179, 193));
            editorTextPane.setBackground(new Color(43, 43, 43));
        });

        light.addActionListener(e -> {
            rootPanel.setBackground(new Color(240, 240, 240));
            editorTextPane.setCaretColor(new Color(44, 44, 44));
            editorTextPane.setForeground(new Color(44, 44, 44));
            editorTextPane.setBackground(new Color(255, 255, 255));
        });

        menuBar.add(fileMenu);
        menuBar.add(assembleMenu);
        menuBar.add(preferenceMenu);

        setJMenuBar(menuBar);
    }

    private void initFunctionality() {
        btnRun.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String program = editorTextPane.getText();
                String[] lines = program.split("\n");
                for (String line : lines)
                    Program.getInstructionsList().add(line);

                Parser.getInstance().parse(Program.getInstructionsList(), Parser.Mode.FREE);

                OutputGenerator outputGenerator = new OutputGenerator();
                outputGenerator.generateSymbolFile();
                outputGenerator.terminal.showAddressFile();
                outputGenerator.terminal.showSymbolFile();

                ObjectCodeGenerator objectCodeGenerator = new ObjectCodeGenerator();
                objectCodeGenerator.generate();
                objectCodeGenerator.terminal.show();
            }
        });
    }

    @Nullable
    private String getOS() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("mac"))
            return "MAC";
        else if (os.contains("win"))
            return "WIN";
        else if (os.contains("nix") || os.contains("nux"))
            return "LINUX/UNIX";
        else if (os.contains("sunos"))
            return "SOLARIS";
        return null;
    }
}
