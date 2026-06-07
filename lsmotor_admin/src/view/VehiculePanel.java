package view;

import javax.swing.*;
import java.awt.*;

/**
 * ============================================================
 *  VUE : VehiculePanel – Gestion des véhicules
 * ============================================================
 *  Hérite de BaseCrudPanel.
 *  C'est le panneau le plus complet car un véhicule a
 *  plus de champs que les autres entités.
 *
 *  Table SQL concernée : `vehicule`
 *    ID           → caché (index 0)
 *    NomModele    → champNom
 *    ID_Marque    → comboMarque    (JComboBox peuplé par le controller)
 *    ID_Categorie → comboCategorie (JComboBox peuplé par le controller)
 *    PrixCatalogue→ champPrix
 *    Description  → champDescription (JTextArea)
 *    Image        → champImage (chemin du fichier)
 *    Actif        → checkActif (JCheckBox)
 *
 *  COLONNES DU TABLEAU :
 *    ID (caché) | Nom/Modèle | Prix GTA$ | Marque | Catégorie | Actif
 *
 *  PARTICULARITÉ :
 *    Les JComboBox marque et catégorie sont vides au départ.
 *    C'est VehiculeController qui les peuple en appelant
 *    getComboMarque().addItem(marque) et
 *    getComboCategorie().addItem(categorie)
 *    après avoir chargé les listes depuis la BDD.
 * ============================================================
 */
public class VehiculePanel extends BaseCrudPanel {

    // ─────────────────────────────────────────────────────────
    //  CHAMPS DU FORMULAIRE
    // ─────────────────────────────────────────────────────────

    /** NomModele – nom/modèle du véhicule */
    private JTextField champNom;

    /** PrixCatalogue – prix de base sans marge */
    private JTextField champPrix;

    /**
     * Sélection de la marque.
     * Type Object car on y met des objets Marque directement.
     * Marque.toString() retourne le nom → affiché dans le combo.
     *
     * PEUPLÉ PAR VehiculeController :
     *   List<Marque> marques = marqueModel.getAll();
     *   for (Marque m : marques) {
     *       panel.getComboMarque().addItem(m);
     *   }
     */
    private JComboBox<Object> comboMarque;

    /**
     * Sélection de la catégorie.
     * Même principe que comboMarque.
     * Categorie.toString() retourne le libellé.
     */
    private JComboBox<Object> comboCategorie;

    /**
     * Description textuelle du véhicule (multi-lignes).
     * Correspond à vehicule.Description (TEXT en SQL).
     */
    private JTextArea champDescription;

    /**
     * Chemin ou nom du fichier image.
     * Correspond à vehicule.Image (VARCHAR en SQL).
     * Ex : "pegassi_zentorno.jpg"
     */
    private JTextField champImage;

    /**
     * Véhicule actif ou non.
     * Correspond à vehicule.Actif (TINYINT 0/1 en SQL).
     * true = affiché sur le site, false = masqué.
     */
    private JCheckBox checkActif;

    // ─────────────────────────────────────────────────────────
    //  BOUTONS D'ACTION
    // ─────────────────────────────────────────────────────────

    private JButton btnAjouter;
    private JButton btnModifier;
    private JButton btnSupprimer;

    // ─────────────────────────────────────────────────────────
    //  CONSTRUCTEUR
    // ─────────────────────────────────────────────────────────

    public VehiculePanel() {
        super("Véhicules", new String[]{
            "ID", "Nom / Modèle", "Prix GTA $",
            "Marque", "Catégorie", "Actif"
        });

        // Cache la colonne ID (index 0)
        cacherColonne(0);

        // Ajuste les largeurs des colonnes visibles
        configurerLargeursColonnes();

        // Remplit le formulaire droite
        construireFormulaire();
    }

    // ─────────────────────────────────────────────────────────
    //  CONFIGURATION DES COLONNES
    // ─────────────────────────────────────────────────────────

    /**
     * Définit des largeurs préférées pour chaque colonne
     * visible afin que le tableau soit plus lisible.
     * Appelé après cacherColonne(0).
     */
    private void configurerLargeursColonnes() {
        // Index des colonnes visibles après avoir caché l'ID :
        // 1=Nom, 2=Prix, 3=Marque, 4=Catégorie, 5=Actif
        int[][] largeurs = {
            {1, 200},  // Nom/Modèle : large
            {2, 110},  // Prix       : moyen
            {3, 130},  // Marque     : moyen
            {4, 120},  // Catégorie  : moyen
            {5, 60}    // Actif      : petit
        };
        for (int[] col : largeurs) {
            table.getColumnModel()
                 .getColumn(col[0])
                 .setPreferredWidth(col[1]);
        }
    }

    // ─────────────────────────────────────────────────────────
    //  CONSTRUCTION DU FORMULAIRE
    // ─────────────────────────────────────────────────────────

    private void construireFormulaire() {

        // ── Titre ─────────────────────────────────────────────
        panelFormulaire.add(creerTitreFormulaire("Véhicule"));
        panelFormulaire.add(Box.createRigidArea(new Dimension(0, 20)));

        // ── Nom / Modèle ──────────────────────────────────────
        panelFormulaire.add(creerLabelFormulaire("Nom / Modèle"));
        panelFormulaire.add(Box.createRigidArea(new Dimension(0, 5)));
        champNom = creerChampTexte();
        panelFormulaire.add(champNom);
        panelFormulaire.add(Box.createRigidArea(new Dimension(0, 12)));

        // ── Prix catalogue ────────────────────────────────────
        panelFormulaire.add(
            creerLabelFormulaire("Prix catalogue GTA $ (sans marge)")
        );
        panelFormulaire.add(Box.createRigidArea(new Dimension(0, 5)));
        champPrix = creerChampTexte();
        panelFormulaire.add(champPrix);
        panelFormulaire.add(Box.createRigidArea(new Dimension(0, 12)));

        // ── Marque (JComboBox) ────────────────────────────────
        panelFormulaire.add(creerLabelFormulaire("Marque"));
        panelFormulaire.add(Box.createRigidArea(new Dimension(0, 5)));
        comboMarque = new JComboBox<>();
        comboMarque.setAlignmentX(Component.LEFT_ALIGNMENT);
        Theme.styleComboBox(comboMarque);
        panelFormulaire.add(comboMarque);
        panelFormulaire.add(Box.createRigidArea(new Dimension(0, 12)));

        // ── Catégorie (JComboBox) ─────────────────────────────
        panelFormulaire.add(creerLabelFormulaire("Catégorie"));
        panelFormulaire.add(Box.createRigidArea(new Dimension(0, 5)));
        comboCategorie = new JComboBox<>();
        comboCategorie.setAlignmentX(Component.LEFT_ALIGNMENT);
        Theme.styleComboBox(comboCategorie);
        panelFormulaire.add(comboCategorie);
        panelFormulaire.add(Box.createRigidArea(new Dimension(0, 12)));

        // ── Image ─────────────────────────────────────────────
        panelFormulaire.add(
            creerLabelFormulaire("Nom du fichier image")
        );
        panelFormulaire.add(Box.createRigidArea(new Dimension(0, 5)));
        champImage = creerChampTexte();
        panelFormulaire.add(champImage);
        panelFormulaire.add(Box.createRigidArea(new Dimension(0, 12)));

        // ── Description (JTextArea multi-lignes) ──────────────
        panelFormulaire.add(
            creerLabelFormulaire("Description (optionnel)")
        );
        panelFormulaire.add(Box.createRigidArea(new Dimension(0, 5)));
        panelFormulaire.add(creerZoneDescription());
        panelFormulaire.add(Box.createRigidArea(new Dimension(0, 12)));

        // ── Actif (JCheckBox) ─────────────────────────────────
        panelFormulaire.add(creerPanelActif());
        panelFormulaire.add(Box.createRigidArea(new Dimension(0, 20)));

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

        panelFormulaire.add(Box.createVerticalGlue());
    }

    // ─────────────────────────────────────────────────────────
    //  COMPOSANTS INTERNES
    // ─────────────────────────────────────────────────────────

    /**
     * Crée la zone de texte multi-lignes pour la description.
     * Enveloppée dans un JScrollPane stylé.
     */
    private JScrollPane creerZoneDescription() {
        champDescription = new JTextArea(3, 1);
        champDescription.setBackground(Theme.INPUT_BG);
        champDescription.setForeground(Theme.TEXT_WHITE);
        champDescription.setCaretColor(Theme.GOLD);
        champDescription.setFont(Theme.FONT_INPUT);
        champDescription.setLineWrap(true);
        champDescription.setWrapStyleWord(true);
        champDescription.setBorder(
            BorderFactory.createEmptyBorder(6, 8, 6, 8)
        );

        // Focus : bordure dorée
        champDescription.addFocusListener(
            new java.awt.event.FocusAdapter() {
                @Override
                public void focusGained(java.awt.event.FocusEvent e) {
                    scrollDesc.setBorder(
                        BorderFactory.createLineBorder(Theme.GOLD, 1)
                    );
                }
                @Override
                public void focusLost(java.awt.event.FocusEvent e) {
                    scrollDesc.setBorder(
                        BorderFactory.createLineBorder(Theme.BORDER, 1)
                    );
                }
            }
        );

        scrollDesc = new JScrollPane(champDescription);
        scrollDesc.setBorder(
            BorderFactory.createLineBorder(Theme.BORDER, 1)
        );
        scrollDesc.getViewport().setBackground(Theme.INPUT_BG);
        scrollDesc.setBackground(Theme.INPUT_BG);
        scrollDesc.setMaximumSize(new Dimension(Integer.MAX_VALUE, 75));
        scrollDesc.setAlignmentX(Component.LEFT_ALIGNMENT);

        return scrollDesc;
    }

    // Référence au scroll de la description pour changer sa bordure
    private JScrollPane scrollDesc;

    /**
     * Crée le panel contenant la checkbox "Véhicule actif".
     * Fond transparent, aligné à gauche.
     */
    private JPanel creerPanelActif() {
        JPanel panel = new JPanel(
            new FlowLayout(FlowLayout.LEFT, 0, 0)
        );
        panel.setBackground(Theme.DARK_SURFACE);
        panel.setOpaque(false);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        checkActif = new JCheckBox("Véhicule actif (visible sur le site)");
        checkActif.setBackground(Theme.DARK_SURFACE);
        checkActif.setForeground(Theme.TEXT_WHITE);
        checkActif.setFont(Theme.FONT_SMALL);
        checkActif.setFocusPainted(false);
        checkActif.setSelected(true); // Actif par défaut
        checkActif.setOpaque(false);

        panel.add(checkActif);
        return panel;
    }

    // ─────────────────────────────────────────────────────────
    //  MÉTHODES PUBLIQUES – appelées par VehiculeController
    // ─────────────────────────────────────────────────────────

    /**
     * Vide tous les champs et désélectionne le tableau.
     * Appelée après un ajout, modification, ou via "Vider".
     */
    public void viderFormulaire() {
        champNom.setText("");
        champPrix.setText("");
        champImage.setText("");
        champDescription.setText("");
        checkActif.setSelected(true);
        if (comboMarque.getItemCount()    > 0)
            comboMarque.setSelectedIndex(0);
        if (comboCategorie.getItemCount() > 0)
            comboCategorie.setSelectedIndex(0);
        table.clearSelection();
    }

    /**
     * Remplit le formulaire avec les données du véhicule
     * sélectionné dans le tableau.
     *
     * Appelée par VehiculeController dans le
     * ListSelectionListener.
     *
     * @param nom         vehicule.NomModele
     * @param prix        vehicule.PrixCatalogue (en String)
     * @param image       vehicule.Image
     * @param description vehicule.Description
     * @param actif       vehicule.Actif (true = 1)
     * @param idxMarque   Index dans le JComboBox marque
     *                    (à trouver par l'ID dans le controller)
     * @param idxCategorie Index dans le JComboBox catégorie
     */
    public void remplirFormulaire(String nom, String prix,
                                   String image, String description,
                                   boolean actif,
                                   int idxMarque, int idxCategorie) {
        champNom.setText(nom != null ? nom : "");
        champPrix.setText(prix != null ? prix : "");
        champImage.setText(image != null ? image : "");
        champDescription.setText(
            description != null ? description : ""
        );
        checkActif.setSelected(actif);

        if (idxMarque >= 0
                && idxMarque < comboMarque.getItemCount()) {
            comboMarque.setSelectedIndex(idxMarque);
        }
        if (idxCategorie >= 0
                && idxCategorie < comboCategorie.getItemCount()) {
            comboCategorie.setSelectedIndex(idxCategorie);
        }
    }

    // ─────────────────────────────────────────────────────────
    //  GETTERS CHAMPS – lus par VehiculeController
    // ─────────────────────────────────────────────────────────

    /** vehicule.NomModele */
    public String getNom() {
        return champNom.getText().trim();
    }

    /**
     * vehicule.PrixCatalogue
     * Retourné en String → le controller le parse en double :
     *   double prix = Double.parseDouble(panel.getPrix());
     */
    public String getPrix() {
        return champPrix.getText().trim();
    }

    /** vehicule.Image */
    public String getImage() {
        return champImage.getText().trim();
    }

    /** vehicule.Description */
    public String getDescription() {
        return champDescription.getText().trim();
    }

    /**
     * vehicule.Actif
     * true  → stocker 1 en BDD
     * false → stocker 0 en BDD
     */
    public boolean isActif() {
        return checkActif.isSelected();
    }

    /**
     * Retourne l'objet Marque sélectionné dans le combo.
     * Le controller le caste en Marque pour récupérer l'ID :
     *
     *   Object sel = panel.getMarqueSelectionnee();
     *   if (sel instanceof Marque) {
     *       int idMarque = ((Marque) sel).getId();
     *   }
     */
    public Object getMarqueSelectionnee() {
        return comboMarque.getSelectedItem();
    }

    /**
     * Retourne l'objet Categorie sélectionné.
     * Même principe que getMarqueSelectionnee().
     *
     *   Object sel = panel.getCategorieSelectionnee();
     *   if (sel instanceof Categorie) {
     *       int idCat = ((Categorie) sel).getId();
     *   }
     */
    public Object getCategorieSelectionnee() {
        return comboCategorie.getSelectedItem();
    }

    // ─────────────────────────────────────────────────────────
    //  GETTERS COMBOS – peuplés par VehiculeController
    // ─────────────────────────────────────────────────────────

    /**
     * Retourne le JComboBox des marques.
     * VehiculeController l'utilise pour le peupler :
     *
     *   panel.getComboMarque().removeAllItems();
     *   for (Marque m : marques) {
     *       panel.getComboMarque().addItem(m);
     *   }
     */
    public JComboBox<Object> getComboMarque() {
        return comboMarque;
    }

    /**
     * Retourne le JComboBox des catégories.
     * Même utilisation que getComboMarque().
     */
    public JComboBox<Object> getComboCategorie() {
        return comboCategorie;
    }

    // ─────────────────────────────────────────────────────────
    //  GETTERS BOUTONS – le controller y attache les listeners
    // ─────────────────────────────────────────────────────────

    /**
     * UTILISATION dans VehiculeController :
     *   panel.getBtnAjouter()
     *        .addActionListener(e -> ajouter());
     */
    public JButton getBtnAjouter()   { return btnAjouter;   }

    /**
     * UTILISATION dans VehiculeController :
     *   panel.getBtnModifier()
     *        .addActionListener(e -> modifier());
     */
    public JButton getBtnModifier()  { return btnModifier;  }

    /**
     * UTILISATION dans VehiculeController :
     *   panel.getBtnSupprimer()
     *        .addActionListener(e -> supprimer());
     */
    public JButton getBtnSupprimer() { return btnSupprimer; }
}