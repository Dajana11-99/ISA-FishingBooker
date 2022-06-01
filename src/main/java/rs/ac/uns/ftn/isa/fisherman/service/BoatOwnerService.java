package rs.ac.uns.ftn.isa.fisherman.service;

import rs.ac.uns.ftn.isa.fisherman.model.BoatOwner;

public interface BoatOwnerService {
    BoatOwner findByUsername(String ownersUsername);
    void save(BoatOwner boatOwner);
}
