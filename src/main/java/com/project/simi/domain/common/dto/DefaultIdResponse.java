package com.project.simi.domain.common.dto;

import lombok.Getter;

@Getter
public class DefaultIdResponse extends IdResponseBase {

    public DefaultIdResponse(Long id) {
        super(id);
    }
}
