package view;

import javax.swing.*;
import java.awt.*;

/**
 * ============================================================
 *  VUE : MarquePanel – Gestion des marques automobiles
 * ============================================================
 *  Hérite de BaseCrudPanel.
 *  Quasi identique à CategoriePanel mais pour les marques.
 *
 *  Table SQL concernée : `marque`
 *    ID  → caché dans le tableau (index 0)
 *    Nom → affiché + éditable dans le formulaire
 *
 *  FORMULAIRE DROITE :
 *    → Champ "Nom de la marque"
 *    → Compteur de marques (mis à jour par le controller)
 *    → Avertissement suppression
 *    → Boutons Ajouter / Modifier / Supprimer / Vider
 *
 *  COLONNES DU TABLEAU :
 *    ID (caché) | Nom de la marque
 *
 *  EXEMPLES DE MARQUES GTA V RP :
 *    Pegassi, Grotti, Dewbauchee, Ocelot, Bravado,
 *    Pfister, Truffade, Vapid, Annis, Benefactor...
 * ============================================================
 */
public class MarquePanel extends BaseCrudPanel {

    // ─────────────────────────────────────────────────────────
    //  CHAMPS DU FORMULAIRE
    // ─────────────────────────────────────────────────────────

    /** Champ texte pour saisir/modifier le nom */
    private JTextField champNom;

    /**
     * Label affichant le nombre total de marques.
     * Mis à jour par MarqueController après chaque opération.
     * Ex : "52 marques enregistrées"
     */
    private JLabel lblCompteur;

    // ─────────────────────────────────────────────────────────
    //  BOUTONS D'ACTION
    // ─────────────────────────────────────────────────────────

    private JButton btnAjouter;
    private JButton btnModifier;
    private JButton btnSupprimer;

    // ─────────────────────────────────────────────────────────
    //  CONSTRUCTEUR
    // ─────────────────────────────────────────────────────────

    public MarquePanel() {
        // Titre + colonnes
        // ID caché (index 0), Nom visible (index 1)
        super("Marques", new String[]{"ID", "Nom de la marque"});

        // Cache la colonne ID
        cacherColonne(0);

        // Remplit le panelFormulaire hérité
        construireFormulaire();
    }

    // ─────────────────────────────────────────────────────────
    //  CONSTRUCTION DU FORMULAIRE
    // ─────────────────────────────────────────────────────────

    private void construireFormulaire() {

        // ── Titre ─────────────────────────────────────────────
        panelFormulaire.add(creerTitreFormulaire("Marque"));
        panelFormulaire.add(Box.createRigidArea(new Dimension(0, 6)));

        // ── Compteur de marques ───────────────────────────────
        // Affiché sous le titre, mis à jour par le controller
        lblCompteur = new JLabel("— marques enregistrées");
        lblCompteur.setFont(Theme.FONT_SMALL);
        lblCompteur.setForeground(Theme.TEXT_GRAY);
        lblCompteur.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelFormulaire.add(lblCompteur);
        panelFormulaire.add(Box.createRigidArea(new Dimension(0, 20)));

        // ── Champ Nom ─────────────────────────────────────────
        panelFormulaire.add(creerLabelFormulaire("Nom de la marque"));
        panelFormulaire.add(Box.createRigidArea(new Dimension(0, 5)));
        champNom = creerChampTexte();
        panelFormulaire.add(champNom);
        panelFormulaire.add(Box.createRigidArea(new Dimension(0, 20)));

        // ── Avertissement suppression ─────────────────────────
        panelFormulaire.add(creerPanelAvertissement(
            "⚠  Ne pas supprimer une marque"
          + " déjà utilisée par des véhicules."
        ));
        panelFormulaire.add(Box.createRigidArea(new Dimension(0, 22)));

        // ── Séparateur ────────────────────────────────────────
        panelFormulaire.add(creerSeparateur());
        panelFormulaire.add(Box.createRigidArea(new Dimension(0, 16)));

        // ── Bouton Ajouter ────────────────────────────────────
        btnAjouter = new JButton("+ Ajouter");
        btnAjouter.setAlignmentX(Component.LEFT_ALIGNMENT);
        Theme.styleButtonPrimary(btnAjouter);
        panelFormulaire.add(btnAjouter);
        panelFormulaire.add(Box.createRigidArea(new Dimension(0, 8)));

        // ── Bouton Modifier ───────────────────────────────────
        btnModifier = new JButton("✎  Modifier");
        btnModifier.setAlignmentX(Component.LEFT_ALIGNMENT);
        Theme.styleButtonSecondary(btnModifier);
        panelFormulaire.add(btnModifier);
        panelFormulaire.add(Box.createRigidArea(new Dimension(0, 8)));

        // ── Bouton Supprimer ──────────────────────────────────
        btnSupprimer = new JButton("✕  Supprimer");
        btnSupprimer.setAlignmentX(Component.LEFT_ALIGNMENT);
        Theme.styleButtonDanger(btnSupprimer);
        panelFormulaire.add(btnSupprimer);
        panelFormulaire.add(Box.createRigidArea(new Dimension(0, 8)));

        // ── Bouton Vider ──────────────────────────────────────
        JButton btnVider = creerBoutonVider();
        btnVider.addActionListener(e -> viderFormulaire());
        panelFormulaire.add(btnVider);

        // Pousse tout vers le haut
        panelFormulaire.add(Box.createVerticalGlue());
    }

    // ─────────────────────────────────────────────────────────
    //  COMPOSANT UTILITAIRE
    // ─────────────────────────────────────────────────────────

    /**
     * Panneau d'avertissement orange arrondi.
     * Même design que CategoriePanel.
     * @param message Texte à afficher
     */
    private JPanel creerPanelAvertissement(String message) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
                );
                g2.setColor(new Color(0x33, 0x22, 0x00));
                g2.fillRoundRect(0, 0,
                    getWidth(), getHeight(), 8, 8);
                g2.setColor(new Color(0xFF, 0xB3, 0x40, 80));
                g2.setStroke(new BasicStroke(1f));
                g2.drawRoundRect(0, 0,
                    getWidth() - 1, getHeight() - 1, 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        panel.setOpaque(false);
        panel.setLayout(new BorderLayout());
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setBorder(
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        );

        JLabel lbl = new JLabel("<html>" + message + "</html>");
        lbl.setFont(Theme.FONT_SMALL);
        lbl.setForeground(Theme.WARNING);
        panel.add(lbl, BorderLayout.CENTER);

        return panel;
    }

    // ─────────────────────────────────────────────────────────
    //  MÉTHODES PUBLIQUES – appelées par MarqueController
    // ─────────────────────────────────────────────────────────

    /**
     * Vide le champ nom et désélectionne le tableau.
     * Appelée après un ajout, une modification, ou via
     * le bouton "Vider".
     */
    public void viderFormulaire() {
        champNom.setText("");
        table.clearSelection();
    }

    /**
     * Remplit le champ avec le nom de la ligne sélectionnée.
     *
     * Appelée par MarqueController dans le
     * ListSelectionListener :
     *
     *   panel.getTable()
     *        .getSelectionModel()
     *        .addListSelectionListener(e -> {
     *            int row = panel.getLigneSelectionnee();
     *            if (row == -1) return;
     *            String nom = (String) panel.getValeurColonne(1);
     *            panel.setNom(nom);
     *        });
     *
     * @param nom Le nom à afficher dans le champ
     */
    public void setNom(String nom) {
        champNom.setText(nom != null ? nom : "");
    }

    /**
     * Met à jour le compteur de marques affiché sous le titre.
     * Appelée par MarqueController après chaque chargement.
     *
     * EXEMPLE dans MarqueController.chargerTableau() :
     *   List<Marque> liste = modele.getAll();
     *   panel.setCompteur(liste.size());
     *   // Convertir en Object[][] et appeler majTableau()
     *
     * @param nb Nombre total de marques en base
     */
    public void setCompteur(int nb) {
        lblCompteur.setText(nb + " marque"
            + (nb > 1 ? "s" : "")
            + " enregistrée"
            + (nb > 1 ? "s" : ""));
    }

    // ─────────────────────────────────────────────────────────
    //  GETTERS CHAMPS – lus par MarqueController
    // ─────────────────────────────────────────────────────────

    /**
     * Retourne le nom saisi dans le formulaire.
     *
     * EXEMPLE dans MarqueController.ajouter() :
     *   String nom = panel.getNom();
     *   if (nom.isEmpty()) {
     *       panel.afficherErreur("Le nom ne peut pas être vide.");
     *       return;
     *   }
     *   boolean ok = modele.ajouter(nom);
     *   if (ok) {
     *       chargerTableau();
     *       panel.viderFormulaire();
     *   }
     */
    public String getNom() {
        return champNom.getText().trim();
    }

    // ─────────────────────────────────────────────────────────
    //  GETTERS BOUTONS – le controller y attache les listeners
    // ─────────────────────────────────────────────────────────

    /**
     * Retourne le bouton Ajouter.
     *
     * UTILISATION dans MarqueController :
     *   panel.getBtnAjouter()
     *        .addActionListener(e -> ajouter());
     */
    public JButton getBtnAjouter()   { return btnAjouter;   }

    /**
     * Retourne le bouton Modifier.
     *
     * UTILISATION dans MarqueController :
     *   panel.getBtnModifier()
     *        .addActionListener(e -> modifier());
     */
    public JButton getBtnModifier()  { return btnModifier;  }

    /**
     * Retourne le bouton Supprimer.
     *
     * UTILISATION dans MarqueController :
     *   panel.getBtnSupprimer()
     *        .addActionListener(e -> supprimer());
     */
    public JButton getBtnSupprimer() { return btnSupprimer; }
}