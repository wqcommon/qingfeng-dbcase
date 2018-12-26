package com.dbcase.dialect;

import org.hibernate.dialect.PostgreSQL95Dialect;
import org.hibernate.type.StringType;

import java.sql.Types;

/**
 * 添加json类型的处理
 */
public class JsonbPostgresDialect extends PostgreSQL95Dialect{

    public JsonbPostgresDialect(){
        super();
        registerColumnType(Types.JAVA_OBJECT,"jsonb");
    }

}
