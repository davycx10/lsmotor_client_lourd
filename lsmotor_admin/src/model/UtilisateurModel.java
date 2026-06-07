package model;

import model.POJOs.Utilisateur;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurModel {

    private BDD uneDb;

    // Le constructeur reçoit la BDD et la stocke
    public UtilisateurModel(BDD db) {
        this.uneDb = db;
    }

    // ============================================================
    //  MÉTHODE 1 : getAll()
    // ============================================================
    public List<Utilisateur> getAll() {
        List<Utilisateur> liste = new ArrayList<>();

        // ⚠Pas de Passwrd dans le SELECT (sécurité)
        String sql = "SELECT ID, Nom, Prenom, Email, Role, " +
                "DiscordPseudo, DateInscription " +
                "FROM utilisateur ORDER BY Nom ASC";
        try {
            // PreparedStatement même sans paramètres
            PreparedStatement ps =
                    uneDb.getMaConnexion().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Utilisateur user = new Utilisateur(
                        rs.getInt("ID"),
                        rs.getString("Nom"),
                        rs.getString("Prenom"),
                        rs.getString("Email"),
                        "",  // Passwrd vide volontairement
                        rs.getString("Role"),
                        rs.getString("DiscordPseudo"),
                        rs.getString("DateInscription")
                );
                liste.add(user);
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
    public List<Utilisateur> rechercher(String terme) {
        List<Utilisateur> liste = new ArrayList<>();

        String sql = "SELECT ID, Nom, Prenom, Email, Role, " +
                "DiscordPseudo, DateInscription " +
                "FROM utilisateur " +
                "WHERE Nom LIKE ? OR Prenom LIKE ? OR Email LIKE ? " +
                "ORDER BY Nom ASC";
        try {
            PreparedStatement ps =
                    uneDb.getMaConnexion().prepareStatement(sql);

            // Les 3 paramètres ? reçoivent le même terme encadré de %
            String t = "%" + terme + "%";
            ps.setString(1, t);
            ps.setString(2, t);
            ps.setString(3, t);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Utilisateur user = new Utilisateur(
                        rs.getInt("ID"),
                        rs.getString("Nom"),
                        rs.getString("Prenom"),
                        rs.getString("Email"),
                        "",
                        rs.getString("Role"),
                        rs.getString("DiscordPseudo"),
                        rs.getString("DateInscription")
                );
                liste.add(user);
            }
            ps.close();

        } catch (SQLException e) {
            System.out.println("Erreur rechercher : " + e.getMessage());
        }
        return liste;
    }

    // ============================================================
    //  MÉTHODE 3 : verifierConnexion(String email, String mdp)
    // ============================================================
    public Utilisateur verifierConnexion(String email, String mdp) {

        // On retourne un Utilisateur, pas un Admin (inexistant)
        Utilisateur admin = null;

        // On vérifie email + mdp + Role = 'admin' directement en SQL
        String sql = "SELECT ID, Nom, Prenom, Email, Role, " +
                "DiscordPseudo, DateInscription " +
                "FROM utilisateur " +
                "WHERE Email = ? AND Passwrd = ? AND Role = 'admin'";
        try {
            PreparedStatement ps =
                    uneDb.getMaConnexion().prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, mdp);

            ResultSet rs = ps.executeQuery();

            // Si une ligne est trouvée → c'est un admin valide
            if (rs.next()) {
                admin = new Utilisateur(
                        rs.getInt("ID"),
                        rs.getString("Nom"),
                        rs.getString("Prenom"),
                        rs.getString("Email"),
                        "",  // Passwrd non ramené en mémoire
                        rs.getString("Role"),
                        rs.getString("DiscordPseudo"),
                        rs.getString("DateInscription")
                );
            }
            ps.close();

        } catch (SQLException e) {
            System.out.println("Erreur verifierConnexion : " + e.getMessage());
        }

        // Retourne l'admin trouvé, ou null si email/mdp/rôle incorrects
        return admin;
    }

    // ============================================================
    //  MÉTHODE 4 : ajouter(Utilisateur u)
    // ============================================================
    public boolean ajouter(Utilisateur u) {
        String sql = "INSERT INTO utilisateur " +
                "(Nom, Prenom, Email, Passwrd, Role, DiscordPseudo) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps =
                    uneDb.getMaConnexion().prepareStatement(sql);
                    ps.setString(1, u.getNom());
                    ps.setString(2, u.getPrenom());
                    ps.setString(3, u.getEmail());
                    ps.setString(4, u.getPasswrd());
                    ps.setString(5, u.getRole());
                    ps.setString(6, u.getDiscordPseudo());

            int lignes = ps.executeUpdate();
            ps.close();
            return lignes > 0;

        } catch (SQLException e) {
            System.out.println("Erreur ajouter : " + e.getMessage());
            return false;
        }
    }

    // ============================================================
    //  MÉTHODE 5 : modifier(Utilisateur u)
    // ============================================================
    public boolean modifier(Utilisateur u) {
        try {
            PreparedStatement ps;

            // CAS 1 : mot de passe fourni → on le met à jour
            if (u.getPasswrd() != null && !u.getPasswrd().isEmpty()) {
                String sql = "UPDATE utilisateur " +
                        "SET Nom=?, Prenom=?, Email=?, Passwrd=?, " +
                        "Role=?, DiscordPseudo=? " +
                        "WHERE ID=?";
                ps = uneDb.getMaConnexion().prepareStatement(sql);
                ps.setString(1, u.getNom());
                ps.setString(2, u.getPrenom());
                ps.setString(3, u.getEmail());
                ps.setString(4, u.getPasswrd());
                ps.setString(5, u.getRole());
                ps.setString(6, u.getDiscordPseudo());
                ps.setInt(7, u.getId()); // ID toujours en dernier

                // CAS 2 : mot de passe vide → on ne le change pas
            } else {
                String sql = "UPDATE utilisateur " +
                        "SET Nom=?, Prenom=?, Email=?, " +
                        "Role=?, DiscordPseudo=? " +
                        "WHERE ID=?";
                ps = uneDb.getMaConnexion().prepareStatement(sql);
                ps.setString(1, u.getNom());
                ps.setString(2, u.getPrenom());
                ps.setString(3, u.getEmail());
                ps.setString(4, u.getRole());
                ps.setString(5, u.getDiscordPseudo());
                ps.setInt(6, u.getId()); // ID toujours en dernier
            }

            int lignes = ps.executeUpdate();
            ps.close();
            return lignes > 0;

        } catch (SQLException e) {
            System.out.println("Erreur modifier : " + e.getMessage());
            return false;
        }
    }

    // ============================================================
    //  MÉTHODE 6 : supprimer(int id)
    // ============================================================
    public boolean supprimer(int id) {
        String sql = "DELETE FROM utilisateur WHERE ID = ?";
        try {
            PreparedStatement ps =
                    uneDb.getMaConnexion().prepareStatement(sql);
            ps.setInt(1, id);

            int lignes = ps.executeUpdate();
            ps.close();
            return lignes > 0;

        } catch (SQLException e) {
            // Détecte si c'est une erreur de clé étrangère (ventes associées)
            if (e.getMessage().contains("foreign key")) {
                System.out.println(
                        "Suppression impossible : cet utilisateur a des ventes."
                );
            } else {
                System.out.println("Erreur supprimer : " + e.getMessage());
            }
            return false;
        }
    }
}