# **LS MOTORS – Documentation Juridique**

## **Auteurs**
- **Dave ISRAEL**  
- **Youssef BOUMLIK**  
- **Noah MILLOT**  
- **Théo DORIVAL**  

**Formation :** BTS SIO – Option SLAM  
**Entreprise :** NYT (*Nyght*)

---

## **Sommaire**
1. [Introduction](#introduction)  
2. [Nature juridique du projet](#nature-juridique-du-projet)  
3. [Conformité RGPD](#conformité-rgpd)  
   - Principes appliqués  
   - Données collectées  
4. [Sécurité informatique et obligations légales](#sécurité-informatique-et-obligations-légales)  
   - Obligations de sécurité  
   - Politique de mots de passe  
   - Gestion des accès  
5. [Propriété intellectuelle](#propriété-intellectuelle)  
6. [Responsabilités et engagements](#responsabilités-et-engagements)  
7. [Mentions légales](#mentions-légales)  
8. [Conclusion](#conclusion)

---

# **Introduction**

Ce document présente le cadre juridique applicable au projet **LS Motors**, développé par l’entreprise *Nyght*.  
Bien que le projet évolue dans un environnement fictif de **roleplay (GTA V / FiveM)**, il respecte les principes fondamentaux du droit informatique français et européen.

---

# **Nature juridique du projet**

L’entreprise **NYT** est une société de développement web.  
Le client **LS Motors** est une concession automobile fictive opérant dans le serveur RP *District RP*.

Principes appliqués :

- Les comptes utilisateurs constituent des **données personnelles**.  
- Les mots de passe sont des **données sensibles**.  
- Toute donnée stockée doit être **sécurisée** et **protégée**.

---

# **Conformité RGPD**

## **Principes appliqués**

| Principe RGPD | Application dans LS Motors |
|---------------|----------------------------|
| **Minimisation** | Seules les données nécessaires sont collectées |
| **Finalité** | Gestion interne de la concession RP |
| **Sécurité** | Mots de passe hashés, rôles, sessions sécurisées |
| **Confidentialité** | Accès restreint selon le rôle |
| **Droit d'accès** | L’administrateur peut gérer les comptes |

## **Données collectées**

- Identifiant utilisateur  
- Mot de passe **hashé** (bcrypt / password_hash)  
- Rôle (admin / employé)  
- Données véhicules (marque, catégorie, prix)  
- Historique des ventes (optionnel)  

---

# **Sécurité informatique et obligations légales**

## **Obligations de sécurité**

Conformément à l’article 32 du RGPD :

- Hash des mots de passe  
- Requêtes préparées (PDO)  
- Validation des entrées utilisateur  
- Vérification de session et rôle  
- Blocage des accès directs aux pages sensibles  

## **Politique de mots de passe**

| Règle | Détail |
|-------|--------|
| **Stockage** | Hash obligatoire |
| **Algorithme** | bcrypt / password_hash() |

## **Gestion des accès**

| Rôle | Droits | Restrictions |
|------|--------|--------------|
| **Administrateur (client lourd)** | CRUD complet, gestion utilisateurs | Aucune |
| **Employé** | Catalogue, recherche, ventes, RDV | Pas d’accès admin |
| **Public** | Catalogue public, contact, RDV, achat | Pas de back-office |

---

# **Propriété intellectuelle**

## **Code source**
Le code source est la propriété intellectuelle de l’équipe NYT :  
**Youssef BOUMLIK, Noah MILLOT, Théo DORIVAL, Dave ISRAEL**

## **Identité visuelle**
Le logo et la charte graphique sont des créations originales.

## **Environnement GTA V / FiveM**
- GTA V appartient à Rockstar Games  
- FiveM est un mod non officiel  
- Le projet n’est **pas affilié** à Rockstar  
- Aucune exploitation commerciale  

---

# **Responsabilités et engagements**

## **Responsabilités du prestataire (NYT)**

- Livrer une application fonctionnelle et sécurisée  
- Protéger les données stockées  
- Documenter le projet  
- Ne pas divulguer les données  

## **Responsabilités du client (LS Motors)**

- Utiliser la plateforme dans le respect du RP  
- Gérer les accès administrateurs  
- Signaler tout bug ou faille  

---

# **Mentions légales**

| Information | Valeur |
|-------------|--------|
| **Nom du projet** | LS Motors |
| **Prestataire** | NYT (Nyght) |
| **Cadre** | BTS SIO SLAM – Épreuve E6 |
| **Année** | 2025–2026 |
| **Responsable technique** | Noah MILLOT |
| **Responsable gestion** | Dave ISRAEL |
| **Responsable qualité** | Théo DORIVAL |
| **Responsable analyse** | Youssef BOUMLIK |
| **Technologies** | PHP / MySQL /  CSS / JS – MVC |
| **Hébergement** | Environnement local / pédagogique |

---

# **Conclusion**

Le projet LS Motors respecte les principes essentiels du droit informatique, du RGPD et des bonnes pratiques de sécurité.  
L’équipe NYT s’engage à maintenir ces standards durant tout le cycle de développement et de livraison.

