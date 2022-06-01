package rs.ac.uns.ftn.isa.fisherman.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.isa.fisherman.service.BoatOwnerService;
import rs.ac.uns.ftn.isa.fisherman.model.BoatOwner;
import rs.ac.uns.ftn.isa.fisherman.repository.BoatOwnerRepository;


@Service
public class BoatOwnerServiceImpl implements BoatOwnerService {
    private BoatOwnerRepository boatOwnerRepository;
    @Autowired
    public BoatOwnerServiceImpl(BoatOwnerRepository boatOwnerRepository) {
        this.boatOwnerRepository = boatOwnerRepository;
    }


    @Override
    public BoatOwner findByUsername(String ownersUsername) {
        return boatOwnerRepository.findByUsername(ownersUsername);
    }

    @Override
    public void save(BoatOwner boatOwner) {
        boatOwnerRepository.save(boatOwner);
    }




}
