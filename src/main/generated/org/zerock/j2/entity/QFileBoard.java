package org.zerock.j2.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFileBoard is a Querydsl query type for FileBoard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFileBoard extends EntityPathBase<FileBoard> {

    private static final long serialVersionUID = -227001871L;

    public static final QFileBoard fileBoard = new QFileBoard("fileBoard");

    public final NumberPath<Long> bno = createNumber("bno", Long.class);

    public final StringPath content = createString("content");

    public final ListPath<FileBoardImage, QFileBoardImage> images = this.<FileBoardImage, QFileBoardImage>createList("images", FileBoardImage.class, QFileBoardImage.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    public final StringPath writer = createString("writer");

    public QFileBoard(String variable) {
        super(FileBoard.class, forVariable(variable));
    }

    public QFileBoard(Path<? extends FileBoard> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFileBoard(PathMetadata metadata) {
        super(FileBoard.class, metadata);
    }

}

