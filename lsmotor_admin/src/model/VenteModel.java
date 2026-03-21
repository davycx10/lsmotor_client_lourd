package model;

import model.POJOs.Config;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VenteModel {

    // ── Attribut BDD ──────────────────────────────────────────
    private BDD uneDb;

    // ── Constructeur ──────────────────────────────────────────
    public VenteModel(BDD uneDb) {
        this.uneDb = uneDb;
    }

    // ============================================================
    //  MÉTHODE 1 : getAllGlobal()
    // ============================================================
    // Retourne les ventes regroupées par semaine.
    // On retourne List<Object[]> et non List<Vente> car ce sont
    // des données agrégées (SUM, COUNT) → pas une ligne unique.
    public List<Object[]> getAllGlobal() {
        List<Object[]> liste = new ArrayList<>();

        // DATE_FORMAT('%x%v') → "202512" = semaine 12 de 2025
        String sql = "SELECT " +
                "DATE_FORMAT(DateVente, '%x%v') AS semaine, " +
                "MIN(DateVente)                 AS debut, " +
                "COUNT(*)                       AS nb_ventes, " +
                "SUM(PrixVente)                 AS total " +
                "FROM vente " +
                "GROUP BY DATE_FORMAT(DateVente, '%x%v') " +
                "ORDER BY semaine DESC";
        try {
            PreparedStatement ps =
                    uneDb.getMaConnexion().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Un tableau de 4 valeurs par ligne de résultat
                Object[] ligne = new Object[4];
                ligne[0] = rs.getString("semaine");
                ligne[1] = rs.getString("debut");
                ligne[2] = rs.getInt("nb_ventes");
                ligne[3] = rs.getDouble("total");
                liste.add(ligne);
            }
            ps.close();

        } catch (SQLException e) {
            System.out.println("Erreur getAllGlobal : " + e.getMessage());
        }
        return liste;
    }

    // ============================================================
    //  MÉTHODE 2 : getAllParEmploye()
    // ============================================================
    // Retourne le détail de chaque vente avec le nom de l'employé
    // et le nom du véhicule grâce aux JOIN.
    public List<Object[]> getAllParEmploye() {
        List<Object[]> liste = new ArrayList<>();

        // CONCAT pour assembler prénom + nom de l'employé
        // JOIN utilisateur pour l'employé, JOIN vehicule pour le nom
        String sql = "SELECT " +
                "v.DateVente, " +
                "CONCAT(u.Prenom, ' ', u.Nom) AS employe, " +
                "vh.NomModele                  AS vehicule, " +
                "v.NomClient, " +
                "v.PrixVente " +
                "FROM vente v " +
                "JOIN utilisateur u  ON v.ID_Employe  = u.ID " +
                "JOIN vehicule    vh ON v.ID_Vehicule = vh.ID " +
                "ORDER BY v.DateVente DESC";
        try {
            PreparedStatement ps =
                    uneDb.getMaConnexion().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Un tableau de 5 valeurs par ligne de résultat
                Object[] ligne = new Object[5];
                ligne[0] = rs.getString("DateVente");
                ligne[1] = rs.getString("employe");
                ligne[2] = rs.getString("vehicule");
                ligne[3] = rs.getString("NomClient");
                ligne[4] = rs.getDouble("PrixVente");
                liste.add(ligne);
            }
            ps.close();

        } catch (SQLException e) {
            System.out.println("Erreur getAllParEmploye : " + e.getMessage());
        }
        return liste;
    }

    // ============================================================
    //  MÉTHODE 3 : getTotalSemaineCourante()
    // ============================================================
    // Retourne le total GTA$ des ventes de la semaine en cours.
    // COALESCE évite que SUM retourne NULL si aucune vente
    // → COALESCE(NULL, 0) retourne 0 à la place.
    public double getTotalSemaineCourante() {
        String sql = "SELECT COALESCE(SUM(PrixVente), 0) AS total " +
                "FROM vente " +
                "WHERE DATE_FORMAT(DateVente, '%x%v') " +
                "    = DATE_FORMAT(NOW(), '%x%v')";
        try {
            PreparedStatement ps =
                    uneDb.getMaConnexion().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                double total = rs.getDouble("total");
                ps.close();
                return total;
            }
            ps.close();

        } catch (SQLException e) {
            System.out.println("Erreur getTotalSemaineCourante : "
                    + e.getMessage());
        }
        // Aucune vente ou erreur → on retourne 0
        return 0.0;
    }

    // ============================================================
    //  MÉTHODE 4 : getNbVentesSemaineCourante()
    // ============================================================
    // Retourne le nombre de ventes de la semaine en cours.
    // Même principe que getTotalSemaineCourante() mais avec COUNT.
    public int getNbVentesSemaineCourante() {
        String sql = "SELECT COUNT(*) AS nb " +
                "FROM vente " +
                "WHERE DATE_FORMAT(DateVente, '%x%v') " +
                "    = DATE_FORMAT(NOW(), '%x%v')";
        try {
            PreparedStatement ps =
                    uneDb.getMaConnexion().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int nb = rs.getInt("nb");
                ps.close();
                return nb;
            }
            ps.close();

        } catch (SQLException e) {
            System.out.println("Erreur getNbVentesSemaineCourante : "
                    + e.getMessage());
        }
        return 0;
    }

    // ============================================================
    //  MÉTHODE 5 : getMarge()
    // ============================================================
    // Lit la marge dans la table config (1 seule ligne, ID = 1).
    // Retourne un objet Config avec marge + date de modif.
    // Retourne null si la ligne n'existe pas (ne devrait pas arriver).
    public Config getMarge() {
        String sql = "SELECT ID, MargePourcent, DateMaj " +
                "FROM config " +
                "WHERE ID = 1";
        try {
            PreparedStatement ps =
                    uneDb.getMaConnexion().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Config config = new Config(
                        rs.getInt("ID"),
                        rs.getDouble("MargePourcent"),
                        rs.getString("DateMaj")
                );
                ps.close();
                return config;
            }
            ps.close();

        } catch (SQLException e) {
            System.out.println("Erreur getMarge : " + e.getMessage());
        }
        // Ligne introuvable ou erreur SQL
        return null;
    }

    // ============================================================
    //  MÉTHODE 6 : saveMarge(double marge)
    // ============================================================
    // Met à jour la marge dans config.
    // ⚠️ On ne touche PAS DateMaj → MySQL le fait automatiquement
    //    grâce à ON UPDATE CURRENT_TIMESTAMP dans le script SQL.
    public boolean saveMarge(double marge) {
        String sql = "UPDATE config " +
                "SET MargePourcent = ? " +
                "WHERE ID = 1";
        try {
            PreparedStatement ps =
                    uneDb.getMaConnexion().prepareStatement(sql);
            ps.setDouble(1, marge);

            int lignes = ps.executeUpdate();
            ps.close();
            return lignes > 0;

        } catch (SQLException e) {
            System.out.println("Erreur saveMarge : " + e.getMessage());
            return false;
        }
    }

    // ============================================================
    //  MÉTHODE 7 (BONUS) : getNbVehiculesTotal()
    // ============================================================
    // Retourne le nombre de véhicules actifs.
    // Utilisée par DashboardPanel pour la carte "Véhicules".
    // ⚠️ WHERE Actif = 1 → seulement les véhicules visibles.
    public int getNbVehiculesTotal() {
        String sql = "SELECT COUNT(*) AS nb " +
                "FROM vehicule " +
                "WHERE Actif = 1";
        try {
            PreparedStatement ps =
                    uneDb.getMaConnexion().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int nb = rs.getInt("nb");
                ps.close();
                return nb;
            }
            ps.close();

        } catch (SQLException e) {
            System.out.println("Erreur getNbVehiculesTotal : "
                    + e.getMessage());
        }
        return 0;
    }

    // ============================================================
    //  MÉTHODE 8 (BONUS) : getNbUtilisateursTotal()
    // ============================================================
    // Retourne le nombre total d'utilisateurs enregistrés.
    // Utilisée par DashboardPanel pour la carte "Utilisateurs".
    public int getNbUtilisateursTotal() {
        String sql = "SELECT COUNT(*) AS nb FROM utilisateur";
        try {
            PreparedStatement ps =
                    uneDb.getMaConnexion().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int nb = rs.getInt("nb");
                ps.close();
                return nb;
            }
            ps.close();

        } catch (SQLException e) {
            System.out.println("Erreur getNbUtilisateursTotal : "
                    + e.getMessage());
        }
        return 0;
    }
}