package com.english_study.exception;

import com.english_study.model.dto.ConflictInfo;
import lombok.Getter;
import java.util.List;

@Getter
public class ConflictWordException extends RuntimeException {
    private final List<ConflictInfo> conflicts;

    public ConflictWordException(String message, List<ConflictInfo> conflicts) {
        super(message);
        this.conflicts = conflicts;
    }
}
