package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================
 *  MODEL : VenteModel
 * ============================================================
 *  Rôle : Toutes les requêtes SQL sur la table `vente`.
 *
 *  PARTICULARITÉ IMPORTANTE :
 *    Ce model est principalement en LECTURE SEULE.
 *    Les ventes sont créées par le site web RP, pas par
 *    la console admin. On ne fait pas de INSERT ici.
 *
 *    On fait uniquement :
 *      → SELECT pour lire l'historique (2 variantes)
 *      → SELECT pour lire la marge (table config)
 *      → UPDATE pour modifier la marge (table config)
 *
 *  ⚠️ CE MODEL GÈRE AUSSI LA TABLE `config` :
 *     On aurait pu créer un ConfigModel séparé mais
 *     comme la config ne contient que la marge et que
 *     la marge est liée aux ventes (prix TTC), on met
 *     tout ici. Plus simple.
 *
 *  ATTRIBUTS À DÉCLARER :
 *  ──────────────────────
 *  → private BDD db;
 *
 *  CONSTRUCTEUR :
 *  ──────────────
 *  → public VenteModel(BDD db)
 *     Stocke la BDD dans this.db
 *
 * ============================================================
 *  MÉTHODE 1 : getAllGlobal()
 * ============================================================
 *  Signature : public List<Object[]> getAllGlobal()
 *  Retourne  : les ventes regroupées par semaine
 *
 *  ⚠️ On retourne List<Object[]> et non List<Vente>
 *     car ce sont des données agrégées (SUM, COUNT)
 *     qui ne correspondent pas à une ligne unique
 *     de la table vente.
 *
 *  REQUÊTE SQL :
 *    SELECT
 *      DATE_FORMAT(DateVente, '%x%v')  AS semaine,
 *      MIN(DateVente)                  AS debut,
 *      COUNT(*)                        AS nb_ventes,
 *      SUM(PrixVente)                  AS total
 *    FROM vente
 *    GROUP BY DATE_FORMAT(DateVente, '%x%v')
 *    ORDER BY semaine DESC
 *
 *  EXPLICATION DATE_FORMAT('%x%v') :
 *    %x = année ISO (ex: 2025)
 *    %v = numéro de semaine ISO sur 2 chiffres (ex: 12)
 *    Résultat : "202512" = semaine 12 de 2025
 *
 *  COMMENT FAIRE :
 *    1. PreparedStatement avec la requête
 *    2. ResultSet rs = ps.executeQuery()
 *    3. List<Object[]> liste = new ArrayList<>()
 *    4. while(rs.next()) {
 *         Object[] ligne = new Object[4];
 *         ligne[0] = rs.getString("semaine");
 *         ligne[1] = rs.getString("debut");
 *         ligne[2] = rs.getInt("nb_ventes");
 *         ligne[3] = rs.getDouble("total");
 *         liste.add(ligne);
 *       }
 *    5. return liste;
 *    6. try/catch → return new ArrayList<>() si erreur
 *
 *  COMMENT LE CONTROLLER L'UTILISE :
 *    List<Object[]> data = modele.getAllGlobal();
 *    Object[][] tableau = data.toArray(new Object[0][]);
 *    panel.majTableauGlobal(tableau);
 *
 * ============================================================
 *  MÉTHODE 2 : getAllParEmploye()
 * ============================================================
 *  Signature : public List<Object[]> getAllParEmploye()
 *  Retourne  : le détail de chaque vente avec l'employé
 *
 *  REQUÊTE SQL :
 *    SELECT
 *      v.DateVente,
 *      CONCAT(u.Prenom, ' ', u.Nom)  AS employe,
 *      vh.NomModele                   AS vehicule,
 *      v.NomClient,
 *      v.PrixVente
 *    FROM vente v
 *    JOIN utilisateur u  ON v.ID_Employe  = u.ID
 *    JOIN vehicule    vh ON v.ID_Vehicule = vh.ID
 *    ORDER BY v.DateVente DESC
 *
 *  COMMENT FAIRE :
 *    1. PreparedStatement avec la requête
 *    2. ResultSet rs = ps.executeQuery()
 *    3. List<Object[]> liste = new ArrayList<>()
 *    4. while(rs.next()) {
 *         Object[] ligne = new Object[5];
 *         ligne[0] = rs.getString("DateVente");
 *         ligne[1] = rs.getString("employe");
 *         ligne[2] = rs.getString("vehicule");
 *         ligne[3] = rs.getString("NomClient");
 *         ligne[4] = rs.getDouble("PrixVente");
 *         liste.add(ligne);
 *       }
 *    5. return liste;
 *    6. try/catch → return new ArrayList<>() si erreur
 *
 *  COMMENT LE CONTROLLER L'UTILISE :
 *    List<Object[]> data = modele.getAllParEmploye();
 *    Object[][] tableau = data.toArray(new Object[0][]);
 *    panel.majTableauEmploye(tableau);
 *
 * ============================================================
 *  MÉTHODE 3 : getTotalSemaineCourante()
 * ============================================================
 *  Signature : public double getTotalSemaineCourante()
 *  Retourne  : le total GTA$ des ventes de la semaine actuelle
 *
 *  REQUÊTE SQL :
 *    SELECT COALESCE(SUM(PrixVente), 0) AS total
 *    FROM vente
 *    WHERE DATE_FORMAT(DateVente, '%x%v')
 *          = DATE_FORMAT(NOW(), '%x%v')
 *
 *  EXPLICATION COALESCE :
 *    Si aucune vente cette semaine, SUM retourne NULL.
 *    COALESCE(NULL, 0) retourne 0 à la place.
 *    Évite les NullPointerException en Java.
 *
 *  COMMENT FAIRE :
 *    1. PreparedStatement avec la requête
 *    2. ResultSet rs = ps.executeQuery()
 *    3. if(rs.next()) {
 *           return rs.getDouble("total");
 *       }
 *    4. return 0.0 par défaut
 *    5. try/catch → return 0.0 si erreur
 *
 * ============================================================
 *  MÉTHODE 4 : getNbVentesSemaineCourante()
 * ============================================================
 *  Signature : public int getNbVentesSemaineCourante()
 *  Retourne  : le nombre de ventes de la semaine actuelle
 *
 *  REQUÊTE SQL :
 *    SELECT COUNT(*) AS nb
 *    FROM vente
 *    WHERE DATE_FORMAT(DateVente, '%x%v')
 *          = DATE_FORMAT(NOW(), '%x%v')
 *
 *  COMMENT FAIRE :
 *    1. PreparedStatement avec la requête
 *    2. ResultSet rs = ps.executeQuery()
 *    3. if(rs.next()) {
 *           return rs.getInt("nb");
 *       }
 *    4. return 0 par défaut
 *    5. try/catch → return 0 si erreur
 *
 *  COMMENT LE CONTROLLER L'UTILISE :
 *    panel.setTotalSemaine(
 *        String.format("%,.0f",
 *            modele.getTotalSemaineCourante())
 *    );
 *    panel.setNbVentes(
 *        modele.getNbVentesSemaineCourante()
 *    );
 *
 * ============================================================
 *  MÉTHODE 5 : getMarge()
 * ============================================================
 *  Signature : public Config getMarge()
 *  Retourne  : l'objet Config avec la marge et la date
 *
 *  REQUÊTE SQL :
 *    SELECT ID, MargePourcent, DateMaj
 *    FROM config
 *    WHERE ID = 1
 *
 *  ⚠️ On cible toujours WHERE ID = 1 car c'est la
 *     seule ligne de la table config (une seule config).
 *
 *  COMMENT FAIRE :
 *    1. PreparedStatement avec la requête
 *    2. ResultSet rs = ps.executeQuery()
 *    3. if(rs.next()) {
 *           return new Config(
 *               rs.getInt("ID"),
 *               rs.getDouble("MargePourcent"),
 *               rs.getString("DateMaj")
 *           );
 *       }
 *    4. return null si rien trouvé
 *       (ne devrait jamais arriver si le script SQL
 *        a bien été exécuté avec l'INSERT IGNORE)
 *    5. try/catch → return null si erreur
 *
 *  COMMENT LE CONTROLLER L'UTILISE :
 *    Config config = modele.getMarge();
 *    if (config != null) {
 *        panel.setMarge(config.getMargePourcent());
 *        panel.setDateMaj(config.getDateMaj());
 *    }
 *
 * ============================================================
 *  MÉTHODE 6 : saveMarge(double marge)
 * ============================================================
 *  Signature : public boolean saveMarge(double marge)
 *  Retourne  : true si succès, false sinon
 *
 *  REQUÊTE SQL :
 *    UPDATE config
 *    SET MargePourcent = ?
 *    WHERE ID = 1
 *
 *  ⚠️ On ne touche PAS à DateMaj car MySQL le met
 *     à jour automatiquement grâce à :
 *     ON UPDATE CURRENT_TIMESTAMP
 *
 *  COMMENT FAIRE :
 *    1. PreparedStatement avec la requête
 *    2. ps.setDouble(1, marge)
 *    3. return ps.executeUpdate() > 0;
 *    4. try/catch → return false si erreur
 *
 *  COMMENT LE CONTROLLER L'UTILISE :
 *
 *    Dans ConfigController.sauvegarderMarge() :
 *
 *      // Récupérer la valeur saisie
 *      String txt = panel.getMargeTexte();
 *
 *      // Valider que c'est un nombre
 *      double valeur;
 *      try {
 *          valeur = Double.parseDouble(txt);
 *      } catch (NumberFormatException e) {
 *          panel.afficherStatut(
 *              "✕  Entrez un nombre valide.", false
 *          );
 *          return;
 *      }
 *
 *      // Valider la plage (0 à 200%)
 *      if (valeur < 0 || valeur > 200) {
 *          panel.afficherStatut(
 *              "✕  La marge doit être entre 0 et 200.", false
 *          );
 *          return;
 *      }
 *
 *      // Enregistrer
 *      boolean ok = modele.saveMarge(valeur);
 *      if (ok) {
 *          panel.afficherStatut(
 *              "✓  Marge enregistrée avec succès.", true
 *          );
 *          // Recharger pour afficher la nouvelle DateMaj
 *          chargerMarge();
 *      } else {
 *          panel.afficherStatut(
 *              "✕  Erreur lors de l'enregistrement.", false
 *          );
 *      }
 *
 * ============================================================
 *  MÉTHODE 7 (BONUS) : getNbVehiculesTotal()
 * ============================================================
 *  Signature : public int getNbVehiculesTotal()
 *  Retourne  : le nombre total de véhicules actifs en stock
 *  Utilisée  : par DashboardPanel pour la carte "Véhicules"
 *
 *  REQUÊTE SQL :
 *    SELECT COUNT(*) AS nb
 *    FROM vehicule
 *    WHERE Actif = 1
 *
 *  COMMENT FAIRE :
 *    Même principe que getNbVentesSemaineCourante()
 *    → rs.getInt("nb") ou rs.getInt(1)
 *
 * ============================================================
 *  MÉTHODE 8 (BONUS) : getNbUtilisateursTotal()
 * ============================================================
 *  Signature : public int getNbUtilisateursTotal()
 *  Retourne  : le nombre total d'utilisateurs
 *  Utilisée  : par DashboardPanel pour la carte "Utilisateurs"
 *
 *  REQUÊTE SQL :
 *    SELECT COUNT(*) AS nb
 *    FROM utilisateur
 *
 *  COMMENT FAIRE :
 *    Même principe → rs.getInt("nb")
 *
 * ============================================================
 */
public class VenteModel {

    // Déclare ton attribut BDD ici
    // private BDD db;

    // Constructeur
    // public VenteModel(BDD db) { ... }

    // Méthode 1 : getAllGlobal()
    // → SELECT avec DATE_FORMAT GROUP BY semaine
    // → Retourne List<Object[]> (4 colonnes par ligne)

    // Méthode 2 : getAllParEmploye()
    // → SELECT avec JOIN utilisateur + vehicule
    // → Retourne List<Object[]> (5 colonnes par ligne)

    // Méthode 3 : getTotalSemaineCourante()
    // → SELECT COALESCE(SUM(PrixVente), 0)
    //   WHERE semaine = semaine actuelle
    // → Retourne double

    // Méthode 4 : getNbVentesSemaineCourante()
    // → SELECT COUNT(*) WHERE semaine = semaine actuelle
    // → Retourne int

    // Méthode 5 : getMarge()
    // → SELECT * FROM config WHERE ID = 1
    // → Retourne Config

    // Méthode 6 : saveMarge(double marge)
    // → UPDATE config SET MargePourcent=? WHERE ID=1
    // → Retourne boolean

    // Méthode 7 (BONUS) : getNbVehiculesTotal()
    // → SELECT COUNT(*) FROM vehicule WHERE Actif=1
    // → Retourne int

    // Méthode 8 (BONUS) : getNbUtilisateursTotal()
    // → SELECT COUNT(*) FROM utilisateur
    // → Retourne int
}