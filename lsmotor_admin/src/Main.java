import model.BDD;
import view.LoginView;

/**
 * ============================================================
 *  POINT D'ENTRÉE : Main.java
 * ============================================================
 *  C'est ici que tout démarre.
 *  Ce fichier est à la RACINE du projet (pas dans un package).
 *
 *  CE QUE TU DOIS FAIRE :
 *
 *  1. Déclarer les 4 constantes de connexion BDD :
 *     → public static final String SERVEUR = "localhost";
 *     → public static final String USER    = "root";
 *     → public static final String MDP     = "";
 *     → public static final String BDD_NOM = "ls_motors";
 *     ⚠️ Mets TES vraies valeurs ici
 *     ⚠️ public static car LoginController en a besoin
 *        pour rouvrir la connexion lors de la déconnexion
 *
 *  2. Écrire la méthode main :
 *     public static void main(String[] args)
 *
 *  3. Dans le main, TOUJOURS lancer Swing dans l'EDT :
 *     SwingUtilities.invokeLater(() -> {
 *         // tout le code Swing ici
 *     });
 *     ⚠️ Ne JAMAIS créer une JFrame en dehors de invokeLater
 *        → risque de bugs graphiques aléatoires
 *
 *  4. Dans le lambda invokeLater :
 *     a. Créer la BDD :
 *        BDD db = new BDD(SERVEUR, USER, MDP, BDD_NOM);
 *        db.seConnecter();
 *
 *     b. Vérifier que la connexion a réussi :
 *        if (db.getMaConnexion() == null) {
 *            JOptionPane.showMessageDialog(null,
 *                "Impossible de se connecter à la BDD.\n"
 *              + "Vérifiez les paramètres dans Main.java",
 *                "Erreur BDD",
 *                JOptionPane.ERROR_MESSAGE
 *            );
 *            return; // on arrête tout
 *        }
 *
 *     c. Créer la vue login :
 *        LoginView loginView = new LoginView();
 *
 *     d. Créer le controller login :
 *        new controller.LoginController(db, loginView);
 *
 *     e. Afficher la vue :
 *        loginView.setVisible(true);
 *
 * ============================================================
 */
public class Main {

    // Étape 1 : 4 constantes de connexion BDD

    // Étape 2-5 : méthode main avec invokeLater
}