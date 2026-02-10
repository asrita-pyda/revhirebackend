package org.example.revhire.repository;

import org.example.revhire.model.ApplicationNotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ApplicationNoteRepository extends JpaRepository<ApplicationNotes, Integer> {
    List<ApplicationNotes> findByApplication_Id(Integer applicationId);
}