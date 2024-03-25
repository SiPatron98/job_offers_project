package pl.joboffers.domain.offer;

import pl.joboffers.domain.offer.dto.JobOfferResponseDto;

import java.util.List;

public class FetcherTestImpl implements OfferFetchable {

    List<JobOfferResponseDto> jobOffers;

    FetcherTestImpl(List<JobOfferResponseDto> jobOffers) {
        this.jobOffers = jobOffers;
    }

    @Override
    public List<JobOfferResponseDto> fetchOffers() {
        return jobOffers;
    }
}
