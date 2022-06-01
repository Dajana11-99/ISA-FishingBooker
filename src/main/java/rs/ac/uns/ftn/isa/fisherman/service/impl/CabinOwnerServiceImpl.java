package rs.ac.uns.ftn.isa.fisherman.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.isa.fisherman.service.CabinOwnerService;
import rs.ac.uns.ftn.isa.fisherman.model.CabinOwner;
import rs.ac.uns.ftn.isa.fisherman.repository.CabinOwnerRepository;


@Service
public class CabinOwnerServiceImpl implements CabinOwnerService {
    private CabinOwnerRepository cabinOwnerRepository;
    @Autowired
    public CabinOwnerServiceImpl(CabinOwnerRepository cabinOwnerRepository) {
        this.cabinOwnerRepository = cabinOwnerRepository;
    }
    @Override
    public CabinOwner findByUsername(String username) {
        return cabinOwnerRepository.findByUsername(username);
    }

}
