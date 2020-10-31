package study.board.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import study.board.domain.Post;
import javax.persistence.EntityManager;
import java.util.List;

import static study.board.domain.QBBS.bBS;
import static study.board.domain.QPost.post;

public class BBS_IndexServiceCustomImpl implements BBS_IndexServiceCustom{
    private final JPAQueryFactory jpaQueryFactory;

    public BBS_IndexServiceCustomImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Post> findPostsfromId(Long id) {
        List<Post> posts = jpaQueryFactory
                .select(post).distinct()
                .from(bBS)
                .join(bBS.posts, post)
                .where(bBS.id.eq(id))
                .fetch();

        return posts;
    }
}
