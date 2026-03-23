package controller;

import model.BDD;
import model.POJOs.Utilisateur;
import model.UtilisateurModel;
import view.LoginView;
import view.MainView;

public class LoginController {

    // ── Attributs ─────────────────────────────────────────────
    private BDD              uneDb;
    private LoginView        vue;
    private UtilisateurModel modele;

    // ── Constructeur ──────────────────────────────────────────
    // ✅ Reçoit BDD + LoginView en paramètres
    public LoginController(BDD uneDb, LoginView vue) {
        this.uneDb  = uneDb;
        this.vue    = vue;
        // ✅ On crée le modèle ici avec new
        this.modele = new UtilisateurModel(uneDb);

        // ✅ Brancher le bouton connexion
        vue.getBtnConnexion()
                .addActionListener(e -> seConnecter());

        // ✅ Touche Entrée sur le champ email
        vue.getChampEmail().addKeyListener(
                new java.awt.event.KeyAdapter() {
                    @Override
                    public void keyPressed(java.awt.event.KeyEvent e) {
                        if (e.getKeyCode() ==
                                java.awt.event.KeyEvent.VK_ENTER) {
                            seConnecter();
                        }
                    }
                }
        );

        // ✅ Touche Entrée sur le champ mot de passe
        vue.getChampMotDePasse().addKeyListener(
                new java.awt.event.KeyAdapter() {
                    @Override
                    public void keyPressed(java.awt.event.KeyEvent e) {
                        if (e.getKeyCode() ==
                                java.awt.event.KeyEvent.VK_ENTER) {
                            seConnecter();
                        }
                    }
                }
        );
    }

    // ── Méthode seConnecter() ─────────────────────────────────
    private void seConnecter() {

        // 1. Cacher l'éventuel message d'erreur précédent
        vue.cacherErreur();

        // 2. Récupérer les valeurs saisies
        String email = vue.getEmail();
        String mdp   = vue.getMotDePasse();

        // 3. Vérifier que les champs ne sont pas vides
        if (email.isEmpty() || mdp.isEmpty()) {
            vue.afficherErreur(
                    "Veuillez remplir tous les champs."
            );
            return;
        }

        // 4. Vérifier en BDD (email + mdp + rôle admin)
        Utilisateur admin = modele.verifierConnexion(email, mdp);

        // 5a. Connexion réussie → ouvrir MainView
        if (admin != null) {
            MainView mainView = new MainView(uneDb, admin);
            mainView.setVisible(true);
            // Fermer la fenêtre de login
            vue.dispose();

            // 5b. Connexion échouée → afficher l'erreur
        } else {
            vue.afficherErreur(
                    "Email ou mot de passe incorrect."
            );
            // ⚠️ On ne ferme PAS la fenêtre
        }
    }
}