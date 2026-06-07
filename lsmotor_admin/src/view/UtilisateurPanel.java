package view;

import javax.swing.*;
import java.awt.*;

/**
 * ============================================================
 *  VUE : UtilisateurPanel – Gestion des utilisateurs
 * ============================================================
 *  Hérite de BaseCrudPanel qui fournit déjà :
 *    → Le titre "Utilisateurs" en haut
 *    → La barre de recherche
 *    → Le tableau JTable
 *    → Le panel formulaire vide à droite
 *
 *  Cette classe ajoute uniquement le formulaire droite avec :
 *    → Champ Nom
 *    → Champ Prénom
 *    → Champ Email
 *    → Champ Mot de passe
 *    → ComboBox Rôle (admin / employe / joueur)
 *    → Champ Discord (optionnel)
 *    → Boutons Ajouter / Modifier / Supprimer / Vider
 *
 *  COLONNES DU TABLEAU (depuis la table `utilisateur`) :
 *    ID (caché) | Nom | Prénom | Email | Rôle | Discord
 *
 *  CORRESPONDANCE SQL :
 *    champNom        → utilisateur.Nom
 *    champPrenom     → utilisateur.Prenom
 *    champEmail      → utilisateur.Email
 *    champMotDePasse → utilisateur.Passwrd
 *    comboRole       → utilisateur.Role
 *    champDiscord    → utilisateur.DiscordPseudo
 * ============================================================
 */
public class UtilisateurPanel extends BaseCrudPanel {

    // ─────────────────────────────────────────────────────────
    //  CHAMPS DU FORMULAIRE
    //  Privés ici, exposés uniquement via getters.
    //  Le controller les lit via getNom(), getEmail(), etc.
    // ─────────────────────────────────────────────────────────
    private JTextField     champNom;
    private JTextField     champPrenom;
    private JTextField     champEmail;
    private JPasswordField champMotDePasse;
    private JComboBox<String> comboRole;
    private JTextField     champDiscord;

    // ─────────────────────────────────────────────────────────
    //  BOUTONS D'ACTION
    //  Privés, exposés via getBtnAjouter() etc.
    //  Le controller y attache les ActionListeners.
    // ─────────────────────────────────────────────────────────
    private JButton btnAjouter;
    private JButton btnModifier;
    private JButton btnSupprimer;

    // ─────────────────────────────────────────────────────────
    //  CONSTRUCTEUR
    // ─────────────────────────────────────────────────────────

    public UtilisateurPanel() {
        // Appelle BaseCrudPanel avec le titre et les colonnes
        // La colonne ID est à l'index 0, elle sera cachée
        super("Utilisateurs", new String[]{
            "ID", "Nom", "Prénom", "Email", "Rôle", "Discord"
        });

        // Cache la colonne ID (index 0)
        // L'ID reste accessible via getValeurColonne(0)
        // mais n'est pas visible à l'écran
        cacherColonne(0);

        // Remplir le panelFormulaire hérité de BaseCrudPanel
        construireFormulaire();
    }

    // ─────────────────────────────────────────────────────────
    //  CONSTRUCTION DU FORMULAIRE
    // ─────────────────────────────────────────────────────────

    /**
     * Remplit le panelFormulaire (hérité de BaseCrudPanel)
     * avec les champs spécifiques aux utilisateurs.
     *
     * Ordre visuel de haut en bas :
     *   Titre → Nom → Prénom → Email → Mot de passe
     *   → Rôle → Discord → [séparateur] → boutons
     */
    private void construireFormulaire() {

        // ── Titre du formulaire ───────────────────────────────
        panelFormulaire.add(creerTitreFormulaire("Détails utilisateur"));
        panelFormulaire.add(Box.createRigidArea(new Dimension(0, 20)));

        // ── Nom ───────────────────────────────────────────────
        panelFormulaire.add(creerLabelFormulaire("Nom"));
        panelFormulaire.add(Box.createRigidArea(new Dimension(0, 5)));
        champNom = creerChampTexte();
        panelFormulaire.add(champNom);
        panelFormulaire.add(Box.createRigidArea(new Dimension(0, 12)));

        // ── Prénom ────────────────────────────────────────────
        panelFormulaire.add(creerLabelFormulaire("Prénom"));
        panelFormulaire.add(Box.createRigidArea(new Dimension(0, 5)));
        champPrenom = creerChampTexte();
        panelFormulaire.add(champPrenom);
        panelFormulaire.add(Box.createRigidArea(new Dimension(0, 12)));

        // ── Email ─────────────────────────────────────────────
        panelFormulaire.add(creerLabelFormulaire("Email"));
        panelFormulaire.add(Box.createRigidArea(new Dimension(0, 5)));
        champEmail = creerChampTexte();
        panelFormulaire.add(champEmail);
        panelFormulaire.add(Box.createRigidArea(new Dimension(0, 12)));

        // ── Mot de passe ──────────────────────────────────────
        panelFormulaire.add(creerLabelFormulaire("Mot de passe"));
        panelFormulaire.add(Box.createRigidArea(new Dimension(0, 5)));
        champMotDePasse = new JPasswordField();
        champMotDePasse.setAlignmentX(Component.LEFT_ALIGNMENT);
        Theme.stylePasswordField(champMotDePasse);
        panelFormulaire.add(champMotDePasse);
        panelFormulaire.add(Box.createRigidArea(new Dimension(0, 12)));

        // ── Rôle (JComboBox) ──────────────────────────────────
        panelFormulaire.add(creerLabelFormulaire("Rôle"));
        panelFormulaire.add(Box.createRigidArea(new Dimension(0, 5)));
        // Les valeurs correspondent exactement à l'ENUM SQL :
        // ENUM('admin','employe','joueur')
        comboRole = new JComboBox<>(
            new String[]{"joueur", "employe", "admin"}
        );
        comboRole.setAlignmentX(Component.LEFT_ALIGNMENT);
        Theme.styleComboBox(comboRole);
        panelFormulaire.add(comboRole);
        panelFormulaire.add(Box.createRigidArea(new Dimension(0, 12)));

        // ── Pseudo Discord (optionnel) ────────────────────────
        panelFormulaire.add(
            creerLabelFormulaire("Pseudo Discord (optionnel)")
        );
        panelFormulaire.add(Box.createRigidArea(new Dimension(0, 5)));
        champDiscord = creerChampTexte();
        panelFormulaire.add(champDiscord);
        panelFormulaire.add(Box.createRigidArea(new Dimension(0, 22)));

        // ── Séparateur avant les boutons ──────────────────────
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
        // Créé via la méthode utilitaire de BaseCrudPanel
        JButton btnVider = creerBoutonVider();
        // Action directement dans la vue (pas besoin du controller)
        btnVider.addActionListener(e -> viderFormulaire());
        panelFormulaire.add(btnVider);

        // Espace flexible en bas pour tout pousser vers le haut
        panelFormulaire.add(Box.createVerticalGlue());
    }

    // ─────────────────────────────────────────────────────────
    //  MÉTHODES PUBLIQUES – appelées par UtilisateurController
    // ─────────────────────────────────────────────────────────

    /**
     * Vide tous les champs du formulaire.
     * Appelée par le bouton "Vider" et après un ajout réussi.
     * Aussi appelée par le controller : panel.viderFormulaire()
     */
    public void viderFormulaire() {
        champNom.setText("");
        champPrenom.setText("");
        champEmail.setText("");
        champMotDePasse.setText("");
        champDiscord.setText("");
        comboRole.setSelectedIndex(0); // Remet sur "joueur"
        table.clearSelection();        // Désélectionne le tableau
    }

    /**
     * Remplit le formulaire avec les données d'une ligne
     * sélectionnée dans le tableau.
     * Appelée par UtilisateurController dans le
     * ListSelectionListener quand l'utilisateur clique
     * sur une ligne.
     *
     * @param nom      Valeur de utilisateur.Nom
     * @param prenom   Valeur de utilisateur.Prenom
     * @param email    Valeur de utilisateur.Email
     * @param role     Valeur de utilisateur.Role
     *                 ("admin" | "employe" | "joueur")
     * @param discord  Valeur de utilisateur.DiscordPseudo
     *
     * NOTE : Le mot de passe n'est JAMAIS pré-rempli
     *        pour des raisons de sécurité.
     *        Si le mot de passe est laissé vide lors
     *        d'une modification, le controller ne le
     *        change pas en BDD.
     */
    public void remplirFormulaire(String nom, String prenom,
                                   String email, String role,
                                   String discord) {
        champNom.setText(nom);
        champPrenom.setText(prenom);
        champEmail.setText(email);
        champMotDePasse.setText(""); // Jamais pré-rempli
        champDiscord.setText(discord != null ? discord : "");

        // Sélectionner le bon rôle dans le JComboBox
        for (int i = 0; i < comboRole.getItemCount(); i++) {
            if (comboRole.getItemAt(i).equals(role)) {
                comboRole.setSelectedIndex(i);
                break;
            }
        }
    }

    // ─────────────────────────────────────────────────────────
    //  GETTERS CHAMPS – lus par UtilisateurController
    // ─────────────────────────────────────────────────────────

    /** Retourne la valeur du champ Nom */
    public String getNom() {
        return champNom.getText().trim();
    }

    /** Retourne la valeur du champ Prénom */
    public String getPrenom() {
        return champPrenom.getText().trim();
    }

    /** Retourne la valeur du champ Email */
    public String getEmail() {
        return champEmail.getText().trim();
    }

    /**
     * Retourne le mot de passe saisi.
     * Converti en String depuis char[] (JPasswordField).
     * Retourne une chaîne vide si non rempli.
     */
    public String getMotDePasse() {
        return new String(champMotDePasse.getPassword());
    }

    /**
     * Retourne le rôle sélectionné dans le JComboBox.
     * Valeurs possibles : "admin", "employe", "joueur"
     * Correspond exactement à l'ENUM SQL.
     */
    public String getRole() {
        return (String) comboRole.getSelectedItem();
    }

    /** Retourne le pseudo Discord (peut être vide) */
    public String getDiscord() {
        return champDiscord.getText().trim();
    }

    // ─────────────────────────────────────────────────────────
    //  GETTERS BOUTONS – le controller y attache les listeners
    // ─────────────────────────────────────────────────────────

    /**
     * Retourne le bouton Ajouter.
     *
     * UTILISATION dans UtilisateurController :
     *   panel.getBtnAjouter()
     *        .addActionListener(e -> ajouter());
     */
    public JButton getBtnAjouter()   { return btnAjouter;   }

    /**
     * Retourne le bouton Modifier.
     *
     * UTILISATION dans UtilisateurController :
     *   panel.getBtnModifier()
     *        .addActionListener(e -> modifier());
     */
    public JButton getBtnModifier()  { return btnModifier;  }

    /**
     * Retourne le bouton Supprimer.
     *
     * UTILISATION dans UtilisateurController :
     *   panel.getBtnSupprimer()
     *        .addActionListener(e -> supprimer());
     */
    public JButton getBtnSupprimer() { return btnSupprimer; }
}