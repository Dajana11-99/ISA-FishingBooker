package rs.ac.uns.ftn.isa.fisherman.service;

import rs.ac.uns.ftn.isa.fisherman.dto.NewComplaintDto;
import rs.ac.uns.ftn.isa.fisherman.model.Complaint;

import java.util.List;

public interface ComplaintService {

    boolean addComplaint(NewComplaintDto newComplaintDto);

    List<Complaint> getAll();

    boolean sendMailAboutComplaint(Complaint complaint,String response);

    Complaint getOne(Long id);
}
