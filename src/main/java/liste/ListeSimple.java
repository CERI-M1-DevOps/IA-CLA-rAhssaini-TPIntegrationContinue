package liste;

import java.util.Objects;

public class ListeSimple {
    private long size;
    Noeud tete;

    public long getSize() {
        return size;
    }

    public void ajout(int element) {
        tete = new Noeud(element, tete);
        size++;
    }

    public void modifiePremier(Object element, Object nouvelleValeur) {
        Noeud courant = tete;
        while (courant != null && !Objects.equals(courant.getElement(), element)) {
            courant = courant.getSuivant();
        }
        if (courant != null) {
            courant.setElement(nouvelleValeur);
        }
    }

    public void modifieTous(Object element, Object nouvelleValeur) {
        Noeud courant = tete;
        while (courant != null) {
            if (Objects.equals(courant.getElement(), element)) {
                courant.setElement(nouvelleValeur);
            }
            courant = courant.getSuivant();
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("ListeSimple(");
        Noeud n = tete;
        while (n != null) {
            sb.append(n);
            n = n.getSuivant();
            if (n != null) {
                sb.append(", ");
            }
        }
        sb.append(")");
        return sb.toString();
    }

    public void supprimePremier(Object element) {
        if (tete != null) {
            if (Objects.equals(tete.getElement(), element)) {
                tete = tete.getSuivant();
                size--;
                return;
            }
            Noeud precedent = tete;
            Noeud courant = tete.getSuivant();
            while (courant != null && !Objects.equals(courant.getElement(), element)) {
                precedent = precedent.getSuivant();
                courant = courant.getSuivant();
            }
            if (courant != null) {
                precedent.setSuivant(courant.getSuivant());
                size--;
            }
        }
    }

    public void supprimeTous(int element) {
       tete = supprimeTousRecurs(element, tete);
    }

    public Noeud supprimeTousRecurs(Object element, Noeud noeudCourant) {
        if (noeudCourant != null) {
            Noeud suiteListe = supprimeTousRecurs(element, noeudCourant.getSuivant());
            if (Objects.equals(noeudCourant.getElement(), element)) {
                size--;
                return suiteListe;
            } else {
                noeudCourant.setSuivant(suiteListe);
                return noeudCourant;
            }
        } else {
            return null;
        }
    }

    public Noeud getAvantDernier() {
        if (tete == null || tete.getSuivant() == null) {
            return null;
        } else {
            Noeud courant = tete;
            Noeud suivant = courant.getSuivant();
            while (suivant.getSuivant() != null) {
                courant = suivant;
                suivant = suivant.getSuivant();
            }
            return courant;
        }
    }

    public void inverser() {
        Noeud precedent = null;
        Noeud courant = tete;
        while (courant != null) {
            Noeud next = courant.getSuivant();
            courant.setSuivant(precedent);
            precedent = courant;
            courant = next;
        }
        tete = precedent;
    }

    public Noeud getPrecedent(Noeud r) {
        Noeud precedent = tete;
        Noeud courant = precedent.getSuivant();
        while (courant != r) {
            precedent = courant;
            courant = courant.getSuivant();
        }
        return precedent;
    }

    public void echanger(Noeud r1, Noeud r2) {
        if (r1 == r2) {
            return;
        }
        Noeud precedentR1, precedentR2;
        if (r1 != tete && r2 != tete) {
            precedentR1 = getPrecedent(r1);
            precedentR2 = getPrecedent(r2);
            precedentR1.setSuivant(r2);
            precedentR2.setSuivant(r1);
        } else if (r1 == tete) {
            precedentR2 = getPrecedent(r2);
            precedentR2.setSuivant(tete);
            tete = r2;
        } else if (r2 == tete) {
            precedentR1 = getPrecedent(r1);
            precedentR1.setSuivant(tete);
            tete = r1;
        }

        Noeud temp = r2.getSuivant();
        r2.setSuivant(r1.getSuivant());
        r1.setSuivant(temp);
    }
    @Test
    public void testModifiePremierNonExistant() {
        ListeSimple liste = new ListeSimple();
        liste.ajout(1);
        liste.ajout(2);
        // Tente de modifier un élément qui n'est pas dans la liste
        liste.modifiePremier(99, 100); 
        assertEquals(2, liste.getSize());
    }

    @Test
    public void testSupprimePremierListeVide() {
        ListeSimple liste = new ListeSimple();
        // Tente de supprimer sur une liste vide
        liste.supprimePremier(1); 
        assertEquals(0, liste.getSize());
    }

    @Test
    public void testSupprimePremierTete() {
        ListeSimple liste = new ListeSimple();
        liste.ajout(1);
        liste.ajout(2);
        // Supprime la tête de liste (le dernier ajouté, donc 2)
        liste.supprimePremier(2); 
        assertEquals(1, liste.getSize());
    }

    @Test
    public void testSupprimePremierMilieuEtNonExistant() {
        ListeSimple liste = new ListeSimple();
        liste.ajout(1);
        liste.ajout(2);
        liste.ajout(3);
        // Supprime au milieu
        liste.supprimePremier(2); 
        // Supprime un élément inexistant pour couvrir la fin de boucle
        liste.supprimePremier(99); 
        assertEquals(2, liste.getSize());
    }

    @Test
    public void testEchangerMemeNoeudEtTete() {
        ListeSimple liste = new ListeSimple();
        liste.ajout(1);
        liste.ajout(2);
        liste.ajout(3);
        
        Noeud noeudTete = liste.tete; // 3
        Noeud noeudMilieu = liste.tete.getSuivant(); // 2
        
        // Test: échanger le même nœud
        liste.echanger(noeudMilieu, noeudMilieu);
        
        // Test: échanger la tête avec un autre (r1 == tete)
        liste.echanger(noeudTete, noeudMilieu);
        
        // Test: échanger un autre avec la tête (r2 == tete)
        // Après l'échange précédent, la tête a changé. Récupérons la nouvelle configuration.
        Noeud nouvelleTete = liste.tete;
        Noeud autreNoeud = liste.tete.getSuivant();
        liste.echanger(autreNoeud, nouvelleTete);
        
        assertEquals(3, liste.getSize());
    }
}