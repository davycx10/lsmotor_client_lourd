package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

/**
 * ============================================================
 *  VUE : BaseCrudPanel – Classe de base pour tous les CRUD
 * ============================================================
 *  Rôle : Évite de réécrire le même code dans chaque panneau.
 *         UtilisateurPanel, CategoriePanel, MarquePanel et
 *         VehiculePanel héritent tous de cette classe.
 *
 *  CE QU'ELLE FOURNIT AUTOMATIQUEMENT :
 *    → Le titre de la page en haut à gauche
 *    → La barre de recherche en haut à droite
 *    → Le tableau JTable scrollable au centre
 *    → Le panel formulaire vide à droite (à remplir par la sous-classe)
 *    → Des méthodes utilitaires (majTableau, creerLabel, etc.)
 *
 *  STRUCTURE VISUELLE :
 *
 *    ┌──────────────────────────────────────────────────────┐
 *    │  [Titre]                    [🔍 Rechercher...      ] │
 *    ├─────────────────────────────┬────────────────────────┤
 *    │                             │                        │
 *    │   TABLEAU (JTable)          │  FORMULAIRE            │
 *    │   scrollable                │  (rempli par la        │
 *    │                             │   sous-classe)         │
 *    │                             │                        │
 *    └─────────────────────────────┴────────────────────────┘
 *
 *  COMMENT HÉRITER :
 *    public class UtilisateurPanel extends BaseCrudPanel {
 *        public UtilisateurPanel() {
 *            // Passe le titre et les noms des colonnes
 *            super("Utilisateurs",
 *                  new String[]{"ID","Nom","Prénom","Email","Rôle"});
 *            // Ensuite ajoute ton formulaire dans panelFormulaire
 *            construireFormulaire();
 *        }
 *    }
 * ============================================================
 */
public abstract class BaseCrudPanel extends JPanel {

    // ─────────────────────────────────────────────────────────
    //  COMPOSANTS PARTAGÉS
    //  Déclarés protected pour que les sous-classes y accèdent
    //  directement sans getter (plus pratique en interne).
    // ─────────────────────────────────────────────────────────

    /** Le tableau principal des données */
    protected JTable            table;

    /** Modèle du tableau (pour ajouter/vider les lignes) */
    protected DefaultTableModel tableModel;

    /** Barre de recherche en haut à droite */
    protected JTextField        champRecherche;

    /**
     * Panel de droite : vide ici, rempli par chaque sous-classe
     * avec ses propres champs de formulaire.
     */
    protected JPanel            panelFormulaire;

    // ─────────────────────────────────────────────────────────
    //  CONSTRUCTEUR
    // ─────────────────────────────────────────────────────────

    /**
     * @param titre   Affiché en grand en haut à gauche
     *                Ex : "Utilisateurs", "Véhicules"
     * @param colonnes Noms des colonnes du tableau
     *                Ex : new String[]{"ID","Nom","Email"}
     */
    public BaseCrudPanel(String titre, String[] colonnes) {
        configurerPanel();
        construireStructure(titre, colonnes);
    }

    // ─────────────────────────────────────────────────────────
    //  CONFIGURATION
    // ─────────────────────────────────────────────────────────

    private void configurerPanel() {
        setBackground(Theme.DARK_BG);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(
            Theme.PADDING, Theme.PADDING,
            Theme.PADDING, Theme.PADDING
        ));
    }

    // ─────────────────────────────────────────────────────────
    //  CONSTRUCTION DE LA STRUCTURE
    // ─────────────────────────────────────────────────────────

    private void construireStructure(String titre, String[] colonnes) {

        // ── NORTH : Titre + barre de recherche ────────────────
        add(creerPanelTop(titre), BorderLayout.NORTH);

        // ── CENTER : Tableau + formulaire côte à côte ─────────
        JPanel centre = new JPanel(new BorderLayout(16, 0));
        centre.setBackground(Theme.DARK_BG);
        centre.setBorder(BorderFactory.createEmptyBorder(16, 0, 0, 0));

        // Tableau à gauche (prend tout l'espace restant)
        centre.add(creerTableau(colonnes), BorderLayout.CENTER);

        // Formulaire à droite (largeur fixe 290px)
        panelFormulaire = creerPanelFormulaire();
        centre.add(panelFormulaire, BorderLayout.EAST);

        add(centre, BorderLayout.CENTER);
    }

    // ─────────────────────────────────────────────────────────
    //  PANEL TOP : Titre + Recherche
    // ─────────────────────────────────────────────────────────

    /**
     * Barre du haut : titre à gauche, champ de recherche à droite.
     */
    private JPanel creerPanelTop(String titre) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Theme.DARK_BG);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        // ── Titre ─────────────────────────────────────────────
        JLabel lblTitre = new JLabel(titre);
        lblTitre.setFont(Theme.FONT_TITLE);
        lblTitre.setForeground(Theme.TEXT_WHITE);

        // ── Barre de recherche ────────────────────────────────
        JPanel recherche = creerBarreRecherche();

        panel.add(lblTitre,  BorderLayout.WEST);
        panel.add(recherche, BorderLayout.EAST);

        return panel;
    }

    /**
     * Crée la barre de recherche stylée :
     * icône loupe + champ texte dans un container arrondi.
     */
    private JPanel creerBarreRecherche() {
        // Container arrondi dessiné à la main
        JPanel container = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                // Fond
                g2.setColor(Theme.INPUT_BG);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                // Bordure
                g2.setColor(Theme.BORDER);
                g2.setStroke(new BasicStroke(1f));
                g2.drawRoundRect(0, 0,
                    getWidth() - 1, getHeight() - 1, 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        container.setOpaque(false);
        container.setPreferredSize(new Dimension(270, Theme.INPUT_HEIGHT));

        // Icône loupe à gauche
        JLabel lblLoupe = new JLabel("  ⌕  ");
        lblLoupe.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblLoupe.setForeground(Theme.TEXT_GRAY);

        // Champ de saisie transparent (le container gère le fond)
        champRecherche = new JTextField();
        champRecherche.setOpaque(false);
        champRecherche.setBackground(new Color(0, 0, 0, 0));
        champRecherche.setForeground(Theme.TEXT_WHITE);
        champRecherche.setCaretColor(Theme.GOLD);
        champRecherche.setFont(Theme.FONT_BODY);
        champRecherche.setBorder(null);

        // Placeholder gris (hint text)
        champRecherche.setToolTipText("Rechercher...");

        // Espace à droite
        JLabel espace = new JLabel("  ");

        container.add(lblLoupe,       BorderLayout.WEST);
        container.add(champRecherche, BorderLayout.CENTER);
        container.add(espace,         BorderLayout.EAST);

        // Bordure dorée quand le champ est actif
        champRecherche.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                container.repaint();
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                container.repaint();
            }
        });

        return container;
    }

    // ─────────────────────────────────────────────────────────
    //  TABLEAU
    // ─────────────────────────────────────────────────────────

    /**
     * Crée le tableau JTable scrollable avec les colonnes données.
     * Les cellules ne sont PAS éditables directement dans le tableau
     * (on passe par le formulaire à droite).
     *
     * @param colonnes Noms des colonnes
     */
    private JScrollPane creerTableau(String[] colonnes) {
        // Modèle : données vides au départ, colonnes fixes
        tableModel = new DefaultTableModel(
            new Object[][]{}, colonnes
        ) {
            @Override
            public boolean isCellEditable(int row, int col) {
                // Empêche l'édition directe dans le tableau
                // On passe toujours par le formulaire à droite
                return false;
            }
        };

        table = new JTable(tableModel);

        // Appliquer la charte graphique LS Motors
        Theme.styleTable(table);

        // Largeur minimale des colonnes
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // Sélection ligne par ligne uniquement
        table.setSelectionMode(
            ListSelectionModel.SINGLE_SELECTION);

        // ScrollPane autour du tableau
        JScrollPane scroll = new JScrollPane(table);
        Theme.styleScrollPane(scroll);

        return scroll;
    }

    // ─────────────────────────────────────────────────────────
    //  PANEL FORMULAIRE (droite)
    // ─────────────────────────────────────────────────────────

    /**
     * Crée le panel vide de droite.
     * Les sous-classes le remplissent dans leur constructeur
     * en ajoutant des composants à panelFormulaire.
     */
    private JPanel creerPanelFormulaire() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                // Fond arrondi
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Theme.DARK_SURFACE);
                g2.fillRoundRect(0, 0,
                    getWidth(), getHeight(), 10, 10);
                // Bordure subtile
                g2.setColor(Theme.BORDER);
                g2.setStroke(new BasicStroke(1f));
                g2.drawRoundRect(0, 0,
                    getWidth() - 1, getHeight() - 1, 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(290, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(
            24, 22, 24, 22));

        return panel;
    }

    // ─────────────────────────────────────────────────────────
    //  MÉTHODES UTILITAIRES POUR LES SOUS-CLASSES
    // ─────────────────────────────────────────────────────────

    /**
     * Met à jour les données affichées dans le tableau.
     * Appelée par le controller après chaque opération BDD.
     *
     * @param data Tableau 2D de Object :
     *             chaque ligne = une ligne du tableau
     *             chaque colonne = une valeur
     *
     * EXEMPLE :
     *   Object[][] data = {
     *     {1, "ISRAEL", "Dave", "dave@mail.com", "admin"},
     *     {2, "MILLOT", "Noah", "noah@mail.com", "employe"}
     *   };
     *   panel.majTableau(data);
     */
    public void majTableau(Object[][] data) {
        // Vider toutes les lignes existantes
        tableModel.setRowCount(0);
        // Ajouter les nouvelles lignes
        for (Object[] ligne : data) {
            tableModel.addRow(ligne);
        }
    }

    /**
     * Retourne l'index de la ligne actuellement sélectionnée.
     * @return -1 si aucune ligne n'est sélectionnée
     */
    public int getLigneSelectionnee() {
        return table.getSelectedRow();
    }

    /**
     * Retourne la valeur d'une cellule de la ligne sélectionnée.
     * Pratique pour récupérer l'ID ou le nom d'un item.
     *
     * @param col Index de la colonne (0 = première colonne)
     * @return La valeur de la cellule, ou null si rien sélectionné
     *
     * EXEMPLE pour récupérer l'ID (colonne 0) :
     *   int id = (int) panel.getValeurColonne(0);
     */
    public Object getValeurColonne(int col) {
        int row = getLigneSelectionnee();
        if (row == -1) return null;
        return tableModel.getValueAt(row, col);
    }

    /**
     * Cache une colonne du tableau (ex: la colonne ID).
     * L'ID reste dans le modèle (récupérable par getValeurColonne)
     * mais n'est pas visible à l'écran.
     *
     * @param col Index de la colonne à cacher (ex: 0 pour ID)
     *
     * UTILISATION dans la sous-classe :
     *   cacherColonne(0); // Cache la colonne ID
     */
    protected void cacherColonne(int col) {
        table.getColumnModel().getColumn(col).setMinWidth(0);
        table.getColumnModel().getColumn(col).setMaxWidth(0);
        table.getColumnModel().getColumn(col).setWidth(0);
        table.getColumnModel().getColumn(col).setPreferredWidth(0);
    }

    /**
     * Affiche un message d'erreur dans une popup.
     * @param message Ex : "Veuillez remplir tous les champs"
     */
    public void afficherErreur(String message) {
        JOptionPane.showMessageDialog(
            this,
            message,
            "Erreur",
            JOptionPane.ERROR_MESSAGE
        );
    }

    /**
     * Affiche un message de succès dans une popup.
     * @param message Ex : "Utilisateur ajouté avec succès"
     */
    public void afficherSucces(String message) {
        JOptionPane.showMessageDialog(
            this,
            message,
            "Succès",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    // ─────────────────────────────────────────────────────────
    //  MÉTHODES UTILITAIRES POUR CRÉER LES COMPOSANTS
    //  du formulaire (utilisées dans les sous-classes)
    // ─────────────────────────────────────────────────────────

    /**
     * Crée un titre pour le formulaire droite.
     * Ex : "Détails utilisateur", "Nouveau véhicule"
     */
    protected JLabel creerTitreFormulaire(String texte) {
        JLabel lbl = new JLabel(texte);
        lbl.setFont(Theme.FONT_SUBTITLE);
        lbl.setForeground(Theme.TEXT_WHITE);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }

    /**
     * Crée un petit label gris pour les champs du formulaire.
     * Ex : "Nom", "Email", "Rôle"
     */
    protected JLabel creerLabelFormulaire(String texte) {
        JLabel lbl = new JLabel(texte);
        lbl.setFont(Theme.FONT_SMALL);
        lbl.setForeground(Theme.TEXT_GRAY);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }

    /**
     * Crée un JTextField stylé prêt à l'emploi.
     * Fond sombre, bordure grise → dorée au focus.
     */
    protected JTextField creerChampTexte() {
        JTextField field = new JTextField();
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        Theme.styleInput(field);
        return field;
    }

    /**
     * Crée un séparateur horizontal fin (ligne grise).
     * Utile pour séparer les champs des boutons.
     */
    protected JSeparator creerSeparateur() {
        JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
        sep.setForeground(Theme.BORDER);
        sep.setBackground(Theme.BORDER);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep.setAlignmentX(Component.LEFT_ALIGNMENT);
        return sep;
    }

    /**
     * Crée un bouton "Vider les champs" discret (gris, sans bordure).
     * L'action (viderFormulaire) est à définir dans la sous-classe.
     *
     * UTILISATION :
     *   JButton btnVider = creerBoutonVider();
     *   btnVider.addActionListener(e -> viderFormulaire());
     *   panelFormulaire.add(btnVider);
     */
    protected JButton creerBoutonVider() {
        JButton btn = new JButton("Vider les champs");
        btn.setFont(Theme.FONT_SMALL);
        btn.setForeground(Theme.TEXT_GRAY);
        btn.setBackground(Theme.DARK_BG);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setForeground(Theme.TEXT_WHITE);
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setForeground(Theme.TEXT_GRAY);
            }
        });

        return btn;
    }

    // ─────────────────────────────────────────────────────────
    //  GETTERS PUBLICS – pour les controllers
    // ─────────────────────────────────────────────────────────

    /**
     * Retourne le JTable pour y attacher un ListSelectionListener.
     *
     * UTILISATION dans le controller :
     *   panel.getTable()
     *        .getSelectionModel()
     *        .addListSelectionListener(e -> remplirFormulaire());
     */
    public JTable getTable() { return table; }

    /**
     * Retourne le champ de recherche pour y attacher un KeyListener.
     *
     * UTILISATION dans le controller :
     *   panel.getChampRecherche()
     *        .addKeyListener(new KeyAdapter() {
     *            public void keyReleased(KeyEvent e) {
     *                rechercher();
     *            }
     *        });
     */
    public JTextField getChampRecherche() { return champRecherche; }
}