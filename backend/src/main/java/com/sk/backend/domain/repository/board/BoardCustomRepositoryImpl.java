package com.sk.backend.domain.repository.board;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sk.backend.domain.dto.board.BoardCardResponse;
import com.sk.backend.domain.dto.board.BoardDetailResponse;
import com.sk.backend.domain.dto.board.BoardSearchFilter;
import com.sk.backend.domain.entity.QBoard;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * packageName    : com.sk.backend.domain.repository.board
 * fileName       : BoardCustomRepository
 * author         : moongi
 * date           : 12/1/24
 * description    :
 */
@Repository
@RequiredArgsConstructor
public class BoardCustomRepositoryImpl implements BoardCustomRepository {
    private final JPAQueryFactory queryFactory;
    private final QBoard b = QBoard.board;

    @Override
    public BoardDetailResponse findBoardByIdAndDeletedAtNull(Long id) {
        return queryFactory
                .select(
                        Projections.constructor
                                (BoardDetailResponse.class,
                                        b.id,
                                        b.title,
                                        b.author.username,
                                        b.createdAt,
                                        b.viewCnt,
                                        b.content,
                                        b.file
                                ))
                .from(b)
                .where(b.id.eq(id), b.deletedAt.isNull())
                .fetchOne();
    }

    @Override
    public Page<BoardCardResponse> findBoardsByFilterAndLoginUser(BoardSearchFilter filter) {

        BooleanBuilder builder = new BooleanBuilder();

        // 제목 검색 조건
        if (filter.getTitle() != null && !filter.getTitle().isEmpty()) {
            builder.and(b.title.containsIgnoreCase(filter.getTitle())); // 제목 검색
        }

        // 작성자 검색 조건
        if (filter.getAuthor() != null && !filter.getAuthor().isEmpty()) {
            builder.and(b.author.username.containsIgnoreCase(filter.getAuthor())); // 작성자 검색
        }

        // 페이징 설정
        PageRequest pageable = PageRequest.of(filter.getPage(), filter.getSize());


        List<BoardCardResponse> boardCardResponses = queryFactory
                .select(
                        Projections.constructor(BoardCardResponse.class,
                                b.id,
                                b.title,
                                b.author.username,
                                b.viewCnt,
                                b.isFile,
                                b.createdAt
                        )
                )
                .from(b)
                .where(builder, b.deletedAt.isNull())
                .orderBy(b.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(b.count())
                .from(b)
                .where(builder, b.deletedAt.isNull())
                .fetchOne();

        return new PageImpl<>(boardCardResponses, pageable, total);

    }
}
