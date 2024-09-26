package com.project.simi.auth.repository.command;

import com.project.simi.auth.domain.RefreshToken;

public interface RefreshTokenCommandRepository {
    RefreshToken save(RefreshToken of);
}
