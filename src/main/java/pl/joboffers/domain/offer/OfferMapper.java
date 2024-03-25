package pl.joboffers.domain.offer;

import pl.joboffers.domain.offer.dto.JobOfferResponseDto;
import pl.joboffers.domain.offer.dto.OfferRequestDto;
import pl.joboffers.domain.offer.dto.OfferResponseDto;

public class OfferMapper {

    public static OfferResponseDto mapFromOfferToOfferDto(Offer offer) {
        return OfferResponseDto.builder()
                .id(offer.id())
                .companyName(offer.companyName())
                .position(offer.position())
                .salary(offer.salary())
                .url(offer.url())
                .build();
    }

    public static Offer mapFromOfferDtoToOffer(OfferRequestDto offerRequestDto) {
        return Offer.builder()
                .companyName(offerRequestDto.companyName())
                .position(offerRequestDto.position())
                .salary(offerRequestDto.salary())
                .url(offerRequestDto.url())
                .build();
    }

    public static Offer mapFromJobOfferResponseToOffer(JobOfferResponseDto jobOfferResponseDto) {
        return Offer.builder()
                .companyName(jobOfferResponseDto.company())
                .position(jobOfferResponseDto.title())
                .salary(jobOfferResponseDto.salary())
                .url(jobOfferResponseDto.url())
                .build();
    }
}
