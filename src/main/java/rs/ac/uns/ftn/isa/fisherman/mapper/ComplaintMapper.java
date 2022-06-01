package rs.ac.uns.ftn.isa.fisherman.mapper;

import rs.ac.uns.ftn.isa.fisherman.dto.ComplaintDto;
import rs.ac.uns.ftn.isa.fisherman.model.Complaint;

public class ComplaintMapper {
    public ComplaintDto complaintToComplaintDto(Complaint complaint){
        return  new ComplaintDto(complaint.getId(),complaint.getText(),complaint.getDate(),complaint.isResponded(),complaint.getClient().getUsername(),complaint.getOwnersUsername(),complaint.getComplaintType());
    }

}
