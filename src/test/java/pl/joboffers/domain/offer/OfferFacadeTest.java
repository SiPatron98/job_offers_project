package pl.joboffers.domain.offer;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.joboffers.domain.offer.dto.JobOfferResponseDto;
import pl.joboffers.domain.offer.dto.OfferRequestDto;
import pl.joboffers.domain.offer.dto.OfferResponseDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;


public class OfferFacadeTest {

    @Test
    public void shouldFetchFromJobsFromRemoteAndSaveAllOffersWhenRepositoryIsEmpty() {
        // given
        OfferFacade offerFacade = new OfferFacadeTestConfiguration().offerFacadeForTests();
        assertThat(offerFacade.findAllOffers()).isEmpty();

        // when
        List<OfferResponseDto> result = offerFacade.fetchAllOffersAndSaveAllIfNotExist();

        // then
        assertThat(result).hasSize(6);
    }

    @Test
    public void shouldSave4OffersWhenThereAreNoOffersInDatabase() {
        // given
        OfferFacade offerFacade = new OfferFacadeTestConfiguration(List.of()).offerFacadeForTests();

        // when
        offerFacade.save(new OfferRequestDto("company1", "asds", "1000", "1"));
        offerFacade.save(new OfferRequestDto("company2", "asds", "2000", "2"));
        offerFacade.save(new OfferRequestDto("company3", "asds", "3000", "3"));
        offerFacade.save(new OfferRequestDto("company4", "asds", "4000", "4"));

        // then
        assertThat(offerFacade.findAllOffers()).hasSize(4);
    }

    @Test
    public void shouldSaveOnly2OffersWhenRepositoryHad4AddedWithOfferUrls() {
        // given
        OfferFacade offerFacade = new OfferFacadeTestConfiguration(
                List.of(
                        new JobOfferResponseDto("id1", "company1", "1000", "1"),
                        new JobOfferResponseDto("id2", "company2", "2000", "2"),
                        new JobOfferResponseDto("id3", "company3", "3000", "3"),
                        new JobOfferResponseDto("id4", "company4", "4000", "4"),
                        new JobOfferResponseDto("id5", "company5", "5000", "https://someurl.com/5"),
                        new JobOfferResponseDto("id6", "company6", "6000", "https://otherurl.com/6")
                )
        ).offerFacadeForTests();
        offerFacade.save(new OfferRequestDto("id1", "company1", "1000", "1"));
        offerFacade.save(new OfferRequestDto("id2", "company2", "2000", "2"));
        offerFacade.save(new OfferRequestDto("id3", "company3", "3000", "3"));
        offerFacade.save(new OfferRequestDto("id4", "company4", "4000", "4"));

        assertThat(offerFacade.findAllOffers()).hasSize(4);

        // when
        List<OfferResponseDto> result = offerFacade.fetchAllOffersAndSaveAllIfNotExist();

        // then
        assertThat(
                List.of(
                        result.get(0).url(),
                        result.get(1).url()
                )
        ).containsExactlyInAnyOrder("https://someurl.com/5", "https://otherurl.com/6");
    }

    @Test
    public void shouldFindOfferByIdWhenOfferWasSaved() {
        // given
        OfferFacade offerFacade = new OfferFacadeTestConfiguration(List.of()).offerFacadeForTests();
        OfferResponseDto offerResponseDto = offerFacade.save(new OfferRequestDto("company", "junior", "1000", "1"));

        // when
        OfferResponseDto offerById = offerFacade.findOfferById(offerResponseDto.id());

        // then
        assertThat(offerById).isEqualTo(OfferResponseDto.builder()
                .id(offerResponseDto.id())
                .companyName("company")
                .position("junior")
                .salary("1000")
                .url("1")
                .build()
        );
    }
    @Test
    public void shouldThrowNotFoundExceptionWhenOfferNotFound() {
        // given
        OfferFacade offerFacade = new OfferFacadeTestConfiguration(List.of()).offerFacadeForTests();
        assertThat(offerFacade.findAllOffers()).isEmpty();

        // when, then
        Assertions.assertThrows(
                OfferNotFoundException.class,
                () -> offerFacade.findOfferById("100"),
                "Cannot find offer with this id: [100]"
        );
    }

    @Test
    public void shouldThrowDuplicateKeyExceptionWhenOfferWithUrlExists() {
        // given
        OfferFacade offerFacade = new OfferFacadeTestConfiguration(List.of()).offerFacadeForTests();
        OfferResponseDto offerResponseDto = offerFacade.save(new OfferRequestDto("company", "junior", "1000", "1"));
        String savedId = offerResponseDto.id();
        assertThat(offerFacade.findOfferById(savedId).id()).isEqualTo(savedId);

        // when
        Throwable thrown = catchThrowable(() -> offerFacade.save(
                new OfferRequestDto("company2", "senior", "3000", "1")));

        // then
        AssertionsForClassTypes.assertThat(thrown)
                .isInstanceOf(OfferDuplicateException.class)
                .hasMessage("Offer with offerUrl [1] already exists");
    }
}