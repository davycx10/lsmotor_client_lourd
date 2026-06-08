package model;

import java.sql.*;
import model.POJOs.Marque;
import java.util.ArrayList;
import java.util.List;

public class MarqueModel {

    // Déclaration des attributs
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