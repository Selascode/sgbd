# Projet Intégratif — Développement Orienté Objet 2026
kamara
## Mini-SGBD en Java avec parseur JavaCC

---

## Compilation

Tout se fait via le `makefile` depuis la **racine du projet** :

```bash
# Générer les sources Java depuis la grammaire JavaCC, puis compiler tout le projet
make comp

# Lancer tous les tests unitaires JUnit
make test

# Lancer un seul fichier de test
make test TEST_CLASS=fr.insarouen.iti.prog.sgbd.modele.TestTable

# Nettoyer les fichiers compilés
make clean
```

> **Note :** `make comp` appelle automatiquement `make javacc` pour régénérer le parseur depuis `sgbd.jj` avant de compiler. Ne pas modifier manuellement les fichiers du dossier `parseur/` générés par JavaCC.

---

## Utilisation de l'interpréteur

L'interpréteur lit un fichier `.sql` et exécute chaque commande l'une après l'autre.

```bash
# Après make comp :
java -cp classes fr.insarouen.iti.prog.sgbd.execution.Interpreteur <chemin_vers_fichier.sql>
```

### Exemple

```bash
java -cp classes fr.insarouen.iti.prog.sgbd.execution.Interpreteur Exemples/test_bat_creation.sql
```

Sortie attendue :
```
Analyse du fichier: Exemples/test_bat_creation.sql
Table 'bases' créée.
[OK] Commande syntaxiquement correcte !
1 ligne insérée dans bases
[OK] Commande syntaxiquement correcte !
...
Au revoir ;)
```

---

## Syntaxe SQL supportée

Toutes les commandes se terminent par un **point-virgule** (`;`). Les mots-clés sont **insensibles à la casse**.

### DDL — Définition de données

```sql
-- Créer une table
CREATE TABLE heroes (id SERIAL, nom VARCHAR, puissance INT);

-- Supprimer une table
DROP TABLE heroes;

-- Lister toutes les tables de la base
TABLES;
```

### DML — Manipulation de données

```sql
-- Insérer une ligne (les colonnes SERIAL sont auto-incrémentées si omises)
INSERT INTO heroes (nom, puissance) VALUES ("Batman", 100);

-- Supprimer des lignes (WHERE optionnel — sans WHERE supprime tout)
DELETE FROM heroes WHERE puissance = 100;

-- Interroger la base (SELECT en cours d'implémentation)
SELECT nom, alias FROM heroes WHERE puissance > 50;
SELECT * FROM heroes;
```

### Commandes de contrôle

```sql
-- Quitter l'interpréteur
EXIT;
QUIT;
```

### Types de données

| Type SQL | Description |
|----------|-------------|
| `INT` | Entier signé |
| `VARCHAR` | Chaîne de caractères (entre guillemets `"`) |
| `SERIAL` | Entier auto-incrémenté (clé primaire typique) |

### Commentaires

```sql
-- Ceci est un commentaire SQL (ignoré par le parseur)
CREATE TABLE test (id SERIAL); -- commentaire en fin de ligne
```

---

## Fichiers d'exemples

Des fichiers `.sql` de démonstration sont disponibles dans le dossier `Exemples/` :

| Fichier | Description |
|---------|-------------|
| `test_bat_creation.sql` | Création de 3 tables liées (bases, heroes, gadgets) avec insertions et jointure |
| `test_types.sql` | Test des différents types de données |
| `test_drop.sql` | Création et suppression de tables |
| `test_duplicate.sql` | Gestion des erreurs (table déjà existante) |
| `test_delete.sql` | Suppression de lignes avec condition WHERE |
| `test_tables.sql` | Commande TABLES (liste des tables) |
| `test_error.sql` | Commandes syntaxiquement incorrectes |
| `test_jointure.sql` | SELECT avec jointure entre deux tables |

```bash
# Lancer le fichier de démonstration complet
java -cp classes fr.insarouen.iti.prog.sgbd.execution.Interpreteur Exemples/test_bat_creation.sql
```

---

## Architecture du projet

```
src/
└── fr/insarouen/iti/prog/sgbd/
    ├── modele/          # Modèle de données : BaseDeDonnees, Table, Tuple, Valeur...
    ├── parseur/         # Grammaire JavaCC (sgbd.jj) + fichiers générés
    ├── expressions/     # Expressions logiques pour les clauses WHERE
    ├── commandes/       # Classes de commandes SQL (structure AST optionnelle)
    ├── exceptions/      # Exceptions métier
    ├── execution/       # Interpreteur.java — point d'entrée
    └── persistance/     # Gestion de la sauvegarde/chargement

srctest/                 # Tests unitaires JUnit (même structure que src/)
Exemples/                # Fichiers .sql de test et démonstration
lib/                     # JARs JUnit (junit-4.13.2.jar, hamcrest-core-1.3.jar)
makefile                 # Règles de compilation et de test
```

---

## État d'avancement du parseur

| Commande | État |
|----------|------|
| `CREATE TABLE` | ✅ Opérationnel (avec gestion SERIAL auto) |
| `DROP TABLE` | ✅ Opérationnel |
| `TABLES` | ✅ Opérationnel |
| `INSERT INTO` | ✅ Opérationnel |
| `EXIT` / `QUIT` | ✅ Opérationnel |
| `DELETE FROM WHERE` | 🔧 En cours (dépend du module expressions) |
| `SELECT FROM WHERE` | 🔧 En cours (dépend du module algèbre relationnelle) |

---

## Auteurs

Projet INSA Rouen Normandie — 2025/2026
