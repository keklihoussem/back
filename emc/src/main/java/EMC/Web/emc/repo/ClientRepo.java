package EMC.Web.emc.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import EMC.Web.emc.entities.Client;

public interface ClientRepo extends JpaRepository<Client, Long>{

}
