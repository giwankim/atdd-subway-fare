package nextstep.subway.path.domain;

import nextstep.subway.section.domain.Section;
import org.jgrapht.graph.DefaultWeightedEdge;

public class SectionEdge extends DefaultWeightedEdge {
    private final Section section;

    public static SectionEdge of(Section section) {
        return new SectionEdge(section);
    }

    public SectionEdge(Section section) {
        this.section = section;
    }

    public Section getSection() {
        return section;
    }
}
