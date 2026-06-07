Parfait, j'ai les vraies tables SQL. Je vais adapter tous les POJOs à ta vraie structure, puis te les donner un par un.

---

## Structure réelle de la BDD

| Table | Champs clés |
|-------|-------------|
| `utilisateur` | ID, Nom, Prenom, Email, **Passwrd**, Role, DiscordPseudo, DateInscription |
| `categorie` | ID, Libelle |
| `marque` | ID, Nom |
| `vehicule` | ID, NomModele, ID_Marque, ID_Categorie, PrixCatalogue, Description, Image, Actif |
| `vente` | ID, ID_Employe, ID_Vehicule, NomClient, DateVente, PrixVente |
| `config` | ID, MargePourcent, DateMaj |

---

## 📄 Fichier 1/6 — `model/Utilisateur.java`

```java
package model;

/**
 * ============================================================
 *  POJO : Utilisateur
 * ============================================================
 *  Représente une ligne de la table `utilisateur`.
 *
 *  CORRESPONDANCE SQL :
 *    ID              → int    id
 *    Nom             → String nom
 *    Prenom          → String prenom
 *    Email           → String email
 *    Passwrd         → String passwrd      ← nom exact de la colonne SQL
 *    Role            → String role         ← "admin" | "employe" | "joueur"
 *    DiscordPseudo   → String discordPseudo
 *    DateInscription → String dateInscription (format String pour affichage)
 * ============================================================
 */
public class Utilisateur {

    private int    id;
    private String nom;
    private String prenom;
    private String email;
    private String passwrd;        // Colonne SQL : Passwrd
    private String role;           // "admin" | "employe" | "joueur"
    private String discordPseudo;
    private String dateInscription;

    // ── Constructeur vide ──────────────────────────────────────
    public Utilisateur() {}

    // ── Constructeur complet ───────────────────────────────────
    public Utilisateur(int id, String nom, String prenom, String email,
                       String passwrd, String role, String discordPseudo,
                       String dateInscription) {
        this.id              = id;
        this.nom             = nom;
        this.prenom          = prenom;
        this.email           = email;
        this.passwrd         = passwrd;
        this.role            = role;
        this.discordPseudo   = discordPseudo;
        this.dateInscription = dateInscription;
    }

    // ── Getters ────────────────────────────────────────────────
    public int    getId()              { return id;              }
    public String getNom()            { return nom;             }
    public String getPrenom()         { return prenom;          }
    public String getEmail()          { return email;           }
    public String getPasswrd()        { return passwrd;         }
    public String getRole()           { return role;            }
    public String getDiscordPseudo()  { return discordPseudo;   }
    public String getDateInscription(){ return dateInscription; }

    // ── Setters ────────────────────────────────────────────────
    public void setId(int id)                        { this.id              = id;              }
    public void setNom(String nom)                   { this.nom             = nom;             }
    public void setPrenom(String prenom)             { this.prenom          = prenom;          }
    public void setEmail(String email)               { this.email           = email;           }
    public void setPasswrd(String passwrd)           { this.passwrd         = passwrd;         }
    public void setRole(String role)                 { this.role            = role;            }
    public void setDiscordPseudo(String discord)     { this.discordPseudo   = discord;         }
    public void setDateInscription(String date)      { this.dateInscription = date;            }

    // ── toString (debug) ───────────────────────────────────────
    @Override
    public String toString() {
        return "Utilisateur{id=" + id + ", nom=" + nom + ", prenom=" + prenom
             + ", email=" + email + ", role=" + role + "}";
    }
}
```

---

## 📄 Fichier 2/6 — `model/Vehicule.java`

```java
package model;

/**
 * ============================================================
 *  POJO : Vehicule
 * ============================================================
 *  Représente une ligne de la table `vehicule`.
 *
 *  CORRESPONDANCE SQL :
 *    ID            → int     id
 *    NomModele     → String  nomModele
 *    ID_Marque     → int     idMarque
 *    ID_Categorie  → int     idCategorie
 *    PrixCatalogue → double  prixCatalogue
 *    Description   → String  description
 *    Image         → String  image          ← chemin/URL image
 *    Actif         → boolean actif          ← 1 = actif, 0 = masqué
 *
 *  CHAMPS EXTRA (jointures SQL, pas de colonne directe) :
 *    nomMarque     → récupéré via JOIN sur marque
 *    nomCategorie  → récupéré via JOIN sur categorie
 * ============================================================
 */
public class Vehicule {

    private int     id;
    private String  nomModele;
    private int     idMarque;
    private String  nomMarque;       // JOIN marque.Nom
    private int     idCategorie;
    private String  nomCategorie;    // JOIN categorie.Libelle
    private double  prixCatalogue;
    private String  description;
    private String  image;
    private boolean actif;

    // ── Constructeur vide ──────────────────────────────────────
    public Vehicule() {}

    // ── Constructeur complet ───────────────────────────────────
    public Vehicule(int id, String nomModele, int idMarque, String nomMarque,
                    int idCategorie, String nomCategorie, double prixCatalogue,
                    String description, String image, boolean actif) {
        this.id            = id;
        this.nomModele     = nomModele;
        this.idMarque      = idMarque;
        this.nomMarque     = nomMarque;
        this.idCategorie   = idCategorie;
        this.nomCategorie  = nomCategorie;
        this.prixCatalogue = prixCatalogue;
        this.description   = description;
        this.image         = image;
        this.actif         = actif;
    }

    // ── Getters ────────────────────────────────────────────────
    public int     getId()            { return id;            }
    public String  getNomModele()     { return nomModele;     }
    public int     getIdMarque()      { return idMarque;      }
    public String  getNomMarque()     { return nomMarque;     }
    public int     getIdCategorie()   { return idCategorie;   }
    public String  getNomCategorie()  { return nomCategorie;  }
    public double  getPrixCatalogue() { return prixCatalogue; }
    public String  getDescription()   { return description;   }
    public String  getImage()         { return image;         }
    public boolean isActif()          { return actif;         }

    // ── Setters ────────────────────────────────────────────────
    public void setId(int id)                      { this.id            = id;            }
    public void setNomModele(String nomModele)      { this.nomModele     = nomModele;     }
    public void setIdMarque(int idMarque)           { this.idMarque      = idMarque;      }
    public void setNomMarque(String nomMarque)      { this.nomMarque     = nomMarque;     }
    public void setIdCategorie(int idCategorie)     { this.idCategorie   = idCategorie;   }
    public void setNomCategorie(String nomCat)      { this.nomCategorie  = nomCat;        }
    public void setPrixCatalogue(double prix)       { this.prixCatalogue = prix;          }
    public void setDescription(String description)  { this.description   = description;   }
    public void setImage(String image)              { this.image         = image;         }
    public void setActif(boolean actif)             { this.actif         = actif;         }

    @Override
    public String toString() {
        return "Vehicule{id=" + id + ", nomModele=" + nomModele
             + ", marque=" + nomMarque + ", prix=" + prixCatalogue + "}";
    }
}
```

---

## 📄 Fichier 3/6 — `model/Marque.java`

```java
package model;

/**
 * ============================================================
 *  POJO : Marque
 * ============================================================
 *  Représente une ligne de la table `marque`.
 *
 *  CORRESPONDANCE SQL :
 *    ID  → int    id
 *    Nom → String nom
 *
 *  ⚠️ toString() retourne this.nom
 *     → C'est OBLIGATOIRE pour que les JComboBox affichent le nom.
 * ============================================================
 */
public class Marque {

    private int    id;
    private String nom;

    public Marque() {}

    public Marque(int id, String nom) {
        this.id  = id;
        this.nom = nom;
    }

    public int    getId()  { return id;  }
    public String getNom() { return nom; }

    public void setId(int id)       { this.id  = id;  }
    public void setNom(String nom)  { this.nom = nom; }

    /**
     * ⚠️ IMPORTANT : retourne le nom pour l'affichage dans les JComboBox.
     * Swing appelle toString() pour afficher les items d'un JComboBox.
     */
    @Override
    public String toString() { return this.nom; }
}
```

---

## 📄 Fichier 4/6 — `model/Categorie.java`

```java
package model;

/**
 * ============================================================
 *  POJO : Categorie
 * ============================================================
 *  Représente une ligne de la table `categorie`.
 *
 *  CORRESPONDANCE SQL :
 *    ID      → int    id
 *    Libelle → String libelle
 *
 *  ⚠️ toString() retourne this.libelle (même raison que Marque).
 * ============================================================
 */
public class Categorie {

    private int    id;
    private String libelle;

    public Categorie() {}

    public Categorie(int id, String libelle) {
        this.id      = id;
        this.libelle = libelle;
    }

    public int    getId()      { return id;      }
    public String getLibelle() { return libelle; }

    public void setId(int id)            { this.id      = id;      }
    public void setLibelle(String lib)   { this.libelle = lib;     }

    /** ⚠️ Retourne le libellé pour l'affichage dans les JComboBox. */
    @Override
    public String toString() { return this.libelle; }
}
```

---

## 📄 Fichier 5/6 — `model/Vente.java`

```java
package model;

/**
 * ============================================================
 *  POJO : Vente
 * ============================================================
 *  Représente une ligne de la table `vente`.
 *
 *  CORRESPONDANCE SQL :
 *    ID          → int    id
 *    ID_Employe  → int    idEmploye
 *    ID_Vehicule → int    idVehicule
 *    NomClient   → String nomClient
 *    DateVente   → String dateVente   (String pour l'affichage)
 *    PrixVente   → double prixVente
 *
 *  CHAMPS EXTRA (jointures) :
 *    nomEmploye  → JOIN utilisateur.Nom + Prenom
 *    nomVehicule → JOIN vehicule.NomModele
 *
 *  NOTE : La table vente n'a PAS de colonne "semaine".
 *         Le regroupement par semaine se fait en SQL avec :
 *         DATE_FORMAT(DateVente, '%x%v') AS semaine
 *         (format ISO : YYYYWW)
 * ============================================================
 */
public class Vente {

    private int    id;
    private int    idEmploye;
    private String nomEmploye;   // JOIN : utilisateur.Nom + Prenom
    private int    idVehicule;
    private String nomVehicule;  // JOIN : vehicule.NomModele
    private String nomClient;
    private String dateVente;
    private double prixVente;

    public Vente() {}

    public Vente(int id, int idEmploye, String nomEmploye, int idVehicule,
                 String nomVehicule, String nomClient, String dateVente, double prixVente) {
        this.id          = id;
        this.idEmploye   = idEmploye;
        this.nomEmploye  = nomEmploye;
        this.idVehicule  = idVehicule;
        this.nomVehicule = nomVehicule;
        this.nomClient   = nomClient;
        this.dateVente   = dateVente;
        this.prixVente   = prixVente;
    }

    // ── Getters ────────────────────────────────────────────────
    public int    getId()          { return id;          }
    public int    getIdEmploye()   { return idEmploye;   }
    public String getNomEmploye()  { return nomEmploye;  }
    public int    getIdVehicule()  { return idVehicule;  }
    public String getNomVehicule() { return nomVehicule; }
    public String getNomClient()   { return nomClient;   }
    public String getDateVente()   { return dateVente;   }
    public double getPrixVente()   { return prixVente;   }

    // ── Setters ────────────────────────────────────────────────
    public void setId(int id)                    { this.id          = id;          }
    public void setIdEmploye(int idEmploye)      { this.idEmploye   = idEmploye;   }
    public void setNomEmploye(String nom)        { this.nomEmploye  = nom;         }
    public void setIdVehicule(int idVehicule)    { this.idVehicule  = idVehicule;  }
    public void setNomVehicule(String nom)       { this.nomVehicule = nom;         }
    public void setNomClient(String nomClient)   { this.nomClient   = nomClient;   }
    public void setDateVente(String dateVente)   { this.dateVente   = dateVente;   }
    public void setPrixVente(double prixVente)   { this.prixVente   = prixVente;   }

    @Override
    public String toString() {
        return "Vente{id=" + id + ", client=" + nomClient
             + ", vehicule=" + nomVehicule + ", prix=" + prixVente + "}";
    }
}
```

---

## 📄 Fichier 6/6 — `model/Config.java`

```java
package model;

/**
 * ============================================================
 *  POJO : Config
 * ============================================================
 *  Représente la ligne unique de la table `config`.
 *
 *  CORRESPONDANCE SQL :
 *    ID            → int    id          (toujours = 1)
 *    MargePourcent → double margePourcent
 *    DateMaj       → String dateMaj
 *
 *  UTILISATION dans VenteModel (ou ConfigModel) :
 *
 *    // Lire la marge
 *    SELECT MargePourcent FROM config WHERE ID = 1
 *
 *    // Modifier la marge
 *    UPDATE config SET MargePourcent = ? WHERE ID = 1
 *
 *  Note : Il n'y a qu'une seule ligne dans cette table (ID = 1).
 *         L'INSERT IGNORE dans script.sql l'a déjà créée.
 * ============================================================
 */
public class Config {

    private int    id;
    private double margePourcent;
    private String dateMaj;

    public Config() {}

    public Config(int id, double margePourcent, String dateMaj) {
        this.id            = id;
        this.margePourcent = margePourcent;
        this.dateMaj       = dateMaj;
    }

    public int    getId()             { return id;             }
    public double getMargePourcent()  { return margePourcent;  }
    public String getDateMaj()        { return dateMaj;        }

    public void setId(int id)                        { this.id            = id;            }
    public void setMargePourcent(double marge)       { this.margePourcent = marge;         }
    public void setDateMaj(String dateMaj)           { this.dateMaj       = dateMaj;       }

    @Override
    public String toString() {
        return "Config{marge=" + margePourcent + "%, dateMaj=" + dateMaj + "}";
    }
}
```

---

## Résumé des changements vs ta première version

- `Passwrd` (et non `motDePasse`) — c'est le nom exact de ta colonne SQL
- `vehicule` a des champs en plus : `Description`, `Image`, `Actif`
- `vente` n'a **pas** de colonne `semaine` — le regroupement se fait en SQL avec `DATE_FORMAT()`
- Ajout du POJO `Config.java` (manquait)
- `toString()` sur `Marque` et `Categorie` retourne le nom/libellé → **indispensable pour les JComboBox**
















DossierFichiersRacineMain.javamodel/BDD.java, Utilisateur.java, Vehicule.java, Marque.java, Categorie.java, Vente.java, Config.java, UtilisateurModel.java, VehiculeModel.java, MarqueModel.java, CategorieModel.java, VenteModel.javaview/Theme.java, LoginView.java, HeaderPanel.java, SidebarPanel.java, MainView.java, DashboardPanel.java, BaseCrudPanel.java, UtilisateurPanel.java, CategoriePanel.java, MarquePanel.java, VehiculePanel.java, VentePanel.java, ConfigPanel.javacontroller/LoginController.java, UtilisateurController.java, MarqueController.java, CategorieController.java, VehiculeController.java, VenteController.java, ConfigController.java
L'ordre recommandé pour coder :

Les POJOs (juste des attributs + getters/setters)
Les Models (les requêtes SQL)
Tester les Models avec des System.out.println
Les Controllers (brancher vue ↔ modèle)
Tester chaque onglet un par un







les fichiers Views sont terminés !
Voici le récapitulatif complet de ce qui a été donné :
#FichierRôle1Theme.javaCouleurs, polices, méthodes de style2LoginView.javaÉcran de connexion (2 colonnes)3HeaderPanel.javaBarre fixe du haut4SidebarPanel.javaNavigation latérale5MainView.javaFenêtre principale + CardLayout6DashboardPanel.javaTableau de bord avec cartes stats7BaseCrudPanel.javaBase réutilisable (tableau + recherche)8UtilisateurPanel.javaCRUD utilisateurs9CategoriePanel.javaCRUD catégories10MarquePanel.javaCRUD marques11VehiculePanel.javaCRUD véhicules (le plus complet)12VentePanel.javaHistorique ventes (lecture seule)13ConfigPanel.javaMarge globale





