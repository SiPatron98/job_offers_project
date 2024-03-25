package pl.joboffers.domain.offer;

import pl.joboffers.domain.offer.dto.JobOfferResponseDto;

import java.util.List;

public class OfferFacadeTestConfiguration {

    private final FetcherTestImpl fetcherTest;
    private final OfferRepositoryTestImpl offerRepositoryTest;

    public OfferFacadeTestConfiguration() {
        this.fetcherTest = new FetcherTestImpl(
                List.of(
                        new JobOfferResponseDto("id1", "company1", "1000", "1"),
                        new JobOfferResponseDto("id2", "company2", "2000", "2"),
                        new JobOfferResponseDto("id3", "company3", "3000", "3"),
                        new JobOfferResponseDto("id4", "company4", "4000", "4"),
                        new JobOfferResponseDto("id5", "company5", "5000", "5"),
                        new JobOfferResponseDto("id6", "company6", "6000", "6")
                )
        );
        this.offerRepositoryTest = new OfferRepositoryTestImpl();
    }

    OfferFacadeTestConfiguration(List<JobOfferResponseDto> remoteClientOffers) {
        this.fetcherTest = new FetcherTestImpl(remoteClientOffers);
        this.offerRepositoryTest = new OfferRepositoryTestImpl();
    }

    OfferFacade offerFacadeForTests() {
        return new OfferFacade(offerRepositoryTest, new OfferService(offerRepositoryTest, fetcherTest));
    }
}
