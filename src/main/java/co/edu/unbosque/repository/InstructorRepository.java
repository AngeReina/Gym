package co.edu.unbosque.repository;

import co.edu.unbosque.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {
    Instructor findByNumeroDocumento(String numeroDocumento);
}
