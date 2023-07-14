package org.zerock.j2.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProduct is a Querydsl query type for Product
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProduct extends EntityPathBase<Product> {

    private static final long serialVersionUID = -1975349802L;

    public static final QProduct product = new QProduct("product");

    public final BooleanPath delFlag = createBoolean("delFlag");

    public final ListPath<ProductImage, QProductImage> images = this.<ProductImage, QProductImage>createList("images", ProductImage.class, QProductImage.class, PathInits.DIRECT2);

    public final StringPath pdesc = createString("pdesc");

    public final StringPath pname = createString("pname");

    public final NumberPath<Long> pno = createNumber("pno", Long.class);

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final StringPath writer = createString("writer");

    public QProduct(String variable) {
        super(Product.class, forVariable(variable));
    }

    public QProduct(Path<? extends Product> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProduct(PathMetadata metadata) {
        super(Product.class, metadata);
    }

}

