# Cahier des charges – Electricity Business (TP Java SE )

### 1. Portée

| Catégorie | Exigences **obligatoires** (à livrer) | **Bonus**  |
| --- | --- | --- |
| **Comptes** | - Inscription ➜ génération d’un *code* - Validation du compte par le code - Connexion / déconnexion | — |
| **Bornes & lieux** | - Ajouter / modifier un **Lieu** - Ajouter / modifier une **Borne** - Supprimer une borne **si** aucune réservation future | - Historique des changements de tarif |
| **Réservation** | - Chercher bornes **DISPONIBLES** pour un créneau - Créer une réservation *(statut : EN_ATTENTE)* - Accepter / refuser une réservation | - Filtrage des réservations (par date, borne…) |
| **Documents** | - Générer un *reçu* texte (`.txt`) lors de l’acceptation | - Générer un pseudo‑PDF (`.txt` structuré) - Export CSV/Excel de l’historique |
| **IHM console** | - Menu principal clair - Validation des entrées utilisateur | - Couleurs ANSI ou ASCII‑art « carte » |

---

### 2. Contraintes techniques

1. **Technologie** : JDK 20+ ; pas d’outils de build, pas de bibliothèque externe.
2. **Packages** attendus :
    - `model` – entités : `Utilisateur`, `LieuRecharge`, `BorneRecharge`, `Reservation`, énumérations `EtatBorne`, `StatutReservation`.
    - `interfaces` – abstractions : `AuthentificationService`, `ReservationService`, `BorneService`, `DocumentService`.
    - `services` – implémentations en mémoire (collections Java).
    - `ui` – menus console, classe `Main`.
3. **Persistance** : aucune (données volatiles). *(Bonus : sérialisation Java dans un fichier à la fermeture)*
4. **Tests unitaires** : non demandés.

---

### 3. Modèle de données minimal

```
Utilisateur { id, email, motDePasse, codeValidation, estValide }
LieuRecharge { id, nom, adresse, bornes: List<BorneRecharge> }
BorneRecharge { id, etat, tarifHoraire }
Reservation { id, utilisateur, borne, dateDebut, dateFin, statut }

```

---

### 4. Scénario d’utilisation (workflow simplifié)

1. **Inscription** → affichage du code dans la console « (e‑mail simulé) ».
2. **Validation** du compte avec ce code.
3. **Connexion**.
4. **Recherche** d’une borne libre pour un créneau (la console liste les bornes “DISPONIBLE”).
5. **Réservation** → statut initial *EN_ATTENTE*.
6. **Opérateur** accepte ou refuse (le développeur peut commuter l’application en “mode opérateur” via le menu).
7. Sur *ACCEPTÉE* → génération d’un fichier `recu_<id>.txt` dans `/exports`.

---

### 5. Menu console de référence *(obligatoire)*

```
=== Electricity Business ===
1. S'inscrire
2. Valider l'inscription
3. Se connecter
4. Rechercher & réserver une borne
5. Gérer mes réservations
6. Administration (lieux / bornes)
0. Quitter

```

---

### 6. Livrables

1. **Code source** complet (`src/`)
2. **README.md** avec liste claire :
    - Partie **core** terminée.
    - Bonus implémentés (facultatif).
3. Dossier `exports/` contenant les reçus générés.