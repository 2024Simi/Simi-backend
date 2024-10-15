package com.project.simi.domain.diary.domain.converter;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.simi.domain.diary.domain.EmotionOfEpisodes;

public class EmotionOfEpisodesJsonType implements UserType<EmotionOfEpisodes> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public int getSqlType() {
        return Types.OTHER; // PostgreSQL의 JSONB 타입을 처리할 때는 OTHER 타입을 사용
    }

    @Override
    public Class<EmotionOfEpisodes> returnedClass() {
        return EmotionOfEpisodes.class;
    }

    @Override
    public boolean equals(EmotionOfEpisodes x, EmotionOfEpisodes y) throws HibernateException {
        return Objects.equals(x, y);
    }

    @Override
    public int hashCode(EmotionOfEpisodes x) throws HibernateException {
        return Objects.hashCode(x);
    }

    @Override
    public EmotionOfEpisodes nullSafeGet(
            ResultSet rs, int position, SharedSessionContractImplementor session, Object owner)
            throws SQLException {
        String json = rs.getString(position);
        if (json == null || json.isEmpty()) {
            return null;
        }

        try {
            // JSON 문자열을 EmotionOfEpisodes 객체로 변환
            return objectMapper.readValue(json, EmotionOfEpisodes.class);
        } catch (JsonProcessingException e) {
            throw new HibernateException(
                    "Failed to convert String to EmotionOfEpisodes: " + json, e);
        }
    }

    @Override
    public void nullSafeSet(
            PreparedStatement st,
            EmotionOfEpisodes value,
            int index,
            SharedSessionContractImplementor session)
            throws HibernateException, SQLException {
        if (value == null) {
            st.setNull(index, Types.OTHER); // 값이 없을 때는 NULL로 설정
        } else {
            try {
                // EmotionOfEpisodes 객체를 JSON 문자열로 변환
                String json = objectMapper.writeValueAsString(value);
                st.setObject(index, json, Types.OTHER);
            } catch (JsonProcessingException e) {
                throw new HibernateException(
                        "Failed to convert EmotionOfEpisodes to String: " + value, e);
            }
        }
    }

    @Override
    public EmotionOfEpisodes deepCopy(EmotionOfEpisodes value) throws HibernateException {
        try {
            // 깊은 복사를 위해 객체를 JSON으로 직렬화한 후 다시 역직렬화
            return objectMapper.readValue(
                    objectMapper.writeValueAsString(value), EmotionOfEpisodes.class);
        } catch (JsonProcessingException e) {
            throw new HibernateException("Failed to deep copy EmotionOfEpisodes: " + value, e);
        }
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(EmotionOfEpisodes value) throws HibernateException {
        return deepCopy(value);
    }

    @Override
    public EmotionOfEpisodes assemble(Serializable cached, Object owner) throws HibernateException {
        return deepCopy((EmotionOfEpisodes) cached);
    }

    @Override
    public EmotionOfEpisodes replace(
            EmotionOfEpisodes original, EmotionOfEpisodes target, Object owner)
            throws HibernateException {
        return deepCopy(original);
    }
}
