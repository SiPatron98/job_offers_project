package pl.joboffers.domain.offer;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OfferRepositoryTestImpl implements OfferRepository {

    Map<String, Offer> offerList = new ConcurrentHashMap<>();

    @Override
    public boolean existsByOfferUrl(String offerUrl) {
        long count = offerList.values()
                .stream()
                .filter(offer -> offer.url().equals(offerUrl))
                .count();
        return count == 1;
    }

    @Override
    public Optional<Offer> findByOfferUrl(String offerUrl) {
        return Optional.of(offerList.get(offerUrl));
    }

    @Override
    public List<Offer> saveAll(List<Offer> offers) {
        return offers.stream()
                .map(this::save)
                .toList();
    }

    @Override
    public List<Offer> findAll() {
        return offerList.values()
                .stream()
                .toList();
    }

    @Override
    public Optional<Offer> findById(String id) {
        return Optional.ofNullable(offerList.get(id));
    }

    @Override
    public Offer save(Offer entity) {
        if (offerList.values().stream().anyMatch(offer -> offer.url().equals(entity.url()))) {
            throw new OfferDuplicateException(entity.url());
        }
        UUID id = UUID.randomUUID();
        Offer offer = Offer.builder()
                .id(id.toString())
                .companyName(entity.companyName())
                .position(entity.position())
                .salary(entity.salary())
                .url(entity.url())
                .build();
        offerList.put(id.toString(), offer);
        return offer;
    }
}
