package rs.ac.uns.ftn.isa.fisherman.service;

import rs.ac.uns.ftn.isa.fisherman.model.OwnersReport;

import java.util.List;

public interface OwnersReportService {
    List<OwnersReport> getAllUnApprovedReports();
    void sendReviewResponse(String clientUsername, String ownersUsername, String comment);
    OwnersReport setReviewStatus(Long id);
    List<OwnersReport> findAll();
    OwnersReport findById(Long id);
}
