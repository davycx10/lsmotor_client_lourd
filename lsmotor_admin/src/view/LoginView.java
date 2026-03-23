package view;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * ============================================================
 *  VUE : LoginView – Écran de connexion administrateur
 * ============================================================
 *  C'est la première fenêtre affichée au lancement.
 *  Elle NE contient aucune logique → tout est dans LoginController.
 *
 *  STRUCTURE VISUELLE :
 *
 *    ┌─────────────────────────────────────────────────────┐
 *    │             Fond noir (DARK_BG)                     │
 *    │                                                     │
 *    │        ┌──────────────────────────────┐             │
 *    │        │   LS MOTORS                  │             │
 *    │        │   Console Administration     │             │
 *    │        │   ─────────────────────────  │             │
 *    │        │   Email                      │             │
 *    │        │   [ champ email          ]   │             │
 *    │        │   Mot de passe               │             │
 *    │        │   [ champ mot de passe   ]   │             │
 *    │        │                              │             │
 *    │        │   [    SE CONNECTER      ]   │             │
 *    │        │                              │             │
 *    │        │   ● message erreur           │             │
 *    │        └──────────────────────────────┘             │
 *    └─────────────────────────────────────────────────────┘
 *
 *  COMMENT LE CONTROLLER L'UTILISE :
 *    LoginView vue = new LoginView();
 *    vue.getBtnConnexion().addActionListener(e -> seConnecter());
 *    String email = vue.getEmail();
 *    String mdp   = vue.getMotDePasse();
 *    vue.afficherErreur("Email ou mot de passe incorrect");
 * ============================================================
 */
public class LoginView extends JFrame {

    // ── Composants accessibles par LoginController ────────────
    private JTextField     champEmail;
    private JPasswordField champMotDePasse;
    private JButton        btnConnexion;
    private JLabel         lblErreur;

    public LoginView() {
        configurerFenetre();
        construireUI();
    }

    // ─────────────────────────────────────────────────────────
    //  CONFIGURATION DE LA FENÊTRE
    // ─────────────────────────────────────────────────────────

    private void configurerFenetre() {
        setTitle("LS Motors – Console Administration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(960, 640);
        setMinimumSize(new Dimension(800, 560));
        setLocationRelativeTo(null); // Centre à l'écran
        setResizable(false);
        getContentPane().setBackground(Theme.DARK_BG);
    }

    // ─────────────────────────────────────────────────────────
    //  CONSTRUCTION DE L'INTERFACE
    // ─────────────────────────────────────────────────────────

    private void construireUI() {
        // Layout principal : GridBagLayout pour centrer la carte
        setLayout(new GridBagLayout());
        getContentPane().setBackground(Theme.DARK_BG);

        // Panneau gauche décoratif + carte droite
        JPanel wrapper = new JPanel(new GridLayout(1, 2, 0, 0));
        wrapper.setBackground(Theme.DARK_BG);
        wrapper.setPreferredSize(new Dimension(860, 520));

        wrapper.add(creerPanneauGauche());
        wrapper.add(creerCarte());

        add(wrapper, new GridBagConstraints());
    }

    /**
     * Panneau gauche : branding LS Motors (logo + slogan).
     * Zone purement décorative, fond légèrement différent.
     */
    private JPanel creerPanneauGauche() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                // Fond dégradé vertical du noir vers DARK_SURFACE
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(
                    0, 0,             Theme.DARK_BG,
                    0, getHeight(),   Theme.DARK_SURFACE
                );
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());

                // Ligne dorée à droite (séparateur entre les deux panneaux)
                g2.setColor(Theme.GOLD_DARK);
                g2.setStroke(new BasicStroke(1));
                g2.drawLine(getWidth() - 1, 0, getWidth() - 1, getHeight());

                g2.dispose();
                super.paintComponent(g);
            }
        };
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(60, 50, 60, 50));

        // ── Logo ──────────────────────────────────────────────
        // ⚠️ POUR UTILISER L'IMAGE :
        ImageIcon icon = new ImageIcon(
                getClass().getResource("/resources/logo_wide.png")
        );

        Image scaled = icon.getImage().getScaledInstance(350, 160, Image.SCALE_SMOOTH);
        JLabel lblLogo = new JLabel(new ImageIcon(scaled));
        lblLogo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(lblLogo);
        // ─────────────────────────────────────────────────────

        // Texte logo (si pas d'image)
        // JLabel lblLS = new JLabel("LS");
        // lblLS.setFont(new Font("Segoe UI", Font.BOLD, 52));
        // lblLS.setForeground(Theme.GOLD);
        //lblLS.setAlignmentX(Component.LEFT_ALIGNMENT);

        // JLabel lblMotors = new JLabel("MOTORS");
        // lblMotors.setFont(new Font("Segoe UI", Font.PLAIN, 32));
        // lblMotors.setForeground(Theme.TEXT_WHITE);
        // lblMotors.setAlignmentX(Component.LEFT_ALIGNMENT);

        // JLabel lblDealership = new JLabel("DEALERSHIP");
        // lblDealership.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        // lblDealership.setForeground(Theme.TEXT_GRAY);
        // lblDealership.setAlignmentX(Component.LEFT_ALIGNMENT);

        // panel.add(Box.createVerticalGlue());
        // panel.add(lblLS);
        // panel.add(lblMotors);
        // panel.add(Box.createRigidArea(new Dimension(0, 6)));
        // panel.add(lblDealership);
        // panel.add(Box.createRigidArea(new Dimension(0, 40)));

        // Ligne décorative dorée
        JPanel ligne = new JPanel();
        ligne.setBackground(Theme.GOLD);
        ligne.setMaximumSize(new Dimension(60, 3));
        ligne.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(ligne);
        panel.add(Box.createRigidArea(new Dimension(0, 24)));

        // Slogan
        JLabel lblSlogan = new JLabel("<html>Gérez votre concession<br>depuis un seul espace.</html>");
        lblSlogan.setFont(Theme.FONT_BODY);
        lblSlogan.setForeground(Theme.TEXT_GRAY);
        lblSlogan.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(lblSlogan);

        panel.add(Box.createVerticalGlue());

        // Bas de page
        JLabel lblVersion = new JLabel("Console Admin v1.0 — BTS SIO SLAM");
        lblVersion.setFont(Theme.FONT_SMALL);
        lblVersion.setForeground(new Color(0x44, 0x44, 0x55));
        lblVersion.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(lblVersion);

        return panel;
    }

    /**
     * Carte droite : formulaire de connexion.
     * Fond DARK_SURFACE + coins arrondis + bordure dorée subtile.
     */
    private JPanel creerCarte() {
        JPanel carte = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                // Fond de la carte
                g2.setColor(Theme.DARK_SURFACE);
                g2.fill(new RoundRectangle2D.Double(
                    0, 0, getWidth(), getHeight(), 0, 0));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        carte.setOpaque(false);
        carte.setLayout(new BoxLayout(carte, BoxLayout.Y_AXIS));
        carte.setBorder(BorderFactory.createEmptyBorder(60, 50, 60, 50));

        // ── Titre du formulaire ───────────────────────────────
        JLabel lblTitre = new JLabel("Connexion");
        lblTitre.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitre.setForeground(Theme.TEXT_WHITE);
        lblTitre.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblSous = new JLabel("Accès réservé aux administrateurs");
        lblSous.setFont(Theme.FONT_SMALL);
        lblSous.setForeground(Theme.TEXT_GRAY);
        lblSous.setAlignmentX(Component.LEFT_ALIGNMENT);

        carte.add(Box.createVerticalGlue());
        carte.add(lblTitre);
        carte.add(Box.createRigidArea(new Dimension(0, 6)));
        carte.add(lblSous);
        carte.add(Box.createRigidArea(new Dimension(0, 36)));

        // ── Séparateur doré ───────────────────────────────────
        JPanel sepDoré = new JPanel();
        sepDoré.setBackground(Theme.GOLD);
        sepDoré.setMaximumSize(new Dimension(40, 2));
        sepDoré.setAlignmentX(Component.LEFT_ALIGNMENT);
        carte.add(sepDoré);
        carte.add(Box.createRigidArea(new Dimension(0, 36)));

        // ── Champ Email ───────────────────────────────────────
        carte.add(creerLabel("Adresse email"));
        carte.add(Box.createRigidArea(new Dimension(0, 6)));
        champEmail = new JTextField();
        champEmail.setAlignmentX(Component.LEFT_ALIGNMENT);
        Theme.styleInput(champEmail);
        carte.add(champEmail);
        carte.add(Box.createRigidArea(new Dimension(0, 18)));

        // ── Champ Mot de passe ────────────────────────────────
        carte.add(creerLabel("Mot de passe"));
        carte.add(Box.createRigidArea(new Dimension(0, 6)));
        champMotDePasse = new JPasswordField();
        champMotDePasse.setAlignmentX(Component.LEFT_ALIGNMENT);
        Theme.stylePasswordField(champMotDePasse);
        carte.add(champMotDePasse);
        carte.add(Box.createRigidArea(new Dimension(0, 32)));

        // ── Bouton Connexion ──────────────────────────────────
        btnConnexion = new JButton("SE CONNECTER");
        btnConnexion.setAlignmentX(Component.LEFT_ALIGNMENT);
        Theme.styleButtonPrimary(btnConnexion);
        carte.add(btnConnexion);
        carte.add(Box.createRigidArea(new Dimension(0, 16)));

        // ── Message d'erreur (caché par défaut) ───────────────
        lblErreur = new JLabel("", SwingConstants.LEFT);
        lblErreur.setFont(Theme.FONT_SMALL);
        lblErreur.setForeground(Theme.DANGER);
        lblErreur.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblErreur.setVisible(false);
        carte.add(lblErreur);

        carte.add(Box.createVerticalGlue());

        return carte;
    }

    /**
     * Crée un petit label gris pour les champs du formulaire.
     * @param texte Le texte du label (ex: "Adresse email")
     */
    private JLabel creerLabel(String texte) {
        JLabel lbl = new JLabel(texte);
        lbl.setFont(Theme.FONT_SMALL);
        lbl.setForeground(Theme.TEXT_GRAY);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }

    // ─────────────────────────────────────────────────────────
    //  GETTERS – utilisés par LoginController
    // ─────────────────────────────────────────────────────────

    /** Retourne le bouton connexion pour y attacher un ActionListener */
    public JButton getBtnConnexion() { return btnConnexion; }

    /** Retourne le texte saisi dans le champ email */
    public String getEmail() { return champEmail.getText().trim(); }

    /** Retourne le mot de passe saisi (converti en String) */
    public String getMotDePasse() { return new String(champMotDePasse.getPassword()); }

    /** Retourne le champ email (pour écouter la touche Entrée) */
    public JTextField getChampEmail() { return champEmail; }

    /** Retourne le champ mot de passe (pour écouter la touche Entrée) */
    public JPasswordField getChampMotDePasse() { return champMotDePasse; }

    // ─────────────────────────────────────────────────────────
    //  MÉTHODES APPELÉES PAR LoginController
    // ─────────────────────────────────────────────────────────

    /**
     * Affiche un message d'erreur rouge sous le bouton.
     * Appelée par LoginController si l'authentification échoue.
     * @param message Le message à afficher (ex: "Email ou mot de passe incorrect")
     */
    public void afficherErreur(String message) {
        lblErreur.setText("✕  " + message);
        lblErreur.setVisible(true);
        // Secouer légèrement la fenêtre pour indiquer l'erreur
        secouerFenetre();
    }

    /** Cache le message d'erreur (appelé quand on retape) */
    public void cacherErreur() {
        lblErreur.setVisible(false);
    }

    /**
     * Petit effet visuel : secoue la fenêtre horizontalement
     * quand le mot de passe est incorrect.
     */
    private void secouerFenetre() {
        Point pos = getLocation();
        // Timer qui déplace la fenêtre rapidement de gauche à droite
        Timer timer = new Timer(30, null);
        final int[] compteur = {0};
        final int[] direction = {1};
        timer.addActionListener(e -> {
            setLocation(pos.x + (direction[0] * 6), pos.y);
            direction[0] *= -1;
            compteur[0]++;
            if (compteur[0] >= 8) {
                setLocation(pos); // Remet en place
                timer.stop();
            }
        });
        timer.start();
    }
}
