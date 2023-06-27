package com.khpt.projectkim.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ChatAuthor {
    SYSTEM("system"),
    ASSISTANT("assistant"),
    USER("user");

    private final String value;
}
