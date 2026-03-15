package model;


import java.sql.*;
import java.util.ArrayList;

public class Model {

    // ---------------------------------------------------------
    // Connexion à la base via ta classe BDD
    // ---------------------------------------------------------
    // private static BDD uneBdd = new BDD("localhost", "mysql_user", "mysql_password", "database_name");
    private static DataBaseConection uneDataBase = new DataBaseConection("localhost", "adminphp", "ls_motors", "");
    // ---------------------------------------------------------
    // Création d'un admin (première authentification)
    // ---------------------------------------------------------
    public static boolean creerAdmin(String nom, String prenom, String email, String mdp) {
        boolean ok = false;
        String requete = "INSERT INTO admin VALUES (NULL, ?, ?, ?, ?);";

        try {
            uneBdd.seConnecter();
            PreparedStatement ps = uneBdd.getMaConnexion().prepareStatement(requete);

            ps.setString(1, nom);
            ps.setString(2, prenom);
            ps.setString(3, email);
            ps.setString(4, mdp);

            ps.executeUpdate();
            ok = true;

            ps.close();
            uneBdd.seDeconnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur creerAdmin : " + exp.getMessage());
        }

        return ok;
    }

    // ---------------------------------------------------------
    // Connexion d'un admin existant
    // ---------------------------------------------------------
    public static Admin connexionAdmin(String email, String mdp) {
        Admin unAdmin = null;
        String requete = "SELECT * FROM admin WHERE email = ? AND mot_de_passe = ?;";

        try {
            uneBdd.seConnecter();
            PreparedStatement ps = uneBdd.getMaConnexion().prepareStatement(requete);

            ps.setString(1, email);
            ps.setString(2, mdp);

            ResultSet unRes = ps.executeQuery();

            if (unRes.next()) {
                unAdmin = new Admin(
                    unRes.getInt("id_admin"),
                    unRes.getString("nom"),
                    unRes.getString("prenom"),
                    unRes.getString("email"),
                    unRes.getString("mot_de_passe")
                );
            }

            ps.close();
            uneBdd.seDeconnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur connexionAdmin : " + exp.getMessage());
        }

        return unAdmin;
    }

    // ---------------------------------------------------------
    // Récupération de toutes les candidatures
    // ---------------------------------------------------------
    public static ArrayList<Candidature> getAllCandidatures() {
        ArrayList<Candidature> liste = new ArrayList<>();
        String requete = "SELECT * FROM candidature ORDER BY date_candidature DESC;";

        try {
            uneBdd.seConnecter();
            Statement st = uneBdd.getMaConnexion().createStatement();
            ResultSet rs = st.executeQuery(requete);

            while (rs.next()) {
                Candidature c = new Candidature(
                    rs.getInt("id_candidature"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("email"),
                    rs.getString("adresse"),
                    rs.getInt("basic_fit"),
                    rs.getString("specialite"),
                    rs.getInt("experience"),
                    rs.getString("cv_pdf"),
                    rs.getString("linkedin"),
                    rs.getString("password"),
                    rs.getString("statut")
                );
                liste.add(c);
            }

            st.close();
            uneBdd.seDeconnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur getAllCandidatures : " + exp.getMessage());
        }

        return liste;
    }

    // ---------------------------------------------------------
    // Validation d'une candidature → insertion dans coach
    // ---------------------------------------------------------
    public static boolean validerCandidature(Candidature c) {
        boolean ok = false;

        String requete = "INSERT INTO coach VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?);";

        try {
            uneBdd.seConnecter();
            PreparedStatement ps = uneBdd.getMaConnexion().prepareStatement(requete);

            ps.setString(1, c.getNom());
            ps.setString(2, c.getPrenom());
            ps.setString(3, c.getEmail());
            ps.setString(4, c.getAdresse());
            ps.setInt(5, c.getBasicFit());
            ps.setString(6, c.getSpecialite());
            ps.setInt(7, c.getExperience());
            ps.setString(8, c.getPassword());

            ps.executeUpdate();
            ok = true;

            ps.close();
            uneBdd.seDeconnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur validerCandidature : " + exp.getMessage());
        }

        return ok;
    }

    // ---------------------------------------------------------
    // Mise à jour du statut d'une candidature
    // ---------------------------------------------------------
    public static void updateStatutCandidature(int id, String statut) {
        String requete = "UPDATE candidature SET statut = ? WHERE id_candidature = ?;";

        try {
            uneBdd.seConnecter();
            PreparedStatement ps = uneBdd.getMaConnexion().prepareStatement(requete);

            ps.setString(1, statut);
            ps.setInt(2, id);

            ps.executeUpdate();
            ps.close();
            uneBdd.seDeconnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur updateStatutCandidature : " + exp.getMessage());
        }
    }

    // ---------------------------------------------------------
    // Récupération de tous les coachs
    // ---------------------------------------------------------
    public static ArrayList<Coach> getAllCoachs() {
        ArrayList<Coach> liste = new ArrayList<>();
        String requete = "SELECT * FROM coach;";

        try {
            uneBdd.seConnecter();
            Statement st = uneBdd.getMaConnexion().createStatement();
            ResultSet rs = st.executeQuery(requete);

            while (rs.next()) {
                Coach c = new Coach(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("email"),
                    rs.getString("adresse"),
                    rs.getInt("basic_fit"),
                    rs.getString("specialite"),
                    rs.getInt("experience"),
                    rs.getString("password")
                );
                liste.add(c);
            }

            st.close();
            uneBdd.seDeconnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur getAllCoachs : " + exp.getMessage());
        }

        return liste;
    }

    // ---------------------------------------------------------
    // Suppression d'un coach
    // ---------------------------------------------------------
    public static boolean supprimerCoach(int idCoach) {
        boolean ok = false;
        String requete = "DELETE FROM coach WHERE id = ?;";

        try {
            uneBdd.seConnecter();
            PreparedStatement ps = uneBdd.getMaConnexion().prepareStatement(requete);

            ps.setInt(1, idCoach);
            ps.executeUpdate();
            ok = true;

            ps.close();
            uneBdd.seDeconnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur supprimerCoach : " + exp.getMessage());
        }

        return ok;
    }

    // ---------------------------------------------------------
    // Suppression d'un client
    // ---------------------------------------------------------
    public static boolean supprimerClient(int idClient) {
        boolean ok = false;
        String requete = "DELETE FROM client WHERE id = ?;";

        try {
            uneBdd.seConnecter();
            PreparedStatement ps = uneBdd.getMaConnexion().prepareStatement(requete);

            ps.setInt(1, idClient);
            ps.executeUpdate();
            ok = true;

            ps.close();
            uneBdd.seDeconnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur supprimerClient : " + exp.getMessage());
        }

        return ok;
    }

    // ---------------------------------------------------------
    // Récupération de tous les clients
    // ---------------------------------------------------------
    public static ArrayList<Client> getAllClients() {
        ArrayList<Client> liste = new ArrayList<>();
        String requete = "SELECT * FROM client;";

        try {
            uneBdd.seConnecter();
            Statement st = uneBdd.getMaConnexion().createStatement();
            ResultSet rs = st.executeQuery(requete);

            while (rs.next()) {
                Client c = new Client(
                    rs.getInt("id_client"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("mail"),
                    rs.getString("mot_de_passe"),
                    rs.getInt("poids"),
                    rs.getInt("taille"),
                    rs.getString("genre"),
                    rs.getInt("basic_fit"),
                    rs.getString("objectif"),
                    rs.getString("dispo_jours"),
                    rs.getString("dispo_creneaux"),
                    rs.getString("motivation"),
                    rs.getInt("id_coach")
                );
                liste.add(c);
            }

            st.close();
            uneBdd.seDeconnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur getAllClients : " + exp.getMessage());
        }

        return liste;
    }
}