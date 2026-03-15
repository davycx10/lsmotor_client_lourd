package view;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * ============================================================
 *  VUE : ConfigPanel – Configuration de la marge globale
 * ============================================================
 *  Page simple pour lire et modifier la marge appliquée
 *  automatiquement sur tous les véhicules du site web RP.
 *
 *  Table SQL concernée : `config`
 *    ID            → toujours 1 (une seule ligne)
 *    MargePourcent → le seul champ modifiable
 *    DateMaj       → mis à jour automatiquement par MySQL
 *                    (ON UPDATE CURRENT_TIMESTAMP)
 *
 *  STRUCTURE VISUELLE :
 *
 *    ┌──────────────────────────────────────────────────────┐
 *    │  Configuration                                       │
 *    │  Paramètres globaux de l'application                 │
 *    ├──────────────────────────────────────────────────────┤
 *    │                                                      │
 *    │   ┌──────────────────────────────────────────────┐  │
 *    │   │  ─── Marge globale ───────────────────────   │  │
 *    │   │                                              │  │
 *    │   │  Marge actuelle :   [ 40.00 ]  %             │  │
 *    │   │                                              │  │
 *    │   │  Dernière modification : 2025-03-15 14:32    │  │
 *    │   │                                              │  │
 *    │   │  ⚠ Avertissement impact global               │  │
 *    │   │                                              │  │
 *    │   │  [ Charger ]    [ Enregistrer ]              │  │
 *    │   │                                              │  │
 *    │   │  ✓ Marge enregistrée avec succès.  (statut) │  │
 *    │   └──────────────────────────────────────────────┘  │
 *    │                                                      │
 *    │   ┌──────────────────────────────────────────────┐  │
 *    │   │  ─── Informations système ────────────────   │  │
 *    │   │  Base de données : ls_motors                 │  │
 *    │   │  Version console  : 1.0                      │  │
 *    │   └──────────────────────────────────────────────┘  │
 *    └──────────────────────────────────────────────────────┘
 *
 *  UTILISATION :
 *    ConfigPanel panel = new ConfigPanel();
 *    panel.setMarge(40.0);
 *    panel.setDateMaj("2025-03-15 14:32:00");
 *    panel.afficherStatut("Enregistré !", true);
 * ============================================================
 */
public class ConfigPanel extends JPanel {

    // ─────────────────────────────────────────────────────────
    //  CHAMPS
    // ─────────────────────────────────────────────────────────

    /**
     * Champ de saisie de la marge (ex : "40.00").
     * Petit, centré, juste assez large pour un nombre.
     */
    private JTextField champMarge;

    /**
     * Affiche la date de dernière modification.
     * Mis à jour par ConfigController après chargement.
     * Ex : "15/03/2025 à 14h32"
     */
    private JLabel lblDateMaj;

    /**
     * Message de statut affiché après une action.
     * Vert = succès, Rouge = erreur.
     * Ex : "✓ Marge enregistrée avec succès."
     */
    private JLabel lblStatut;

    // ─────────────────────────────────────────────────────────
    //  BOUTONS
    // ─────────────────────────────────────────────────────────

    /**
     * Recharge la marge depuis la BDD.
     * → ConfigController appelle modele.getMarge()
     *   et met à jour champMarge + lblDateMaj.
     */
    private JButton btnCharger;

    /**
     * Enregistre la nouvelle marge en BDD.
     * → ConfigController lit champMarge, valide, appelle
     *   modele.saveMarge(valeur).
     */
    private JButton btnEnregistrer;

    // ─────────────────────────────────────────────────────────
    //  CONSTRUCTEUR
    // ─────────────────────────────────────────────────────────

    public ConfigPanel() {
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

        // ── NORTH : Titre de la page ──────────────────────────
        add(creerPanelTitre(), BorderLayout.NORTH);

        // ── CENTER : Cartes de configuration ──────────────────
        // On utilise un ScrollPane au cas où la fenêtre est petite
        JPanel contenu = new JPanel();
        contenu.setBackground(Theme.DARK_BG);
        contenu.setLayout(new BoxLayout(contenu, BoxLayout.Y_AXIS));

        contenu.add(Box.createRigidArea(new Dimension(0, 24)));
        contenu.add(creerCarteMarge());
        contenu.add(Box.createRigidArea(new Dimension(0, 20)));
        contenu.add(creerCarteInfoSysteme());
        contenu.add(Box.createVerticalGlue());

        JScrollPane scroll = new JScrollPane(contenu);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(Theme.DARK_BG);
        scroll.setBackground(Theme.DARK_BG);
        scroll.setHorizontalScrollBarPolicy(
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        add(scroll, BorderLayout.CENTER);
    }

    // ─────────────────────────────────────────────────────────
    //  PANEL TITRE
    // ─────────────────────────────────────────────────────────

    private JPanel creerPanelTitre() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Theme.DARK_BG);
        panel.setBorder(
            BorderFactory.createEmptyBorder(0, 0, 4, 0)
        );

        JLabel lblTitre = new JLabel("Configuration");
        lblTitre.setFont(Theme.FONT_TITLE);
        lblTitre.setForeground(Theme.TEXT_WHITE);

        JLabel lblSous = new JLabel(
            "Paramètres globaux de l'application"
        );
        lblSous.setFont(Theme.FONT_SMALL);
        lblSous.setForeground(Theme.TEXT_GRAY);

        JPanel textes = new JPanel();
        textes.setBackground(Theme.DARK_BG);
        textes.setLayout(new BoxLayout(textes, BoxLayout.Y_AXIS));
        textes.add(lblTitre);
        textes.add(Box.createRigidArea(new Dimension(0, 4)));
        textes.add(lblSous);

        panel.add(textes, BorderLayout.WEST);
        return panel;
    }

    // ─────────────────────────────────────────────────────────
    //  CARTE MARGE
    // ─────────────────────────────────────────────────────────

    /**
     * Carte principale : saisie et enregistrement de la marge.
     * Fond DARK_SURFACE + coins arrondis + bordure dorée subtile.
     * Largeur max 600px pour ne pas s'étirer trop sur grand écran.
     */
    private JPanel creerCarteMarge() {
        JPanel carte = creerCarteBase();
        carte.setMaximumSize(new Dimension(600, 340));

        // ── Titre de section ──────────────────────────────────
        carte.add(creerTitreSection("Marge globale sur les véhicules"));
        carte.add(Box.createRigidArea(new Dimension(0, 6)));

        JLabel lblDesc = new JLabel(
            "Appliquée automatiquement au prix catalogue sur le site web RP."
        );
        lblDesc.setFont(Theme.FONT_SMALL);
        lblDesc.setForeground(Theme.TEXT_GRAY);
        lblDesc.setAlignmentX(Component.LEFT_ALIGNMENT);
        carte.add(lblDesc);
        carte.add(Box.createRigidArea(new Dimension(0, 24)));

        // ── Ligne : libellé + champ + % ───────────────────────
        JPanel ligneSaisie = creerLigneSaisie();
        carte.add(ligneSaisie);
        carte.add(Box.createRigidArea(new Dimension(0, 16)));

        // ── Dernière modification ─────────────────────────────
        JPanel ligneDateMaj = new JPanel(
            new FlowLayout(FlowLayout.LEFT, 0, 0)
        );
        ligneDateMaj.setOpaque(false);
        ligneDateMaj.setMaximumSize(
            new Dimension(Integer.MAX_VALUE, 22)
        );
        ligneDateMaj.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblDateLib = new JLabel("Dernière modification : ");
        lblDateLib.setFont(Theme.FONT_SMALL);
        lblDateLib.setForeground(Theme.TEXT_GRAY);

        lblDateMaj = new JLabel("—");
        lblDateMaj.setFont(Theme.FONT_SMALL);
        lblDateMaj.setForeground(Theme.TEXT_WHITE);

        ligneDateMaj.add(lblDateLib);
        ligneDateMaj.add(lblDateMaj);
        carte.add(ligneDateMaj);
        carte.add(Box.createRigidArea(new Dimension(0, 20)));

        // ── Avertissement ─────────────────────────────────────
        carte.add(creerPanelAvertissement());
        carte.add(Box.createRigidArea(new Dimension(0, 24)));

        // ── Séparateur ────────────────────────────────────────
        JSeparator sep = new JSeparator();
        sep.setForeground(Theme.BORDER);
        sep.setBackground(Theme.BORDER);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep.setAlignmentX(Component.LEFT_ALIGNMENT);
        carte.add(sep);
        carte.add(Box.createRigidArea(new Dimension(0, 20)));

        // ── Boutons ───────────────────────────────────────────
        carte.add(creerPanelBoutons());
        carte.add(Box.createRigidArea(new Dimension(0, 16)));

        // ── Label statut ──────────────────────────────────────
        lblStatut = new JLabel(" ");
        lblStatut.setFont(Theme.FONT_SMALL);
        lblStatut.setForeground(Theme.SUCCESS);
        lblStatut.setAlignmentX(Component.LEFT_ALIGNMENT);
        carte.add(lblStatut);

        return carte;
    }

    /**
     * Ligne "Marge actuelle : [ 40.00 ] %"
     * Champ petit + label % en or à droite.
     */
    private JPanel creerLigneSaisie() {
        JPanel panel = new JPanel(
            new FlowLayout(FlowLayout.LEFT, 10, 0)
        );
        panel.setOpaque(false);
        panel.setMaximumSize(
            new Dimension(Integer.MAX_VALUE, Theme.INPUT_HEIGHT)
        );
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblLib = new JLabel("Marge actuelle :");
        lblLib.setFont(Theme.FONT_BODY);
        lblLib.setForeground(Theme.TEXT_WHITE);

        // Champ petit (juste pour un nombre)
        champMarge = new JTextField("40.00");
        champMarge.setPreferredSize(new Dimension(90, Theme.INPUT_HEIGHT));
        champMarge.setHorizontalAlignment(JTextField.CENTER);
        Theme.styleInput(champMarge);

        // % en grand en or
        JLabel lblPourcent = new JLabel("%");
        lblPourcent.setFont(
            new Font("Segoe UI", Font.BOLD, 20)
        );
        lblPourcent.setForeground(Theme.GOLD);

        panel.add(lblLib);
        panel.add(champMarge);
        panel.add(lblPourcent);

        return panel;
    }

    /**
     * Panneau d'avertissement orange :
     * modification de la marge = impact immédiat sur le site.
     */
    private JPanel creerPanelAvertissement() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
                );
                g2.setColor(new Color(0x33, 0x22, 0x00));
                g2.fillRoundRect(
                    0, 0, getWidth(), getHeight(), 8, 8
                );
                g2.setColor(new Color(0xFF, 0xB3, 0x40, 80));
                g2.setStroke(new BasicStroke(1f));
                g2.drawRoundRect(
                    0, 0, getWidth() - 1, getHeight() - 1, 8, 8
                );
                g2.dispose();
                super.paintComponent(g);
            }
        };
        panel.setOpaque(false);
        panel.setLayout(new BorderLayout());
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setBorder(
            BorderFactory.createEmptyBorder(10, 14, 10, 14)
        );

        JLabel lbl = new JLabel(
            "<html>"
          + "<b>⚠  Attention :</b> cette valeur impacte "
          + "immédiatement les prix affichés sur le site web RP.<br>"
          + "Cliquer sur <b>Charger</b> pour voir la valeur "
          + "actuelle avant de modifier."
          + "</html>"
        );
        lbl.setFont(Theme.FONT_SMALL);
        lbl.setForeground(Theme.WARNING);

        panel.add(lbl, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Panel avec les deux boutons Charger + Enregistrer.
     */
    private JPanel creerPanelBoutons() {
        JPanel panel = new JPanel(
            new FlowLayout(FlowLayout.LEFT, 12, 0)
        );
        panel.setOpaque(false);
        panel.setMaximumSize(
            new Dimension(Integer.MAX_VALUE, Theme.BTN_HEIGHT)
        );
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        btnCharger = new JButton("↓  Charger");
        btnCharger.setPreferredSize(
            new Dimension(130, Theme.BTN_HEIGHT)
        );
        Theme.styleButtonSecondary(btnCharger);

        btnEnregistrer = new JButton("✓  Enregistrer");
        btnEnregistrer.setPreferredSize(
            new Dimension(150, Theme.BTN_HEIGHT)
        );
        Theme.styleButtonPrimary(btnEnregistrer);

        panel.add(btnCharger);
        panel.add(btnEnregistrer);

        return panel;
    }

    // ─────────────────────────────────────────────────────────
    //  CARTE INFOS SYSTÈME
    // ─────────────────────────────────────────────────────────

    /**
     * Carte secondaire : infos en lecture seule sur l'appli.
     * Purement informatif, rien d'éditable ici.
     */
    private JPanel creerCarteInfoSysteme() {
        JPanel carte = creerCarteBase();
        carte.setMaximumSize(new Dimension(600, 160));

        carte.add(creerTitreSection("Informations système"));
        carte.add(Box.createRigidArea(new Dimension(0, 16)));

        // Lignes d'info
        carte.add(creerLigneInfo(
            "Base de données", "ls_motors"
        ));
        carte.add(Box.createRigidArea(new Dimension(0, 8)));
        carte.add(creerLigneInfo(
            "Version console", "1.0"
        ));
        carte.add(Box.createRigidArea(new Dimension(0, 8)));
        carte.add(creerLigneInfo(
            "Développé par",
            "Dave ISRAEL · Youssef BOUMILK · Noah MILLOT · Théo DORIVAL"
        ));
        carte.add(Box.createRigidArea(new Dimension(0, 8)));
        carte.add(creerLigneInfo(
            "Formation", "BTS SIO SLAM — NYT (Nyght)"
        ));

        return carte;
    }

    /**
     * Ligne "libellé : valeur" pour la carte infos système.
     *
     * @param libelle Nom du paramètre (gris)
     * @param valeur  Valeur affichée  (blanc)
     */
    private JPanel creerLigneInfo(String libelle, String valeur) {
        JPanel ligne = new JPanel(
            new FlowLayout(FlowLayout.LEFT, 8, 0)
        );
        ligne.setOpaque(false);
        ligne.setMaximumSize(
            new Dimension(Integer.MAX_VALUE, 20)
        );
        ligne.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblLib = new JLabel(libelle + " :");
        lblLib.setFont(Theme.FONT_SMALL);
        lblLib.setForeground(Theme.TEXT_GRAY);
        lblLib.setPreferredSize(new Dimension(130, 16));

        JLabel lblVal = new JLabel(valeur);
        lblVal.setFont(Theme.FONT_SMALL);
        lblVal.setForeground(Theme.TEXT_WHITE);

        ligne.add(lblLib);
        ligne.add(lblVal);
        return ligne;
    }

    // ─────────────────────────────────────────────────────────
    //  UTILITAIRES INTERNES
    // ─────────────────────────────────────────────────────────

    /**
     * Crée la base d'une carte (fond arrondi DARK_SURFACE).
     * Utilisée par creerCarteMarge() et creerCarteInfoSysteme().
     */
    private JPanel creerCarteBase() {
        JPanel carte = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
                );
                // Fond
                g2.setColor(Theme.DARK_SURFACE);
                g2.fill(new RoundRectangle2D.Double(
                    0, 0, getWidth(), getHeight(), 12, 12
                ));
                // Bordure dorée très subtile
                g2.setColor(Theme.GOLD_DARK);
                g2.setStroke(new BasicStroke(1f));
                g2.draw(new RoundRectangle2D.Double(
                    0.5, 0.5,
                    getWidth() - 1, getHeight() - 1,
                    12, 12
                ));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        carte.setOpaque(false);
        carte.setLayout(new BoxLayout(carte, BoxLayout.Y_AXIS));
        carte.setAlignmentX(Component.LEFT_ALIGNMENT);
        carte.setBorder(BorderFactory.createEmptyBorder(
            28, 28, 28, 28
        ));
        return carte;
    }

    /**
     * Titre de section avec ligne dorée en dessous.
     * Ex : "Marge globale sur les véhicules"
     */
    private JPanel creerTitreSection(String texte) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setMaximumSize(
            new Dimension(Integer.MAX_VALUE, 36)
        );

        JLabel lbl = new JLabel(texte);
        lbl.setFont(Theme.FONT_SUBTITLE);
        lbl.setForeground(Theme.TEXT_WHITE);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Petite ligne dorée sous le titre
        JPanel ligne = new JPanel();
        ligne.setBackground(Theme.GOLD);
        ligne.setMaximumSize(new Dimension(40, 2));
        ligne.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(lbl);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(ligne);

        return panel;
    }

    // ─────────────────────────────────────────────────────────
    //  MÉTHODES PUBLIQUES – appelées par ConfigController
    // ─────────────────────────────────────────────────────────

    /**
     * Affiche la marge chargée depuis la BDD dans le champ.
     *
     * EXEMPLE dans ConfigController.chargerMarge() :
     *   double marge = modele.getMarge();
     *   panel.setMarge(marge);
     *
     * @param marge La valeur à afficher (ex : 40.0)
     */
    public void setMarge(double marge) {
        champMarge.setText(String.valueOf(marge));
    }

    /**
     * Affiche la date de dernière modification.
     *
     * EXEMPLE dans ConfigController.chargerMarge() :
     *   String date = modele.getDateMaj();
     *   panel.setDateMaj(date);
     *
     * @param date Date formatée (ex : "2025-03-15 14:32:00")
     */
    public void setDateMaj(String date) {
        lblDateMaj.setText(date != null ? date : "—");
    }

    /**
     * Affiche un message de statut sous les boutons.
     * Vert pour succès, rouge pour erreur.
     *
     * EXEMPLES dans ConfigController :
     *   panel.afficherStatut(
     *       "✓  Marge enregistrée avec succès.", true
     *   );
     *   panel.afficherStatut(
     *       "✕  Valeur invalide (doit être entre 0 et 200).",
     *       false
     *   );
     *
     * @param message Le texte à afficher
     * @param succes  true = vert, false = rouge
     */
    public void afficherStatut(String message, boolean succes) {
        lblStatut.setText(message);
        lblStatut.setForeground(
            succes ? Theme.SUCCESS : Theme.DANGER
        );
    }

    /**
     * Efface le message de statut.
     * Utile à appeler quand l'utilisateur commence
     * à retaper dans le champ marge.
     */
    public void effacerStatut() {
        lblStatut.setText(" ");
    }

    // ─────────────────────────────────────────────────────────
    //  GETTERS – lus par ConfigController
    // ─────────────────────────────────────────────────────────

    /**
     * Retourne la valeur saisie dans le champ marge.
     * Le controller la parse en double et valide :
     *
     *   String txt = panel.getMargeTexte();
     *   try {
     *       double val = Double.parseDouble(txt);
     *       if (val < 0 || val > 200) {
     *           panel.afficherStatut("Valeur invalide.", false);
     *           return;
     *       }
     *       modele.saveMarge(val);
     *       panel.afficherStatut("Enregistré !", true);
     *   } catch (NumberFormatException e) {
     *       panel.afficherStatut("Entrez un nombre valide.", false);
     *   }
     */
    public String getMargeTexte() {
        return champMarge.getText().trim();
    }

    /**
     * Retourne le bouton Charger.
     *
     * UTILISATION dans ConfigController :
     *   panel.getBtnCharger()
     *        .addActionListener(e -> chargerMarge());
     */
    public JButton getBtnCharger() { return btnCharger; }

    /**
     * Retourne le bouton Enregistrer.
     *
     * UTILISATION dans ConfigController :
     *   panel.getBtnEnregistrer()
     *        .addActionListener(e -> sauvegarderMarge());
     */
    public JButton getBtnEnregistrer() { return btnEnregistrer; }
}