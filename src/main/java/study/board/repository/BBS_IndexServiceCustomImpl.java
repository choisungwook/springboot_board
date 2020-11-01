package study.board.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import study.board.domain.dto.index.QResponse_index_posts_dto;
import study.board.domain.dto.index.Response_index_posts_dto;

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
    public Page<Response_index_posts_dto> findPostsfromId(Long id, Pageable pageable) {
        QueryResults<Response_index_posts_dto> results = jpaQueryFactory
                .select(new QResponse_index_posts_dto(post.id, post.title, post.hit, post.createDate))
                .from(bBS)
                .join(bBS.posts, post)
                .where(bBS.id.eq(id))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Response_index_posts_dto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }
}
