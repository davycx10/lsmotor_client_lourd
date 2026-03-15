package view;

import javax.swing.*;
import java.awt.*;

/**
 * ============================================================
 *  VUE : CategoriePanel – Gestion des catégories
 * ============================================================
 *  Hérite de BaseCrudPanel.
 *
 *  Table SQL concernée : `categorie`
 *    ID      → caché dans le tableau (index 0)
 *    Libelle → affiché + éditable dans le formulaire
 *
 *  FORMULAIRE DROITE (simple) :
 *    → Champ "Libellé de la catégorie"
 *    → Avertissement (ne pas supprimer si utilisée)
 *    → Boutons Ajouter / Modifier / Supprimer / Vider
 *
 *  COLONNES DU TABLEAU :
 *    ID (caché) | Libellé
 *
 *  EXEMPLES DE CATÉGORIES :
 *    Sport, SUV, Berline, Moto, Pickup, Muscle Car...
 * ============================================================
 */
public class CategoriePanel extends BaseCrudPanel {

    // ─────────────────────────────────────────────────────────
    //  CHAMPS DU FORMULAIRE
    // ─────────────────────────────────────────────────────────

    /** Champ texte pour saisir/modifier le libellé */
    private JTextField champLibelle;

    // ─────────────────────────────────────────────────────────
    //  BOUTONS D'ACTION
    // ─────────────────────────────────────────────────────────

    private JButton btnAjouter;
    private JButton btnModifier;
    private JButton btnSupprimer;

    // ─────────────────────────────────────────────────────────
    //  CONSTRUCTEUR
    // ─────────────────────────────────────────────────────────

    public CategoriePanel() {
        // Titre + colonnes du tableau
        // ID caché (index 0), Libellé visible (index 1)
        super("Catégories", new String[]{"ID", "Libellé"});

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
        panelFormulaire.add(creerTitreFormulaire("Catégorie"));
        panelFormulaire.add(Box.createRigidArea(new Dimension(0, 20)));

        // ── Champ libellé ─────────────────────────────────────
        panelFormulaire.add(creerLabelFormulaire("Libellé"));
        panelFormulaire.add(Box.createRigidArea(new Dimension(0, 5)));
        champLibelle = creerChampTexte();
        panelFormulaire.add(champLibelle);
        panelFormulaire.add(Box.createRigidArea(new Dimension(0, 20)));

        // ── Avertissement ─────────────────────────────────────
        // Rappel visuel : ne pas supprimer si des véhicules
        // utilisent cette catégorie (contrainte FK en BDD)
        JPanel panelAvert = creerPanelAvertissement(
            "⚠  Ne pas supprimer une catégorie"
          + " déjà utilisée par des véhicules."
        );
        panelFormulaire.add(panelAvert);
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
     * Crée un petit panneau d'avertissement orange arrondi.
     * Réutilisable dans MarquePanel aussi.
     *
     * @param message Le texte d'avertissement à afficher
     */
    private JPanel creerPanelAvertissement(String message) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                // Fond orange très transparent
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
                );
                // Fond orange très sombre
                g2.setColor(new Color(0x33, 0x22, 0x00));
                g2.fillRoundRect(0, 0,
                    getWidth(), getHeight(), 8, 8);
                // Bordure orange subtile
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
        panel.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

        JLabel lbl = new JLabel(
            "<html>" + message + "</html>"
        );
        lbl.setFont(Theme.FONT_SMALL);
        lbl.setForeground(Theme.WARNING);

        panel.add(lbl, BorderLayout.CENTER);
        return panel;
    }

    // ─────────────────────────────────────────────────────────
    //  MÉTHODES PUBLIQUES – appelées par CategorieController
    // ─────────────────────────────────────────────────────────

    /**
     * Vide le champ libellé et désélectionne le tableau.
     * Appelée après un ajout, une modification, ou via
     * le bouton "Vider".
     */
    public void viderFormulaire() {
        champLibelle.setText("");
        table.clearSelection();
    }

    /**
     * Remplit le champ avec le libellé de la ligne
     * sélectionnée dans le tableau.
     *
     * Appelée par CategorieController dans le
     * ListSelectionListener :
     *
     *   panel.getTable()
     *        .getSelectionModel()
     *        .addListSelectionListener(e -> {
     *            int row = panel.getLigneSelectionnee();
     *            if (row == -1) return;
     *            String lib = (String) panel.getValeurColonne(1);
     *            panel.setLibelle(lib);
     *        });
     *
     * @param libelle Le libellé à afficher dans le champ
     */
    public void setLibelle(String libelle) {
        champLibelle.setText(libelle != null ? libelle : "");
    }

    // ─────────────────────────────────────────────────────────
    //  GETTERS CHAMPS – lus par CategorieController
    // ─────────────────────────────────────────────────────────

    /**
     * Retourne le libellé saisi dans le formulaire.
     * Utilisé par CategorieController pour ajouter/modifier.
     *
     * EXEMPLE dans CategorieController.ajouter() :
     *   String lib = panel.getLibelle();
     *   if (lib.isEmpty()) { panel.afficherErreur("..."); return; }
     *   modele.ajouter(lib);
     */
    public String getLibelle() {
        return champLibelle.getText().trim();
    }

    // ─────────────────────────────────────────────────────────
    //  GETTERS BOUTONS – le controller y attache les listeners
    // ─────────────────────────────────────────────────────────

    /**
     * Retourne le bouton Ajouter.
     *
     * UTILISATION dans CategorieController :
     *   panel.getBtnAjouter()
     *        .addActionListener(e -> ajouter());
     */
    public JButton getBtnAjouter()   { return btnAjouter;   }

    /**
     * Retourne le bouton Modifier.
     *
     * UTILISATION dans CategorieController :
     *   panel.getBtnModifier()
     *        .addActionListener(e -> modifier());
     */
    public JButton getBtnModifier()  { return btnModifier;  }

    /**
     * Retourne le bouton Supprimer.
     *
     * UTILISATION dans CategorieController :
     *   panel.getBtnSupprimer()
     *        .addActionListener(e -> supprimer());
     */
    public JButton getBtnSupprimer() { return btnSupprimer; }
}