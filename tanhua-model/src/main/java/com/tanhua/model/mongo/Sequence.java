package com.tanhua.model.mongo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @author sxs
 * @create 2022-09-17 13:31
 */
@Data
@Document("sequence")
public class Sequence implements Serializable {
    private Object id;
    private String collName;
    private Long seqId;
}
