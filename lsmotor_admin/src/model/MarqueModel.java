package model;

import java.sql.*;
import model.POJOs.Marque;
import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================
 *  MODEL : MarqueModel
 * ============================================================
 *  Rôle : Toutes les requêtes SQL sur la table `marque`.
 *  Model simple car la table n'a que 2 colonnes (ID, Nom).
 *
 *  ATTRIBUTS À DÉCLARER :
 *  ──────────────────────
 *  → private BDD db;
 *
 *  CONSTRUCTEUR :
 *  ──────────────
 *  → public MarqueModel(BDD db)
 *     Stocke la BDD dans this.db
 *
 * ============================================================
 *  MÉTHODE 1 : getAll()
 * ============================================================
 *  Signature : public List<Marque> getAll()
 *  Retourne  : toutes les marques triées par nom
 *
 *  REQUÊTE SQL :
 *    SELECT ID, Nom
 *    FROM marque
 *    ORDER BY Nom ASC
 *
 *  COMMENT FAIRE :
 *    1. PreparedStatement avec la requête
 *    2. ResultSet rs = ps.executeQuery()
 *    3. List<Marque> liste = new ArrayList<>()
 *    4. while(rs.next()) {
 *         Marque m = new Marque(
 *             rs.getInt("ID"),
 *             rs.getString("Nom")
 *         );
 *         liste.add(m);
 *       }
 *    5. return liste;
 *    6. try/catch(SQLException e)
 *       → en cas d'erreur retourner une liste vide
 *         return new ArrayList<>();
 *
 *  CETTE MÉTHODE EST UTILISÉE PAR :
 *    → MarqueController.chargerTableau()
 *       pour afficher dans la JTable
 *    → VehiculeController.chargerMarques()
 *       pour peupler le JComboBox marque dans VehiculePanel
 *       (c'est pour ça que toString() de Marque = le nom)
 *
 * ============================================================
 *  MÉTHODE 2 : rechercher(String terme)
 * ============================================================
 *  Signature : public List<Marque> rechercher(String terme)
 *  Retourne  : les marques dont le nom contient le terme
 *
 *  REQUÊTE SQL :
 *    SELECT ID, Nom
 *    FROM marque
 *    WHERE Nom LIKE ?
 *    ORDER BY Nom ASC
 *
 *  COMMENT FAIRE :
 *    1. PreparedStatement avec la requête
 *    2. ps.setString(1, "%" + terme + "%")
 *    3. Même boucle que getAll()
 *    4. try/catch → liste vide si erreur
 *
 * ============================================================
 *  MÉTHODE 3 : ajouter(String nom)
 * ============================================================
 *  Signature : public boolean ajouter(String nom)
 *  Retourne  : true si succès, false sinon
 *
 *  ⚠️ Ici on reçoit directement un String et non un
 *     objet Marque car on n'a besoin que du nom.
 *     L'ID est auto_increment, on ne le gère pas.
 *
 *  REQUÊTE SQL :
 *    INSERT INTO marque (Nom) VALUES (?)
 *
 *  COMMENT FAIRE :
 *    1. PreparedStatement avec la requête
 *    2. ps.setString(1, nom)
 *    3. return ps.executeUpdate() > 0;
 *    4. try/catch → return false si erreur SQL
 *
 *  ⚠️ Si le nom existe déjà, MySQL va lever une erreur
 *     car la colonne Nom est UNIQUE dans ta table.
 *     Dans le catch tu peux détecter ça :
 *       e.getMessage().contains("Duplicate entry")
 *     Et retourner false avec un message explicite
 *     que le controller pourra afficher.
 *
 * ============================================================
 *  MÉTHODE 4 : modifier(int id, String nouveauNom)
 * ============================================================
 *  Signature : public boolean modifier(int id, String nom)
 *  Retourne  : true si succès, false sinon
 *
 *  ⚠️ On reçoit l'ID et le nouveau nom directement.
 *     Pas besoin d'un objet Marque entier.
 *
 *  REQUÊTE SQL :
 *    UPDATE marque
 *    SET Nom = ?
 *    WHERE ID = ?
 *
 *  COMMENT FAIRE :
 *    1. PreparedStatement avec la requête
 *    2. Remplir dans l'ordre :
 *       ps.setString(1, nom);
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
 *    DELETE FROM marque WHERE ID = ?
 *
 *  COMMENT FAIRE :
 *    1. PreparedStatement
 *    2. ps.setInt(1, id)
 *    3. return ps.executeUpdate() > 0;
 *    4. try/catch → return false
 *
 *  ⚠️ CONTRAINTE FK IMPORTANTE :
 *     La table `vehicule` a une FK sur marque :
 *       fk_vehicule_marque
 *     Si des véhicules utilisent cette marque,
 *     MySQL refusera la suppression.
 *
 *     BONNE PRATIQUE :
 *     Appelle estUtilisee(id) AVANT de supprimer
 *     dans le MarqueController pour afficher un
 *     message clair à l'utilisateur.
 *
 * ============================================================
 *  MÉTHODE 6 : estUtilisee(int id)
 * ============================================================
 *  Signature : public boolean estUtilisee(int id)
 *  Retourne  : true si des véhicules utilisent cette marque
 *
 *  REQUÊTE SQL :
 *    SELECT COUNT(*)
 *    FROM vehicule
 *    WHERE ID_Marque = ?
 *
 *  COMMENT FAIRE :
 *    1. PreparedStatement
 *    2. ps.setInt(1, id)
 *    3. ResultSet rs = ps.executeQuery()
 *    4. if(rs.next()) {
 *           return rs.getInt(1) > 0;
 *           → getInt(1) = valeur du COUNT(*)
 *       }
 *    5. return false par défaut
 *
 *  COMMENT LE CONTROLLER L'UTILISE :
 *
 *    Dans MarqueController.supprimer() :
 *      int id = (int) panel.getValeurColonne(0);
 *      if (modele.estUtilisee(id)) {
 *          panel.afficherErreur(
 *              "Impossible de supprimer : cette marque "
 *            + "est utilisée par des véhicules."
 *          );
 *          return; // on arrête là
 *      }
 *      // Sinon on confirme et on supprime
 *      int choix = JOptionPane.showConfirmDialog(...);
 *      if (choix == JOptionPane.YES_OPTION) {
 *          modele.supprimer(id);
 *          chargerTableau();
 *      }
 *
 * ============================================================
 *  RÉSUMÉ DE L'ORDRE D'APPEL TYPIQUE :
 * ============================================================
 *
 *  Cas : l'utilisateur tape dans la barre de recherche
 *    KeyListener → MarqueController.rechercher()
 *      → modele.rechercher(terme)
 *        → retourne List<Marque>
 *      → convertir en Object[][] pour majTableau()
 *      → panel.majTableau(data)
 *
 *  Cas : l'utilisateur clique sur une ligne du tableau
 *    ListSelectionListener → MarqueController
 *      → panel.getValeurColonne(1) → nom de la marque
 *      → panel.setNom(nom)
 *
 *  Cas : l'utilisateur clique sur Ajouter
 *    ActionListener → MarqueController.ajouter()
 *      → panel.getNom() → valider non vide
 *      → modele.ajouter(nom)
 *      → chargerTableau() + panel.viderFormulaire()
 *
 * ============================================================
 */
public class MarqueModel {

    // Déclare ton attribut BDD ici
    private BDD uneDb;

    // Constructeur
    public MarqueModel(BDD db) {
        this.uneDb = db;
    }


    // Méthode 1 : getAll()
    // → SELECT ID, Nom FROM marque ORDER BY Nom ASC
    public List<Marque> getAll(){
        List<Marque> liste = new ArrayList<>();

        String sql = "SELECT ID, Nom" +
                "FROM marque ORDER BY Nom ASC";
        try {
            PreparedStatement ps =
                    uneDb.getMaConnexion().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Marque m = new Marque(
                        rs.getInt("ID"),
                        rs.getString("Nom")
                );
                liste.add(m);
            }
            ps.close();

        } catch (Exception e) {
            System.out.println("Erreur getAll : "
            + e.getMessage());
        }
        return liste;
    }

    // Méthode 2 : rechercher(String terme)
    // → WHERE Nom LIKE ?
    public List<Marque> rechercher(String terme){
        List<Marque> liste = new ArrayList<>();

        String sql = "SELECT ID, NOM " +
                "FROM marque " +
                "WHERE Nom LIKE ? ORDER BY Nom ASC";
        try {
            PreparedStatement ps = 
                    uneDb.getMaConnexion().prepareStatement(sql);
            String t = "%" + terme + "%";
            ps.setString(1, t);
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()){
                Marque m = new Marque(
                        rs.getInt("ID"),
                        rs.getString("Nom")
                );
                liste.add(m);
            }
            ps.close();
            
        } catch (Exception e) {
            System.out.println("Erreur rechercher : " + e.getMessage());
        }
        return liste;
    }

    // Méthode 3 : ajouter(String nom)
    // → INSERT INTO marque (Nom) VALUES (?)

    public boolean ajouter(String nom) {
        String sql = "INSERT INTO marque " +
                "(Nom)" + "VALUES (?)";
        try {
            PreparedStatement  ps =
                    uneDb.getMaConnexion().prepareStatement(sql);
                    ps.setString(1, nom);
            int lignes = ps.executeUpdate();
            ps.close();
            return lignes > 0;
            
        } catch (Exception e) {
            System.out.println("Erreur ajouter : " + e.getMessage());
            return false;
        }
    }

    // Méthode 4 : modifier(int id, String nom)
    // → UPDATE marque SET Nom=? WHERE ID=?
    // ⚠️ ID toujours en dernier
    public boolean modifier(int id, String nom) {
        try {
            PreparedStatement ps;
            String sql = "UPDATE marque" +
                    "SET Nom=?" + "WHERE ID=?";
            ps = uneDb.getMaConnexion().prepareStatement(sql);
            ps.setString(1, nom);
            ps.setInt(2, id);
            
            int lignes = ps.executeUpdate();
            ps.close();
            return lignes > 0;
            
            
        } catch (Exception e) {
            System.out.println("Erreur modifier : " + e.getMessage());
            return  false;
        }
    }
    
    

    // Méthode 5 : supprimer(int id)
    // → DELETE FROM marque WHERE ID=?
    // ⚠️ Vérifier estUtilisee() avant dans le controller
    public  boolean supprimer(int id){
        String sql = "DELETE FROM marque" +
                "WHERE ID=?";
        try {
            PreparedStatement ps =
                    uneDb.getMaConnexion().prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Erreur supprimer : " + e.getMessage());
        }
        return false;
    }

    // Méthode 6 : estUtilisee(int id)
    // → SELECT COUNT(*) FROM vehicule WHERE ID_Marque=?
    
    public boolean estUtilisee(int id){
        String sql = "SELECT COUNT(*) " +
                "FROM vehicule " + "WHERE ID_Marque=?";
        try {
            PreparedStatement ps =
                    uneDb.getMaConnexion().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                boolean resultat = rs.getInt(1) > 0;
                ps.close();
                return resultat;
            }
            ps.close();
            
        } catch (Exception e) {
            System.out.println("Erreur estUtilisee : " + e.getMessage());
        }
        return false;
    }
    

}