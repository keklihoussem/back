package EMC.Web.emc.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class Agence implements Serializable{
	
	@Id
	private Long codeAgence;
	private String nomAgence;
	
	@OneToMany(cascade = CascadeType.ALL)
    private Set<User> users;
	
	@OneToMany(mappedBy = "agence")
	private Set<Client> clients;

	public Agence(Long codeAgence, String nomAgence, Set<User> users, Set<Client> clients) {
		super();
		this.codeAgence = codeAgence;
		this.nomAgence = nomAgence;
		this.users = users;
		this.clients = clients;
	}

	public Agence() {
		super();
	}

	public Long getCodeAgence() {
		return codeAgence;
	}

	public void setCodeAgence(Long codeAgence) {
		this.codeAgence = codeAgence;
	}

	public String getNomAgence() {
		return nomAgence;
	}

	public void setNomAgence(String nomAgence) {
		this.nomAgence = nomAgence;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Set<Client> getClients() {
		return clients;
	}

	public void setClients(Set<Client> clients) {
		this.clients = clients;
	}

	@Override
	public String toString() {
		return "Agence [codeAgence=" + codeAgence + ", nomAgence=" + nomAgence + ", users=" + users + ", clients="
				+ clients + "]";
	}
	
	
	

	
	

}
