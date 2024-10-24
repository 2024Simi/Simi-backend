package com.project.simi.domain.user.repository.command;

import com.project.simi.domain.user.domain.User;

public interface UserCommandRepository {
    User save(User user);
}
