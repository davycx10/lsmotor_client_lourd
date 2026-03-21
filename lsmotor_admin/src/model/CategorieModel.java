package model;

import model.POJOs.Categorie;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategorieModel {

    private BDD uneDb;

    public CategorieModel(BDD uneDb) {
        this.uneDb = uneDb;
    }

    // ── Méthode 1 : getAll() ─────────────────────────────────
    public List<Categorie> getAll() {
        List<Categorie> liste = new ArrayList<>();

        String sql = "SELECT ID, Libelle " +
                "FROM categorie " +
                "ORDER BY Libelle ASC";
        try {
            PreparedStatement ps =
                    uneDb.getMaConnexion().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Categorie c = new Categorie(
                        rs.getInt("ID"),
                        rs.getString("Libelle")
                );
                liste.add(c);
            }
            ps.close();

        } catch (Exception e) {
            System.out.println("Erreur getAll : " + e.getMessage());
        }
        return liste;
    }

    // ── Méthode 2 : rechercher(String terme) ─────────────────
    public List<Categorie> rechercher(String terme) {
        List<Categorie> liste = new ArrayList<>();

        String sql = "SELECT ID, Libelle " +
                "FROM categorie " +
                "WHERE Libelle LIKE ? " +
                "ORDER BY Libelle ASC";
        try {
            PreparedStatement ps =
                    uneDb.getMaConnexion().prepareStatement(sql);
            ps.setString(1, "%" + terme + "%");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Categorie c = new Categorie(
                        rs.getInt("ID"),
                        rs.getString("Libelle")
                );
                liste.add(c);
            }
            ps.close();

        } catch (Exception e) {
            System.out.println("Erreur rechercher : " + e.getMessage());
        }
        return liste;
    }

    // ── Méthode 3 : ajouter(String libelle) ──────────────────
    public boolean ajouter(String libelle) {
        String sql = "INSERT INTO categorie (Libelle) VALUES (?)";
        try {
            PreparedStatement ps =
                    uneDb.getMaConnexion().prepareStatement(sql);
            ps.setString(1, libelle);

            int lignes = ps.executeUpdate();
            ps.close();
            return lignes > 0;

        } catch (Exception e) {
            System.out.println("Erreur ajouter : " + e.getMessage());
            return false;
        }
    }

    // ── Méthode 4 : modifier(int id, String libelle) ─────────
    public boolean modifier(int id, String libelle) {
        String sql = "UPDATE categorie " +
                "SET Libelle=? " +
                "WHERE ID=?";
        try {
            PreparedStatement ps =
                    uneDb.getMaConnexion().prepareStatement(sql);
            ps.setString(1, libelle);
            ps.setInt(2, id);

            int lignes = ps.executeUpdate();
            ps.close();
            return lignes > 0;

        } catch (Exception e) {
            System.out.println("Erreur modifier : " + e.getMessage());
            return false;
        }
    }

    // ── Méthode 5 : supprimer(int id) ────────────────────────
    public boolean supprimer(int id) {
        String sql = "DELETE FROM categorie WHERE ID = ?";
        try {
            PreparedStatement ps =
                    uneDb.getMaConnexion().prepareStatement(sql);
            ps.setInt(1, id);

            int lignes = ps.executeUpdate();
            ps.close();
            return lignes > 0;

        } catch (Exception e) {
            System.out.println("Erreur supprimer : " + e.getMessage());
            return false;
        }
    }

    // ── Méthode 6 : estUtilisee(int id) ──────────────────────
    public boolean estUtilisee(int id) {
        String sql = "SELECT COUNT(*) " +
                "FROM vehicule " +
                "WHERE ID_Categorie = ?";
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