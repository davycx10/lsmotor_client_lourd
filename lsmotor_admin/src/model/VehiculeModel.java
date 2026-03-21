package model;

import model.POJOs.Vehicule;
import model.POJOs.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehiculeModel {

    // ── Requête de base réutilisée dans getAll() et rechercher()
    // Déclarée ici pour ne pas la réécrire dans chaque méthode
    private static final String SQL_BASE =
            "SELECT v.ID, v.NomModele, v.PrixCatalogue, " +
                    "v.Description, v.Image, v.Actif, " +
                    "v.ID_Marque, v.ID_Categorie, " +
                    "m.Nom AS nomMarque, c.Libelle AS nomCategorie " +
                    "FROM vehicule v " +
                    "JOIN marque    m ON v.ID_Marque    = m.ID " +
                    "JOIN categorie c ON v.ID_Categorie = c.ID ";

    // ── Attribut BDD ──────────────────────────────────────────
    private BDD uneDb;

    // ── Constructeur ──────────────────────────────────────────
    public VehiculeModel(BDD uneDb) {
        this.uneDb = uneDb;
    }

    // ============================================================
    //  MÉTHODE PRIVÉE : creerVehiculeDepuisRS(ResultSet rs)
    // ============================================================
    // Crée un objet Vehicule depuis une ligne du ResultSet.
    // Appelée dans getAll() et rechercher() pour éviter
    // de dupliquer le même bloc de setters partout.
    //
    // ⚠️ throws SQLException car rs.getInt(), rs.getString()...
    //    peuvent lancer cette exception → on laisse la méthode
    //    appelante (getAll/rechercher) s'en occuper dans son catch
    private Vehicule creerVehiculeDepuisRS(ResultSet rs)
            throws SQLException {
        Vehicule v = new Vehicule();
        v.setId(rs.getInt("ID"));
        v.setNomModele(rs.getString("NomModele"));
        v.setPrixCatalogue(rs.getDouble("PrixCatalogue"));
        v.setDescription(rs.getString("Description"));
        v.setImage(rs.getString("Image"));
        // ⚠️ Actif est un TINYINT en SQL (0 ou 1)
        //    → on compare à 1 pour obtenir un boolean Java
        v.setActif(rs.getInt("Actif") == 1);
        v.setIdMarque(rs.getInt("ID_Marque"));
        v.setIdCategorie(rs.getInt("ID_Categorie"));
        // Ces deux champs viennent du JOIN (alias SQL)
        v.setNomMarque(rs.getString("nomMarque"));
        v.setNomCategorie(rs.getString("nomCategorie"));
        return v;
    }

    // ============================================================
    //  MÉTHODE 1 : getAll()
    // ============================================================
    // Retourne tous les véhicules triés par nom de modèle.
    // Utilise SQL_BASE + ORDER BY pour éviter la répétition.
    public List<Vehicule> getAll() {
        List<Vehicule> liste = new ArrayList<>();

        // SQL_BASE contient déjà les JOIN marque + categorie
        String sql = SQL_BASE + "ORDER BY v.NomModele ASC";

        try {
            PreparedStatement ps =
                    uneDb.getMaConnexion().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // On appelle la méthode privée pour créer l'objet
                // → évite de réécrire les 10 setters ici
                liste.add(creerVehiculeDepuisRS(rs));
            }
            ps.close();

        } catch (SQLException e) {
            System.out.println("Erreur getAll : " + e.getMessage());
        }
        return liste;
    }

    // ============================================================
    //  MÉTHODE 2 : rechercher(String terme)
    // ============================================================
    // Retourne les véhicules dont le nom, la marque ou la
    // catégorie contient le terme recherché.
    public List<Vehicule> rechercher(String terme) {
        List<Vehicule> liste = new ArrayList<>();

        // SQL_BASE + WHERE sur 3 colonnes de 3 tables différentes
        // ⚠️ Les alias "m" et "c" viennent des JOIN dans SQL_BASE
        String sql = SQL_BASE +
                "WHERE v.NomModele LIKE ? " +
                "OR    m.Nom       LIKE ? " +
                "OR    c.Libelle   LIKE ? " +
                "ORDER BY v.NomModele ASC";

        try {
            PreparedStatement ps =
                    uneDb.getMaConnexion().prepareStatement(sql);

            // Les 3 paramètres reçoivent le même terme encadré de %
            String t = "%" + terme + "%";
            ps.setString(1, t);
            ps.setString(2, t);
            ps.setString(3, t);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                liste.add(creerVehiculeDepuisRS(rs));
            }
            ps.close();

        } catch (SQLException e) {
            System.out.println("Erreur rechercher : " + e.getMessage());
        }
        return liste;
    }

    // ============================================================
    //  MÉTHODE 3 : ajouter(Vehicule v)
    // ============================================================
    // Insère un nouveau véhicule en BDD.
    // ⚠️ On n'insère pas ID (auto_increment MySQL).
    public boolean ajouter(Vehicule v) {
        String sql = "INSERT INTO vehicule " +
                "(NomModele, ID_Marque, ID_Categorie, " +
                "PrixCatalogue, Description, Image, Actif) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps =
                    uneDb.getMaConnexion().prepareStatement(sql);

            // Remplir dans le même ordre que les colonnes du INSERT
            ps.setString(1, v.getNomModele());
            ps.setInt(2,    v.getIdMarque());
            ps.setInt(3,    v.getIdCategorie());
            ps.setDouble(4, v.getPrixCatalogue());
            ps.setString(5, v.getDescription());
            ps.setString(6, v.getImage());
            // ⚠️ isActif() retourne boolean → convertir en 0 ou 1
            ps.setInt(7,    v.isActif() ? 1 : 0);

            int lignes = ps.executeUpdate();
            ps.close();
            return lignes > 0;

        } catch (SQLException e) {
            System.out.println("Erreur ajouter : " + e.getMessage());
            return false;
        }
    }

    // ============================================================
    //  MÉTHODE 4 : modifier(Vehicule v)
    // ============================================================
    // Met à jour un véhicule existant en BDD.
    // ⚠️ L'ID est TOUJOURS le dernier paramètre car il est
    //    dans le WHERE et non dans le SET.
    public boolean modifier(Vehicule v) {
        String sql = "UPDATE vehicule " +
                "SET NomModele=?, ID_Marque=?, " +
                "ID_Categorie=?, PrixCatalogue=?, " +
                "Description=?, Image=?, Actif=? " +
                "WHERE ID=?";
        try {
            PreparedStatement ps =
                    uneDb.getMaConnexion().prepareStatement(sql);

            ps.setString(1, v.getNomModele());
            ps.setInt(2,    v.getIdMarque());
            ps.setInt(3,    v.getIdCategorie());
            ps.setDouble(4, v.getPrixCatalogue());
            ps.setString(5, v.getDescription());
            ps.setString(6, v.getImage());
            ps.setInt(7,    v.isActif() ? 1 : 0);
            // ⚠️ ID en position 8 → dernier, dans le WHERE
            ps.setInt(8,    v.getId());

            int lignes = ps.executeUpdate();
            ps.close();
            return lignes > 0;

        } catch (SQLException e) {
            System.out.println("Erreur modifier : " + e.getMessage());
            return false;
        }
    }

    // ============================================================
    //  MÉTHODE 5 : supprimer(int id)
    // ============================================================
    // Supprime un véhicule par son ID.
    // ⚠️ Si des ventes existent pour ce véhicule,
    //    MySQL refusera (FK fk_vente_vehicule).
    //    → Appeler estUtilise(id) AVANT dans le controller.
    public boolean supprimer(int id) {
        String sql = "DELETE FROM vehicule WHERE ID = ?";
        try {
            PreparedStatement ps =
                    uneDb.getMaConnexion().prepareStatement(sql);
            ps.setInt(1, id);

            int lignes = ps.executeUpdate();
            ps.close();
            return lignes > 0;

        } catch (SQLException e) {
            // Détecte si c'est une erreur de clé étrangère
            if (e.getMessage().contains("foreign key")) {
                System.out.println(
                        "Suppression impossible : ce véhicule " +
                                "a des ventes associées."
                );
            } else {
                System.out.println("Erreur supprimer : " + e.getMessage());
            }
            return false;
        }
    }

    // ============================================================
    //  MÉTHODE 6 : estUtilise(int id)
    // ============================================================
    // Vérifie si ce véhicule a des ventes avant suppression.
    // Retourne true si des ventes existent → bloquer la suppression.
    // Appelée dans VehiculeController.supprimer() AVANT le DELETE.
    public boolean estUtilise(int id) {
        String sql = "SELECT COUNT(*) FROM vente WHERE ID_Vehicule = ?";
        try {
            PreparedStatement ps =
                    uneDb.getMaConnexion().prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // Stocker avant de fermer, puis retourner
                boolean resultat = rs.getInt(1) > 0;
                ps.close();
                return resultat;
            }
            ps.close();

        } catch (SQLException e) {
            System.out.println("Erreur estUtilise : " + e.getMessage());
        }
        return false;
    }
}