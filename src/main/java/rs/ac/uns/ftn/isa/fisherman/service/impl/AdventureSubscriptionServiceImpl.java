package rs.ac.uns.ftn.isa.fisherman.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.isa.fisherman.model.AdventureSubscription;
import rs.ac.uns.ftn.isa.fisherman.model.Client;
import rs.ac.uns.ftn.isa.fisherman.repository.AdventureSubscriptionRepository;
import rs.ac.uns.ftn.isa.fisherman.service.AdventureSubscriptionService;
import rs.ac.uns.ftn.isa.fisherman.service.AdventureService;
import rs.ac.uns.ftn.isa.fisherman.service.ClientService;

import java.util.HashSet;
import java.util.Set;

@Service
public class AdventureSubscriptionServiceImpl implements AdventureSubscriptionService {

    @Autowired
    private ClientService clientService;
    @Autowired
    private AdventureService adventureService;
    @Autowired
    private AdventureSubscriptionRepository adventureSubscriptionRepository;

    @Override
    public void addSubscription(String clientUsername, Long adventureId) {
        Client client = clientService.findByUsername(clientUsername);
        if(adventureSubscriptionRepository.subscriptionExists(adventureId, client.getId()))
            return;
        adventureSubscriptionRepository.save(new AdventureSubscription(null, client, adventureService.findById(adventureId)));
    }

    @Override
    public void removeSubscription(String username, Long adventureId) {
        AdventureSubscription adventureSubscription = adventureSubscriptionRepository.getSubscriptionOnAdventure(adventureId, clientService.findByUsername(username).getId());
        adventureSubscriptionRepository.deleteById(adventureSubscription.getId());
    }

    @Override
    public boolean checkIfUserIsSubscribed(String username, Long adventureId) {
        return adventureSubscriptionRepository.subscriptionExists(adventureId, clientService.findByUsername(username).getId());
    }

    @Override
    public Set<AdventureSubscription> findSubscriptionsByClientUsername(String username) {
        Long clientId = clientService.findByUsername(username).getId();
        Set<AdventureSubscription> subscriptions = new HashSet<>();
        for(AdventureSubscription subscription:adventureSubscriptionRepository.findAll())
            if(subscription.getClient().getId().equals(clientId))
                subscriptions.add(subscription);
        return subscriptions;
    }

    @Override
    public Set<String> findAdventureSubscribers(Long adventureId) {
        return adventureSubscriptionRepository.findAdventureSubscribers(adventureId);
    }
}
