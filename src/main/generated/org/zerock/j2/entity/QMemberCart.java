package org.zerock.j2.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMemberCart is a Querydsl query type for MemberCart
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberCart extends EntityPathBase<MemberCart> {

    private static final long serialVersionUID = -1098654061L;

    public static final QMemberCart memberCart = new QMemberCart("memberCart");

    public final NumberPath<Long> cno = createNumber("cno", Long.class);

    public final StringPath email = createString("email");

    public final NumberPath<Long> pno = createNumber("pno", Long.class);

    public QMemberCart(String variable) {
        super(MemberCart.class, forVariable(variable));
    }

    public QMemberCart(Path<? extends MemberCart> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMemberCart(PathMetadata metadata) {
        super(MemberCart.class, metadata);
    }

}

