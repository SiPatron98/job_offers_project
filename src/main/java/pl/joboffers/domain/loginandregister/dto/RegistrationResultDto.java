package pl.joboffers.domain.loginandregister.dto;

import lombok.Builder;

@Builder
public record RegistrationResultDto(String id, boolean created, String login) {
}
