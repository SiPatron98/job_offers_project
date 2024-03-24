package pl.joboffers.domain.loginandregister.dto;

import lombok.Builder;

@Builder
public record RegisterUserDto(String login, String password) {
}
