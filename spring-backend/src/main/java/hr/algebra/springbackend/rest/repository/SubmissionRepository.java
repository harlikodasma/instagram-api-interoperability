package hr.algebra.springbackend.rest.repository;

import hr.algebra.springbackend.rest.model.jpa.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
}
