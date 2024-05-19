package teamkiim.koffeechat.skillcategory.domain.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import teamkiim.koffeechat.skillcategory.domain.SkillCategory;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SkillCategoryRepository{

    private final EntityManager em;

    /**
     * 카테고리 이름 리스트로 카테고리 조회
     */
    public List<SkillCategory> findCategories(List<String> names) {
        return em.createQuery(
                        "SELECT c FROM SkillCategory c WHERE c.name IN :names", SkillCategory.class)
                .setParameter("names", names)
                .getResultList();
    }

}