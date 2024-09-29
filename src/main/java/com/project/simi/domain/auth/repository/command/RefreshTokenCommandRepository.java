package com.project.simi.domain.auth.repository.command;

import com.project.simi.domain.auth.domain.RefreshToken;

public interface RefreshTokenCommandRepository {
    RefreshToken save(RefreshToken of);
}
