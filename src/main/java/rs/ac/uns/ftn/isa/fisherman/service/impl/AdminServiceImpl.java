package rs.ac.uns.ftn.isa.fisherman.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.isa.fisherman.model.Admin;
import rs.ac.uns.ftn.isa.fisherman.repository.AdminRepository;
import rs.ac.uns.ftn.isa.fisherman.service.AdminService;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminRepository adminRepository;

    @Override
    public Admin findByUsername(String username) {
        return adminRepository.findByUsername(username);
    }

    public Boolean isPredefined(String username){
        Admin admin= adminRepository.findByUsername(username);
        return admin.getPredefined();
    }
    public List<Admin> getAllAdmin(){
        return adminRepository.findAll();
    }

    public Boolean hasAlreadyResetPassword(String username){
        Admin admin= adminRepository.findByUsername(username);
        return admin.getChangedPassword();
    }
}
