package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================
 *  MODEL : UtilisateurModel
 * ============================================================
 *  Rôle : Toutes les requêtes SQL sur la table `utilisateur`.
 *  C'est la seule classe qui parle à la BDD pour les users.
 *  Le controller appelle ce model, jamais la BDD directement.
 *
 *  ATTRIBUTS À DÉCLARER :
 *  ──────────────────────
 *  → private BDD db;
 *     La connexion BDD reçue dans le constructeur.
 *     On ne la crée pas ici, on la reçoit depuis le controller.
 *
 *  CONSTRUCTEUR :
 *  ──────────────
 *  → public UtilisateurModel(BDD db)
 *     Reçoit la BDD en paramètre et la stocke dans this.db
 *     EXEMPLE :
 *       public UtilisateurModel(BDD db) {
 *           this.db = db;
 *       }
 *
 * ============================================================
 *  MÉTHODE 1 : getAll()
 * ============================================================
 *  Signature : public List<Utilisateur> getAll()
 *  Retourne  : la liste de TOUS les utilisateurs
 *
 *  REQUÊTE SQL :
 *    SELECT ID, Nom, Prenom, Email, Role,
 *           DiscordPseudo, DateInscription
 *    FROM utilisateur
 *    ORDER BY Nom ASC
 *
 *  ⚠️ On ne sélectionne PAS Passwrd pour des raisons
 *     de sécurité (inutile de le ramener en mémoire)
 *
 *  COMMENT FAIRE :
 *    1. Créer un PreparedStatement avec la requête
 *    2. Exécuter avec executeQuery() → ResultSet rs
 *    3. Créer une List<Utilisateur> vide
 *    4. Boucle while(rs.next()) {
 *         Créer un new Utilisateur()
 *         Remplir avec rs.getInt("ID"), rs.getString("Nom")...
 *         Ajouter à la liste
 *       }
 *    5. Retourner la liste
 *    6. Tout dans un try/catch(SQLException e)
 *
 * ============================================================
 *  MÉTHODE 2 : rechercher(String terme)
 * ============================================================
 *  Signature : public List<Utilisateur> rechercher(String terme)
 *  Retourne  : les utilisateurs dont nom/prénom/email
 *              contiennent le terme recherché
 *
 *  REQUÊTE SQL :
 *    SELECT ID, Nom, Prenom, Email, Role,
 *           DiscordPseudo, DateInscription
 *    FROM utilisateur
 *    WHERE Nom      LIKE ?
 *       OR Prenom   LIKE ?
 *       OR Email    LIKE ?
 *    ORDER BY Nom ASC
 *
 *  COMMENT FAIRE :
 *    1. PreparedStatement avec la requête ci-dessus
 *    2. Les 3 paramètres ? sont : "%" + terme + "%"
 *       → ps.setString(1, "%" + terme + "%");
 *       → ps.setString(2, "%" + terme + "%");
 *       → ps.setString(3, "%" + terme + "%");
 *    3. Même logique de boucle que getAll()
 *
 * ============================================================
 *  MÉTHODE 3 : verifierConnexion(String email, String mdp)
 * ============================================================
 *  Signature : public Utilisateur verifierConnexion(
 *                  String email, String mdp)
 *  Retourne  : l'Utilisateur si trouvé, null sinon
 *  Utilisée  : par LoginController pour authentifier
 *
 *  REQUÊTE SQL :
 *    SELECT ID, Nom, Prenom, Email, Role,
 *           DiscordPseudo, DateInscription
 *    FROM utilisateur
 *    WHERE Email   = ?
 *    AND   Passwrd = ?
 *    AND   Role    = 'admin'
 *
 *  ⚠️ On vérifie que le Role est 'admin' car seuls
 *     les admins ont accès à la console.
 *
 *  COMMENT FAIRE :
 *    1. PreparedStatement avec la requête
 *    2. ps.setString(1, email);
 *       ps.setString(2, mdp);
 *       ⚠️ Le mot de passe est stocké en clair dans
 *          ta BDD (selon ton script SQL) donc pas de
 *          hash ici — à améliorer plus tard si besoin
 *    3. ResultSet rs = ps.executeQuery()
 *    4. if (rs.next()) → créer et retourner l'Utilisateur
 *    5. Sinon retourner null
 *
 * ============================================================
 *  MÉTHODE 4 : ajouter(Utilisateur u)
 * ============================================================
 *  Signature : public boolean ajouter(Utilisateur u)
 *  Retourne  : true si l'ajout a réussi, false sinon
 *
 *  REQUÊTE SQL :
 *    INSERT INTO utilisateur
 *      (Nom, Prenom, Email, Passwrd, Role, DiscordPseudo)
 *    VALUES (?, ?, ?, ?, ?, ?)
 *
 *  ⚠️ On n'insère pas ID (auto_increment)
 *     ni DateInscription (DEFAULT CURRENT_TIMESTAMP)
 *
 *  COMMENT FAIRE :
 *    1. PreparedStatement avec la requête
 *    2. Remplir les ? dans l'ordre :
 *       ps.setString(1, u.getNom());
 *       ps.setString(2, u.getPrenom());
 *       ps.setString(3, u.getEmail());
 *       ps.setString(4, u.getPasswrd());
 *       ps.setString(5, u.getRole());
 *       ps.setString(6, u.getDiscordPseudo());
 *    3. int lignes = ps.executeUpdate()
 *    4. return lignes > 0;
 *       → si au moins 1 ligne insérée = succès
 *    5. try/catch → return false si erreur SQL
 *
 * ============================================================
 *  MÉTHODE 5 : modifier(Utilisateur u)
 * ============================================================
 *  Signature : public boolean modifier(Utilisateur u)
 *  Retourne  : true si la modification a réussi, false sinon
 *
 *  DEUX CAS POSSIBLES selon si le mot de passe est fourni :
 *
 *  CAS 1 — Mot de passe fourni (non vide) :
 *    UPDATE utilisateur
 *    SET Nom=?, Prenom=?, Email=?, Passwrd=?,
 *        Role=?, DiscordPseudo=?
 *    WHERE ID = ?
 *
 *  CAS 2 — Mot de passe vide (on ne le change pas) :
 *    UPDATE utilisateur
 *    SET Nom=?, Prenom=?, Email=?,
 *        Role=?, DiscordPseudo=?
 *    WHERE ID = ?
 *
 *  COMMENT FAIRE :
 *    1. Vérifier si u.getPasswrd() est vide ou non :
 *       if (u.getPasswrd() == null
 *               || u.getPasswrd().isEmpty()) {
 *           // CAS 2 : requête sans Passwrd
 *       } else {
 *           // CAS 1 : requête avec Passwrd
 *       }
 *    2. Construire le bon PreparedStatement selon le cas
 *    3. Remplir les ? dans l'ordre
 *       ⚠️ L'ID est toujours le DERNIER paramètre
 *          car il est dans le WHERE
 *    4. int lignes = ps.executeUpdate()
 *    5. return lignes > 0;
 *
 * ============================================================
 *  MÉTHODE 6 : supprimer(int id)
 * ============================================================
 *  Signature : public boolean supprimer(int id)
 *  Retourne  : true si la suppression a réussi, false sinon
 *
 *  REQUÊTE SQL :
 *    DELETE FROM utilisateur WHERE ID = ?
 *
 *  COMMENT FAIRE :
 *    1. PreparedStatement avec la requête
 *    2. ps.setInt(1, id);
 *    3. int lignes = ps.executeUpdate()
 *    4. return lignes > 0;
 *    5. try/catch → return false si erreur SQL
 *
 *  ⚠️ ATTENTION AUX CONTRAINTES FK :
 *     Si l'utilisateur a des ventes (table `vente`),
 *     MySQL va refuser la suppression car il y a une
 *     FK : fk_vente_employe.
 *     Dans le catch, tu peux détecter ça avec :
 *       e.getMessage().contains("foreign key")
 *     Et retourner false avec un message explicite.
 *
 * ============================================================
 *  RÈGLE GÉNÉRALE POUR TOUS LES PREPAREDSTATEMENT :
 * ============================================================
 *
 *  TOUJOURS utiliser PreparedStatement, JAMAIS concatener :
 *
 *  ❌ MAUVAIS (risque d'injection SQL) :
 *     String sql = "SELECT * FROM utilisateur WHERE Nom = '"
 *                  + nom + "'";
 *
 *  ✓ BON (sécurisé) :
 *     String sql = "SELECT * FROM utilisateur WHERE Nom = ?";
 *     PreparedStatement ps =
 *         db.getMaConnexion().prepareStatement(sql);
 *     ps.setString(1, nom);
 *
 * ============================================================
 */
public class UtilisateurModel {

    // Déclare ton attribut BDD ici
    // private BDD db;

    // Constructeur
    // public UtilisateurModel(BDD db) { ... }

    // Méthode 1 : getAll()

    // Méthode 2 : rechercher(String terme)

    // Méthode 3 : verifierConnexion(String email, String mdp)

    // Méthode 4 : ajouter(Utilisateur u)

    // Méthode 5 : modifier(Utilisateur u)

    // Méthode 6 : supprimer(int id)
}