package view;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * ============================================================
 *  VUE : DashboardPanel – Tableau de bord (page d'accueil)
 * ============================================================
 *  Première page visible après connexion.
 *  Affiche des cartes de statistiques + accès rapide.
 *
 *  STRUCTURE VISUELLE :
 *
 *    ┌──────────────────────────────────────────────────────┐
 *    │  Tableau de bord            dimanche 15 mars 2026    │
 *    │  Console d'administration LS Motors                  │
 *    ├──────────────────────────────────────────────────────┤
 *    │  VUE D'ENSEMBLE                                      │
 *    │                                                      │
 *    │  ┌────────────┐ ┌────────────┐ ┌────────────┐ ┌───┐ │
 *    │  │ Véhicules  │ │Utilisateurs│ │  Ventes    │ │ M │ │
 *    │  │    —       │ │    —       │ │    —       │ │40%│ │
 *    │  │  en stock  │ │ enregistrés│ │  GTA $     │ │   │ │
 *    │  └────────────┘ └────────────┘ └────────────┘ └───┘ │
 *    │                                                      │
 *    │  ACCÈS RAPIDE                                        │
 *    │  [+ Véhicule]  [+ Utilisateur]                       │
 *    └──────────────────────────────────────────────────────┘
 *
 *  LES VRAIS CHIFFRES :
 *    Les labels (lblNbVehicules etc.) sont mis à jour par
 *    un DashboardController optionnel qui appelle les modèles.
 *    Par défaut ils affichent "—".
 *
 *  UTILISATION :
 *    DashboardPanel dashboard = new DashboardPanel();
 *    dashboard.setNbVehicules("247");
 *    dashboard.setNbUtilisateurs("12");
 * ============================================================
 */
public class DashboardPanel extends JPanel {

    // ── Labels mis à jour par le controller ───────────────────
    private JLabel lblNbVehicules;
    private JLabel lblNbUtilisateurs;
    private JLabel lblTotalVentes;
    private JLabel lblMarge;

    // ── Référence à MainView pour la navigation rapide ────────
    // Optionnel : permet aux boutons d'accès rapide de naviguer
    private Runnable onAjouterVehicule;
    private Runnable onAjouterUtilisateur;

    public DashboardPanel() {
        configurerPanel();
        construireUI();
    }

    // ─────────────────────────────────────────────────────────
    //  CONFIGURATION
    // ─────────────────────────────────────────────────────────

    private void configurerPanel() {
        setBackground(Theme.DARK_BG);
        setLayout(new BorderLayout());
    }

    // ─────────────────────────────────────────────────────────
    //  CONSTRUCTION DE L'INTERFACE
    // ─────────────────────────────────────────────────────────

    private void construireUI() {
        // ScrollPane pour s'adapter si la fenêtre est petite
        JPanel contenu = new JPanel();
        contenu.setBackground(Theme.DARK_BG);
        contenu.setLayout(new BoxLayout(contenu, BoxLayout.Y_AXIS));
        contenu.setBorder(BorderFactory.createEmptyBorder(
            Theme.PADDING, Theme.PADDING, Theme.PADDING, Theme.PADDING
        ));

        // ── En-tête ───────────────────────────────────────────
        contenu.add(creerEntete());
        contenu.add(Box.createRigidArea(new Dimension(0, 24)));

        // ── Séparateur ────────────────────────────────────────
        contenu.add(creerSeparateur());
        contenu.add(Box.createRigidArea(new Dimension(0, 28)));

        // ── Section cartes stats ──────────────────────────────
        contenu.add(creerLabelSection("VUE D'ENSEMBLE"));
        contenu.add(Box.createRigidArea(new Dimension(0, 14)));
        contenu.add(creerRangeCartes());
        contenu.add(Box.createRigidArea(new Dimension(0, 36)));

        // ── Section accès rapide ──────────────────────────────
        contenu.add(creerLabelSection("ACCÈS RAPIDE"));
        contenu.add(Box.createRigidArea(new Dimension(0, 14)));
        contenu.add(creerAccesRapide());
        contenu.add(Box.createRigidArea(new Dimension(0, 36)));

        // ── Section infos ─────────────────────────────────────
        contenu.add(creerLabelSection("INFORMATIONS"));
        contenu.add(Box.createRigidArea(new Dimension(0, 14)));
        contenu.add(creerCarteInfo());

        // Espace en bas
        contenu.add(Box.createVerticalGlue());

        // Wrap dans un ScrollPane
        JScrollPane scroll = new JScrollPane(contenu);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(Theme.DARK_BG);
        scroll.setBackground(Theme.DARK_BG);
        scroll.setHorizontalScrollBarPolicy(
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        add(scroll, BorderLayout.CENTER);
    }

    // ─────────────────────────────────────────────────────────
    //  COMPOSANTS
    // ─────────────────────────────────────────────────────────

    /**
     * Bloc en-tête : titre à gauche + date à droite.
     */
    private JPanel creerEntete() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Theme.DARK_BG);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Gauche : titre + sous-titre
        JPanel gauche = new JPanel();
        gauche.setBackground(Theme.DARK_BG);
        gauche.setLayout(new BoxLayout(gauche, BoxLayout.Y_AXIS));

        JLabel lblTitre = new JLabel("Tableau de bord");
        lblTitre.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitre.setForeground(Theme.TEXT_WHITE);

        JLabel lblSous = new JLabel(
            "Console d'administration LS Motors");
        lblSous.setFont(Theme.FONT_BODY);
        lblSous.setForeground(Theme.TEXT_GRAY);

        gauche.add(lblTitre);
        gauche.add(Box.createRigidArea(new Dimension(0, 4)));
        gauche.add(lblSous);

        // Droite : date du jour en français
        String dateStr = LocalDate.now().format(
            DateTimeFormatter.ofPattern("EEEE d MMMM yyyy",
                new Locale("fr", "FR"))
        );
        // Majuscule sur la première lettre
        dateStr = Character.toUpperCase(dateStr.charAt(0))
                + dateStr.substring(1);

        JLabel lblDate = new JLabel(dateStr);
        lblDate.setFont(Theme.FONT_SMALL);
        lblDate.setForeground(Theme.TEXT_GRAY);

        panel.add(gauche,  BorderLayout.WEST);
        panel.add(lblDate, BorderLayout.EAST);

        return panel;
    }

    /**
     * Ligne dorée fine de séparation.
     */
    private JPanel creerSeparateur() {
        JPanel sep = new JPanel();
        sep.setBackground(Theme.BORDER);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep.setAlignmentX(Component.LEFT_ALIGNMENT);
        return sep;
    }

    /**
     * Petit label de titre de section en gris majuscule.
     * Ex : "VUE D'ENSEMBLE", "ACCÈS RAPIDE"
     */
    private JLabel creerLabelSection(String texte) {
        JLabel lbl = new JLabel(texte);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lbl.setForeground(Theme.TEXT_GRAY);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }

    /**
     * Ligne de 4 cartes de statistiques côte à côte.
     * Chaque carte a un accent coloré en haut.
     */
    private JPanel creerRangeCartes() {
        JPanel row = new JPanel(new GridLayout(1, 4, 16, 0));
        row.setBackground(Theme.DARK_BG);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Carte 1 : Véhicules en stock (accent or)
        lblNbVehicules = new JLabel("—");
        row.add(creerCarte(
            "Véhicules",
            lblNbVehicules,
            "en stock",
            Theme.GOLD
        ));

        // Carte 2 : Utilisateurs (accent gris)
        lblNbUtilisateurs = new JLabel("—");
        row.add(creerCarte(
            "Utilisateurs",
            lblNbUtilisateurs,
            "enregistrés",
            Theme.TEXT_GRAY
        ));

        // Carte 3 : Total ventes semaine (accent vert)
        lblTotalVentes = new JLabel("—");
        row.add(creerCarte(
            "Ventes (semaine)",
            lblTotalVentes,
            "GTA $",
            Theme.SUCCESS
        ));

        // Carte 4 : Marge appliquée (accent or)
        lblMarge = new JLabel("40 %");
        row.add(creerCarte(
            "Marge appliquée",
            lblMarge,
            "sur tous les prix",
            Theme.GOLD
        ));

        return row;
    }

    /**
     * Crée une carte de statistique individuelle.
     * Fond DARK_SURFACE + barre colorée en haut + valeur grande.
     *
     * @param titre    Titre de la carte (petit, gris)
     * @param lblVal   Label dont la valeur est mise à jour par le controller
     * @param sousTitre Texte sous la valeur (petit, gris)
     * @param accent   Couleur de la barre du haut et de la valeur
     */
    private JPanel creerCarte(String titre, JLabel lblVal,
                               String sousTitre, Color accent) {
        JPanel carte = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);

                // Fond de la carte
                g2.setColor(Theme.DARK_SURFACE);
                g2.fillRoundRect(0, 0,
                    getWidth(), getHeight(), 10, 10);

                // Barre colorée en haut (4px)
                g2.setColor(accent);
                g2.fillRoundRect(0, 0, getWidth(), 4, 10, 10);
                // Rectangle plat en bas de la barre pour couvrir les coins
                g2.fillRect(0, 2, getWidth(), 4);

                g2.dispose();
                super.paintComponent(g);
            }
        };
        carte.setOpaque(false);
        carte.setLayout(new BoxLayout(carte, BoxLayout.Y_AXIS));
        carte.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));

        // Titre de la carte
        JLabel lblTitre = new JLabel(titre);
        lblTitre.setFont(Theme.FONT_SMALL);
        lblTitre.setForeground(Theme.TEXT_GRAY);
        lblTitre.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Valeur principale (grande, colorée)
        lblVal.setFont(new Font("Segoe UI", Font.BOLD, 30));
        lblVal.setForeground(accent);
        lblVal.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Sous-titre
        JLabel lblSous = new JLabel(sousTitre);
        lblSous.setFont(Theme.FONT_SMALL);
        lblSous.setForeground(Theme.TEXT_GRAY);
        lblSous.setAlignmentX(Component.LEFT_ALIGNMENT);

        carte.add(lblTitre);
        carte.add(Box.createRigidArea(new Dimension(0, 8)));
        carte.add(lblVal);
        carte.add(Box.createRigidArea(new Dimension(0, 4)));
        carte.add(lblSous);

        return carte;
    }

    /**
     * Boutons d'accès rapide vers les sections principales.
     * Callbacks branchés via setOnAjouterVehicule() etc.
     */
    private JPanel creerAccesRapide() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        panel.setBackground(Theme.DARK_BG);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Bouton ajouter véhicule
        JButton btnVehicule = new JButton("+ Ajouter un véhicule");
        btnVehicule.setPreferredSize(new Dimension(200, Theme.BTN_HEIGHT));
        Theme.styleButtonPrimary(btnVehicule);
        btnVehicule.addActionListener(e -> {
            if (onAjouterVehicule != null) onAjouterVehicule.run();
        });

        // Bouton ajouter utilisateur
        JButton btnUser = new JButton("+ Ajouter un utilisateur");
        btnUser.setPreferredSize(new Dimension(210, Theme.BTN_HEIGHT));
        Theme.styleButtonSecondary(btnUser);
        btnUser.addActionListener(e -> {
            if (onAjouterUtilisateur != null) onAjouterUtilisateur.run();
        });

        panel.add(btnVehicule);
        panel.add(btnUser);

        return panel;
    }

    /**
     * Carte d'information générale sur l'application.
     */
    private JPanel creerCarteInfo() {
        JPanel carte = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Theme.DARK_SURFACE);
                g2.fill(new RoundRectangle2D.Double(
                    0, 0, getWidth(), getHeight(), 10, 10));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        carte.setOpaque(false);
        carte.setLayout(new BoxLayout(carte, BoxLayout.Y_AXIS));
        carte.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));
        carte.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));
        carte.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblTitre = new JLabel("LS Motors — Console Administration");
        lblTitre.setFont(Theme.FONT_SUBTITLE);
        lblTitre.setForeground(Theme.TEXT_WHITE);
        lblTitre.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblDesc = new JLabel(
            "<html>Application de gestion interne réservée aux administrateurs."
          + " Gérez les véhicules, utilisateurs, marques, catégories et ventes."
          + " Les modifications sont appliquées en temps réel sur le site web RP.</html>"
        );
        lblDesc.setFont(Theme.FONT_SMALL);
        lblDesc.setForeground(Theme.TEXT_GRAY);
        lblDesc.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblAuteurs = new JLabel(
            "Développé par Nyght"

        );
        lblAuteurs.setFont(new Font("Segoe UI", Font.ITALIC, 10));
        lblAuteurs.setForeground(new Color(0x44, 0x44, 0x55));
        lblAuteurs.setAlignmentX(Component.LEFT_ALIGNMENT);

        carte.add(lblTitre);
        carte.add(Box.createRigidArea(new Dimension(0, 6)));
        carte.add(lblDesc);
        carte.add(Box.createRigidArea(new Dimension(0, 10)));
        carte.add(lblAuteurs);

        return carte;
    }

    // ─────────────────────────────────────────────────────────
    //  SETTERS – mis à jour par le controller
    // ─────────────────────────────────────────────────────────

    /**
     * Met à jour le nombre de véhicules affiché.
     * Appelé par DashboardController ou VehiculeController.
     * @param val Ex : "247"
     */
    public void setNbVehicules(String val) {
        lblNbVehicules.setText(val);
    }

    /**
     * Met à jour le nombre d'utilisateurs.
     * @param val Ex : "12"
     */
    public void setNbUtilisateurs(String val) {
        lblNbUtilisateurs.setText(val);
    }

    /**
     * Met à jour le total des ventes de la semaine.
     * @param val Ex : "4 200 000"
     */
    public void setTotalVentes(String val) {
        lblTotalVentes.setText(val);
    }

    /**
     * Met à jour la marge affichée.
     * @param val Ex : "40 %"
     */
    public void setMarge(String val) {
        lblMarge.setText(val);
    }

    // ─────────────────────────────────────────────────────────
    //  SETTERS CALLBACKS – navigation rapide
    // ─────────────────────────────────────────────────────────

    /**
     * Branche le bouton "+ Ajouter un véhicule" sur une action.
     *
     * UTILISATION dans MainView :
     *   dashboardPanel.setOnAjouterVehicule(() ->
     *       afficherPanel(SidebarPanel.IDX_VEHICULES));
     */
    public void setOnAjouterVehicule(Runnable action) {
        this.onAjouterVehicule = action;
    }

    /**
     * Branche le bouton "+ Ajouter un utilisateur".
     *
     * UTILISATION dans MainView :
     *   dashboardPanel.setOnAjouterUtilisateur(() ->
     *       afficherPanel(SidebarPanel.IDX_UTILISATEURS));
     */
    public void setOnAjouterUtilisateur(Runnable action) {
        this.onAjouterUtilisateur = action;
    }
}