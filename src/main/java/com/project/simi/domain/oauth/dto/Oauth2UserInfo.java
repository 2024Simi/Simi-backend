package com.project.simi.domain.oauth.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.fasterxml.jackson.databind.ObjectMapper;

@Getter
@AllArgsConstructor
public abstract class Oauth2UserInfo {
    protected static final ObjectMapper objectMapper = new ObjectMapper();
    protected Map<String, Object> attributes;
}
