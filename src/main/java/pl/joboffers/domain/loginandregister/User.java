package pl.joboffers.domain.loginandregister;

import lombok.Builder;

@Builder
record User(String id, String login, String password) {
}
