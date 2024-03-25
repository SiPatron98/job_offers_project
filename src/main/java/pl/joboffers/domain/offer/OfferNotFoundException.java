package pl.joboffers.domain.offer;

import lombok.Getter;

@Getter
public class OfferNotFoundException extends RuntimeException {

    private final String offerId;
    public OfferNotFoundException(String offerId) {
        super("Cannot find offer with this id: [%s]".formatted(offerId));
        this.offerId = offerId;
    }
}
