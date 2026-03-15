package view;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * ============================================================
 *  THÈME LS MOTORS – Charte graphique centralisée
 * ============================================================
 *  Toutes les couleurs, polices et méthodes de style
 *  sont ici. Pour changer une couleur → tu la changes UNE
 *  seule fois ici et ça s'applique partout.
 *
 *  UTILISATION :
 *    panel.setBackground(Theme.DARK_BG);
 *    label.setForeground(Theme.GOLD);
 *    label.setFont(Theme.FONT_TITLE);
 *    Theme.styleButtonPrimary(monBouton);
 * ============================================================
 */
public class Theme {

    // ─────────────────────────────────────────────────────────
    //  COULEURS – Palette officielle LS Motors
    // ─────────────────────────────────────────────────────────

    /** Fond principal très sombre (presque noir) */
    public static final Color DARK_BG      = new Color(0x0D, 0x0D, 0x10);

    /** Fond secondaire (panneaux, cartes, formulaires) */
    public static final Color DARK_SURFACE = new Color(0x22, 0x22, 0x2C);

    /** Or LS Motors – couleur accent principale */
    public static final Color GOLD         = new Color(0xC9, 0xA2, 0x27);

    /** Or clair – effet hover */
    public static final Color GOLD_LIGHT   = new Color(0xE0, 0xBB, 0x45);

    /** Or sombre – bordures subtiles */
    public static final Color GOLD_DARK    = new Color(0x8A, 0x6E, 0x15);

    /** Texte blanc principal */
    public static final Color TEXT_WHITE   = Color.WHITE;

    /** Texte gris secondaire (labels, sous-titres) */
    public static final Color TEXT_GRAY    = new Color(0x8F, 0x90, 0x96);

    /** Bordure subtile entre éléments */
    public static final Color BORDER       = new Color(0x33, 0x33, 0x3D);

    /** Fond des champs de saisie */
    public static final Color INPUT_BG     = new Color(0x1A, 0x1A, 0x24);

    /** Rouge – bouton supprimer / erreur */
    public static final Color DANGER       = new Color(0xE5, 0x3E, 0x3E);

    /** Rouge sombre – fond bouton supprimer */
    public static final Color DANGER_BG    = new Color(0x3D, 0x15, 0x15);

    /** Vert – succès / confirmation */
    public static final Color SUCCESS      = new Color(0x2D, 0xBF, 0x6E);

    /** Orange – avertissement */
    public static final Color WARNING      = new Color(0xFF, 0xB3, 0x40);

    // ─────────────────────────────────────────────────────────
    //  POLICES – Segoe UI (charte graphique LS Motors)
    // ─────────────────────────────────────────────────────────

    private static final String FONT_FAMILY = "Segoe UI";

    public static final Font FONT_TITLE     = new Font(FONT_FAMILY, Font.BOLD,  22);
    public static final Font FONT_SUBTITLE  = new Font(FONT_FAMILY, Font.BOLD,  15);
    public static final Font FONT_BUTTON    = new Font(FONT_FAMILY, Font.BOLD,  13);
    public static final Font FONT_BODY      = new Font(FONT_FAMILY, Font.PLAIN, 13);
    public static final Font FONT_SMALL     = new Font(FONT_FAMILY, Font.PLAIN, 11);
    public static final Font FONT_TABLE_HDR = new Font(FONT_FAMILY, Font.BOLD,  12);
    public static final Font FONT_INPUT     = new Font(FONT_FAMILY, Font.PLAIN, 13);
    public static final Font FONT_NAV       = new Font(FONT_FAMILY, Font.PLAIN, 13);

    // ─────────────────────────────────────────────────────────
    //  DIMENSIONS
    // ─────────────────────────────────────────────────────────

    public static final int HEADER_HEIGHT = 64;
    public static final int SIDEBAR_WIDTH = 220;
    public static final int BTN_HEIGHT    = 38;
    public static final int INPUT_HEIGHT  = 38;
    public static final int PADDING       = 24;

    // ─────────────────────────────────────────────────────────
    //  MÉTHODES DE STYLE – appliquées sur les composants Swing
    // ─────────────────────────────────────────────────────────

    /**
     * Bouton principal doré (Ajouter, Se connecter, Enregistrer).
     * Fond or → texte noir, hover : or clair.
     */
    public static void styleButtonPrimary(JButton btn) {
        btn.setBackground(GOLD);
        btn.setForeground(DARK_BG);
        btn.setFont(FONT_BUTTON);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(btn.getPreferredSize().width, BTN_HEIGHT));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, BTN_HEIGHT));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(GOLD_LIGHT); }
            public void mouseExited(MouseEvent e)  { btn.setBackground(GOLD);       }
        });
    }

    /**
     * Bouton secondaire (Modifier, Rafraîchir) :
     * Fond sombre + contour doré + texte or.
     */
    public static void styleButtonSecondary(JButton btn) {
        btn.setBackground(DARK_SURFACE);
        btn.setForeground(GOLD);
        btn.setFont(FONT_BUTTON);
        btn.setBorderPainted(true);
        btn.setBorder(BorderFactory.createLineBorder(GOLD, 1));
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, BTN_HEIGHT));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(0x2C, 0x2C, 0x3A));
            }
            public void mouseExited(MouseEvent e) {
                btn.setBackground(DARK_SURFACE);
            }
        });
    }

    /**
     * Bouton danger (Supprimer) :
     * Fond rouge sombre + contour rouge + texte rouge.
     * Au hover : fond rouge vif + texte blanc.
     */
    public static void styleButtonDanger(JButton btn) {
        btn.setBackground(DANGER_BG);
        btn.setForeground(DANGER);
        btn.setFont(FONT_BUTTON);
        btn.setBorderPainted(true);
        btn.setBorder(BorderFactory.createLineBorder(DANGER, 1));
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, BTN_HEIGHT));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(DANGER);
                btn.setForeground(Color.WHITE);
            }
            public void mouseExited(MouseEvent e) {
                btn.setBackground(DANGER_BG);
                btn.setForeground(DANGER);
            }
        });
    }

    /**
     * Style d'un JTextField : fond sombre, texte blanc,
     * bordure grise → bordure dorée au focus.
     */
    public static void styleInput(JTextField field) {
        field.setBackground(INPUT_BG);
        field.setForeground(TEXT_WHITE);
        field.setCaretColor(GOLD);
        field.setFont(FONT_INPUT);
        field.setPreferredSize(new Dimension(field.getPreferredSize().width, INPUT_HEIGHT));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, INPUT_HEIGHT));
        appliquerBordureInput(field, false);

        field.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) { appliquerBordureInput(field, true);  }
            public void focusLost(FocusEvent e)   { appliquerBordureInput(field, false); }
        });
    }

    /**
     * Style d'un JPasswordField (même principe que JTextField).
     */
    public static void stylePasswordField(JPasswordField field) {
        field.setBackground(INPUT_BG);
        field.setForeground(TEXT_WHITE);
        field.setCaretColor(GOLD);
        field.setFont(FONT_INPUT);
        field.setPreferredSize(new Dimension(field.getPreferredSize().width, INPUT_HEIGHT));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, INPUT_HEIGHT));
        appliquerBordureInput(field, false);

        field.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) { appliquerBordureInput(field, true);  }
            public void focusLost(FocusEvent e)   { appliquerBordureInput(field, false); }
        });
    }

    /**
     * Applique la bordure input (grise au repos, dorée au focus).
     * @param focus true = actif (doré), false = repos (gris)
     */
    private static void appliquerBordureInput(JComponent field, boolean focus) {
        Color couleurBord = focus ? GOLD : BORDER;
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(couleurBord, 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
    }

    /**
     * Style d'un JComboBox : fond sombre, texte blanc.
     * Note : pour changer la couleur des items dans la liste
     * déroulante, il faut aussi un ListCellRenderer personnalisé
     * (voir UtilisateurPanel pour un exemple).
     */
    public static void styleComboBox(JComboBox<?> combo) {
        combo.setBackground(INPUT_BG);
        combo.setForeground(TEXT_WHITE);
        combo.setFont(FONT_INPUT);
        combo.setBorder(BorderFactory.createLineBorder(BORDER, 1));
        combo.setMaximumSize(new Dimension(Integer.MAX_VALUE, INPUT_HEIGHT));
        combo.setPreferredSize(new Dimension(combo.getPreferredSize().width, INPUT_HEIGHT));
        combo.setOpaque(true);

        // Renderer pour les items de la liste déroulante
        combo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                    int index, boolean selected, boolean focused) {
                super.getListCellRendererComponent(list, value, index, selected, focused);
                setBackground(selected ? new Color(0x2C, 0x2C, 0x3A) : INPUT_BG);
                setForeground(selected ? GOLD : TEXT_WHITE);
                setFont(FONT_INPUT);
                setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
                return this;
            }
        });
    }

    /**
     * Style d'un JTable : fond sombre, texte blanc,
     * header or, lignes alternées.
     * À appeler après que la table est créée.
     */
    public static void styleTable(JTable table) {
        table.setBackground(DARK_SURFACE);
        table.setForeground(TEXT_WHITE);
        table.setFont(FONT_BODY);
        table.setRowHeight(40);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 1));
        table.setSelectionBackground(new Color(0xC9, 0xA2, 0x27, 50));
        table.setSelectionForeground(TEXT_WHITE);
        table.setFillsViewportHeight(true);

        // Header (en-tête du tableau)
        JTableHeader header = table.getTableHeader();
        header.setBackground(DARK_BG);
        header.setForeground(GOLD);
        header.setFont(FONT_TABLE_HDR);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, GOLD));
        header.setReorderingAllowed(false);
        header.setResizingAllowed(true);

        // Renderer lignes alternées
        table.setDefaultRenderer(Object.class,
            new javax.swing.table.DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable t, Object val,
                        boolean selected, boolean focused, int row, int col) {
                    super.getTableCellRendererComponent(t, val, selected, focused, row, col);
                    setFont(FONT_BODY);
                    setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));
                    if (selected) {
                        setBackground(new Color(0x2C, 0x28, 0x10));
                        setForeground(GOLD);
                    } else {
                        // Alternance : ligne paire sombre / ligne impaire légèrement plus claire
                        setBackground(row % 2 == 0
                            ? DARK_SURFACE
                            : new Color(0x1D, 0x1D, 0x28));
                        setForeground(TEXT_WHITE);
                    }
                    return this;
                }
            }
        );
    }

    /**
     * Style d'un JScrollPane pour qu'il soit cohérent avec le thème.
     */
    public static void styleScrollPane(JScrollPane scroll) {
        scroll.setBorder(BorderFactory.createLineBorder(BORDER, 1));
        scroll.getViewport().setBackground(DARK_SURFACE);
        scroll.setBackground(DARK_SURFACE);
        scroll.getVerticalScrollBar().setBackground(DARK_BG);
        scroll.getHorizontalScrollBar().setBackground(DARK_BG);
    }
}