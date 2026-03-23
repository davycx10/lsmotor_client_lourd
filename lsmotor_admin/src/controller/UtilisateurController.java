package controller;

import model.BDD;
import model.POJOs.Utilisateur;
import model.UtilisateurModel;
import view.UtilisateurPanel;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class UtilisateurController {

    // ── Attributs ─────────────────────────────────────────────
    private UtilisateurPanel vue;
    private UtilisateurModel modele;

    // ── Constructeur ──────────────────────────────────────────
    public UtilisateurController(BDD uneDb, UtilisateurPanel vue) {
        this.vue    = vue;
        this.modele = new UtilisateurModel(uneDb);

        // Les boutons appellent les méthodes du controller
        vue.getBtnAjouter()
                .addActionListener(e -> ajouter());
        vue.getBtnModifier()
                .addActionListener(e -> modifier());
        vue.getBtnSupprimer()
                .addActionListener(e -> supprimer());

        // addKeyListener (pas addActionListener)
        vue.getChampRecherche().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                rechercher();
            }
        });

        // Appelle remplirFormulaire() du controller
        vue.getTable()
                .getSelectionModel()
                .addListSelectionListener(e -> {
                    if (!e.getValueIsAdjusting()) {
                        remplirFormulaire();
                    }
                });

        // Charge les données au démarrage
        chargerTableau();
    }

    // ============================================================
    //  chargerTableau()
    // ============================================================
    // Méthode séparée → réutilisable après chaque modification
    private void chargerTableau() {
        List<Utilisateur> liste = modele.getAll();

        Object[][] data = new Object[liste.size()][6];
        for (int i = 0; i < liste.size(); i++) {
            Utilisateur u = liste.get(i);
            data[i][0] = u.getId();           // caché
            data[i][1] = u.getNom();
            data[i][2] = u.getPrenom();
            data[i][3] = u.getEmail();
            data[i][4] = u.getRole();
            data[i][5] = u.getDiscordPseudo();
        }
        vue.majTableau(data);
    }

    // ============================================================
    //  rechercher()
    // ============================================================
    private void rechercher() {
        String terme = vue.getChampRecherche().getText().trim();

        List<Utilisateur> liste;
        if (terme.isEmpty()) {
            liste = modele.getAll();
        } else {
            liste = modele.rechercher(terme);
        }

        Object[][] data = new Object[liste.size()][6];
        for (int i = 0; i < liste.size(); i++) {
            Utilisateur u = liste.get(i);
            data[i][0] = u.getId();
            data[i][1] = u.getNom();
            data[i][2] = u.getPrenom();
            data[i][3] = u.getEmail();
            data[i][4] = u.getRole();
            data[i][5] = u.getDiscordPseudo();
        }
        vue.majTableau(data);
    }

    // ============================================================
    //  ajouter()
    // ============================================================
    private void ajouter() {
        // Lire les champs du formulaire
        String nom     = vue.getNom();
        String prenom  = vue.getPrenom();
        String email   = vue.getEmail();
        String mdp     = vue.getMotDePasse();
        String role    = vue.getRole();
        String discord = vue.getDiscord();

        // Valider les champs obligatoires
        if (nom.isEmpty() || prenom.isEmpty()
                || email.isEmpty() || mdp.isEmpty()) {
            vue.afficherErreur(
                    "Nom, prénom, email et mot de passe sont obligatoires."
            );
            return;
        }

        // Créer l'objet Utilisateur avec les valeurs saisies
        Utilisateur u = new Utilisateur();
        u.setNom(nom);
        u.setPrenom(prenom);
        u.setEmail(email);
        u.setPasswrd(mdp);
        u.setRole(role);
        u.setDiscordPseudo(discord);

        // Appeler le modèle
        boolean ok = modele.ajouter(u);
        if (ok) {
            chargerTableau();
            vue.viderFormulaire();
        } else {
            vue.afficherErreur(
                    "Erreur : cet email existe peut-être déjà."
            );
        }
    }

    // ============================================================
    //  modifier()
    // ============================================================
    private void modifier() {
        // Vérifier qu'une ligne est sélectionnée
        if (vue.getLigneSelectionnee() == -1) {
            vue.afficherErreur(
                    "Sélectionnez un utilisateur à modifier."
            );
            return;
        }

        // Récupérer l'ID depuis la colonne 0 (cachée)
        int id = (int) vue.getValeurColonne(0);

        String nom     = vue.getNom();
        String prenom  = vue.getPrenom();
        String email   = vue.getEmail();
        String mdp     = vue.getMotDePasse();
        String role    = vue.getRole();
        String discord = vue.getDiscord();

        // Valider les champs obligatoires (mdp optionnel en modification)
        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty()) {
            vue.afficherErreur("Nom, prénom et email sont obligatoires.");
            return;
        }

        Utilisateur u = new Utilisateur();
        u.setId(id);
        u.setNom(nom);
        u.setPrenom(prenom);
        u.setEmail(email);
        u.setPasswrd(mdp); // si vide → UtilisateurModel ne le change pas
        u.setRole(role);
        u.setDiscordPseudo(discord);

        boolean ok = modele.modifier(u);
        if (ok) {
            chargerTableau();
            vue.viderFormulaire();
        } else {
            vue.afficherErreur("Erreur lors de la modification.");
        }
    }

    // ============================================================
    //  supprimer()
    // ============================================================
    private void supprimer() {
        if (vue.getLigneSelectionnee() == -1) {
            vue.afficherErreur(
                    "Sélectionnez un utilisateur à supprimer."
            );
            return;
        }

        int id = (int) vue.getValeurColonne(0);

        // Demander confirmation avant suppression définitive
        int choix = JOptionPane.showConfirmDialog(
                vue,
                "Supprimer cet utilisateur ?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (choix == JOptionPane.YES_OPTION) {
            boolean ok = modele.supprimer(id);
            if (ok) {
                chargerTableau();
                vue.viderFormulaire();
            } else {
                vue.afficherErreur(
                        "Impossible de supprimer : cet utilisateur "
                                + "a peut-être des ventes associées."
                );
            }
        }
    }

    // ============================================================
    //  remplirFormulaire()
    // ============================================================
    private void remplirFormulaire() {
        int row = vue.getLigneSelectionnee();
        if (row == -1) return;

        // Lire chaque colonne de la ligne sélectionnée
        String nom     = (String) vue.getValeurColonne(1);
        String prenom  = (String) vue.getValeurColonne(2);
        String email   = (String) vue.getValeurColonne(3);
        String role    = (String) vue.getValeurColonne(4);
        String discord = (String) vue.getValeurColonne(5);

        // Remplir les champs du formulaire dans la vue
        vue.remplirFormulaire(nom, prenom, email, role, discord);
    }
}