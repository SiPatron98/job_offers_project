package pl.joboffers.domain.offer;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
class OfferService {

    private final OfferRepository offerRepository;
    private final OfferFetchable offerFetcher;

    List<Offer> fetchAllOffersAndSaveAllIfNotExist() {
        List<Offer> jobOffers = fetchOffers();
        final List<Offer> offers = filterNotExistingOffers(jobOffers);
        try {
            return offerRepository.saveAll(offers);
        } catch (OfferDuplicateException offerDuplicateException) {
            throw new OfferSavingException(offerDuplicateException.getMessage(), jobOffers);
        }
    }

    private List<Offer> filterNotExistingOffers(List<Offer> jobOffers) {
        return jobOffers.stream()
                .filter(offer -> !offer.url().isEmpty())
                .filter(offer -> !offerRepository.existsByOfferUrl(offer.url()))
                .toList();
    }

    private List<Offer> fetchOffers() {
        return offerFetcher.fetchOffers()
                .stream()
                .map(OfferMapper::mapFromJobOfferResponseToOffer)
                .toList();

    }
}
