package com.dbcase.dbType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.HibernateException;
import org.hibernate.boot.registry.classloading.internal.ClassLoaderServiceImpl;
import org.hibernate.boot.registry.classloading.spi.ClassLoaderService;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.SerializationException;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;
import org.postgresql.util.PGobject;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * jsonb类型
 */
public class JsonbType implements UserType,ParameterizedType{

    private final ObjectMapper mapper = new ObjectMapper();

    private static final ClassLoaderService classLoaderService = new ClassLoaderServiceImpl();

    private static final String CLASS = "CLASS";

    private Class<?> jsonClassType;


    @Override
    public void setParameterValues(Properties properties) {
        final String classzz = (String) properties.get(CLASS);
    }

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.JAVA_OBJECT};
    }

    @Override
    public Class returnedClass() {
        return Map.class;
    }

    @Override
    public boolean equals(Object o, Object o1) throws HibernateException {
        return ObjectUtils.nullSafeEquals(o,o1);
    }

    @Override
    public int hashCode(Object o) throws HibernateException {
        if(o == null){
            return 0;
        }
        return o.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] strings, SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException, SQLException {
        PGobject pGobject = (PGobject) resultSet.getObject(strings[0]);
        if(pGobject != null && pGobject.getValue() != null){
            try {
                return mapper.readValue(pGobject.getValue(),Map.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new HashMap<String,Object>();
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object o, int i, SharedSessionContractImplementor sharedSessionContractImplementor) throws HibernateException, SQLException {
        if(o == null){
            preparedStatement.setNull(i, Types.OTHER);
        }else {
            try {
                preparedStatement.setObject(i,mapper.writeValueAsString(o),Types.OTHER);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Object deepCopy(Object o) throws HibernateException {
        if(o != null){
            try {
                return mapper.readValue(mapper.writeValueAsString(o),returnedClass());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(Object o) throws HibernateException {
        Object copy = deepCopy(o);
        if(copy instanceof Serializable){
            return (Serializable) copy;
        }
       throw new SerializationException(String.format("Cannot serialize %s,%s is not serializable.",o,o.getClass()),null);
    }

    @Override
    public Object assemble(Serializable serializable, Object o) throws HibernateException {
        return deepCopy(serializable);
    }

    @Override
    public Object replace(Object o, Object o1, Object o2) throws HibernateException {
        return deepCopy(o);
    }
}
