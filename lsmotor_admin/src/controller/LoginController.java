package controller;

import model.BDD;
import model.Utilisateur;
import model.UtilisateurModel;
import view.LoginView;
import view.MainView;

/**
 * ============================================================
 *  CONTROLLER : LoginController
 * ============================================================
 *  Rôle : Fait le lien entre LoginView et UtilisateurModel
 *         pour authentifier l'administrateur.
 *
 *  ATTRIBUTS À DÉCLARER :
 *  ──────────────────────
 *  → private BDD              db;
 *  → private LoginView        vue;
 *  → private UtilisateurModel modele;
 *
 *  CONSTRUCTEUR :
 *  ──────────────
 *  → public LoginController(BDD db, LoginView vue)
 *
 *  DANS LE CONSTRUCTEUR, FAIRE 3 CHOSES :
 *
 *  1. Stocker les attributs :
 *     this.db     = db;
 *     this.vue    = vue;
 *     this.modele = new UtilisateurModel(db);
 *
 *  2. Brancher le bouton connexion :
 *     vue.getBtnConnexion()
 *        .addActionListener(e -> seConnecter());
 *
 *  3. Brancher la touche Entrée sur les deux champs :
 *     → Sur le champ email ET le champ mot de passe
 *     → Utilise un KeyAdapter :
 *        vue.getChampEmail().addKeyListener(
 *            new java.awt.event.KeyAdapter() {
 *                public void keyPressed(
 *                        java.awt.event.KeyEvent e) {
 *                    if (e.getKeyCode() ==
 *                            java.awt.event.KeyEvent.VK_ENTER){
 *                        seConnecter();
 *                    }
 *                }
 *            }
 *        );
 *     → Même chose sur getChampMotDePasse()
 *
 * ============================================================
 *  MÉTHODE : seConnecter()
 * ============================================================
 *  Signature : private void seConnecter()
 *
 *  COMMENT FAIRE (dans l'ordre) :
 *
 *  1. Cacher l'éventuel message d'erreur précédent :
 *     vue.cacherErreur();
 *
 *  2. Récupérer les valeurs saisies :
 *     String email = vue.getEmail();
 *     String mdp   = vue.getMotDePasse();
 *
 *  3. Vérifier que les champs ne sont pas vides :
 *     if (email.isEmpty() || mdp.isEmpty()) {
 *         vue.afficherErreur(
 *             "Veuillez remplir tous les champs."
 *         );
 *         return;
 *     }
 *
 *  4. Appeler le modèle pour vérifier en BDD :
 *     Utilisateur admin =
 *         modele.verifierConnexion(email, mdp);
 *
 *  5a. Si la connexion réussit (admin != null) :
 *      → Créer et afficher MainView :
 *         MainView mainView = new MainView(db, admin);
 *         mainView.setVisible(true);
 *      → Fermer LoginView :
 *         vue.dispose();
 *
 *  5b. Si la connexion échoue (admin == null) :
 *      → Afficher l'erreur dans la vue :
 *         vue.afficherErreur(
 *             "Email ou mot de passe incorrect."
 *         );
 *      → NE PAS fermer la fenêtre
 *
 * ============================================================
 */
public class LoginController {

    // Déclare tes 3 attributs ici

    // Constructeur :
    // → Stocker les attributs
    // → Brancher le bouton connexion → seConnecter()
    // → Brancher la touche Entrée sur les deux champs

    // Méthode seConnecter()
}