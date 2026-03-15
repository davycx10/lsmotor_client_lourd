package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================
 *  MODEL : CategorieModel
 * ============================================================
 *  Rôle : Toutes les requêtes SQL sur la table `categorie`.
 *
 *  ⚠️ CE MODEL EST QUASI IDENTIQUE À MarqueModel.
 *     La seule différence : la colonne s'appelle "Libelle"
 *     et non "Nom", et la table s'appelle "categorie".
 *
 *     Si tu as bien compris MarqueModel → celui-ci
 *     sera très rapide à écrire.
 *
 *  ATTRIBUTS À DÉCLARER :
 *  ──────────────────────
 *  → private BDD db;
 *
 *  CONSTRUCTEUR :
 *  ──────────────
 *  → public CategorieModel(BDD db)
 *     Stocke la BDD dans this.db
 *
 * ============================================================
 *  COMPARAISON DIRECTE AVEC MarqueModel :
 * ============================================================
 *
 *    MarqueModel           →    CategorieModel
 *    ─────────────────────────────────────────────────
 *    table : marque        →    table : categorie
 *    colonne : Nom         →    colonne : Libelle
 *    objet : Marque        →    objet : Categorie
 *    getNom()              →    getLibelle()
 *    setNom()              →    setLibelle()
 *    ajouter(String nom)   →    ajouter(String libelle)
 *    modifier(id, nom)     →    modifier(id, libelle)
 *    FK : vehicule.ID_Marque → FK : vehicule.ID_Categorie
 *
 *  En gros : remplace tous les "Nom"/"marque" de MarqueModel
 *  par "Libelle"/"categorie" et tu as ce fichier.
 *
 * ============================================================
 *  MÉTHODE 1 : getAll()
 * ============================================================
 *  Signature : public List<Categorie> getAll()
 *  Retourne  : toutes les catégories triées par libellé
 *
 *  REQUÊTE SQL :
 *    SELECT ID, Libelle
 *    FROM categorie
 *    ORDER BY Libelle ASC
 *
 *  COMMENT FAIRE :
 *    1. PreparedStatement avec la requête
 *    2. ResultSet rs = ps.executeQuery()
 *    3. List<Categorie> liste = new ArrayList<>()
 *    4. while(rs.next()) {
 *         Categorie c = new Categorie(
 *             rs.getInt("ID"),
 *             rs.getString("Libelle")
 *         );
 *         liste.add(c);
 *       }
 *    5. return liste;
 *    6. try/catch → return new ArrayList<>() si erreur
 *
 *  CETTE MÉTHODE EST UTILISÉE PAR :
 *    → CategorieController.chargerTableau()
 *       pour afficher dans la JTable
 *    → VehiculeController.chargerCategories()
 *       pour peupler le JComboBox categorie
 *       dans VehiculePanel
 *
 * ============================================================
 *  MÉTHODE 2 : rechercher(String terme)
 * ============================================================
 *  Signature : public List<Categorie> rechercher(String terme)
 *  Retourne  : les catégories dont le libellé contient terme
 *
 *  REQUÊTE SQL :
 *    SELECT ID, Libelle
 *    FROM categorie
 *    WHERE Libelle LIKE ?
 *    ORDER BY Libelle ASC
 *
 *  COMMENT FAIRE :
 *    1. PreparedStatement avec la requête
 *    2. ps.setString(1, "%" + terme + "%")
 *    3. Même boucle que getAll()
 *       mais avec rs.getString("Libelle")
 *    4. try/catch → liste vide si erreur
 *
 * ============================================================
 *  MÉTHODE 3 : ajouter(String libelle)
 * ============================================================
 *  Signature : public boolean ajouter(String libelle)
 *  Retourne  : true si succès, false sinon
 *
 *  REQUÊTE SQL :
 *    INSERT INTO categorie (Libelle) VALUES (?)
 *
 *  COMMENT FAIRE :
 *    1. PreparedStatement avec la requête
 *    2. ps.setString(1, libelle)
 *    3. return ps.executeUpdate() > 0;
 *    4. try/catch → return false si erreur SQL
 *
 *  ⚠️ Même remarque que MarqueModel :
 *     Si le libellé existe déjà, MySQL lèvera une erreur
 *     "Duplicate entry". Gère ça dans le catch.
 *
 * ============================================================
 *  MÉTHODE 4 : modifier(int id, String libelle)
 * ============================================================
 *  Signature : public boolean modifier(int id, String libelle)
 *  Retourne  : true si succès, false sinon
 *
 *  REQUÊTE SQL :
 *    UPDATE categorie
 *    SET Libelle = ?
 *    WHERE ID = ?
 *
 *  COMMENT FAIRE :
 *    1. PreparedStatement avec la requête
 *    2. Dans l'ordre :
 *       ps.setString(1, libelle);
 *       ps.setInt(2, id);
 *       ⚠️ L'ID est TOUJOURS le dernier (dans le WHERE)
 *    3. return ps.executeUpdate() > 0;
 *    4. try/catch → return false si erreur
 *
 * ============================================================
 *  MÉTHODE 5 : supprimer(int id)
 * ============================================================
 *  Signature : public boolean supprimer(int id)
 *  Retourne  : true si succès, false sinon
 *
 *  REQUÊTE SQL :
 *    DELETE FROM categorie WHERE ID = ?
 *
 *  COMMENT FAIRE :
 *    1. PreparedStatement
 *    2. ps.setInt(1, id)
 *    3. return ps.executeUpdate() > 0;
 *    4. try/catch → return false
 *
 *  ⚠️ CONTRAINTE FK :
 *     La table `vehicule` a une FK sur categorie :
 *       fk_vehicule_categorie
 *     Si des véhicules utilisent cette catégorie,
 *     MySQL refusera la suppression.
 *     → Appelle estUtilisee(id) AVANT dans le controller.
 *
 * ============================================================
 *  MÉTHODE 6 : estUtilisee(int id)
 * ============================================================
 *  Signature : public boolean estUtilisee(int id)
 *  Retourne  : true si des véhicules utilisent cette catégorie
 *
 *  REQUÊTE SQL :
 *    SELECT COUNT(*)
 *    FROM vehicule
 *    WHERE ID_Categorie = ?
 *
 *  ⚠️ Différence avec MarqueModel.estUtilisee() :
 *     On vérifie ID_Categorie et non ID_Marque
 *
 *  COMMENT FAIRE :
 *    1. PreparedStatement
 *    2. ps.setInt(1, id)
 *    3. ResultSet rs = ps.executeQuery()
 *    4. if(rs.next()) {
 *           return rs.getInt(1) > 0;
 *       }
 *    5. return false par défaut
 *
 *  COMMENT LE CONTROLLER L'UTILISE :
 *
 *    Dans CategorieController.supprimer() :
 *      int id = (int) panel.getValeurColonne(0);
 *
 *      // Vérifie avant de supprimer
 *      if (modele.estUtilisee(id)) {
 *          panel.afficherErreur(
 *              "Impossible de supprimer : cette catégorie "
 *            + "est utilisée par des véhicules."
 *          );
 *          return;
 *      }
 *
 *      // Demande confirmation
 *      int choix = JOptionPane.showConfirmDialog(
 *          panel,
 *          "Supprimer cette catégorie ?",
 *          "Confirmation",
 *          JOptionPane.YES_NO_OPTION
 *      );
 *
 *      if (choix == JOptionPane.YES_OPTION) {
 *          boolean ok = modele.supprimer(id);
 *          if (ok) {
 *              chargerTableau();
 *              panel.viderFormulaire();
 *          } else {
 *              panel.afficherErreur("Erreur lors de la suppression.");
 *          }
 *      }
 *
 * ============================================================
 *  CONVERSION List<Categorie> → Object[][] pour le tableau
 * ============================================================
 *
 *  Dans CategorieController.chargerTableau() tu devras
 *  convertir la liste en Object[][] pour majTableau() :
 *
 *    List<Categorie> liste = modele.getAll();
 *
 *    Object[][] data = new Object[liste.size()][2];
 *    for (int i = 0; i < liste.size(); i++) {
 *        data[i][0] = liste.get(i).getId();      // caché
 *        data[i][1] = liste.get(i).getLibelle(); // visible
 *    }
 *
 *    panel.majTableau(data);
 *    panel.setCompteur(liste.size()); // si tu as ajouté ça
 *
 *  C'est la même logique pour tous les controllers CRUD.
 *
 * ============================================================
 */
public class CategorieModel {

    // Déclare ton attribut BDD ici
    // private BDD db;

    // Constructeur
    // public CategorieModel(BDD db) { ... }

    // Méthode 1 : getAll()
    // → SELECT ID, Libelle FROM categorie ORDER BY Libelle ASC

    // Méthode 2 : rechercher(String terme)
    // → WHERE Libelle LIKE ?

    // Méthode 3 : ajouter(String libelle)
    // → INSERT INTO categorie (Libelle) VALUES (?)

    // Méthode 4 : modifier(int id, String libelle)
    // → UPDATE categorie SET Libelle=? WHERE ID=?
    // ⚠️ ID toujours en dernier

    // Méthode 5 : supprimer(int id)
    // → DELETE FROM categorie WHERE ID=?
    // ⚠️ Vérifier estUtilisee() avant dans le controller

    // Méthode 6 : estUtilisee(int id)
    // → SELECT COUNT(*) FROM vehicule WHERE ID_Categorie=?
}