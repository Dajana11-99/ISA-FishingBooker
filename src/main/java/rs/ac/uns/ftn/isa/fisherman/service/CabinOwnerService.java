package rs.ac.uns.ftn.isa.fisherman.service;

import rs.ac.uns.ftn.isa.fisherman.model.CabinOwner;

public interface CabinOwnerService {

    CabinOwner findByUsername(String ownerUsername);
}
