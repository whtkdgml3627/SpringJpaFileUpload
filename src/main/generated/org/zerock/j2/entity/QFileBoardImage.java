package org.zerock.j2.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFileBoardImage is a Querydsl query type for FileBoardImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFileBoardImage extends EntityPathBase<FileBoardImage> {

    private static final long serialVersionUID = -1136980502L;

    public static final QFileBoardImage fileBoardImage = new QFileBoardImage("fileBoardImage");

    public final StringPath fname = createString("fname");

    public final NumberPath<Long> imagno = createNumber("imagno", Long.class);

    public final NumberPath<Integer> ord = createNumber("ord", Integer.class);

    public final StringPath uuid = createString("uuid");

    public QFileBoardImage(String variable) {
        super(FileBoardImage.class, forVariable(variable));
    }

    public QFileBoardImage(Path<? extends FileBoardImage> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFileBoardImage(PathMetadata metadata) {
        super(FileBoardImage.class, metadata);
    }

}

