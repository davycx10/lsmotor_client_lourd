package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================
 *  MODEL : VehiculeModel
 * ============================================================
 *  Rôle : Toutes les requêtes SQL sur la table `vehicule`.
 *  C'est le model le plus complet car vehicule a des JOIN
 *  avec marque et categorie pour récupérer les noms.
 *
 *  ATTRIBUTS À DÉCLARER :
 *  ──────────────────────
 *  → private BDD db;
 *
 *  CONSTRUCTEUR :
 *  ──────────────
 *  → public VehiculeModel(BDD db)
 *     Stocke la BDD dans this.db
 *
 * ============================================================
 *  LA REQUÊTE DE BASE (utilisée par getAll et rechercher)
 * ============================================================
 *
 *  Voici la requête complète avec les JOIN :
 *
 *    SELECT
 *      v.ID,
 *      v.NomModele,
 *      v.PrixCatalogue,
 *      v.Description,
 *      v.Image,
 *      v.Actif,
 *      v.ID_Marque,
 *      v.ID_Categorie,
 *      m.Nom      AS nomMarque,
 *      c.Libelle  AS nomCategorie
 *    FROM vehicule v
 *    JOIN marque    m ON v.ID_Marque    = m.ID
 *    JOIN categorie c ON v.ID_Categorie = c.ID
 *
 *  Tu peux créer une constante privée pour éviter
 *  de la réécrire dans chaque méthode :
 *
 *    private static final String SQL_BASE =
 *        "SELECT v.ID, v.NomModele, v.PrixCatalogue, " +
 *        "v.Description, v.Image, v.Actif, " +
 *        "v.ID_Marque, v.ID_Categorie, " +
 *        "m.Nom AS nomMarque, c.Libelle AS nomCategorie " +
 *        "FROM vehicule v " +
 *        "JOIN marque    m ON v.ID_Marque    = m.ID " +
 *        "JOIN categorie c ON v.ID_Categorie = c.ID ";
 *
 *  COMMENT CRÉER UN Vehicule DEPUIS UN ResultSet :
 *
 *    Vehicule v = new Vehicule();
 *    v.setId(rs.getInt("ID"));
 *    v.setNomModele(rs.getString("NomModele"));
 *    v.setPrixCatalogue(rs.getDouble("PrixCatalogue"));
 *    v.setDescription(rs.getString("Description"));
 *    v.setImage(rs.getString("Image"));
 *    v.setActif(rs.getInt("Actif") == 1);
 *    v.setIdMarque(rs.getInt("ID_Marque"));
 *    v.setIdCategorie(rs.getInt("ID_Categorie"));
 *    v.setNomMarque(rs.getString("nomMarque"));
 *    v.setNomCategorie(rs.getString("nomCategorie"));
 *
 *  Crée une méthode PRIVÉE creerVehiculeDepuisRS(ResultSet rs)
 *  qui fait exactement ça → tu l'appelles dans la boucle
 *  while(rs.next()) de getAll() et rechercher()
 *  pour éviter de dupliquer ce code.
 *
 * ============================================================
 *  MÉTHODE 1 : getAll()
 * ============================================================
 *  Signature : public List<Vehicule> getAll()
 *  Retourne  : tous les véhicules avec marque + catégorie
 *
 *  REQUÊTE SQL :
 *    SQL_BASE + "ORDER BY v.NomModele ASC"
 *
 *  COMMENT FAIRE :
 *    1. PreparedStatement avec SQL_BASE + ORDER BY
 *    2. executeQuery() → ResultSet
 *    3. List<Vehicule> vide
 *    4. while(rs.next()) → creerVehiculeDepuisRS(rs)
 *       → ajouter à la liste
 *    5. Retourner la liste
 *    6. try/catch(SQLException e)
 *
 * ============================================================
 *  MÉTHODE 2 : rechercher(String terme)
 * ============================================================
 *  Signature : public List<Vehicule> rechercher(String terme)
 *  Retourne  : véhicules dont nom/marque/catégorie
 *              contiennent le terme
 *
 *  REQUÊTE SQL :
 *    SQL_BASE +
 *    "WHERE v.NomModele  LIKE ? " +
 *    "OR    m.Nom        LIKE ? " +
 *    "OR    c.Libelle    LIKE ? " +
 *    "ORDER BY v.NomModele ASC"
 *
 *  COMMENT FAIRE :
 *    1. PreparedStatement avec la requête
 *    2. Les 3 paramètres = "%" + terme + "%"
 *    3. Même boucle que getAll()
 *
 * ============================================================
 *  MÉTHODE 3 : ajouter(Vehicule v)
 * ============================================================
 *  Signature : public boolean ajouter(Vehicule v)
 *  Retourne  : true si succès, false sinon
 *
 *  REQUÊTE SQL :
 *    INSERT INTO vehicule
 *      (NomModele, ID_Marque, ID_Categorie,
 *       PrixCatalogue, Description, Image, Actif)
 *    VALUES (?, ?, ?, ?, ?, ?, ?)
 *
 *  COMMENT FAIRE :
 *    1. PreparedStatement avec la requête
 *    2. Remplir les ? dans l'ordre :
 *       ps.setString(1, v.getNomModele());
 *       ps.setInt(2,    v.getIdMarque());
 *       ps.setInt(3,    v.getIdCategorie());
 *       ps.setDouble(4, v.getPrixCatalogue());
 *       ps.setString(5, v.getDescription());
 *       ps.setString(6, v.getImage());
 *       ps.setInt(7,    v.isActif() ? 1 : 0);
 *       ⚠️ isActif() retourne boolean → convertir en 0/1
 *    3. int lignes = ps.executeUpdate()
 *    4. return lignes > 0;
 *
 * ============================================================
 *  MÉTHODE 4 : modifier(Vehicule v)
 * ============================================================
 *  Signature : public boolean modifier(Vehicule v)
 *  Retourne  : true si succès, false sinon
 *
 *  REQUÊTE SQL :
 *    UPDATE vehicule
 *    SET NomModele     = ?,
 *        ID_Marque     = ?,
 *        ID_Categorie  = ?,
 *        PrixCatalogue = ?,
 *        Description   = ?,
 *        Image         = ?,
 *        Actif         = ?
 *    WHERE ID = ?
 *
 *  COMMENT FAIRE :
 *    1. PreparedStatement avec la requête
 *    2. Remplir les ? dans l'ordre :
 *       ps.setString(1, v.getNomModele());
 *       ps.setInt(2,    v.getIdMarque());
 *       ps.setInt(3,    v.getIdCategorie());
 *       ps.setDouble(4, v.getPrixCatalogue());
 *       ps.setString(5, v.getDescription());
 *       ps.setString(6, v.getImage());
 *       ps.setInt(7,    v.isActif() ? 1 : 0);
 *       ps.setInt(8,    v.getId());
 *       ⚠️ L'ID est TOUJOURS le dernier car il est
 *          dans le WHERE
 *    3. return ps.executeUpdate() > 0;
 *
 * ============================================================
 *  MÉTHODE 5 : supprimer(int id)
 * ============================================================
 *  Signature : public boolean supprimer(int id)
 *  Retourne  : true si succès, false sinon
 *
 *  REQUÊTE SQL :
 *    DELETE FROM vehicule WHERE ID = ?
 *
 *  COMMENT FAIRE :
 *    1. PreparedStatement
 *    2. ps.setInt(1, id)
 *    3. return ps.executeUpdate() > 0;
 *    4. try/catch → return false si erreur
 *
 *  ⚠️ CONTRAINTE FK :
 *     Si ce véhicule a des ventes dans la table `vente`,
 *     MySQL refusera la suppression (fk_vente_vehicule).
 *     Gère ça dans le catch avec un message clair.
 *
 * ============================================================
 *  MÉTHODE 6 (BONUS) : estUtilise(int id)
 * ============================================================
 *  Signature : public boolean estUtilise(int id)
 *  Retourne  : true si le véhicule a des ventes associées
 *
 *  Utile pour vérifier AVANT de supprimer et afficher
 *  un message d'erreur propre plutôt qu'une erreur SQL.
 *
 *  REQUÊTE SQL :
 *    SELECT COUNT(*) FROM vente WHERE ID_Vehicule = ?
 *
 *  COMMENT FAIRE :
 *    1. PreparedStatement
 *    2. ps.setInt(1, id)
 *    3. ResultSet rs = ps.executeQuery()
 *    4. if (rs.next()) return rs.getInt(1) > 0;
 *    5. return false par défaut
 *
 * ============================================================
 *  MÉTHODE PRIVÉE : creerVehiculeDepuisRS(ResultSet rs)
 * ============================================================
 *  Signature : private Vehicule creerVehiculeDepuisRS(
 *                  ResultSet rs) throws SQLException
 *
 *  Crée et retourne un objet Vehicule depuis une ligne
 *  du ResultSet. Évite la duplication de code.
 *
 *  COMMENT FAIRE :
 *    Vehicule v = new Vehicule();
 *    → setId, setNomModele, setPrixCatalogue...
 *    → Pour Actif : v.setActif(rs.getInt("Actif") == 1)
 *    return v;
 *
 * ============================================================
 */
public class VehiculeModel {

    // Déclare SQL_BASE ici (private static final String)

    // Déclare ton attribut BDD ici
    // private BDD db;

    // Constructeur
    // public VehiculeModel(BDD db) { ... }

    // Méthode privée : creerVehiculeDepuisRS(ResultSet rs)
    // → Crée un Vehicule depuis une ligne ResultSet
    // → throws SQLException car rs.get...() peut lancer ça

    // Méthode 1 : getAll()

    // Méthode 2 : rechercher(String terme)

    // Méthode 3 : ajouter(Vehicule v)

    // Méthode 4 : modifier(Vehicule v)

    // Méthode 5 : supprimer(int id)

    // Méthode 6 : estUtilise(int id)
}