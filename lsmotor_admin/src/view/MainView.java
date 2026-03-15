package view;

import model.BDD;
import model.Utilisateur;

import javax.swing.*;
import java.awt.*;

/**
 * ============================================================
 *  VUE : MainView – Fenêtre principale post-connexion
 * ============================================================
 *  C'est la "coquille" de l'application après le login.
 *  Elle ne contient pas de logique métier, uniquement
 *  la structure visuelle et le système de navigation.
 *
 *  PRINCIPE (comme index.php en PHP) :
 *    → La sidebar dit "je veux aller à l'index 2"
 *    → MainView appelle afficherPanel(2)
 *    → Le CardLayout montre le bon JPanel
 *    → C'est tout. Le contenu est dans chaque Panel.
 *
 *  STRUCTURE VISUELLE :
 *
 *    ┌────────────────────────────────────────────────────────┐
 *    │              HEADER (fixe, toujours visible)           │
 *    ├──────────────┬─────────────────────────────────────────┤
 *    │              │                                         │
 *    │   SIDEBAR    │    CONTENU (change selon l'onglet)      │
 *    │   (fixe)     │    CardLayout → un Panel à la fois      │
 *    │              │                                         │
 *    │              │                                         │
 *    └──────────────┴─────────────────────────────────────────┘
 *
 *  UTILISATION depuis LoginController :
 *    MainView main = new MainView(db, utilisateurConnecte);
 *    main.setVisible(true);
 *    loginView.dispose();
 * ============================================================
 */
public class MainView extends JFrame {

    // ─────────────────────────────────────────────────────────
    //  NOMS DES CARTES pour le CardLayout
    //  Chaque panneau a un nom unique pour être affiché.
    // ─────────────────────────────────────────────────────────
    private static final String CARD_DASHBOARD    = "dashboard";
    private static final String CARD_UTILISATEURS = "utilisateurs";
    private static final String CARD_CATEGORIES   = "categories";
    private static final String CARD_MARQUES      = "marques";
    private static final String CARD_VEHICULES    = "vehicules";
    private static final String CARD_VENTES       = "ventes";
    private static final String CARD_CONFIG       = "config";

    // ─────────────────────────────────────────────────────────
    //  RÉFÉRENCES PARTAGÉES
    // ─────────────────────────────────────────────────────────

    /** Connexion BDD partagée avec tous les controllers */
    private final BDD         db;

    /** Utilisateur actuellement connecté */
    private final Utilisateur utilisateurConnecte;

    // ─────────────────────────────────────────────────────────
    //  COMPOSANTS PRINCIPAUX
    // ─────────────────────────────────────────────────────────

    private HeaderPanel  header;
    private SidebarPanel sidebar;

    /** Container qui change de panneau selon l'onglet */
    private JPanel     panelContenu;
    private CardLayout cardLayout;

    // ─────────────────────────────────────────────────────────
    //  PANNEAUX DE CHAQUE SECTION
    //  Créés une seule fois au démarrage, réutilisés ensuite.
    //  Accessibles via getters pour que les controllers
    //  puissent s'y brancher.
    // ─────────────────────────────────────────────────────────

    private DashboardPanel   dashboardPanel;
    private UtilisateurPanel utilisateurPanel;
    private CategoriePanel   categoriePanel;
    private MarquePanel      marquePanel;
    private VehiculePanel    vehiculePanel;
    private VentePanel       ventePanel;
    private ConfigPanel      configPanel;

    // ─────────────────────────────────────────────────────────
    //  CONSTRUCTEUR
    // ─────────────────────────────────────────────────────────

    /**
     * @param db                  Connexion BDD ouverte depuis LoginController
     * @param utilisateurConnecte L'admin actuellement connecté
     */
    public MainView(BDD db, Utilisateur utilisateurConnecte) {
        this.db                   = db;
        this.utilisateurConnecte  = utilisateurConnecte;
        configurerFenetre();
        construireUI();
        initialiserControllers();
    }

    // ─────────────────────────────────────────────────────────
    //  CONFIGURATION DE LA FENÊTRE
    // ─────────────────────────────────────────────────────────

    private void configurerFenetre() {
        setTitle("LS Motors – Console Administration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 800);
        setMinimumSize(new Dimension(1024, 640));
        setLocationRelativeTo(null);
        getContentPane().setBackground(Theme.DARK_BG);
    }

    // ─────────────────────────────────────────────────────────
    //  CONSTRUCTION DE L'INTERFACE
    // ─────────────────────────────────────────────────────────

    private void construireUI() {
        setLayout(new BorderLayout());

        // ── NORTH : Header fixe ───────────────────────────────
        String prenom = utilisateurConnecte != null
            ? utilisateurConnecte.getPrenom() : "Admin";
        String nom = utilisateurConnecte != null
            ? utilisateurConnecte.getNom()    : "";

        header = new HeaderPanel(prenom, nom);
        add(header, BorderLayout.NORTH);

        // ── WEST : Sidebar fixe ───────────────────────────────
        // Le lambda reçoit l'index cliqué et appelle afficherPanel()
        sidebar = new SidebarPanel(index -> afficherPanel(index));

        // Brancher la déconnexion sur la sidebar
        // Quand l'utilisateur confirme la déconnexion :
        //   → ferme la BDD
        //   → ferme cette fenêtre
        //   → rouvre LoginView
        sidebar.setOnDeconnexion(() -> seDeconnecter());

        add(sidebar, BorderLayout.WEST);

        // ── CENTER : Zone de contenu avec CardLayout ──────────
        cardLayout   = new CardLayout();
        panelContenu = new JPanel(cardLayout);
        panelContenu.setBackground(Theme.DARK_BG);

        // Créer tous les panneaux (une seule fois)
        dashboardPanel   = new DashboardPanel();
        utilisateurPanel = new UtilisateurPanel();
        categoriePanel   = new CategoriePanel();
        marquePanel      = new MarquePanel();
        vehiculePanel    = new VehiculePanel();
        ventePanel       = new VentePanel();
        configPanel      = new ConfigPanel();

        // Ajouter chaque panneau avec son nom-clé dans le CardLayout
        panelContenu.add(dashboardPanel,   CARD_DASHBOARD);
        panelContenu.add(utilisateurPanel, CARD_UTILISATEURS);
        panelContenu.add(categoriePanel,   CARD_CATEGORIES);
        panelContenu.add(marquePanel,      CARD_MARQUES);
        panelContenu.add(vehiculePanel,    CARD_VEHICULES);
        panelContenu.add(ventePanel,       CARD_VENTES);
        panelContenu.add(configPanel,      CARD_CONFIG);

        add(panelContenu, BorderLayout.CENTER);

        // Afficher le dashboard par défaut au démarrage
        afficherPanel(SidebarPanel.IDX_DASHBOARD);
    }

    // ─────────────────────────────────────────────────────────
    //  INITIALISATION DES CONTROLLERS
    // ─────────────────────────────────────────────────────────

    /**
     * Instancie et branche tous les controllers sur leurs panneaux.
     *
     * ➡️ QUAND TES CONTROLLERS SERONT IMPLÉMENTÉS :
     *    Décommente chaque ligne et crée le controller correspondant.
     *
     * Exemple pour UtilisateurController :
     *   new controller.UtilisateurController(db, utilisateurPanel);
     *
     * Le controller va :
     *   1. Recevoir le panel (la vue)
     *   2. Créer le modèle (new UtilisateurModel(db))
     *   3. Brancher les boutons (addActionListener)
     *   4. Charger les données initiales (chargerTableau())
     */
    private void initialiserControllers() {

        // ➡️ Décommente quand tes controllers sont prêts :

        // new controller.UtilisateurController(db, utilisateurPanel);
        // new controller.CategorieController(db, categoriePanel);
        // new controller.MarqueController(db, marquePanel);
        // new controller.VehiculeController(db, vehiculePanel);
        // new controller.VenteController(db, ventePanel);
        // new controller.ConfigController(db, configPanel);

    }

    // ─────────────────────────────────────────────────────────
    //  NAVIGATION – appelée par SidebarPanel
    // ─────────────────────────────────────────────────────────

    /**
     * Affiche le panneau correspondant à l'index donné.
     * Appelée par SidebarPanel quand l'utilisateur clique
     * sur un item de navigation.
     *
     * @param index Index du panneau (constantes SidebarPanel.IDX_xxx)
     *
     * EXEMPLE D'UTILISATION depuis un bouton du dashboard :
     *   btnVehicule.addActionListener(e ->
     *       afficherPanel(SidebarPanel.IDX_VEHICULES));
     */
    public void afficherPanel(int index) {
        // Met à jour visuellement la sidebar
        sidebar.setItemActif(index);

        // Affiche le bon panneau dans le CardLayout
        switch (index) {
            case SidebarPanel.IDX_DASHBOARD:
                cardLayout.show(panelContenu, CARD_DASHBOARD);
                break;
            case SidebarPanel.IDX_UTILISATEURS:
                cardLayout.show(panelContenu, CARD_UTILISATEURS);
                break;
            case SidebarPanel.IDX_CATEGORIES:
                cardLayout.show(panelContenu, CARD_CATEGORIES);
                break;
            case SidebarPanel.IDX_MARQUES:
                cardLayout.show(panelContenu, CARD_MARQUES);
                break;
            case SidebarPanel.IDX_VEHICULES:
                cardLayout.show(panelContenu, CARD_VEHICULES);
                break;
            case SidebarPanel.IDX_VENTES:
                cardLayout.show(panelContenu, CARD_VENTES);
                break;
            case SidebarPanel.IDX_CONFIG:
                cardLayout.show(panelContenu, CARD_CONFIG);
                break;
        }
    }

    // ─────────────────────────────────────────────────────────
    //  DÉCONNEXION
    // ─────────────────────────────────────────────────────────

    /**
     * Ferme la session et revient à l'écran de login.
     * Appelée par le callback de déconnexion dans la sidebar.
     *
     * ➡️ QUAND LoginView ET LoginController SERONT PRÊTS :
     *    Remplace System.exit(0) par :
     *      db.seDeconnecter();
     *      dispose();
     *      LoginView loginView = new LoginView();
     *      new controller.LoginController(db, loginView);
     *      loginView.setVisible(true);
     */
    private void seDeconnecter() {
        if (db != null) db.seDeconnecter();
        dispose();

        // ➡️ Remplace cette ligne quand LoginView est prêt :
        System.exit(0);

        // ➡️ Version finale (décommente quand prêt) :
        // LoginView loginView = new LoginView();
        // BDD nouvelleDb = new BDD(
        //     Main.SERVEUR, Main.USER, Main.MDP, Main.BDD_NOM
        // );
        // nouvelleDb.seConnecter();
        // new controller.LoginController(nouvelleDb, loginView);
        // loginView.setVisible(true);
    }

    // ─────────────────────────────────────────────────────────
    //  GETTERS – pour les controllers et le dashboard
    // ─────────────────────────────────────────────────────────

    /** Retourne la connexion BDD (utile pour les controllers) */
    public BDD getDb() { return db; }

    /** Retourne l'utilisateur connecté */
    public Utilisateur getUtilisateurConnecte() { return utilisateurConnecte; }

    // Getters des panneaux
    // Les controllers les reçoivent via le constructeur,
    // mais ces getters peuvent être utiles pour la navigation
    // depuis le dashboard (ex: bouton "Aller aux véhicules")

    public DashboardPanel   getDashboardPanel()   { return dashboardPanel;   }
    public UtilisateurPanel getUtilisateurPanel() { return utilisateurPanel; }
    public CategoriePanel   getCategoriePanel()   { return categoriePanel;   }
    public MarquePanel      getMarquePanel()       { return marquePanel;      }
    public VehiculePanel    getVehiculePanel()     { return vehiculePanel;    }
    public VentePanel       getVentePanel()        { return ventePanel;       }
    public ConfigPanel      getConfigPanel()       { return configPanel;      }
}