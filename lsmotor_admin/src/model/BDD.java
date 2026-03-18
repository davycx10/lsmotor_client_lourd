package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BDD {

    // Attributs propres à BDD
    private final String serveur, user, mdp, bdd;
    private Connection maConnexion;

    // Constructeur : reçoit les 4 paramètres
    public BDD(String serveur, String user, String mdp, String bdd) {
        super();
        this.serveur      = serveur;
        this.user         = user;
        this.mdp          = mdp;
        this.bdd          = bdd;
        this.maConnexion  = null;
    }

    public void chargerPilote() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Absence du pilote JDBC");
        }
    }

    public void seConnecter() {
        this.chargerPilote();
        String url = "jdbc:mysql://" + this.serveur + "/" + this.bdd;
        try {
            this.maConnexion = DriverManager.getConnection(
                    url, this.user, this.mdp
            );
        } catch (SQLException e) {
            System.out.println("Erreur de connexion a : " + url);
        }
    }

    public void seDeconnecter() {
        try {
            if (maConnexion != null) {
                this.maConnexion.close();
            }
        } catch (SQLException e) {
            System.out.println("Erreur de fermeture de la connexion");
        }
    }

    public Connection getMaConnexion() {
        return this.maConnexion;
    }
}