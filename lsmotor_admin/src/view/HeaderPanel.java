package view;

import javax.swing.*;
import java.awt.*;

/**
 * ============================================================
 *  VUE : HeaderPanel – Barre supérieure fixe
 * ============================================================
 *  Présent sur TOUTES les pages après connexion.
 *  Ne change jamais, reste toujours en haut (BorderLayout.NORTH).
 *
 *  STRUCTURE VISUELLE :
 *
 *    ┌────────────────────────────────────────────────────────┐
 *    │  [LOGO]  LS MOTORS  │  Console Administration         │
 *    │                              [Prénom NOM]  [●avatar]  │
 *    │                              Admin                     │
 *    └────────────────────────────────────────────────────────┘
 *         ↑ Gauche                       ↑ Droite
 *
 *  Séparé du reste par une ligne dorée en bas (2px).
 *
 *  UTILISATION dans MainView :
 *    HeaderPanel header = new HeaderPanel("Dave", "ISRAEL");
 *    frame.add(header, BorderLayout.NORTH);
 *
 *  MISE À JOUR du nom (si besoin) :
 *    header.setNomUtilisateur("Noah", "MILLOT");
 * ============================================================
 */
public class HeaderPanel extends JPanel {

    // ── Composants mis à jour dynamiquement ───────────────────
    private JLabel lblNom;    // Nom affiché à droite
    private JPanel avatar;    // Rond avec initiale

    // Initiale stockée pour le repaint de l'avatar
    private char initiale = 'A';

    /**
     * @param prenom Prénom de l'utilisateur connecté (ex: "Admin1")
     * @param nom    Nom de l'utilisateur connecté    (ex: "admin")
     */
    public HeaderPanel(String prenom, String nom) {
        configurerPanel();
        construireUI(prenom, nom);
    }

    // ─────────────────────────────────────────────────────────
    //  CONFIGURATION DU PANEL
    // ─────────────────────────────────────────────────────────

    private void configurerPanel() {
        setPreferredSize(new Dimension(0, Theme.HEADER_HEIGHT));
        setBackground(Theme.DARK_BG);
        setLayout(new BorderLayout());
        // Ligne dorée en bas du header – séparateur visuel
        setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Theme.GOLD));
    }

    // ─────────────────────────────────────────────────────────
    //  CONSTRUCTION DE L'INTERFACE
    // ─────────────────────────────────────────────────────────

    private void construireUI(String prenom, String nom) {
        add(creerPartieGauche(), BorderLayout.WEST);
        add(creerPartieDroite(prenom, nom), BorderLayout.EAST);
    }

    /**
     * Partie gauche : logo LS MOTORS + libellé "Console Administration".
     */
    private JPanel creerPartieGauche() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel.setBackground(Theme.DARK_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 24, 0, 0));

        // ── Option 1 : Logo image ─────────────────────────────
        // ⚠️ POUR UTILISER LE LOGO IMAGE :
        // Remplace le bloc "Option 2" ci-dessous par :
        //
         ImageIcon icon = new ImageIcon(
                 getClass().getResource("/resources/logo.png")
         );
         Image scaled = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
         JLabel lblIcon = new JLabel(new ImageIcon(scaled));
         lblIcon.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 14));
         panel.add(lblIcon);
        // ─────────────────────────────────────────────────────

        // ── Option 2 : Logo texte (par défaut) ───────────────
        // Petit carré doré avec "LS" dedans
        // JPanel logoBox = new JPanel() {
         //   @Override
         //   protected void paintComponent(Graphics g) {
         //       super.paintComponent(g);
         //       Graphics2D g2 = (Graphics2D) g.create();
         //       g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
         //                           RenderingHints.VALUE_ANTIALIAS_ON);
                // Fond or avec coins légèrement arrondis
         //       g2.setColor(Theme.GOLD);
         //       g2.fillRoundRect(0, 0, getWidth(), getHeight(), 6, 6);
                // Texte "LS" en noir au centre
         //       g2.setColor(Theme.DARK_BG);
         //       g2.setFont(new Font("Segoe UI", Font.BOLD, 14));
         //       FontMetrics fm = g2.getFontMetrics();
         //       String txt = "LS";
         //       int x = (getWidth()  - fm.stringWidth(txt)) / 2;
         //       int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
         //       g2.drawString(txt, x, y);
         //       g2.dispose();
         //   }
        //};
        //logoBox.setOpaque(false);
        //logoBox.setPreferredSize(new Dimension(36, 36));

        // Séparateur invisible entre logo et texte
        JPanel espacement = new JPanel();
        espacement.setBackground(Theme.DARK_BG);
        espacement.setPreferredSize(new Dimension(14, 1));

        // "MOTORS" en blanc + taille réduite
        //JLabel lblMotors = new JLabel("MOTORS");
        //lblMotors.setFont(new Font("Segoe UI", Font.BOLD, 16));
        //lblMotors.setForeground(Theme.TEXT_WHITE);

        // Séparateur vertical gris
        JLabel lblSep = new JLabel("  |  ");
        lblSep.setFont(Theme.FONT_BODY);
        lblSep.setForeground(new Color(0x44, 0x44, 0x55));

        // "Console Administration" en gris
        JLabel lblConsole = new JLabel("Console Administration");
        lblConsole.setFont(Theme.FONT_SMALL);
        lblConsole.setForeground(Theme.TEXT_GRAY);

        panel.add(lblIcon);
        panel.add(espacement);
        //panel.add(lblMotors);
        panel.add(lblSep);
        panel.add(lblConsole);

        return panel;
    }

    /**
     * Partie droite : nom de l'utilisateur connecté + avatar rond.
     * @param prenom Prénom affiché
     * @param nom    Nom affiché
     */
    private JPanel creerPartieDroite(String prenom, String nom) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 16, 0));
        panel.setBackground(Theme.DARK_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 24));

        // ── Bloc nom + rôle ───────────────────────────────────
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Theme.DARK_BG);

        // Nom complet (aligné à droite)
        lblNom = new JLabel(prenom + " " + nom);
        lblNom.setFont(Theme.FONT_BODY);
        lblNom.setForeground(Theme.TEXT_WHITE);
        lblNom.setAlignmentX(Component.RIGHT_ALIGNMENT);

        // Badge "Admin" en or
        JLabel lblRole = new JLabel("Administrateur");
        lblRole.setFont(Theme.FONT_SMALL);
        lblRole.setForeground(Theme.GOLD);
        lblRole.setAlignmentX(Component.RIGHT_ALIGNMENT);

        infoPanel.add(lblNom);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 2)));
        infoPanel.add(lblRole);

        // ── Avatar rond avec initiale ─────────────────────────
        // Prend la première lettre du prénom
        if (prenom != null && !prenom.isEmpty()) {
            initiale = Character.toUpperCase(prenom.charAt(0));
        }

        avatar = creerAvatar(initiale);

        panel.add(infoPanel);
        panel.add(avatar);

        return panel;
    }

    /**
     * Crée un rond doré avec l'initiale de l'utilisateur.
     * Dessiné à la main avec Graphics2D (pas d'image nécessaire).
     * @param lettre L'initiale à afficher (ex: 'A' pour Admin)
     */
    private JPanel creerAvatar(char lettre) {
        JPanel rond = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);

                // Cercle doré
                g2.setColor(Theme.GOLD);
                g2.fillOval(0, 0, getWidth(), getHeight());

                // Initiale en noir centré
                g2.setColor(Theme.DARK_BG);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 15));
                FontMetrics fm = g2.getFontMetrics();
                String txt = String.valueOf(lettre);
                int x = (getWidth()  - fm.stringWidth(txt)) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(txt, x, y);

                g2.dispose();
            }
        };
        rond.setOpaque(false);
        rond.setPreferredSize(new Dimension(36, 36));
        return rond;
    }

}