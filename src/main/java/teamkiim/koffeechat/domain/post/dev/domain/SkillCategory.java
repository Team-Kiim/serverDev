package teamkiim.koffeechat.domain.post.dev.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SkillCategory {

    @Enumerated(EnumType.STRING)
    private ParentSkillCategory parentSkillCategory;
    @Enumerated(EnumType.STRING)
    private ChildSkillCategory childSkillCategory;

}
