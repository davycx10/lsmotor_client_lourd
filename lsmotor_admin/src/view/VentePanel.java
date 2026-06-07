package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * ============================================================
 *  VUE : VentePanel – Historique des ventes
 * ============================================================
 *  PAS de formulaire ici → page en LECTURE SEULE.
 *  Les ventes sont créées par le site web RP, pas par la console.
 *
 *  Table SQL concernée : `vente`
 *    ID          → caché
 *    ID_Employe  → affiché via JOIN (Nom + Prénom employé)
 *    ID_Vehicule → affiché via JOIN (NomModele véhicule)
 *    NomClient   → affiché
 *    DateVente   → affichée
 *    PrixVente   → affiché en or (valeur importante)
 *
 *  STRUCTURE VISUELLE :
 *
 *    ┌──────────────────────────────────────────────────────┐
 *    │  Historique des ventes         [↻ Rafraîchir]        │
 *    ├──────────────────────────────────────────────────────┤
 *    │  ┌─────────────────┐  ┌──────────────────────────┐  │
 *    │  │ Récap. global   │  │ Détail par employé        │  │
 *    │  │ (onglet 1)      │  │ (onglet 2)                │  │
 *    │  └─────────────────┘  └──────────────────────────┘  │
 *    │                                                      │
 *    │  ┌──────────────────────────────────────────────┐   │
 *    │  │  TABLEAU (change selon l'onglet actif)        │   │
 *    │  └──────────────────────────────────────────────┘   │
 *    │                                                      │
 *    │  ┌──────────────────────────────────────────────┐   │
 *    │  │  TOTAL SEMAINE : GTA$ 4 200 000 (or, grand)  │   │
 *    │  └──────────────────────────────────────────────┘   │
 *    └──────────────────────────────────────────────────────┘
 *
 *  DEUX TABLEAUX :
 *    1. Récap global → regroupé par semaine (DATE_FORMAT SQL)
 *       Colonnes : Semaine | Nb ventes | Total GTA$
 *
 *    2. Détail par employé → une ligne par vente
 *       Colonnes : Date | Employé | Véhicule | Client | Prix GTA$
 *
 *  UTILISATION :
 *    VentePanel panel = new VentePanel();
 *    // Mettre à jour le tableau global :
 *    panel.majTableauGlobal(data);
 *    // Mettre à jour le tableau par employé :
 *    panel.majTableauEmploye(data);
 *    // Mettre à jour le total :
 *    panel.setTotalSemaine("4 200 000");
 * ============================================================
 */
public class VentePanel extends JPanel {

    // ─────────────────────────────────────────────────────────
    //  TABLEAU GLOBAL (récap par semaine)
    // ─────────────────────────────────────────────────────────

    private JTable            tableGlobal;
    private DefaultTableModel modelGlobal;

    // ─────────────────────────────────────────────────────────
    //  TABLEAU PAR EMPLOYÉ (détail vente par vente)
    // ─────────────────────────────────────────────────────────

    private JTable            tableEmploye;
    private DefaultTableModel modelEmploye;

    // ─────────────────────────────────────────────────────────
    //  AUTRES COMPOSANTS
    // ─────────────────────────────────────────────────────────

    /** Bouton pour recharger les données depuis la BDD */
    private JButton btnRafraichir;

    /**
     * Label affichant le total des ventes de la semaine en cours.
     * Mis à jour par VenteController.
     * Ex : "GTA$ 4 200 000"
     */
    private JLabel lblTotalSemaine;

    /**
     * Label affichant le nombre de ventes de la semaine.
     * Ex : "12 ventes cette semaine"
     */
    private JLabel lblNbVentes;

    // ─────────────────────────────────────────────────────────
    //  CONSTRUCTEUR
    // ─────────────────────────────────────────────────────────

    public VentePanel() {
        setBackground(Theme.DARK_BG);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(
            Theme.PADDING, Theme.PADDING,
            Theme.PADDING, Theme.PADDING
        ));
        construireUI();
    }

    // ─────────────────────────────────────────────────────────
    //  CONSTRUCTION DE L'INTERFACE
    // ─────────────────────────────────────────────────────────

    private void construireUI() {
        // ── NORTH : Titre + bouton rafraîchir ─────────────────
        add(creerPanelTop(), BorderLayout.NORTH);

        // ── CENTER : Onglets + tableaux ───────────────────────
        add(creerPanelCentre(), BorderLayout.CENTER);

        // ── SOUTH : Carte total semaine ───────────────────────
        add(creerCarteTotalSemaine(), BorderLayout.SOUTH);
    }

    // ─────────────────────────────────────────────────────────
    //  PANEL TOP
    // ─────────────────────────────────────────────────────────

    /**
     * Barre du haut : titre à gauche, bouton rafraîchir à droite.
     */
    private JPanel creerPanelTop() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Theme.DARK_BG);
        panel.setBorder(
            BorderFactory.createEmptyBorder(0, 0, 20, 0)
        );

        // Titre
        JLabel lblTitre = new JLabel("Historique des ventes");
        lblTitre.setFont(Theme.FONT_TITLE);
        lblTitre.setForeground(Theme.TEXT_WHITE);

        // Bouton rafraîchir
        btnRafraichir = new JButton("↻  Rafraîchir");
        btnRafraichir.setPreferredSize(
            new Dimension(150, Theme.BTN_HEIGHT)
        );
        Theme.styleButtonSecondary(btnRafraichir);

        panel.add(lblTitre,      BorderLayout.WEST);
        panel.add(btnRafraichir, BorderLayout.EAST);

        return panel;
    }

    // ─────────────────────────────────────────────────────────
    //  PANEL CENTRE : Onglets
    // ─────────────────────────────────────────────────────────

    /**
     * Panel central avec deux onglets :
     * - Onglet 1 : récap global par semaine
     * - Onglet 2 : détail par employé
     */
    private JPanel creerPanelCentre() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Theme.DARK_BG);

        // ── JTabbedPane custom ────────────────────────────────
        JTabbedPane onglets = new JTabbedPane() {
            @Override
            protected void paintComponent(Graphics g) {
                // Fond sombre sous les onglets
                g.setColor(Theme.DARK_BG);
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        onglets.setBackground(Theme.DARK_BG);
        onglets.setForeground(Theme.TEXT_WHITE);
        onglets.setFont(Theme.FONT_SUBTITLE);
        onglets.setOpaque(true);

        // Surcharge UIManager pour les couleurs des onglets
        UIManager.put("TabbedPane.selected",
            Theme.DARK_SURFACE);
        UIManager.put("TabbedPane.background",
            Theme.DARK_BG);
        UIManager.put("TabbedPane.foreground",
            Theme.TEXT_GRAY);
        UIManager.put("TabbedPane.selectedForeground",
            Theme.GOLD);
        UIManager.put("TabbedPane.contentBorderInsets",
            new Insets(0, 0, 0, 0));

        // ── Onglet 1 : Récap global ───────────────────────────
        onglets.addTab(
            "  Récapitulatif global  ",
            creerOngletGlobal()
        );

        // ── Onglet 2 : Détail par employé ─────────────────────
        onglets.addTab(
            "  Détail par employé  ",
            creerOngletEmploye()
        );

        panel.add(onglets, BorderLayout.CENTER);
        return panel;
    }

    // ─────────────────────────────────────────────────────────
    //  ONGLET 1 : RÉCAP GLOBAL
    // ─────────────────────────────────────────────────────────

    /**
     * Tableau récapitulatif regroupé par semaine.
     *
     * QUERY SQL dans VenteModel.getAllGlobal() :
     *   SELECT
     *     DATE_FORMAT(DateVente, '%x%v') AS semaine,
     *     MIN(DateVente)                 AS debut,
     *     COUNT(*)                       AS nb_ventes,
     *     SUM(PrixVente)                 AS total
     *   FROM vente
     *   GROUP BY semaine
     *   ORDER BY semaine DESC
     *
     * COLONNES : Semaine | Début de semaine | Nb ventes | Total GTA$
     */
    private JPanel creerOngletGlobal() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Theme.DARK_SURFACE);
        panel.setBorder(
            BorderFactory.createEmptyBorder(12, 0, 0, 0)
        );

        // Modèle : non éditable
        modelGlobal = new DefaultTableModel(
            new Object[][]{},
            new String[]{
                "Semaine (YYYYWW)",
                "Début de semaine",
                "Nb ventes",
                "Total GTA $"
            }
        ) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        tableGlobal = new JTable(modelGlobal);
        Theme.styleTable(tableGlobal);

        // Colonne "Total GTA $" en or pour mettre en valeur
        tableGlobal.getColumnModel()
                   .getColumn(3)
                   .setCellRenderer(creerRendererOr());

        // Colonne "Nb ventes" centrée
        tableGlobal.getColumnModel()
                   .getColumn(2)
                   .setCellRenderer(creerRendererCentre());

        JScrollPane scroll = new JScrollPane(tableGlobal);
        Theme.styleScrollPane(scroll);

        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    // ─────────────────────────────────────────────────────────
    //  ONGLET 2 : DÉTAIL PAR EMPLOYÉ
    // ─────────────────────────────────────────────────────────

    /**
     * Tableau détaillé : une ligne par vente avec l'employé.
     *
     * QUERY SQL dans VenteModel.getAllParEmploye() :
     *   SELECT
     *     v.ID,
     *     v.DateVente,
     *     CONCAT(u.Prenom, ' ', u.Nom) AS employe,
     *     vh.NomModele                 AS vehicule,
     *     v.NomClient,
     *     v.PrixVente
     *   FROM vente v
     *   JOIN utilisateur u  ON v.ID_Employe  = u.ID
     *   JOIN vehicule    vh ON v.ID_Vehicule = vh.ID
     *   ORDER BY v.DateVente DESC
     *
     * COLONNES : Date | Employé | Véhicule | Client | Prix GTA$
     */
    private JPanel creerOngletEmploye() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Theme.DARK_SURFACE);
        panel.setBorder(
            BorderFactory.createEmptyBorder(12, 0, 0, 0)
        );

        // Modèle : non éditable
        modelEmploye = new DefaultTableModel(
            new Object[][]{},
            new String[]{
                "Date de vente",
                "Employé",
                "Véhicule",
                "Client RP",
                "Prix GTA $"
            }
        ) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        tableEmploye = new JTable(modelEmploye);
        Theme.styleTable(tableEmploye);

        // Colonne "Prix GTA $" en or
        tableEmploye.getColumnModel()
                    .getColumn(4)
                    .setCellRenderer(creerRendererOr());

        JScrollPane scroll = new JScrollPane(tableEmploye);
        Theme.styleScrollPane(scroll);

        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    // ─────────────────────────────────────────────────────────
    //  CARTE TOTAL SEMAINE (bas de page)
    // ─────────────────────────────────────────────────────────

    /**
     * Bande en bas affichant le total de la semaine en cours.
     * Fond DARK_SURFACE + montant en grand en or.
     */
    private JPanel creerCarteTotalSemaine() {
        JPanel carte = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
                );
                g2.setColor(Theme.DARK_SURFACE);
                g2.fillRoundRect(0, 0,
                    getWidth(), getHeight(), 10, 10);
                // Ligne dorée en haut
                g2.setColor(Theme.GOLD);
                g2.setStroke(new BasicStroke(2f));
                g2.drawLine(0, 0, getWidth(), 0);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        carte.setOpaque(false);
        carte.setLayout(new BorderLayout(20, 0));
        carte.setBorder(BorderFactory.createEmptyBorder(
            16, 24, 16, 24
        ));
        carte.setMaximumSize(
            new Dimension(Integer.MAX_VALUE, 80)
        );

        // Gauche : label "Semaine en cours"
        JPanel gauche = new JPanel();
        gauche.setOpaque(false);
        gauche.setLayout(new BoxLayout(gauche, BoxLayout.Y_AXIS));

        JLabel lblLibelle = new JLabel("Semaine en cours");
        lblLibelle.setFont(Theme.FONT_SMALL);
        lblLibelle.setForeground(Theme.TEXT_GRAY);

        lblNbVentes = new JLabel("— ventes");
        lblNbVentes.setFont(Theme.FONT_BODY);
        lblNbVentes.setForeground(Theme.TEXT_WHITE);

        gauche.add(lblLibelle);
        gauche.add(Box.createRigidArea(new Dimension(0, 3)));
        gauche.add(lblNbVentes);

        // Droite : montant total en grand en or
        JPanel droite = new JPanel();
        droite.setOpaque(false);
        droite.setLayout(new BoxLayout(droite, BoxLayout.Y_AXIS));

        JLabel lblTotalLib = new JLabel("Total des ventes");
        lblTotalLib.setFont(Theme.FONT_SMALL);
        lblTotalLib.setForeground(Theme.TEXT_GRAY);
        lblTotalLib.setAlignmentX(Component.RIGHT_ALIGNMENT);

        lblTotalSemaine = new JLabel("GTA$ —");
        lblTotalSemaine.setFont(
            new Font("Segoe UI", Font.BOLD, 22)
        );
        lblTotalSemaine.setForeground(Theme.GOLD);
        lblTotalSemaine.setAlignmentX(Component.RIGHT_ALIGNMENT);

        droite.add(lblTotalLib);
        droite.add(Box.createRigidArea(new Dimension(0, 3)));
        droite.add(lblTotalSemaine);

        carte.add(gauche,  BorderLayout.WEST);
        carte.add(droite,  BorderLayout.EAST);

        // Wrapper avec marge en haut
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(Theme.DARK_BG);
        wrapper.setBorder(
            BorderFactory.createEmptyBorder(12, 0, 0, 0)
        );
        wrapper.add(carte, BorderLayout.CENTER);

        return wrapper;
    }

    // ─────────────────────────────────────────────────────────
    //  RENDERERS PERSONNALISÉS
    // ─────────────────────────────────────────────────────────

    /**
     * Renderer qui affiche le texte en or.
     * Utilisé pour la colonne "Total GTA $" et "Prix GTA $".
     */
    private DefaultTableCellRenderer creerRendererOr() {
        return new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable t, Object val, boolean selected,
                    boolean focused, int row, int col) {
                super.getTableCellRendererComponent(
                    t, val, selected, focused, row, col
                );
                setFont(new Font("Segoe UI", Font.BOLD, 13));
                setBorder(BorderFactory.createEmptyBorder(
                    0, 12, 0, 12)
                );
                if (selected) {
                    setBackground(new Color(0x2C, 0x28, 0x10));
                    setForeground(Theme.GOLD_LIGHT);
                } else {
                    setBackground(row % 2 == 0
                        ? Theme.DARK_SURFACE
                        : new Color(0x1D, 0x1D, 0x28));
                    setForeground(Theme.GOLD);
                }
                return this;
            }
        };
    }

    /**
     * Renderer qui centre le texte dans la cellule.
     * Utilisé pour la colonne "Nb ventes".
     */
    private DefaultTableCellRenderer creerRendererCentre() {
        return new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable t, Object val, boolean selected,
                    boolean focused, int row, int col) {
                super.getTableCellRendererComponent(
                    t, val, selected, focused, row, col
                );
                setHorizontalAlignment(SwingConstants.CENTER);
                setFont(Theme.FONT_BODY);
                setBorder(BorderFactory.createEmptyBorder(
                    0, 12, 0, 12)
                );
                if (selected) {
                    setBackground(new Color(0x2C, 0x28, 0x10));
                    setForeground(Theme.GOLD);
                } else {
                    setBackground(row % 2 == 0
                        ? Theme.DARK_SURFACE
                        : new Color(0x1D, 0x1D, 0x28));
                    setForeground(Theme.TEXT_WHITE);
                }
                return this;
            }
        };
    }

    // ─────────────────────────────────────────────────────────
    //  MÉTHODES PUBLIQUES – appelées par VenteController
    // ─────────────────────────────────────────────────────────

    /**
     * Met à jour le tableau récapitulatif global.
     *
     * @param data Tableau 2D :
     *   [0] = Semaine (ex: "202512")
     *   [1] = Début de semaine (ex: "2025-03-17 14:32")
     *   [2] = Nb ventes (ex: 8)
     *   [3] = Total GTA$ (ex: "4 200 000")
     *
     * EXEMPLE dans VenteController.charger() :
     *   List<Object[]> lignes = modele.getAllGlobal();
     *   Object[][] data = lignes.toArray(new Object[0][]);
     *   panel.majTableauGlobal(data);
     */
    public void majTableauGlobal(Object[][] data) {
        modelGlobal.setRowCount(0);
        for (Object[] ligne : data) {
            modelGlobal.addRow(ligne);
        }
    }

    /**
     * Met à jour le tableau détaillé par employé.
     *
     * @param data Tableau 2D :
     *   [0] = Date vente (ex: "2025-03-18 16:45")
     *   [1] = Employé    (ex: "Dave ISRAEL")
     *   [2] = Véhicule   (ex: "Pegassi Zentorno")
     *   [3] = Client RP  (ex: "Tony Montana")
     *   [4] = Prix GTA$  (ex: "1 680 000")
     */
    public void majTableauEmploye(Object[][] data) {
        modelEmploye.setRowCount(0);
        for (Object[] ligne : data) {
            modelEmploye.addRow(ligne);
        }
    }

    /**
     * Met à jour le total affiché en bas.
     *
     * EXEMPLE dans VenteController :
     *   panel.setTotalSemaine("4 200 000");
     *
     * @param total Montant formaté en String (ex: "4 200 000")
     */
    public void setTotalSemaine(String total) {
        lblTotalSemaine.setText("GTA$ " + total);
    }

    /**
     * Met à jour le nombre de ventes affiché en bas à gauche.
     *
     * EXEMPLE dans VenteController :
     *   panel.setNbVentes(8);
     *
     * @param nb Nombre de ventes de la semaine
     */
    public void setNbVentes(int nb) {
        lblNbVentes.setText(nb + " vente"
            + (nb > 1 ? "s" : "")
            + " cette semaine");
    }

    // ─────────────────────────────────────────────────────────
    //  GETTER BOUTON – le controller y attache le listener
    // ─────────────────────────────────────────────────────────

    /**
     * Retourne le bouton Rafraîchir.
     *
     * UTILISATION dans VenteController :
     *   panel.getBtnRafraichir()
     *        .addActionListener(e -> charger());
     */
    public JButton getBtnRafraichir() { return btnRafraichir; }
}