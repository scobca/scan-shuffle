CREATE TABLE user_sessions
(
    session_id         UUID PRIMARY KEY DEFAULT uuidv7(),
    user_id            UUID                     NOT NULL,
    refresh_token_hash VARCHAR(255)             NOT NULL UNIQUE,
    expires_at         TIMESTAMP WITH TIME ZONE NOT NULL,
    revoked_at         TIMESTAMP WITH TIME ZONE,
    created_at         TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT fk_user_sessions_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE INDEX idx_user_sessions_user_id ON user_sessions (user_id);
