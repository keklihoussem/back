package EMC.Web.emc.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import EMC.Web.emc.entities.FileDB;




@Repository
public interface FileDBRepository extends JpaRepository<FileDB, String> {

}