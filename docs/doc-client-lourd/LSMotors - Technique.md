# **LS MOTORS – Documentation Technique (Client Lourd Java)**

## **Auteurs**
- **Dave ISRAEL**  
- **Youssef BOUMILK**  
- **Noah MILLOT**  
- **Théo DORIVAL**  

**Entreprise :** NYT (Nyght)  
**Formation :** BTS SIO – Option SLAM  

---

## **Sommaire**
1. [Liste Technique](#liste-technique)  
2. [Base de données](#base-de-données)  
3. [Sécurité](#sécurité)  
4. [Déploiement et exécution](#déploiement-et-exécution)  
5. [Outils de développement](#outils-de-développement)

---

# **Liste Technique**

| Technologie | Rôle | Version |
|------------|------|---------|
| **Java** | Langage principal | Java 17+ |
| **Java Swing** | Interface graphique (GUI) native | Intégré JDK |
| **JDBC** | Connexion Java ↔ MySQL (requêtes préparées) | Intégré JDK |
| **MySQL** | Base de données partagée avec le client léger | MySQL 8+ |
| **Maven / Gradle** | Gestion des dépendances & compilation | - |
| **GitHub** | Versionnement du code source | - |

---

# **Base de données**

| Table utilisée | Opérations dans la console Java |
|----------------|--------------------------------|
| **utilisateur** | SELECT, INSERT, UPDATE, DELETE + vérification login/password |
| **vehicule** | SELECT (avec filtres), INSERT, UPDATE, DELETE |
| **categorie** | SELECT, INSERT, UPDATE, DELETE |
| **marque** | SELECT, INSERT, UPDATE, DELETE |
| **vente** | SELECT (groupé par semaine / employé) |
| **config** | SELECT (chargement), UPDATE (marge) |

---

# **Sécurité**

| Mesure | Implémentation |
|--------|----------------|
| **Authentification** | Vérification email + mot de passe hashé (bcrypt) |
| **Accès restreint** | Seuls les comptes admin peuvent se connecter |
| **Requêtes préparées** | JDBC `PreparedStatement` sur toutes les requêtes |
| **Pas de stockage local** | Aucune donnée sensible enregistrée sur le poste |
| **Session en mémoire** | Credentials conservés uniquement en RAM |

---

# **Déploiement et exécution**

| Étape | Détail |
|-------|--------|
| **Prérequis** | Java 17+ installé, accès réseau MySQL |
| **Compilation** | `mvn package` ou `gradle build` |
| **Exécution** | `java -jar LSMotors-Console.jar` |
| **Configuration BDD** | Modifier `DatabaseConnection.java` |
| **Driver MySQL** | Inclure `mysql-connector-j-8.x.jar` dans le classpath |

---

# **Outils de développement**

| Outil | Usage |
|-------|-------|
| **IntelliJ IDEA / Eclipse** | IDE Java principal |
| **Java Swing Designer** | Conception visuelle des interfaces |
| **Maven / Gradle** | Gestion des dépendances |
| **phpMyAdmin** | Vérification des données en base |
| **GitHub** | Versionnement & collaboration NYT |
