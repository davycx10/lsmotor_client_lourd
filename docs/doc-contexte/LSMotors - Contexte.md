# **LS MOTORS – Contexte & Présentation du Projet**

## **Auteurs**
- **Dave ISRAEL**  
- **Youssef BOUMILK**  
- **Noah MILLOT**  
- **Théo DORIVAL**  

**Entreprise :** NYT (Nyght)  
**Formation :** BTS SIO – Option SLAM  

---

## **Sommaire**
1. [Introduction générale](#introduction-générale)  
2. [Comprendre l’environnement : GTA, FiveM et le RolePlay](#comprendre-lenvironnement--gta-fivem-et-le-roleplay)  
3. [Présentation du client](#présentation-du-client)  
4. [Fonctionnement de l’entreprise LS Motors](#fonctionnement-de-lentreprise-ls-motors)  
5. [Problèmes rencontrés avant le projet](#problèmes-rencontrés-avant-le-projet)  
6. [Besoins exprimés par le client](#besoins-exprimés-par-le-client)  
7. [Contexte technique et pédagogique](#contexte-technique-et-pédagogique)  
8. [Organisation interne – Entreprise NYT](#organisation-interne--entreprise-nyt)  
9. [Environnement d’utilisation de l’application](#environnement-dutilisation-de-lapplication)  
10. [Finalité du projet](#finalité-du-projet)

---

# **Introduction générale**

Le projet **LS Motors** est réalisé dans le cadre du **BTS SIO option SLAM**, dont l’objectif est de concevoir, développer et documenter une application informatique complète répondant à un besoin professionnel réel ou simulé.

Pour ce projet, les étudiants ont créé leur propre entreprise fictive : **NYT (Nyght)**, agissant comme prestataire de services numériques.

NYT a été mandatée pour développer une **solution digitale** destinée à améliorer la gestion d’une concession automobile fictive intégrée à un environnement immersif RP.

---

# **Comprendre l’environnement : GTA, FiveM et le RolePlay**

##  **GTA V : un monde virtuel moderne**
Jeu en monde ouvert simulant une grande ville : **Los Santos**.  
Le joueur peut y conduire, visiter, acheter, interagir…

##  **FiveM : des serveurs personnalisés**
FiveM permet de créer des serveurs indépendants avec leurs propres règles, entreprises et systèmes.

##  **Le RP (RolePlay) : jouer un rôle réaliste**
Les joueurs incarnent un personnage avec un métier et des responsabilités :

- Policier  
- Médecin  
- Mécanicien  
- Vendeur automobile (LS Motors)  
- etc.

Le RP transforme GTA en **simulation de vie**.

##  **Pourquoi une concession automobile ?**
Dans un serveur RP, une concession fonctionne comme une vraie entreprise :

- catalogue  
- employés  
- clients  
- prix et taxes  
- gestion interne  

--> D’où le besoin d’un **outil professionnel**, clair et structuré.

---

# **Présentation du client**

Le client est le **gérant de LS Motors**, concession automobile RP sur un serveur FiveM.

Ses objectifs :

- moderniser la gestion interne  
- améliorer la présentation des véhicules  
- professionnaliser les interactions avec les joueurs  

LS Motors dispose de :

- plusieurs employés  
- un catalogue important  
- des catégories et marques variées  
- des prix et taxes spécifiques  
- une organisation proche d’une vraie concession  

---

# **Fonctionnement de l’entreprise LS Motors**

Comme une concession réelle, LS Motors doit :

- gérer son catalogue  
- organiser les modèles par catégories  
- gérer les marques  
- tenir compte des prix et taxes  
- employer et former des vendeurs  
- présenter les véhicules clairement  
- assurer un suivi professionnel  

Sans outil adapté, ce travail était difficile.

---

# **Problèmes rencontrés avant le projet**

Avant l’application, LS Motors faisait face à :

 Aucune plateforme centralisée  
 Informations dispersées  
 Confusion catégories / marques  
 Pas de gestion des employés  
 Aucun site public  
 Présentation peu professionnelle  
 Perte de temps en scène RP  
 Erreurs fréquentes dans les prix  

Ces problèmes impactaient :

--> l’efficacité interne  
--> le professionnalisme  
--> la cohérence RP  

---

# **Besoins exprimés par le client**

Le gérant souhaite une plateforme :

✔ moderne  
✔ responsive  
✔ sécurisée  
✔ cohérente avec LS Motors  

Fonctionnalités demandées :

- CRUD véhicules  
- CRUD marques  
- CRUD catégories  
- Gestion utilisateurs (admin/employé)  
- Catalogue public  
- Consultation rapide en RP  
- Base de données fiable  
- Identité visuelle premium  

---

# **Contexte technique et pédagogique**

Le projet respecte les exigences du BTS SIO SLAM.

## 🔹 **Conception**
- Merise (MCD, MLD)  
- Use Cases  
- Gantt  
- Xmind  
- Wireframes  
- Maquettes Figma  

## 🔹 **Développement**
- PHP natif  
- Architecture MVC  
- MySQL (PDO sécurisé)  
- HTML/CSS/JS  
- CRUD complet  
- Sécurité par rôles  

## 🔹 **Documentation**
- Cahier des charges  
- Documentation client  
- Documentation prestataire  
- Contexte  
- Contraintes  

## 🔹 **Organisation**
- Répartition des tâches  
- Suivi Trello  
- Méthode agile simplifiée  

---

# **Organisation interne – Entreprise NYT**

NYT regroupe trois membres aux rôles définis :

## **Noah**
- Chef de projet  
- Architecture MVC  
- Base de données  
- Développement  
- Designer  
- Cahier des charges / Contexte  

## **Dave**
- Gestion de projet    
- Base de données  
- Développement
- GitHub  

## **Youssef**
- Documentation client / prestataire  
- Analyste fonctionnel  
- MCD / MLD  
- Merise  
- Wireframe + Figma  
- Xmind  
- Gantt  
- Documentation technique  

## **Théo**
- Tests  
- Qualité  
- Validation  
- Vérification des exigences  

Cette organisation garantit un travail structuré et professionnel.

---

# **Environnement d’utilisation de l’application**

## **En interne**
- Gestion complète du catalogue  
- CRUD véhicules / marques / catégories / utilisateurs  
- Accès sécurisé (admin / employé)  

## **En situation RP**
- Consultation rapide (mobile / tablette / PC)  
- Présentation immédiate d’un véhicule  
- Informations claires (prix, image, caractéristiques)  

## **En public**
- Catalogue accessible à tous  
- Filtres par catégorie  
- Images et prix  
- Contact Discord  

---

# **Finalité du projet**

Le projet LS Motors vise à fournir :

- un outil métier complet  
- simple et ergonomique  
- sécurisé et fiable  
- adapté au RP  
- professionnellement documenté  

Il démontre la capacité de **NYT** à mener un projet complet, de l’analyse à la réalisation technique.

