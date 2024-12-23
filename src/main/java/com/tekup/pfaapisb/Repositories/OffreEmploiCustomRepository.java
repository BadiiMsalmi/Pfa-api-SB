package com.tekup.pfaapisb.Repositories;

import com.tekup.pfaapisb.Models.OffreEmploi;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OffreEmploiCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<OffreEmploi> searchOffres(String titre, String localisation, Integer experience, Double salaire) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<OffreEmploi> query = cb.createQuery(OffreEmploi.class);
        Root<OffreEmploi> offre = query.from(OffreEmploi.class);

        List<Predicate> predicates = new ArrayList<>();

        if (titre != null && !titre.isEmpty()) {
            predicates.add(cb.like(cb.lower(offre.get("titre")), "%" + titre.toLowerCase() + "%"));
        }
        if (localisation != null && !localisation.isEmpty()) {
            predicates.add(cb.like(cb.lower(offre.get("localisation")), "%" + localisation.toLowerCase() + "%"));
        }
        if (experience != null && experience > 0) {
            predicates.add(cb.equal(offre.get("experience"), experience));
        }
        if (salaire != null  && salaire > 0) {
            predicates.add(cb.greaterThanOrEqualTo(offre.get("salaire"), salaire));
        }

        query.where(cb.and(predicates.toArray(new Predicate[0])));
        List<OffreEmploi> result = entityManager.createQuery(query).getResultList();
        return result;
    }

}
