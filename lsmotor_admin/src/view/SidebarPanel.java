package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================
 *  VUE : SidebarPanel – Menu de navigation latéral
 * ============================================================
 *  Affiché à gauche dans MainView, toujours visible.
 *  Quand on clique sur un item → notifie MainView via
 *  l'interface NavigationListener pour changer le panneau.
 *
 *  STRUCTURE VISUELLE :
 *
 *    ┌──────────────────────┐
 *    │                      │
 *    │  ▣  Tableau de bord  │ ← item actif (fond légèrement
 *    │                      │   éclairé + barre dorée gauche)
 *    │  ◉  Utilisateurs     │
 *    │  ◈  Catégories       │
 *    │  ◇  Marques          │
 *    │  ◆  Véhicules        │
 *    │  ▣  Ventes           │
 *    │  ⊙  Configuration    │
 *    │                      │
 *    │  ──────────────────  │
 *    │  ⊗  Déconnexion      │ ← rouge, tout en bas
 *    └──────────────────────┘
 *
 *  UTILISATION dans MainView :
 *    SidebarPanel sidebar = new SidebarPanel(index -> afficherPanel(index));
 *
 *  CHANGER L'ITEM ACTIF depuis MainView :
 *    sidebar.setItemActif(SidebarPanel.IDX_VEHICULES);
 * ============================================================
 */
public class SidebarPanel extends JPanel {

    // ─────────────────────────────────────────────────────────
    //  INDEX DES PANNEAUX
    //  Ces constantes sont partagées avec MainView pour savoir
    //  quel panneau afficher dans le CardLayout.
    // ─────────────────────────────────────────────────────────
    public static final int IDX_DASHBOARD    = 0;
    public static final int IDX_UTILISATEURS = 1;
    public static final int IDX_CATEGORIES   = 2;
    public static final int IDX_MARQUES      = 3;
    public static final int IDX_VEHICULES    = 4;
    public static final int IDX_VENTES       = 5;
    public static final int IDX_CONFIG       = 6;

    // ─────────────────────────────────────────────────────────
    //  INTERFACE FONCTIONNELLE
    //  Permet à MainView de savoir quel onglet a été cliqué.
    //  Utilisé comme lambda : index -> afficherPanel(index)
    // ─────────────────────────────────────────────────────────
    public interface NavigationListener {
        void onNavigate(int panelIndex);
    }

    // ── Attributs internes ────────────────────────────────────
    private final List<NavItem>      items    = new ArrayList<>();
    private       int                indexActif = IDX_DASHBOARD;
    private final NavigationListener listener;

    // Callback spécial pour la déconnexion (géré à part)
    private Runnable onDeconnexion;

    /**
     * @param listener Callback appelé quand l'utilisateur clique sur un item.
     *                 Exemple : index -> mainView.afficherPanel(index)
     */
    public SidebarPanel(NavigationListener listener) {
        this.listener = listener;
        configurerPanel();
        construireItems();
    }

    // ─────────────────────────────────────────────────────────
    //  CONFIGURATION DU PANEL
    // ─────────────────────────────────────────────────────────

    private void configurerPanel() {
        setPreferredSize(new Dimension(Theme.SIDEBAR_WIDTH, 0));
        setBackground(Theme.DARK_SURFACE);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        // Bordure droite subtile pour séparer du contenu
        setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Theme.BORDER));
    }

    // ─────────────────────────────────────────────────────────
    //  CONSTRUCTION DES ITEMS
    // ─────────────────────────────────────────────────────────

    private void construireItems() {
        add(Box.createRigidArea(new Dimension(0, 24)));

        // ── Items de navigation principal ─────────────────────
        // Format : ajouterItem(icône, libellé, index)
        ajouterItem("⊞", "Tableau de bord",    IDX_DASHBOARD);
        ajouterItem("◉", "Utilisateurs",        IDX_UTILISATEURS);
        ajouterItem("◈", "Catégories",          IDX_CATEGORIES);
        ajouterItem("◇", "Marques",             IDX_MARQUES);
        ajouterItem("◆", "Véhicules",           IDX_VEHICULES);
        ajouterItem("▣", "Historique ventes",   IDX_VENTES);
        ajouterItem("⊙", "Configuration",       IDX_CONFIG);

        // ── Espace flexible (pousse la déconnexion vers le bas) ─
        add(Box.createVerticalGlue());

        // ── Séparateur ────────────────────────────────────────
        JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
        sep.setForeground(Theme.BORDER);
        sep.setBackground(Theme.BORDER);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        add(sep);
        add(Box.createRigidArea(new Dimension(0, 8)));

        // ── Bouton déconnexion ────────────────────────────────
        // Index -1 = c'est la déconnexion, pas un panneau
        JPanel itemDeconnexion = creerItemPanel("⊗", "Déconnexion", -1);
        add(itemDeconnexion);
        add(Box.createRigidArea(new Dimension(0, 16)));

        // Activer le dashboard par défaut au démarrage
        setItemActif(IDX_DASHBOARD);
    }

    /**
     * Crée un item de navigation et l'ajoute à la sidebar.
     * @param icone   Icône unicode affichée à gauche
     * @param libelle Texte affiché à droite de l'icône
     * @param index   Index du panneau cible (IDX_xxx)
     */
    private void ajouterItem(String icone, String libelle, int index) {
        JPanel itemPanel = creerItemPanel(icone, libelle, index);
        items.add(new NavItem(itemPanel, index, icone, libelle));
        add(itemPanel);
    }

    /**
     * Construit visuellement un item de navigation.
     * Gère le style normal, hover, actif et déconnexion.
     * @param index -1 = bouton déconnexion (style rouge)
     */
    private JPanel creerItemPanel(String icone, String libelle, int index) {
        boolean estDeconnexion = (index == -1);

        // ── Panel principal de l'item ─────────────────────────
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Barre dorée verticale à gauche si item actif
                if (index == indexActif && !estDeconnexion) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setColor(Theme.GOLD);
                    // Barre de 3px, avec 6px de marge haut/bas
                    g2.fillRect(0, 6, 3, getHeight() - 12);
                    g2.dispose();
                }
            }
        };
        panel.setOpaque(true);
        panel.setBackground(Theme.DARK_SURFACE);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
        panel.setPreferredSize(new Dimension(Theme.SIDEBAR_WIDTH, 46));
        panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // Padding interne de l'item
        panel.setBorder(BorderFactory.createEmptyBorder(0, 18, 0, 12));

        // ── Contenu : icône + texte ───────────────────────────
        JPanel contenu = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        contenu.setOpaque(false);

        // Couleur initiale selon le type d'item
        Color couleurInitiale = estDeconnexion ? Theme.DANGER : Theme.TEXT_GRAY;

        JLabel lblIcone = new JLabel(icone);
        lblIcone.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblIcone.setForeground(couleurInitiale);

        JLabel lblTexte = new JLabel(libelle);
        lblTexte.setFont(Theme.FONT_NAV);
        lblTexte.setForeground(couleurInitiale);

        contenu.add(lblIcone);
        contenu.add(lblTexte);
        panel.add(contenu, BorderLayout.CENTER);

        // ── Événements souris ─────────────────────────────────
        panel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                // Hover : fond légèrement éclairé + texte plus visible
                if (index != indexActif) {
                    panel.setBackground(new Color(0x2A, 0x2A, 0x36));
                    if (estDeconnexion) {
                        lblTexte.setForeground(Theme.DANGER);
                        lblIcone.setForeground(Theme.DANGER);
                    } else {
                        lblTexte.setForeground(Theme.TEXT_WHITE);
                        lblIcone.setForeground(Theme.TEXT_WHITE);
                    }
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Retour au style normal si pas actif
                if (index != indexActif) {
                    panel.setBackground(Theme.DARK_SURFACE);
                    lblTexte.setForeground(couleurInitiale);
                    lblIcone.setForeground(couleurInitiale);
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (estDeconnexion) {
                    // Déconnexion : demande confirmation puis appelle le callback
                    int choix = JOptionPane.showConfirmDialog(
                        panel,
                        "Voulez-vous vous déconnecter ?",
                        "Déconnexion",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                    );
                    if (choix == JOptionPane.YES_OPTION && onDeconnexion != null) {
                        onDeconnexion.run();
                    }
                } else {
                    // Navigation normale
                    setItemActif(index);
                    if (listener != null) {
                        listener.onNavigate(index);
                    }
                }
            }
        });

        return panel;
    }

    // ─────────────────────────────────────────────────────────
    //  MÉTHODE DE MISE À JOUR VISUELLE
    // ─────────────────────────────────────────────────────────

    /**
     * Met à jour visuellement l'item actif.
     * → Fond éclairé + barre dorée gauche + texte doré sur l'item actif.
     * → Retour au style normal sur les autres items.
     *
     * Appelée par :
     *   - mouseClicked (clic utilisateur)
     *   - MainView.afficherPanel() (navigation programmatique)
     *
     * @param index Index de l'item à activer (IDX_xxx)
     */
    public void setItemActif(int index) {
        indexActif = index;

        for (NavItem item : items) {
            boolean estActif = (item.index == index);

            // Fond de l'item
            Color fondItem = estActif
                ? new Color(0x1E, 0x1E, 0x2A)
                : Theme.DARK_SURFACE;
            item.panel.setBackground(fondItem);

            // Couleur texte + icône
            Color couleurTexte = estActif ? Theme.GOLD : Theme.TEXT_GRAY;

            // Parcourir les composants enfants pour changer les couleurs
            for (Component comp : item.panel.getComponents()) {
                if (comp instanceof JPanel) {
                    ((JPanel) comp).setBackground(fondItem);
                    for (Component child : ((JPanel) comp).getComponents()) {
                        if (child instanceof JLabel) {
                            ((JLabel) child).setForeground(couleurTexte);
                        }
                    }
                }
            }

            // Repaint pour redessiner la barre dorée gauche
            item.panel.repaint();
        }
    }

    // ─────────────────────────────────────────────────────────
    //  SETTER CALLBACK DÉCONNEXION
    // ─────────────────────────────────────────────────────────

    /**
     * Définit l'action à exécuter quand l'utilisateur clique
     * sur "Déconnexion" et confirme.
     *
     * UTILISATION dans MainView :
     *   sidebar.setOnDeconnexion(() -> {
     *       db.seDeconnecter();
     *       dispose();
     *       new LoginView().setVisible(true);
     *   });
     *
     * @param action Le Runnable à exécuter lors de la déconnexion
     */
    public void setOnDeconnexion(Runnable action) {
        this.onDeconnexion = action;
    }

    // ─────────────────────────────────────────────────────────
    //  CLASSE INTERNE – Stocke les infos de chaque item
    // ─────────────────────────────────────────────────────────

    /**
     * Objet interne qui regroupe toutes les infos d'un item
     * de navigation pour pouvoir le retrouver et le modifier.
     */
    private static class NavItem {
        JPanel panel;   // Le composant visuel de l'item
        int    index;   // L'index du panneau associé (IDX_xxx)
        String icone;   // L'icône unicode
        String libelle; // Le texte affiché

        NavItem(JPanel panel, int index, String icone, String libelle) {
            this.panel   = panel;
            this.index   = index;
            this.icone   = icone;
            this.libelle = libelle;
        }
    }
}