package hr.algebra.springbackend.repository;

import hr.algebra.springbackend.model.jpa.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
}
