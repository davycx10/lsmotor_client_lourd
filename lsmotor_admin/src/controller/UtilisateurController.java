package controller;

import model.BDD;
import model.POJOs.Utilisateur;
import model.UtilisateurModel;
import view.UtilisateurPanel;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

/**
 * ============================================================
 *  CONTROLLER : UtilisateurController
 * ============================================================
 *  Rôle : Gère toutes les interactions de l'onglet Utilisateurs.
 *
 *  ATTRIBUTS À DÉCLARER :
 *  ──────────────────────
 *  → private UtilisateurPanel  vue;
 *  → private UtilisateurModel  modele;
 *
 *  CONSTRUCTEUR :
 *  ──────────────
 *  → public UtilisateurController(BDD db, UtilisateurPanel vue)
 *
 *  DANS LE CONSTRUCTEUR, FAIRE 5 CHOSES :
 *
 *  1. Stocker les attributs :
 *     this.vue    = vue;
 *     this.modele = new UtilisateurModel(db);
 *
 *  2. Brancher le bouton Ajouter :
 *     vue.getBtnAjouter()
 *        .addActionListener(e -> ajouter());
 *
 *  3. Brancher le bouton Modifier :
 *     vue.getBtnModifier()
 *        .addActionListener(e -> modifier());
 *
 *  4. Brancher le bouton Supprimer :
 *     vue.getBtnSupprimer()
 *        .addActionListener(e -> supprimer());
 *
 *  5. Brancher la barre de recherche (KeyListener) :
 *     vue.getChampRecherche().addKeyListener(
 *         new KeyAdapter() {
 *             public void keyReleased(KeyEvent e) {
 *                 rechercher();
 *             }
 *         }
 *     );
 *
 *  6. Brancher la sélection dans le tableau :
 *     vue.getTable()
 *        .getSelectionModel()
 *        .addListSelectionListener(e -> {
 *            if (!e.getValueIsAdjusting()) {
 *                remplirFormulaire();
 *            }
 *        });
 *     ⚠️ Le !e.getValueIsAdjusting() évite d'appeler
 *        remplirFormulaire() deux fois par clic
 *
 *  7. Charger les données initiales :
 *     chargerTableau();
 *
 * ============================================================
 *  MÉTHODE : chargerTableau()
 * ============================================================
 *  Signature : private void chargerTableau()
 *
 *  COMMENT FAIRE :
 *  1. List<Utilisateur> liste = modele.getAll();
 *
 *  2. Convertir en Object[][] :
 *     Object[][] data = new Object[liste.size()][6];
 *     for (int i = 0; i < liste.size(); i++) {
 *         Utilisateur u = liste.get(i);
 *         data[i][0] = u.getId();           // caché
 *         data[i][1] = u.getNom();
 *         data[i][2] = u.getPrenom();
 *         data[i][3] = u.getEmail();
 *         data[i][4] = u.getRole();
 *         data[i][5] = u.getDiscordPseudo();
 *     }
 *
 *  3. vue.majTableau(data);
 *
 * ============================================================
 *  MÉTHODE : rechercher()
 * ============================================================
 *  Signature : private void rechercher()
 *
 *  COMMENT FAIRE :
 *  1. String terme = vue.getChampRecherche().getText().trim();
 *
 *  2. List<Utilisateur> liste;
 *     if (terme.isEmpty()) {
 *         liste = modele.getAll();
 *     } else {
 *         liste = modele.rechercher(terme);
 *     }
 *
 *  3. Même conversion Object[][] que chargerTableau()
 *  4. vue.majTableau(data);
 *
 * ============================================================
 *  MÉTHODE : ajouter()
 * ============================================================
 *  Signature : private void ajouter()
 *
 *  COMMENT FAIRE :
 *  1. Récupérer les valeurs :
 *     String nom    = vue.getNom();
 *     String prenom = vue.getPrenom();
 *     String email  = vue.getEmail();
 *     String mdp    = vue.getMotDePasse();
 *     String role   = vue.getRole();
 *     String discord= vue.getDiscord();
 *
 *  2. Valider les champs obligatoires :
 *     if (nom.isEmpty() || prenom.isEmpty()
 *             || email.isEmpty() || mdp.isEmpty()) {
 *         vue.afficherErreur(
 *             "Nom, prénom, email et mot de passe "
 *           + "sont obligatoires."
 *         );
 *         return;
 *     }
 *
 *  3. Créer l'objet Utilisateur :
 *     Utilisateur u = new Utilisateur();
 *     u.setNom(nom);
 *     u.setPrenom(prenom);
 *     u.setEmail(email);
 *     u.setPasswrd(mdp);
 *     u.setRole(role);
 *     u.setDiscordPseudo(discord);
 *
 *  4. Appeler le modèle :
 *     boolean ok = modele.ajouter(u);
 *
 *  5a. Si ok :
 *      chargerTableau();
 *      vue.viderFormulaire();
 *
 *  5b. Si pas ok :
 *      vue.afficherErreur(
 *          "Erreur : cet email existe peut-être déjà."
 *      );
 *
 * ============================================================
 *  MÉTHODE : modifier()
 * ============================================================
 *  Signature : private void modifier()
 *
 *  COMMENT FAIRE :
 *  1. Vérifier qu'une ligne est sélectionnée :
 *     if (vue.getLigneSelectionnee() == -1) {
 *         vue.afficherErreur(
 *             "Sélectionnez un utilisateur à modifier."
 *         );
 *         return;
 *     }
 *
 *  2. Récupérer l'ID de la ligne sélectionnée :
 *     int id = (int) vue.getValeurColonne(0);
 *     ⚠️ Colonne 0 = ID (cachée mais toujours dans le modèle)
 *
 *  3. Récupérer les valeurs du formulaire (même que ajouter)
 *
 *  4. Valider les champs obligatoires (sans le mdp) :
 *     if (nom.isEmpty() || prenom.isEmpty()
 *             || email.isEmpty()) {
 *         vue.afficherErreur("...");
 *         return;
 *     }
 *
 *  5. Créer l'objet et appeler modele.modifier(u) :
 *     Utilisateur u = new Utilisateur();
 *     u.setId(id);
 *     u.setNom(nom);
 *     ... etc
 *     ⚠️ Si mdp vide → ne pas le changer en BDD
 *        (VehiculeModel.modifier gère les 2 cas)
 *
 *  6. Si ok → chargerTableau() + viderFormulaire()
 *     Sinon → vue.afficherErreur("...")
 *
 * ============================================================
 *  MÉTHODE : supprimer()
 * ============================================================
 *  Signature : private void supprimer()
 *
 *  COMMENT FAIRE :
 *  1. Vérifier sélection → même vérification que modifier()
 *
 *  2. Récupérer l'ID :
 *     int id = (int) vue.getValeurColonne(0);
 *
 *  3. Demander confirmation :
 *     int choix = JOptionPane.showConfirmDialog(
 *         vue,
 *         "Supprimer cet utilisateur ?",
 *         "Confirmation",
 *         JOptionPane.YES_NO_OPTION,
 *         JOptionPane.WARNING_MESSAGE
 *     );
 *
 *  4. if (choix == JOptionPane.YES_OPTION) {
 *         boolean ok = modele.supprimer(id);
 *         if (ok) {
 *             chargerTableau();
 *             vue.viderFormulaire();
 *         } else {
 *             vue.afficherErreur(
 *                 "Impossible de supprimer : cet utilisateur "
 *               + "a peut-être des ventes associées."
 *             );
 *         }
 *     }
 *
 * ============================================================
 *  MÉTHODE : remplirFormulaire()
 * ============================================================
 *  Signature : private void remplirFormulaire()
 *
 *  COMMENT FAIRE :
 *  1. int row = vue.getLigneSelectionnee();
 *     if (row == -1) return;
 *
 *  2. Récupérer chaque valeur de la ligne :
 *     String nom     = (String) vue.getValeurColonne(1);
 *     String prenom  = (String) vue.getValeurColonne(2);
 *     String email   = (String) vue.getValeurColonne(3);
 *     String role    = (String) vue.getValeurColonne(4);
 *     String discord = (String) vue.getValeurColonne(5);
 *
 *  3. Appeler la méthode de la vue :
 *     vue.remplirFormulaire(nom, prenom, email, role, discord);
 *
 * ============================================================
 */
public class UtilisateurController {

    // Attributs : vue + modele

    // Constructeur :
    // → Stocker attributs
    // → Brancher boutons + recherche + sélection tableau
    // → chargerTableau()

    // chargerTableau()
    // rechercher()
    // ajouter()
    // modifier()
    // supprimer()
    // remplirFormulaire()
}